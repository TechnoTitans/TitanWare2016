package org.usfirst.frc.team1683.driveTrain;

import java.util.ArrayList;

/**
 * @author David Luo
 *
 */
public interface DriveTrain {
	
	ArrayList<Motor> motors = new ArrayList<>();

	public void moveDistance(double distance);
	public void turn(double degrees);
	public void driveMode(double distance);
	public void stop();
}
