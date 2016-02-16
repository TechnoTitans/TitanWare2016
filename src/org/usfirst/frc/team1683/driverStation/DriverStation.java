package org.usfirst.frc.team1683.driverStation;

import org.usfirst.frc.team1683.robot.HWR;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;

public class DriverStation {

	public static boolean[][] lasts = new boolean[3][11];

	public static final int XAxis = 0;
	public static final int YAxis = 1;
	public static final int ZAxis = 2;
	public static final int dialAxis = 3;

	public static Joystick leftStick = new Joystick(HWR.LEFT_JOYSTICK);
	public static Joystick rightStick = new Joystick(HWR.RIGHT_JOYSTICK);
	public static Joystick auxStick = new Joystick(HWR.AUX_JOYSTICK);

	public static double scaleToQuarter(Joystick joy) {
		return 0.25 * joy.getRawAxis(YAxis);
	}

	}
}