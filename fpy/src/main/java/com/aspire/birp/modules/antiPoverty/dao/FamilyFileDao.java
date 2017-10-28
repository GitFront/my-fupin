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
public interface FamilyFileDao {


	  public  List<Map<String,Object>> queryPerCapitaDisincomeChart(Map<String,Object> params);
	  public  List<ChartConfigBar> queryPerCapitaDisincomeCharto(Map<String,Object> params);
	  public  List<Map<String,Object>> queryPerCapitaDisincomeTable(Map<String,Object> params);
	  public  Map<String,Object> queryPerCapitaDisincomeCount(Map<String,Object> params);
	  public  FileIncomeData queryPerCapitaDisincomeCounto(Map<String,Object> params);
	
	 /**
     * 家庭成员
     * @param #{RESIDENCE_ID}<!--户ID-->
     * @return
     */
    public  List<Map<String,Object>> queryPovertyMember(Map<String,Object> params);
    public  List<Member> queryPovertyMembero(Map<String,Object> params);

	
	 /**
     * 帮扶施策图片列表 PROJ_ID PIC_TYPE
     * @param params
     * @return
     */
    public  List<Map<String,Object>> queryFamilyFileHelpPic(Map<String,Object> params);



    /**
     * 家庭相册
     * @param FAM_ID 户id
     * @return
     */
    public List<Map<String,Object>> queryFamilyfileFamPic(Map<String,Object> params);
    public List<Album> queryFamilyfileFamPico(Map<String,Object> params);


    /**
     *
     * 脱贫轨迹 图表
     * @param FAM_ID 户id MONTH_TAG 201609
     * @return
     */
    public List<Map<String,Object>> queryFamilyfileChart(Map<String,Object> params);
    public List<ChartConfigBar> queryFamilyfileCharto(Map<String,Object> params);


    /**
     *
     * 脱贫轨迹
     * @param FAM_ID 户id MONTH_TAG 201609
     * @return
     */
    public Map<String,Object> queryFamilyfileAidTrail(Map<String,Object> params);
    /**
     *
     * 资金投入  累计到村资金
     * @param FAM_ID 户id STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryCapitalTotalCount(Map<String,Object> params);
    /**
     *
     * 资金投入  当年到村资金
     * @param FAM_ID 户id STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryCapitalCount(Map<String,Object> params);

    /**
     * 列表
     * 资金投入  当年累计列表
     * @param FAM_ID 户id STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryCapitalListByYear(Map<String,Object> params);

    /**
     * 列表
     * 资金投入  每月资金列表
     * @param FAM_ID 户id STAT_TIME 日期可选
     * @return
     */
    public List<Map<String,Object>> queryCapitalList(Map<String,Object> params);



    /**
     * 列表
     * 帮扶动态
     * @param FAM_ID 户id STAT_TIME 日期可选
     * @return
     */
    public List<Map<String,Object>> queryFamilyFileTrends(Map<String,Object> params);
    
    public List<News> queryFamilyFileTrendso(Map<String,Object> params);
    /**
     * 详情
     * 帮扶动态
     * @param FAM_ID 户id STAT_TIME 日期可选
     * @return
     */
    public Map<String,Object> queryFamilyFileTrendsById(Map<String,Object> params);
    
    public  News queryFamilyFileTrendsByIdo(Map<String,Object> params);

    /**
     * 详情
     * 帮扶动态图片
     * @param FAM_ID 户id STAT_TIME 日期可选
     * @return
     */
    public List<Map<String,Object>> queryFamilyFileTrendsPicById(Map<String,Object> params);
    
    
    /**
     * 列表
     * 帮扶策划
     * @param FAM_ID 户id
     * @return
     */

    public List<Map<String,Object>> queryFamilyFileMethodList(Map<String,Object> params);
    /**
     * 项目数 进行中 已完成 预计投入资金 已投入资金 项目收益
     * 帮扶策划
     * @param FAM_ID 户id
     * @return
     */
    public Map<String,Object> queryFamilyFileMethodCount(Map<String,Object> params);
    public List<CountryFileImpBase> queryCountryFileImplementData(Map<String,Object> params);


    /**
     * 帮扶计划
     * @param FAM_ID 户id PLAN_YEAR 帮扶计划年度  nonnonoononoonono
     * @return
     */
    public List<Map<String,Object>> queryFamilyFilePlan(Map<String,Object> params);
    public List<Plan> queryFamilyFilePlano(Map<String,Object> params);



    /**
     * 生活条件
     * @param FAM_ID 户id
     * @return
     */
    public Map<String,Object> queryFamilyFileCond(Map<String,Object> params);
    public Object queryFamilyFileConditionData(Map<String,Object> params);


    /**
     * 贫困户档案基础信息
     * @param FAM_ID 户id
     * @return
     */
    public Map<String,Object> queryFamilyFileInfo(Map<String,Object> params);
    public Object queryFamilyFileBasicData(Map<String,Object> params);
    /**
     * 业务办理
     * @param FAM_ID 户id PROCESS_STATUS 办理状态
     * @return
     */
    public List<Map<String,Object>> queryBusiProcessList(Map<String,Object> params);
    public List<TextLink> queryBusiProcessListo(Map<String,Object> params);
    
    /**
     * 业务办理-新构造
     * @param FAM_ID 户id PROCESS_STATUS 办理状态
     * @return
     */
    public List<BaseListMode> queryBusiProcessListNew(Map<String,Object> params);
    
    /**
     * 数据轨迹
     * @param 
     * @return
     */
    public List<Map<String,Object>> queryFamilyFileDataPath(Map<String,Object> params);
    //public List<Map<String,Object>> queryFamilyFileDataPathUpdate(Map<String,Object> params);
}
