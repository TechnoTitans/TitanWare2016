package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.sensors.DIOEncoder;
import org.usfirst.frc.team1683.sensors.Encoder;

import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Represents the drive train in tank drive configuration.
 * 
 * @author David Luo
 *
 */
public class TankDrive implements DriveTrain {

	private MotorGroup left;
	private MotorGroup right;
	private Gyro gyro;

	public TankDrive(MotorGroup left, MotorGroup right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * @param distance
	 *            Distance to move in inches.
	 * @throws EncoderNotFoundException
	 *             Encoder not found.
	 */
	@Override
	public void moveDistance(double distance) throws EncoderNotFoundException {
		moveDistance(distance, Motor.MID_SPEED);
	}

	/**
	 * @param distance
	 *            Distance to move in inches.
	 * @param speed
	 *            Speed from 0 to 1.
	 * @throws EncoderNotFoundException
	 *             Encoder not found.
	 */
	@Override
	public void moveDistance(double distance, double speed) throws EncoderNotFoundException {
		left.moveDistance(distance, speed);
		right.moveDistance(distance, speed);
	}

	/**
	 * @param degrees
	 *            How much to turn the robot.
	 */
	@Override
	public void turn(double degrees) {
		// TODO Needs encoder/gyro
		gyro.getAngle();

	}

	/**
	 * Sets the speed.
	 */
	@Override
	public void set(double speed) {
		left.set(speed);
		right.set(speed);
	};
	
	/**
	 * Stop the drive train.
	 */
	@Override
	public void stop() {
		left.setBrakeMode(true);
		right.setBrakeMode(true);
		left.stop();
		right.stop();
	}
	
	public void coast() {
		left.setBrakeMode(false);
		right.setBrakeMode(false);
		left.stop();
		right.stop();
	}

	/**
	 * Start driving.
	 */
	@Override
	public void driveMode() {
		left.setBrakeMode(false);
		right.setBrakeMode(false);
		
		double lSpeed = DriverStation.leftStick.getRawAxis(DriverStation.YAxis);
		double rSpeed = DriverStation.rightStick.getRawAxis(DriverStation.YAxis);

		left.set(lSpeed);
		right.set(rSpeed);
	}

	public Encoder getLeftEncoder() {
		return left.getEncoder();
	}
	
	public Encoder getRightEncoder() {
		return right.getEncoder();
	}
	
	public void resetEncoders() {
		left.getEncoder().reset();
		right.getEncoder().reset();
	}

	@Override
	public MotorGroup getLeftGroup() {
		return left;
	}

	@Override
	public MotorGroup getRightGroup() {
		return right;
	}
}
