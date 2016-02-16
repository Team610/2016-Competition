package org.usfirst.frc.team610.robot.constants;

import edu.wpi.first.wpilibj.Preferences;

public class Constants {
	//literally all of these are temp
	public static Preferences prefs = Preferences.getInstance();
	public static final double HANGER_RATCHET_CLOSED = 0.368;//GET
	public static final double HANGER_RATCHET_OPEN = 0.3;
	public static final double FLIPPER_SERVO_OUTA = 0.1;
	public static final double FLIPPER_SERVO_INA = 0.8;
	public static final double FLIPPER_SERVO_OUTB = 0.9;
	public static final double FLIPPER_SERVO_INB = 0.2;
	
	public static final double INTAKE_OFFSET = -0.05;
	public static final double INTAKE_POT_UP = 0.700 + INTAKE_OFFSET;

	public static double INTAKE_POT_SHOOTING = prefs.getDouble("shootingPotValue", 0.83+INTAKE_OFFSET);//0.83 + INTAKE_OFFSET;
	public static final double INTAKE_POT_POP = 0.87 + INTAKE_OFFSET;//TEMP PLS FIX
	public static final double INTAKE_POT_DOWN = 0.01;
	public static final double INTAKE_POT_DEAD = 0.08 + INTAKE_OFFSET;
	public static final double INTAKE_POT_INTAKE = 0.12 + INTAKE_OFFSET;
	public static final double WINCH_SPEED_UP = 1;
	public static final double ENC_TOP_HANG = 1725;
	public static final double ENC_TOP_FINAL = 70;
	public static final double INTAKE_SHOOT_SPEED = 2000;
	public static final double ROLLER_INTAKE = 0.65;
	public static final double ROLLER_OUTTAKE = -0.65;
	public static final double ROLLER_TOP_POP = -0.7;
	public static final double ROLLER_BOT_POP = -0.1;
	//-0.7000
	public static double SHOOTER_TOP = prefs.getDouble("topShooterSpeed", -3500) ; //change please
	public static double SHOOTER_BOT = prefs.getDouble("botShooterSpeed", -4400) ; //change please
//	public static final double INTAKE_SHOOT_OUT_L = 0.5;
//	public static final double INTAKE_SHOOT_IN_L = 0.2;
//	public static final double INTAKE_SHOOT_OUT_R = 0.5;
//	public static final double INTAKE_SHOOT_IN_R = 0.2;
	public static void update()
	{
		INTAKE_POT_SHOOTING = prefs.getDouble("shotPOS", 0.83+INTAKE_OFFSET);// 15
		SHOOTER_TOP = prefs.getDouble("topShooterSpeed", -3500) ;
		SHOOTER_BOT = prefs.getDouble("botShooterSpeed", -4400) ;
	}
	
}
