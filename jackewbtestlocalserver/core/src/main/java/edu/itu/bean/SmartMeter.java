// default package
// Generated Apr 6, 2015 2:49:25 PM by Hibernate Tools 3.4.0.CR1
package edu.itu.bean;
/**
 * SmartMeter generated by hbm2java
 */
public class SmartMeter implements java.io.Serializable {

	private int smId;
	private String smName;
	private byte[] smIeeeAddress;
	private Integer coordinatorId;
	private String validation;
	private byte[] smStatus;

	public SmartMeter() {
	}

	public SmartMeter(int smId) {
		this.smId = smId;
	}

	public SmartMeter(int smId, String smName, byte[] smIeeeAddress,
			Integer coordinatorId, String validation, byte[] smStatus) {
		this.smId = smId;
		this.smName = smName;
		this.smIeeeAddress = smIeeeAddress;
		this.coordinatorId = coordinatorId;
		this.validation = validation;
		this.smStatus = smStatus;
	}

	public int getSmId() {
		return this.smId;
	}

	public void setSmId(int smId) {
		this.smId = smId;
	}

	public String getSmName() {
		return this.smName;
	}

	public void setSmName(String smName) {
		this.smName = smName;
	}

	public byte[] getSmIeeeAddress() {
		return this.smIeeeAddress;
	}

	public void setSmIeeeAddress(byte[] smIeeeAddress) {
		this.smIeeeAddress = smIeeeAddress;
	}

	public Integer getCoordinatorId() {
		return this.coordinatorId;
	}

	public void setCoordinatorId(Integer coordinatorId) {
		this.coordinatorId = coordinatorId;
	}

	public String getValidation() {
		return this.validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public byte[] getSmStatus() {
		return this.smStatus;
	}

	public void setSmStatus(byte[] smStatus) {
		this.smStatus = smStatus;
	}

}