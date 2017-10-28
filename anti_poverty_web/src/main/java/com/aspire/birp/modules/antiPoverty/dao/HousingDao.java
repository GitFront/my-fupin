package com.aspire.birp.modules.antiPoverty.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface HousingDao {
	
	public List<Map<String,Object>> queryHousingChart1(Map<String,Object> params);
	public List<Map<String,Object>> queryHousingChart2(Map<String,Object> params);
	public List<Map<String,Object>> queryHousingChart3(Map<String,Object> params);
	public List<Map<String,Object>> queryHousingChart4(Map<String,Object> params);
	
	public Long queryHousingSortCount(Map<String,Object> params);
	public List<Map<String,Object>> queryHousingSort(Map<String,Object> params);

	
	public Long queryHousingDetailCount (Map<String,Object> params);
	public List<Map<String,Object>> queryHousingDetail(Map<String,Object> params);

	public Map<String,Object> queryHousingAnalyzeTableC(Map<String,Object> params);
	public List<Map<String,Object>> queryHousingAnalyzeTable(Map<String,Object> params);
	
	
	public Map<String,Object> queryHousingManageTableC(Map<String,Object> params);
	public List<Map<String,Object>> queryHousingManageTable(Map<String,Object> params);
	
	public List<Map<String,Object>> querySortDetail(Map<String,Object> params);
 
}
