package org.usfirst.frc.team1683.driveTrain;

import java.util.ArrayList;

import org.omg.PortableServer.THREAD_POLICY_ID;
import org.usfirst.frc.team1683.sensors.Encoder;

/**
 * Represents a group of motors that move together, tied to the same gearsbox
 * and using the same encoder.
 * 
 * @author David Luo
 *
 */
public class MotorGroup extends ArrayList<Motor> {

	private ArrayList<Motor> motors;
	private Encoder encoder;
	private Thread thread;
	private MotorMover mover;

	/**
	 * Private class to move motor in separate thread.
	 * 
	 * @author David Luo
	 *
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
//			synchronized (this) {
				while (Math.abs(encoder.getDistance()) < Math.abs(distance)) {
					motors.set(speed);
					// do nothing
				}
//			}
			motors.stop();
			this.notifyAll();
		}

	}

	public MotorGroup(Encoder encoder, Motor... motors) {
		this.encoder = encoder;
		for (Motor motor : motors) {
			this.add(motor);
		}
	}

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
	public void moveDistance(double distance, double speed) throws EncoderNotFoundException {
		// for (Motor motor : this) {
		// motor.moveDistance(distance, speed);
		// }
		if (hasEncoder()) {

		}
	}

	/**
	 * Set collective speed of motors.
	 * 
	 * @param speed
	 *            Speed from 0 to 1.
	 */
	public void set(double speed) {
		for (Motor motor : this) {
			motor.set(speed);
		}
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
		return !encoder.equals(null);
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
