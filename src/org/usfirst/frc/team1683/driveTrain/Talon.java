package org.usfirst.frc.team1683.driveTrain;

public class Talon extends edu.wpi.first.wpilibj.Talon{

	public Talon(int channel, boolean reversed) {
		super(channel);
		setInverted(reversed);
	}
	
	public void setSpeed(double speed) {
		set(speed);
	}

}
