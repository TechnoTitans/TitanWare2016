package org.usfirst.frc.team1683.pneumatics;

import org.usfirst.frc.team1683.robot.HWR;

public class ClimbingPistons {
	Piston anglePiston;
	Piston chinUpDeploy;
	Piston chinUpRetract;
	
	public ClimbingPistons(int angleChannel, int chinUpDeployChannel, int chinUpRetractChannel) {
		anglePiston = new Piston(HWR.DEFAULT_MODULE_CHANNEL, angleChannel);
		chinUpDeploy = new Piston(HWR.DEFAULT_MODULE_CHANNEL, chinUpDeployChannel);
		chinUpRetract = new Piston(HWR.DEFAULT_MODULE_CHANNEL, chinUpRetractChannel);
		
	}
	
	public void deployAnglePiston() {
		anglePiston.extend();
	}
	
	public void deployChinUp() {
		chinUpDeploy.extend();
	}
	
	public void retractAngle() {
		anglePiston.retract();
	}
	
	public void retractChinUp() {
		chinUpRetract.retract();
	}
	

		

}
