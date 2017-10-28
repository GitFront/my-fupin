package com.aspire.birp.modules.antiPoverty.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by kamyam on 2016/12/4.
 */
@Repository
public interface DisposableIncomeDao {
	
	public List<Map<String,Object>> queryIncomeSort(Map<String,Object> params);
	public Long queryIncomeSortCount(Map<String,Object> params);
	
	public List<Map<String,Object>> querySpendingAnalysisChart(Map<String,Object> params);
	public List<Map<String,Object>> queryIncomeAnalysisChart(Map<String,Object> params);

	public Map<String,Object> queryIncomeAnalysisC(Map<String,Object> params);
	public List<Map<String,Object>> queryIncomeAnalysis(Map<String,Object> params);
	
	public Map<String,Object> querySpendingAnalysisC(Map<String,Object> params);
	public List<Map<String,Object>> querySpendingAnalysis(Map<String,Object> params);
	
	public Long queryDetailOfIncomeTableC(Map<String,Object> params);
	public List<Map<String,Object>> queryDetailOfIncomeTable(Map<String,Object> params);
	public List<Map<String,Object>> queryExpensesAnalyzeTable(Map<String,Object> params);
	public Map<String,Object> queryExpensesAnalyzeTableC(Map<String,Object> params);

	public Map<String,Object> queryDisposableIncomeMTableC(Map<String,Object> params);
	public List<Map<String,Object>> queryDisposableIncomeMTable(Map<String,Object> params);
	public List<Map<String,Object>> queryDisposableIncomeMChartR(Map<String,Object> params);
	public List<Map<String,Object>> queryDisposableIncomeMChartL(Map<String,Object> params);
	
	
	public List<Map<String,Object>> queryExpensesAnalyzeChart1(Map<String, Object> params);
	public List<Map<String,Object>> queryExpensesAnalyzeChart2(Map<String, Object> params);
	public List<Map<String,Object>> queryExpensesAnalyzeChart3(Map<String, Object> params);
	public List<Map<String,Object>> queryExpensesAnalyzeChart4(Map<String, Object> params);
	public List<Map<String,Object>> querySortDetail(Map<String,Object> params);
}
