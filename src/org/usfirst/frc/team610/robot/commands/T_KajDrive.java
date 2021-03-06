package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_KajDrive extends Command {

	private DriveTrain driveTrain;
	private OI oi;
	private double tRightDistance;
	private double curLeftDistance;
	private double curRightDistance;
	private double encLeftError;
	private double encRightError;
	private double lastEncLeftError;
	private double lastEncRightError;
	private double leftErrorDistance;
	private double rightErrorDistance;
	private int pov;
	private double tLeftDistance;

	// Angles
	
	private boolean isDPressed = false;
	private boolean posLock;
	private boolean isPovPressed = false;


	public T_KajDrive() {

		driveTrain = DriveTrain.getInstance();
		oi = OI.getInstance();
		tRightDistance = 0;
		tLeftDistance = 0;
		posLock = false;

	}

	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double x, y, leftSpeed, rightSpeed;
		
		pov = oi.getDriver().getPOV();
		
		//Press L2 for position lock
		if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L2)) {
			posLock = true;
			driveTrain.resetEncoders();
			isPovPressed = false;
		}

		x = oi.getDriver().getRawAxis(LogitechF310Constants.AXIS_RIGHT_X);
		y = oi.getDriver().getRawAxis(LogitechF310Constants.AXIS_LEFT_Y);

		//If you move your joystick, turn position lock off
		if (Math.abs(x) > 0.075 || Math.abs(y) > 0.075) {
			posLock = false;
		}

		if (!posLock) {
			leftSpeed = y - x;
			rightSpeed = y + x;
			driveTrain.setRight(rightSpeed);
			driveTrain.setLeft(leftSpeed);
		} else if (posLock) {
			
			//Press Up on D-pad to increase tDistance and down to decrease it.
			if ((pov == 0) && !isDPressed) {
				isDPressed = true;
				isPovPressed = false;
				tRightDistance += 4;
				tLeftDistance += 4;
			} else if ((pov == 180) && !isDPressed) {
				isDPressed = true;
				isPovPressed = false;
				tRightDistance -= 4;
				tLeftDistance -= 4;
			} else if (pov == 90 && !isDPressed){
				isDPressed = true;
				isPovPressed = false;
				tRightDistance -= 4;
				tLeftDistance += 4;
			} else if (pov == 270 && !isDPressed){
				isDPressed = true;
				isPovPressed = false;
				tRightDistance += 4;
				tLeftDistance -= 4;
			}
			//Make sure every time you press a button, you only press it once in code
			if (pov == -1 && !isPovPressed) {
				isDPressed = false;
				isPovPressed = true;
				driveTrain.resetEncoders();
				tRightDistance = 0;
				tLeftDistance = 0;
			}
			
			//The current distance
			curLeftDistance = driveTrain.getLeftInches();
			curRightDistance = driveTrain.getRightInches();

			//The error in distance
			encLeftError = tRightDistance - curLeftDistance;
			encRightError = tLeftDistance - curRightDistance;
			
			
			leftErrorDistance = lastEncLeftError - encLeftError;
			rightErrorDistance = encRightError - lastEncRightError;
			
			//Set encoder PID
			rightSpeed = encRightError * PIDConstants.ENCODER_Kp + rightErrorDistance * PIDConstants.ENCODER_Kd;
			leftSpeed = encLeftError * PIDConstants.ENCODER_Kp + leftErrorDistance * PIDConstants.ENCODER_Kd;
			
			
			driveTrain.setLeft(leftSpeed);
			driveTrain.setRight(rightSpeed);
			
			lastEncLeftError = encLeftError;
			lastEncRightError = encRightError;

		}
		
		// Driver Start button updates Constants
		if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_START))
		{
			Constants.update();
		}
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
