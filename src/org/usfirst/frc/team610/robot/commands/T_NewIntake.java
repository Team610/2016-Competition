package org.usfirst.frc.team610.robot.commands;

import org.sixten.chareslib.PID;
import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Hanger;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_NewIntake extends Command {
	private Intake intake;
	private OI oi;
	private Hanger hanger;
//	private PID topPID;
//	private PID botPID;
//	private PID pivotPID;

	
    public T_NewIntake() {
    	hanger = Hanger.getInstance();
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
    	
//    	switch(intake.curIntakeState){
//		case DEAD:
//			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)){
//				intake.setTopRoller(Constants.INTAKE_INTAKE_POWER);
//				intake.setBotRoller(Constants.INTAKE_INTAKE_POWER);
//				intake.setFeeder(Constants.INTAKE_FEEDER_IN);
//    		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
//    			intake.setTopRoller(Constants.INTAKE_OUTTAKE_POWER);
//    			intake.setBotRoller(Constants.INTAKE_OUTTAKE_POWER);
//    			intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
//    		} else {
//    			intake.setTopRoller(0);
//    			intake.setBotRoller(0);
//    			intake.setFeeder(0);
//    		}
//			
//			break;
//		case POP:
//			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)){
//				intake.setTopRoller(Constants.INTAKE_INTAKE_POWER);
//				intake.setBotRoller(Constants.INTAKE_INTAKE_POWER);
//				intake.setFeeder(Constants.INTAKE_FEEDER_IN);
//    		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
//    			intake.setTopRoller(Constants.INTAKE_TOP_POP_POWER);
//    			intake.setBotRoller(Constants.INTAKE_BOT_POP_POWER);
//    			intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
//    		} else {
//    			intake.setTopRoller(0);
//    			intake.setBotRoller(0);
//    			intake.setFeeder(0);
//    		}
//			break;
//		case UP:
//			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)){
//				intake.setTopRoller(Constants.INTAKE_INTAKE_POWER);
//				intake.setBotRoller(Constants.INTAKE_INTAKE_POWER);
//				intake.setFeeder(Constants.INTAKE_FEEDER_IN);
//    		} else {
//    			intake.setTopRoller(0);
//    			intake.setBotRoller(0);
//    			intake.setFeeder(0);
//    		}
//			break;
//		case HANGING:
//			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
//				intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
//			} else {
//				intake.setFeeder(0);
//			}
//			intake.setTopRoller(topPID.getValue(intake.getTopSpeed(), Constants.SHOOTER_TOP_HANG, intake.getTopFeedForward(Constants.SHOOTER_TOP_HANG)));
//			intake.setBotRoller(botPID.getValue(intake.getBotSpeed(), Constants.SHOOTER_BOT_HANG, intake.getBotFeedForward(Constants.SHOOTER_BOT_HANG)));
//			break;
//		case SHOOTING:
//			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
//				intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
//			} else {
//				intake.setFeeder(0);
//			}
//			intake.setTopRoller(topPID.getValue(intake.getTopSpeed(), Constants.SHOOTER_TOP, intake.getTopFeedForward(Constants.SHOOTER_TOP)));
//			intake.setBotRoller(botPID.getValue(intake.getBotSpeed(), Constants.SHOOTER_BOT, intake.getBotFeedForward(Constants.SHOOTER_BOT)));
//			
//			break;
//		}
//		
////		//Change 14.5
////		if(getPivotCurrent() > 14.5){
////			setIntakePivot(0);
////			waitCounter = 0;
////		}
////		if(waitCounter < 100){
////			waitCounter++;
////		} else {
////			setIntakePivot(pivotPID.getValue(getPot(), getTarget(state)));
////		}
//		
//    	intake.setIntakePivot(pivotPID.getValue(intake.getPot(), intake.getTarget(intake.curIntakeState)));
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
