package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;
import org.usfirst.frc.team610.robot.subsystems.NavX;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_Static extends CommandGroup {
    
	
	
    public  G_Static() {
    	
    	DriveTrain.getInstance().resetEncoders();
    	NavX.getInstance().resetAngle();
    	addParallel(new A_SetIntakePosition(intakeState.UP, 16));
    	addSequential(new A_SetFeeder(0.25, 0));
    	addSequential(new A_PositionMove(-150,0,0.7,5));
    	addSequential(new A_ResetTurn(1));
    	addSequential(new A_NewPositionLock(15, 1));
    	addSequential(new A_PositionMove(-70, 0, .5, 2));
    	NavX.getInstance().resetAngle();
    	addSequential(new A_SetFeeder(0.5,0));
    	addSequential(new A_VisionTurn(2));
    	addParallel(new A_SetIntakePosition(intakeState.SHOOTING, 10));
    	
    	addSequential(new A_SetFeeder(5, -0.25));
    }
    
    
}
