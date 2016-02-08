package org.usfirst.frc.team1683.sensors;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.GyroBase;
/**
 * 
 * @author Sneha Kadiyala
 *
 */
public class Gyro extends AnalogGyro implements Sensor{
	public final static double SENSITIVITY = 0.00666693;
	
	public Gyro(int channel) {
		super(channel);
		super.initGyro();
		super.reset();
		super.setSensitivity(SENSITIVITY);
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

	
	public double getDirection() {
		double angle;
		if (super.getAngle() > 0){
			angle = super.getAngle() % 360.0;
		}
		else if (super.getAngle() < 0){
			angle = 360.0 - Math.abs(super.getAngle() % 360.0);
		}
		else {
			angle = super.getAngle();
		}
		return angle;
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
