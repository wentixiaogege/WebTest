package com.itu.localaction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.itu.dataserveraction.CommonProtoAction;
import com.itu.locallogic.AddSmartMeterLoadDataLogic;

import edu.itu.proto.LocalServerSmartMeterActionProtos.LocalServerSmartMeterAction;
import edu.itu.proto.ResultsProtos.Result;
import edu.itu.util.Log4jUtil;

@Path("/LocalServerAddSmartMeterData")
public class AddSmartMeterLoadDataAction extends CommonProtoAction<LocalServerSmartMeterAction,Result>{
	//Logger logger = Log4jUtil.getLogger(LocalServerAddAction.class);

	
	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		logger = Log4jUtil.getLogger(AddSmartMeterLoadDataAction.class);
		cmpLogic =  new AddSmartMeterLoadDataLogic();
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("testing using hello");
		return "Hello Jersey";
	}
}
