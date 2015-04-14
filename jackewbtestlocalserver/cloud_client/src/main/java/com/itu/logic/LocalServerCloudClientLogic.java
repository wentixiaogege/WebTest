package com.itu.logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.itu.bean.SmartMeterData;

import edu.itu.proto.CommonEnum.OpterationType;
import edu.itu.proto.LocalServerSmartMeterDataActionProtos.LocalServerSmartMeterDataAction;
import edu.itu.proto.LocalServerSmartMeterDataRecordProtos.LocalServerSmartMeterDataRecord;
import edu.itu.proto.ResultsProtos.Result;
import edu.itu.util.ClassDeepCopy;
import edu.itu.util.Log4jUtil;

public class LocalServerCloudClientLogic extends CloudClientLogic<LocalServerSmartMeterDataAction,List<SmartMeterData>, Result> {

	Logger logger = Log4jUtil.getLogger(LocalServerCloudClientLogic.class);
	public LocalServerCloudClientLogic(String url, String method) {
		super(url, method);
	}

	@Override
	public LocalServerSmartMeterDataAction buildMessage(List<SmartMeterData> smartMeterDataList) {
		//String cmdid = params[0];
		//String param1 = params[1];
		//logger.debug("cmdid is " + cmdid);
		//logger.debug("param is " + param1);
		LocalServerSmartMeterDataAction.Builder actionBuilder = LocalServerSmartMeterDataAction.newBuilder();
		//CloudCommand cc = CloudCommand.newBuilder().setId(Integer.parseInt(cmdid)).setParam1(Integer.parseInt(param1)).setCoordinatorId(0)
		//		.setSmartmeterId(2).build();
		//CloudCommand cc2 = CloudCommand.newBuilder().setId(Integer.parseInt("2")).setParam1(Integer.parseInt("3")).setCoordinatorId(2)
		//		.setSmartmeterId(2).build();
		//actionBuilder.addInsertCmds(cc);
		//actionBuilder.addInsertCmds(cc2);
		
		
		int count = smartMeterDataList.size();
		logger.debug("smartMeterDataList size:" + count);
		
		for (SmartMeterData smartMeterData : smartMeterDataList) 
		{
			LocalServerSmartMeterDataRecord.Builder smartMeterDataRecord = LocalServerSmartMeterDataRecord.newBuilder();
			ClassDeepCopy.CopyBeanToProto(smartMeterData, smartMeterDataRecord, "checked");
			//smartMeterDataRecord.setIeeeAddress(smartMeterData.getSmIeeeAddress());
			//ClassDeepCopy.CopyBeanToProto(smartMeterData, smartMeterDataRecord, "Timestamp");
			//smartMeterDataRecord.setTimestamp(DateUtils.toUnixTime(smartMeterData.getTimestamp()));
			actionBuilder.addRecords(smartMeterDataRecord);
			logger.debug("SmartMeterDataRecord ID: " + smartMeterDataRecord.getId());
			
			//ClassDeepCopy.CopyProtoToBean(smartMeterDataRecord, smartMeterData, "Timestamp");
			//logger.debug("SmartMeterDataRecord ID: " + smartMeterData.getId());
		}
		
		actionBuilder.setOperation(OpterationType.ADD);// jack adds here
		
		return actionBuilder.build();
	}

	@Override
	protected Result convertToV(InputStream in, int size)
			throws IOException {
		// TODO Auto-generated method stub
		
		byte[] response = new byte[size];		int curr = 0, read = 0;

		while (curr < size) {
			read = in.read(response, curr, size - curr);
			if (read <= 0)
				break;
			curr += read;
		}

		Result result = Result.parseFrom(response);
		in.close();

		return result;
		
	}

	/**
	@Override
	protected CloudCommand convertToV(InputStream in, int size) throws IOException {
		// int size = conn.getContentLength();

		byte[] response = new byte[size];		int curr = 0, read = 0;

		while (curr < size) {
			read = in.read(response, curr, size - curr);
			if (read <= 0)
				break;
			curr += read;
		}

		CloudCommand cc = CloudCommand.parseFrom(response);
		in.close();

		return cc;
	}
	
	*/


}
