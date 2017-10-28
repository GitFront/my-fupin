
package com.aspire.birp.modules.dataLabel.label.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CyclicBarrier;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.auth.modules.sys.constants.LogConstant;
import com.aspire.auth.modules.sys.entity.Role;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.bi.common.constant.DateConstant;
import com.aspire.bi.common.util.DateUtils;
import com.aspire.bi.common.util.StringUtils;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.base.constant.DataPeriodType;
import com.aspire.birp.modules.base.constant.IconSkin;
import com.aspire.birp.modules.base.thread.FileWriteTaskThread;
import com.aspire.birp.modules.base.thread.SqlReadThread;
import com.aspire.birp.modules.base.utils.Utils;
import com.aspire.birp.modules.base.vo.ProgressObject;
import com.aspire.birp.modules.dataLabel.base.constant.DataLabelConstant;
import com.aspire.birp.modules.dataLabel.base.constant.LabelNodeType;
import com.aspire.birp.modules.dataLabel.base.service.DlBaseService;
import com.aspire.birp.modules.dataLabel.config.entity.DmLabelTreeDef;
import com.aspire.birp.modules.dataLabel.config.service.DlConfigService;
import com.aspire.birp.modules.dataLabel.config.vo.DmLabelTreeDefVO;
import com.aspire.birp.modules.dataLabel.label.service.DlUserAnalysisService;
import com.aspire.birp.modules.dataLabel.label.vo.LabelTreeObject;
import com.aspire.birp.modules.dataMapping.mapping.service.DmMappingService;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableMappingVO;
import com.aspire.birp.modules.smartQuery.base.constant.FileProperties;
import com.aspire.birp.modules.smartQuery.base.constant.OptionType;
import com.aspire.birp.modules.smartQuery.query.service.SqQueryService;
import com.aspire.birp.modules.sys.utils.UserUtils;

/**  
 * @Title: 数据标签用户分析功能服务实现类
 * @Description: 用于实现数据标签用户分析功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年5月5日 下午2:58:35
 * @version: V1.0
 */
@Service
public class DlUserAnalysisServiceImpl extends DlBaseService implements
		DlUserAnalysisService {
	
	private static Logger log = LoggerFactory.getLogger(DlUserAnalysisServiceImpl.class);
	

    private static final String FUNCTION = "客户标签";
	
	/*数据标签配置服务类*/
	@Autowired
	private DlConfigService dlConfigService;
	
	/*映射管理服务类*/
	@Autowired
	private DmMappingService sqMappingService;
	
	/*智能查询服务类*/
	@Autowired
	private SqQueryService sqQueryService;
	/*线程池管理*/
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	
	/*分页导出单线程最大查询数*/
	private static final int MAX_PERCENT_DOWNLOAD_NUM = 150000;
	
	/**
	 * sql缓存库，主要用于导出数据时使用
	 * key:searchKey,标识单次查询
	 * @param String sql 存储sql语句
	 * @param Map mapper 存储列的语义信息
	 */
	private static Map<String,Map<String,Object>> sqlPool;
	
	/**
	 * 导出文件进度缓存库，主要用于导出数据时使用
	 * key:searchKey标识单次导出
	 * value:本次导出的进度数据
	 */
	private static Map<String,ProgressObject> loadPool;
	

	public DlUserAnalysisServiceImpl() {
		super();
		sqlPool = new HashMap<String, Map<String,Object>>();
		loadPool = new HashMap<String, ProgressObject>();
	}
	
	public static void clearSqlPool() {
		log.info("清空SQL缓存库信息");
		DlUserAnalysisServiceImpl.sqlPool.clear();
	}
	
	public static void clearLoadPool() {
		log.info("清空导出文件进度缓存库信息");
		DlUserAnalysisServiceImpl.loadPool.clear();
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.label.service.DlUserAnalysisService#queryLabelTree()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<LabelTreeObject> queryLabelTree() {
		List<LabelTreeObject> vos = new ArrayList<LabelTreeObject>();
		String queryString = "from DmLabelTreeDef where pid <> ? and active = ? order by sort";
		List<?> rs = getHibernateTemplate().find(queryString,
				new Object[]{DataLabelConstant.EDIT_LABEL_TREE_ROOT_NODE_ID,DataLabelConstant.COMMON_FLAG_TRUE});
		if(rs != null && !rs.isEmpty()){
			for (Object obj : rs) {
				DmLabelTreeDef def = (DmLabelTreeDef) obj;
				String type = def.getType();
				boolean isLabel = LabelNodeType.label.toString().equals(type);
				LabelTreeObject vo = new LabelTreeObject(def.getId(),def.getName(),def.getPid());
				vo.setNodeType(type);
				vo.setParent(true);
				vo.setIconSkin(IconSkin.folder.toString());
				if(isLabel){					
					vo.setAssoTableId(def.getAssoTableId());
					vo.setAssoTable(def.getAssoTable());
					vo.setAssoFieldId(def.getAssoFieldId());
					vo.setAssoField(def.getAssoField());
					vo.setAssoDimId(def.getAssoDimId());
					vo.setAssoDim(def.getAssoDim());
					vo.setAssoRule(def.getAssoRule());
					vo.setTopNum(def.getTopNum());
					vo.setIconSkin(IconSkin.property.toString());					
				}				
				vos.add(vo);
				/*查询对应的分层信息
				if(isLabel)
					vos.addAll(this.queryDimLabel(vo));*/
			}
		}
		return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.label.service.DlUserAnalysisService#queryLabelData(java.util.String,java.util.List, java.util.List, java.util.List)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> queryLabelData(String searchKey,List<String> labelIDs,List<String[]> values, List<String> times,String searchNum,
			int page, int rows) {
		/**
		 * 查询标签表数据
		 * select 
			       TA_TARGET_CUSTOMER_D_20160504.User_Num as User_Num,
			       DIM_TAG_BRAND_.BRAND_NAME as CM_BRAND, 
			       DIM_TAG_VIP_TYPE_.VIP_TYPE_NAME as VIP_TYPE,
			       DIM_TAG_JUDGE_.JUDGE_NAME AS IS_4G_CUST
			       
			from (SELECT * FROM TA_TARGET_CUSTOMER_D WHERE STAT_TIME='20160504') TA_TARGET_CUSTOMER_D_20160504,
			     (SELECT * FROM TA_TARGET_CUSTOMER_M WHERE STAT_MONTH='201604') TA_TARGET_CUSTOMER_M_201604,
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
		Map<String, Object> rs = new HashMap<String, Object>();
		if(labelIDs != null){
			/*获取当前用户数据*/
			User user = UserUtils.getUser();
			
			/*加载数据表的数据*/
			Map<String,String> tableAlias = new HashMap<String,String>();
			Map<String,String> dimAlias = new HashMap<String,String>();
			
			/*字段对应关系*/
			List<String> columns = new ArrayList<String>();
			columns.add(DataLabelConstant.LABEL_USER_NUM_FIELD);
			LinkedHashMap<String, String> mapper = new LinkedHashMap<String, String>();
			mapper.put(DataLabelConstant.LABEL_USER_NUM_FIELD, "用户号码");
			
			/*数据标签的描述信息*/
			StringBuffer labels = new StringBuffer();
			
			/*事实表别名标识*/
			String ta = "";
			StringBuffer selectSQL = new StringBuffer(" SELECT " + paginationDual + " ");
			StringBuffer fromSQL = new StringBuffer(" FROM ");
			StringBuffer filterSQL = new StringBuffer(" WHERE 1=1 ");
			for (int i = 0; i < labelIDs.size(); i++) {
				
				DmLabelTreeDefVO def = dlConfigService.queryLabelDef(labelIDs.get(i));
				String table = def.getAssoTable();
				String field = def.getAssoField();
				String period = times.get(i);
				columns.add(field);
				mapper.put(field, def.getName());
				/*处理数据表SQL别名*/
				String tableAlia = this.getAliasForSuffix(table, period);
				if(!tableAlias.containsKey(tableAlia)){
					String tableId = def.getAssoTableId();
					DmTableMappingVO tableMapping = sqMappingService.queryTableMappingById(tableId);
					/*是否加入权限控制*/
					String userAcc = null;
					if(DataLabelConstant.COMMON_FLAG_TRUE.equals(tableMapping.getIsPerimssion()))
						userAcc = user.getLoginName();
					
					String tableSQL = this.getUserPermissionSQL(userAcc, table, period);
					tableAlias.put(tableAlia, tableSQL);				
				}
				/*获取当前字段的维表别名*/
				String dimTable = def.getAssoDim();
				String dimAlia = getAliasForSuffix(dimTable, field);
				if(!dimAlias.containsKey(dimAlia)){			
					String tableSQL = this.getUserPermissionSQL(null, dimTable, null);
					dimAlias.put(dimAlia, tableSQL);				
				}			
				String[] rule = StringUtils.split(def.getAssoRule(), DataLabelConstant.LABEL_DIM_ASSO_RULE_SEPARATOR);
				String dimId = rule[0]; /*维表的ID字段*/
				String dimName = rule[1]; /*维表的名称字段*/
				/*加入用户号码列显示*/
				if(i == 0){
					selectSQL.append(tableAlia+"." + DataLabelConstant.LABEL_USER_NUM_FIELD + " AS "+ DataLabelConstant.LABEL_USER_NUM_FIELD);
					ta = tableAlia;
				}
					
				selectSQL.append(", "+dimAlia+"." + dimName + " AS "+ field);
				/*加入维表关联信息*/
				filterSQL.append(" AND "+ tableAlia+"."+field+"="+dimAlia+"."+dimId+"(+)");
				
				/*加入值过滤信息*/
				if(values.get(i).length != 0){
					String value = com.aspire.bi.common.util.StringUtils.splitStringForArray(values.get(i), ",");
					filterSQL.append(" AND "+ tableAlia+"."+field+" IN ("+value+")");
					if(labels.length() != 0)
						labels.append("、");
					labels.append(getPropertyDesc(def.getName(), dimTable, dimId, dimName, value));					
				}			
			}
			
			/*处理数据表关系及关联信息*/
			boolean first = true;
			/*上一周期的数据表别名*/
			String lastTable = "";
			for (String alias : tableAlias.keySet()) {
				if(first){
					first = false;
					lastTable = alias;
				}else{
					fromSQL.append(",");
					filterSQL.append(" AND "+lastTable+"."+DataLabelConstant.LABEL_USER_NUM_FIELD + "=" + alias+"."+DataLabelConstant.LABEL_USER_NUM_FIELD);
					lastTable = alias;
				}
				fromSQL.append( " "+tableAlias.get(alias) + " " + alias);
			}
			
			/*处理维度表关系*/
			for (String alias : dimAlias.keySet()) {
				fromSQL.append( ", "+dimAlias.get(alias) + " " + alias);
			}
			
			/*处理用户号码查询条件*/
			if(StringUtils.isNotBlank(searchNum))
				filterSQL.append(" AND "+ ta+"."+DataLabelConstant.LABEL_USER_NUM_FIELD +" LIKE '"+searchNum+"%'");
			
			String sql = selectSQL.toString()+fromSQL.toString()+filterSQL.toString();		
			
			rs = this.runSQLForGrid(sql, null, columns, page, rows);
			
			Map<String,Object> sqlParam = new HashMap<String, Object>();
			sqlParam.put("sql", sql);
			sqlParam.put("mapper", mapper);
			sqlParam.put("columns", columns);
			sqlParam.put("labels", labels.toString());
			
			sqlPool.put(searchKey, sqlParam);
			
			/*添加数据标签查询日志*/			
			Map<String,String> lp = new HashMap<String, String>();
			lp.put("labels", labels.toString());
			markOptionLogger(FUNCTION, OptionType.label_search, lp,LogConstant.OPER_QUERY);
			
		}else{
			Map<String,Object> sqlParam = sqlPool.get(searchKey);
			String sql = String.valueOf(sqlParam.get("sql"));
			@SuppressWarnings("unchecked")
			List<String> columns = (List<String>) sqlParam.get("columns");
			
			rs = this.runSQLForGrid(sql, null, columns, page, rows);			
		}
		return rs;
	}
	
	/**
	 * 获取单个属性的标签描述
	 * @param property 属性名
	 * @param dimTable 维表名称
	 * @param dimId 维表ID字段名
	 * @param dimName 维表名称字段名
	 * @param values 值的集合，例如：'1','2','3'
	 * @return
	 * @author 张健雄
	 */
	private String getPropertyDesc(String property,String dimTable,String dimId,String dimName,String values){
		StringBuffer proDesc = new StringBuffer();
		proDesc.append("【"+property+"】《");
		String sql = "SELECT "+dimName+" FROM " + dimTable + " WHERE " + dimId + " IN (" + values + ")";
		List<T> list = this.excuteSQLQuery(sql);
		if(list != null && !list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				String label = String.valueOf(list.get(i));
				if(i != 0)
					proDesc.append(",");
				proDesc.append(label);
					
			}
		}
		proDesc.append("》");		
		return proDesc.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.label.service.DlUserAnalysisService#queryLastPeriod()
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Map<String, String>> queryLastPeriod() {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String sql = "SELECT table_name,stat_time,period FROM TA_T_LAST_STAT_TIME ";
		List<Object[]> rs = this.excuteSQLQuery(sql);
		
		if(rs != null && !rs.isEmpty()){
			for (Object[] objects : rs) {
				String tableName = String.valueOf(objects[0]);
				String statTime = String.valueOf(objects[1]);	
				String period = String.valueOf(objects[2]);				
				Map<String,String> map = new HashMap<String, String>();
				map.put("tableName",tableName);
				map.put("period",period);
				map.put("time", statTime);
				if(DataLabelConstant.LABEL_PERIOD_TYPE_DAY.equals(period)){
					map.put("timeValue", DateUtils.dateTimeToString(DateUtils.stringToDate(statTime, DateConstant.YEARMOUTHDAY), DateConstant.YEAR_MOUTH_DAY));
				}else{
					map.put("timeValue", DateUtils.dateTimeToString(DateUtils.stringToDate(statTime, DateConstant.YEARMOUTHDAY), DateConstant.YEAR_MOUTH));
				}
				
				list.add(map);				
			}
		}		
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.label.service.DlUserAnalysisService#queryDimValue(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String queryDimValue(String dimValue, String labelID) {
		String value = "";
		if(StringUtils.isBlank(labelID) || StringUtils.isBlank(dimValue))
			return value;
		DmLabelTreeDefVO label = dlConfigService.queryLabelDef(labelID);
		if(label == null)
			return value;
		String dimTable = label.getAssoDim();
		String assoRule = label.getAssoRule();
		String[] rule = StringUtils.split(assoRule, DataLabelConstant.LABEL_DIM_ASSO_RULE_SEPARATOR);
		String dimId = rule[0]; /*维表的ID字段*/
		String dimName = rule[1]; /*维表的名称字段*/
		
		String sql = "SELECT "+dimName+" FROM "+dimTable+" WHERE "+dimId+"=?";
		
		List<Object> rs = this.excuteSQLQuery(sql, new Object[]{dimValue});
		if(rs == null || rs.isEmpty())
			return value;
		value = String.valueOf(rs.get(0));
		
		return value;
	}

	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.label.service.DlUserAnalysisService#queryPeriodList(java.lang.String)
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Map<String,String>> queryPeriodList(String tableName) {
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
	
	/**
	 * 通过用户定义的标签属性查询对应的分层信息
	 * @param pid 标签ID属性
	 * @return
	 * @author 张健雄
	 */
	public List<LabelTreeObject> queryDimLabel(String pid){
		List<LabelTreeObject> dims = new ArrayList<LabelTreeObject>();
		if(StringUtils.isBlank(pid))
			return dims;
		DmLabelTreeDefVO label = dlConfigService.queryLabelDef(pid);
	
		String sql = this.createDimSQL(label);
		
		/*获取维表值统计数据*/
		Map<String,Integer> dimCount = this.queryDimCount(pid,label.getAssoTable());
		
		List<Object[]> rs = this.excuteSQLQuery(sql);
		if(rs != null && !rs.isEmpty()){
			for (Object[] obj : rs) {
				String dimKey = String.valueOf(obj[0]);
				String dimValue = String.valueOf(obj[1]);
				String id = UUID.randomUUID().toString();
				StringBuffer name = new StringBuffer(dimValue + " ");
				if(dimCount.containsKey(dimKey)){
					name.append("("+dimCount.get(dimKey)+")");
				}else if(!dimCount.containsKey(dimKey)){
					name.append("(0)");
				}
				LabelTreeObject dim = new LabelTreeObject(id,name.toString(),pid);
				dim.setNodeType(LabelNodeType.layer.toString());
				dim.setIconSkin(IconSkin.tag.toString());
				dim.setDimKey(dimKey);
				dim.setParent(false);
				dims.add(dim);
			}
			/*添加“全部”的选择标识*/
			String id = UUID.randomUUID().toString();
			String key = DataLabelConstant.LABEL_DIM_ALL_PREFIX+pid;
			LabelTreeObject dimAll = new LabelTreeObject(id,"全部",pid);
			dimAll.setIconSkin(IconSkin.tag.toString());
			dimAll.setDimKey(key);
			dims.add(dimAll);
			
		}		
		return dims;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * saveTempDatabySqId(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, String> saveTempData(String searchKey,int total) {
		
		
		Map<String, String> fileinfo = this.createLoadingFile(searchKey,total);
		String filePath = fileinfo.get("path");

		Map<String, String> info = new HashMap<String, String>();
		info.put("name", "数据标签-目标客户信息"+ DateUtils.dateTimeToString(new Date(), DateConstant.YEARMOUTHDAY) + ".csv");
		info.put("path", filePath);
		
		return info;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aspire.birp.modules.smartQuery.query.service.SqQueryService#
	 * saveTempDatabySqId(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, String> saveTempForThread(String searchKey,int total) {
		
		/*创建数据进度对象*/
		ProgressObject p = new ProgressObject(total, 0, 0,false);
		loadPool.put(searchKey, p);

		/* 获取当前查询标识的查询信息 */		
		Map<String,Object> sqlParam = sqlPool.get(searchKey);
		String sql = String.valueOf(sqlParam.get("sql"));
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> mapper = (LinkedHashMap<String, String>) sqlParam.get("mapper");
		
		/* 创建临时文件夹 */
		String filePath = FileProperties.getTempFilePath() + System.getProperty("file.separator");

		File path = new File(filePath);

		if (!path.isDirectory())
			path.mkdirs();
		
		/*生成带标题信息的csv文件*/
		File csv = CSVUtils.createEmptyCSV(mapper, filePath, "TEMP_FILE_");
		
		/*获取数据文件路径信息*/
		String name = csv.getName();
		
		/*多线程查询生成数据文件*/
		this.createFileForThread(searchKey, total, sql, mapper, csv);
		
		Map<String, String> info = new HashMap<String, String>();
		info.put("name", "数据标签-目标客户信息"+ DateUtils.dateTimeToString(new Date(), DateConstant.YEARMOUTHDAY) + ".csv");
		info.put("path", filePath + System.getProperty("file.separator") + name);

		/*记录操作日志信息-下载操作*/
		String labels = String.valueOf(sqlParam.get("labels"));
		Map<String,String> lp = new HashMap<String, String>();
		lp.put("labels", labels);
		lp.put("totalCount", String.valueOf(total));
		markOptionLogger(FUNCTION, OptionType.label_export, lp,LogConstant.OPER_FILE_DOWNLOAD);
		
		return info;
	}
	

	/**
	 * 通过标签属性创建分层信息的读取SQL
	 * @param label
	 * @return
	 * @author 张健雄
	 */
	private String createDimSQL(DmLabelTreeDefVO label){
		/*查询对应维度表数据*/
		String dimTable = label.getAssoDim();
		String assoRule = label.getAssoRule();
		String[] rule = StringUtils.split(assoRule, DataLabelConstant.LABEL_DIM_ASSO_RULE_SEPARATOR);
		String dimId = rule[0]; /*维表的ID字段*/
		String dimName = rule[1]; /*维表的名称字段*/
		String dimSort = rule[2]; /*维表的排序字段*/
		String dimTop = rule[3]; /*维表的排名字段*/
		/*动态生成查询SQL*/
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT d." + dimId + " ,d." + dimName + " FROM " + dimTable + " d ");
		/*关联数据信息表*/
		sql.append("WHERE 1=1 AND d."+DataLabelConstant.LABEL_DIM_VAILD_FIELD + "='"+DataLabelConstant.COMMON_FLAG_TRUE+"' ");
		/*读取维表的TOP排名信息*/
		int topNum = label.getTopNum();
		if(!DataLabelConstant.LABEL_DIM_RULE_NULL.equals(dimTop) && topNum != 0)
			sql.append(" AND d." + dimTop +" <= " + topNum + " ");
		/*读取维表的排序信息*/
		if(!DataLabelConstant.LABEL_DIM_RULE_NULL.equals(dimSort))
			sql.append(" ORDER BY d." + dimSort);
		
		return sql.toString();
	}
	
	/**
	 * 通过标签ID获取分层数据的统计数
	 * @param labelID 标签ID值
	 * @param tableName 标签关联的表名称
	 * @return
	 * @author 张健雄
	 */
	private Map<String,Integer> queryDimCount(String labelID,String tableName){
		/*查询标签属性的数据周期*/
		String period = DataPeriodType.D.toString();
		String dpsql = "SELECT PERIOD FROM "+DataLabelConstant.DB_TABLE_DATA_MARKET_PERIOD + " WHERE TABLE_NAME = '" + tableName + "'";
		List<Object> rsp = this.excuteSQLQuery(dpsql);
		if(rsp != null && !rsp.isEmpty())
			period = rsp.get(0).toString().trim();
		
		String calcTable = DataLabelConstant.DB_TABLE_LABEL_STATISTICS_D;
		
		if(DataPeriodType.M.toString().equals(period))
			calcTable = DataLabelConstant.DB_TABLE_LABEL_STATISTICS_M;
		
		Map<String,Integer> count = new HashMap<String, Integer>();
		/*动态生成查询SQL*/
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT i.dim_tab_value , c.tag_usr_cnt  FROM "+DataLabelConstant.DB_TABLE_LABEL_DEFINE+" i , "+calcTable+" c ");
		/*关联数据信息表*/
		sql.append("WHERE i.tag_id = c.tag_id ");
		/*定位数据周期*/
		sql.append(" AND EXISTS (SELECT stat_time FROM "+DataLabelConstant.DB_TABLE_DATA_MARKET_PERIOD+" WHERE stat_time=c.stat_time and table_name='"+calcTable+"') ");
		/*定位分组信息*/
		sql.append(" AND i.tag_grp_front_id='"+labelID+"' ");
		
		List<Object[]> rs = this.excuteSQLQuery(sql.toString());
		if(rs != null && !rs.isEmpty()){
			for (Object[] obj : rs) {
				String dimKey = String.valueOf(obj[0]);
				Integer dimCount = Integer.valueOf(String.valueOf(obj[1]));
				count.put(dimKey, dimCount);
			}			
		}
		return count;
	}

	
	/**
	 * 获取指定用户的权限SQL信息及指定周期数据
	 * 
	 * @param userAcc 当前用户的账号
	 * @param table 对应查询数据权限的数据表
	 * @param period 数据表周期
	 * @return
	 * @author 张健雄
	 */
	private String getUserPermissionSQL(String userAcc, String table,String period) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("( SELECT * FROM " + table + " WHERE 1=1 ");
		if(StringUtils.isNotBlank(userAcc)){
			User user = UserUtils.getUser(userAcc);
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
			sql.append(")) WHERE cmcc_branch_cd=parent_code AND area_cd=area_code )");
		}
		
		/*加入数据周期过滤*/
		if(StringUtils.isNotBlank(period)){
			sql.append(" AND " + DataLabelConstant.LABEL_PERIOD_FIELD + " = "+period+" ");
		}		
		sql.append(")");
		return sql.toString();
	}
	
	/**
	 * 通过别名后缀获取SQL统一的识别别名
	 * 
	 * @param table  数据表名
	 * @param suffix 别名后缀
	 * @return
	 * @author 张健雄
	 */
	public String getAliasForSuffix(String table,String suffix) {

		String alias =  table + "_" ;
		if(StringUtils.isNotBlank(suffix))
			alias += suffix;

		if (alias.length() >= 30) 
			alias = alias.substring(alias.length()-30);	
		while (alias.startsWith("_")) {
			alias = alias.substring(1);			
		}
		return alias;
	}
	
	/**
	 * 通过SQL语句生成CSV文件
	 * 
	 * @param searchKey 数据查询标识
	 * @param total 数据总条数
	 * @author 张健雄
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> createLoadingFile(String searchKey,int total) {
		
		/*创建数据进度对象*/
		ProgressObject p = new ProgressObject(total, 0, 1,false);
		if(loadPool.containsKey(searchKey))
			loadPool.remove(searchKey);
		loadPool.put(searchKey, p);
		
		Map<String, String> fileinfo = new HashMap<String, String>();

		/* 获取当前查询标识的查询信息 */
		
		Map<String,Object> sqlParam = sqlPool.get(searchKey);
		String sql = String.valueOf(sqlParam.get("sql"));
		Map<String,Object> parameters = new HashMap<String,Object>();
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> mapper = (LinkedHashMap<String, String>) sqlParam.get("mapper");
		
		/* 用于保存SQL列读取信息 */
		List<String> columnReader = new ArrayList<String>();
		for (Iterator propertyIterator = mapper.entrySet().iterator(); propertyIterator.hasNext();) {
			java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
			columnReader.add(propertyEntry.getKey().toString().trim());
		}

		/* 创建临时文件夹 */
		String filePath = FileProperties.getTempFilePath() + System.getProperty("file.separator");

		File path = new File(filePath);

		if (!path.isDirectory())
			path.mkdirs();

		/*生成带标题信息的csv文件*/
		File csv = CSVUtils.createEmptyCSV(mapper, filePath, "TEMP_FILE_");
		/*获取统计导出总数SQL 
		String count = countSQL + sql.substring(sql.indexOf("FROM"));
		 查询导出总条数 
		List<Object> countRs = this.excuteSQLQuery(count, parameters);
		int countRow = Integer.parseInt(String.valueOf(countRs.get(0)));*/
		
		/*计算分页查询页数与分页导出数据并写入文件*/
		final int x = 50000;
		for (int i = 0; i < (total/x)+1; i++) {
			int page = i+1;
			List<Map<String, Object>> rows = this.runSQL(sql,parameters,columnReader,page, x);
			csv = CSVUtils.appendCSV(rows, mapper, csv);
			loadPool.get(searchKey).setFinished(page*x);
			Double progress = com.aspire.bi.common.util.Utils.divide(Double.valueOf(page*x), Double.valueOf(total), 2);
			loadPool.get(searchKey).setProgress((int)(progress*100));
		}
		/*设置完成进度*/
		loadPool.get(searchKey).setFinish(true);
		
		String name = csv.getName();

		long size = csv.length();

		fileinfo.put("searchKey", searchKey);
		fileinfo.put("name", name);
		fileinfo.put("path", filePath + System.getProperty("file.separator") + name);
		fileinfo.put("size", String.valueOf(size));
		fileinfo.put("type", "csv");

		return fileinfo;
	}
	
	/**
	 * 通过SQL语句生成CSV文件(多线程执行)
	 * 
	 * @param searchKey 数据查询标识
	 * @param totalRecords 数据总条数
	 * @param sql 数据查询SQL
	 * @param mapper sql查询字段映射关系
	 * @param csv 原始的数据文件
	 * @author 张健雄
	 */
	@SuppressWarnings("rawtypes")
	private void createFileForThread(String searchKey,int totalRecords,String sql,LinkedHashMap<String, String> mapper,File csv) {
		
		/*创建数据进度对象*/
		ProgressObject p = new ProgressObject(totalRecords, 0, 1,false);
		if(loadPool.containsKey(searchKey))
			loadPool.remove(searchKey);
		loadPool.put(searchKey, p);
		
		/* 用于保存SQL列读取信息 */
		List<String> columnReader = new ArrayList<String>();
		for (Iterator propertyIterator = mapper.entrySet().iterator(); propertyIterator.hasNext();) {
			java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
			columnReader.add(propertyEntry.getKey().toString().trim());
		}
		
		/*计算分页查询页数与分页导出数据并写入文件*/
		int PercentNum = this.getPercentNumber(totalRecords);		
		
		int temp = totalRecords / PercentNum;
        int queryCount = (totalRecords % PercentNum) != 0 ? temp + 1 : temp;
		
        List<Map<String, Object>> resultList = Collections.synchronizedList(new ArrayList<Map<String, Object>>());// 线程安全的List
        // 同步辅助类,当所有子线程全部执行结束后来执行不同辅助类指定的同步线程FileWriteTaskThread
        CyclicBarrier cyclicBarrier = new CyclicBarrier(queryCount, new FileWriteTaskThread(resultList,mapper,csv,searchKey,loadPool));
        
        for (int i = 1; i <= queryCount; i++) {

            SqlReadThread readThread = new SqlReadThread();
            readThread.setBaseService(this);
            readThread.setColumnReader(columnReader);
            readThread.setCyclicBarrier(cyclicBarrier);
            readThread.setLoadPool(loadPool);
            readThread.setPage(i);
            readThread.setPageSize(PercentNum);
            readThread.setResultList(resultList);
            readThread.setSearchKey(searchKey);
            readThread.setSql(sql);
            readThread.setTotalRecords(totalRecords);
            
            // 启动多线程来从数据库中分页读取数据
            taskExecutor.execute(readThread);
        }
        /*taskExecutor.shutdown();*/
	}

	/**
	 * 通过记录总数计算线程数
	 * @param totalRecords
	 * @return
	 * @author 张健雄
	 */
	private Integer getPercentNumber(int totalRecords){
		Integer partition = 1;
		if(totalRecords>100 && totalRecords<=1000){
			partition = 5;
		}else{
			partition = 10;
		}
		Double temp = com.aspire.bi.common.util.Utils.divide(Double.valueOf(totalRecords),Double.valueOf(partition),0);
		Integer number = temp.intValue();
		if(number > MAX_PERCENT_DOWNLOAD_NUM)
			number = MAX_PERCENT_DOWNLOAD_NUM;
		return number;
	}
	
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.dataLabel.label.service.DlUserAnalysisService#queryLoadStatus(java.lang.String)
	 */
	@Override
	public ProgressObject queryLoadStatus(String searchKey) {
		if(StringUtils.isBlank(searchKey))
			return new ProgressObject(0, 0, 0, false);
		return loadPool.get(searchKey);
	}

}


