package com.aspire.birp.modules.dataMapping.mapping.dao;

import static org.hibernate.criterion.Example.create;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.base.dao.BaseDAO;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMappingAssociation;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMappingAssociationId;

/**
 * A data access object (DAO) providing persistence and search support for
 * DmTableMappingAssociation entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.aspire.birp.modules.smartQuery.DmTableMappingAssociation.entity.DmTableMappingAssociation
 * @author MyEclipse Persistence Tools
 */
@Repository("dmTableMappingAssociationDAO")
public class DmTableMappingAssociationDAO extends BaseDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DmTableMappingAssociationDAO.class);
	// property constants
	public static final String MP_TABLE_CODE1 = "mpTableCode1";
	public static final String MP_COLUMN_ID1 = "mpColumnId1";
	public static final String MP_COLUMN_CODE1 = "mpColumnCode1";
	public static final String MP_TABLE_CODE2 = "mpTableCode2";
	public static final String MP_COLUMN_ID2 = "mpColumnId2";
	public static final String MP_COLUMN_CODE2 = "mpColumnCode2";
	public static final String CREATOR = "creator";
	public static final String SORT = "sort";

	public DmTableMappingAssociation findById(
			DmTableMappingAssociationId id) {
		log.debug("getting DmTableMappingAssociation instance with id: " + id);
		try {
			DmTableMappingAssociation instance = (DmTableMappingAssociation) getSession()
					.get(DmTableMappingAssociation.class,
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<DmTableMappingAssociation> findByExample(
			DmTableMappingAssociation instance) {
		log.debug("finding DmTableMappingAssociation instance by example");
		try {
			List<DmTableMappingAssociation> results = (List<DmTableMappingAssociation>) getSession()
					.createCriteria(
							DmTableMappingAssociation.class)
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<DmTableMappingAssociation> findByProperty(String propertyName, Object value) {
		log.debug("finding DmTableMappingAssociation instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DmTableMappingAssociation as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<DmTableMappingAssociation> findByMpTableCode1(
			Object mpTableCode1) {
		return findByProperty(MP_TABLE_CODE1, mpTableCode1);
	}

	public List<DmTableMappingAssociation> findByMpColumnId1(Object mpColumnId1) {
		return findByProperty(MP_COLUMN_ID1, mpColumnId1);
	}

	public List<DmTableMappingAssociation> findByMpColumnCode1(
			Object mpColumnCode1) {
		return findByProperty(MP_COLUMN_CODE1, mpColumnCode1);
	}

	public List<DmTableMappingAssociation> findByMpTableCode2(
			Object mpTableCode2) {
		return findByProperty(MP_TABLE_CODE2, mpTableCode2);
	}

	public List<DmTableMappingAssociation> findByMpColumnId2(Object mpColumnId2) {
		return findByProperty(MP_COLUMN_ID2, mpColumnId2);
	}

	public List<DmTableMappingAssociation> findByMpColumnCode2(
			Object mpColumnCode2) {
		return findByProperty(MP_COLUMN_CODE2, mpColumnCode2);
	}

	public List<DmTableMappingAssociation> findByCreator(Object creator) {
		return findByProperty(CREATOR, creator);
	}

	public List<DmTableMappingAssociation> findBySort(Object sort) {
		return findByProperty(SORT, sort);
	}

	public List<DmTableMappingAssociation> findAll() {
		log.debug("finding all DmTableMappingAssociation instances");
		try {
			String queryString = "from DmTableMappingAssociation";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}