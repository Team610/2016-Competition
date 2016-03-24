package org.usfirst.frc.team610.robot.subsystems;

import org.sixten.chareslib.PID;
import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Intake extends Subsystem {

	private static Intake instance;
	private PowerDistributionPanel pdb;
	private DigitalInput optical;
	private Victor topRoller, botRoller;
	private TalonSRX intakePivot;
	private Victor leftFeeder, rightFeeder;
	public servoPosition flipperServoPos;
	public intakeState curIntakeState;
	private AnalogPotentiometer intakePot;
	double RPMfactor;
	private double topPeriod;
	private double botPeriod;
	private OI oi;
	private PID pivotPID, topPID, botPID;
	
	private int waitCounter = 100;

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
		oi = OI.getInstance();
		
		pdb = new PowerDistributionPanel();
		
		RPMfactor = 60;// CHANGE
		topRoller = new Victor(ElectricalConstants.INTAKE_ROLLER_TOP);
		botRoller = new Victor(ElectricalConstants.INTAKE_ROLLER_BOT);
		leftFeeder = new Victor(ElectricalConstants.INTAKE_FEEDER_LEFT);
		rightFeeder = new Victor(ElectricalConstants.INTAKE_FEEDER_RIGHT);
		intakePivot = new TalonSRX(ElectricalConstants.INTAKE_PIVOT);
		intakePot = new AnalogPotentiometer(ElectricalConstants.INTAKE_POT);
		optTopCounter = new Counter(ElectricalConstants.OPTICAL_TOP);
		optBotCounter = new Counter(ElectricalConstants.OPTICAL_BOTTOM);
		// optTopCounter.setMaxPeriod(maxPeriod);
		curIntakeState = intakeState.POP;
		
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
		optical = new DigitalInput(ElectricalConstants.OPTICAL_INTAKE);
		
		pivotPID = new PID(PIDConstants.INTAKE_POS_Kp, 0, PIDConstants.INTAKE_POS_Kd, -1, 1);
		topPID = new PID(PIDConstants.INTAKE_SHOOT_Kp, 0, PIDConstants.INTAKE_SHOOT_Kd, -1, 1);
		botPID = new PID(PIDConstants.INTAKE_SHOOT_Kp, 0, PIDConstants.INTAKE_SHOOT_Kd, -1, 1);
		
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
	
	public double getPivotCurrent(){
		return pdb.getCurrent(Constants.PDB_INTAKE_PIVOT);
	}

	public double getPot() {
		return intakePot.get();
	}

	public enum intakeState {
		UP, POP, DEAD, SHOOTING, HANGING
	}

	public enum servoPosition {
		OUT, IN
	}

	public boolean getOptical(){
		return optical.get();
	}
	
	public void setIntakePivot(double v) {
		intakePivot.set(v);
	}
	
	public void setFeeder(double speed){
		rightFeeder.set(speed);
		leftFeeder.set(speed);
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
	//	botMotorSpeed = 1.6e-4 * tSpeedBot - 0.05;
	//	topMotorSpeed = 1.52e-4 * tSpeedTop - 0.05;
	private double getTopFeedForward(double rpm){
		return 1.52e-4 * rpm - 0.05;
	}
	
	private double getBotFeedForward(double rpm){
		return 1.6e-4 * rpm - 0.05;
	}
	
	private double getTarget(intakeState state){
		double out;
		switch(state){
		case DEAD:
			out = Constants.INTAKE_POT_DEAD;
		case POP:
			out = Constants.INTAKE_POT_POP;
		case UP:
			out = Constants.INTAKE_POT_UP;
		case HANGING:
			out = Constants.INTAKE_POT_HANGING;
		case SHOOTING:
			out = Constants.INTAKE_POT_SHOOTING;
		default:
			out = Constants.INTAKE_POT_DEAD;
		}
		
		return out;
	}
	
	public void setIntake(intakeState state){
		switch(state){
		case DEAD:
			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)){
				setTopRoller(Constants.INTAKE_INTAKE_POWER);
				setBotRoller(Constants.INTAKE_INTAKE_POWER);
				setFeeder(Constants.INTAKE_FEEDER_IN);
    		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
    			setTopRoller(Constants.INTAKE_OUTTAKE_POWER);
				setBotRoller(Constants.INTAKE_OUTTAKE_POWER);
				setFeeder(Constants.INTAKE_FEEDER_OUT);
    		} else {
    			setTopRoller(0);
				setBotRoller(0);
				setFeeder(0);
    		}
			
			break;
		case POP:
			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)){
				setTopRoller(Constants.INTAKE_INTAKE_POWER);
				setBotRoller(Constants.INTAKE_INTAKE_POWER);
				setFeeder(Constants.INTAKE_FEEDER_IN);
    		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
    			setTopRoller(Constants.INTAKE_TOP_POP_POWER);
				setBotRoller(Constants.INTAKE_BOT_POP_POWER);
				setFeeder(Constants.INTAKE_FEEDER_OUT);
    		} else {
    			setTopRoller(0);
				setBotRoller(0);
				setFeeder(0);
    		}
			break;
		case UP:
			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)){
				setTopRoller(Constants.INTAKE_INTAKE_POWER);
				setBotRoller(Constants.INTAKE_INTAKE_POWER);
				setFeeder(Constants.INTAKE_FEEDER_IN);
    		} else {
    			setTopRoller(0);
				setBotRoller(0);
				setFeeder(0);
    		}
			break;
		case HANGING:
			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
				setFeeder(Constants.INTAKE_FEEDER_OUT);
			} else {
				setFeeder(0);
			}
			setTopRoller(topPID.getValue(getTopSpeed(), Constants.SHOOTER_TOP_HANG, getTopFeedForward(Constants.SHOOTER_TOP_HANG)));
			setBotRoller(botPID.getValue(getBotSpeed(), Constants.SHOOTER_BOT_HANG, getBotFeedForward(Constants.SHOOTER_BOT_HANG)));
			break;
		case SHOOTING:
			if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
				setFeeder(Constants.INTAKE_FEEDER_OUT);
			} else {
				setFeeder(0);
			}
			setTopRoller(topPID.getValue(getTopSpeed(), Constants.SHOOTER_TOP, getTopFeedForward(Constants.SHOOTER_TOP)));
			setBotRoller(botPID.getValue(getBotSpeed(), Constants.SHOOTER_BOT, getBotFeedForward(Constants.SHOOTER_BOT)));
			
			break;
		}
		
		//Change 14.5
		if(getPivotCurrent() > 14.5){
			setIntakePivot(0);
			waitCounter = 0;
		}
		if(waitCounter < 100){
			waitCounter++;
		} else {
			setIntakePivot(pivotPID.getValue(getPot(), getTarget(state)));
		}
	}

	//
	// public double getPower(){
	// //Temp, need to find RPM to power
	// return getRPM() * 20;
	// }

	public void initDefaultCommand() {

	}

}