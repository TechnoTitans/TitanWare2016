package org.usfirst.frc.team1683.autonomous;

/**
 * Reaches base of defense
 * 
 * @author Sneha Kadiyala
 *
 */
public class ReachDefense extends Autonomous{
	
	
	public void run() {
		switch(presentState) {
		case INIT_CASE:
		{
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			tankDrive.moveDistance(distance);
			nextState = State.END_CASE;
			break;
		}
		case END_CASE:
		{
			break;
		}
		}
	presentState = nextState;
	}

}
