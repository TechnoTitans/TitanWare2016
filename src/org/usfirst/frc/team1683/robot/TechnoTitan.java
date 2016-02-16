
package org.usfirst.frc.team1683.robot;
import org.usfirst.frc.team1683.autonomous.AutonomousSwitcher;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.Talon;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.pneumatics.Piston;
import org.usfirst.frc.team1683.sensors.Gyro;
import org.usfirst.frc.team1683.sensors.QuadEncoder;
import org.usfirst.frc.team1683.shooter.PickerUpper;
import org.usfirst.frc.team1683.shooter.Shooter;
import org.usfirst.frc.team1683.test.AccelSPITester;
import org.usfirst.frc.team1683.test.BuiltInAccelTester;
import org.usfirst.frc.team1683.test.GyroTester;
import org.usfirst.frc.team1683.test.VisionTest;

import com.ni.vision.NIVision.SupervisedColorSegmentationReport;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TechnoTitan extends IterativeRobot {
	// final String[] AUTOS = {"Default", "My Auto"};
	// final String defaultAuto = "Default";
	// final String customAuto = "My Auto";
	// String autoSelected;
	// SendableChooser chooser;
	public static AutonomousSwitcher switcher;
	public static final double WHEEL_DISTANCE_PER_PULSE = 10;
	public static final boolean LEFT_REVERSE = false;
	public static final boolean RIGHT_REVERSE = true;
	public static final double WHEEL_RADIUS =3.391/2; 
	VisionTest vision;
	TankDrive drive;
	BuiltInAccelTester accel; 
	AccelSPITester accel2;
//	GyroTester gyro;
	Gyro gyro;
	PickerUpper pickerUpper;
	TalonSRX leftTalon;
	TalonSRX rightTalon;
	Talon angleMotor;
	Shooter shooter;
	Piston shootPiston;

	MotorGroup testGroup;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// chooser = new SendableChooser();
		// chooser.addDefault("Default Auto", defaultAuto);
		// chooser.addObject("My Auto", customAuto);
		// SmartDashboard.putData("Auto choices", chooser);
//		//FindGoal vision=new FindGoal();
//		switcher = new AutonomousSwitcher(drive);
//		vision = new VisionTest();		
		
		//leftTalon = new TalonSRX(3,false);
		//rightTalon = new TalonSRX(5, false);
		
		gyro = new Gyro(HWR.GYRO);
		
		TalonSRX leftETalonSRX = new TalonSRX(HWR.LEFT_DRIVE_TRAIN_FRONT_E, LEFT_REVERSE);
		TalonSRX rightETalonSRX = new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_FRONT_E, RIGHT_REVERSE);
		
		MotorGroup leftGroup = new MotorGroup(new QuadEncoder(leftETalonSRX, WHEEL_RADIUS),
//		MotorGroup leftGroup = new MotorGroup(
				leftETalonSRX,
				new TalonSRX(HWR.LEFT_DRIVE_TRAIN_BACK, LEFT_REVERSE));
		
		MotorGroup rightGroup = new MotorGroup(new QuadEncoder(rightETalonSRX, WHEEL_RADIUS),
//		MotorGroup rightGroup = new MotorGroup(
				rightETalonSRX,
				new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_BACK, RIGHT_REVERSE));
		drive = new TankDrive(leftGroup, rightGroup, gyro);
		
		testGroup = rightGroup;
		
		shootPiston = new Piston(HWR.DEFAULT_MODULE_CHANNEL, HWR.SHOOTER_PISTON_CHANNEL);
		MotorGroup shooterGroup = new MotorGroup(
				new TalonSRX(HWR.SHOOTER_LEFT, false),
				new TalonSRX(HWR.SHOOTER_RIGHT, false));
		
		pickerUpper = new PickerUpper(shooterGroup);
		shooter = new Shooter(angleMotor, shooterGroup, shootPiston);
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
//		switcher.run();
		// switch (autoSelected) {
		// case customAuto:
		// // Put custom auto code here
		// break;
		// case defaultAuto:
		// default:
		// // Put default auto code here
		// break;
		// }
//		
		if (!run) {
			gyro.reset();
//			drive.moveDistance(120, 0.3);
			drive.turn(180, 0.25);
//			drive.set(.25);
			run = true;
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
	

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		drive.driveMode();
//		pickerUpper.intake();
//		shooter.shootBall();
		//accel.test();

//		vision.test();42
	}
	
	public void testInit() {
		accel = new BuiltInAccelTester();
		//accel2 = new AccelSPITester();
//		gyro = new GyroTester();
		
		//shooter.joystickAngleShooter();
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		accel.test();
		accel2.test();
//		gyro.test();
		//pickerUpper.intake();
		shooter.joystickAngleShooter();
		

	}

}

