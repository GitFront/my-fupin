package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqTableInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqTableInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqTableInfo
 * @author MyEclipse Persistence Tools
 */
@Repository("sqTableInfoDAO")
public class SqTableInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqTableInfoDAO.class);
	// property constants
	public static final String MP_TABLE_ID = "mpTableId";
	public static final String SQ_TABLE = "sqTable";
	public static final String SQ_ID = "sqId";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public SqTableInfo findById(java.lang.String id) {
		log.debug("getting SqTableInfo instance with id: " + id);
		try {
			SqTableInfo instance = (SqTableInfo) getSession().get(
					SqTableInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqTableInfo> findByExample(SqTableInfo instance) {
		log.debug("finding SqTableInfo instance by example");
		try {
			List<SqTableInfo> results = (List<SqTableInfo>) getSession()
					.createCriteria(SqTableInfo.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqTableInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding SqTableInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SqTableInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqTableInfo> findByMpTableId(Object mpTableId) {
		return findByProperty(MP_TABLE_ID, mpTableId);
	}

	public List<SqTableInfo> findBySqTable(Object sqTable) {
		return findByProperty(SQ_TABLE, sqTable);
	}

	public List<SqTableInfo> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqTableInfo> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqTableInfo> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqTableInfo> findAll() {
		log.debug("finding all SqTableInfo instances");
		try {
			String queryString = "from SqTableInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}