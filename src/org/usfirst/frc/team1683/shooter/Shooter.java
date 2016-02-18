package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.sensors.AccelSPI;
import org.usfirst.frc.team1683.sensors.AnalogAccel;
import org.usfirst.frc.team1683.vision.FindGoal;
import org.usfirst.frc.team1683.sensors.LimitSwitch;

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
	FindGoal vision;
	MotorGroup group;
	AccelSPI accel;
	TalonSRX angleMotor;
	Piston shootPiston;
	AnalogAccel analogAccel;
	LimitSwitch bottomLimit;
	LimitSwitch upperLimit;
	Motor shoot;
	public Shooter(MotorGroup group, TalonSRX angleMotor, AccelSPI accel, Piston shootPiston,FindGoal vision, int bottomDIOPort, int upperDIOPort) {
		this.group = group;
		this.accel = accel;
		this.angleMotor = angleMotor;
		this.shootPiston = shootPiston;
		this.vision=vision;
		this.shoot=shoot;
		bottomLimit = new LimitSwitch(bottomDIOPort);
		upperLimit = new LimitSwitch(upperDIOPort);
	}

	public Shooter(TalonSRX angleMotor, MotorGroup group, Piston shootPiston) {
		this.angleMotor = angleMotor;
		this.group = group;
		this.shootPiston = shootPiston;
		this.vision=vision;
		this.shoot=shoot;
	}
	/**
	
	
	/**
	 * Angles shooter using Accelerometer
	 * @param angle 
	 * 			  Angle from getAngle
	 */
	public void angleShooterAccel(double angle) {
		double speed = 0.3; // find steady speed
		while (accel.getAngleXZ() >= angle + TOLERANCE && accel.getAngleXZ() <= angle - TOLERANCE) {
			if (accel.getAngleXZ() >= angle + TOLERANCE)
				angleMotor.set(-speed);// turn negative speed
			else if (accel.getAngleXZ() <= angle - TOLERANCE)
				angleMotor.set(speed);
			; // turn positive speed
		}
		
		if(bottomLimit.isPressed() || upperLimit.isPressed()) angleMotor.PIDPosition(angleMotor.getPosition());
	}
	

	/**
	 * PID controls for the angleMotor
	 */
	public void enablePIDControl() {
		double p = SmartDashboard.getDouble("P");
		double i = SmartDashboard.getDouble("I");
		double d = SmartDashboard.getDouble("D");
		double tolerance = SmartDashboard.getDouble("PIDTolerance");
		PIDController pidControl = new PIDController(p,i,d, analogAccel, angleMotor);
		pidControl.setOutputRange(0, 1);
		pidControl.setPercentTolerance(tolerance);
	}
	

	/**
	 * Spins wheels up to speed 
	 * @param distance
	 * 				Distance from vision
	 * @param angle
	 * 			    Angle based off distance
	 */
	public void spinRollers(double rpm) {
		group.set(rpm); //change to .PIDSpeed(rpm/6000);
	}
	
	/**
	 * Extends pistons to shoot ball
	 */
	public void shootBall() {
		if (DriverStation.auxStick.getRawButton(HWR.ACTUATE_PISTON_SHOOTER))
			shootPiston.extend();
		else shootPiston.retract();
		
		if(DriverStation.auxStick.getRawButton(HWR.SHOOTER_TOGGLE)) {
			group.set(SHOOT_SPEED);
		}
		
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
