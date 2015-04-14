package source;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocalServerLightManipulation {
	private int coordinator_id = 0;
	private int sm_id = 0;
	private int load_id = 0;
	private int load_config_id = 0;
	private boolean manipulation = false;

	public int getLoad_config_id() {
		return load_config_id;
	}

	public void setLoad_config_id(int load_config_id) {
		this.load_config_id = load_config_id;
	}

	public int getSm_id() {
		return sm_id;
	}

	public int getCoordinator_id() {
		return coordinator_id;
	}

	public void setCoordinator_id(int coordinator_id) {
		this.coordinator_id = coordinator_id;
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

	public boolean isManipulation() {
		return manipulation;
	}

	public void setManipulation(boolean manipulation) {
		this.manipulation = manipulation;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LocalServerSmartMeterManipulation sm_id=" + sm_id
				+ " load_config_id=" + load_config_id + "load_id=" + load_id
				+ "manipulation=" + manipulation + "";
	}

}
