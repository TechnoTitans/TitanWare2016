package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.sensors.AccelSPI;
import org.usfirst.frc.team1683.sensors.AnalogAccel;

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
	MotorGroup group;
	AccelSPI accel;
	Motor angleMotor;
	Piston shootPiston;
	AnalogAccel analogAccel;

	public Shooter(MotorGroup group, Motor angleMotor, AccelSPI accel, Piston shootPiston) {
		this.group = group;
		this.accel = accel;
		this.angleMotor = angleMotor;
		this.shootPiston = shootPiston;
	}

	public Shooter(Motor angleMotor, MotorGroup group, Piston shootPiston) {
		this.angleMotor = angleMotor;
		this.group = group;
		this.shootPiston = shootPiston;

	}
	/**
	 * TODO: use distance from vision to find optimum angle 
	 * @param distance
	 * 				Distance from vision 
	 */
	public void getAngle(double distance) {
		
	}
	
	/**
	 * Angles shooter using Accelerometer
	 * @param angle 
	 * 			  Angle from getAngle
	 */
	public void angleShooter(double angle) {
		double speed = 0.3; // find steady speed
		while (accel.getAngleXZ() >= angle + TOLERANCE && accel.getAngleXZ() <= angle - TOLERANCE) {
			if (accel.getAngleXZ() >= angle + TOLERANCE)
				angleMotor.set(-speed);// turn negative speed
			else if (accel.getAngleXZ() <= angle - TOLERANCE)
				angleMotor.set(speed);
			; // turn positive speed
		}
	}
	

	/**
	 * PID controls for the angleMotor
	 */
	public void enablePIDControl() {
		double p = SmartDashboard.getDouble("PIDValueP");
		double i = SmartDashboard.getDouble("PIDValueI");
		double d = SmartDashboard.getDouble("PIDValueD");
		double tolerance = SmartDashboard.getDouble("PIDTolerance");
		PIDController pidControl = new PIDController(p,i,d, analogAccel, angleMotor);
		pidControl.setOutputRange(0, 1);
		pidControl.setPercentTolerance(tolerance);
	}
	

	/**
	 * Spins wheels up to speed 
	 * TODO: change speed based off distance and angle
	 * @param distance
	 * 				Distance from vision
	 * @param angle
	 * 			    Angle based off distance
	 */
	public boolean spinRollers(double distance, double angle) {
		return false;
	}
	
	/**
	 * Extends pistons to shoot ball
	 */
	public void shootBall() {
		if (DriverStation.antiBounce(HWR.AUX_JOYSTICK, HWR.ACTUATE_PISTON_SHOOTER))
			shootPiston.extend();
		
		if(DriverStation.antiBounce(HWR.AUX_JOYSTICK, HWR.SHOOTER_TOGGLE)) group.set(SHOOT_SPEED);
		else group.stop();
		
	}
	

	/**
	 * Angles shooter using Joy Sticks
	 */
	public void joystickAngleShooter() {
		if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) > 0.04)
			angleMotor.set(DriverStation.scaleToMax(DriverStation.auxStick));
		else if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) < -0.04)
			angleMotor.set(DriverStation.scaleToMin(DriverStation.auxStick));
	}

	public Shooter(MotorGroup group, Motor angleMotor, AccelSPI accel) {
		this.group = group;
		this.accel = accel;
		this.angleMotor = angleMotor;
	}

}
