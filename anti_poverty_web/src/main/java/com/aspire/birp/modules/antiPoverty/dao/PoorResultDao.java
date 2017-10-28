package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface PoorResultDao {
   
	
	public List<Map<String,Object>> queryPovertyResultsTable(Map<String,Object> params);
	public Map<String,Object> queryPovertyResultsTableC(Map<String,Object> params);
	public List<Map<String,Object>> queryPovertyResultsChartR(Map<String,Object> params);
	public List<Map<String,Object>> queryPovertyResultsChartL(Map<String,Object> params);
	
	
	
	public List<Map<String,Object>> queryAnalysisOfEffectsChart1(Map<String,Object> params);
	public List<Map<String,Object>> queryAnalysisOfEffectsChart2(Map<String,Object> params);
	public List<Map<String,Object>> queryAnalysisOfEffectsChart3(Map<String,Object> params);
	public List<Map<String,Object>> queryAnalysisOfEffectsTable(Map<String,Object> params);
	public Map<String,Object> queryAnalysisOfEffectsTableC(Map<String,Object> params);

	
	public List<Map<String,Object>> queryAnalysisOfEffectsDetails(Map<String,Object> params);
	public Long queryAnalysisOfEffectsDetailsCount(Map<String,Object> params);
	
	public List<Map<String,Object>> queryPoorResultSort(Map<String,Object> params);
	public Long queryPoorResultSortCount(Map<String,Object> params);
	
	
	public List<Map<String,Object>> queryNotAnalysisTable(Map<String,Object> params);
	public Map<String,Object> queryNotAnalysisTableC(Map<String,Object> params);
	
	public List<Map<String,Object>> queryNotAnalysisChart(Map<String,Object> params);
	
	public List<Map<String,Object>> querySortDetail(Map<String,Object> params);
}
