package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.sensors.Gyro;

public class Antidrift {
	
	private final int antidriftangle = 0;
	private final double kp;
	private Gyro gyro;
	
	MotorGroup left;
	MotorGroup right;
	
	public Antidrift(MotorGroup left, MotorGroup right, Gyro gyro, double kp) {
		this.left = left;
		this.right = right;
		this.kp = kp;
		this.gyro = gyro;
	}
	
	public double antiDrift(double speed, MotorGroup motors){
		double error = antidriftangle - gyro.getAngle();
		double correction = kp*error/2.0;
		if (left.equals(motors)){
			//TODO:make sure motors are spinning the correct direction
			double leftSpeed = limitSpeed(speed+correction); //motors need to be spinning the correct direction
			return leftSpeed;
		}else if (right.equals(motors)){
			double rightSpeed = limitSpeed(speed-correction);//motors need to be spinning the correct direction
			return rightSpeed;
		}else{
			return speed;
		}
		
	}
	
	public static double limitSpeed(double speed){
		if (speed>1.0){
			return 1.0;
		}
		else if (speed<-1.0){
			return -1.0;
		}
		else{
			return speed;
		}
	}
}
