package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.vision.FindGoal;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.Piston;
import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.InputFilter;

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
	
	private final double FORWARD_LIMIT_ANGLE = 19;
	private final double BACK_LIMIT_ANGLE = 46;

	private InputFilter inputFilter;

	private double targetPos;
	FindGoal vision;
	
	MotorGroup shooterMotors;
	TalonSRX angleMotor;

	Piston shootPiston;

	public Shooter(MotorGroup group, TalonSRX angleMotor, Piston shootPiston, FindGoal vision) {
		this(group, angleMotor, shootPiston);
		// this.accel = accel;
		this.vision = vision;
	}

	public Shooter(MotorGroup group, TalonSRX angleMotor, Piston shootPiston) {
		this.angleMotor = angleMotor;
		this.shootPiston = shootPiston;
		this.shooterMotors = group;

		// this.angleMotor.configEncoderCodesPerRev(4096);

		this.angleMotor.calibrate();
		this.angleMotor.enableLimitSwitch(true, true);
		this.angleMotor.PIDInit();
		this.shooterMotors.PIDInit();

		inputFilter = new InputFilter(SmartDashboard.getDouble("Shooter K"));

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
	 * Set shooter to speed {@param rpm}
	 * 
	 * @param rpm
	 */
	public void spinShooter(double rpm) {
		shooterMotors.PIDSpeed(rpm);
	}

	/**
	 * TeleOp
	 */
	public void shootMode() {
		if (DriverStation.auxStick.getRawButton(HWR.SHOOT_BALL))
			shootBall();
		else
			resetShootPistons();

		if (DriverStation.auxStick.getRawButton(HWR.SPIN_UP_SHOOTER)) {
			shooterMotors.PIDSpeed(SmartDashboard.getDouble("TeleOp Shooter Target Speed"));
			// shooterMotors.PIDSpeed(SHOOT_SPEED);
		} else {
			shooterMotors.enableBrakeMode(false);
			shooterMotors.stop();
		}

		double angle;
		
		 if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) > 0.04) {
			 angle = DriverStation.scaleToMax(DriverStation.auxStick);
		 } else if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) < -0.04) {
			 angle = DriverStation.scaleToMin(DriverStation.auxStick);		
		 } else {
			 angle = 0;
		 }
		 
		 SmartDashboard.sendData("Joystick position", angle);
//		 SmartDashboard.sendData("Joystick filtered position", angle);
		 angleShooter(angle);

//		angleShooter(SmartDashboard.getDouble("shooter angle position"));

		SmartDashboard.sendData("Shooter Left Spin Speed", ((TalonSRX) shooterMotors.get(0)).getSpeed());
		SmartDashboard.sendData("Shooter Right Spin Speed", ((TalonSRX) shooterMotors.get(1)).getSpeed());
		SmartDashboard.sendData("Shooter Target Angle", targetPos);
		
		updatePrefs();
	}

	public void reset() {
		targetPos = angleMotor.getPosition();
		angleMotor.calibrate();
	}

	public void report() {
		SmartDashboard.sendData("Shooter Encoder Position", angleMotor.getEncPosition());
		SmartDashboard.sendData("Shooter Position", angleMotor.getPosition());
		SmartDashboard.sendData("Shooter Angle", angleMotor.getPosition() * POSITION_TO_ANGLE_COEFFICENT);
	}
	
	public void updatePrefs() {
		inputFilter.setFilterK(SmartDashboard.getDouble("Shooter K"));
	}
}
