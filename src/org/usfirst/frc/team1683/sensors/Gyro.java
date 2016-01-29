package org.usfirst.frc.team1683.sensors;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.GyroBase;

public class Gyro extends AnalogGyro implements Sensor{
	
	public Gyro(int channel) {
		super(channel);
		super.initGyro();
		super.reset();
	}
	@Override
	public double getRaw() {
		// TODO Auto-generated method stub
		return super.getAngle();
	}

	@Override
	public void calibrate() {
		// TODO Auto-generated method stub
		super.calibrate();
	}

	@Override
	public double getAngle() {
		return super.getAngle()%360;
	}

	@Override
	public double getRate() {
		// TODO Auto-generated method stub
		return super.getRate();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}