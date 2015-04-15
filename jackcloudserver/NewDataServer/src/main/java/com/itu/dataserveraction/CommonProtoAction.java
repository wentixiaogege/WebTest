package com.itu.dataserveraction;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.protobuf.Message;
import com.itu.dataserverlogic.CommonProtoLogic;

import edu.itu.util.ItuMediaType;
import edu.itu.util.Log4jUtil;

public abstract class CommonProtoAction<T extends Message, V extends Message> extends CommonAction<T, V> {
	 

	protected CommonProtoLogic<T, V> cmpLogic;

	public CommonProtoAction() {
		logger = Log4jUtil.getLogger(CommonProtoAction.class);
		initCommonProtoLogic();
	}

	@Override
	protected void initLogic() {

	}

	protected abstract void initCommonProtoLogic();

	/**
	 * this is default method, get a T, return a string
	 * 
	 * @param t
	 * @return
	 */
	
	@GET
	@Consumes(ItuMediaType.APPLICATION_PROTOBUF)
	public Response doGet(T t) {
		logger.debug("do get is starting");
		V result = cmpLogic.executeAction(t);
		return ResponseResult(result);
	}

	@GET
	@Consumes(ItuMediaType.APPLICATION_PROTOBUF)
	@Path("noparam")
	public Response doGet() {
		logger.debug("do get is starting");
		V result = cmpLogic.executeAction();
		return ResponseResult(result);
	}

	@POST
	@Consumes(ItuMediaType.APPLICATION_PROTOBUF)
	public Response doPost(T t) {
		System.out.println("here");
		logger.debug("do post is starting");
		V result = cmpLogic.executeAction(t);
		return ResponseResult(result);
	}

	@POST
	@Consumes(ItuMediaType.APPLICATION_PROTOBUF)
	@Path("noparam")
	public Response doPost() {
		V result = cmpLogic.executeAction();
		return ResponseResult(result);
	}
}
