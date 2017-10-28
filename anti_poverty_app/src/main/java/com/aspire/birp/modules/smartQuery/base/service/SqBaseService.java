
package com.aspire.birp.modules.smartQuery.base.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.auth.modules.sys.constants.LogConstant;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.bi.common.constant.DateConstant;
import com.aspire.bi.common.util.DateUtils;
import com.aspire.birp.modules.base.service.BaseService;
import com.aspire.birp.modules.dataMapping.mapping.dao.DmCatalogBindingDao;
import com.aspire.birp.modules.dataMapping.mapping.dao.DmColumnMappingDAO;
import com.aspire.birp.modules.dataMapping.mapping.dao.DmTableCatalogDAO;
import com.aspire.birp.modules.dataMapping.mapping.dao.DmTableMappingAssociationDAO;
import com.aspire.birp.modules.dataMapping.mapping.dao.DmTableMappingDAO;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmColumnMapping;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableCatalog;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMapping;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMappingAssociation;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableCatalogVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableMappingVO;
import com.aspire.birp.modules.smartQuery.base.constant.OptionType;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.constant.TackCycType;
import com.aspire.birp.modules.smartQuery.base.dao.DimFilterFormulaDAO;
import com.aspire.birp.modules.smartQuery.base.dao.DimFilterRalationDAO;
import com.aspire.birp.modules.smartQuery.base.dao.DimMColumnTypeDAO;
import com.aspire.birp.modules.smartQuery.base.dao.DimSqSortDAO;
import com.aspire.birp.modules.smartQuery.base.vo.ColumnType;
import com.aspire.birp.modules.smartQuery.exception.dao.SqExceptionInfoDAO;
import com.aspire.birp.modules.smartQuery.market.dao.SqFileDataInfoDAO;
import com.aspire.birp.modules.smartQuery.market.dao.SqFileShareDAO;
import com.aspire.birp.modules.smartQuery.market.dao.SqPersonalCatalogDAO;
import com.aspire.birp.modules.smartQuery.market.dao.SqPersonalCatalogShareDAO;
import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalog;
import com.aspire.birp.modules.smartQuery.market.vo.SqPersonalCatalogVO;
import com.aspire.birp.modules.smartQuery.query.dao.SqFilterInfoDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqFilterInfoTempDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqFilterParameterDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqListFilterDescDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqListFilterInfoDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqQueryInfoDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqQueryInfoTempDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqSelectColumnDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqSelectInfoDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqSelectInfoTempDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqSortInfoDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqSortInfoTempDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqTableInfoDAO;
import com.aspire.birp.modules.smartQuery.query.dao.SqTableInfoTempDAO;
import com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqFilterInfoTemp;
import com.aspire.birp.modules.smartQuery.query.entity.SqListFilterInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfoTemp;
import com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqSelectInfoTemp;
import com.aspire.birp.modules.smartQuery.query.entity.SqSortInfo;
import com.aspire.birp.modules.smartQuery.query.entity.SqSortInfoTemp;
import com.aspire.birp.modules.smartQuery.query.vo.SqFilterInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqListFilterInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSelectInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSortInfoVO;
import com.aspire.birp.modules.smartQuery.task.dao.SqTaskInfoDAO;
import com.aspire.birp.modules.smartQuery.task.dao.SqTaskStatusInfoDAO;
import com.aspire.birp.modules.sys.utils.UserUtils;


/**  
 * @Title: 智能查询通用功能服务实现类
 * @Description: 用于实现智能查询的通用功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月15日 下午2:30:56
 * @version: V1.0
 */
@Service
public class SqBaseService extends BaseService {

	private static Logger log = LoggerFactory.getLogger(SqBaseService.class);
	
	
	/**
	 * 列类型转化列表
	 */
	protected Map<String,String> columnTypeMap;
	
	public SqBaseService() {
		super();
		/*定义列类型语义*/
		if(columnTypeMap == null){
			columnTypeMap = new HashMap<String, String>();
			columnTypeMap.put(ColumnType.s.toString(), "字符");
			columnTypeMap.put(ColumnType.n.toString(), "数字");
			columnTypeMap.put(ColumnType.d.toString(), "日期");
		}
	}
    
    /*获取DAO服务类*/
    @Autowired
    private DimFilterFormulaDAO dimFilterFormulaDAO;
    @Autowired
    private DimFilterRalationDAO dimFilterRalationDAO;
    @Autowired
    private DimMColumnTypeDAO dimMColumnTypeDAO;
    @Autowired
    private DimSqSortDAO dimSqSortDAO;
    @Autowired
    private DmCatalogBindingDao dmCatalogBindingDao;
    @Autowired
    private DmTableCatalogDAO dmTableCatalogDAO;
    @Autowired
    private DmTableMappingDAO dmTableMappingDAO;
    @Autowired
    private DmColumnMappingDAO dmColumnMappingDAO;
    @Autowired
    private DmTableMappingAssociationDAO dmTableMappingAssociationDAO;
    
    @Autowired
    private SqTableInfoTempDAO sqTableInfoTempDAO;
    @Autowired
    private SqFilterInfoTempDAO sqFilterInfoTempDAO;
    @Autowired
    private SqQueryInfoTempDAO sqQueryInfoTempDAO;
    @Autowired
    private SqSelectInfoTempDAO sqSelectInfoTempDAO;
    @Autowired
    private SqSortInfoTempDAO sqSortInfoTempDAO;
    
    @Autowired
    private SqTableInfoDAO sqTableInfoDAO;
    @Autowired
    private SqFilterInfoDAO sqFilterInfoDAO;
    @Autowired
    private SqQueryInfoDAO sqQueryInfoDAO;
    @Autowired
    private SqSelectInfoDAO sqSelectInfoDAO;
    @Autowired
    private SqSortInfoDAO sqSortInfoDAO;
    @Autowired
    private SqPersonalCatalogDAO sqPersonalCatalogDAO;
    @Autowired
    private SqFileDataInfoDAO sqFileDataInfoDAO;
    @Autowired
    private SqTaskInfoDAO sqTaskInfoDAO;
    @Autowired
    private SqTaskStatusInfoDAO sqTaskStatusInfoDAO;
    @Autowired
    private SqFileShareDAO sqFileShareDAO;
    @Autowired
    private SqPersonalCatalogShareDAO sqPersonalCatalogShareDAO;
    @Autowired
    private SqExceptionInfoDAO sqExceptionInfoDAO;
    @Autowired
    private SqFilterParameterDAO sqFilterParameterDAO;
    @Autowired
    private SqSelectColumnDAO sqSelectColumnDAO;
    @Autowired
    private SqListFilterDescDAO sqListFilterDescDAO;
    @Autowired
    private SqListFilterInfoDAO sqListFilterInfoDAO;
    
    
    /*entity与vo属性转换方法*/
    protected DmColumnMappingVO copyBeanProperties(DmColumnMapping orig){
    	DmColumnMappingVO vo = new DmColumnMappingVO();
		try {
			BeanUtils.copyProperties(orig,vo);
		} catch (Exception e) {
			log.error(DmColumnMapping.class+"对象属性转换失败",e);
		} 
		return vo;
    }    

    protected DmTableCatalogVO copyBeanProperties(DmTableCatalog orig){
    	DmTableCatalogVO vo = new DmTableCatalogVO();
		try {
			BeanUtils.copyProperties(orig,vo);
		} catch (Exception e) {
			log.error(DmTableCatalog.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    protected SqPersonalCatalogVO copyBeanProperties(SqPersonalCatalog orig){
    	SqPersonalCatalogVO vo = new SqPersonalCatalogVO();
		try {
			BeanUtils.copyProperties(orig,vo);
			vo.setCreateTime(DateUtils.dateTimeToString(orig.getCreateTime(), DateConstant.YEAR_MOUTH_DAY));
		} catch (Exception e) {
			log.error(DmTableCatalog.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    protected DmTableMappingVO copyBeanProperties(DmTableMapping orig){
    	DmTableMappingVO vo = new DmTableMappingVO();
		try {
			BeanUtils.copyProperties(orig, vo);
		} catch (Exception e) {
			log.error(DmTableMapping.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    protected SqQueryInfoVO copyBeanPropertiesQuery(Object orig,boolean temp){
    	SqQueryInfoVO vo = new SqQueryInfoVO();
		try {
			if(temp){
				SqQueryInfoTemp en = (SqQueryInfoTemp) orig;
				BeanUtils.copyProperties(en, vo);
				vo.setValidDate(DateUtils.dateTimeToString(en.getValidDate(), DateConstant.YEAR_MOUTH_DAY));
				vo.setInvalidDate(DateUtils.dateTimeToString(en.getInvalidDate(), DateConstant.YEAR_MOUTH_DAY));
				vo.setCreateTime(DateUtils.dateTimeToString(en.getCreateTime(), DateConstant.YEAR_MOUTH_DAY));
				vo.setIsTemp(SmartQueryConstant.COMMON_FLAG_TRUE);
			}else{
				SqQueryInfo en = (SqQueryInfo) orig;
				BeanUtils.copyProperties(en, vo);
				vo.setValidDate(DateUtils.dateTimeToString(en.getValidDate(), DateConstant.YEAR_MOUTH_DAY));
				vo.setInvalidDate(DateUtils.dateTimeToString(en.getInvalidDate(), DateConstant.YEAR_MOUTH_DAY));
				vo.setCreateTime(DateUtils.dateTimeToString(en.getCreateTime(), DateConstant.YEAR_MOUTH_DAY));
				vo.setIsTemp(SmartQueryConstant.COMMON_FLAG_FALSE);
			}
			
		} catch (Exception e) {
			log.error(SqQueryInfo.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    protected void updatePropertiesQuery(SqQueryInfoVO vo,SqQueryInfo info){
		info.setName(vo.getName());
		info.setConfigType(vo.getConfigType());
		info.setMpTableId(vo.getMpTableId());
		info.setSqTable(vo.getSqTable());
		if (SmartQueryConstant.COMMON_FLAG_TRUE.equals(vo.getConfigType())) {
			info.setFileName(vo.getFileName());
			info.setPersonalCatalogId(vo.getPersonalCatalogId());
			info.setDataStoreDate(vo.getDataStoreDate());
			info.setCycType(vo.getCycType());
			info.setCycLen(vo.getCycLen());
			info.setCycInfo(vo.getCycInfo());
			info.setValidDate(new Timestamp(DateUtils.stringToDate(vo.getValidDate(),DateConstant.YEAR_MOUTH_DAY).getTime()));
			info.setInvalidDate(new Timestamp(DateUtils.stringToDate(vo.getInvalidDate(),DateConstant.YEAR_MOUTH_DAY).getTime()));
		}else{
			info.setFileName(null);
			info.setPersonalCatalogId(null);
			info.setDataStoreDate(null);
			info.setCycType(null);
			info.setCycLen(null);
			info.setCycInfo(null);
			info.setValidDate(null);
			info.setInvalidDate(null);
		}
    }
    
    protected SqSelectInfoVO copyBeanPropertiesSelect(Object orig,boolean temp){
    	SqSelectInfoVO vo = new SqSelectInfoVO();
		try {
			if(temp){
				SqSelectInfoTemp en = (SqSelectInfoTemp) orig;
				BeanUtils.copyProperties(en, vo);
			}else{
				SqSelectInfo en = (SqSelectInfo) orig;
				BeanUtils.copyProperties(en, vo);
			}
			
		} catch (Exception e) {
			log.error(SqSelectInfo.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    protected SqFilterInfoVO copyBeanPropertiesFilter(Object orig,boolean temp){
    	SqFilterInfoVO vo = new SqFilterInfoVO();
		try {
			if(temp){
				SqFilterInfoTemp en = (SqFilterInfoTemp) orig;
				BeanUtils.copyProperties(en, vo);
			}else{
				SqFilterInfo en = (SqFilterInfo) orig;
				BeanUtils.copyProperties(en, vo);
			}
			
		} catch (Exception e) {
			log.error(SqFilterInfo.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    protected SqSortInfoVO copyBeanPropertiesSort(Object orig,boolean temp){
    	SqSortInfoVO vo = new SqSortInfoVO();
		try {
			if(temp){
				SqSortInfoTemp en = (SqSortInfoTemp) orig;
				BeanUtils.copyProperties(en, vo);
			}else{
				SqSortInfo en = (SqSortInfo) orig;
				BeanUtils.copyProperties(en, vo);
			}
			
		} catch (Exception e) {
			log.error(SqSortInfo.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    protected SqListFilterInfoVO copyBeanPropertiesListFilter(SqListFilterInfo orig){
    	SqListFilterInfoVO vo = new SqListFilterInfoVO();
		try {
			BeanUtils.copyProperties(orig, vo);
			vo.setCreateTime(DateUtils.dateTimeToString(orig.getCreateTime(), DateConstant.YEAR_MOUTH_DAY));
		} catch (Exception e) {
			log.error(SqListFilterInfo.class+"对象属性转换失败",e);
		} 
		return vo;
    }
    
    /**
	 * 执行动态生成的SQL语句
	 * @param sql 动态SQL语句
	 * @param parameters sql的匹配参数
	 * @param columns 列映射名列表
	 * @return
	 * @author 张健雄
	 */
    protected List<Map<String,Object>> runSQL(String sql,Map<String,Object> parameters,List<String> columns){
		List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
		if(StringUtils.isBlank(sql)) return data;
		/*数据列表*/
		if(columns.size() == 1) {
			List<Object> rowsTemp = this.excuteSQLQuery(sql,parameters);
			for (int i = 0; i < rowsTemp.size(); i++) {
				Map<String, Object> row = new HashMap<String, Object>();
				row.put(columns.get(0), rowsTemp.get(i));
				data.add(row);
			}
		}else {
			List<Object[]> rowsTemp = this.excuteSQLQuery(sql,parameters);
			for (int i = 0; i < rowsTemp.size(); i++) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (int j = 0; j < columns.size(); j++) {
					Object value = rowsTemp.get(i)[j];
					row.put(columns.get(j), value);
				}
				data.add(row);
			}
		}
		return data;
	}
    
    
    /**
	 * 执行动态生成的SQL语句
	 * @param sql 动态SQL语句
	 * @param columns 列映射名列表
	 * @return 返回OBJECT数组
	 * @author 张健雄
	 */
    protected List<Object[]> runSQLForArray(String sql,List<String> columns){
		return this.runSQLForArray(sql, null, columns);
	}
    
    /**
	 * 执行动态生成的SQL语句
	 * @param sql 动态SQL语句
	 * @param obj sql的匹配参数
	 * @param columns 列映射名列表
	 * @return 返回OBJECT数组
	 * @author 张健雄
	 */
    protected List<Object[]> runSQLForArray(String sql,Object[] obj,List<String> columns){
		List<Object[]> data = new ArrayList<Object[]>();
		if(StringUtils.isBlank(sql)) return data;
		/*数据列表*/
		if(columns.size() == 1) {
			List<Object> rows = this.excuteSQLQuery(sql,obj);
			for (Object object : rows) {
				data.add(new Object[]{object});
			}			 
		}else {
			data = this.excuteSQLQuery(sql,obj);
		}
		return data;
	}
    
    

    /**
     * 校验表是否存在关联，对于一张表而言，存在于一个sqlId，则代表有关联，
     * 对于N张表而言，如果一个sqlId里有N个tableId，则代表有关联
     * @param tableIds
     * @return true代表都存在关联，false表示都不存在关联
     * @author 苏伟城
     */
    protected boolean isTableMappingAssociations(String... tableIds){
        if(tableIds.length == 0)  {
            return false;
        }

        //组装sql查询
        StringBuilder hql = new StringBuilder();
        hql.append("select count(*) as total,sqId ");
        hql.append("from SqTableInfo ti ");
        hql.append("where ti.mpTableId in( ");
        for (int i = 0; i < tableIds.length; i++) {
        	hql.append("?,");
		}
        hql.delete(hql.length() - 1,hql.length());
        hql.append(") group by sqId having count(*) > ? ");

        //设置参数
        List<Object> params = new LinkedList<Object>();
        params.addAll(Arrays.asList(tableIds));
        params.add(new Long(tableIds.length - 1));
        List<Object> list = (List<Object>) excuteQuery(hql.toString(),params.toArray(),1,10);
        if(list.size() > 0) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 校验表是否存在关联，如果存在多个没有关联的表，则返回第一个没有关联关系的表
     * @param tableIds
     * @return map为null，则表示所有表都有关联，不为null，则可从key为source和target取出没有关联关系的两张表的Id
     * @author 苏伟城
     */
    protected Map<String,String> checkTableMappingAssociations(String... tableIds){
        if(tableIds.length == 0)  {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("from DmTableMappingAssociation a where (a.id.mpTableId1 = ? and ");
        sb.append(" a.id.mpTableId2 = ?) or (a.id.mpTableId2 = ? and a.id.mpTableId1 = ?)");
        String hql = sb.toString();
        Map<String,String> result = null;
        Map<String,Integer> associationsMap = new HashMap<String, Integer>(tableIds.length);
        //关联关系验证：如果一张表与其他任意一张表有关联关系，则表示该表通过验证；
        //如果一张表与其他任意一张表都没有关联，则表示该表验证不通过
        for(int i = 0;i < tableIds.length;i++) {
           /* if(associationsMap.containsKey(tableIds[i])) {  //已经存在关联关系
                continue;
            }*/
            for(int j = i + 1;j < tableIds.length;j++) {
            	List<?> rows = getHibernateTemplate().find(hql,tableIds[i],tableIds[j],tableIds[i],tableIds[j]);
                if(null != rows && rows.size() > 0) {
                    associationsMap.put(tableIds[i],1);
                    associationsMap.put(tableIds[j],1);
//                    break;
                }
            }
            if(!associationsMap.containsKey(tableIds[i])) {
                result = new HashMap<String, String>(1);
                result.put("source",tableIds[i]);
                return result;
            }
        }
        return result;
    }

    /**
     * 返回表的所有关联关系
     * @param tableIds 表Id
     * @author 苏伟城
     */
    public List<DmTableMappingAssociation> queryTableMappingAssociations(String ... tableIds) {
        List<DmTableMappingAssociation> dmTableMappingAssociations = new LinkedList<DmTableMappingAssociation>();
        StringBuffer sb = new StringBuffer();
        sb.append("from DmTableMappingAssociation a where (a.id.mpTableId1 = ? and ");
        sb.append(" a.id.mpTableId2 = ?) or (a.id.mpTableId2 = ? and a.id.mpTableId1 = ?)");
        String hql = sb.toString();
        for(int i = 0;i < tableIds.length;i++) {
            for(int j = i + 1;j < tableIds.length;j++) {
            	List<?> rows = getHibernateTemplate().find(hql,tableIds[i],tableIds[j],tableIds[i],tableIds[j]);
            	if(rows != null && rows.size() != 0){
            		for (Object object : rows) {
            			DmTableMappingAssociation a = (DmTableMappingAssociation) object;
            			dmTableMappingAssociations.add(a);
					}
            	}
            }
        }
        return dmTableMappingAssociations;
    }
    
    /**
     * 通过table列表获取映射表信息
     * @param table 单表或多表模式
     * @return
     * @author 张健雄
     */
    protected List<DmTableMappingVO> queryMappingbytables(String... table) {
    	List<DmTableMappingVO> tables = new ArrayList<DmTableMappingVO>();
    	if(table.length == 0 )
    		return tables;
    	String ids = com.aspire.bi.common.util.StringUtils.splitStringForArray(table, ",");
    	String hql = "FROM DmTableMapping t WHERE t.tableName IN ("+ids+")";
    	List<?> tms = getHibernateTemplate().find(hql);
    	for (Object obj : tms) {
    		DmTableMapping tm = (DmTableMapping) obj;
    		DmTableMappingVO vo = copyBeanProperties(tm);
    		tables.add(vo);
		}    	
		return tables;
	}
    
    /**
     * 数据操作日志记录接口
     * @param function 功能名称
     * @param type 操作类型
     * @param lp 日志描述参数
     * @author 张健雄
     */
    protected void markOptionLogger(final String function,OptionType type,Map<String,String> lp) {
		User user = UserUtils.getUser();
		String desc = getLogDescForDataoption(lp, type);
		this.getLogService().saveLog(LogConstant.TYPE_OPERATION, function, LogConstant.OPER_CREATE_FILE, desc,user);
	}
    
    /**
     * 通过功能模块的标识符及必要的参数信息生成数据操作的日志描述
     * @param parameters 日志参数
     * @param type 功能模块的标识符
     * @return
     * @author 张健雄
     */
    protected String getLogDescForDataoption(Map<String,String> parameters,OptionType type ) {
    	StringBuffer desc = new StringBuffer();
    	if(type == null)
    		return desc.toString();
    	List<DmTableMappingVO> tms = new ArrayList<DmTableMappingVO>();
    	switch (type) {
		case SmartQuery_query:
			String qt = parameters.get("tables");
			tms = queryMappingbytables(StringUtils.split(qt,","));
			desc.append("对 ");
			for (int i = 0; i < tms.size(); i++) {
				DmTableMappingVO tm = tms.get(i);
				if(i != 0)
					desc.append("、");
				desc.append("“"+tm.getMappingName()+"”");
			}
			desc.append("进行数据查询操作");
			break;
		case SmartQuery_download:
			String dt = parameters.get("tables");
			tms = queryMappingbytables(StringUtils.split(dt,","));
			desc.append("对 ");
			for (int i = 0; i < tms.size(); i++) {
				DmTableMappingVO tm = tms.get(i);
				if(i != 0)
					desc.append("、");
				desc.append("“"+tm.getMappingName()+"”");
			}
			desc.append("进行数据下载操作");
			break;
		case SmartQuery_filesave:
			String fst = parameters.get("tables");
			String fsn = parameters.get("file");
			tms = queryMappingbytables(StringUtils.split(fst,","));			
			desc.append("对 ");
			for (int i = 0; i < tms.size(); i++) {
				DmTableMappingVO tm = tms.get(i);
				if(i != 0)
					desc.append("、");
				desc.append("“"+tm.getMappingName()+"”");
			}
			desc.append("进行数据文件的保存操作，保存文件名为"+fsn);
			break;
		case SmartQuery_taskcreator:
			String tt = parameters.get("tables");
			String cyc = parameters.get("cyc");
			int cycLen = Integer.parseInt(parameters.get("cycLen"));
			tms = queryMappingbytables(StringUtils.split(tt,","));
			desc.append("针对 ");
			for (int i = 0; i < tms.size(); i++) {
				DmTableMappingVO tm = tms.get(i);
				if(i != 0)
					desc.append("、");
				desc.append("“"+tm.getMappingName()+"”");
			}
			desc.append(" 的数据查询创建自动定时任务，文件生成周期为每");
			switch (cycLen) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				desc.append("两");
				break;
			default:
				desc.append(cycLen);
				break;
			}
			
			if(TackCycType.y.equals(cyc)){
				desc.append("年");
			}else if(TackCycType.m.equals(cyc)){
				desc.append("月");
			}else if(TackCycType.w.equals(cyc)){
				desc.append("周");
			}else{
				desc.append("天");
			}			
			break;	
		case Task_edit:
				String task = parameters.get("task");
				desc.append("对“"+task+"”自动任务进行修改操作");
				break;			
					
		case DataMarket_download:
			String dlfile = parameters.get("file");
			desc.append("对“"+dlfile+"”数据文件进行下载操作");			
			break;
		case DataMarket_delete:
			String delfile = parameters.get("file");
			desc.append("对“"+delfile+"”数据文件进行删除操作");			
			break;		
		case DataShare_catalog:
			String user = parameters.get("user");
			String path = parameters.get("path");
			String scUsers = parameters.get("shareUsers");
			
			desc.append("对"+user+"的“"+path+"”目录进行分享操作，分享用户为"+scUsers);			
			break;
		case ShareClean_catalog:
			String scuser = parameters.get("user");
			String scpath = parameters.get("path");
			
			desc.append("对"+scuser+"的“"+scpath+"”目录进行清空分享操作");			
			break;
		case DataShare_file:
			String sfile = parameters.get("file");
			String sfUsers = parameters.get("shareUsers");
			desc.append("对“"+sfile+"”数据文件进行分享操作，分享用户为"+sfUsers);			
			break;	
		case ShareClean_file:
			String scfile = parameters.get("file");
			desc.append("对“"+scfile+"”数据文件进行清空分享操作");			
			break;		
				
		default:
			break;
		}
    	return desc.toString();
	}

	public DimFilterFormulaDAO getDimFilterFormulaDAO() {
		return dimFilterFormulaDAO;
	}

	public void setDimFilterFormulaDAO(DimFilterFormulaDAO dimFilterFormulaDAO) {
		this.dimFilterFormulaDAO = dimFilterFormulaDAO;
	}

	public DimFilterRalationDAO getDimFilterRalationDAO() {
		return dimFilterRalationDAO;
	}

	public void setDimFilterRalationDAO(DimFilterRalationDAO dimFilterRalationDAO) {
		this.dimFilterRalationDAO = dimFilterRalationDAO;
	}

	public DimMColumnTypeDAO getDimMColumnTypeDAO() {
		return dimMColumnTypeDAO;
	}

	public void setDimMColumnTypeDAO(DimMColumnTypeDAO dimMColumnTypeDAO) {
		this.dimMColumnTypeDAO = dimMColumnTypeDAO;
	}

	public DimSqSortDAO getDimSqSortDAO() {
		return dimSqSortDAO;
	}

	public void setDimSqSortDAO(DimSqSortDAO dimSqSortDAO) {
		this.dimSqSortDAO = dimSqSortDAO;
	}




	public SqFilterInfoTempDAO getSqFilterInfoTempDAO() {
		return sqFilterInfoTempDAO;
	}

	public void setSqFilterInfoTempDAO(SqFilterInfoTempDAO sqFilterInfoTempDAO) {
		this.sqFilterInfoTempDAO = sqFilterInfoTempDAO;
	}

	public SqQueryInfoTempDAO getSqQueryInfoTempDAO() {
		return sqQueryInfoTempDAO;
	}

	public void setSqQueryInfoTempDAO(SqQueryInfoTempDAO sqQueryInfoTempDAO) {
		this.sqQueryInfoTempDAO = sqQueryInfoTempDAO;
	}

	public SqSelectInfoTempDAO getSqSelectInfoTempDAO() {
		return sqSelectInfoTempDAO;
	}

	public void setSqSelectInfoTempDAO(SqSelectInfoTempDAO sqSelectInfoTempDAO) {
		this.sqSelectInfoTempDAO = sqSelectInfoTempDAO;
	}

	public SqSortInfoTempDAO getSqSortInfoTempDAO() {
		return sqSortInfoTempDAO;
	}

	public void setSqSortInfoTempDAO(SqSortInfoTempDAO sqSortInfoTempDAO) {
		this.sqSortInfoTempDAO = sqSortInfoTempDAO;
	}

	public SqFilterInfoDAO getSqFilterInfoDAO() {
		return sqFilterInfoDAO;
	}

	public void setSqFilterInfoDAO(SqFilterInfoDAO sqFilterInfoDAO) {
		this.sqFilterInfoDAO = sqFilterInfoDAO;
	}

	public SqQueryInfoDAO getSqQueryInfoDAO() {
		return sqQueryInfoDAO;
	}

	public void setSqQueryInfoDAO(SqQueryInfoDAO sqQueryInfoDAO) {
		this.sqQueryInfoDAO = sqQueryInfoDAO;
	}

	public SqSelectInfoDAO getSqSelectInfoDAO() {
		return sqSelectInfoDAO;
	}

	public void setSqSelectInfoDAO(SqSelectInfoDAO sqSelectInfoDAO) {
		this.sqSelectInfoDAO = sqSelectInfoDAO;
	}

	public SqSortInfoDAO getSqSortInfoDAO() {
		return sqSortInfoDAO;
	}

	public void setSqSortInfoDAO(SqSortInfoDAO sqSortInfoDAO) {
		this.sqSortInfoDAO = sqSortInfoDAO;
	}

	public SqPersonalCatalogDAO getSqPersonalCatalogDAO() {
		return sqPersonalCatalogDAO;
	}

	public void setSqPersonalCatalogDAO(SqPersonalCatalogDAO sqPersonalCatalogDAO) {
		this.sqPersonalCatalogDAO = sqPersonalCatalogDAO;
	}

	public SqFileDataInfoDAO getSqFileDataInfoDAO() {
		return sqFileDataInfoDAO;
	}

	public void setSqFileDataInfoDAO(SqFileDataInfoDAO sqFileDataInfoDAO) {
		this.sqFileDataInfoDAO = sqFileDataInfoDAO;
	}

	public SqTaskInfoDAO getSqTaskInfoDAO() {
		return sqTaskInfoDAO;
	}

	public void setSqTaskInfoDAO(SqTaskInfoDAO sqTaskInfoDAO) {
		this.sqTaskInfoDAO = sqTaskInfoDAO;
	}

	public SqTaskStatusInfoDAO getSqTaskStatusInfoDAO() {
		return sqTaskStatusInfoDAO;
	}

	public void setSqTaskStatusInfoDAO(SqTaskStatusInfoDAO sqTaskStatusInfoDAO) {
		this.sqTaskStatusInfoDAO = sqTaskStatusInfoDAO;
	}

	public SqFileShareDAO getSqFileShareDAO() {
		return sqFileShareDAO;
	}

	public void setSqFileShareDAO(SqFileShareDAO sqFileShareDAO) {
		this.sqFileShareDAO = sqFileShareDAO;
	}

	public SqPersonalCatalogShareDAO getSqPersonalCatalogShareDAO() {
		return sqPersonalCatalogShareDAO;
	}

	public void setSqPersonalCatalogShareDAO(
			SqPersonalCatalogShareDAO sqPersonalCatalogShareDAO) {
		this.sqPersonalCatalogShareDAO = sqPersonalCatalogShareDAO;
	}


	public DmTableCatalogDAO getDmTableCatalogDAO() {
		return dmTableCatalogDAO;
	}

	public void setDmTableCatalogDAO(DmTableCatalogDAO dmTableCatalogDAO) {
		this.dmTableCatalogDAO = dmTableCatalogDAO;
	}

	public DmTableMappingDAO getDmTableMappingDAO() {
		return dmTableMappingDAO;
	}

	public void setDmTableMappingDAO(DmTableMappingDAO dmTableMappingDAO) {
		this.dmTableMappingDAO = dmTableMappingDAO;
	}

	public DmColumnMappingDAO getDmColumnMappingDAO() {
		return dmColumnMappingDAO;
	}

	public void setDmColumnMappingDAO(DmColumnMappingDAO dmColumnMappingDAO) {
		this.dmColumnMappingDAO = dmColumnMappingDAO;
	}

	public DmTableMappingAssociationDAO getDmTableMappingAssociationDAO() {
		return dmTableMappingAssociationDAO;
	}

	public void setDmTableMappingAssociationDAO(DmTableMappingAssociationDAO dmTableMappingAssociationDAO) {
		this.dmTableMappingAssociationDAO = dmTableMappingAssociationDAO;
	}

	public SqExceptionInfoDAO getSqExceptionInfoDAO() {
		return sqExceptionInfoDAO;
	}

	public void setSqExceptionInfoDAO(SqExceptionInfoDAO sqExceptionInfoDAO) {
		this.sqExceptionInfoDAO = sqExceptionInfoDAO;
	}

	public SqTableInfoTempDAO getSqTableInfoTempDAO() {
		return sqTableInfoTempDAO;
	}

	public void setSqTableInfoTempDAO(SqTableInfoTempDAO sqTableInfoTempDAO) {
		this.sqTableInfoTempDAO = sqTableInfoTempDAO;
	}

	public SqTableInfoDAO getSqTableInfoDAO() {
		return sqTableInfoDAO;
	}

	public void setSqTableInfoDAO(SqTableInfoDAO sqTableInfoDAO) {
		this.sqTableInfoDAO = sqTableInfoDAO;
	}

	public SqFilterParameterDAO getSqFilterParameterDAO() {
		return sqFilterParameterDAO;
	}

	public void setSqFilterParameterDAO(SqFilterParameterDAO sqFilterParameterDAO) {
		this.sqFilterParameterDAO = sqFilterParameterDAO;
	}

	public SqSelectColumnDAO getSqSelectColumnDAO() {
		return sqSelectColumnDAO;
	}

	public void setSqSelectColumnDAO(SqSelectColumnDAO sqSelectColumnDAO) {
		this.sqSelectColumnDAO = sqSelectColumnDAO;
	}

	public SqListFilterDescDAO getSqListFilterDescDAO() {
		return sqListFilterDescDAO;
	}

	public void setSqListFilterDescDAO(SqListFilterDescDAO sqListFilterDescDAO) {
		this.sqListFilterDescDAO = sqListFilterDescDAO;
	}

	public SqListFilterInfoDAO getSqListFilterInfoDAO() {
		return sqListFilterInfoDAO;
	}

	public void setSqListFilterInfoDAO(SqListFilterInfoDAO sqListFilterInfoDAO) {
		this.sqListFilterInfoDAO = sqListFilterInfoDAO;
	}

	public DmCatalogBindingDao getDmCatalogBindingDao() {
		return dmCatalogBindingDao;
	}

	public void setDmCatalogBindingDao(DmCatalogBindingDao dmCatalogBindingDao) {
		this.dmCatalogBindingDao = dmCatalogBindingDao;
	}
    
    
}


