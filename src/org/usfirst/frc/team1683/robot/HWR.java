package org.usfirst.frc.team1683.robot;

/**
 * HWR - Stands for HardWare References. Each variable references a piece of
 * hardware to a channel or port.
 * 
 * @author David Luo
 *
 */
public class HWR {

	// Motors
	public static final int LEFT_DRIVE_TRAIN_FRONT = HWP.CAN_3;
	public static final int LEFT_DRIVE_TRAIN_BACK = HWP.CAN_2;
	public static final int RIGHT_DRIVE_TRAIN_FRONT = HWP.CAN_4;
	public static final int RIGHT_DRIVE_TRAIN_BACK = HWP.CAN_1;

	// TODO: Get these values
	public static final int SHOOTER_LEFT = HWP.CAN_3;
	public static final int SHOOTER_RIGHT = HWP.CAN_5;

	// Encoders
	// TODO: Get these values
	public static final int LEFT_DRIVE_ENCODER = 0;
	public static final int RIGHT_DRIVE_ENCODER = 0;

	// Joysticks
	public static final int LEFT_JOYSTICK = HWP.JOY_0;
	public static final int RIGHT_JOYSTICK = HWP.JOY_1;
	public static final int AUX_JOYSTICK = HWP.JOY_2;

	// Gyro
	public static final int GYRO = HWP.ANALOG_1;
	
	// Compressor
	// TODO: Get these values
	public static final int COMPRESSOR = 0;
	public static final int DEFAULT_MODULE_CHANNEL = 0;
	public static final int SHOOTER_PISTON_CHANNEL = 3;
	
	// Buttons
	public static final int START_INTAKE_BALL = HWP.BUTTON_5;
	public static final int START_SHOOTER = HWP.BUTTON_4;
	public static final int ACTUATE_PISTON_SHOOTER = HWP.BUTTON_2;

}
