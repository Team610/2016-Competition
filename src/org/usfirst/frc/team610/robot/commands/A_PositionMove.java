package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves, using PID, to a certain distance in inches.
 */
public class A_PositionMove extends Command {

	// Distance
	private DriveTrain driveTrain;
	private double tDistance;
	private int count = 0;
	private double curLeftDistance = 0;
	private double curRightDistance = 0;
	private double encLeftError = 0;
	private double encRightError = 0;
	private double rightSpeed = 0;
	private double leftSpeed = 0;
	private double lastEncLeftError = 0;
	private double lastEncRightError = 0;
	private double leftErrorDistance = 0;
	private double rightErrorDistance = 0;
	private double gyroRightSpeed = 0;
	private double gyroLeftSpeed = 0;
	private boolean isFinished = false;
	private double limit;
	
	// Angles
	private double error;
	private double lastError;
	private double differenceError;
	private double tAngle;

	public A_PositionMove(double distance) {

		tDistance = distance;
		driveTrain = DriveTrain.getInstance();
		driveTrain.resetSensors();
		curRightDistance = 0;
		curLeftDistance = 0;

		tAngle = -1;
		this.limit = Integer.MAX_VALUE;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	public A_PositionMove(double distance, double angle) {
		tDistance = distance;
		driveTrain = DriveTrain.getInstance();
		driveTrain.resetSensors();
		curRightDistance = 0;
		curLeftDistance = 0;
		tAngle = angle;
		this.limit = 1;
	}
	public A_PositionMove(double distance, double angle, double limit) {
		tDistance = distance;
		driveTrain = DriveTrain.getInstance();
		driveTrain.resetSensors();
		curRightDistance = 0;
		curLeftDistance = 0;
		tAngle = angle;
		this.limit = limit;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveTrain.resetSensors();
		count = 0;
		curLeftDistance = driveTrain.getLeftInches();
		curRightDistance = driveTrain.getRightInches();

		if (tAngle == -1) {
			tAngle = 0;
		} else {
			tAngle = driveTrain.getYaw();
		}
		isFinished = false;
		setTimeout(5);
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		curLeftDistance = driveTrain.getLeftInches();
		curRightDistance = driveTrain.getRightInches();
		
		error = tAngle - driveTrain.getYaw();
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
		
		if(rightSpeed > limit){
			leftSpeed -= rightSpeed - limit;
		}
		
		if(leftSpeed > limit){
			rightSpeed -= leftSpeed - limit;
		}
		
		if(Math.abs(leftSpeed) > limit || Math.abs(rightSpeed) > limit){
			if(leftSpeed < 0 || rightSpeed < 0){
				leftSpeed = -limit;
				rightSpeed = - limit;
			} else if(leftSpeed > 0 || rightSpeed > 0){
				rightSpeed = limit;
				leftSpeed = limit;
			}
		}
		
		driveTrain.setLeft(leftSpeed);
		driveTrain.setRight(rightSpeed);

		if ((Math.abs(encLeftError) + Math.abs(encRightError))/2 < 0.1) {
			count++;

			if (count > 25) {
				driveTrain.setLeft(0);

				driveTrain.setRight(0);
				isFinished = true;
			}
		} else {
			count = 0;
		}
		lastError = error;

		
		//Uncomment if we change d from 0.00
		
		// lastEncRightError = encRightError;
		// lastEncLeftError = encLeftError;

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
