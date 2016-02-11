package org.usfirst.frc.team1683.autonomous;
import org.usfirst.frc.team1683.driveTrain.EncoderNotFoundException;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.vision.FindGoal;
/**
 * Shoots at target
 * 
 * @author Yi Liu
 *
 */
public class ShootAtTarget extends Autonomous {
	public ShootAtTarget(TankDrive driveTrain) {
		super(driveTrain);
	}
	
	public void run() {
		FindGoal findgoal=new FindGoal();
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
			nextState = State.BREACH_DEFENSE;
			break;
		}
		case BREACH_DEFENSE:{
			//TODO:add autonomous code for each of the seperate obstacles
			nextState=State.REACH_DISTANCE;//after breaching defense, arrive at a good point for shooting.
			break;
		}
		case REACH_DISTANCE:{
			//TODO:Find proper spots for each position we could start in (left side, center, right)
			nextState=State.FIND_TARGET;
			break;
		}
		case FIND_TARGET:{
			while(findgoal.isCentered()==2){
				tankDrive.turn(2);
			}
			break;
		}
		case FIRE:{
			//TODO:add shooting code
			nextState=State.END_CASE;
			break;
		}
		case END_CASE: {
			break;
		}
		}
		presentState = nextState;
	}
}
