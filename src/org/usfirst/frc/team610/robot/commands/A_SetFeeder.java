package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetFeeder extends Command {

	Intake intake;
	private double time;
	private double speed;
	int counter;
    public A_SetFeeder(double time, double speed) {
    	intake = Intake.getInstance();
    	this.time = time;
    	this.speed = speed;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(time);
    	counter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	counter ++;
    	if(counter > 25){
    	intake.setFeeder(speed);
    	} else {
    		intake.setFeeder(0);
    	}
    	if(isTimedOut()){
    		intake.setFeeder(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
