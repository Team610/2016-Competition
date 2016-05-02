package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.NavX;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_StaticRight extends CommandGroup {
    
    public  G_StaticRight() {
    	DriveTrain.getInstance().resetEncoders();
    	NavX.getInstance().resetAngle();
    	//Kalob
    	addParallel(new A_SetIntakePosition(intakeState.UP, 16));
    	addSequential(new A_SetFeeder(0.25, 0));
    	addSequential(new A_PositionMove(-159,0,0.6,5));
    	addSequential(new A_ResetTurn(1));
    	addSequential(new A_PositionMove(-148,0,0.6,5));
    	addSequential(new A_NewPositionLock(-63, 2));
    	NavX.getInstance().resetAngle();
    	addSequential(new A_VisionTurn(2));
    	addParallel(new A_SetIntakePosition(intakeState.SHOOTING, 10));
    	
    	addSequential(new A_SetFeeder(5, -0.25));
    }
}
