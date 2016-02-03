package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * None of this is tested
 */
public class Hanger extends Subsystem {

	static Hanger inst;
	Victor winch1, winch2;
	Servo ratchet;

	public static Hanger getInstance() {
		if (inst == null) {
			inst = new Hanger();
		}
		return inst;
	}

	private Hanger() {		
		ratchet = new Servo(ElectricalConstants.LIFT_RATCHET);
	}
	
	
	public void setWinches(double speed) {
		winch1.set(speed);
		winch2.set(speed);
	}
	
	public void setRatchet(double value) {
		ratchet.set(value);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
