
package com.aspire.birp.modules.smartQuery.query.service;

import java.util.List;
import java.util.Map;

import com.aspire.birp.modules.common.vo.GroupDataVo;
import com.aspire.birp.modules.common.vo.PieDataVo;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO;
import com.aspire.birp.modules.smartQuery.base.entity.DimFilterFormula;
import com.aspire.birp.modules.smartQuery.base.entity.DimFilterRalation;
import com.aspire.birp.modules.smartQuery.base.entity.DimSqSort;
import com.aspire.birp.modules.smartQuery.base.vo.SqDataGridObject;
import com.aspire.birp.modules.smartQuery.query.vo.SqColumnMappingTree;
import com.aspire.birp.modules.smartQuery.query.vo.SqFilterInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqFilterVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSelectInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSelecterVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSortInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqTableInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqTableMappingTree;


/**  
 * @Title: 智能查询功能服务接口类
 * @Description: 用于定义智能查询功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月22日 下午2:29:38
 * @version: V1.0
 */
public interface SqQueryService {

	/**
	 * 查询宽表映射展示树
	 * @return
	 * @author 张健雄
	 * @param id 
	 */
	public List<SqTableMappingTree> queryTableMappingTree(String type);
	
	/**
	 * 查询字段映射展示树
	 * @param check 是否显示复选框
	 * @return
	 * @author 张健雄
	 */
	public List<SqColumnMappingTree> queryColumnMappingTree(boolean check);
	
	/**
	 * 查询映射表集合可用数据列表
	 * @param tableids
	 * @param type 
	 * @return
	 * @author 张健雄
	 */
	public List<DmColumnMappingVO> queryColumnListByTables(String tableids,String type);
	
	/**
	 * 查询未选择的映射数据列表信息
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public List<DmColumnMappingVO> queryUnselectColumnList(String sqId, String isTemp);
	
	/**
	 * 查询已选择的映射数据表信息
	 * @param sqId
	 * @param isTemp
	 * @return
	 * @author 张健雄
	 */
	public List<SqTableInfoVO> querySqTableInfoList(String sqId, String isTemp);
	/**
	 * 查询已选择的映射数据列信息
	 * @param sqId 查询ID值
	 * @param isTemp 是否为临时数据
	 * @return 返回已配置映射表的数据列信息
	 * @author 张健雄
	 */
	public List<SqSelectInfoVO> querySqSelectInfoList(String sqId,String isTemp);	
	
	/**
	 * 查询已选择的映射数据列信息
	 * @param sqId 查询ID值
	 * @param isTemp 是否为临时数据
	 * @param types 查询某种数据类型的数据列
	 * @return 返回已配置映射表的数据列信息
	 * @author 张健雄
	 */
	public List<DmColumnMappingVO> querySqSelectInfoListByType(String sqId,String isTemp,String[] types);	
	
	/**
	 * 通过映射表ID创建一个临时查询对象
	 * @param tableids 映射表ID值集合（以,号分隔）
	 * @return
	 * @author 张健雄
	 */
	public SqQueryInfoVO saveSqQueryInfoTemp(String tableids);
	
	/**
	 * 通过智能查询ID查询一个查询信息对象
	 * @param sqId 智能查询ID
	 * @return 
	 * @author 张健雄
	 */
	public SqQueryInfoVO queryQueryConfigById(String sqId);
	
	/**
	 * 通过已移除的映射字段ID集清除查询显示列
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @param mpColumnIds 映射字段ID集
	 * @return 是否移除成功
	 * @author 张健雄
	 */
	public boolean removeSqSelectInfo(String sqId,String isTemp,List<String> mpColumnIds);
	/**
	 * 通过已选择的映射字段ID集保存查询显示列
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @param mpColumnIds 映射字段ID集
	 * @return 是否保存成功
	 * @author 张健雄
	 */
	public boolean saveSqSelectInfo(String sqId,String isTemp,List<String> mpColumnIds);
	
	/**
	 * 查询过滤条件列表信息
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public List<SqFilterInfoVO> querySqFilterInfoList(String sqId,String isTemp);
	
	/**
	 * 更新或保存查询过滤条件
	 * @param filter 过滤条件的数据属性
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public boolean saveOrUpdateSqFilterInfo(SqFilterInfoVO filter,String isTemp);
	
	/**
	 * 通过过滤条件ID查询过滤条件
	 * @param id 过滤条件ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public SqFilterInfoVO querySqFilterInfoById(String id,String isTemp);
	
	/**
	 * 删除查询过滤条件
	 * @param id 过滤条件ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public boolean removeSqFilterInfo(String id,String isTemp);
	
	/**
	 * 查询排序方式列表信息
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public List<SqSortInfoVO> querySqSortInfoList(String sqId,String isTemp);
	
	/**
	 * 更新或保存查询排序方式
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @param data 排序方式的数据属性
	 * @return
	 * @author 张健雄
	 */
	public boolean saveOrUpdateSqSortInfo(String sqId,String isTemp,SqSortInfoVO data);
	
	/**
	 * 删除查询排序方式
	 * @param id 排序方式ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public boolean removeSqSortInfo(String id,String isTemp);
	
	
	/*维表操作服务*/
	/**
     * 查询过滤条件关系维表信息
     * @return
     * @author 张健雄
     */
	public List<DimFilterRalation> queryFilerRalation();
	/**
	 * 通过过滤条件ID查询关系名称信息
	 * @param ralationId
	 * @return
	 * @author 张健雄
	 */
	public String queryRalationName(String ralationId);
	/**
     * 查询过滤条件匹配公式维表信息
     * @param type 针对的字段类型
     * @return
     * @author 张健雄
     */
	public List<DimFilterFormula> queryFilerFormula(String type);
	/**
	 * 通过过滤条件ID查询匹配公式名称信息
	 * @param id
	 * @return
	 * @author 张健雄
	 */
	public String queryFormulaName(String id);
	/**
     * 查询排序方式维表信息
     * @return
     * @author 张健雄
     */
	public List<DimSqSort> querySortType();
	/**
	 * 通过ID查询排序方式名称信息
	 * @param id
	 * @return
	 * @author 张健雄
	 */
	public String querySortTypeName(String id);
	
	/**
	 * 判断查询ID的有效性
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public boolean smartQueryValid(String sqId,String isTemp);
	
	/**
	 * 生成并更新动态SQL信息，并保存在查询对象中
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public boolean updateSmartQuerySql(String sqId,String isTemp);
	
	/**
	 * 通过查询ID生成临时CSV文件，并返回文件保存路径
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @return 返回文件信息
	 * @author 张健雄
	 */
	public Map<String,String> saveTempDatabySqId(String sqId,String isTemp);
	
	/**
	 * 通过查询ID生成动态SQL，并执行查询语句返回结果信息
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @param page 页数
	 * @param pageSize 每页多少行
	 * @return 返回data数据列表信息与columns列表信息
	 * @author 张健雄
	 */
	public Map<String,Object> querySqlDatabySqId(String sqId,String isTemp,int page,int pageSize);
	/**
	 * 通过查询ID获取数据列表的列定义信息
	 * @param sqId 查询ID
	 * @param temp 是否为临时数据
	 * @return
	 * @throws RuntimeException
	 * @author 张健雄
	 */
	public List<SqDataGridObject> queryDataColumns(String sqId,String isTemp) throws RuntimeException;
	
	/**
	 * 通过查询ID查询excel导出的全部数据
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public Map<String,Object> queryExcelInfo(String sqId,String isTemp);
	
	/**
	 * 通过查询ID查询智能对象结果数据
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @return
	 * @author 张健雄
	 */
	public SqQueryInfoVO querySqQueryInfoById(String sqId,String isTemp);
	
	/**
	 * 验证配置名称是否唯一
	 * @param sqId
	 * @param name
	 * @return
	 * @author 张健雄
	 */
	public boolean validQueryName(String sqId,String name);
	
	/**
	 * 更新或保存智能查询数据对象
	 * @param query 智能查询数据对象
	 * @return
	 * @author 张健雄
	 */
	public boolean saveOrUpdateQuery(SqQueryInfoVO query);
	
	/**
	 * 删除智能查询数据对象
	 * @param query 智能查询数据对象
	 * @return
	 * @author 张健雄
	 */
	public boolean deleteQuery(String sqId);	
	
	/**
	 * 分页查询已有的查询配置项
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 张健雄
	 */
	public Map<String, Object> querySqQueryInfoList(int page,int pageSize);
	
	/**
	 * 查询全部已有的查询配置项
	 * @return
	 * @author 张健雄
	 */
	public List<SqQueryInfoVO> querySqQueryInfoList();
	
	/**
	 * 查询全部自动查询任务配置项
	 * @return
	 * @author 张健雄
	 */
	public List<SqQueryInfoVO> querySqQueryAutoList();
	
	
	/**
	 * 清除智能查询临时数据
	 * @author 张健雄
	 */
	public void cleanTemporaryQuery();
	
	/**
	 * 扫描并标识已丢失或已过期的数据文件
	 * @author 张健雄
	 */
	public void markLostfiles();
	
	/*-----------开放查询接口-------------*/
	
	/**
	 * 通过任务ID创建所需的数据文件
	 * @param SqId 查询ID
	 * @return 返回数据文件信息
	 * @author 张健雄
	 */
	public Map<String,String> createFileByTaskId(String taskId);
	
	/**
	 * 通过查询ID创建所需的数据文件
	 * @param SqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @param fileName 创建文件名
	 * @param userAcc 创建用户账号（用于控制权限）
	 * @return 返回数据文件信息
	 * @author 张健雄
	 */
	public Map<String,String> createFileBySqId(String sqId,String isTemp,String fileName,String userAcc);
	
	
	/*---------图表生成器操作-----------*/
	
	/**
	 * 查询分组图表的统计方法
	 * @param sqId
	 * @param isTemp
	 * @param name
	 * @param value
	 * @param group
	 * @return
	 * @author 张健雄
	 */
	public List<GroupDataVo> queryGroupReport(String sqId,String isTemp,String name,String value,String group);
	
	/**
	 * 查询饼状图表的统计方法
	 * @param sqId
	 * @param isTemp
	 * @param name
	 * @param value
	 * @param group
	 * @return
	 * @author 张健雄
	 */
	public List<PieDataVo> queryPieReport(String sqId,String isTemp,String name,String value);
	
	/**
	 * 判断数据表之间是否存在关联关系 
	 * @param tableids
	 * @return
	 * @author 张健雄
	 */
	public Map<String,Object> isAssociation(String tableids);
	
	/**
	 * 获取SQL统一的别名
	 * @param table 数据表名
	 * @param col 数据字段名
	 * @param index 字段属性索引
	 * @return
	 * @author 张健雄
	 */
	public String getColumnAlias(String table, String col, String index);
	
	/**
	 * 获取SQL统一的表识别码（如传入用户账号信息，则需要添加数据权限过滤代码）
	 * 
	 * @param table  数据表名
	 * @param userAcc 用户账号信息
	 * @return
	 * @author 张健雄
	 */
	public String getSqlTableStr(String table, String userAcc);
	
	
	/**
	 * 智能查询应用接口
	 * @param searchKey 每一次启动查询的唯一识别码，用于定位对应的SQL信息
	 * @param selecters 查询的显示列信息列表（只做分页查询时不需要）
	 * @param filters 查询的过滤器信息列表（只做分页查询时不需要）
	 * @param page 分页查询的页数
	 * @param pageSize 分页查询的每页最大条数
	 * @return 返回Map列表，rows：查询结果；total：结果条数
	 * @author 张健雄
	 */
	public Map<String,Object> queryData(String searchKey,List<SqSelecterVO> selecters,List<SqFilterVO> filters,int page,int pageSize);

	public List<Map<String, Object>> queryColumList(String columsId);

	public List<Map<String, String>> queryPeriodList(String tableName);

}


