package org.usfirst.frc.team1683.sensors;

public class Encoder extends edu.wpi.first.wpilibj.Encoder {

	// Pulses per inch(?) from encoder
	private double wheelDistancePerPulse;

	public Encoder(int aChannel, int bChannel, boolean reverseDirection, double wheelDistancePerPulse) {
		super(aChannel, bChannel, reverseDirection);
		this.wheelDistancePerPulse = wheelDistancePerPulse;
		super.setDistancePerPulse(wheelDistancePerPulse);
	}

	/**
	 * @return distance in inches
	 */
	public double getDistance() {
		return super.getDistance();
	}

	/**
	 * @return speed in inches/sec
	 */
	public double getSpeed() {
		return super.getRate();
	}
}
