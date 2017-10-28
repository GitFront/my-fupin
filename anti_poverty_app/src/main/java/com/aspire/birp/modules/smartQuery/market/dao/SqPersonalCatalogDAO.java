package com.aspire.birp.modules.smartQuery.market.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalog;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqPersonalCatalog entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalog
 * @author MyEclipse Persistence Tools
 */
@Repository("sqPersonalCatalogDAO")
public class SqPersonalCatalogDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqPersonalCatalogDAO.class);
	// property constants
	public static final String PARENT_ID = "parentId";
	public static final String NAME = "name";
	public static final String FULL_PATH = "fullPath";
	public static final String CREATOR_ID = "creatorId";
	public static final String CREATOR = "creator";
	public static final String SHARE_STATUS = "shareStatus";
	public static final String SORT = "sort";

	public SqPersonalCatalog findById(java.lang.String id) {
		log.debug("getting SqPersonalCatalog instance with id: " + id);
		try {
			SqPersonalCatalog instance = (SqPersonalCatalog) getSession().get(
					SqPersonalCatalog.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqPersonalCatalog> findByExample(SqPersonalCatalog instance) {
		log.debug("finding SqPersonalCatalog instance by example");
		try {
			List<SqPersonalCatalog> results = (List<SqPersonalCatalog>) getSession()
					.createCriteria(
							SqPersonalCatalog.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqPersonalCatalog> findByProperty(String propertyName, Object value) {
		log.debug("finding SqPersonalCatalog instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqPersonalCatalog as model where model."
					+ propertyName + "= ? order by sort";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqPersonalCatalog> findByParentId(Object parentId) {
		return findByProperty(PARENT_ID, parentId);
	}

	public List<SqPersonalCatalog> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<SqPersonalCatalog> findByFullPath(Object fullPath) {
		return findByProperty(FULL_PATH, fullPath);
	}

	public List<SqPersonalCatalog> findByCreatorId(Object creatorId) {
		return findByProperty(CREATOR_ID, creatorId);
	}

	public List<SqPersonalCatalog> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqPersonalCatalog> findByShareStatus(Object shareStatus) {
		return findByProperty(SHARE_STATUS, shareStatus);
	}

	public List<SqPersonalCatalog> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqPersonalCatalog> findAll() {
		log.debug("finding all SqPersonalCatalog instances");
		try {
			String queryString = "from SqPersonalCatalog order by sort";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}