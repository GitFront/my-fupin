package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 最新时间
 */
@Repository
public interface NewDateDao {
    /**
     * 最新统计日期
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public List<Map<String,Object>> queryNewDate(Map<String,Object> params);
    
    /**
     * 地图数据的最新统计日期
     * @return
     */
    public int queryMapDate();
    
    /**
     * 示范村地图数据的最新统计日期
     * @return
     */
    public int queryEcMapDate();
    
    
}
