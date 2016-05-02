package org.usfirst.frc.team610.robot.subsystems;

import org.sixten.chareslib.PID;
import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;

import com.ni.vision.NIVision.LegFeature;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private double feederSpeed;

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

		pivotPID = new PID(PIDConstants.INTAKE_POS_Kp, 0, PIDConstants.INTAKE_POS_Kd);
		topPID = new PID(PIDConstants.INTAKE_SHOOT_Kp, 0, PIDConstants.INTAKE_SHOOT_Kd, -1, 1);
		botPID = new PID(PIDConstants.INTAKE_SHOOT_Kp, 0, PIDConstants.INTAKE_SHOOT_Kd, -1, 1);

	}

	// public void updateLists() {
	// if (topRollers.size() > 50) {
	// topRollers.add(0, optTopCounter.getPeriod());
	// topRollers.remove(topRollers.size());
	// botRollers.add(0, optBotCounter.getPeriod());
	// botRollers.remove(topRollers.size());
	// } else {
	//
	// }
	//
	// }

	public double getTopSpeed() {
		return RPMfactor / getTopPeriod();
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
		return RPMfactor / getBotPeriod();
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

	public double getPivotCurrent() {
		return pdb.getCurrent(Constants.PDB_INTAKE_PIVOT);
	}

	public double getLeftRollerCurrent() {
		return pdb.getCurrent(9);
	}

	public double getRightRollerCurrent() {
		return pdb.getCurrent(3);
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

	public boolean getOptical() {
		return optical.get();
	}

	public void setIntakePivot(double v) {
		intakePivot.set(v);
	}

	public void setFeeder(double speed) {
		rightFeeder.set(-speed);
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

	public double getBotFeedForward(double rpm) {
		return 1.4e-4 * rpm - 0.05;
	}

	public double getTopFeedForward(double rpm) {
		return 1.4e-4 * rpm - 0.05;
	}

	public double getTarget(intakeState state) {
		double out;
		switch (state) {
		case DEAD:
			out = Constants.INTAKE_POT_DEAD;
			break;
		case POP:
			out = Constants.INTAKE_POT_POP;
			break;
		case UP:
			out = Constants.INTAKE_POT_UP;
			break;
		case HANGING:
			out = Constants.INTAKE_POT_HANGING;
			break;
		case SHOOTING:
			out = Constants.INTAKE_POT_SHOOTING;
			break;
		default:
			out = Constants.INTAKE_POT_DEAD;
			break;
		}

		return out;
	}
	private int counter = 0;
	private double topSpeed = 0;;
	private double botSpeed = 0;
	public void setIntake() {
		switch (curIntakeState) {
		case DEAD:
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1) && !getOptical()) {
				setTopRoller(Constants.INTAKE_INTAKE_POWER);
				setBotRoller(Constants.INTAKE_INTAKE_POWER);
				feederSpeed = Constants.INTAKE_FEEDER_IN;

			} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				setTopRoller(Constants.INTAKE_TOP_POP_POWER);
				setBotRoller(Constants.INTAKE_BOT_POP_POWER);
				feederSpeed = Constants.INTAKE_FEEDER_OUT;

			} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_X)) {
				setTopRoller(Constants.INTAKE_INTAKE_POWER);
				setBotRoller(Constants.INTAKE_INTAKE_POWER);
				feederSpeed = Constants.INTAKE_FEEDER_IN;
			}  else {
				setTopRoller(0);
				setBotRoller(0);
				feederSpeed = 0;
			}
			break;
		case POP:
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1) && !getOptical()) {
				setTopRoller(Constants.INTAKE_INTAKE_POWER);
				setBotRoller(Constants.INTAKE_INTAKE_POWER);
				feederSpeed = Constants.INTAKE_FEEDER_IN;

			} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				setTopRoller(Constants.INTAKE_TOP_POP_POWER);
				setBotRoller(Constants.INTAKE_BOT_POP_POWER);
				feederSpeed = Constants.INTAKE_FEEDER_OUT;

			} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_X)) {
				setTopRoller(Constants.INTAKE_INTAKE_POWER);
				setBotRoller(Constants.INTAKE_INTAKE_POWER);
				feederSpeed = Constants.INTAKE_FEEDER_IN;
			}  else {
				setTopRoller(0);
				setBotRoller(0);
				feederSpeed = 0;
			}
			if (!getOptical() && !oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
				feederSpeed = Constants.INTAKE_FEEDER_IN;
			}
			SmartDashboard.putBoolean("Optical", getOptical());
			break;
		case UP:
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				curIntakeState = intakeState.SHOOTING;
				counter = 0;
			}
			
			topSpeed = topPID.getValue(-getTopSpeed(), Constants.SHOOTER_TOP,
					getTopFeedForward(Constants.SHOOTER_TOP));
			botSpeed = botPID.getValue(-getBotSpeed(), Constants.SHOOTER_BOT,
					getBotFeedForward(Constants.SHOOTER_BOT));

			if (botSpeed < 0) {
				setBotRoller(botSpeed);
			}
			if (topSpeed < 0) {
				setTopRoller(topSpeed);
			}
			break;
		case HANGING:
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)) {
				feederSpeed = Constants.INTAKE_FEEDER_OUT;

			} else {
				feederSpeed = 0;

			}
			
			botSpeed = botPID.getValue(-getBotSpeed(), Constants.SHOOTER_BOT_HANG,
					getBotFeedForward(Constants.SHOOTER_BOT_HANG));
			topSpeed = topPID.getValue(-getTopSpeed(), Constants.SHOOTER_TOP_HANG,
					getTopFeedForward(Constants.SHOOTER_TOP_HANG));

			if (botSpeed < 0) {
				setBotRoller(botSpeed);
			}
			if (topSpeed < 0) {
				setTopRoller(topSpeed);
			}
			SmartDashboard.putNumber("BotTarget", Constants.SHOOTER_BOT_HANG);
			SmartDashboard.putNumber("TopTarget", Constants.SHOOTER_TOP_HANG);
			break;
		case SHOOTING:
			if(counter < 50){
				counter ++;
			}
			if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1) && counter > 25) {
				feederSpeed = Constants.INTAKE_FEEDER_OUT;

			} else if (!getOptical()){
				feederSpeed = Constants.INTAKE_FEEDER_IN; 
			} else {
				feederSpeed = 0;
			}
			topSpeed = topPID.getValue(-getTopSpeed(), Constants.SHOOTER_TOP,
					getTopFeedForward(Constants.SHOOTER_TOP));
			botSpeed = botPID.getValue(-getBotSpeed(), Constants.SHOOTER_BOT,
					getBotFeedForward(Constants.SHOOTER_BOT));

			if (botSpeed < 0) {
				setBotRoller(botSpeed);
			}
			if (topSpeed < 0) {
				setTopRoller(topSpeed);
			}
			SmartDashboard.putNumber("BotTarget", Constants.SHOOTER_BOT);
			SmartDashboard.putNumber("TopTarget", Constants.SHOOTER_TOP);
			SmartDashboard.putNumber("BotSpeed", botSpeed);
			SmartDashboard.putNumber("TopSpeed", topSpeed);
			break;
		}

		if (curIntakeState.equals(intakeState.DEAD)) {
			if (getPot() < Constants.INTAKE_POT_DIE) {
				double speed = pivotPID.getValue(getPot(), getTarget(curIntakeState));
				SmartDashboard.putNumber("Pivot Power", speed);
				setIntakePivot(speed);
			} else {
				double speed = -0.05;
				SmartDashboard.putNumber("Pivot Power", speed);
				setIntakePivot(speed);
			}
		} else {
			double speed = pivotPID.getValue(getPot(), getTarget(curIntakeState));
			SmartDashboard.putNumber("Pivot Power", speed);
			setIntakePivot(speed);
		}

		SmartDashboard.putNumber("Intake Angle", getTarget(curIntakeState));
		
		setFeeder(feederSpeed);
	}
	
	public void setAutoIntake(){
		switch(curIntakeState){
		case DEAD:
			setTopRoller(0);
			setBotRoller(0);
			setFeeder(0);
			break;
		case POP:
			
			setTopRoller(Constants.INTAKE_INTAKE_POWER);
			setBotRoller(Constants.INTAKE_INTAKE_POWER);
			break;
		case UP:
			
			break;
		case SHOOTING:
			
			double topSpeed = topPID.getValue(-getTopSpeed(), Constants.SHOOTER_TOP,
					getTopFeedForward(Constants.SHOOTER_TOP));
			double botSpeed = botPID.getValue(-getBotSpeed(), Constants.SHOOTER_BOT,
					getBotFeedForward(Constants.SHOOTER_BOT));

			if (botSpeed < 0) {
				setBotRoller(botSpeed);
			}
			if (topSpeed < 0) {
				setTopRoller(topSpeed);
			}
			SmartDashboard.putNumber("BotTarget", Constants.SHOOTER_BOT);
			SmartDashboard.putNumber("TopTarget", Constants.SHOOTER_TOP);
			SmartDashboard.putNumber("BotSpeed", botSpeed);
			SmartDashboard.putNumber("TopSpeed", topSpeed);
			break;
		default:
			setTopRoller(Constants.INTAKE_INTAKE_POWER);
			setBotRoller(Constants.INTAKE_INTAKE_POWER);
			break;

		}
		if (curIntakeState.equals(intakeState.DEAD)) {
			if (getPot() < Constants.INTAKE_POT_DIE) {
				setIntakePivot(pivotPID.getValue(getPot(), getTarget(curIntakeState)));
			} else {
				setIntakePivot(-0.05);
			}
		} else {
			setIntakePivot(pivotPID.getValue(getPot(), getTarget(curIntakeState)));
		}
	}

	public void initDefaultCommand() {

	}

}