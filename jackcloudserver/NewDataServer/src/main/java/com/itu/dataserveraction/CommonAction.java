package com.itu.dataserveraction;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import edu.itu.util.Log4jUtil;

public abstract class CommonAction<T, V> {
	protected Logger logger = Log4jUtil.getLogger(CommonAction.class);

	protected com.itu.dataserverlogic.ICommonLogic<T, V> commonLogic;

	public CommonAction() {
		initLogic();
	}

	protected abstract void initLogic();

	protected Response ResponseResult(Object result) {
		return Response.status(200).entity(result).build();
	}

}
