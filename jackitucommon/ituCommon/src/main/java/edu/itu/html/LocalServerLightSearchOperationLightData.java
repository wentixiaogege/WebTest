package edu.itu.html;

public class LocalServerLightSearchOperationLightData {
	private int load_config_id ;
	private int load_id ;
	private String load_name ;
	private float temp_data ;
	private float max_value ;
	private float min_value ;
	private int status ;
	
	public int getLoad_config_id() {
		return load_config_id;
	}
	public void setLoad_config_id(int load_config_id) {
		this.load_config_id = load_config_id;
	}
	public int getLoad_id() {
		return load_id;
	}
	public void setLoad_id(int load_id) {
		this.load_id = load_id;
	}
	public String getLoad_name() {
		return load_name;
	}
	public void setLoad_name(String load_name) {
		this.load_name = load_name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public float getTemp_data() {
		return temp_data;
	}
	public void setTemp_data(float temp_data) {
		this.temp_data = temp_data;
	}
	public float getMax_value() {
		return max_value;
	}
	public void setMax_value(float max_value) {
		this.max_value = max_value;
	}
	public float getMin_value() {
		return min_value;
	}
	public void setMin_value(float min_value) {
		this.min_value = min_value;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
 		return "\nlights load_config_id="+load_config_id+"\n load_id="+load_id+"\n load_name="+load_name+"\n temp_data="+temp_data+"\n status="+status+"";

	}
	
}
