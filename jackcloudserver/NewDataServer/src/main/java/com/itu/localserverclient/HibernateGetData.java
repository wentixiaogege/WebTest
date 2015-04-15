package com.itu.localserverclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.itu.bean.SmartMeterLoadData;
import com.itu.util.HibernateUtil;

@SuppressWarnings("deprecation")
public class HibernateGetData {

	/**
	16      * @param args
	17      */
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();
	     /**
	     * @param args
	     */
	    @SuppressWarnings("null")
		public static void main(String[] args) {
	         // TODO Auto-generated method stub
	         Session session = factory.openSession();
	
	         
	         Transaction tran = session.beginTransaction();
	         Date endDate = new Date();

				//创建基于当前时间的日历对象

				Calendar cl = Calendar.getInstance();

				cl.setTime(endDate);
				int seconds = 360000;
				cl.add(Calendar.SECOND, -seconds);
				Date startDate = cl.getTime();
				
				SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				//格式化开始日期和结束日期

				String start = dd.format(startDate);
				String end = dd.format(endDate);
				
				System.out.println("00000"+start);
				System.out.println("11111"+end);
				
		         List<Integer> sm_ids = new ArrayList<Integer>();
		         String idsString = "";
		         sm_ids.add(0,0);
		         sm_ids.add(1,1);
		         sm_ids.add(2,2);
		         for(int i =1 ; i< sm_ids.size();i++)
		 			idsString += "or "+ sm_ids.get(i).toString();
		         System.out.println(sm_ids.get(0));
		 		
		 		 String hql = ("from SmartMeterLoadData smtable where smtable.timestamp > '"
		 				+ start
		 				+ "' and smtable.timestamp <= '"
		 				+ end + "' and  smtable.smIndex ='"+sm_ids.get(1)+"'");// need test
		 	    List<SmartMeterLoadData> result = session.createQuery(hql).list();

		 	   for(SmartMeterLoadData a : result){
	        	     System.out.print("------------->");
	                 System.out.println(a.getId());
	         }
				List<SmartMeterLoadData> newlist =new ArrayList<SmartMeterLoadData>();
				/**
				 * 
				 * 
				 * get the interval data from here
				*/
				//Calendar cl1 = Calendar.getInstance();			
				cl.setTime(result.get(0).getTimestamp());
				newlist.add(result.get(0));
				cl.add(Calendar.SECOND, 3600);
				System.out.println(cl.getTime());
				for(int i1 = 0 ;i1<result.size();i1++)
				{
					SmartMeterLoadData oneData  = result.get(i1);
					
					if(oneData.getTimestamp().after(cl.getTime()))
					{
						cl.add(Calendar.SECOND, 3600);
						newlist.add(oneData);
					}
						
					
					
					
				}
				
		         for(SmartMeterLoadData a : newlist){
		        	 System.out.print("++++++++++++++++++++++++++++++++++++> "); 
		                 System.out.println(a.getId());
		         }
		         System.out.println("++++++++++++++++++++++++++++++++++++> "+result.size()+"-----------"+newlist.size());
			/*	Date endDate = new Date();

				//创建基于当前时间的日历对象

				Calendar cl = Calendar.getInstance();

				cl.setTime(endDate);
				int seconds = 360000;
				cl.add(Calendar.SECOND, -seconds);
				Date startDate = cl.getTime();
				
				SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				//格式化开始日期和结束日期

				String start = dd.format(startDate);
				String end = dd.format(endDate);
				
				System.out.print("00000"+start);
				System.out.print("11111"+end);
				String hql = ("from SmartMeterLoadData smtable where smtable.timestamp > '"
						+ start
						+ "' and smtable.timestamp <= '"
						+ end + "'");
	         
	         
	         
	         
	         //   采用HQL的方式，
//	         Date begin = java.sql.Date.valueOf("2015-2-11");
//	         Date end = java.sql.Date.valueOf("2015-2-12");
	         //Date end = new Date();
//	         System.out.print("0000000"+end);
	         //for the sql start and end time
//	         String hqlString = "from SmartMeterLoadData a where a.timestamp > '"+begin+"' and a.timestamp <= '"+end+"'";
	         List<SmartMeterLoadData> result = session.createQuery(hql).list();

	         for(SmartMeterLoadData a : result){
	        	     System.out.print("------------>");
	                 System.out.println(a.getId());
	         }
	         
	         
//	         String hqlString = "FROM SmartMeterLoadData where TimeStamp >= DATE(DATE_SUB(NOW(), INTERVAL 20 DAY))";

//	         List<SmartMeterLoadData> result = session.createSQLQuery(hqlString).list();	       
//	         String hqlString = "SELECT * FROM smart_meter_data where timestamp >= DATE(DATE_SUB(NOW(), INTERVAL 20 DAY))";

//	         List<SmartMeterLoadData> result = session.createSQLQuery(hqlString).list();	         
	         //result.forEach(x->{System.out.println(x.toString());});
	         
//	         String sqlWhere = "{alias}.timestamp > DATE_SUB(curdate(), INTERVAL 20 DAY)";
//	         Criteria criteria = session.createCriteria(SmartMeterLoadData.class);
//	         criteria.add(Restrictions.sqlRestriction(sqlWhere));
//	         List<SmartMeterLoadData> result = criteria.list();
	         
	         @SuppressWarnings("unchecked")
			 List<SmartMeterLoadData> result = (List<SmartMeterLoadData>)session.createSQLQuery(hqlString).list();

	         result.forEach(x->{System.out.println(x.getId());});

	         for(SmartMeterLoadData a : result){
	        	     System.out.print("--------》");
	                 System.out.println(a.getId());
	         }
	         */
	         
	 //        采用QBC的方式。
	        /* Date begin = java.sql.Date.valueOf("2015-2-11");
	         Date end = java.sql.Date.valueOf("2015-2-12");
	         Criteria criteria = session.createCriteria(SmartMeterLoadData.class);
	         Criterion creterion = Expression.between("timestamp", begin, end);
	         @SuppressWarnings("unchecked")
			 List<SmartMeterLoadData> result = criteria.add(creterion).list();
	         for(SmartMeterLoadData a : result){
	             System.out.println(a.getId());
	            // System.out.println(a.getTitle());
	            // System.out.println(a.getDate());
	         }*/
	     }

}
