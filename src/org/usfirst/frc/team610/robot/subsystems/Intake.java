package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
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
	public servoPosition flipperServoPos;
	public intakeState curIntakeState;
	AnalogPotentiometer intakePot;

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
		intakePot = new AnalogPotentiometer(ElectricalConstants.INTAKE_POT);

	}
	
	public double getPot(){
		return intakePot.get();
	}
	
	public enum intakeState{
		INTAKING, SHOOTING
	}

	public enum servoPosition {
		OUT, IN
	}
	public void setPinballFlippers(servoPosition s) {
		if (s == servoPosition.OUT) {
			setIntakeServos(Constants.FLIPPER_SERVO_OUT);
		} else if (s == servoPosition.IN) {
			setIntakeServos(Constants.FLIPPER_SERVO_IN);
			
		}
		flipperServoPos = s;

	}
	public void setIntakePivot(double v){
		intakePivot.set(v);
	}
	
	
	public void setIntakeServos(double value) {
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