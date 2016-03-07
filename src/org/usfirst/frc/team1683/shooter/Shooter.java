package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.Settings;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.Piston;
import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.InputFilter;
import org.usfirst.frc.team1683.vision.FindGoal;

public class Shooter {
	public static final double TOLERANCE = 1.0;
	public static final double SPEED_TOLERANCE = 0.05;
	public static final double SHOOT_SPEED = SmartDashboard.getDouble("Shooter speed");
	public static final boolean EXTENDED = true;
	public static final boolean RETRACTED = false;

	private final double MAX_ENCODER_COUNT = 512;
	private final double MIN_ENCODER_COUNT = 0;
	private final double MAX_ANGLE = 46;
	private final double MIN_ANGLE = -4;
	private final double POSITION_TO_ANGLE_COEFFICENT = (MAX_ANGLE - MIN_ANGLE)
			/ (MAX_ENCODER_COUNT - MIN_ENCODER_COUNT);
	private final double ANGLE_TO_POSITION_COEFFICENT = (MAX_ENCODER_COUNT - MIN_ENCODER_COUNT)
			/ (MAX_ANGLE - MIN_ANGLE);

	public final static double FORWARD_LIMIT_ANGLE = 19;
	public final static double BACK_LIMIT_ANGLE = 46;

	private InputFilter inputFilter;

	private double targetPos;
	FindGoal vision;

//	MotorGroup shooterMotors;
	TalonSRX leftMotor;
	TalonSRX rightMotor;
	TalonSRX angleMotor;

	Piston shootPiston;

	private boolean isCreated = false;
	private State presentTeleOpState;
	public static final double INTAKE_SPEED = -2000;
	private static final double INTAKE_ANGLE = 0; // Pick better angle
	private static final double HOLD_SPEED = 0;

	public enum State {
		INTAKE, HOLD, SHOOT;
	}

	public Shooter(int leftChannel, int rightChannel, TalonSRX angleMotor, Piston shootPiston, FindGoal vision) {
		this(leftChannel, rightChannel, angleMotor, shootPiston);
		// this.accel = accel;
		this.vision = vision;
	}

	public Shooter(int leftChannel, int rightChannel, TalonSRX angleMotor, Piston shootPiston) {
		this.angleMotor = angleMotor;
		this.shootPiston = shootPiston;

		// this.angleMotor.configEncoderCodesPerRev(4096);

		this.angleMotor.calibrate();
		this.angleMotor.enableLimitSwitch(true, true);
		this.angleMotor.PIDInit();
		
		this.leftMotor = new TalonSRX(leftChannel, false);
		this.rightMotor = new TalonSRX(rightChannel, true);
		
		this.leftMotor.PIDInit();
		this.rightMotor.PIDInit();

		this.leftMotor.enableBrakeMode(false); // coast mode
		this.rightMotor.enableBrakeMode(false); // coast mode
		this.leftMotor.reverseSensor(false);
		this.rightMotor.reverseSensor(false);

		inputFilter = new InputFilter(SmartDashboard.getDouble("Shooter K"));

	}

	public double getJoystickAngle() {
		double angle;
		if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) > 0.04) {
			angle = DriverStation.scaleToMax(DriverStation.auxStick);
		} else if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) < -0.04) {
			angle = DriverStation.scaleToMin(DriverStation.auxStick);
		} else {
			angle = 0;
		}

		SmartDashboard.sendData("Joystick position", angle);
		// SmartDashboard.sendData("Joystick filtered position", angle);
		return angle;
	}

	public void setShooterSensitivity(double sensitivity) {
		inputFilter.setFilterK(sensitivity);
	}

	/**
	 * Extends pistons to shoot ball
	 */
	public void shootBall() {
		if (!shootPiston.isExtended())
			shootPiston.extend();
	}

	/**
	 * Retracts Pistons
	 */
	public void resetShootPistons() {
		if (shootPiston.isExtended()) {
			shootPiston.retract();
		}
	}

	/**
	 * Set shooter to absolute position angle
	 * 
	 * @param angle
	 */
	public void angleShooter(double angle) {
		if (angle < FORWARD_LIMIT_ANGLE) {
			angle = FORWARD_LIMIT_ANGLE;
		} else if (angle > BACK_LIMIT_ANGLE) {
			angle = BACK_LIMIT_ANGLE;
		}

		updatePrefs();

		SmartDashboard.sendData("Shooter Set Angle", angle);

		angle = inputFilter.filterInput(angle);

		SmartDashboard.sendData("Shooter Filtered Angle", angle);

		angle = angle * ANGLE_TO_POSITION_COEFFICENT;

		angleMotor.PIDPosition(angle);
	}

	/**
	 * TeleOp
	 */
	public void shootMode() {

		stateSwitcher(isCreated);

		if (presentTeleOpState == State.SHOOT && DriverStation.auxStick.getRawButton(HWR.SHOOT_BALL))
			shootBall();
		else
			resetShootPistons();

		updatePrefs();
		report();
	}

	public void intake(double angle) {
		leftMotor.PIDSpeed(INTAKE_SPEED);
		rightMotor.PIDSpeed(-INTAKE_SPEED);
		angleMotor.PIDPosition(angle);
	}

	public void hold(double angle) {
		leftMotor.stop();
		rightMotor.stop();
//		leftMotor.PIDSpeed(0);
//		rightMotor.PIDSpeed(0);
		angleMotor.PIDPosition(angle);
	}

	public void shoot(double speed, double angle) {
		leftMotor.PIDSpeed(speed);
		rightMotor.PIDSpeed(-speed);
		angleMotor.PIDPosition(angle);
	}
	
	public void updatePIDF() {
		Settings.updateSettings();
		
		leftMotor.setP(Settings.shooterMotorP);
		leftMotor.setI(Settings.shooterMotorI);
		leftMotor.setD(Settings.shooterMotorD);
		leftMotor.setF(Settings.shooterMotorF);
		
		rightMotor.setP(Settings.shooterMotorP);
		rightMotor.setI(Settings.shooterMotorI);
		rightMotor.setD(Settings.shooterMotorD);
		rightMotor.setF(Settings.shooterMotorF);
		
		leftMotor.setCloseLoopRampRate(Settings.shooterRampRate);
		rightMotor.setCloseLoopRampRate(Settings.shooterRampRate);
	}

	public void stateSwitcher(boolean isCreated) {
		String state = "out";
		State nextState;
		
		updatePIDF();

		if (!isCreated) {
			presentTeleOpState = State.HOLD;
			this.isCreated = true;
		}
		switch (presentTeleOpState) {
		case HOLD: {
			state = "HOLD";
			hold(getJoystickAngle());
			nextState = State.HOLD;
			if (DriverStation.auxStick.getRawButton(HWR.SPIN_UP_INTAKE)
					&& DriverStation.auxStick.getRawButton(HWR.SPIN_UP_SHOOTER)) {
				nextState = State.HOLD;
			} else if (DriverStation.auxStick.getRawButton(HWR.SPIN_UP_SHOOTER)) {
				nextState = State.SHOOT;
			} else if (DriverStation.auxStick.getRawButton(HWR.SPIN_UP_INTAKE)) {
				nextState = State.INTAKE;
			}
			break;
		}
		case INTAKE: {
			state = "INTAKE";
			intake(getJoystickAngle());
			nextState = State.INTAKE;
			if (!DriverStation.auxStick.getRawButton(HWR.SPIN_UP_INTAKE)) {
				nextState = State.HOLD;
			}

			break;
		}
		case SHOOT: {
			state = "SHOOT";
			shoot(SmartDashboard.getDouble("TeleOp Shooter Target Speed"), getJoystickAngle());
			nextState = State.SHOOT;
			if (!DriverStation.auxStick.getRawButton(HWR.SPIN_UP_SHOOTER)) {
				nextState = State.HOLD;
			}
			break;

		}
		default: {
			nextState = State.HOLD;
			SmartDashboard.sendData("Shooter state machine error", true);
			break;
		}
		}

		presentTeleOpState = nextState;
		SmartDashboard.sendData("Shooter state", state);
//		report();
	}

	public void reset() {
		targetPos = angleMotor.getPosition();
		angleMotor.calibrate();
	}

	public void report() {
		SmartDashboard.sendData("Shooter Encoder Position", angleMotor.getEncPosition());
		SmartDashboard.sendData("Shooter Position", angleMotor.getPosition());
		SmartDashboard.sendData("Shooter Angle", angleMotor.getPosition() * POSITION_TO_ANGLE_COEFFICENT);
		
		SmartDashboard.sendData("Left Talon Target", leftMotor.PIDTargetSpeed());
		SmartDashboard.sendData("Right Talon Target", rightMotor.PIDTargetSpeed());
		SmartDashboard.sendData("Left Talon Speed", leftMotor.getSpeed());
		SmartDashboard.sendData("Right Talon Speed", rightMotor.getSpeed());
		SmartDashboard.sendData("Left Talon Error", leftMotor.PIDErrorSpeed());
		SmartDashboard.sendData("Right Talon Error", rightMotor.PIDErrorSpeed());
		
		SmartDashboard.sendData("Left Setpoint", leftMotor.getSetpoint());
		SmartDashboard.sendData("Left Speed", leftMotor.get());
		SmartDashboard.sendData("Left Error", leftMotor.getClosedLoopError()/4096.0);
		
		SmartDashboard.sendData("right Setpoint", rightMotor.getSetpoint());
		SmartDashboard.sendData("right Speed", rightMotor.get());
		SmartDashboard.sendData("right Error", rightMotor.getClosedLoopError()/4096.0);
	}

	public void updatePrefs() {
		inputFilter.setFilterK(SmartDashboard.getDouble("Shooter K"));
	}

	/**
	 * Set shooter to speed {@param rpm}
	 * 
	 * @param rpm
	 */
	public void spinShooter(double rpm) {
		leftMotor.PIDSpeed(rpm);
		rightMotor.PIDSpeed(-rpm);
	}
}
