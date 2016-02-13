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
		if(contours.length!=0){
			SmartDashboard.sendData("DistanceTarget", vision.FindDistance(contours));
			SmartDashboard.sendData("Centered",vision.isCentered(contours));
			SmartDashboard.sendData("YAimPOint",(20*(contours[0].Y_POS+contours[0].HEIGHT/2)/contours[0].WIDTH));
			SmartDashboard.sendData("Closest",vision.ClosestContour(contours));
		}
	}
	//
}