package org.usfirst.frc.team1683.vision;
import org.usfirst.frc.team1683.vision.Contour;
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
	private double distance;
	private double[] defaultvalue = new double[0];
	private double optic_angle=49; //M1103 cameras only
	private double FOVpx=300; //pixels of the grip program
	private double Targetin=20; //target width
	private double ShooterAngle=0;//TODO
	private final double ShooterWidthLength=0;//TODO:length of shooter
	private final double ShooterHeightLength=0;//TODO
	private final double TargetHeight=0;//TODO
	private final double CameraHeight=0;//TODO
	private final double ShooterHeight=0;//TODO
	private final double CameraShooterDistance=0;//TODO
	
	private double HeightRobot=0;//TODO:Find distance between robot shooter and target;
	/*
	 * go to https://wpilib.screenstepslive.com/s/4485/m/24194/l/288985-identifying-and-processing-the-targets
	 */
	public FindGoal(){
		tableContour=NetworkTable.getTable("GRIP/myContoursReport");
	}
	public Contour[] getData() {
		Contour[] contours;
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
		this.distance=Targetin*FOVpx/(2*contours[ClosestContour(AREA)].WIDTH*Math.tan(optic_angle));
		SmartDashboard.sendData("DistanceTarget",this.distance);
		return distance;
	}
	public double AngleTriangle(double c,double a){
		return (Math.sin(a/c));
	}
	public void getAngle(double ShooterAngle){
		this.ShooterAngle=ShooterAngle;
	}
	/*
	 * 	checks if robot is aligned. -1 for too far left. 0 for just right. 1 for too far right. 2 for error
	 */
	//2.82
	//public double ShooterSpeed(){
		//TODO:Test values for shooter. Plot points on graphical analysis and take derivative.
	//}
	
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
	//Assisted aiming
	//public double FindAngle(double distances){
		
	//}
}
