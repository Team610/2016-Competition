package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.subsystems.Hanger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_HangerTester extends Command {

	private Hanger hanger;
	private OI oi;
	private boolean isPressed = false;
	private boolean isPressedX = false;
	private boolean isPressedY = false;
	private boolean ratchet = true;
	private double speed = 0;
	private double ratchetValue = 0.368;

	public T_HangerTester() {
		hanger = Hanger.getInstance();
		oi = OI.getInstance();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
//		if (oi.getDriver().getRawButton(InputConstants.BTN_A) || oi.getOperator().getRawButton(InputConstants.BTN_A)) {
//			speed = 1;
//		} else {
//			speed = 0;
//		}
		
	//	speed = oi.getDriver().getRawAxis(InputConstants.AXIS_RIGHT_Y);
		
//		
//		if(oi.getDriver().getRawButton(InputConstants.BTN_X)){
//			hanger.setRatchet(0.3);
//		}
//		
//		if(oi.getDriver().getRawButton(InputConstants.BTN_Y)){
//			hanger.setRatchet(0.368);
//		}
//		if(oi.getDriver().getRawButton(InputConstants.BTN_B)){
//			hanger.resetEncoder();
//		}
		
//		LiveWindow.addActuator("Hanger", "Ratchet", hanger.getRatchet());
		
		
		SmartDashboard.putNumber("Ratchet Value: ", ratchetValue);
		SmartDashboard.putNumber("EncoderValue", hanger.getEnc());
		
//		hanger.setRatchet(ratchetValue);
	
		
		hanger.setWinches(speed);

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
