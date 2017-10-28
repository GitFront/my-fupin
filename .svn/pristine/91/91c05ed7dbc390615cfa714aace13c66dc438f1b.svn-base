package com.aspire.birp.modules.smartQuery.query.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.auth.modules.sys.entity.Role;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.bi.common.constant.DateConstant;
import com.aspire.bi.common.util.DateUtils;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.common.excel.Column;
import com.aspire.birp.modules.base.constant.BaseConstant;
import com.aspire.birp.modules.base.constant.StorageType;
import com.aspire.birp.modules.base.service.BaseService;
import com.aspire.birp.modules.base.utils.Utils;
import com.aspire.birp.modules.common.vo.GroupDataVo;
import com.aspire.birp.modules.common.vo.PieDataVo;
import com.aspire.birp.modules.dataLabel.base.constant.DataLabelConstant;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmColumnMapping;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableCatalog;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMapping;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMappingAssociation;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMappingAssociationId;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableMappingVO;
import com.aspire.birp.modules.smartQuery.base.constant.OptionType;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.constant.TackCycType;
import com.aspire.birp.modules.smartQuery.base.entity.DimFilterFormula;
import com.aspire.birp.modules.smartQuery.base.entity.DimFilterRalation;
import com.aspire.birp.modules.smartQuery.base.entity.DimSqSort;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.base.vo.ColumnType;
import com.aspire.birp.modules.smartQuery.base.vo.FilterDateCycType;
import com.aspire.birp.modules.smartQuery.base.vo.FilterMonthCycType;
import com.aspire.birp.modules.smartQuery.base.vo.FilterType;
import com.aspire.birp.modules.smartQuery.base.vo.SqDataGridObject;
import com.aspire.birp.modules.smartQuery.market.entity.SqFileDataInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfoTemp;
import com.aspire.birp.modules.smartQuery.query.entity.SqFilterParameter;
import com.aspire.birp.modules.smartQuery.query.entity.SqListFilterInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfoTemp;
import com.aspire.birp.modules.smartQuery.query.entity.SqSelectColumn;
import com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfoTemp;
import com.aspire.birp.modules.smartQuery.query.entity.SqSortInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqSortInfoTemp;
import com.aspire.birp.modules.smartQuery.query.entity.SqTableInfoTemp;
import com.aspire.birp.modules.smartQuery.query.service.SqQueryService;
import com.aspire.birp.modules.smartQuery.query.vo.SqColumnMappingTree;
import com.aspire.birp.modules.smartQuery.query.vo.SqFilterInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqFilterVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSelectInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSelecterVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSortInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqTableInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqTableMappingTree;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskInfo;
import com.aspire.birp.modules.smartQuery.task.service.SqTaskCommonService;
import com.aspire.birp.modules.sys.utils.UserUtils;

/**
 * @Title: 智能查询功能服务实现类
 * @Description: 用于实现智能查询功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月22日 下午2:30:56
 * @version: V1.0
 */
@Service
public class SqQueryServiceImpl extends SqBaseService implements SqQueryService {

	private static Logger log = LoggerFactory
			.getLogger(SqQueryServiceImpl.class);
	@Autowired
	private SqTaskCommonService sqTaskCommonService;
	
    private static final String FUNCTION = "智能查询";

	/* 对应的参数别名系数 */
	private final static String INDEX_SELECT = "S";
	
	/**
	 * sql缓存库，主要用于导出数据时使用
	 * key:searchKey,标识单次查询
	 * @param String sql 存储sql语句
	 * @param Map mapper 存储列的语义信息
	 */
	private static Map<String,Map<String,Object>> sqlPool;
	
	
	
	/*用于存储存在分区统计的数据表集合<数据表名，默认分区时间>*/
	private Map<String,String> statTimeMap = new HashMap<String,String>();
	
	
	public SqQueryServiceImpl() {
		super();
		sqlPool = new HashMap<String, Map<String,Object>>();
	}
	
	public static void clearSqlPool() {
		log.info("清空SQL缓存库信息");
		SqQueryServiceImpl.sqlPool.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryTableMappingTree()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqTableMappingTree> queryTableMappingTree(String type) {
		/* 创建树形对象列表 */
		List<SqTableMappingTree> list = new ArrayList<SqTableMappingTree>();
		/* 查询目录列表信息 */
		List<DmTableCatalog> catalogs = this.getDmTableCatalogDAO().findByType(type);
		SqTableMappingTree node =null;
		for (DmTableCatalog catalog : catalogs) {
			node = new SqTableMappingTree();
			node.setId(catalog.getId());
			node.setName(catalog.getName());
			node.setpId(catalog.getParentId());
			node.setParent(true);
			node.setOpen(true);
			list.add(node);
			List<DmTableMapping> dmTableMappingList = catalog.getDmTableMappingList();
			for (DmTableMapping dmTableMapping : dmTableMappingList) {
				node = new SqTableMappingTree();
				node.setId(dmTableMapping.getId());
				node.setName(dmTableMapping.getMappingName());
				node.setpId(catalog.getId());
				node.setParent(false);
				node.setMpTableName(dmTableMapping.getTableName());
				node.setCycType(dmTableMapping.getCycType());
				node.setStorageType(dmTableMapping.getStorageType());
				list.add(node);
			}
			/*List<DmTableCatalog> children = catalog.getChildren();
			for (DmTableCatalog dmTableCatalog : children) {
				node = new SqTableMappingTree();
				node.setId(dmTableCatalog.getId());
				node.setName(dmTableCatalog.getName());
				node.setpId(dmTableCatalog.getParentId());
				node.setParent(true);
				node.setOpen(true);
				list.add(node);
				dmTableMappingList = dmTableCatalog.getDmTableMappingList();
				for (DmTableMapping dmTableMapping : dmTableMappingList) {
					node = new SqTableMappingTree();
					node.setId(dmTableMapping.getId());
					node.setName(dmTableMapping.getMappingName());
					node.setpId(dmTableCatalog.getId());
					node.setParent(false);
					node.setMpTableName(dmTableMapping.getTableName());
					node.setCycType(dmTableMapping.getCycType());
					node.setStorageType(dmTableMapping.getStorageType());
					list.add(node);
				}
			}*/
			
			
		}
		 for(SqTableMappingTree sqTableMappingTree : list) {
	            sqTableMappingTree.setParent(true);
	            sqTableMappingTree.setOpen(true);
	            if(StringUtils.isNotEmpty(sqTableMappingTree.getMpTableName())) {
	                sqTableMappingTree.setIconSkin("icon-ztree-table");
	            }
	        }
		log.info("宽表目录树查询成功！");

		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#queryColumnMappingTree()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqColumnMappingTree> queryColumnMappingTree(boolean check) {
		/* 创建树形对象列表 */
		List<SqColumnMappingTree> list = new ArrayList<SqColumnMappingTree>();
		
		/* 查询目录列表信息 */
		List<DmTableCatalog> catalogs = this.getDmTableCatalogDAO().findAll();
		/* 加载目录数据 */
		boolean hasCatalog = false;
		if (catalogs != null && catalogs.size() > 0) {
			hasCatalog = true;
			SqColumnMappingTree node =null;
			for (int i = 0; i < catalogs.size(); i++) {
				DmTableCatalog catalog = catalogs.get(i);
				 node = new SqColumnMappingTree();
				node.setId(catalog.getId());
				node.setName(catalog.getName());
				node.setpId(catalog.getParentId());
				if(i == 0)
					node.setOpen(true);
				node.setParent(true);				
				list.add(node);
				
				List<DmTableMapping> dmTableMappingList = catalog.getDmTableMappingList();
				for (DmTableMapping dmTableMapping : dmTableMappingList) {
					node = new SqColumnMappingTree();
					node.setId(dmTableMapping.getId()+catalog.getId());
					node.setName(dmTableMapping.getMappingName());
					node.setpId(catalog.getId());
					node.setParent(true);
					node.setMpTable(dmTableMapping.getTableName());
					list.add(node);
				}
				
				/*查询映射字段绑定信息*/
				String	hql = "FROM DmColumnMapping t WHERE EXISTS (SELECT tableId FROM DmCatalogBinding WHERE  tableId=t.mpTableId and catalogId=?)  ORDER BY t.sort";
				
				List<?> cmList = this.getHibernateTemplate().find(hql,catalog.getId());
				
				if(hasCatalog && cmList != null && !cmList.isEmpty()){
					for (Object object : cmList) {
						DmColumnMapping cm = (DmColumnMapping) object;
						node = new SqColumnMappingTree();
						node.setId(cm.getId());
						node.setName(cm.getMappingName());
						node.setpId(cm.getMpTableId()+catalog.getId());
						node.setParent(false);
						node.setMpTable(cm.getMpTable());
						node.setMpColumn(cm.getColumnName());;
						list.add(node);
					}
				}
				
			}
		}
		
		
		
		
		
		

	/*	 查询映射表绑定信息 
		String hql = "FROM DmTableMapping t WHERE t.catalogId <> ? ORDER BY t.sort";
		List<?> tmList = this.getHibernateTemplate().find(hql,
				SmartQueryConstant.TABLE_MAPPING_DEFAULT_CATALOG);

		if (hasCatalog && tmList != null && tmList.size() > 0) {
			for (Object object : tmList) {
				DmTableMapping tm = (DmTableMapping) object;
				SqColumnMappingTree node = new SqColumnMappingTree();
				node.setId(tm.getId());
				node.setName(tm.getMappingName());
//				node.setpId(tm.getCatalogId());
				node.setParent(true);
				node.setMpTable(tm.getTableName());
				list.add(node);
			}
		}
		*/

		log.info("宽表字段目录树查询成功！");
		
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * saveSqQueryInfoTemp(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public SqQueryInfoVO saveSqQueryInfoTemp(String tableids) {
		if (StringUtils.isBlank(tableids))
			log.error("映射表信息为空，无法保存临时查询对象", new RuntimeException(
					"Option False : SqQueryInfoTemp save happen Exception!"));
		String ids = com.aspire.bi.common.util.StringUtils
				.splitStringForIds(tableids);

		String hql = "FROM DmTableMapping t WHERE t.id IN (" + ids + ")";

		List<?> tms = this.getHibernateTemplate().find(hql);
		if (tms == null || tms.size() == 0)
			log.error("映射表信息为空，无法保存临时查询对象", new RuntimeException(
					"Option False : SqQueryInfoTemp save happen Exception!"));
		/* 创建信息 */
		String creatorId = UserUtils.getUser().getId();
		String creator = UserUtils.getUser().getName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		/* 生成临时查询ID值 */
		String sqId = UUID.randomUUID().toString();
		/* 对应数据表字符信息 */
		String sqTables = "";
		List<String> tableNames = new ArrayList<String>();
		for (int i = 0; i < tms.size(); i++) {
			DmTableMapping tm = (DmTableMapping) tms.get(i);
			tableNames.add(tm.getMappingName());
			/* 组装表编码字符 */
			if (i == 0) {
				sqTables = tm.getTableName();
			} else {
				sqTables += "," + tm.getTableName();
			}

		}
		/* 保存查询对象数据 */
		SqQueryInfoTemp query = new SqQueryInfoTemp();

		query.setId(sqId);
		query.setName("临时查询信息");
		query.setConfigType(SmartQueryConstant.QUERY_TASK_TYPE_MANUAL);
		query.setMpTableId(tableids);
		query.setSqTable(sqTables);
		query.setCreatorId(creatorId);
		query.setCreator(creator);
		query.setCreateTime(createTime);
		this.getHibernateTemplate().save(query);

		/* 保存查询表数据信息 */
		for (int i = 0; i < tms.size(); i++) {
			DmTableMapping tm = (DmTableMapping) tms.get(i);
			SqTableInfoTemp table = new SqTableInfoTemp();
			table.setId(UUID.randomUUID().toString());
			table.setMpTableId(tm.getId());
			table.setSqTable(tm.getTableName());
			table.setSqId(sqId);
			table.setCreator(creator);
			table.setCreateTime(createTime);
			table.setSort(Long.valueOf(i));
			this.getHibernateTemplate().save(table);
		}

		SqQueryInfoVO vo = this.copyBeanPropertiesQuery(query, true);
		/* 前端展示已选择表名 */
		vo.setTableNames(tableNames);
		/* 设置临时数据表单的默认值 */
		vo.setFileName("");
		vo.setTableMappingName("");
		vo.setDataStoreDate(30);
		vo.setPersonalCatalogId("");
		vo.setValidDate(DateUtils.dateTimeToString(new Date(),
				DateConstant.YEAR_MOUTH_DAY));
		vo.setInvalidDate("");
		vo.setCycType(TackCycType.m.toString());
		vo.setCycLen(1);

		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryColumnListByTables(java.lang.String)
	 */
	@Override
	public List<DmColumnMappingVO> queryColumnListByTables(String tableids,
			String type) {
		List<DmColumnMappingVO> vos = new ArrayList<DmColumnMappingVO>();
		/* 传入table参数异常时返回空列表信息 */
		if (StringUtils.isBlank(tableids))
			return vos;
		String[] tableidArray = StringUtils.split(tableids, ",");
		String ids = com.aspire.bi.common.util.StringUtils.splitStringForArray(
				tableidArray, ",");
		String hql = "SELECT new com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO(c.id,c.columnName,c.mappingName,c.columnType,c.dataType, "
				+ "t.id,t.tableName,t.mappingName,c.sort) FROM DmColumnMapping c , DmTableMapping t "
				+ "WHERE c.mpTableId=t.id AND t.id IN (" + ids + ") ";
		if (StringUtils.isNotBlank(type))
			hql += " AND c.columnType='" + type + "' ";
		hql += " ORDER BY c.sort";
		List<?> list = this.getHibernateTemplate().find(hql);
		/* 转换列类型的转义 */
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				DmColumnMappingVO vo = (DmColumnMappingVO) obj;
				vo.setColumnTypeName(columnTypeMap.get(vo.getColumnType()));
				vos.add(vo);
			}
		}
		return vos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.mapping.service.SqMappingService#
	 * queryColumnMappingList(java.lang.String)
	 */
	@Override
	public List<DmColumnMappingVO> queryUnselectColumnList(String sqId,
			String isTemp) {
		List<DmColumnMappingVO> vos = new ArrayList<DmColumnMappingVO>();
		/* 传入table参数异常时返回空列表信息 */
		if (StringUtils.isBlank(sqId))
			return vos;
		String sqSelectTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo";
		if (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp))
			sqSelectTable += "Temp";
		SqQueryInfoVO query = this.querySqQueryInfoById(sqId, isTemp);
		String[] tableidArray = StringUtils.split(query.getMpTableId(), ",");
		String ids = com.aspire.bi.common.util.StringUtils.splitStringForArray(
				tableidArray, ",");
		String hql = "SELECT new com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO(c.id,c.columnName,c.mappingName,c.columnType,c.dataType, "
				+ "t.id,t.tableName,t.mappingName,c.sort) FROM DmColumnMapping c , DmTableMapping t "
				+ "WHERE c.mpTableId=t.id AND t.id IN ("
				+ ids
				+ ") AND NOT EXISTS (SELECT st.id FROM "
				+ sqSelectTable
				+ " st WHERE st.sqId=? AND st.mpColumnId=c.id)"
				+ " ORDER BY c.sort";
		List<?> list = this.getHibernateTemplate().find(hql, sqId);
		/* 转换列类型的转义 */
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				DmColumnMappingVO vo = (DmColumnMappingVO) obj;
				vo.setColumnTypeName(columnTypeMap.get(vo.getColumnType()));
				vos.add(vo);
			}
		}
		return vos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * removeSqSelectInfoTemp(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean removeSqSelectInfo(String sqId, String isTemp,
			List<String> mpColumnIds) {
		String ids = com.aspire.bi.common.util.StringUtils
				.splitStringForList(mpColumnIds);
		String sqTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo";
		if (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp))
			sqTable += "Temp";

		String hql = "DELETE FROM " + sqTable + " t WHERE t.id in (" + ids
				+ ")";
		/* 批量删除列对象 */
		this.getHibernateTemplate().bulkUpdate(hql);

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * saveSqSelectInfoTemp(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean saveSqSelectInfo(String sqId, String isTemp,
			List<String> mpColumnIds) {
		String ids = com.aspire.bi.common.util.StringUtils
				.splitStringForList(mpColumnIds);
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		String sqTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo";
		if (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp))
			sqTable += "Temp";

		String hql = "FROM DmColumnMapping t WHERE t.id IN (" + ids
				+ ") AND t.id NOT IN (SELECT mpColumnId FROM " + sqTable
				+ " WHERE sqId=?) ORDER BY t.sort";
		List<?> rows = this.getHibernateTemplate().find(hql, sqId);

		if (rows != null && rows.size() > 0) {
			/* 查询数据库里的最大序号及创建信息 */
			Long sort = this.getSqSelectInfoTempDAO().getMaxSort(sqTable) + 1;
			String creator = UserUtils.getUser().getName();
			Timestamp createTime = new Timestamp(new Date().getTime());
			if (temp) {
				for (Object object : rows) {
					DmColumnMapping column = (DmColumnMapping) object;
					SqSelectInfoTemp select = new SqSelectInfoTemp();
					/* 创建ID */
					String id = UUID.randomUUID().toString();
					select.setId(id);
					select.setSqId(sqId);
					select.setMpTableId(column.getMpTableId());
					select.setSqTable(column.getMpTable());
					select.setMpColumnId(column.getId());
					select.setSqColumn(column.getColumnName());
					select.setCreator(creator);
					select.setCreateTime(createTime);
					select.setSort(sort);
					this.getHibernateTemplate().save(select);
					sort++;
				}
			} else {
				for (Object object : rows) {
					DmColumnMapping column = (DmColumnMapping) object;
					SqSelectInfo select = new SqSelectInfo();
					/* 创建ID */
					String id = UUID.randomUUID().toString();
					select.setId(id);
					select.setSqId(sqId);
					select.setMpTableId(column.getMpTableId());
					select.setSqTable(column.getMpTable());
					select.setMpColumnId(column.getId());
					select.setSqColumn(column.getColumnName());
					select.setCreator(creator);
					select.setCreateTime(createTime);
					select.setSort(sort);
					this.getHibernateTemplate().save(select);
					sort++;
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryFilerRalation()
	 */
	@Override
	public List<DimFilterRalation> queryFilerRalation() {
		List<DimFilterRalation> list = this.getDimFilterRalationDAO().findAll();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryRalationName(java.lang.String)
	 */
	@Override
	public String queryRalationName(String ralationId) {
		String ralationName = "";
		if (StringUtils.isBlank(ralationId))
			return ralationName;
		DimFilterRalation r = this.getDimFilterRalationDAO().findById(
				ralationId);
		if (r != null)
			ralationName = r.getName();

		return ralationName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryFilerFormula()
	 */
	@Override
	public List<DimFilterFormula> queryFilerFormula(String type) {
		List<DimFilterFormula> list = new ArrayList<DimFilterFormula>();
		if (StringUtils.isBlank(type)) {
			list = this.getDimFilterFormulaDAO().findAll();
		} else {
			list = this.getDimFilterFormulaDAO().findLikeSpace(type);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryFormulaName(java.lang.String)
	 */
	@Override
	public String queryFormulaName(String id) {
		String name = "";
		if (StringUtils.isBlank(id))
			return name;
		DimFilterFormula r = this.getDimFilterFormulaDAO().findById(id);
		if (r != null)
			name = r.getName();

		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.birp.modules.smartQuery.query.service.SqQueryService#querySortType
	 * ()
	 */
	@Override
	public List<DimSqSort> querySortType() {
		List<DimSqSort> list = this.getDimSqSortDAO().findAll();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySortTypeName(java.lang.String)
	 */
	@Override
	public String querySortTypeName(String id) {
		String name = "";
		if (StringUtils.isBlank(id))
			return name;
		DimSqSort r = this.getDimSqSortDAO().findById(id);
		if (r != null)
			name = r.getName();

		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqFilterInfoTempList(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqFilterInfoVO> querySqFilterInfoList(String sqId, String isTemp) {
		List<SqFilterInfoVO> list = new ArrayList<SqFilterInfoVO>();
		/* 是否为临时数据 */
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		/* 传入table参数异常时返回空列表信息 */
		if (StringUtils.isBlank(sqId))
			return list;

		String sqFilterTable = "SqFilterInfo";
		if (temp)
			sqFilterTable += "Temp";
		String hql = "SELECT new com.aspire.birp.modules.smartQuery.query.vo.SqFilterInfoVO( t.id,t.filterType, t.relationId, t.relation,r.name, "
				+ " t.mpTableId, t.sqTable,s.mappingName,t.mpColumnId,t.sqColumn,m.mappingName, t.sqColumnType, "
				+ " t.formulaId,t.formula,f.name, t.value,t.monthCycType, t.monthCycValue, t.dateCycType, t.dateCycValue, t.sqId,t.sort) "
				+ " FROM "
				+ sqFilterTable
				+ " t, "
				+ " DmColumnMapping m,"
				+ " DmTableMapping s,"
				+ " DimFilterFormula f,"
				+ " DimFilterRalation r"
				+ " WHERE t.mpColumnId=m.id AND t.mpTableId=s.id AND t.relationId=r.id AND t.formulaId=f.id"
				+ " AND t.sqId=? " + " ORDER BY t.sort ";

		List<?> rows = this.getHibernateTemplate().find(hql, sqId);
		/* 转换列类型的转义 */
		if (rows != null && rows.size() > 0) {
			for (Object obj : rows) {
				SqFilterInfoVO vo = (SqFilterInfoVO) obj;
				/* 转换值描述信息 */
				vo.setValueDesc(this.filterValueDesc(vo));
				list.add(vo);
			}
		}
		return list;
	}

	/**
	 * 转换值描述信息
	 * 
	 * @param filter
	 * @return
	 * @author 张健雄
	 */
	private String filterValueDesc(SqFilterInfoVO filter) {
		StringBuffer desc = new StringBuffer();
		if (FilterType.cyc.toString().equals(filter.getFilterType())) {
			if (FilterMonthCycType.onMonth.toString().equals(
					filter.getMonthCycType())) {
				desc.append("（当月）");
			} else if (FilterMonthCycType.lastMonth.toString().equals(
					filter.getMonthCycType())) {
				desc.append("（上月）");
			} else {
				desc.append("（前" + filter.getMonthCycValue() + "月）");
			}
			if (FilterDateCycType.monthStart.toString().equals(
					filter.getDateCycType())) {
				desc.append("月初");
			} else if (FilterDateCycType.monthEnd.toString().equals(
					filter.getDateCycType())) {
				desc.append("月末");
			} else {
				desc.append("" + filter.getDateCycValue() + "号");
			}
			
		}else if (FilterType.list.toString().equals(filter.getFilterType())) {
			String value = filter.getValue();
			if(StringUtils.isNotBlank(value)){
				SqListFilterInfo info = getSqListFilterInfoDAO().findById(value);
				desc.append(info.getListName()+"[列表]");
			}			
		} else {
			desc.append(filter.getValue());
		}
		return desc.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * saveOrUpdateSqFilterInfo(java.lang.String, java.lang.String,
	 * java.util.Map)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean saveOrUpdateSqFilterInfo(SqFilterInfoVO filter, String isTemp) {
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));

		/* 查询数据创建信息 */
		String creator = UserUtils.getUser().getName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		String id = filter.getId();
		/* 获取维表编码信息 */
		DimFilterRalation ralation = this.getDimFilterRalationDAO().findById(
				filter.getRelationId());
		DimFilterFormula formula = this.getDimFilterFormulaDAO().findById(
				filter.getFormulaId());
		DmColumnMapping column = this.getDmColumnMappingDAO().findById(
				filter.getMpColumnId());

		if (temp) {
			SqFilterInfoTemp info = new SqFilterInfoTemp();
			info.setId(id);
			info.setFilterType(filter.getFilterType());
			info.setMonthCycType(filter.getMonthCycType());
			info.setMonthCycValue(filter.getMonthCycValue());
			info.setDateCycType(filter.getDateCycType());
			info.setDateCycValue(filter.getDateCycValue());
			info.setValue(filter.getValue());
			info.setSqId(filter.getSqId());
			info.setSort(filter.getSort());

			info.setRelationId(ralation.getId());
			info.setRelation(ralation.getCode());

			info.setMpTableId(column.getMpTableId());
			info.setSqTable(column.getMpTable());
			info.setMpColumnId(column.getId());
			info.setSqColumn(column.getColumnName());
			info.setSqColumnType(column.getColumnType());

			info.setFormulaId(formula.getId());
			info.setFormula(formula.getCode());

			info.setCreator(creator);
			info.setCreateTime(createTime);
			this.getHibernateTemplate().merge(info);
		} else {
			SqFilterInfo info = new SqFilterInfo();
			info.setId(id);
			info.setFilterType(filter.getFilterType());
			info.setMonthCycType(filter.getMonthCycType());
			info.setMonthCycValue(filter.getMonthCycValue());
			info.setDateCycType(filter.getDateCycType());
			info.setDateCycValue(filter.getDateCycValue());
			info.setValue(filter.getValue());
			info.setSqId(filter.getSqId());
			info.setSort(filter.getSort());

			info.setRelationId(ralation.getId());
			info.setRelation(ralation.getCode());

			info.setMpTableId(column.getMpTableId());
			info.setSqTable(column.getMpTable());
			info.setMpColumnId(column.getId());
			info.setSqColumn(column.getColumnName());
			info.setSqColumnType(column.getColumnType());

			info.setFormulaId(formula.getId());
			info.setFormula(formula.getCode());

			info.setCreator(creator);
			info.setCreateTime(createTime);
			this.getHibernateTemplate().merge(info);
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqSelectInfoList(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqTableInfoVO> querySqTableInfoList(String sqId, String isTemp) {
		List<SqTableInfoVO> list = new ArrayList<SqTableInfoVO>();
		/* 传入table参数异常时返回空列表信息 */
		if (StringUtils.isBlank(sqId))
			return list;
		boolean temp = SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp);
		String sqSelectTable = "com.aspire.birp.modules.smartQuery.query.entity.SqTableInfo";
		if (temp)
			sqSelectTable += "Temp";

		String hql = "SELECT new com.aspire.birp.modules.smartQuery.query.vo.SqTableInfoVO( ti.id, ti.mpTableId, ti.sqTable, tm.mappingName, ti.sqId) "
				+ "FROM "
				+ sqSelectTable
				+ " ti,DmTableMapping tm "
				+ "WHERE ti.sqId=? AND ti.mpTableId=tm.id "
				+ "ORDER BY ti.createTime";

		List<?> rows = this.getHibernateTemplate().find(hql,
				new Object[] { sqId });
		/* 转换列类型的转义 */
		if (rows != null && rows.size() > 0) {
			for (Object obj : rows) {
				SqTableInfoVO vo = (SqTableInfoVO) obj;
				list.add(vo);
			}
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqSelectInfoList(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqSelectInfoVO> querySqSelectInfoList(String sqId, String isTemp) {
		List<SqSelectInfoVO> list = new ArrayList<SqSelectInfoVO>();
		/* 传入table参数异常时返回空列表信息 */
		if (StringUtils.isBlank(sqId))
			return list;
		boolean temp = SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp);
		String sqSelectTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo";
		if (temp)
			sqSelectTable += "Temp";

		String hql = "SELECT new com.aspire.birp.modules.smartQuery.query.vo.SqSelectInfoVO(st.id, st.mpTableId, st.sqTable, tt.mappingName, "
				+ "st.mpColumnId, st.sqColumn, ct.mappingName, ct.columnType, st.sqId,st.sort) "
				+ "FROM "
				+ sqSelectTable
				+ " st,DmColumnMapping ct,DmTableMapping tt "
				+ "WHERE st.sqId=? AND st.mpColumnId=ct.id AND st.mpTableId=tt.id "
				+ "ORDER BY st.sort";

		List<?> rows = this.getHibernateTemplate().find(hql,
				new Object[] { sqId });
		/* 转换列类型的转义 */
		if (rows != null && rows.size() > 0) {
			for (Object obj : rows) {
				SqSelectInfoVO sm = (SqSelectInfoVO) obj;
				sm.setColumnTypeName(columnTypeMap.get(sm.getColumnType()));
				list.add(sm);
			}
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqSelectInfoListByType(java.lang.String, java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<DmColumnMappingVO> querySqSelectInfoListByType(String sqId,
			String isTemp, String[] types) {
		List<DmColumnMappingVO> list = new ArrayList<DmColumnMappingVO>();
		/* 传入table参数异常时返回空列表信息 */
		if (StringUtils.isBlank(sqId))
			return list;
		String ids = com.aspire.bi.common.util.StringUtils.splitStringForArray(
				types, ",");
		boolean temp = SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp);
		String sqSelectTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo";
		if (temp)
			sqSelectTable += "Temp";

		String hql = "SELECT new com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO(ct.id,ct.columnName,ct.mappingName,ct.columnType,ct.dataType, "
				+ "tt.id,tt.tableName,tt.mappingName,ct.sort)"
				+ "FROM "
				+ sqSelectTable
				+ " st,DmColumnMapping ct, DmTableMapping tt "
				+ "WHERE st.sqId=? AND st.mpColumnId=ct.id AND ct.mpTableId=tt.id AND ct.columnType in ("
				+ ids + ")" + "ORDER BY st.sort";

		List<?> rows = this.getHibernateTemplate().find(hql,
				new Object[] { sqId });
		/* 转换列类型的转义 */
		if (rows != null && rows.size() > 0) {
			for (Object obj : rows) {
				DmColumnMappingVO sm = (DmColumnMappingVO) obj;
				sm.setColumnTypeName(columnTypeMap.get(sm.getColumnType()));
				list.add(sm);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqSortInfoList(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqSortInfoVO> querySqSortInfoList(String sqId, String isTemp) {
		List<SqSortInfoVO> list = new ArrayList<SqSortInfoVO>();
		/* 是否为临时数据 */
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		/* 传入table参数异常时返回空列表信息 */
		if (StringUtils.isBlank(sqId))
			return list;

		String sqSortTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSortInfo";
		if (temp)
			sqSortTable += "Temp";
		String hql = "FROM " + sqSortTable + " WHERE sqId=? ORDER BY sort";

		List<?> rows = this.getHibernateTemplate().find(hql, sqId);
		/* 转换列类型的转义 */
		if (rows != null && rows.size() > 0) {
			for (Object obj : rows) {
				SqSortInfoVO vo = this.copyBeanPropertiesSort(obj, temp);
				list.add(vo);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * saveOrUpdateSqSortInfo(java.lang.String, java.lang.String,
	 * com.aspire.birp.modules.smartQuery.query.vo.SqSortInfoVO)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean saveOrUpdateSqSortInfo(String sqId, String isTemp,
			SqSortInfoVO data) {
		if (StringUtils.isBlank(sqId))
			return false;
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));

		String sqTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSortInfo";
		if (temp)
			sqTable += "Temp";
		/* 查询数据库里的最大序号及创建信息 */
		Long sort = this.getSqSelectInfoTempDAO().getMaxSort(sqTable) + 1;
		String creator = UserUtils.getUser().getName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		String id = data.getId();
		/* 获取维表编码信息 */
		DimSqSort sortObj = this.getDimSqSortDAO().findById(
				data.getSortTypeId());
		DmColumnMapping column = this.getDmColumnMappingDAO().findById(
				data.getMpColumnId());

		if (temp) {
			SqSortInfoTemp info = new SqSortInfoTemp();
			info.setId(id);
			info.setMpTableId(column.getMpTableId());
			info.setSqTable(column.getMpTable());
			info.setMpColumnId(column.getId());
			info.setSqColumn(column.getColumnName());
			info.setSortTypeId(sortObj.getId());
			info.setSortType(sortObj.getCode());
			info.setSqId(sqId);
			info.setCreator(creator);
			info.setCreateTime(createTime);
			info.setSort(sort);
			this.getHibernateTemplate().merge(info);
		} else {
			SqSortInfo info = new SqSortInfo();
			info.setId(id);
			info.setMpTableId(column.getMpTableId());
			info.setSqTable(column.getMpTable());
			info.setMpColumnId(column.getId());
			info.setSqColumn(column.getColumnName());
			info.setSortTypeId(sortObj.getId());
			info.setSortType(sortObj.getCode());
			info.setSqId(sqId);
			info.setCreator(creator);
			info.setCreateTime(createTime);
			info.setSort(sort);
			this.getHibernateTemplate().merge(info);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqFilterInfoById(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public SqFilterInfoVO querySqFilterInfoById(String id, String isTemp) {
		SqFilterInfoVO vo = new SqFilterInfoVO();
		if (StringUtils.isBlank(id))
			return vo;
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		String sqTable = "com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfo";
		if (temp)
			sqTable += "Temp";
		String hql = "FROM " + sqTable + " t WHERE t.id=?";
		List<?> rs = this.getHibernateTemplate().find(hql, id);
		if (rs == null || rs.size() == 0)
			return vo;
		vo = this.copyBeanPropertiesFilter(rs.get(0), temp);
		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * removeSqFilterInfo(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean removeSqFilterInfo(String id, String isTemp) {
		if (StringUtils.isBlank(id))
			return false;
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		String sqTable = "com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfo";
		if (temp)
			sqTable += "Temp";
		String hql = "DELETE FROM " + sqTable + " t WHERE t.id=?";
		this.getHibernateTemplate().bulkUpdate(hql, id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * removeSqSortInfo(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean removeSqSortInfo(String id, String isTemp) {
		if (StringUtils.isBlank(id))
			return false;
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		String sqTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSortInfo";
		if (temp)
			sqTable += "Temp";
		String hql = "DELETE FROM " + sqTable + " t WHERE t.id=?";
		this.getHibernateTemplate().bulkUpdate(hql, id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * smartQueryValid(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public boolean smartQueryValid(String sqId, String isTemp) {
		if (StringUtils.isBlank(sqId))
			return false;
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		String sqTable = "com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo";
		if (temp)
			sqTable += "Temp";
		String hql = "FROM " + sqTable + " WHERE sqId=?";
		List<?> list = this.getHibernateTemplate().find(hql, sqId);
		if (list == null || list.size() == 0)
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySQLbySqId(java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> querySqlDatabySqId(String sqId, String isTemp,
			int page, int pageSize) {
		/* 初始化返回参数 */
		Map<String, Object> rs = new HashMap<String, Object>();
		/* 获取当前user信息 */
		User user = UserUtils.getUser();
		String userAcc = user.getLoginName();

		if (StringUtils.isBlank(sqId))
			log.error("未正确保存查询SQL数据，智能查询失败！", new RuntimeException());

		SqQueryInfoVO query = this.querySqQueryInfoById(sqId, isTemp);
		String sqTables = query.getSqTable();
		String sqSelectSQL = query.getSqSelectSql();
		String sqFilterSQL = query.getSqFilterSql();
		String sqSortSQL = query.getSqSortSql();

		if (StringUtils.isBlank(sqSelectSQL) || StringUtils.isBlank(sqTables))
			log.error("未正确保存查询SQL数据，智能查询失败！", new RuntimeException());

		/* 查询显示列数据与过滤器参数数据 */
		List<SqSelectColumn> selects = this.getSqSelectColumnDAO().findBySqId(
				sqId);
		List<String> columnReader = new ArrayList<String>();
		for (SqSelectColumn select : selects) {
			columnReader.add(select.getSqColumnAlias());
		}
		Map<String, Object> parameters = this.getFilterParameters(sqId);
		/*更新数据周期列表*/
		updateStatTimeMap(sqId, isTemp);
		/* 执行SQL语句生成数据列表 */
		String sqSQL = this.packageSQL(sqTables, sqSelectSQL, sqFilterSQL,
				null, sqSortSQL, userAcc);
		rs = this.runSQLForGrid(sqSQL,parameters,columnReader,page,pageSize);

		return rs;
	}

	/**
	 * 通过查询ID获取数据列表的列定义信息
	 * 
	 * @param sqId 查询ID
	 * @param temp 是否为临时数据
	 * @return
	 * @throws RuntimeException
	 * @author 张健雄
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqDataGridObject> queryDataColumns(String sqId, String isTemp)
			throws RuntimeException {
		/* 列结构信息 */
		List<SqDataGridObject> columns = new ArrayList<SqDataGridObject>();
		/*查询对象信息*/
		SqQueryInfoVO query = this.querySqQueryInfoById(sqId, isTemp);
		/* 查询显示列信息 */
		List<SqSelectInfoVO> selects = this.querySqSelectInfoList(sqId, isTemp);
		if (selects == null || selects.size() == 0)
			log.error("找不到相关的查询显示列信息，智能查询失败！", new RuntimeException());

		/* 清除该查询的缓存显示列数据 */
		String deleteColumn = "DELETE FROM SqSelectColumn t WHERE t.sqId=?";
		this.getHibernateTemplate().bulkUpdate(deleteColumn, sqId);

		for (int i = 0; i < selects.size(); i++) {
			SqSelectInfoVO selectvo = (SqSelectInfoVO) selects.get(i);
			String table = selectvo.getSqTable();
			String column = selectvo.getSqColumn();
			String sqColumnAlias = getColumnAlias(table, column, INDEX_SELECT);
			String columnName = selectvo.getSqColumnName();
			String columntype = selectvo.getColumnType();
			String align = SmartQueryConstant.GRID_COLUMN_ALIGN_LEFT;
			if (ColumnType.n.toString().equals(columntype)) {
				/* 日期类型的列定义 */
				align = SmartQueryConstant.GRID_COLUMN_ALIGN_RIGHT;
			} else if (ColumnType.d.toString().equals(columntype)) {
				/* 数字类型的列定义 */
				align = SmartQueryConstant.GRID_COLUMN_ALIGN_CENTER;
			}
			/* 添加datagrid列定义 */
			columns.add(new SqDataGridObject(sqColumnAlias, columnName, align));

			/* 记录查询显示列缓存信息 */
			SqSelectColumn selectColumn = new SqSelectColumn();
			selectColumn.setId(UUID.randomUUID().toString());
			selectColumn.setSqTable(table);
			selectColumn.setSqColumn(column);
			selectColumn.setSqColumnAlias(sqColumnAlias);
			selectColumn.setSqColumnName(columnName);
			selectColumn.setSqId(sqId);
			selectColumn.setSort(selectvo.getSort());
			this.getHibernateTemplate().save(selectColumn);
		}
		
		/**记录操作日志信息-查询操作*/
		Map<String,String> lp = new HashMap<String, String>();
		lp.put("tables", query.getSqTable());
		markOptionLogger(FUNCTION, OptionType.SmartQuery_query, lp);
		
		return columns;
	}

	/**
	 * 通过查询ID动态生成SQL语句
	 * 
	 * @param sqId
	 *            查询ID
	 * @param temp
	 *            是否为临时数据
	 * @return
	 * @throws RuntimeException
	 * @author 张健雄
	 */
	private Map<String, String> createSQL(String sqId, String isTemp)
			throws RuntimeException {
		Map<String, String> rs = new HashMap<String, String>();

		/* 查询对应的表信息 */
		List<SqTableInfoVO> tables = this.querySqTableInfoList(sqId, isTemp);
		if (tables == null || tables.size() == 0)
			log.error("找不到相关的查询数据表信息，智能查询失败！", new RuntimeException());

		/* 查询显示列信息 */
		List<SqSelectInfoVO> selects = this.querySqSelectInfoList(sqId, isTemp);
		if (selects == null || selects.size() == 0)
			log.error("找不到相关的查询显示列信息，智能查询失败！", new RuntimeException());

		/* 查询过滤条件信息 */
		List<SqFilterInfoVO> filters = this.querySqFilterInfoList(sqId, isTemp);
		
		/* 查询排序方式信息 */
		List<SqSortInfoVO> sorts = this.querySqSortInfoList(sqId, isTemp);

		StringBuffer selectSQL = new StringBuffer();
		selectSQL.append("SELECT " + paginationDual + " ");
		for (int i = 0; i < selects.size(); i++) {
			SqSelectInfoVO selectvo = selects.get(i);
			String table = selectvo.getSqTable();
			String column = selectvo.getSqColumn();
			String sqColumnAlias = getColumnAlias(table, column, INDEX_SELECT);

			/* 添加SQL的select参数 */
			if (i == 0) {
				selectSQL.append(getSqlColumnStr(selectvo.getSqTable(),
						selectvo.getSqColumn()) + " AS " + sqColumnAlias);
			} else {
				selectSQL.append(","
						+ getSqlColumnStr(selectvo.getSqTable(),
								selectvo.getSqColumn()) + " AS "
						+ sqColumnAlias);
			}
		}
		
		StringBuffer fromSQL = new StringBuffer();
		String[] tableIds = new String[tables.size()];
		fromSQL.append(" FROM ");
		for (int i = 0; i < tables.size(); i++) {
			SqTableInfoVO tablevo = tables.get(i);
			tableIds[i] = tablevo.getMpTableId();
			/* 添加SQL的select参数 */
			if (i == 0) {
				fromSQL.append(getSqlTableStr(tablevo.getSqTable(), null));
			} else {
				fromSQL.append("," + getSqlTableStr(tablevo.getSqTable(), null));
			}
		}

		StringBuffer filterSQL = new StringBuffer();
		StringBuffer associationSQL = new StringBuffer();
		StringBuffer formulaSQL = new StringBuffer();
		if (tables.size() > 1) {
			/* 多表关联关系查询 */
			List<DmTableMappingAssociation> association = queryTableMappingAssociations(tableIds);
			if (association == null || association.size() == 0)
				log.error("找不到相关查询表的关联关系信息，智能查询失败！", new RuntimeException());
			for (int i = 0; i < association.size(); i++) {
				DmTableMappingAssociation ass = association.get(i);
				if (i == 0) {
					associationSQL.append(getSqlColumnStr(
							ass.getMpTableCode1(), ass.getMpColumnCode1())
							+ " = "
							+ getSqlColumnStr(ass.getMpTableCode2(),
									ass.getMpColumnCode2()));
				} else {
					associationSQL.append(getSqlColumnStr(
							" AND " + ass.getMpTableCode1(),
							ass.getMpColumnCode1())
							+ " = "
							+ getSqlColumnStr(ass.getMpTableCode2(),
									ass.getMpColumnCode2()));
				}
			}
		}
		/* 清除该查询的缓存参数数据 */
		String deleteParameter = "DELETE FROM SqFilterParameter t WHERE t.sqId=?";
		this.getHibernateTemplate().bulkUpdate(deleteParameter, sqId);

		if (filters != null && filters.size() > 0) {
			for (int i = 0; i < filters.size(); i++) {

				SqFilterInfoVO filtervo = filters.get(i);
				/* 参数别名定义 */
				String alias = getParamAlias(filtervo.getSqTable(),
						filtervo.getSqColumn());
				/* 添加SQL的filter参数 */
				if (i != 0)
					formulaSQL.append(filtervo.getRelation() + " ");
				
				
				String filterType = filtervo.getFilterType();
				
				String value = filtervo.getValue();
				
				if(FilterType.list.toString().equals(filterType)){
					/*列表过滤器特殊处理*/
					String formula = " EXISTS ";
					if(SmartQueryConstant.COLUMN_FORMULA_NOT_CONTAINS_CODE.equalsIgnoreCase(filtervo.getFormula()))
						formula = " NOT EXISTS ";
					
					formulaSQL.append( formula + " (SELECT info FROM sq_list_filter_desc WHERE list_id ="
							+ " :" + alias + " AND "+getSqlColumnStr(filtervo.getSqTable(),filtervo.getSqColumn())+" = info ) ");
				}else{
					formulaSQL.append(getSqlColumnStr(filtervo.getSqTable(),
							filtervo.getSqColumn())
							+ " "
							+ filtervo.getFormula()
							+ " :" + alias + " ");

					if (SmartQueryConstant.COLUMN_FORMULA_CONTAINS_CODE
							.equalsIgnoreCase(filtervo.getFormula())
							|| SmartQueryConstant.COLUMN_FORMULA_NOT_CONTAINS_CODE
									.equalsIgnoreCase(filtervo.getFormula()))
						value = "%" + value + "%";
				}			

				/* 为该查询缓存参数保存 */
				SqFilterParameter parameter = new SqFilterParameter();
				parameter.setId(UUID.randomUUID().toString());
				parameter.setSqId(sqId);
				parameter.setSqTable(filtervo.getSqTable());
				parameter.setSqColumn(filtervo.getSqColumn());
				parameter.setParameterKey(alias);
				parameter.setParameterType(filtervo.getFilterType());
				parameter.setValue(value);
				parameter.setValueType(filtervo.getSqColumnType());
				parameter.setMonthCycType(filtervo.getMonthCycType());
				parameter.setMonthCycValue(filtervo.getMonthCycValue());
				parameter.setDateCycType(filtervo.getDateCycType());
				parameter.setDateCycValue(filtervo.getDateCycValue());
				this.getHibernateTemplate().save(parameter);
			}
		}

		if (StringUtils.isNotBlank(associationSQL)
				&& StringUtils.isNotBlank(formulaSQL)) {
			filterSQL.append(" WHERE " + associationSQL + " AND " + formulaSQL);
		} else if (StringUtils.isBlank(associationSQL)
				&& StringUtils.isNotBlank(formulaSQL)) {
			filterSQL.append(" WHERE " + formulaSQL);
		} else if (StringUtils.isNotBlank(associationSQL)
				&& StringUtils.isBlank(formulaSQL)) {
			filterSQL.append(" WHERE " + associationSQL);
		}

		StringBuffer sortSQL = new StringBuffer();
		if (sorts != null && sorts.size() > 0) {
			sortSQL.append(" ORDER BY ");
			for (int i = 0; i < sorts.size(); i++) {
				SqSortInfoVO sortvo = sorts.get(i);
				/* 添加SQL的排序参数 */
				if (i == 0) {
					sortSQL.append(getSqlColumnStr(sortvo.getSqTable(),
							sortvo.getSqColumn())
							+ " " + sortvo.getSortType());
				} else {
					sortSQL.append(","
							+ getSqlColumnStr(sortvo.getSqTable(),
									sortvo.getSqColumn()) + " "
							+ sortvo.getSortType());
				}
			}
		}
		
		
		

		rs.put("countSQL", countSQL);
		rs.put("selectSQL", selectSQL.toString());
		rs.put("fromSQL", fromSQL.toString());
		rs.put("filterSQL", filterSQL.toString());
		rs.put("sortSQL", sortSQL.toString());

		return rs;
	}

	/**
	 * 通过查询ID创建所需的数据文件
	 * 
	 * @param SqId
	 *            查询ID
	 * @return 返回数据文件信息
	 * @author 张健雄
	 */
	@Override
	public Map<String, String> createFileByTaskId(String taskId) {
		return this.createFileBySqId(taskId,
				SmartQueryConstant.COMMON_FLAG_FALSE, null, null);
	}

	/**
	 * 通过查询ID创建所需的数据文件
	 * 
	 * @param SqId
	 *            查询ID
	 * @param isTemp
	 *            是否为临时数据
	 * @param fileName
	 *            创建文件名
	 * @param userAcc
	 *            创建用户账号（用于控制权限）
	 * @return 返回数据文件信息
	 * @author 张健雄
	 */
	@Override
	public Map<String, String> createFileBySqId(String sqId, String isTemp,
			String fileName, String userAcc) {

		SqQueryInfoVO query = this.querySqQueryInfoById(sqId, isTemp);
		String tables = query.getSqTable();
		String selectSQL = query.getSqSelectSql();
		String filterSQL = query.getSqFilterSql();
		String sortSQL = query.getSqSortSql();
		if (StringUtils.isBlank(userAcc))
			userAcc = query.getCreatorAcc();
		if (StringUtils.isBlank(fileName))
			fileName = query.getFileName();
		/*更新数据周期列表*/
		updateStatTimeMap(sqId, isTemp);
		/* 获取SQL信息 */
		String sql = this.packageSQL(tables, selectSQL, filterSQL, null,
				sortSQL, userAcc);
		/* 获取SQL参数信息 */
		Map<String, Object> parameters = this.getFilterParameters(sqId);
		List<SqSelectColumn> selects = this.getSqSelectColumnDAO().findBySqId(
				sqId);
		LinkedHashMap<String, String> mapper = new LinkedHashMap<String, String>();
		for (SqSelectColumn select : selects) {
			mapper.put(select.getSqColumnAlias(), select.getSqColumnName());
		}

		return this.createFileBySql(sql, parameters, mapper, fileName, userAcc);
	}

	/**
	 * 通过模块SQL参数组装完整的SQL信息 （包含权限信息和分组信息）
	 * 
	 * @param sqTables 数据表集合 table1,table2,table3
	 * @param selectSQL 显示列SQL
	 * @param filterSQL 过滤SQL
	 * @param groupSQL 分组SQL
	 * @param sortSQL 排序SQL
	 * @param userAcc 用户账号（用于获取用户权限信息）
	 * @return
	 * @author 张健雄
	 */
	private String packageSQL(String sqTables, String selectSQL,
			String filterSQL, String groupSQL, String sortSQL, String userAcc) {

		StringBuffer sql = new StringBuffer();
		String[] tables = StringUtils.split(sqTables, ",");
		if (StringUtils.isBlank(selectSQL) || tables.length == 0)
			log.error("智能查询组装SQL失败，无法获取足够的SQL信息！", new RuntimeException(
					"智能查询组装SQL失败，无法获取足够的SQL信息！"));		
		
		/* 获取当前用户的权限信息 */
		StringBuffer permission = new StringBuffer();
		permission.append(" FROM ");
		for (int i = 0; i < tables.length; i++) {
			String tableName = tables[i];
			DmTableMapping table = this.getDmTableMappingDAO().findByTableName(tableName).get(0);
			String isPerimssion = table.getIsPerimssion();
			String sqlTable = "";
			if (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isPerimssion)) {
				sqlTable = getSqlTableStr(tableName, userAcc);
			} else {
				sqlTable = getSqlTableStr(tableName, null);
			}
			if (i == 0) {
				permission.append(sqlTable);
			} else {
				permission.append("," + sqlTable + " ");
			}
		}

		sql.append(selectSQL + permission.toString());

		if (StringUtils.isNotBlank(filterSQL))
			sql.append(filterSQL);

		if (StringUtils.isNotBlank(groupSQL))
			sql.append(groupSQL);

		if (StringUtils.isNotBlank(sortSQL))
			sql.append(sortSQL);

		return sql.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryDataBySqid(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> queryExcelInfo(String sqId, String isTemp) {
		if (StringUtils.isBlank(sqId))
			log.error("智能查询ID为空，无法找到相关的查询对象信息，数据导出失败！", new RuntimeException());

		SqQueryInfoVO vo = this.querySqQueryInfoById(sqId, isTemp);
		String selectSQL = vo.getSqSelectSql();
		String filterSQL = vo.getSqFilterSql();
		String sortSQL = vo.getSqSortSql();
		String title = vo.getName();

		/* 用于保存Excel的表头信息 */
		Map<String, Column> columns = new HashMap<String, Column>();
		List<String> columnReader = new ArrayList<String>();
		List<SqSelectColumn> selects = this.getSqSelectColumnDAO().findBySqId(
				sqId);
		for (int i = 0; i < selects.size(); i++) {
			SqSelectColumn column = selects.get(i);
			columnReader.add(column.getSqColumnAlias());
			columns.put(
					column.getSqColumnAlias(),
					new Column(i, column.getSqColumnAlias(), column
							.getSqColumnName()));
		}

		/* 查询过滤器参数数据 */
		Map<String, Object> parameters = this.getFilterParameters(sqId);
		
		/* 获取当前user信息 */
		User user = UserUtils.getUser();
		String userAcc = user.getLoginName();
		/*更新数据周期列表*/
		updateStatTimeMap(sqId, isTemp);
		/* 生成导出数据信息 */
		String sql = this.packageSQL(vo.getSqTable(), selectSQL, filterSQL,
				null, sortSQL, userAcc);
		List<Map<String, Object>> rows = this.runSQL(sql, parameters,
				columnReader);

		/* 封装Eexcel导出数据 */
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("title", title);
		info.put("mapper", columns);
		info.put("rs", rows);
		return info;
	}
	
	/**
	 * 更新分区过滤器数据表集合
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @author 张健雄
	 */
	private void updateStatTimeMap(String sqId,String isTemp){
		statTimeMap = new HashMap<String,String>();
		/*获取全部数据表的默认数据周期*/
		String sql = "SELECT table_name,stat_time FROM TA_T_LAST_STAT_TIME ";
		List<Object[]> rs = this.excuteSQLQuery(sql);
		
		if(rs != null && !rs.isEmpty()){
			for (Object[] objects : rs) {
				String tableName = String.valueOf(objects[0]);
				String statTime = String.valueOf(objects[1]);
				statTimeMap.put(tableName,statTime);							
			}
		}	
		
		List<SqFilterInfoVO> filters = this.querySqFilterInfoList(sqId, isTemp);
		for (SqFilterInfoVO filter : filters) {
			String table = filter.getSqTable();
			String column = filter.getSqColumn();
			if(SmartQueryConstant.COLUMN_DATE_NAME.equalsIgnoreCase(column)){
				if(statTimeMap.containsKey(table))
					statTimeMap.remove(table);
			}
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqQueryInfoById(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public SqQueryInfoVO querySqQueryInfoById(String sqId, String isTemp) {
		if (StringUtils.isBlank(sqId))
			log.error("智能查询ID为空，无法找到相关的查询对象信息，智能查询失败！", new RuntimeException());
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		String sqQueryTable = "com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfo";
		if (temp)
			sqQueryTable += "Temp";
		String hql = "FROM " + sqQueryTable + " WHERE id=?";
		List<?> querys = this.getHibernateTemplate().find(hql, sqId);
		if (querys == null || querys.size() == 0)
			log.error("找不到相关的查询信息，智能查询失败！", new RuntimeException());

		SqQueryInfoVO vo = this.copyBeanPropertiesQuery(querys.get(0), temp);

		return vo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqQueryInfoById(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public SqQueryInfoVO queryQueryConfigById(String sqId) {
		SqQueryInfoVO vo = this.querySqQueryInfoById(sqId,
				SmartQueryConstant.COMMON_FLAG_FALSE);
		/* SQL语句不应用于前端展示 */
		vo.setSqSelectSql("");
		vo.setSqFromSql("");
		vo.setSqFilterSql("");
		vo.setSqSortSql("");

		String ids = com.aspire.bi.common.util.StringUtils.splitStringForIds(vo
				.getMpTableId());

		String hql = "FROM DmTableMapping t WHERE t.id IN ("
				+ ids + ")";

		List<?> tms = this.getHibernateTemplate().find(hql);
		List<String> tableNames = new ArrayList<String>();
		/* 前端展示表名列表 */
		for (int i = 0; i < tms.size(); i++) {
			DmTableMapping tm = (DmTableMapping) tms.get(i);
			tableNames.add(tm.getMappingName());
		}
		vo.setTableNames(tableNames);
		return vo;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * updateSmartQuerySql(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean updateSmartQuerySql(String sqId, String isTemp) {
		if (StringUtils.isBlank(sqId))
			return false;
		boolean temp = (SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp));
		/* 生成动态SQL语句，包含数据SQL：SQL和行数SQL：countSQL，显示列的列表：columns */
		Map<String, String> sqlinfo = this.createSQL(sqId, isTemp);
		if (temp) {
			SqQueryInfoTemp em = this.getSqQueryInfoTempDAO().findById(sqId);
			em.setSqSelectSql(sqlinfo.get("selectSQL"));
			em.setSqFromSql(sqlinfo.get("fromSQL"));
			em.setSqFilterSql(sqlinfo.get("filterSQL"));
			em.setSqSortSql(sqlinfo.get("sortSQL"));
			this.getHibernateTemplate().merge(em);
		} else {
			SqQueryInfo em = this.getSqQueryInfoDAO().findById(sqId);
			em.setSqSelectSql(sqlinfo.get("selectSQL"));
			em.setSqFromSql(sqlinfo.get("fromSQL"));
			em.setSqFilterSql(sqlinfo.get("filterSQL"));
			em.setSqSortSql(sqlinfo.get("sortSQL"));
			this.getHibernateTemplate().merge(em);
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * saveOrUpdateQuery
	 * (com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean saveOrUpdateQuery(SqQueryInfoVO query) {
		boolean flag = true;
		String isTemp = query.getIsTemp();
		/* 判断当前任务是否为临时任务 */
		boolean temp = SmartQueryConstant.COMMON_FLAG_TRUE.equals(isTemp);
		/* 获取当前用户信息 */
		User user = UserUtils.getUser();
		String creatorId = user.getId();
		String creator = user.getName();
		String creatorAcc = user.getLoginName();
		Timestamp createTime = new Timestamp(new Date().getTime());
		String id = query.getId();

		/**记录操作日志信息-自动任务创建或编辑操作*/
		Map<String,String> lp = new HashMap<String, String>();
		lp.put("tables", query.getSqTable());
		
		if (temp) {
			/* 获取创建信息 */
			Long sort = this.getSqSelectInfoTempDAO().getMaxSort("SqQueryInfo") + 1;

			/* 如果为临时数据，则保存对应的配置项 */
			SqQueryInfoTemp tp = this.getSqQueryInfoTempDAO().findById(id);

			/* 如果保留自动任务时，并没有对数据进行生成操作时，需要自动在后台生成动态SQL信息 */
			if (SmartQueryConstant.QUERY_TASK_TYPE_AUTO.equalsIgnoreCase(query
					.getConfigType())
					&& (StringUtils.isBlank(tp.getSqSelectSql())
							|| StringUtils.isBlank(tp.getSqFromSql()) || StringUtils
								.isBlank(tp.getSqColumns()))) {
				Map<String, String> sqlinfo = this.createSQL(id, isTemp);

				tp.setSqSelectSql(sqlinfo.get("selectSQL"));
				tp.setSqFromSql(sqlinfo.get("fromSQL"));
				tp.setSqFilterSql(sqlinfo.get("filterSQL"));
				tp.setSqSortSql(sqlinfo.get("sortSQL"));

			}

			SqQueryInfo entity = new SqQueryInfo();
			/* 正式数据与临时数据使用相同的ID值 */
			entity.setId(id);
			this.updatePropertiesQuery(query, entity);
			entity.setCreatorId(creatorId);
			entity.setCreator(creator);
			entity.setCreatorAcc(creatorAcc);
			entity.setCreateTime(createTime);
			entity.setSqSelectSql(tp.getSqSelectSql());
			entity.setSqFromSql(tp.getSqFromSql());
			entity.setSqFilterSql(tp.getSqFilterSql());
			entity.setSqSortSql(tp.getSqSortSql());
			entity.setSqColumns(tp.getSqColumns());
			entity.setSort(sort);
			this.getHibernateTemplate().save(entity);

			/* 将关联信息表同步至正式表中 */

			/* selecter */
			String hql = "insert into SqSelectInfo(id,mpTableId,sqTable,mpColumnId,sqColumn,sqId,creator,createTime,sort) "
					+ "select id,mpTableId,sqTable,mpColumnId,sqColumn,sqId,creator,createTime,sort from SqSelectInfoTemp where sqId=?";
			this.getHibernateTemplate().bulkUpdate(hql, id);

			/* tables */
			hql = "insert into SqTableInfo(id, mpTableId, sqTable,sqId, creator, createTime, sort) "
					+ "select id, mpTableId, sqTable,sqId, creator, createTime, sort from SqTableInfoTemp where sqId=?";
			this.getHibernateTemplate().bulkUpdate(hql, id);

			/* filter */
			hql = "insert into SqFilterInfo(id,filterType,relationId,relation,mpTableId,sqTable,mpColumnId,sqColumn,sqColumnType,formulaId,formula,value,"
					+ "monthCycType,monthCycValue,dateCycType,dateCycValue,sqId,creator,createTime,sort) "
					+ "select id,filterType,relationId,relation,mpTableId,sqTable,mpColumnId,sqColumn,sqColumnType,formulaId,formula,value,"
					+ "monthCycType,monthCycValue,dateCycType,dateCycValue,sqId,creator,createTime,sort from SqFilterInfoTemp where sqId=?";
			this.getHibernateTemplate().bulkUpdate(hql, id);
			/* sorter */
			hql = "insert into SqSortInfo(id,mpTableId,sqTable,mpColumnId,sqColumn,sortTypeId,sortType,sqId,creator,createTime,sort) "
					+ "select id,mpTableId,sqTable,mpColumnId,sqColumn,sortTypeId,sortType,sqId,creator,createTime,sort from SqSortInfoTemp where sqId=?";
			this.getHibernateTemplate().bulkUpdate(hql, id);

			/* 如果为自动任务，则自动创建任务数据 */
			if (SmartQueryConstant.QUERY_TASK_TYPE_AUTO.equalsIgnoreCase(entity
					.getConfigType())) {
				SqTaskInfo task = new SqTaskInfo();
				updatePropertiesTask(entity, task);
				/* 对象创建信息 */
				task.setTaskId(query.getId());
				task.setTaskAutherId(creatorId);
				task.setTaskAutherAcc(creatorAcc);
				task.setTaskAuther(creator);
				task.setTaskCommitTime(createTime);
				task.setTaskStatus(SmartQueryConstant.COMMON_FLAG_TRUE);
				this.getHibernateTemplate().save(task);
				/*创建自动任务日志*/
				lp.put("cyc", entity.getCycType());
				lp.put("cycLen", String.valueOf(entity.getCycLen()));
				markOptionLogger(FUNCTION, OptionType.SmartQuery_taskcreator, lp);
			}

		} else {
			/* 如果不为临时数据，则更新对应查询配置项的信息 */
			SqQueryInfo entity = this.getSqQueryInfoDAO().findById(id);
			if (entity != null) {
				updatePropertiesQuery(query, entity);
				/* 如果保留自动任务时，并没有对数据进行生成操作时，需要自动在后台生成动态SQL信息 */
				if (SmartQueryConstant.QUERY_TASK_TYPE_AUTO
						.equalsIgnoreCase(query.getConfigType())
						&& (StringUtils.isBlank(entity.getSqSelectSql())
								|| StringUtils.isBlank(entity.getSqFromSql()) || StringUtils
									.isBlank(entity.getSqColumns()))) {
					Map<String, String> sqlinfo = this.createSQL(id, isTemp);

					entity.setSqSelectSql(sqlinfo.get("selectSQL"));
					entity.setSqFromSql(sqlinfo.get("fromSQL"));
					entity.setSqFilterSql(sqlinfo.get("filterSQL"));
					entity.setSqSortSql(sqlinfo.get("sortSQL"));

				}
				this.getHibernateTemplate().merge(entity);
			}
			if (SmartQueryConstant.QUERY_TASK_TYPE_AUTO.equalsIgnoreCase(query
					.getConfigType())) {
				/* 更新任务列表数据 */
				SqTaskInfo task = this.getSqTaskInfoDAO().findById(id);
				if (task != null) {
					updatePropertiesTask(entity, task);
					this.getHibernateTemplate().merge(task);
					sqTaskCommonService.deleteExceptionStatusInfo(id);
					/*编辑自动任务日志*/
					lp.put("task", query.getName());
					markOptionLogger(FUNCTION, OptionType.Task_edit, lp);

				} else {
					/* 如果查询没有任务信息，则重新创建一个相关任务 */
					task = new SqTaskInfo();
					updatePropertiesTask(entity, task);
					/* 对象创建信息 */
					task.setTaskId(query.getId());
					task.setTaskAutherId(creatorId);
					task.setTaskAutherAcc(creatorAcc);
					task.setTaskAuther(creator);
					task.setTaskCommitTime(createTime);
					task.setTaskStatus(SmartQueryConstant.COMMON_FLAG_TRUE);
					this.getHibernateTemplate().save(task);
					/*创建自动任务日志*/
					lp.put("cyc", entity.getCycType());
					lp.put("cycLen", String.valueOf(entity.getCycLen()));
					markOptionLogger(FUNCTION, OptionType.SmartQuery_taskcreator, lp);
				}
			}
		}
		return flag;
	}

	/**
	 * 通过查询对象信息更新任务列表数据
	 * 
	 * @param query
	 * @param task
	 * @author 张健雄
	 */
	private void updatePropertiesTask(SqQueryInfo query, SqTaskInfo task) {

		task.setRqSourceTb(query.getSqTable());
		task.setCatalogId(query.getPersonalCatalogId());
		task.setCycTyp(query.getCycType());
		task.setCycLen(query.getCycLen());
		task.setValidTime(query.getValidDate());
		task.setInvalidTime(query.getInvalidDate());

		task.setDataStoreDt(query.getDataStoreDate());
		task.setFileName(query.getFileName());
		task.setSqSelectSql(query.getSqSelectSql());
		task.setSqFilterSql(query.getSqFilterSql());
		task.setSqSortSql(query.getSqSortSql());
		task.setSqFromSql(query.getSqFromSql());
		task.setSqColumns(query.getSqColumns());
		task.setDataCyc(query.getValidDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * validQueryName(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validQueryName(String sqId, String name) {
		if (StringUtils.isBlank(sqId))
			return false;
		String hql = "FROM SqQueryInfo WHERE name=? AND id<>?";
		List<?> rows = this.getHibernateTemplate().find(hql,
				new Object[] { name, sqId });
		if (rows != null && rows.size() > 0)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqQueryInfoList(int, int)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> querySqQueryInfoList(int page, int pageSize) {
		Map<String, Object> rs = new HashMap<String, Object>();
		List<SqQueryInfoVO> rows = new ArrayList<SqQueryInfoVO>();
		String creator = UserUtils.getUser().getName();
		String hql = "FROM SqQueryInfo t WHERE t.creator=? ORDER BY t.sort";
		List<?> list = this.excuteQuery(hql, new Object[] { creator }, page,
				pageSize);
		for (Object object : list) {
			SqQueryInfo info = (SqQueryInfo) object;
			SqQueryInfoVO vo = this.copyBeanPropertiesQuery(info, false);
			rows.add(vo);
		}
		hql = "SELECT COUNT(t.id) " + hql;
		List<?> c = this.getHibernateTemplate().find(hql, creator);
		Long total = Long.valueOf(String.valueOf(c.get(0)));
		rs.put("total", total);
		rs.put("rows", rows);
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqQueryAutoList()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqQueryInfoVO> querySqQueryAutoList() {
		List<SqQueryInfoVO> list = new ArrayList<SqQueryInfoVO>();
		String creator = UserUtils.getUser().getId();
		String hql = "FROM SqQueryInfo t WHERE t.creatorId=? AND t.configType=? ORDER BY t.sort";
		List<?> rows = this.getHibernateTemplate()
				.find(hql,
						new Object[] { creator,
								SmartQueryConstant.QUERY_TASK_TYPE_AUTO });
		if (rows != null && rows.size() > 0) {
			for (int i = 0; i < rows.size(); i++) {
				SqQueryInfo info = (SqQueryInfo) rows.get(i);
				SqQueryInfoVO vo = this.copyBeanPropertiesQuery(info, false);
				list.add(vo);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * querySqQueryAutoList()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<SqQueryInfoVO> querySqQueryInfoList() {
		List<SqQueryInfoVO> list = new ArrayList<SqQueryInfoVO>();
		String creator = UserUtils.getUser().getId();
		List<SqQueryInfo> rows = this.getSqQueryInfoDAO().findByCreatorId(
				creator);
		for (SqQueryInfo info : rows) {
			SqQueryInfoVO vo = this.copyBeanPropertiesQuery(info, false);
			list.add(vo);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.birp.modules.smartQuery.query.service.SqQueryService#deleteQuery
	 * (java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteQuery(String sqId) {
		if (StringUtils.isBlank(sqId))
			return false;
		SqQueryInfo rs = this.getSqQueryInfoDAO().findById(sqId);
		if (rs == null)
			return false;
		this.getHibernateTemplate().delete(rs);
		String tableHql = "DELETE FROM SqTableInfo WHERE sqId=?";
		String selectHql = "DELETE FROM SqSelectInfo WHERE sqId=?";
		String filterHql = "DELETE FROM SqFilterInfo WHERE sqId=?";
		String sortHql = "DELETE FROM SqSortInfo WHERE sqId=?";
		String parameterHql = "DELETE FROM SqFilterParameter t t.sqId=?";

		/* 批量删除查询对象 */
		this.getHibernateTemplate().bulkUpdate(tableHql,sqId);
		this.getHibernateTemplate().bulkUpdate(selectHql,sqId);
		this.getHibernateTemplate().bulkUpdate(filterHql,sqId);
		this.getHibernateTemplate().bulkUpdate(sortHql,sqId);
		this.getHibernateTemplate().bulkUpdate(parameterHql,sqId);
		
		return true;
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * cleanTemporaryQuery()
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cleanTemporaryQuery() {
		String queryHql = "DELETE FROM SqQueryInfoTemp";
		String tableHql = "DELETE FROM SqTableInfoTemp";
		String selectHql = "DELETE FROM SqSelectInfoTemp";
		String filterHql = "DELETE FROM SqFilterInfoTemp";
		String sortHql = "DELETE FROM SqSortInfoTemp";
		
		/* 清除该查询的缓存参数数据 */
		String deleteParameter = "DELETE FROM SqFilterParameter t WHERE EXISTS (SELECT id FROM SqQueryInfoTemp WHERE t.sqId=id)";
		this.getHibernateTemplate().bulkUpdate(deleteParameter);

		/* 批量删除查询对象 */
		this.getHibernateTemplate().bulkUpdate(queryHql);
		this.getHibernateTemplate().bulkUpdate(tableHql);
		this.getHibernateTemplate().bulkUpdate(selectHql);
		this.getHibernateTemplate().bulkUpdate(filterHql);
		this.getHibernateTemplate().bulkUpdate(sortHql);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * cleanTemporaryQuery()
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void markLostfiles(){
		/*对已过期的数据文件不作校验处理*/
		String hql = "FROM SqFileDataInfo t WHERE t.status<>?";
		List<?> list = this.getHibernateTemplate().find(hql,SmartQueryConstant.FILE_STATUS_EXPIRE);
		if(list != null && !list.isEmpty()){
			for (Object object : list) {
				SqFileDataInfo file = (SqFileDataInfo) object;
				String filepath = file.getFilePath();
				File f =new File(filepath);    
				/*获取文件过期限期*/
				Timestamp store = file.getDataStoreDate();
				Timestamp now = new Timestamp(System.currentTimeMillis());
				if(store.before(now)){
					/*如果数据文件已过期，则删除数据文件并标识已过期状态*/
					if(f.exists()){
						String path = filepath.substring(0, filepath.lastIndexOf(File.separator));
						String fileName = filepath.substring(filepath.lastIndexOf(File.separator)+1);
						CSVUtils.deleteFile(path, fileName);
					}
					file.setStatus(SmartQueryConstant.FILE_STATUS_EXPIRE);
					this.getHibernateTemplate().merge(file);
				}else if(!f.exists()){
					/*如果数据文件实体不存在，则标识为已丢失状态*/
					file.setStatus(SmartQueryConstant.FILE_STATUS_LOST);
					this.getHibernateTemplate().merge(file);
				}
			}
		}		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryGroupReport(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<GroupDataVo> queryGroupReport(String sqId, String isTemp,
			String name, String value, String group) {
		/* 获取智能查询对象信息 */
		boolean isGroup = StringUtils.isNotBlank(group);
		SqQueryInfoVO query = this.querySqQueryInfoById(sqId, isTemp);
		String filterSQL = query.getSqFilterSql();
		String sortSQL = query.getSqSortSql();

		/* 获取column信息 */
		DmColumnMapping nameCol = this.getDmColumnMappingDAO().findById(name);
		DmColumnMapping groupCol = this.getDmColumnMappingDAO().findById(group);
		DmColumnMapping valueCol = this.getDmColumnMappingDAO().findById(value);

		StringBuffer selectSql = new StringBuffer();
		selectSql.append("SELECT ");
		selectSql.append(getSqlColumnStr(nameCol.getMpTable(),
				nameCol.getColumnName())
				+ " AS rp_name ");
		selectSql.append(",SUM("
				+ getSqlColumnStr(valueCol.getMpTable(),
						valueCol.getColumnName()) + ") AS rp_value ");
		if (isGroup)
			selectSql.append(","
					+ getSqlColumnStr(groupCol.getMpTable(),
							groupCol.getColumnName()) + " AS rp_group ");
		/* 加入group分组函数 */
		String groupSQL = "GROUP BY "
				+ getSqlColumnStr(nameCol.getMpTable(), nameCol.getColumnName());
		if (isGroup)
			groupSQL += ","
					+ getSqlColumnStr(groupCol.getMpTable(),
							groupCol.getColumnName());
		
		/* 获取当前user信息 */
		User user = UserUtils.getUser();
		String userAcc = user.getLoginName();
		/*更新数据周期列表*/
		updateStatTimeMap(sqId, isTemp);
		String sql = this.packageSQL(query.getSqTable(), selectSql.toString(),
				filterSQL, groupSQL, sortSQL, userAcc);
		List<Object[]> rows = this.excuteSQLQuery(sql);
		List<GroupDataVo> groupvos = new ArrayList<GroupDataVo>();
		if (rows != null && rows.size() > 0) {
			for (Object[] objects : rows) {
				if (isGroup) {
					groupvos.add(new GroupDataVo(objects[0], objects[2],
							objects[1]));
				} else {
					groupvos.add(new GroupDataVo(objects[0], objects[1]));
				}

			}
		}
		return groupvos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * queryPieReport(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<PieDataVo> queryPieReport(String sqId, String isTemp,
			String name, String value) {
		/* 获取智能查询对象信息 */
		SqQueryInfoVO query = this.querySqQueryInfoById(sqId, isTemp);
		String filterSQL = query.getSqFilterSql();
		String sortSQL = query.getSqSortSql();

		/* 获取column信息 */
		DmColumnMapping nameCol = this.getDmColumnMappingDAO().findById(name);
		DmColumnMapping valueCol = this.getDmColumnMappingDAO().findById(value);

		StringBuffer selectSql = new StringBuffer();
		selectSql.append("SELECT ");
		selectSql.append(getSqlColumnStr(nameCol.getMpTable(),
				nameCol.getColumnName())
				+ " AS rp_name, ");
		selectSql.append("SUM("
				+ getSqlColumnStr(valueCol.getMpTable(),
						valueCol.getColumnName()) + ") AS rp_value ");
		/* 加入group分组函数 */
		String groupSQL = "GROUP BY "
				+ getSqlColumnStr(nameCol.getMpTable(), nameCol.getColumnName());
		
		/* 获取当前user信息 */
		User user = UserUtils.getUser();
		String userAcc = user.getLoginName();
		/*更新数据周期列表*/
		updateStatTimeMap(sqId, isTemp);
		String sql = this.packageSQL(query.getSqTable(), selectSql.toString(),
				filterSQL, groupSQL, sortSQL, userAcc);
		List<Object[]> rows = this.excuteSQLQuery(sql);
		List<PieDataVo> pievos = new ArrayList<PieDataVo>();
		if (rows != null && rows.size() > 0) {
			for (Object[] objects : rows) {
				pievos.add(new PieDataVo(objects[0], objects[1]));
			}
		}
		return pievos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * saveTempDatabySqId(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, String> saveTempDatabySqId(String sqId, String isTemp) {
		/* 获取文件名称 */
		SqQueryInfoVO query = querySqQueryInfoById(sqId, isTemp);
		User user = UserUtils.getUser();
		String userAcc = user.getLoginName();
		String selectSQL = query.getSqSelectSql();
		String filterSQL = query.getSqFilterSql();
		String sortSQL = query.getSqSortSql();
		/*更新数据周期列表*/
		updateStatTimeMap(sqId, isTemp);
		/* sql语句整合 */
		String sql = packageSQL(query.getSqTable(), selectSQL, filterSQL, null,
				sortSQL, userAcc);

		/* 查询显示列数据与过滤器参数数据 */
		List<SqSelectColumn> selects = this.getSqSelectColumnDAO().findBySqId(sqId);
		LinkedHashMap<String, String> mapper = new LinkedHashMap<String, String>();
		for (SqSelectColumn select : selects) {
			mapper.put(select.getSqColumnAlias(), select.getSqColumnName());
		}
		Map<String, Object> parameters = this.getFilterParameters(sqId);

		String filePath = "";
		try {
			filePath = createTempFileBySql(sql, parameters, mapper);
		} catch (Exception e) {
			log.error("创建临时csv文件失败", e);
		}
		Map<String, String> info = new HashMap<String, String>();
		info.put("name", query.getName() + ".csv");
		info.put("path", filePath);

		/**记录操作日志信息-下载操作*/
		Map<String,String> lp = new HashMap<String, String>();
		lp.put("tables", query.getSqTable());
		markOptionLogger(FUNCTION, OptionType.SmartQuery_download, lp);
		
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aspire.birp.modules.smartQuery.query.service.SqQueryService#isAssociation
	 * (java.lang.String)
	 */
	@Override
	public Map<String, Object> isAssociation(String tableids) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (StringUtils.isBlank(tableids)) {
			rs.put("flag", false);
			return rs;
		}
		String[] tables = StringUtils.split(tableids, ",");
		if(tables.length == 1){
			rs.put("flag", true);
			return rs;
		}
		Map<String, String> errorTable = checkTableMappingAssociations(tables);
		if (errorTable == null) {
			rs.put("flag", true);
		} else {
			rs.put("flag", false);
			/* 查询关联缺失的映射表名 */
			String sTableId = errorTable.get("source");
			DmTableMapping source = this.getDmTableMappingDAO().findById(
					sTableId);
			rs.put("source", source.getMappingName());
		}
		return rs;
	}

	/**
	 * 通过查询ID
	 * 
	 * @param sqId
	 * @return
	 * @author 张健雄
	 */
	private Map<String, Object> getFilterParameters(String sqId) {
		Map<String, Object> pm = new HashMap<String, Object>();
		if (StringUtils.isBlank(sqId))
			return pm;

		List<SqFilterParameter> parameters = this.getSqFilterParameterDAO()
				.findBySqId(sqId);
		if (parameters == null || parameters.isEmpty())
			return pm;
		for (SqFilterParameter p : parameters) {
			/* 获取过滤器类型 */
			String filterType = p.getParameterType();
			String key = p.getParameterKey();
			String ovalue = p.getValue();
			Object value = new Object();

			Date statTime = new Date();

			if (FilterType.cyc.toString().equals(filterType)) {
				/* 周期任务处理方式 */
				String monthCycType = p.getMonthCycType();
				String dateCycType = p.getDateCycType();
				/* 获取周期时间值 */
				Calendar cycDate = Calendar.getInstance();
				int year = cycDate.get(Calendar.YEAR);
				int month = cycDate.get(Calendar.MONTH);
				int date = cycDate.get(Calendar.DAY_OF_MONTH);
				/* 计算周期月份数据 */
				if (FilterMonthCycType.lastMonth.toString()
						.equals(monthCycType)) {
					/* 上月 */
					month = month - 1;
				} else if (FilterMonthCycType.custom.toString().equals(
						monthCycType)) {
					/* 自定义 */
					Integer monthValue = p.getMonthCycValue();
					month = month - monthValue;
				}
				/* 计算周期日期数据 */
				if (FilterDateCycType.monthStart.toString().equals(dateCycType)) {
					/* 月初 */
					date = 1;
				} else if (FilterDateCycType.monthEnd.toString().equals(
						dateCycType)) {
					/* 月末 */
					month = month + 1;
					date = 0;
				} else {
					/* 自定义 */
					Integer dateValue = p.getDateCycValue();
					date = dateValue;
				}
				cycDate.set(year, month, date);
				statTime = cycDate.getTime();
				value = cycDate.getTime();
			}else {
				/* 普通过滤器处理方式 */
				String columnType = p.getValueType();
				if (ColumnType.n.toString().equals(columnType)) {
					/* 数字类型参数处理方式 */
					value = Double.parseDouble(ovalue);
				} else if (ColumnType.d.toString().equals(columnType)) {
					/* 日期类型参数处理方式 */
					statTime = DateUtils.stringToDate(ovalue,
							DateConstant.YEAR_MOUTH_DAY);
					value = DateUtils.stringToDate(ovalue,
							DateConstant.YEAR_MOUTH_DAY);
				} else {
					value = ovalue;
				}
			}
			/* 宽表统计时间字段做特殊处理 */
			if (SmartQueryConstant.COLUMN_DATE_NAME.equalsIgnoreCase(p
					.getSqColumn()))
				value = Integer.parseInt(DateUtils.dateTimeToString(statTime,
						DateConstant.YEAR_MOUTH_DAY_C));
			pm.put(key, value);
		}
		return pm;
	}

	/**
	 * 获取SQL统一的字段识别码
	 * 
	 * @param table
	 * @param col
	 * @return
	 * @author 张健雄
	 */
	private String getSqlColumnStr(String table, String col) {
		return table + "_." + col;
	}

	/**
	 * 获取SQL统一的别名
	 * 
	 * @param table
	 * @param col
	 * @param index
	 * @return
	 * @author 张健雄
	 */
	public String getColumnAlias(String table, String col, String index) {
		String alias = table + "_" + index + "_" + col;
		if (alias.length() >= 30) {
			alias = table.substring(0, table.length() - (alias.length() - 30))
					+ "_" + index + "_" + col;
		}
		return alias;
	}
	
	/**
	 * 获取SQL统一的参数别名
	 * 
	 * @param table
	 * @param col
	 * @param index
	 * @return
	 * @author 张健雄
	 */
	private String getParamAlias(String table, String col) {
		
		//取得一个3位随机数字字符串
		String num = RandomStringUtils.random(3, false, true);
		String alias = table + "_" + col + num;
		
		if (alias.length() >= 30) {
			alias = table.substring(0, table.length() - (alias.length() - 30))
					+ "_" + col + num;;
		}
		return alias;
	}

	/**
	 * 获取SQL统一的表识别码（如传入用户账号信息，则需要添加数据权限过滤代码）
	 * 
	 * @param table  数据表名
	 * @param userAcc 用户账号信息
	 * @return
	 * @author 张健雄
	 */
	public String getSqlTableStr(String table, String userAcc) {
		StringBuffer tableSQL = new StringBuffer();
		if (StringUtils.isBlank(userAcc)) {
			if(statTimeMap.containsKey(table)){
				tableSQL.append("(SELECT * FROM " + table + " WHERE STAT_TIME='"+statTimeMap.get(table)+"') " + table + "_");
			}else{
				tableSQL.append(table + " " + table + "_");
			}			
		} else {
			String perimssionSQL = getUserPermissionSQL(userAcc, table);
			tableSQL.append(perimssionSQL + " " + table + "_");
		}

		return tableSQL.toString();
	}
	
	

	/**
	 * 获取指定用户的权限SQL信息
	 * 
	 * @param userAcc 当前用户的账号
	 * @param table 对应查询数据权限的数据表
	 * @return
	 * @author 张健雄
	 */
	private String getUserPermissionSQL(String userAcc, String table) {
		StringBuffer sql = new StringBuffer();
		User user = UserUtils.getUser(userAcc);
		sql.append("( SELECT * FROM " + table);
		sql.append(" WHERE 1=1 ");
		if(statTimeMap.containsKey(table))
			sql.append(" AND STAT_TIME='"+statTimeMap.get(table)+"' ");		
		
		sql.append(" AND EXISTS (SELECT area_code FROM (SELECT DISTINCT area_code,parent_code FROM SYS_ROLE_AREA WHERE role_id IN (");
		if (user == null) {
			sql.append("''");
		} else {
			List<Role> roles = user.getRoleList();
			if (roles == null || roles.size() == 0) {
				sql.append("''");
			} else {
				for (int i = 0; i < roles.size(); i++) {
					if (i == 0) {
						sql.append("'" + roles.get(i).getId() + "'");
					} else {
						sql.append(",'" + roles.get(i).getId() + "'");
					}
				}
			}
		}
		sql.append(")) WHERE cmcc_branch_cd=parent_code AND area_cd=area_code )) ");

		return sql.toString();
	}

	/**
	 * 
	 * @author 张健雄
	 */
	public static void main(String[] args) {
		/*
		 * Calendar cal1 = Calendar.getInstance(); cal1.set(2015,4,0);
		 * System.out.println(cal1.get(Calendar.YEAR));
		 * System.out.println(cal1.get(Calendar.MONTH)+1);
		 * System.out.println(cal1.get(Calendar.DAY_OF_MONTH));
		 * System.out.println(cal1.getTime());
		 */
		SqQueryServiceImpl s = new SqQueryServiceImpl();
		System.out.println(s.getColumnAlias("TW_GROUP_INFO_D",
				"CNTCT_TEL_NBR2", SqQueryServiceImpl.INDEX_SELECT));

	}

	/**
	 * 通过SQL语句生成CSV文件，并放置在系统个人文件夹中
	 * 
	 * @param sql
	 *            sql语句
	 * @param parameters
	 *            sql的匹配参数
	 * @param mapper
	 *            字段与名称的对应关系
	 * @param fileName
	 *            文件名
	 * @param creator
	 *            归属人账号
	 * @return 返回excel文件信息（name/path/size/type）
	 * @throws Exception
	 * @author 张健雄
	 */
	protected Map<String, String> createFileBySql(String sql,
			Map<String, Object> parameters,
			LinkedHashMap<String, String> mapper, String fileName,
			String creator) {
		return createFileBySql(sql, parameters, mapper, fileName, creator,
				false);
	}
	
	/**
	 * 通过SQL语句生成临时CSV文件
	 * 
	 * @param sql sql语句
	 * @param parameters sql的匹配参数
	 * @param mapper 字段与名称的对应关系
	 * @return 返回临时文件的路径
	 * @throws Exception
	 * @author 张健雄
	 */
	private String createTempFileBySql(String sql,
			Map<String, Object> parameters, LinkedHashMap<String, String> mapper)
			throws Exception {
		String filename = "TEMP_FILE_";
		Map<String, String> fileinfo = this.createFileBySql(sql, parameters,
				mapper, filename, null, true);
		return fileinfo.get("path");
	}
	
	/*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝智能查询改造方案接口＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*/

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#queryData(java.lang.String, java.util.List, java.util.List, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryData(String searchKey,
			List<SqSelecterVO> selecters, List<SqFilterVO> filters, int page,
			int pageSize) {
		
		/**
		 * 查询标签表数据
		 * select 
			       TA_TARGET_CUSTOMER_D_20160504.User_Num as User_Num,
			       
			       DIM_TAG_BRAND_.BRAND_NAME as CM_BRAND, 
			       DIM_TAG_VIP_TYPE_.VIP_TYPE_NAME as VIP_TYPE,
			       DIM_TAG_JUDGE_.JUDGE_NAME AS IS_4G_CUST
			       
			from (SELECT * FROM TA_TARGET_CUSTOMER_D WHERE STAT_TIME='20160504') TA_TARGET_CUSTOMER_D_20160504,
			     (SELECT * FROM TA_TARGET_CUSTOMER_M WHERE STAT_TIME='20160401') TA_TARGET_CUSTOMER_M_201604,
			     (SELECT * FROM TA_TARGET_CUSTOMER_D WHERE STAT_TIME='20160503') TA_TARGET_CUSTOMER_D_20160503,
			     DIM_TAG_BRAND DIM_TAG_BRAND_,
			     DIM_TAG_VIP_TYPE DIM_TAG_VIP_TYPE_,
			     DIM_TAG_JUDGE DIM_TAG_JUDGE_
			     
			where TA_TARGET_CUSTOMER_D_20160504.USER_NUM = TA_TARGET_CUSTOMER_M_201604.USER_NUM 
			      and TA_TARGET_CUSTOMER_D_20160504.USER_NUM = TA_TARGET_CUSTOMER_D_20160503.USER_NUM
			      
			      
			      and TA_TARGET_CUSTOMER_D_20160504.CM_BRAND in ('207','317') 
			      and TA_TARGET_CUSTOMER_D_20160503.IS_4G_CUST in ('1') 
			      and TA_TARGET_CUSTOMER_M_201604.VIP_TYPE in ('CardVip')
			      
			      and TA_TARGET_CUSTOMER_D_20160504.CM_BRAND=DIM_TAG_BRAND_.BRAND 
			      and TA_TARGET_CUSTOMER_D_20160503.IS_4G_CUST=DIM_TAG_JUDGE_.JUDGE
			      and TA_TARGET_CUSTOMER_M_201604.VIP_TYPE=DIM_TAG_VIP_TYPE_.VIP_TYPE
		 * 
		 */
		String sql="";
		Map<String, Object> parameters =null;
		List<String> columns=null;
		if(sqlPool.containsKey(searchKey)){
			/*直接从缓存中获取SQL数据*/
			Map<String, Object> sqlParam = sqlPool.get(searchKey);
			parameters=(Map<String, Object>) sqlParam.get("parameters");
			sql=(String) sqlParam.get("sql");
			columns=(List<String>) sqlParam.get("columns");
			
		}else{
			/*如果过滤器集合与数据集合都为空，则需要提示错误信息，并返回空数据集*/
				if((selecters == null || selecters.isEmpty())){
					log.error("智能查询功能：查询显示列为空！");
					return null;				
				}
			/*获取当前用户数据*/
				User user = UserUtils.getUser();	
			/*从参数信息中创建查询SQL*/
				StringBuffer selectSQL = new StringBuffer(" SELECT " + BaseService.paginationDual + " ");
				StringBuffer fromSQL = new StringBuffer(" FROM ");
				StringBuffer filterSQL = new StringBuffer(" WHERE 1=1 ");
			/*获取数据映射表对应MAP信息*/
			/*获取数据映射列对应List信息*/
				columns=new ArrayList<String>();
			/*保存表ID与表别名对应信息*/	
				Map<String,String> aliasMap=new HashMap<String, String>();
				for (int i = 0; i < selecters.size(); i++) {
					SqSelecterVO sqSelecterVO = selecters.get(i);
					DmTableMapping dmTable = this.getDmTableMappingDAO().findById(sqSelecterVO.getMpTableId());
					DmColumnMapping dmColumn = this.getDmColumnMappingDAO().findById(sqSelecterVO.getMpColumnId());
					if(null!=dmColumn){
						columns.add(dmColumn.getColumnName());
					}
					/*历遍显示列，获取映射表的列表 select*/
					if(!StringUtils.isNotBlank(sqSelecterVO.getStatTime()))sqSelecterVO.setStatTime("20160401");
					String alias = getAliasForTime(dmTable.getTableName(),sqSelecterVO.getStatTime());
					if(!aliasMap.containsKey(alias)){
						/*是否加入权限控制*/
						String userAcc = null;
						if(DataLabelConstant.COMMON_FLAG_TRUE.equals(dmTable.getIsPerimssion()))
							userAcc = user.getLoginName();
						/*获取数据表列表 from */
						Boolean isPerimssion =Boolean.valueOf(dmTable.getIsPerimssion()).booleanValue();
						StorageType storageType=StorageType.getStorageType(dmTable.getStorageType());
						String fromSql=	getFromSql(dmTable.getTableName(), isPerimssion, storageType, sqSelecterVO.getStatTime(), userAcc);
						aliasMap.put(alias,sqSelecterVO.getMpTableId());
						fromSQL.append(fromSql+",");//FROM
					}
					selectSQL.append(alias+"."+dmColumn.getColumnName()+",");//SELECT
				}
			/************************************************sqFilterVO*****************************************************/	
			/*获取过滤器关联对应MAP信息*/
			/*获取过滤器匹配关系对应MAP信息*/
				parameters = new HashMap<String, Object>();
				int count=0;
				for (SqFilterVO sqFilterVO : filters) {
					DimFilterRalation ralation =this.getDimFilterRalationDAO().findById(sqFilterVO.getRelationId());
					DimFilterFormula formula=this.getDimFilterFormulaDAO().findById(sqFilterVO.getFormulaId());
					DmTableMapping dmTable = this.getDmTableMappingDAO().findById(sqFilterVO.getMpTableId());;	
					DmColumnMapping dmColumn =this.getDmColumnMappingDAO().findById(sqFilterVO.getMpColumnId());
					/*历遍显示列，获取映射表的列表 select*/
					if(!StringUtils.isNotBlank(sqFilterVO.getStatTime()))sqFilterVO.setStatTime("20160401");
					String alias = getAliasForTime(dmTable.getTableName(), sqFilterVO.getStatTime());
					if(!aliasMap.containsKey(alias)){
						/*是否加入权限控制*/
						String userAcc = null;
						if(DataLabelConstant.COMMON_FLAG_TRUE.equals(dmTable.getIsPerimssion()))
							userAcc = user.getLoginName();
						/*获取数据表列表 from */
						Boolean isPerimssion =Boolean.valueOf(dmTable.getIsPerimssion()).booleanValue();
						StorageType storageType=StorageType.getStorageType(dmTable.getStorageType());
						String fromSql=	getFromSql(dmTable.getTableName(), isPerimssion, storageType,sqFilterVO.getStatTime(), userAcc);
						aliasMap.put(alias,sqFilterVO.getMpTableId());
						fromSQL.append(fromSql+",");//FROM
					}
					/*历遍过滤器，获取映射表的列表 where
					 * 表名+20160501（缓存列表）去重 - 生成表的别名生成TA_TARGET_CUSTOMER_D_20160504
					 * 字段名,关系,值,匹配条件
					 * */
					String key=dmColumn.getColumnName()+count;
					String value = sqFilterVO.getValue()[0];
					if("like".equals(formula.getCode())||"not like".equals(formula.getCode())){
						value="%"+value+"%";
					}
					filterSQL.append(" "+ralation.getCode()+" "+alias+"."+dmColumn.getColumnName()
					+" "+formula.getCode()+":"+key);
					parameters.put(key, value);
					count++;
				}	
				/*获取表与表之间的关联关系
				 * 1、DmTableMappingAssociation 不同表
				 * 2、相同表 ， 通过表名找到主键名 - 历遍表名+20160501（表名缓存列表） 先不做
				 * TA_TARGET_CUSTOMER_D_20160501.k1 = TA_TARGET_CUSTOMER_D_20160503.k1
				   TA_TARGET_CUSTOMER_D_20160504.k1 = TA_TARGET_CUSTOMER_M_201604.k2
				 * */
				//过滤关联关系的相同表
				Map<String,String> relationMap=new HashMap<String,String>();
				
				if(aliasMap.keySet().size()>=2){
					for (String alias1 : aliasMap.keySet()) {
						for (String alias2 : aliasMap.keySet()) {
							String id1=aliasMap.get(alias1);
							String id2=aliasMap.get(alias2);
							DmTableMappingAssociation association = this.getDmTableMappingAssociationDAO().findById(new DmTableMappingAssociationId(id1,id2));
							if(association!=null){
								//不同表
								filterSQL.append(" and "+alias1+"."+association.getMpColumnCode1()
								+"="+alias2+"."+association.getMpColumnCode2());
							}else{
								
								//相同表
								//id一样//别名不一样
								if(id1.equals(id2)&&!alias1.equals(alias2)&&!relationMap.containsKey(alias1+"="+alias2)&&!relationMap.containsKey(alias2+"="+alias1)){
									String hql="FROM DmColumnMapping where mpTableId=? and isKeys=?";
									List<DmColumnMapping> columnList = (List<DmColumnMapping>) this.getHibernateTemplate().find(hql, id1,"Y");
									DmColumnMapping column = columnList.get(0);
									filterSQL.append(" and "+alias1+"."+column.getColumnName()
									+"="+alias2+"."+column.getColumnName());
									relationMap.put(alias1+"="+alias2, "");
									relationMap.put(alias2+"="+alias1, "");
								}
							}
						}
					}
				}
				/*获取数据表最新的数据周期对应MAP信息
				 *Map<tableName,time> */
				/*拼接SQL*/
				sql=selectSQL.toString().substring(0,selectSQL.length()-1)+fromSQL.toString().substring(0,fromSQL.length()-1)+filterSQL.toString();
				Map<String, Object> sqlParam = new HashMap<String,Object>();
				sqlParam.put("sql", sql);
				sqlParam.put("columns", columns);
				sqlParam.put("parameters", parameters);
				sqlPool.put(searchKey,sqlParam);
				
		}
		/*查询结果*/
		return this.runSQLForGrid(sql, parameters, columns, page, pageSize);
	}
	
	
	/**
	 * 通过别名后缀获取SQL统一的识别别名
	 * 
	 * @param table  数据表名
	 * @param statTime 数据表的周期时间（全量数据表则为空）
	 * @return 如果表名超长的情况下，则通过表字符截取的方式保留30个字符
	 * @author 张健雄
	 */
	private String getAliasForTime(String table,String statTime) {

		String alias =  table + "_" ;
		if(StringUtils.isNotBlank(statTime))
			alias += statTime;

		if (alias.length() >= 30) 
			alias = alias.substring(alias.length()-30);	
		while (alias.startsWith("_")) {
			alias = alias.substring(1);			
		}
		return alias;
	} 
	
	/**
	 * 通过参数信息查询数据表的SQL数据
	 * @param table 数据表名
	 * @param isPerimssion 是否包含权限数据
	 * @param storageType 数据存储方式
	 * @param statTime 数据周期时间（全量数据时为空）
	 * @param userAcc 用户账号信息
	 * @return
	 * @author 张健雄
	 */
	private String getFromSql(String table,boolean isPerimssion,StorageType storageType,String statTime,String userAcc) {
		StringBuffer tableSQL = new StringBuffer();
		
		String alias = this.getAliasForTime(table, statTime);
		if(isPerimssion){
			/*数据表包含权限信息*/
			String perimssionSQL = getPermissionSQL(table,storageType,userAcc,statTime);
			tableSQL.append(perimssionSQL + " " + alias);
		}else{
			/*数据表不包含权限信息*/			
			/*如果是数据增量表，则需要包含周期数据*/
			if(StorageType.I.equals(storageType)&&StringUtils.isNotBlank(statTime)){
				tableSQL.append("(SELECT * FROM " + table +  " WHERE STAT_TIME='"+statTime+"') " + alias);
			}else{
				tableSQL.append(table + " " + alias);
			}
		}
		return tableSQL.toString();
	}

	/**
	 * 获取指定用户的权限SQL信息
	 * @param table 对应查询数据权限的数据表
	 * @param storageType 数据存储方式
	 * @param userAcc 用户账号
	 * @param statTime 数据周期（全量数据时为空）
	 * @return
	 * @author 张健雄
	 */
	private String getPermissionSQL(String table,StorageType storageType,String userAcc,String statTime) {
		StringBuffer sql = new StringBuffer();
		User user = UserUtils.getUser(userAcc);
		sql.append("( SELECT * FROM " + table);
		sql.append(" WHERE 1=1 ");
		if(StorageType.I.equals(storageType))
			sql.append(" AND STAT_TIME='"+statTime+"' ");		
		
		sql.append(" AND EXISTS (SELECT area_code FROM (SELECT DISTINCT area_code,parent_code FROM SYS_ROLE_AREA WHERE role_id IN (");
		if (user == null) {
			sql.append("''");
		} else {
			List<Role> roles = user.getRoleList();
			if (roles == null || roles.size() == 0) {
				sql.append("''");
			} else {
				for (int i = 0; i < roles.size(); i++) {
					if (i == 0) {
						sql.append("'" + roles.get(i).getId() + "'");
					} else {
						sql.append(",'" + roles.get(i).getId() + "'");
					}
				}
			}
		}
		sql.append(")) WHERE cmcc_branch_cd=parent_code AND area_cd=area_code )) ");

		return sql.toString();
	} 
	
	
	
	/**
     * 查询匹配字符列表
     * @param id
     * @return
     */
    public List<Map<String, Object>> queryColumList(String columsId) {
    	//DmColumnMapping dmColumnMapping = this.getDmColumnMappingDAO().findById(columsId);
    	String sql="SELECT * FROM DIM_AREA";
    	SQLQuery sqlQuery = this.getDmColumnMappingDAO().getSession().createSQLQuery(sql);
    	sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	return sqlQuery.list();
    }
    
    

	@Override
	public List<Map<String, String>> queryPeriodList(String tableName) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(StringUtils.isBlank(tableName))
			return list;
		
		String sql = "SELECT stat_time,period FROM TA_T_LAST_STAT_TIME WHERE TABLE_NAME='"+tableName+"'";
		
		List<Object[]> rs = this.excuteSQLQuery(sql);
		if(rs != null && !rs.isEmpty()){
			Object[] time = rs.get(0);
			String statTime = String.valueOf(time[0]);
			String period = String.valueOf(time[1]);
			
			if(DataLabelConstant.LABEL_PERIOD_TYPE_MONTH.equals(period)){
				/*月周期类型处理*/
				Date statDate = DateUtils.stringToDate(statTime, DateConstant.YEARMOUTHDAY);
				/*加载当前日期的信息*/
				Map<String,String> now = new HashMap<String,String>();
				now.put("id", statTime);
				now.put("text", DateUtils.dateTimeToString(statDate, DateConstant.YEAR_MOUTH));
				now.put("group", DateUtils.dateTimeToString(statDate, DateConstant.YEAR+"年"));
				list.add(now);
				/*加载前6个月的日期信息*/
				for (int i = 0; i < 11; i++) {
					int x = 0-i-1;
					Date oldDate = Utils.dateAddMonth(statDate, x);
					Map<String,String> old = new HashMap<String,String>();
					old.put("id", DateUtils.dateTimeToString(oldDate, DateConstant.YEARMOUTHDAY));
					old.put("text", DateUtils.dateTimeToString(oldDate, DateConstant.YEAR_MOUTH));
					old.put("group", DateUtils.dateTimeToString(oldDate, DateConstant.YEAR+"年"));
					list.add(old);
				}
			}else{
				/*日周期类型处理*/
				Date statDate = DateUtils.stringToDate(statTime, DateConstant.YEARMOUTHDAY);
				Map<String,String> now = new HashMap<String,String>();
				now.put("id", statTime);
				now.put("text", DateUtils.dateTimeToString(statDate, DateConstant.YEAR_MOUTH_DAY));
				now.put("group", DateUtils.dateTimeToString(statDate, "yyyy年MM月"));
				list.add(now);
				/*加载前31日的日期信息*/
				for (int i = 0; i < 61; i++) {
					int x = 0-i-1;
					Date oldDate = DateUtils.dateAddDays(statDate, x);
					Map<String,String> old = new HashMap<String,String>();
					old.put("id", DateUtils.dateTimeToString(oldDate, DateConstant.YEARMOUTHDAY));
					old.put("text", DateUtils.dateTimeToString(oldDate, DateConstant.YEAR_MOUTH_DAY));
					old.put("group", DateUtils.dateTimeToString(oldDate, "yyyy年MM月"));
					list.add(old);
				}	
			}
			
		}
		return list;
	}


}
