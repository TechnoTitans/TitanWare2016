package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.sensors.Encoder;

/**
 * Class to represent the Talons attached to motors.
 * 
 * @author David Luo
 *
 */
public class Talon extends edu.wpi.first.wpilibj.Talon implements Motor {

	private Encoder encoder;

	/**
	 * Private class to move motor in separate thread.
	 * 
	 * @author David Luo
	 */
	private class MotorMover implements Runnable {

		private double distance;
		private double speed;
		private Talon talon;
		private Encoder encoder;

		public MotorMover(Talon talon, double distance, Encoder encoder) {
			this.talon = talon;
			this.distance = distance;
			this.encoder = encoder;
			this.speed = Motor.MID_SPEED;
		}

		public MotorMover(Talon talon, double distance, Encoder encoder, double speed) {
			this.talon = talon;
			this.distance = distance;
			this.encoder = encoder;
			this.speed = speed;
		}

		@Override
		public void run() {
			encoder.reset();
			talon.set(speed);
			while (Math.abs(encoder.getDistance()) < distance) {
				// do nothing
			}
			talon.stop();
			// encoder.reset();
		}

	}

	/**
	 * Constructor
	 * 
	 * @param channel
	 *            The port where the Talon is plugged in.
	 * @param reversed
	 *            If the Talon should invert the signal.
	 */
	public Talon(int channel, boolean reversed) {
		super(channel);
		setInverted(reversed);
	}

	/**
	 * Constructor
	 * 
	 * @param channel
	 *            The port where the Talon is plugged in.
	 * @param reversed
	 *            If the Talon should invert the signal.
	 * @param encoder
	 *            Encoder to attach to this Talon.
	 */
	public Talon(int channel, boolean reversed, Encoder encoder) {
		super(channel);
		setInverted(reversed);
		this.encoder = encoder;
	}

	/**
	 * Move distance in inches
	 * 
	 * @param distance
	 *            Distance in inches
	 */
	public void moveDistance(double distance) throws EncoderNotFoundException {
		if (hasEncoder()) {
			new Thread(new MotorMover(this, distance, encoder)).start();
		} else {
			throw new EncoderNotFoundException();
		}
	}

	/**
	 * Move distance in inches
	 * 
	 * @param distance
	 *            Distance in inches.
	 * @param speed
	 *            Speed from 0 to 1.
	 */
	public void moveDistance(double distance, double speed) throws EncoderNotFoundException {
		if (hasEncoder()) {
			new Thread(new MotorMover(this, distance, encoder, speed)).start();
		} else {
			throw new EncoderNotFoundException();
		}
	}

	/** 
	 * Set the speed of the Talon.
	 * @param Speed from 0 to 1.
	 */
	public void set(double speed) {
		super.set(speed);
	}

	/**
	 * Stops motor.
	 */
	public void stop() {
		super.stopMotor();
	}

	/**
	 * @return If there is an encoder attached to this Talon.
	 */
	@Override
	public boolean hasEncoder() {
		return !encoder.equals(null);
	}

	/**
	 * @return The encoder attached to this Talon if exists, null otherwise.
	 */
	@Override
	public Encoder getEncoder() {
		return encoder;
	}

	/**
	 * @return If the Talon is reversed.
	 */
	public boolean isReversed() {
		return super.getInverted();
	}

}
