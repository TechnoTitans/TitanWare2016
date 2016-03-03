package org.usfirst.frc.team1683.pneumatics;

import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.robot.HWP;
import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.sensors.LinearActuator;

import edu.wpi.first.wpilibj.Timer;

public class ClimbingPistons {
	Timer chinUpTimer;
	LinearActuator actuator;
	Piston chinUpDeploy;
	Piston chinUpRetract;
	public static State presentState;
	public static State nextState;

	public static final double LIFT_ANGLE = 45;
	public static final double RETRACT_ANGLE = 0;
	public static final double CHINUP_TIME = 1;

	public static enum State {
		INIT_CASE, END_CASE, LIFT_HOOK, ROBOT_CHINUP, LOWER_HOOK
	}

	public ClimbingPistons(int chinUpDeployChannel, int chinUpRetractChannel) {
		presentState = State.INIT_CASE;
		actuator = new LinearActuator(HWR.LINEAR_ACTUATOR);
		chinUpDeploy = new Piston(HWR.DEFAULT_MODULE_CHANNEL, chinUpDeployChannel);
		chinUpRetract = new Piston(HWR.DEFAULT_MODULE_CHANNEL, chinUpRetractChannel);
		actuator.PIDinit();

	}

	public void deployChinUp() {
		chinUpDeploy.extend();
		chinUpRetract.retract();
	}

	public void retractChinUp() {
		chinUpRetract.extend();
		chinUpDeploy.retract();
	}

	public void test() {

		switch (presentState) {
		case INIT_CASE: {
			nextState = State.LOWER_HOOK;
			break;
		}
		case LOWER_HOOK: {
			this.retractChinUp();
			SmartDashboard.sendData("State", "lower hook");
			nextState = State.LOWER_HOOK;
			if (DriverStation.leftStick.getRawButton(HWP.BUTTON_4)
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_4)) {
				nextState = State.LIFT_HOOK;
			}
		}
		case LIFT_HOOK: {
			this.deployChinUp();
			SmartDashboard.sendData("State", "lift_hook");
			nextState = State.LIFT_HOOK;

			if (DriverStation.leftStick.getRawButton(HWP.BUTTON_5)
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_5))
				nextState = State.LOWER_HOOK;

			else if (DriverStation.leftStick.getRawButton(HWP.BUTTON_1)
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_1))
				chinUpTimer.reset();
			if (chinUpTimer.hasPeriodPassed(CHINUP_TIME))
				nextState = State.ROBOT_CHINUP;
			break;
		}
		case ROBOT_CHINUP: {
			this.retractChinUp();
			SmartDashboard.sendData("State", "chinUp");
			nextState = State.ROBOT_CHINUP;
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
