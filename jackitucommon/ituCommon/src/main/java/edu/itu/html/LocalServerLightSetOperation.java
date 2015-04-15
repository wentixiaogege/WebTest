package edu.itu.html;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocalServerLightSetOperation {
	private int coordinator_id =0;
	private int sm_id =0;
	private int load_id = 0;
	private int load_config_id = 0;
	private float max_value = 0;
	private float min_value = 0;
	public int getCoordinator_id() {
		return coordinator_id;
	}
	public void setCoordinator_id(int coordinator_id) {
		this.coordinator_id = coordinator_id;
	}
	public int getSm_id() {
		return sm_id;
	}
	public void setSm_id(int sm_id) {
		this.sm_id = sm_id;
	}
	public int getLoad_id() {
		return load_id;
	}
	public void setLoad_id(int load_id) {
		this.load_id = load_id;
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
	public int getLoad_config_id() {
		return load_config_id;
	}
	public void setLoad_config_id(int load_config_id) {
		this.load_config_id = load_config_id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LocalServerLightSetOperation load_config_id ="+load_config_id+"";
	}
}
