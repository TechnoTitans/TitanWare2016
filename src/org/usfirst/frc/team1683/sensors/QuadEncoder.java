package org.usfirst.frc.team1683.sensors;

import org.usfirst.frc.team1683.driveTrain.EncoderNotFoundException;
import org.usfirst.frc.team1683.driveTrain.TalonSRX;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;

public class QuadEncoder implements Encoder {

	public static final double PULSES_PER_REVOLUTION = 1024;
	private TalonSRX talonSRX;
	private double distancePerRevolution;

	public QuadEncoder(TalonSRX talonSRX, double distancePerRevolution){
//		if (talonSRX.isSensorPresent(FeedbackDevice.QuadEncoder) == FeedbackDeviceStatus.FeedbackStatusPresent) {
			this.talonSRX = talonSRX;
//		} else {
//			throw new EncoderNotFoundException();
//			this.talonSRX = null;
//		}
		
		this.talonSRX.configEncoderCodesPerRev((int) distancePerRevolution);
	}

	@Override
	public double getDistance() {
//		double pos = talonSRX.getEncPosition();
//		return (pos%PULSES_PER_REVOLUTION)/PULSES_PER_REVOLUTION*360;
		return talonSRX.getPosition();
	}

	public double getPosition() {
		return talonSRX.getEncPosition();
	}
	
	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return talonSRX.getSpeed();
	}
	
	public void reset() {
		talonSRX.setPosition(0);
//		talonSRX.setpos
	}
	
	public TalonSRX getTalon() {
		return talonSRX;
	}

}
