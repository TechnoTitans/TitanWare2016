package org.usfirst.frc.team1683.sensors;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class BuiltInAccel extends BuiltInAccelerometer{
	public static final double MAX_FLAT_ANGLE = 3.0;
	
	public BuiltInAccel() {
		super();
	}
	
	public double getX() {
		return super.getX();
	}
	
	
	public double getY() {
		return super.getY();
	}
	
	public double getZ() {
		return super.getZ();
	}
	
	public boolean isFlat() {
		return Math.abs(getAngleXZ()) < MAX_FLAT_ANGLE && Math.abs(getAngleYZ())< MAX_FLAT_ANGLE;		
		
	}
	
	public double getAngleXZ() {
		return Math.atan2(getX(), getZ())*180/Math.PI;
	}
	
	public double getAngleYZ() {
		return Math.atan2(getY(), getZ())*180/Math.PI;
	}
	


}
