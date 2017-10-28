package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

/**
 * 示范村地图Dao
 * @author zhudiyuan
 *
 */
public interface ExampleCountryMapDao {
	
	  /**
     * 获取地图区域展示数据
     * @param params
     * @return
     */
    public  Map<String,Object> queryEcMapData(Map<String,Object> params);
    /**
     * 获取地图位置点经纬度（示范村）
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryMappiontGeoForComplete(Map<String,Object> params);
    
    /**
     * 获取地图位置点经纬度（示范村）
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryMappiontGeoForBuilding(Map<String,Object> params);
    
    /**
     * 获取地图位置点经纬度（示范村）
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryMappiontGeoForNostart(Map<String,Object> params);
    
}
