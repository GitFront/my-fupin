package com.aspire.birp.modules.dataLabel.config.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.dataLabel.config.entity.DmLabelTreeDef;

/**
 * A data access object (DAO) providing persistence and search support for
 * DmLabelTreeDef entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.dataLabel.config.entity.DmLabelTreeDef
 * @author MyEclipse Persistence Tools
 */
@Repository("dmLabelTreeDefDAO")
public class DmLabelTreeDefDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DmLabelTreeDefDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String PID = "pid";
	public static final String TYPE = "type";
	public static final String ASSO_TABLE = "assoTable";
	public static final String ASSO_FIELD = "assoField";
	public static final String ASSO_DIM = "assoDim";
	public static final String ASSO_RULE = "assoRule";
	public static final String TOP_NUM = "topNum";
	public static final String IS_TEMP = "isTemp";
	public static final String SORT = "sort";

	public DmLabelTreeDef findById(java.lang.String id) {
		log.debug("getting DmLabelTreeDef instance with id: " + id);
		try {
			DmLabelTreeDef instance = (DmLabelTreeDef) getSession().get(
					DmLabelTreeDef.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DmLabelTreeDef> findByExample(DmLabelTreeDef instance) {
		log.debug("finding DmLabelTreeDef instance by example");
		try {
			List<DmLabelTreeDef> results = (List<DmLabelTreeDef>) getSession()
					.createCriteria(
							DmLabelTreeDef.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<DmLabelTreeDef> findByProperty(String propertyName, Object value) {
		log.debug("finding DmLabelTreeDef instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DmLabelTreeDef as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DmLabelTreeDef> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<DmLabelTreeDef> findByPid(Object pid) {
		return findByProperty(PID, pid);
	}

	public List<DmLabelTreeDef> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<DmLabelTreeDef> findByAssoTable(Object assoTable) {
		return findByProperty(ASSO_TABLE, assoTable);
	}

	public List<DmLabelTreeDef> findByAssoField(Object assoField) {
		return findByProperty(ASSO_FIELD, assoField);
	}

	public List<DmLabelTreeDef> findByAssoDim(Object assoDim) {
		return findByProperty(ASSO_DIM, assoDim);
	}

	public List<DmLabelTreeDef> findByAssoRule(Object assoRule) {
		return findByProperty(ASSO_RULE, assoRule);
	}

	public List<DmLabelTreeDef> findByTopNum(Object topNum) {
		return findByProperty(TOP_NUM, topNum);
	}

	public List<DmLabelTreeDef> findByIsTemp(Object isTemp) {
		return findByProperty(IS_TEMP, isTemp);
	}

	public List<DmLabelTreeDef> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<DmLabelTreeDef> findAll() {
		log.debug("finding all DmLabelTreeDef instances");
		try {
			String queryString = "from DmLabelTreeDef";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}