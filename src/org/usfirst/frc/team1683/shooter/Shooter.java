package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.sensors.AccelSPI;
import org.usfirst.frc.team1683.sensors.AnalogAccel;
import org.usfirst.frc.team1683.vision.FindGoal;
import org.usfirst.frc.team1683.sensors.LimitSwitch;
import org.usfirst.frc.team1683.sensors.TiltSensor;

import edu.wpi.first.wpilibj.PIDController;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.Piston;
import org.usfirst.frc.team1683.robot.HWP;
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
		this.accel = accel;

		this.angleMotor.calibrate();
		this.angleMotor.enableLimitSwitch(true, true);
		this.angleMotor.PIDInit();
		this.shooterMotors.PIDInit();
		
	}

	/**
	 * 
	 * 
	 * /** Angles shooter using Accelerometer
	 * 
	 * @param angle
	 *            Angle from getAngle
	 */
	// public void angleShooterAccel(double angle) {
	// double speed = 0.3; // find steady speed
	// while (accel.getAngleXZ() >= angle + TOLERANCE && accel.getAngleXZ() <=
	// angle - TOLERANCE) {
	// if (accel.getAngleXZ() >= angle + TOLERANCE)
	// angleMotor.set(-speed);// turn negative speed
	// else if (accel.getAngleXZ() <= angle - TOLERANCE)
	// angleMotor.set(speed);
	// ; // turn positive speed
	// }
	//
	// if (bottomLimit.isPressed() || upperLimit.isPressed())
	// angleMotor.PIDPosition(angleMotor.getPosition());
	// }

	/**
	 * PID controls for the angleMotor
	 */
	// public void enablePIDControl() {
	// double p = SmartDashboard.getDouble("P");
	// double i = SmartDashboard.getDouble("I");
	// double d = SmartDashboard.getDouble("D");
	// double tolerance = SmartDashboard.getDouble("PIDTolerance");
	// PIDController pidControl = new PIDController(p, i, d, analogAccel,
	// angleMotor);
	// pidControl.setOutputRange(0, 1);
	// pidControl.setPercentTolerance(tolerance);
	// }

	/**
	 * Extends pistons to shoot ball
	 */
	public void shootBall() {
		if (DriverStation.auxStick.getRawButton(HWR.ACTUATE_PISTON_SHOOTER))
			shootPiston.extend();
		else
			shootPiston.retract();

		if (DriverStation.auxStick.getRawButton(HWR.SHOOTER_BUTTON)) {
			shooterMotors.PIDSpeed(SmartDashboard.getDouble("Shooter Target Speed"));
			// shooterMotors.PIDSpeed(SHOOT_SPEED);
		} else {
			shooterMotors.enableBrakeMode(false);
			shooterMotors.stop();
		}

	}

	/**
	 * Angles shooter using Joy Sticks
	 */
	public void joystickAngleShooter() {
		if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) > 0.04) {
			// angleMotor.set(DriverStation.scaleToMax(DriverStation.auxStick));
			targetPos += SmartDashboard.getDouble("TeleOp Angle Motor Sensitivity");
			angleMotor.PIDPosition(targetPos);
		} else if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) < -0.04) {
			targetPos -= SmartDashboard.getDouble("TeleOp Angle Motor Sensitivity");
			angleMotor.PIDPosition(targetPos);
			// angleMotor.set(DriverStation.scaleToMin(DriverStation.auxStick));
		} else {
			angleMotor.PIDPosition(targetPos);
		}
		SmartDashboard.sendData("angle motor target position", targetPos);
	}

	public void angleShooter(double angle) {
		if (angle < -10) {
			angle = -10;
		} else if (angle > 50) {
			angle = 50;
		}
		angleMotor.PIDPosition(angle);
	}

	public void shootMode() {
		shootBall();
		joystickAngleShooter();
	}

	public void reset() {
		targetPos = angleMotor.getPosition();
		angleMotor.calibrate();
	}
}
