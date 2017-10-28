package com.aspire.birp.modules.antiPoverty.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface EducationDao {
	public List<Map<String,Object>> queryEducationChart1(Map<String,Object> params);
	public List<Map<String,Object>> queryEducationChart2(Map<String,Object> params);
	public List<Map<String,Object>> queryEducationChart3(Map<String,Object> params);
	
	public List<Map<String,Object>> queryEducationSortDetail(Map<String,Object> params);

	public List<Map<String,Object>> queryEducationSort(Map<String,Object> params);
	public Long queryEducationSortCount(Map<String,Object> params);

	public List<Map<String,Object>> queryEducationDetail(Map<String,Object> params);
	public Long queryEducationDetailCount(Map<String,Object> params);

	public List<Map<String,Object>> queryEducationManageTable(Map<String,Object> params);
	public Map<String,Object> queryEducationManageTableC(Map<String,Object> params);
	
	public List<Map<String,Object>> queryEducationAnalysisTable(Map<String,Object> params);
	public Map<String,Object> queryEducationAnalysisTableC(Map<String,Object> params);
	
	
 
}
