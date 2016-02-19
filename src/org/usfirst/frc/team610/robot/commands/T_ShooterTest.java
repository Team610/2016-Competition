package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_ShooterTest extends Command {
	
	private Intake intake;
	private OI oi;
	double botMotorSpeed;
	double topMotorSpeed;
	double tSpeed;
	double topSpeedError;
	double botSpeedError;
	double topSpeed;
	double botSpeed;
	boolean isPressedL1 = false;
	boolean isPressedR1 = false;
	
    public T_ShooterTest() {
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
    	
    	if(oi.getOperator().getRawButton(InputConstants.BTN_L1) && !isPressedL1){
    		topSpeed += 0.05;
    		botSpeed += 0.05;
    		isPressedL1 = true;
    	}
    	
    	if(!oi.getOperator().getRawButton(InputConstants.BTN_L1)){
    		isPressedL1 = false;
    	}
    	
    	if(oi.getOperator().getRawButton(InputConstants.BTN_R1) && !isPressedR1){
    		topSpeed -= 0.05;
    		botSpeed -= 0.05;
    		isPressedR1 = true;
    	}
    	if(!oi.getOperator().getRawButton(InputConstants.BTN_R1)){
    		isPressedR1 = false;
    	}
    	
    	intake.setBotRoller(botSpeed);
    	intake.setTopRoller(topSpeed);
    	

    	SmartDashboard.putNumber("Top Speed", topSpeed);
    	SmartDashboard.putNumber("Bot Speed", botSpeed);
    	
    	SmartDashboard.putNumber("Top RPM", intake.getTopSpeed());
    	SmartDashboard.putNumber("Bot RPM", intake.getBotSpeed());
    	
    	
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