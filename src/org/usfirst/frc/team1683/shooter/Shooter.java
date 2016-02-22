package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.vision.FindGoal;
import org.usfirst.frc.team1683.sensors.TiltSensor;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.Piston;
import org.usfirst.frc.team1683.robot.HWR;

public class Shooter {
	public static final double TOLERANCE = 1.0;
	public static final double SPEED_TOLERANCE = 0.05;
	public static final double SHOOT_SPEED = SmartDashboard.getDouble("Shooter speed");
	public static final boolean EXTENDED = true;
	public static final boolean RETRACTED = false;

	private double targetPos;

	FindGoal vision;
	TiltSensor accel;

	MotorGroup shooterMotors;
	TalonSRX angleMotor;

	Piston shootPiston;

	public Shooter(MotorGroup group, TalonSRX angleMotor, Piston shootPiston, TiltSensor accel, FindGoal vision) {
		this(group, angleMotor, shootPiston);
		// this.accel = accel;
		this.vision = vision;
	}

	public Shooter(MotorGroup group, TalonSRX angleMotor, Piston shootPiston) {
		this.angleMotor = angleMotor;
		this.shootPiston = shootPiston;
		this.shooterMotors = group;

		this.angleMotor.calibrate();
		this.angleMotor.enableLimitSwitch(true, true);
		this.angleMotor.PIDInit();
		this.shooterMotors.PIDInit();

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
	 * @param angle
	 */
	public void angleShooter(double angle) {
		if (angle < -10) {
			angle = -10;
		} else if (angle > 50) {
			angle = 50;
		}
		angleMotor.PIDPosition(angle);
	}

	/**
	 * Set shooter to speed {@param rpm}
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
//			shooterMotors.PIDSpeed(SHOOT_SPEED);
		} else {
			shooterMotors.enableBrakeMode(false);
			shooterMotors.stop();
		}
		
		if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) > 0.04) {
//			targetPos += SmartDashboard.getDouble("TeleOp Angle Motor Sensitivity")*DriverStation.auxStick.getRawAxis(DriverStation.YAxis);
//			targetPos += SmartDashboard.getDouble("TeleOp Angle Motor Sensitivity");
			SmartDashboard.sendData("Joystick target position", DriverStation.scaleToMax(DriverStation.auxStick));
			angleMotor.PIDPosition(DriverStation.scaleToMax(DriverStation.auxStick));
		} else if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) < -0.04) {
//			targetPos -= SmartDashboard.getDouble("TeleOp Angle Motor Sensitivity")*DriverStation.auxStick.getRawAxis(DriverStation.YAxis);
//			targetPos -= SmartDashboard.getDouble("TeleOp Angle Motor Sensitivity");
			SmartDashboard.sendData("Joystick target position", DriverStation.scaleToMin(DriverStation.auxStick));
			angleMotor.PIDPosition(DriverStation.scaleToMin(DriverStation.auxStick));
		} else {
//			angleMotor.PIDPosition(0);
		}
//		angleMotor.PIDPosition(SmartDashboard.getDouble("shooter angle position"));
		
		SmartDashboard.sendData("Shooter Left Spin Speed", ((TalonSRX) shooterMotors.get(0)).getSpeed());
		SmartDashboard.sendData("Shooter Right Spin Speed", ((TalonSRX) shooterMotors.get(1)).getSpeed());
		SmartDashboard.sendData("Shooter Target Angle", targetPos);
	}

	public void reset() {
		targetPos = angleMotor.getPosition();
		angleMotor.calibrate();
	}
}
