package com.itu.logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.itu.bean.ListSmartMeterData;
import com.itu.bean.SmartMeterData;
import com.itu.bean.SmartMeterDataQuery;

import edu.itu.proto.CommonEnum.OpterationType;
import edu.itu.proto.FrontServerSmartMeterDataActionProtos;
import edu.itu.proto.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
import edu.itu.util.ClassDeepCopy;
import edu.itu.util.DateUtils;

public class SmartMeterMonitorLogic extends CloudClientLogic<FrontServerSmartMeterDataAction, SmartMeterDataQuery, ListSmartMeterData> {

	public SmartMeterMonitorLogic(String url, String method) {
		super(url, method);
	}

	@Override
	public FrontServerSmartMeterDataAction buildMessage(SmartMeterDataQuery querys) {
		FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction.Builder actionBuilder = FrontServerSmartMeterDataAction
				.newBuilder();
		actionBuilder.setOperation(OpterationType.SEARCH);
		actionBuilder.setTimeBeforeCurrent(querys.getPeroid());
		actionBuilder.setStartTime(querys.getStartTime());
		actionBuilder.setEndTime(querys.getEndTime());
		actionBuilder.setInterval(querys.getInterval());
		// actionBuilder.addAllSmIds(querys.SmartIds);
		// 1. if serach by intervals, using intervals
		// if (querys.getTimeBeforeCurr() > 0) {
		// } else if (querys.getStartTime() > 0 && querys.getEndTime() > 0) {
		// // 2. search by days
		// }
		FrontServerSmartMeterDataAction action = actionBuilder.build();
		// logger.info(String.format("time before curr:%d, startTime:%s, endTime:%s, cout:%d, interval:%d",
		// action.getTimeBeforeCurrent(),
		// action.getStartTime(), action.getEndTime(), action.getSmIdsCount(),
		// action.getInterval()));
		logger.info("start time:" + DateUtils.fromUnixTimeStr(action.getStartTime()));
		logger.info("end time:" + DateUtils.fromUnixTimeStr(action.getEndTime()));
		// action.getSmIdsList().forEach(x -> logger.info("smart meter id--->" +
		// x));
		return action;
	}

	@Override
	protected ListSmartMeterData convertToV(InputStream in, int size) throws IOException {
		byte[] response = new byte[size];
		int curr = 0, read = 0;

		while (curr < size) {
			read = in.read(response, curr, size - curr);
			if (read <= 0)
				break;
			curr += read;
		}

		// copy SmartMeterDataAction to ListSmartMeterData
		List<SmartMeterData> datas = new ArrayList<SmartMeterData>();
		FrontServerSmartMeterDataAction action = FrontServerSmartMeterDataAction.parseFrom(response);
		// action.getRecordsList().forEach(a -> {
		// SmartMeterData data = new SmartMeterData();
		// ClassDeepCopy.CopyProtoToBean(a, data);
		// datas.add(data);
		// });
		// logger.info("action.getRecordsCount():" + action.getRecordsCount());
		// if (1 == action.getRecordsCount()) {
		// SmartMeterRecords records = action.getRecords(0);
		// records.getRecordsList().forEach(a -> {
		// SmartMeterData data = new SmartMeterData();
		// ClassDeepCopy.CopyProtoToBean(a, data);
		// datas.add(data);
		// });
		// } else {
		// //TODO
		// }

		in.close();

		ListSmartMeterData lstData = new ListSmartMeterData(datas);
		return lstData;
	}

}
