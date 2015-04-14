// default package
// Generated Apr 6, 2015 2:49:25 PM by Hibernate Tools 3.4.0.CR1
package edu.itu.bean;
import java.util.Date;

/**
 * DataRegisterData generated by hbm2java
 */
public class DataRegisterData implements java.io.Serializable {

	private Integer dataRegisterDataId;
	private Date timestamp;
	private Integer smId;
	private byte[] dataRegisterDataValue;
	private String processed;

	public DataRegisterData() {
	}

	public DataRegisterData(Integer smId, byte[] dataRegisterDataValue,
			String processed) {
		this.smId = smId;
		this.dataRegisterDataValue = dataRegisterDataValue;
		this.processed = processed;
	}

	public Integer getDataRegisterDataId() {
		return this.dataRegisterDataId;
	}

	public void setDataRegisterDataId(Integer dataRegisterDataId) {
		this.dataRegisterDataId = dataRegisterDataId;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getSmId() {
		return this.smId;
	}

	public void setSmId(Integer smId) {
		this.smId = smId;
	}

	public byte[] getDataRegisterDataValue() {
		return this.dataRegisterDataValue;
	}

	public void setDataRegisterDataValue(byte[] dataRegisterDataValue) {
		this.dataRegisterDataValue = dataRegisterDataValue;
	}

	public String getProcessed() {
		return this.processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

}
