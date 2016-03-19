package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.sensors.LinearActuator;

import edu.wpi.first.wpilibj.Timer;

/**
 * Reaches base of defense
 * 
 * @author Sneha Kadiyala
 *
 */
public class ReachDefense extends Autonomous {

	LinearActuator actuator;
//	Timer timeout;
	
	public ReachDefense(TankDrive driveTrain) {
		super(driveTrain);
//		timeout = new Timer();
	}

	@Override
	public void run() {
		switch (presentState) {
		case INIT_CASE:
//			timeout.start();
			nextState = State.DRIVE_FORWARD;
			break;

//		case STOW_PISTONS:
//			if(actuator.getError() < Autonomous.ACTUATOR_ERROR_TOLERANCE)
//				nextState = State.DRIVE_FORWARD;
		case DRIVE_FORWARD:
			tankDrive.moveDistance(REACH_DISTANCE, 0.8);
//			if (timeout.get() < 5) {
//				tankDrive.set(0.75);
//			}
			nextState = State.END_CASE;
			break;

		case END_CASE:
//			tankDrive.stop();
			nextState = State.END_CASE;
			break;

		default:
			break;
		}
		presentState = nextState;
	}
}
