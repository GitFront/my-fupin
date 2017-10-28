package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 树信息
 */
@Repository
public interface TreeDataDao {
   
	public Map<String,Object> queryAreaById(Map<String,Object> params);
	
    public List<Map<String,Object>> queryAreaBySubId(Map<String,Object> params);
    
    public List<Map<String,Object>> queryAreaByPPAC(Map<String,Object> params);
    
}
