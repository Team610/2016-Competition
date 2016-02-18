package org.usfirst.frc.team610.robot.constants;

import edu.wpi.first.wpilibj.Preferences;

public class PIDConstants {
	public static Preferences prefs = Preferences.getInstance();
	public static final double GYRO_Kp = 0.045;
	public static final double GYRO_Kd = 0.1;
//	public static final double Ki = 1;
	
	public static final double ENCODER_Kp = -0.15;
	public static final double ENCODER_Kd = 0.00;
//	public static final double ENCODER_Ki = 0.01;
	
	public static double INTAKE_POS_Kp = prefs.getDouble("intakeKp", 15) ;// 15
	public static double INTAKE_POS_Ki = prefs.getDouble("intakeKi", 0);
	public static double INTAKE_POS_Kd = prefs.getDouble("intakeKd", 0);
	
	//add INTAKE_POS_Kd
	public static final double INTAKE_SHOOT_Kp = 0.0003;
	public static final double INTAKE_SHOOT_Kd = 0;
	
	public static void update()
	{
		INTAKE_POS_Kp = prefs.getDouble("intakeKp", 15) ;// 15
		INTAKE_POS_Ki = prefs.getDouble("intakeKi", 0);
		INTAKE_POS_Kd = prefs.getDouble("intakeKd", 1);
	}
	
}
