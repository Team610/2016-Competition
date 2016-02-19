package org.usfirst.frc.team610.robot.constants;

import edu.wpi.first.wpilibj.Preferences;

public class Constants {
	// Prefs allow constants to be updated via SmartDashboard
	// Updated and stored in persistent memory on RoboRIO between power cycles
	// Values here are defaults if there are no existing prefs entries in persistent storage
	// If there is an entry already, the persistent value will be used instead
	
	// Please update these after live tuning!
	public static Preferences prefs = Preferences.getInstance();
	
	//Drivetrain
	public static double DRIVETRAIN_PULSES_PER_INCH = prefs.getDouble("DRIVETRAIN_PULSES_PER_INCH", 97.1830985915493);
	
	//Hanger
	public static double HANGER_RATCHET_CLOSED = prefs.getDouble("HANGER_RATCHET_CLOSED", 0.368);
	public static double HANGER_RATCHET_OPEN = prefs.getDouble("HANGER_RATCHET_OPEN", 0.3);
	public static double HANGER_WINCH_SPEED_UP = prefs.getDouble("HANGER_WINCH_SPEED_UP", 1);
	public static double HANGER_ENC_TOP_HANG = prefs.getDouble("HANGER_ENC_TOP_HANG", 1725);
	public static double HANGER_ENC_TOP_FINAL = prefs.getDouble("HANGER_ENC_TOP_FINAL", 20);
	
	//Intake
	public static double INTAKE_FLIPPER_SERVO_OUTA = prefs.getDouble("INTAKE_FLIPPER_SERVO_OUTA", 0.1);
	public static double INTAKE_FLIPPER_SERVO_INA = prefs.getDouble("INTAKE_FLIPPER_SERVO_INA", 0.8);
	public static double INTAKE_FLIPPER_SERVO_OUTB = prefs.getDouble("INTAKE_FLIPPER_SERVO_OUTB", 0.9);
	public static double INTAKE_FLIPPER_SERVO_INB = prefs.getDouble("INTAKE_FLIPPER_SERVO_INB", 0.2);
	
	public static double INTAKE_POT_OFFSET = prefs.getDouble("INTAKE_POT_OFFSET", 0.03);
	public static double INTAKE_POT_UP = prefs.getDouble("INTAKE_POT_UP", 0.700);
	public static double INTAKE_POT_SHOOTING = prefs.getDouble("INTAKE_POT_SHOOTING", 0.83);
	public static double INTAKE_POT_POP = prefs.getDouble("INTAKE_POT_POP", 0.88);
	public static double INTAKE_POT_DEAD = prefs.getDouble("INTAKE_POT_DEAD", 0.1);
	public static double INTAKE_POT_INTAKE = prefs.getDouble("INTAKE_POT_INTAKE", 0.12);
	
	//Shooter
	public static double INTAKE_INTAKE_POWER = prefs.getDouble("INTAKE_INTAKE_POWER", 0.65);
	public static double INTAKE_OUTTAKE_POWER = prefs.getDouble("INTAKE_OUTTAKE_POWER", -0.65);
	public static double INTAKE_TOP_POP_POWER = prefs.getDouble("INTAKE_TOP_POP_POWER", -0.65);
	public static double INTAKE_BOT_POP_POWER = prefs.getDouble("INTAKE_BOT_POP_POWER", -0.1); 
	//0.339
	public static double SHOOTER_SERVO_RIGHT_OUT = prefs.getDouble("SHOOTER_SERVO_RIGHT_OUT", 0.564); //65
	public static double SHOOTER_SERVO_RIGHT_IN = prefs.getDouble("SHOOTER_SERVO_RIGHT_IN", 0.225);
	public static double SHOOTER_SERVO_LEFT_OUT = prefs.getDouble("SHOOTER_SERVO_LEFT_OUT", 0.3);
	public static double SHOOTER_SERVO_LEFT_IN = prefs.getDouble("SHOOTER_SERVO_LEFT_IN", 0.639);
	
	//-0.7000
	public static double SHOOTER_TOP = prefs.getDouble("topShooterSpeed", -2000) ; //change please
	public static double SHOOTER_BOT = prefs.getDouble("botShooterSpeed", -2000) ; //change please
	
	public static void update()
	{
		//Drivetrain
		DRIVETRAIN_PULSES_PER_INCH = prefs.getDouble("DRIVETRAIN_PULSES_PER_INCH", 97.1830985915493);
		
		//Hanger
		HANGER_RATCHET_CLOSED = prefs.getDouble("HANGER_RATCHET_CLOSED", 0.368);
		HANGER_RATCHET_OPEN = prefs.getDouble("HANGER_RATCHET_OPEN", 0.3);
		HANGER_WINCH_SPEED_UP = prefs.getDouble("HANGER_WINCH_SPEED_UP", 1);
		HANGER_ENC_TOP_HANG = prefs.getDouble("HANGER_ENC_TOP_HANG", 1725);
		HANGER_ENC_TOP_FINAL = prefs.getDouble("HANGER_ENC_TOP_FINAL", 20);
		
		//Intake
		INTAKE_FLIPPER_SERVO_OUTA = prefs.getDouble("INTAKE_FLIPPER_SERVO_OUTA", 0.1);
		INTAKE_FLIPPER_SERVO_INA = prefs.getDouble("INTAKE_FLIPPER_SERVO_INA", 0.8);
		INTAKE_FLIPPER_SERVO_OUTB = prefs.getDouble("INTAKE_FLIPPER_SERVO_OUTB", 0.9);
		INTAKE_FLIPPER_SERVO_INB = prefs.getDouble("INTAKE_FLIPPER_SERVO_INB", 0.2);
		
		INTAKE_POT_OFFSET = prefs.getDouble("INTAKE_POT_OFFSET", 0.03);
		INTAKE_POT_UP = prefs.getDouble("INTAKE_POT_UP", 0.700);
		INTAKE_POT_SHOOTING = prefs.getDouble("INTAKE_POT_SHOOTING", 0.83);
		INTAKE_POT_POP = prefs.getDouble("INTAKE_POT_POP", 0.88);
		INTAKE_POT_DEAD = prefs.getDouble("INTAKE_POT_DEAD", 0.1);
		INTAKE_POT_INTAKE = prefs.getDouble("INTAKE_POT_INTAKE", 0.12);
		
		//Shooter
		INTAKE_INTAKE_POWER = prefs.getDouble("INTAKE_INTAKE_POWER", 0.65);
		INTAKE_OUTTAKE_POWER = prefs.getDouble("INTAKE_OUTTAKE_POWER", -0.65);
		INTAKE_TOP_POP_POWER = prefs.getDouble("INTAKE_TOP_POP_POWER", -0.65);
		INTAKE_BOT_POP_POWER = prefs.getDouble("INTAKE_BOT_POP_POWER", -0.1); 
		
		SHOOTER_SERVO_RIGHT_OUT = prefs.getDouble("SHOOTER_SERVO_RIGHT_OUT", 0.564); //65
		SHOOTER_SERVO_RIGHT_IN = prefs.getDouble("SHOOTER_SERVO_RIGHT_IN", 0.225);
		SHOOTER_SERVO_LEFT_OUT = prefs.getDouble("SHOOTER_SERVO_LEFT_OUT", 0.3);
		SHOOTER_SERVO_LEFT_IN = prefs.getDouble("SHOOTER_SERVO_LEFT_IN", 0.639);
		
		SHOOTER_TOP = prefs.getDouble("topShooterSpeed", -2000) ; //change please
		SHOOTER_BOT = prefs.getDouble("botShooterSpeed", -2000) ; //change please
	}
	
}
