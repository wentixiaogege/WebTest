package dao;
// Generated Apr 7, 2015 4:46:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import bean.DataRegisterData;
import bean.MonitorData;
import util.DataAccess;
import util.HibernateUtil;
import util.Log4jUtil;

/**
 * Home object for domain model class DataRegisterData.
 * @see .DataRegisterData
 * @author Hibernate Tools
 */
public class DataRegisterDataHome {
	static Logger logger = Log4jUtil.getLogger(SmartMeterLoadConfigHome.class);
//****add operation***********
	public  static <T> void addOperation(List<T> add) {
		
		try {
			DataAccess.addOperation(add);
		} catch (Exception e) {
			logger.debug("DataRegisterData addOperation failed", e);
		}
	}
//******search operation**********
	public static <T> List<T> searchOperation(Query query) {
		List<T> list = null;
		try {
	//		update.setProcessed(1);
			list = DataAccess.SearchOperation(query);
		}
		 catch (Exception e) {
			logger.debug("DataRegisterData searchOperation failed", e);
	}
		return list;
	}
//****update operation********8
	public  static void updateOperation(DataRegisterData update) {	
			
		    try {
		    	update.setProcessed("1");
			    DataAccess.updateOperation(update);
			} catch (Exception e) {
				logger.debug("DataRegisterData updateOperation failed", e);
			}
	}
}
