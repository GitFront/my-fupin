package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqSelectColumn;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqSelectColumn entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqSelectColumn
 * @author MyEclipse Persistence Tools
 */
@Repository("sqSelectColumnDAO")
public class SqSelectColumnDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqSelectColumnDAO.class);
	// property constants
	public static final String SQ_TABLE = "sqTable";
	public static final String SQ_COLUMN = "sqColumn";
	public static final String SQ_COLUMN_ALIAS = "sqColumnAlias";
	public static final String SQ_COLUMN_NAME = "sqColumnName";
	public static final String SQ_ID = "sqId";
	public static final String SORT = "sort";

	public SqSelectColumn findById(java.lang.String id) {
		log.debug("getting SqSelectColumn instance with id: " + id);
		try {
			SqSelectColumn instance = (SqSelectColumn) getSession().get(
					SqSelectColumn.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqSelectColumn> findByExample(SqSelectColumn instance) {
		log.debug("finding SqSelectColumn instance by example");
		try {
			List<SqSelectColumn> results = (List<SqSelectColumn>) getSession()
					.createCriteria(
							SqSelectColumn.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqSelectColumn> findByProperty(String propertyName, Object value) {
		log.debug("finding SqSelectColumn instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqSelectColumn as model where model."
					+ propertyName + "= ? order by model.sort";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqSelectColumn> findBySqTable(Object sqTable) {
		return findByProperty(SQ_TABLE, sqTable);
	}

	public List<SqSelectColumn> findBySqColumn(Object sqColumn) {
		return findByProperty(SQ_COLUMN, sqColumn);
	}

	public List<SqSelectColumn> findBySqColumnAlias(Object sqColumnAlias) {
		return findByProperty(SQ_COLUMN_ALIAS, sqColumnAlias);
	}

	public List<SqSelectColumn> findBySqColumnName(Object sqColumnName) {
		return findByProperty(SQ_COLUMN_NAME, sqColumnName);
	}

	public List<SqSelectColumn> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqSelectColumn> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqSelectColumn> findAll() {
		log.debug("finding all SqSelectColumn instances");
		try {
			String queryString = "from SqSelectColumn";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}