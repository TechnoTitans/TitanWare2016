package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;

/**
 * Class to be inherited by all auto classes.
 * 
 * @author David Luo
 *
 */
public abstract class Autonomous {
	// TODO: make automous
	public static TankDrive tankDrive;
	
	protected static enum State {
		INIT_CASE, END_CASE, DRIVE_FORWARD, 
	}
	
	public static State presentState = State.INIT_CASE;
	public static State nextState;
	public static double distance;
}
