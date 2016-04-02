package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetIntakePosition extends Command {

	private Intake intake;
	private Intake.intakeState intakeState;
	private double intakePosError = 0;
	private boolean isFinished = false;
	private double intakePosLastError = 0;
	private double intakePosDiffError = 0;
	private double tAngle;
	private double time;
	

	public A_SetIntakePosition(Intake.intakeState intakeState) {
		intake = Intake.getInstance();
		this.intakeState = intakeState;
//		intake.curIntakeState = this.intakeState;
		 this.time = 5;
		 tAngle = 0;
	}
	public A_SetIntakePosition(Intake.intakeState intakeState, double time) {
		intake = Intake.getInstance();
		this.intakeState = intakeState;
//		intake.curIntakeState = this.intakeState;
		this.time = time;
		tAngle = 0;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		isFinished = false;
		intakePosDiffError = 0;
		
		setTimeout(time);
		intake.curIntakeState = intakeState.DEAD;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch (this.intakeState) {
		case DEAD:
			intake.curIntakeState = intakeState.DEAD;
			break;
		case UP:
			intake.curIntakeState = intakeState.UP;
			break;
		case POP:
			intake.curIntakeState = intakeState.POP;
			break;
		case SHOOTING:
			intake.curIntakeState = intakeState.SHOOTING;
			break;
		default:
			intake.curIntakeState = intakeState.DEAD;
			break;

		}
		
		
		intake.setIntake();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
//		intake.setIntakePivot(0);
		return isFinished || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
