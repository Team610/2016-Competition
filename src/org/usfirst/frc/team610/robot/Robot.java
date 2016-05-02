
package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.G_LowBarHigh;
import org.usfirst.frc.team610.robot.commands.G_Static;
import org.usfirst.frc.team610.robot.commands.G_StaticBack;
import org.usfirst.frc.team610.robot.commands.G_StaticRight;
import org.usfirst.frc.team610.robot.commands.G_Turn;
import org.usfirst.frc.team610.robot.commands.T_Teleop;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
import org.usfirst.frc.team610.robot.subsystems.Hanger;
import org.usfirst.frc.team610.robot.subsystems.Intake;
import org.usfirst.frc.team610.robot.subsystems.NavX;
import org.usfirst.frc.team610.robot.subsystems.Vision;

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
		Constants.update();
		PIDConstants.update();
	}
	String autoMode = "";

	public void disabledInit() {
		teleop.cancel();
		auton = new G_LowBarHigh();
		sensor.start();
	}

	
	
	public void disabledPeriodic() {
		SmartDashboard.putNumber("", intake.getPot());
		
		SmartDashboard.putNumber("GyroAngle: ",DriveTrain.getInstance().getYaw());
		
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
			auton = new G_Turn();
			autoMode = "Test";
		} else if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_R1)){
			auton = new G_Static();
			autoMode = "StaticMid";
		} else if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_L2)){
			auton = new G_StaticRight();
			autoMode = "StaticRight";
		} else if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_R2)){
			auton = new G_StaticBack();
			autoMode = "StaticBack";
		} else if(oi.getDriver().getRawButton(LogitechF310Constants.BTN_START)){
			auton = new G_LowBarHigh();
			autoMode = "LowBarHigh";
		} 
		
		SmartDashboard.putString("Auton Mode: ", autoMode);
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		Vision.getInstance().setExposureBright(false);
		teleop.cancel();
		sensor.start();
		auton.start();
	}

	public void autonomousPeriodic() {
		PIDConstants.update();
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		Hanger.getInstance().resetEncoder();
		Vision.getInstance().setExposureBright(true);
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
