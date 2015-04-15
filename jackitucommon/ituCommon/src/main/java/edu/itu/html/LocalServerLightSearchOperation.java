package edu.itu.html;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocalServerLightSearchOperation {
	
	private int coordinator_id ;
	private int sm_id ;
	private String sm_name;
	private List<LocalServerLightSearchOperationLightData> lightDatas;
	
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
	public String getSm_name() {
		return sm_name;
	}
	public void setSm_name(String sm_name) {
		this.sm_name = sm_name;
	}
	public List<LocalServerLightSearchOperationLightData> getLightDatas() {
		return lightDatas;
	}
	public void setLightDatas(
			List<LocalServerLightSearchOperationLightData> listLightDatas) {
		this.lightDatas = listLightDatas;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\nLocalServerLightOperation coordinator_id="+coordinator_id+"\n sm_id="+sm_id+"\n sm_name="+sm_name+"\n"+lightDatas.toString()+"";
	}
}
