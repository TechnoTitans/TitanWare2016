package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.sensors.Encoder;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive implements DriveTrain {

	public TankDrive() {
		
	}
	
	public TankDrive(int lChannelA, int lChannelB, boolean lRevDir, 
			int rChannelA, int rChannelB, boolean rRevDir, double wheelDistPerPulse) {
		
		Encoder leftEncoder = new Encoder(lChannelA, lChannelB, lRevDir, wheelDistPerPulse);
		Encoder rightEncoder = new Encoder(rChannelA, rChannelB, rRevDir, wheelDistPerPulse);
	}
	@Override
	public void moveDistance(double distance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void turn(double degrees) {
		// TODO Auto-generated method stub

	}

	@Override
	public void driveMode(double distance) {
		double lSpeed = DriverStation.leftStick.getRawAxis(DriverStation.YAxis);
		double rSpeed = DriverStation.rightStick.getRawAxis(DriverStation.YAxis);
		

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
