// default package
// Generated Apr 7, 2015 4:46:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class SmartMeterLoadData.
 * @see .SmartMeterLoadData
 * @author Hibernate Tools
 */
public class SmartMeterLoadDataHome {

	private static final Log log = LogFactory
			.getLog(SmartMeterLoadDataHome.class);

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

	public void persist(SmartMeterLoadData transientInstance) {
		log.debug("persisting SmartMeterLoadData instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(SmartMeterLoadData instance) {
		log.debug("attaching dirty SmartMeterLoadData instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SmartMeterLoadData instance) {
		log.debug("attaching clean SmartMeterLoadData instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(SmartMeterLoadData persistentInstance) {
		log.debug("deleting SmartMeterLoadData instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SmartMeterLoadData merge(SmartMeterLoadData detachedInstance) {
		log.debug("merging SmartMeterLoadData instance");
		try {
			SmartMeterLoadData result = (SmartMeterLoadData) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SmartMeterLoadData findById(java.lang.Integer id) {
		log.debug("getting SmartMeterLoadData instance with id: " + id);
		try {
			SmartMeterLoadData instance = (SmartMeterLoadData) sessionFactory
					.getCurrentSession().get("SmartMeterLoadData", id);
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

	public List findByExample(SmartMeterLoadData instance) {
		log.debug("finding SmartMeterLoadData instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("SmartMeterLoadData")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
