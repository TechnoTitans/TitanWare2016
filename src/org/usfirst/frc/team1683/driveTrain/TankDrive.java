package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.driverStation.DriverStation;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Represents the drive train in tank drive configuration.
 * @author David Luo
 *
 */
public class TankDrive implements DriveTrain {

	private Motor left;
	private Motor right;
	private Gyro gyro;

	public TankDrive(Motor left, Motor right) {
		if (left instanceof Talon && right instanceof Talon) {
			this.left = (Talon) left;
			this.right = (Talon) right;
		} else if (left instanceof TalonSRX && right instanceof TalonSRX) {
			this.left = (TalonSRX) left;
			this.right = (TalonSRX) right;
		}

	}

	/**
	 * @param distance Distance to move in inches.
	 * @throws EncoderNotFoundException Encoder not found.
	 */
	@Override
	public void moveDistance(double distance) throws EncoderNotFoundException {
		left.moveDistance(distance);
		right.moveDistance(distance);
	}
	/**
	 * @param distance Distance to move in inches.
	 * @param speed Speed from 0 to 1.
	 * @throws EncoderNotFoundException Encoder not found.
	 */
	@Override
	public void moveDistance(double distance, double speed) throws EncoderNotFoundException {
		left.moveDistance(distance, speed);
		right.moveDistance(distance, speed);
	}

	/**
	 * @param degrees How much to turn the robot.
	 */
	@Override
	public void turn(double degrees) {
		// TODO Needs encoder/gyro

	}

	/**
	 * Stop the drive train.
	 */
	@Override
	public void stop() {
		left.stop();
		right.stop();
	}

	/**
	 * Start driving.
	 */
	@Override
	public void driveMode() {
		double lSpeed = DriverStation.leftStick.getRawAxis(DriverStation.YAxis);
		double rSpeed = DriverStation.rightStick.getRawAxis(DriverStation.YAxis);

		left.set(lSpeed);
		right.set(rSpeed);
	}


}
