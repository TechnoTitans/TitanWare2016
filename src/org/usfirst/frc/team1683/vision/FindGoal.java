package org.usfirst.frc.team1683.vision;

import org.usfirst.frc.team1683.vision.Contour;

import com.ni.vision.NIVision.ContourPoint;

/**
 * Vision methods
 * 
 * @author Yi Liu
 *
 */
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

public class FindGoal {
	public static NetworkTable tableContour;
	private double[] GOAL_X, GOAL_Y, HEIGHT, WIDTH, AREA;
	private double distance = 0;
	private double[] defaultvalue = new double[0];
	private double optic_angle = 28.393 * Math.PI / 180; // M1103 cameras only
	private double optic_angle1 = 0 * Math.PI / 180; // small black camera
	private double FOVpx = 320; // pixels of the grip program
	private double Targetin = 20; // target width
	private final double CENTER_WIDTH_PX = 5;// The max offset
	private final double SHOOTER_HEIGHT = 0;// TODO
	/*
	 * go to
	 * https://wpilib.screenstepslive.com/s/4485/m/24194/l/288985-identifying-
	 * and-processing-the-targets
	 */

	public FindGoal() {
		tableContour = NetworkTable.getTable("GRIP/myContoursReport");
	}

	public Contour[] getData() {
		Contour[] contours;
		try {
			AREA = tableContour.getNumberArray("area", defaultvalue);
			GOAL_X = tableContour.getNumberArray("centerX", defaultvalue);
			GOAL_Y = tableContour.getNumberArray("centerY", defaultvalue);
			WIDTH = tableContour.getNumberArray("width", defaultvalue);
			HEIGHT = tableContour.getNumberArray("height", defaultvalue);
			contours = new Contour[AREA.length];
			if (AREA.length > 0) {
				for (int i = 0; i < contours.length; i++) {
					contours[i] = new Contour(i, HEIGHT[i], WIDTH[i], GOAL_X[i], GOAL_Y[i], AREA[i]);
				}
			}
//			SmartDashboard.sendData("AREA LENGTH", AREA.length);
//			SmartDashboard.sendData("HEIGHT LENGTH", HEIGHT.length);
//			SmartDashboard.sendData("GOAL_X LENGTH", GOAL_X.length);
//			SmartDashboard.sendData("GOAL_Y LENGTH", GOAL_Y.length);
//			SmartDashboard.sendData("WIDTH LENGTH", WIDTH.length);
		} catch (TableKeyNotDefinedException exp) {
			System.out.println("TableKeyNotDefinedExceptionFix");
			// SmartDashboard.sendData("", val);
			contours = null;
		}
		return contours;
	}

	public int ClosestContour(Contour[] contours) {
		int maxarea = 0;
		for (int i = 0; i < contours.length; i++) {
			if (contours[maxarea].AREA > contours[i].AREA) {
				maxarea = i;
			}
		}
		return maxarea;
	}

	/*
	 * checks distance to target not to base of target
	 */
	public double FindDistance() {
		Contour[] contours = getData();
		this.distance = Targetin * FOVpx / (2 * contours[ClosestContour(contours)].WIDTH * Math.tan(optic_angle));
		SmartDashboard.sendData("DistanceTarget", this.distance);
		return this.distance;
	}
//
	/*/
	 * checks if robot is aligned. -1 for too far left. 0 for just right. 1 for
	 * too far right. 2 for error
	 */
	// public double ShooterSpeed(){
	// TODO:Test values for shooter. Plot points on graphical analysis and take
	// derivative.
	// }
	public double FindHeight() {
		Contour[] contours = getData();
		double cameratarget;
		cameratarget = 20 * (contours[ClosestContour(contours)].Y_POS - 160) / contours[ClosestContour(contours)].WIDTH;
		return (cameratarget - SHOOTER_HEIGHT);
	}
	
	public int getOffset() {
		Contour[] contours = getData();
		return (int) (FOVpx / 2 - contours[ClosestContour(contours)].X_POS);
	}

	public boolean isCentered() {
//		Contour[] contours = getData();
		double offset = getOffset();
//		offset = FOVpx / 2 - contours[ClosestContour(contours)].X_POS;
//		SmartDashboard.sendData("Offset", offset);
//		if (offset < -CENTER_WIDTH_PX) {
//			return 1;
//		} else if (offset > CENTER_WIDTH_PX) {
//			return -1;
//		} else if ((offset > -CENTER_WIDTH_PX) && (offset < CENTER_WIDTH_PX)) {
//			return 0;
//		} else {
//			return 2;
//		}
		
		if (Math.abs(offset) < CENTER_WIDTH_PX) 
			return true;
		return false;
	}
}

