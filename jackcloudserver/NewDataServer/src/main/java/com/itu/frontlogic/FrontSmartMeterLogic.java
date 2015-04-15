package com.itu.frontlogic;
/**
 *   Authour jack
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.itu.DAO.DataAccess;
import com.itu.bean.SmartMeterLightData;
import com.itu.bean.SmartMeterLoadData;
import com.itu.dataserverlogic.CommonProtoLogic;

import edu.itu.proto.CommonEnum.OpterationType;
import edu.itu.proto.FrontServerSmartMeterDataActionProtos;
import edu.itu.proto.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
import edu.itu.proto.FrontServerSmartMeterLoadRecordProtos;
import edu.itu.util.ClassDeepCopy;
import edu.itu.util.DateUtils;
import edu.itu.util.ItuStaticUtil;
import edu.itu.util.Log4jUtil;


public class FrontSmartMeterLogic extends CommonProtoLogic<FrontServerSmartMeterDataAction, FrontServerSmartMeterDataAction> {

	@Override
	public FrontServerSmartMeterDataAction executeAction(FrontServerSmartMeterDataAction t) {
		
		// TODO Auto-generated method stub
		logger = Log4jUtil.getLogger(FrontSmartMeterLogic.class);
		Integer smid = null;
		long starttime = 0, endtime = 0;
		int time_before_current = 0, intervals = 0;
		FrontServerSmartMeterDataAction.Builder smdataaction = FrontServerSmartMeterDataAction.newBuilder();
		logger.debug("front server search here down is opteration type");
		logger.debug(t.getOperation());
		// logger.debug(t.getIdsList());
		if (t.getOperation().equals(OpterationType.SEARCH))//get a specific sm value with all the loads
		{
			/**
			 * the smartmeter time search parameters
			 */
			time_before_current = t.getTimeBeforeCurrent();
			starttime = t.getStartTime();
			endtime = t.getEndTime();
			intervals = t.getInterval();
			smid = t.getSmId();
			logger.debug(time_before_current + starttime + endtime + intervals);
			logger.debug("t.getSmIdsCount()");
			logger.debug(t.getSmId());

			if ((0 != starttime) && (0 != endtime)) {

				logger.debug("(0 != starttime) && (0 != endtime)");
				logger.debug("search all the smartmeters with start time");
				getDataFromDatabase(smid, smdataaction, starttime, endtime, intervals);

			} else {
				logger.debug("(0 == starttime) || (0 == endtime)");
				logger.debug("search dedicated the smartmeters without starttime");
				if (0 == time_before_current)
					time_before_current = 3600;// default 1 hour data;
				getDataFromDatabase(smid, smdataaction, time_before_current, intervals);
			}
		}
		return smdataaction.build();
	}

	/**
	 * 
	 * @param sm_id
	 * @param seconds
	 * @param parameters
	 * @return
	 */
	private String setSearchSQL(Integer sm_id, int seconds, int type,String... parameters) {

		String hql = null;
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat dd = new SimpleDateFormat(ItuStaticUtil.FOMAT_DATE);

		Date endDate = new Date();

		cl.setTime(endDate);
		cl.add(Calendar.SECOND, -seconds);
		

		String start = dd.format(cl.getTime());
		String end = dd.format(endDate);

		logger.debug("00000" + start);
		logger.debug("11111" + end);
		if(type==0)
		 hql = ("from SmartMeterLoadData smtable where smtable.localTimestamp > '" + start + "' and smtable.localTimestamp <= '" + end + "' and smtable.smId = '" + sm_id + "'");
		if(type ==1)
		 hql = ("from SmartMeterLightData smtable where smtable.localTimestamp > '" + start + "' and smtable.localTimestamp <= '" + end + "' and smtable.smId = '" + sm_id + "'");

		return hql;
	}

	/**
	 * 
	 * @param sm_id
	 * @param starttime
	 * @param endtime
	 * @param i
	 * @return
	 */
	private String setSearchSQL(Integer sm_id, long starttime, long endtime, int type) {

		String sql = null,start ,end;
		SimpleDateFormat dd = new SimpleDateFormat(ItuStaticUtil.FOMAT_DATE);
		
	
		if(0 ==endtime){
			start = dd.format(DateUtils.fromUnixTime(starttime));
			end = dd.format(new Date());// for now
		}else {
			start = dd.format(DateUtils.fromUnixTime(starttime));
			end = dd.format(DateUtils.fromUnixTime(endtime));
		}
	   

		logger.debug("long starttime, long endtime" + start);
		logger.debug("long starttime, long endtime" + end);
		if(type ==0)
		 sql = ("from SmartMeterLoadData smtable where smtable.localTimestamp > '" + start + "' and smtable.localTimestamp <= '" + end + "' and smtable.smId = '" + sm_id + "'");// need
		if(type==1)
		 sql = ("from SmartMeterLightData smtable where smtable.localTimestamp > '" + start + "' and smtable.localTimestamp <= '" + end + "' and smtable.smId = '" + sm_id + "'");// need
	
			// test
		return sql;
	}

	/**
	 * 
	 * @param <T>
	 * @param list
	 * @param onesmdatarecords
	 * @return
	 */
	private <T> boolean hibernateListtoProto(List<T> list, int type,FrontServerSmartMeterDataAction.Builder smactionbuilder) {

		for (T hibernateonedata : list) {

			if(type == 0)
			{
				FrontServerSmartMeterLoadRecordProtos.FrontServerSmartMeterLoadRecord.Builder smloaddata = FrontServerSmartMeterLoadRecordProtos.FrontServerSmartMeterLoadRecord.newBuilder();
				if (ClassDeepCopy.CopyBeanToProto(hibernateonedata, smloaddata))
					smactionbuilder.addLoadRecords(smloaddata);
				else {
					logger.debug("copy error");
					return false;
					}
			}else
			{
				FrontServerSmartMeterLoadRecordProtos.FrontServerSmartMeterLightRecord.Builder smlightdata = FrontServerSmartMeterLoadRecordProtos.FrontServerSmartMeterLightRecord.newBuilder();
	
		
				if(ClassDeepCopy.CopyBeanToProto(hibernateonedata, smlightdata))	
					smactionbuilder.addLightRecords(smlightdata);
			    else {
					logger.debug("copy error");
					return false;
				}
			}
		
		}
		return true;
	}
	
	/**
	 * 
	 * @param sm_ids
	 * @param smactionbuilder
	 * @param starttime
	 * @param endtime
	 * @param intervals
	 * @return
	 */
	// fetch data from table and return to Builder
	private void getDataFromDatabase(Integer sm_id, FrontServerSmartMeterDataAction.Builder smactionbuilder, long starttime, long endtime, int intervals) {
			logger.debug(intervals);
			Calendar load_cl = Calendar.getInstance();
			Calendar light_cl = Calendar.getInstance();
			// set the search HQL
		    List<SmartMeterLoadData> listloads = DataAccess.HibernateSearchOperation(setSearchSQL(sm_id, starttime, endtime,0));
		    List<SmartMeterLightData> listlights =  DataAccess.HibernateSearchOperation(setSearchSQL(sm_id, starttime, endtime,1));

			if (intervals > 0) {
				/**
				 * initializing
				 */
				List<SmartMeterLoadData> newloadslist = new ArrayList<SmartMeterLoadData>();
				List<SmartMeterLightData> newlightslist = new ArrayList<SmartMeterLightData>();
				/**
				 * get the interval data from here
				 */
				if ((0 == listloads.size())&&(0 == listlights.size())) {
					smactionbuilder.setErrMsg("listloads and listlights are zero ");
					return;
				}
				/*
				 * set the loads voltage and current value
				 */
				load_cl.setTime(listloads.get(0).getLocalTimestamp());
				newloadslist.add(listloads.get(0));
				load_cl.add(Calendar.SECOND, intervals);
				for (int i1 = 0; i1 < listloads.size(); i1++) {
					SmartMeterLoadData oneloadrecord = listloads.get(i1);
					if (oneloadrecord.getTimestamp().after(load_cl.getTime())) {
						load_cl.add(Calendar.SECOND, intervals);
						newloadslist.add(oneloadrecord);
					}
				}
				/*
				 * set the lights temp value
				 */
				light_cl.setTime(listlights.get(0).getLocalTimestamp());
				newlightslist.add(listlights.get(0));	
				light_cl.add(Calendar.SECOND, intervals);
				
				for (int j1 = 0; j1 < listloads.size(); j1++) {
					SmartMeterLightData onelightrecord = listlights.get(j1);
					if (onelightrecord.getTimestamp().after(light_cl.getTime())) {
						light_cl.add(Calendar.SECOND, intervals);
						newlightslist.add(onelightrecord);
					}

				}

				logger.debug("hibernateListtoProtoget data begin.." + System.nanoTime());
				if (!hibernateListtoProto(newloadslist,0, smactionbuilder))
				{
					smactionbuilder.setErrMsg("copy loads error");
					smactionbuilder.setStatus("false");
				}
				if (!hibernateListtoProto(newlightslist,1, smactionbuilder))
				{
					smactionbuilder.setErrMsg("copy lights error");
					smactionbuilder.setStatus("false");
				}
				logger.debug("hibernateListtoProtoget data end.." + System.nanoTime());
			} else {
				logger.debug(intervals + " zero");
				logger.debug("hibernateListtoProtoget data begin.." + System.nanoTime());
				if (!hibernateListtoProto(listloads,0,smactionbuilder))
				{
					smactionbuilder.setErrMsg("copy error");
					smactionbuilder.setStatus("false");
				}
				if (!hibernateListtoProto(listlights,1,smactionbuilder))
				{
					smactionbuilder.setErrMsg("copy error");
					smactionbuilder.setStatus("false");
				}
				logger.debug("hibernateListtoProtoget data end.." + System.nanoTime());
			}
		}
//	}

	/**
	 * 
	 * @param sm_ids
	 * @param smactionbuilder
	 * @param seconds
	 * @param intervals
	 * @return
	 */
	private void getDataFromDatabase(Integer sm_id, FrontServerSmartMeterDataAction.Builder smactionbuilder, int seconds, int intervals) {
		Calendar load_cl = Calendar.getInstance();
		Calendar light_cl = Calendar.getInstance();
		// set the search HQL
	    List<SmartMeterLoadData> listloads = DataAccess.HibernateSearchOperation(setSearchSQL(sm_id, seconds,0));
	    List<SmartMeterLightData> listlights =  DataAccess.HibernateSearchOperation(setSearchSQL(sm_id, seconds,1));

		if (intervals > 0) {
			List<SmartMeterLoadData> newloadslist = new ArrayList<SmartMeterLoadData>();
			List<SmartMeterLightData> newlightslist = new ArrayList<SmartMeterLightData>();
			/**
			 * 
			 * 
			 * get the interval data from here
			 */
			if ((0 == listloads.size())&&(0 == listlights.size())) {
				smactionbuilder.setErrMsg("listloads and listlights are zero ");
				return;
			}
			load_cl.setTime(listloads.get(0).getLocalTimestamp());
			light_cl.setTime(listlights.get(0).getLocalTimestamp());
			newlightslist.add(listlights.get(0));
			newloadslist.add(listloads.get(0));
			load_cl.add(Calendar.SECOND, intervals);
			light_cl.add(Calendar.SECOND, intervals);
			for (int i1 = 0; i1 < listloads.size(); i1++) {
				SmartMeterLoadData oneloadrecord = listloads.get(i1);
				if (oneloadrecord.getTimestamp().after(load_cl.getTime())) {
					load_cl.add(Calendar.SECOND, intervals);
					newloadslist.add(oneloadrecord);
				}
			}
			for (int j1 = 0; j1 < listloads.size(); j1++) {
				SmartMeterLightData onelightrecord = listlights.get(j1);
				if (onelightrecord.getTimestamp().after(light_cl.getTime())) {
					light_cl.add(Calendar.SECOND, intervals);
					newlightslist.add(onelightrecord);
				}

			}

			logger.debug("hibernateListtoProtoget data begin.." + System.nanoTime());
			if (!hibernateListtoProto(newloadslist,0, smactionbuilder))
			{
				smactionbuilder.setErrMsg("copy loads error");
				smactionbuilder.setStatus("false");
			}
			if (!hibernateListtoProto(newlightslist,1, smactionbuilder))
			{
				smactionbuilder.setErrMsg("copy lights error");
				smactionbuilder.setStatus("false");
			}
			logger.debug("hibernateListtoProtoget data end.." + System.nanoTime());
		} else {
			logger.debug(intervals + " zero");
			logger.debug("hibernateListtoProtoget data begin.." + System.nanoTime());
			if (!hibernateListtoProto(listloads,0,smactionbuilder))
			{
				smactionbuilder.setErrMsg("copy error");
				smactionbuilder.setStatus("false");
			}
			if (!hibernateListtoProto(listlights,1,smactionbuilder))
			{
				smactionbuilder.setErrMsg("copy error");
				smactionbuilder.setStatus("false");
			}
			logger.debug("hibernateListtoProtoget data end.." + System.nanoTime());
		}
	}

	@Override
	public FrontServerSmartMeterDataAction executeAction() {
		// TODO Auto-generated method stub
		return null;
	}
}
