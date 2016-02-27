package org.usfirst.frc.team1683.driveTrain;

import java.util.ArrayList;

import javax.swing.text.Position;

import org.usfirst.frc.team1683.driverStation.SmartDashboard;
import org.usfirst.frc.team1683.sensors.Encoder;

import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/**
 * Represents a group of motors that move together, tied to the same gearsbox
 * and using the same encoder.
 * 
 * @author David Luo
 *
 */
public class MotorGroup {

	// This variable was required for some reason.
	private static final long serialVersionUID = 1L;
	private Encoder encoder;
	private Thread thread;

	private ArrayList<Motor> motors;
	private AntiDrift antiDrift;

	/**
	 * Private class to move motor in separate thread.
	 */
	private class MotorMover implements Runnable {

		private double distance;
		private double speed;
		private MotorGroup motors;

		public MotorMover(MotorGroup group, double distance, double speed) {
			this.motors = group;
			this.distance = distance;
			if (distance < 0) {
				this.speed = -speed;
			} else {
				this.speed = speed;
			}
		}

		@Override
		public void run() {
			encoder.reset();
			// synchronized (this) {
			while (Math.abs(encoder.getDistance()) < Math.abs(distance)) {
				if (isAntiDriftEnabled()) {
					speed = antiDrift.antiDrift(speed, motors);
					motors.set(speed);
				} else {
					motors.set(speed);
				}

				SmartDashboard.sendData("EncoderDistance", encoder.getDistance());
				SmartDashboard.sendData("TargetDistance", distance);
			}
			// }
			motors.stop();
			// thread.notifyAll();
			// thread.destroy();
		}

	}

	/**
	 * Constructor
	 * 
	 * @param encoder
	 *            The encoder attached to this MotorGroup
	 * @param motors
	 *            The motors.
	 */
	public MotorGroup(Encoder encoder, Motor... motors) {
		this.encoder = encoder;
		this.motors = new ArrayList<>();
		for (Motor motor : motors) {
			if (motor instanceof TalonSRX) {
				((TalonSRX) motor).setEncoder(encoder);
			}
			this.motors.add(motor);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param motors
	 *            The motors.
	 */
	public MotorGroup(Motor... motors) {
		this.motors = new ArrayList<>();
		for (Motor motor : motors) {
			this.motors.add(motor);
		}
	}

	/**
	 * Move distance in inches.
	 * 
	 * @param distance
	 *            Distance in inches.
	 * @throws EncoderNotFoundException
	 *             If encoder is not found.
	 * 
	 */
	public void moveDistance(double distance) throws EncoderNotFoundException {
		moveDistance(distance, Motor.MID_SPEED);
	}

	/**
	 * Move distance in inches.
	 * 
	 * @param distance
	 *            Distance in inches.
	 * @throws EncoderNotFoundException
	 *             If encoder not found.
	 */
	// TODO: make this linear instead of rotations
	public void moveDistance(double distance, double speed) throws EncoderNotFoundException {
//		if (hasEncoder()) {
//			// for (Motor motor : this) {
//			// motor.moveDistance(distance, speed);++
//			// }
//			// if (thread == null ||
//			// thread.getState().equals(Thread.State.TERMINATED)) {
//			SmartDashboard.sendData("Thread State", thread.getState().name());
//			thread = new Thread(new MotorMover(this, distance, speed));
//			SmartDashboard.sendData("Thread State", thread.getState().name());
//			thread.start();
//			// }
//			SmartDashboard.sendData("Thread State", thread.getState().name());
//			// if (thread.getState().equals(Thread.State.NEW)) {
//			// thread.start();
//			// }
//		} else {
//			throw new EncoderNotFoundException();
//		}
		for (Motor motor : motors) {
			motor.moveDistance(distance, speed);
		}

	}

	// public void moveDistancePeriodic(double distance, double speed) throws
	// EncoderNotFoundException {
	// if (hasEncoder()) {
	// if
	// }
	// }

	/**
	 * Set collective speed of motors.
	 * 
	 * @param speed
	 *            Speed from 0 to 1.
	 */
	public void set(double speed) {
		for (Motor motor : this.motors) {
			// TODO
			// if (motor instanceof TalonSRX) {
			// ((TalonSRX)
			// motor).changeControlMode(TalonControlMode.PercentVbus);
			((TalonSRX) motor).set(speed);
			// }
		}
	}

	/**
	 * Gets collective speed of motors
	 */
	public double getSpeed() {
		double speed = 0;
		for (Motor motor : this.motors) {
			speed += motor.get();
		}
		return speed / this.motors.size();
	}

	// BEGIN SHOOTER ONLY METHODS!!! #BADCODE
	public void PIDInit() {
		for (Motor m : this.motors) {
			if (m instanceof TalonSRX) {
				if (((TalonSRX) m).isSensorPresent(FeedbackDevice.PulseWidth)
						.equals(FeedbackDeviceStatus.FeedbackStatusPresent)) {
					((TalonSRX) m).enableBrakeMode(false);
					((TalonSRX) m).setPosition(0);
				}
			}
		}
	}

	private void PIDUpdate() {
		for (Motor m : this.motors) {
			if (m instanceof TalonSRX) {
				// ((TalonSRX)
				// m).setVoltageRampRate(SmartDashboard.getDouble("RampRate"));
				((TalonSRX) m).setPID(SmartDashboard.getDouble("Shooter P"), SmartDashboard.getDouble("Shooter I"),
						SmartDashboard.getDouble("Shooter D"));
				((TalonSRX) m).setF(SmartDashboard.getDouble("Shooter F"));
				((TalonSRX) m).enableControl();

			}
		}
	}

	public void PIDSpeed(double RPM) {
		PIDUpdate();
		for (Motor m : this.motors) {
			if (m instanceof TalonSRX) {
				((TalonSRX) m).changeControlMode(TalonControlMode.Speed);
				m.set(RPM);
				SmartDashboard.sendData("Shooter Talon " + m.getChannel() + " Speed", ((TalonSRX) m).getSpeed());
			}
		}
	}
	// END BAD CODE

	/**
	 * Stops group.
	 */
	public void stop() {
		for (Motor motor : this.motors) {
			motor.stop();
		}
	}

	/**
	 * @return If there is an encoder associated with the group.
	 */
	public boolean hasEncoder() {
		return !(encoder == null);
	}
	
	public double getError() {
		double error = 0;
		for(Motor motor : this.motors) {
			error += ((TalonSRX)motor).getError();
		}
		error /= 2;
		return error;
	}

	/**
	 * @return The encoder associated with the group.
	 */
	public Encoder getEncoder() {
		return encoder;
	}

	public void enableBrakeMode(boolean enabled) {
		for (Motor motor : this.motors) {
			if (motor instanceof TalonSRX) {
				((TalonSRX) motor).enableBrakeMode(enabled);
			}
		}
	}

	// /**
	// * @return The motors in the group.
	// */
	// public Motor getMotors() {
	// return motors;
	// }

	// probably want to find a better way to use antidrift than this way.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	public void enableAntiDrift(AntiDrift antiDrift) {
		this.antiDrift = antiDrift;
	}

	public void disableAntiDrift() {
		this.antiDrift = null;
	}

	public boolean isAntiDriftEnabled() {
		return !(antiDrift == null);
	}

	public Motor get(int index) {
		return this.motors.get(index);
	}
}
