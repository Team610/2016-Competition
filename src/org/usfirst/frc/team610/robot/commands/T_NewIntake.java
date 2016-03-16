package org.usfirst.frc.team610.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.subsystems.Hanger;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_NewIntake extends Command {
	private Intake intake;
	private Hanger hanger;
	private OI oi;
	
	private double intakePosError;
	private boolean readyToShoot;
	private double speed;
	
	private boolean isDPressed = false;
	private boolean isDDownPressed = false;
	private double outtakeSpeed = -0.65;
	private int pov;
	
	private double botMotorSpeed;
	private double topMotorSpeed;
	private double tSpeedBot;
	private double tSpeedTop;
	private double RPMTrim = 0;
	
	private double topSpeedError;
	private double botSpeedError;
	private double topSpeed;
	private double botSpeed;
	private double topSpeedErrDiff;
	private double botSpeedErrDiff;
	private double topLastError = 0;
	private double botLastError = 0;
	
	private double intakePosLastError = 0;
	private double intakePosDiffError;
	private double intakeSpeed;
	private double tAngle;
	private int popCounter = 0;
	double potValue;
	
	private ArrayList <Double> potValues = new ArrayList <Double>();
	private ArrayList <Double> sortedPotValues = new ArrayList <Double>();
	
    public T_NewIntake() {
    	hanger = Hanger.getInstance();
		intake = Intake.getInstance();
		oi = OI.getInstance();
		readyToShoot = false;
		topSpeed = 0;
		botSpeed = 0;
		speed = 0;
		tSpeedTop = Constants.SHOOTER_TOP; // -3500
		tSpeedBot = Constants.SHOOTER_BOT; // -4200

		intake.curIntakeState = intakeState.POP;
		potValue = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	intake.curIntakeState = intakeState.POP;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    	//Press A for Dead
    	if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_A)) {
    		intake.curIntakeState = intakeState.DEAD;
    	} //Press B for Intaking 
    	else if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_B)) {
    		intake.curIntakeState = intakeState.UP;
    	} //Press X for Shooting 
    	else if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_X)) {
    		intake.curIntakeState = intakeState.SHOOTING;
    	} //Press Y for Pop 
    	else if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_Y)) {
    		intake.curIntakeState = intakeState.POP;
    	} //Press R2 for Hanging 
    	else if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_R2)) {
    		intake.curIntakeState = intakeState.HANGING;
    	}
    	
    	switch(intake.curIntakeState){
    	case POP:
    		tAngle = Constants.INTAKE_POT_POP;
    		break;
    	case SHOOTING:
    		tAngle = Constants.INTAKE_POT_SHOOTING;
    		break;
    	case HANGING:
    		tAngle = Constants.INTAKE_POT_HANGING;
    		break;
    	case UP:
    		tAngle = Constants.INTAKE_POT_UP;
    		break;
    	case DEAD:
    		tAngle = Constants.INTAKE_POT_DEAD;
    		break;
    	default:
    		tAngle = Constants.INTAKE_POT_DEAD;
    		break;
    	}
    	
    }
    //INTAKING, POP, DEAD, SHOOTING, HANGING

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
