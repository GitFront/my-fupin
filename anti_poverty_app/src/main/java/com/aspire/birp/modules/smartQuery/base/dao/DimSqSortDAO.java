package com.aspire.birp.modules.smartQuery.base.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.base.entity.DimSqSort;

/**
 * A data access object (DAO) providing persistence and search support for
 * DimSqSort entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @author MyEclipse Persistence Tools
 */
@Repository("dimSqSortDAO")
public class DimSqSortDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DimSqSortDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String SORT = "sort";

	public void save(DimSqSort transientInstance) {
		log.debug("saving DimSqSort instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DimSqSort persistentInstance) {
		log.debug("deleting DimSqSort instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DimSqSort findById(java.lang.String id) {
		log.debug("getting DimSqSort instance with id: " + id);
		try {
			DimSqSort instance = (DimSqSort) getSession().get(
					DimSqSort.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DimSqSort> findByExample(DimSqSort instance) {
		log.debug("finding DimSqSort instance by example");
		try {
			List<DimSqSort> results = (List<DimSqSort>) getSession()
					.createCriteria(DimSqSort.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<DimSqSort> findByProperty(String propertyName, Object value) {
		log.debug("finding DimSqSort instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from DimSqSort as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DimSqSort> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<DimSqSort> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<DimSqSort> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<DimSqSort> findAll() {
		log.debug("finding all DimSqSort instances");
		try {
			String queryString = "from DimSqSort";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DimSqSort merge(DimSqSort detachedInstance) {
		log.debug("merging DimSqSort instance");
		try {
			DimSqSort result = (DimSqSort) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DimSqSort instance) {
		log.debug("attaching dirty DimSqSort instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DimSqSort instance) {
		log.debug("attaching clean DimSqSort instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}