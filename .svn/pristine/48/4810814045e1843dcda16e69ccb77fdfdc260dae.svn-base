package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aspire.birp.modules.antiPoverty.model.poorFile.BaseListMode;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileEliminatePath.CountryFileListBase;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileImplement.CountryFileImpBase;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileNews.CountryFileBase;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFilePlan.CountryFilePlanBase;

/**
 * Created by kamyam on 2016/12/4.
 */
@Repository
public interface CountryFileDao {

	

    /**
     * 帮扶施策图片列表 PROJ_ID PIC_TYPE
     * @param params
     * @return
     */
    public  List<Map<String,Object>> queryCountryfileHelpPic(Map<String,Object> params);
  
    /**
     * 脱贫轨迹图表
     * @param params
     * @return
     */
    public  List<Map<String,Object>> queryCountryfileChart(Map<String,Object> params);
    public  List<CountryFileListBase> queryCountryFileListBase(Map<String,Object> params);

    /**
     *脱贫轨迹
     * @param params
     * @return
     */
    public Map<String,Object> queryCountryfileAidTrail(Map<String,Object> params);



    /**
     *
     * 资金投入  累计到村资金 
     * @param COUNTRY_PAC  STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryCapitalTotalCount(Map<String,Object> params);
    /**
     *
     * 资金投入  当年到村资金
     * @param COUNTRY_PAC  STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryCapitalCount(Map<String,Object> params);

    /**
     * 列表
     * 资金投入  当年累计列表
     * @param COUNTRY_PAC  STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryCapitalListByYear(Map<String,Object> params);

    /**
     * 列表
     * 资金投入  每月资金列表
     * @param COUNTRY_PAC  STAT_TIME 日期可选
     * @return
     */
    public List<Map<String,Object>> queryCapitalList(Map<String,Object> params);



    /**
     * 业务办理
     * @param COUNTRY_PAC  PROCESS_STATUS 办理状态
     * @return
     */
    public List<Map<String,Object>> queryBusiProcessList(Map<String,Object> params);

    /**Add new 17/05/16
     * 业务办理
     * @param COUNTRY_PAC  PROCESS_STATUS 办理状态
     * @return
     */
    public List<BaseListMode> queryBusiProcessListNew(Map<String,Object> params);
    /**
     * 帮扶会议图片详情
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryCountryfileMeetingPicById(Map<String,Object> params);

    /**
     * 帮扶会议详情
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryCountryfileMeetingById(Map<String,Object> params);

    /**
     * 帮扶会议列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryCountryfileMeetingList(Map<String,Object> params);

    public Object queryCountryFileMeetingNewsDetailData(Map<String,Object> params);

    /**
     * 查询帮扶动态图片详情
     * @param params COUNTRY_PAC
     * @return
     */
    public List<Map<String,Object>>  queryCountryfileTrendsPicById(Map<String,Object> params);
    
    /**
     * 帮扶动态详情
     * @param params COUNTRY_PAC
     * @return
     */
    public List<Map<String,Object>>  queryCountryfileTrendsById(Map<String,Object> params);
    public Object  queryCountryById(Map<String,Object> params);

    /**
     * 帮扶动态列表
     * @param params COUNTRY_PAC
     * @return
     */
    public List<Map<String,Object>> queryCountryfileTrendsList(Map<String,Object> params);
    public List<CountryFileBase> queryCountryfileList(Map<String,Object> params);

    /**
     *
     * @param params   COUNTRY_PAC 村pac PROJ_STATUS项目状态
     * @return
     */
    public List<Map<String,Object>> queryCountryfileHelpList(Map<String,Object> params);
    public List<CountryFileImpBase> queryCountryFileImplement1(Map<String,Object> params);

    /**
     * 帮扶施策汇总
     * @param params  COUNTRY_PAC 村pac
     * @return
     */
    public Map<String,Object> queryCountryfileHelpCount(Map<String,Object> params);
    public Object queryCountryFileImplement(Map<String,Object> params);

    /**
     * 帮扶计划
     * @param params COUNTRY_PAC PLAN_YEAR
     * @return
     */
    public List<Map<String,Object>> queryCountryFilePlanList(Map<String,Object> params);
    public List<CountryFilePlanBase> queryCountryPlanList(Map<String,Object> params);
    
    /**
     * 发展状况
     * @param params  COUNTRY_PAC 村pac
     * @return
     */
    public Map<String,Object> queryCountryDevStatus(Map<String,Object> params); 
    public Object querycountryFileDevelopmentStatusData(Map<String,Object> params);
    /**
     * 基本情况
     * @param params  COUNTRY_PAC 村pac
     * @return
     */
    public Map<String,Object> queryCountryFileStatus(Map<String,Object> params);

    /**
     * 村首页基本信息
     * @param params COUNTRY_PAC 村pac
     * @return
     */
    public Map<String,Object> queryCountryFileBasicInfo(Map<String,Object> params);
    public Object queryCountryFileBasicData(Map<String,Object> params);
    /**
     *数据轨迹
     * @param params COUNTRY_PAC 村pac
     * @return
     */
    public Map<String,Object>  queryCountryFileDataPath(Map<String,Object> params);
    public List<Map<String,Object>>  queryCountryFileDataPathData(Map<String,Object> params);

}
