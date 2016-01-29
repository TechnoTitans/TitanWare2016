package org.usfirst.frc.team1683.vision;
import org.usfirst.frc.team1683.robot.HWR;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
public class FindGoal {
	public static NetworkTable table;
	public static NetworkTable table2;
	private double[] GOAL_X,GOAL_Y,HEIGHT,WIDTH,AREA;
	double[] XAXIS,YAXIS,XAXIS1,YAXIS1,XVALUES,YVALUES,maxKX2,minKX3,maxKX1,minKX4;
	double[][] edges=new double[4][2];
	private double[] defaultvalue=null;
	public FindGoal(){
		table=NetworkTable.getTable("GRIP/myContoursReport");
		table2=NetworkTable.getTable("GRIP/myLinesReport");
		XAXIS   = FindGoal.table2.getNumberArray("x1",defaultvalue);
		XAXIS1   = FindGoal.table2.getNumberArray("x2",defaultvalue);
		YAXIS   = FindGoal.table2.getNumberArray("y1",defaultvalue);
		YAXIS1   = FindGoal.table2.getNumberArray("y2",defaultvalue);
		XVALUES=MergeArray(XAXIS,XAXIS1);
		YVALUES=MergeArray(YAXIS,YAXIS1);
		edges=FindVertices(XVALUES,YVALUES);
		AREA 	= FindGoal.table.getNumberArray("areas",defaultvalue);
		GOAL_X 	= FindGoal.table.getNumberArray("centerX",defaultvalue);
		GOAL_Y 	= FindGoal.table.getNumberArray("centerY",defaultvalue);
		WIDTH 	= FindGoal.table.getNumberArray("width",defaultvalue);
		HEIGHT 	= FindGoal.table.getNumberArray("height",defaultvalue);
	}
	/*
	 * Merges two arrays in order to combine beginning and end points of lines
	 */
	public double[] MergeArray(double[] a,double[] b){
		int length=a.length+b.length;
		double[] c=new double[length];
		System.arraycopy(a,0,c,0,a.length);
        System.arraycopy(b,0,c,a.length,b.length);
        return c;
	}
	//public double FindDistanceAway(){
		//TODO:find distance
	//}
	/*
	 * FindEdges Take points in the picture and returns coordinates of vertices
	 */
	public double[][] FindVertices(double[] a,double[] b){
		double minKX3=b[0]-a[0],maxKX2=b[0]-a[0],maxKX1=a[0]+b[0],minKX4=a[0]+b[0];
		double edge[][]={{a[0],b[0]},{a[0],b[0]},{a[0],b[0]},{a[0],b[0]}};
		for(int k=0;k<XVALUES.length;k++){
			if(maxKX2<(b[k]-a[k])){
				edges[2][0]=a[k];
				edges[2][1]=b[k];
			}
			if(minKX3>(b[k]-a[k])){
				edges[3][0]=a[k];
				edges[3][1]=b[k];
			}
			if(minKX4>(b[k]+a[k])){
				edges[4][0]=a[k];
				edges[4][1]=b[k];
			}
			if(maxKX1>(b[k]+a[k])){
				edges[1][0]=a[k];
				edges[1][1]=b[k];
			}
		}
		return edge;
	}
}
