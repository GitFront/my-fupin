package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

public interface FundMonitoringDao {
/*资金管理	*/
		
		public List<Map<String,Object>> queryFundMonitoringManagement1(Map<String,Object> params);
		
		public List<Map<String,Object>> queryFundMonitoringManagement2(Map<String,Object> params);
		
		public List<Map<String,Object>> queryFundMonitoringManagement3(Map<String,Object> params);
		
		public Map<String,Object> queryFundMonitoringManagementTableC(Map<String,Object> params);
		
		public List<Map<String,Object>> queryFundMonitoringManagementTable(Map<String,Object> params);
		
/*资金分析*/		
		
		public List<Map<String,Object>> queryFundAnalysisManagement1(Map<String,Object> params);
		
		public List<Map<String,Object>> queryFundAnalysisManagement2(Map<String,Object> params);
		
		public List<Map<String,Object>> queryFundAnalysisManagement3(Map<String,Object> params);
		
		public Map<String,Object> queryFundAnalysisManagementTableC(Map<String,Object> params);
		
		public List<Map<String,Object>> queryFundAnalysisManagementTable(Map<String,Object> params);
		
/*资金分析详细*/	
		
		public List<Map<String,Object>> queryFundAnalysisManagementDetail(Map<String,Object> params);
		
		public Long queryFundAnalysisManagementDetailCount(Map<String,Object> params);
/*资金排序*/
		public List<Map<String,Object>> queryFundAnalysisSort(Map<String,Object> params);
		
		public Long queryFundAnalysisSortCount(Map<String,Object> params);
/*排序*/	
		public List<Map<String,Object>> queryFundAnalysisSortTtH(Map<String,Object> params);
		
		public Long queryFundAnalysisSortTtHCount(Map<String,Object> params);
		
		
		public List<Map<String,Object>> queryFundAnalysisSortDetail(Map<String,Object> params);
		
		/*查询文件号*/
		public List<Map<String,Object>> queryFundFileList(Map<String,Object> params);
		
		public Long queryFundFileListCount(Map<String,Object> params);

		public List<Map<String, Object>> queryCapitalCapitalFileDetail(Map<String, Object> params);

		public List<Map<String, Object>> queryFundFileDetailList(Map<String, Object> params);

		public Long queryFundFileDetailListCount(Map<String, Object> params);

		public List<Map<String, Object>> queryFundFileBasicInfo(Map<String, Object> params);

		public List<Map<String, Object>> queryFundFileDetailListo(Map<String, Object> params);
		
		public List<Map<String, Object>> queryFundFileTree(Map<String, Object> params);
		
		public List<Map<String, Object>> queryFundFileSubTree(Map<String, Object> params);
		
		public List<Map<String, Object>> queryFundFileBySubId(Map<String, Object> params);
		//根据文件号和文件pac查询文件详情
		public Map<String,Object> queryFundFileByFileId(Map<String, Object> params);
		//根据文件号查询文件
		public Map<String,Object> queryFundFileById(Map<String, Object> params);
		/*贫困户列表*/
		public List<Map<String,Object>> queryPoorFamilyListTable(Map<String,Object> params);
		public Long queryPoorFamilyListTableC(Map<String,Object> params);
		

}
