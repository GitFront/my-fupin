package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectManagementDao {
	
	/**
	 *项目规模 统计图
	 * */
	public List<Map<String,Object>> projectScale(Map<String,Object> params);
	
	/**
	 *项目资金 统计图
	 * */
	public List<Map<String,Object>> projectFunds(Map<String,Object> params);
	
	/**
	 * 项目监控管理统计
	 * */
	public List<Map<String,Object>> projectManagementCount(Map<String,Object> params);
	/**
	 * 项目监控管理管理 合计
	 * */
	public Map<String,Object> projectManagementTotal(Map<String,Object> params);

	
	/**
	 *项目资金趋势 统计图
	 * */
	public List<Map<String,Object>> projectAnalysisFundsTrends(Map<String,Object> params);
	/**
	 *村户项目 统计图
	 * */
	public List<Map<String,Object>> projectAnalysisCountry(Map<String,Object> params);
	/**
	 *村户项目 统计图-资金到村
	 * */
	public List<Map<String,Object>> projectAnalysisMoneyToCountry(Map<String,Object> params);
	/**
	 * 项目分析统计
	 * */
	public List<Map<String,Object>> projectAnalysisCount(Map<String,Object> params);
	/**
	 * 项目分析 合计
	 * */
	public Map<String,Object> projectAnalysisTotal(Map<String,Object> params);
	/**
	 * 项目分析 明细
	 * */
	public List<Map<String,Object>> projectAnalysisDetail(Map<String,Object> params);
	/**
	 * 项目分析 明细总数
	 * */ 
	public Long projectAnalysisDetailCount(Map<String,Object> params);
	/**
	 * 项目分析 排序
	 * */
	public List<Map<String,Object>> projectAnalysisSort(Map<String,Object> params);
	/**
	 * 项目分析 排序总数
	 * */ 
	public Long projectAnalysisSortCount(Map<String,Object> params);
	/**
	 * 项目分析排序详细
	 * */ 
	public List<Map<String,Object>> projectAnalysisSortDetail(Map<String,Object> params);
	/**
	 * 项目分析统计-到村
	 * */
	public List<Map<String,Object>> projectAnalysisToCountryCount(Map<String,Object> params);
	/**
	 * 项目分析 合计-到村
	 * */
	public Map<String,Object> projectAnalysisToCountryTotal(Map<String,Object> params);
	
	/**
	 * 项目分析统计-到户
	 * */
	public List<Map<String,Object>> projectAnalysisToFamilyCount(Map<String,Object> params);
	/**
	 * 项目分析 合计-到户
	 * */
	public Map<String,Object> projectAnalysisToFamilyTotal(Map<String,Object> params);
	
	/**
	 * 获取表的建档立卡时间
	 * @param params
	 * @return
	 */
	public Long getNewCreateDate(Map<String,Object> params);
}
