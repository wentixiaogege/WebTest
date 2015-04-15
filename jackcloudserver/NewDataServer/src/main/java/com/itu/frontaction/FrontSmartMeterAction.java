package com.itu.frontaction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.itu.dataserveraction.CommonProtoAction;
import com.itu.frontlogic.FrontSmartMeterLogic;

import edu.itu.proto.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
import edu.itu.util.Log4jUtil;

@Path("/FrontServerSmartMeterSearchAction")
public class FrontSmartMeterAction extends CommonProtoAction<FrontServerSmartMeterDataAction,FrontServerSmartMeterDataAction> {
	//Logger logger = Log4jUtil.getLogger(CommonAction.class);
	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		logger = Log4jUtil.getLogger(FrontSmartMeterAction.class);
		logger.debug("FrontSmartMeterAction comming here!");
		cmpLogic = new FrontSmartMeterLogic();
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("testing using hello");
		return "Hello Jersey";
	}
}
