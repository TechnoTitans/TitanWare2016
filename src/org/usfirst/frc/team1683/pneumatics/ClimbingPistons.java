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
	Solenoid chinUpDeploy;
	Solenoid chinUpRetract;
	public static State presentState;
	public static State nextState;

	public static final double LIFT_ANGLE = 45;
	public static final double RETRACT_ANGLE = 0;
	public static final double CHINUP_TIME = 1;
	public static final double END_GAME_TIME = 135-27;

	public static enum State {
		INIT_CASE, END_CASE, LIFT_HOOK, START_CHINUP, ROBOT_CHINUP, LOWER_HOOK
	}

	public ClimbingPistons(int chinUpDeployChannel, int chinUpRetractChannel) {
		presentState = State.INIT_CASE;
		chinUpTimer = new Timer();
		actuator = new LinearActuator(HWR.LINEAR_ACTUATOR, false);
		chinUpDeploy = new Solenoid(HWR.DEFAULT_MODULE_CHANNEL, chinUpDeployChannel);
		chinUpRetract = new Solenoid(HWR.DEFAULT_MODULE_CHANNEL, chinUpRetractChannel);
		actuator.PIDinit();

	}

	public void deployChinUp() {
		chinUpDeploy.extend();
		chinUpRetract.extend();
	}

	public void retractChinUp() {
		chinUpRetract.retract();
		chinUpDeploy.retract();
	}

	public void climbMode() {

		actuator.angleClimberPistons();

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
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_4)
					&& Timer.getMatchTime() > END_GAME_TIME) {
				nextState = State.LIFT_HOOK;
			}
			break;
		}
		case LIFT_HOOK: {
			this.deployChinUp();
			chinUpTimer.reset();
			SmartDashboard.sendData("State", "lift_hook");
			nextState = State.LIFT_HOOK;
			
			if (DriverStation.leftStick.getRawButton(HWP.BUTTON_1)
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_1))
				nextState = State.START_CHINUP;
		}
		case START_CHINUP: {
			this.deployChinUp();
			SmartDashboard.sendData("State", "start chinup");
			nextState = State.START_CHINUP;
			
			if(!(DriverStation.leftStick.getRawButton(HWP.BUTTON_1)
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_1))) 
				nextState = State.LIFT_HOOK;
			if(chinUpTimer.hasPeriodPassed(CHINUP_TIME)) 
				nextState = State.ROBOT_CHINUP;
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
