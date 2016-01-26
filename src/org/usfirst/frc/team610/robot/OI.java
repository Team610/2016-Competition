package org.usfirst.frc.team610.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private OI instance;
	private Joystick driver;
	private Joystick operator;
	
	private OI(){
		driver = new Joystick(0);
		operator = new Joystick(1);
	}
	
	public OI getInstance(){
		if(instance == null){
			instance = new OI();
		}
		return instance;
	}
	
	public Joystick getDriver(){
		return driver;
	}
	
	public Joystick getOperator(){
		return operator;
	}
	
}

