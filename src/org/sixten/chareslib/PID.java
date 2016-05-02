package org.sixten.chareslib;


public class PID {

	private double p;
	private double d;
	private double i;
	private double max;
	private double min;
	private double curTarget;
	
	private double error;
	private double lastError;
	private double diffError;
	private double iCounter;

	private double output;
	
	public PID(){
		error = 0;
		lastError = 0;
		diffError = 0;
		output = 0;
		curTarget = 0;
		min = -1e50;
		max = 1e50;
	}
	
	public PID(double p, double i, double d){
		this();
		this.p = p;
		this.i = i;
		this.d = d;
	}
	public PID(double p, double i, double d, double min, double max){
		this(p, i, d);
		this.max = max;
		this.min = min;
	}
	
	public double getValue(double input, double target){
		if(curTarget != target){
			resetPID();
			curTarget = target;
		}
		error = target - input;
		diffError = lastError - error;
		
		output = error * p + diffError * d;	
		
		lastError = error;
		if(output > max){
			output = max;
		} else if (output < min){
			output = min;
		}
		
		return output;
	}
	
	public double getValue(double input, double target, double feedForward){
		if(curTarget != target){
			resetPID();
			curTarget = target;
		}
		error = target - input;
		diffError = lastError - error;
		
		output = error * p + diffError * d + feedForward;	
		
		lastError = error;
		if(output > max){
			output = max;
		} else if (output < min){
			output = min;
		}
		
		return output;
	}
	
	public void resetPID(){
		error = 0;
		lastError = 0;
		diffError = 0;
		iCounter = 0;
		output = 0;
	}
	
	public void updatePID(double p, double i, double d){
		this.p = p;
		this.i = i;
		this.d = d;
	}
	
	public double getError(){
		return error;
	}
	
}
