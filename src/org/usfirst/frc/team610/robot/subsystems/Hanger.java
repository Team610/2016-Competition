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

	// Stores current position of servo, to access from commands
	public servoPosition ratchetServoPos;
	// Singleton Instance of Hanger
	static Hanger inst;
	// Victors for the two winch motors
	private Victor winch1, winch2;
	
	public boolean isHanging;
	// NavX, to use extra PWM ports
	private NavX navx;
	// Encoder attached to lift
	private Encoder liftEnc;
	// Servo used on the ratchet on the gearbox
	private Servo ratchet;

	public static Hanger getInstance() {
		if (inst == null) {
			inst = new Hanger();
		}
		return inst;
	}

	public enum servoPosition {
		LOCKED, UNLOCKED
	}

	private Hanger() {
		navx = NavX.getInstance();
		isHanging = false;
		liftEnc = new Encoder(ElectricalConstants.ENCODER_LIFT_A, ElectricalConstants.ENCODER_LIFT_B);

		winch1 = new Victor(NavX.getInstance().getChannelFromPin(PinType.PWM, ElectricalConstants.LIFT_WINCHA_NAVX));

		winch2 = new Victor(NavX.getInstance().getChannelFromPin(PinType.PWM, ElectricalConstants.LIFT_WINCHB_NAVX));

		ratchet = new Servo(ElectricalConstants.LIFT_RATCHET);
	}

	public void resetEncoder() {
		liftEnc.reset();
	}

	public void hangingMode(boolean b){
		isHanging = b;
	}
	// GETTERS

	public double getEnc() {
		return liftEnc.get();
	}

	// SETTERS

	// Resets lift Encoder
	public void setWinches(double speed) {
		winch1.set(speed);
		winch2.set(speed);
	}

	public void setRatchet(servoPosition pos) {
		if (pos == servoPosition.LOCKED) {
			ratchet.set(Constants.HANGER_RATCHET_CLOSED);
		} else if (pos == servoPosition.UNLOCKED) {
			ratchet.set(Constants.HANGER_RATCHET_OPEN);
		}
		ratchetServoPos = pos;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
