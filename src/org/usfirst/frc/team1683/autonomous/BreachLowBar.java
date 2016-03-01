package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;

public class BreachLowBar extends Autonomous {

	public BreachLowBar(TankDrive tankDrive) {
		super(tankDrive);
	}

	@Override
	public void run() {
		switch (presentState) {
		case INIT_CASE:
			nextState = State.DRIVE_FORWARD;
			break;

		case DRIVE_FORWARD:
			// TODO: Need to add timeout to moveDistance?
			tankDrive.moveDistance(REACH_DISTANCE + RAMP_LENGTH + LOW_BAR_DISTANCE);
			nextState = State.STOP;
			break;

		case STOP:
			tankDrive.stop();
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
