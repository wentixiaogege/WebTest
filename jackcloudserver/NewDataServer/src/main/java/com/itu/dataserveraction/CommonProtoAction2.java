package com.itu.dataserveraction;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.protobuf.Message;
import com.itu.dataserverlogic.CommonProtoLogic2;

import edu.itu.util.ItuMediaType;
import edu.itu.util.Log4jUtil;

public abstract class CommonProtoAction2<T extends Message> extends CommonAction<T, String> {
	protected Logger logger = Log4jUtil.getLogger(CommonProtoAction2.class);

	CommonProtoLogic2<T> cmpLogic;

	public CommonProtoAction2() {
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
		String result = cmpLogic.executeAction(t);
		return ResponseResult(result);
	}

	/**
	 * this is method with return type T
	 * 
	 * @param t
	 * @return
	 */
	@GET
	@Path("return")
	@Consumes(ItuMediaType.APPLICATION_PROTOBUF)
	public Response doGetWithReturn(T t) {
		T result = cmpLogic.executeActionBuffer(t);
		return ResponseResult(result);
	}

	@POST
	@Consumes(ItuMediaType.APPLICATION_PROTOBUF)
	public Response doPost(T t) {
		String result = cmpLogic.executeAction(t);
		logger.debug("result is " + result);
		return ResponseResult(result);
	}

	/**
	 * this is method with return type T
	 * 
	 * @param t
	 * @return
	 */
	@POST
	@Path("return")
	@Consumes(ItuMediaType.APPLICATION_PROTOBUF)
	public Response doPostWithReturn(T t) {
		T result = cmpLogic.executeActionBuffer(t);
		return ResponseResult(result);
	}
}
