package org.usfirst.frc.team610.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class T_Teleop extends CommandGroup {
	
    public  T_Teleop() {
      //  addParallel(new A_PositionMove(0));
//    	addParallel(new T_KajDrive());
    //	addParallel(new T_Hang());
    	addParallel(new T_Intake());
    	
    	
        
    }
}
