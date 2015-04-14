package com.itu.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListSmartMeterData {
	private List<SmartMeterData> listsmarMeterDatas;
	public ListSmartMeterData(){
		this.setListsmarMeterDatas(new ArrayList<SmartMeterData>());
	}
	
	public ListSmartMeterData(List<SmartMeterData> listSmartMeterDatas){
		this.setListsmarMeterDatas(listSmartMeterDatas);
	}

	public List<SmartMeterData> getListsmarMeterDatas() {
		return listsmarMeterDatas;
	}

	public void setListsmarMeterDatas(List<SmartMeterData> listsmarMeterDatas) {
		this.listsmarMeterDatas = listsmarMeterDatas;
	}
	
	public void addListsmarMeterDatas(SmartMeterData data) {
//		this.listsmarMeterDatas = listsmarMeterDatas;
		this.listsmarMeterDatas.add(data);
	}
	
	public int size(){
		return listsmarMeterDatas.size();
	}
}
