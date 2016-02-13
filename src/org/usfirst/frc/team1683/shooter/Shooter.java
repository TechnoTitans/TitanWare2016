package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.sensors.AccelSPI;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.Piston;
import org.usfirst.frc.team1683.robot.HWP;
import org.usfirst.frc.team1683.robot.HWR;

public class Shooter {
	public static final double TOLERANCE = 1.0;
	public static final double UP_ANGLE_SPEED = SmartDashboard.getDouble("angleMotor Up Speed");
	public static final double DOWN_ANGLE_SPEED = SmartDashboard.getDouble("angleMotor Down Speed");
	public static final double SHOOT_SPEED = SmartDashboard.getDouble("Shooter speed");
	public static final boolean EXTENDED = true;
	public static final boolean RETRACTED = false;
	MotorGroup group;
	AccelSPI accel;
	Motor angleMotor;
	Piston shootPiston;

	public Shooter(MotorGroup group, Motor angleMotor, AccelSPI accel, Piston shootPiston) {
		this.group = group;
		this.accel = accel;
		this.angleMotor = angleMotor;
		this.shootPiston = shootPiston;
	}

	public Shooter(Motor angleMotor, MotorGroup group, Piston shootPiston) {
		// this.angleMotor = angleMotor;
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
	
	public void enablePID() {
		
	}
	/**
	 * 
	 */
	public void holdAngle() {
		
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
		boolean isUpToSpeed = false;
		
		if (DriverStation.auxStick.getRawButton(HWR.START_SHOOTER)) {
			group.set(-SHOOT_SPEED);
		}
		if (DriverStation.auxStick.getRawButton(HWP.BUTTON_7))
			group.stop();
		while(group.get() != SHOOT_SPEED) {
			isUpToSpeed = false;
		}
		isUpToSpeed = true;
		return isUpToSpeed;
	}
	
	/**
	 * Extends pistons to shoot ball
	 */
	public void shootBall() {
		if (DriverStation.auxStick.getRawButton(HWR.ACTUATE_PISTON_SHOOTER))
			shootPiston.extend();
	}
	

	/**
	 * Angles shooter using Joy Sticks
	 */
	public void joystickAngleShooter() {
		if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) > 0.04)
			angleMotor.set(UP_ANGLE_SPEED);
		else if (DriverStation.auxStick.getRawAxis(DriverStation.YAxis) < -0.04)
			angleMotor.set(-DOWN_ANGLE_SPEED);
	}

}
