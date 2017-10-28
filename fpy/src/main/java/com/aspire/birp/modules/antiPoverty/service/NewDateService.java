package com.aspire.birp.modules.antiPoverty.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.birp.common.utils.CommonUtil;
import com.aspire.birp.modules.antiPoverty.dao.NewDateDao;

/** 
 *查询最新时间
 */
@Service
public class NewDateService  {
	
	private static Map<String, Object> dateContants; 
	@Autowired
	private  NewDateDao newDateDao;
	
	public void refreshDateContants(){
		Map<String,Object> params = new HashMap<String,Object>();
		List<Map<String, Object>> queryNewDate = newDateDao.queryNewDate(null);
		for (int i = 0; i < queryNewDate.size(); i++) {
			Map<String, Object> map = queryNewDate.get(i);
			params.put(getValue(map,"TAB_NAME").toString(), getValue(map,"STAT_TIME"));
			params.put(getValue(map,"TAB_NAME")+"Y", getValue(map,"YEAR_TAG"));
			params.put(getValue(map,"TAB_NAME")+"M", getValue(map,"MONTH_TAG"));
		}
		dateContants=params;
	}
	
	public static Map<String, Object> getDateMapCostants(){
		
		return dateContants;
	}
	
	 private Object getValue(Map<String,Object> map,String key){
	    	return CommonUtil.getMapValue(map,key);
		}
	
}
