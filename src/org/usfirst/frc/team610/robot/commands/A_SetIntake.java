package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetIntake extends Command {

	private Intake intake;
	double speed;
	int time;
	
    public A_SetIntake(double speed) {
    	intake = Intake.getInstance();

    	this.speed = speed;
    }
    public A_SetIntake(int time, double speed){
    	this.time = time;
    	this.speed = speed;
    	intake = Intake.getInstance();
    	setTimeout(time);

    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("SetIntaking: " + speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.setBothRollers(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(isTimedOut()){
    		intake.setBothRollers(0);
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