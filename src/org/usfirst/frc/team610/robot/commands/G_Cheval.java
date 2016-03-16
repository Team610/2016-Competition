package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_Cheval extends CommandGroup {
    
    public  G_Cheval(int mode) {
    	
    	addSequential(new A_PositionMove(45, 0, 0.5));
    	addSequential(new A_SetIntakePosition(intakeState.DEAD, 2));
    	addParallel(new A_SetIntakePosition(intakeState.POP, 1));
    	addSequential(new A_PositionMove(100, 0, 0.5));
    	addSequential(new A_ResetTurn(0.5));
    	addSequential(new A_SetIntakePosition(intakeState.UP));
    	
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
    		//Added break to stop running case 3 after case 2; Fix if needed
    		break;
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
    	case 5:
    		break;
    	}
    	
    	
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
