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
	private double ShooterAngle=0;//TODO
	private final double CENTERWIDTHPX=5;//The max offset
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
		double offset;
		if(contours.length!=0){
			 offset=FOVpx/2-contours[0].X_POS;
		}
		else{
			 offset=1234567;
		}
		SmartDashboard.sendData("Offset",offset);
		if(offset<-CENTERWIDTHPX){
			return 1;
		}
		else if(offset>CENTERWIDTHPX){
			return -1;
		}
		else if((offset>-CENTERWIDTHPX)&&(offset<CENTERWIDTHPX)){
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
