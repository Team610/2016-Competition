package org.usfirst.frc.team610.robot.constants;

import edu.wpi.first.wpilibj.Preferences;

public class PIDConstants {
	public static Preferences prefs = Preferences.getInstance();
	public static double GYRO_Kp = prefs.getDouble("GYRO_Kp", 0.05);
	public static double GYRO_Kd = prefs.getDouble("Gyro_Kd", 0);
	
	public static double GYRO_DRIVE_P = 0.05;
	public static double GYRO_DRIVE_D = 0.0;
	
//	public static final double Ki = 1;
	
	public static double ENCODER_Kp = prefs.getDouble("ENCODER_Kp", -0.1);
	public static double ENCODER_Kd = prefs.getDouble("ENCODER_Kd", 0);
//	public static final double ENCODER_Ki = 0.01;
	
	//P is 15
	//D is 50
	
	public static double INTAKE_POS_Kp = -15;
	public static double INTAKE_POS_Kd = 10;
	public static double INTAKE_POS_Ki = 0;
	
	//add INTAKE_POS_Kd
	public static final double INTAKE_SHOOT_Kp = 0.0005;
	public static final double INTAKE_SHOOT_Kd = 0.00000;
	
	public static void update(){
		GYRO_Kp = prefs.getDouble("Gyro_Kp", 0.04);
		GYRO_Kd = prefs.getDouble("Gyro_Kd", -0.1);
		GYRO_DRIVE_P = prefs.getDouble("Drive_P", 0.05);
		GYRO_DRIVE_D = prefs.getDouble("Drive_D", 0.);
		ENCODER_Kp = prefs.getDouble("ENCODER_Kp", -0.1);
		ENCODER_Kd = prefs.getDouble("ENCODER_Kd", 0);
	}
	
}
