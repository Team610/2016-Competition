package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Hanger;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;
import org.usfirst.frc.team610.robot.subsystems.Hanger.servoPosition;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Hang extends Command {

	private Hanger hanger;
	private Intake intake;
	private OI oi;
	boolean isExtending, readyToPullUp, isXPressed;
	boolean isL1Pressed;
	boolean manual;
	int counter;
	double joyValue;
	double curEnc;
	double lastEnc;
	double encError;

	public T_Hang() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		hanger = Hanger.getInstance();
		intake = Intake.getInstance();
		oi = OI.getInstance();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		isExtending = false;
		readyToPullUp = false;
		isXPressed = false;
		isL1Pressed = false;
		manual = false;
		hanger.resetEncoder();
		counter = 0;

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Press A to lock ratchet
		if (oi.getOperator().getRawButton(InputConstants.BTN_A)) {
			hanger.setRatchet(servoPosition.LOCKED);
			intake.curIntakeState = intakeState.SHOOTING;
		}
		// Press B to lock ratchet
		if (oi.getOperator().getRawButton(InputConstants.BTN_B)) {
			hanger.setRatchet(servoPosition.UNLOCKED);
		}

		// Press Y and R1 to extend
		if (oi.getOperator().getRawButton(InputConstants.BTN_Y)
				&& oi.getOperator().getRawButton(InputConstants.BTN_R1)) {
			isExtending = true;
			hanger.setRatchet(servoPosition.UNLOCKED);
		}

		// Press L1 to swap between manual and automatic

		if (oi.getOperator().getRawButton(InputConstants.BTN_L1) && !isL1Pressed) {
			isL1Pressed = true;
			manual = !manual;
		}

		if (!oi.getOperator().getRawButton(InputConstants.BTN_L1)) {
			isL1Pressed = false;
		}

		SmartDashboard.putNumber("Enc Lift", hanger.getEnc());
		SmartDashboard.putBoolean("Manual", manual);

		if (isExtending) {
			if (counter < 10) {
				hanger.setWinches(0.15);
				counter++;
			} else {
				curEnc = hanger.getEnc();

				if (curEnc > 0) {
					if (Math.abs(hanger.getEnc() - Constants.ENC_TOP_HANG) > 25) {
						hanger.setWinches(-0.4);
						
					} else {
						hanger.setWinches(0);
						isExtending = false;
						readyToPullUp = true;
					}
				} else {
					hanger.setWinches(0);
				}

			}
		}
		if (!manual && readyToPullUp) {

			// if the lift is at the bottom, you can't go down
			joyValue = oi.getOperator().getRawAxis(InputConstants.AXIS_LEFT_Y);
			if(hanger.getEnc() > Constants.ENC_TOP_FINAL){
				if (Math.abs(joyValue) > 0.05) {
					hanger.setWinches(joyValue);
				}
			} else {
				hanger.setWinches(0);
			}
			// if (readytoPullUp &&
			// oi.getDriver().getRawButton(InputConstants.BTN_X)) {
			// hanger.setRatchet(servoPosition.LOCKED);
			// intake.curIntakeState = intakeState.SHOOTING;
			// if (Math.abs(hanger.getEnc() - Constants.ENC_TOP_FINAL) > 25) {
			// hanger.setWinches(0.70);
			// } else {
			// hanger.setWinches(0);
			// }
			// }
		} else if (manual) {

			// if the lift is at the bottom, you can't go down
			joyValue = oi.getOperator().getRawAxis(InputConstants.AXIS_LEFT_Y);
			if (Math.abs(joyValue) > 0.05) {
				hanger.setWinches(joyValue);
			} else {
				hanger.setWinches(0);
			}
		}

		/*
		 * if (isHanging) {
		 * 
		 * if (counter < 10) { hanger.setWinches(.15); //Positive is blipping
		 * System.out.println("BLIPPING"); counter++;
		 * 
		 * }
		 * 
		 * else { // hanger.setWinches(0); // 30/50 is magic number //
		 * hanger.setRatchet(servoPosition.LOCKED); if (Math.abs(hanger.getEnc()
		 * - Constants.ENC_TOP_HANG) > 50) { System.out.println("Winching Up");
		 * hanger.setWinches(-0.75); } else { hanger.setWinches(0);
		 * System.out.println("DONE"); readytoPullUp = true;
		 * 
		 * } } if(oi.getOperator().getRawButton(InputConstants.BTN_X)){
		 * isXPressed = true; }
		 * 
		 * 
		 * if(readytoPullUp && isXPressed){
		 * hanger.setRatchet(servoPosition.LOCKED); if(Math.abs(hanger.getEnc()
		 * - Constants.ENC_TOP_FINAL) > 50){ hanger.setWinches(0.70); }else{
		 * hanger.setWinches(0); } }
		 * 
		 * }
		 */

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
