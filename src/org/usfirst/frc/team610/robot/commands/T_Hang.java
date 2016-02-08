package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Hanger;
import org.usfirst.frc.team610.robot.subsystems.Hanger.servoPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_Hang extends Command {

	private Hanger hanger;
	private OI oi;
	boolean isHanging,readytoPullUp,isXPressed;
	int counter;

	public T_Hang() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		hanger = Hanger.getInstance();
		oi = OI.getInstance();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		isHanging = false;
		readytoPullUp = false;
		isXPressed = false;
		hanger.resetEncoder();
		counter = 0;

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		 hanger.setWinches(oi.getDriver().getRawAxis(InputConstants.AXIS_LEFT_Y));
		 //
		 if(oi.getDriver().getRawButton(InputConstants.BTN_A)){
		 hanger.setRatchet(servoPosition.LOCKED);
		 }
		 if(oi.getDriver().getRawButton(InputConstants.BTN_B)){
		 hanger.setRatchet(servoPosition.UNLOCKED);
		 }
		if (oi.getDriver().getRawButton(InputConstants.BTN_Y)) {
			isHanging = true;
			hanger.setRatchet(servoPosition.UNLOCKED);
		}

		if (isHanging) {

			if (counter < 10) {
				hanger.setWinches(.15); //Positive is blipping
				System.out.println("BLIPPING");
				counter++;

			}

			else {
			//	hanger.setWinches(0);
				 // 30/50 is magic number
				 // hanger.setRatchet(servoPosition.LOCKED);
				 if (Math.abs(hanger.getEnc() - Constants.ENC_TOP_HANG) > 50)
				 {
				 System.out.println("Winching Up");
				 hanger.setWinches(-0.70);
				 } else {
				 hanger.setWinches(0);
				 System.out.println("DONE");
				 readytoPullUp = true;
				
				 }
			}
			if(oi.getDriver().getRawButton(InputConstants.BTN_X)){
				isXPressed = true;
			}
			
			
			if(readytoPullUp && isXPressed){
				hanger.setRatchet(servoPosition.LOCKED);
				if(Math.abs(hanger.getEnc() - Constants.ENC_TOP_FINAL) > 50){
					hanger.setWinches(0.70);
				}else{
					hanger.setWinches(0);
				}
			}
			

	//	}
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
