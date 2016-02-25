package org.usfirst.frc.team610.robot.commands;

import java.util.ArrayList;
import java.util.Collections;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
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
	private double intakePosError;
	private boolean readyToShoot;
	private double speed;
	private boolean isDUpPressed = false;
	private boolean isDDownPressed = false;
	private double outtakeSpeed = -0.65;
	private int pov;
	private double botMotorSpeed;
	private double topMotorSpeed;
	private double tSpeedBot;
	private double tSpeedTop;
	private double topSpeedError;
	private double botSpeedError;
	private double topSpeed;
	private double botSpeed;
	private double topSpeedErrDiff;
	private double botSpeedErrDiff;
	private double topLastError = 0;
	private double botLastError = 0;

	private double intakePosLastError = 0;
	private double RPMTrim = 0;
	private double intakePosDiffError;
	private double intakeSpeed;
	private double tAngle;
	private int popCounter = 0;
	double potValue;
	
	private ArrayList <Double> potValues = new ArrayList <Double>();
	private ArrayList <Double> sortedPotValues = new ArrayList <Double>();

	public T_Intake() {
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
		tAngle = Constants.INTAKE_POT_POP;
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		//Median Filter
		if(potValues.size() > 4){
			potValues.remove(potValues.size() - 1);
		}
		potValues.add(0, intake.getPot());
		sortedPotValues = (ArrayList<Double>) potValues.clone();
		Collections.sort(sortedPotValues);
		if(potValues.size() > 3){
			potValue = sortedPotValues.get(2);
		}
		
		
		tSpeedBot = Constants.SHOOTER_BOT;
		tSpeedTop = Constants.SHOOTER_TOP;
		botMotorSpeed = 0.000147 * tSpeedBot - 0.016;
		topMotorSpeed = 0.000146 * tSpeedTop - 0.0294;

		pov = oi.getOperator().getPOV();

		// B for intaking,
		// A for dead
		// X for shooting
		// Y for Pop

		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_B)) {
			intake.curIntakeState = intakeState.INTAKING;
			tAngle = Constants.INTAKE_POT_INTAKE + Constants.INTAKE_POT_OFFSET;
			outtakeSpeed = Constants.INTAKE_OUTTAKE_POWER;
			intakeSpeed = Constants.INTAKE_INTAKE_POWER;
			SmartDashboard.putString("State", "Intaking");
		}
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_A)) {
			intake.curIntakeState = intakeState.DEAD;
			tAngle = Constants.INTAKE_POT_DEAD + Constants.INTAKE_POT_OFFSET;
			outtakeSpeed = Constants.INTAKE_OUTTAKE_POWER;
			intakeSpeed = Constants.INTAKE_INTAKE_POWER;

			SmartDashboard.putString("State", "Dead");
		}
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_X)) {
			intake.curIntakeState = intakeState.SHOOTING;
			// Plz god change this
			tAngle = Constants.INTAKE_POT_SHOOTING + Constants.INTAKE_POT_OFFSET;
			SmartDashboard.putString("State", "Shooting");
		}
		if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_Y)) {
			intake.curIntakeState = intakeState.POP;
			tAngle = Constants.INTAKE_POT_POP + Constants.INTAKE_POT_OFFSET;
			topSpeed = Constants.INTAKE_TOP_POP_POWER;
			botSpeed = Constants.INTAKE_BOT_POP_POWER;
			intakeSpeed = Constants.INTAKE_INTAKE_POWER;
			SmartDashboard.putString("State", "Pop");
		}
		if(oi.getOperator().getRawButton(LogitechF310Constants.BTN_L2)){
			intake.curIntakeState = intakeState.INTAKING;
			tAngle = Constants.INTAKE_POT_INTAKE + Constants.INTAKE_POT_OFFSET + 0.09;
			outtakeSpeed = Constants.INTAKE_OUTTAKE_POWER;
			intakeSpeed = Constants.INTAKE_INTAKE_POWER;
			SmartDashboard.putString("State", "Intaking PLUS");
		}

		switch (intake.curIntakeState) {
		case INTAKING:
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				speed = outtakeSpeed;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_OUT);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_OUT);
			} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)) {
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
			tAngle = Constants.INTAKE_POT_POP;
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				intake.setTopRoller(topSpeed);
				intake.setBotRoller(botSpeed);
				popCounter++;
				if (popCounter > 5) {
					intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_OUT);
					intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_OUT);
				}
			} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)) {
				intake.setTopRoller(intakeSpeed);
				intake.setBotRoller(intakeSpeed);
			} else {
				popCounter = 0;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
				intake.setTopRoller(0);
				intake.setBotRoller(0);
			}
			break;

		case DEAD:
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				speed = outtakeSpeed;
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_OUT);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_OUT);
			} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)) {
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
			if (oi.getOperator().getRawButton(LogitechF310Constants.BTN_L2)) {
				RPMTrim = 0;
			}

			topSpeedError = tSpeedTop + intake.getTopSpeed();
			botSpeedError = tSpeedBot + intake.getBotSpeed();

			topSpeedErrDiff = topSpeedError - topLastError;
			botSpeedErrDiff = botSpeedError - botLastError;

			topSpeed = topMotorSpeed + topSpeedError * PIDConstants.INTAKE_SHOOT_Kp
					- topSpeedErrDiff * PIDConstants.INTAKE_SHOOT_Kd;
			botSpeed = botMotorSpeed + botSpeedError * PIDConstants.INTAKE_SHOOT_Kp
					- botSpeedErrDiff * PIDConstants.INTAKE_SHOOT_Kd;


			// 0.1
			if (Math.abs(intakePosError) < 0.1) {
				intake.setTopRoller(topSpeed);
				intake.setBotRoller(botSpeed);
			} else {
				intake.setBothRollers(0);
			}

			topLastError = topSpeedError;
			botLastError = botSpeedError;

			// if (Math.abs(topSpeedError) < 100 && Math.abs(botSpeedError) <
			// 100) {
			readyToShoot = true;
			// }

			if (readyToShoot) {
				if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
					intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_OUT);
					intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_OUT);
					readyToShoot = false;
				} else {
					intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN - 0.05);
					intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN + 0.05);
				}

			} else {
				intake.setRightServo(Constants.SHOOTER_SERVO_RIGHT_IN);
				intake.setLeftServo(Constants.SHOOTER_SERVO_LEFT_IN);
			}
			SmartDashboard.putBoolean("Ready to Shoot", readyToShoot);
			break;
		}
		// End of switch
		
		intakePosError = potValue - tAngle;
		if (!intake.curIntakeState.equals(intakeState.DEAD) && !intake.curIntakeState.equals(intakeState.POP)) {
			intakePosError = potValue - tAngle;
			intakePosDiffError = intakePosError - intakePosLastError;

			intake.setIntakePivot(
					intakePosError * PIDConstants.INTAKE_POS_Kp + intakePosDiffError * PIDConstants.INTAKE_POS_Kd);
			intakePosLastError = intakePosError;

		} else {
			if (intake.curIntakeState.equals(intakeState.DEAD)) {
				if (Math.abs(intakePosError) > 0.05) {
					intakePosError = potValue - tAngle;
					intakePosDiffError = intakePosError - intakePosLastError;
					intake.setIntakePivot(intakePosError * PIDConstants.INTAKE_POS_Kp
							+ intakePosDiffError * PIDConstants.INTAKE_POS_Kd);
					intakePosLastError = intakePosError;
				} else {
					intake.setIntakePivot(0);
				}
			} else if (intake.curIntakeState.equals(intakeState.POP)) {
				if (Math.abs(intakePosError) > 0.05) {
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
