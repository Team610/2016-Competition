
package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.T_HangerTester;
import org.usfirst.frc.team610.robot.commands.T_Teleop;
import org.usfirst.frc.team610.robot.subsystems.NavX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	CommandGroup teleop;
	Command sensor;
	Command hangerTest;
	NavX navx;

	public void robotInit() {

		teleop = new T_Teleop();
		sensor = new D_SensorReadings();
		hangerTest = new T_HangerTester();
	}

	public void disabledInit() {
		sensor.start();
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
//		teleop.start();
		hangerTest.start();
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
