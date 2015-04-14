package edu.itu.localrest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.itu.jacktest.CommonJSONAction;
import edu.itu.jacktest.SmartMeterDataRecordAction;
import edu.itu.util.Log4jUtil;


@Path("/LocalServerAddSmartMeterData")
public class SearchLocalServerSmartMeterDataAction extends CommonJSONAction<SmartMeterDataRecordAction,SmartMeterDataRecordAction>{

	@Override
	protected void initCommonJSONLogic() {
		// TODO Auto-generated method stub
		logger = Log4jUtil.getLogger(SearchLocalServerSmartMeterDataAction.class);
		cmpLogic =  new SearchLocalServerSmartMeterDataLogic();
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("testing using hello");
		return "Hello Jersey";
	}
}
