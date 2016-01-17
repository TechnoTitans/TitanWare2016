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
	private final double SPEED = 0.5;

	public Talon(int channel, boolean reversed) {
		super(channel);
		setInverted(reversed);
	}

	public Talon(int channel, boolean reversed, Encoder encoder) {
		super(channel);
		setInverted(reversed);
		this.encoder = encoder;
	}

	/**
	 * Private class to move motor in separate thread.
	 * 
	 * @author David Luo
	 */
	private class MotorMover implements Runnable {

		double distance;
		Talon talon;

		public MotorMover(Talon talon, double distance) {
			this.distance = distance;
			this.talon = talon;
		}

		@Override
		public void run() {
			encoder.reset();
			talon.set(SPEED);
			while (Math.abs(encoder.getDistance()) < distance) {
				// do nothing
			}
			talon.stop();
			// encoder.reset();
		}

	}

	/**
	 * Move distance in inches
	 * 
	 * @param distance
	 *            in inches
	 */
	public void moveDistance(double distance) {
		if (hasEncoder()) {
			new Thread(new MotorMover(this, distance)).start();
		}
		else {
			System.out.println("ENCODER NOT PRESENT");
		}

	}

	/**
	 * Stops motor
	 */
	public void stop() {
		super.stopMotor();
	}

	/**
	 * @return if there is an encoder attached to this Talon.
	 */
	@Override
	public boolean hasEncoder() {
		return !encoder.equals(null);
	}

	/**
	 * @return the encoder attached to this Talon if exists, null otherwise.
	 */
	@Override
	public Encoder getEncoder() {
		return encoder;
	}

	public boolean isReversed() {
		return super.getInverted();
	}
	
	public void set(double speed) {
		super.set(speed);
	}
}
