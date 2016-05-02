package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.subsystems.NavX;
import org.usfirst.frc.team610.robot.subsystems.Intake.intakeState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_Turn extends CommandGroup {
    
    public  G_Turn() {
    	NavX.getInstance().resetAngle();
    	addSequential(new TimeOut(1));
    	addParallel(new A_SetIntakePosition(intakeState.POP, 1000));
    	addSequential(new A_PositionMove(-200, 0, 0.6, 5));
    	addSequential(new A_ResetTurn(1));
   
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
