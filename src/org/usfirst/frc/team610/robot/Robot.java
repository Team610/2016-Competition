
package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.T_Teleop;
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
	Intake intake;
	NavX navx;

	public void robotInit() {

		intake = Intake.getInstance();
		teleop = new T_Teleop();
		sensor = new D_SensorReadings();
	//	hangerTest = new T_HangerTester();
	}

	public void disabledInit() {
	//	sensor.start();
	}

	public void disabledPeriodic() {
		SmartDashboard.putNumber("TOP RPM", intake.getTopSpeed());
		SmartDashboard.putNumber("BOT RPM", intake.getBotSpeed());
		SmartDashboard.putNumber("TOP PERIOD", intake.getTopPeriod());
		SmartDashboard.putNumber("BOT PERIOD", intake.getBotPeriod());
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		teleop.start();
	//	hangerTest.start();
		sensor.start();
//		LiveWindow.run();
	}

	public void teleopPeriodic() {
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
