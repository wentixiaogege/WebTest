package edu.itu.jacktest;
/**
 * 
 * @author jack
 *
 */

public class SmartMeterDataRecord {

	private  long timestamp;
	private int rms_V1;
	private float rms_I1;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getRms_V1() {
		return rms_V1;
	}
	public void setRms_V1(int rms_V1) {
		this.rms_V1 = rms_V1;
	}
	public float getRms_I1() {
		return rms_I1;
	}
	public void setRms_I1(float rms_I1) {
		this.rms_I1 = rms_I1;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "SmartMeterDataRecord [timestamp="+timestamp+", rms_V1="+rms_V1+", rms_I1="+rms_I1+"]";
	}
	
}
