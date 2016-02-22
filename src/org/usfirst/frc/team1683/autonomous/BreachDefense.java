package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.sensors.BuiltInAccel;

import edu.wpi.first.wpilibj.Timer;

public class BreachDefense extends Autonomous {

	BuiltInAccel accel;
	private final double RAMP_LENGTH = 18;
	Timer timer;

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
			tankDrive.moveDistance(reachDistance);
			nextState = State.CROSS_DEFENSE;
			break;
		}
			// uses accel to tell if on defense or not
		case CROSS_DEFENSE: {
			tankDrive.moveDistance(RAMP_LENGTH);
			if (!accel.isFlat()) {
				tankDrive.set(Motor.MID_SPEED);
			} else
				tankDrive.stop();

			nextState = State.END_CASE;
			break;
		}
		case END_CASE: {
			break;
		}
		default:
			break;

		}
		presentState = nextState;
	}
}