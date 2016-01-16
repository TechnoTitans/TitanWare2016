package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.sensors.Encoder;

import edu.wpi.first.wpilibj.CANTalon;

public class TalonSRX extends CANTalon implements Motor{

	boolean reverseDirection;
	Encoder encoder;
	boolean hasEncoder = false;;
	int channel;
	
	public TalonSRX(int channel, boolean reverseDirection) {
		super(channel);
		this.channel = channel;
		this.reverseDirection = reverseDirection;
		// TODO Auto-generated constructor stub
	}
	public TalonSRX(int channel, boolean reverseDirection, Encoder encoder) {
		super(channel);
		this.reverseDirection = reverseDirection;
		hasEncoder = true;
		this.encoder = encoder;
		// TODO Auto-generated constructor stub
	}
	
	
	public void setSpeed(double speed) {
		if (reverseDirection) super.set(speed);
		else super.set(-speed);
	}

	@Override
	public String getSmartDashboardType() {
		// TODO Auto-generated method stub
		return null;
	}	

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.disableControl();
		
	}
	
	 private class MotorMover implements Runnable {
		public MotorMover() {
			//does stuff
		}
		public void run() {
			
		}
	}

	@Override
	public boolean hasEncoder() {
		if (hasEncoder) return true;
		else return false;
	}

	@Override
	public Encoder getEncoder() {
		// TODO Auto-generated method stub
		return encoder;
	}

	@Override
	public int getChannel() {
		// TODO Auto-generated method stub
		return channel;
	}
	@Override
	public void moveDistance(double distance) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isReversed() {
		// TODO Auto-generated method stub
		return false;
	}

}
