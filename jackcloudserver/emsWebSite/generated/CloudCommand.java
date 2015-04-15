// default package
// Generated Apr 8, 2015 4:51:37 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * CloudCommand generated by hbm2java
 */
public class CloudCommand implements java.io.Serializable {

	private Integer id;
	private String name;
	private Integer dataLength;
	private Integer checked;
	private Date timestamp;
	private Integer coordinatorId;
	private Integer smartmeterId;
	private Integer param1;
	private Integer commandId;

	public CloudCommand() {
	}

	public CloudCommand(String name, Integer dataLength, Integer checked, Date timestamp, Integer coordinatorId, Integer smartmeterId,
			Integer param1, Integer commandId) {
		this.name = name;
		this.dataLength = dataLength;
		this.checked = checked;
		this.timestamp = timestamp;
		this.coordinatorId = coordinatorId;
		this.smartmeterId = smartmeterId;
		this.param1 = param1;
		this.commandId = commandId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public Integer getChecked() {
		return this.checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getCoordinatorId() {
		return this.coordinatorId;
	}

	public void setCoordinatorId(Integer coordinatorId) {
		this.coordinatorId = coordinatorId;
	}

	public Integer getSmartmeterId() {
		return this.smartmeterId;
	}

	public void setSmartmeterId(Integer smartmeterId) {
		this.smartmeterId = smartmeterId;
	}

	public Integer getParam1() {
		return this.param1;
	}

	public void setParam1(Integer param1) {
		this.param1 = param1;
	}

	public Integer getCommandId() {
		return this.commandId;
	}

	public void setCommandId(Integer commandId) {
		this.commandId = commandId;
	}

}
