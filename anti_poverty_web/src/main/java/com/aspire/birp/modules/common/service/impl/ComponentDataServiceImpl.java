package com.aspire.birp.modules.common.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.birp.modules.common.service.ComponentDataService;



/**
 * 定义组件数据加载服务类
 * Title: birp_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 * 
 * @author <a href="mailto:qinjianqiu@aspirecn.com">覃剑秋</a>
 * @e-mail: qinjianqiu@aspirecn.com
 * @version 1.0 
 * @creatdate 2015年7月13日 上午11:03:07
 *
 */
@Service
public class ComponentDataServiceImpl implements ComponentDataService {
	
	private static final String suffix = "_count";
	private static final String totalKey = "total";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	

	@Transactional(isolation = Isolation.READ_COMMITTED)
 	public Object[] queryDataGrid(String queryName, Map<String, Object> params,Integer startIndex, Integer limit) {
 		Long total = 0L ;
 		List<Map<String, Object>> list = null;
		Object[] objs = null;
		Map<String, Object> object = sqlSessionTemplate.selectOne(queryName + suffix, params);
		if(object!=null){
			Object totalObj = object.get(totalKey);
			if(totalObj == null)
				totalObj = object.get(totalKey.toUpperCase());
			total = Long.parseLong(String.valueOf(totalObj));
		}
        list = sqlSessionTemplate.selectList(queryName, params,new RowBounds(startIndex, limit));
        objs = new Object[]{total,list};
  	 	return objs;
	}

}
