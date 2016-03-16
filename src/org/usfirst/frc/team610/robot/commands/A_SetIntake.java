package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetIntake extends Command {

	private Intake intake;
	private double speed;
	private int time;
	
    public A_SetIntake(double speed) {
    	intake = Intake.getInstance();
    	this.speed = speed;
    	this.time = 1;
    }
    public A_SetIntake(int time, double speed){
    	this.time = time;
    	this.speed = speed;
    	intake = Intake.getInstance();
 

    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(time);
    	System.out.println("SetIntaking: " + speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.setBothRollers(speed);
    	if(speed < 0){
    		intake.setFeeder(Constants.SHOOTER_FEEDER_OUT);
    	} else if(speed > 0){
    		intake.setFeeder(Constants.SHOOTER_FEEDER_IN);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(isTimedOut()){
    		intake.setBothRollers(0);
    		intake.setFeeder(0);
    	}
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
