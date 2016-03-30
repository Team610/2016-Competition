package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.subsystems.Hanger;
import org.usfirst.frc.team610.robot.subsystems.Hanger.servoPosition;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Hang extends Command {

	private Hanger hanger;
	private Intake intake;
	private OI oi;
	private boolean isExtending, readyToPullUp;
	private boolean isSTARTPressed;
	private boolean manual;
	private int counter;
	private double joyValue;
	private double curEnc;

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
		isSTARTPressed = false;
		manual = false;
		hanger.resetEncoder();
		counter = 0;
		hanger.setWinches(0);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Press Operator R2 to lock ratchet
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_R2)) {
			hanger.setRatchet(servoPosition.LOCKED);
			intake.curIntakeState = intakeState.HANGING;
		}
		// Press Operator Back to lock ratchet
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_BACK)) {
			hanger.setRatchet(servoPosition.UNLOCKED);
		}

		// Press Operator L1 and R1 to extend
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_L1)
				&& oi.getOperator().getRawButton(LogitechF310Constants.BTN_R1)) {
			isExtending = true;
			hanger.isHanging = true;
			hanger.setRatchet(servoPosition.UNLOCKED);
		}

		// Press START to swap between manual and automatic
		
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_START) && !isSTARTPressed) {
			isSTARTPressed = true;
			manual = !manual;
		}

		if (!oi.getOperator().getRawButton(LogitechF310Constants.BTN_START)) {
			isSTARTPressed = false;
		}


		if (isExtending) {
			
			if (counter < 10) {
				hanger.setWinches(0.15);
				counter++;
			} else {
				curEnc = hanger.getEnc();

				if (curEnc > 0) {
					if (hanger.getEnc() < Constants.HANGER_ENC_TOP_HANG) {
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
			joyValue = oi.getOperator().getRawAxis(LogitechF310Constants.AXIS_LEFT_Y);
			
			if(hanger.getEnc() > Constants.HANGER_ENC_TOP_FINAL){
				if (Math.abs(joyValue) > 0.05) {
					hanger.setWinches(joyValue);
				} else {
					hanger.setWinches(0);
				}
			} else {
				hanger.setWinches(0);
			}
		
		} else if (manual) {
			
			joyValue = oi.getOperator().getRawAxis(LogitechF310Constants.AXIS_LEFT_Y);
			if (Math.abs(joyValue) > 0.05) {
				hanger.setWinches(joyValue);
			} else {
				hanger.setWinches(0);
			}
		}
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
