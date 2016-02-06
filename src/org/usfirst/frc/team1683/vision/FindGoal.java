package org.usfirst.frc.team1683.vision;
import org.usfirst.frc.team1683.vision.Contour;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
public class FindGoal {
	public static NetworkTable tableContour;
	private double[] GOAL_X, GOAL_Y, HEIGHT, WIDTH, AREA;
	private double distance;
	private double[] defaultvalue = null;
	private double defaultdouble=0;
	private int ObjectNumber=0;
	private double optic_angle=49; //M1103 cameras only
	private double FOVpx=800; //pixels of the grip program
	private double Targetin=20; //target width
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
			contours[i] = new Contour(i,HEIGHT[i], WIDTH[i],GOAL_X[i], GOAL_Y[i],AREA[i]);
		}
		
		return contours;
	}
	public double FindDistance(){
		Contour[] contours = getData();
		// TODO: create method to find closest contour (instead of just getting contour[0])
		this.distance=Targetin*FOVpx/(2*contours[0].WIDTH*Math.tan(optic_angle));
		SmartDashboard.sendData("DistanceTarget",this.distance);
		return distance;
	}
}
