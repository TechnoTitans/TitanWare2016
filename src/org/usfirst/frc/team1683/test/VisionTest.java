package org.usfirst.frc.team1683.test;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.vision.FindGoal;
import org.usfirst.frc.team1683.vision.Contour;

public class VisionTest{
	FindGoal vision;
	Contour[] contours;
	
	public VisionTest() {
		vision = new FindGoal();
	}
	
	public void test(){
		contours = vision.getData();
		SmartDashboard.sendData("DistanceTarget", vision.FindDistance());
	}
}