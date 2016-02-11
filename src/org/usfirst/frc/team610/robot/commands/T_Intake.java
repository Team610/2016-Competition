package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.InputConstants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;
import org.usfirst.frc.team610.robot.subsystems.Intake.servoPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class T_Intake extends Command {
	Intake intake;
	OI oi;
	double n = 0;
	double intakePosError;
	double intakePosKp = 0.01;
	boolean isShooting;
	double rpmError = 0;
	boolean isR1Pressed = false;
	boolean isR2Pressed = false;
	boolean isL1Pressed = false;
	boolean isL2Pressed = false;
	boolean isDUpPressed = false;
	boolean isDDownPressed = false;
	int pov;

	public T_Intake() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		intake = Intake.getInstance();
		oi = OI.getInstance();
		isShooting = false;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		pov = oi.getOperator().getPOV();
		
		if ((pov == 0 || pov == 325 || pov == 45) && !isDUpPressed) {
			intake.curIntakeState = intakeState.INTAKING;
			isDUpPressed = true;
		} else if ((pov == 180 || pov == 135 || pov == 225) && !isDDownPressed) {
			intake.curIntakeState = intakeState.SHOOTING;
			isDDownPressed = true;
		}
		
		if(pov == -1){
			isDDownPressed = false;
			isDUpPressed = false;
		}

		switch (intake.curIntakeState) {
		case INTAKING:
			//Set intake down 
			intakePosError = intake.getPot() - Constants.INTAKE_POT_DOWN;
			intake.setIntakePivot(intakePosError*PIDConstants.INTAKE_POS_Kp);
			
			//Hold R2 to intake
			if(oi.getDriver().getRawButton(InputConstants.BTN_R2)){
				intake.setBothRollers(0.75);
			} 
			//Hold L2 to outtake									
			else if (oi.getDriver().getRawButton(InputConstants.BTN_L2)){
				intake.setBothRollers(-0.75);
			} else {
				intake.setBothRollers(0);
			}
			
			break;
		case SHOOTING:
			//Set intake up
			intakePosError = intake.getPot() - Constants.INTAKE_POT_UP;
			intake.setIntakePivot(intakePosError*PIDConstants.INTAKE_POS_Kp);
			
			if(Math.abs(intakePosError) < 0.01){
				isShooting = true;
			} else {
				isShooting = false;
			}
			
			//PID for 
			rpmError = Constants.INTAKE_SHOOT_SPEED - intake.getRPM();
			
			if(oi.getDriver().getRawButton(InputConstants.BTN_L1)){
				isL1Pressed = true;
				isR1Pressed = false;
			}
			if(oi.getDriver().getRawButton(InputConstants.BTN_R1)){
				isL1Pressed = false;
				isR1Pressed = true;
			}
			
			//Press L1 for shooting
			if(isL1Pressed){
				intake.setTopRoller(intake.getPower() + rpmError * PIDConstants.INTAKE_SHOOT_Kp);
			} 
			//Press R1 for outtake
			else if (isR1Pressed){
				intake.setTopRoller(0.75);
			}
			
			//Press X to shoot
			if(oi.getDriver().getRawButton(InputConstants.BTN_X)){
				intake.setLeftServo(Constants.FLIPPER_SERVO_OUTA);
				intake.setRightServo(Constants.FLIPPER_SERVO_OUTB);
			} else {
				intake.setLeftServo(Constants.FLIPPER_SERVO_INA);
				intake.setRightServo(Constants.FLIPPER_SERVO_INB);
			}
			
		}
			

		// intake.setBothRollers(oi.getDriver().getRawAxis(InputConstants.AXIS_LEFT_Y));
		// intake.setIntakePivot(oi.getDriver().getRawAxis(InputConstants.AXIS_RIGHT_Y));
		// if(oi.getDriver().getRawButton(InputConstants.BTN_L2)){
		// n+=0.05;
		// }
		// if(oi.getDriver().getRawButton(InputConstants.BTN_R2)){
		//
		// n-=0.05;
		// }
		// SmartDashboard.putNumber("n value of intake servo", n);
		// intake.setIntakeServos(n);

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
