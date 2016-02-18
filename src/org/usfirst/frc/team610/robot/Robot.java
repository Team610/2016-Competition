
package org.usfirst.frc.team610.robot;

import org.usfirst.frc.team610.robot.commands.D_SensorReadings;
import org.usfirst.frc.team610.robot.commands.G_LowBarDump;
import org.usfirst.frc.team610.robot.commands.T_Teleop;
import org.usfirst.frc.team610.robot.subsystems.DriveTrain;
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

	public void robotInit() {

		intake = Intake.getInstance();
		auton = new G_LowBarDump();
		drivetrain = DriveTrain.getInstance();
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
		SmartDashboard.putNumber("Pot", intake.getPot());
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		drivetrain.resetSensors();
		auton.start();
	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		Scheduler.getInstance().removeAll();
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
