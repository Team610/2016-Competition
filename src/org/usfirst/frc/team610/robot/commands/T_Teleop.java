package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Teleop extends CommandGroup {
	
    public  T_Teleop() {
    	addParallel(new T_KajDrive());
    	addParallel(new T_Hang());
//    	addParallel(new T_Intake());
    	addParallel(new D_SensorReadings());
    	addParallel(new T_ShooterTest());
        
    }
}
