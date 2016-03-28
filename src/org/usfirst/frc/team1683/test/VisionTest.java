package org.usfirst.frc.team1683.test;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.vision.FindGoal;
import org.usfirst.frc.team1683.vision.Contour;

public class VisionTest {
	FindGoal vision;
	Contour[] contours;

	public VisionTest() {
		vision = new FindGoal();
	}

	public void test() {
//		contours = vision.getData();//
//		if (contours.length > 0) {
//			SmartDashboard.sendData("DistanceTarget", vision.FindDistance());
//			SmartDashboard.sendData("Centered", vision.isCentered());
//			SmartDashboard.sendData("YAimPoint",
//					(20 * (contours[0].Y_POS + contours[0].HEIGHT / 2) / contours[0].WIDTH));
//			SmartDashboard.sendData("Closest", vision.ClosestContour(contours));
//			SmartDashboard.sendData("HeightTarget", vision.FindHeight());
//			
//		}
	}
}