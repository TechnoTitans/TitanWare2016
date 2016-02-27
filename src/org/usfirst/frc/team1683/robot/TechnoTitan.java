
package org.usfirst.frc.team1683.robot;
import org.usfirst.frc.team1683.autonomous.AutonomousSwitcher;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.Talon;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.ClimbingPistons;
import org.usfirst.frc.team1683.pneumatics.Piston;
import org.usfirst.frc.team1683.sensors.Gyro;
import org.usfirst.frc.team1683.sensors.QuadEncoder;
import org.usfirst.frc.team1683.sensors.TiltSensor;
import org.usfirst.frc.team1683.sensors.PressureReader;
import org.usfirst.frc.team1683.shooter.PickerUpper;
import org.usfirst.frc.team1683.shooter.Shooter;
import org.usfirst.frc.team1683.test.VisionTest;
import org.usfirst.frc.team1683.vision.FindGoal;

import edu.wpi.first.wpilibj.Compressor;
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
	public static final double WHEEL_DISTANCE_PER_PULSE = 10;
	public static final boolean LEFT_REVERSE = false;
	public static final boolean RIGHT_REVERSE = true;
	public static final double WHEEL_RADIUS = 3.391 / 2;
	//LightRing ring;
	//JoystickFilter auxFilter;
	FindGoal findgoal;
	TankDrive drive;
	Gyro gyro;
	ClimbingPistons climberPistons;
	TalonSRX leftTalon;
	TalonSRX rightTalon;
	TalonSRX angleMotor;
	TiltSensor tiltSensor;
	Piston shootPiston;
	PressureReader pressureReader;
	// LightRing lightRing;
	
	Shooter shooter;
	PickerUpper pickerUpper;

	MotorGroup testGroup;

	Compressor compressor;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// switcher = new AutonomousSwitcher(drive);

		angleMotor = new TalonSRX(HWR.ANGLE_MOTOR, false);
		
		gyro = new Gyro(HWR.GYRO);

		TalonSRX leftETalonSRX = new TalonSRX(HWR.LEFT_DRIVE_TRAIN_FRONT_E, LEFT_REVERSE);
		TalonSRX rightETalonSRX = new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_FRONT_E, RIGHT_REVERSE);


		MotorGroup leftGroup = new MotorGroup(new QuadEncoder(leftETalonSRX, WHEEL_RADIUS),
				// MotorGroup leftGroup = new MotorGroup(
				leftETalonSRX, new TalonSRX(HWR.LEFT_DRIVE_TRAIN_BACK, LEFT_REVERSE));

		MotorGroup rightGroup = new MotorGroup(new QuadEncoder(rightETalonSRX, WHEEL_RADIUS),
				// MotorGroup rightGroup = new MotorGroup(
				rightETalonSRX, new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_BACK, RIGHT_REVERSE));
		
		drive = new TankDrive(leftGroup, rightGroup, gyro);
		
		tiltSensor = new TiltSensor(HWR.ACCEL_CHANNEL_X, HWR.ACCEL_CHANNEL_Y);
		
//		testGroup = rightGroup;
		climberPistons = new ClimbingPistons(HWR.ANGLE_PISTON_CHANNEL, HWR.ClIMB_DEPLOY_CHANNEL, HWR.CLIMB_RETRACT_CHANNEL);
		shootPiston = new Piston(HWR.DEFAULT_MODULE_CHANNEL, HWR.SHOOTER_PISTON_CHANNEL);
		MotorGroup shooterGroup = new MotorGroup(
				new TalonSRX(HWR.SHOOTER_LEFT, true),
				new TalonSRX(HWR.SHOOTER_RIGHT, true));
		
		pickerUpper = new PickerUpper(shooterGroup);
		shooter = new Shooter(shooterGroup, 
				angleMotor, shootPiston);
		//pressureReader = new PressureReader(3);
		//ring = new LightRing(HWR.LIGHT_RING);
	 //ring = new TalonSRX(HWR.LIGHT_RING, false);
		compressor = new Compressor(1);
		//auxFilter = new JoystickFilter(DriverStation.auxStick);
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
		
		// autoSelected = (String) chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
//		System.out.println("Auto selected: " + autoSelected);
//		switcher.updateAutoSelected();
	}
	

	/**
	 * This function is called periodically during autonomous
	 */
	boolean run = false;
	public void autonomousPeriodic() {
		if (!run) {
//			gyro.reset();
////			drive.moveDistance(120, 0.3);
//			drive.turn(180, 0.25);
////			drive.set(.25);
//			run = true;
		}
		SmartDashboard.sendData("getLeftPosition", ((QuadEncoder)drive.getLeftGroup().getEncoder()).getTalon().getPosition()); 
		SmartDashboard.sendData("getRightPosition", ((QuadEncoder)drive.getRightGroup().getEncoder()).getTalon().getPosition()); 
		SmartDashboard.sendData("getLeftDistance", ((QuadEncoder)drive.getLeftGroup().getEncoder()).getDistance()); 
		SmartDashboard.sendData("getRightDistance", ((QuadEncoder)drive.getRightGroup().getEncoder()).getDistance()); 
//		SmartDashboard.sendData("getEncPosition", ((QuadEncoder)drive.getLeftGroup().getEncoder()).getTalon().getEncPosition()); 
		
//		testGroup.set(0.25);
//		drive.set(0.25);
	}
	
	public void disabledPeriodic() {
		run = false;
	}
	

	public void teleopInit() {
		compressor.stop();
		shooter.reset();
	}
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		//drive.driveMode();
		//pickerUpper.intakeMode();
		shooter.shootMode();
	
		
		
	}
	
	public void testInit() {
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
	}

}

