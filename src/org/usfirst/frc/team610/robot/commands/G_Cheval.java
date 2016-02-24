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
    	addSequential(new A_SetIntakePosition(intakeState.INTAKING));
    	
    	switch(mode){
    	case 1:
    		//closeset one from the wall
    		break;
    	case 2:
    		//Second one from the wall
    		break;
    	case 3:
    		//Third one formt he wall
    		break;
    	case 4:
    		//Fourth one from the wall
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
