
package org.usfirst.frc.team1683.robot;
import org.usfirst.frc.team1683.vision.FindGoal;
import org.usfirst.frc.team1683.sensors.Encoder;
import org.usfirst.frc.team1683.test.AccelSPITester;
import org.usfirst.frc.team1683.test.BuiltInAccelTester;
import org.usfirst.frc.team1683.test.GyroTester;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Timer.StaticInterface;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import java.util.ArrayList;

import org.usfirst.frc.team1683.driveTrain.DriveTrain;
import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driveTrain.Talon;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;

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
	public static final double WHEEL_DISTANCE_PER_PULSE = 10;
	public static final boolean LEFT_REVERSE = true;
	public static final boolean RIGHT_REVERSE = false;
	FindGoal vision;
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
		Encoder leftEncoder = new Encoder(HWR.LEFT_DRIVE_ENCODER_A, HWR.LEFT_DRIVE_ENCODER_B, LEFT_REVERSE, WHEEL_DISTANCE_PER_PULSE);
		Encoder rightEncoder = new Encoder(HWR.RIGHT_DRIVE_ENCODER_A, HWR.RIGHT_DRIVE_ENCODER_B, RIGHT_REVERSE, WHEEL_DISTANCE_PER_PULSE);
		ArrayList<Motor> left = new ArrayList<>();
		ArrayList<Motor> right = new ArrayList<>();
		left.add(new TalonSRX(HWR.LEFT_DRIVE_TRAIN_FRONT, LEFT_REVERSE));
		left.add(new TalonSRX(HWR.LEFT_DRIVE_TRAIN_BACK, LEFT_REVERSE));
		right.add(new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_FRONT, RIGHT_REVERSE));
		right.add(new TalonSRX(HWR.RIGHT_DRIVE_TRAIN_BACK, RIGHT_REVERSE));
		MotorGroup leftGroup = new MotorGroup(left, leftEncoder);
		MotorGroup rightGroup = new MotorGroup(right, rightEncoder);
		drive = new TankDrive(leftGroup, rightGroup);

		

		vision=new FindGoal();
		
		
		
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
	}
	
	public void testInit() {
		accel = new BuiltInAccelTester();
		accel2 = new AccelSPITester();
		gyro = new GyroTester();
		
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		// switch (autoSelected) {
		// case customAuto:
		// // Put custom auto code here
		// break;
		// case defaultAuto:
		// default:
		// // Put default auto code here
		// break;
		// }
		vision.FindDistance();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		drive.driveMode();
		accel.test();
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

