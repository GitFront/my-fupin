package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqTableInfoTemp;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqTableInfoTemp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.david.hibernate.pojo.d7.SqTableInfoTemp
 * @author MyEclipse Persistence Tools
 */
@Repository("sqTableInfoTempDAO")
public class SqTableInfoTempDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqTableInfoTempDAO.class);
	// property constants
	public static final String MP_TABLE_ID = "mpTableId";
	public static final String SQ_TABLE = "sqTable";
	public static final String SQ_ID = "sqId";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public SqTableInfoTemp findById(java.lang.String id) {
		log.debug("getting SqTableInfoTemp instance with id: " + id);
		try {
			SqTableInfoTemp instance = (SqTableInfoTemp) getSession().get(
					"com.david.hibernate.pojo.d7.SqTableInfoTemp", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqTableInfoTemp> findByExample(SqTableInfoTemp instance) {
		log.debug("finding SqTableInfoTemp instance by example");
		try {
			List<SqTableInfoTemp> results = (List<SqTableInfoTemp>) getSession()
					.createCriteria(
							"com.david.hibernate.pojo.d7.SqTableInfoTemp")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqTableInfoTemp> findByProperty(String propertyName, Object value) {
		log.debug("finding SqTableInfoTemp instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqTableInfoTemp as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqTableInfoTemp> findByMpTableId(Object mpTableId) {
		return findByProperty(MP_TABLE_ID, mpTableId);
	}

	public List<SqTableInfoTemp> findBySqTable(Object sqTable) {
		return findByProperty(SQ_TABLE, sqTable);
	}

	public List<SqTableInfoTemp> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqTableInfoTemp> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqTableInfoTemp> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqTableInfoTemp> findAll() {
		log.debug("finding all SqTableInfoTemp instances");
		try {
			String queryString = "from SqTableInfoTemp";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}