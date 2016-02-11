package org.usfirst.frc.team1683.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
public class PressureReader implements Sensor {
	private final double MAX_VOLTAGE = 4.5;
	private final double MIN_VOLTAGE = 0.5;
	private final double MIN_PRESSURE= 0;
	private final double MAX_PRESSURE = 200;
	private double averageVoltage;
	private double portNumber;
	AnalogInput sensor;
	private double pressure;
	private final double VOLTAGE_RANGE= MAX_VOLTAGE-MIN_VOLTAGE;
	private final double PRESSURE_SLOPE= MAX_PRESSURE/VOLTAGE_RANGE;

	public PressureReader(int portNumber) {
		this.portNumber = portNumber;
		sensor = new AnalogInput(portNumber);
	}
// gets port number and returns it
	public double getPortNumber() {
		return portNumber;
	}
// gets voltage and returns average as an Analog Value
	public double getRaw() {
		return sensor.getAverageVoltage();

	}

	public double getPressure() {
		averageVoltage= sensor.getAverageVoltage();
		if( averageVoltage > MAX_VOLTAGE ){
			pressure=MAX_PRESSURE;
		}
		else if (averageVoltage < MIN_VOLTAGE){
			pressure=MIN_PRESSURE;
		}
		else {
			pressure= PRESSURE_SLOPE*(averageVoltage-MIN_VOLTAGE);
		}
		
		return pressure;
		
	}
}
