package edu.itu.jacktest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.itu.util.ItuMediaType;
import edu.itu.util.Log4jUtil;

public abstract class CommonJSONAction<T extends Object,V extends Object>  extends CommonAction<T, V> {

	protected CommonJSONLogic<T, V> cmpLogic;
	@Override
	protected void initLogic() {
		logger = Log4jUtil.getLogger(CommonJSONAction.class);
		initCommonJSONLogic();
		
	}

	protected abstract void initCommonJSONLogic();
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doGet(T t) {
		logger.debug("do get is starting");
		V result = cmpLogic.executeAction(t);
		return ResponseResult(result);
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("noparam")
	public Response doGet() {
		logger.debug("do get is starting");
		V result = cmpLogic.executeAction();
		return ResponseResult(result);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPost(T t) {
		System.out.println("here");
		logger.debug("do post is starting");
		V result = cmpLogic.executeAction(t);
		return ResponseResult(result);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("noparam")
	public Response doPost() {
		V result = cmpLogic.executeAction();
		return ResponseResult(result);
	}
}
