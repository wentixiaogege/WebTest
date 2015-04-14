package com.itu.DAO;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

//import com.itu.util.DateUtils;
import com.itu.util.HibernateUtil;

import edu.itu.util.Log4jUtil;
//import com.itu.bean.CloudCommand;
//import com.itu.bean.Command;

public class DataAccess {
	static Logger logger = Log4jUtil.getLogger(DataAccess.class);
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();

	public  static <T> boolean addOperation(T[] add) {
		logger.debug("add  begin..");

		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(add);// 执行
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
	public  static boolean modifyOperation(String update) {
		logger.debug("Update operation begins .. ");
		
		s = factory.openSession();
		Transaction tx = null;

		try {
			
			tx = s.beginTransaction();// 开始事物
			Query query = s.createQuery(update);
			int result = query.executeUpdate();
			System.out
			.println("In Data Access, update return result:" + result);
			
			logger.debug("Update operation result .. " + result);
			
			//s.save(update);// 执行
			tx.commit();// 提交
		} catch (Exception e) {
			logger.debug("failed", e);
			if (tx!=null)
			{
				tx.rollback();
			}
			return false;
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("Update operation ends ...");
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
	public  static <T> List<T> searchOperation(String get) {
		logger.debug("get data begin..");
		List<T> list = null;
		s = factory.openSession();
		Transaction tx = null;
		try {
			
			
			tx = s.beginTransaction();
			
			Query query = s.createQuery(get);
			list = query.list();
			
			tx.commit();
			
		} catch (Exception e) {
			if (tx !=null) 
			{
				tx.rollback();
			}
			logger.debug("failed", e);
			
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("get data end..");
		return list;

	}
}