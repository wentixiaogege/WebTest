package com.itu.action;

import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

@Path("/testaction")
public class TestAction extends JerseyAction {

	@Override
	public Object doPostAction(Object params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object convertToPostParams(
			MultivaluedMap<String, String> queryParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doGetAction(Object params) {
		// TODO Auto-generated method stub
//		return null;
		return "{'key':'value'}";
	}

	@Override
	public Object convertToGetParams(
			MultivaluedMap<String, String> queryParameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
