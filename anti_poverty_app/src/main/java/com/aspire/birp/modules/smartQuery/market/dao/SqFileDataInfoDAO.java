package com.aspire.birp.modules.smartQuery.market.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.smartQuery.market.entity.SqFileDataInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * SqFileDataInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.market.entity.SqFileDataInfo
 * @author MyEclipse Persistence Tools
 */
@Repository("sqFileDataInfoDAO")
public class SqFileDataInfoDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(SqFileDataInfoDAO.class);
	// property constants
	public static final String FILE_NAME = "fileName";
	public static final String CREATE_TYPE = "createType";
	public static final String FILE_TYPE = "fileType";
	public static final String FILE_SIZE = "fileSize";
	public static final String FILE_PATH = "filePath";
	public static final String CATALOG_ID = "catalogId";
	public static final String SQ_ID = "sqId";
	public static final String STATUS = "status";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public SqFileDataInfo findById(java.lang.String id) {
		log.debug("getting SqFileDataInfo instance with id: " + id);
		try {
			SqFileDataInfo instance = (SqFileDataInfo) getSession().get(
					SqFileDataInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SqFileDataInfo> findByExample(SqFileDataInfo instance) {
		log.debug("finding SqFileDataInfo instance by example");
		try {
			List<SqFileDataInfo> results = (List<SqFileDataInfo>) getSession()
					.createCriteria(
							SqFileDataInfo.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SqFileDataInfo> findByProperty(String propertyName, Object value) {
		log.debug("finding SqFileDataInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SqFileDataInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SqFileDataInfo> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List<SqFileDataInfo> findByCreateType(Object createType) {
		return findByProperty(CREATE_TYPE, createType);
	}

	public List<SqFileDataInfo> findByFileType(Object fileType) {
		return findByProperty(FILE_TYPE, fileType);
	}

	public List<SqFileDataInfo> findByFileSize(Object fileSize) {
		return findByProperty(FILE_SIZE, fileSize);
	}

	public List<SqFileDataInfo> findByFilePath(Object filePath) {
		return findByProperty(FILE_PATH, filePath);
	}

	public List<SqFileDataInfo> findByCatalogId(Object catalogId) {
		return findByProperty(CATALOG_ID, catalogId);
	}

	public List<SqFileDataInfo> findBySqId(Object sqId) {
		return findByProperty(SQ_ID, sqId);
	}

	public List<SqFileDataInfo> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<SqFileDataInfo> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<SqFileDataInfo> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<SqFileDataInfo> findAll() {
		log.debug("finding all SqFileDataInfo instances");
		try {
			String queryString = "from SqFileDataInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}