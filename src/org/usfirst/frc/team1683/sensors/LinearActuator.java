package org.usfirst.frc.team1683.sensors;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.CANTalon;

public class LinearActuator extends CANTalon{

	public static final double ROT_PER_INCH = 8.76/12;
	public static final int POT_TURN_NUM = 10;
	public static final double MAX_ANGLE = 90;
	public static final double MIN_ANGLE = 0;
	public static final double MAX_INCH = 12; //fix number
	public static final double MIN_INCH = 0; //fix number
	
	public LinearActuator(int deviceNumber) {
		super(deviceNumber);
		// TODO Auto-generated constructor stub
	}
	
	public void PIDinit() {
//		boolean isPresent;
//		if(super.isSensorPresent(FeedbackDevice.AnalogPot).equals(FeedbackDeviceStatus.FeedbackStatusNotPresent)) {
//			isPresent = false;
//		}
//		else isPresent = true;
//		SmartDashboard.sendData("Linear Actuator present ", isPresent);
		super.setFeedbackDevice(FeedbackDevice.AnalogPot);
		super.setInverted(false);
		super.changeControlMode(TalonControlMode.Position);
		super.enableBrakeMode(true);
		super.configPotentiometerTurns(POT_TURN_NUM);
		super.enable();

	}
	
	public void PIDupdate(double posInInches) {
		super.setPID(SmartDashboard.getDouble("Linear Actuator P"), SmartDashboard.getDouble("Linear Actuator I") , SmartDashboard.getDouble("Linear Actuator D"));
		
		super.set(clampInches(posInInches) * ROT_PER_INCH); //conversion to rotations

		SmartDashboard.sendData("Talon " + super.getDeviceID(), "Position(inches)" + posInInches);
		SmartDashboard.sendData("Talon " + super.getDeviceID(), "Position(rotations)" + super.getPosition());

		SmartDashboard.sendData("Linear Actuator error", super.getError());
	}         
	
	public double clampInches(double posInInches) {
		if(posInInches > MAX_INCH) posInInches = MAX_INCH;
		if (posInInches < MIN_INCH) posInInches = MIN_INCH;
		return posInInches;
	}
	
	public double convertAngle(double angle) {
		if(angle > MAX_ANGLE) angle = MAX_ANGLE;
		if(angle <MAX_ANGLE) angle = MIN_ANGLE;
		double inches = angle * (MAX_INCH-MIN_INCH)/(MAX_ANGLE-MIN_ANGLE);
		return inches;
		
	}
	
	

}
