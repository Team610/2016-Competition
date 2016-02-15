package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_ShooterTest extends Command {
	
	private Intake intake;
	double botMotorSpeed;
	double topMotorSpeed;
	double tSpeed;
	double topSpeedError;
	double botSpeedError;
	
    public T_ShooterTest() {
    	intake = Intake.getInstance();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	tSpeed = 3500;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    	
    	
    	botMotorSpeed = 0.00016 * tSpeed;
    	topMotorSpeed = 0.00016 * tSpeed;
    	
    	topSpeedError = tSpeed - intake.getTopSpeed();
    	botSpeedError = tSpeed - intake.getBotSpeed();
//    	
//    	SmartDashboard.putNumber("Top RPM", intake.getTopSpeed());
//    	SmartDashboard.putNumber("Bot RPM", intake.getBotSpeed());
    	
    	intake.setTopRoller(topMotorSpeed + topSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
    	intake.setBotRoller(botMotorSpeed + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
    	
    	
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
