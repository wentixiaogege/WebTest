package com.itu.DAO;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.itu.util.HibernateUtil;

import edu.itu.util.Log4jUtil;

public class DataAccess {
	static Logger logger = Log4jUtil.getLogger(DataAccess.class);
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();

	public  static <T> boolean addOperation(List<T> add) {
		logger.debug("add  begin..");
		logger.debug("size"+add.size());
//		add.forEach(x->x);
		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
//			s.save(add);
//			for (T t : add) {
//				s.save(t);
//			}
			
			add.forEach(t->s.save(t));
			/*for(T onerecord : add)
			{
				s.save(onerecord);// 执行
			}*/
			tran.commit();// 提交
			
		} catch (Exception e) {
			logger.debug("add failed", e);
			return false;
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("add  end..");
		return true;

	}
	public  static <T> boolean modifyOperation(T update) {
		logger.debug("addNewCommand begin..");

		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(update);// 执行
			tran.commit();// 提交
		} catch (Exception e) {
			logger.debug("failed", e);
			return false;
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("addNewCommand end..");
		return true;

	}
	public  static <T> boolean deleteOperation(T delete) {
		logger.debug("addNewCommand begin..");

		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(delete);// 执行
			tran.commit();// 提交
		} catch (Exception e) {
			logger.debug("failed", e);
			return false;
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("addNewCommand end..");
		return true;

	}
	public  static <T> List<T> HibernateSearchOperation(String get) {
//		logger.debug("get data begin.."+System.nanoTime());
		logger.info("get data begin.."+System.nanoTime());
		List<T> list = null;
		try {
			s = factory.openSession();
			Query query = s.createQuery(get);//.setTimestamp("beginTime", begin).setTimestamp("endTime", end)
			//query.setMaxResults(100);
			list = query.list();
		} catch (Exception e) {
			logger.debug("failed", e);
			
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.info("get data end.."+System.nanoTime());
		return list;

	}
	public  static <T> List<T> MysqlSearchOperation(String get) {
		logger.debug("get data begin..");
		List<T> list = null;
		try {
			s = factory.openSession();
			
			Query query = s.createSQLQuery(get);//.setTimestamp("beginTime", begin).setTimestamp("endTime", end)
			list = query.list();
			
		} catch (Exception e) {
			logger.debug("failed", e);
			
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("get data end..");
		return list;

	}
}
