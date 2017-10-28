package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.antiPoverty.model.poorFile.BaseListMode;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan.Plan;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNews.News;
import com.aspire.birp.modules.antiPoverty.model.poorFile.fileBusinessRest.TextLink;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileImplement.CountryFileImpBase;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.ChartConfigBar;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.FileIncomeData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers.Album;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers.Member;

/**
 * Created by kamyam on 2016/12/4.
 */
@Repository
public interface FamilyProjectDao {

	/**
     * 查柱状图数据   config_money_trend  产业扶贫
     * @param 
     * @return
     */
	  public  List<Map<String,Object>>  queryMoneyTrendChart(Map<String,Object> params);
		/**
	     * 查区域数据   config_money_trend 产业扶贫
	     * @param 
	     * @return
	     */
	 public  List<Map<String,Object>>  queryMoneyTrendChartByArea(Map<String,Object> params);
	  /**
	     * 查区域数据统计表   config_money_trend 产业扶贫
	     * @param 
	     * @return
	     */
	 public Map<String,Object>  queryMoneyTrendAllTableC(Map<String,Object> params);
	 
	 /**
	     * 查区域数据统计表   config_money_trend 产业扶贫
	     * @param 
	     * @return
	     */
	 public List<Map<String,Object>>   queryMoneyTrendAllTable(Map<String,Object> params);
	  
	
	 /**
	     * 查柱状图数据   config_money_trend 住房改造
	     * @param 
	     * @return
	    */
	 public  List<Map<String,Object>>  queryHouseTrendChart(Map<String,Object> params);
			/**
		     * 查区域数据   config_money_trend住房改造
		     * @param 
		     * @return
		     */
	 public  List<Map<String,Object>>  queryHouseTrendChartByArea(Map<String,Object> params);
		  /**
		     * 查区域数据统计表   config_money_trend住房改造
		     * @param 
		     * @return
		     */
	 public Map<String,Object>  queryHouseTrendAllTableC(Map<String,Object> params);
		 
		 /**
		     * 查区域数据统计表   config_money_trend 住房改造
		     * @param 
		     * @return
		     */
	public List<Map<String,Object>>  queryHouseTrendAllTable(Map<String,Object> params);
		 /**
		     * 查柱状图数据   config_money_trend 金融扶贫
		     * @param 
		     * @return
		    */
	 public  List<Map<String,Object>>  queryFinanceTrendChart(Map<String,Object> params);
				/**
			     * 查区域数据   config_money_trend 金融扶贫
			     * @param 
			     * @return
			     */
	 public  List<Map<String,Object>>  queryFinanceTrendChartByArea(Map<String,Object> params);
			  /**
			     * 查区域数据统计表   config_money_trend 金融扶贫
			     * @param 
			     * @return
			     */
	 public Map<String,Object>  queryFinanceTrendAllTableC(Map<String,Object> params);
			 
			 /**
			     * 查区域数据统计表   config_money_trend 金融扶贫
			     * @param 
			     * @return
			     */
	 public List<Map<String,Object>>  queryFinanceTrendAllTable(Map<String,Object> params);
	 /**
	     * 查柱状图数据   config_money_trend 资产扶贫
	     * @param 
	     * @return
	    */
	public  List<Map<String,Object>>  queryPropertyTrendChart(Map<String,Object> params);
				/**
			     * 查区域数据   config_money_trend 资产扶贫
			     * @param 
			     * @return
			     */
	public  List<Map<String,Object>>  queryPropertyTrendChartByArea(Map<String,Object> params);
			  /**
			     * 查区域数据统计表   config_money_trend 资产扶贫
			     * @param 
			     * @return
			     */
	public Map<String,Object>  queryPropertyTrendAllTableC(Map<String,Object> params);
			 
			 /**
			     * 查区域数据统计表   config_money_trend 资产扶贫
			     * @param 
			     * @return
			     */
	public List<Map<String,Object>>  queryPropertyTrendAllTable(Map<String,Object> params); 
		/**
	     * 查柱状图数据   config_money_trend 慰问扶贫
	     * @param 
	     * @return
	    */
	public  List<Map<String,Object>>  queryVisitTrendChart(Map<String,Object> params);
			/**
		     * 查区域数据   config_money_trend 慰问扶贫
		     * @param 
		     * @return
		     */
	public  List<Map<String,Object>>  queryVisitTrendChartByArea(Map<String,Object> params);
		  /**
		     * 查区域数据统计表   config_money_trend 慰问扶贫
		     * @param 
		     * @return
		     */
	public Map<String,Object>  queryVisitTrendAllTableC(Map<String,Object> params);
		 
		 /**
		     * 查区域数据统计表   config_money_trend 慰问扶贫
		     * @param 
		     * @return
		     */
	public List<Map<String,Object>>  queryVisitTrendAllTable(Map<String,Object> params); 
	/**
     * 查柱状图数据   config_money_trend 就业扶贫
     * @param 
     * @return
    */
	public  List<Map<String,Object>>  queryEmploymentTrendChart(Map<String,Object> params);
			/**
		     * 查区域数据   config_money_trend 就业扶贫
		     * @param 
		     * @return
		     */
	public  List<Map<String,Object>>  queryEmploymentTrendChartByArea(Map<String,Object> params);
		  /**
		     * 查区域数据统计表   config_money_trend 就业扶贫
		     * @param 
		     * @return
		     */
	public Map<String,Object>  queryEmploymentTrendAllTableC(Map<String,Object> params);
		 
		 /**
		     * 查区域数据统计表   config_money_trend 就业扶贫
		     * @param 
		     * @return
		     */
	public List<Map<String,Object>>  queryEmploymentTrendAllTable(Map<String,Object> params); 
	/**
     * 查柱状图数据   config_money_trend 技能培训
     * @param 
     * @return
    */
public  List<Map<String,Object>>  querySkillTrendChart(Map<String,Object> params);
		/**
	     * 查区域数据   config_money_trend 技能培训
	     * @param 
	     * @return
	     */
public  List<Map<String,Object>>  querySkillTrendChartByArea(Map<String,Object> params);
	  /**
	     * 查区域数据统计表   config_money_trend 技能培训
	     * @param 
	     * @return
	     */
public Map<String,Object> querySkillTrendA3(Map<String,Object> params);//在家务农人数
public  List<Map<String,Object>> querySkillTrendA4(Map<String,Object> params);//在家务农人数


public Map<String,Object>  querySkillTrendAllTableC(Map<String,Object> params);
	 
	 /**
	     * 查区域数据统计表   config_money_trend 技能培训
	     * @param 
	     * @return
	     */
public List<Map<String,Object>>  querySkillTrendAllTable(Map<String,Object> params); 
/**
 * 查柱状图数据   config_money_trend 教育扶贫
 * @param 
 * @return
*/
public  List<Map<String,Object>>  queryEduTrendChart(Map<String,Object> params);
		/**
	     * 查区域数据   config_money_trend 教育扶贫
	     * @param 
	     * @return
	     */
public  List<Map<String,Object>>  queryEduTrendChartByArea(Map<String,Object> params);
	  /**
	     * 查区域数据统计表   config_money_trend 教育扶贫
	     * @param 
	     * @return
	     */
public Map<String,Object>  queryEduTrendAllTableC(Map<String,Object> params);
	 
	 /**
	     * 查区域数据统计表   config_money_trend 教育扶贫
	     * @param 
	     * @return
	     */
public List<Map<String,Object>>  queryEduTrendAllTable(Map<String,Object> params); 
/**
 * 查区域数据统计表   config_money_trend 产业扶贫明细
 * @param 
 * @return
 */
public List<Map<String,Object>> queryIndustryDetail(Map<String,Object> params);
public long queryIndustryDetailCount(Map<String,Object> params);
/**
 * 查区域数据统计表   config_money_trend 金融扶贫明细
 * @param 
 * @return
 */
public List<Map<String,Object>> queryFinanceDetail(Map<String,Object> params);
public long queryFinanceDetailCount(Map<String,Object> params);
/**
 * 查区域数据统计表   config_money_trend 住房改造明细
 * @param 
 * @return
 */
public List<Map<String,Object>> queryHouseDetail(Map<String,Object> params);
public long queryHouseDetailCount(Map<String,Object> params);
/**
 * 查区域数据统计表   config_money_trend 资产扶贫明细
 * @param 
 * @return
 */
public List<Map<String,Object>> queryPropertyDetail(Map<String,Object> params);
public long queryPropertyDetailCount(Map<String,Object> params);
/**
 * 查区域数据统计表   config_money_trend 慰问扶贫明细
 * @param 
 * @return
 */
public List<Map<String,Object>> queryVisitDetail(Map<String,Object> params);
public long queryVisitDetailCount(Map<String,Object> params);
/**
 * 查区域数据统计表   config_money_trend 技能培训明细
 * @param 
 * @return
 */
public List<Map<String,Object>> querySkillDetail(Map<String,Object> params);
public long querySkillDetailCount(Map<String,Object> params);
/**
 * 查区域数据统计表   config_money_trend 教育扶贫明细
 * @param 
 * @return
 */
public List<Map<String,Object>> queryEduDetail(Map<String,Object> params);
public long queryEduDetailCount(Map<String,Object> params);
/**
 * 查区域数据统计表   config_money_trend 就业扶贫明细
 * @param 
 * @return
 */
public List<Map<String,Object>> queryEmploymentDetail(Map<String,Object> params);
public long queryEmploymentDetailCount(Map<String,Object> params);
}
