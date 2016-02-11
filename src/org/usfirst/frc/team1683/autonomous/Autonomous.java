package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.sensors.DIOEncoder;
import org.usfirst.frc.team1683.sensors.Encoder;

import edu.wpi.first.wpilibj.Timer.StaticInterface;

/**
 * Class to be inherited by all auto classes.
 * 
 * @author David Luo
 *
 */
public abstract class Autonomous {
	// TODO: make autonomous
	protected TankDrive tankDrive;
	protected Encoder leftEncoder;
	protected Encoder rightEncoder;

	public Autonomous(TankDrive tankDrive) {
		this.tankDrive = tankDrive;
		leftEncoder = tankDrive.getLeftEncoder();
		rightEncoder = tankDrive.getRightEncoder();
	}

	protected static enum State {
		INIT_CASE, END_CASE, DRIVE_FORWARD,BREACH_DEFENSE,REACH_DISTANCE,FIND_TARGET,FIRE
	}

	public static enum AutonomousMode {
		DO_NOTHING, REACH_DEFENSE, TEST_AUTO
	}
	protected static enum Obstacle{
		PORTULLIS, CHEVAL_DE_FRISE, MOAT, RAMPARTS, DRAWBRIDGE, SALLY_PORT, ROCK_WALL,ROUGH_TERRAIN,LOW_BAR,PLATFORMS
	}
	protected static enum FinalPosition{
		LEFT_SIDE,CENTER,RIGHT_SIDE
	}
	public static State presentState = State.INIT_CASE;
	public static State nextState;
	public static double reachDistance; // 74 inches

	public void updatePreferences() {
		reachDistance = DriverStation.getDouble("reachDistance");
	}

	 public abstract void run();
}
