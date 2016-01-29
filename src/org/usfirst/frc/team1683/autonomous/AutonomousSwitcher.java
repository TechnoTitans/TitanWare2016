package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.DriverStation;
/**
 * 
 * @author Sneha Kadiyala
 *
 *Switches between different autonomous programs
 *
 */
public class AutonomousSwitcher {
	Autonomous autonomous;
	
	public AutonomousSwitcher(TankDrive tankDrive) {
		int autoMode = DriverStation.getInt("autonomousMode");
		
		if(autoMode == 1) autonomous = new ReachDefense(tankDrive);
		else if (autoMode == 2) autonomous = new DoNothing(tankDrive);
		
		//else autonomous = new DefaultAuto();
	}
}
