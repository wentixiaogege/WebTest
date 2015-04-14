// default package
// Generated Apr 6, 2015 2:49:25 PM by Hibernate Tools 3.4.0.CR1
package edu.itu.bean;
/**
 * Command generated by hbm2java
 */
public class Command implements java.io.Serializable {

	private int commandId;
	private String name;
	private Integer dataLength;
	private String paramName1;
	private String paramName2;

	public Command() {
	}

	public Command(int commandId) {
		this.commandId = commandId;
	}

	public Command(int commandId, String name, Integer dataLength,
			String paramName1, String paramName2) {
		this.commandId = commandId;
		this.name = name;
		this.dataLength = dataLength;
		this.paramName1 = paramName1;
		this.paramName2 = paramName2;
	}

	public int getCommandId() {
		return this.commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDataLength() {
		return this.dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
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

}