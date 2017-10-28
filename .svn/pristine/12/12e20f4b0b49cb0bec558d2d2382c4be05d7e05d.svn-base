package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 数据分析数据查询
 * 
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月12日 下午3:59:20
 */
@Repository
public interface DataAnalyseDao {
		
	/**
	 * <!--判断这个村是否重点贫困村 1是 0不是  -->
	 * */
	 public int IfFurtherPpoor(String pac);
	 
	 /**
	  * 根据pac查询区域ID
	  * */
	 public Map<String,Object> queryAreaNameByPac(Map<String,Object> params);

    /**
     * 预警监控存在异常的贫困户明细100项
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryFamilyAlarmed(Map<String,Object> params);
    /**
     * 预警监控存在异常的贫困户明细
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryFamilyDetailList(Map<String,Object> params);
    /**
     * 预警监控存在异常的贫困户-对比项、比例
     * @param 区域PAC
     * @return
     */
    public Map<String,Object> queryFamilyChange(Map<String,Object> params);
    /**
     * 预警监控存在异常的贫困户-统计图
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryFamilyCount(Map<String,Object> params);
    

    /**
     * 人均可支配收入分析统计表 明细
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryAverageTable(Map<String,Object> params);
    /**
     * 人均可支配收入分析 总数
     * @param 区域PAC
     * @return
     */
    public Map<String,Object> queryAverageIncomeCount(Map<String,Object> params);
    /**
     * 人均可支配收入分析 合计
     * @param 区域PAC
     * @return
     */
     public Map<String,Object> queryAverageTotal(Map<String,Object> params);
    /**
     * 人均可支配收入分析 各类人口数 
     * @param 区域PAC
     * @return
     */
    public Map<String,Object> queryAveragePopCnt(Map<String,Object> params);
    /**
     * 人均可支配收入分析 统计图 
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryAverageCount(Map<String,Object> params);
    /**
     * 贫困分析 致贫原因分析
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryPoorReason(Map<String,Object> params);
    public Map<String,Object> queryPoorReasonCount(Map<String,Object> params);
    /**
     * 贫困分析 贫困户属性分析
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryPoorAttribute(Map<String,Object> params);
    public Map<String,Object> queryPoorAttributeCount(Map<String,Object> params);
    /**
     * 贫困分析 危房级别
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryDangerHouseLevel(Map<String,Object> params);
    public Map<String,Object> queryDangerHouseLevelCount(Map<String,Object> params);
    /**
     * 贫困分析 危房结构
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryDangerHouseStructure(Map<String,Object> params);
    /**
     * 贫困分析 贫困人口务工状况分析
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryPoorLabor(Map<String,Object> params);
    public Map<String,Object> queryPoorLaborZJ(Map<String,Object> params);
    public Map<String,Object> queryPoorLaborCount(Map<String,Object> params);
    /**
     * 贫困分析  教育分析
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryPoorEdu(Map<String,Object> params);
    public Map<String,Object> queryPoorEduCount(Map<String,Object> params);
    /**
     * 贫困分析  健康状况 
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryPoorHealth(Map<String,Object> params);
    public Map<String,Object> queryPoorHealthCount(Map<String,Object> params);
    /**
     * 贫困分析   健康状况 TOP10 疾病 
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> queryPoorHealthTOP(Map<String,Object> params);
    /**
     *   社会性兜底 明细与最上面 两个-
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> querySocialPolicyTable(Map<String,Object> params);
    /**
     *    兜底 收入情况明细 总数 
     * @param 区域PAC
     * @return
     */
    public Map<String,Object> querySocialPolicyAmount(Map<String,Object> params);
    /**
     * 社会性兜底有差异的家庭明细
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> querySocialPolicyDetail(Map<String,Object> params);
    /**
     * 社会性兜底统计图
     * @param 区域PAC
     * @return
     */
    public List<Map<String,Object>> querySocialPolicyCount(Map<String,Object> params);

    
    /**
     * 义务教育
     * @param params
     * @return
     */
    public Map<String,Object> queryGuaranteeEdu(Map<String,Object> params);
    /**
     * 义务教育统计表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeEduList(Map<String,Object> params);
    /**
     * 义务教育统计表合计
     * @param params
     * @return
     */
    public Map<String,Object> queryGuaranteeEduCount(Map<String,Object> params);
    /**
     * 义务教育图表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeEducChart(Map<String,Object> params);
    /**
     * 义务教育搜索
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeEduSearch(Map<String,Object> params);
    
    
    
    /**
     * 医保
     * @param params
     * @return
     */
    public Map<String,Object> queryGuaranteeMedical(Map<String,Object> params);
    /**
     * 医保统计表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeMedicalList(Map<String,Object> params);
    /**
     * 医保统计表合计
     * @param params
     * @return
     */
    public Map<String,Object> queryGuaranteeMedicalCount(Map<String,Object> params);
    /**
     * 医保图表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeMedicalChart(Map<String,Object> params);
    /**
     * 医保搜索
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeMedicalSearch(Map<String,Object> params);
    
    
    
    
    /**
     * 住房
     * @param params
     * @return
     */
    public Map<String,Object> queryGuaranteeHouse(Map<String,Object> params);
    /**
     * 住房统计表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeHouseList(Map<String,Object> params);
    /**
     * 住房统计表合计
     * @param params
     * @return
     */
    public Map<String,Object> queryGuaranteeHouseCount(Map<String,Object> params);
    /**
     * 住房图表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeHouseChart(Map<String,Object> params);
    /**
     * 住房搜索
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryGuaranteeHouseSearch(Map<String,Object> params);
    
    
    
    
  
    
    
    
    
    
    
    /**
     * 道路硬化统计表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionRoadList(Map<String,Object> params);
    /**
     * 道路硬化统计表合计
     * @param params
     * @return
     */
    public Map<String,Object> queryConditionRoadCount(Map<String,Object> params);
    /**
     * 道路硬化图表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionRoadChart(Map<String,Object> params);
    /**
     * 道路硬化搜索
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionRoadSearch(Map<String,Object> params);
    
    
    
    
    
    
    
    /**
     * 安全饮水统计表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionWaterList(Map<String,Object> params);
    /**
     * 安全饮水统计表合计
     * @param params
     * @return
     */
    public Map<String,Object> queryConditionWaterCount(Map<String,Object> params);
    /**
     * 安全饮水图表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionWaterChart(Map<String,Object> params);
    /**
     * 安全饮水搜索
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionWaterSearch(Map<String,Object> params);
    
    
    
    
    /**
     * 卫生间统计表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionHealthList(Map<String,Object> params);
    /**
     * 卫生间统计表合计
     * @param params
     * @return
     */
    public Map<String,Object> queryConditionHealthCount(Map<String,Object> params);
    /**
     * 卫生间图表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionHealthChart(Map<String,Object> params);
    /**
     * 卫生间搜索
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionHealthSearch(Map<String,Object> params);
    
    
    
    
    /**
     * 通电统计表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionElectricityList(Map<String,Object> params);
    /**
     * 通电统计表合计
     * @param params
     * @return
     */
    public Map<String,Object> queryConditionElectricityCount(Map<String,Object> params);
    /**
     * 通电图表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionElectricityChart(Map<String,Object> params);
    /**
     * 通电搜索
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConditionElectricitySearch(Map<String,Object> params);
    
    
    
    
    /**
     * 广播电视统计表
     * @param params
     * @return
     */
   public List<Map<String,Object>> queryConditionBroadcastList(Map<String,Object> params);
   /**
    * 广播电视统计表合计
    * @param params
    * @return
    */
   public Map<String,Object> queryConditionBroadcastCount(Map<String,Object> params);
   /**
    * 广播电视图表
    * @param params
    * @return
    */
   public List<Map<String,Object>> queryConditionBroadcastChart(Map<String,Object> params);
   /**
    * 广播电视搜索
    * @param params
    * @return
    */
   public List<Map<String,Object>> queryConditionBroadcastSearch(Map<String,Object> params);
    
   
   

   /**
    * 宽带统计表
    * @param params
    * @return
    */
  public List<Map<String,Object>> queryConditionNetList(Map<String,Object> params);
  /**
   * 宽带统计表合计
   * @param params
   * @return
   */
  public Map<String,Object> queryConditionNetCount(Map<String,Object> params);
  /**
   * 宽带图表
   * @param params
   * @return
   */
  public List<Map<String,Object>> queryConditionNetChart(Map<String,Object> params);
  /**
   * 宽带搜索
   * @param params
   * @return
   */
  public List<Map<String,Object>> queryConditionNetSearch(Map<String,Object> params);
    
}
