package org.usfirst.frc.team610.robot.subsystems;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class NavX extends Subsystem {
	static NavX inst;
	static IMUAdvanced navx;
	static SerialPort serial_port;
	public final int MAX_NAVX_MXP_DIGIO_PIN_NUMBER = 9;
	public final int NUM_ROBORIO_ONBOARD_DIGIO_PINS = 10;
	public final int NUM_ROBORIO_ONBOARD_PWM_PINS = 10;

	//Get static instance of NavX
	public static NavX getInstance() {
		if (inst == null) {

			inst = new NavX();
		}
		return inst;
	}

	private NavX() {
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kMXP);
			byte update_rate_hz = 50;
			navx = new IMUAdvanced(serial_port, update_rate_hz);
		} catch (Exception ex) {
			DriverStation.reportError("NavX Failed to Initilaized", true);
		}
	}

	public double getAngle() {
		return navx.getYaw();

	}

	
	public double getPitch() {
		return navx.getPitch();
	}

	//Reset navX yaw
	public void resetAngle() {
		navx.zeroYaw();
	}
	

	public enum PinType {
		DigitalIO, PWM
	};

	public int getChannelFromPin(PinType type, int io_pin_number) throws IllegalArgumentException {
		int roborio_channel = 0;
		if (io_pin_number < 0) {
			throw new IllegalArgumentException("Error:  navX-MXP I/O Pin #");
		}
		switch (type) {
		case DigitalIO:
			if (io_pin_number > MAX_NAVX_MXP_DIGIO_PIN_NUMBER) {
				throw new IllegalArgumentException("Error:  Invalid navX-MXP Digital I/O Pin #");
			}
			roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_DIGIO_PINS + (io_pin_number > 3 ? 4 : 0);
			break;
		case PWM:
			if (io_pin_number > MAX_NAVX_MXP_DIGIO_PIN_NUMBER) {
				throw new IllegalArgumentException("Error:  Invalid navX-MXP Digital I/O Pin #");
			}
			roborio_channel = io_pin_number + NUM_ROBORIO_ONBOARD_PWM_PINS;
			break;

		}
		return roborio_channel;
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
