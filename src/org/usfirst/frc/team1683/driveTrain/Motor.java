package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.sensors.Encoder;

public interface Motor {
	public void moveDistance(double distance);
//	public void moveDegrees();
	public void set(double speed);
	public void stop();
	public boolean hasEncoder();
	public Encoder getEncoder();
	public int getChannel();
	public boolean isReversed();
}
