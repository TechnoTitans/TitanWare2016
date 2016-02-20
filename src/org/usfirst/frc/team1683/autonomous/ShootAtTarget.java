package org.usfirst.frc.team1683.autonomous;
import org.usfirst.frc.team1683.driveTrain.EncoderNotFoundException;
import org.usfirst.frc.team1683.shooter.Shooter;
import org.usfirst.frc.team1683.sensors.BuiltInAccel;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.vision.FindGoal;
import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.vision.*;

/**
 * Shoots at target
 * 
 * @author Yi Liu
 *
 */
public class ShootAtTarget extends Autonomous {
	BuiltInAccel accel;
	FindGoal vision;
	Shooter shooter;
	ShootingPhysics physics;
	public ShootAtTarget(TankDrive driveTrain,BuiltInAccel accel,FindGoal vision,Shooter shooter,ShootingPhysics physics) {
		super(driveTrain);
		this.accel=accel;
		this.vision=vision;
		this.shooter=shooter;
		this.physics=physics;
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
				nextState=State.REACH_DISTANCE;
			}//after breaching defense, arrive at a good point for shooting
			break;
		}
		case REACH_DISTANCE:{
			try {
				tankDrive.moveDistance(properDistance);
			} catch (EncoderNotFoundException e) {
				System.err.println("Need encoders on tankDrive");
			}
			nextState=State.FIND_TARGET;
			break;
		}
		case FIND_TARGET:{
			if(vision.isCentered()!=0){
				tankDrive.turn(2);
			}
			else{
				nextState=State.FIRE;
			}
			break;
		}
		case FIRE:{
//			shooter.angleShooterAccel(physics.FindAngle());
//			shooter.spinRollers(physics.FindSpinSpeed());
//			try{
//			Thread.sleep(3000); 
//			}
//			catch(InterruptedException e){
//				System.out.print("Thread fail");
//			}
//			shooter.shootBall();
//			nextState=State.END_CASE;
			break;
		}
		case END_CASE: {
			break;
		}
		}
		presentState = nextState;
	}
}
