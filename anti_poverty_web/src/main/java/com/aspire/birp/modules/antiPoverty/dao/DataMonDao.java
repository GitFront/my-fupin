package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 数据监控首页 on 2016/12/4.
 */
@Repository
public interface DataMonDao {
    /**
     * 区域描述
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryAreaDesc(Map<String,Object> params);
    
    /**
     * 区域描述1 把区域描述移到 F_COUNTRYFILE_AREA_DESC_D 表
     * @param 
     * @return
     */
    public String queryAreaDesc1(Map<String,Object> params);

    /**
     * 获取区域简称
     * @param params
     * @return
     */
    public String queryAreaName(Map<String,Object> params);
    /**
     * 数据监控首页 出列村  帮扶 等统计 
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryCount(Map<String,Object> params);
    
    public Object queryCountStat(Map<String,Object> params);
    
    public Object queryCountTop(Map<String,Object> params);
    
    public Object queryCountIndex(Map<String,Object> params);
    
    public Object queryCountIndexData(Map<String,Object> params);
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
     * 列表
     * 贫困分希
     * @param PAC STAT_TIME 日期可选
     * @return
     */
    public List<Map<String,Object>> queryPkfx(Map<String,Object> params);
    
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
    
    /**
     * 查询区域
     * @param params
     * @return
     */
    public Map<String,Object> queryAreaById(Map<String,Object> params);
    
    /**
     * 首页统计表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryCapitalCount(Map<String,Object> params);
    public Map<String,Object> queryHomePageCount(Map<String,Object> params);
    public Map<String,Object> queryProjectCapitalCount(Map<String,Object> params);
    //public Map<String,Object> queryPoorAnalysis(Map<String,Object> params);
    
    /**
     * 通过用户id获取用户读取公告
     * @param 
     * @return
     */
    public Map<String,Object> queryMsgByUserId(Map<String,Object> params);
    
    /**
     * 通过公告id获取公告信息
     * @param 
     * @return
     */
    public Map<String,Object> queryMsgById(Map<String,Object> params);
    public void insertUserMsg(Map<String,Object> params);
}
