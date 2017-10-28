package com.aspire.birp.modules.dataMapping.mapping.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmColumnMapping;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmDatabaseColumn;


/**
 * A data access object (DAO) providing persistence and search support for
 * DmColumnMapping entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.DmColumnMapping.entity.DmColumnMapping
 * @author MyEclipse Persistence Tools
 */ 
@Repository("dmColumnMappingDAO")
public class DmColumnMappingDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DmColumnMappingDAO.class);
	// property constants
	public static final String COLUMN_NAME = "columnName";
	public static final String MAPPING_NAME = "mappingName";
	public static final String COLUMN_TYPE = "columnType";
	public static final String DATA_TYPE = "dataType";
	public static final String DATA_LENGTH = "dataLength";
	public static final String DATA_PRECISION = "dataPrecision";
	public static final String NULLABLE = "nullable";
	public static final String COLUMN_ID = "columnId";
	public static final String MP_TABLE_ID = "mpTableId";
	public static final String MP_TABLE = "mpTable";
	public static final String SORT = "sort";

	public DmColumnMapping findById(java.lang.String id) {
		log.debug("getting DmColumnMapping instance with id: " + id);
		try {
			this.getHibernateTemplate().clear();
			DmColumnMapping instance = (DmColumnMapping) this.getHibernateTemplate().get(
					DmColumnMapping.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<String> findExsitByColumn(List<DmDatabaseColumn> columns){
		log.debug("finding DmColumnMapping instance exsit by column");
		String columnids = "";
		for (int i = 0; i < columns.size(); i++) {
			if(i==0){
				columnids = "'" + columns.get(i).getColumn() + "'";
			}else{
				columnids += ",'" + columns.get(i).getColumn() + "'";
			}
		}
		String hql = "FROM DmColumnMapping WHERE columnName in ("+columnids+")";
		
		try {
			String queryString = "SELECT columnName FROM DmColumnMapping WHERE columnName in ("+columnids+")";
			/*Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();*/
			List<String>  list = (List<String>) this.getHibernateTemplate().find(queryString, null);
			return list;
 		} catch (RuntimeException re) {
			log.error("finding DmColumnMapping instance exsit by column failed", re);
			throw re;
		}
	}
	
	
	public List<DmColumnMapping> findByExample(DmColumnMapping instance) {
		log.debug("finding DmColumnMapping instance by example");
		try {
			List<DmColumnMapping> results = (List<DmColumnMapping>) getSession()
					.createCriteria(DmColumnMapping.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<DmColumnMapping> findByProperty(String propertyName, Object value) {
		log.debug("finding DmColumnMapping instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DmColumnMapping as model where model."
					+ propertyName + "= ? order by sort";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DmColumnMapping> findByColumnName(Object columnName) {
		return findByProperty(COLUMN_NAME, columnName);
	}

	public List<DmColumnMapping> findByMappingName(Object mappingName) {
		return findByProperty(MAPPING_NAME, mappingName);
	}

	public List<DmColumnMapping> findByColumnType(Object columnType) {
		return findByProperty(COLUMN_TYPE, columnType);
	}

	public List<DmColumnMapping> findByDataType(Object dataType) {
		return findByProperty(DATA_TYPE, dataType);
	}

	public List<DmColumnMapping> findByDataLength(Object dataLength) {
		return findByProperty(DATA_LENGTH, dataLength);
	}

	public List<DmColumnMapping> findByDataPrecision(Object dataPrecision) {
		return findByProperty(DATA_PRECISION, dataPrecision);
	}

	public List<DmColumnMapping> findByNullable(Object nullable) {
		return findByProperty(NULLABLE, nullable);
	}

	public List<DmColumnMapping> findByColumnId(Object columnId) {
		return findByProperty(COLUMN_ID, columnId);
	}

	public List<DmColumnMapping> findByMpTableId(Object mpTableId) {
		return findByProperty(MP_TABLE_ID, mpTableId);
	}

	public List<DmColumnMapping> findByMpTable(Object mpTable) {
		return findByProperty(MP_TABLE, mpTable);
	}

	public List<DmColumnMapping> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<DmColumnMapping> findAll() {
		log.debug("finding all DmColumnMapping instances");
		try {
			String queryString = "from DmColumnMapping order by columnName";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}