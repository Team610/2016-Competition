package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class A_SittingDuck extends Command {

	private double leftSpeed;
	private double rightSpeed;
	private double error;
	private double tAngle;
	private DriveTrain driveTrain;
	
	
	
    public A_SittingDuck() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

    	driveTrain = driveTrain.getInstance();
    	requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveTrain.resetSensors();
    	tAngle = driveTrain.getYaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	tAngle = driveTrain.getYaw();
    	error = 0 - tAngle;
    	leftSpeed = error * PIDConstants.Kp;
    	rightSpeed = error * PIDConstants.Kp;
    	
    	driveTrain.setLeft(-leftSpeed);
    	driveTrain.setRight(rightSpeed);
    	
    	SmartDashboard.putNumber("Left Speed: ", leftSpeed);
    	SmartDashboard.putNumber("Right Speed: ", rightSpeed);
    	SmartDashboard.putNumber("Error: ", error);
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
