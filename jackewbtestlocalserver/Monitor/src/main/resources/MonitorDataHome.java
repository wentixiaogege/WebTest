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
import bean.MonitorData;

/**
 * Home object for domain model class MonitorData.
 * @see .MonitorData
 * @author Hibernate Tools
 */
public class MonitorDataHome {

/*	private static final Log log = LogFactory.getLog(MonitorDataHome.class);

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

	public void persist(MonitorData transientInstance) {
		log.debug("persisting MonitorData instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(MonitorData instance) {
		log.debug("attaching dirty MonitorData instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MonitorData instance) {
		log.debug("attaching clean MonitorData instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(MonitorData persistentInstance) {
		log.debug("deleting MonitorData instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MonitorData merge(MonitorData detachedInstance) {
		log.debug("merging MonitorData instance");
		try {
			MonitorData result = (MonitorData) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public MonitorData findById(java.lang.Integer id) {
		log.debug("getting MonitorData instance with id: " + id);
		try {
			MonitorData instance = (MonitorData) sessionFactory
					.getCurrentSession().get("MonitorData", id);
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

	public List findByExample(MonitorData instance) {
		log.debug("finding MonitorData instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("MonitorData")
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
			logger.debug("MonitorData searchOperation failed", e);
	}
	}
//*******update operation*********
	public void static updateOperation(MonitorData update){
		
		try {
			update.setProcessed(1);
			DataAccess.updateOperation(update);
		}
		 catch (Exception e) {
			logger.debug("MonitorData updateOperation failed", e);
	}
}
