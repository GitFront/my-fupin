package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqFilterInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfo
 * @author MyEclipse Persistence Tools
 */
@Repository("sqFilterInfoDAO")
public class SqFilterInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqFilterInfoDAO.class);
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

	public SqFilterInfo findById(java.lang.String id) {
		log.debug("getting SqFilterInfo instance with id: " + id);
		try {
			SqFilterInfo instance = (SqFilterInfo) getSession().get(
					SqFilterInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqFilterInfo> findByExample(SqFilterInfo instance) {
		log.debug("finding SqFilterInfo instance by example");
		try {
			List<SqFilterInfo> results = (List<SqFilterInfo>) getSession()
					.createCriteria(SqFilterInfo.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqFilterInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding SqFilterInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqFilterInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqFilterInfo> findByRelationId(Object relationId) {
		return findByProperty(RELATION_ID, relationId);
	}

	public List<SqFilterInfo> findByRelation(Object relation) {
		return findByProperty(RELATION, relation);
	}

	public List<SqFilterInfo> findByMpColumnId(Object mpColumnId) {
		return findByProperty(MP_COLUMN_ID, mpColumnId);
	}

	public List<SqFilterInfo> findBySqColumn(Object sqColumn) {
		return findByProperty(SQ_COLUMN, sqColumn);
	}

	public List<SqFilterInfo> findByFormulaId(Object formulaId) {
		return findByProperty(FORMULA_ID, formulaId);
	}

	public List<SqFilterInfo> findByFormula(Object formula) {
		return findByProperty(FORMULA, formula);
	}

	public List<SqFilterInfo> findByValue(Object value) {
		return findByProperty(VALUE, value);
	}

	public List<SqFilterInfo> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqFilterInfo> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqFilterInfo> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqFilterInfo> findAll() {
		log.debug("finding all SqFilterInfo instances");
		try {
			String queryString = "from SqFilterInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}