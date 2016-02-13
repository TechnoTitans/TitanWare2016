package org.usfirst.frc.team1683.driverStation;

import org.usfirst.frc.team1683.robot.HWR;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Joystick;

public class DriverStation {
	
	public static boolean[][] lasts = new boolean[3][	11];
	

	public static final int XAxis = 0;
	public static final int YAxis = 1;
	public static final int ZAxis = 2;
	public static final int dialAxis = 3;
	
	public static Joystick leftStick = new Joystick(HWR.LEFT_JOYSTICK);
	public static Joystick rightStick = new Joystick(HWR.RIGHT_JOYSTICK);
	public static Joystick auxStick = new Joystick(HWR.AUX_JOYSTICK);
	
	public static double scaleToQuarter(Joystick joy) {
	     return 0.25*joy.getRawAxis(YAxis);
	}
	//TODO
	 public static boolean antiBounce(int joystick, int button){
	       boolean pressed = false;
	       if (joystick == 1)
	           pressed = DriverStation.leftStick.getRawButton(button);
	       else if (joystick == 2)
	           pressed = DriverStation.rightStick.getRawButton(button);
	       else if (joystick == 0)
	           pressed = DriverStation.auxStick.getRawButton(button);
	       
	       if(pressed && !lasts[joystick][button-1]){
	           lasts[joystick][button-1] = true;
	           return true;
	       }
	       else if (pressed && lasts[joystick][button-1]){
	           return false;
	       }
	       else
	       {
	           lasts[joystick][button-1] = false;
	           return false;
	       }
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
