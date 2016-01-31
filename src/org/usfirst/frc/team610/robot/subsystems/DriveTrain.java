package org.usfirst.frc.team610.robot.subsystems;

import org.usfirst.frc.team610.robot.constants.ElectricalConstants;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	SerialPort serial_port;
	static DriveTrain instance;
	Victor leftFront;
	Victor leftBack;
	Victor rightFront;
	Victor rightBack;
	Encoder leftEnc;
	Encoder rightEnc;
	IMUAdvanced imu;
	
	public static DriveTrain getInstance(){
		if(instance == null){
			instance = new DriveTrain();
		}
		return instance;
	}
	
	private DriveTrain(){
		leftFront = new Victor(ElectricalConstants.TALON_LEFT_FRONT);
		leftBack = new Victor(ElectricalConstants.TALON_LEFT_BACK);
		rightFront = new Victor(ElectricalConstants.TALON_RIGHT_FRONT);
		rightBack = new Victor(ElectricalConstants.TALON_RIGHT_BACK);
		leftEnc = new Encoder(ElectricalConstants.ENCODER_DRIVE_LEFTA, ElectricalConstants.ENCODER_DRIVE_LEFTB);
		rightEnc = new Encoder(ElectricalConstants.ENCODER_DRIVE_RIGHTA, ElectricalConstants.ENCODER_DRIVE_RIGHTB);
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kMXP);
			byte update_rate_hz = 50;
			imu = new IMUAdvanced(serial_port, update_rate_hz);
		} catch (Exception ex) {
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setRight(double speed){
    	System.out.println("in right loop");
    	rightFront.set(speed);
    	rightBack.set(speed);
    }
    
    public void setLeft(double speed){
    	System.out.println("in left loop");
    	leftFront.set(speed);
    	leftBack.set(speed);
    }
    
    
    public int getRightDistance(){
    	return rightEnc.get();
    }
    
    public int getLeftDistance(){
    	return leftEnc.get();
    }
    
    public double getYaw(){
    	return imu.getYaw();
    }
    
    public double getPitch(){
    	return imu.getPitch();
    }
    
    public double getRoll(){
    	return imu.getRoll();
    }
}

