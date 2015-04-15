package com.itu.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.itu.bean.ListSmartMeterData;

public class SmartMeterMoniterAdapter implements IAdapter<ListSmartMeterData> {

	@Override
	public JSONObject convertToJson(ListSmartMeterData t) throws JSONException {
		// ArrayList<String> times= new ArrayList<>();
		// ArrayList<Integer> vs = new ArrayList<>();
		// ArrayList<BigDecimal> is=new ArrayList<>();
		List<Map<String, Object>> arrResult = new ArrayList<>();

		t.getListsmarMeterDatas().forEach(x -> {
				Map<String, Object> obs = new HashMap<String, Object>();
				obs.put("timestamp", x.getTimestamp().toString());
				obs.put("rmsV1", x.getRmsV1());
				obs.put("rmsI1", x.getRmsI1());
				arrResult.add(obs);
		});


		// JSONObject joTimes = new JSONObject();
		// joTimes.put("timestamp", times);
		//
		// JSONObject joRmsV1 = new JSONObject();
		// joRmsV1.put("rmsV1", vs);
		//
		// JSONObject joRmsI1 = new JSONObject();
		// joRmsI1.put("rmsI1", is);
		//
		// JSONArray ja = new JSONArray();
		// ja.put(joTimes).put(joRmsV1).put(joRmsI1);

		JSONObject joRes = new JSONObject();
		joRes.put(t.getClass().getSimpleName(), arrResult);
		return joRes;
	}

}
