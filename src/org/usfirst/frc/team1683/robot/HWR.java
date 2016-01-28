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
	public static final int LEFT_DRIVE_TRAIN = HWP.CAN_0;
	public static final int RIGHT_DRIVE_TRAIN = HWP.CAN_1;

	// Encoders
	public static final int LEFT_DRIVE_ENCODER_A = HWP.DIO_0;
	public static final int LEFT_DRIVE_ENCODER_B = HWP.DIO_1;
	public static final int RIGHT_DRIVE_ENCODER_A = HWP.DIO_2;
	public static final int RIGHT_DRIVE_ENCODER_B = HWP.DIO_3;

	// Joysticks
	public static final int LEFT_JOYSTICK = HWP.JOY_0;
	public static final int RIGHT_JOYSTICK = HWP.JOY_1;
	public static final int AUX_JOYSTICK = HWP.JOY_2;

}
