package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Intake extends Subsystem {

	private static Intake instance;
	private Victor topRoller, botRoller, intakePivot;
	private Servo leftFeeder, rightFeeder;
	public servoPosition flipperServoPos;
	public intakeState curIntakeState;
	AnalogPotentiometer intakePot;
	double RPMfactor;

	static Counter optTopCounter, optBotCounter;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static Intake getInstance() {
		if (instance == null) {
			instance = new Intake();
		}
		return instance;
	}

	private Intake() {
		RPMfactor = 1/60;//CHANGE
		topRoller = new Victor(ElectricalConstants.INTAKE_ROLLER_TOP);
		botRoller = new Victor(ElectricalConstants.INTAKE_ROLLER_BOT);
		leftFeeder = new Servo(ElectricalConstants.INTAKE_FEEDER_LEFTSERVO);
		rightFeeder = new Servo(ElectricalConstants.INTAKE_FEEDER_RIGHTSERVO);
		intakePivot = new Victor(ElectricalConstants.INTAKE_PIVOT);
		intakePot = new AnalogPotentiometer(ElectricalConstants.INTAKE_POT);
		optTopCounter = new Counter(ElectricalConstants.OPTICAL_TOP);
		optBotCounter = new Counter(ElectricalConstants.OPTICAL_BOTTOM);

		curIntakeState = intakeState.INTAKING;
		optTopCounter.setMaxPeriod(5);
		optTopCounter.setSemiPeriodMode(true);
		optTopCounter.reset();
		optBotCounter.setMaxPeriod(5);
		optBotCounter.setSemiPeriodMode(true);
		optBotCounter.reset();

	}
	
	public double getTopSpeed(){
		return 	RPMfactor/optTopCounter.getPeriod();
	}
	public double getTopPeriod(){
		return optTopCounter.getPeriod();
	}public double getBotSpeed(){
		return 	RPMfactor/optTopCounter.getPeriod();
	}
	public double getBotPeriod(){
		return optTopCounter.getPeriod();
	}
	public double getPot(){
		return intakePot.get();
	}
	
	public enum intakeState{
		INTAKING, SHOOTING, DEAD
	}

	public enum servoPosition {
		OUT, IN
	}
	public void setPinballFlippers(servoPosition s) {
		if (s == servoPosition.OUT) {
			setRightServo(Constants.FLIPPER_SERVO_OUTA);
			setLeftServo(Constants.FLIPPER_SERVO_OUTB);
		} else if (s == servoPosition.IN) {
			setRightServo(Constants.FLIPPER_SERVO_INA);
			setLeftServo(Constants.FLIPPER_SERVO_INB);
		}
		flipperServoPos = s;

	}
	public void setIntakePivot(double v){
		intakePivot.set(v);
	}
	
	
	public void setServos(double value) {
		leftFeeder.set(value);
		rightFeeder.set(value);
	}
	
	public void setRightServo(double value){
		rightFeeder.set(value);
	}
	
	public void setLeftServo(double value){
		leftFeeder.set(value);
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
	

//	
//	public double getPower(){
//		//Temp, need to find RPM to power
//		return getRPM() * 20;
//	}
	
	public void initDefaultCommand() {

	}
	

}