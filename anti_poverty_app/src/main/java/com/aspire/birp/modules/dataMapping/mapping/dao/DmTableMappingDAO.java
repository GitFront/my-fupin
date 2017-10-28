package com.aspire.birp.modules.dataMapping.mapping.dao;

import static org.hibernate.criterion.Example.create;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.aspire.birp.common.utils.StringUtils;
import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMapping;
import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalog;

/**
 * A data access object (DAO) providing persistence and search support for
 * DmTableMapping entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.DmTableMapping.entity.DmTableMapping
 * @author MyEclipse Persistence Tools
 * @param <E>
 */ 
@Repository("dmTableMappingDAO")
public class DmTableMappingDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DmTableMappingDAO.class);
	// property constants
	public static final String TABLE_NAME = "tableName";
	public static final String MAPPING_NAME = "mappingName";
	public static final String TABLE_TYPE = "tableType";
	public static final String STATUS = "status";
	public static final String CATALOG_ID = "catalogId";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public DmTableMapping findById(java.lang.String id) {
		log.debug("getting DmTableMapping instance with id: " + id);
		try {
			this.getHibernateTemplate().clear();
			DmTableMapping instance = (DmTableMapping) getSession().get(
					DmTableMapping.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DmTableMapping> findByExample(DmTableMapping instance) {
		log.debug("finding DmTableMapping instance by example");
		try {
			List<DmTableMapping> results = (List<DmTableMapping>) getSession()
					.createCriteria(DmTableMapping.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<DmTableMapping> findByProperty(String propertyName, Object value) {
		log.debug("finding DmTableMapping instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DmTableMapping as model where model."
					+ propertyName + "= ? order by tableName";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DmTableMapping> findByTableName(Object tableName) {
		return findByProperty(TABLE_NAME, tableName);
	}

	public List<DmTableMapping> findByMappingName(Object mappingName) {
		return findByProperty(MAPPING_NAME, mappingName);
	}

	public List<DmTableMapping> findByTableType(Object tableType) {
		return findByProperty(TABLE_TYPE, tableType);
	}

	public List<DmTableMapping> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}
	
	public List<DmTableMapping> findByCatalogId(Object catalogId) {
		return findByProperty(CATALOG_ID, catalogId);
	}

	public List<DmTableMapping> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<DmTableMapping> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<DmTableMapping> findAll() {
		log.debug("finding all DmTableMapping instances");
		try {
			getSession().clear();
			String queryString = "from DmTableMapping order by createTime";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	
	
	
}