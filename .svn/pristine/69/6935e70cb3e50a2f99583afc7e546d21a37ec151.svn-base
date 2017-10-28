package com.aspire.birp.modules.smartQuery.market.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalogShare;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqPersonalCatalogShare entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalogShare
 * @author MyEclipse Persistence Tools
 */
@Repository("sqPersonalCatalogShareDAO")
public class SqPersonalCatalogShareDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqPersonalCatalogShareDAO.class);

	// property constants

	public SqPersonalCatalogShare findById(
			com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalogShareId id) {
		log.debug("getting SqPersonalCatalogShare instance with id: " + id);
		try {
			SqPersonalCatalogShare instance = (SqPersonalCatalogShare) getSession()
					.get(SqPersonalCatalogShare.class,
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqPersonalCatalogShare> findByExample(
			SqPersonalCatalogShare instance) {
		log.debug("finding SqPersonalCatalogShare instance by example");
		try {
			List<SqPersonalCatalogShare> results = (List<SqPersonalCatalogShare>) getSession()
					.createCriteria(
							SqPersonalCatalogShare.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqPersonalCatalogShare> findByProperty(String propertyName, Object value) {
		log.debug("finding SqPersonalCatalogShare instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqPersonalCatalogShare as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqPersonalCatalogShare> findAll() {
		log.debug("finding all SqPersonalCatalogShare instances");
		try {
			String queryString = "from SqPersonalCatalogShare";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}