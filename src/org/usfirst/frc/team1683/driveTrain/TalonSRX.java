package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.sensors.DIOEncoder;
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
	private Thread thread;
	private boolean calibrated = false;

	/**
	 * Private class to move motor in separate thread.
	 * 
	 * @author David Luo
	 *
	 */
	private class MotorMover implements Runnable {

		private double distance;
		private double speed;
		private TalonSRX talonSrx;
		private Encoder encoder;

		public MotorMover(TalonSRX talonSrx, double distance, double speed) {
			this.talonSrx = talonSrx;
			this.distance = distance;
//			this.encoder = encoder;
			if (distance < 0)
				this.speed = -speed;
			else
				this.speed = speed;
		}

		@Override
		public void run() {
			encoder.reset();
			// synchronized (this) {
			while (Math.abs(encoder.getDistance()) < Math.abs(distance)) {
				talonSrx.set(speed);
				// SmartDashboard.sendData("encoder val",
				// encoder.getDistance());
				// do nothing
			}
			// }
			talonSrx.stop();
			// notify();
			encoder.reset();
		}
	}

	
	/**
	 * Constructor
	 * 
	 * @param channel
	 *            The port where the TalonSRX is plugged in.
	 * @param reversed
	 *            If the TalonSRX should invert the signal.
	 */
	public TalonSRX(int channel, boolean reversed) {
		super(channel);
		super.setInverted(reversed);
	}

	/**
	 * Constructor
	 * 
	 * @param channel
	 *            The port where the TalonSRX is plugged in.
	 * @param reversed
	 *            If the TalonSRX should invert the signal.
	 * @param encoder
	 *            Encoder to attach to this TalonSRX.
	 */
	public TalonSRX(int channel, boolean reversed, Encoder encoder) {
		super(channel);
		super.setInverted(reversed);
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
		moveDistance(distance, Motor.MID_SPEED);
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
			if (thread == null || thread.getState().equals(Thread.State.TERMINATED)) {
				thread = new Thread(new MotorMover(this, distance, speed));
			}
			if (thread.getState().equals(Thread.State.NEW)) {
				thread.start();

				// synchronized (thread) {
				// try {
				// thread.wait();
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// }
				SmartDashboard.sendData("EncoderNotFound", false);
			}
		} else {
			SmartDashboard.sendData("EncoderNotFound", true);
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
		super.changeControlMode(TalonControlMode.PercentVbus);
//		super.enableControl();
		super.set(speed);
	}

	/**
	 * Gets speed of the TalonSRX
	 */
	public double get() {
		return super.get();
	}
	
	public void calibrate() {
		super.setPosition(0);
		calibrated = true;
	}
	
	public void PIDInit() throws EncoderNotFoundException {
		if (!super.isSensorPresent(FeedbackDevice.PulseWidth).equals(FeedbackDeviceStatus.FeedbackStatusPresent)){
//			throw new EncoderNotFoundException();
			SmartDashboard.sendData("Encoder on Talon " + getChannel() + " not found", true);
		} else
			SmartDashboard.sendData("Encoder on Talon " + getChannel() + " not found", false);
		super.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		super.setInverted(false);
//		super.configEncoderCodesPerRev(4096);
		calibrate();
	}
	
	private void PIDUpdate() {
//		super.setCloseLoopRampRate(SmartDashboard.getDouble("RampRate"));
//		super.setVoltageRampRate(SmartDashboard.getDouble("RampRate"));
//		super.enableBrakeMode(SmartDashboard.getBoolean("Brake Mode", false));
		super.setAllowableClosedLoopErr(SmartDashboard.getInt("Allowable CL Error"));
		super.setPID(SmartDashboard.getDouble("P"), SmartDashboard.getDouble("I"), SmartDashboard.getDouble("D"));
		super.enableControl();
	}
	
	public void PIDAngle(double angle) {
		PIDPosition(angle/360.0);
	}
	
	public void PIDPosition(double position) {
		PIDUpdate();
		super.changeControlMode(TalonControlMode.Position);
		super.set(position);
		SmartDashboard.sendData("Talon " + this.getChannel() + " Position", super.getPosition());
	}
	
	public void PIDSpeed(double rpm) {
		PIDUpdate();
		super.changeControlMode(TalonControlMode.Speed);
		super.setPosition(rpm);
		SmartDashboard.sendData("Talon " + this.getChannel() + " Speed", super.getSpeed());
	}

	/**
	 * Stops motor.
	 */
	@Override
	public void stop() {
//		super.enableBrakeMode(true);
		super.disableControl();
	}

	/**
	 * @return If there is an encoder attached to this TalonSRX.
	 */
	@Override
	public boolean hasEncoder() {
		return !(encoder == null);
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

	public double getError() {
		return super.getError();
	}
	/**
	 * @return If the Talon is reversed.
	 */
	@Override
	public boolean isReversed() {
		return super.getInverted();
	}

}
