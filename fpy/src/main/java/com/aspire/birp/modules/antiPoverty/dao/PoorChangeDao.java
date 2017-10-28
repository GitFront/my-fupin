package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface PoorChangeDao {
   
	
	public long queryPoorChangeSortCount(Map<String,Object> params);
	public long queryObjectDetailCount(Map<String,Object> params);
	
	public List<Map<String,Object>> queryObjectDetail(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendAllTable(Map<String,Object> params);
	public Map<String,Object> queryThePoorTrendAllTableC(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendAddTable(Map<String,Object> params);
	public Map<String,Object> queryThePoorTrendAddTableC(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendEndTable(Map<String,Object> params);
	public Map<String,Object> queryThePoorTrendEndTableC(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendNAddTable(Map<String,Object> params);
	public Map<String,Object> queryThePoorTrendNAddTableC(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendNReduceChart(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendNAddChart(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendEndChart(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendAddChart(Map<String,Object> params);
	
	public List<Map<String,Object>> queryThePoorTrendAllChart(Map<String,Object> params);
	
	public List<Map<String,Object>> queryPoorChangeChart(Map<String,Object> params);
	
	public Long queryPoorChangeCount(Map<String,Object> params);
	
	public List<Map<String,Object>> queryPoorChangeSort(Map<String,Object> params);
	
	public List<Map<String,Object>> queryAreaBySubId(Map<String,Object> params);
	
	public List<Map<String,Object>>  querySortDetail(Map<String,Object> params);
	
	public List<Map<String,Object>>  queryPopType(Map<String,Object> params);
	
	public Map<String,Object> queryPopTypeCount(Map<String,Object> params);
	
	public List<Map<String,Object>>  queryPopDetail(Map<String,Object> params);
	
	public List<Map<String,Object>>  queryPopDataCount(Map<String,Object> params);
	
}
