package org.usfirst.frc.team1683.autonomous;
import org.usfirst.frc.team1683.driveTrain.EncoderNotFoundException;
import org.usfirst.frc.team1683.sensors.BuiltInAccel;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.vision.FindGoal;
import org.usfirst.frc.team1683.driveTrain.Motor;

/**
 * Shoots at target
 * 
 * @author Yi Liu
 *
 */
public class ShootAtTarget extends Autonomous {
	FindGoal findgoal=new FindGoal();
	BuiltInAccel accel;
	public ShootAtTarget(TankDrive driveTrain,BuiltInAccel accel) {
		super(driveTrain);
		this.accel=accel;
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
			nextState = State.CROSS_DEFENSE;
			break;
		}
		case CROSS_DEFENSE:{
			if(!accel.isFlat()){
				tankDrive.set(Motor.MID_SPEED);
			}
			else{
				tankDrive.stop();
			}
			nextState=State.REACH_DISTANCE;//after breaching defense, arrive at a good point for shooting
			break;
		}
		case REACH_DISTANCE:{
			//TODO:Find proper spots for each position we could start in (left side, center, right)
			nextState=State.FIND_TARGET;
			break;
		}
		case FIND_TARGET:{
			//while(findgoal.isCentered()!=0){
			//	tankDrive.turn(2);
			//}
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
