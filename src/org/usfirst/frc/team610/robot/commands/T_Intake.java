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
	boolean isAPressed = false;
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

	double intakePosLastError = 0;
	double intakePosSumError = 0;
	double RPMTrim = 0;
	double intakePosDerivative;
	double intakeSpeed;
	double tAngle;

	public T_Intake() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
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
		botMotorSpeed = 0.0002 * tSpeedBot + 0.0501;
		topMotorSpeed = 0.0002 * tSpeedTop + 0.0501;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		if (oi.getOperator().getRawButton(InputConstants.BTN_A) && !isAPressed) {
			PIDConstants.update();
			Constants.update();
			tSpeedTop = Constants.SHOOTER_TOP; // -3500
			tSpeedBot = Constants.SHOOTER_BOT;
			botMotorSpeed = 0.0002 * tSpeedBot + 0.0501;
			topMotorSpeed = 0.0002 * tSpeedTop + 0.0501;
			isAPressed = true;
		} else {
			isAPressed = false;
		}

		pov = oi.getOperator().getPOV();

		// if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
		// speed = outtakeSpeed;
		// outtakeBotSpeed = -0.1;
		// } else if (oi.getDriver().getRawButton(InputConstants.BTN_R1)) {
		// speed = 0.65;
		// outtakeBotSpeed = 0.65;
		// }else{
		// speed = 0;
		// }

		// B for intaking,
		// A for dead
		// X for shooting
		// Y for Pop

		if (oi.getOperator().getRawButton(InputConstants.BTN_B)) {
			intake.curIntakeState = intakeState.INTAKING;
			tAngle = Constants.INTAKE_POT_INTAKE;
			outtakeSpeed = Constants.ROLLER_OUTTAKE;
			intakeSpeed = Constants.ROLLER_INTAKE;
		}
		if (oi.getOperator().getRawButton(InputConstants.BTN_A)) {
			intake.curIntakeState = intakeState.DEAD;
			tAngle = Constants.INTAKE_POT_DEAD;
			outtakeSpeed = Constants.ROLLER_OUTTAKE;
			intakeSpeed = Constants.ROLLER_INTAKE;
		}
		if (oi.getOperator().getRawButton(InputConstants.BTN_X)) {
			intake.curIntakeState = intakeState.SHOOTING;
			tAngle = Constants.INTAKE_POT_SHOOTING;
		}
		if (oi.getOperator().getRawButton(InputConstants.BTN_Y)) {
			intake.curIntakeState = intakeState.POP;
			tAngle = Constants.INTAKE_POT_POP;
			topSpeed = Constants.ROLLER_TOP_POP;
			botSpeed = Constants.ROLLER_BOT_POP;
		}

		switch (intake.curIntakeState) {
		case INTAKING:
			if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
				speed = outtakeSpeed;
			} else if (oi.getDriver().getRawButton(InputConstants.BTN_R1)) {
				speed = intakeSpeed;
			} else {
				speed = 0;
			}
			intake.setLeftServo(0.5);
			intake.setRightServo(0.5);

			intake.setBothRollers(speed);

			break;
		case POP:

			if (oi.getDriver().getRawButton(InputConstants.BTN_L2)) {
				intake.setLeftServo(0);
				intake.setRightServo(1);
			} else {
				intake.setLeftServo(0.5);
				intake.setRightServo(0.5);
			}

			intake.setTopRoller(topSpeed);
			intake.setBotRoller(botSpeed);

			break;

		case DEAD:
			if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
				speed = outtakeSpeed;
			} else if (oi.getDriver().getRawButton(InputConstants.BTN_R1)) {
				speed = intakeSpeed;
			} else {
				speed = 0;
			}
			intake.setLeftServo(0.5);
			intake.setRightServo(0.5);

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

			topSpeedError = (tSpeedTop + intake.getTopSpeed());
			botSpeedError = tSpeedBot + intake.getBotSpeed();

			topSpeed = topMotorSpeed + RPMTrim + topSpeedError * PIDConstants.INTAKE_SHOOT_Kp;
			botSpeed = botMotorSpeed + RPMTrim + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp;

			intake.setTopRoller(topSpeed);
			intake.setBotRoller(botSpeed);

			// SmartDashboard.putNumber("topMotorSpeed", topMotorSpeed
			// +topSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
			// SmartDashboard.putNumber("botMotorSpeed", botMotorSpeed +
			// botSpeedError * PIDConstants.INTAKE_SHOOT_Kp);
			// SmartDashboard.putNumber("topSpeedError", topSpeedError);
			// SmartDashboard.putNumber("botSpeedError", botSpeedError);
			// SmartDashboard.putNumber("topRPM", intake.getTopSpeed());
			// SmartDashboard.putNumber("botRPM", intake.getBotSpeed());

			if (Math.abs(topSpeedError) < 50 && Math.abs(botSpeedError) < 50) {
				readyToShoot = true;
			}

			if (readyToShoot) {
				if (oi.getDriver().getRawButton(InputConstants.BTN_L1)) {
					intake.setLeftServo(0);
					intake.setRightServo(1);
					readyToShoot = false;
				} else {
					intake.setLeftServo(0.5);
					intake.setRightServo(0.5);
				}

			}
			break;
		}
		// End of switch
		if (!intake.curIntakeState.equals(intakeState.DEAD)) {
			intakePosError = intake.getPot() - tAngle;
			intakePosDerivative = intakePosError - intakePosLastError;
			intake.setIntakePivot(
					intakePosError * PIDConstants.INTAKE_POS_Kp + intakePosDerivative * PIDConstants.INTAKE_POS_Kd);
			intakePosLastError = intakePosError;
		} else {
			intake.setIntakePivot(0);
		}

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
