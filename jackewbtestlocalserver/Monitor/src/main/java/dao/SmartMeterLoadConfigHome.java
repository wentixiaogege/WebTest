package dao;
// Generated Apr 7, 2015 4:46:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import bean.SmartMeterLoadConfig;
import source.Decode;
import util.DataAccess;
import util.Log4jUtil;

/**
 * Home object for domain model class SmartMeterLoadConfig.
 * @see .SmartMeterLoadConfig
 * @author Hibernate Tools
 */
public class SmartMeterLoadConfigHome {
	static Logger logger = Log4jUtil.getLogger(SmartMeterLoadConfigHome.class);
	//******search operation**********
		public static <T> List<T> searchOperation(Query query) {
			List<T> smLoadConfig = null;
			try {
		//		update.setProcessed(1);
				smLoadConfig = DataAccess.SearchOperation(query);
			}
			 catch (Exception e) {
				logger.debug("SmartMeterLoadConfig searchOperation failed", e);
		}
			return smLoadConfig;
		}
}
