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
	private double distance=0;
	private double[] defaultvalue = new double[0];
	private double optic_angle=28.393*Math.PI/180; //M1103 cameras only
	private double FOVpx=320; //pixels of the grip program
	private double Targetin=20; //target width
	private final double CENTER_WIDTH_PX=5;//The max offset
	private final double SHOOTER_HEIGHT=0;//TODO
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
		if(AREA.length!=0){
			for (int i = 0; i < contours.length; i++) {
				contours[i] = new Contour(i,HEIGHT[i], WIDTH[i],GOAL_X[i], GOAL_Y[i],AREA[i]);
			}
		}
		return contours;
	}
	public int ClosestContour(Contour[] contours){
		int maxarea=0;
		for(int i=0;i<contours.length;i++){
			if(contours[maxarea].AREA>contours[i].AREA){
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
		if(contours.length!=0){
			this.distance=Targetin*FOVpx/(2*contours[0].WIDTH*Math.tan(optic_angle));
			SmartDashboard.sendData("DistanceTarget",this.distance);
			System.out.println(this.distance);
		}
		else{
			SmartDashboard.sendData("DistanceTarget",1234567);
		}
		return this.distance;
	}
	public double AngleTriangle(double c,double a){
		return (Math.sin(a/c));
	}
	/*
	 * 	checks if robot is aligned. -1 for too far left. 0 for just right. 1 for too far right. 2 for error
	 */
	//public double ShooterSpeed(){
		//TODO:Test values for shooter. Plot points on graphical analysis and take derivative.
	//}
	public double FindHeight(){
		Contour[] contours=getData();
		double cameratarget;
		if(contours.length!=0){
			cameratarget=20*(contours[ClosestContour(contours)].Y_POS-160)/contours[ClosestContour(contours)].WIDTH;
		}
		else{
			cameratarget=1234567;
		}
		return (cameratarget-SHOOTER_HEIGHT);
	}
	public double FindDistanceToTarget(){
		double CameraTargetDistance=Math.sqrt(Math.pow(2,(FindDistance()))-Math.pow(2,(FindHeight())));
		return CameraTargetDistance;
	}
	public int isCentered() {
		Contour[] contours = getData();
		double offset;
		if(contours.length!=0){
			 offset=FOVpx/2-contours[0].X_POS;
		}
		else{
			 offset=1234567;
		}
		SmartDashboard.sendData("Offset",offset);
		if(offset<-CENTER_WIDTH_PX){
			return 1;
		}
		else if(offset>CENTER_WIDTH_PX){
			return -1;
		}
		else if((offset>-CENTER_WIDTH_PX)&&(offset<CENTER_WIDTH_PX)){
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
