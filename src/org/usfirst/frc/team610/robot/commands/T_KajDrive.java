package org.usfirst.frc.team610.robot.commands;

import javax.print.DocFlavor.INPUT_STREAM;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.NavX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_KajDrive extends Command {

	private DriveTrain driveTrain;
	private OI oi;
	private NavX navx;
//	Talon leftTalon;

	public T_KajDrive() {

		driveTrain = DriveTrain.getInstance();
//		navx = NavX.getInstance();
		oi = OI.getInstance();

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double x, y, leftSpeed, rightSpeed;
		x = oi.getDriver().getRawAxis(InputConstants.AXIS_RIGHT_X);
		y = oi.getDriver().getRawAxis(InputConstants.AXIS_LEFT_Y);

		leftSpeed = y - x;
		rightSpeed = y + x;
		driveTrain.setRight(rightSpeed);
		driveTrain.setLeft(leftSpeed);
//		System.out.println(navx.getAngle());
//		drivetrain.test(oi.getDriver().getRawAxis(InputConstants.AXIS_LEFT_Y));
		
		if(oi.getDriver().getRawButton(InputConstants.BTN_A)){
			driveTrain.resetEncoders();
		}
		
		System.out.println("runnig kaj/test");
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
