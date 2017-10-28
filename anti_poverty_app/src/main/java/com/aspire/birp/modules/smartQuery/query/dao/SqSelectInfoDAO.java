package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqSelectInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo
 * @author MyEclipse Persistence Tools
 */
@Repository("sqSelectInfoDAO")
public class SqSelectInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqSelectInfoDAO.class);
	// property constants
	public static final String MP_COLUMN_ID = "mpColumnId";
	public static final String SQ_COLUMN = "sqColumn";
	public static final String SQ_ID = "sqId";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public SqSelectInfo findById(java.lang.String id) {
		log.debug("getting SqSelectInfo instance with id: " + id);
		try {
			SqSelectInfo instance = (SqSelectInfo) getSession().get(
					SqSelectInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqSelectInfo> findByExample(SqSelectInfo instance) {
		log.debug("finding SqSelectInfo instance by example");
		try {
			List<SqSelectInfo> results = (List<SqSelectInfo>) getSession()
					.createCriteria(SqSelectInfo.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqSelectInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding SqSelectInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqSelectInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqSelectInfo> findByMpColumnId(Object mpColumnId) {
		return findByProperty(MP_COLUMN_ID, mpColumnId);
	}

	public List<SqSelectInfo> findBySqColumn(Object sqColumn) {
		return findByProperty(SQ_COLUMN, sqColumn);
	}

	public List<SqSelectInfo> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqSelectInfo> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqSelectInfo> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqSelectInfo> findAll() {
		log.debug("finding all SqSelectInfo instances");
		try {
			String queryString = "from SqSelectInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}