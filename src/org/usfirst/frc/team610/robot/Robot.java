
package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
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
	NavX navx;

	public void robotInit() {

		teleop = new T_Teleop();
		sensor = new D_SensorReadings();
//		navx = NavX.getInstance();

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
		teleop.start();
	
		sensor.start();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {
		LiveWindow.run();
	}
}
