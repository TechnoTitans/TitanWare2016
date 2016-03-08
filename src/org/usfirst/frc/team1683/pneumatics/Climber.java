package org.usfirst.frc.team1683.pneumatics;

import org.usfirst.frc.team1683.driverStation.DriverStation;
import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.robot.HWP;
import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.sensors.LinearActuator;

import edu.wpi.first.wpilibj.Timer;

public class Climber {
	Timer chinUpTimer;
	LinearActuator actuator;
	Solenoid bottomSolenoid;
	Solenoid topSolenoid;
	public static State presentState;
	public static State nextState;

	public static final double LIFT_ANGLE = 45;
	public static final double RETRACT_ANGLE = 0;
	public static final double CHINUP_TIME = 1;
	public static final double END_GAME_TIME = 135 - 27;

	public static final boolean EXTEND = true;
	public static final boolean RETRACT = false;

	public static enum State {
		INIT_CASE, END_CASE, LIFT_HOOK, START_CHINUP, ROBOT_CHINUP, LOWER_HOOK
	}

	public Climber(int bottomSolenoidChannel, int topSolenoidChannel) {
		presentState = State.INIT_CASE;
		chinUpTimer = new Timer();

		topSolenoid = new Solenoid(HWR.DEFAULT_MODULE_CHANNEL, topSolenoidChannel);
		bottomSolenoid = new Solenoid(HWR.DEFAULT_MODULE_CHANNEL, bottomSolenoidChannel);

		actuator = new LinearActuator(HWR.LINEAR_ACTUATOR, false);
		actuator.PIDinit();

	}

	public void deployHook() {
		bottomSolenoid.set(EXTEND);
		topSolenoid.set(EXTEND);
	}

	public void retractHook() {
		topSolenoid.set(RETRACT);
		bottomSolenoid.set(RETRACT);
	}

	public void climbMode() {

		actuator.angleClimberPistons();

		switch (presentState) {
		case INIT_CASE: {
			nextState = State.LOWER_HOOK;
			break;
		}
		case LOWER_HOOK: {
			this.retractHook();
			if (DriverStation.leftStick.getRawButton(HWP.BUTTON_4)
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_4) && Timer.getMatchTime() > END_GAME_TIME) {
				nextState = State.LIFT_HOOK;
			}
			nextState = State.LOWER_HOOK;
			break;
		}
		case LIFT_HOOK: {
			this.deployHook();
			chinUpTimer.reset();

			if (DriverStation.leftStick.getRawButton(HWP.BUTTON_1)
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_1)) {
				nextState = State.START_CHINUP;
			}

			nextState = State.LIFT_HOOK;
			break;
		}
		case START_CHINUP: {
			this.deployHook();

			if (!(DriverStation.leftStick.getRawButton(HWP.BUTTON_1)
					&& DriverStation.rightStick.getRawButton(HWP.BUTTON_1)))
				nextState = State.LIFT_HOOK;
			if (chinUpTimer.hasPeriodPassed(CHINUP_TIME))
				nextState = State.ROBOT_CHINUP;

			nextState = State.START_CHINUP;
			break;
		}
		case ROBOT_CHINUP: {
			this.retractHook();
			nextState = State.ROBOT_CHINUP;
			break;
		}
		case END_CASE: {

			nextState = State.END_CASE;
			break;
		}
		default:
			nextState = State.END_CASE;
			break;

		}
		SmartDashboard.sendData("Present Climber State", presentState.name());
		presentState = nextState;

	}

}
