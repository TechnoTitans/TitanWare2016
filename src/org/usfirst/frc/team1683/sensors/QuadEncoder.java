package org.usfirst.frc.team1683.sensors;

import org.usfirst.frc.team1683.driveTrain.TalonSRX;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;

public class QuadEncoder implements Encoder {

	private TalonSRX talonSRX;
	private double distancePerRevolution;

	public QuadEncoder(TalonSRX talonSRX, double distancePerRevolution) {
		if (talonSRX.isSensorPresent(FeedbackDevice.QuadEncoder) == FeedbackDeviceStatus.FeedbackStatusPresent) {
			this.talonSRX = talonSRX;
		}
		this.distancePerRevolution = distancePerRevolution;

		this.talonSRX.configEncoderCodesPerRev((int) distancePerRevolution);
	}

	@Override
	public double getDistance() {
		return talonSRX.getPosition();
	}

	@Override
	public double getSpeed() {
		return talonSRX.getSpeed();
	}

}
