package org.usfirst.frc.team1683.driveTrain;

import java.util.ArrayList;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.sensors.Encoder;

/**
 * Represents a group of motors that move together, tied to the same gearsbox
 * and using the same encoder.
 * 
 * @author David Luo
 *
 */
public class MotorGroup extends ArrayList<Motor> {

	// This variable was required for some reason.
	private static final long serialVersionUID = 1L;
	private Encoder encoder;
	private Thread thread;

	/**
	 * Private class to move motor in separate thread.
	 */
	private class MotorMover implements Runnable {

		private double distance;
		private double speed;
		private MotorGroup motors;

		public MotorMover(MotorGroup group, double distance, double speed) {
			this.motors = group;
			this.distance = distance;
			if (distance < 0) {
				this.speed = -speed;
			} else {
				this.speed = speed;
			}
		}

		@Override
		public void run() {
			encoder.reset();
			// synchronized (this) {
			while (Math.abs(encoder.getDistance()) < Math.abs(distance)) {
				motors.set(speed);

				SmartDashboard.sendData("EncoderDistance", encoder.getDistance());
				SmartDashboard.sendData("TargetDistance", distance);
				// do nothing
			}
			// }
			motors.stop();
			this.notifyAll();
		}

	}

	/**
	 * Constructor
	 * 
	 * @param encoder
	 *            The encoder attached to this MotorGroup
	 * @param motors
	 *            The motors.
	 */
	public MotorGroup(Encoder encoder, Motor... motors) {
		this.encoder = encoder;
		for (Motor motor : motors) {
			this.add(motor);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param motors
	 *            The motors.
	 */
	public MotorGroup(Motor... motors) {
		for (Motor motor : motors) {
			this.add(motor);
		}
	}

	/**
	 * Move distance in inches.
	 * 
	 * @param distance
	 *            Distance in inches.
	 * @throws EncoderNotFoundException
	 *             If encoder is not found.
	 */
	public void moveDistance(double distance) throws EncoderNotFoundException {
		moveDistance(distance, Motor.MID_SPEED);
	}

	/**
	 * Move distance in inches.
	 * 
	 * @param distance
	 *            Distance in inches.
	 * @throws EncoderNotFoundException
	 *             If encoder not found.
	 */
	// TODO: make this linear instead of rotations
	public void moveDistance(double distance, double speed) throws EncoderNotFoundException {
		if (hasEncoder()) {
			// for (Motor motor : this) {
			// motor.moveDistance(distance, speed);
			// }
			thread = new Thread(new MotorMover(this, distance, speed));
			thread.start();
		} else {
			throw new EncoderNotFoundException();
		}
	}

	/**
	 * Set collective speed of motors.
	 * 
	 * @param speed
	 * 			  Speed from 0 to 1.
	 */
	public void set(double speed) {
		for (Motor motor : this) {
			motor.set(speed);
		}
	}
	
	/**
	 * Gets collective speed of motors
	 */
	public double get() {
		double speed = 0;
		for(Motor motor : this) {
			speed += motor.get();
		}
		return speed/this.size();
	}
	

	/**
	 * Stops group.
	 */
	public void stop() {
		for (Motor motor : this) {
			motor.stop();
		}
	}

	/**
	 * @return If there is an encoder associated with the group.
	 */
	public boolean hasEncoder() {
		return !(encoder == null);
	}

	/**
	 * @return The encoder associated with the group.
	 */
	public Encoder getEncoder() {
		return encoder;
	}

	public void setBrakeMode(boolean enabled) {
		for (Motor motor : this) {
			if (motor instanceof TalonSRX) {
				((TalonSRX) motor).enableBrakeMode(enabled);
			}
		}
	}

	// /**
	// * @return The motors in the group.
	// */
	// public Motor getMotors() {
	// return motors;
	// }
}
