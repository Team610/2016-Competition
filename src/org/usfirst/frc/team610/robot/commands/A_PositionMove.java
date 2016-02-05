package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Moves, using PID, to a certain distance in inches.
 */
public class A_PositionMove extends Command {

	DriveTrain driveTrain;
	double tDistance;
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

	public A_PositionMove(double distance) {
		
		tDistance = distance;
		driveTrain = DriveTrain.getInstance();
		driveTrain.resetSensors();
		curRightDistance = 0;
		curLeftDistance = 0;
		
		
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		driveTrain.resetEncoders();

		curLeftDistance = driveTrain.getLeftDistance();
		curRightDistance = driveTrain.getRightDistance();

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		curLeftDistance = driveTrain.getLeftDistance();
		curRightDistance = driveTrain.getRightDistance();

		encLeftError = tDistance - curLeftDistance;
		encRightError = tDistance - curRightDistance;

//		leftErrorDistance = lastEncLeftError - encLeftError;
//		rightErrorDistance = encRightError - lastEncRightError;

		rightSpeed = encRightError * PIDConstants.ENCODER_Kp + rightErrorDistance * PIDConstants.ENCODER_Kd;
		leftSpeed = encLeftError * PIDConstants.ENCODER_Kp + leftErrorDistance * PIDConstants.ENCODER_Kd;

		driveTrain.setLeft(leftSpeed);
		driveTrain.setRight(rightSpeed);
		
		SmartDashboard.putNumber("RightSpeed: ", rightSpeed);
		SmartDashboard.putNumber("LeftSpeed: ", leftSpeed);
		
//		lastEncRightError = encRightError;
//		lastEncLeftError = encLeftError;

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