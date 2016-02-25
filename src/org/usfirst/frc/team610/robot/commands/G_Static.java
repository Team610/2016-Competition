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
    	addSequential(new A_PositionMove(140, 0, 0.55));
    	addSequential(new A_ResetTurn(3));
    	addSequential(new A_SetIntakePosition(intakeState.INTAKING));
    	
    	switch(mode){
    	case 1:
    		//closeset one from the wall
    		//Move Forward
    		//Turn 53 right
    		//Move Forward
    		break;
    	case 2:
    		//Second one from the wall
    		//Move Forward
    		//Turn 90 right
    		break;
    	case 3:
    		//Third one form the wall
    		//Turn 24.5 right
    		//Move forward
    		//Turn 90 left
    		//Move forward
    		break;
    	case 4:
    		//Fourth one from the wall
    		//Move Forward
    		//Turn 64 left
    		break;
    	}
    	
    	addParallel(new A_SetIntake(Constants.INTAKE_OUTTAKE_POWER));
    }
    
    
}
