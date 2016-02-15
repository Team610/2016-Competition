package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Moves, using PID, to a certain distance in inches.
 */
public class A_PositionMove extends Command {

	//Distance
	DriveTrain driveTrain;
	double tDistance;
	int count = 0;
	double curLeftDistance;
	double curRightDistance;
	double encLeftError;
	double encRightError;
	double rightSpeed;
	double leftSpeed;
	double lastEncLeftError;
	double lastEncRightError;
	double leftErrorDistance;
	double rightErrorDistance;
	double gyroRightSpeed;
	double gyroLeftSpeed;
	boolean isFinished = false;
	
	//Angles
	private double error;
	private double lastError;
	private double differenceError;
	private double tAngle;
 double angle;
	public A_PositionMove(double distance) {
		
		tDistance = distance;
		driveTrain = DriveTrain.getInstance();
		driveTrain.resetSensors();
		curRightDistance = 0;
		curLeftDistance = 0;
		
		angle = -1;
		
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}
	public A_PositionMove(double distance, double angle){
		tDistance = distance;
		driveTrain = DriveTrain.getInstance();
		driveTrain.resetSensors();
		curRightDistance = 0;
		curLeftDistance = 0; 
		this.angle = angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveTrain.resetSensors();

		curLeftDistance = driveTrain.getLeftInches();
		curRightDistance = driveTrain.getRightInches();

    	tAngle = driveTrain.getYaw();

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("GyroNew", driveTrain.getYaw());
		SmartDashboard.putNumber("LeftEncoderNew", driveTrain.getLeftInches());
		SmartDashboard.putNumber("RightEncoderNew", driveTrain.getRightInches());
		
		if(angle != -1){
			tAngle = angle;
		}else{
			tAngle = driveTrain.getYaw();

		}

		curLeftDistance = driveTrain.getLeftInches();
		curRightDistance = driveTrain.getRightInches();
    	error =0-tAngle;
    	differenceError = error - lastError;
    	
    	gyroLeftSpeed = error * PIDConstants.GYRO_Kp + differenceError * PIDConstants.GYRO_Kd;
    	gyroRightSpeed = error * PIDConstants.GYRO_Kp + differenceError * PIDConstants.GYRO_Kd;

		encLeftError = tDistance - curLeftDistance;
		encRightError = tDistance - curRightDistance;

		leftErrorDistance = lastEncLeftError - encLeftError;
		rightErrorDistance = encRightError - lastEncRightError;

		rightSpeed = encRightError * PIDConstants.ENCODER_Kp + rightErrorDistance * PIDConstants.ENCODER_Kd;
		leftSpeed = encLeftError * PIDConstants.ENCODER_Kp + leftErrorDistance * PIDConstants.ENCODER_Kd;

		
		rightSpeed += gyroRightSpeed;
		leftSpeed -= gyroLeftSpeed;
		
		driveTrain.setLeft(leftSpeed);
		driveTrain.setRight(rightSpeed);
		
		if(Math.abs(encLeftError) < 2 && Math.abs(encRightError) < 1){
			count++;
		
		if(count > 25){
			driveTrain.setLeft(0);

			driveTrain.setRight(0);
			isFinished = true;
		}
		}
		
		
		
//		
//		
//		lastEncRightError = encRightError;
//		lastEncLeftError = encLeftError;

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (isFinished || isTimedOut());
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
