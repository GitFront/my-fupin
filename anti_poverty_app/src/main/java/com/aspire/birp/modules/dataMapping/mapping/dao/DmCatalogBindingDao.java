package com.aspire.birp.modules.dataMapping.mapping.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmCatalogBinding;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmDatabaseColumn;


/**
 * A data access object (DAO) providing persistence and search support for
 * DmCatalogBinding entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.aspire.birp.modules.smartQuery.DmCatalogBinding.entity.DmCatalogBinding
 * @author MyEclipse Persistence Tools
 */ 
@Repository("dmCatalogBindingDao")
public class DmCatalogBindingDao extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DmCatalogBindingDao.class);
	// property constants
	public static final String CATALOG_ID = "catalogId";
	public static final String TABLE_ID = "tableId";
	public static final String CREATOR = "creator";
	public static final String CREATE_TIME = "createTime";

	public DmCatalogBinding findById(java.lang.String id) {
		log.debug("getting DmCatalogBinding instance with id: " + id);
		try {
			DmCatalogBinding instance = (DmCatalogBinding) this.getHibernateTemplate().get(
					DmCatalogBinding.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<String> findExsitByColumn(List<DmDatabaseColumn> columns){
		log.debug("finding DmCatalogBinding instance exsit by column");
		String columnids = "";
		for (int i = 0; i < columns.size(); i++) {
			if(i==0){
				columnids = "'" + columns.get(i).getColumn() + "'";
			}else{
				columnids += ",'" + columns.get(i).getColumn() + "'";
			}
		}
		String hql = "FROM DmCatalogBinding WHERE columnName in ("+columnids+")";
		
		try {
			String queryString = "SELECT columnName FROM DmCatalogBinding WHERE columnName in ("+columnids+")";
			/*Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();*/
			List<String>  list = (List<String>) this.getHibernateTemplate().find(queryString, null);
			return list;
 		} catch (RuntimeException re) {
			log.error("finding DmCatalogBinding instance exsit by column failed", re);
			throw re;
		}
	}
	
	
	public List<DmCatalogBinding> findByExample(DmCatalogBinding instance) {
		log.debug("finding DmCatalogBinding instance by example");
		try {
			List<DmCatalogBinding> results = (List<DmCatalogBinding>) getSession()
					.createCriteria(DmCatalogBinding.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<DmCatalogBinding> findByProperty(String propertyName, Object value) {
		log.debug("finding DmCatalogBinding instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DmCatalogBinding as model where model."
					+ propertyName + "= ? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DmCatalogBinding> findByCatalogId(Object catalogId) {
		return findByProperty(CATALOG_ID, catalogId);
	}

	public List<DmCatalogBinding> findByTableId(Object tableId) {
		return findByProperty(TABLE_ID, tableId);
	}


	public List<DmCatalogBinding> findAll() {
		log.debug("finding all DmCatalogBinding instances");
		try {
			String queryString = "from DmCatalogBinding order by columnName";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}