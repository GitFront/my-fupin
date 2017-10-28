
package com.aspire.birp.modules.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aspire.birp.modules.app.dao.AppDao;
import com.aspire.birp.modules.base.service.BaseService;

/**  
 * @Title: 扶贫办app版本升级接口
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zr.wenzhongrong@aspirecn.com">温钟荣</a>
 * @Email: zr.wenzhongrong@aspirecn.com
 * @date: 2016年11月21日 上午11:28:45
 * @version: V1.0
 */

@Service
public class AppService extends BaseService implements
		AppDao {

	private static Logger log = LoggerFactory.getLogger(AppService.class);

	
	@Override
	public List<Map<String, Object>> queryCommonList(String queryName,
			Map<String, Object> params) {
		List<Map<String, Object>> result = this.queryList(queryName, params);
		log.info("数据内容通用查询成功！");
		return result;
	}

	@Override
	public List<Map<String, String>> querySelectList(String queryName,
			Map<String, Object> params) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return list;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		list = getSqlSessionTemplate().selectList(queryName, params);
		log.info("通用查询成功！");
		return list;
	}

	@Override
	public Map<String, Object> queryCommonOne(String queryName, Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String,Object>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return map;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		map = getSqlSessionTemplate().selectOne(queryName, params);
		log.info("数据通用查询成功！");
		return map;
	}

}


