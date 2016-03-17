package org.usfirst.frc.team610.robot.commands;

import java.util.ArrayList;
import java.util.Collections;

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
public class T_Intake extends Command {
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

	public T_Intake() {
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
		tAngle = Constants.INTAKE_POT_POP + Constants.INTAKE_POT_OFFSET;
	}

	// Called repeatedly when this Command is scheduled to run
	@SuppressWarnings("unchecked")
	protected void execute() {
		
		
		//Median Filter for gyro readings\\
		//Take out oldest value
		if(potValues.size() > 4){
			potValues.remove(potValues.size() - 1);
		}
		//Put most recent value in index 0
		potValues.add(0, intake.getPot());
		//Put sorted values in different array list
		sortedPotValues = (ArrayList<Double>) potValues.clone();
		Collections.sort(sortedPotValues);
		
		//If we have more than 3 values, take the mid value
		if(potValues.size() > 3){
			potValue = sortedPotValues.get(2);
		}
		
		//Feed forwards speed for top/bot motor speeds
		
		botMotorSpeed = 1.6e-4 * tSpeedBot - 0.05;
		topMotorSpeed = 1.52e-4 * tSpeedTop - 0.05;

		
		//Pov is D-Pad readings
		pov = oi.getOperator().getPOV();

		// B for intaking, regular
		// A for dead
		// X for shooting
		// Y for Pop
		// L2 for intaking, up

		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_B)) {
			//Set current state to INTAKING
			intake.curIntakeState = intakeState.UP;
			//Set target Angle
			tAngle = Constants.INTAKE_POT_UP + Constants.INTAKE_POT_OFFSET;
			outtakeSpeed = Constants.INTAKE_OUTTAKE_POWER;
			intakeSpeed = Constants.INTAKE_INTAKE_POWER;
		}
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_A)) {
			//Set current state to DEAD
			intake.curIntakeState = intakeState.DEAD;
			tAngle = Constants.INTAKE_POT_DEAD + Constants.INTAKE_POT_OFFSET;
			outtakeSpeed = Constants.INTAKE_OUTTAKE_POWER;
			intakeSpeed = Constants.INTAKE_INTAKE_POWER;
		}
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_X)) {
			//Set current state to SHOOTING
			Constants.update();
			if(hanger.isHanging){
				intake.curIntakeState = intakeState.HANGING;
				tAngle = Constants.INTAKE_POT_HANGING + Constants.INTAKE_POT_OFFSET;
			} else {
				intake.curIntakeState = intakeState.SHOOTING;
				tAngle = Constants.INTAKE_POT_SHOOTING + Constants.INTAKE_POT_OFFSET;
			}
		}
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_Y)) {
			//Set current state to POP
			intake.curIntakeState = intakeState.POP;
			tAngle = Constants.INTAKE_POT_POP + Constants.INTAKE_POT_OFFSET;
			topSpeed = Constants.INTAKE_TOP_POP_POWER;
			botSpeed = Constants.INTAKE_BOT_POP_POWER;
			intakeSpeed = Constants.INTAKE_INTAKE_POWER;
		}
		if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_L2)){
			//Set current state to INTAKING
			intake.curIntakeState = intakeState.UP;
			tAngle = Constants.INTAKE_POT_UP + Constants.INTAKE_POT_OFFSET + 0.09;
			outtakeSpeed = Constants.INTAKE_OUTTAKE_POWER;
			intakeSpeed = Constants.INTAKE_INTAKE_POWER;
		}
		
		//Put intatke state to Smart Dashboard
		SmartDashboard.putString("IntakteState: ", intake.curIntakeState.toString());

		switch (intake.curIntakeState) {
		case UP:
			//Press L1 to outtake
			//Press R1 to intake
			//Press nothing to stop
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				topSpeed = outtakeSpeed;
				botSpeed = outtakeSpeed;
				intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
			} 
			//Only intake if there's no ball
			//Took out optical check, won't be able to adjust ball if doesn't go in correctly 
			else if ((oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1) && intake.getOptical()) || (oi.getDriver().getRawButton(LogitechF310Constants.BTN_X))) {
				topSpeed = intakeSpeed;
				botSpeed = intakeSpeed;
				intake.setFeeder(Constants.INTAKE_FEEDER_IN);
			} else if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_L1) && !oi.getOperator().getRawButton(LogitechF310Constants.BTN_R1)){
				topSpeed = 0;
				botSpeed = -1;
				intake.setFeeder(0);
			} else {
				topSpeed = 0;
				botSpeed = 0;
				intake.setFeeder(0);
			}

			intake.setTopRoller(topSpeed);
			intake.setBotRoller(botSpeed);
			SmartDashboard.putNumber("TopSpeed", topSpeed);
			SmartDashboard.putNumber("BotSpeed", botSpeed);

			break;
		case POP:
			tAngle = Constants.INTAKE_POT_POP + Constants.INTAKE_POT_OFFSET;
			//Press L1 to outtake
			//Press R1 to intake
			//Press nothing to stop
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				intake.setTopRoller(topSpeed);
				intake.setBotRoller(botSpeed);
				popCounter++;
				//Wait 5 ticks to shoot
				if (popCounter > 5) {
					intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
				}
			} 
			//You can only intake if there's no ball
			else if ((oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1) && intake.getOptical())  || oi.getDriver().getRawButton(LogitechF310Constants.BTN_X)) {
				intake.setTopRoller(intakeSpeed);
				intake.setBotRoller(intakeSpeed);
				intake.setFeeder(Constants.INTAKE_FEEDER_IN);
			} else {
				popCounter = 0;
				intake.setFeeder(0);
				intake.setTopRoller(0);
				intake.setBotRoller(0);
			}
			break;

		case DEAD:
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				topSpeed = outtakeSpeed;
				botSpeed = outtakeSpeed;
				intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
			} 
			//Only intake if there's no ball
			//Took out optical check, won't be able to adjust ball if doesn't go in correctly 
			else if ((oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1) && intake.getOptical()) || (oi.getDriver().getRawButton(LogitechF310Constants.BTN_X))) {
				topSpeed = intakeSpeed;
				botSpeed = intakeSpeed;
				intake.setFeeder(Constants.INTAKE_FEEDER_IN);
			} else if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_L1) && !oi.getOperator().getRawButton(LogitechF310Constants.BTN_R1)){
				topSpeed = 0;
				botSpeed = -1;
				intake.setFeeder(0);
			} else {
				topSpeed = 0;
				botSpeed = 0;
				intake.setFeeder(0);
			}

			intake.setTopRoller(topSpeed);
			intake.setBotRoller(botSpeed);
			SmartDashboard.putNumber("TopSpeed", topSpeed);
			SmartDashboard.putNumber("BotSpeed", botSpeed);
			break;
		case SHOOTING:
			
			tSpeedBot = Constants.SHOOTER_BOT;
			tSpeedTop = Constants.SHOOTER_TOP;
			
			//RPM trim with Operator D-Pad
//			if ((pov == 0 || pov == 45 || pov == 315) && !isDPressed) {
//				RPMTrim += 50;
//				isDPressed = true;
//
//			} else if ((pov == 180 || pov == 135 || pov == 225) && !isDDownPressed) {
//				RPMTrim -= 50;
//				isDPressed = true;
//			}
//
//			if (pov == -1) {
//				isDPressed = false;
//			}
//			if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_L2)) {
//				RPMTrim = 0;
//			}

			//Activiate PID for hang shot!!!!
			
			if(hanger.isHanging){
				// Use Hangshot rpms etc
				// Maybe just use flat motor values? maybe no pid needed
			}else{
				//Use normal shoot 
			}
			topSpeedError = tSpeedTop + intake.getTopSpeed();
			botSpeedError = tSpeedBot + intake.getBotSpeed();

			topSpeedErrDiff = topSpeedError - topLastError;
			botSpeedErrDiff = botSpeedError - botLastError;

			topSpeed = topMotorSpeed + topSpeedError * PIDConstants.INTAKE_SHOOT_Kp
					- topSpeedErrDiff * PIDConstants.INTAKE_SHOOT_Kd;
			botSpeed = botMotorSpeed + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp
					- botSpeedErrDiff * PIDConstants.INTAKE_SHOOT_Kd;


			//Don't activiate rollers before we get to shooting pos
			if (Math.abs(intakePosError) < 0.1) {
				intake.setTopRoller(topSpeed);
				intake.setBotRoller(botSpeed);
			} else {
				intake.setBothRollers(0);
			}

			topLastError = topSpeedError;
			botLastError = botSpeedError;

			
			//Shoot if RPM is within 5% error
			// if (Math.abs(topSpeedError) < topMotorSpeed * 0.05 && Math.abs(botSpeedError) <
			// botMotorSpeed * 0.05) {
			readyToShoot = true;
			// }

			//If we are ready to shoot, press L1 to shoot!!!!!
			if (readyToShoot) {
				if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
					intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
					readyToShoot = false;
				} else {
					intake.setFeeder(0);
				}

			} else {
				intake.setFeeder(0);
			}
			SmartDashboard.putBoolean("Ready to Shoot", readyToShoot);
			break;
		case HANGING:

			tAngle = Constants.INTAKE_POT_HANGING + Constants.INTAKE_POT_OFFSET;
			
			tSpeedBot = Constants.SHOOTER_BOT_HANG;
			tSpeedTop = Constants.SHOOTER_TOP_HANG;
			//RPM trim with Operator D-Pad
//			if ((pov == 0 || pov == 45 || pov == 315) && !isDPressed) {
//				RPMTrim += 50;
//				isDPressed = true;
//
//			} else if ((pov == 180 || pov == 135 || pov == 225) && !isDDownPressed) {
//				RPMTrim -= 50;
//				isDPressed = true;
//			}
//
//			if (pov == -1) {
//				isDPressed = false;
//			}
//			if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_L2)) {
//				RPMTrim = 0;
//			}

			//Activiate PID for hang shot!!!!
			
			topSpeedError = tSpeedTop + intake.getTopSpeed();
			botSpeedError = tSpeedBot + intake.getBotSpeed();

			topSpeedErrDiff = topSpeedError - topLastError;
			botSpeedErrDiff = botSpeedError - botLastError;

			topSpeed = topMotorSpeed + topSpeedError * PIDConstants.INTAKE_SHOOT_Kp
					- topSpeedErrDiff * PIDConstants.INTAKE_SHOOT_Kd;
			botSpeed = botMotorSpeed + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp
					- botSpeedErrDiff * PIDConstants.INTAKE_SHOOT_Kd;


			//Don't activiate rollers before we get to shooting pos
			if (Math.abs(intakePosError) < 0.1) {
				intake.setTopRoller(topSpeed);
				intake.setBotRoller(botSpeed);
			} else {
				intake.setBothRollers(0);
			}

			topLastError = topSpeedError;
			botLastError = botSpeedError;

			
			//Shoot if RPM is within 5% error
			// if (Math.abs(topSpeedError) < topMotorSpeed * 0.05 && Math.abs(botSpeedError) <
			// botMotorSpeed * 0.05) {
			readyToShoot = true;
			// }

			//If we are ready to shoot, press L1 to shoot!!!!!
			if (readyToShoot) {
				if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
					intake.setFeeder(Constants.INTAKE_FEEDER_OUT);
					readyToShoot = false;
				} else {
					intake.setFeeder(0);
				}

			} else {
				intake.setFeeder(0);
			}
			SmartDashboard.putBoolean("Ready to Shoot", readyToShoot);
			break;
		}
		// End of switch statement 
		
		//PID for angle of Claw
		intakePosError = potValue - tAngle;
		if (intake.curIntakeState.equals(intakeState.UP) || intake.curIntakeState.equals(intakeState.SHOOTING)) {
			//Hold PID angle
			intakePosError = potValue - tAngle;
			intakePosDiffError = intakePosError - intakePosLastError;

			intake.setIntakePivot(
					intakePosError * PIDConstants.INTAKE_POS_Kp + intakePosDiffError * PIDConstants.INTAKE_POS_Kd);
			intakePosLastError = intakePosError;

		} else {
			//Don't hold PID if the claw is at the position
			if(intake.curIntakeState.equals(intakeState.POP) || intake.curIntakeState.equals(intakeState.HANGING)){
				if (Math.abs(intakePosError) > 0.1) {
					intakePosError = potValue - tAngle;
					intakePosDiffError = intakePosError - intakePosLastError;
					intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp
							+ intakePosDiffError * PIDConstants.INTAKE_POS_Kd);
					intakePosLastError = intakePosError;
				} else {
					intake.setIntakePivot(0);
				}
			} else if (intake.curIntakeState.equals(intakeState.DEAD)){
				if (intake.getPot() > Constants.INTAKE_POT_DIE + Constants.INTAKE_POT_OFFSET) {
					intakePosError = potValue - tAngle;
					intakePosDiffError = intakePosError - intakePosLastError;
					intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp
							+ intakePosDiffError * PIDConstants.INTAKE_POS_Kd);
					intakePosLastError = intakePosError;
				} else {
					intake.setIntakePivot(0);
				}
			}
		}
		SmartDashboard.putNumber("Intake Power: ", intakePosError * PIDConstants.INTAKE_POS_Kp
						+ intakePosDiffError * PIDConstants.INTAKE_POS_Kd);

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
