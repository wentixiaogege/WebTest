// default package
// Generated Mar 26, 2015 3:44:56 PM by Hibernate Tools 3.4.0.CR1
package com.itu.bean;
import java.util.HashSet;
import java.util.Set;

/**
 * Command generated by hbm2java
 */
public class Command implements java.io.Serializable {

	private int id;
	private String name;
	private Integer dataLength;
	private String param1;
	private String param2;
	private Set commandLogs = new HashSet(0);

	public Command() {
	}

	public Command(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Command(int id, String name, Integer dataLength, String param1,
			String param2, Set commandLogs) {
		this.id = id;
		this.name = name;
		this.dataLength = dataLength;
		this.param1 = param1;
		this.param2 = param2;
		this.commandLogs = commandLogs;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getParam1() {
		return this.param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return this.param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public Set getCommandLogs() {
		return this.commandLogs;
	}

	public void setCommandLogs(Set commandLogs) {
		this.commandLogs = commandLogs;
	}

}
