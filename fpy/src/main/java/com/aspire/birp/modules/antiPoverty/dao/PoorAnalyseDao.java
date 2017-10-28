package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 数据分析数据查询
 * 
 * @author chudy
 * @version 创建时间：2017年5月25日 下午3:59:20
 */

@Repository
public interface PoorAnalyseDao {
    /**
     * 贫困分析 贫困户属性分析
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryPoorAttribute(Map<String,Object> params);
    
    public List<Map<String,Object>> queryPoorReason(Map<String,Object> params);
    public List<Map<String,Object>> queryPoorIncome(Map<String,Object> params);
   // public Map<String,Object> queryPoorAttributeCount(Map<String,Object> params);
    public List<Map<String,Object>> queryPoorAge(Map<String,Object> params);
    public List<Map<String,Object>> queryPoorSex(Map<String,Object> params);
    
    public List<Map<String,Object>> queryPoorNationality(Map<String,Object> params);
    public List<Map<String,Object>> queryPoorProtical(Map<String,Object> params);
    
    public List<Map<String,Object>> queryPoorEducation(Map<String,Object> params);
    public List<Map<String,Object>> queryPoorHealthStatus(Map<String,Object> params);
    
    public List<Map<String,Object>> queryPoorLaborStatus(Map<String,Object> params);
    public List<Map<String,Object>> queryPoorWorkStatus(Map<String,Object> params);
    
    public List<Map<String,Object>> queryPoorAnalysisDetail(Map<String,Object> params);
    public Long queryPoorAnalysisDetailCount(Map<String,Object> params);
    /**
     * 贫困分析 致贫原因分析
     * @param 区域PAC
     * @return
     */
   // public List<Map<String,Object>> queryPoorReason(Map<String,Object> params);
   // public Map<String,Object> queryPoorReasonCount(Map<String,Object> params);

    /**
     * 贫困分析 危房级别
     * @param 区域PAC
     * @return
     */
   // public List<Map<String,Object>> queryDangerHouseLevel(Map<String,Object> params);
   // public Map<String,Object> queryDangerHouseLevelCount(Map<String,Object> params);
    /**
     * 贫困分析 危房结构
     * @param 区域PAC
     * @return
     */
   // public List<Map<String,Object>> queryDangerHouseStructure(Map<String,Object> params);
    /**
     * 贫困分析 贫困人口务工状况分析
     * @param 区域PAC
     * @return
     */
   // public List<Map<String,Object>> queryPoorLabor(Map<String,Object> params);
   // public Map<String,Object> queryPoorLaborZJ(Map<String,Object> params);
   // public Map<String,Object> queryPoorLaborCount(Map<String,Object> params);
    /**
     * 贫困分析  教育分析
     * @param 区域PAC
     * @return
     */
   // public List<Map<String,Object>> queryPoorEdu(Map<String,Object> params);
  //  public Map<String,Object> queryPoorEduCount(Map<String,Object> params);
    /**
     * 贫困分析  健康状况 
     * @param 区域PAC
     * @return
     */
  //  public List<Map<String,Object>> queryPoorHealth(Map<String,Object> params);
   // public Map<String,Object> queryPoorHealthCount(Map<String,Object> params);
    /**
     * 贫困分析   健康状况 TOP10 疾病 
     * @param 区域PAC
     * @return
     */
   // public List<Map<String,Object>> queryPoorHealthTOP(Map<String,Object> params);

}
