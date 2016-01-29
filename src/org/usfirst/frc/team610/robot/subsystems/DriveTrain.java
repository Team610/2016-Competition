package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	DriveTrain instance;
	Talon leftFront;
	Talon leftMid;
	Talon leftBack;
	Talon rightFront;
	Talon rightMid;
	Talon rightBack;
	Encoder leftEnc;
	Encoder rightEnc;
	
	public DriveTrain getInstance(){
		if(instance == null){
			instance = new DriveTrain();
		}
		return instance;
	}
	
	private DriveTrain(){
		leftFront = new Talon(ElectricalConstants.TALON_LEFT_FRONT);
		leftMid = new Talon(ElectricalConstants.TALON_LEFT_MID);
		leftBack = new Talon(ElectricalConstants.TALON_LEFT_BACK);
		rightFront = new Talon(ElectricalConstants.TALON_RIGHT_FRONT);
		rightMid = new Talon(ElectricalConstants.TALON_RIGHT_MID);
		rightBack = new Talon(ElectricalConstants.TALON_RIGHT_BACK);
		leftEnc = new Encoder(ElectricalConstants.ENCODER_DRIVE_LEFTA, ElectricalConstants.ENCODER_DRIVE_LEFTB);
		rightEnc = new Encoder(ElectricalConstants.ENCODER_DRIVE_RIGHTA, ElectricalConstants.ENCODER_DRIVE_RIGHTB);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setRight(double speed){
    	rightFront.set(speed);
    	rightMid.set(speed);
    	rightBack.set(speed);
    }
    
    public void setLeft(double speed){
    	leftFront.set(speed);
    	leftMid.set(speed);
    	leftBack.set(speed);
    }
    
    public int getRightDistance(){
    	return rightEnc.get();
    }
    
    public int getLeftDistance(){
    	return leftEnc.get();
    }
    
}

