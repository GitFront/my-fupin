
package com.aspire.birp.modules.dataMapping.mapping.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.birp.modules.dataMapping.mapping.entity.DmCatalogBinding;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmColumnMapping;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableCatalog;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMapping;
import com.aspire.birp.modules.dataMapping.mapping.service.DmMappingService;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmDatabaseColumn;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmDatabaseTable;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableCatalogVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableMappingVO;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.base.vo.ColumnType;
import com.aspire.birp.modules.sys.utils.UserUtils;

/**  
 * @Title: 映射管理功能服务实现类
 * @Description: 用于实现映射管理功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月15日 下午2:30:56
 * @version: V1.0
 */
@Service
public class DmMappingServiceImpl extends SqBaseService implements DmMappingService {

	private static Logger log = LoggerFactory.getLogger(DmMappingServiceImpl.class);

	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryWideTableList()
	 */
	@Override
	public List<DmDatabaseTable> queryWideTableList(String keyword,String tableName) {
		
		List<DmDatabaseTable> list = new ArrayList<DmDatabaseTable>();
		
		String[] keywords = StringUtils.split(keyword,",");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT t.TABLE_NAME,tc.TABLE_TYPE,t.STATUS,tc.COMMENTS "
				+ "FROM (SELECT * FROM USER_TABLES ut WHERE NOT EXISTS (SELECT tm.id FROM DM_TABLE_MAPPING tm WHERE tm.TABLE_NAME=ut.TABLE_NAME)) t, "
				+ 		"USER_TAB_COMMENTS tc "
				+ "WHERE tc.TABLE_NAME=t.TABLE_NAME and ");
		for (int i = 0; i < keywords.length; i++) {
			if(StringUtils.isNotBlank(tableName)){
				sql.append(" (upper(t.TABLE_NAME) like '"+tableName.toUpperCase()+"%' ") ;
				break;
		 	}	
			if(i == 0){
				sql.append(" ( upper(t.TABLE_NAME) like '"+keywords[i]+"%' ") ;
			}else{
				sql.append(" or upper(t.TABLE_NAME) like '"+keywords[i]+"%'");
			}
		}
		sql.append(") ORDER BY t.TABLE_NAME");
		
		
		
		List<Object[]> rows = this.excuteSQLQuery(sql.toString());
		
		if(rows != null && rows.size() > 0){
			for (Object[] objects : rows) {
				list.add(new DmDatabaseTable(objects[0], objects[1], objects[2], objects[3]));
			}
		}
		
		log.info("Oracle数据库宽表信息查询成功");
		
		return list;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryDatabaseColumnList(java.lang.String)
	 */
	@Override
	public List<DmDatabaseColumn> queryDatabaseColumnList(String table) {
		List<DmDatabaseColumn> list = new ArrayList<DmDatabaseColumn>();
		/*传入table参数异常时返回空列表信息*/
		if(StringUtils.isBlank(table)) return list;
		
		String sql = "SELECT t.COLUMN_NAME,c.COMMENTS,t.DATA_TYPE,t.DATA_LENGTH,t.DATA_PRECISION,t.NULLABLE,t.COLUMN_ID "
				+ "FROM (SELECT * FROM USER_TAB_COLUMNS uc WHERE NOT EXISTS (SELECT cm.id FROM DM_COLUMN_MAPPING cm WHERE cm.COLUMN_NAME=uc.COLUMN_NAME AND cm.MP_TABLE=uc.TABLE_NAME)) t, "
				+ 		"USER_COL_COMMENTS c "
				+ "WHERE t.TABLE_NAME = c.TABLE_NAME and t.COLUMN_NAME = c.COLUMN_NAME and t.TABLE_NAME = ?"
				+ " ORDER BY t.COLUMN_NAME";
		
		List<Object[]> rows = this.excuteSQLQuery(sql, new String[]{table});
		
		if(rows != null && rows.size() > 0){
			for (Object[] objects : rows) {
				list.add(new DmDatabaseColumn(objects[0], objects[1], objects[2], objects[3], objects[4], objects[5], objects[6],"N"));
			}
		}
		
		log.info("Oracle数据库宽表的列信息查询成功");
		
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryWideTableInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, String> queryWideTableInfo(String id,
			String tableName) {
		Map<String, String> rs = new HashMap<String,String>();
		if(StringUtils.isBlank(id)){
			/*当ID值为空时，则数据值未配置映射表*/
			String sql = "SELECT t.TABLE_NAME,tc.COMMENTS,tc.TABLE_TYPE,t.STATUS "
					+ "FROM USER_TABLES t, USER_TAB_COMMENTS tc "
					+ "WHERE tc.TABLE_NAME=t.TABLE_NAME and t.TABLE_NAME = ?";
			List<Object[]> rows = this.excuteSQLQuery(sql, new String[]{tableName});
			
			if(rows != null && rows.size() > 0){
				Object[] objects = rows.get(0);
				rs.put("id", "");
				rs.put("table", String.valueOf(objects[0]));
				rs.put("comment", String.valueOf(objects[1]));
				rs.put("tableType", "FACT");
				rs.put("storageType", "F");
				rs.put("cycType","D" );
				rs.put("isPerimssion", SmartQueryConstant.COMMON_FLAG_FALSE);
				rs.put("status", String.valueOf(objects[3]));
			}
		}else{
			/*当ID值不为空时，则数据值为已配置映射*/
			DmTableMapping tm = this.getDmTableMappingDAO().findById(id);
			rs.put("id", tm.getId());
			rs.put("table", tm.getTableName());
			rs.put("comment", tm.getMappingName());
			rs.put("tableType", tm.getTableType());
			rs.put("isPerimssion", tm.getIsPerimssion());
			rs.put("cycType", tm.getCycType());
			rs.put("storageType", tm.getStorageType());
			rs.put("status", tm.getStatus());
		}
		
		return rs;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryTableMappingById(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public DmTableMappingVO queryTableMappingById(String id) {
		DmTableMappingVO vo = new DmTableMappingVO();
		if(StringUtils.isBlank(id))
			return vo;
		DmTableMapping tm = this.getDmTableMappingDAO().findById(id);
		vo = this.copyBeanProperties(tm);
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryTableMappingList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<DmTableMappingVO> queryTableMappingList(String mappingName) {
		DetachedCriteria dc = DetachedCriteria.forClass(DmTableMapping.class);
		if(StringUtils.isNotBlank(mappingName)){
	 		dc.add(Restrictions.ilike("mappingName",mappingName,MatchMode.START));
	 	}
		dc.addOrder(Order.asc("createTime"));
		List<DmTableMapping> list = (List<DmTableMapping>) this.getDmTableMappingDAO().findByCriteria(dc);
		List<DmTableMappingVO> vos = new ArrayList<DmTableMappingVO>();
		for (DmTableMapping en : list) {
			DmTableMappingVO vo = this.copyBeanProperties(en);
			vos.add(vo);
		}		
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryDimMappingList()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<DmTableMappingVO> queryDimMappingList() {
		List<DmTableMappingVO> vos = new ArrayList<DmTableMappingVO>();
		String hql = "FROM DmTableMapping WHERE tableType=?";
		List<?> rs = this.getHibernateTemplate().find(hql, SmartQueryConstant.TABLE_TYPE_DIM);
		for (Object obj : rs) {
			DmTableMapping en = (DmTableMapping) obj;
			vos.add(this.copyBeanProperties(en));
		}		
		return vos;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryTableCatalogList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<DmTableCatalogVO> queryTableCatalogList(String type) {
		List<DmTableCatalogVO> vos = new ArrayList<DmTableCatalogVO>();
		List<DmTableCatalog> list = this.getDmTableCatalogDAO().findByType(type);
		for (DmTableCatalog en : list) {
			vos.add(this.copyBeanProperties(en));
		}
		return vos;
	}
	/**
	 * 递归将children里的对象放到vos
	 * @param children
	 * @param vos
	 */
	private void entityAddList(List<DmTableCatalog> children,List<DmTableCatalogVO> vos ){
			for (DmTableCatalog dmTableCatalog : children) {
				vos.add(this.copyBeanProperties(dmTableCatalog));
				if(dmTableCatalog.getChildren()!=null&&dmTableCatalog.getChildren().size()>0){
					entityAddList(dmTableCatalog.getChildren(),vos);
				}
			}
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#saveTableCatalogList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean saveTableCatalog(String id, String name, String pId,
			String fullPath,String type) {
		String user = UserUtils.getUser().getName();
		Long sort = this.getDmTableCatalogDAO().getMaxSort("DmTableCatalog") + 1;
		this.getHibernateTemplate().save(new DmTableCatalog(id, pId, name, fullPath, user, new Timestamp(new Date().getTime()), sort,type));
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#updateTableCatalogList(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updateTableCatalog(String id, String name,String fullPath) {
		if(StringUtils.isBlank(id)) return false;
		DmTableCatalog renameInstance = this.getDmTableCatalogDAO().findById(id);
		renameInstance.setName(name);
		renameInstance.setFullPath(fullPath);
		this.getHibernateTemplate().merge(renameInstance);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#removeTableCatalogList(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean removeTableCatalog(String id) {
		if(StringUtils.isBlank(id)) return false;

		DmTableCatalog persistentInstance = this.getDmTableCatalogDAO().findById(id);
		this.getDmTableCatalogDAO().closeSession();
		this.getHibernateTemplate().delete(persistentInstance);
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#isremoveTableCatalog(java.lang.String)
	 */
	@Override
	public boolean isremoveTableCatalog(String id) {
		if(StringUtils.isBlank(id)) return false;
		/*判断该目录是否含有绑定的宽表数据*/
		List<String> catalogIdsList=new ArrayList<String>();
		DmTableCatalog dmTableCatalog = this.getDmTableCatalogDAO().findById(id);
		catalogIdsList.add(dmTableCatalog.getId());
		getCatalogIds(catalogIdsList, dmTableCatalog.getChildren());
		String hql = "FROM DmCatalogBinding cb where  cb.catalogId  IN(:catalogIds))";
		List<DmCatalogBinding> tables = (List<DmCatalogBinding>) this.getHibernateTemplate().findByNamedParam(hql, "catalogIds", catalogIdsList);
		//List<DmTableMapping> tables = this.getDmTableMappingDAO().findByCatalogId(id);
		if(tables != null && tables.size()>0) return false;
		
		return true;
	}
	private void getCatalogIds(List<String> catalogIdsList,List<DmTableCatalog> children){
		for (DmTableCatalog dmTableCatalog : children) {
			catalogIdsList.add(dmTableCatalog.getId());
			if(dmTableCatalog.getChildren()!=null&&dmTableCatalog.getChildren().size()>0)
			getCatalogIds(catalogIdsList,dmTableCatalog.getChildren());	
		}
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryColumnMappingList(java.lang.String)
	 */
	@Override
	public List<DmColumnMappingVO> queryColumnMappingList(String mpTableId) {
		List<DmColumnMappingVO> vos = new ArrayList<DmColumnMappingVO>();
		/*传入table参数异常时返回空列表信息*/
		if(StringUtils.isBlank(mpTableId)) return vos;
		List<DmColumnMapping> list = this.getDmColumnMappingDAO().findByMpTableId(mpTableId);
		/*转换列类型的转义*/
		if(list != null && list.size() > 0){
			for (DmColumnMapping sm : list) {
				DmColumnMappingVO vo = this.copyBeanProperties(sm);
				vo.setColumnTypeName(columnTypeMap.get(sm.getColumnType()));
				vos.add(vo);
			}
		}		
		return vos;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public String saveTableMapping(DmTableMapping dmTableMapping) {
		/*生成唯一的ID值*/
		String id = UUID.randomUUID().toString();
		String creator = UserUtils.getUser().getName();
		/*默认无目录的宽表为9999*/
		//String nonCatalog = SmartQueryConstant.TABLE_MAPPING_DEFAULT_CATALOG;
		Timestamp createTime = new Timestamp(new Date().getTime());
		dmTableMapping.setId(id);
		dmTableMapping.setCreator(creator);
		dmTableMapping.setCreateTime(createTime);
		this.getHibernateTemplate().save(dmTableMapping);
		
		return id;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#updateTableMapping(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updateTableMapping(String id, String mapping,String isPerimssion,String cycType,String tableType,String storageType) {
		if(StringUtils.isBlank(id)) return false;
		DmTableMapping renameInstance = this.getDmTableMappingDAO().findById(id);
		renameInstance.setMappingName(mapping);
		renameInstance.setIsPerimssion(isPerimssion);
		renameInstance.setCycType(cycType);
		renameInstance.setStorageType(storageType);
		renameInstance.setTableType(tableType);
		this.getHibernateTemplate().merge(renameInstance);
		return false;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#saveColumnMappings(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean saveColumnMappings(String mpTableId, String mpTableName,
			List<DmDatabaseColumn> columns) {
		if(StringUtils.isBlank(mpTableId) || StringUtils.isBlank(mpTableName)) return false;
		
		if(columns != null && columns.size() > 0){			
			String creator = UserUtils.getUser().getName();
			Timestamp createTime = new Timestamp(new Date().getTime());
			Long sort = this.getDmColumnMappingDAO().getMaxSort("DmColumnMapping")+1;
			/*转换列数据格式*/
			for (DmDatabaseColumn column : columns) {
				String id = UUID.randomUUID().toString();
				String columnType = this.convColumnType(column.getDataType());
				/*统计日期字段统一转换为日期类型*/
				if(SmartQueryConstant.COLUMN_DATE_NAME.equalsIgnoreCase(column.getColumn()))
					columnType = ColumnType.d.toString();
				/*自动去除系统说明注解*/
				String mapping = column.getComment();
				if(StringUtils.isNotBlank(mapping) && !SmartQueryConstant.COLUMN_COMMENT_NOTE_SPLIT.equals("?") 
						&& mapping.indexOf(SmartQueryConstant.COLUMN_COMMENT_NOTE_SPLIT) != -1){
					mapping = mapping.substring(0,mapping.indexOf(SmartQueryConstant.COLUMN_COMMENT_NOTE_SPLIT));
				}
				this.getHibernateTemplate().save(new DmColumnMapping(id, column.getColumn(), mapping, columnType, 
						column.getDataType(), column.getDataLength(), column.getDataPrecision(), column.getNullable(), 
						column.getColumnId(), mpTableId, mpTableName,creator,createTime,sort,column.getIsKeys()));
				sort++;				
			}
		}
		
		return true;
	}
	
	
	/**
	 * 转换为智能查询默认的列类型信息
	 * @param dataType 数据库列类型信息
	 * @return
	 * @author 张健雄
	 */
	private String convColumnType(String dataType){
		String columnType = ColumnType.s.toString();/*默认列类型值*/
		if(StringUtils.isBlank(dataType)) return columnType;
		/*定义数据库类型列表*/
		String[] numberType = {"long","binary","binary_float","binary_double","bigint","int","integer","smallint","tinyint",
							   "bit","numeric","decimal","money","smallmoney","float","real","number"};
		String[] stringType = {"char","varchar","varchar2","text","nchar","nvarchar","nvarchar2","ntext","varbinary"};
		String[] dateType = {"date","timestamp","datetime","smalldatetime"};
		String type = dataType.toLowerCase();
		
		if(Arrays.asList(stringType).contains(type)){
			columnType = ColumnType.s.toString();
		}else if(Arrays.asList(numberType).contains(type)){
			columnType = ColumnType.n.toString();
		}else if(Arrays.asList(dateType).contains(type)){
			columnType = ColumnType.d.toString();
		}
		return columnType;
		
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#removeColumnMappings(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean removeColumnMappings(List<String> idlist) {
		
		String ids = com.aspire.bi.common.util.StringUtils.splitStringForList(idlist);
		String hql = "DELETE FROM DmColumnMapping t WHERE t.id in ("+ids+")";
		/*批量删除列对象*/
		this.getHibernateTemplate().bulkUpdate(hql);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#removeTableMapping(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean removeTableMapping(String id) {
		if(StringUtils.isBlank(id)) return false;
		/*删除对应的全部映射列信息*/
		String hql = "DELETE FROM DmColumnMapping WHERE mpTableId=?";
		/*批量删除列对象*/
		this.getHibernateTemplate().bulkUpdate(hql,id);
		/*删除映射表信息*/
		DmTableMapping table = this.getDmTableMappingDAO().findById(id);
		this.getHibernateTemplate().delete(table);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#updateColumnMappings(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updateColumnMappings(String id, String mapping,
			String columnType, String isKeys) {
		if(StringUtils.isBlank(id)) return false;
		
		DmColumnMapping scm = this.getDmColumnMappingDAO().findById(id);
		scm.setMappingName(mapping);
		scm.setColumnType(columnType);
		scm.setIsKeys(isKeys);
		this.getHibernateTemplate().merge(scm);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryTableMappingListByCatalog(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<DmTableMappingVO> queryTableMappingListByCatalog(String catalogId,String type) {
		List<DmTableMappingVO> vos = new ArrayList<DmTableMappingVO>();
		String hql="";
		 List<DmTableMapping> rows =null;
		if(!StringUtils.isBlank(catalogId)) {
			 hql = "FROM DmTableMapping tm "
						+ "WHERE   EXISTS(SELECT cb.tableId   FROM DmCatalogBinding cb where  cb.catalogId =? and cb.tableId =tm.id  )"
						+ "ORDER BY sort";
			 rows = (List<DmTableMapping>) this.getHibernateTemplate().find(hql, new Object[]{catalogId});
		}else{
			//查询未绑定
			 List<DmTableCatalog> catalogs = this.getDmTableCatalogDAO().findByType(type);
			 List<String> catalogIdsList = new ArrayList<String>();
			 for (DmTableCatalog catalog : catalogs) {
				 catalogIdsList.add(catalog.getId());
				}
			 hql = "FROM DmTableMapping tm "
					 + "WHERE  NOT EXISTS(SELECT cb.tableId FROM DmCatalogBinding cb where cb.tableId=tm.id  and cb.catalogId  IN(:catalogIds))"
					 + "ORDER BY sort";
			 rows = (List<DmTableMapping>) this.getHibernateTemplate().findByNamedParam(hql, "catalogIds", catalogIdsList);
			 
		}
		
		/*转换列类型的转义*/
		if(rows != null && rows.size() > 0){
			for (DmTableMapping dmTableMapping : rows) {
				DmTableMappingVO vo = this.copyBeanProperties(dmTableMapping);
				vos.add(vo);
				
			}
		}	
		this.getHibernateTemplate().flush();
		return vos;
	}


	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#updateTableMappings(java.util.List, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean updateTableMappings(String tmids, String catalogId,Boolean on) {
		DmTableCatalog dmTableCatalog = this.getDmTableCatalogDAO().findById(catalogId);
		String creator = UserUtils.getUser().getName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		
	
		if (dmTableCatalog!=null&&tmids != null){
			String[] ids = StringUtils.split(tmids, ",");
			if(on!=null&&on){
				//绑定
				for (String tableId : ids) {
					DmCatalogBinding dmCatalogBinding = new DmCatalogBinding();
					dmCatalogBinding.setCreator(creator);
					dmCatalogBinding.setCreateTime(createTime);
					dmCatalogBinding.setCatalogId(dmTableCatalog.getId());
					dmCatalogBinding.setTableId(tableId);

					this.getHibernateTemplate().saveOrUpdate(dmCatalogBinding);
					this.getHibernateTemplate().flush();
				}
			}else{
				//解绑
				String hql = "FROM DmCatalogBinding cb where cb.tableId IN(:tmids) and cb.catalogId =:catalogId";
				 
				 List<DmCatalogBinding> dmCatalogBindingList =(List<DmCatalogBinding>) this.getHibernateTemplate().
						 findByNamedParam(hql,new String[]{"tmids","catalogId"},new Object[]{ids,catalogId} );
				 
				 this.getHibernateTemplate().deleteAll(dmCatalogBindingList);
					this.getHibernateTemplate().flush();
			
			
			}
		}	
		
		return true;
	}
	

	
	
	
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryTableCatalogInfo(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public DmTableCatalogVO queryTableCatalogInfo(String id) {
		DmTableCatalogVO vo = new DmTableCatalogVO();
		if(StringUtils.isBlank(id)) return vo;
		DmTableCatalog catalog = this.getDmTableCatalogDAO().findById(id);
		vo = this.copyBeanProperties(catalog);
		return vo;
	}



	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryColumnMappingList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<DmColumnMappingVO> queryColumnMappingList(String mpTableId,
			String sqId, String isTemp) {
		List<DmColumnMappingVO> list = new ArrayList<DmColumnMappingVO>();
		/*传入table参数异常时返回空列表信息*/
		if(StringUtils.isBlank(mpTableId) || StringUtils.isBlank(sqId)) return list;
		String sqSelectTable = "SqSelectInfo";
		if(SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp)) sqSelectTable += "Temp";
		
		String hql = "FROM DmColumnMapping t "
				+ "WHERE NOT EXISTS (SELECT st.id FROM "+sqSelectTable+" st WHERE st.sqId=? AND st.mpColumnId=t.id) AND t.mpTableId=? "
				+ "ORDER BY t.sort";
		
		List<?> rows = this.getHibernateTemplate().find(hql, new Object[]{sqId,mpTableId});
		/*转换列类型的转义*/
		if(rows != null && rows.size() > 0){
			for (Object obj : rows) {
				DmColumnMapping sm = (DmColumnMapping) obj;
				DmColumnMappingVO vo = this.copyBeanProperties(sm);
				vo.setColumnTypeName(columnTypeMap.get(sm.getColumnType()));
				list.add(vo);
			}
		}	
		return list;
	}


	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryColumnMappingName(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String queryColumnMappingName(String id) {
		String cname = "";
		if(StringUtils.isBlank(id)) return cname;
		DmColumnMapping r = this.getDmColumnMappingDAO().findById(id);
		if(r != null) cname = r.getMappingName();
		return cname;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#queryTableMappingName(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String queryTableMappingName(String id) {
		String tname = "";
		if(StringUtils.isBlank(id)) return tname;
		DmTableMapping r = this.getDmTableMappingDAO().findById(id);
		if(r != null) tname = r.getMappingName();
		return tname;
	}
	/**
	 * 获取主题下拉列表
	 */
	@Override
	public List<Map<String, Object>> selectThemeByType() {
		List<Map<String,Object>> datas=new ArrayList<Map<String,Object>>();
		String sql="SELECT DISTINCT type FROM DM_TABLE_CATALOG WHERE type is NOT NULL";
		List<Object> list = this.excuteSQLQuery(sql);
		Map<String,Object>data=null;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i)!=null){
				data=new HashMap<String,Object>();
				data.put("id", list.get(i));
				data.put("type", list.get(i));
				datas.add(data);
			}
		}
		return datas;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public Object[] saveTheme(DmTableCatalog dmTableCatalog) {
		List<DmTableCatalog> list = this.getDmTableCatalogDAO().findByType(dmTableCatalog.getType());
		Object[] results = null;
		if(list.size()>0){
			 results = new Object[]{Boolean.FALSE,"保存失败,名称重复"};;
		}else{
			String id = UUID.randomUUID().toString();
			String creator = UserUtils.getUser().getName();
			Timestamp createTime = new Timestamp(new Date().getTime());
			dmTableCatalog.setCreateTime(createTime);
			dmTableCatalog.setCreator(creator);
			dmTableCatalog.setId(id);
			dmTableCatalog.setParentId("-1");
			dmTableCatalog.setSort(0l);
			dmTableCatalog.setName("根目录");
			dmTableCatalog.setFullPath(dmTableCatalog.getName());
			try {
				this.getHibernateTemplate().save(dmTableCatalog);
				  results = new Object[]{Boolean.TRUE,"主题保存成功"};
			} catch (Exception e) {
				  results = new Object[]{Boolean.FALSE,"主题保存失败，请稍候再试！"};
			}
		}
		return results;
	}

	
	
	
}
	


