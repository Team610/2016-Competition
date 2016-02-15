package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_LowBarDump extends CommandGroup {
    
    public  G_LowBarDump() {
    	
    	
    	
    	//Parallel IntakeDead & Drive forward through low bar
    	// Sequential Outtake Ball in Courtyard
    	//
    	//
    	addSequential(new A_PositionMove(30));
//    	addSequential(new A_PositionLock(40, 90));
    	//addSequential(new A_SetIntakePosition(Intake.intakeState.INTAKING));
    	
    	//addSequential(new A_SetIntake(-0.7));
    	
    	
    	
    	
    	
    	
    	
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
