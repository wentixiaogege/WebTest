package com.itu.locallogic;
/*package com.itu.dataserverlogic;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.bean.SmartMeterData;

import edu.itu.proto.CommonEnum.OpterationType;
import edu.itu.proto.CommonEnum.ResultType;
import edu.itu.proto.LocalServerSmartMeterDataActionProtos.LocalServerSmartMeterDataAction;
import edu.itu.proto.LocalServerSmartMeterDataRecordProtos.LocalServerSmartMeterDataRecord;
import edu.itu.proto.ResultsProtos;
import edu.itu.proto.ResultsProtos.Result;
import edu.itu.util.ClassDeepCopy;
import edu.itu.util.Log4jUtil;

public class LocalServerAddLogic extends
		CommonProtoLogic<LocalServerSmartMeterDataAction, Result> {

	Logger logger = Log4jUtil.getLogger(LocalServerAddLogic.class);
	ResultsProtos.Result.Builder resultbuilder = ResultsProtos.Result
			.newBuilder();

	@Override
	public Result executeAction() {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public Result executeAction(LocalServerSmartMeterDataAction t) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		logger.debug(t.getOperation());
		if (t.getOperation().equals(OpterationType.ADD)) {
			System.out.println("cominghere");
			int count = t.getRecordsCount();

			java.util.List<SmartMeterData> hibernatesmdatalist = new ArrayList<>();
			System.out.println(count);
			for (int i = 0; i < count; i++) {
				LocalServerSmartMeterDataRecord record = t.getRecords(i);

				SmartMeterData bean = new SmartMeterData();
				if (ClassDeepCopy.CopyProtoToBean(record, bean)) {
					logger.debug("copying ...");
				} else {
					logger.debug("copy error");
					resultbuilder.setRes(ResultType.FALSE);
					//resultbuilder.setRes("false");
					resultbuilder.setErrMsg("internal copy error");
				}
				hibernatesmdatalist.add(bean);
			}

			System.out.println("after setting here");
			logger.debug("after setting here");

			if (DataAccess.addOperation(hibernatesmdatalist)) {
				resultbuilder.setRes(ResultType.TRUE);
				logger.debug("add new data");
			} else {
				logger.debug("add new data error");
				resultbuilder.setErrMsg("add new data error");
				resultbuilder.setRes(ResultType.FALSE);
			}
			System.out.println("after adding here");
			logger.debug("add " + count + "sm records to database");

			return resultbuilder.build();
		}
		resultbuilder.setRes(ResultType.FALSE);
		resultbuilder.setErrMsg("checking t.getOperation().equals(add) error");
		return resultbuilder.build();
	}

}
*/