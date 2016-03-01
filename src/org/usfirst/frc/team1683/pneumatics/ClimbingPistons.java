package org.usfirst.frc.team1683.pneumatics;

import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.robot.HWP;
import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.sensors.LinearActuator;

public class ClimbingPistons {
	LinearActuator actuator;
	Piston chinUpDeploy;
	Piston chinUpRetract;
	public static State presentState;
	public static State nextState;

	public static final double LIFT_ANGLE = 45;
	public static final double RETRACT_ANGLE = 0;

	public static enum State {
		INIT_CASE, END_CASE, STOWED, DEPLOY, LIFT_HOOK, ROBOT_CHINUP, STOP,
	}

	public ClimbingPistons(int chinUpDeployChannel, int chinUpRetractChannel) {
		presentState = State.INIT_CASE;
		actuator = new LinearActuator(HWR.LINEAR_ACTUATOR);
		chinUpDeploy = new Piston(HWR.DEFAULT_MODULE_CHANNEL, chinUpDeployChannel);
		chinUpRetract = new Piston(HWR.DEFAULT_MODULE_CHANNEL, chinUpRetractChannel);
		actuator.PIDinit();

	}

	public void deployAngle() {
		actuator.PIDupdate(actuator.convertAngle(LIFT_ANGLE));
	}

	public void deployChinUp() {
		chinUpDeploy.extend();
		chinUpRetract.retract();
	}

	public void retractAngle() {
		actuator.PIDupdate(actuator.convertAngle(RETRACT_ANGLE));
	}

	public void retractChinUp() {
		chinUpRetract.extend();
		chinUpDeploy.retract();
	}

	public void test() {

		switch (presentState) {
		case INIT_CASE: {
			nextState = State.STOWED;
			break;
		}

		case STOWED: {
			this.retractAngle();
			this.retractChinUp();
			SmartDashboard.sendData("State", "stowed");
			nextState = State.STOWED;
			if (DriverStation.auxStick.getRawButton(HWP.BUTTON_6))
				nextState = State.DEPLOY;
			break;
		}

		case DEPLOY: {
			this.deployAngle();
			this.retractChinUp();
			SmartDashboard.sendData("State", "deploy");
			nextState = State.DEPLOY;
			if (DriverStation.auxStick.getRawButton(HWP.BUTTON_7))
				nextState = State.LIFT_HOOK;
			break;
		}
		case LIFT_HOOK: {
			this.deployAngle();
			this.deployChinUp();
			SmartDashboard.sendData("State", "lift_hook");
			nextState = State.LIFT_HOOK;
			if (DriverStation.auxStick.getRawButton(HWP.BUTTON_8))
				nextState = State.ROBOT_CHINUP;
			break;
		}
		case ROBOT_CHINUP: {
			this.deployAngle();
			this.retractChinUp();
			SmartDashboard.sendData("State", "chinUp");
			nextState = State.ROBOT_CHINUP;
			if (DriverStation.auxStick.getRawButton(HWP.BUTTON_9))
				nextState = State.END_CASE;
			break;
		}
		case END_CASE: {

			SmartDashboard.sendData("State", "end");
			nextState = State.END_CASE;
			break;
		}
		default:
			SmartDashboard.sendData("State", "illegal");
			presentState = State.END_CASE;
			break;

		}
		presentState = nextState;
	}

}
