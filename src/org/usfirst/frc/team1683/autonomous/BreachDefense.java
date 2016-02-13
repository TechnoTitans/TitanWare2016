package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.EncoderNotFoundException;
import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.sensors.BuiltInAccel;

public class BreachDefense extends Autonomous {

	BuiltInAccel accel;
	
	public BreachDefense(TankDrive tankDrive, BuiltInAccel accel) {
		super(tankDrive);
		this.accel = accel;
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
			{
				nextState = State.CROSS_DEFENSE;
				break;
			}
		}
		//uses accel to tell if on defense or not
		case CROSS_DEFENSE: {
			if(!accel.isFlat()){
			tankDrive.set(Motor.MID_SPEED);
			} 
			else tankDrive.stop();

			nextState = State.END_CASE;
			break;
		}
		case END_CASE: {
			break;
		}		
		case FIND_TARGET:
			break;
		case FIRE:
			break;
		case REACH_DISTANCE:
			break;
		default:
			break;
		
		}
		presentState = nextState;
	}
}