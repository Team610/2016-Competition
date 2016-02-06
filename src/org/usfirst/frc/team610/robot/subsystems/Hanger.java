package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.subsystems.NavX.PinType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * None of this is tested
 */
public class Hanger extends Subsystem {

	static Hanger inst;
	Victor winch1, winch2;
	NavX navx;
	Encoder liftEnc;
	Servo ratchet;
	public servoPosition servoPos;

	public static Hanger getInstance() {
		if (inst == null) {
			inst = new Hanger();
		}
		return inst;
	}

	private Hanger() {
		navx = NavX.getInstance();
		/*
		 * temp
		 */
		liftEnc = new Encoder(ElectricalConstants.ENCODER_LIFT_A,ElectricalConstants.ENCODER_LIFT_B);
		winch1 = new Victor(NavX.getInstance().getChannelFromPin(PinType.PWM, ElectricalConstants.LIFT_WINCHA_NAVX));
		winch2 = new Victor(NavX.getInstance().getChannelFromPin(PinType.PWM, ElectricalConstants.LIFT_WINCHB_NAVX));
		ratchet = new Servo(ElectricalConstants.LIFT_RATCHET);
	}
	
	
	public void resetEncoder(){
		liftEnc.reset();
	}

	public void setWinches(double speed) {
		winch1.set(speed);
		winch2.set(speed);
	}
	public double getEnc(){
		return liftEnc.get();
	}

	/* @param 0-1
	 * 
	 */
	
	public enum servoPosition{
		LOCKED,UNLOCKED
	}
	public void setRatchet(servoPosition pos) {
		if(pos == servoPosition.LOCKED){
			ratchet.set(Constants.HANGER_RATCHET_CLOSED);
		}else if(pos == servoPosition.UNLOCKED){
			ratchet.set(Constants.HANGER_RATCHET_OPEN);
		}
		servoPos = pos;
	}

	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
