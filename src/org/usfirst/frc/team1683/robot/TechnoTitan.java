
package org.usfirst.frc.team1683.robot;

import org.usfirst.frc.team1683.autonomous.AutonomousSwitcher;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.Climber;
import org.usfirst.frc.team1683.pneumatics.Solenoid;
import org.usfirst.frc.team1683.sensors.Gyro;
import org.usfirst.frc.team1683.sensors.LinearActuator;
import org.usfirst.frc.team1683.sensors.QuadEncoder;
import org.usfirst.frc.team1683.sensors.PressureReader;
import org.usfirst.frc.team1683.shooter.Shooter;
import org.usfirst.frc.team1683.vision.FindGoal;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TechnoTitan extends IterativeRobot {
	public static AutonomousSwitcher switcher;
//	public static final double WHEEL_DISTANCE_PER_PULSE = 10;
	public static final boolean LEFT_REVERSE = false;
	public static final boolean RIGHT_REVERSE = true;
	public static final double WHEEL_RADIUS = 3.391 / 2;
	FindGoal findgoal;
	TankDrive drive;
	Climber climberPistons;
	PressureReader pressureReader;
	// LightRing lightRing;
	Shooter shooter;

	LinearActuator actuator;



	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// switcher = new AutonomousSwitcher(drive);
		
		// GYRO
		Gyro gyro = new Gyro(HWR.GYRO);

		// DRIVE TRAIN
		TalonSRX leftETalonSRX = new TalonSRX(HWR.LEFT_DRIVE_TRAIN_FRONT_E, LEFT_REVERSE);
		TalonSRX rightETalonSRX = new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_FRONT_E, RIGHT_REVERSE);

		MotorGroup leftGroup = new MotorGroup(new QuadEncoder(leftETalonSRX, WHEEL_RADIUS),
		// MotorGroup leftGroup = new MotorGroup(
				leftETalonSRX, new TalonSRX(HWR.LEFT_DRIVE_TRAIN_BACK, LEFT_REVERSE));

		MotorGroup rightGroup = new MotorGroup(new QuadEncoder(rightETalonSRX, WHEEL_RADIUS),
		// MotorGroup rightGroup = new MotorGroup(
				rightETalonSRX, new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_BACK, RIGHT_REVERSE));

		drive = new TankDrive(leftGroup, rightGroup, gyro);
		// END DRIVE TRAIN

		actuator = new LinearActuator(HWP.CAN_5, false);

		climberPistons = new Climber(HWR.ClIMB_DEPLOY_CHANNEL, HWR.CLIMB_RETRACT_CHANNEL);

		Solenoid shootPiston = new Solenoid(HWR.DEFAULT_MODULE_CHANNEL, HWR.SHOOTER_PISTON_CHANNEL);

		shooter = new Shooter(HWR.SHOOTER_LEFT, HWR.SHOOTER_RIGHT, HWR.ANGLE_MOTOR, shootPiston);

		// CameraServer server = CameraServer.getInstance();
		// server.setQuality(50);
		// server.startAutomaticCapture("cam1");

	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
		SmartDashboard.sendData("getLeftPosition",
				((QuadEncoder) drive.getLeftGroup().getEncoder()).getTalon().getPosition());
		SmartDashboard.sendData("getRightPosition",
				((QuadEncoder) drive.getRightGroup().getEncoder()).getTalon().getPosition());
		SmartDashboard.sendData("getLeftDistance", ((QuadEncoder) drive.getLeftGroup().getEncoder()).getDistance());
		SmartDashboard.sendData("getRightDistance", ((QuadEncoder) drive.getRightGroup().getEncoder()).getDistance());
	}

	@Override
	public void teleopInit() {
		// shooter.reset();
	}

	@Override
	public void teleopPeriodic() {
		drive.driveMode();
		shooter.shootMode();

	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}

}
