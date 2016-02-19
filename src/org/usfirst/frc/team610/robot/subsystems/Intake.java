package org.usfirst.frc.team610.robot.subsystems;

import java.util.ArrayList;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Counter;
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
	private AnalogPotentiometer intakePot;
	private ArrayList<Double> topRollers, botRollers;
	double RPMfactor;
	private double topPeriod;
	private double botPeriod;

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
		topRollers = new ArrayList<Double>();
		botRollers = new ArrayList<Double>();
		RPMfactor = 60;// CHANGE
		topRoller = new Victor(ElectricalConstants.INTAKE_ROLLER_TOP);
		botRoller = new Victor(ElectricalConstants.INTAKE_ROLLER_BOT);
		leftFeeder = new Servo(ElectricalConstants.INTAKE_FEEDER_LEFTSERVO);
		rightFeeder = new Servo(ElectricalConstants.INTAKE_FEEDER_RIGHTSERVO);
		intakePivot = new Victor(ElectricalConstants.INTAKE_PIVOT);
		intakePot = new AnalogPotentiometer(ElectricalConstants.INTAKE_POT);
		optTopCounter = new Counter(ElectricalConstants.OPTICAL_TOP);
		optBotCounter = new Counter(ElectricalConstants.OPTICAL_BOTTOM);
		// optTopCounter.setMaxPeriod(maxPeriod);
		curIntakeState = intakeState.INTAKING;
		optTopCounter.setMaxPeriod(.1);
		optTopCounter.setDistancePerPulse(1);
		optTopCounter.setSamplesToAverage(1);
		optTopCounter.reset();
		optBotCounter.setMaxPeriod(.1);
		optBotCounter.setDistancePerPulse(1);
		optBotCounter.setSamplesToAverage(1);
		optBotCounter.reset();
		topPeriod = Double.POSITIVE_INFINITY;
		botPeriod = Double.POSITIVE_INFINITY;

	}

//	public void updateLists() {
//		if (topRollers.size() > 50) {
//			topRollers.add(0, optTopCounter.getPeriod());
//			topRollers.remove(topRollers.size());
//			botRollers.add(0, optBotCounter.getPeriod());
//			botRollers.remove(topRollers.size());
//		} else {
//
//		}
//
//	}

	public double getTopSpeed() {
		return RPMfactor/getTopPeriod();
	}

	public double getTopPeriod() {
		double topPeriod = optTopCounter.getPeriod();

		if (topPeriod < 1.66e-4) {
			return this.topPeriod;
		} else {
			this.topPeriod = topPeriod;
			return topPeriod;
		}
	}

	public double getBotSpeed() {
		return RPMfactor/getBotPeriod();
	}

	public double getBotPeriod() {
		double botPeriod = optBotCounter.getPeriod();

		if (botPeriod < 1.66e-4) {
			return this.botPeriod;
		} else {
			this.botPeriod = botPeriod;
			return botPeriod;
		}
	}

	public double getPot() {
		return intakePot.get();
	}

	public enum intakeState {
		INTAKING, POP, DEAD, SHOOTING
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

	public void setIntakePivot(double v) {
		intakePivot.set(v);
	}

	public void setServos(double value) {
		leftFeeder.set(value);
		rightFeeder.set(value);
	}

	public void setRightServo(double value) {
		rightFeeder.set(value);
	}

	public void setLeftServo(double value) {
		leftFeeder.set(value);
	}

	// Flip in Code or Electrically?
	public void setBothRollers(double v) {
		setTopRoller(v);
		setBotRoller(v);
	}

	public void setTopRoller(double v) {
		topRoller.set(v);
	}

	public void setBotRoller(double v) {
		botRoller.set(v);
	}

	//
	// public double getPower(){
	// //Temp, need to find RPM to power
	// return getRPM() * 20;
	// }

	public void initDefaultCommand() {

	}

}