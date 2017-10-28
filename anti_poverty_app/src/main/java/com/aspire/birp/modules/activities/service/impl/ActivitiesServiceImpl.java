package com.aspire.birp.modules.activities.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.birp.modules.activities.service.ActivitiesService;

/**
 * Title: 营销活动服务方法实现类 <br>
 * Description: 主要用于营销活动报表的数据查询及反馈 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年7月22日 下午17:45:03 <br>
 *
 */
@Service
public class ActivitiesServiceImpl implements ActivitiesService {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	/**
	 * 普通Select数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	@Override
	public List<Map<String, String>> querySelect(String queryName,
			Map<String, Object> params) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return list;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		list = sqlSessionTemplate.selectList(queryName, params);
		
		return list;
	}
	
	/**
	 * 普通数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	@Override
	public List<Map<String, Object>> queryList(String queryName,
			Map<String, Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return list;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		list = sqlSessionTemplate.selectList(queryName, params);
		
		return list;
	}
	
	/**
	 * 查询单个数据信息
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	@Override
	public Map<String, Object> queryOne(String queryName,
			Map<String, Object> params) {
		
		Map<String, Object> object = new HashMap<String,Object>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return object;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		object = sqlSessionTemplate.selectOne(queryName, params);
		
		return object;
	}

	
	
	
}
