package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.EncoderNotFoundException;
import org.usfirst.frc.team1683.shooter.Shooter;
import org.usfirst.frc.team1683.sensors.BuiltInAccel;
import org.usfirst.frc.team1683.sensors.Gyro;
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
	Gyro gyro;
	double originalAngle;

	public ShootAtTarget(TankDrive driveTrain, BuiltInAccel accel, FindGoal vision, Shooter shooter, Gyro gyro) {
		super(driveTrain);
		this.accel = accel;
		this.vision = vision;
		this.shooter = shooter;
		this.gyro = gyro;

		// this.physics=physics;
	}

	public void run() {
		switch (presentState) {
		case INIT_CASE:
			nextState = State.DRIVE_FORWARD;
			break;

		case DRIVE_FORWARD:
			// originalAngle = gyro.getAngle();
			tankDrive.moveDistance(reachDistance);
			nextState = State.CROSS_DEFENSE;
			break;

		// TODO: Need to add timeout to moveDistance
		case CROSS_DEFENSE:
			tankDrive.moveDistance(RAMP_LENGTH);
			if (!accel.isFlat()) {
				tankDrive.set(Motor.MID_SPEED);
			} else {
				tankDrive.stop();
			}
			// after breaching defense, arrive at a good point for shooting
			nextState = State.ANGLE_CORRECT;
			break;

		case ANGLE_CORRECT:
			if (Math.abs(gyro.getAngle() - originalAngle) >= GYRO_ANGLE_TOLERANCE)
				tankDrive.turn(gyro.getAngle() - originalAngle);
			nextState = State.REACH_DISTANCE;
			break;

		case REACH_DISTANCE:
			// TODO: WTF does this variable do?
			tankDrive.moveDistance(properDistance);
			nextState = State.FIND_TARGET;
			break;

		case FIND_TARGET:
			if (vision.isCentered() == 1) {
				tankDrive.turn(-1);
			} 
			else if (vision.isCentered() == -1) {
				tankDrive.turn(1);
			}
			else {
				nextState = State.FIRE;
			}
			break;

		case FIRE:
			shooter.angleShooter(physics.FindAngle());
			// TODO: Get correct units for spinShooter
			 shooter.spinShooter(physics.FindSpinSpeed());
			// try{
			// Thread.sleep(3000);
			// }
			// catch(InterruptedException e){
			// System.out.print("Thread fail");
			// }
			 shooter.shootBall();
			 nextState=State.END_CASE;
			break;

		case END_CASE:
			break;

		default:
			break;
		}

		presentState = nextState;
	}
}
