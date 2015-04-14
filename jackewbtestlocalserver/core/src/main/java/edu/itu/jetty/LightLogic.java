
package edu.itu.jetty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.itu.bean.MonitorData;
import edu.itu.bean.SmartMeter;
import edu.itu.bean.SmartMeterLightDataBean;
import edu.itu.bean.SmartMeterLoadConfig;
import edu.itu.bean.SmartMeterLoadData;
import edu.itu.html.LocalServerLightSearchOperation;
import edu.itu.html.LocalServerLightSearchOperationLightData;
import edu.itu.html.LocalServerLightSetOperation;
import edu.itu.util.HibernateUtil;
import edu.itu.util.Log4jUtil;

public class LightLogic {
	Logger logger = Log4jUtil.getLogger(LightLogic.class);
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();

	/*
	 * get the light status and set the max min value
	 * @param t
	 * @return
	 */
	public List<LocalServerLightSearchOperation> executeLightSearchAction() {
		
		//principle ---- every table search one time
	    
		logger.info("11111111111111111get all the smartmeters");
		s = factory.openSession();
		Map<Integer, SmartMeterLightDataBean> mInnerSmToLd = new HashMap<Integer, SmartMeterLightDataBean>();
		//get all the smartmeters
		String get_smartMeter="from SmartMeter";
		Query query_smartMeter = s.createQuery(get_smartMeter);
		List<SmartMeter> list_smartMeters = query_smartMeter.list();
		Map<Integer, SmartMeter> mSmToCoord = new HashMap<Integer, SmartMeter>();
		String string_smartMeters = "";
		
		for (SmartMeter smartMeter : list_smartMeters) {
			mSmToCoord.put(smartMeter.getSmId(), smartMeter);
			string_smartMeters = string_smartMeters.concat(smartMeter.getSmId()+",") ;
		}
		string_smartMeters = string_smartMeters.substring(0, string_smartMeters.length()-1);
		logger.debug("string_smartMeters"+string_smartMeters);
		logger.info("222222222222222222get all the light loads configuration");
		//get all the light loads configuration
		String get_smart_meter_load_config="from SmartMeterLoadConfig smLoadConfig where smLoadConfig.smId in("+string_smartMeters+") and smLoadConfig.smLoadName like '%light%'";
		Query query_smart_meter_load_config = s.createQuery(get_smart_meter_load_config);
		List<SmartMeterLoadConfig> list_smLoadConfigs =query_smart_meter_load_config.list();
		String string_SmartMeterLoadConfig = "";
		
		//setting the maps not finished setting value yet
		for (SmartMeterLoadConfig smartMeterLoadConfig : list_smLoadConfigs) {
			
			if (!mInnerSmToLd.containsKey(smartMeterLoadConfig.getSmLoadConfigId())) {
				SmartMeterLightDataBean smartMeterLightDataBean = new  SmartMeterLightDataBean();
				smartMeterLightDataBean.setLoad_config_id(smartMeterLoadConfig.getSmLoadConfigId());
				smartMeterLightDataBean.setSm_id(smartMeterLoadConfig.getSmId());
				smartMeterLightDataBean.setSm_load_id(smartMeterLoadConfig.getSmLoadId());
				smartMeterLightDataBean.setCoordinator_id(mSmToCoord.get(smartMeterLoadConfig.getSmId()).getCoordinatorId());
				smartMeterLightDataBean.setSm_name(mSmToCoord.get(smartMeterLoadConfig.getSmId()).getSmName());
				mInnerSmToLd.put(smartMeterLoadConfig.getSmLoadConfigId(), smartMeterLightDataBean);
			}
			string_SmartMeterLoadConfig=string_SmartMeterLoadConfig.concat(smartMeterLoadConfig.getSmLoadConfigId()+",");
		}
		string_SmartMeterLoadConfig = string_SmartMeterLoadConfig.substring(0, string_SmartMeterLoadConfig.length()-1);
		logger.debug("string_SmartMeterLoadConfig"+string_SmartMeterLoadConfig);
		
		
		//get the load data and the monitor data
		logger.info("3333333333333333get the load data ");
		String get_smart_meter_load_data = "select a.* from smart_meter_load_data a inner join ( select max(timestamp) "
				+ "as mtimestamp, sm_load_config_id as loconfigid from smart_meter_load_data where sm_load_config_id "
				+ "in ("+string_SmartMeterLoadConfig+") group by sm_load_config_id ) b on a.sm_load_config_id = b.loconfigid and a.timestamp = b.mtimestamp";
		Query query_smart_meter_load_data = s.createSQLQuery(get_smart_meter_load_data).addEntity(SmartMeterLoadData.class);
		
		List<SmartMeterLoadData> list_smartMeterLoadDatas = query_smart_meter_load_data.list();
	
		for (SmartMeterLoadData smartMeterLoadData : list_smartMeterLoadDatas) {
			int loadid=smartMeterLoadData.getSmLoadConfigId();
			if (mInnerSmToLd.containsKey(loadid)) {
				SmartMeterLightDataBean smartMeterLightDataBean = mInnerSmToLd.get(loadid);
				
				smartMeterLightDataBean.setTemp_data(smartMeterLoadData.getParamValue1());
				smartMeterLightDataBean.setLoad_status(smartMeterLoadData.getLoadStatus());
				
				mInnerSmToLd.replace(loadid, smartMeterLightDataBean);
			}
		}
		logger.info("444444444444444get and set monitor data");
		//get and set the monitor data
		
		//select * from stock a inner join (select max(stock_name) as mName, STOCK_CODE as mCode from stock where STOCK_CODE in (2,3) group by stock_code ) b on a.STOCK_NAME=b.mName
		String get_monito_data="select * from MonitorData monitorData inner join(select max(timestamp) as mtimestamp,smLoadConfigId as loadconfigid from MonitorData "
				+ "where smLoadConfigId in ("+string_SmartMeterLoadConfig+")) group by smLocadConfigId) innerload on monitorData.timestamp = innerload.mtimestamp and monitorData.smLocadConfigId = innerload.loadconfigid";
		get_monito_data = "select a.* from emsdb22_20150330.monitor_data a inner join ( select max(timestamp) "
		+ "as mtimestamp, sm_load_config_id as loconfigid from emsdb22_20150330.monitor_data where sm_load_config_id "
		+ "in ("+string_SmartMeterLoadConfig+") group by sm_load_config_id ) b on a.sm_load_config_id = b.loconfigid and a.timestamp = b.mtimestamp";
		
		Query query_monitor_data = s.createSQLQuery(get_monito_data).addEntity(MonitorData.class);

//		List list_monitor_data = query_monitor_data.list(); 
		List<MonitorData> list_monitor_data = query_monitor_data.list(); 
		//if the data is null
		for (MonitorData monitorData : list_monitor_data) {
			int loadid=monitorData.getSmLoadConfigId();
			if (mInnerSmToLd.containsKey(loadid)) {
				SmartMeterLightDataBean smartMeterLightDataBean = mInnerSmToLd.get(loadid);
				
				smartMeterLightDataBean.setMax_data(monitorData.getParamMax());
				smartMeterLightDataBean.setMin_data(monitorData.getParamMin());
				
				mInnerSmToLd.replace(loadid, smartMeterLightDataBean);
			}
		}
		
		logger.info("55555555555555555555555555555555555555555555555555last step");
		//at last transfer the map into json list
		Map<Integer, LocalServerLightSearchOperation> finalMap = new HashMap<Integer, LocalServerLightSearchOperation>();
//		List<LocalServerLightSearchOperation> finalLightSearchOperations = new ArrayList<LocalServerLightSearchOperation>();
		List<SmartMeterLightDataBean> list = new ArrayList<SmartMeterLightDataBean>(mInnerSmToLd.values());
		//override here
		for (SmartMeterLightDataBean smartMeterLightDataBean : list) {
			
			LocalServerLightSearchOperation oneLightSearchOperation = new LocalServerLightSearchOperation();
			List<LocalServerLightSearchOperationLightData> listLightDatas = new ArrayList<LocalServerLightSearchOperationLightData>();
			
			int coordinator_id = smartMeterLightDataBean.getCoordinator_id();
			int sm_id =          smartMeterLightDataBean.getSm_id();
			int sm_load_id = smartMeterLightDataBean.getSm_load_id();
			String sm_name =     smartMeterLightDataBean.getSm_name() ;
			int ld_config_id = smartMeterLightDataBean.getLoad_config_id();
			float temp_data = smartMeterLightDataBean.getTemp_data();
			float max_value = smartMeterLightDataBean.getMax_data();
			float min_value = smartMeterLightDataBean.getMin_data();
			int status      = smartMeterLightDataBean.getLoad_status();
			
			if (!finalMap.containsKey(sm_id)) {
				
				oneLightSearchOperation.setCoordinator_id(coordinator_id);
				oneLightSearchOperation.setSm_id(sm_id);
				oneLightSearchOperation.setSm_name(sm_name);
			}else {
				
				oneLightSearchOperation = finalMap.get(sm_id);
				listLightDatas = oneLightSearchOperation.getLightDatas();
			}
			
			LocalServerLightSearchOperationLightData oneData = new LocalServerLightSearchOperationLightData();
			oneData.setLoad_id(sm_load_id);
			oneData.setLoad_config_id(ld_config_id);
			oneData.setTemp_data(temp_data);
			oneData.setMax_value(max_value);
			oneData.setMin_value(min_value);
			oneData.setStatus(status);
			
			listLightDatas.add(oneData);
			oneLightSearchOperation.setLightDatas(listLightDatas);
			finalMap.put(sm_id, oneLightSearchOperation);
		}
		return new ArrayList<LocalServerLightSearchOperation>(finalMap.values());
		
		//get the monitor_config 
		/*Map<Integer, Integer> mLdToMonitoridMap = new HashMap<Integer, Integer>();
		String get_monitor_config="from MonitorConfig monitorConfig where monitorConfig.smLoadConfigId in("+string_SmartMeterLoadConfig+") "; //get all the monitor Config 
		Query query_monitor_config = s.createQuery(get_monitor_config);
		List<MonitorConfig> list_monitorconfigs = query_monitor_config.list();
		
		//setting the monitor id to be the key and the load_coinfig_id to be the value
		for (MonitorConfig monitorConfig : list_monitorconfigs) {
			int monitorid = monitorConfig.getMonitorConfigId();
			if (!mLdToMonitoridMap.containsKey(monitorid)) {
				
				mLdToMonitoridMap.put(monitorid, monitorConfig.getSmLoadConfigId());
			}
		}
		//get the monitorid responding max and min value newest.
		String get_monito_data="frmo MonitorData mdata where";
		Query query_monitor_data = s.createQuery(get_monito_data);
		List<MonitorData> list_monitor_data = query_monitor_data.list();
		
		// put the value to the mInnerSmToLd mapping
		for (MonitorData monitorData : list_monitor_data) {
			float max = monitorData.getParamMax();
			float min = monitorData.getParamMin();
			int key = mLdToMonitoridMap.get(monitorData.getMonitorConfigId());
			if(mInnerSmToLd.containsKey(key))
			{
				SmartMeterLightDataBean replaceBean = mInnerSmToLd.get(key);
				replaceBean.setMax_data(max);
				replaceBean.setMin_data(min);
				mInnerSmToLd.replace(key, replaceBean);
			}
		}*/
		
		
//		return mInnerSmToLd.values();
		/*
		String getloadconfigidsql="select new list(coordinator.coordinatorId,smartmeter.smId ,smartmeter.smName,smloadconfig.smLoadConfigId,smloadconfig.smLoadId,smloadconfig.smLoadName) "
				+ "from Coordinator coordinator,SmartMeter smartmeter,SmartMeterLoadConfig smloadconfig"
				+"where smloaddata.smLoadConfigId=smloadconfig.smLoadConfigId and smloadconfig.smLoadName like '%light%' and smloadconfig.smId = smartmeter.smId and smartmeter.coordinatorId =coordinator.coordinatorId";
	

		String getloaddatasql="select smloaddata.paramValue1,smloaddata.status"
				+ "from SmartMeterLoadData smloaddata"
				+ "where smloaddata.smLoadConfigId= :smLoadConfigId"
				+ "ORDER BY smloaddata.timestamp ASC";
	
		String getmaxminValueofload = "select monitiordata.paramValue1,monitiordata.paramValue2 "
				+ "from MonitorConfig monitorconfig MonitorData monitiordata"
				+ "where monitiordata.monitorConfigId=monitorconfig.monitorConfigId and monitorconfig.smLoadConfigId= :smLoadConfigId"
				+ "ORDER BY monitiordata.timestamp ASC" ;//shoudl add the timestamp???
		
		
		Iterator<?> listsIterator = s.createSQLQuery(
				getloadconfigidsql)
	            .list()
	            .iterator();
		
		/* get the data responding the load_config_id
		 
		while (listsIterator.hasNext()) {
			
		    Object[] smartmeterlist = (Object[]) listsIterator.next();
		    
		    LocalServerLightSearchOperation oneoperation =new LocalServerLightSearchOperation();
		    ArrayList<LocalServerLightSearchOperationLightData>  onelightsdatalist = new ArrayList<LocalServerLightSearchOperationLightData>();
		    
		    
		    Query onesmartmeteroperationquery = s.createSQLQuery(getloaddatasql) ;
		    onesmartmeteroperationquery.setParameter("smLoadConfigId",(int)smartmeterlist[3]);
		    List<?> onesmartmeteroperationlist = onesmartmeteroperationquery.list();
		    Object[] onesmartmeteroperationstruct = (Object[])onesmartmeteroperationlist.get(0);
		    
		    
		    oneoperation.setCoordinator_id((int)smartmeterlist[0]);
		    oneoperation.setSm_id((int)smartmeterlist[1]);
		    oneoperation.setSm_name((String)smartmeterlist[2]);
		    
		    Query onelightoperationquery = s.createSQLQuery(getmaxminValueofload) ;
		    onelightoperationquery.setParameter("smLoadConfigId",(int)smartmeterlist[3]);
		    List<?> onelightoperationlist = onelightoperationquery.list();
		    Object[] onelightoperationstruct = (Object[])onelightoperationlist.get(0);
		    
		    for (int i = 0; i < onesmartmeteroperationlist.size(); i++) {
		    	
			    LocalServerLightSearchOperationLightData element = new LocalServerLightSearchOperationLightData();
			    element.setLoad_config_id((int)smartmeterlist[3]);
			    element.setLoad_id((int)smartmeterlist[4]);
			    element.setLoad_name((String)smartmeterlist[5]);
			    element.setTemp_data((int)onesmartmeteroperationstruct[0]);
			    element.setMax_value((int)onelightoperationstruct[0]);
			    element.setMin_value((int)onelightoperationstruct[1]);
			    element.setStatus((int)onesmartmeteroperationstruct[1]);
			    
			    onelightsdatalist.set(i, element);
			    
			}
		    oneoperation.setLightDatas(onelightsdatalist);
		    localServerLightOperation.add(oneoperation);
		}
		
		return localServerLightOperation;*/
	}

	public boolean executeLightSetAction(LocalServerLightSetOperation mLocalServerLightSetOperation) {
		
		 
		 //manipulate monitor_data table
		 
		System.out.println("0.................  server .... \n");

		try {
		s = factory.openSession();
		//get the json values
		int coordinatorId = mLocalServerLightSetOperation.getCoordinator_id();
		int smId = mLocalServerLightSetOperation.getSm_id();
		int smLoadId = mLocalServerLightSetOperation.getLoad_id();
		int smLoadConfigId = mLocalServerLightSetOperation.getLoad_config_id();
		float maxValue = mLocalServerLightSetOperation.getMax_value();
		float minValue = mLocalServerLightSetOperation.getMin_value();
		System.out.println("someone set the maxandmin Value coordinatorId="+coordinatorId+" smId="+smId+" smLoadId="+smLoadId+" maxValue="+maxValue+" minValue="+minValue+"");

		
		
		
		
		//the sqlstring for get the monitorid
		/*String getmonitorconfigid = "select monitorconfig.monitorConfigId "
				+ "from Coordinator coordinator,SmartMeter smartmeter,SmartMeterLoadConfig smloadconfig,MonitorConfig monitorconfig"
				+ "where monitorconfig.smLoadConfigId=smloadconfig.smLoadConfigId and smloadconfig.smLoadId= :smLoadId and smloadconfig.smId = :smId and smartmeter.coordinatorId =:coordinatorId";
		
		Query oneoperationquery = s.createSQLQuery(getmonitorconfigid);
		oneoperationquery.setParameter("smLoadId", smLoadId);
		oneoperationquery.setParameter("smId", smId);
		oneoperationquery.setParameter("coordinatorId", coordinatorId);
		List<?> oneoperationlist = oneoperationquery.list();
		Object[] oneoperationstruct = (Object[]) oneoperationlist.get(0);
		int monitorConfigId = (int) oneoperationstruct[0];*/
		
		//save the data here
		MonitorData mdata = new MonitorData();
		mdata.setSmLoadConfigId(smLoadConfigId);
		/*byte[] bytesmaxValue = ByteBuffer.allocate(4).putInt(maxValue).array();
		byte[] bytesminValue = ByteBuffer.allocate(4).putInt(minValue).array();

		System.out.println("**************************************************************");
		for (byte b : bytesmaxValue) {
			   System.out.format("0x%x ", b);
			}
		
		for (byte b : bytesminValue) {
			   System.out.format("%x ", b);
//			System.out.print(b);
			}*/
		System.out.println("**************************************************************");
		mdata.setParamMax(maxValue);
		mdata.setParamMin(minValue);
		mdata.setProcessed(0);
		
		
		Transaction tran = s.beginTransaction();// 开始事物		
		s.save(mdata);
		
		tran.commit();// 提交
		logger.info("someone set the maxandmin Value coordinatorId="+coordinatorId+" smId="+smId+" smLoadId="+smLoadId+" maxValue="+maxValue+" minValue="+minValue+"");
		return true;
		}
		catch(Exception e)
		{
			logger.info("someone set the maxandmin Value Error!");
			e.printStackTrace();
			
		}
		

		return false;

	}
}
