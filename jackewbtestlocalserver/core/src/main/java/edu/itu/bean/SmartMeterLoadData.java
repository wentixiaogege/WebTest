// default package
// Generated Apr 6, 2015 2:49:25 PM by Hibernate Tools 3.4.0.CR1
package edu.itu.bean;
import java.util.Date;

/**
 * SmartMeterLoadData generated by hbm2java
 */
public class SmartMeterLoadData implements java.io.Serializable {

	private Integer smLoadDataId;
	private Date timestamp;
	private Integer smLoadConfigId;
	private Float paramValue1;
	private Float paramValue2;
	private Float paramValue3;
	private Integer processed;
	private Integer loadStatus;

	public SmartMeterLoadData() {
	}

	public SmartMeterLoadData(Integer smLoadConfigId, Float paramValue1,
			Float paramValue2, Float paramValue3, Integer processed,
			Integer loadStatus) {
		this.smLoadConfigId = smLoadConfigId;
		this.paramValue1 = paramValue1;
		this.paramValue2 = paramValue2;
		this.paramValue3 = paramValue3;
		this.processed = processed;
		this.loadStatus = loadStatus;
	}

	public Integer getSmLoadDataId() {
		return this.smLoadDataId;
	}

	public void setSmLoadDataId(Integer smLoadDataId) {
		this.smLoadDataId = smLoadDataId;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getSmLoadConfigId() {
		return this.smLoadConfigId;
	}

	public void setSmLoadConfigId(Integer smLoadConfigId) {
		this.smLoadConfigId = smLoadConfigId;
	}

	public Float getParamValue1() {
		return this.paramValue1;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getSmLoadDataId() + "  "+this.getTimestamp() + " " + this.getSmLoadConfigId() + " " + this.getParamValue1() + " " + this.getProcessed() ;
	}

	public void setParamValue1(Float paramValue1) {
		this.paramValue1 = paramValue1;
	}

	public Float getParamValue2() {
		return this.paramValue2;
	}

	public void setParamValue2(Float paramValue2) {
		this.paramValue2 = paramValue2;
	}

	public Float getParamValue3() {
		return this.paramValue3;
	}

	public void setParamValue3(Float paramValue3) {
		this.paramValue3 = paramValue3;
	}

	public Integer getProcessed() {
		return this.processed;
	}

	public void setProcessed(Integer processed) {
		this.processed = processed;
	}

	public Integer getLoadStatus() {
		return this.loadStatus;
	}

	public void setLoadStatus(Integer loadStatus) {
		this.loadStatus = loadStatus;
	}

}