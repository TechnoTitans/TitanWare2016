package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.sensors.Encoder;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Class to represent TalonSRXs attatched to motors.
 * 
 * @author David Luo
 *
 */
public class TalonSRX extends CANTalon implements Motor {

	private Encoder encoder;

	/**
	 * Private class to move motor in separate thread.
	 * 
	 * @author David Luo
	 *
	 */
	private class MotorMover implements Runnable {

		private double distance;
		private double speed;

		private Encoder encoder;
		private TalonSRX talonSrx;

		public MotorMover(TalonSRX talonSrx, double distance, Encoder encoder) {
			this.distance = distance;
			this.speed = Motor.MID_SPEED;
			this.talonSrx = talonSrx;
			this.encoder = encoder;
		}

		public MotorMover(TalonSRX talonSrx, double distance, Encoder encoder, double speed) {
			this.distance = distance;
			this.speed = speed;
			this.talonSrx = talonSrx;
			this.encoder = encoder;
		}

		@Override
		public void run() {
			encoder.reset();
			talonSrx.set(speed);
			while (Math.abs(encoder.getDistance()) < distance) {
				// do nothing
			}
			talonSrx.stop();
			// encoder.reset();
		}
	}

	/**
	 * Constructor
	 * 
	 * @param channel
	 *            The port where the TalonSRX is plugged in.
	 * @param reverseDirection
	 *            If the TalonSRX should invert the signal.
	 */
	public TalonSRX(int channel, boolean reverseDirection) {
		super(channel);
		super.setInverted(reverseDirection);
	}

	/**
	 * Constructor
	 * 
	 * @param channel
	 *            The port where the TalonSRX is plugged in.
	 * @param reverseDirection
	 *            If the TalonSRX should invert the signal.
	 * @param encoder
	 *            Encoder to attach to this TalonSRX.
	 */
	public TalonSRX(int channel, boolean reverseDirection, Encoder encoder) {
		super(channel);
		super.setInverted(reverseDirection);
		this.encoder = encoder;
	}

	/**
	 * Move distance in inches
	 * 
	 * @param distance
	 *            Distance in inches
	 */
	@Override
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
	@Override
	public void moveDistance(double distance, double speed) throws EncoderNotFoundException {
		if (hasEncoder()) {
			new Thread(new MotorMover(this, distance, encoder, speed)).start();
		} else {
			throw new EncoderNotFoundException();
		}
	}

	/**
	 * Set the speed of the TalonSRX.
	 * 
	 * @param speed
	 *            Speed from 0 to 1.
	 */
	public void set(double speed) {
		super.set(speed);
	}

	/**
	 * Stops motor.
	 */
	@Override
	public void stop() {
		super.disableControl();

	}

	/**
	 * @return If there is an encoder attached to this TalonSRX.
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

	// TODO: make sure this works.
	@Override
	public int getChannel() {
		return super.getDeviceID();
	}

	/**
	 * @return If the Talon is reversed.
	 */
	@Override
	public boolean isReversed() {
		return super.getInverted();
	}

}
