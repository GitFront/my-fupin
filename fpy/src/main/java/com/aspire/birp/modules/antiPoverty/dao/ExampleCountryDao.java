package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

/**
 * 示范村Dao
 * @author zhudiyuan
 *
 */
public interface ExampleCountryDao {
	
	  /**
     * 获取示范村首页数据
     * @param params
     * @return
     */
    public  Map<String,Object> queryCountryIndexStaticData(Map<String,Object> params);
    /**
     * 获取村图片
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryExampleCountryCountryPhoto(Map<String,Object> params);
    public Long queryExampleCountryNewMonth(Map<String,Object> params);
    /**
     * 获取示范村图表数据
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryExampleManagement1(Map<String,Object> params);
	
	public List<Map<String,Object>> queryExampleManagement2(Map<String,Object> params);
	/**
     * 获取示范村表单数据
     * @param params
     * @return
     */
	public Map<String,Object> queryExampleManagementTableC(Map<String,Object> params);
	
	public List<Map<String,Object>> queryExampleManagementTable(Map<String,Object> params);
	/**
     * 获取示范村分析详细
     * @param params
     * @return
     */
	public Long queryFundAnalysisManagementDetailCount(Map<String,Object> params);
	
	public List<Map<String,Object>> queryFundAnalysisManagementDetail(Map<String,Object> params);
	


}
