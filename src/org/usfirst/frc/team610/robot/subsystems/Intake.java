package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Intake extends Subsystem {

	private static Intake instance;
	Victor topRoller, botRoller, intakePivot;
	Servo leftFeeder, rightFeeder;
	servoPosition curServoPos;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static Intake getInstance() {
		if (instance == null) {
			instance = new Intake();
		}
		return instance;
	}

	private Intake() {
		topRoller = new Victor(ElectricalConstants.INTAKE_ROLLER_TOP);
		botRoller = new Victor(ElectricalConstants.INTAKE_ROLLER_BOT);
		leftFeeder = new Servo(ElectricalConstants.INTAKE_FEEDER_LEFTSERVO);
		rightFeeder = new Servo(ElectricalConstants.INTAKE_FEEDER_RIGHTSERVO);
		intakePivot = new Victor(ElectricalConstants.INTAKE_PIVOT);

	}

	enum servoPosition {
		UP, DOWN
	}
	public void setIntakePos(servoPosition s) {
		if (s == servoPosition.DOWN) {
			setIntakeServos(Constants.INTAKE_SERVO_DOWN);
		} else if (s == servoPosition.UP) {
			setIntakeServos(Constants.INTAKE_SERVO_UP);
			
		}
		curServoPos = s;

	}
	public void setIntakePivot(double v){
		intakePivot.set(v);
	}
	private void setIntakeServos(double value) {
		leftFeeder.set(value);
		rightFeeder.set(value);
	}
	//Flip in Code or Electrically?
	public void setBothRollers(double v){
		setTopRoller(v);
		setBotRoller(v);
	}
	
	public void setTopRoller(double v) {
		topRoller.set(v);
	}
	public void setBotRoller(double v){
		botRoller.set(v);
	}

	public void initDefaultCommand() {

	}
	

}