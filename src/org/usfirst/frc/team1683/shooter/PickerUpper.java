package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.robot.HWP;
import org.usfirst.frc.team1683.robot.HWR;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;

public class PickerUpper {
	public static final double INTAKE_SPEED = 0.4; // need to choose best
	public MotorGroup group;
	

	public PickerUpper(MotorGroup group) {
		this.group = group;
		
	}

	public void intakeMode() {
		
		if (DriverStation.auxStick.getRawButton(HWR.INTAKE_TOGGLE)) {
			group.PIDSpeed(SmartDashboard.getNumber("Intake Speed"));
		} 
		else group.stop();
		
	}
	
	public void intake() {
		group.moveDistance(-10, INTAKE_SPEED);
	}

}
