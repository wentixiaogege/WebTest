// default package
// Generated Apr 7, 2015 4:46:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import util.DataAccess;

/**
 * Home object for domain model class SmartMeterLoadConfig.
 * @see .SmartMeterLoadConfig
 * @author Hibernate Tools
 */
public class SmartMeterLoadConfigHome {

/*	private static final Log log = LogFactory
			.getLog(SmartMeterLoadConfigHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(SmartMeterLoadConfig transientInstance) {
		log.debug("persisting SmartMeterLoadConfig instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SmartMeterLoadConfig instance) {
		log.debug("attaching dirty SmartMeterLoadConfig instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SmartMeterLoadConfig instance) {
		log.debug("attaching clean SmartMeterLoadConfig instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SmartMeterLoadConfig persistentInstance) {
		log.debug("deleting SmartMeterLoadConfig instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SmartMeterLoadConfig merge(SmartMeterLoadConfig detachedInstance) {
		log.debug("merging SmartMeterLoadConfig instance");
		try {
			SmartMeterLoadConfig result = (SmartMeterLoadConfig) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SmartMeterLoadConfig findById(java.lang.Integer id) {
		log.debug("getting SmartMeterLoadConfig instance with id: " + id);
		try {
			SmartMeterLoadConfig instance = (SmartMeterLoadConfig) sessionFactory
					.getCurrentSession().get("SmartMeterLoadConfig", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SmartMeterLoadConfig instance) {
		log.debug("finding SmartMeterLoadConfig instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("SmartMeterLoadConfig")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}*/
	//******search operation**********
		public static <T> List<T> searchOperation(Query query) {
			try {
		//		update.setProcessed(1);
				DataAccess.SearchOperation(query)
			}
			 catch (Exception e) {
				logger.debug("SmartMeterLoadConfig searchOperation failed", e);
		}
		}
}
