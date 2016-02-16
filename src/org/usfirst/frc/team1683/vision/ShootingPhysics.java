package org.usfirst.frc.team1683.vision;
public class ShootingPhysics {
	double height;//height between robot and shooter
	double length;//distance between robot and shooter
	final double radius=0.0492125;
	final double powerincrease=0;//TODO
	public ShootingPhysics(double height,double length){
		this.height=height;
		this.length=length;
	}
	public double findSpeedX(){
		return(Math.sqrt(2*9.81*this.height));
	}
	public double findSpeedY(){
		return(this.length/(Math.sqrt(this.height*2/9.81)));
	}
	public double FindSpinSpeed(){
		return(Math.sqrt(Math.pow(2,(findSpeedX()))+Math.pow(2,(findSpeedY())))*powerincrease/(radius*0.10472));//returns in RPM
	}
	public double FindAngle(){
		return(Math.tan(findSpeedX()/findSpeedY()));//radians
	}
}
