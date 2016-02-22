package org.usfirst.frc.team1683.shooter;

import java.sql.Driver;

import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickFilter {
	Joystick joy;
	private final double JOYSTICK_SENSTITIVITY;
	private double oldOutput;

	public JoystickFilter(Joystick joy) {
		this.joy = joy;
		if (DriverStation.auxStick.equals(joy))
			JOYSTICK_SENSTITIVITY = SmartDashboard.getDouble("Aux Joystick Sensitivity");
		else if (DriverStation.leftStick.equals(joy))
			JOYSTICK_SENSTITIVITY = SmartDashboard.getDouble("Left Joystick Sensitivity");
		else if (DriverStation.rightStick.equals(joy))
			JOYSTICK_SENSTITIVITY = SmartDashboard.getDouble("Right Joystick Sensitivity");
		else 
			JOYSTICK_SENSTITIVITY = 0;			
	}

	public double filterInput(double input) {
		double output = input + JOYSTICK_SENSTITIVITY * (oldOutput - input);
		oldOutput = output;
		return output;
	}
}
