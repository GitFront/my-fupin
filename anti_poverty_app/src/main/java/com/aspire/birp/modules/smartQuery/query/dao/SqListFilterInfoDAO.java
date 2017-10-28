package com.aspire.birp.modules.smartQuery.query.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqListFilterInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqListFilterInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.query.entity.SqListFilterInfo
 * @author MyEclipse Persistence Tools
 */
@Repository("sqListFilterInfoDAO")
public class SqListFilterInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqListFilterInfoDAO.class);
	// property constants
	public static final String LIST_NAME = "listName";
	public static final String LIST_SEPARATOR = "listSeparator";
	public static final String LIST_NUM = "listNum";
	public static final String FILE_NAME = "fileName";
	public static final String FILE_PATH = "filePath";
	public static final String CREATOR_ID = "creatorId";
	public static final String CREATOR = "creator";
	public static final String CREATOR_ACC = "creatorAcc";
	public static final String SORT = "sort";

	public SqListFilterInfo findById(java.lang.String id) {
		log.debug("getting SqListFilterInfo instance with id: " + id);
		try {
			getSession().clear();/*解决数据脏读问题*/
			SqListFilterInfo instance = (SqListFilterInfo) getSession().get(
					SqListFilterInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqListFilterInfo> findByExample(SqListFilterInfo instance) {
		log.debug("finding SqListFilterInfo instance by example");
		try {
			List<SqListFilterInfo> results = (List<SqListFilterInfo>) getSession()
					.createCriteria(
							SqListFilterInfo.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqListFilterInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding SqListFilterInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			getSession().clear();/*解决数据脏读问题*/
			String queryString = "from SqListFilterInfo as model where model."
					+ propertyName + "= ? order by model.sort";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqListFilterInfo> findByListName(Object listName) {
		return findByProperty(LIST_NAME, listName);
	}

	public List<SqListFilterInfo> findByListSeparator(Object listSeparator) {
		return findByProperty(LIST_SEPARATOR, listSeparator);
	}

	public List<SqListFilterInfo> findByListNum(Object listNum) {
		return findByProperty(LIST_NUM, listNum);
	}

	public List<SqListFilterInfo> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List<SqListFilterInfo> findByFilePath(Object filePath) {
		return findByProperty(FILE_PATH, filePath);
	}

	public List<SqListFilterInfo> findByCreatorId(Object creatorId) {
		return findByProperty(CREATOR_ID, creatorId);
	}

	public List<SqListFilterInfo> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqListFilterInfo> findByCreatorAcc(Object creatorAcc) {
		return findByProperty(CREATOR_ACC, creatorAcc);
	}

	public List<SqListFilterInfo> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqListFilterInfo> findAll() {
		log.debug("finding all SqListFilterInfo instances");
		try {
			String queryString = "from SqListFilterInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}