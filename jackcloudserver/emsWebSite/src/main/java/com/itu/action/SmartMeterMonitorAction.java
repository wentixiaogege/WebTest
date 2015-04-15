package com.itu.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.itu.adapter.SmartMeterMoniterAdapter;
import com.itu.bean.ListSmartMeterData;
import com.itu.bean.SmartMeterDataQuery;
import com.itu.logic.SmartMeterMonitorLogic;

import edu.itu.util.DateUtils;
import edu.itu.util.ItuStaticUtil;
import edu.itu.util.RandomUtil;

@Path("/smartmetermonitoraction")
public class SmartMeterMonitorAction extends JerseyAction {
	SmartMeterMonitorLogic logic;

	@Override
	protected void initLogic() {
		logic = new SmartMeterMonitorLogic(ItuStaticUtil.SMARTMETERINFO_MONITER_URL, HttpMethod.POST);
	}

	/**
	 * return the json of smartmeterdata.
	 * 
	 * @throws Exception
	 */
	@Override
	public Object doPostAction(Object params) throws Exception {

		SmartMeterDataQuery query = (SmartMeterDataQuery) params;
		logger.info(query.toString());

		// ListSmartMeterData listSmartMeterData = logic.executeLogic(query);

		SmartMeterMoniterAdapter adapter = new SmartMeterMoniterAdapter();
		// return adapter.convertToJson(listSmartMeterData);

		// ###################333just return a test json this is for a
		// test############################
		return toTestJson(query.getTestNum());

	}

	private Object toTestJson(String flag) throws Exception {
		JSONArray array = new JSONArray();
		for (int i = 0; i < 4; i++) {
			JSONObject joRes = new JSONObject();
			joRes.put("Data", getArrResult());
			joRes.put("LoadId", i);
			joRes.put("LoadName", "load-" + i);
			array.put(joRes);
		}

		return array;
	}

	private Collection getArrResult() {
		List<Map<String, Object>> arrResult = new ArrayList<>();

		// t.getListsmarMeterDatas().forEach(x -> {
		// // times.add(x.getTimestamp().toString());
		// Map<String, Object> obs = new HashMap<String, Object>();
		// obs.put("timestamp", x.getTimestamp().toString());
		// obs.put("rmsV1", x.getRmsV1());
		// obs.put("rmsI1", x.getRmsI1());
		// arrResult.add(obs);
		// });
		final int len = 30;
		// String[] dates = { "2015-02-11 16:49:01", "2015-02-11 16:49:02",
		// "2015-02-11 16:49:03", "2015-02-11 16:49:04", "2015-02-11 16:49:05"
		// };
		String[] dates = new String[len];
		// t
		// Date today = DateUtils.getToday();
		Calendar now = DateUtils.getNow();
		for (int i = 0; i < dates.length; i++) {
			// today
			now.add(Calendar.MINUTE, 2);
			// now.getTime()
			dates[i] = DateUtils.getDateString(now.getTime());
		}

		int[] rmsV1s = new int[len];
		// new Random().
		for (int i = 0; i < rmsV1s.length; i++) {
			rmsV1s[i] = RandomUtil.randInt(102, 200);
		}

		double[] rmsI1s = new double[len];
		for (int i = 0; i < rmsI1s.length; i++) {
			rmsI1s[i] = RandomUtil.randDouble(0.75, 1.5);
		}

		for (int i = 0; i < len; i++) {
			// int u = "1".equals(flag) ? i : 4 - i;
			int u = i;
			Map<String, Object> obs = new HashMap<String, Object>();
			obs.put("timestamp", dates[u]);
			obs.put("rmsV1", rmsV1s[u]);
			obs.put("rmsI1", rmsI1s[u]);
			arrResult.add(obs);

		}
		return arrResult;
	}

	@Override
	public Object convertToPostParams(MultivaluedMap<String, String> queryParameters) {
		int searchType = Integer.valueOf(queryParameters.getFirst("searchTimeType"));
		logger.debug("searchTimeType:" + searchType);
		SmartMeterDataQuery query = new SmartMeterDataQuery();

		query.setSearchType(searchType);
		// search by date
		if (searchType == 0) {
			// start time ,end time
			String startDateString = queryParameters.getFirst("startDate");
			if (!StringUtils.isEmpty(startDateString) && !"undefined".equals(startDateString))
				query.setStartTime(DateUtils.toUnixTime(startDateString, DateUtils.FOMAT_DATE2));

			String endDateString = queryParameters.getFirst("endDate");
			if (!StringUtils.isEmpty(endDateString) && !"undefined".equals(endDateString))
				query.setEndTime(DateUtils.toUnixTime(endDateString, DateUtils.FOMAT_DATE2));
		} else {
			// search by peroid
			query.setPeroid(getIntervalFromParam(queryParameters.getFirst("peroid")));
		}

		// smId
		query.SmartIds.add(Integer.parseInt(queryParameters.getFirst("smId")));

		// interval
		query.setInterval(Integer.parseInt(queryParameters.getFirst("interval")));

		return query;
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
