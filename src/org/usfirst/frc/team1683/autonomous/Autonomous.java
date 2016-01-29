package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.sensors.Encoder;

/**
 * Class to be inherited by all auto classes.
 * 
 * @author David Luo
 *
 */
public abstract class Autonomous {
	// TODO: make autonomous
	protected static TankDrive tankDrive;
//	protected static Encoder leftEncoder;
//	protected static Encoder rightEncoder;
	
	public Autonomous(TankDrive tankDrive) {
		this.tankDrive = tankDrive;
		//leftEncoder = tankDrive.leftEncoder;
		//rightEncoder = tankDrive.rightEncoder;
	}
	
	protected static enum State {
		INIT_CASE, END_CASE, DRIVE_FORWARD, 
	}
	
	public static State presentState = State.INIT_CASE;
	public static State nextState;
	public static double reachDistance;  //74  inches
	
	

	public void updatePreferences() {
		reachDistance = DriverStation.getDouble("reachDistance");
	}
}
