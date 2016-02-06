package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Hanger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_Hang extends Command {
	

	private Hanger hanger;
	private OI oi;
	boolean isHanging;
	
	
	

    public T_Hang() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	hanger = Hanger.getInstance();
    	oi = OI.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isHanging = false;
    	hanger.resetEncoder();
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(oi.getDriver().getRawButton(InputConstants.BTN_Y)){
    		isHanging = true;
    	}
    	
    	
    	
    	if(isHanging){
    		hanger.setRatchet(Constants.HANGER_RATCHER_OPEN);
    		
    		if(hanger.getEnc() < Constants.ENC_TOP_HANG){
    		hanger.setWinches(Constants.WINCH_SPEED_UP);
    		
    		}else{
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
