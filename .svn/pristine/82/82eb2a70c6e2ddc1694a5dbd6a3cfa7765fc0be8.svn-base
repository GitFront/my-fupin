package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfoTemp;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqSelectInfoTemp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfoTemp
 * @author MyEclipse Persistence Tools
 */

@Repository("sqSelectInfoTempDAO")
public class SqSelectInfoTempDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqSelectInfoTempDAO.class);
	// property constants
	public static final String MP_COLUMN_ID = "mpColumnId";
	public static final String SQ_COLUMN = "sqColumn";
	public static final String SQ_ID = "sqId";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public SqSelectInfoTemp findById(java.lang.String id) {
		log.debug("getting SqSelectInfoTemp instance with id: " + id);
		try {
			SqSelectInfoTemp instance = (SqSelectInfoTemp) getSession().get(
					SqSelectInfoTemp.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqSelectInfoTemp> findByExample(SqSelectInfoTemp instance) {
		log.debug("finding SqSelectInfoTemp instance by example");
		try {
			List<SqSelectInfoTemp> results = (List<SqSelectInfoTemp>) getSession()
					.createCriteria(
							SqSelectInfoTemp.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqSelectInfoTemp> findByProperty(String propertyName, Object value) {
		log.debug("finding SqSelectInfoTemp instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqSelectInfoTemp as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqSelectInfoTemp> findByMpColumnId(Object mpColumnId) {
		return findByProperty(MP_COLUMN_ID, mpColumnId);
	}

	public List<SqSelectInfoTemp> findBySqColumn(Object sqColumn) {
		return findByProperty(SQ_COLUMN, sqColumn);
	}

	public List<SqSelectInfoTemp> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqSelectInfoTemp> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqSelectInfoTemp> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqSelectInfoTemp> findAll() {
		log.debug("finding all SqSelectInfoTemp instances");
		try {
			String queryString = "from SqSelectInfoTemp";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}