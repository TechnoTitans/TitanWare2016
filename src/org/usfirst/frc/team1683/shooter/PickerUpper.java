package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.robot.HWR;

public class PickerUpper {
	public static final double INTAKE_SPEED = 0.25; // need to choose best
	public MotorGroup group;

	public PickerUpper(MotorGroup group) {
		this.group = group;

	}

	public void intake() {

		SmartDashboard.putBoolean(" Intake Button 5",
				DriverStation.antiBounce(HWR.AUX_JOYSTICK, HWR.START_INTAKE_BALL));

		// if (DriverStation.antiBounce(HWR.AUX_JOYSTICK, HWP.BUTTON_5)) {
		// group.set(INTAKE_SPEED);
		// } else if (DriverStation.antiBounce(HWR.AUX_JOYSTICK, HWP.BUTTON_6))
		// group.stop();

		if (DriverStation.antiBounce(HWR.AUX_JOYSTICK, HWR.START_INTAKE_BALL)) {
			group.set(INTAKE_SPEED);
		} else if (DriverStation.antiBounce(HWR.AUX_JOYSTICK, HWR.START_INTAKE_BALL)) {
			group.stop();
		}

	}

}
