package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 战略地图
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月19日 下午1:53:11
 */
@Repository
public interface StrategyDao {

    /**
     * 数据监控首页 出列村  帮扶 等统计 
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryCount(Map<String,Object> params);
    
    /**
     * 劳动能力人均可支配 
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryDis(Map<String,Object> params);
    
    /**
     * 兜底
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryBottom(Map<String,Object> params);
    
    /**
     * 义务教育
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryYwjy(Map<String,Object> params);
    
    /**
     * 基本医疗
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryJbyl(Map<String,Object> params);
    
    /**
     * 住房安全
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryZfaq(Map<String,Object> params);
    
    /**
     * 一相当
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryYxd(Map<String,Object> params);
    
    /**
     * 项目资金
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public List<Map<String,Object>> queryXmzj(Map<String,Object> params);
    public Map<String,Object> queryXmzjCount(Map<String,Object> params);
    
    /**
     * 预警监控
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryYjjk(Map<String,Object> params);
}
