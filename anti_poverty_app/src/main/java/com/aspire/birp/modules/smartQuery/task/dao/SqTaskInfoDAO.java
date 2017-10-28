package com.aspire.birp.modules.smartQuery.task.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqTaskInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.task.entity.SqTaskInfo
 * @author MyEclipse Persistence Tools
 */

@Repository("sqTaskInfoDAO")
public class SqTaskInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqTaskInfoDAO.class);
	// property constants
	public static final String RQ_CONTENT = "rqContent";
	public static final String RQ_SOURCE_TB = "rqSourceTb";
	public static final String CYC_TYP = "cycTyp";
	public static final String CYC_LEN = "cycLen";
	public static final String TASK_AUTHER = "taskAuther";
	public static final String TASK_STATUS = "taskStatus";
	public static final String DATA_STORE_DT = "dataStoreDt";
	public static final String FILE_NAME = "fileName";
	public static final String SQ_COLUMNS = "sqColumns";

	public SqTaskInfo findById(java.lang.String id) {
		log.debug("getting SqTaskInfo instance with id: " + id);
		try {
			SqTaskInfo instance = (SqTaskInfo) getSession().get(
					SqTaskInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqTaskInfo> findByExample(SqTaskInfo instance) {
		log.debug("finding SqTaskInfo instance by example");
		try {
			List<SqTaskInfo> results = (List<SqTaskInfo>) getSession()
					.createCriteria(SqTaskInfo.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqTaskInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding SqTaskInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SqTaskInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqTaskInfo> findByRqContent(Object rqContent) {
		return findByProperty(RQ_CONTENT, rqContent);
	}

	public List<SqTaskInfo> findByRqSourceTb(Object rqSourceTb) {
		return findByProperty(RQ_SOURCE_TB, rqSourceTb);
	}

	public List<SqTaskInfo> findByCycTyp(Object cycTyp) {
		return findByProperty(CYC_TYP, cycTyp);
	}

	public List<SqTaskInfo> findByCycLen(Object cycLen) {
		return findByProperty(CYC_LEN, cycLen);
	}

	public List<SqTaskInfo> findByTaskAuther(Object taskAuther) {
		return findByProperty(TASK_AUTHER, taskAuther);
	}

	public List<SqTaskInfo> findByTaskStatus(Object taskStatus) {
		return findByProperty(TASK_STATUS, taskStatus);
	}

	public List<SqTaskInfo> findByDataStoreDt(Object dataStoreDt) {
		return findByProperty(DATA_STORE_DT, dataStoreDt);
	}

	public List<SqTaskInfo> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List<SqTaskInfo> findBySqColumns(Object sqColumns) {
		return findByProperty(SQ_COLUMNS, sqColumns);
	}

	public List<SqTaskInfo> findAll() {
		log.debug("finding all SqTaskInfo instances");
		try {
			String queryString = "from SqTaskInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}