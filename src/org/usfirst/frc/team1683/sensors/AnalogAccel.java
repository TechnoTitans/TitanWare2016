package org.usfirst.frc.team1683.sensors;

public class AnalogAccel extends edu.wpi.first.wpilibj.AnalogAccelerometer{
	
	public AnalogAccel(int channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}
	public double getAcceleration() {
		return super.getAcceleration();
	}
	public void setSensitivity(double sensitivity){
		super.setSensitivity(sensitivity);
	}
}
