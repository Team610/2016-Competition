package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Test_Motor extends Command {

	Intake intake;
	OI oi;
	
    public Test_Motor() {
    	
    	intake = Intake.getInstance();
    	oi = OI.getInstance();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_Y)){
    		intake.setIntakePivot(1);
    	} else if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_A)){
    		intake.setIntakePivot(-1);
    	} else {
    		intake.setIntakePivot(0);
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
