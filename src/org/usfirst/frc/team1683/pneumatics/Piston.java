package org.usfirst.frc.team1683.pneumatics;

public class Piston extends edu.wpi.first.wpilibj.Solenoid {

	public Piston(int moduleChannel, int channel) {
		super(moduleChannel, channel);
	}

	public void extend() {
		super.set(true);
	}

	public void retract() {
		super.set(false);
	}

	public boolean isExtended() {
		return super.get();
	}

}
