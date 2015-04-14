package dao;
// default package
// Generated Apr 7, 2015 4:46:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import util.DataAccess;
import util.Log4jUtil;
import bean.MonitorData;

/**
 * Home object for domain model class MonitorData.
 * @see .MonitorData
 * @author Hibernate Tools
 */
public class MonitorDataHome {

	static Logger logger = Log4jUtil.getLogger(SmartMeterLoadConfigHome.class);
//******search operation**********
	public static <T> List<T> searchOperation(Query query) {
		List<T> list = null;
		try {
	//		update.setProcessed(1);
			list = DataAccess.SearchOperation(query);
		}
		 catch (Exception e) {
			logger.debug("MonitorData searchOperation failed", e);
	}
		return list;
	}
//*******update operation*********
	public static void updateOperation(MonitorData update){
		
		try {
			update.setProcessed(1);
			DataAccess.updateOperation(update);
		}
		 catch (Exception e) {
			logger.debug("MonitorData updateOperation failed", e);
	}
}
}
