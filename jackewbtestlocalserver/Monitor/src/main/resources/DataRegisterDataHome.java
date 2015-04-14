// default package
// Generated Apr 7, 2015 4:46:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import bean.DataRegisterData;
import bean.MonitorData;
import util.DataAccess;
import util.HibernateUtil;

/**
 * Home object for domain model class DataRegisterData.
 * @see .DataRegisterData
 * @author Hibernate Tools
 */
public class DataRegisterDataHome {

//	private static final Log log = LogFactory
//			.getLog(DataRegisterDataHome.class);

//	private static SessionFactory factory = HibernateUtil.getSessionFactory();
//	private final SessionFactory sessionFactory = getSessionFactory();

//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext()
//					.lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException(
//					"Could not locate SessionFactory in JNDI");
//		}
//	}
//****add operation***********
	public void  static  addOperation(List<T> add) {
		
		try {
			DataAccess.addOperation(add);
		} catch (Exception e) {
			logger.debug("DataRegisterData addOperation failed", e);
		}
	}
//******search operation**********
	public static <T> List<T> searchOperation(Query query) {
			try {
		//		update.setProcessed(1);
				DataAccess.SearchOperation(query)
			}
			 catch (Exception e) {
				logger.debug("DataRegisterData searchOperation failed", e);
		}
		}
//****update operation********8
	public  static <T> boolean updateOperation(DataRegisterData update) {	
			
		    try {
		    	update.setProcessed("1");
			    DataAccess.updateOperation(update);
			} catch (Exception e) {
				logger.debug("DataRegisterData updateOperation failed", e);
			}
	}
/*	public void persist(DataRegisterData transientInstance) {
		log.debug("persisting DataRegisterData instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DataRegisterData instance) {
		log.debug("attaching dirty DataRegisterData instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DataRegisterData instance) {
		log.debug("attaching clean DataRegisterData instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DataRegisterData persistentInstance) {
		log.debug("deleting DataRegisterData instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DataRegisterData merge(DataRegisterData detachedInstance) {
		log.debug("merging DataRegisterData instance");
		try {
			DataRegisterData result = (DataRegisterData) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DataRegisterData findById(java.lang.Integer id) {
		log.debug("getting DataRegisterData instance with id: " + id);
		try {
			DataRegisterData instance = (DataRegisterData) sessionFactory
					.getCurrentSession().get("DataRegisterData", id);
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

	public List findByExample(DataRegisterData instance) {
		log.debug("finding DataRegisterData instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("DataRegisterData")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}*/
}
