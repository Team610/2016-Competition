package org.usfirst.frc.team610.robot.subsystems;

import org.sixten.chareslib.PID;
import org.usfirst.frc.team610.robot.OI;
import org.usfirst.frc.team610.robot.constants.Constants;
import org.usfirst.frc.team610.robot.constants.ElectricalConstants;
import org.usfirst.frc.team610.robot.constants.LogitechF310Constants;
import org.usfirst.frc.team610.robot.constants.PIDConstants;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	static DriveTrain instance;

	private Victor leftFront;
	private Victor leftBack;
	private Victor rightFront;
	private Victor rightBack;
	private Encoder leftEnc;
	private Encoder rightEnc;
	private NavX navx;
	private PID gyroPid;
	private PID encPid;

	double tDistance;
	double tAngle;
	boolean isLock = false;

	private OI oi;

	public static DriveTrain getInstance() {
		if (instance == null) {
			instance = new DriveTrain();
		}
		return instance;

	}

	private DriveTrain() {
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
		encPid = new PID(PIDConstants.ENCODER_Kp, 0, PIDConstants.ENCODER_Kd);
		gyroPid = new PID(PIDConstants.GYRO_Kp, 0, PIDConstants.GYRO_Kd);
		oi = OI.getInstance();
		tDistance = 0;
	}

	public void initDefaultCommand() {
	}

	public void setRight(double speed) {
		rightFront.set(speed);
		rightBack.set(speed);
	}

	public void setLeft(double speed) {
		leftFront.set(speed);
		leftBack.set(speed);
	}

	public double getRightInches() {
		return rightEnc.getDistance() / Constants.DRIVETRAIN_PULSES_PER_INCH;
	}

	public double getLeftInches() {
		return -leftEnc.getDistance() / Constants.DRIVETRAIN_PULSES_PER_INCH;
	}

	public double getYaw() {
		return navx.getAngle();
	}

	public void resetSensors() {
		navx.resetAngle();
		leftEnc.reset();
		rightEnc.reset();
	}

	public void resetEncoders() {
		leftEnc.reset();
		rightEnc.reset();
	}

	public void drive() {
		double leftSpeed, rightSpeed;
		double avgSpeed = 0;
		double turnSpeed = 0;
		double x;
		double y;

		if (oi.getDriver().getRawButton(LogitechF310Constants.BTN_L2) && !isLock) {
			isLock = true;
			resetSensors();
			tDistance = 0;
			tAngle = 0;
		}

		if (!oi.getDriver().getRawButton(LogitechF310Constants.BTN_L2)) {
			isLock = false;
		}

		if (isLock) {
			x = oi.getDriver().getRawAxis(LogitechF310Constants.AXIS_RIGHT_X) * Constants.LOCK_GYRO_SENSITIVITY;
			y = oi.getDriver().getRawAxis(LogitechF310Constants.AXIS_LEFT_Y) * Constants.LOCK_ENC_SENSITIVITY;
			
			if(Math.abs(x) < 0.05){
				x = 0;
			}
			
			if(Math.abs(y) < 0.05){
				y = 0;
			}
			
			tAngle += x;
			tDistance += y;

			avgSpeed = encPid.getValue((getLeftInches() + getRightInches()) / 2, tDistance);
			turnSpeed = gyroPid.getValue(getYaw(), tAngle);

			leftSpeed = avgSpeed + turnSpeed;
			rightSpeed = avgSpeed - turnSpeed;
		} else {
			x = oi.getDriver().getRawAxis(LogitechF310Constants.AXIS_RIGHT_X);
			y = oi.getDriver().getRawAxis(LogitechF310Constants.AXIS_LEFT_Y);
			leftSpeed = y - x;
			rightSpeed = y + x;
		}

		setRight(rightSpeed);
		setLeft(leftSpeed);

	}

}
