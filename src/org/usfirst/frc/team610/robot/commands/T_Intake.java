package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;
import org.usfirst.frc.team610.robot.subsystems.Intake.servoPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_Intake extends Command {
	Intake intake;
	OI oi;
	double n = 0;
	double intakePosError;
	double intakePosKp = 0.01;
	boolean isShooting;

	public T_Intake() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		intake = Intake.getInstance();
		oi = OI.getInstance();
		isShooting = false;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (oi.getDriver().getRawButton(InputConstants.BTN_A)) {
			intake.curIntakeState = intakeState.INTAKING;
		}
		if (oi.getDriver().getRawButton(InputConstants.BTN_B)) {
			intake.curIntakeState = intakeState.SHOOTING;
		}

		switch (intake.curIntakeState) {
		case INTAKING:
			if(Math.abs(intake.getPot() - Constants.INTAKE_POT_DOWN) != 0)
			{
				intakePosError = intake.getPot() - Constants.INTAKE_POT_DOWN;
				intake.setIntakePivot(intakePosError*PIDConstants.INTAKE_POS_Kp);
			}
			break;
		case SHOOTING:
			//shoot
		}
			

		// intake.setBothRollers(oi.getDriver().getRawAxis(InputConstants.AXIS_LEFT_Y));
		// intake.setIntakePivot(oi.getDriver().getRawAxis(InputConstants.AXIS_RIGHT_Y));
		// if(oi.getDriver().getRawButton(InputConstants.BTN_L2)){
		// n+=0.05;
		// }
		// if(oi.getDriver().getRawButton(InputConstants.BTN_R2)){
		//
		// n-=0.05;
		// }
		// SmartDashboard.putNumber("n value of intake servo", n);
		// intake.setIntakeServos(n);test

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
