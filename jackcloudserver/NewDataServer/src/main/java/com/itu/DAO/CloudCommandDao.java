package com.itu.DAO;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.itu.bean.Command;
import com.itu.util.HibernateUtil;

import edu.itu.util.Log4jUtil;


public class CloudCommandDao {
	static Logger logger = Log4jUtil.getLogger(CloudCommandDao.class);
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();

	public  static <T> boolean addNewCommand(T cmd) {
		logger.debug("addNewCommand begin..");
		// using enum or using data access
		Command tmpcmd = null;
//		cmd.setId(0);
		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(cmd);// 执行
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

	
}
