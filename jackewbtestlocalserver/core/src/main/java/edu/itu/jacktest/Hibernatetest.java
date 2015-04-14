package edu.itu.jacktest;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import edu.itu.bean.SmartMeterLoadData;
import edu.itu.jetty.LightLogic;
import edu.itu.util.HibernateUtil;
import edu.itu.util.Log4jUtil;

public class Hibernatetest {
	static Logger logger = Log4jUtil.getLogger(LightLogic.class);
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("11111111111111111get all the smartmeters");
		s = factory.openSession();
		/*
		 * Map<Integer, SmartMeterLightDataBean> mInnerSmToLd = new
		 * HashMap<Integer, SmartMeterLightDataBean>(); //get all the
		 * smartmeters String get_smartMeter="from SmartMeter where 1=1"; Query
		 * query_smartMeter = s.createQuery(get_smartMeter); List<SmartMeter>
		 * list_smartMeters = query_smartMeter.list(); Map<Integer, SmartMeter>
		 * mSmToCoord = new HashMap<Integer, SmartMeter>(); String
		 * string_smartMeters = "";
		 * 
		 * for (int i = 0; i < list_smartMeters.size(); i++) {
		 * string_smartMeters =
		 * string_smartMeters.concat(list_smartMeters.get(i).getSmId()+",") ; }
		 * logger
		 * .debug("list_smartMeters"+list_smartMeters.size()+"string_smartMeters"
		 * +string_smartMeters); for (SmartMeter smartMeter : list_smartMeters)
		 * { mSmToCoord.put(smartMeter.getSmId(), smartMeter);
		 * string_smartMeters =
		 * string_smartMeters.concat(smartMeter.getSmId()+",") ; }
		 * string_smartMeters = string_smartMeters.substring(0,
		 * string_smartMeters.length()-1);
		 * logger.debug("list_smartMeters"+list_smartMeters
		 * .size()+"string_smartMeters"+string_smartMeters);
		 * logger.info("222222222222222222get all the light loads configuration"
		 * );
		 */
		// .add(Restrictions.ge("someColumn", xxxxx))
		
		  List<Integer> iliIntegers = new ArrayList<Integer>();
		  iliIntegers.add(1); iliIntegers.add(2); iliIntegers.add(3);
		  iliIntegers.add(4);
		  
		  /*Criteria criteria = s.createCriteria(SmartMeterLoadData.class, "productLine");
		  criteria.add(Expression.eq("productLine.active", "Y"));

		  DetachedCriteria subCriteria = DetachedCriteria.forClass(Model.class, "model");
		  subCriteria.setProjection(Projections.rowCount());
		  subCriteria.createAlias("model.modelLanguages", "modelLang");
		  subCriteria.createAlias("modelLang.language", "lang");
		  criteria.add(Expression.eq("lang.langCode", "EN"));
		  subCriteria.add(Restrictions.eqProperty("model.productLine.productLineId","productLine.productLineId"));
		  */
		/* Criteria testCriteria = s.createCriteria(SmartMeterLoadData.class,"outer")
				 .createAlias("outer.SmartMeterLoadData", "inner")
				 .setProjection(Projections.projectionList()
						  .add(Projections.max("inner.timestamp"))
						  .add(Projections.groupProperty("inner.smLoadConfigId")))
				  .add(Restrictions.in("inner.smLoadConfigId", iliIntegers))
				  .add(Restrictions.and(Property.forName("outer.timestamp").eqProperty("inner.timestamp"),Property.forName("outer.smLoadConfigId").eqProperty("inner.smLoadConfigId")));
*/
//		 String hql3 = "from User user where (select count(*) from user.addresses)>1"; 
		 
		 /*String hqlString = "from SmartMeterLoadData smdataout  where (select max(smdatain.timestamp) "
				+ "as mtimestamp, smdatain.smLoadConfigId as loconfigid from SmartMeterLoadData smdatain where smdatain.smLoadConfigId "
				+ "in (1,2,3,4) group by smdatain.smLoadConfigId ) smdataconn on smdataout.timestamp=smdataconn.mtimestamp and smdataout.smLoadConfigId = smdataconn.loconfigid";
*/
//		 String hqlString = "from SmartMeterLoadData smdataout where smdataout.smLoadConfigId in (1,2,3,4)";
		 
		 
		 String hqlString = "from SmartMeterLoadData smdataout where smdataout.smLoadConfigId in (1,2,3,4) group by smdataout.smLoadConfigId";
		 
	
		 
		 Criteria criteria1 = s.createCriteria(SmartMeterLoadData.class)
				  .setProjection(
							   Projections.projectionList()
							  .add(Property.forName("smLoadDataId"))
							  .add(Projections.max("timestamp"))
							  .add(Projections.groupProperty("smLoadConfigId"))
							  .add(Property.forName("paramValue1"))
							  .add(Property.forName("paramValue2"))
							  .add(Property.forName("paramValue3"))
							  .add(Property.forName("processed"))
							  .add(Property.forName("loadStatus"))
						  )
				  .add(Restrictions.in("smLoadConfigId", iliIntegers));
		/* def colors = Color.where {
			    dateCreated == max(dateCreated)
			}.property("name").property("shade").property("dateCreated").list()*/
//				  .setResultTransformer(Transformers.aliasToBean(SmartMeterLoadData.class));
		 
//		 .setResultTransformer(Transformers.aliasToBean(User.class));
		  /*
		  DetachedCriteria criteria3 =DetachedCriteria.forClass(SmartMeterLoadData.class,"outerone")
				  .add(Restrictions.and(Property.forName("outerone.timestamp").eqProperty("innerone.timestamp"),Property.forName("outerone.smLoadConfigId").eqProperty("innerone.smLoadConfigId")));

		 criteria1.add(Subqueries.eqAll(criteria1, criteria3));*/
//		 Query query_smart_meter_load_data = s.createQuery(hqlString);
		
	    @SuppressWarnings("rawtypes")
		List<Object[]> objects = criteria1.list();
		 for (Object [] object : objects) {
//			 System.out.println(object[0]);
			 System.out.println(object[1]);
			 System.out.println(object[2]);
		}
	/*	
		  List results = criteria.list(); 
		  Map stateMap = new HashMap(); 
		  for(Object[] obj : results) { DownloadState downloadState =
		  (DownloadState) obj[0];
		  stateMap.put(downloadState.getDescription().toLowerCase() (Integer)
		  obj[1]); }*/
		 

		/*String getString = "select a.* from smart_meter_load_data a inner join ( select max(timestamp) "
				+ "as mtimestamp, sm_load_config_id as loconfigid from smart_meter_load_data where sm_load_config_id "
				+ "in (1,2,3,4) group by sm_load_config_id ) b on a.sm_load_config_id = b.loconfigid and a.timestamp = b.mtimestamp";
		Query query_smart_meter_load_data = s.createSQLQuery(getString).addEntity(SmartMeterLoadData.class);
		
		@SuppressWarnings("unchecked")
		List<SmartMeterLoadData> list_smartMeterLoadDatas = query_smart_meter_load_data.list();

		for (SmartMeterLoadData smartMeterLoadData : list_smartMeterLoadDatas) {
			int loadid = smartMeterLoadData.getSmLoadConfigId();
			
			System.out.println(loadid);
		}
		logger.info("444444444444444get and set monitor data");*/
	}
}
