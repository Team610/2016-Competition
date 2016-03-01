package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_Static extends CommandGroup {
    
	
    public  G_Static(int mode) {
    	
    	DriveTrain.getInstance().resetEncoders();
    	addSequential(new A_PositionMove(145, 0, 0.55));
    	addSequential(new A_ResetTurn(3));
    	addSequential(new A_SetIntakePosition(intakeState.INTAKING));
    	
    	//DEFENITELY NOT TESTED\\
    	switch(mode){
    	case 1:
    		//Second one from the wall
    		addSequential(new A_PositionMove(150, 0, 0.75));
    		//Turn 60 left
    		addSequential(new A_PositionLock(2, 60));
    		addSequential(new A_PositionMove(24, 0, 0.5));
    		
    		break;
    	case 2:
    		addSequential(new A_PositionLock(2, -20));
    		addSequential(new A_PositionMove(155, 0, 0.75));
    		addSequential(new A_PositionLock(2, 90));
    	case 3:
    		//Third one form the wall
    		//Turn 54.7 right
    		addSequential(new A_PositionLock(2, 55));
    		//Move forward
    		addSequential(new A_PositionMove(90, 0, 0.75));
    		//Turn 143 left
    		addSequential(new A_PositionLock(2, -36));
    		//Move forward
    		addSequential(new A_PositionMove(72, 0, 0.75));
    		addSequential(new A_PositionLock(2, -60));

    		addSequential(new A_PositionMove(24, 0, 0.5));
    		break;
    	case 4:
    		//Fourth one from the wall
    		//Move Forward
    		addSequential(new A_PositionMove(150, 0, 0.75));
    		//Turn 60 left
    		addSequential(new A_PositionLock(2, -60));

    		addSequential(new A_PositionMove(24, 0, 0.5));
    		break;
    	}
    	
    	addParallel(new A_SetIntake(Constants.INTAKE_OUTTAKE_POWER));
    }
    
    
}
