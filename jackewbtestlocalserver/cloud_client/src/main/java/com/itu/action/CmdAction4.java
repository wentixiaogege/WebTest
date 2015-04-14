package com.itu.action;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONObject;

import com.itu.logic.FrontServer3;

@Path("/cmdAction4")
public class CmdAction4 extends JerseyAction {

	FrontServer3 server3;

	@Override
	protected void initLogic() {
		server3 = new FrontServer3("http://localhost:8080/EmsFront/front/frontclientaction2", HttpMethod.POST);
	}

	@Override
	public Object doPostAction(Object params) {
		// TODO Auto-generated method stub
		// return null;
		String res = "";
		JSONObject jo = new JSONObject();
		try {
			res = server3.executeLogic(new String[] { "3", "5" });
			jo.put("reslut", res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo;
	}

	@Override
	public Object convertToPostParams(MultivaluedMap<String, String> queryParameters) {
		//Builder newBuilder = SmartMeterDataAction.newBuilder();
		//newBuilder.addRecords(null);
		//SmartMeterDataAction action = newBuilder.build();
		//action.getRecordsList();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doGetAction(Object params) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public Object convertToGetParams(MultivaluedMap<String, String> queryParameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
