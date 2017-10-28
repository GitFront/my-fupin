package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfoTemp;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqFilterInfoTemp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfoTemp
 * @author MyEclipse Persistence Tools
 */
@Repository("sqFilterInfoTempDAO")
public class SqFilterInfoTempDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqFilterInfoTempDAO.class);
	// property constants
	public static final String RELATION_ID = "relationId";
	public static final String RELATION = "relation";
	public static final String MP_COLUMN_ID = "mpColumnId";
	public static final String SQ_COLUMN = "sqColumn";
	public static final String FORMULA_ID = "formulaId";
	public static final String FORMULA = "formula";
	public static final String VALUE = "value";
	public static final String SQ_ID = "sqId";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public SqFilterInfoTemp findById(java.lang.String id) {
		log.debug("getting SqFilterInfoTemp instance with id: " + id);
		try {
			SqFilterInfoTemp instance = (SqFilterInfoTemp) getSession().get(
					SqFilterInfoTemp.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqFilterInfoTemp> findByExample(SqFilterInfoTemp instance) {
		log.debug("finding SqFilterInfoTemp instance by example");
		try {
			List<SqFilterInfoTemp> results = (List<SqFilterInfoTemp>) getSession()
					.createCriteria(
							SqFilterInfoTemp.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqFilterInfoTemp> findByProperty(String propertyName, Object value) {
		log.debug("finding SqFilterInfoTemp instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqFilterInfoTemp as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqFilterInfoTemp> findByRelationId(Object relationId) {
		return findByProperty(RELATION_ID, relationId);
	}

	public List<SqFilterInfoTemp> findByRelation(Object relation) {
		return findByProperty(RELATION, relation);
	}

	public List<SqFilterInfoTemp> findByMpColumnId(Object mpColumnId) {
		return findByProperty(MP_COLUMN_ID, mpColumnId);
	}

	public List<SqFilterInfoTemp> findBySqColumn(Object sqColumn) {
		return findByProperty(SQ_COLUMN, sqColumn);
	}

	public List<SqFilterInfoTemp> findByFormulaId(Object formulaId) {
		return findByProperty(FORMULA_ID, formulaId);
	}

	public List<SqFilterInfoTemp> findByFormula(Object formula) {
		return findByProperty(FORMULA, formula);
	}

	public List<SqFilterInfoTemp> findByValue(Object value) {
		return findByProperty(VALUE, value);
	}

	public List<SqFilterInfoTemp> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqFilterInfoTemp> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqFilterInfoTemp> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqFilterInfoTemp> findAll() {
		log.debug("finding all SqFilterInfoTemp instances");
		try {
			String queryString = "from SqFilterInfoTemp";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}