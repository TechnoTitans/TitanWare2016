package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.Settings;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.Solenoid;
import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.InputFilter;
import org.usfirst.frc.team1683.sensors.LinearActuator;
import org.usfirst.frc.team1683.vision.FindGoal;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

	public static final double MAX_DISTANCE = 136;
	public static final double MIN_DISTANCE = 80.6;
	
	public static final double FORWARD_LIMIT_ANGLE = 10;
	public static final double BACK_LIMIT_ANGLE = 65;
	 
	public static final int ALLOWABLE_ERROR = 400; // find num

	public static final double INTAKE_SPEED = -3000;
	public static final double LOW_GOAL_ANGLE = 30; // Pick better angle
	public static final double LOW_GOAL_SPEED = 4500;
	private static final double DEFAULT_SPEED = 4500;
	public static final double ANGLE_OFFSET = 60; // change based on shooter
													// mounting
	// For calculating floor distance
	public static final double TARGET_OVERHANG = 5;
	public static final double TARGET_HEIGHT = 97;
	public static final double CAMERA_HOR_OFF = 8;
	public static final double CAMERA_VER_OFF = 13;

	// For smoothing changing shooter angles
	private InputFilter inputFilter;

	TalonSRX leftMotor;
	TalonSRX rightMotor;
	TalonSRX angleMotor;

	Solenoid shootPiston;

	// private boolean isCreated = false;
	private State presentTeleOpState;

	public enum State {
		INTAKE, HOLD, SHOOT;
	}

	public Shooter(int leftChannel, int rightChannel, int angleMotorChannel, Solenoid shootPiston) {
		this.angleMotor = new TalonSRX(angleMotorChannel, true);
		this.leftMotor = new TalonSRX(leftChannel, false);
		this.rightMotor = new TalonSRX(rightChannel, false);
		this.shootPiston = shootPiston;

		this.angleMotor.PIDInit();
		this.leftMotor.PIDInit();
		this.rightMotor.PIDInit();

		// this.angleMotor.configEncoderCodesPerRev(4096);

		this.angleMotor.calibrate();
		this.angleMotor.setAllowableClosedLoopErr(0);
		// this.angleMotor.enableLimitSwitch(true, true);

		this.leftMotor.enableBrakeMode(false); // coast mode
		this.rightMotor.enableBrakeMode(false); // coast mode
		this.leftMotor.reverseSensor(false);
		this.rightMotor.reverseSensor(false);

		inputFilter = new InputFilter(SmartDashboard.getDouble("Shooter K"));

		this.presentTeleOpState = State.HOLD;

		// actuator = new LinearActuator(actuatorChannel, false);

	}

	/**
	 * returns scaled joystick output
	 * 
	 * @return
	 */
	public double getJoystickAngle() {
		double angle;
		// if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) > 0.04) {
		// angle = DriverStation.scaleToMax(DriverStation.auxStick);
		// } else if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) <
		// -0.04) {
		// angle = DriverStation.scaleToMin(DriverStation.auxStick);
		// } else {
		// angle = (BACK_LIMIT_ANGLE + FORWARD_LIMIT_ANGLE) / 2;
		// }

		angle = (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) + 1) / 2
				* (BACK_LIMIT_ANGLE - FORWARD_LIMIT_ANGLE) + FORWARD_LIMIT_ANGLE;

		SmartDashboard.sendData("Joystick position", angle);

		return angle;
	}

	public void setShooterSensitivity(double sensitivity) {
		inputFilter.setFilterK(sensitivity);
	}

	public double setSpeed() {
		double speed = DriverStation.auxStick.getRawAxis(DriverStation.ZAxis) * 750 + 3750;
		SmartDashboard.sendData("Shooter speed", speed);
		return speed;
	}

	/**
	 * Extends pistons to shoot ball
	 */
	public void shootBall() {
		if (!shootPiston.isExtended())
			shootPiston.set(true);
	}

	/**
	 * Retracts Pistons
	 */
	public void resetShootPistons() {
		if (shootPiston.isExtended()) {
			shootPiston.set(false);
		}
	}

	/**
	 * takes in distance from vision in inches
	 * 
	 * @param visDist
	 * @return
	 */
	public double getFloorDistance(double visDist) {
		return Math.sqrt(Math.pow(visDist, 2) - Math.pow(TARGET_HEIGHT - CAMERA_VER_OFF, 2)) + CAMERA_HOR_OFF
				+ TARGET_OVERHANG;
	}

	public double getSpeed() {
		double visionDistance = SmartDashboard.getDouble("Vision Distance");
		double speed;
		if (getAngle() < LOW_GOAL_ANGLE)
			speed = LOW_GOAL_SPEED;
		else if (getFloorDistance(visionDistance) < MAX_DISTANCE && getFloorDistance(visionDistance) > MIN_DISTANCE)
			speed = 0.0001054 * Math.pow(getFloorDistance(visionDistance), 4)
					- 0.04996 * Math.pow(getFloorDistance(visionDistance), 3)
					+ 8.614 * Math.pow(getFloorDistance(visionDistance), 2) - 632.4 * getFloorDistance(visionDistance)
					+ 2.083 * Math.pow(10, 4);
		else
			speed = DEFAULT_SPEED;
		SmartDashboard.sendData("Shooter Speed", speed);
		return speed;
	}

	public double getAngle() {
		double visionDistance = SmartDashboard.getDouble("Vision Distance");
		double angle;

		if (DriverStation.auxStick.getRawButton(HWR.SWITCH_SHOOTER_MODE))
			angle = getJoystickAngle();

		else if (getFloorDistance(visionDistance) < MAX_DISTANCE && getFloorDistance(visionDistance) > MIN_DISTANCE)
			angle = 0.0007799 * Math.pow(getFloorDistance(visionDistance), 2)
					- 0.3196 * getFloorDistance(visionDistance) + 89.48;
		else
			angle = getJoystickAngle();

		// SmartDashboard.sendData("Shooter Angle", angle);
		return angle;
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

		angle -= ANGLE_OFFSET;

		updatePIDF();

		SmartDashboard.sendData("Shooter Set Angle", angle);

		angle = inputFilter.filterInput(angle);

		SmartDashboard.sendData("Shooter Filtered Angle", angle);

		// angle = angle * ANGLE_TO_POSITION_COEFFICENT;

		// angleMotor.PIDPosition(angle);
		angleMotor.setFeedbackDevice(FeedbackDevice.AnalogPot);
		angleMotor.PIDAngle(angle);
	}

	/**
	 * TeleOp
	 */
	public void shootMode() {
		updatePIDF();
		updatePrefs();

		stateSwitcher();

		// angleClimberPistons();

		if (presentTeleOpState == State.SHOOT && DriverStation.auxStick.getRawButton(HWR.SHOOT_BALL))
			shootBall();
		else
			resetShootPistons();
		// angleShooter(getAngle(SmartDashboard.getDouble("Target Distance")));

		updatePrefs();
		report();
	}

	public void intake() {
		leftMotor.PIDSpeed(INTAKE_SPEED);
		rightMotor.PIDSpeed(INTAKE_SPEED);

	}

	public void hold() {
		leftMotor.stop();
		rightMotor.stop();
		// leftMotor.PIDSpeed(0);
		// rightMotor.PIDSpeed(0);

	}

	public void shoot(double speed) {
		leftMotor.PIDSpeed(speed);
		rightMotor.PIDSpeed(speed);

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

		angleMotor.setP(Settings.angleMotorP);
		angleMotor.setI(Settings.angleMotorI);
		angleMotor.setD(Settings.angleMotorD);
	}

	public void stateSwitcher() {
		String state = "out";
		double speed = setSpeed();
		State nextState;

		updatePIDF();

		angleShooter(getJoystickAngle());
		// if (!isCreated) {
		// presentTeleOpState = State.HOLD;
		// this.isCreated = true;
		// }
		switch (presentTeleOpState) {
		case HOLD: {
			state = "HOLD";
			hold();
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
			intake();
			nextState = State.INTAKE;
			if (!DriverStation.auxStick.getRawButton(HWR.SPIN_UP_INTAKE)) {
				nextState = State.HOLD;
			}

			break;
		}
		case SHOOT: {
			state = "SHOOT";
			// shoot(getSpeed());
			shoot(speed);
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
		// report();
	}

	public void reset() {
		angleMotor.getPosition();
		angleMotor.calibrate();
	}

	public void report() {
		SmartDashboard.sendData("Shooter Encoder Position", angleMotor.getEncPosition());
		SmartDashboard.sendData("Shooter Position", angleMotor.getPosition());
		// This is the position you're looking for.
		SmartDashboard.sendData("Shooter relAngle", angleMotor.getPosition() * 360);
		SmartDashboard.sendData("Shooter Angle", BACK_LIMIT_ANGLE + (angleMotor.getPosition() * 360));

		SmartDashboard.sendData("Left Talon Target", leftMotor.PIDTargetSpeed());
		SmartDashboard.sendData("Right Talon Target", rightMotor.PIDTargetSpeed());
		SmartDashboard.sendData("Left Talon Speed", leftMotor.getSpeed());
		SmartDashboard.sendData("Right Talon Speed", rightMotor.getSpeed());
		SmartDashboard.sendData("Left Talon Error", leftMotor.PIDErrorSpeed());
		SmartDashboard.sendData("Right Talon Error", rightMotor.PIDErrorSpeed());

		SmartDashboard.sendData("Left Setpoint", leftMotor.getSetpoint());
		SmartDashboard.sendData("Left Speed", leftMotor.get());
		SmartDashboard.sendData("Left Error", leftMotor.getClosedLoopError() / 4096.0);

		SmartDashboard.sendData("right Setpoint", rightMotor.getSetpoint());
		SmartDashboard.sendData("right Speed", rightMotor.get());
		SmartDashboard.sendData("right Error", rightMotor.getClosedLoopError() / 4096.0);
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
