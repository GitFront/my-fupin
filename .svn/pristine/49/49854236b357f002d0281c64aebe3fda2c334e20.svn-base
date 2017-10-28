package com.aspire.birp.modules.smartQuery.task.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskStatusInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqTaskStatusInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.task.entity.SqTaskStatusInfo
 * @author MyEclipse Persistence Tools
 */

@Repository("sqTaskStatusInfoDAO")
public class SqTaskStatusInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqTaskStatusInfoDAO.class);
	// property constants
	public static final String TASK_ID = "taskId";
	public static final String TASK_STATUS = "taskStatus";

	public SqTaskStatusInfo findById(java.lang.String id) {
		log.debug("getting SqTaskStatusInfo instance with id: " + id);
		try {
			SqTaskStatusInfo instance = (SqTaskStatusInfo) getSession().get(
					SqTaskStatusInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqTaskStatusInfo> findByExample(SqTaskStatusInfo instance) {
		log.debug("finding SqTaskStatusInfo instance by example");
		try {
			List<SqTaskStatusInfo> results = (List<SqTaskStatusInfo>) getSession()
					.createCriteria(
							SqTaskStatusInfo.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqTaskStatusInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding SqTaskStatusInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqTaskStatusInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqTaskStatusInfo> findByTaskId(Object taskId) {
		return findByProperty(TASK_ID, taskId);
	}

	public List<SqTaskStatusInfo> findByTaskStatus(Object taskStatus) {
		return findByProperty(TASK_STATUS, taskStatus);
	}

	public List<SqTaskStatusInfo> findAll() {
		log.debug("finding all SqTaskStatusInfo instances");
		try {
			String queryString = "from SqTaskStatusInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}