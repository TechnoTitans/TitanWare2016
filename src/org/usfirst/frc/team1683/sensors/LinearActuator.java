package org.usfirst.frc.team1683.sensors;

import org.usfirst.frc.team1683.driveTrain.TalonSRX;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;


public class LinearActuator extends TalonSRX {

	public static final double ROT_PER_INCH = 8.76 / 12;
	public static final double COUNTS_PER_INCH = 820/10.9375;
	public static final int POT_TURN_NUM = 10;
	public static final double MAX_ANGLE = 120;
	public static final double MIN_ANGLE = 0;
	public static final double L_ACT_MAX = 12; // fix number
	public static final double L_ACT_MIN = 0; // fix number
	public static final double L_BASE = 23.37;
	public static final double L_PIVOT = 5.02;
	public static final double L_FIXED = 18.38;
	public static final double ANGLE_OFFSET = 20;
	public static final int ALLOWABLE_ERROR = 4;
	

	public LinearActuator(int deviceNumber, boolean reversed) {
		super(deviceNumber, reversed);
		// TODO Auto-generated constructor stub
		PIDinit();
	}

	public void PIDinit() {
		// boolean isPresent;
		// if(super.isSensorPresent(FeedbackDevice.AnalogPot).equals(FeedbackDeviceStatus.FeedbackStatusNotPresent))
		// {
		// isPresent = false;
		// }
		// else isPresent = true;
		// SmartDashboard.sendData("Linear Actuator present ", isPresent);
		super.setFeedbackDevice(FeedbackDevice.AnalogPot);
		super.setInverted(false);
		super.changeControlMode(TalonControlMode.Position);
		super.enableBrakeMode(true);
		super.setAllowableClosedLoopErr(ALLOWABLE_ERROR);
		//super.configPotentiometerTurns(POT_TURN_NUM);
		super.enable();

	}

	public void PIDupdate(double posInInches) {
		super.setPID(SmartDashboard.getDouble("Linear Actuator P"), SmartDashboard.getDouble("Linear Actuator I"),
				SmartDashboard.getDouble("Linear Actuator D"));
		
		super.PIDPosition(clampInches(posInInches)* COUNTS_PER_INCH + 62); // conversion to
															// rotations

		SmartDashboard.sendData("Talon " + super.getDeviceID(), "Position(inches)" + posInInches);
		SmartDashboard.sendData("Talon " + super.getDeviceID(), "Position(rotations)" + super.getPosition());

		SmartDashboard.sendData("Linear Actuator error", super.getError());
	}

	public double clampInches(double posInInches) {
		if (posInInches > L_ACT_MAX)
			posInInches = L_ACT_MAX;
		if (posInInches < L_ACT_MIN)
			posInInches = L_ACT_MIN;
		return posInInches;
	}
	/**
	 * 
	 * @param angle
	 * @return inches
	 * 
	 * Converts angle (degrees) to  linear actuator length (inches)
	 */
	public double convertToInch(double angle) {
		
		angle -= ANGLE_OFFSET;
		
		if (angle > MAX_ANGLE)
			angle = MAX_ANGLE;
		if (angle < MIN_ANGLE)
			angle = MIN_ANGLE;
	//	double inches = angle * (MAX_INCH - MIN_INCH) / (MAX_ANGLE - MIN_ANGLE);
		
		//Using law of cosines on climber geometry
		//c^2 = a^2 + b^2 - 2*a*b*cos(angle)
		double inches =  Math.sqrt(Math.pow(L_BASE, 2) + Math.pow(L_PIVOT, 2) - 2*L_BASE*L_PIVOT*Math.cos(angle*Math.PI/180));
		//subtracting fixed length
		inches -= L_FIXED;
		SmartDashboard.sendData("Linear actuator inches", inches);
		return inches;
	}
	
	/**
	 * 
	 * @param inch
	 * @return angle
	 * Converts inches (moving) into angles (degrees)
	 */
	public double convertToAngle(double inch) {
		
		inch = clampInches(inch);
		inch += L_FIXED;
		double angle = Math.acos((Math.pow(L_BASE, 2) + Math.pow(L_PIVOT, 2)-Math.pow(inch, 2))/(2*L_BASE*L_PIVOT))*180/Math.PI;
		angle += ANGLE_OFFSET;
		SmartDashboard.sendData("Linear actuator Angle", angle);
		return angle;
	}
	
	public void angleClimberPistons() {
		// scales raw input from knob
		double angle = -60 * (DriverStation.auxStick.getRawAxis(DriverStation.ZAxis) - 1);
		SmartDashboard.sendData("Knob angle", angle);
		SmartDashboard.sendData("knob raw", DriverStation.auxStick.getRawAxis(DriverStation.ZAxis));
		PIDupdate(convertToInch(angle));
	}
	
	public void angleClimberPistons(double angle) {
		PIDupdate(convertToInch(angle));
	}
	
	
	public double getPos() {
		return super.getEncPosition()*10.9375/820;
	}
	
	

}
