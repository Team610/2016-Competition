package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	static DriveTrain instance;
	
	
	Victor leftFront;
	Victor leftBack;
	Victor rightFront;
	Victor rightBack;
	Encoder leftEnc;
	Encoder rightEnc;
	NavX navx;
	Talon testTalon;
	
	public static DriveTrain getInstance(){
		if(instance == null){
			instance = new DriveTrain();
		}
		return instance;
	}
	
	private DriveTrain(){
		navx = NavX.getInstance();
		leftFront = new Victor(ElectricalConstants.VICTOR_LEFT_FRONT);
		leftBack = new Victor(ElectricalConstants.VICTOR_LEFT_BACK);
		rightFront = new Victor(ElectricalConstants.VICTOR_RIGHT_FRONT);
		rightBack = new Victor(ElectricalConstants.VICTOR_RIGHT_BACK);
		leftEnc = new Encoder(ElectricalConstants.ENCODER_DRIVE_LEFTA, ElectricalConstants.ENCODER_DRIVE_LEFTB);
		rightEnc = new Encoder(ElectricalConstants.ENCODER_DRIVE_RIGHTA, ElectricalConstants.ENCODER_DRIVE_RIGHTB);

	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    public void test(double speed){
    	testTalon.set(speed);
    }
    
    public void setRight(double speed){
    	rightFront.set(speed);
    	rightBack.set(speed);
    }
    
    public void setLeft(double speed){
    	leftFront.set(speed);
    	leftBack.set(speed);
    }
    
    
    public double getRightDistance(){
    	return rightEnc.getDistance();
    }
  
    public double getLeftDistance(){
    	return leftEnc.getDistance();
    }
    public double getYaw(){
    	return navx.getAngle();
    }

}

