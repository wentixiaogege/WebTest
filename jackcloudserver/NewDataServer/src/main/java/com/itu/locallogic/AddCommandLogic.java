package com.itu.locallogic;

import jacktest.mysql.jdbc.DataAccessJDBC;

import java.util.ArrayList;
import java.util.List;

import com.itu.bean.Command;
import com.itu.dataserverlogic.CommonProtoLogic;

import edu.itu.proto.CommonEnum.OpterationType;
import edu.itu.proto.CommonEnum.ResultType;
import edu.itu.proto.LocalServerCommandProtos.LocalServerCommand;
import edu.itu.proto.LocalServerSmartMeterActionProtos.LocalServerSmartMeterAction;
import edu.itu.proto.ResultsProtos.Result;
import edu.itu.util.ClassDeepCopy;

public class AddCommandLogic extends CommonProtoLogic<LocalServerSmartMeterAction, Result> {


	Result.Builder  Result = edu.itu.proto.ResultsProtos.Result.newBuilder();
	@Override
	public Result executeAction(LocalServerSmartMeterAction t) {
		// TODO Auto-generated method stub
		
		/*
		 * optional OpterationType operation = 1;
		 * optional string status = 12;
		   optional string err_msg = 13;
		 */
		
		//add operation
		
		if(OpterationType.ADD.equals(t.getOperation())){
			
			List<LocalServerCommand> commList = new ArrayList<LocalServerCommand>();
			List<Command>  commList2          = new ArrayList<Command>();
			commList = t.getCommandRecordsList();
			//bean to protoc
			for (LocalServerCommand command : commList) {
				Command beanCommand = new Command();
				if (ClassDeepCopy.CopyProtoToBean(command, beanCommand)) {
					commList2.add(beanCommand);
				} else {
					logger.debug("copy error");
				    	
					Result.setErrMsg("copy error");
					Result.setRes(ResultType.FALSE);
					return Result.build();
				}
			}
			
			
			
			
//			DataAccessJDBC.addOperation(add);
			
			
		}
		return null;
	}



	@Override
	public Result executeAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
