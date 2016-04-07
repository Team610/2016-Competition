package org.usfirst.frc.team610.robot.commands;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.NavX;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class G_LowBarHigh extends CommandGroup {
    
    public  G_LowBarHigh() {
    	
    	NavX.getInstance().resetAngle();
    	DriveTrain.getInstance().resetSensors();
    	addParallel(new A_ResetTurn(1));
    	addSequential(new A_SetIntakePosition(Intake.intakeState.DEAD, 1));
    	//190 total
    	addSequential(new A_PositionMove(-212, 0, 0.6, 5));
    	addParallel(new A_SetIntakePosition(Intake.intakeState.SHOOTING, 10));
    	addSequential(new A_ResetTurn(0.5));
    	addSequential(new A_PositionLock(2,62));
    	addSequential(new A_PositionMove(-69, 0, 0.5, 3));
    	addSequential(new A_ResetTurn(0.5));
    	addSequential(new A_SetFeeder(1,Constants.INTAKE_FEEDER_OUT));
    	
    	
    	
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
