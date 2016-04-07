
package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.A_ResetTurn;
import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.G_Cheval;
import org.usfirst.frc.team610.robot.commands.G_LowBarDump;
import org.usfirst.frc.team610.robot.commands.G_LowBarHigh;
import org.usfirst.frc.team610.robot.commands.G_Static;
import org.usfirst.frc.team610.robot.commands.G_StaticBack;
import org.usfirst.frc.team610.robot.commands.T_Teleop;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Hanger;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.NavX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	CommandGroup teleop;
	Command sensor;
	Command hangerTest;
	CommandGroup auton;
	Intake intake;
	DriveTrain drivetrain;
	NavX navx;
	OI oi;
	int mode;
	public void robotInit() {
		oi = OI.getInstance();
		intake = Intake.getInstance();
		drivetrain = DriveTrain.getInstance();
		teleop = new T_Teleop();
		sensor = new D_SensorReadings();
	}

	public void disabledInit() {
		teleop.cancel();
		auton = new G_LowBarDump();
		sensor.start();
	}

	public void disabledPeriodic() {
		SmartDashboard.putNumber("", intake.getPot());
		
		if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_A)){
			mode = 1;
		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_B)){
			mode = 2;
		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_X)){
			mode = 3;
		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_Y)){
			mode = 4;
		}else if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_START)){
			//5 Should be to just go over defense and stop. 
			mode = 5;
		} 
		
		SmartDashboard.putNumber("Mode: ", mode);
		
		if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_L1)){
			auton = new G_Cheval(mode);
			SmartDashboard.putString("Auton Mode: ", "Cheval_" + mode);
		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)){
			auton = new G_Static(mode);
			SmartDashboard.putString("Auton Mode: ", "Static_" + mode);
		} else if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_L2)){
			auton = new G_LowBarDump();
			SmartDashboard.putString("Auton Mode: ", "LowBarDump");
		} else if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_R2)){
			auton = new G_StaticBack();
			SmartDashboard.putString("Auton Mode: ", "Static_Backwards");
		} else if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_START)){
			auton = new G_LowBarHigh();
			SmartDashboard.putString("Auton Mode: ", "LowBarHigh");
		} else if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_BACK)){
			auton = new G_Static(8);
			SmartDashboard.putString("Auton Mode: ", "Sitting Duck");
		}
		
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		teleop.cancel();
		sensor.start();
		auton.start();
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		Hanger.getInstance().resetEncoder();
		teleop.start();
		sensor.start();
	}

	public void teleopPeriodic() {
		Constants.update();
		PIDConstants.update();
		Scheduler.getInstance().run();
	}

	public void testInit(){
//		teleop.start();
//		hangerTest.start(); 
		
	}
	
	public void testPeriodic() {
		LiveWindow.run();
	}
}
