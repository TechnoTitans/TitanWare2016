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
public class MotorGroup {

	private ArrayList<Motor> motors;
	private Encoder encoder;
	private Thread thread;

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
		private Encoder encoder;

		public MotorMover(MotorGroup group, double distance, Encoder encoder, double speed) {
			this.motors = group;
			this.distance = distance;
			this.encoder = encoder;
			if (distance < 0) {
				this.speed = -speed;
			} else {
				this.speed = speed;
			}
		}

		@Override
		public void run() {
			encoder.reset();
			motors.set(speed);
			synchronized (this) {
				while (Math.abs(encoder.getDistance()) < Math.abs(distance)) {
					// do nothing
				}
			}
			motors.stop();
			notify();
		}
	}

	/**
	 * Constructor
	 * 
	 * @param motorsArr
	 *            The motors to be grouped. Each motor is individually reversed.
	 */
	public MotorGroup(ArrayList<Motor> motorsArr) {
		this.motors = motorsArr;
	}

	/**
	 * Constructor
	 * 
	 * @param motorArr
	 *            The motors to be grouped. Each motor is individually reversed.
	 * @param encoder
	 *            The encoder attached to the group of motors.
	 */
	public MotorGroup(ArrayList<Motor> motorArr, Encoder encoder) {
		this.motors = motorArr;
		this.encoder = encoder;
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
		if (hasEncoder()) {
			if (thread == null || thread.getState().equals(Thread.State.TERMINATED)) {
				thread = new Thread(new MotorMover(this, distance, encoder, speed));
			}

			if (thread.getState().equals(Thread.State.NEW)) {
				thread.start();

				synchronized (thread) {
					try {
						thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			throw new EncoderNotFoundException();
		}
	}

	/**
	 * Set collective speed of motors.
	 * 
	 * @param speed
	 *            Speed from 0 to 1.
	 */
	public void set(double speed) {
		for (Motor m : getMotors()) {
			m.set(speed);
		}
	}

	/**
	 * Stops group.
	 */
	public void stop() {
		for (Motor m : getMotors()) {
			m.stop();
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

	/**
	 * @return The motors in the group.
	 */
	public ArrayList<Motor> getMotors() {
		return motors;
	}
}
