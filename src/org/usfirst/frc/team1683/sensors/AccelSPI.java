package org.usfirst.frc.team1683.sensors;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class AccelSPI extends edu.wpi.first.wpilibj.ADXL362{

	public AccelSPI(SPI.Port port, Accelerometer.Range range) {
		super(port, range);
		// TODO Auto-generated constructor stub
	}
	
	public AccelSPI(Accelerometer.Range range) {
		super(range);
		super.initTable(getTable());
	}
	
	/*public double getXaxis() {
		super.updateTable();
		return getTable().getNumber("X", 0.0);
	}*/
	
	public double getXaxis() {
		return super.getAccelerations().XAxis;
	}
	
	public double getYaxis() {
		return super.getAccelerations().YAxis;
	}
	
	public double getZaxis() {
		return super.getAccelerations().ZAxis;
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
}
