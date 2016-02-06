package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.PIDController610;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class A_PositionLock extends Command {

	private double leftSpeed;
	private double rightSpeed;
	private double error;
	private double lastError;
	private double differenceError;
	private double tAngle;
	private DriveTrain driveTrain;
	private PIDController610 pidController;
	
	
	
    public A_PositionLock() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

    	driveTrain = DriveTrain.getInstance();
    }
    public A_PositionLock(int time){
    	driveTrain = DriveTrain.getInstance();
    	setTimeout(time);
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
    	differenceError = error - lastError;
    	leftSpeed = error * PIDConstants.GYRO_Kp + differenceError * PIDConstants.GYRO_Kd;
    	rightSpeed = error * PIDConstants.GYRO_Kp + differenceError * PIDConstants.GYRO_Kd;
    	
    	driveTrain.setLeft(-leftSpeed);
    	driveTrain.setRight(rightSpeed);
    	
    	lastError = error;
    	
    	SmartDashboard.putNumber("Left Speed: ", leftSpeed);
    	SmartDashboard.putNumber("Right Speed: ", rightSpeed);
    	SmartDashboard.putNumber("Error: ", error);
    
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(isTimedOut()){
        	driveTrain.setLeft(0);
        	driveTrain.setRight(0);

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
