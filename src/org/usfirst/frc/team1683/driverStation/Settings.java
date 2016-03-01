package org.usfirst.frc.team1683.driverStation;

public class Settings {

	public static double angleMotorP;
	public static double angleMotorI;
	public static double angleMotorD;

	public static double shooterMotorP;
	public static double shooterMotorI;
	public static double shooterMotorD;
	public static double shooterMotorF;

	public static double shooterFilterSensitivity;
	public static double shooterTargetSpeed;

	public static double properDistance;

	public static double antiDriftKp;

	public static void updateSettings() {
		angleMotorP = SmartDashboard.getDouble("Angle Motor P");
		angleMotorI = SmartDashboard.getDouble("Angle Motor I");
		angleMotorD = SmartDashboard.getDouble("Angle Motor D");

		shooterMotorP = SmartDashboard.getDouble("Shooter Motor P");
		shooterMotorI = SmartDashboard.getDouble("Shooter Motor I");
		shooterMotorD = SmartDashboard.getDouble("Shooter Motor D");
		shooterMotorF = SmartDashboard.getDouble("Shooter Motor F");

		shooterFilterSensitivity = SmartDashboard.getDouble("Shooter Filter Sensitivity");
		shooterTargetSpeed = SmartDashboard.getDouble("TeleOp Shooter Target Speed");

		properDistance = SmartDashboard.getDouble("Proper Distance");

		antiDriftKp = SmartDashboard.getDouble("AntiDrift Kp");
	}
}
