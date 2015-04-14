package com.itu.action;

import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;

import com.itu.bean.ListSmartMeterData;
import com.itu.bean.SmartMeterData;

@Path("/cmdAction3")
public class CmdAction3 extends JerseyAction {

	@Override
	public Object doPostAction(Object params) {
		// TODO Auto-generated method stub
		String p = (String) params;
		logger.debug(p);

		// test json
		// JSONArray array = new JSONArray();
		//
		// for (int i = 0; i < 6; i++) {
		// JSONObject o = new JSONObject();
		// try {
		// o.put("kevin", i);
		// array.put(o);
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }

		// 2 test class
		// return new MyJaxbBean("zhangsan", 18);

		// 3 test smart meter
		// List<SmartMeter> lstSmartMeters = new ArrayList<SmartMeter>();
		// for (int i = 0; i < 5; i++) {
		// SmartMeter sm = new SmartMeter();
		// sm.setId(i);
		// sm.setCoordinatorId(i);
		// sm.setIeeeAddress("ieeeAddress" + i);
		// sm.setValidation(i+2);
		// lstSmartMeters.add(sm);
		// }
		// return lstSmartMeters;

		// int i=3;
		// SmartMeter sm = new SmartMeter();
		// sm.setId(i);
		// sm.setCoordinatorId(i);
		// sm.setIeeeAddress("ieeeAddress" + i);
		// sm.setValidation(i+2);
		// return sm;
		//SmartMeterDataAction action = SmartMeterDataAction.newBuilder().setOperation(OpterationType.MODIFY).setErrMsg("error").build();

		//logger.debug("operationtype:" + action.getOperation().toString());

		ListSmartMeterData llsData = new ListSmartMeterData();
		SmartMeterData smd = new SmartMeterData();
		smd.setId(2);
		smd.setMagI1(15);
		llsData.addListsmarMeterDatas(smd);

		SmartMeterData smd2 = new SmartMeterData();
		smd2.setId(2);
		llsData.addListsmarMeterDatas(smd2);
		return llsData;
		// return null;
	}

	@Override
	public Object convertToPostParams(MultivaluedMap<String, String> queryParameters) {
		// TODO Auto-generated method stub
		String selCmd = queryParameters.getFirst("selCmd");
		String rdiParam = queryParameters.getFirst("rdiParam");
		logger.debug(String.format("selCmd:%s, rdiParam:%s", selCmd, rdiParam));
		return selCmd + "&" + rdiParam;
	}

	@Override
	public Object doGetAction(Object params) {
		return new MyJaxbBean("wangwu", 27);
	}

	@Override
	public Object convertToGetParams(MultivaluedMap<String, String> queryParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public Object getResult() {
	// // TODO Auto-generated method stub
	// // return super.getResult();
	// return new MyJaxbBean("Agamemnon", 32);
	// }

}

@XmlRootElement
class MyJaxbBean {
	public String name;
	public int age;

	public MyJaxbBean() {
	} // JAXB needs this

	public MyJaxbBean(String name, int age) {
		this.name = name;
		this.age = age;
	}
}
