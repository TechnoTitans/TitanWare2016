package org.usfirst.frc.team1683.vision;

import edu.wpi.first.wpilibj.TalonSRX;

/**
 * Represents the Light Ring
 * 
 * @author David Luo
 *
 */
public class LightRing extends TalonSRX {

	public LightRing(int channel) {
		super(channel);
	}

	@Override
	public void set(double brightness) {
		super.set(Math.abs(brightness));
	}

	public void setMaxBright() {
		super.set(1);
	}

	public void setOff() {
		super.set(0);
	}

}
