package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class A_SetIntakePosition extends Command {

	Intake intake;
	Intake.intakeState intakeState;
	double intakePosError=0;
	boolean isFinished = false;
	double intakePosLastError = 0;
    public A_SetIntakePosition(Intake.intakeState intakeState) {
    	intake = Intake.getInstance();
    	this.intakeState = intakeState;
    //setTimeout(6);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(intakeState){
		case DEAD:
			intake.setIntakePivot(0);
			isFinished = true;
			break;
		case INTAKING:
			intakePosError = intake.getPot() - Constants.INTAKE_POT_INTAKE;
			intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp+ (intakePosError - intakePosLastError)*PIDConstants.INTAKE_POS_Kd);
			intakePosLastError = intakePosError;
			
			break;
		case POP:
			intakePosError = intake.getPot() - Constants.INTAKE_POT_POP;			
			if (intake.getPot() < Constants.INTAKE_POT_POP) {
				intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp);
			} else {
				intake.setIntakePivot(0);
				isFinished = true;
			}
			break;
		case SHOOTING:
			intakePosError = intake.getPot() - Constants.INTAKE_POT_SHOOTING;
			intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp + (intakePosError - intakePosLastError)*PIDConstants.INTAKE_POS_Kd);
			intakePosLastError = intakePosError;	
			
			
			break;
		default:
			
			break;
    	
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
