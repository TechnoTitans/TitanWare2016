package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.sensors.LinearActuator;

/**
 * Reaches base of defense
 * 
 * @author Sneha Kadiyala
 *
 */
public class ReachDefense extends Autonomous {

	LinearActuator actuator;
	
	public ReachDefense(TankDrive driveTrain) {
		super(driveTrain);
	}

	@Override
	public void run() {
		switch (presentState) {
		case INIT_CASE:
			nextState = State.STOW_PISTONS;
			break;

		case STOW_PISTONS:
			if(actuator.getError() < Autonomous.ACTUATOR_ERROR_TOLERANCE)
				nextState = State.DRIVE_FORWARD;
		case DRIVE_FORWARD:
			tankDrive.moveDistance(REACH_DISTANCE);
			nextState = State.END_CASE;
			break;

		case END_CASE:
			nextState = State.END_CASE;
			break;

		default:
			break;
		}
		presentState = nextState;
	}
}
