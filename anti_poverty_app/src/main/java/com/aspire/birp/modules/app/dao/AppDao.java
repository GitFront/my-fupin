
package com.aspire.birp.modules.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/**  
 * @Title: 扶贫办app版本升级接口
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zr.wenzhongrong@aspirecn.com">温钟荣</a>
 * @Email: zr.wenzhongrong@aspirecn.com
 * @date: 2016年11月21日 上午11:28:45
 * @version: V1.0
 */
public interface AppDao {

	/**
	 * 通用维表数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	public List<Map<String, String>> querySelectList(String queryName,Map<String, Object> params);
	/**
	 * 通用数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	public List<Map<String, Object>> queryCommonList(String queryName,Map<String, Object> params);
	/**
	 * 通用数据的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回单挑数据
	 */
	public Map<String, Object> queryCommonOne(String queryName,Map<String, Object> params);
}


