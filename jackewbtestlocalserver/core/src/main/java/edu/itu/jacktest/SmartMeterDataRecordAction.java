package edu.itu.jacktest;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SmartMeterDataRecordAction {
	private int id;
	private int sm_id;
	private long start_time;
	private long end_time;
	private int time_before_current;
	private OpterationType operation;
	private List<SmartMeterDataRecord> listsmdatarecords; 
	private String status;
	private String err_msg;
	private int interval;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSm_id() {
		return sm_id;
	}
	public void setSm_id(int sm_id) {
		this.sm_id = sm_id;
	}
	public long getStart_time() {
		return start_time;
	}
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	public long getEnd_time() {
		return end_time;
	}
	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}
	public int getTime_before_current() {
		return time_before_current;
	}
	public void setTime_before_current(int time_before_current) {
		this.time_before_current = time_before_current;
	}
	public OpterationType getOperation() {
		return operation;
	}
	public void setOperation(OpterationType operation) {
		this.operation = operation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	
	public List<SmartMeterDataRecord> getListsmdatarecords() {
		return listsmdatarecords;
	}
	public void setListsmdatarecords(List<SmartMeterDataRecord> listsmdatarecords) {
		this.listsmdatarecords = listsmdatarecords;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "SmartMeterDataRecordAction [id="+id+", sm_id="+sm_id+", start_time="+start_time+", "
				+ "end_time="+end_time+", time_before_current="+time_before_current+", operation="+operation+
				", status="+status+", err_msg="+err_msg+", interval="+interval+"]";
	}
	
}
