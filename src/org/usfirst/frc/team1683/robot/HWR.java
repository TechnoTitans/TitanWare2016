package org.usfirst.frc.team1683.robot;

/**
 * HWR - Stands for HardWare References. Each variable references a piece of
 * hardware to a channel or port.
 * 
 * @author David
 *
 */
public class HWR {

	// Motors
	public static final int TALON_0 = HWP.PWM_0;
	public static final int TALON_1 = HWP.PWM_1;
	public static final int TALON_2 = HWP.PWM_2;
	public static final int TALON_3 = HWP.PWM_3;

	public static final int TALON_SRX_0 = HWP.CAN_1;
	public static final int TALON_SRX_1 = HWP.CAN_2;
	public static final int TALON_SRX_2 = HWP.CAN_3;
	public static final int TALON_SRX_3 = HWP.CAN_4;

	// Encoders
	public static final int ENCODER_0_A = HWP.DIO_0;
	public static final int ENCODER_0_B = HWP.DIO_1;

	// Joysticks
	public static final int LEFT_JOYSTICK = HWP.JOY_0;
	public static final int RIGHT_JOYSTICK = HWP.JOY_1;
	public static final int AUX_JOYSTICK = HWP.JOY_2;

}
