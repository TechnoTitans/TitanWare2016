package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.sensors.Encoder;

import edu.wpi.first.wpilibj.CANTalon;

public class TalonSRX extends CANTalon implements Motor {

	private Encoder encoder;
	private final double SPEED = 0.5;
	private int channel;

	public TalonSRX(int channel, boolean reverseDirection) {
		super(channel);
		super.setInverted(reverseDirection);
		this.channel = channel;
	}

	public TalonSRX(int channel, boolean reverseDirection, Encoder encoder) {
		super(channel);
		super.setInverted(reverseDirection);
		this.encoder = encoder;
	}

	public void set(double speed) {
		super.set(speed);
	}

	@Override
	public String getSmartDashboardType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop() {
		super.disableControl();

	}
	
	@Override
	public void moveDistance(double distance) {
		if (hasEncoder()) {
			new Thread(new MotorMover(this, distance)).start();
		}
		else {
			System.out.println("ENCODER NOT PRESENT");
		}
	}

	private class MotorMover implements Runnable {

		double distance;
		TalonSRX talonSrx;

		public MotorMover(TalonSRX talonSrx, double distance) {
			this.distance = distance;
			this.talonSrx = talonSrx;
		}

		@Override
		public void run() {
			encoder.reset();
			talonSrx.set(SPEED);
			while (Math.abs(encoder.getDistance()) < distance) {
				// do nothing
			}
			// encoder.reset();
		}
	}

	@Override
	public boolean hasEncoder() {
		return !encoder.equals(null);
	}

	@Override
	public Encoder getEncoder() {
		return encoder;
	}

	@Override
	public int getChannel() {
		return super.getDeviceID();
	}


	@Override
	public boolean isReversed() {
		return super.getInverted();
	}

}
