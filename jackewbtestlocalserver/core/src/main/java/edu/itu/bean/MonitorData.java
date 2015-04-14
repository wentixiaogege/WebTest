// default package
// Generated Apr 6, 2015 2:49:25 PM by Hibernate Tools 3.4.0.CR1
package edu.itu.bean;
import java.util.Date;

/**
 * MonitorData generated by hbm2java
 */
public class MonitorData implements java.io.Serializable {

	private Integer monitorDataId;
	private Date timestamp;
	private Integer smLoadConfigId;
	private String paramName;
	private Float paramMax;
	private Float paramMin;
	private Integer processed;

	public MonitorData() {
	}

	public MonitorData(Integer smLoadConfigId, String paramName,
			Float paramMax, Float paramMin, Integer processed) {
		this.smLoadConfigId = smLoadConfigId;
		this.paramName = paramName;
		this.paramMax = paramMax;
		this.paramMin = paramMin;
		this.processed = processed;
	}

	public Integer getMonitorDataId() {
		return this.monitorDataId;
	}

	public void setMonitorDataId(Integer monitorDataId) {
		this.monitorDataId = monitorDataId;
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

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Float getParamMax() {
		return this.paramMax;
	}

	public void setParamMax(Float paramMax) {
		this.paramMax = paramMax;
	}

	public Float getParamMin() {
		return this.paramMin;
	}

	public void setParamMin(Float paramMin) {
		this.paramMin = paramMin;
	}

	public Integer getProcessed() {
		return this.processed;
	}

	public void setProcessed(Integer processed) {
		this.processed = processed;
	}

}
