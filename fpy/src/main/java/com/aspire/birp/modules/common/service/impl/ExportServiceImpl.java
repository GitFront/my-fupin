package com.aspire.birp.modules.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.birp.modules.common.service.ExportService;


@Service
public class ExportServiceImpl implements ExportService {
	
	private static Logger log = Logger.getLogger(ExportServiceImpl.class);
	
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
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Map<String, String>> querySelect(String queryName,
			Map<String, Object> params) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return list;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		list = sqlSessionTemplate.selectList(queryName, params);
		log.info("下拉列表数据查询成功");
		return list;
	}
	
	/**
	 * 普通数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Map<String, Object>> queryList(String queryName,
			Map<String, Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return list;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		list = sqlSessionTemplate.selectList(queryName, params);
		log.info("列表数据查询成功");
		return list;
	}
	
	/**
	 * 查询单个数据信息
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> queryOne(String queryName,
			Map<String, Object> params) {
		
		Map<String, Object> object = new HashMap<String,Object>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return object;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		object = sqlSessionTemplate.selectOne(queryName, params);
		log.info("单一数据查询成功");
		return object;
	}

	
	
	
}
