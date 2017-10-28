package com.aspire.birp.modules.smartQuery.market.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.market.entity.SqFileShare;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqFileShare entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.market.entity.SqFileShare
 * @author MyEclipse Persistence Tools
 */

@Repository("sqFileShareDAO")
public class SqFileShareDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqFileShareDAO.class);

	// property constants

	public SqFileShare findById(com.aspire.birp.modules.smartQuery.market.entity.SqFileShareId id) {
		log.debug("getting SqFileShare instance with id: " + id);
		try {
			SqFileShare instance = (SqFileShare) getSession().get(
					SqFileShare.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqFileShare> findByExample(SqFileShare instance) {
		log.debug("finding SqFileShare instance by example");
		try {
			List<SqFileShare> results = (List<SqFileShare>) getSession()
					.createCriteria(SqFileShare.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqFileShare> findByProperty(String propertyName, Object value) {
		log.debug("finding SqFileShare instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SqFileShare as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqFileShare> findAll() {
		log.debug("finding all SqFileShare instances");
		try {
			String queryString = "from SqFileShare";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}