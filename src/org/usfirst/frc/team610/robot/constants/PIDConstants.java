package org.usfirst.frc.team610.robot.constants;

import edu.wpi.first.wpilibj.Preferences;

public class PIDConstants {
	public static Preferences prefs = Preferences.getInstance();
	public static final double GYRO_Kp = 0.05;
	public static final double GYRO_Kd = 0.1;
//	public static final double Ki = 1;
	
	public static final double ENCODER_Kp = -0.1;
	public static final double ENCODER_Kd = 0.0;
//	public static final double ENCODER_Ki = 0.01;
	
	//P is 15
	//D is 50
	
	public static double INTAKE_POS_Kp = prefs.getDouble("intakeKp", 13);
	public static double INTAKE_POS_Kd = prefs.getDouble("intakeKd", 50);
	
	//add INTAKE_POS_Kd
	public static final double INTAKE_SHOOT_Kp = 0.0005;
	public static final double INTAKE_SHOOT_Kd = 0.00000;
	
	public static void update(){
		INTAKE_POS_Kp = prefs.getDouble("intakeKp", 13);
		INTAKE_POS_Kd = prefs.getDouble("intakeKd", 50);
	}
	
	
}
