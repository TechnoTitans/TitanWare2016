package org.usfirst.frc.team1683.autonomous;

import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
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
	public static final double GYRO_ANGLE_TOLERANCE = 15.0;
	public static final double RAMP_LENGTH = 18;

	protected TankDrive tankDrive;
	protected Encoder leftEncoder;
	protected Encoder rightEncoder;

	public Autonomous(TankDrive tankDrive) {
		this.tankDrive = tankDrive;
		leftEncoder = tankDrive.getLeftEncoder();
		rightEncoder = tankDrive.getRightEncoder();
	}

	protected static enum State {
		INIT_CASE, END_CASE, DRIVE_FORWARD, CROSS_DEFENSE,REACH_DISTANCE,FIND_TARGET,FIRE, ANGLE_CORRECT
	}

	public static enum AutonomousMode {
		DO_NOTHING, REACH_DEFENSE, BREACH_DEFENSE, TEST_AUTO, SHOOT_AT_TARGET
	}
	public static State presentState = State.INIT_CASE;
	public static State nextState;
	public static double reachDistance; // 74 inches
	public static double properDistance;
	public void updatePreferences() {
		reachDistance = 74;// SmartDashboard.getDouble("reachDistance");
		properDistance = SmartDashboard.getDouble("properDistance");
	}

	 public abstract void run();
}
