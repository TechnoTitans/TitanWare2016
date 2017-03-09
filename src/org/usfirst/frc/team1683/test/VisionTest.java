package org.usfirst.frc.team1683.test;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.vision.Contour;
import org.usfirst.frc.team1683.vision.FindGoal;

public class VisionTest {
	FindGoal vision;
	Contour[] contours;

	public VisionTest() {
		vision = new FindGoal();
	}

	public void test() {
		SmartDashboard.putNumber("Filtered Distance Value", vision.getFilteredDistance());
		SmartDashboard.sendData("DistanceTarget", vision.getDistance());
		SmartDashboard.sendData("Centered", vision.isCentered());
		SmartDashboard.sendData("offset", vision.getOffset());
	}
}