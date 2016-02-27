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
	public static final int LEFT_DRIVE_TRAIN_FRONT_E = HWP.CAN_5;
	public static final int LEFT_DRIVE_TRAIN_BACK = HWP.CAN_4;
	public static final int RIGHT_DRIVE_TRAIN_FRONT_E = HWP.CAN_3;
	public static final int RIGHT_DRIVE_TRAIN_BACK = HWP.CAN_2;
	public static final int LIGHT_RING = HWP.CAN_4;
	public static final int ANGLE_MOTOR = HWP.CAN_1;
	public static final int SHOOTER_LEFT = HWP.CAN_3;
	public static final int SHOOTER_RIGHT = HWP.CAN_5;

	// Encoders
	// TODO: Get these values
	public static final int LEFT_DRIVE_ENCODER = HWP.CAN_5;
	public static final int RIGHT_DRIVE_ENCODER = HWP.CAN_3;

	// Joysticks
	public static final int LEFT_JOYSTICK = HWP.JOY_0;
	public static final int RIGHT_JOYSTICK = HWP.JOY_1;
	public static final int AUX_JOYSTICK = HWP.JOY_2;

	//Sensors
	public static final int GYRO = HWP.ANALOG_0;
	public static final int ACCEL_CHANNEL_X = HWP.ANALOG_2;
	public static final int ACCEL_CHANNEL_Y = HWP.ANALOG_3;
	public static final int PRESSURE_SENSOR = HWP.ANALOG_3;
	
	// Compressor
	// TODO: Get these values
	public static final int COMPRESSOR = 0;
	public static final int DEFAULT_MODULE_CHANNEL = 1;
	public static final int SHOOTER_PISTON_CHANNEL = 4;
	public static final int ClIMB_DEPLOY_CHANNEL = 2;
	public static final int ANGLE_PISTON_CHANNEL = 1;
	public static final int CLIMB_RETRACT_CHANNEL = 3;
	
	
	// Buttons
	public static final int SPIN_UP_INTAKE = HWP.BUTTON_4;
	public static final int SPIN_UP_SHOOTER = HWP.BUTTON_5;
	public static final int SHOOT_BALL = HWP.BUTTON_2;
}
