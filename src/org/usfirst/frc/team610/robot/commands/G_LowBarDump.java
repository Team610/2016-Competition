package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.NavX;

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
    	NavX.getInstance().resetAngle();
    	DriveTrain.getInstance().resetSensors();
    	addParallel(new A_ResetTurn(1));
    	addSequential(new A_SetIntakePosition(Intake.intakeState.DEAD, 1));
    	//190 total
    	addSequential(new A_PositionMove(200, 0, 0.5, 5));
    	addSequential(new A_ResetTurn(0.5));
    	addSequential(new A_PositionLock(2,62));
    	addParallel(new A_SetIntakePosition(Intake.intakeState.UP, 5));
    	addSequential(new A_PositionMove(65, 0, 0.6, 3));
    	addSequential(new A_SetIntake(-0.5));
//    	addParallel(new A_SetIntakePosition(Intake.intakeState.POP, 3));
//    	addSequential(new A_PositionMove(-90, 0, 1));
    	
//    	addSequential(new A_PositionLock(3,127));
//    	addParallel(new A_SetIntakePosition(Intake.intakeState.DEAD, 5));
//    	addSequential(new A_PositionMove(180, 0, 1));
//    	addSequential(new A_PositionLock(2,-20));
  
    	
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
