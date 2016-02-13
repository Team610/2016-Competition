package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class T_Teleop extends CommandGroup {
	
    public  T_Teleop() {
    	SmartDashboard.putString("hi", "teleop working");
    	addParallel(new T_KajDrive());
    	//addParallel(new T_Hang());
    	SmartDashboard.putString("test", "at kaj");
    	addParallel(new T_Intake());
    	SmartDashboard.putString("test2", "at intake");
    	
        
    }
}
