package org.usfirst.frc.team1683.robot.pneumatics;

import edu.wpi.first.wpilibj.Solenoid;

import java.util.ArrayList;

import org.usfirst.frc.team1683.sensors.PressureReader;
public class AirControl {
final static boolean EXTENDED = true;
final static boolean RETRACTED = false;
	PressureReader pressure;
ArrayList<Solenoid> solenoids = new ArrayList <Solenoid>();

public void extend(){
	for(Solenoid solenoid: solenoids) {
		solenoid.set(EXTENDED);
	}
}
public boolean isExtended() {
	boolean isExtended = true;
	for(Solenoid solenoid :solenoids) {
		if (!solenoid.get())
			isExtended =false;
		
	}
	return isExtended;
	
	
}
}