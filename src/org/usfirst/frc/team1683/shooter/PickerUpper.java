package org.usfirst.frc.team1683.shooter;

import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.robot.HWR;

public class PickerUpper {
	public static final double INTAKE_SPEED = 0.2; // tested value
	// Uses moveDistance to move rollers for amount of time needed to move this
	// variable's distance.
	public static final double INTAKE_DURATION = 10;
	public MotorGroup group;

	public PickerUpper(MotorGroup group) {
		this.group = group;

	}

	public void intakeMode() {

		if (DriverStation.auxStick.getRawButton(HWR.SPIN_UP_INTAKE)) {
			this.group.PIDInit();
			group.PIDSpeed(INTAKE_SPEED);
		} else
			group.stop();

	}

	public void intake() {
		group.moveDistance(-INTAKE_DURATION, INTAKE_SPEED);
	}

}
