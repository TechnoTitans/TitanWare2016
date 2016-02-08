package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;

public class DoNothing extends Autonomous {

	public DoNothing(TankDrive driveTrain){
		super(driveTrain);
	}
	
	public void run() {
		switch(presentState) {
		case INIT_CASE:
		{
			nextState = State.END_CASE;
			break;
		}
		
		case END_CASE:
		{
			break;
		}
		default:
			presentState = State.END_CASE;
		}
		presentState = nextState;
	}
}
