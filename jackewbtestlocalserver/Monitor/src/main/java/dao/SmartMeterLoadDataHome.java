package dao;
// Generated Apr 7, 2015 4:46:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import util.DataAccess;
import util.Log4jUtil;

/**
 * Home object for domain model class SmartMeterLoadData.
 * @see .SmartMeterLoadData
 * @author Hibernate Tools
 */
public class SmartMeterLoadDataHome {
	static Logger logger = Log4jUtil.getLogger(SmartMeterLoadConfigHome.class);
	//****add operation***********
		public  static <T> void addOperation(List<T> add) {
			
			try {
				DataAccess.addOperation(add);
			} catch (Exception e) {
				logger.debug("SmartMeterLoadData addOperation failed", e);
			}
		}
}
