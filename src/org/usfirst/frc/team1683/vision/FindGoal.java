package org.usfirst.frc.team1683.vision;
import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.vision.Contour;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
public class FindGoal {
	public static NetworkTable tableContour;
	private double[] GOAL_X, GOAL_Y, HEIGHT, WIDTH, AREA;
	private double focal = (131 * 33) / 14;
	private double distance;
	private double[] defaultvalue = null;
	private double defaultdouble=0;
	private int ObjectNumber=0;
	private double optic_angle;
	private double FOVf;
	private double FOVp;
	/*
	 * TODO Need to find following values private final double HeightOffGround;
	 * private final double HeightTower; private final double HeightTarget;
	 * private final double NodalDistance; private final double FocalLength;
	 */
	public FindGoal(){
		tableContour=NetworkTable.getTable("GRIP/myContoursReport");
	}
	public Contour[] getData() {
		ObjectNumber 	= (int) FindGoal.tableContour.getNumber("ObjectNumber",defaultdouble);
		Contour[] contours = new Contour[ObjectNumber];
		try {
			AREA =FindGoal.tableContour.getNumberArray("areas", defaultvalue);
			GOAL_X = FindGoal.tableContour.getNumberArray("centerX", defaultvalue);
			GOAL_Y = FindGoal.tableContour.getNumberArray("centerY", defaultvalue);
			WIDTH = FindGoal.tableContour.getNumberArray("width", defaultvalue);
			HEIGHT = FindGoal.tableContour.getNumberArray("height", defaultvalue);
		}
		catch(TableKeyNotDefinedException exp) {
			System.out.println("TableKeyNotDefinedException");
		}
		for (int i = 0; i < contours.length; i++) {
			contours[i] = new Contour(i, AREA[i], WIDTH[i], HEIGHT[i], GOAL_X[i], GOAL_Y[i]);
		}
		
		return contours;
	}
	public double FindDistance(double area,double focal,double defaultarea){
		
	}
}
