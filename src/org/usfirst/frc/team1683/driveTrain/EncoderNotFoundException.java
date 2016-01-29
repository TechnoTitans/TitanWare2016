package org.usfirst.frc.team1683.driveTrain;

/**
 * Exception thrown when encoder not found.
 * @author David Luo
 *
 */
public class EncoderNotFoundException extends Exception {
	public EncoderNotFoundException() {
		super();
	}

	public EncoderNotFoundException(String msg) {
		super(msg);
	}
}
