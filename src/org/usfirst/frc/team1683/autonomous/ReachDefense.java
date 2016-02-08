package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.DriveTrain;
import org.usfirst.frc.team1683.driveTrain.EncoderNotFoundException;
import org.usfirst.frc.team1683.driveTrain.TankDrive;

/**
 * Reaches base of defense
 * 
 * @author Sneha Kadiyala
 *
 */
public class ReachDefense extends Autonomous {
	
	public ReachDefense(TankDrive driveTrain) {
		super(driveTrain);
	}
	
	public void run() {
		switch (presentState) {
		case INIT_CASE: {
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD: {
			try {
				tankDrive.moveDistance(reachDistance);
			} catch (EncoderNotFoundException e) {
				System.err.println("Need encoders on tankDrive");
			}
			nextState = State.END_CASE;
			break;
		}
		case END_CASE: {
			break;
		}
		}
		presentState = nextState;
	}
}
