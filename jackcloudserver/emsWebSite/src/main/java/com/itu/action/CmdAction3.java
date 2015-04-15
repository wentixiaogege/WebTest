package com.itu.action;

import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;

import com.itu.bean.ListSmartMeterData;
import com.itu.bean.SmartMeterData;

import edu.itu.proto.CommonEnum.OpterationType;
import edu.itu.proto.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;

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
		FrontServerSmartMeterDataAction action = FrontServerSmartMeterDataAction.newBuilder().setOperation(OpterationType.MODIFY).setErrMsg("error").build();

		logger.debug("operationtype:" + action.getOperation().toString());

		ListSmartMeterData llsData = new ListSmartMeterData();
		SmartMeterData smd = new SmartMeterData();
		smd.setId(2);
		smd.setMagI1(15);
		llsData.addListsmarMeterDatas(smd);

		SmartMeterData smd2 = new SmartMeterData();
		smd2.setId(2);
		llsData.addListsmarMeterDatas(smd2);
//		return llsData;
		String str= "";
		str+="[";
		str+="[1,  37.8, 8.8, 41.8],";
		str+="[2,  30.9, 69.5, 32.4],";
		str+="[3,  25.4,   57, 25.7],";
		str+="[4,  11.7, 18.8, 10.5],";
		str+="[5,  11.9, 17.6, 10.4],";
		str+="[6,   8.8, 13.6,  7.7],";
		str+="[7,   7.6, 12.3,  9.6],";
		str+="[8,  12.3, 29.2, 10.6],";
		str+="[9,  16.9, 42.9, 14.8],";
		str+="[10, 12.8, 30.9, 11.6],";
		str+="[11,  5.3,  7.9,  4.7],";
		str+="[12,  6.6,  8.4,  5.2],";
		str+="[13,  4.8,  6.3,  3.6],";
		str+="[14,  4.2,  6.2,  3.4]";
		str+="]";
		// return null;
		return str;
	}

	@Override
	public Object convertToPostParams(MultivaluedMap<String, String> queryParameters) {
		// TODO Auto-generated method stub
		String selCmd = queryParameters.getFirst("selCmd");
		String rdiParam = queryParameters.getFirst("rdiParam");
		logger.debug(String.format("selCmd:%s, rdiParam:%s", selCmd, rdiParam));
		
		String title = queryParameters.getFirst("title");
		String content = queryParameters.getFirst("content");
		String param1 =queryParameters.getFirst("param1");
		logger.debug(String.format("title:%s, content:%s, param1:%s", title, content, param1));
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
