package com.itu.localaction;

import javax.ws.rs.Path;

import com.itu.dataserveraction.CommonProtoAction;
import com.itu.locallogic.AddCommandLogic;

import edu.itu.proto.LocalServerSmartMeterActionProtos.LocalServerSmartMeterAction;
import edu.itu.proto.ResultsProtos.Result;

@Path("/LocalServerAddCommandAction")
public class AddCommandAction extends CommonProtoAction<LocalServerSmartMeterAction,Result > {

	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		cmpLogic = new AddCommandLogic();
		
	}

}
