package org.usfirst.frc.team1683.driverStation;

import org.usfirst.frc.team1683.robot.HWR;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Joystick;

public class DriverStation {

	public static final int XAxis = 0;
	public static final int YAxis = 1;
	public static final int ZAxis = 2;
	public static final int dialAxis = 3;
	
	public static Joystick leftStick = new Joystick(HWR.LEFT_JOYSTICK);
	public static Joystick rightStick = new Joystick(HWR.RIGHT_JOYSTICK);
	public static Joystick auxStick = new Joystick(HWR.AUX_JOYSTICK);
	
	
	//TODO
	public static boolean antiBounce (/*args*/) {
		return false;
	}
	
	//returns int
	public static int getInt(String key) {
		return Preferences.getInstance().getInt(key, 0);
	}
	
	//returns double
	public static double getDouble(String key) {
		return Preferences.getInstance().getDouble(key, 0.0);
	}
}
