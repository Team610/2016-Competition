package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	static DriveTrain instance;
	
	private SerialPort visionInput;
	
	private Victor leftFront;
	private Victor leftBack;
	private Victor rightFront;
	private Victor rightBack;
	private Encoder leftEnc;
	private Encoder rightEnc;
	private NavX navx;
	
	public static DriveTrain getInstance(){
		if(instance == null){
			instance = new DriveTrain();
		}
		return instance;
		
	}
	
	private DriveTrain(){
		navx = NavX.getInstance();
		leftFront = new Victor(ElectricalConstants.VICTOR_LEFT_FRONT);
		leftFront.enableDeadbandElimination(true);
		leftBack = new Victor(ElectricalConstants.VICTOR_LEFT_BACK);
		leftBack.enableDeadbandElimination(true);
		rightFront = new Victor(ElectricalConstants.VICTOR_RIGHT_FRONT);
		rightFront.enableDeadbandElimination(true);
		rightBack = new Victor(ElectricalConstants.VICTOR_RIGHT_BACK);
		rightBack.enableDeadbandElimination(true);
		leftEnc = new Encoder(ElectricalConstants.ENCODER_DRIVE_LEFTA, ElectricalConstants.ENCODER_DRIVE_LEFTB);
		rightEnc = new Encoder(ElectricalConstants.ENCODER_DRIVE_RIGHTA, ElectricalConstants.ENCODER_DRIVE_RIGHTB);
		visionInput = new SerialPort(8, Port.kUSB);
	}
	
    public void initDefaultCommand() {
    }
    
  
    public void setRight(double speed){
    	rightFront.set(speed);
    	rightBack.set(speed);
    }
    
    public void setLeft(double speed){
    	leftFront.set(speed);
    	leftBack.set(speed);
    }
    
    
    public double getRightInches(){
    	return rightEnc.getDistance() / Constants.DRIVETRAIN_PULSES_PER_INCH;
    }
  
    public double getLeftInches(){
    	return -leftEnc.getDistance() / Constants.DRIVETRAIN_PULSES_PER_INCH;
    }
    public double getYaw(){
    	return navx.getAngle();
    }
    public void resetSensors(){
    	navx.resetAngle();
    	leftEnc.reset();
    	rightEnc.reset();
    }
    
    public void resetEncoders(){
    	leftEnc.reset();
    	rightEnc.reset();
    }
    
    public String getVision(){
    	return visionInput.readString();
    }

}

