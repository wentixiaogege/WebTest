// default package
// Generated Apr 6, 2015 2:49:25 PM by Hibernate Tools 3.4.0.CR1
package edu.itu.bean;
/**
 * SmartMeterLoadConfig generated by hbm2java
 */
public class SmartMeterLoadConfig implements java.io.Serializable {

	private Integer smLoadConfigId;
	private Integer smId;
	private Integer smLoadId;
	private String smLoadName;
	private String smLoadDescription;
	private String paramName1;
	private String paramName2;
	private String paramName3;

	public SmartMeterLoadConfig() {
	}

	public SmartMeterLoadConfig(Integer smId, Integer smLoadId,
			String smLoadName, String smLoadDescription, String paramName1,
			String paramName2, String paramName3) {
		this.smId = smId;
		this.smLoadId = smLoadId;
		this.smLoadName = smLoadName;
		this.smLoadDescription = smLoadDescription;
		this.paramName1 = paramName1;
		this.paramName2 = paramName2;
		this.paramName3 = paramName3;
	}

	public Integer getSmLoadConfigId() {
		return this.smLoadConfigId;
	}

	public void setSmLoadConfigId(Integer smLoadConfigId) {
		this.smLoadConfigId = smLoadConfigId;
	}

	public Integer getSmId() {
		return this.smId;
	}

	public void setSmId(Integer smId) {
		this.smId = smId;
	}

	public Integer getSmLoadId() {
		return this.smLoadId;
	}

	public void setSmLoadId(Integer smLoadId) {
		this.smLoadId = smLoadId;
	}

	public String getSmLoadName() {
		return this.smLoadName;
	}

	public void setSmLoadName(String smLoadName) {
		this.smLoadName = smLoadName;
	}

	public String getSmLoadDescription() {
		return this.smLoadDescription;
	}

	public void setSmLoadDescription(String smLoadDescription) {
		this.smLoadDescription = smLoadDescription;
	}

	public String getParamName1() {
		return this.paramName1;
	}

	public void setParamName1(String paramName1) {
		this.paramName1 = paramName1;
	}

	public String getParamName2() {
		return this.paramName2;
	}

	public void setParamName2(String paramName2) {
		this.paramName2 = paramName2;
	}

	public String getParamName3() {
		return this.paramName3;
	}

	public void setParamName3(String paramName3) {
		this.paramName3 = paramName3;
	}

}
