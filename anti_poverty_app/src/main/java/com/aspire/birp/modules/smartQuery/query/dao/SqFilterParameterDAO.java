package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqFilterParameter;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqFilterParameter entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqFilterParameter
 * @author MyEclipse Persistence Tools
 */
@Repository("sqFilterParameterDAO")
public class SqFilterParameterDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqFilterParameterDAO.class);
	// property constants
	public static final String PARAMETER_TYPE = "parameterType";
	public static final String PARAMETER_KEY = "parameterKey";
	public static final String VALUE_TYPE = "valueType";
	public static final String VALUE = "value";
	public static final String MONTH_CYC_TYPE = "monthCycType";
	public static final String MONTH_CYC_VALUE = "monthCycValue";
	public static final String DATE_CYC_TYPE = "dateCycType";
	public static final String DATE_CYC_VALUE = "dateCycValue";
	public static final String SQ_ID = "sqId";

	public SqFilterParameter findById(java.lang.String id) {
		log.debug("getting SqFilterParameter instance with id: " + id);
		try {
			SqFilterParameter instance = (SqFilterParameter) getSession().get(
					SqFilterParameter.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqFilterParameter> findByExample(SqFilterParameter instance) {
		log.debug("finding SqFilterParameter instance by example");
		try {
			List<SqFilterParameter> results = (List<SqFilterParameter>) getSession()
					.createCriteria(
							SqFilterParameter.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqFilterParameter> findByProperty(String propertyName, Object value) {
		log.debug("finding SqFilterParameter instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqFilterParameter as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqFilterParameter> findByParameterType(Object parameterType) {
		return findByProperty(PARAMETER_TYPE, parameterType);
	}

	public List<SqFilterParameter> findByParameterKey(Object parameterKey) {
		return findByProperty(PARAMETER_KEY, parameterKey);
	}

	public List<SqFilterParameter> findByValueType(Object valueType) {
		return findByProperty(VALUE_TYPE, valueType);
	}

	public List<SqFilterParameter> findByValue(Object value) {
		return findByProperty(VALUE, value);
	}

	public List<SqFilterParameter> findByMonthCycType(Object monthCycType) {
		return findByProperty(MONTH_CYC_TYPE, monthCycType);
	}

	public List<SqFilterParameter> findByMonthCycValue(Object monthCycValue) {
		return findByProperty(MONTH_CYC_VALUE, monthCycValue);
	}

	public List<SqFilterParameter> findByDateCycType(Object dateCycType) {
		return findByProperty(DATE_CYC_TYPE, dateCycType);
	}

	public List<SqFilterParameter> findByDateCycValue(Object dateCycValue) {
		return findByProperty(DATE_CYC_VALUE, dateCycValue);
	}

	public List<SqFilterParameter> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqFilterParameter> findAll() {
		log.debug("finding all SqFilterParameter instances");
		try {
			String queryString = "from SqFilterParameter";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

}