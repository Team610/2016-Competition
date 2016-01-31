package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_KajDrive extends Command {

	private DriveTrain drivetrain;
	private OI oi;
	private double leftSpeed;
	private double rightSpeed;
	private double x;
	private double y;

	public T_KajDrive() {

		drivetrain = DriveTrain.getInstance();
		oi = OI.getInstance();

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		x = oi.getDriver().getRawAxis(InputConstants.AXIS_RIGHT_X);
		y = oi.getDriver().getRawAxis(InputConstants.AXIS_LEFT_Y);

		leftSpeed = y - x;
		rightSpeed = y + x;
		drivetrain.setRight(rightSpeed);
		drivetrain.setLeft(leftSpeed);
		System.out.println("in kaj loop");

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
