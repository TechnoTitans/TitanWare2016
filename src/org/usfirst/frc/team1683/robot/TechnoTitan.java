
package org.usfirst.frc.team1683.robot;
import org.usfirst.frc.team1683.autonomous.AutonomousSwitcher;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.sensors.DIOEncoder;
import org.usfirst.frc.team1683.sensors.QuadEncoder;
import org.usfirst.frc.team1683.test.AccelSPITester;
import org.usfirst.frc.team1683.test.BuiltInAccelTester;
import org.usfirst.frc.team1683.test.GyroTester;
import org.usfirst.frc.team1683.test.VisionTest;

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
	public static final boolean LEFT_REVERSE = true;
	public static final boolean RIGHT_REVERSE = false;
	VisionTest vision;
	TankDrive drive;
	BuiltInAccelTester accel; 
	AccelSPITester accel2;
	GyroTester gyro;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// chooser = new SendableChooser();
		// chooser.addDefault("Default Auto", defaultAuto);
		// chooser.addObject("My Auto", customAuto);
		// SmartDashboard.putData("Auto choices", chooser);
		//FindGoal vision=new FindGoal();
		
//		DIOEncoder leftEncoder = new DIOEncoder(HWR.LEFT_DRIVE_ENCODER_A, HWR.LEFT_DRIVE_ENCODER_B, LEFT_REVERSE, WHEEL_DISTANCE_PER_PULSE);
//		DIOEncoder rightEncoder = new DIOEncoder(HWR.RIGHT_DRIVE_ENCODER_A, HWR.RIGHT_DRIVE_ENCODER_B, RIGHT_REVERSE, WHEEL_DISTANCE_PER_PULSE);

//		leftGroup.add(new TalonSRX(HWR.LEFT_DRIVE_TRAIN_FRONT, LEFT_REVERSE));
//		leftGroup.add(new TalonSRX(HWR.LEFT_DRIVE_TRAIN_BACK, LEFT_REVERSE));
//		rightGroup.add(new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_FRONT, RIGHT_REVERSE));
//		rightGroup.add(new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_BACK, RIGHT_REVERSE));
		/*
		TalonSRX encoderTalon = new TalonSRX(3, false);
		QuadEncoder encoder = new QuadEncoder(encoderTalon, 1024);
		MotorGroup leftGroup = new MotorGroup(encoder, encoderTalon);
		MotorGroup rightGroup = new MotorGroup(encoder, new TalonSRX(5,true));
//		leftGroup.add(encoderTalon);
//		rightGroup.add(new TalonSRX(5, true));
		leftGroup.setBrakeMode(true);
		rightGroup.setBrakeMode(true);
		drive = new TankDrive(leftGroup, rightGroup);

		switcher = new AutonomousSwitcher(drive);

		vision = new VisionTest();		
		*/
		
		MotorGroup leftGroup = new MotorGroup(new TalonSRX(HWR.LEFT_DRIVE_TRAIN_FRONT, LEFT_REVERSE),
				new TalonSRX(HWR.LEFT_DRIVE_TRAIN_BACK, LEFT_REVERSE));
		MotorGroup rightGroup = new MotorGroup(new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_FRONT, RIGHT_REVERSE),
				new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_BACK, RIGHT_REVERSE));
		drive = new TankDrive(leftGroup, rightGroup);
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
//		if (!run) {
//			drive.moveDistance(250, 1);
//			run = true;
//		}
		
//		SmartDashboard.sendData("getPosition", ((QuadEncoder)drive.getLeftGroup().getEncoder()).getTalon().getPosition()); 
//		SmartDashboard.sendData("getEncPosition", ((QuadEncoder)drive.getLeftGroup().getEncoder()).getTalon().getEncPosition()); 
	}
	

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		drive.driveMode();
		//accel.test();
//		vision.test();42
	}
	
	public void testInit() {
		accel = new BuiltInAccelTester();
		//accel2 = new AccelSPITester();
		gyro = new GyroTester();
		
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		accel.test();
		accel2.test();
		//gyro.test();
		

	}

}

