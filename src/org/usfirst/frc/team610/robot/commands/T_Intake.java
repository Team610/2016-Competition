package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.subsystems.Hanger;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Intake extends Command {
	private Intake intake;
	private OI oi;
//	private PID topPID;
//	private PID botPID;
//	private PID pivotPID;
	private int pov;
	private boolean isPovPressed = false;
	
    public T_Intake() {
		intake = Intake.getInstance();
		oi = OI.getInstance();
		intake.curIntakeState = intakeState.POP;
//		pivotPID = new PID(PIDConstants.INTAKE_POS_Kp, 0, PIDConstants.INTAKE_POS_Kd, -1, 1);
//		topPID = new PID(PIDConstants.INTAKE_SHOOT_Kp, 0, PIDConstants.INTAKE_SHOOT_Kd, -1, 1);
//		botPID = new PID(PIDConstants.INTAKE_SHOOT_Kp, 0, PIDConstants.INTAKE_SHOOT_Kd, -1, 1);
		

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	intake.curIntakeState = intakeState.POP;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	pov = oi.getOperator().getPOV();
    	
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
    	
    	intake.setIntake();
    	
    	if(pov == 0 && !isPovPressed){
    		Constants.INTAKE_POT_SHOOTING += 0.005;
    		isPovPressed = true;
    	} else if (pov == 180 && !isPovPressed) {
    		Constants.INTAKE_POT_SHOOTING -= 0.005;
    		isPovPressed = true;
    	}
    	
    	if(pov == -1){
    		isPovPressed = false;
    	}
    	
    	SmartDashboard.putNumber("Shooting Angle", Constants.INTAKE_POT_SHOOTING);
    	SmartDashboard.putString("State", intake.curIntakeState.toString());
    	
    	
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
