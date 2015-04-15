package com.itu.bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import edu.itu.util.DateUtils;

@XmlRootElement
public class SmartMeterDataQuery {

	private long startTime;
	private long endTime;
	private int timeBeforeCurr;
	public ArrayList<Integer> SmartIds = new ArrayList<Integer>();
	private int interval;

	private int searchType;
	private String testNum;

	@Override
	public String toString() {
		int cnt = SmartIds.size() > 0 ? SmartIds.get(0) : -21;

		return String.format("startTime:%s, endTime:%s, timeBeforeCurr:%d, interval:%d, selectType:%d, smId:%d",
				DateUtils.fromUnixTimeStr(startTime, DateUtils.FOMAT_DATE3), DateUtils.fromUnixTimeStr(endTime, DateUtils.FOMAT_DATE3),
				timeBeforeCurr, interval, searchType, cnt);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getPeroid() {
		return timeBeforeCurr;
	}

	public void setPeroid(int interval) {
		this.timeBeforeCurr = interval;
	}

	public String getTestNum() {
		return testNum;
	}

	public void setTestNum(String testNum) {
		this.testNum = testNum;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

}
