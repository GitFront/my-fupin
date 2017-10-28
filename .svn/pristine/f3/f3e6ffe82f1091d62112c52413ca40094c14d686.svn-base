package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface DutyDao {
	
	/**
	 *帮扶主体趋势
	 * */
	public List<Map<String,Object>> dutyHelpTrends(Map<String,Object> params);
	
	/**
	 *帮扶主体分布
	 * */
	public List<Map<String,Object>> dutyHelpFunds(Map<String,Object> params);
	
	/**
	 * 责任监控管理统计
	 * */
	public List<Map<String,Object>> dutyManagementCount(Map<String,Object> params);
	/**
	 * 责任监控管理管理 合计
	 * */
	public Map<String,Object> dutyManagementTotal(Map<String,Object> params);
	
	/**
	 *帮扶责任分布
	 * */
	public List<Map<String,Object>> dutyAnalyseHelpTrends(Map<String,Object> params);
	
	/**
	 *帮扶力度分布
	 * */
	public List<Map<String,Object>> dutyAnalyseHelpFunds(Map<String,Object> params);

	/**
	 * 责任监控分析统计
	 * */
	public List<Map<String,Object>> dutyAnalyseCount(Map<String,Object> params);
	/**
	 * 责任监控分析 合计
	 * */
	public Map<String,Object> dutyAnalyseTotal(Map<String,Object> params);
	
	/**
	 * 责任监控分析明细 
	 * */
	public List<Map<String,Object>> dutyAnalysisDetail(Map<String,Object> params);
	/**
	 * 责任监控分析明细总数 
	 * */ 
	public Long dutyAnalysisDetailCount(Map<String,Object> params);
}
