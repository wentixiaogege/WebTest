package edu.itu.bean;

public class SmartMeterLightDataBean {
	private int load_config_id;
	private int coordinator_id;
	private int sm_id;
	private int sm_load_id;
	public int getSm_load_id() {
		return sm_load_id;
	}
	public void setSm_load_id(int sm_load_id) {
		this.sm_load_id = sm_load_id;
	}
	private String sm_name;
	private float temp_data;
	private float max_data;
	private float min_data;
	public String getSm_name() {
		return sm_name;
	}
	public void setSm_name(String sm_name) {
		this.sm_name = sm_name;
	}
	private int load_status;
	public int getLoad_config_id() {
		return load_config_id;
	}
	public void setLoad_config_id(int load_config_id) {
		this.load_config_id = load_config_id;
	}
	public int getCoordinator_id() {
		return coordinator_id;
	}
	public void setCoordinator_id(int coordinator_id) {
		this.coordinator_id = coordinator_id;
	}
	public int getSm_id() {
		return sm_id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	public void setSm_id(int sm_id) {
		this.sm_id = sm_id;
	}
	
	public float getTemp_data() {
		return temp_data;
	}
	public void setTemp_data(float temp_data) {
		this.temp_data = temp_data;
	}
	public float getMax_data() {
		return max_data;
	}
	public void setMax_data(float max_data) {
		this.max_data = max_data;
	}
	public float getMin_data() {
		return min_data;
	}
	public void setMin_data(float min_data) {
		this.min_data = min_data;
	}
	public void setMin_data(int min_data) {
		this.min_data = min_data;
	}
	public int getLoad_status() {
		return load_status;
	}
	public void setLoad_status(int load_status) {
		this.load_status = load_status;
	}
	
}
