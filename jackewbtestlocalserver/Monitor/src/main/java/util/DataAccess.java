package util;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import bean.DataRegisterData;
import bean.MonitorData;

public class DataAccess {
	static Logger logger = Log4jUtil.getLogger(DataAccess.class);
	public static Session session = null;
	public static SessionFactory factory = HibernateUtil.getSessionFactory();

	public  static <T> boolean addOperation(List<T> add) {
		logger.debug("add  begin..");
		logger.debug("size"+add.size());
		
//		add.forEach(x->x);
		try {
			session = factory.openSession();
			Transaction tran = session.beginTransaction();// 开始事物
//			s.save(add);
//			for (T t : add) {
//				s.save(t);
//			}
			
			add.forEach(t->session.save(t));
//			for(T onerecord : add)
//			{
//				s.save(onerecord);// 执行
//			}
			tran.commit();// 提交
			
		} catch (Exception e) {
			logger.debug("add failed", e);
			return false;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		logger.debug("add  end..");
		return true;

	}

	public static <T> List<T> SearchOperation(Query query) {
		// logger.debug("get data begin.."+System.nanoTime());
	//	logger.info("get data begin.." + System.nanoTime());
		List<T> list = null;
		try {
//			session = factory.openSession();
/*			Query query = session.createQuery("from SmartMeterLoadConfig data where data.paramName1=:paramName1 and data.paramName2 =:paramName2 and data.smId =:smId");// .setTimestamp("beginTime",
												// begin).setTimestamp("endTime",
			query.setString("paramName1", paramName1);
			query.setString("paramName2", paramName2);
		    query.setInteger("smId", smId);*/
			// query.setMaxResults(100);
		//	s.setString
			list = query.list();
			logger.info("search successully");
		} catch (Exception e) {
			logger.debug("failed", e);

		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
	//	logger.info("get data end.." + System.nanoTime());
		return list;

	}
	

	
	public  static <T> boolean updateOperation(T update) {
		logger.debug("modifyNewCommand begin..");

		try {
			session = factory.openSession();
			Transaction tran = session.beginTransaction();// 开始事物	
			session.update(update);// 执行
			tran.commit();// 提交
		} catch (Exception e) {
			logger.debug("failed", e);
			return false;
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		logger.debug("modifyNewCommand end..");
		return true;
	}
}
