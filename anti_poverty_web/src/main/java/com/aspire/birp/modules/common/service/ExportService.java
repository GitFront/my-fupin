package com.aspire.birp.modules.common.service;

import java.util.List;
import java.util.Map;

/**
 * Title: 黄页BI公用服务方法接口类 <br>
 * Description: 主要用于定义黄页BI报表公用的数据查询方法 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年7月22日 下午17:45:03 <br>
 *
 */
public interface ExportService {

	/**
	 * 用于普通select数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	public List<Map<String, String>> querySelect(String queryName, Map<String, Object> params);
	
	/**
	 * 用于普通数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	public List<Map<String, Object>> queryList(String queryName, Map<String, Object> params);
	
	/**
	 * 查询单个数据信息
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	public Map<String, Object> queryOne(String queryName, Map<String, Object> params);
	
}
