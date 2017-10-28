package com.aspire.birp.modules.dataMapping.mapping.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableCatalog;

/**
 * A data access object (DAO) providing persistence and search support for
 * DmTableCatalog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.DmTableCatalogVO.DmTableCatalogVO
 * @author MyEclipse Persistence Tools
 */ 
@Repository("dmTableCatalogDAO")
public class DmTableCatalogDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DmTableCatalogDAO.class);
	// property constants
	public static final String PARENT_ID = "parentId";
	public static final String NAME = "name";
	public static final String FULL_PATH = "fullPath";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";
	public static final String TYPE = "type";

	public DmTableCatalog findById(java.lang.String id) {
		log.debug("getting DmTableCatalog instance with id: " + id);
		try {
			getSession().clear();
			DmTableCatalog instance = (DmTableCatalog) getSession().get(
					DmTableCatalog.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DmTableCatalog> findByExample(DmTableCatalog instance) {
		log.debug("finding DmTableCatalog instance by example");
		try {
			List<DmTableCatalog> results = (List<DmTableCatalog>) getSession()
					.createCriteria(DmTableCatalog.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<DmTableCatalog> findByProperty(String propertyName, Object value) {
		log.debug("finding DmTableCatalog instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DmTableCatalog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DmTableCatalog> findByParentId(Object parentId) {
		return findByProperty(PARENT_ID, parentId);
	}
	public List<DmTableCatalog> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<DmTableCatalog> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<DmTableCatalog> findByFullPath(Object fullPath) {
		return findByProperty(FULL_PATH, fullPath);
	}

	public List<DmTableCatalog> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<DmTableCatalog> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<DmTableCatalog> findAll() {
		log.debug("finding all DmTableCatalog instances");
		try {
			getSession().clear();/*解决数据脏读问题*/
			String queryString = "from DmTableCatalog ORDER BY sort";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}