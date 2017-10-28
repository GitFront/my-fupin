package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface AlarmedPoorExceptionDao {
	
	 public List<Map<String,Object>> queryExceptionScale(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryExceptionArea(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryPoorIndetificationTable(Map<String,Object> params);
	 
	 public Map<String,Object> queryPoorIndetificationTableCount(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryRecordsExceptionScale(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryRecordsExceptionArea(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryRecordsTable(Map<String,Object> params);
	 
	 public Map<String,Object> queryRecordsTableCount(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryPoorIndetificationSort(Map<String,Object> params);
	 
	 public Long queryPoorIndetificationSortCount(Map<String,Object> params);
	 
     public List<Map<String,Object>> queryPoorRecordsSort(Map<String,Object> params);
	 
	 public Long queryPoorRecordsSortCount(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryAlarmedMoneyScale(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryAlarmedMoneyArea(Map<String,Object> params);
	 
     public List<Map<String,Object>> queryAlarmedMoneyTable(Map<String,Object> params);
	 
	 public Map<String,Object> queryAlarmedMoneyTableCount(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryAlarmedMoneySort(Map<String,Object> params);
		 
	 public Long queryAlarmedMoneySortCount(Map<String,Object> params);
		 
	 public List<Map<String,Object>> queryAlarmedMoneyDetail(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryPoorIndetificationDetail(Map<String,Object> params);
	 public List<Map<String,Object>> queryPoorRecordDetail(Map<String,Object> params);
	 
     public List<Map<String,Object>> queryPoorIndetificationDetailo(Map<String,Object> params);
	 
	 public Long queryPoorIndetificationDetailoCount(Map<String,Object> params);
	 
 	 public List<Map<String,Object>> queryPoorRecordsDetail(Map<String,Object> params);
	 
	 public Long queryPoorRecordsDetailCount(Map<String,Object> params);
	 
	 public List<Map<String,Object>> queryPoorMoneyDetail(Map<String,Object> params);
	 
	 public Long queryPoorMoneyDetailCount(Map<String,Object> params);
	 
	 
	 
}
