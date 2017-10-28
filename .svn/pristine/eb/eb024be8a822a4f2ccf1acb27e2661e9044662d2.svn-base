package com.aspire.birp.modules.smartQuery.base.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.base.entity.DimFilterFormula;

/**
 * A data access object (DAO) providing persistence and search support for
 * DimFilterFormula entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @author MyEclipse Persistence Tools
 */
@Repository("dimFilterFormulaDAO")
public class DimFilterFormulaDAO extends BaseDAO{
	private static final Logger log = LoggerFactory
			.getLogger(DimFilterFormulaDAO.class);
	// property constants
	public static final String CODE = "code";
	public static final String TRANSCODE = "transcode";
	public static final String NAME = "name";
	public static final String SPACE = "space";
	public static final String SORT = "sort";

	public void save(DimFilterFormula transientInstance) {
		log.debug("saving DimFilterFormula instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DimFilterFormula persistentInstance) {
		log.debug("deleting DimFilterFormula instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DimFilterFormula findById(java.lang.String id) {
		log.debug("getting DimFilterFormula instance with id: " + id);
		try {
			this.getHibernateTemplate().clear();
			DimFilterFormula instance = (DimFilterFormula) getSession().get(
					DimFilterFormula.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DimFilterFormula> findByExample(DimFilterFormula instance) {
		log.debug("finding DimFilterFormula instance by example");
		try {
			List<DimFilterFormula> results = (List<DimFilterFormula>) getSession()
					.createCriteria(
							DimFilterFormula.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<DimFilterFormula> findByProperty(String propertyName, Object value) {
		log.debug("finding DimFilterFormula instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DimFilterFormula as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<DimFilterFormula> findLikeProperty(String propertyName, Object value) {
		log.debug("finding DimFilterFormula instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DimFilterFormula as model where model."
					+ propertyName + " like '%"+value+"%'";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find like property space failed", re);
			throw re;
		}
	}

	public List<DimFilterFormula> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<DimFilterFormula> findByTranscode(Object transcode) {
		return findByProperty(TRANSCODE, transcode);
	}

	public List<DimFilterFormula> findByName(Object name) {
		return findByProperty(NAME, name);
	}
	
	public List<DimFilterFormula> findLikeSpace(Object space) {
		return findLikeProperty(SPACE, space);
	}

	public List<DimFilterFormula> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<DimFilterFormula> findAll() {
		log.debug("finding all DimFilterFormula instances");
		try {
			String queryString = "from DimFilterFormula";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DimFilterFormula merge(DimFilterFormula detachedInstance) {
		log.debug("merging DimFilterFormula instance");
		try {
			DimFilterFormula result = (DimFilterFormula) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DimFilterFormula instance) {
		log.debug("attaching dirty DimFilterFormula instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DimFilterFormula instance) {
		log.debug("attaching clean DimFilterFormula instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}