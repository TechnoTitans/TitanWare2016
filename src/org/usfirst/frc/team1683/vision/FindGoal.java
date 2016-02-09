package org.usfirst.frc.team1683.vision;
import org.usfirst.frc.team1683.vision.Contour;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
public class FindGoal {
	public static NetworkTable tableContour;
	private double[] GOAL_X, GOAL_Y, HEIGHT, WIDTH, AREA;
	private double distance;
	private double[] defaultvalue = new double[0];
	private double optic_angle=49; //M1103 cameras only
	private double FOVpx=300; //pixels of the grip program
	private double Targetin=20; //target width
	/*
	 * go to https://wpilib.screenstepslive.com/s/4485/m/24194/l/288985-identifying-and-processing-the-targets
	 */
	public FindGoal(){
		tableContour=NetworkTable.getTable("GRIP/myContoursReport");
	}
	public Contour[] getData() {
		Contour[] contours;
		SmartDashboard.sendData("myTests","Test");
		try {
			AREA =FindGoal.tableContour.getNumberArray("area", defaultvalue);
			GOAL_X = FindGoal.tableContour.getNumberArray("centerX", defaultvalue);
			GOAL_Y = FindGoal.tableContour.getNumberArray("centerY", defaultvalue);
			WIDTH = FindGoal.tableContour.getNumberArray("width", defaultvalue);
			HEIGHT = FindGoal.tableContour.getNumberArray("height", defaultvalue);
			contours = new Contour[AREA.length];
		}
		catch(TableKeyNotDefinedException exp) {
			System.out.println("TableKeyNotDefinedExceptionFix");
			contours = null;
		}
		for (int i = 0; i < contours.length; i++) {
			contours[i] = new Contour(i,HEIGHT[i], WIDTH[i],GOAL_X[i], GOAL_Y[i],AREA[i]);
		}
		
		return contours;
	}
	public int ClosestContour(double[] area){
		int maxarea=0;
		for(int i=0;i<area.length;i++){
			if(area[i]>area[maxarea]){
				maxarea=i;
			}
		}
		return maxarea;
	}
	/*
	 * checks distance to target not to base of target
	 */
	public double FindDistance(){
		Contour[] contours = getData();
		this.distance=Targetin*FOVpx/(2*contours[0].WIDTH*Math.tan(optic_angle));
		SmartDashboard.sendData("DistanceTarget",this.distance);
		return distance;
	}
	/*
	 * 	checks if robot is aligned. -1 for too far left. 0 for just right. 1 for too far right. 2 for error
	 */
	public int isCentered() {
		Contour[] contours = getData();
		double offset=FOVpx-contours[ClosestContour(AREA)].WIDTH;
		if(offset>2){
			return 1;
		}
		else if(offset<-2){
			return -1;
		}
		else if((offset<-2)&&(offset>2)){
			return 0;
		}
		else{
			return 2;
		}
	}
	
}
