package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfoTemp;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqQueryInfoTemp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfoTemp
 * @author MyEclipse Persistence Tools
 */

@Repository("sqQueryInfoTempDAO")
public class SqQueryInfoTempDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqQueryInfoTempDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String CONFIG_TYPE = "configType";
	public static final String MP_TABLE_ID = "mpTableId";
	public static final String SQ_TABLE = "sqTable";
	public static final String FILE_NAME = "fileName";
	public static final String PERSONAL_CATALOG_ID = "personalCatalogId";
	public static final String DATA_STORE_DATE = "dataStoreDate";
	public static final String CYC_TYPE = "cycType";
	public static final String CYC_LEN = "cycLen";
	public static final String CYC_INFO = "cycInfo";
	public static final String CREATOR = "creator";
	public static final String SQ_SQL = "sqSql";
	public static final String SORT = "sort";

	public SqQueryInfoTemp findById(java.lang.String id) {
		log.debug("getting SqQueryInfoTemp instance with id: " + id);
		try {
			SqQueryInfoTemp instance = (SqQueryInfoTemp) getSession().get(
					SqQueryInfoTemp.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqQueryInfoTemp> findByExample(SqQueryInfoTemp instance) {
		log.debug("finding SqQueryInfoTemp instance by example");
		try {
			List<SqQueryInfoTemp> results = (List<SqQueryInfoTemp>) getSession()
					.createCriteria(
							SqQueryInfoTemp.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqQueryInfoTemp> findByProperty(String propertyName, Object value) {
		log.debug("finding SqQueryInfoTemp instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqQueryInfoTemp as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqQueryInfoTemp> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<SqQueryInfoTemp> findByConfigType(Object configType) {
		return findByProperty(CONFIG_TYPE, configType);
	}

	public List<SqQueryInfoTemp> findByMpTableId(Object mpTableId) {
		return findByProperty(MP_TABLE_ID, mpTableId);
	}

	public List<SqQueryInfoTemp> findBySqTable(Object sqTable) {
		return findByProperty(SQ_TABLE, sqTable);
	}

	public List<SqQueryInfoTemp> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List<SqQueryInfoTemp> findByPersonalCatalogId(
			Object personalCatalogId) {
		return findByProperty(PERSONAL_CATALOG_ID, personalCatalogId);
	}

	public List<SqQueryInfoTemp> findByDataStoreDate(Object dataStoreDate) {
		return findByProperty(DATA_STORE_DATE, dataStoreDate);
	}

	public List<SqQueryInfoTemp> findByCycType(Object cycType) {
		return findByProperty(CYC_TYPE, cycType);
	}

	public List<SqQueryInfoTemp> findByCycLen(Object cycLen) {
		return findByProperty(CYC_LEN, cycLen);
	}

	public List<SqQueryInfoTemp> findByCycInfo(Object cycInfo) {
		return findByProperty(CYC_INFO, cycInfo);
	}

	public List<SqQueryInfoTemp> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqQueryInfoTemp> findBySqSql(Object sqSql) {
		return findByProperty(SQ_SQL, sqSql);
	}

	public List<SqQueryInfoTemp> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqQueryInfoTemp> findAll() {
		log.debug("finding all SqQueryInfoTemp instances");
		try {
			String queryString = "from SqQueryInfoTemp";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}