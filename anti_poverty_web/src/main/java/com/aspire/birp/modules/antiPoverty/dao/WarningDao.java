package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

public interface WarningDao {
	/**
	 * 预警监控-走访监控
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	//查图表数据
	public List<Map<String,Object>> queryAlarmedImplementManagement(Map<String,Object> params);
	public List<Map<String,Object>> queryAlarmedImplementManagement2(Map<String,Object> params);
	
	//查table数据
	public Map<String,Object> queryAlarmedImplementManagementTableC(Map<String,Object> params);
	
	
	public Map<String,Object> queryAlarmedA3(Map<String,Object> params);
	public  List<Map<String,Object>> queryAlarmedA4(Map<String,Object> params);
	public  List<Map<String,Object>> queryAlarmedA5(Map<String,Object> params);

	
	public List<Map<String,Object>> queryAlarmedImplementManagementTable(Map<String,Object> params);

	
	
	
	//分析明细
	public List<Map<String,Object>> queryAlarmedImplementAnalysisDetail(Map<String,Object> params);
	
	public Long queryAlarmedImplementAnalysisDetailCount(Map<String,Object> params);
	//道路硬底化排序
	public List<Map<String,Object>> queryAlarmedImplementAnalysisSort(Map<String,Object> params);
	
	public Long queryAlarmedImplementAnalysisSortCount(Map<String,Object> params);
	
	//落实排序>区域数据
	public List<Map<String,Object>> queryAlarmedSortDetail(Map<String,Object> params);

	
}
