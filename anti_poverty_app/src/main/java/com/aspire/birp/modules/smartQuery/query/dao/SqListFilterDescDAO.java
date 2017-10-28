package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqListFilterDesc;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqListFilterDesc entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqListFilterDesc
 * @author MyEclipse Persistence Tools
 */
@Repository("sqListFilterDescDAO")
public class SqListFilterDescDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqListFilterDescDAO.class);
	// property constants
	public static final String INFO = "info";
	public static final String LIST_ID = "listId";

	public SqListFilterDesc findById(java.lang.String id) {
		log.debug("getting SqListFilterDesc instance with id: " + id);
		try {
			SqListFilterDesc instance = (SqListFilterDesc) getSession().get(
					SqListFilterDesc.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqListFilterDesc> findByExample(SqListFilterDesc instance) {
		log.debug("finding SqListFilterDesc instance by example");
		try {
			List<SqListFilterDesc> results = (List<SqListFilterDesc>) getSession()
					.createCriteria(
							SqListFilterDesc.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqListFilterDesc> findByProperty(String propertyName, Object value) {
		log.debug("finding SqListFilterDesc instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqListFilterDesc as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqListFilterDesc> findByInfo(Object info) {
		return findByProperty(INFO, info);
	}

	public List<SqListFilterDesc> findByListId(Object listId) {
		return findByProperty(LIST_ID, listId);
	}

	public List<SqListFilterDesc> findAll() {
		log.debug("finding all SqListFilterDesc instances");
		try {
			String queryString = "from SqListFilterDesc";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}