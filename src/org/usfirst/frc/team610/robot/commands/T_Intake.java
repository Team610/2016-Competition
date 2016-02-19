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
	boolean isYPressed = false;
	boolean isDUpPressed = false;
	boolean isDDownPressed = false;
	boolean isDRightPressed = false;
	boolean isXPressed = false;
	double outtakeSpeed = -0.65;
	double outtakeBotSpeed = -.1;
	int pov;
	int deadCounter, shootCounter;
	double botMotorSpeed;
	double topMotorSpeed;

	double tSpeedBot;
	double tSpeedTop;
	double topSpeedError;
	double botSpeedError;
	double topSpeed;
	double botSpeed;
	double topSpeedErrDiff;
	double botSpeedErrDiff;
	double topLastError = 0;
	double botLastError = 0;

	double intakePosLastError = 0;
	double intakePosSumError = 0;
	double RPMTrim = 0;
	double intakePosDiffError;
	double intakeSpeed;
	double tAngle;

	public T_Intake() {
		intake = Intake.getInstance();
		oi = OI.getInstance();
		isShooting = false;
		intakePotShootConstant = 0.850;
		readyToShoot = false;
		topSpeed = 0;
		botSpeed = 0;
		speed = 0;
		deadCounter = 0;
		tSpeedTop = Constants.SHOOTER_TOP; // -3500
		tSpeedBot = Constants.SHOOTER_BOT; // -4200

		intake.curIntakeState = intakeState.DEAD;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("New Window Pot", intake.getPot());
		

		if (oi.getOperator().getRawButton(InputConstants.BTN_X) && !isXPressed) {
			Constants.update();
			tSpeedTop = Constants.SHOOTER_TOP; // -3500
			tSpeedBot = Constants.SHOOTER_BOT;
			// y = 0.0001x + 0.0294 - Top
			// y = 0.0001x + 0.016 - Bot
			botMotorSpeed = 0.00016 * tSpeedBot - 0.016;
			topMotorSpeed = 0.00016 * tSpeedTop - 0.0294;
			isXPressed = true;
		} else {
			isXPressed = false;
		}
		tSpeedBot = Constants.SHOOTER_BOT;
		tSpeedTop = Constants.SHOOTER_TOP;
		botMotorSpeed = 0.00016 * tSpeedBot - 0.016;
		topMotorSpeed = 0.00016 * tSpeedTop - 0.0294;

		pov = oi.getOperator().getPOV();

		// B for intaking,
		// A for dead
		// X for shooting
		// Y for Pop
		SmartDashboard.putNumber("Top RPM", intake.getTopSpeed());
		SmartDashboard.putNumber("Bot RPM", intake.getBotSpeed());
		SmartDashboard.putNumber("Pot", intake.getPot());
		if (oi.getOperator().getRawButton(InputConstants.BTN_B)) {
			intake.curIntakeState = intakeState.INTAKING;
			tAngle = Constants.INTAKE_POT_INTAKE;
			outtakeSpeed = Constants.ROLLER_OUTTAKE;
			intakeSpeed = Constants.ROLLER_INTAKE;
			SmartDashboard.putString("State", "Intaking");
		}
		if (oi.getOperator().getRawButton(InputConstants.BTN_A)) {
			intake.curIntakeState = intakeState.DEAD;
			tAngle = Constants.INTAKE_POT_DEAD;
			outtakeSpeed = Constants.ROLLER_OUTTAKE;
			intakeSpeed = Constants.ROLLER_INTAKE;

			SmartDashboard.putString("State", "Dead");
		}
		if (oi.getOperator().getRawButton(InputConstants.BTN_X)) {
			intake.curIntakeState = intakeState.SHOOTING;
			//Plz god change this
			tAngle = Constants.INTAKE_POT_SHOOTING;
			SmartDashboard.putString("State", "Shooting");
		}
		if (oi.getOperator().getRawButton(InputConstants.BTN_Y)) {
			intake.curIntakeState = intakeState.POP;
			tAngle = Constants.INTAKE_POT_POP;
			topSpeed = Constants.ROLLER_TOP_POP;
			botSpeed = Constants.ROLLER_BOT_POP;
			intakeSpeed = Constants.ROLLER_INTAKE;
			SmartDashboard.putString("State", "Pop");
		}

		switch (intake.curIntakeState) {
		case INTAKING:
			if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
				speed = outtakeSpeed;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_OUT);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_OUT);
			} else if (oi.getDriver().getRawButton(InputConstants.BTN_R1)) {
				speed = intakeSpeed;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
			} else {
				speed = 0;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
			}

			intake.setBothRollers(speed);

			break;
		case POP:

			if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
				intake.setTopRoller(topSpeed);
				intake.setBotRoller(botSpeed);
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_OUT);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_OUT);
			} else if(oi.getDriver().getRawButton(InputConstants.BTN_R1)){
				intake.setTopRoller(intakeSpeed);
				intake.setBotRoller(intakeSpeed);
			} else {
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
				intake.setTopRoller(0);
				intake.setBotRoller(0);
			}
			break;

		case DEAD:
			if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
				speed = outtakeSpeed;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_OUT);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_OUT);
			} else if (oi.getDriver().getRawButton(InputConstants.BTN_R1)) {
				speed = intakeSpeed;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
			} else {
				speed = 0;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
			}

			intake.setBothRollers(speed);
			break;

		case SHOOTING:
			if ((pov == 0 || pov == 45 || pov == 315) && !isDUpPressed) {
				RPMTrim += 50;
				isDUpPressed = true;

			} else if ((pov == 180 || pov == 135 || pov == 225) && !isDDownPressed) {
				RPMTrim -= 50;
				isDDownPressed = true;
			}

			if (pov == -1) {
				isDUpPressed = false;
				isDDownPressed = false;
			}
			if (oi.getOperator().getRawButton(InputConstants.BTN_L2)) {
				RPMTrim = 0;
			}

			shootCounter = 0;

			topSpeedError = tSpeedTop + intake.getTopSpeed();
			botSpeedError = tSpeedBot + intake.getBotSpeed();

			topSpeedErrDiff = topSpeedError - topLastError;
			botSpeedErrDiff = botSpeedError - botLastError;

			topSpeed = topMotorSpeed + topSpeedError * PIDConstants.INTAKE_SHOOT_Kp
					- topSpeedErrDiff * PIDConstants.INTAKE_SHOOT_Kd;
			botSpeed = botMotorSpeed + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp
					- botSpeedErrDiff * PIDConstants.INTAKE_SHOOT_Kd;

			SmartDashboard.putNumber("TopSpeed", topSpeed);
			SmartDashboard.putNumber("BotSpeed", botSpeed);

			if (Math.abs(intakePosError) < 0.1) {
				intake.setTopRoller(topSpeed);
				intake.setBotRoller(botSpeed);
			} else {
				intake.setBothRollers(0);
			}

			topLastError = topSpeedError;
			botLastError = botSpeedError;

			if (Math.abs(topSpeedError) < 100 && Math.abs(botSpeedError) < 100) {
				readyToShoot = true;
			}

			if (readyToShoot) {
				if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
					intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_OUT);
					intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_OUT);
					readyToShoot = false;
				} else {
					intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
					intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
				}

			} else {
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
			}
			SmartDashboard.putBoolean("Ready to Shoot", readyToShoot);
			break;
		}
		// End of switch
		if (!intake.curIntakeState.equals(intakeState.DEAD)) {
			intakePosError = intake.getPot() - tAngle;
			intakePosDiffError = intakePosError - intakePosLastError;
			intake.setIntakePivot(
					intakePosError * PIDConstants.INTAKE_POS_Kp + intakePosDiffError * PIDConstants.INTAKE_POS_Kd);
			intakePosLastError = intakePosError;
		} else {
			if (intake.getPot() > Constants.INTAKE_POT_DEAD) {
				intakePosError = intake.getPot() - tAngle;
				intakePosDiffError = intakePosError - intakePosLastError;
				intake.setIntakePivot(
						intakePosError * PIDConstants.INTAKE_POS_Kp + intakePosDiffError * PIDConstants.INTAKE_POS_Kd);
				intakePosLastError = intakePosError;
			} else {
				intake.setIntakePivot(0);
			}
		}
		SmartDashboard.putNumber("Top RPMError", topSpeedError);
		SmartDashboard.putNumber("Bot RPMError", botSpeedError);
		SmartDashboard.putNumber("WindowMotorPot", intake.getPot());

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
