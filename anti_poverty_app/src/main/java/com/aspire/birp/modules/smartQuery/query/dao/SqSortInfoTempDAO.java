package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqSortInfoTemp;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqSortInfoTemp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqSortInfoTemp
 * @author MyEclipse Persistence Tools
 */

@Repository("sqSortInfoTempDAO")
public class SqSortInfoTempDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqSortInfoTempDAO.class);
	// property constants
	public static final String MP_COLUMN_ID = "mpColumnId";
	public static final String SQ_COLUMN = "sqColumn";
	public static final String SORT_TYPE_ID = "sortTypeId";
	public static final String SORT_TYPE = "sortType";
	public static final String SQ_ID = "sqId";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public SqSortInfoTemp findById(java.lang.String id) {
		log.debug("getting SqSortInfoTemp instance with id: " + id);
		try {
			SqSortInfoTemp instance = (SqSortInfoTemp) getSession().get(
					SqSortInfoTemp.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqSortInfoTemp> findByExample(SqSortInfoTemp instance) {
		log.debug("finding SqSortInfoTemp instance by example");
		try {
			List<SqSortInfoTemp> results = (List<SqSortInfoTemp>) getSession()
					.createCriteria(
							SqSortInfoTemp.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqSortInfoTemp> findByProperty(String propertyName, Object value) {
		log.debug("finding SqSortInfoTemp instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqSortInfoTemp as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqSortInfoTemp> findByMpColumnId(Object mpColumnId) {
		return findByProperty(MP_COLUMN_ID, mpColumnId);
	}

	public List<SqSortInfoTemp> findBySqColumn(Object sqColumn) {
		return findByProperty(SQ_COLUMN, sqColumn);
	}

	public List<SqSortInfoTemp> findBySortTypeId(Object sortTypeId) {
		return findByProperty(SORT_TYPE_ID, sortTypeId);
	}

	public List<SqSortInfoTemp> findBySortType(Object sortType) {
		return findByProperty(SORT_TYPE, sortType);
	}

	public List<SqSortInfoTemp> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqSortInfoTemp> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqSortInfoTemp> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqSortInfoTemp> findAll() {
		log.debug("finding all SqSortInfoTemp instances");
		try {
			String queryString = "from SqSortInfoTemp";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}