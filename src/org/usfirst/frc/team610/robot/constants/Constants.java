package org.usfirst.frc.team610.robot.constants;

import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants {
	// Prefs allow constants to be updated via SmartDashboard
	// Updated and stored in persistent memory on RoboRIO between power cycles
	// Values here are defaults if there are no existing prefs entries in
	// persistent storage
	// If there is an entry already, the persistent value will be used instead

	//Change
	public static int PDB_INTAKE_PIVOT = 3;
	
	// Please update these after live tuning!
	public static Preferences prefs = Preferences.getInstance();

	// Drivetrain
	public static double DRIVETRAIN_PULSES_PER_INCH = prefs.getDouble("DRIVETRAIN_PULSES_PER_INCH", 97.1830985915493);

	// Hanger
	public static double HANGER_RATCHET_CLOSED = prefs.getDouble("HANGER_RATCHET_CLOSED", 0.368);
	public static double HANGER_RATCHET_OPEN = prefs.getDouble("HANGER_RATCHET_OPEN", 0.3);
	public static double HANGER_WINCH_SPEED_UP = prefs.getDouble("HANGER_WINCH_SPEED_UP", 1);
	public static double HANGER_ENC_TOP_HANG = prefs.getDouble("HANGER_ENC_TOP_HANG", 1725);
	public static double HANGER_ENC_TOP_FINAL = -65;

	public static double INTAKE_POT_OFFSET = 0;
	public static double INTAKE_POT_SHOOTING = prefs.getDouble("INTAKE_POT_SHOOTING", 0.497) + INTAKE_POT_OFFSET;
	public static double INTAKE_POT_HANGING = 0.55 + INTAKE_POT_OFFSET;
	public static double INTAKE_POT_POP = 0.567 + INTAKE_POT_OFFSET;
	public static double INTAKE_POT_DIE = 0.40 + INTAKE_POT_OFFSET;
	public static double INTAKE_POT_DEAD = 0.25 + INTAKE_POT_OFFSET;
	public static double INTAKE_POT_UP = 0.225 + INTAKE_POT_OFFSET;

	// Shooter
	// intake - 0.8
	// outtake - -0.65
	public static double INTAKE_INTAKE_POWER = 1;
	public static double INTAKE_OUTTAKE_POWER = -0.65;
	// public static double INTAKE_TOP_INTAKE_POWER =
	// prefs.getDouble("INTAKE_TOP_INTAKE_POWER", 0.6);
	// public static double INTAKE_BOT_INTAKE_POWER =
	// prefs.getDouble("INTAKE_BOT_INTAKE_POWER", 1);
	public static double INTAKE_TOP_POP_POWER = prefs.getDouble("INTAKE_TOP_POP_POWER", -0.65);
	public static double INTAKE_BOT_POP_POWER = prefs.getDouble("INTAKE_BOT_POP_POWER", -0.2);

	public static double INTAKE_FEEDER_IN = 1;
	public static double INTAKE_FEEDER_OUT = -0.25;

	// 0.339
	// public static double SHOOTER_SERVO_RIGHT_OUT =
	// prefs.getDouble("SHOOTER_SERVO_RIGHT_OUT", 0.2); // 65
	// public static double SHOOTER_SERVO_RIGHT_IN =
	// prefs.getDouble("SHOOTER_SERVO_RIGHT_IN", 0.58);
	// public static double SHOOTER_SERVO_LEFT_OUT =
	// prefs.getDouble("SHOOTER_SERVO_LEFT_OUT", 0.52);
	// public static double SHOOTER_SERVO_LEFT_IN =
	// prefs.getDouble("SHOOTER_SERVO_LEFT_IN", 0.15);

	// -0.7000
	public static double SHOOTER_TOP = prefs.getDouble("SHOOTER_TOP", -6000); // change
																				// please
	public static double SHOOTER_BOT = prefs.getDouble("SHOOTER_TOP", -4000); // change
																				// please

	public static double SHOOTER_TOP_HANG = -3000;
	public static double SHOOTER_BOT_HANG = -3000;

	public static double AUTON_FIRST_MOVE = prefs.getDouble("AUTON_FIRST_MOVE", 180);
	public static double AUTON_TURN = prefs.getDouble("AUTON_TURN", 50);
	public static double AUTON_SECOND_MOVE = prefs.getDouble("AUTON_SECOND_MOVE", 96);

	public static void setOffset(){
		SmartDashboard.putNumber("Offset:", 0);
		INTAKE_POT_OFFSET = Intake.getInstance().getPot() - INTAKE_POT_POP;
	}
	
	public static void update() {
		// Drivetrain
		// DRIVETRAIN_PULSES_PER_INCH =
		// prefs.getDouble("DRIVETRAIN_PULSES_PER_INCH", 97.1830985915493);
		//
		// // Hanger
		// HANGER_RATCHET_CLOSED = prefs.getDouble("HANGER_RATCHET_CLOSED",
		// 0.368);
		// HANGER_RATCHET_OPEN = prefs.getDouble("HANGER_RATCHET_OPEN", 0.3);
		// HANGER_WINCH_SPEED_UP = prefs.getDouble("HANGER_WINCH_SPEED_UP", 1);
		// HANGER_ENC_TOP_HANG = prefs.getDouble("HANGER_ENC_TOP_HANG", 1725);
		// HANGER_ENC_TOP_FINAL = prefs.getDouble("HANGER_ENC_TOP_FINAL", 10);
		// INTAKE_TOP_INTAKE_POWER = prefs.getDouble("INTAKE_TOP_INTAKE_POWER",
		// 0.6);
		// INTAKE_BOT_INTAKE_POWER = prefs.getDouble("INTAKE_BOT_INTAKE_POWER",
		// 1);
//		INTAKE_POT_OFFSET = prefs.getDouble("INTAKE_POT_OFFSET", -0.053);
		INTAKE_POT_SHOOTING = prefs.getDouble("INTAKE_POT_SHOOTING", 0.497);
		// INTAKE_POT_POP = prefs.getDouble("INTAKE_POT_POP", 0.567);
		// INTAKE_POT_DEAD = prefs.getDouble("INTAKE_POT_DEAD", 0.155);
		// INTAKE_POT_INTAKE = prefs.getDouble("INTAKE_POT_INTAKE", 0.160);

		// // Shooter09
		// INTAKE_INTAKE_POWER = prefs.getDouble("INTAKE_INTAKE_POWER", 0.65);
		// INTAKE_OUTTAKE_POWER = prefs.getDouble("INTAKE_OUTTAKE_POWER",
		// -0.65);
		// INTAKE_TOP_POP_POWER = prefs.getDouble("INTAKE_TOP_POP_POWER",
		// -0.65);
		// INTAKE_BOT_POP_POWER = prefs.getDouble("INTAKE_BOT_POP_POWER", -0.1);

		// SHOOTER_SERVO_RIGHT_OUT = prefs.getDouble("SHOOTER_SERVO_RIGHT_OUT",
		// 0.2); // 65
		// SHOOTER_SERVO_RIGHT_IN = prefs.getDouble("SHOOTER_SERVO_RIGHT_IN",
		// 0.58);
		// SHOOTER_SERVO_LEFT_OUT = prefs.getDouble("SHOOTER_SERVO_LEFT_OUT",
		// 0.52);
		// SHOOTER_SERVO_LEFT_IN = prefs.getDouble("SHOOTER_SERVO_LEFT_IN",
		// 0.15);

		SHOOTER_TOP = prefs.getDouble("SHOOTER_TOP", -6000); // change
		// // please
		SHOOTER_BOT = prefs.getDouble("SHOOTER_BOT", -4000); // change
		// // please
		// AUTON_FIRST_MOVE = prefs.getDouble("AUTON_FIRST_MOVE", 180);
		// AUTON_TURN = prefs.getDouble("AUTON_TURN", 50);
		// AUTON_SECOND_MOVE = prefs.getDouble("AUTON_SECOND_MOVE", 96);
		//
		// //Auton movements
		//
	}

}
