package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Intake extends Command {
	Intake intake;
	OI oi;
	double n = 0;
    public T_Intake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	intake = Intake.getInstance();
    	oi = OI.getInstance();
    	 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//Outtake
    	
    	
    	intake.setBothRollers(oi.getDriver().getRawAxis(InputConstants.AXIS_LEFT_Y));
    	intake.setIntakePivot(oi.getDriver().getRawAxis(InputConstants.AXIS_RIGHT_Y));
    	if(oi.getDriver().getRawButton(InputConstants.BTN_L2)){
    		n+=0.05;
    	}
    	if(oi.getDriver().getRawButton(InputConstants.BTN_R2)){
    		
    		n-=0.05;
    	}
    	SmartDashboard.putNumber("n value of intake servo", n);
    	intake.setIntakeServos(n);
    	
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
