package org.usfirst.frc.team610.robot.commands;

import org.omg.CORBA.ShortHolder;
import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;
import org.usfirst.frc.team610.robot.subsystems.Intake.servoPosition;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Intake extends Command {
	Intake intake;
	OI oi;
	double intakePotShootConstant;
	double n = 0;
	double intakePosError, shooterTopError, shooterBotError;
	double intakePosKp = 0.01;
	boolean isShooting;
	boolean readyToShoot;
	double rpmError = 0;
	double topDiff, botDiff;
	double speed;
	boolean isR1Pressed = false;
	boolean isBPressed = false;
	boolean isR2Pressed = false;
	boolean isL1Pressed = false;
	boolean isL2Pressed = false;
	boolean isDUpPressed = false;
	boolean isDDownPressed = false;
	boolean isDRightPressed = false;
	boolean isAPressed = false;
	double outtakeSpeed = -0.65;
	double outtakeBotSpeed = -.1;
	int pov;
	int deadCounter,shootCounter;
	double botMotorSpeed;
	double topMotorSpeed;
	
	double tSpeedBot;
	double tSpeedTop;
	double topSpeedError;
	double botSpeedError;	
	
	double intakePosLastError = 0;
	double intakePosSumError = 0;

	public T_Intake() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		intake = Intake.getInstance();
		oi = OI.getInstance();
		isShooting = false;
		intakePotShootConstant = 0.850;
		readyToShoot = false;
		speed = 0;
		deadCounter = 0;
		tSpeedTop = Constants.SHOOTER_TOP; //-3500
		tSpeedBot = Constants.SHOOTER_BOT; //-4200
		botMotorSpeed = 0.0002 * tSpeedBot +0.0501;
    	topMotorSpeed = 0.0002 * tSpeedTop+0.0501;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
//		SmartDashboard.putNumber("intakePotShootConstant", intakePotShootConstant);
//		intakePotShootConstant = SmartDashboard.getNumber("intakePotShootConstant");
		if(oi.getOperator().getRawButton(InputConstants.BTN_A)&&!isAPressed){
			PIDConstants.update();
			Constants.update();
			tSpeedTop = Constants.SHOOTER_TOP; //-3500
			tSpeedBot = Constants.SHOOTER_BOT;
			botMotorSpeed = 0.0002 * tSpeedBot +0.0501;
	    	topMotorSpeed = 0.0002 * tSpeedTop+0.0501;
			isAPressed = true;
		}
		else
		{
			isAPressed = false;
		}
    	
		
		if(intake.curIntakeState == intakeState.POP){
			outtakeSpeed = -0.7;
		} else {
			outtakeSpeed = -0.65;
		}
		
		if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
			speed = outtakeSpeed;
			outtakeBotSpeed = -0.1;
			isBPressed = false;
		} else if (oi.getDriver().getRawButton(InputConstants.BTN_R1)) {
			speed = 0.65;
			outtakeBotSpeed = 0.65;
			isBPressed = false;
		} else if (!isBPressed) {
			speed = 0;
			outtakeBotSpeed = 0;
		}
		if (oi.getDriver().getRawButton(InputConstants.BTN_B) && !isBPressed) {
			isBPressed = true;

		}
		if (oi.getOperator().getRawButton(InputConstants.BTN_L1) && !isL1Pressed && isBPressed) {
			isL1Pressed = true;
			speed += 0.05;
		}

		if (oi.getOperator().getRawButton(InputConstants.BTN_R1) && !isR1Pressed && isBPressed) {
			isR1Pressed = true;
			speed -= 0.05;
		}

		if (!oi.getOperator().getRawButton(InputConstants.BTN_R1)) {
			isR1Pressed = false;
		}

		if (!oi.getOperator().getRawButton(InputConstants.BTN_L1)) {
			isL1Pressed = false;
		}

		if(intake.curIntakeState == intakeState.POP){
			intake.setTopRoller(speed);
			intake.setBotRoller(outtakeBotSpeed);
		} else {
			intake.setBothRollers(speed);
		}
//		if (oi.getDriver().getRawButton(InputConstants.BTN_L2)) {
//			intake.setLeftServo(0);
//			intake.setRightServo(1);
//		} else {
			
//		}
//
//		
//		
//		
		
		
		
		
		
		
		
		
		pov = oi.getOperator().getPOV();

		if ((pov == 0 || pov == 45 || pov == 315) && !isDUpPressed) {
			intake.curIntakeState = intakeState.POP;
			isDUpPressed = true;
		} else if ((pov == 180 || pov == 135 || pov == 225) && !isDDownPressed) {

			intake.curIntakeState = intakeState.INTAKING;
			isDDownPressed = true;
		}
//		} else if (pov == 90) {
//			intake.curIntakeState = intakeState.DEAD;
//			isDRightPressed = true;
//		}
		if (pov == -1) {
			isDDownPressed = false;
			isDUpPressed = false;
			isDRightPressed = false;
		}
		
		
		
		
		
		
		//
		// // Hold R2 to intake
		//
		// SmartDashboard.putNumber("Pot Value: ", intake.getPot());
		//
		switch (intake.curIntakeState) {
		case INTAKING:
			// // Set intake down]
			intakePosError = intake.getPot() - Constants.INTAKE_POT_INTAKE;
			intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp+ (intakePosError - intakePosLastError)*PIDConstants.INTAKE_POS_Kd);
			intakePosLastError = intakePosError;
			intake.setLeftServo(0.5);
			intake.setRightServo(0.5);
			
			if(pov == 180 || pov == 135 || pov == 225){
				deadCounter++;
			}
			if(deadCounter > 25){
				intake.curIntakeState = intakeState.DEAD;
			}
			
			SmartDashboard.putNumber("intake pos - last error", intakePosError - intakePosLastError);
			 SmartDashboard.putString("WindowPosition", "Intaking/ INTAKEDOWN");

			break;
		case POP:

			/*
			 * 
			 */
			
			
			intakePosError = intake.getPot() - Constants.INTAKE_POT_POP;

			if(pov == 0 || pov == 315 || pov == 45){
				shootCounter++;
				
			}
			if(shootCounter > 25){
				intake.curIntakeState = intakeState.SHOOTING;
			}
			
			if (intake.getPot() < Constants.INTAKE_POT_POP) {
				intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp);
				SmartDashboard.putString("WindowPosition", "Shooting Pos/ INTAKE POP");
			} else {
				intake.setIntakePivot(0);
			}
			if (oi.getDriver().getRawButton(InputConstants.BTN_L2)) {
				intake.setLeftServo(0);
				intake.setRightServo(1);
			} else {
				intake.setLeftServo(0.5);
				intake.setRightServo(0.5);
			}
	    
//			if (oi.getOperator().getRawButton(InputConstants.BTN_A) && readyToShoot) {
//		    	intake.setTopRoller(topMotorSpeed + topSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
//		    	intake.setBotRoller(botMotorSpeed + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
//			}

	    	
	    	
			
//			 shooterTopError = intake.getTopPeriod() - Constants.SHOOTER_TOP;
//			 shooterBotError = intake.getTopPeriod() - Constants.SHOOTER_BOT;
//			
//			 double topSpeed = PIDConstants.INTAKE_SHOOT_Kp * shooterTopError;
//			 double botSpeed = PIDConstants.INTAKE_SHOOT_Kp * shooterBotError;
//			
//			 // Hold L2 to outtake
//			 if (readyToShoot) {
//			 intake.setTopRoller(topSpeed);
//			
//			 intake.setBotRoller(botSpeed);
//			 if (oi.getDriver().getRawButton(InputConstants.BTN_L1) &&
//			 Math.abs(topDiff - topSpeed) < 50 && Math.abs(botDiff - botSpeed)
//			 < 50) {
//			 intake.setLeftServo(0);
//			 intake.setRightServo(1);
//			 readyToShoot = false;
//			 } else {
//			 intake.setLeftServo(0.5);
//			 intake.setRightServo(0.5);
//			 }
//			 } else {
//			 intake.setBothRollers(0);
//			 }
//			 SmartDashboard.putNumber("IntakePositionError", intakePosError);
//			 SmartDashboard.putNumber("Shooter Top Error", shooterTopError);
//			 SmartDashboard.putNumber("Shooter Bot Error", shooterBotError);
//			 SmartDashboard.putNumber("Shooter Top Speed", topSpeed);
//			 SmartDashboard.putNumber("Shooter Bot Speed", botSpeed);
//			 SmartDashboard.putNumber("WindowMotorSpeed", intakePosError *
//			 PIDConstants.INTAKE_POS_Kp);
//			 SmartDashboard.putNumber("\"shooter\" top rpm",
//			 intake.getTopSpeed());
//			 SmartDashboard.putNumber("\"shooter\" bot rpm",
//			 intake.getBotSpeed());

			break;

		case DEAD:
			intake.setIntakePivot(0);
			SmartDashboard.putString("WindowPosition", "DEAD");
			deadCounter = 0;
			break;

		
		case SHOOTING:
			
			shootCounter = 0;
			intakePosError = intake.getPot() - Constants.INTAKE_POT_SHOOTING;
			intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp + (intakePosError - intakePosLastError)*PIDConstants.INTAKE_POS_Kd);
			intakePosLastError = intakePosError;
			
			topSpeedError = (tSpeedTop + intake.getTopSpeed());
	    	botSpeedError = tSpeedBot + intake.getBotSpeed();
	    	
	    	
	    	intake.setTopRoller(topMotorSpeed +topSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
	    	intake.setBotRoller(botMotorSpeed + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp);

	    	SmartDashboard.putNumber("topMotorSpeed", topMotorSpeed +topSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
	    	SmartDashboard.putNumber("botMotorSpeed", botMotorSpeed + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
	    	SmartDashboard.putNumber("topSpeedError", topSpeedError);
	    	SmartDashboard.putNumber("botSpeedError", botSpeedError);
	    	SmartDashboard.putNumber("topRPM", intake.getTopSpeed());
	    	SmartDashboard.putNumber("botRPM", intake.getBotSpeed());
	    	
	    	if(Math.abs(topSpeedError) < 74 && Math.abs(botSpeedError) < 74){
	    		readyToShoot = true;
	    	}
	    	
	    	if(readyToShoot){
	    		if (oi.getDriver().getRawButton(InputConstants.BTN_R2)) {
	    			intake.setLeftServo(0);
	    			intake.setRightServo(1);
	    			readyToShoot = false;
	    		} else{
	    			intake.setLeftServo(0.5);
	    			intake.setRightServo(0.5);
	    		}
	    		
	    	}
			
			
			
			
			
		}	
		
		SmartDashboard.putNumber("Pot", intake.getPot());
		// // //Set intake up
		// // intakePosError = intake.getPot() - Constants.INTAKE_POT_UP;
		// // intake.setIntakePivot(intakePosError*PIDConstants.INTAKE_POS_Kp);
		// //
		// // if(Math.abs(intakePosError) < 0.01){
		// // isShooting = true;
		// // } else {
		// // isShooting = false;
		// // }
		// //
		// // //PID for
		// // rpmError = Constants.INTAKE_SHOOT_SPEED - intake.getRPM();
		// //
		// // if(oi.getDriver().getRawButton(InputConstants.BTN_L1)){
		// // isL1Pressed = true;
		// // isR1Pressed = false;
		// // }
		// // if(oi.getDriver().getRawButton(InputConstants.BTN_R1)){
		// // isL1Pressed = false;
		// // isR1Pressed = true;
		// // }
		// //
		// // //Press L1 for shooting
		// // if(isL1Pressed){
		// // intake.setTopRoller(intake.getPower() + rpmError *
		// // PIDConstants.INTAKE_SHOOT_Kp);
		// // }
		// // //Press R1 for outtake
		// // else if (isR1Pressed){
		// // intake.setTopRoller(0.75);
		// // }
		// //
		// // Press X to shoot
		// }
		//
		// //
		// intake.setBothRollers(oi.getDriver().getRawAxis(InputConstants.AXIS_LEFT_Y));
		// //
		// intake.setIntakePivot(oi.getDriver().getRawAxis(InputConstants.AXIS_RIGHT_Y));
		// // if(oi.getDriver().getRawButton(InputConstants.BTN_L2)){
		// // n+=0.05;
		// // }
		// // if(oi.getDriver().getRawButton(InputConstants.BTN_R2)){
		// //
		// // n-=0.05;
		// // }
		// // SmartDashboard.putNumber("n value of intake servo", n);
		// // intake.setIntakeServos(n);

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
