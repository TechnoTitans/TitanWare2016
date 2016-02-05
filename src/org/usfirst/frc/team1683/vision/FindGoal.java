package org.usfirst.frc.team1683.vision;
import org.usfirst.frc.team1683.vision.Contour;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
public class FindGoal {
	public static NetworkTable tableContour;
	private double[] GOAL_X, GOAL_Y, HEIGHT, WIDTH, AREA;
	private double distance;
	private double[] defaultvalue = null;
	private double defaultdouble=0;
	private int ObjectNumber=0;
	private double optic_angle;
	private double FOVpx;
	private double Targetft;
	/*
	 * go to https://wpilib.screenstepslive.com/s/4485/m/24194/l/288985-identifying-and-processing-the-targets
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
	public double FindDistance(double width){
		this.distance=Targetft*FOVpx/(2*width*Math.tan(optic_angle));
		return distance;
	}
}
