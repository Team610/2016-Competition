package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * None of this is tested
 */
public class Hanger extends Subsystem {

	static NavX navx;
	static Hanger inst;
	Servo winch1, winch2;
	

	public static Hanger getInstance() {
		if (inst == null) {
			inst = new Hanger();
		}
		return inst;
	}

	private Hanger() {
		navx = NavX.getInstance();
		winch1 = new Servo(navx.getChannelFromPin(NavX.PinType.PWM, ElectricalConstants.LIFT_WINCHA_NAVX));
		winch2 = new Servo(navx.getChannelFromPin(NavX.PinType.PWM, ElectricalConstants.LIFT_WINCHB_NAVX));

	}
	
	public void setWinches(double value) {
		winch1.set(value);
		winch2.set(value);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
