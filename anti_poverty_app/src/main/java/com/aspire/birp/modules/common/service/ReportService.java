package com.aspire.birp.modules.common.service;

import java.util.List;
import java.util.Map;

import com.aspire.birp.modules.common.vo.GroupDataVo;
import com.aspire.birp.modules.common.vo.InvertedLineVo;
import com.aspire.birp.modules.common.vo.PieDataVo;

/**
 * Title: 用于封装图表数据读取的通用服务方法接口类 <br>
 * Description: 主要用于定义读取Bar\StackBars\Line\TimeSeriesLine等图表的数据列表方法接口 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年7月9日 下午17:45:03 <br>
 *
 */
public interface ReportService {

	
	/**
	 * 查询info表格的数据集合
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @return 返回info表格的数据集合
	 * @author 张健雄
	 */
	public Map<String,Object> queryInfoTableData(String queryName, Map<String, Object> params,Map<String,Object> mapper);
	/**
	 * 查询饼状图的数据集合
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param rowsValue 行转列参数
	 * @return 返回饼状图的数据列表
	 * @author 张健雄
	 */
	public List<PieDataVo> queryPieData(String queryName, Map<String, Object> params,Map<String,Object> mapper,Map<String,String> rowsValue);
	
	/**
	 * 查询地图的数据集合
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param joinBy 区域参数KEY码
	 * @return 返回地图的数据列表
	 * @author 张健雄
	 */
	public List<Map<String,Object>> queryMapData(String queryName, Map<String, Object> params,Map<String,Object> mapper,String joinBy);
	
	
	/**
	 * 查询分组数据集合，包括柱状图、折线图等
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param hideGroup 隐藏分组配置项
	 * @param rowsGroup 行转列参数
	 * @return 返回分组数据列表
	 * @author 张健雄
	 */
	public List<GroupDataVo> queryGroupData(String queryName, Map<String, Object> params,Map<String,Object> mapper,String[] hideGroup,Map<String,String> rowsGroup);
	/**
	 * 查询垂直折线数据集合，包括柱状图、折线图等
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param hideGroup 隐藏分组配置项
	 * @return 返回垂直折线数的数据列表
	 * @author 张健雄
	 */
	public List<InvertedLineVo> queryInvertedLineData(String queryName, Map<String, Object> params,Map<String,Object> mapper,String[] hideGroup);
	/**
	 * 查询仪表盘数据集合
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param decimal 数据精度
	 * @return 返回仪表盘的数据列表
	 * @author 张健雄
	 */
	public Double queryGaugeData(String queryName, Map<String, Object> params,Map<String,Object> mapper,Integer decimal);
	
	/**
	 * 普通数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	public List<Map<String, Object>> queryList(String queryName,Map<String, Object> params);
	
	
}
