package com.aspire.birp.modules.smartQuery.base.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.base.entity.DimMColumnType;

/**
 * A data access object (DAO) providing persistence and search support for
 * DimMColumnType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @author MyEclipse Persistence Tools
 */
@Repository("dimMColumnTypeDAO")
public class DimMColumnTypeDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DimMColumnTypeDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String SORT = "sort";

	public void save(DimMColumnType transientInstance) {
		log.debug("saving DimMColumnType instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DimMColumnType persistentInstance) {
		log.debug("deleting DimMColumnType instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DimMColumnType findById(java.lang.String id) {
		log.debug("getting DimMColumnType instance with id: " + id);
		try {
			DimMColumnType instance = (DimMColumnType) getSession().get(
					DimMColumnType.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DimMColumnType> findByExample(DimMColumnType instance) {
		log.debug("finding DimMColumnType instance by example");
		try {
			List<DimMColumnType> results = (List<DimMColumnType>) getSession()
					.createCriteria(
							DimMColumnType.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding DimMColumnType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DimMColumnType as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DimMColumnType> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<DimMColumnType> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<DimMColumnType> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List findAll() {
		log.debug("finding all DimMColumnType instances");
		try {
			String queryString = "from DimMColumnType";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DimMColumnType merge(DimMColumnType detachedInstance) {
		log.debug("merging DimMColumnType instance");
		try {
			DimMColumnType result = (DimMColumnType) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DimMColumnType instance) {
		log.debug("attaching dirty DimMColumnType instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DimMColumnType instance) {
		log.debug("attaching clean DimMColumnType instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}