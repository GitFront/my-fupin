package com.aspire.birp.modules.smartQuery.exception.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.exception.entity.SqExceptionInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqExceptionInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.exception.entity.SqExceptionInfo
 * @author MyEclipse Persistence Tools
 */

@Repository("sqExceptionInfoDAO")
public class SqExceptionInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqExceptionInfoDAO.class);
	// property constants
	public static final String LOG = "log";
	public static final String TYPE = "type";
	public static final String DESCRIPTION = "description";
	public static final String MODULE_ID = "moduleId";
	public static final String EXTRA_PROP_ONE = "extraPropOne";
	public static final String EXTRA_PROP_TWO = "extraPropTwo";

	public SqExceptionInfo findById(java.lang.String id) {
		log.debug("getting SqExceptionInfo instance with id: " + id);
		try {
			SqExceptionInfo instance = (SqExceptionInfo) getSession().get(
					SqExceptionInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqExceptionInfo> findByExample(SqExceptionInfo instance) {
		log.debug("finding SqExceptionInfo instance by example");
		try {
			List<SqExceptionInfo> results = (List<SqExceptionInfo>) getSession()
					.createCriteria(
							SqExceptionInfo.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqExceptionInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding SqExceptionInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqExceptionInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqExceptionInfo> findByLog(Object log) {
		return findByProperty(LOG, log);
	}

	public List<SqExceptionInfo> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<SqExceptionInfo> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List<SqExceptionInfo> findByModuleId(Object moduleId) {
		return findByProperty(MODULE_ID, moduleId);
	}

	public List<SqExceptionInfo> findByExtraPropOne(Object extraPropOne) {
		return findByProperty(EXTRA_PROP_ONE, extraPropOne);
	}

	public List<SqExceptionInfo> findByExtraPropTwo(Object extraPropTwo) {
		return findByProperty(EXTRA_PROP_TWO, extraPropTwo);
	}

	public List<SqExceptionInfo> findAll() {
		log.debug("finding all SqExceptionInfo instances");
		try {
			String queryString = "from SqExceptionInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}