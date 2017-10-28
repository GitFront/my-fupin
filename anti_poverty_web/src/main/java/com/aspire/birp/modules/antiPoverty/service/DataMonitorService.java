package com.aspire.birp.modules.antiPoverty.service;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.common.utils.CommonUtil;
import com.aspire.birp.modules.antiPoverty.dao.AlarmedPoorExceptionDao;
import com.aspire.birp.modules.antiPoverty.dao.CountryDao;
import com.aspire.birp.modules.antiPoverty.dao.DataMonDao;
import com.aspire.birp.modules.antiPoverty.dao.DisposableIncomeDao;
import com.aspire.birp.modules.antiPoverty.dao.DutyDao;
import com.aspire.birp.modules.antiPoverty.dao.EducationDao;
import com.aspire.birp.modules.antiPoverty.dao.ExampleCountryDao;
import com.aspire.birp.modules.antiPoverty.dao.FairlyDao;
import com.aspire.birp.modules.antiPoverty.dao.FamilyProjectDao;
import com.aspire.birp.modules.antiPoverty.dao.FiveLowDao;
import com.aspire.birp.modules.antiPoverty.dao.FundMonitoringDao;
import com.aspire.birp.modules.antiPoverty.dao.HousingDao;
import com.aspire.birp.modules.antiPoverty.dao.MedicalDao;
import com.aspire.birp.modules.antiPoverty.dao.PoorAnalyseDao;
import com.aspire.birp.modules.antiPoverty.dao.PoorChangeDao;
import com.aspire.birp.modules.antiPoverty.dao.PoorResultDao;
import com.aspire.birp.modules.antiPoverty.dao.ProjectManagementDao;
import com.aspire.birp.modules.antiPoverty.dao.TreeDataDao;
import com.aspire.birp.modules.antiPoverty.dao.WarningDao;
import com.aspire.birp.modules.antiPoverty.model.dataMonitor.dataMonitorBasicInfo.DataMonitorBasicInfo;
import com.aspire.birp.modules.antiPoverty.model.dataMonitor.dataMonitorBasicInfo.DataMonitorBasicInfoData;
import com.aspire.birp.modules.antiPoverty.model.dataMonitor.dataMonitorBasicInfo.MonitorBasicList;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.antiPoverty.utils.JsonFormatUtils;
import com.aspire.birp.modules.antiPoverty.utils.ReadThread;
import com.aspire.birp.modules.antiPoverty.utils.AnalyseThread;
import com.aspire.birp.modules.antiPoverty.utils.CommaProcessing;
import com.aspire.birp.modules.base.constant.BaseConstant;
import com.aspire.birp.modules.base.thread.FileWriteTaskThread;
import com.aspire.birp.modules.base.vo.ProgressObject;
import com.aspire.birp.modules.sys.utils.UserUtils;

/** 
 * 数据分析服务Service 
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2017年3月20日 下午4:53:51
 */
@Service
public class DataMonitorService {
	
	private static Logger log = LoggerFactory.getLogger(DataMonitorService.class);
	@Autowired
	private FairlyDao fairlyDao;
	@Autowired
	private TreeDataDao treeDao;
	@Autowired
	private PoorChangeDao pcDao;
	@Autowired
	private PoorResultDao prDao;
	@Autowired
	private DisposableIncomeDao diDao;
	@Autowired
	private FiveLowDao flDao;
	@Autowired
	private EducationDao eduDao;
	@Autowired
	private MedicalDao medicalDao;
	@Autowired
	private HousingDao houseDao;
	@Autowired
	private PoorAnalyseDao poorAnalyseDao;
	@Autowired
	private AlarmedPoorExceptionDao alarmedPoorExceptionDao;
	@Autowired
	private WarningDao warningDao;

	@Autowired
	private FamilyProjectDao fpDao;
	@Autowired
	private FundMonitoringDao fmtDao;
	@Autowired
	private ProjectManagementDao projectManagementDao;
	@Autowired
	private CountryDao countryDao;
	@Autowired
	private DutyDao dutyDao;
	@Autowired
	private ExampleCountryDao exampleCountryDao;
	@Autowired
	private DataMonDao dao;
	/*线程池管理*/
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	
	
	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * 导出文件进度缓存库，主要用于导出数据时使用
	 * key:searchKey标识单次导出
	 * value:本次导出的进度数据
	 */
	private static Map<String,ProgressObject> loadPool;
	/**
	 * 导出文件参数缓存库，主要用于导出数据时使用
	 * key:searchKey标识单次导出
	 * value:本次导出的参数数据
	 */
	private static Map<String,Object>paramsPool;

	public DataMonitorService() {
		super();
		loadPool = new HashMap<String, ProgressObject>();
		paramsPool = new HashMap<String, Object>();
	}
	
	public static void clearLoadPool() {
		log.info("清空导出文件进度缓存库信息");
		DataMonitorService.loadPool.clear();
	}
	
	public static void clearParamsdPool() {
		log.info("清空导出文件参数缓存库信息");
		DataMonitorService.paramsPool.clear();
	}
	
	public static Map<String, Object> getParamsPool() {
		return paramsPool;
	}

	public static void setParamsPool(Map<String, Object> paramsPool) {
		DataMonitorService.paramsPool = paramsPool;
	}
	
	
	
	public PoorChangeDao getPcDao() {
		return pcDao;
	}

	public void setPcDao(PoorChangeDao pcDao) {
		this.pcDao = pcDao;
	}

	public PoorAnalyseDao getPoorAnalyseDao() {
		return poorAnalyseDao;
	}

	public void setPoorAnalyseDao(PoorAnalyseDao poorAnalyseDao) {
		this.poorAnalyseDao = poorAnalyseDao;
	}

	public static Map<String, ProgressObject> getLoadPool() {
		return loadPool;
	}

	public static void setLoadPool(Map<String, ProgressObject> loadPool) {
		DataMonitorService.loadPool = loadPool;
	}

	//建档立卡日期
	private String getNewCreateDate(Map<String, Object> params) throws Exception{
		Long dateTime = projectManagementDao.getNewCreateDate(params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
		dateTime = dateTime == null ? 0 : dateTime;
		return fmt.format(sdf.parse(dateTime == 0 ? "20170701" : dateTime+""));
	}
	
	//行业数据日期
	private String getNewIndustryDate(Map<String, Object> params) throws Exception{
		return "-年-月-日";
	}

	/**
	 * 获取区域树JSON动态数据
	 * @param id 查找对应行政区域的ID
	 * @param level 见层级取值
	 * @param year 年
	 * @param wintype 数据监控窗口所属类型
	 * @param tabtype 数据监控窗口标签页类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorTree(String id,String level,String year,String wintype,String tabtype) {
		log.info("查询区域树信息===========");
		JSONObject json = new JSONObject();
		try{
			Map<String, Object> params =new HashMap<String, Object>();
			
			String unit = "";
			String hy_time = "";
			String total_count_field = "";
			String tips = "";
			String time_str = "";
			boolean isIndustryTime = false;
			
			JSONObject data = new JSONObject();
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			params.putAll(Constants.dateContants);
			if("poor".equals(wintype)){
				unit = "人";
				total_count_field = "POOR_COUNT";
				tips = "此处指标为累计贫困人口数。";
			}else if("result".equals(wintype)){
				if("plan_management".equals(tabtype)){
					unit = "人";
					total_count_field = "POVERTY_COUNT";
					tips = "此处指标为年度预脱贫计划人数。";
				}else{
					unit = "%";
					total_count_field = "POVERTY_RATE";
					tips = "此处指标为预脱贫率（按人数）%。";
				}
			}else if("average_income".equals(wintype)){
				unit = "元";
				total_count_field = "AVERAGE_INCOME_COUNT";
				if("success_analysis".equals(tabtype)){
					/**TODO 应急方案 tips = "此处指标为：有劳动能力的脱贫户人均可支配收入。";*/
					tips = "此处指标为：有劳动能力的贫困户人均可支配收入。";
				}else{
					tips = "此处指标为：有劳动能力的贫困户人均可支配收入。";
				}
			}else if("five_low".equals(wintype)){
				isIndustryTime = true;
				hy_time = getValue(params,"INDUSTRY_MZJ").toString();
				unit = "%";
				total_count_field = "FIVE_LOW_COUNT";
				tips = "此处指标为政策落实率(按人数)。";
			}else if("edu".equals(wintype)){
				isIndustryTime = true;
				hy_time = getValue(params,"INDUSTRY_JYT").toString();
				unit = "%";
				total_count_field = "EDU_COUNT";
				tips = "此处指标为义务教育政策落实率。";
			}else if("medical_policy".equals(wintype)){
				isIndustryTime = true;
				hy_time = getValue(params,"INDUSTRY_RSJ").toString();
				unit = "%";
				total_count_field = "MEDICAL_POLICY_ACOUNT";
				tips = "此处指标为医疗政策落实率。";
			}else if("house".equals(wintype)){
				unit = "%";
				total_count_field = "HOUSE_ACOUNT";
				if("implement_management".equals(tabtype)){
					isIndustryTime = true;
					hy_time = getValue(params,"INDUSTRY_ZJB").toString();
					tips = "此处指标为C/D级危房改造完成率。";
				}else{
					tips = "此处指标为建档立卡C/D级危房改造完成率。 ";
				}
			}else if("road".equals(wintype)){
				unit = "%";
				total_count_field = "ROAD_ACOUNT";
				tips = "此处指标为完全道路硬底化村比例。";
			}else if("water".equals(wintype)){
				unit = "%";
				total_count_field = "WATER_ACOUNT";
				tips = "此处指标为完全实现安全饮水村比例。";
			}else if("electricity".equals(wintype)){
				unit = "%";
				total_count_field = "ELECTRICITY_ACOUNT";
				tips = "此处指标为全通生活用电村比例。";
			}else if("medical_facility".equals(wintype)){
				unit = "%";
				total_count_field = "MEDICAL_FACILITY_ACOUNT";
				tips = "此处指标为有卫生站的村比例。";
			}else if("broadcast".equals(wintype)){
				unit = "%";
				total_count_field = "BROADCAST_ACOUNT";
				tips = "此处指标为完全通广播电视村比例。";
			}else if("net".equals(wintype)){
				unit = "%";
				total_count_field = "NET_ACOUNT";
				tips = "此处指标为网络全覆盖村比例。";
			}else if("poor_analysis".equals(wintype)){
				unit = "人";
				total_count_field = "CURRENT_COUNT";
				tips = "此处指标为当前贫困人口数。";
			}else if("duty".equals(wintype)){
				unit = "人";
				total_count_field = "DUTY_ACOUNT";
				tips = "此处指标为驻村干部人数。";
			}else if("project".equals(wintype)){
				if("project_order".equals(tabtype)){
					unit = "个";
					total_count_field = "PROJECT_ACOUNT";
					tips = "此处指标为累计项目总数。";
				}else{
					unit = "个";
					total_count_field = "PROJECT_ACOUNT";
					tips = "此处指标为项目数。";
				}
				
			}else if("project_family".equals(wintype) && "industry".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_INDUSTRY";
				tips = "此处指标为产业扶贫项目数。";
			}else if("project_family".equals(wintype) && "finance".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_FINANCE";
				tips = "此处指标为金融扶贫项目数。";
			}else if("project_family".equals(wintype) && "house".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_HOUSE";
				tips = "此处指标为住房改造项目数。";
			}else if("project_family".equals(wintype) && "property".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_PROPERTY";
				tips = "此处指标为资产扶贫项目数。";
			}else if("project_family".equals(wintype) && "visit".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_VISIT";
				tips = "此处指标为慰问扶贫项目数。";
			}else if("project_family".equals(wintype) && "employment".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_EMPLOYMENT";
				tips = "此处指标为就业扶贫项目数。";
			}else if("project_family".equals(wintype) && "skill".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_SKILL";
				tips = "此处指标为技能培训项目数。";
			}else if("project_family".equals(wintype) && "edu".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_EDU";
				tips = "此处指标为教育扶贫项目数。";
			}else if("project_country".equals(wintype) && "road".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_ROAD";
				tips = "此处指标为硬底化项目数。";
			}else if("project_country".equals(wintype) && "water".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_WATER";
				tips = "此处指标为饮水工程项目数。";
			}else if("project_country".equals(wintype) && "recreation_sport".equals(tabtype)){
				unit = "人";
				total_count_field = "PROJECT_COUNTRY_RECREATION_SP";
				tips = "此处指标为文体设施项目数。";
			}else if("project_country".equals(wintype) && "hygiene".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_ARTS_SPORTS";
				tips = "此处指标为卫生设施项目数。";
			}else if("project_country".equals(wintype) && "lamp".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_LAMP";
				tips = "此处指标为路灯安装项目数。";
			}else if("project_country".equals(wintype) && "farm".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_FARM";
				tips = "此处指标为农田水利项目数。";
			}else if("project_country".equals(wintype) && "public_facility".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_PUBLIC_FACILIT";
				tips = "此处指标为公共设施项目数。";
			}else if("project_country".equals(wintype) && "collective_economy".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_COLLECTIVE_ECO";
				tips = "此处指标为集体经济项目数。";
			}else if("project_country".equals(wintype) && "edu".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_EDU";
				tips = "此处指标为教育教学项目数。";
			}else if("alarmed_poor".equals(wintype)){
				isIndustryTime = true;
				hy_time = getValue(params,"INDUSTRY_GAT").toString();
				unit = "个";
				total_count_field = "ALARMED_POOR_ACOUNT";
				tips = "此处指标为异常项数。";
			}else if("alarmed_records".equals(wintype)){
				unit = "个";
				total_count_field = "ALARMED_RECORDS_ACOUNT";
				tips = "此处指标为异常记录数。";
			}else if("alarmed_money".equals(wintype)){
				unit = "%";
				total_count_field = "ALARMED_MONEY_ACOUNT";
				tips = "此处指标为帮扶资金小于<30万的相对贫困村比例。";
			}else if("alarmed_visit".equals(wintype)){
				unit = "%";
				total_count_field = "ALARMED_VISIT_ACOUNT";
				tips = "此处指标为1年内未被走访的贫困户比例。";
			}else if("capital".equals(wintype)){
				unit = "";
				total_count_field = "FINANCIAL_TOTAL";
				tips = "此处指标为：财政资金总额，单位“万元”，";
			}else if("example_country_building".equals(wintype)){
			unit = "";
			total_count_field = "MODELRATIO";
			tips = "此处指标为：行政村与自然村达标比例，";
			}
			
			String jd_time_s = "";
			String match_id = id;
			Long userPac = Long.parseLong(UserUtils.getAreaList().iterator().next().toString());
			params.put("PAC", userPac);
			params.put("year", year);
			Map<String,Object> userMap = treeDao.queryAreaById(params);
			String user_level = userMap.get("S_LEVEL").toString();
			
			Map<String,Object> areaMap = new HashMap<String,Object>();
			if(Long.parseLong(id)>userPac){
				params.put("PAC", id);
			}else{
				params.put("PAC", userPac);
			}
			List<Map<String,Object>> areaList = treeDao.queryAreaBySubId(params);
			jd_time_s = areaList.get(0).get("STAT_TIME").toString();
			Map<String,Object> province_map = new HashMap<String,Object>();
			Map<String,Object> city_map = new HashMap<String,Object>();
			Map<String,Object> county_map = new HashMap<String,Object>();
			Map<String,Object> town_map = new HashMap<String,Object>();
			Map<String,Object> country_map = new HashMap<String,Object>();
			for(Map<String,Object> map: areaList){
				Integer lvl = Integer.parseInt(getValue(map,"S_LEVEL").toString());
				if(lvl == 1)province_map= map;
				if(lvl == 2)city_map= map;
				if(lvl == 3)county_map= map;
				if(lvl == 4)town_map= map;
				if(lvl == 5)country_map= map;
			}
			if("province".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get(total_count_field));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				params.put("PPAC", province_map.get("S_ID").toString());
				areaMap.put(province_map.get("S_ID").toString(), treeDao.queryAreaByPPAC(params));
				//县
				String cityid = city_map.get("S_ID")==null?"":city_map.get("S_ID").toString();
				params.put("PPAC", cityid);
				areaMap.put(cityid, treeDao.queryAreaByPPAC(params));
				//镇
				String countyid = county_map.get("S_ID")==null?"":county_map.get("S_ID").toString();
				params.put("PPAC", countyid);
				areaMap.put(countyid, treeDao.queryAreaByPPAC(params));
				//村
				String townid = town_map.get("S_ID")==null?"":town_map.get("S_ID").toString();
				params.put("PPAC", townid);
				areaMap.put(townid, treeDao.queryAreaByPPAC(params));
			}else if("city".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get(total_count_field));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				List<Map<String,Object>> citylist = new ArrayList<Map<String,Object>>();
				city_map.put("AREA_ID", userPac);
				city_map.put("AREA_LEVEL", user_level);
				city_map.put(total_count_field, userMap.get(total_count_field));
				citylist.add(city_map);
				areaMap.put(province_map.get("S_ID").toString(), citylist);
				//县
				String cityid = city_map.get("S_ID")==null?"":city_map.get("S_ID").toString();
				params.put("PPAC", cityid);
				areaMap.put(cityid, treeDao.queryAreaByPPAC(params));
				//镇
				String countyid = county_map.get("S_ID")==null?"":county_map.get("S_ID").toString();
				params.put("PPAC", countyid);
				areaMap.put(countyid, treeDao.queryAreaByPPAC(params));
				//村
				String townid = town_map.get("S_ID")==null?"":town_map.get("S_ID").toString();
				params.put("PPAC", townid);
				areaMap.put(townid, treeDao.queryAreaByPPAC(params));
			}else if("county".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get(total_count_field));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				List<Map<String,Object>> citylist = new ArrayList<Map<String,Object>>();
				city_map.put("AREA_ID", userPac);
				city_map.put("AREA_LEVEL", user_level);
				city_map.put(total_count_field, userMap.get(total_count_field));
				citylist.add(city_map);
				areaMap.put(province_map.get("S_ID").toString(), citylist);
				//县
				List<Map<String,Object>> countylist = new ArrayList<Map<String,Object>>();
				county_map.put("AREA_ID", userPac);
				county_map.put("AREA_LEVEL", user_level);
				county_map.put(total_count_field, userMap.get(total_count_field));
				countylist.add(county_map);
				areaMap.put(city_map.get("S_ID").toString(), countylist);
				//镇
				String countyid = county_map.get("S_ID")==null?"":county_map.get("S_ID").toString();
				params.put("PPAC", countyid);
				areaMap.put(countyid, treeDao.queryAreaByPPAC(params));
				//村
				String townid = town_map.get("S_ID")==null?"":town_map.get("S_ID").toString();
				params.put("PPAC", townid);
				areaMap.put(townid, treeDao.queryAreaByPPAC(params));
			}else if("town".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get(total_count_field));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				List<Map<String,Object>> citylist = new ArrayList<Map<String,Object>>();
				city_map.put("AREA_ID", userPac);
				city_map.put("AREA_LEVEL", user_level);
				city_map.put(total_count_field, userMap.get(total_count_field));
				citylist.add(city_map);
				areaMap.put(province_map.get("S_ID").toString(), citylist);
				//县
				List<Map<String,Object>> countylist = new ArrayList<Map<String,Object>>();
				county_map.put("AREA_ID", userPac);
				county_map.put("AREA_LEVEL", user_level);
				county_map.put(total_count_field, userMap.get(total_count_field));
				countylist.add(county_map);
				areaMap.put(city_map.get("S_ID").toString(), countylist);
				//镇
				List<Map<String,Object>> townlist = new ArrayList<Map<String,Object>>();
				town_map.put("AREA_ID", userPac);
				town_map.put("AREA_LEVEL", user_level);
				town_map.put(total_count_field, userMap.get(total_count_field));
				townlist.add(town_map);
				areaMap.put(county_map.get("S_ID").toString(), townlist);
				//村
				String townid = town_map.get("S_ID")==null?"":town_map.get("S_ID").toString();
				params.put("PPAC", townid);
				areaMap.put(townid, treeDao.queryAreaByPPAC(params));
			}else if("country".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get(total_count_field));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				List<Map<String,Object>> citylist = new ArrayList<Map<String,Object>>();
				city_map.put("AREA_ID", userPac);
				city_map.put("AREA_LEVEL", user_level);
				city_map.put(total_count_field, userMap.get(total_count_field));
				citylist.add(city_map);
				areaMap.put(province_map.get("S_ID").toString(), citylist);
				//县
				List<Map<String,Object>> countylist = new ArrayList<Map<String,Object>>();
				county_map.put("AREA_ID", userPac);
				county_map.put("AREA_LEVEL", user_level);
				county_map.put(total_count_field, userMap.get(total_count_field));
				countylist.add(county_map);
				areaMap.put(city_map.get("S_ID").toString(), countylist);
				//镇
				List<Map<String,Object>> townlist = new ArrayList<Map<String,Object>>();
				town_map.put("AREA_ID", userPac);
				town_map.put("AREA_LEVEL", user_level);
				town_map.put(total_count_field, userMap.get(total_count_field));
				townlist.add(town_map);
				areaMap.put(county_map.get("S_ID").toString(), townlist);
				//村
				List<Map<String,Object>> countrylist = new ArrayList<Map<String,Object>>();
				country_map.put("AREA_ID", userPac);
				country_map.put("AREA_LEVEL", user_level);
				country_map.put(total_count_field, userMap.get(total_count_field));
				countrylist.add(country_map);
				areaMap.put(town_map.get("S_ID").toString(), countrylist);
			}
			if(isIndustryTime){
				time_str = "建档立卡数据日期"+jd_time_s.substring(0, 4)+"年"+jd_time_s.substring(4, 6)+"月"+jd_time_s.substring(6, 8)+"日"
						+"。<br>行业数据更新日期-年-月-日"+"。";//以后可能再增加行业数据日期
			}else{
				time_str = "建档立卡数据日期"+jd_time_s.substring(0, 4)+"年"+jd_time_s.substring(4, 6)+"月"+jd_time_s.substring(6, 8)+"日。";
			}
			data.put("tip", tips+"<br>"+time_str);
			data.put("match_node_id", match_id);
			
			List<Map<String,Object>> provList = (List<Map<String, Object>>) areaMap.get("province");
			List<JSONObject> provtree = new ArrayList<JSONObject>();
			JSONObject prov = new JSONObject();
			prov.put("id", provList.get(0).get("S_ID"));
			prov.put("name", provList.get(0).get("S_NAME")+"("+provList.get(0).get(total_count_field)+unit+")");
			prov.put("area_level", provList.get(0).get("AREA_LEVEL"));
			prov.put("area_id", provList.get(0).get("AREA_ID"));
			/*****************市列表***********************************************/
			List<Map<String,Object>> cityList = (List<Map<String, Object>>) areaMap.get(provList.get(0).get("S_ID"));
			if(cityList!=null && cityList.size()>0){
				List<JSONObject> citytree = new ArrayList<JSONObject>();
				for(Map<String,Object> citymap: cityList){//市级层次
					JSONObject city = new JSONObject();
					city.put("id", citymap.get("S_ID"));
					city.put("name", citymap.get("S_NAME")+"("+citymap.get(total_count_field)+unit+")");
					if(cityList.size() > 0){
						city.put("area_level", "city");
						city.put("area_id", citymap.get("S_ID"));
					}else{
						city.put("area_level", citymap.get("AREA_LEVEL"));
						city.put("area_id", citymap.get("AREA_ID"));
					}
					city.put("isParent", true);
					/*****************县列表***********************************************/
					List<Map<String,Object>> countyList = (List<Map<String, Object>>) areaMap.get(citymap.get("S_ID"));
					if(countyList!=null && countyList.size()>0){
						List<JSONObject> countytree = new ArrayList<JSONObject>();
						for(Map<String,Object> countymap: countyList){//县级层次
							JSONObject county = new JSONObject();
							county.put("id", countymap.get("S_ID"));
							county.put("name", countymap.get("S_NAME")+"("+countymap.get(total_count_field)+unit+")");
							if(countyList.size() > 0){
								county.put("area_level", "county");
								county.put("area_id", countymap.get("S_ID"));
							}else{
								county.put("area_level", countymap.get("AREA_LEVEL"));
								county.put("area_id", countymap.get("AREA_ID"));
							}
							county.put("isParent", true);
							/*****************镇列表***********************************************/
							List<Map<String,Object>> townList = (List<Map<String, Object>>) areaMap.get(countymap.get("S_ID"));
							if(townList!=null && townList.size()>0){
								List<JSONObject> towntree = new ArrayList<JSONObject>();
								for(Map<String,Object> townMap:townList){//镇级层次
									JSONObject town = new JSONObject();
									town.put("id", townMap.get("S_ID"));
									town.put("name", townMap.get("S_NAME")+"("+townMap.get(total_count_field)+unit+")");
									if(townList.size() > 0){
										town.put("area_level", "town");
										town.put("area_id", townMap.get("S_ID"));
									}else{
										town.put("area_level", townMap.get("AREA_LEVEL"));
										town.put("area_id", townMap.get("AREA_ID"));
									}
									town.put("isParent", true);
									/*****************村列表***********************************************/
									List<Map<String,Object>> countryList = (List<Map<String, Object>>) areaMap.get(townMap.get("S_ID"));
									if(countryList!=null && countryList.size()>0){
										List<JSONObject> countrytree = new ArrayList<JSONObject>();
										for(Map<String,Object> countryMap : countryList){//村级层次
											JSONObject country = new JSONObject();
											country.put("id", countryMap.get("S_ID"));
											country.put("name", countryMap.get("S_NAME")+"("+countryMap.get(total_count_field)+unit+")");
											country.put("area_level", "country");
											country.put("area_id", countryMap.get("S_ID"));
											countrytree.add(country);
										}
										town.put("children", countrytree);
									}
									towntree.add(town);
								}
								county.put("children", towntree);
							}
							countytree.add(county);
						}
						city.put("children", countytree);
					}
					citytree.add(city);
				}
				prov.put("children", citytree);
			}
			provtree.add(prov);
			data.put("tree", provtree);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("区域树查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取区域树子节点JSON数据
	 * @param id 查找对应行政区域的ID
	 * @param level 见层级取值
	 * @param year 年
	 * @param wintype 数据监控窗口所属类型
	 * @param tabtype 数据监控窗口标签页类型
	 * @return
	 */
	public String getDataMonitorSubTree(String id,String level,String year,String wintype,String tabtype) {
		log.info("查询区域树子节点信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("PPAC", id);
			params.put("year", year);
			List<Map<String,Object>> subAreaList = treeDao.queryAreaByPPAC(params);
			
			String unit = "";
			String total_count_field = "";
			if("poor".equals(wintype)){
				unit = "人";
				total_count_field = "POOR_COUNT";
			}else if("result".equals(wintype)){
				if("plan_management".equals(tabtype)){
					unit = "人";
					total_count_field = "POVERTY_COUNT";
				}else{
					unit = "%";
					total_count_field = "POVERTY_RATE";
				}
			}else if("average_income".equals(wintype)){
				unit = "元";
				total_count_field = "AVERAGE_INCOME_COUNT";
			}else if("five_low".equals(wintype)){
				unit = "%";
				total_count_field = "FIVE_LOW_COUNT";
			}else if("edu".equals(wintype)){
				unit = "%";
				total_count_field = "EDU_COUNT";
			}else if("medical_policy".equals(wintype)){
				unit = "%";
				total_count_field = "MEDICAL_POLICY_ACOUNT";
			}else if("house".equals(wintype)){
				unit = "%";
				total_count_field = "HOUSE_ACOUNT";
			}else if("road".equals(wintype)){
				unit = "%";
				total_count_field = "ROAD_ACOUNT";
			}else if("water".equals(wintype)){
				unit = "%";
				total_count_field = "WATER_ACOUNT";
			}else if("electricity".equals(wintype)){
				unit = "%";
				total_count_field = "ELECTRICITY_ACOUNT";
			}else if("medical_facility".equals(wintype)){
				unit = "%";
				total_count_field = "MEDICAL_FACILITY_ACOUNT";
			}else if("broadcast".equals(wintype)){
				unit = "%";
				total_count_field = "BROADCAST_ACOUNT";
			}else if("net".equals(wintype)){
				unit = "%";
				total_count_field = "NET_ACOUNT";
			}else if("poor_analysis".equals(wintype)){
				unit = "人";
				total_count_field = "POOR_COUNT";
			}else if("duty".equals(wintype)){
				unit = "人";
				total_count_field = "DUTY_ACOUNT";
			}else if("project".equals(wintype)){
				unit = "个";
				total_count_field = "PROJECT_ACOUNT";
			}else if("project_family".equals(wintype) && "industry".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_INDUSTRY";
			}else if("project_family".equals(wintype) && "finance".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_FINANCE";
			}else if("project_family".equals(wintype) && "house".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_HOUSE";
			}else if("project_family".equals(wintype) && "property".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_PROPERTY";
			}else if("project_family".equals(wintype) && "visit".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_VISIT";
			}else if("project_family".equals(wintype) && "employment".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_EMPLOYMENT";
			}else if("project_family".equals(wintype) && "skill".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_SKILL";
			}else if("project_family".equals(wintype) && "edu".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_FAMILY_EDU";
			}else if("project_country".equals(wintype) && "road".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_ROAD";
			}else if("project_country".equals(wintype) && "water".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_WATER";
			}else if("project_country".equals(wintype) && "recreation_sport".equals(tabtype)){
				unit = "人";
				total_count_field = "PROJECT_COUNTRY_RECREATION_SP";
			}else if("project_country".equals(wintype) && "hygiene".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_ARTS_SPORTS";
			}else if("project_country".equals(wintype) && "lamp".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_LAMP";
			}else if("project_country".equals(wintype) && "farm".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_FARM";
			}else if("project_country".equals(wintype) && "public_facility".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_PUBLIC_FACILIT";
			}else if("project_country".equals(wintype) && "collective_economy".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_COLLECTIVE_ECO";
			}else if("project_country".equals(wintype) && "edu".equals(tabtype)){
				unit = "个";
				total_count_field = "PROJECT_COUNTRY_EDU";
			}else if("alarmed_poor".equals(wintype)){
				unit = "个";
				total_count_field = "ALARMED_POOR_ACOUNT";
			}else if("alarmed_records".equals(wintype)){
				unit = "个";
				total_count_field = "ALARMED_RECORDS_ACOUNT";
			}else if("alarmed_money".equals(wintype)){
				unit = "%";
				total_count_field = "ALARMED_MONEY_ACOUNT";
			}else if("alarmed_visit".equals(wintype)){
				unit = "%";
				total_count_field = "ALARMED_VISIT_ACOUNT";
			}else if("capital".equals(wintype)){
				unit = "";
				total_count_field = "FINANCIAL_TOTAL";
			}
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			List<JSONObject> dataList = new ArrayList<JSONObject>();
			if(subAreaList!=null){
				for(Map<String,Object> map: subAreaList){
					JSONObject area = new JSONObject();
					area.put("id", getValue(map,"S_ID"));
					area.put("pId", getValue(map,"S_ID"));
					area.put("name", getValue(map,"S_NAME")+"("+getValue(map,total_count_field)+unit+")");
					area.put("area_level", Constants.nextLevelMap.get(level));
					area.put("area_id", getValue(map,"S_ID"));
					if(!"country".equals(Constants.nextLevelMap.get(level))){
						area.put("isParent", true);
					}
					dataList.add(area);
				}
			}
			json.put("data", dataList);
		}catch(Exception e){
			e.printStackTrace();
			log.error("区域树子节点查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/** 05/18修改
	 * 数据监控基础信息JSON
	 * @param id 区域id
	 * @param level 区域层级
	 * @return
	 */
	public String getDataMonitorBasicInfo(String id,String level,String year) {
		log.info("查询数据监控基础信息-年份===========");
		//JSONObject json = new JSONObject(); 
		DataMonitorBasicInfo di = new DataMonitorBasicInfo();
		DataMonitorBasicInfoData gd = new DataMonitorBasicInfoData();
		try{
			/*json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			JSONObject years = new JSONObject();
			
			List<JSONObject> available_years_list = new ArrayList<JSONObject>();
			for(int i=2016;i<=2018;i++){
				JSONObject available_years = new JSONObject();
				available_years.put("name", i+"年");
				available_years.put("value", i);
				available_years_list.add(available_years);
			}
			years.put("available_years", available_years_list);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			years.put("selected_value", sdf.format(new Date()));
			
			data.put("years", years);			
			json.put("data", data);*/
			di.setCode(0);
			di.setMsg("获取信息成功");
			List<MonitorBasicList> available_years_list = new ArrayList<MonitorBasicList>();
			for(int i=2016;i<=2018;i++){
				MonitorBasicList available_years = new MonitorBasicList();
				available_years.setName(i+"年");
				available_years.setValue(i+"");
				available_years_list.add(available_years);
			}
			gd.setAvailableList(available_years_list);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			gd.setSelectedValue(year == null ? sdf.format(new Date()) : year );
			di.setData(gd);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案业务办理查询失败异常信息："+e.getMessage());
			/*json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");		*/	
			di.setCode(1);
			di.setMsg("数据处理异常，获取信息失败!");	
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		//return json.toString();
		return di.toString();
	}
	
	/**
	 * 数据监控基础信息JSON
	 * @param id 区域id
	 * @param level 区域层级
	 * @return
	 */
	public String getDataMonitorContentBasicInfo(String id,String level,String year) {
		log.info("查询数据监控基础信息-区域属性信息===========");
		JSONObject json = new JSONObject();
		try{
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject scope_available = new JSONObject();
			scope_available.put("1", true);
			scope_available.put("2", true);
			scope_available.put("3", true);
			scope_available.put("4", true);
			scope_available.put("5", true);
			
			data.put("scope_available", scope_available);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("数据监控基础信息-区域属性查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 数据监控窗口排序页面基础信息
	 * @param id 行政区域的ID
	 * @param level 层级
	 * @param year 年
	 * @param wintype 数据监控窗口所属类型
	 * @param tabtype 数据监控窗口标签页类型
	 * @return
	 */
	public String getDataMonitorOrderBasicInfo(String id,String level,String year,String wintype,String tabtype) {
		JSONObject json = new JSONObject();
		try{
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			List<JSONObject> params_arr = new ArrayList<JSONObject>();
			Map<String,Object> map = new HashMap<String,Object>();
			int order_size = 0;
			
			//扶贫对象变动排序
			if("poor".equals(wintype) && "change_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-扶贫对象变动排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"准确率","累计贫困户数","累计贫困人口数","当前贫困户数","当前贫困人口数","新增贫困户数","终止贫困户数","并户贫困户数","销户贫困户数","拆户贫困户数","自然增加人口数","自然减少人口数"},
						new Integer[]{1,11,12,2,3,4,5,6,7,8,9,10},
						new Boolean[]{false,false,false,false,false,false,false,false,false,false,false,false}});
			//脱贫成效排序
			}else if("result".equals(wintype) && "result_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-脱贫成效排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"预脱贫率(按人数)%","预脱贫人数","年度预脱贫计划人数","年度预脱贫人数","年度监测达标人数"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
			//人均可支配收入排序
			}else if("average_income".equals(wintype) && "income_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-人均可支配收入排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"人均可支配收入(元)","可支配收入(万元)","工资性收入(万元)","生产经营收入(万元)","转移性收入(万元)","转移性支出(万元)","生产经营支出(万元)"},
						new Integer[]{1,2,3,4,5,6,7},
						new Boolean[]{false,false,false,false,false,false,false}});
			//低五保政策落实排序
			}else if("five_low".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-低五保政策落实排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"政策落实率","低保落实率","五保落实率","重合率","低保重合率","五保重合率"},
						new Integer[]{1,2,3,4,5,6},
						new Boolean[]{false,false,false,false,false,false}});
			//教育政策落实排序
			}else if("edu".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-教育政策落实排序===========");
				order_size = 5;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("1"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr3",new Object[]{"edu_stage","教育阶段","radio",Arrays.asList("2"),
						new String[]{"全部","义务教育","高中(职中)教育"},
						new Integer[]{1,2,3},
						new Boolean[]{false,false,false}});
				map.put("objArr4",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"政策落实率","重合率"},
						new Integer[]{1,2},
						new Boolean[]{false,false}});
			//医疗政策落实排序
			}else if("medical_policy".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-医疗政策落实排序===========");
				order_size = 4;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("1"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr3",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"政策落实率","重合率"},
						new Integer[]{1,2},
						new Boolean[]{false,false}});
			//住房政策落实排序
			}else if("house".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-住房政策落实排序===========");
				order_size = 5;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"house_level","危房级别","radio",Arrays.asList("6"),
						new String[]{"C\\D级","D级","C级","B级","A级"},
						new Integer[]{6,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr3",new Object[]{"schedule","改造进度","radio",Arrays.asList("1"),
						new String[]{"全部","已完成","已动工","未动工"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr4",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"住房改造完成率(建档立卡)","住房改造户数"},
						new Integer[]{1,2},
						new Boolean[]{false,false}});
/*测试使用*/
				//道路硬底化排序
			}else if("road".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-道路硬底化排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"完全道路硬底化村比例","帮扶村未通沥青(水泥)路里程数","当年改造完成里程数"},
						new Integer[]{1,2,3},
						new Boolean[]{false,false,false}});
			//安全饮水排序
			}else if("water".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-安全饮水排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"完全实现安全饮水村比例","未完全实现安全饮水的村数","完成饮水改造户数"},
						new Integer[]{1,2,3},
						new Boolean[]{false,false,false}});
			//生活用电排序
			}else if("electricity".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-生活用电排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"全通生活用电的村比例","未全通生活用电的村数","完成生活用电改造的村数"},
						new Integer[]{1,2,3},
						new Boolean[]{false,false,false}});
			//医疗设施排序
			}else if("medical_facility".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-医疗设施排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"有卫生站的村比例","无卫生站的村数","新建卫生站的村数","有执业医师的村数","无执业医师的村数","新增执业医师的村数"},
						new Integer[]{1,2,3,4,5,6},
						new Boolean[]{false,false,false,false,false,false}});
			//通广播电视排序
			}else if("broadcast".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-通广播电视排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"完全通广播电视村比例","未全通广播电视的村数","通广播电视改造村数"},
						new Integer[]{1,2,3},
						new Boolean[]{false,false,false}});
			//网络覆盖排序
			}else if("net".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-网络覆盖排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"网络全覆盖村比例","网络全覆盖的村数","网络未全覆盖的村数"},
						new Integer[]{1,2,3},
						new Boolean[]{false,false,false}});
			//项目排序
			}else if("project".equals(wintype) && "project_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-项目排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"累计项目总数(个)","累计资金总数(万元)","累计村项目数(个)","累计村项目资金(万元)","累计户项目数(个)","累计户项目资金(万元)","年度项目总数(个)","年度项目资金(万元)","年度到村项目数(个)","年度到村项目资金(万元)","年度户项目数(个)","年度户项目资金(万元)"},
						new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12},
						new Boolean[]{false,false,false,false,false,false,false,false,false,false,false,false}});
			//贫困识别排序
			}else if("alarmed_poor".equals(wintype) && "exception_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-贫困识别排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"exception_type","异常类型","check",Arrays.asList("1","2","3","4","5","6","7"),
						new String[]{"身份证不匹配","低保五保不匹配","残疾信息不匹配","名下有房","名下有车","有工商注册信息","享有财政供养"},
						new Integer[]{1,2,3,4,5,6,7},
						new Boolean[]{false,false,false,false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"异常项数","异常数比例"},
						new Integer[]{1,2},
						new Boolean[]{false,false}});
			//建档立卡异常排序
			}else if("alarmed_records".equals(wintype) && "exception_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-建档立卡异常排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"exception_type","异常类型","check",Arrays.asList("20","21","22","23"),
						new String[]{"户主身份信息错漏","成员身份信息错漏","残疾信息错漏","重复身份信息记录"},
						new Integer[]{20,21,22,23},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"异常项数","异常数比例"},
						new Integer[]{1,2},
						new Boolean[]{false,false}});
			//帮扶资金排序
			}else if("alarmed_money".equals(wintype) && "money_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-帮扶资金排序===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"exception_type","异常类型","radio",Arrays.asList("1"),
						new String[]{"帮扶资金小于＜30万的相对贫困村比例","帮扶资金小于＜30万的相对贫困村数"},
						new Integer[]{1,2},
						new Boolean[]{false,false}});
			//贫困户走访排序
			}else if("alarmed_visit".equals(wintype) && "visit_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-贫困户走访排序===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"exception_type","异常类型","radio",Arrays.asList("1"),
						new String[]{"未被走访的累计贫困户比例","未被走访的累计贫困户数"},
						new Integer[]{1,2},
						new Boolean[]{false,false}});
				
			}else if("capital".equals(wintype) && "capital_order".equals(tabtype)){
				log.info("数据监控窗口排序页面基础信息-资金监控项目排序===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"sort_area","排序类型","radio",Arrays.asList(Constants.levelTranMap.get(level)),
						new String[]{"地级市","县（市）","镇（乡）","村"},
						new Integer[]{1,2,3,4},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new Integer[]{1,2,3,4,5},
						new Boolean[]{false,false,false,false,false}});
/*				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"年度项目资金总额(万元)","年度财政资金(万元)","财政资金余额(万元)","财政资金下拨周期(天)","年度行业资金(万元)","年度社会资金(万元)","年度帮扶单位自筹资金(万元)"},
						new Integer[]{1,2,3,4,5,6,7},
						new Boolean[]{false,false,false,false,false,false,false}});*/
				map.put("objArr2",new Object[]{"taxis_indexes","排序指标","radio",Arrays.asList("1"),
						new String[]{"年度项目资金总额(万元)","年度行业资金(万元)","年度社会资金(万元)","年度帮扶单位自筹资金(万元)"},
						new Integer[]{1,5,6,7},
						new Boolean[]{false,false,false,false}});
			}
			
			/*DataMonitorOrderBasicInfo di = new DataMonitorOrderBasicInfo();
			DataMonitorOrderBasicInfoData gd = new DataMonitorOrderBasicInfoData();*/
			for(int i=0;i<order_size;i++){
				Object[] arr = (Object[]) getValue(map,"objArr"+i);
				JSONObject params = new JSONObject();
				params.put("key", arr[0]);
				params.put("label", arr[1]);
				params.put("type", arr[2]);
				params.put("selected_value", arr[3]);
				List<JSONObject> values_arr = new ArrayList<JSONObject>();
				String[] label_arr = (String[]) arr[4];
				Integer[] value_arr = (Integer[]) arr[5];
				Boolean[] disabled_arr = (Boolean[]) arr[6];
				/*
				DataMonitorOrderBasicInfoBese db = new DataMonitorOrderBasicInfoBese();
				db.setKey((String) arr[0]);
				db.setLabel(arr[1]);
				db.setType(arr[2]);
				db.setSelected_value(arr[3]);
				ValuesNew vn = new ValuesNew();*/
				

				for(int j=0;j<label_arr.length;j++){
					JSONObject values = new JSONObject();
					values.put("label", label_arr[j]);
					values.put("value", value_arr[j]);
					values.put("disabled", disabled_arr[j]);
					values_arr.add(values);
					/*vn.setLabel(value_arr[j]);
					vn.setValue(value_arr[j]);
					vn.setDisabled(disabled_arr[j]);*/
				}
				//2017
				/*db.setValuesNew(vn);
				gd.setChartList((List<DataMonitorOrderBasicInfoBese>) db);
				di.setData(gd);*/
				
				params.put("values", values_arr);
				params_arr.add(params);
				
				
			}
			data.put("params", params_arr);
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("数据监控窗口排序页面基础信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 数据监控窗口排序页面表格数据
	 * @param id 区域ID
	 * @param level 层级
	 * @param year 年
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitoOrderTable(HttpServletRequest request,String id,String level,String year,String wintype,String tabtype,Integer page) {
		JSONObject json = new JSONObject();
		try{
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			   // System.out.println("key: "+entry.getKey()+"  value: "+entry.getValue()[0]);
			}
			String taxisIndexes = params.get("taxis_indexes") == null ? "" : (String)params.get("taxis_indexes");

			 String exceptionType = params.get("exception_type") == null ? "" : (String)params.get("exception_type");
			    List<Integer> exceptionTypeStr = new ArrayList<Integer>();
			    if(null != exceptionType && !"".equals(exceptionType)){
			    	String[] exceptionTypes = exceptionType.split(",");
			    	for(String pr : exceptionTypes){
			    		exceptionTypeStr.add(Integer.parseInt(pr));
			    	}
			    	
			    	params.put("list",exceptionTypeStr);
			    }
			//查询table数据
			params.put("pageSize", 10);
			
			
			List<Map<String, Object>> table_list = new ArrayList<Map<String, Object>>();
			long total_count = 0;
			
			String order_table_title = "";
			String export_task_id = "";
			String export_url = "";
			String[] sort_area = {"地级市","县（市）","镇（乡）","村"};
			String[] sort_area_en = {"city","county","town","country"};
			String sort_level = "";
			//扶贫对象变动排序
			if("poor".equals(wintype) && "change_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-扶贫对象变动排序===========");
				table_list = pcDao.queryPoorChangeSort(params);
				total_count = pcDao.queryPoorChangeSortCount(params);
				export_task_id = "ChangeOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ChangeOrder";
				String[] taxis_indexes = {"准确率","当前贫困户数","当前贫困人口数","新增贫困户数","终止贫困户数","并户贫困户数","销户贫困户数","拆户贫困户数","自然增加人口数","自然减少人口数","累计贫困户数","累计贫困人口数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//脱贫成效排序
			}else if("result".equals(wintype) && "result_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-脱贫成效排序===========");
				table_list = prDao.queryPoorResultSort(params);
				total_count = prDao.queryPoorResultSortCount(params);
				export_task_id = "ResultOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ResultOrder";
				String[] taxis_indexes = {"预脱贫率(按人数)%","预脱贫人数","年度预脱贫计划人数","年度预脱贫人数","年度监测达标人数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//人均可支配收入排序
			}else if("average_income".equals(wintype) && "income_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-人均可支配收入排序===========");
				table_list = diDao.queryIncomeSort(params);
				total_count = diDao.queryIncomeSortCount(params);
				export_task_id = "IncomeOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=IncomeOrder";
				String[] taxis_indexes = {"人均可支配收入","可支配收入","工资性收入","生产经营收入","转移性收入","转移性支出","生产经营支出"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//低五保政策落实排序
			}else if("five_low".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-低五保政策落实排序===========");
				table_list = flDao.flveLowSort(params);
				total_count = flDao.flveLowSortCount(params);
				export_task_id = "FiveLowOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FiveLowOrder";
				String[] taxis_indexes = {"政策落实率(按人数)","低保落实率(按人数)","五保落实率(按人数)","重合率(按人数)","低保重合率(按人数)","五保重合率(按人数)"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//教育政策落实排序
			}else if("edu".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-教育政策落实排序===========");
				table_list = eduDao.queryEducationSort(params);
				total_count = eduDao.queryEducationSortCount(params);
				export_task_id = "EduOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=EduOrder";
				String[] taxis_indexes = {"政策落实率","重合率"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//医疗政策落实排序
			}else if("medical_policy".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-医疗政策落实排序===========");
				table_list = medicalDao.queryMedicalSort(params);
				total_count = medicalDao.queryMedicalSortCount(params);
				export_task_id = "MedicalOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalOrder";
				String[] taxis_indexes = {"政策落实率","重合率"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//住房政策落实排序
			}else if("house".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-住房政策落实排序===========");
				table_list = houseDao.queryHousingSort(params);
				total_count = houseDao.queryHousingSortCount(params);
				export_task_id = "HouseOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=HouseOrder";
				String[] taxis_indexes = {"住房改造完成率(建档立卡)","住房改造户数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
/*测试使用*/
				//道路硬底化排序
			}else if("road".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-道路硬底化排序===========");
				order_table_title = "RoadOrder";
				table_list = fairlyDao.queryRoadImplementAnalysisSort(params);
				total_count = fairlyDao.queryRoadImplementAnalysisSortCount(params);
				export_task_id = "RoadOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=RoadOrder";
				String[] taxis_indexes = {"完全道路硬底化村比例","帮扶村未通沥青(水泥)路里程数","当年改造完成里程数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
		
			//安全饮水排序
			}else if("water".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-安全饮水排序===========");
				order_table_title = "WaterOrder";
				table_list = fairlyDao.queryWaterSortImplementAnalysisSort(params);
				total_count = fairlyDao.queryWaterSortImplementAnalysisSortCount(params);
				export_task_id = "WaterOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=WaterOrder";
				String[] taxis_indexes = {"完全实现安全饮水村比例","未完全实现安全饮水的村数","完成饮水改造户数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//生活用电排序
			}else if("electricity".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-生活用电排序===========");
				order_table_title = "ElectricityOrder";
				table_list = fairlyDao.queryElectricitySortImplementAnalysisSort(params);
				total_count = fairlyDao.queryElectricitySortImplementAnalysisSortCount(params);
				export_task_id = "ElectricityOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ElectricityOrder";
				String[] taxis_indexes = {"全通生活用电的村比例","未全通生活用电的村数","完成生活用电改造的村数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//医疗设施排序
			}else if("medical_facility".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-医疗设施排序===========");
				order_table_title = "MedicalFacilityOrder";
				table_list = fairlyDao.queryMedicalSortImplementAnalysisSort(params);
				total_count = fairlyDao.queryMedicalSortImplementAnalysisSortCount(params);
				export_task_id = "MedicalFacilityOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalFacilityOrder";
				String[] taxis_indexes = {"有卫生站的村比例","无卫生站的村数","新建卫生站的村数","有执业医师的村数","无执业医师的村数","新增执业医师的村数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//通广播电视排序
			}else if("broadcast".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-通广播电视排序===========");
				order_table_title = "BroadcastOrder";
				table_list = fairlyDao.queryBroadcastSortImplementAnalysisSort(params);
				total_count = fairlyDao.queryBroadcastSortImplementAnalysisSortCount(params);
				export_task_id = "BroadcastOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=BroadcastOrder";
				String[] taxis_indexes = {"完全通广播电视村比例","未全通广播电视的村数","通广播电视改造村数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//网络覆盖排序
			}else if("net".equals(wintype) && "implement_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-网络覆盖排序===========");
				order_table_title = "NetdOrder";
				table_list = fairlyDao.queryNetSortImplementAnalysisSort(params);
				total_count = fairlyDao.queryNetSortImplementAnalysisSortCount(params);
				export_task_id = "NetdOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=NetdOrder";
				String[] taxis_indexes = {"网络全覆盖村比例","网络全覆盖的村数","网络未全覆盖的村数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
				//贫困识别排序
				}else if("alarmed_poor".equals(wintype) && "exception_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-贫困识别排序===========");
				order_table_title = "";
				table_list = alarmedPoorExceptionDao.queryPoorIndetificationSort(params);
				total_count = alarmedPoorExceptionDao.queryPoorIndetificationSortCount(params);
				export_task_id = "PoorExceptionOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorExceptionOrder";
				String[] taxis_indexes = {"异常项数","异常数比例"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//建档立卡异常排序
			}else if("alarmed_records".equals(wintype) && "exception_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-建档立卡异常排序===========");
				order_table_title = "";
				table_list = alarmedPoorExceptionDao.queryPoorRecordsSort(params);
				total_count = alarmedPoorExceptionDao.queryPoorRecordsSortCount(params);
				export_task_id = "PoorRecordsOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorRecordsOrder";
				String[] taxis_indexes = {"异常项数","异常数比例"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
				
			//帮扶资金排序
			}else if("alarmed_money".equals(wintype) && "money_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-帮扶资金排序===========");
				order_table_title = "";
				params.put("taxis_indexes", 1);
				table_list = alarmedPoorExceptionDao.queryAlarmedMoneySort(params);
				total_count = alarmedPoorExceptionDao.queryAlarmedMoneySortCount(params);
				export_task_id = "AlarmedMoneydOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedMoneydOrder";
				String[] taxis_indexes = {"帮扶资金小于＜30万的相对贫困村比例","帮扶资金小于＜30万的相对贫困村数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"exception_type").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//贫困户走访排序
			}else if("alarmed_visit".equals(wintype) && "visit_order".equals(tabtype)){
				log.info("查询数据监控窗口排序页面表格数据信息-贫困户走访排序===========");
				order_table_title = "AlarmedVisitdOrder";
				params.put("taxis_indexes", 1);
				table_list = warningDao.queryAlarmedImplementAnalysisSort(params);
				total_count = warningDao.queryAlarmedImplementAnalysisSortCount(params);
				export_task_id = "AlarmedVisitdOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedVisitdOrder";
				String[] taxis_indexes = {"未被走访的累计贫困户比例","未被走访的累计贫困户数"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"exception_type").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			//项目监控排序
			}else if("project".equals(wintype) && "project_order".equals(tabtype)){
				log.info("查询项目监控窗口排序页面表格数据信息-项目排序===========");
				//System.out.println("查询项目监控窗口排序页面表格数据信息-项目排序===========");
				order_table_title = "ProjectOrder";
				table_list = projectManagementDao.projectAnalysisSort(params);
				total_count = projectManagementDao.projectAnalysisSortCount(params);
				export_task_id = "ProjectOrder";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectOrder";
				String[] taxis_indexes = {"累计项目总数","累计资金总数","累计村项目数","累计村项目资金","累计户项目数 ","累计户项目资金","年度项目总数","年度项目资金","年度到村项目数","年度到村项目资金","年度户项目数 ","年度户项目资金"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			}else if("capital".equals(wintype) && "capital_order".equals(tabtype)){
				log.info("查询项目监控窗口排序页面表格数据信息-项目排序===========");
				if(taxisIndexes.equals("2") || taxisIndexes.equals("3") || taxisIndexes.equals("4")){
					order_table_title = "FundAnalysisSortTtH";
					table_list = fmtDao.queryFundAnalysisSortTtH(params);
					total_count = fmtDao.queryFundAnalysisSortTtHCount(params);
					export_task_id = "FundAnalysisSortTtH";
					export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FundAnalysisSortTtH";
				}else{
					order_table_title = "FundAnalysisSort";
					table_list = fmtDao.queryFundAnalysisSort(params);
					total_count = fmtDao.queryFundAnalysisSortCount(params);	
					export_task_id = "FundAnalysisSort";
					export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FundAnalysisSort";
				}
				String[] taxis_indexes = {"年度项目资金总额(万元)","年度财政资金(万元)","财政资金余额(万元)","财政资金下拨周期(天)","年度行业资金(万元)","年度社会资金(万元)","年度帮扶单位自筹资金(万元)"};
				order_table_title = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				sort_level = sort_area_en[Integer.parseInt(getValue(params,"sort_area").toString())-1];
			}
			//用于导出的参数
			params.put("order_table_title", order_table_title);
			params.put("total_count", total_count);
			paramsPool.put(export_task_id, params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject table = new JSONObject();
			table.put("title", order_table_title);
			table.put("export_url", export_url);
			List<List<JSONObject>> head =  new ArrayList<List<JSONObject>>();
			List<JSONObject> head_arr = new ArrayList<JSONObject>();
			List<List<JSONObject>> body =  new ArrayList<List<JSONObject>>();
			if(("capital".equals(wintype) && "capital_order".equals(tabtype) ) && ( taxisIndexes.equals("2") || taxisIndexes.equals("3") || taxisIndexes.equals("4"))) {
				
				String[] head_content_arr = {"排名","行政区域","文件号","指标","操作"};
				for(int i=0;i<5;i++){
					JSONObject content = new JSONObject();
					content.put("content", head_content_arr[i]);
					head_arr.add(content);
				}
				head.add(head_arr);
				table.put("head", head);
				for(int i=0;i<table_list.size();i++){
					List<JSONObject> body_arr = new ArrayList<JSONObject>();
					for(int j=0;j<5;j++){
						JSONObject content = new JSONObject();
						if(j==0){
							content.put("content", ((page-1)*10+(i+1)));
						}else if(j==4){
							content.put("content", "查看");
							content.put("action_type", "file_detail");
							JSONObject action_data = new JSONObject();
							action_data.put("file_id", table_list.get(i).get("fileId"));
							content.put("action_data", action_data);
						}else{
							content.put("content", table_list.get(i).get("A"+(j+1)));
						}
						body_arr.add(content);
					}
					body.add(body_arr);
				}
				
			}else{
				String[] head_content_arr = {"排名","行政区域","指标","操作"};
				for(int i=0;i<4;i++){
					JSONObject content = new JSONObject();
					content.put("content", head_content_arr[i]);
					head_arr.add(content);
				}
				head.add(head_arr);
				table.put("head", head);
				for(int i=0;i<table_list.size();i++){
					List<JSONObject> body_arr = new ArrayList<JSONObject>();
					for(int j=0;j<4;j++){
						JSONObject content = new JSONObject();
						if(j==0){
							content.put("content", ((page-1)*10+(i+1)));
						}else if(j==3){
							content.put("content", "查看");
							content.put("action_type", "area_data");
							JSONObject action_data = new JSONObject();
							action_data.put("area_level", sort_level);
							action_data.put("area_id", table_list.get(i).get("S_ID"));
							content.put("action_data", action_data);
						}else{
							content.put("content", table_list.get(i).get("A"+(j+1)));
						}
						body_arr.add(content);
					}
					body.add(body_arr);
				}
			}
			table.put("body", body);
			
			JSONObject pagination = new JSONObject();
			pagination.put("page_total", total_count%10>0?total_count/10+1:total_count/10);
			pagination.put("page_cur", page);
			pagination.put("amount_total", total_count);
			table.put("pagination", pagination);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("数据监控窗口排序页面表格数据信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 数据监控窗口详细清单弹窗基础信息
	 * @param id 区域ID
	 * @param level 层级
	 * @param year 年
	 * @param wintype 数据监控窗口所属类型
	 * @param tabtype 数据监控窗口标签页类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorDetailBasicInfo(String id,String level,String year,String month,String wintype,String tabtype,Map<String,Object> Map) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> provinceMap = (Map<String,Object>)Map.get("provinceMap");
			Map<String,Object> cityMap = (Map<String,Object>)Map.get("cityMap");
			List<Map<String,Object>> cityList = (List<Map<String,Object>>)Map.get("cityList");
			Map<String,Object> countyMap = (Map<String,Object>)Map.get("countyMap");
			List<Map<String,Object>> countyList = (List<Map<String,Object>>)Map.get("countyList");
			Map<String,Object> townMap = (Map<String,Object>)Map.get("townMap");
			List<Map<String,Object>> townList = (List<Map<String,Object>>)Map.get("townList");
			Map<String,Object> countryMap = (Map<String,Object>)Map.get("countryMap");
			List<Map<String,Object>> countryList = (List<Map<String,Object>>)Map.get("countryList");

			Long userPac = Long.parseLong(UserUtils.getAreaList().iterator().next().toString());
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("PAC", userPac);
			Map<String,Object> userMap = dao.queryAreaById(params);
			String userLevel = userMap.get("S_LEVEL") == null ? "" : userMap.get("S_LEVEL").toString();
//			if(Long.parseLong(id) > Long.parseLong(userPac)){
//				params.put("PAC", id);
//			}else{
//				params.put("PAC", userPac);
//			}
//			List<Map<String,Object>> areaListData = treeDao.queryAreaBySubId(params);
//			
//			params.put("PAC", id);
//			Map<String,Object> paramAreaMap = treeDao.queryAreaById(params);
			
			String win_title = "";
			String[] year_arr = {"2016","2017","2018"};
			String[] year_value_arr = {"2016","2017","2018"};
			//System.out.println(wintype+"============"+tabtype);
			//扶贫对象变动分析详细
			if("poor".equals(wintype) && "change_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-扶贫对象变动分析详细===========");
				win_title = "扶贫对象监控>变动分析>扶贫对象明细";
			//脱贫成效分析详细
			}else if("result".equals(wintype) && "result_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-脱贫成效分析详细===========");
				year_arr = new String[]{"2016","2017","2018"};
				year_value_arr = new String[]{"2016","2017","2018"};
				win_title = "脱贫成效监控>预脱贫分析>预脱贫明细";
			//人均可支配收入-脱贫户分析详细
			}else if("average_income".equals(wintype) && "success_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-人均可支配收入-脱贫户分析详细===========");
				year_arr = new String[]{"全部","2016","2017","2018"};
				year_value_arr = new String[]{"","2016","2017","2018"};
				win_title = "人均可支配收入监控>预脱贫户分析>预脱贫户收入明细";
			//人均可支配收入-贫困户分析详细
			}else if("average_income".equals(wintype) && "poor_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-人均可支配收入-贫困户分析详细===========");
				year_arr = new String[]{"全部","2016","2017","2018"};
				year_value_arr = new String[]{"","2016","2017","2018"};
				win_title = "人均可支配收入监控>未脱贫户分析>未脱贫户收入明细";
			//低五保政策落实分析详细
			}else if("five_low".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-低五保政策落实分析详细===========");
				win_title = "低保五保政策落实监控>落实分析>低保五保明细";
			//教育政策落实分析详细
			}else if("edu".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-教育政策落实分析详细===========");
				win_title = "教育政策落实监控>落实分析>教育政策落实明细";
			//医疗政策落实分析详细
			}else if("medical_policy".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-医疗政策落实分析详细===========");
				win_title = "医疗政策落实监控>落实分析>医疗政策落实明细";
			//住房政策落实分析详细
			}else if("house".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-住房政策落实分析详细===========");
				win_title = "住房政策落实监控>落实分析>住房政策落实分析";
			//道路硬底化落实分析详细
			}else if("road".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-道路硬底化监控落实分析详细===========");
				win_title = "道路硬底化落实监控>落实分析>明细";
			//安全饮水落实分析详细
			}else if("water".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-安全饮水监控落实分析详细===========");
				win_title = "安全饮水落实监控>落实分析>明细";
			//生活用电落实分析详细
			}else if("electricity".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-生活用电监控落实分析详细===========");
				win_title = "生活用电落实监控>落实分析>明细";
			//医疗设施落实分析详细
			}else if("medical_facility".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-医疗设施监控落实分析详细===========");
				win_title = "医疗设施落实监控>落实分析>明细";
			//通广播电视落实分析详细
			}else if("broadcast".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-通广播电视监控落实分析详细===========");
				win_title = "通广播电视落实监控>落实分析>明细";
			//网络覆盖落实分析详细
			}else if("net".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗基础信息-网络覆盖监控落实分析详细===========");
				win_title = "网络覆盖落实监控>落实分析>明细";
			}else if("poor_analysis".equals(wintype) && "poor_analysis".equals(tabtype)){
				log.info("查询数据贫困分析>贫困分析明细===========");
				win_title = "贫困分析>贫困分析明细";
			}else if("alarmed_poor".equals(wintype) && "exception_monitor".equals(tabtype)){
				log.info("查询数据贫困识别监控>异常监控>异常明细===========");
				win_title = "贫困识别监控>异常监控>异常明细";
			}else if("alarmed_records".equals(wintype) && "exception_monitor".equals(tabtype)){
				log.info("查询数据预警监控>建档立卡异常记录监控>异常明细===========");
				win_title = "预警监控>建档立卡异常记录监控>异常明细";
			}else if("alarmed_money".equals(wintype) && "money_monitor".equals(tabtype)){
				log.info("查询数据预警监控>帮扶资金监控>异常明细===========");
				win_title = "预警监控>帮扶资金监控>异常明细";

			}else if("alarmed_visit".equals(wintype) && "visit_monitor".equals(tabtype)){
				log.info("查询数预警监控>贫困户走访监控>异常明细===========");
				win_title = "预警监控>贫困户走访监控>异常明细";

			}else if("project".equals(wintype) && "project_analysis".equals(tabtype)){
				log.info("查询数据项目监控>项目分析>项目类型明细===========");
				year_arr = new String[]{"全部","2016","2017","2018"};
				year_value_arr = new String[]{"","2016","2017","2018"};
				win_title = "项目监控>项目分析>项目类型明细";
			}else if("duty".equals(wintype) && "duty_analysis".equals(tabtype)){
				log.info("查询数据责任监控>帮扶责任分析>帮扶责任明细===========");
				win_title = "责任监控>帮扶责任分析>帮扶责任明细";

			}else if("project_country".equals(wintype) && "project_country".equals(tabtype)){
				

			}else if("project_country".equals(wintype) && "road".equals(tabtype)){
				log.info("查询项目监控>到村项目>村道硬底化明细===========");
				year_arr = new String[]{"全部","2016","2017","2018"};
				year_value_arr = new String[]{"","2016","2017","2018"};
				win_title = "项目监控>村扶贫项目分析>村道硬底化明细";
				}else if("project_country".equals(wintype) && "water".equals(tabtype)){
					log.info("查询项目监控>村扶贫项目分析>饮水工程明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>到村项目>饮水工程明细";
				}else if("project_country".equals(wintype) && "recreation_sport".equals(tabtype)){
					log.info("查询项目监控>村扶贫项目分析>文体设施明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>村扶贫项目分析>文体设施明细";
				}else if("project_country".equals(wintype) && "hygiene".equals(tabtype)){
					log.info("查询项目监控>村扶贫项目分析>卫生设施明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>村扶贫项目分析>卫生设施明细";
				}else if("project_country".equals(wintype) && "lamp".equals(tabtype)){
					log.info("查询项目监控>村扶贫项目分析>路灯安装明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>村扶贫项目分析>路灯安装明细";
				}else if("project_country".equals(wintype) && "farm".equals(tabtype)){
					log.info("查询项目监控>村扶贫项目分析>农田水利明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>村扶贫项目分析>农田水利明细";
				}else if("project_country".equals(wintype) && "public_facility".equals(tabtype)){
					log.info("查询项目监控>村扶贫项目分析>公共设施明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>村扶贫项目分析>公共设施明细";
				}else if("project_country".equals(wintype) && "collective_economy".equals(tabtype)){
					log.info("查询项目监控>村扶贫项目分析>集体经济明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>村扶贫项目分析>集体经济明细";
				}else if("project_country".equals(wintype) && "edu".equals(tabtype)){
					log.info("查询项目监控>村扶贫项目分析>教育教学明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>村扶贫项目分析>教育教学明细";
					
				}else if("project_family".equals(wintype) && "industry".equals(tabtype)){
					log.info("查询项目监控>户扶贫项目分析>产业扶贫明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>户扶贫项目分析>产业扶贫明细";
					
				}else if("project_family".equals(wintype) && "finance".equals(tabtype)){
					log.info("查询项目监控>户扶贫项目分析>金融扶贫明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>户扶贫项目分析>金融扶贫明细";
					
				}else if("project_family".equals(wintype) && "house".equals(tabtype)){
					log.info("查询项目监控>户扶贫项目分析>住房改造明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>户扶贫项目分析>住房改造明细";
					
				}else if("project_family".equals(wintype) && "property".equals(tabtype)){
					log.info("查询项目监控>户扶贫项目分析>资产扶贫明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>户扶贫项目分析>资产扶贫明细";
					
				}else if("project_family".equals(wintype) && "visit".equals(tabtype)){
					log.info("查询项目监控>户扶贫项目分析>慰问扶贫明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>户扶贫项目分析>慰问扶贫明细";
					
				}else if("project_family".equals(wintype) && "employment".equals(tabtype)){
					log.info("查询项目监控>户扶贫项目分析>就业扶贫明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>户扶贫项目分析>就业扶贫明细";
					
				}else if("project_family".equals(wintype) && "skill".equals(tabtype)){
					log.info("查询项目监控>户扶贫项目分析>技能培训明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>户扶贫项目分析>技能培训明细";
					
				}else if("project_family".equals(wintype) && "edu".equals(tabtype)){
					log.info("查询项目监控>户扶贫项目分析>教育扶贫明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "项目监控>户扶贫项目分析>教育扶贫明细";
			
				}else if("capital".equals(wintype) && "capital_analysis".equals(tabtype)){
					log.info("查询资金监控>资金分析>资金明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "资金监控>资金分析>资金明细";
				}else if("example_country_building".equals(wintype) && "renovate_standard".equals(tabtype)){
					log.info("示范村建设>整治达标>明细===========");
					year_arr = new String[]{"全部","2016","2017","2018"};
					year_value_arr = new String[]{"","2016","2017","2018"};
					win_title = "示范村建设>整治达标>明细";
				}
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			data.put("win_title", win_title);
			
			JSONObject years = new JSONObject();
			List<JSONObject> available_years_arr = new ArrayList<JSONObject>();
			years.put("selected_value", year);
			for(int i=0;i<year_arr.length;i++){
				JSONObject available_years = new JSONObject();
				available_years.put("name", year_arr[i]);
				available_years.put("value", year_value_arr[i]);
				available_years_arr.add(available_years);
			}
			years.put("available_years", available_years_arr);
			data.put("years", years);
			
			 if("example_country_building".equals(wintype) && "renovate_standard".equals(tabtype)){
				    String[] month_arr = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
					String[] month_value_arr = {"1","2","3","4","5","6","7","8","9","10","11","12"};
					JSONObject months = new JSONObject();
					List<JSONObject> available_month_arr = new ArrayList<JSONObject>();
					months.put("selected_value", month);
					for(int i=0;i<month_arr.length;i++){
						JSONObject available_month = new JSONObject();
						available_month.put("name", month_arr[i]);
						available_month.put("value", month_value_arr[i]);
						available_month_arr.add(available_month);
					}
					months.put("available_month", available_month_arr);
					data.put("month", months);
				}
			
			
//			Map<String,Object> areaMap = new HashMap<String,Object>();
//			int level_c = areaListData.size();
//			for(Map<String,Object> map: areaListData){
//				Integer lvl = Integer.parseInt(map.get("S_LEVEL").toString());
//				String pid = map.get("S_ID").toString();
//				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//				list.add(map);
//				if(lvl == 1){
//					areaMap.put("province", list);//省
//					params.put("PPAC", pid);
//					if(lvl == level_c) areaMap.put("city", treeDao.queryAreaByPPAC(params));
//				}else if(lvl == 2){
//					areaMap.put("city", list);
//					params.put("PPAC", pid);
//					if(lvl == level_c) areaMap.put("county", treeDao.queryAreaByPPAC(params));
//				}else if(lvl == 3){
//					areaMap.put("county", list);
//					params.put("PPAC", pid);
//					if(lvl == level_c) areaMap.put("town", treeDao.queryAreaByPPAC(params));
//				}else if(lvl == 4){
//					areaMap.put("town", list);
//					params.put("PPAC", pid);
//					if(lvl == level_c) areaMap.put("country", treeDao.queryAreaByPPAC(params));
//				}else if(lvl == 5){
//					areaMap.put("country", list);
//				}
//			}
			
//			List<Map<String,Object>> provList = (List<Map<String, Object>>) areaMap.get("province");
			List<JSONObject> areaList = new ArrayList<JSONObject>();
			JSONObject province = new JSONObject();
			province.put("level", "province");
			if("province".equals(userLevel)){
				//province.put("authority",false);
			}
			
			province.put("selected_id", (String)provinceMap.get("ID"));
			province.put("selected_name", (String)provinceMap.get("NAME"));
			List<JSONObject> province_list = new ArrayList<JSONObject>();
			JSONObject province_obj = new JSONObject();
			province_obj.put("id", (String)provinceMap.get("ID"));
			province_obj.put("name", (String)provinceMap.get("NAME"));
			province_list.add(province_obj);
			province.put("list", province_list);
			areaList.add(province);
			
			JSONObject city = new JSONObject();
			city.put("level", "city");
			if("city".equals(userLevel)){
				province.put("authority",false);
				
			}
			if(cityMap == null){
				city.put("selected_id", JSONNull.getInstance());
				city.put("selected_name", "全部");
			}else{
				city.put("selected_id", (String)cityMap.get("ID"));
				city.put("selected_name", (String)cityMap.get("NAME"));
			}
			List<JSONObject> city_list = new ArrayList<JSONObject>();
			for(int i=0;i<cityList.size();i++){
				JSONObject city_obj = new JSONObject();
				city_obj.put("id", cityList.get(i).get("ID"));
				city_obj.put("name", cityList.get(i).get("NAME"));
				city_list.add(city_obj);
			}
			city.put("list", city_list);
			areaList.add(city);
			
			JSONObject county = new JSONObject();
			if(cityMap != null && countyList.size()>0){
				county.put("level", "county");
				if("county".equals(userLevel)){
					province.put("authority",false);
					city.put("authority",false);
				
				}
				if(countyMap == null){
					county.put("selected_id", JSONNull.getInstance());
					county.put("selected_name", "全部");
				}else{
					county.put("selected_id", (String)countyMap.get("ID"));
					county.put("selected_name", (String)countyMap.get("NAME"));
				}
				List<JSONObject> county_list = new ArrayList<JSONObject>();
				for(int i=0;i<countyList.size();i++){
					JSONObject county_obj = new JSONObject();
					county_obj.put("id", countyList.get(i).get("ID"));
					county_obj.put("name", countyList.get(i).get("NAME"));
					county_list.add(county_obj);
				}
				county.put("list", county_list);
				areaList.add(county);
			}
			
			JSONObject town = new JSONObject();
			if(countyMap != null && townList.size()>0){
				town.put("level", "town");
				if("town".equals(userLevel)){
					province.put("authority",false);
					city.put("authority",false);
					county.put("authority",false);
				}
				if(townMap == null){
					town.put("selected_id", JSONNull.getInstance());
					town.put("selected_name", "全部");
				}else{
					town.put("selected_id", (String)townMap.get("ID"));
					town.put("selected_name", (String)townMap.get("NAME"));
				}
				List<JSONObject> town_list = new ArrayList<JSONObject>();
				for(int i=0;i<townList.size();i++){
					JSONObject town_obj = new JSONObject();
					town_obj.put("id", townList.get(i).get("ID"));
					town_obj.put("name", townList.get(i).get("NAME"));
					town_list.add(town_obj);
				}
				town.put("list", town_list);
				areaList.add(town);
			}
			
			if(townMap != null && countryList.size()>0){
				JSONObject country = new JSONObject();
				country.put("level", "country");
				if("country".equals(userLevel)){
					province.put("authority",false);
					city.put("authority",false);
					county.put("authority",false);
					town.put("authority",false);
				}
				if(countryMap == null){
					country.put("selected_id", JSONNull.getInstance());
					country.put("selected_name", "全部");
				}else{
					country.put("selected_id", (String)countryMap.get("ID"));
					country.put("selected_name", (String)countryMap.get("NAME"));
				}
				List<JSONObject> country_list = new ArrayList<JSONObject>();
				for(int i=0;i<countryList.size();i++){
					JSONObject country_obj = new JSONObject();
					country_obj.put("id", countryList.get(i).get("ID"));
					country_obj.put("name", countryList.get(i).get("NAME"));
					country_list.add(country_obj);
				}
				country.put("list", country_list);
				areaList.add(country);
			}
			data.put("area_path", areaList);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("查询数据监控窗口详细清单弹窗基础信息失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 数据监控窗口详细清单弹窗参数信息
	 * @param id 区域ID
	 * @param level 层级
	 * @param year 年
	 * @param wintype 数据监控窗口所属类型
	 * @param tabtype 数据监控窗口标签页类型
	 * @return
	 */
	public String getDataMonitorDetailParamsInfo(String id,String level,String year,String wintype,String tabtype) {
		JSONObject json = new JSONObject();
		try{
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			List<JSONObject> params_arr = new ArrayList<JSONObject>();
			Map<String,Object> map = new HashMap<String,Object>();
			int order_size = 0;
			
			//扶贫对象变动分析详细
			if("poor".equals(wintype) && "change_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-扶贫对象变动分析详细===========");
				order_size = 4;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"labor_attribute","劳力属性","radio",Arrays.asList("all"),
						new String[]{"全部","有劳动能力","无劳动能力"},
						new String[]{"all","have_labor","no_labor"},
						new Boolean[]{false,false,false}});
				map.put("objArr3",new Object[]{"poor_status","状态属性","radio",Arrays.asList("1"),
						new String[]{"全部","累计贫困户 ","当前贫困户","新增贫困户","拆户贫困户","终止贫困户","并户贫困户","销户贫困户","自然增减户"},
						new String[]{"1","2","3","4","5","6","7","8","9"},
						new Boolean[]{false,false,false,false,false,false,false,false,false}});
			//脱贫成效分析详细
			}else if("result".equals(wintype) && "result_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-脱贫成效分析详细===========");
				order_size = 5;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"plan_out_date","计划脱贫","radio",Arrays.asList("all"),
						new String[]{"全部","2016年","2017年","2018年","未填写"},
						new String[]{"all","2016","2017","2018","2999"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"out_date","实际脱贫","radio",Arrays.asList("all"),
						new String[]{"全部","2016年","2017年","2018年"},
						new String[]{"all","2016","2017","2018"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr3",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr4",new Object[]{"labor_attribute","劳力属性","radio",Arrays.asList("all"),
						new String[]{"全部","有劳动能力","无劳动能力"},
						new String[]{"all","have_labor","no_labor"},
						new Boolean[]{false,false,false}});
				
			//人均可支配收入-脱贫户分析详细
			}else if("average_income".equals(wintype) && "success_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-人均可支配收入-脱贫户分析详细===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"labor_attribute","劳力属性","radio",Arrays.asList("have_labor"),
						new String[]{"全部","有劳动能力","无劳动能力"},
						new String[]{"all","have_labor","no_labor"},
						new Boolean[]{false,false,false}});
			//人均可支配收入-贫困户分析详细
			}else if("average_income".equals(wintype) && "poor_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-人均可支配收入-贫困户分析详细===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"labor_attribute","劳力属性","radio",Arrays.asList("have_labor"),
						new String[]{"全部","有劳动能力","无劳动能力"},
						new String[]{"all","have_labor","no_labor"},
						new Boolean[]{false,false,false}});
			//低五保政策落实分析详细
			}else if("five_low".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-低五保政策落实分析详细===========");
				order_size = 4;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","低保户","五保户","无劳动能力的一般贫困户"},
						new String[]{"all","low","five","no_labor"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"policy_implement","政策落实","radio",Arrays.asList("1"),
						new String[]{"全部","全部落实户","未全部落实户"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
				map.put("objArr3",new Object[]{"industry","行业比对","radio",Arrays.asList("1"),
						new String[]{"全部","完全重合户","部分重合户","没有重合户"},
						new String[]{"1","2","3","4"},
						new Boolean[]{false,false,false,false}});
			//教育政策落实分析详细
			}else if("edu".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-教育政策落实分析详细===========");
				order_size = 5;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"edu_stage2","教育阶段","radio",Arrays.asList("2"),
						new String[]{"全部","义务教育","高中（中职）教育"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
				map.put("objArr3",new Object[]{"policy_implement","政策落实","radio",Arrays.asList("1"),
						new String[]{"全部","已落实","未落实"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
				map.put("objArr4",new Object[]{"industry","行业比对","radio",Arrays.asList("1"),
						new String[]{"全部","重合","不重合"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
			//医疗政策落实分析详细
			}else if("medical_policy".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-医疗政策落实分析详细===========");
				order_size = 4;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"policy_implement","政策落实","radio",Arrays.asList("1"),
						new String[]{"全部","已落实","未落实"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
				map.put("objArr3",new Object[]{"industry","行业比对","radio",Arrays.asList("1"),
						new String[]{"全部","重合","不重合"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
			//住房政策落实分析详细
			}else if("house".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-住房政策落实分析详细===========");
				order_size = 4;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"house_level","危房级别","radio",Arrays.asList("1"),
						new String[]{"全部","D级","C级","B级","A级"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr2",new Object[]{"schedule","改造进度","radio",Arrays.asList("1"),
						new String[]{"全部","已完成","已动工","未动工"},
						new String[]{"1","2","3","4"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr3",new Object[]{"industry","行业比对","radio",Arrays.asList("1"),
						new String[]{"全部","已列入行业计划","未列入行业计划"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
/*测试使用*/
				//道路硬底化监控分析详细
			}else if("road".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-道路硬底化监控落实分析详细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","硬底化程度","radio",Arrays.asList("1"),
						new String[]{"全部","完全硬底化村","未完全硬底化村"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
				//安全饮水监控分析详细
			}else if("water".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-安全饮水监控落实分析详细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","是否全实现","radio",Arrays.asList("1"),
						new String[]{"全部","全实现安全饮水的村","未全实现安全饮水的村"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
				//生活用电监控分析详细
			}else if("electricity".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-生活用电监控落实分析详细===========");
				order_size =2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","是否全通","radio",Arrays.asList("1"),
						new String[]{"全部","全通生活用电的村","未实现全通生活用电的村"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
				//医疗设施监控分析详细
			}else if("medical_facility".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-医疗设施监控落实分析详细===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","卫生站情况","radio",Arrays.asList("1"),
						new String[]{"全部","有卫生站的村","无卫生站的村","新建卫生站的村"},
						new String[]{"1","2","3","4"},
						new Boolean[]{false,false,false,false}});              
				map.put("objArr2",new Object[]{"labor_attribute","医师情况","radio",Arrays.asList("1"),
						new String[]{"全部","有职业医师的村","无执业医师的村","新增执业医师的村"},
						new String[]{"1","2","3","4"},
						new Boolean[]{false,false,false,false}});
				//通广播电视监控分析详细
			}else if("broadcast".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-通广播电视监控落实分析详细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","是否全通","radio",Arrays.asList("1"),
						new String[]{"全部","完全通广播电视的村","未全通广播电视的村"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
				//网络覆盖监控分析详细
			}else if("net".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-网络覆盖监控落实分析详细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","是否全覆盖","radio",Arrays.asList("1"),
						new String[]{"全部","全覆盖的村","未全覆盖的村"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
			}else if("poor_analysis".equals(wintype) && "poor_analysis".equals(tabtype)){
				log.info("查询贫困分析>贫困分析明细分析详细===========");
				//System.out.println("查询贫困分析>贫困分析明细分析详细33===========");
				order_size = 4;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"labor_attribute","劳力属性","radio",Arrays.asList("all"),
						new String[]{"全部","有劳动能力","无劳动能力"},
						new String[]{"all","have_labor","no_labor"},
						new Boolean[]{false,false,false}});
				map.put("objArr3",new Object[]{"poor_reason","致贫原因","check",Arrays.asList("Y01","Y02","Y03","Y04","Y05","Y06","Y07","Y08","Y09","Y10","Y11","Y12,Y99"),
						new String[]{"因病","因残","因学","因灾","缺土地","缺水","缺技术","缺劳力","缺资金","交通落后","自身发展","其他"},
						new String[]{"Y01","Y02","Y03","Y04","Y05","Y06","Y07","Y08","Y09","Y10","Y11","Y12,Y99"},
						new Boolean[]{false,false,false,false,false,false,false,false,false,false,false,false},false});

			}else if("alarmed_poor".equals(wintype) && "exception_monitor".equals(tabtype)){
				log.info("贫困识别监控>异常监控>异常明细");
				//System.out.println("查询贫困分析>贫困分析明细分析详细33===========");
				order_size = 1;
				map.put("objArr0",new Object[]{"exception_type","异常类型","check",Arrays.asList("1","3,4,9,10,11,12","6","8","2","5","7"),
						new String[]{"身份证不匹配 ","低保五保不匹配 ","残疾信息不匹配","名下有房","名下有车","有工商注册信息","享有财政供养"},
						new String[]{"1","3,4,9,10,11,12","6","8","2","5","7"},
						new Boolean[]{false,false,false,false,false,false,false},false});

			}else if("alarmed_records".equals(wintype) && "exception_monitor".equals(tabtype)){
				log.info("预警监控>建档立卡异常记录监控>异常明细");
				order_size = 1;
				map.put("objArr0",new Object[]{"exception_type","异常类型","check",Arrays.asList("20","21","22","23"),
						new String[]{"户主身份信息错漏","成员身份信息错漏","残疾信息错漏","重复身份信息记录"},
						new String[]{"20","21","22","23"},
						new Boolean[]{false,false,false,false},false});

			}else if("alarmed_money".equals(wintype) && "money_monitor".equals(tabtype)){
				log.info("预警监控>帮扶资金监控>异常明细");
				order_size = 1;
				map.put("objArr0",new Object[]{"exception_type","异常类型","radio",Arrays.asList("1"),
						new String[]{"全部","年帮扶资金>=30万 ","年帮扶资金<30万"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false,false},false});

			}else if("project_country".equals(wintype) && "road".equals(tabtype)){
			log.info("查询数据监控窗口详细清单弹窗参数信息-村道硬底化项目明细===========");
			order_size = 2;
			map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
					new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
					new String[]{"1","2","3","4","5"},
					new Boolean[]{false,false,false,false,false}});
			map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
					new String[]{"已完成","进行中","未启动"},
					new String[]{"完成","进行中","未启动"},
					new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_country".equals(wintype) && "water".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-饮水工程项目明细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_country".equals(wintype) && "recreation_sport".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-文体设施项目明细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_country".equals(wintype) && "hygiene".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-卫生设施项目明细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_country".equals(wintype) && "lamp".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-路灯安装项目明细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_country".equals(wintype) && "farm".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-农田水利项目明细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_country".equals(wintype) && "public_facility".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-公共设施项目明细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_country".equals(wintype) && "collective_economy".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-集体经济项目明细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_country".equals(wintype) && "edu".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-教育教学项目明细===========");
				order_size = 2;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("alarmed_visit".equals(wintype) && "visit_monitor".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-走访===========");
				order_size = 1;
				map.put("objArr0",new Object[]{"poor_attribute","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","走访次数>=1次","走访次数=0次"},
						new String[]{"1","2","3"},
						new Boolean[]{false,false,false}});
			}else if("project".equals(wintype) && "project_analysis".equals(tabtype)){
				log.info("查询项目监控>项目分析>项目类型明细===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"country_project","到村项目","check",Arrays.asList("1","2","3","4","5","6","7","8","9","10"),
						new String[]{"村道硬底化","饮水工程","文体设施","卫生设施","路灯安装","农田水利","公告设施","集体经济","教育教学","其他"},
						new String[]{"1","2","3","4","5","6","7","8","9","10"},
						new Boolean[]{false,false,false,false,false,false,false,false,false,false,false},true});
				map.put("objArr2",new Object[]{"family_project","到户项目","check",Arrays.asList("11","12","13","14","15","16","17","18","19"),
						new String[]{"产业扶贫","金融扶贫","住房改造","资产扶贫","慰问扶贫","就业扶贫","技能培训","教育培训","政策补贴和社会保障"},
						new String[]{"11","12","13","14","15","16","17","18","19"},
						new Boolean[]{false,false,false,false,false,false,false,false,false,false,false},true});//最后一个true表示有全部选择按钮
			}else if("duty".equals(wintype) && "duty_analysis".equals(tabtype)){
				log.info("查询责任监控>帮扶责任分析>帮扶责任明细===========");
				order_size = 1;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
			}else if("project_family".equals(wintype) && "industry".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-项目监控户扶贫项目分析-产业扶贫===========");
				order_size = 4;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"labor_attribute","劳力属性","radio",Arrays.asList("all"),
						new String[]{"全部","有劳动能力","无劳动能力"},
						new String[]{"all","have_labor","no_labor"},
						new Boolean[]{false,false,false}});
				map.put("objArr3",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_family".equals(wintype) && "finance".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-项目监控户扶贫项目分析-金融扶贫===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_family".equals(wintype) && "house".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-项目监控户扶贫项目分析-住房改造===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_family".equals(wintype) && "property".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-项目监控户扶贫项目分析-资产扶贫===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_family".equals(wintype) && "visit".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-项目监控户扶贫项目分析-慰问扶贫===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_family".equals(wintype) && "employment".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-项目监控户扶贫项目分析-就业扶贫===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_family".equals(wintype) && "skill".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-项目监控户扶贫项目分析-技能培训===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("project_family".equals(wintype) && "edu".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-项目监控户扶贫项目分析-教育扶贫===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_attribute","贫困属性","radio",Arrays.asList("all"),
						new String[]{"全部","一般贫困户","低保户","五保户"},
						new String[]{"all","normal","low","five"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr2",new Object[]{"poor_reason","项目状态","check",Arrays.asList("完成","进行中","未启动"),
						new String[]{"已完成","进行中","未启动"},
						new String[]{"完成","进行中","未启动"},
						new Boolean[]{true,true,true},true});//最后一个true表示有全部选择按钮
			}else if("capital".equals(wintype) && "capital_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-资金分析明细===========");
				order_size = 3;
				map.put("objArr0",new Object[]{"scope","区域类型","radio",Arrays.asList("1"),
						new String[]{"全部","相对贫困村","分散村","革命老区","中央苏区"},
						new String[]{"1","2","3","4","5"},
						new Boolean[]{false,false,false,false,false}});
				map.put("objArr1",new Object[]{"poor_reason_r","到村项目","check",Arrays.asList("country_road","country_water","country_recreation_sport","country_hygiene","country_lamp","country_farm","country_public_facility","country_collective_economy","country_edu","country_other"),
						new String[]{"村道硬底化","饮水工程","文体设施","卫生设施","路灯安装","农田水利","公共设施","集体经济","教育教学","其他"},
						new String[]{"country_road","country_water","country_recreation_sport","country_hygiene","country_lamp","country_farm","country_public_facility","country_collective_economy","country_edu","country_other"},
						new Boolean[]{true,true,true,true,true,true,true,true,true,true},true});//最后一个true表示有全部选择按钮
				map.put("objArr2",new Object[]{"poor_reason_t","到户项目","check",Arrays.asList("family_industry","family_finance","family_house","family_property","family_visit","family_employment","family_skill","family_edu","family_policy"),
						new String[]{"产业扶贫","金融扶贫","住房改造","资产扶贫","慰问扶贫","就业扶贫","技能培训","教育扶贫","政策补贴和社会保障"},
						new String[]{"family_industry","family_finance","family_house","family_property","family_visit","family_employment","family_skill","family_edu","family_policy"},
						new Boolean[]{true,true,true,true,true,true,true,true,true},true});//最后一个true表示有全部选择按钮
			}//资金分析明细
			else if("example_country_building".equals(wintype) && "renovate_standard".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗参数信息-资金分析明细===========");
				order_size = 2;
				
				map.put("objArr0",new Object[]{"state","示范村","radio",Arrays.asList("all"),
						new String[]{"全部","达标","建设中","未启动"},
						new String[]{"all","done","doing","unstart"},
						new Boolean[]{false,false,false,false}});
				map.put("objArr1",new Object[]{"build","自然村","radio",Arrays.asList("all"),
						new String[]{"全部","达标","建设中","未启动"},
						new String[]{"all","done","doing","unstart"},
						new Boolean[]{false,false,false,false}});
			}
			for(int i=0;i<order_size;i++){
				Object[] arr = (Object[]) getValue(map,"objArr"+i);
				JSONObject params = new JSONObject();
				params.put("key", arr[0]);
				params.put("label", arr[1]);
				params.put("type", arr[2]);
				params.put("selected_value", arr[3]);
				List<JSONObject> values_arr = new ArrayList<JSONObject>();
				String[] label_arr = (String[]) arr[4];
				String[] value_arr = (String[]) arr[5];
				Boolean[] disabled_arr = (Boolean[]) arr[6];
				for(int j=0;j<label_arr.length;j++){
					JSONObject values = new JSONObject();
					values.put("label", label_arr[j]);
					values.put("value", value_arr[j]);
					values.put("disabled", disabled_arr[j]);
					values_arr.add(values);
				}
				if("check".equals(arr[2]) && arr.length > 7 &&  (Boolean)arr[7]==true){
					params.put("has_check_all", true); //有全选功能则加上全选功能
				}
				params.put("values", values_arr);
				params_arr.add(params);
			}
			data.put("params", params_arr);
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("查询数据监控窗口详细清单弹窗参数信息失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 数据监控窗口详细清单弹窗表格
	 * @param id 区域ID
	 * @param level 层级
	 * @param year 年
	 * @param wintype 数据监控窗口所属类型
	 * @param tabtype 数据监控窗口标签页类型
	 * @param page 第几页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorDetailTable(HttpServletRequest request,String id,String level,String year,String month,String wintype,String tabtype,Integer page) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			    //System.out.println("key:="+entry.getKey()+" value:="+entry.getValue()[0]);
			}	
			 List<String> poorReasonStr = new ArrayList<String>();
		    if("capital".equals(wintype) && "capital_analysis".equals(tabtype)){
		    	//选择
				String toCountry = params.get("poor_reason_r") == null ? "" : (String)params.get("poor_reason_r") ;
				String toFamily =  params.get("poor_reason_t") == null ? "" : (String)params.get("poor_reason_t") ;
			    List<String> projClass = new ArrayList<String>();
			    if(null != toCountry && !"".equals(toCountry) ){
			    	String[] toCountrys = toCountry.split(",");
			    	for(String et : toCountrys){
			    		if("country_other".equals(et)){//选择村项目其他
	    					projClass.add("捐赠慰问");
			    			projClass.add("基金");
			    		}else
			    		projClass.add(CommaProcessing.projClassMap.get(et));
			    	}
			    	
			    }
			    if(null != toFamily && !"".equals(toFamily) ){
			    	String[] toFamilys = toFamily.split(",");
			    	for(String st : toFamilys){
			    		if("family_policy".equals(st)){//选择户项目政策补贴和社会保障
	    					projClass.add("低保金");
			    			projClass.add("五保金");
			    			projClass.add("养老保险");
			    			projClass.add("医疗保险");
			    			projClass.add("医疗救助");
			    			projClass.add("政策性补贴");
			    			projClass.add("转移性收入");
			    			projClass.add("转移性支出");
			    		}else
			    		projClass.add(CommaProcessing.projClassMap.get(st));
			    	}
			    	
			    }
			    params.put("list",projClass);
		    	
		    }else{
		    	//贫困原因多选框
				String poorReason = params.get("poor_reason") == null ? "" : (String)params.get("poor_reason") ;
			    if(null != poorReason && !"".equals(poorReason)){
			    	String[] poorReasons = poorReason.split(",");
			    	for(String pr : poorReasons){
			    		poorReasonStr.add(pr);
			    	}
			    	params.put("list",poorReasonStr);
			    }
		    }
		   
		    
		  //异常类型
			String exceptionType = params.get("exception_type") == null ? "" : (String)params.get("exception_type");
		    List<Integer> exceptionTypes = new ArrayList<Integer>();
		    if(null != exceptionType && !"".equals(exceptionType)){
		    	String[] exceptionTypeStr = exceptionType.split(",");
		    	for(String et : exceptionTypeStr){
		    		exceptionTypes.add(Integer.parseInt(et));
		    	}
		    	params.put("exceptionTypes",exceptionTypes);
		    }
			//查询table数据
			params.put("pageSize", 10);
			List<Map<String, Object>> table_list = new ArrayList<Map<String, Object>>();
			long total_count = 0;
			
			int size = 0;
			String[] content_arr = null;
			String export_task_id = "";
			String export_url = "";
			String notes = "";
			//扶贫对象变动分析详细
			if("poor".equals(wintype) && "change_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-扶贫对象变动分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list=pcDao.queryObjectDetail(params);
				table_list=CommaProcessing.ProcessingComma(table_list, "A8");
				total_count = pcDao.queryObjectDetailCount(params);
				export_task_id = "PoorChangeAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorChangeAnalysisDetail";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","状态属性","自然增加成员","自然减少成员","操作"};
			//脱贫成效分析详细
			}else if("result".equals(wintype) && "result_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-脱贫成效分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = prDao.queryAnalysisOfEffectsDetails(params);
				total_count = prDao.queryAnalysisOfEffectsDetailsCount(params);
				export_task_id = "PoorResultResultAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorResultResultAnalysisDetail";
				size = 10;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","计划脱贫时间(年)","预脱贫时间（年）","操作"};
			//人均可支配收入-脱贫户分析详细
			}else if("average_income".equals(wintype) && "success_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-人均可支配收入-脱贫户分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				params.put("aid_year", true);
				table_list = diDao.queryDetailOfIncomeTable(params);
				total_count = diDao.queryDetailOfIncomeTableC(params);
				export_task_id = "AverageIncomeSuccessAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomeSuccessAnalysisDetail";
				size = 15;
				content_arr = new String[]{"序号","年份","市","县","镇","村","户码","户主姓名","家庭人数","预脱贫时间(年)","总收入(元)","总支出(元)","可支配收入(元)","人均可支配收入(元)","操作"};
			//人均可支配收入-贫困户分析详细
			}else if("average_income".equals(wintype) && "poor_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-人均可支配收入-贫困户分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				params.put("aid_year", false);
				table_list = diDao.queryDetailOfIncomeTable(params);
				total_count = diDao.queryDetailOfIncomeTableC(params);
				export_task_id = "AverageIncomePoorAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomePoorAnalysisDetail";
				size = 15;
				content_arr = new String[]{"序号","年份","市","县","镇","村","户码","户主姓名","家庭人数","预脱贫时间(年)","总收入(元)","总支出(元)","可支配收入(元)","人均可支配收入(元)","操作"};
			//低五保政策落实分析详细
			}else if("five_low".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-低五保政策落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				table_list = flDao.flveLowDetail(params);
				total_count = flDao.flveLowDetailCount(params);
				export_task_id = "FiveLowAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FiveLowAnalysisDetail";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","贫困属性","家庭人口数","落实政策人口数","行业重合人口数","操作"};
			//教育政策落实分析详细
			}else if("edu".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-教育政策落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_POP_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				table_list = eduDao.queryEducationDetail(params);
				total_count = eduDao.queryEducationDetailCount(params);
				export_task_id = "EduImplementAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=EduImplementAnalysisDetail";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","学生姓名","教育阶段","是否享受政策","是否行业重合","操作"};
			//医疗政策落实分析详细
			}else if("medical_policy".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-医疗政策落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_POP_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				table_list = medicalDao.queryMedicalDetail(params);
				total_count = medicalDao.queryMedicalDetailCount(params);
				export_task_id = "MedicalImplementAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalImplementAnalysisDetail";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","成员姓名","是否政策落实","是否行业重合","操作"};
			//住房政策落实分析详细
			}else if("house".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-住房政策落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				table_list = houseDao.queryHousingDetail(params);
				total_count = houseDao.queryHousingDetailCount(params);
				export_task_id = "HouseImplementAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=HouseImplementAnalysisDetail";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","危房级别","改造进度","是否列入行业计划","操作"};
			//道路硬底化监控落实分析详细
			}else if("road".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-道路硬底化监控落实分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fairlyDao.queryRoadImplementAnalysisDetail(params);
				total_count = fairlyDao.queryRoadImplementAnalysisDetailCount(params);
				export_task_id = "RoadManagement";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=RoadManagement";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","帮扶村未通沥青(水泥)路里程数(公里)","200人以上自然村到行政村未通沥青(水泥)村数(个)","年初行政村到乡镇未通沥青(水泥)路里程(公里)","年初自然村到行政村未通沥青(水泥)路里程(公里)","年初帮扶村未通里程数合计(公里)","改造完成里程(公里)","改造完成率(%)","操作"};
				//安全饮水监控落实分析详细
			}else if("water".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-安全饮水监控落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fairlyDao.queryWaterSortImplementAnalysisDetail(params);
				total_count = fairlyDao.queryWaterSortDetailCount(params);
				export_task_id = "WaterManagement";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=WaterManagement";
				size = 10;
				content_arr = new String[]{"序号","市","县","镇","村","是否全实现安全饮水","年初未实现饮水安全户数(户)","实现改造户数(户)","改造完成率(%)","操作"};
				//生活用电监控落实分析详细
			}else if("electricity".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-生活用电监控落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fairlyDao.queryElectricitySortDetail(params);
				total_count = fairlyDao.queryElectricitySortDetailCount(params);
				export_task_id = "ElectricityManagement";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ElectricityManagement";
				size = 9;
				content_arr = new String[]{"序号","市","县","镇","村","是否全通生活用电","年初是否全通生活用电","是否完成改造","操作"};
				//医疗设施监控落实分析详细
			}else if("medical_facility".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-医疗设施监控落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fairlyDao.queryMedicalSortDetail(params);
				total_count = fairlyDao.queryMedicalSortDetailCount(params);
				export_task_id = "MedicalFacilityManagement";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalFacilityManagement";
				size = 10;
				content_arr = new String[]{"序号","市","县","镇","村","卫生室个数(个)","行政村执业(助理)医师(个)","新建卫生站个数(个)","新增执业医师个数(个)","操作"};
				//通广播电视监控落实分析详细
			}else if("broadcast".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-通广播电视监控落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fairlyDao.queryBroadcastSortDetail(params);
				total_count = fairlyDao.queryBroadcastSortDetailCount(params);
				export_task_id = "BroadcastManagement";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=BroadcastManagement";
				size = 9;
				content_arr = new String[]{"序号","市","县","镇","村","是否全通广播电视","未通广播电视的户数(户)","是否完成改造","操作"};
				//网络覆盖监控落实分析详细
			}else if("net".equals(wintype) && "implement_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-网络覆盖监控落实分析详细===========");
				params.put("T_NAME", "R_DW_POOROBJ_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fairlyDao.queryNetcastSortDetail(params);
				total_count = fairlyDao.queryNetSortDetailCount(params);
				export_task_id = "NetManagement";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=NetManagement";
				size = 9;
				content_arr = new String[]{"序号","市","县","镇","村","网络是否全覆盖","年初是否全覆盖 ","是否完成改造","操作"};
			}else if("poor_analysis".equals(wintype) && "poor_analysis".equals(tabtype)){
				log.info("查询贫困分析>贫困分析明细详细===========");
				//System.out.println("查询贫困分析>贫困分析明细详细===========");
				//notes = "备注，建档立卡数据日期2017年2月28日。";
				table_list = poorAnalyseDao.queryPoorAnalysisDetail(params);
				total_count = poorAnalyseDao.queryPoorAnalysisDetailCount(params);
				export_task_id = "PoorAnalysis";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorAnalysis";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","户号","户主姓名","贫困户属性","劳动力属性","致贫原因","期初人均可支配收入(元)","操作"};
			}else if("alarmed_poor".equals(wintype) && "exception_monitor".equals(tabtype)){
				log.info("贫困识别监控>异常监控>异常明细===========");
				System.out.println("贫困识别监控>异常监控>异常明细===========");
				params.put("T_NAME", "R_DW_POP_IDENTIFICATION_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = alarmedPoorExceptionDao.queryPoorIndetificationDetailo(params);
				total_count = alarmedPoorExceptionDao.queryPoorIndetificationDetailoCount(params);
				export_task_id = "AlarmedPoorDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedPoorDetail";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","户号","户主姓名","成员姓名","异常类型","异常描述","操作"};
			}else if("alarmed_records".equals(wintype) && "exception_monitor".equals(tabtype)){
				log.info("预警监控>建档立卡异常记录监控>异常明细===========");
				//System.out.println("预警监控>建档立卡异常记录监控>异常明细===========");
				params.put("T_NAME", "R_DW_EXCEPTION_MONITOR_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = alarmedPoorExceptionDao.queryPoorRecordsDetail(params);
				total_count = alarmedPoorExceptionDao.queryPoorRecordsDetailCount(params);
				export_task_id = "AlarmedRecordsDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedRecordsDetail";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","户号","户主姓名","成员姓名","异常类型","异常描述","异常记录","操作"};	
			}else if("alarmed_money".equals(wintype) && "money_monitor".equals(tabtype)){
				log.info("预警监控>帮扶资金监控>异常明细===========");
				//System.out.println("预警监控>帮扶资金监控>异常明细===========");
				params.put("T_NAME", "R_DW_POOROBJ_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = alarmedPoorExceptionDao.queryPoorMoneyDetail(params);
				total_count = alarmedPoorExceptionDao.queryPoorMoneyDetailCount(params);
				export_task_id = "AlarmedMoneyDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedMoneyDetail";
				size = 10;
				content_arr = new String[]{"序号","市","县","镇","村","帮扶单位","主要领导","当年帮扶资金(万元)","累计帮扶资金(万元)","操作"};

			}else if("project".equals(wintype) && "project_analysis".equals(tabtype)){
				log.info("项目监控>项目分析>项目类型明细===========");
				//到村项目
				String toCountry = params.get("country_project") == null ? "" : (String)params.get("country_project");
				String toFamily = params.get("family_project") == null ? "" : (String)params.get("family_project");
			    List<String> projClass = new ArrayList<String>();
			    if(null != toCountry && !"".equals(toCountry) ){
			    	String[] toCountrys = toCountry.split(",");
			    	for(String et : toCountrys){
			    		if("10".equals(et)){ //选择村项目其它
			    			projClass.add("基金");
			    			projClass.add("捐赠慰问");
			    		}else
			    			
			    		projClass.add(BaseConstant.projClassMap.get(et));
			    	}
			    	
			    }
			    if(null != toFamily && !"".equals(toFamily) ){
			    	String[] toFamilys = toFamily.split(",");
			    	for(String st : toFamilys){
	    				if("19".equals(st)){//选择户项目政策补贴和社会保障
	    					projClass.add("低保金");
			    			projClass.add("五保金");
			    			projClass.add("养老保险");
			    			projClass.add("医疗保险");
			    			projClass.add("医疗救助");
			    			projClass.add("政策性补贴");
			    			projClass.add("转移性收入");
			    			projClass.add("转移性支出");
			    		}else
			    		projClass.add(BaseConstant.projClassMap.get(st));
			    	}
			    	
			    }
			    params.put("projClass",projClass);
			    params.put("T_NAME", "R_DW_HELP_PROJECT_Y");
			    String dateStr = getNewCreateDate(params);
			    notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = projectManagementDao.projectAnalysisDetail(params);
				total_count = projectManagementDao.projectAnalysisDetailCount(params);
				export_task_id = "projectAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=projectAnalysisDetail";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","项目属性","项目类型","项目数(个)","投资金额(万元)","项目完成率(%)","操作"};

			}else if("duty".equals(wintype) && "duty_analysis".equals(tabtype)){
				log.info("责任监控>帮扶责任分析>帮扶责任明细===========");
				//System.out.println("责任监控>帮扶责任分析>帮扶责任明细===========");
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = dutyDao.dutyAnalysisDetail(params);
				total_count = dutyDao.dutyAnalysisDetailCount(params);
				export_task_id = "dutyAnalysisDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=dutyAnalysisDetail";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","帮扶单位","驻村干部","帮扶责任人","走访次数(次)","操作"};

			}else if("project_country".equals(wintype) && "road".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-村道路硬底化分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.queryRoadImplementAnalysisDetail(params);
				total_count = countryDao.queryRoadImplementAnalysisDetailCount(params);
				export_task_id = "ManagementRoad";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementRoad";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","项目子类","开始日期","里程(公里)","投入资金(万元)","项目状态","操作"};
				
			}else if("project_country".equals(wintype) && "water".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-饮水工程分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.queryWaterImplementAnalysisDetail(params);
				total_count = countryDao.queryWaterImplementAnalysisDetailCount(params);
				export_task_id = "ManagementWater";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementWater";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","开始日期","受益户数","受益人数","投入资金(万元)","项目状态","操作"};
			}else if("project_country".equals(wintype) && "recreation_sport".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-文体设施分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.querySportImplementAnalysisDetail(params);
				total_count = countryDao.querySportImplementAnalysisDetailCount(params);
				export_task_id = "ManagementRecreationSport";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementRecreationSport";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","项目子类","开始日期","投入资金(万元)","项目状态","操作"};
			}else if("project_country".equals(wintype) && "hygiene".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-卫生设施分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.queryHygieneImplementAnalysisDetail(params);
				total_count = countryDao.queryHygieneImplementAnalysisDetailCount(params);
				export_task_id = "ManagementHygiene";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementHygiene";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","项目子类","开始日期","投入资金(万元)","项目状态","操作"};
			}else if("project_country".equals(wintype) && "lamp".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-路灯安装分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.queryLampImplementAnalysisDetail(params);
				total_count = countryDao.queryLampImplementAnalysisDetailCount(params);
				export_task_id = "ManagementLamp";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementLamp";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","开始日期","路灯数(盏)","投入资金(万元)","项目状态","操作"};
			}else if("project_country".equals(wintype) && "farm".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-农田水利分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.queryFarmImplementAnalysisDetail(params);
				total_count = countryDao.queryFarmImplementAnalysisDetailCount(params);
				export_task_id = "ManagementFarm";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementFarm";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","项目子类","开始日期","投入资金(万元)","项目状态","操作"};
			}else if("project_country".equals(wintype) && "public_facility".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-公共设施分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.queryFacilityImplementAnalysisDetail(params);
				total_count = countryDao.queryFacilityImplementAnalysisDetailCount(params);
				export_task_id = "ManagementFacility";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementFacility";
				size = 10;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","开始日期","投入资金(万元)","项目状态","操作"};
			}else if("project_country".equals(wintype) && "collective_economy".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-集体经济分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.queryEconomyImplementAnalysisDetail(params);
				total_count = countryDao.queryEconomyImplementAnalysisDetailCount(params);
				export_task_id = "ManagementEconomy";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementEconomy";
				size = 11;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","开始日期","投入资金(万元)","项目收益(万元)","项目状态","操作"};
			}else if("project_country".equals(wintype) && "edu".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-教育教学分析详细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_COUNTRY_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = countryDao.queryEduImplementAnalysisDetail(params);
				total_count = countryDao.queryEduImplementAnalysisDetailCount(params);
				export_task_id = "ManagementEdu";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ManagementEdu";
				size = 10;
				content_arr = new String[]{"序号","市","县","镇","村","项目名称","开始日期","投入资金(万元)","项目状态","操作"};
			}else if("alarmed_visit".equals(wintype) && "visit_monitor".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-贫困户走访异常明细===========");
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = warningDao.queryAlarmedImplementAnalysisDetail(params);
				total_count = warningDao.queryAlarmedImplementAnalysisDetailCount(params);
				export_task_id = "AlarmedVisit";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedVisit";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","驻村队长","帮扶责任人","当年走访次数","累计走访次数","操作"};
			}//扶贫对象变动分析详细
			else if("project_family".equals(wintype) && "industry".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-项目监控户扶贫项目分析-产业扶贫===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list= fpDao.queryIndustryDetail(params);
				total_count = fpDao.queryIndustryDetailCount(params);
				export_task_id = "THEFAMIndustryDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=THEFAMIndustryDetail";
				size = 16;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","贫困属性","劳动力属性","产业名称","项目开始时期","项目规模","投入资金（万元）",
						"项目收益（万元）","项目状态","操作"};
			}//项目监控户扶贫项目分析-产业扶贫
			else if("project_family".equals(wintype) && "finance".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-项目监控户扶贫项目分析-金融扶贫===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fpDao.queryFinanceDetail(params);
				total_count = fpDao.queryFinanceDetailCount(params);
				export_task_id = "THEFAMFinanceDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=THEFAMFinanceDetail";
				size = 14;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","贫困属性","贷款人姓名","贷款开始时间","贷款金额（万元）","贷款利息（万元）",
						"项目状态","操作"};
			}//项目监控户扶贫项目分析-金融扶贫
			else if("project_family".equals(wintype) && "house".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-项目监控户扶贫项目分析-住房改造===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list= fpDao.queryHouseDetail(params);
				total_count = fpDao.queryHouseDetailCount(params);
				export_task_id = "THEFAMHouseDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=THEFAMHouseDetail";
				size = 13;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","贫困属性","改造开始时间","是否完工","改造金额（万元）",
						"项目状态","操作"};
			}//项目监控户扶贫项目分析-住房改造
			else if("project_family".equals(wintype) && "property".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-项目监控户扶贫项目分析-资产扶贫===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list= fpDao.queryPropertyDetail(params);
				total_count = fpDao.queryPropertyDetailCount(params);
				export_task_id = "THEFAMPropertyDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=THEFAMPropertyDetail";
				size = 13;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","贫困属性","项目开始时间","投入资金（万元）",
						"项目收益（万元）","项目状态","操作"};
			}//项目监控户扶贫项目分析-资产扶贫
			else if("project_family".equals(wintype) && "visit".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-项目监控户扶贫项目分析-慰问扶贫===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fpDao.queryVisitDetail(params);
				total_count = fpDao.queryVisitDetailCount(params);
				export_task_id = "THEFAMVisitDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=THEFAMVisitDetail";
				size = 12;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","贫困属性","慰问时间","慰问金额（元）",
						"项目状态","操作"};
			}//项目监控户扶贫项目分析-慰问扶贫
			else if("project_family".equals(wintype) && "employment".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-项目分析-就业扶贫===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fpDao.queryEmploymentDetail(params);
				total_count = fpDao.queryEmploymentDetailCount(params);
				export_task_id = "THEFAMEmploymentDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=THEFAMEmploymentDetail";
				size = 15;
				content_arr = new String[]{"序号","年份","市","县","镇","村","户码","户主姓名","贫困属性","就业人员姓名","就业地点","临时工/稳定工","年就业收益（万元）",
						"项目状态","操作"};
			}//项目分析-就业扶贫
			else if("project_family".equals(wintype) && "skill".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-项目监控户扶贫项目分析-技能培训===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list =fpDao.querySkillDetail(params);
				total_count = fpDao.querySkillDetailCount(params);
				export_task_id = "THEFAMSkillDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=THEFAMSkillDetail";
				size = 14;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","贫困属性","培训人员姓名","培训内容","培训时间","投入资金（万元）",
						"项目状态","操作"};
			}//项目监控户扶贫项目分析-技能培训
			else if("project_family".equals(wintype) && "edu".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-项目监控户扶贫项目分析-教育扶贫===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				table_list = fpDao.queryEduDetail(params);
				total_count = fpDao.queryEduDetailCount(params);
				export_task_id = "THEFAMEduDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=THEFAMEduDetail";
				size = 14;
				content_arr = new String[]{"序号","市","县","镇","村","户码","户主姓名","贫困属性","学生姓名","教育阶段","扶贫时间","投入资金（万元）",
						"项目状态","操作"};
			}//项目监控户扶贫项目分析-教育扶贫
			else if("capital".equals(wintype) && "capital_analysis".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-资金分析明细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。"+"表中统计数据为万元。";
				table_list = fmtDao.queryFundAnalysisManagementDetail(params);
				total_count = fmtDao.queryFundAnalysisManagementDetailCount(params);
				export_task_id = "FundAnalysisManagementDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FundAnalysisManagementDetail";
				size = 32;
				
				content_arr = new String[]{"序号","市","县","镇","村","户","人","项<br/>目<br/>类<br/>型","项<br/>目<br/>名<br/>称","项目预<br/>计投入<br/>金额"
										,"项<br/>目<br/>状<br/>态","项目<br/>开始<br/>时间","项目<br/>结束<br/>时间","项目实<br/>际投入<br/>金额","帮扶单<br/>位自筹<br/>资金",
											"帮扶市","中央资金","省资金","被帮扶市资金","个<br/>人<br/>自<br/>筹","医<br/>疗<br/>报<br/>销","项<br/>目<br/>收<br/>益","操<br/>作"};
				
				
			}//资金分析明细
			else if("example_country_building".equals(wintype) && "renovate_standard".equals(tabtype)){
				log.info("查询数据监控窗口详细清单弹窗表格信息-示范村明细===========");
				params.put("T_NAME", "R_DW_HELP_PROJECT_FAM_Y");
				String dateStr = getNewCreateDate(params);
				notes = "备注，建档立卡数据日期"+dateStr+"。";
				month = (String) getValue(params,"month");
				params.put("month", year+String.format("%02d", Integer.parseInt(month)));
				table_list = exampleCountryDao.queryFundAnalysisManagementDetail(params);
				total_count = exampleCountryDao.queryFundAnalysisManagementDetailCount(params);
				export_task_id = "ExampleManagementplees";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ExampleManagementplees";
				size = 17;
				content_arr = new String[]{"序号","市","县","镇","村","20户以</br>上自然村</br>(个)","编制整治</br>规划","三清三拆</br>三整治","村道巷道硬化","垃圾处理","污水处理","集中供水","住房建设","公共服务</br>设施","成立村民</br>理事会","自然村达标</br>标准","示范村达标</br>标准"};
				
				
			}
			//用于导出的参数
			paramsPool.put(export_task_id, params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject table = new JSONObject();
			table.put("title", "明细表");
			table.put("export_url", export_url);
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head = new ArrayList<JSONObject>();
			if("capital".equals(wintype) && "capital_analysis".equals(tabtype)){
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan",
						"colspan","colspan","colspan","colspan",
						"rowspan","rowspan","rowspan","rowspan"};
				for(int j=0;j<23;j++){
					JSONObject head_1 =  new JSONObject();
					if (j==16) {
						head_1.put(span_arr[j], 2);
					}else if(j==18){
						head_1.put(span_arr[j], 5);

					}else{
						head_1.put(span_arr[j], 3);
					}
					

					head_1.put("content", content_arr[j]);
					head.add(head_1);
				}
				head_list.add(head);
				
				List<JSONObject> head2 = new ArrayList<JSONObject>();
				String[] content_arr2 = {"地级<br/>市财<br/>政资<br/>金","区级<br/>和镇级<br/>财政<br/>资金","社会<br/>资金",
										"中央<br/>财政<br/>资金","中央<br/>行业<br/>资金",
										"省级<br/>财政<br/>资金","省级<br/>行业<br/>资金","社会<br/>资金",
										"地级<br/>市财<br/>政资<br/>金","县级<br/>和镇<br/>级财<br/>政资<br/>金","地级<br/>市行<br/>业资<br/>金","县级<br/>和镇<br/>级行<br/>业资<br/>金","社<br/>会<br/>资<br/>金"};
				for(int k=0;k<13;k++){
					JSONObject head_2 =  new JSONObject();
					head_2.put("content", content_arr2[k]);
					head2.add(head_2);
				}
				head_list.add(head2);
				
			}else{
				for(int j=0;j<size;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put("content", content_arr[j]);
					head.add(head_1);
				}
				head_list.add(head);
			}
			
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<size;n++){
					JSONObject head_content =  new JSONObject();
					if(n==(size-1)){
						if("capital".equals(wintype) && "capital_analysis".equals(tabtype)){
							Object o =table_list.get(m).get("TYPE");
							int num=Integer.parseInt(o==null?"":o.toString());
							if(num==1){
								head_content.put("content", "村档案");
								head_content.put("action_type", "country_file");
								JSONObject action_data =  new JSONObject();
								action_data.put("area_id", table_list.get(m).get("B1"));
								head_content.put("action_data", action_data);
							}else if(num==2){
								head_content.put("content", "户档案");
								head_content.put("action_type", "family_file");
								JSONObject action_data =  new JSONObject();
								action_data.put("family_id", table_list.get(m).get("S_ID"));
								head_content.put("action_data", action_data);
								
							}
							
						}else{
							if ("road".equals(wintype)|| "water".equals(wintype) || "electricity".equals(wintype) 
									||"medical_facility".equals(wintype)  || "broadcast".equals(wintype) 
									||"net".equals(wintype)||"project_country".equals(wintype)|| "road".equals(tabtype)
									||"water".equals(tabtype)||"recreation_sport".equals(tabtype)||"hygiene".equals(tabtype)
									||"lamp".equals(tabtype)||"farm".equals(tabtype)||"public_facility".equals(tabtype)
									|| "collective_economy".equals(tabtype)|| "edu".equals(tabtype)
									|| "project_analysis".equals(tabtype) || "alarmed_money".equals(wintype)) {
									head_content.put("content", "村档案");
									head_content.put("action_type", "country_file");
									JSONObject action_data =  new JSONObject();
									action_data.put("area_id", table_list.get(m).get("B1"));
									head_content.put("action_data", action_data);
								}else if("example_country_building".equals(wintype) && "renovate_standard".equals(tabtype)){
									if(n==0){
										head_content.put("content", ((page-1)*10+(m+1)));
									}else{
										head_content.put("content", table_list.get(m).get("A"+(n+1)));
									}
								}else{
									head_content.put("content", "户档案");
									head_content.put("action_type", "family_file");
									JSONObject action_data =  new JSONObject();
									action_data.put("family_id", table_list.get(m).get("S_ID"));
									head_content.put("action_data", action_data);
								}
								
						}
	
					}else{
						if(n==0){
							head_content.put("content", ((page-1)*10+(m+1)));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			
			table.put("notes", notes);
			
			JSONObject pagination = new JSONObject();
			pagination.put("page_total", total_count%10>0?total_count/10+1:total_count/10);
			pagination.put("page_cur", page);
			pagination.put("amount_total", total_count);
			table.put("pagination", pagination);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("查询数据监控窗口详细清单弹窗表格信息失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 数据监控窗口区域数据弹窗表格
	 * @param id 区域ID
	 * @param level 层级
	 * @param year 年
	 * @param wintype 数据监控窗口所属类型
	 * @param tabtype 数据监控窗口标签页类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorAreaDataTable(HttpServletRequest request,String id,String level,String year,String wintype,String tabtype) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			    //System.out.println("key: "+entry.getKey()+"  valule: "+entry.getValue()[0]);
			}
			//查询table数据
			List<Map<String, Object>> table_list = new ArrayList<Map<String, Object>>();
			
			String export_task_id = "";
			String export_url = "";
			String win_title = "";
			String tips = "";
			int total_head_size = 0;
			int head1_size = 0;
			int head2_size = 0;
			int head3_size = 0;
			int total_size = 0;
			String[] span_arr = null;
			String[] clon_arr = null; //高的值
			String[] span_value_arr = null;
			String[] clon_value_arr = null;// 高
			String[] span_arr2 = null;
			String[] span_value_arr2 = null;
			String[] content_arr1 = null;
			String[] content_arr2 = null;
			String[] content_arr3 = null;
			//扶贫对象变动排序
			if("poor".equals(wintype) && "change_order".equals(tabtype)){
				table_list = pcDao.querySortDetail(params);
				export_task_id = "ChangeOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ChangeOrderArea";
				win_title = "扶贫对象监控>变动分析>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				total_head_size = 2;
				head1_size = 11;
				span_arr = new String[]{"rowspan","colspan","colspan","colspan","colspan","colspan","colspan","rowspan","rowspan","colspan","colspan"};
				span_value_arr = new String[]{"2","2","2","2","2","2","2","2","2","2","2"};
				content_arr1 = new String[]{"行政区域","年初贫困户总数","新增贫困户总数","终止贫困户总数","销户贫困户总数","拆户贫困户总数","并户贫困户总数","自然增加人数","自然减少人数","当前贫困户总数","准确率(%)"};
				head2_size = 16;
				content_arr2 = new String[]{"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","按户数","按人数"};
				total_size = 19;
			//脱贫成效排序
			}else if("result".equals(wintype) && "result_order".equals(tabtype)){
				table_list = prDao.querySortDetail(params);
				export_task_id = "ResultOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ResultOrderArea";
				win_title = "脱贫对象监控>成效排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				total_head_size = 2;
				head1_size = 7;
				span_arr = new String[]{"rowspan","colspan","colspan","colspan","colspan","colspan","colspan"};
				span_value_arr = new String[]{"2","2","2","2","2","2","2"};
				content_arr1 = new String[]{"行政区域","未脱贫数","年度预脱贫计划人数","监测达标人数","达标与计划人数比例%","预脱贫人数","预脱贫率%"};
				head2_size = 12;
				content_arr2 = new String[]{"户数","人数","户数","人数","户数","人数","按户数","按人数","户数","人数","按户数","按人数"};
				total_size = 13;
			//人均可支配收入排序
			}else if("average_income".equals(wintype) && "income_order".equals(tabtype)){
				table_list = diDao.querySortDetail(params);
				export_task_id = "IncomeOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=IncomeOrderArea";
				win_title = "人均可支配收入监控>收入排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				total_head_size = 2;
				head1_size = 10;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","colspan","rowspan","colspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"2","2","2","2","8","2","4","2","2","2"};
				content_arr1 = new String[]{"行政区域","工资性收入(万元)","生成经营性收入(万元)","财产性收入(万元)","转移性收入","生产经营性支出(万元)","转移性支出","未偿还借(贷)款(万元)","可支配收入(万元)","人均可支配收入(元)"};
				head2_size = 12;
				content_arr2 = new String[]{"计划生育金(万元)","低保金(万元)","五保金(万元)","养老保险金(万元)","生态补偿金(万元)","其他转移性收入(万元)","城乡居民基本医疗保险报销医疗费(万元)","医疗救助金(万元)","个人所得税(万元)","社会保障支出(万元)","赡养支出(万元)","其他转移性支出(万元)"};
				total_size = 20;
			//低五保政策落实排序
			}else if("five_low".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = flDao.flveLowArea(params);
				export_task_id = "FiveLowOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FiveLowOrderArea";
				win_title = "低保五保政策落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				total_head_size = 3;
				head1_size = 8;
				span_arr = new String[]{"rowspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan"};
				span_value_arr = new String[]{"3","2","6","6","6","6","6","6"};
				content_arr1 = new String[]{"行政区域","贫困户数","应该享受低保/五保政策","已经享受低保/五保政策","政策落实比例(%)","行业数据","重合数","重合率(%)"};
				head2_size = 20;
				span_arr2 = new String[]{"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan"};
				span_value_arr2 = new String[]{"2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2"};
				content_arr2 = new String[]{"户数","人数","一般贫困户","低保户","五保户","一般贫困户","低保户","五保户","一般贫困户","低保户","五保户","一般贫困户","低保户","五保户","一般贫困户","低保户","五保户","一般贫困户","低保户","五保户"};
				head3_size = 36;
				content_arr3 = new String[]{"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","按户数","按人数","按户数","按人数","按户数","按人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","按户数","按人数","按户数","按人数","按户数","按人数"};
				total_size = 39;
			//教育政策落实排序
			}else if("edu".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = eduDao.queryEducationSortDetail(params);
				export_task_id = "EduOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=EduOrderArea";
				win_title = "教育政策落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				total_head_size = 2;
				head1_size = 7;
				span_arr = new String[]{"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan"};
				span_value_arr = new String[]{"2","2","2","2","2","2","2"};
				content_arr1 = new String[]{"行政区域","贫困人口数","应该享受教育政策人数","已经享受教育政策人数","教育政策落实率(%)","行业重合数","重合率(%)"};
				head2_size = 10;
				content_arr2 = new String[]{"义务教育人数","高中教育人数","义务教育人数","高中教育人数","义务教育人数","高中教育人数","义务教育人数","高中教育人数","义务教育人数","高中教育人数"};
				total_size = 12;
			//医疗政策落实排序
			}else if("medical_policy".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = medicalDao.querySortDetail(params);
				export_task_id = "MedicalOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalOrderArea";
				win_title = "医疗政策落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				head1_size = 6;
				span_arr = new String[]{"rowspan","rowspan","colspan","colspan","colspan","colspan"};
				span_value_arr = new String[]{"1","1","1","1","1","1"};
				content_arr1 = new String[]{"行政区域","贫困户人数","已购买医疗保险人数","医疗政策落实率(%)","行业重合数","重合率(%)"};
				total_size = 6;
			//住房政策落实排序
			}else if("house".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = houseDao.querySortDetail(params);
				export_task_id = "HouseOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=HouseOrderArea";
				win_title = "住房政策落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				total_head_size = 2;
				head1_size = 6;
				span_arr = new String[]{"rowspan","colspan","colspan","colspan","colspan","colspan"};
				span_value_arr = new String[]{"2","5","5","5","5","5"};
				content_arr1 = new String[]{"行政区域","危房改造户数","已完成改造户数","动工改造户数","未动工改造户数","危房改造完成率(%)"};
				head2_size = 25;
				content_arr2 = new String[]{"合计","D级","C级","B级","A级","合计","D级","C级","B级","A级","合计","D级","C级","B级","A级","合计","D级","C级","B级","A级","合计","D级","C级","B级","A级"};
				total_size = 26;
/*测试使用*/
			//道路硬底化排序
			}else if("road".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = fairlyDao.queryRoadSortDetail(params);
				export_task_id = "RoadOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=RoadOrderArea";
				win_title = "道路硬底化落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				head1_size = 10;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"3","3","5","5","5","5","5","5","5","5"};
				content_arr1 = new String[]{"行政区域","村数(个)","完全硬底化村数(个)","完全硬底化村比例(%)","200人以上自然村到行政村未通沥青(水泥)村数(个)","年初行政村到乡镇未通沥青(水泥)路里程(公里)","年初自然村到行政村未通沥青(水泥)路里程(公里)","年初帮扶村未通里程数合计(公里)","改造完成里程(公里)","改造完成率(%)"};
				total_size = 10;
			//安全饮水排序
			}else if("water".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = fairlyDao.queryWaterSortSortDetail(params);
				export_task_id = "WaterOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=WaterOrderArea";
				win_title = "安全饮水落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				head1_size = 8;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"3","3","5","5","5","5","5","5","5","5"};
				content_arr1 = new String[]{"行政区域","村数(个)","全实现安全饮水的村数(个)","全实现安全饮水村比例(%)","未全实现安全饮水的村数(个)","年初未实现饮水安全户数(户)","完成改造户数(户)","改造完成率(%)"};
				total_size = 8;
			//生活用电排序
			}else if("electricity".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = fairlyDao.queryElectricitySortAortDetail(params);
				export_task_id = "ElectricityOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ElectricityOrderArea";
				win_title = "生活用电落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				head1_size = 7;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"3","3","5","5","5","5","5","5","5","5"};
				content_arr1 = new String[]{"行政区域","村数(个)","全部通生活用电的村数(个)","全部通生活用电的村比例(%)","年初未全部通生活用电的村数(个)","完成改造村数(个)","改造完成率(%)"};
				total_size = 7;
			//医疗设施排序
			}else if("medical_facility".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = fairlyDao.queryMedicalSortAortDetail(params);
				export_task_id = "MedicalFacilityOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalFacilityOrderArea";
				win_title = "医疗设施落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				head1_size = 10;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"3","3","5","5","5","5","5","5","5","5"};
				content_arr1 = new String[]{"行政区域","村数(个)","有卫生室的村数(个)","有卫生站的村比例","无卫生站的村数(个)","新建卫生站的村数(个)","有执业医师的村数(个)","有执业医师的村比例(%)","未有行政村执业(助理)医师的村数(个)","新增执业医师的村数(个)"};
				total_size = 10;
			//通广播电视排序
			}else if("broadcast".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = fairlyDao.queryBroadcastSortAortDetail(params);
				export_task_id = "BroadcastOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=BroadcastOrderArea";
				win_title = "通广播电视落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				head1_size = 7;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"3","3","5","5","5","5","5","5","5","5"};
				content_arr1 = new String[]{"行政区域","村数(个)","全通广播电视的村数(个)","全通广播电视的比例(%)","年初未全通广播电视的村数(个)","完成改造村数(个)","改造完成率(%)"};
				total_size = 7;
			//网络覆盖排序
			}else if("net".equals(wintype) && "implement_order".equals(tabtype)){
				table_list = fairlyDao.queryNetSortAortDetail(params);
				export_task_id = "NetOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=NetOrderArea";
				win_title = "网络覆盖落实监控>落实排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。";
				head1_size = 7;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"3","3","5","5","5","5","5","5","5","5"};
				content_arr1 = new String[]{"行政区域","村数(个)","全覆盖的村数(个)","全覆盖的村比例(%)","年初未全覆盖的村数(个)","完成改造村数(个)","改造完成率(%)"};
				total_size = 7;
			//项目排序
			}else if("project".equals(wintype) && "project_order".equals(tabtype)){
				table_list = projectManagementDao.projectAnalysisSortDetail(params);
				export_task_id = "ProjectOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectOrderArea";
				win_title = "项目监控>项目排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"。上表“数量“单位为个，“计划资金”、“实际资金”单位为万元。";
				total_head_size = 3;
				head1_size = 4;
				span_arr = new String[]{"rowspan","colspan","colspan","colspan"};
				clon_arr =  new String[]{"colspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"3","2","20","20"};
				clon_value_arr =  new String[]{"1","2","1","1"};
				content_arr1 = new String[]{"行政区域","总计","户项目","村项目"};
				head2_size = 20;
				span_arr2 = new String[]{"colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan"};
				span_value_arr2 = new String[]{"2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2"};
				content_arr2 = new String[]{"合计","产业<br>扶贫","金融<br>扶贫","住房<br>改造","资产<br>扶贫","慰问<br>扶贫","就业<br>扶贫","技能<br>培训","教育<br>扶贫","政策补贴+<br>社会保障","合计","村道<br>建设","饮水工程","文体<br>设施","卫生<br>设施","路灯<br>安装","农田<br>水利","公共<br>设施","集体<br>经济","教育<br>教学"};
				head3_size = 42;
				content_arr3 = new String[]{"数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额","数量","金额"};
				total_size = 43;
			//贫困识别排序
			}else if("alarmed_poor".equals(wintype) && "exception_order".equals(tabtype)){
				table_list = alarmedPoorExceptionDao.queryPoorIndetificationDetail(params);
				export_task_id = "AlarmedPoorOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedPoorOrderArea";
				win_title = "贫困识别监控>异常排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				total_head_size = 2;
				head1_size = 9;
				span_arr = new String[]{"rowspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan"};
				span_value_arr = new String[]{"2","2","2","2","2","2","2","2","2"};
				content_arr1 = new String[]{"行政区域","合计(条)","身份信息不匹配","低保五保不匹配","残疾信息不匹配","名下有房","名下有车","有工商注册信息","享受财政供养"};
				head2_size = 16;
				content_arr2 = new String[]{"比对项","异常项","比对项","异常项","比对项","异常项","比对项","异常项","比对项","异常项","比对项","异常项","比对项","异常项","比对项","异常项"};
				total_size = 17;
			//建档立卡异常排序
			}else if("alarmed_records".equals(wintype) && "exception_order".equals(tabtype)){

				table_list = alarmedPoorExceptionDao.queryPoorRecordDetail(params);
				export_task_id = "AlarmedRecordsOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedRecordsOrderArea";
				win_title = "建档立卡异常记录监控>异常监控>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				total_head_size = 2;
				head1_size = 6;
				span_arr = new String[]{"rowspan","colspan","colspan","colspan","colspan","colspan"};
				span_value_arr = new String[]{"2","2","2","2","2","2"};
				content_arr1 = new String[]{"行政区域","合计(条)","户主身份信息错漏","成员身份信息错漏","残疾人信息错漏","重复身份信息记录"};
				head2_size = 10;
				content_arr2 = new String[]{"监测记录数","异常记录数","监测记录数","异常记录数","监测记录数","异常记录数","监测记录数","异常记录数","监测记录数","异常记录数"};
				total_size = 11;
			//帮扶资金排序
			}else if("alarmed_money".equals(wintype) && "money_order".equals(tabtype)){
				table_list = alarmedPoorExceptionDao.queryAlarmedMoneyDetail(params);
				export_task_id = "AlarmedMoneyOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedMoneyOrderArea";
				win_title = "帮扶资金监控>资金排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				head1_size = 4;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"1","1","1","1"};
				content_arr1 = new String[]{"行政区域","相对贫困村数(个)","帮扶资金小于＜30万的相对贫困村数(个)","比例(%)"};
				total_size = 4;
			//贫困户走访排序
			}else if("alarmed_visit".equals(wintype) && "visit_order".equals(tabtype)){
				table_list = warningDao.queryAlarmedSortDetail(params);
				
				List<Map<String,Object>> table_list_a = warningDao.queryAlarmedA5(params);
				if(table_list.size()!=0 ){
					if (table_list_a.size()!=0) {
						for (int i = 0; i < table_list.size(); i++) {
							
							table_list.get(i).put("A4",table_list_a.get(i).get("A4"));
						
					}
					}
					}
				
				
				export_task_id = "AlarmedVisitOrderArea";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedVisitOrderArea";
				win_title = "贫困户走访监控>走访排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				head1_size = 5;
				span_arr = new String[]{"rowspan","rowspan","rowspan","rowspan","rowspan"};
				span_value_arr = new String[]{"1","1","1","1","1"};
				content_arr1 = new String[]{"行政区域","累计贫困户数","帮扶责任人数","一年内未被走访的累计贫困户数","比例(%)"};
				total_size = 5;
			}else if("capital".equals(wintype) && "capital_order".equals(tabtype)){
				table_list = fmtDao.queryFundAnalysisSortDetail(params);
				export_task_id = "FundAnalysisSortDetail";
				export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FundAnalysisSortDetail";
				win_title = "扶贫资金管理>资金排序>区域数据";
				params.put("T_NAME", "R_DW_POOROBJ_FAM_Y");
				String dateStr = getNewCreateDate(params);
				tips = "备注，建档立卡数据日期"+dateStr+"；行业数据日期"+getNewIndustryDate(params)+"。";
				total_head_size = 2;
				head1_size = 8;
				span_arr = new String[] {"rowspan","rowspan","rowspan","rowspan","colspan","colspan","colspan","colspan"};
				span_value_arr = new String[]{"4","4","4","4","4","3","4","6"};
				content_arr1 = new String[]{"序号","行政<br/>区域","年度<br/>项目<br/>资金<br/>总额","帮扶单位<br/>自筹资金","帮扶市","中央","省级","被帮扶市"};
				head2_size = 17;
				content_arr2 = new String[] {"合计","地级<br/>市财<br/>政资<br/>金","区级<br/>和镇级<br/>财政<br/>资金","社会<br/>资金",
						"合计","中央<br/>财政<br/>资金","中央<br/>行业<br/>资金",
						"合计","省级<br/>财政<br/>资金","省级<br/>行业<br/>资金","社会<br/>资金",
						"合计","地级<br/>市财<br/>政资<br/>金","县级<br/>和镇<br/>级财<br/>政资<br/>金","地级<br/>市行<br/>业资<br/>金","县级<br/>和镇<br/>级行<br/>业资<br/>金","社会<br/>资金"};
				total_size = 21;
			}
			//用于导出的参数
			paramsPool.put(export_task_id, params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			data.put("win_title", win_title);
			
			JSONObject table = new JSONObject();
			table.put("title", "");
			table.put("export_url", export_url);
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			for(int j=0;j<head1_size;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], span_value_arr[j]);
				if(null != clon_arr){
					head_1.put(clon_arr[j], clon_value_arr[j]);
				}
				head_1.put("content", content_arr1[j]);
				head1.add(head_1);
				
			}
			head_list.add(head1);
			
			if(total_head_size == 2){
				List<JSONObject> head2 = new ArrayList<JSONObject>();
				for(int k=0;k<head2_size;k++){
					JSONObject head_2 =  new JSONObject();
					head_2.put("content", content_arr2[k]);
					head2.add(head_2);
				}
				head_list.add(head2);
			}else if(total_head_size == 3){
				List<JSONObject> head2 = new ArrayList<JSONObject>();
				for(int k=0;k<head2_size;k++){
					JSONObject head_2 =  new JSONObject();
					head_2.put(span_arr2[k], span_value_arr2[k]);
					head_2.put("content", content_arr2[k]);
					head2.add(head_2);
				}
				head_list.add(head2);
				
				List<JSONObject> head3 = new ArrayList<JSONObject>();
				for(int l=0;l<head3_size;l++){
					JSONObject head_3 =  new JSONObject();
					head_3.put("content", content_arr3[l]);
					head3.add(head_3);
				}
				head_list.add(head3);
			}
			
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
//			//合计
//			List<JSONObject> body0 =  new ArrayList<JSONObject>();
//			if(table_list_c!=null){
//				for(int n=0;n<total_size;n++){
//					JSONObject head_content =  new JSONObject();
//					head_content.put("content", table_list_c.get("A"+(n+2)));
//					body0.add(head_content);
//				}
//				body_list.add(body0);
//			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<total_size;n++){
					JSONObject head_content =  new JSONObject();
					head_content.put("content", table_list.get(m).get("A"+(n+2)));
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			
			table.put("notes", tips);
			data.put("table", table);
			
			json.put("data", data);
			log.info(win_title+"=========");
		}catch(Exception e){
			e.printStackTrace();
			log.error("数据监控窗口区域数据弹窗表格信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 数据监控变动管理JSON
	 * @param id 区域id
	 * @param level 区域层级
	 * @param year 年份
	 * @return
	 */
	public String getDataMonitorPoorChangeManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询数据监控变动管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("PoorChangeManagement", params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			 final CountDownLatch latch = new CountDownLatch(3);//线程计数器
			 //左图右图
			 List<Map<String,Object>> config_current_list = new ArrayList<Map<String,Object>>();
			 List<Map<String,Object>> config_count_list = new ArrayList<Map<String,Object>>();
			 Runnable rn = new AnalyseThread(params,"poorChange",config_current_list,config_count_list,latch,this);
			 Thread t = new Thread(rn) ;
			 taskExecutor.execute(t);
			 
			 //合计
			 Map<String,Object> table_list_c = new HashMap<String,Object>();
			 Runnable rn1 = new AnalyseThread(params,"poorChangecol",table_list_c,latch,this);
			 Thread t1 = new Thread(rn1) ;
			 taskExecutor.execute(t1);
			 
			 List<Map<String,Object>> table_list = new ArrayList<Map<String,Object>>();
			 Runnable rn2 = new AnalyseThread(params,"poorChangetal",table_list,null,latch,this);
			 Thread t2 = new Thread(rn2) ;
			 taskExecutor.execute(t2);
			 
			 try {
					log.info("扶贫监控对象-变动管理-等待3个查询子线程执行完毕...");
		            latch.await(60000,TimeUnit.MILLISECONDS);//最多等待60超时
		            log.info("扶贫监控对象-变动管理-3个查询子线程已经执行完毕");
		            log.info("扶贫监控对象-变动管理-继续执行主线程返回结果集");
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
			 
			JSONObject chart_config_poor_changed_yearly = new JSONObject();
			JSONObject chart_config_poor_changed_accumulated = new JSONObject();
				//查图表数据
				params.put("IF_YEAR_POOR", 1);
				Long config_current = pcDao.queryPoorChangeCount(params);
				params.put("IF_YEAR_POOR", 0);
				Long config_count = pcDao.queryPoorChangeCount(params);
				data.put("amount_poor_changed_yearly_begin", config_current==null?0:config_current);
				data.put("amount_poor_changed_accumulated", config_count==null?0:config_count);
				chart_config_poor_changed_yearly.put("convert_method", "genChartBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<config_current_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", config_current_list.get(i).get("S_NAME"));
					config.put("group", config_current_list.get(i).get("S_GROUP"));
					config.put("value", config_current_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_poor_changed_yearly.put("config", config_list);
				data.put("chart_config_poor_changed_yearly", chart_config_poor_changed_yearly);
				
				chart_config_poor_changed_accumulated.put("convert_method", "genChartBarsConfig");
				List<JSONObject> config2_list = new ArrayList<JSONObject>();
				for(int i=0;i<config_count_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", config_count_list.get(i).get("S_NAME"));
					config.put("group", config_count_list.get(i).get("S_GROUP"));
					config.put("value", config_count_list.get(i).get("S_VALUE"));
					config2_list.add(config);
				}
				chart_config_poor_changed_accumulated.put("config", config2_list);
				data.put("chart_config_poor_changed_accumulated", chart_config_poor_changed_accumulated);
	
				JSONObject table = new JSONObject();
				//查table数据
				//Map<String,Object> table_list_c = null;
				
				//List<Map<String,Object>> table_list = pcDao.queryThePoorTrendAllTable(params);
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorChangeManagement");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan","colspan","rowspan","rowspan","colspan","colspan"};
				String[] content_arr = {"序号","行政区域","年初贫困户","新增贫困户","终止贫困户","销户贫困户","拆户贫困户","并户贫困户","自然<br>增加<br>人数","自然<br>减少<br>人数","当前贫困户","准确率（%）"};
				for(int j=0;j<12;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				List<JSONObject> head2 = new ArrayList<JSONObject>();
				String[] content_arr2 = {"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","按户数","按人数"};
				for(int k=0;k<16;k++){
					JSONObject head_2 =  new JSONObject();
					head_2.put("content", content_arr2[k]);
					head2.add(head_2);
				}
				head_list.add(head2);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null && table_list_c.size() > 0){
					for(int n=0;n<20;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<20;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("数据监控变动管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		
//		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 扶贫监控变动分析-状态属性-累计贫困户
	 * @param id 行政区域的ID
	 * @param level 层级
	 * @param scope 区域类型
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param status_attribute 状态属性
	 * @return
	 */
	public String getDataMonitorPoorChangeAnalysisAll(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
		log.info("查询扶贫监控变动分析-状态属性-累计贫困户信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			params.put("poor_attribute", poor_attribute);
			params.put("labor_attribute", labor_attribute);
			params.put("status_attribute", status_attribute);
			params.put("scope", scope);
			params.put("status", 1);
			//用于导出的参数
			paramsPool.put("PoorChangeAnalysisAll", params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			JSONObject chart_config_trend = new JSONObject();
						//查柱状图数据
						List<Map<String,Object>> chart_list = pcDao.queryThePoorTrendAllChart(params);
						chart_config_trend.put("convert_method", "genChartBarsConfig");
						List<JSONObject> config_list = new ArrayList<JSONObject>();
						for(int i=0;i<chart_list.size();i++){
							JSONObject config = new JSONObject();
							config.put("name", chart_list.get(i).get("S_NAME"));
							config.put("group", chart_list.get(i).get("S_GROUP"));
							config.put("value", chart_list.get(i).get("S_VALUE"));
							config_list.add(config);
						}
						chart_config_trend.put("config", config_list);
						data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
						//查table数据
						Map<String,Object> table_list_c = null;
						if(!"country".equals(level)){
							table_list_c = pcDao.queryThePoorTrendAllTableC(params);//合计
						}
						List<Map<String,Object>> table_list = pcDao.queryThePoorTrendAllTable(params);
						table.put("title", "统计表");
						table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorChangeAnalysisAll");
						List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
						List<JSONObject> head1 = new ArrayList<JSONObject>();
						String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan","colspan","rowspan","rowspan","colspan","colspan"};
						String[] content_arr = {"序号","行政区域","年初贫困户","新增贫困户","终止贫困户","销户贫困户","拆户贫困户","并户贫困户","自然<br>增加<br>人数","自然<br>减少<br>人数","当前贫困户","准确率（%）"};
						for(int j=0;j<12;j++){
							JSONObject head_1 =  new JSONObject();
							head_1.put(span_arr[j], 2);
							head_1.put("content", content_arr[j]);
							head1.add(head_1);
						}
						head_list.add(head1);
						List<JSONObject> head2 = new ArrayList<JSONObject>();
						String[] content_arr2 = {"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","按户数","按人数"};
						for(int k=0;k<16;k++){
							JSONObject head_2 =  new JSONObject();
							head_2.put("content", content_arr2[k]);
							head2.add(head_2);
						}
						head_list.add(head2);
						table.put("head", head_list);
						List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
						//合计
						List<JSONObject> body0 =  new ArrayList<JSONObject>();
						if(table_list_c!=null){
							for(int n=0;n<20;n++){
								JSONObject head_content =  new JSONObject();
								if(n==0){
									head_content.put("content", "-");
								}else{
									head_content.put("content", table_list_c.get("A"+(n+1)));
								}
								body0.add(head_content);
							}
							body_list.add(body0);
						}
						//table体数据
						for(int m=0;m<table_list.size();m++){
							List<JSONObject> body =  new ArrayList<JSONObject>();
							for(int n=0;n<20;n++){
								JSONObject head_content =  new JSONObject();
								if(n==0){
									head_content.put("content", (m+1));
								}else{
									head_content.put("content", table_list.get(m).get("A"+(n+1)));
								}
								body.add(head_content);
							}
							body_list.add(body);
						}
						table.put("body", body_list);
						data.put("table", table);
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("扶贫监控变动分析-状态属性-累计贫困户信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
//		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 扶贫监控变动分析-状态属性-当前贫困户
	 * @param id 行政区域的ID
	 * @param level 层级
	 * @param scope 区域类型
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param status_attribute 状态属性
	 * @return
	 */
	public String getDataMonitorPoorChangeAnalysisCur(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
		log.info("查询扶贫监控变动分析-状态属性-当前贫困户信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			params.put("poor_attribute", poor_attribute);
			params.put("labor_attribute", labor_attribute);
			params.put("status_attribute", status_attribute);
			params.put("scope", scope);
			//用于导出的参数
			paramsPool.put("PoorChangeAnalysisCur", params);
			//查柱状图数据
			List<Map<String,Object>> chart_list = pcDao.queryThePoorTrendAllChart(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = pcDao.queryThePoorTrendAllTableC(params);//合计
			}
			List<Map<String,Object>> table_list = pcDao.queryThePoorTrendAllTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorChangeAnalysisCur");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan","colspan","rowspan","rowspan","colspan","colspan"};
			String[] content_arr = {"序号","行政区域","年初贫困户","新增贫困户","终止贫困户","销户贫困户","拆户贫困户","并户贫困户","自然<br>增加<br>人数","自然<br>减少<br>人数","当前贫困户","准确率（%）"};
			for(int j=0;j<12;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","按户数","按人数"};
			for(int k=0;k<16;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<20;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<20;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("扶贫监控变动分析-状态属性-当前贫困户信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
//		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 扶贫监控变动分析-状态属性-新增贫困户
	 * @param id 行政区域的ID
	 * @param level 层级
	 * @param scope 区域类型
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param status_attribute 状态属性
	 * @return
	 */
	public String getDataMonitorPoorChangeAnalysisAdd(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
		log.info("查询扶贫监控变动分析-状态属性-新增贫困户信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			params.put("poor_attribute", poor_attribute);
			params.put("labor_attribute", labor_attribute);
			params.put("status_attribute", status_attribute);
			params.put("scope", scope);
			//用于导出的参数
			paramsPool.put("PoorChangeAnalysisAdd", params);
			//查柱状图数据
			List<Map<String,Object>> chart_list = pcDao.queryThePoorTrendAddChart(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = pcDao.queryThePoorTrendAddTableC(params);//合计
			}
			List<Map<String,Object>> table_list = pcDao.queryThePoorTrendAddTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLevelLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorChangeAnalysisAdd");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_name_arr = {"rowspan","rowspan","colspan","colspan","colspan"};
			String[] span_value_arr = {"3","3","2","12","2"};
			String[] content_arr = {"序号","行政区域","合计","属于'六进'","其他原因"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				if(j==2 || j==4){
					head_1.put(span_name_arr[j], span_value_arr[j]);
					head_1.put("rowspan", 2);
				}else{
					head_1.put(span_name_arr[j], span_value_arr[j]);
				}
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"'六进'之一","'六进'之二","'六进'之三","'六进'之四","'六进'之五","'六进'之六"};
			for(int k=0;k<6;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("colspan", 2);
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			List<JSONObject> head3 = new ArrayList<JSONObject>();
			String[] content_arr3 = {"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数"};
			for(int l=0;l<16;l++){
				JSONObject head_3 =  new JSONObject();
				head_3.put("content", content_arr3[l]);
				head3.add(head_3);
			}
			head_list.add(head3);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<18;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<18;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("扶贫监控变动分析-状态属性-新增贫困户信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 扶贫监控变动分析-状态属性-终止贫困户 
	 * @param id 行政区域的ID
	 * @param level 层级
	 * @param scope 区域类型
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param status_attribute 状态属性
	 * @return
	 */
	public String getDataMonitorPoorChangeAnalysisEnd(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
		log.info("查询扶贫监控变动分析-状态属性-终止贫困户 信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			params.put("poor_attribute", poor_attribute);
			params.put("labor_attribute", labor_attribute);
			params.put("status_attribute", status_attribute);
			params.put("scope", scope);
			//用于导出的参数
			paramsPool.put("PoorChangeAnalysisEnd", params);
			//查柱状图数据
			List<Map<String,Object>> chart_list = pcDao.queryThePoorTrendEndChart(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = pcDao.queryThePoorTrendEndTableC(params);//合计
			}
			List<Map<String,Object>> table_list = pcDao.queryThePoorTrendEndTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLevelLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorChangeAnalysisEnd");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_name_arr = {"rowspan","rowspan","colspan","colspan","colspan"};
			String[] span_value_arr = {"3","3","2","14","2"};
			String[] content_arr = {"序号","行政区域","合计","属于'七不进'","其他原因"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				if(j==2 || j==4){
					head_1.put(span_name_arr[j], span_value_arr[j]);
					head_1.put("rowspan", 2);
				}else{
					head_1.put(span_name_arr[j], span_value_arr[j]);
				}
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"'七不进'之一","'七不进'之二","'七不进'之三","'七不进'之四","'七不进'之五","'七不进'之六","'七不进'之七"};
			for(int k=0;k<7;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("colspan", 2);
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			List<JSONObject> head3 = new ArrayList<JSONObject>();
			String[] content_arr3 = {"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数"};
			for(int l=0;l<18;l++){
				JSONObject head_3 =  new JSONObject();
				head_3.put("content", content_arr3[l]);
				head3.add(head_3);
			}
			head_list.add(head3);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<20;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<20;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("扶贫监控变动分析-状态属性-终止贫困户查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 扶贫监控变动分析-状态属性-自然增减
	 * @param id 行政区域的ID
	 * @param level 层级
	 * @param scope 区域类型
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param status_attribute 状态属性
	 * @return
	 */
	public String getDataMonitorPoorChangeAnalysisNaturalChange(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
		log.info("查询扶贫监控变动分析-状态属性-自然增减信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			params.put("poor_attribute", poor_attribute);
			params.put("labor_attribute", labor_attribute);
			params.put("status_attribute", status_attribute);
			params.put("scope", scope);
			//用于导出的参数
			paramsPool.put("PoorChangeAnalysisNaturalChange", params);
			//查柱状图数据
			List<Map<String,Object>> chart_add_list = pcDao.queryThePoorTrendNAddChart(params);
			List<Map<String,Object>> chart_cut_list = pcDao.queryThePoorTrendNReduceChart(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = pcDao.queryThePoorTrendNAddTableC(params);//合计
			}
			List<Map<String,Object>> table_list = pcDao.queryThePoorTrendNAddTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend_add = new JSONObject();
			chart_config_trend_add.put("convert_method", "genChartLevelLegendBarsConfig");
			List<JSONObject> config_add_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_add_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_add_list.get(i).get("S_NAME"));
				config.put("group", chart_add_list.get(i).get("S_GROUP"));
				config.put("value", chart_add_list.get(i).get("S_VALUE"));
				config.put("type", chart_add_list.get(i).get("S_TYPE"));
				config_add_list.add(config);
			}
			chart_config_trend_add.put("config", config_add_list);
			data.put("chart_config_trend_add", chart_config_trend_add);
			
			JSONObject chart_config_trend_cut = new JSONObject();
			chart_config_trend_cut.put("convert_method", "genChartLevelLegendBarsConfig");
			List<JSONObject> config_cut_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_cut_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_cut_list.get(i).get("S_NAME"));
				config.put("group", chart_cut_list.get(i).get("S_GROUP"));
				config.put("value", chart_cut_list.get(i).get("S_VALUE"));
				config.put("type", chart_cut_list.get(i).get("S_TYPE"));
				config_cut_list.add(config);
			}
			chart_config_trend_cut.put("config", config_cut_list);
			data.put("chart_config_trend_cut", chart_config_trend_cut);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorChangeAnalysisNaturalChange");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_name_arr = {"rowspan","rowspan","colspan","colspan"};
			String[] span_value_arr = {"3","3","10","10"};
			String[] content_arr = {"序号","行政区域","自然增加","自然减少"};
			for(int j=0;j<4;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_name_arr[j], span_value_arr[j]);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"合计","新生儿","嫁入","错误","其他","合计","死亡","嫁出","错误","其他"};
			for(int k=0;k<10;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("colspan", 2);
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			List<JSONObject> head3 = new ArrayList<JSONObject>();
			String[] content_arr3 = {"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数"};
			for(int l=0;l<20;l++){
				JSONObject head_3 =  new JSONObject();
				head_3.put("content", content_arr3[l]);
				head3.add(head_3);
			}
			head_list.add(head3);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<22;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<22;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("扶贫监控变动分析-状态属性-自然增减查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 脱贫成效监控-计划管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorPoorResultPlanManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询脱贫成效监控-计划管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("PoorResultPlanManagement", params);
			//查左边图表数据
			List<Map<String,Object>> chart_left = prDao.queryPovertyResultsChartL(params);
			//查右边图表数据
			List<Map<String,Object>> chart_right = prDao.queryPovertyResultsChartR(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = prDao.queryPovertyResultsTableC(params);//合计
			}
			List<Map<String,Object>> table_list = prDao.queryPovertyResultsTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_accumulated = new JSONObject();
			chart_config_accumulated.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_left.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_left.get(i).get("S_NAME"));
				config.put("group", chart_left.get(i).get("S_GROUP"));
				config.put("value", chart_left.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_accumulated.put("config", config_list);
			data.put("chart_config_accumulated", chart_config_accumulated);
			
			JSONObject chart_config_cur_year = new JSONObject();
			chart_config_cur_year.put("convert_method", "genChartBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_right.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_right.get(i).get("S_NAME"));
				config.put("group", chart_right.get(i).get("S_GROUP"));
				config.put("value", chart_right.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_cur_year.put("config", config2_list);
			data.put("chart_config_cur_year", chart_config_cur_year);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorResultPlanManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan","colspan"};
			String[] content_arr = {"序号","行政区域","未脱贫人数","年度预脱贫<br>计划人数","监测达标人数","达标与计划<br>人数比例%","预脱贫人数","预脱贫率%"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"户数","人数","户数","人数","户数","人数","户数","人数","户数","人数","户数","人数"};
			for(int k=0;k<12;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<14;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<14;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("脱贫成效监控-计划管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 脱贫成效监控-成效分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域类型
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorPoorResultResultAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute) {
		log.info("查询脱贫成效监控-成效分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("PoorResultResultAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_1 = prDao.queryAnalysisOfEffectsChart2(params);
			List<Map<String,Object>> chart_2 = prDao.queryAnalysisOfEffectsChart3(params);
			List<Map<String,Object>> chart_3 = prDao.queryAnalysisOfEffectsChart1(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = prDao.queryAnalysisOfEffectsTableC(params);//合计
			}
			List<Map<String,Object>> table_list = prDao.queryAnalysisOfEffectsTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_by_poor_attribute = new JSONObject();
			chart_config_by_poor_attribute.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_1.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_1.get(i).get("S_NAME"));
				config.put("group", chart_1.get(i).get("S_GROUP"));
				config.put("value", chart_1.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_by_poor_attribute.put("config", config_list);
			data.put("chart_config_by_poor_attribute", chart_config_by_poor_attribute);
			
			JSONObject chart_config_by_labor_attribute = new JSONObject();
			chart_config_by_labor_attribute.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_2.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_2.get(i).get("S_NAME"));
				config.put("group", chart_2.get(i).get("S_GROUP"));
				config.put("value", chart_2.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_by_labor_attribute.put("config", config2_list);
			data.put("chart_config_by_labor_attribute", chart_config_by_labor_attribute);
			
			JSONObject chart_config_by_scope = new JSONObject();
			chart_config_by_scope.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config3_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_3.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_3.get(i).get("S_NAME"));
				config.put("group", chart_3.get(i).get("S_GROUP"));
				config.put("value", chart_3.get(i).get("S_VALUE"));
				config3_list.add(config);
			}
			chart_config_by_scope.put("config", config3_list);
			data.put("chart_config_by_scope", chart_config_by_scope);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorResultResultAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","colspan"};
			String[] content_arr = {"序号","行政区域","预脱贫人数","预脱贫率%"};
			for(int j=0;j<4;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"户数","人数","按户数","按人数"};
			for(int k=0;k<4;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("脱贫成效监控-成效分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 脱贫成效监控-未脱贫分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域类型
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorPoorResultNotSuccessAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute) {
		log.info("查询脱贫成效监控-未脱贫分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("PoorResultNotSuccessAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = prDao.queryNotAnalysisChart(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = prDao.queryNotAnalysisTableC(params);//合计
			}
			List<Map<String,Object>> table_list = prDao.queryNotAnalysisTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_not_success = new JSONObject();
			chart_config_not_success.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_not_success.put("config", config_list);
			data.put("chart_config_not_success", chart_config_not_success);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=PoorResultNotSuccessAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan"};
			String[] span_value_arr = {"2","2","2","3","3","1","3"};
			String[] content_arr = {"序号","行政区域","未脱贫数","综合得分(户数)","人均可支配收入<br>有劳动能力户数","低保/五保<br>政策落实","三保障指标"};
			for(int j=0;j<7;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], span_value_arr[j]);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"户数","人数","＜60","60≤x＜100","100分<br>(监控数)","4000元以下","4000元-7365元","7365元以上","未享受低保/五保政策人数","义务教育未达标人数","医疗保障未达标人数","住房安全未达标户数"};
			for(int k=0;k<12;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<14;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<14;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("脱贫成效监控-未脱贫分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 人均可支配收入监控收入管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域类型
	 * @return
	 */
	public String getDataMonitorAverageIncomeIncomeManagement(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询人均可支配收入监控收入管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			params.put("scope", scope);
			//用于导出的参数
			paramsPool.put("AverageIncomeIncomeManagement", params);
			//查饼图表数据
			List<Map<String,Object>> chart_l = diDao.queryDisposableIncomeMChartL(params);
			List<Map<String,Object>> chart_r = diDao.queryDisposableIncomeMChartR(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = diDao.queryDisposableIncomeMTableC(params);//合计
			}
			List<Map<String,Object>> table_list = diDao.queryDisposableIncomeMTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_average_income_trend = new JSONObject();
			chart_config_average_income_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			String[] value_arr = {"6433","6883","7365"};
			String[] year_arr = {"2016","2017","2018"};
			for(int i=0;i<value_arr.length;i++){
				JSONObject config = new JSONObject();
				config.put("name", year_arr[i]);
				config.put("group", "目标值");
				config.put("value", value_arr[i]);
				config_list.add(config);
				
				JSONObject config2 = new JSONObject();
				try {
					config2.put("name", chart_l.get(i).get("S_NAME"));
					config2.put("group", "当前值");
					config2.put("value", chart_l.get(i).get("S_VALUE"));
				} catch (Exception e) {
					config2.put("name", year_arr[i]);
					config2.put("group", "当前值");
					config2.put("value", 0);
				}
				
				
				config_list.add(config2);
				
			}
			for(int i=0;i<chart_l.size();i++){
				
				
			}
			
			
			
			
			
			chart_config_average_income_trend.put("config", config_list);
			data.put("chart_config_average_income_trend", chart_config_average_income_trend);
			
			JSONObject chart_config_income_area_distribution = new JSONObject();
			chart_config_income_area_distribution.put("convert_method", "genChartLineBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", "总收入(万元)");
				config.put("value", chart_r.get(i).get("S_VALUE1"));
				config.put("type", "bar");
				config2_list.add(config);
				JSONObject config2 = new JSONObject();
				config2.put("name", chart_r.get(i).get("S_NAME"));
				config2.put("group", "总支出(万元)");
				config2.put("value", chart_r.get(i).get("S_VALUE2"));
				config2.put("type", "bar");
				config2_list.add(config2);
				JSONObject config3 = new JSONObject();
				config3.put("name", chart_r.get(i).get("S_NAME"));
				config3.put("group", "可支配收入(万元)");
				config3.put("value", chart_r.get(i).get("S_VALUE3"));
				config3.put("type", "bar");
				config2_list.add(config3);
				JSONObject config4 = new JSONObject();
				config4.put("name", chart_r.get(i).get("S_NAME"));
				config4.put("group", "人均可支配收入(元/年)");
				config4.put("value", chart_r.get(i).get("S_VALUE4"));
				config4.put("type", "line");
				config2_list.add(config4);
			}
			chart_config_income_area_distribution.put("config", config2_list);
			data.put("chart_config_income_area_distribution", chart_config_income_area_distribution);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomeIncomeManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","有劳动能力的<br>贫困户数","有劳动能力户的<br>贫困人口数","总收入(万元)","总支出(万元)","可支配收入(万元)","人均可支配收入(元/年)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("人均可支配收入监控收入管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 人均可支配收入监控脱贫户分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param year 年
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorAverageIncomeSuccessAnalysis(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
		log.info("查询人均可支配收入监控脱贫户分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("aid_year", true);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			    //System.out.println("key: "+entry.getKey()+"  valule: "+entry.getValue());
			}
			//用于导出的参数
			paramsPool.put("AverageIncomeSuccessAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_1 = diDao.queryExpensesAnalyzeChart1(params);
			List<Map<String,Object>> chart_2 = diDao.queryExpensesAnalyzeChart2(params);
			List<Map<String,Object>> chart_3 = diDao.queryExpensesAnalyzeChart3(params);
			List<Map<String,Object>> chart_4 = diDao.queryExpensesAnalyzeChart4(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = diDao.queryExpensesAnalyzeTableC(params);//合计
			}
			List<Map<String,Object>> table_list = diDao.queryExpensesAnalyzeTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_average_disposable_income = new JSONObject();
			chart_config_average_disposable_income.put("convert_method", "genChartBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_1.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_1.get(i).get("S_NAME"));
				config.put("group", chart_1.get(i).get("S_GROUP"));
				config.put("value", chart_1.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_average_disposable_income.put("config", config_list);
			data.put("chart_config_average_disposable_income", chart_config_average_disposable_income);
			
			JSONObject chart_config_income_composition = new JSONObject();
			chart_config_income_composition.put("convert_method", "genChartPieConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_2.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_2.get(i).get("S_NAME"));
				config.put("value", chart_2.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_income_composition.put("config", config2_list);
			data.put("chart_config_income_composition", chart_config_income_composition);
			
			JSONObject chart_config_income_distribution = new JSONObject();
			chart_config_income_distribution.put("convert_method", "genChartPieConfig");
			List<JSONObject> config3_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_3.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_3.get(i).get("S_NAME"));
				config.put("value", chart_3.get(i).get("S_VALUE"));
				config3_list.add(config);
			}
			chart_config_income_distribution.put("config", config3_list);
			data.put("chart_config_income_distribution", chart_config_income_distribution);
			
			JSONObject chart_config_payment_composition = new JSONObject();
			chart_config_payment_composition.put("convert_method", "genChartPieConfig");
			List<JSONObject> config4_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_4.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_4.get(i).get("S_NAME"));
				config.put("value", chart_4.get(i).get("S_VALUE"));
				config4_list.add(config);
			}
			chart_config_payment_composition.put("config", config4_list);
			data.put("chart_config_payment_composition", chart_config_payment_composition);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomeSuccessAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","rowspan","colspan","colspan","colspan","colspan","rowspan","colspan","colspan","rowspan","rowspan","rowspan"};
			String[] content_arr = {"序号","行政区域","总收入<br>(万元)","工资性收入","生产经营性收入","财产性收入","转移性收入","总支出<br>(万元)","生产经营性支出","转移性支出","未偿还<br>借(贷)款","可支配收入(万元)","人均可支配收入(元)"};
			for(int j=0;j<13;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"金额","比例","金额","比例","金额","比例","金额","比例","金额","比例","金额","比例"};
			for(int k=0;k<12;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<19;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<19;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("人均可支配收入监控脱贫户分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 人均可支配收入监控脱贫户分析-转移性收入分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param year 年
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorAverageIncomeSuccessAnalysisTransferredIncome(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
		log.info("查询人均可支配收入监控脱贫户分析-转移性收入分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("aid_year", true);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AverageIncomeSuccessAnalysisTI", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = diDao.queryIncomeAnalysisChart(params);
			//查table数据
			Map<String,Object> table_list_year = diDao.queryIncomeAnalysisC(params);
			List<Map<String,Object>> table_list = diDao.queryIncomeAnalysis (params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_composition = new JSONObject();
			chart_config_composition.put("convert_method", "genChartPieConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_composition.put("config", config_list);
			data.put("chart_config_composition", chart_config_composition);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomeSuccessAnalysisTI");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","时间","计划生育金(万元)","低保金(万元)","五保金(万元)","养老保险金(万元)","生态补偿金(万元)","其他转移性收入(万元)","城乡居民基本医疗保险报销医疗费(万元)","医疗救助金(万元)"};
			for(int j=0;j<10;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//当年累计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_year!=null){
				for(int n=0;n<10;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", 1);
					}else{
						head_content.put("content", table_list_year.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<10;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+2));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("人均可支配收入监控脱贫户分析-转移性收入分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
//		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 人均可支配收入监控脱贫户分析-转移性支出分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param year 年
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorAverageIncomeSuccessAnalysisTransferredPayment(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
		log.info("查询人均可支配收入监控脱贫户分析-转移性支出分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("aid_year", true);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AverageIncomeSuccessAnalysisTP", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = diDao.querySpendingAnalysisChart(params);
			//查table数据
			Map<String,Object> table_list_year = diDao.querySpendingAnalysisC(params);
			List<Map<String,Object>> table_list = diDao.querySpendingAnalysis (params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_composition = new JSONObject();
			chart_config_composition.put("convert_method", "genChartPieConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_composition.put("config", config_list);
			data.put("chart_config_composition", chart_config_composition);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomeSuccessAnalysisTP");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","时间","个人所得税(万元)","社会保障支出(万元)","赡养支出(万元)","其他转移性支出(万元)"};
			for(int j=0;j<6;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//当年累计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_year!=null){
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", 1);
					}else{
						head_content.put("content", table_list_year.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+2));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("人均可支配收入监控脱贫户分析-转移性支出分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
//		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 人均可支配收入监控贫困户分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param year 年
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorAverageIncomePoorAnalysis(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
		log.info("查询人均可支配收入监控贫困户分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("aid_year", false);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			    //System.out.println("key: "+entry.getKey()+"  valule: "+entry.getValue()[0]);
			}
			//用于导出的参数
			paramsPool.put("AverageIncomePoorAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_1 = diDao.queryExpensesAnalyzeChart1(params);
			List<Map<String,Object>> chart_2 = diDao.queryExpensesAnalyzeChart2(params);
			List<Map<String,Object>> chart_3 = diDao.queryExpensesAnalyzeChart3(params);
			List<Map<String,Object>> chart_4 = diDao.queryExpensesAnalyzeChart4(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = diDao.queryExpensesAnalyzeTableC(params);//合计
			}
			List<Map<String,Object>> table_list = diDao.queryExpensesAnalyzeTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_average_disposable_income = new JSONObject();
			chart_config_average_disposable_income.put("convert_method", "genChartBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_1.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_1.get(i).get("S_NAME"));
				config.put("group", chart_1.get(i).get("S_GROUP"));
				config.put("value", chart_1.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_average_disposable_income.put("config", config_list);
			data.put("chart_config_average_disposable_income", chart_config_average_disposable_income);
			
			JSONObject chart_config_income_composition = new JSONObject();
			chart_config_income_composition.put("convert_method", "genChartPieConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_2.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_2.get(i).get("S_NAME"));
				config.put("value", chart_2.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_income_composition.put("config", config2_list);
			data.put("chart_config_income_composition", chart_config_income_composition);
			
			JSONObject chart_config_income_distribution = new JSONObject();
			chart_config_income_distribution.put("convert_method", "genChartPieConfig");
			List<JSONObject> config3_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_3.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_3.get(i).get("S_NAME"));
				config.put("value", chart_3.get(i).get("S_VALUE"));
				config3_list.add(config);
			}
			chart_config_income_distribution.put("config", config3_list);
			data.put("chart_config_income_distribution", chart_config_income_distribution);
			
			JSONObject chart_config_payment_composition = new JSONObject();
			chart_config_payment_composition.put("convert_method", "genChartPieConfig");
			List<JSONObject> config4_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_4.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_4.get(i).get("S_NAME"));
				config.put("value", chart_4.get(i).get("S_VALUE"));
				config4_list.add(config);
			}
			chart_config_payment_composition.put("config", config4_list);
			data.put("chart_config_payment_composition", chart_config_payment_composition);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomePoorAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","rowspan","colspan","colspan","colspan","colspan","rowspan","colspan","colspan","rowspan","rowspan","rowspan"};
			String[] content_arr = {"序号","行政区域","总收入<br>(万元)","工资性收入","生产经营性收入","财产性收入","转移性收入","总支出<br>(万元)","生产经营性支出","转移性支出","未偿还<br>借(贷)款","可支配收入(万元)","人均可支配收入(元)"};
			for(int j=0;j<13;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"金额","比例","金额","比例","金额","比例","金额","比例","金额","比例","金额","比例"};
			for(int k=0;k<12;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<19;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<19;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("人均可支配收入监控贫困户分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
//		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 人均可支配收入监控贫困户分析-转移性收入分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param year 年
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorAverageIncomePoorAnalysisTransferredIncome(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
		log.info("查询人均可支配收入监控贫困户分析-转移性收入分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("aid_year", false);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AverageIncomePoorAnalysisTI", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = diDao.queryIncomeAnalysisChart(params);
			//查table数据
			Map<String,Object> table_list_year = diDao.queryIncomeAnalysisC(params);
			List<Map<String,Object>> table_list = diDao.queryIncomeAnalysis (params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_composition = new JSONObject();
			chart_config_composition.put("convert_method", "genChartPieConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_composition.put("config", config_list);
			data.put("chart_config_composition", chart_config_composition);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomePoorAnalysisTI");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","时间","计划生育金(万元)","低保金(万元)","五保金(万元)","养老保险金(万元)","生态补偿金(万元)","其他转移性收入(万元)","城乡居民基本医疗保险报销医疗费(万元)","医疗救助金(万元)"};
			for(int j=0;j<10;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//当年累计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_year!=null){
				for(int n=0;n<10;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", 1);
					}else{
						head_content.put("content", table_list_year.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<10;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+2));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("人均可支配收入监控贫困户分析-转移性收入分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
//		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 人均可支配收入监控贫困户分析-转移性支出分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @param year 年
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorAverageIncomePoorAnalysisTransferredPayment(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
		log.info("查询人均可支配收入监控贫困户分析-转移性支出分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("aid_year", false);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AverageIncomePoorAnalysisTP", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = diDao.querySpendingAnalysisChart(params);
			//查table数据
			Map<String,Object> table_list_year = diDao.querySpendingAnalysisC(params);
			List<Map<String,Object>> table_list = diDao.querySpendingAnalysis (params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_composition = new JSONObject();
			chart_config_composition.put("convert_method", "genChartPieConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_composition.put("config", config_list);
			data.put("chart_config_composition", chart_config_composition);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AverageIncomePoorAnalysisTP");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","时间","个人所得税(万元)","社会保障支出(万元)","赡养支出(万元)","其他转移性支出(万元)"};
			for(int j=0;j<6;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//当年累计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_year!=null){
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", 1);
					}else{
						head_content.put("content", table_list_year.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+2));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("人均可支配收入监控贫困户分析-转移性支出分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");	
		}
//		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 低保五保政策落实监控落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorFiveLowImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询低保五保政策落实监控落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("FiveLowImplementManagement", params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			JSONObject table = new JSONObject();
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//查图表数据
						//查饼图表数据
						List<Map<String,Object>> chart_l = flDao.flveLowGovernHu(params);
						List<Map<String,Object>> chart_r = flDao.flveLowGovernRen(params);
						JSONObject chart_config_trend = new JSONObject();
						chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
						List<JSONObject> config_list = new ArrayList<JSONObject>();
						for(int i=0;i<chart_l.size();i++){
							JSONObject config = new JSONObject();
							config.put("name", chart_l.get(i).get("S_NAME"));
							config.put("group", chart_l.get(i).get("S_GROUP"));
							config.put("value", chart_l.get(i).get("S_VALUE"));
							config_list.add(config);
						}
						chart_config_trend.put("config", config_list);
						data.put("chart_config_trend", chart_config_trend);
						
						JSONObject chart_config_current = new JSONObject();
						chart_config_current.put("convert_method", "genChartLegendBarsConfig");
						List<JSONObject> config2_list = new ArrayList<JSONObject>();
						for(int i=0;i<chart_r.size();i++){
							JSONObject config = new JSONObject();
							config.put("name", chart_r.get(i).get("S_NAME"));
							config.put("group", chart_r.get(i).get("S_GROUP"));
							config.put("value", chart_r.get(i).get("S_VALUE"));
							config2_list.add(config);
						}
						chart_config_current.put("config", config2_list);
						data.put("chart_config_current", chart_config_current);
			
				//查table数据
				
							Map<String,Object> table_list_c = null;
							if(!"country".equals(level)){
								table_list_c = flDao.flveLowGovernCount(params);//合计
							}
							table.put("title", "统计表");
							table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FiveLowImplementManagement");
							List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
							List<JSONObject> head1 = new ArrayList<JSONObject>();
							String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan"};
							String[] content_arr = {"序号","行政区域","当前贫困户总数","应该享受低保/五保政策","已经享受低保/五保政策","政策落实比例(%)","行业数据","重合数","重合率(%)"};
							for(int j=0;j<9;j++){
								JSONObject head_1 =  new JSONObject();
								head_1.put(span_arr[j], 2);
								head_1.put("content", content_arr[j]);
								head1.add(head_1);
							}
							head_list.add(head1);
							List<JSONObject> head2 = new ArrayList<JSONObject>();
							String[] content_arr2 = {"户数","人数","户数","人数","户数","人数","按户数","按人数","户数","人数","户数","人数","按户数","按人数"};
							for(int k=0;k<14;k++){
								JSONObject head_2 =  new JSONObject();
								head_2.put("content", content_arr2[k]);
								head2.add(head_2);
							}
							head_list.add(head2);
							table.put("head", head_list);
							//合计
							List<JSONObject> body0 =  new ArrayList<JSONObject>();
							if(table_list_c!=null){
								for(int n=0;n<16;n++){
									JSONObject head_content =  new JSONObject();
									if(n==0){
										head_content.put("content", "-");
									}else{
										head_content.put("content", table_list_c.get("A"+(n+1)));
									}
									body0.add(head_content);
								}
								body_list.add(body0);
							}
				
						List<Map<String,Object>> table_list = flDao.flveLowGovern(params);
						for(int m=0;m<table_list.size();m++){
							List<JSONObject> body =  new ArrayList<JSONObject>();
							for(int n=0;n<16;n++){
								JSONObject head_content =  new JSONObject();
								if(n==0){
									head_content.put("content", (m+1));
								}else{
									head_content.put("content", table_list.get(m).get("A"+(n+1)));
								}
								body.add(head_content);
							}
							body_list.add(body);
						}
						table.put("body", body_list);
	
				data.put("table", table);
				json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("低保五保政策落实监控落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 低保五保政策落实监控落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorFiveLowImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
		log.info("查询低保五保政策落实监控落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("FiveLowImplementAnalysis", params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			JSONObject table = new JSONObject();
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
					//查饼图表数据
					List<Map<String,Object>> chart_l = flDao.flveLowGovernQu(params);
					List<Map<String,Object>> chart_r = flDao.flveLowGovernCurr(params);
					JSONObject chart_config_trend = new JSONObject();
					chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
					List<JSONObject> config_list = new ArrayList<JSONObject>();
					for(int i=0;i<chart_l.size();i++){
						JSONObject config = new JSONObject();
						config.put("name", chart_l.get(i).get("S_NAME"));
						config.put("group", chart_l.get(i).get("S_GROUP"));
						config.put("value", chart_l.get(i).get("S_VALUE"));
						config_list.add(config);
					}
					chart_config_trend.put("config", config_list);
					data.put("chart_config_trend", chart_config_trend);
					
					JSONObject chart_config_current = new JSONObject();
					chart_config_current.put("convert_method", "genChartLegendBarsConfig");
					List<JSONObject> config2_list = new ArrayList<JSONObject>();
					for(int i=0;i<chart_r.size();i++){
						JSONObject config = new JSONObject();
						config.put("name", chart_r.get(i).get("S_NAME"));
						config.put("group", chart_r.get(i).get("S_GROUP"));
						config.put("value", chart_r.get(i).get("S_VALUE"));
						config2_list.add(config);
					}
					chart_config_current.put("config", config2_list);
					data.put("chart_config_current", chart_config_current);
			//查table数据
		
						Map<String,Object> table_list_c = null;
						if(!"country".equals(level)){
							table_list_c = flDao.flveLowAnalyzeCount(params);//合计
						}
						table.put("title", "统计表");
						table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=FiveLowImplementAnalysis");
						List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
						List<JSONObject> head1 = new ArrayList<JSONObject>();
						String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan","colspan","colspan"};
						String[] content_arr = {"序号","行政区域","当前贫困户总数","应该享受低保/五保政策","已经享受低保/五保政策","政策落实比例(%)","行业数据","重合数","重合率(%)"};
						for(int j=0;j<9;j++){
							JSONObject head_1 =  new JSONObject();
							head_1.put(span_arr[j], 2);
							head_1.put("content", content_arr[j]);
							head1.add(head_1);
						}
						head_list.add(head1);
						List<JSONObject> head2 = new ArrayList<JSONObject>();
						String[] content_arr2 = {"户数","人数","户数","人数","户数","人数","按户数","按人数","户数","人数","户数","人数","按户数","按人数"};
						for(int k=0;k<14;k++){
							JSONObject head_2 =  new JSONObject();
							head_2.put("content", content_arr2[k]);
							head2.add(head_2);
						}
						head_list.add(head2);
						table.put("head", head_list);
						//合计
						List<JSONObject> body0 =  new ArrayList<JSONObject>();
						if(table_list_c!=null){
							for(int n=0;n<16;n++){
								JSONObject head_content =  new JSONObject();
								if(n==0){
									head_content.put("content", "-");
								}else{
									head_content.put("content", table_list_c.get("A"+(n+1)));
								}
								body0.add(head_content);
							}
							body_list.add(body0);
						}
					List<Map<String,Object>> table_list = flDao.flveLowAnalyze(params);
					for(int m=0;m<table_list.size();m++){
						List<JSONObject> body =  new ArrayList<JSONObject>();
						for(int n=0;n<16;n++){
							JSONObject head_content =  new JSONObject();
							if(n==0){
								head_content.put("content", (m+1));
							}else{
								head_content.put("content", table_list.get(m).get("A"+(n+1)));
							}
							body.add(head_content);
						}
						body_list.add(body);
					}
					table.put("body", body_list);
			
			data.put("table", table);
			json.put("data", data);			 
		}catch(Exception e){
			e.printStackTrace();
			log.error("低保五保政策落实监控落实分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 教育政策落实监控落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorEduImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询教育政策落实监控落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("EduImplementManagement", params);
			//查图表数据
			List<Map<String,Object>> chart_l = eduDao.queryEducationChart1(params);
			List<Map<String,Object>> chart_r = eduDao.queryEducationChart3(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = eduDao.queryEducationManageTableC(params);//合计
			}
			List<Map<String,Object>> table_list = eduDao.queryEducationManageTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", chart_l.get(i).get("S_GROUP"));
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject chart_config_current = new JSONObject();
			chart_config_current.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_current.put("config", config2_list);
			data.put("chart_config_current", chart_config_current);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=EduImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","当前贫困人口数","应该享受义务教育政策人数","已经享受义务教育政策人数","义务教育政策落实率(%)","行业重合数","重合率(%)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("教育政策落实监控落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 教育政策落实监控落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @param edu_levels 教育阶段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorEduImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String edu_levels) {
		log.info("查询教育政策落实监控落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("EduImplementAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_l = eduDao.queryEducationChart2(params);
			List<Map<String,Object>> chart_r = eduDao.queryEducationChart3(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = eduDao.queryEducationAnalysisTableC(params);//合计
			}
			List<Map<String,Object>> table_list = eduDao.queryEducationAnalysisTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", chart_l.get(i).get("S_GROUP"));
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject chart_config_current = new JSONObject();
			chart_config_current.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_current.put("config", config2_list);
			data.put("chart_config_current", chart_config_current);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=EduImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","当前贫困人口数","应该享受教育政策人数","已经享受教育政策人数","教育政策落实例(%)","行业重合数","重合率(%)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("教育政策落实监控落实分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 医疗政策落实监控落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorMedicalPolicyImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询医疗政策落实监控落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("MedicalPolicyImplementManagement", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = medicalDao.queryMedicalChart1(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = medicalDao.queryMedicalManageTableC(params);//合计
			}
			List<Map<String,Object>> table_list = medicalDao.queryMedicalManageTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalPolicyImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","当前贫困人口数","已购买医疗保险人数","医疗政策落实率(%)","行业重合数","重合率(%)"};
			for(int j=0;j<7;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<7;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<7;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("医疗政策落实监控落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 医疗政策落实监控落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorMedicalPolicyImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
		log.info("查询医疗政策落实监控落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("MedicalPolicyImplementAnalysis", params);
			//查图表数据
			List<Map<String,Object>> chart_list = medicalDao.queryMedicalChart2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = medicalDao.queryMedicalManageTableC(params);//合计
			}
			List<Map<String,Object>> table_list = medicalDao.queryMedicalManageTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalPolicyImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","当前贫困人口数","已购买医疗保险人数","医疗政策落实率(%)","行业重合数","重合率(%)"};
			for(int j=0;j<7;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<7;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<7;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("医疗政策落实监控落实分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 住房政策落实监控落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @param poor_attribute 贫困属性
	 * @return
	 */
	public String getDataMonitorHouseImplementManagement(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
		log.info("查询住房政策落实监控落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("HouseImplementManagement", params);
			//查饼图表数据
			List<Map<String,Object>> chart_l = houseDao.queryHousingChart1(params);
			List<Map<String,Object>> chart_r = houseDao.queryHousingChart2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = houseDao.queryHousingManageTableC(params);//合计
			}
			List<Map<String,Object>> table_list = houseDao.queryHousingManageTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", chart_l.get(i).get("S_GROUP"));
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_progress", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=HouseImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","rowspan"};
			String[] content_arr = {"序号","行政区域","危房改造计划户数","已完成改造户数","改造完成率(%)","重合率(%,按计划算)"};
			for(int j=0;j<6;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"建档立卡(C/D级数)","行业重合数(列入年度计划数)","建档立卡数","行业重合数","建档立卡数","行业重合数"};
			for(int k=0;k<6;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<9;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<9;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("住房政策落实监控落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 住房政策落实监控落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorHouseImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询住房政策落实监控落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("HouseImplementAnalysis", params);
			//查图表数据
			List<Map<String,Object>> chart_l = houseDao.queryHousingChart3(params);
			List<Map<String,Object>> chart_r = houseDao.queryHousingChart4(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = houseDao.queryHousingAnalyzeTableC(params);//合计
			}
			List<Map<String,Object>> table_list = houseDao.queryHousingAnalyzeTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", chart_l.get(i).get("S_GROUP"));
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject chart_config_levels = new JSONObject();
			chart_config_levels.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_levels.put("config", config2_list);
			data.put("chart_config_levels", chart_config_levels);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=HouseImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan"};
			String[] span_value_arr = {"2","2","5","5","5","5","5"};
			String[] content_arr = {"序号","行政区域","危房需改造户数","已完成改造户数","动工改造户数","未动工改造户数","危房改造完成率(%)"};
			for(int j=0;j<7;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], span_value_arr[j]);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"合计","D级","C级","B级","A级","合计","D级","C级","B级","A级","合计","D级","C级","B级","A级","合计","D级","C级","B级","A级","合计","D级","C级","B级","A级"};
			for(int k=0;k<25;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<27;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<27;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("住房政策落实监控落实分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/*********************************************************************************************************/
	/***********************************导出操作service******************************************************/
	/*********************************************************************************************************/
	private Map<String,Object> map = new HashMap<String,Object>();
	/**
	 * 导出前返回唯一导出ID
	 * @param session
	 * @param export_task_id
	 * @return
	 */
	public String beforeExport(final String export_task_id) throws Exception{
		log.info("开始导出前，产生导出ID，线程启动写入文件操作===========");
		JSONObject json = new JSONObject();
		try{
			ProgressObject p = new ProgressObject(1, 0, 0,false);
			if(loadPool.containsKey(export_task_id))
				loadPool.remove(export_task_id);
			loadPool.put(export_task_id, p);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			data.put("export_task_id", export_task_id);
			json.put("data", data);
			
			new Thread() {  
	            public void run() { 
	            	exportData(export_task_id);//将数据写入csv文件
	            };  
	        }.start();  
		}catch(Exception e){
			e.printStackTrace();
			log.error("开始导出前，产生导出ID，线程启动写入文件操作异常信息："+e.getMessage());
			json.put("code", 0);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 读取进度条
	 * @param session
	 * @param export_task_id
	 * @return
	 */
	public String loadProgress(HttpServletRequest request, String export_task_id){
		log.info("读取进度条信息=========");
		JSONObject json = new JSONObject();
		try{
			ProgressObject p = loadPool.get(export_task_id);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			Integer progressCnt = p.getProgress();
			
			if(p.isFinish()){
				data.put("progress", 100);
				data.put("stat", 2);
				
				if("queryPopType".equals(export_task_id) || "queryPopDataCount".equals(export_task_id)){
					data.put("url",  request.getContextPath()+"/antiPoverty/poorObject/downloadFile.do?filePath="+getValue(map,export_task_id+"csvFilePath")+"&fileName="+getValue(map,export_task_id+"fileName")+"&export_task_id="+export_task_id);
				}else{
					data.put("url",  request.getContextPath()+"/antiPoverty/dataMonitor/downloadCsvFile.do?csvFilePath="+getValue(map,export_task_id+"csvFilePath")+"&fileName="+getValue(map,export_task_id+"fileName"));
				}
			}else{
				data.put("progress", progressCnt);
				data.put("stat", 1);
				data.put("url",  "");
			}
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("读取进度条信息失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 导出到csv文件后下载
	 * @param response
	 * @param fileName
	 */
	public void downloadCsvFile(HttpServletResponse response, String csvFilePath, String fileName){
		log.info("导出到csv文件后下载=====");
		try {
			CSVUtils.exportFile(response, csvFilePath, fileName);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("导出失败异常信息："+e.getMessage());
		}
	}
	
	/**
	 * 进行数据分页写入csv文件
	 * @param request
	 * @param export_task_id
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String exportData(String export_task_id){
		log.info("进行数据写入csv文件操作============");
		Map<String,Object> params = (Map<String, Object>) paramsPool.get(export_task_id);
		String fileName = "";
		String[] sort_area = {"地级市","县（市）","镇（乡）","村"};
		try{
			if(params!=null){
				Map<String,Object> table_list_c = null;
				String level = (String) getValue(params,"level");
				String exportFileName = "";
				int content_size = 0;
				String[] content_arr = null;
				long table_count = 0;
				//不是分页======================================================================
				//扶贫对象管理导出
				if("PoorChangeManagement".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = pcDao.queryThePoorTrendAllTableC(params);//合计
					}
					exportFileName = "扶贫对象管理统计表";
					content_size = 19;
					content_arr = new String[]{"行政区域","年初贫困户户数","年初贫困户人数","新增贫困户户数","新增贫困户人数","终止贫困户户数","终止贫困户人数","销户贫困户户数","销户贫困户人数","拆户贫困户户数","拆户贫困户人数","并户贫困户户数","并户贫困户人数","自然增加人数","自然减少人数","当前贫困户户数","当前贫困户人数","准确率（%）按户数","准确率（%）按人数"};
				}else if("PoorChangeAnalysisAll".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = pcDao.queryThePoorTrendAllTableC(params);//合计
					}
					exportFileName = "扶贫对象分析累计贫困户统计表";
					content_size = 19;
					content_arr = new String[]{"行政区域","年初贫困户户数","年初贫困户人数","新增贫困户户数","新增贫困户人数","终止贫困户户数","终止贫困户人数","销户贫困户户数","销户贫困户人数","拆户贫困户户数","拆户贫困户人数","并户贫困户户数","并户贫困户人数","自然增加人数","自然减少人数","当前贫困户户数","当前贫困户人数","准确率（%）按户数","准确率（%）按人数"};
				}else if("PoorChangeAnalysisCur".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = pcDao.queryThePoorTrendAllTableC(params);//合计
					}
					exportFileName = "扶贫对象分析当前贫困户统计表";
					content_size = 19;
					content_arr = new String[]{"行政区域","年初贫困户户数","年初贫困户人数","新增贫困户户数","新增贫困户人数","终止贫困户户数","终止贫困户人数","销户贫困户户数","销户贫困户人数","拆户贫困户户数","拆户贫困户人数","并户贫困户户数","并户贫困户人数","自然增加人数","自然减少人数","当前贫困户户数","当前贫困户人数","准确率（%）按户数","准确率（%）按人数"};
				}else if("PoorChangeAnalysisAdd".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = pcDao.queryThePoorTrendAddTableC(params);//合计
					}
					exportFileName = "扶贫对象分析新增贫困户统计表";
					content_size = 17;
					content_arr = new String[]{"行政区域","合计户数","合计人数","'六进'之一户数","'六进'之一人数","'六进'之二户数","'六进'之二人数","'六进'之三户数","'六进'之三人数","'六进'之四户数","'六进'之四人数","'六进'之五户数","'六进'之五人数","'六进'之六户数","'六进'之六人数","其他原因户数","其他原因人数"};
				}else if("PoorChangeAnalysisEnd".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = pcDao.queryThePoorTrendEndTableC(params);//合计
					}
					exportFileName = "扶贫对象分析终止贫困户统计表";
					content_size = 19;
					content_arr = new String[]{"行政区域","合计户数","合计人数","'七不进'之一户数","'七不进'之一人数","'七不进'之二户数","'七不进'之二人数","'七不进'之三户数","'七不进'之三人数","'七不进'之四户数","'七不进'之四人数","'七不进'之五户数","'七不进'之五人数","'七不进'之六户数","'七不进'之六人数","'七不进'之七户数","'七不进'之七人数","其他原因户数","其他原因人数"};
				}else if("PoorChangeAnalysisNaturalChange".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = pcDao.queryThePoorTrendNAddTableC(params);//合计
					}
					exportFileName = "扶贫对象分析自然增减统计表";
					content_size = 21;
					content_arr = new String[]{"行政区域","自然增加合计户数","自然增加合计人数","自然增加新生儿户数","自然增加新生儿人数","自然增加嫁入户数","自然增加嫁入人数","自然增加错误户数","自然增加错误人数","自然增加其他户数","自然增加其他人数","自然减少合计户数","自然减少合计人数","自然减少新生儿户数","自然减少新生儿人数","自然减少嫁出户数","自然减少嫁出人数","自然减少错误户数","自然减少错误人数","自然减少其他户数","自然减少其他人数"};
				}
				//脱贫对象管理导出
				else if("PoorResultPlanManagement".equals(export_task_id)){
//					if(!"country".equals(level)){
//						table_list_c = prDao.queryPovertyResultsTableC(params);//合计
//					}
					exportFileName = "脱贫对象管理统计表";
					content_size = 13;
					content_arr = new String[]{"行政区域","当前贫困户户数","当前贫困户人数","当年脱贫计划数户数","当年脱贫计划数人数","监测达标数户数","监测达标数人数","达标数与计划比例(%)按户数","达标数与计划比例(%)按人数","累计脱贫完成数户数","累计脱贫完成数人数","累计脱贫完成率(%)按户数","累计脱贫完成率(%)按人数"};
				}else if("PoorResultResultAnalysis".equals(export_task_id)){
//					if(!"country".equals(level)){
//						table_list_c = prDao.queryAnalysisOfEffectsTableC(params);//合计
//					}
					exportFileName = "脱贫对象成效分析统计表";
					content_size = 5;
					content_arr = new String[]{"行政区域","累计脱贫完成数户数","累计脱贫完成数人数","累计脱贫完成率(%)按户数","累计脱贫完成率(%)按人数"};
				}else if("PoorResultNotSuccessAnalysis".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = prDao.queryNotAnalysisTableC(params);//合计
					}
					exportFileName = "未脱贫分析统计表";
					content_size = 13;
					content_arr = new String[]{"行政区域","当前贫困数户数","当前贫困数人数","综合得分(户数)＜60","综合得分(户数)60≤x＜100","综合得分(户数)100分(监控数)","人均可支配收入(有劳动能力户数)4000元以下","人均可支配收入(有劳动能力户数)4000元-7365元","人均可支配收入(有劳动能力户数)7365元以上","低保/五保政策落实未享受低保/五保政策人数","三保障指标义务教育未达标人数","三保障指标医疗保障未达标人数","三保障指标住房安全未达标户数"};
				}
				//可支配人均收入管理
				else if("AverageIncomeIncomeManagement".equals(export_task_id)){
//					if(!"country".equals(level)){
//						table_list_c = diDao.queryDisposableIncomeMTableC(params);//合计
//					}
					exportFileName = "收入管理统计表";
					content_size = 7;
					content_arr = new String[]{"行政区域","有劳动能力的贫困户数","有劳动能力户的贫困人口数","总收入(万元)","总支出(万元)","可支配收入(万元)","人均可支配收入(元/年)"};
				}else if("AverageIncomeSuccessAnalysis".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = diDao.queryExpensesAnalyzeTableC(params);//合计
					}
					exportFileName = "脱贫户分析统计表";
					content_size = 18;
					content_arr = new String[]{"行政区域","总收入(万元)","工资性收入金额","工资性收入比例","生产经营性收入金额","生产经营性收入比例","财产性收入金额","财产性收入比例","转移性收入金额","转移性收入比例","总支出(万元)","生产经营性支出金额","生产经营性支出比例","转移性支出金额","转移性支出比例","未偿还借(贷)款","可支配收入(元)","人均可支配收入(元)"};
				}else if("AverageIncomePoorAnalysis".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = diDao.queryExpensesAnalyzeTableC(params);//合计
					}
					exportFileName = "贫困户分析统计表";
					content_size = 18;
					content_arr = new String[]{"行政区域","总收入(万元)","工资性收入金额","工资性收入比例","生产经营性收入金额","生产经营性收入比例","财产性收入金额","财产性收入比例","转移性收入金额","转移性收入比例","总支出(万元)","生产经营性支出金额","生产经营性支出比例","转移性支出金额","转移性支出比例","未偿还借(贷)款","可支配收入(元)","人均可支配收入(元)"};
				}else if("AverageIncomeSuccessAnalysisTP".equals(export_task_id)){
//					table_list_c = diDao.querySpendingAnalysisC(params);//当年累计
					exportFileName = "脱贫户分析-转移性支出统计表";
					content_size = 5;
					content_arr = new String[]{"时间","个人所得税(万元)","社会保障支出(万元)","赡养支出(万元)","其他转移性支出(万元)"};
				}else if("AverageIncomePoorAnalysisTP".equals(export_task_id)){
//					table_list_c = diDao.querySpendingAnalysisC(params);//当年累计
					exportFileName = "贫困户分析-转移性支出统计表";
					content_size = 5;
					content_arr = new String[]{"时间","个人所得税(万元)","社会保障支出(万元)","赡养支出(万元)","其他转移性支出(万元)"};
				}else if("AverageIncomeSuccessAnalysisTI".equals(export_task_id)){
//					table_list_c = diDao.queryIncomeAnalysisC(params);//当年累计
					exportFileName = "脱贫户分析-转移性收入统计表";
					content_size = 9;
					content_arr = new String[]{"时间","计划生育金(万元)","低保金(万元)","五保金(万元)","养老保险金(万元)","生态补偿金(万元)","其他转移性收入(万元)","城乡居民基本医疗保险报销医疗费(万元)","医疗救助金(万元)"};
				}else if("AverageIncomePoorAnalysisTI".equals(export_task_id)){
//					table_list_c = diDao.queryIncomeAnalysisC(params);//当年累计
					exportFileName = "贫困户分析-转移性收入统计表";
					content_size = 9;
					content_arr = new String[]{"时间","计划生育金(万元)","低保金(万元)","五保金(万元)","养老保险金(万元)","生态补偿金(万元)","其他转移性收入(万元)","城乡居民基本医疗保险报销医疗费(万元)","医疗救助金(万元)"};
				}
				//低保五保政策落实
				else if("FiveLowImplementManagement".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = flDao.flveLowGovernCount(params);//合计
					}
					exportFileName = "低保五保政策落实管理统计表";
					content_size = 15;
					content_arr = new String[]{"行政区域","当前贫困户总数户数","当前贫困户总数人数","应该享受低保/五保政策户数","应该享受低保/五保政策人数","已经享受低保/五保政策户数","已经享受低保/五保政策人数","政策落实比例(%)按户数","政策落实比例(%)按人数","行业数据户数","行业数据人数","重合数户数","重合数人数","重合率(%)按户数","重合率(%)按人数"};
				}else if("FiveLowImplementAnalysis".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = flDao.flveLowAnalyzeCount(params);//合计
					}
					exportFileName = "低保五保政策落实分析统计表";
					content_size = 15;
					content_arr = new String[]{"行政区域","当前贫困户总数户数","当前贫困户总数人数","应该享受低保/五保政策户数","应该享受低保/五保政策人数","已经享受低保/五保政策户数","已经享受低保/五保政策人数","政策落实比例(%)按户数","政策落实比例(%)按人数","行业数据户数","行业数据人数","重合数户数","重合数人数","重合率(%)按户数","重合率(%)按人数"};
				}
				//教育政策落实
				else if("EduImplementManagement".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = eduDao.queryEducationManageTableC(params);//合计
					}
					exportFileName = "教育政策落实管理统计表";
					content_size = 7;
					content_arr = new String[]{"行政区域","当前贫困人口数","应该享受义务教育政策人数","已经享受义务教育政策人数","义务教育政策落实例(%)","行业重合数","重合率(%)"};
				}else if("EduImplementAnalysis".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = eduDao.queryEducationAnalysisTableC(params);//合计
					}
					exportFileName = "教育政策落实分析统计表";
					content_size = 7;
					content_arr = new String[]{"行政区域","当前贫困人口数","应该享受教育政策人数","已经享受教育政策人数","教育政策落实例(%)","行业重合数","重合率(%)"};
				}
				//医疗政策落实
				else if("MedicalPolicyImplementManagement".equals(export_task_id)){
//					if(!"country".equals(level)){
//						table_list_c = medicalDao.queryMedicalManageTableC(params);//合计
//					}
					exportFileName = "医疗政策落实管理统计表";
					content_size = 6;
					content_arr = new String[]{"行政区域","当前贫困人口数","已购买医疗保险人数","医疗政策落实率(%)","行业重合数","重合率(%)"};
				}else if("MedicalPolicyImplementAnalysis".equals(export_task_id)){
//					if(!"country".equals(level)){
//						table_list_c = medicalDao.queryMedicalManageTableC(params);//合计
//					}
					exportFileName = "医疗政策落实分析统计表";
					content_size = 6;
					content_arr = new String[]{"行政区域","当前贫困人口数","已购买医疗保险人数","医疗政策落实率(%)","行业重合数","重合率(%)"};
				}
				//住房政策落实
				else if("HouseImplementManagement".equals(export_task_id)){
//					if(!"country".equals(level)){
//						table_list_c = houseDao.queryHousingManageTableC(params);//合计
//					}
					exportFileName = "住房政策落实管理统计表";
					content_size = 8;
					content_arr = new String[]{"行政区域","危房改造计划户数建档立卡(C/D级数)","危房改造计划户数行业重合数(列入年度计划数)","已完成改造户数建档立卡数","已完成改造户数行业重合数","改造完成率(%)建档立卡数","改造完成率(%)行业重合数","重合率(%，按计划算)"};
				}else if("HouseImplementAnalysis".equals(export_task_id)){
//					if(!"country".equals(level)){
//						table_list_c = houseDao.queryHousingAnalyzeTableC(params);//合计
//					}
					exportFileName = "住房政策落实分析统计表";
					content_size = 26;
					content_arr = new String[]{"行政区域","危房需改造户数合计","危房需改造户数D级","危房需改造户数C级","危房需改造户数B级","危房需改造户数A级","已完成改造户数合计","已完成改造户数D级","已完成改造户数C级","已完成改造户数B级","已完成改造户数A级","动工改造户数合计","动工改造户数D级","动工改造户数C级","动工改造户数B级","动工改造户数A级","未动工改造户数合计","未动工改造户数D级","未动工改造户数C级","未动工改造户数B级","未动工改造户数A级","危房改造完成率(%)合计","危房改造完成率(%)D级","危房改造完成率(%)C级","危房改造完成率(%)B级","危房改造完成率(%)A级"};
				}else if("RoadImplementManagement".equals(export_task_id)){
				exportFileName = "道路硬底化监控落实管理统计表";
				content_size = 10;
				content_arr = new String[]{"行政区域","村数(个)","完全硬底化村数(个)","完全硬底化村比例(%)","200人以上自然村到行政村未通沥青(水泥)村数(个)","年初行政村到乡镇未通沥青(水泥)路里程(公里)","年初自然村到行政村未通沥青(水泥)路里程(公里)","年初帮扶村未通里程数合计(公里)","改造完成里程(公里)","改造完成率(%)"};
				}else if("RoadImplementAnalysis".equals(export_task_id)){
				exportFileName = "道路硬底化监控落实分析统计表";
				content_size = 10;
				content_arr = new String[]{"行政区域","村数(个)","完全硬底化村数(个)","完全硬底化村比例(%)","200人以上自然村到行政村未通沥青(水泥)村数(个)","年初行政村到乡镇未通沥青(水泥)路里程(公里)","年初自然村到行政村未通沥青(水泥)路里程(公里)","年初帮扶村未通里程数合计(公里)","改造完成里程(公里)","改造完成率(%)"};
				}
				else if("RoadManagement".equals(export_task_id)){
				exportFileName = "道路硬底化监控分析明细表";
				content_size = 11;
				content_arr = new String[]{"市","县","镇","村","帮扶村未通沥青(水泥)路里程数(公里)","200人以上自然村到行政村未通沥青(水泥)村数(个)","年初行政村到乡镇未通沥青(水泥)路里程(公里)","年初自然村到行政村未通沥青(水泥)路里程(公里)","年初帮扶村未通里程数合计(公里)","改造完成里程(公里)","改造完成率(%)"};
				}
				else if("RoadOrder".equals(export_task_id)){
				exportFileName = getValue(params,"order_table_title")+"表";
				content_size = 2;
				content_arr = new String[]{"行政区域","指标"};
				}
				else if("RoadOrderArea".equals(export_task_id)){
				exportFileName = "道路硬底化落实排序区域数据表";
				content_size = 10;
				content_arr = new String[]{"行政区域","村数(个)","完全硬底化村数(个)","完全硬底化村比例(%)","200人以上自然村到行政村未通沥青(水泥)村数(个)","年初行政村到乡镇未通沥青(水泥)路里程(公里)","年初自然村到行政村未通沥青(水泥)路里程(公里)","年初帮扶村未通里程数合计(公里)","改造完成里程(公里)","改造完成率(%)"};
				}else if("WaterImplementManagement".equals(export_task_id)){
					exportFileName = "安全饮水落实管理统计表";
					content_size = 8;
					content_arr = new String[]{"行政区域","村数(个)","全实现安全饮水的村数(个)","全实现安全饮水村比例(%)","未全实现安全饮水的村数(个)","年初未实现饮水安全户数(户)","完成改造户数(户)","改造完成率(%)"};
				}else if("WaterImplementAnalysis".equals(export_task_id)){
					exportFileName = "安全饮水落实分析统计表";
					content_size = 8;
					content_arr = new String[]{"行政区域","村数(个)","全实现安全饮水的村数(个)","全实现安全饮水村比例(%)","未全实现安全饮水的村数(个)","年初未实现饮水安全户数(户)","完成改造户数(户)","改造完成率(%)"};
				}
				else if("WaterManagement".equals(export_task_id)){
					exportFileName = "安全饮水分析明细表";
					content_size = 8;
					content_arr = new String[]{"市","县","镇","村","是否全实现安全饮水","年初未实现饮水安全户数(户)","实现改造户数(户)","改造完成率(%)"};
				}
				else if("WaterOrder".equals(export_task_id)){
					exportFileName = getValue(params,"order_table_title")+"表";
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}
				else if("WaterOrderArea".equals(export_task_id)){
					exportFileName = "安全饮水排序区域数据表";
					content_size = 8;
					content_arr = new String[]{"行政区域","村数(个)","全实现安全饮水的村数(个)","全实现安全饮水村比例(%)","未全实现安全饮水的村数(个)","年初未实现饮水安全户数(户)","完成改造户数(户)","改造完成率(%)"};
					
			}else if("ElectricityImplementManagement".equals(export_task_id)){
				exportFileName = "生活用电落实管理统计表";
				content_size = 7;
				content_arr = new String[]{"行政区域","村数(个)","全部通生活用电的村数(个)","全通生活用电村比例(%)","年初未全部通生活用电的村数(个)","完成改造村数(个)","改造完成率(%)"};

			}else if("ElectricityImplementAnalysis".equals(export_task_id)){
				exportFileName = "生活用电落实分析统计表";
				content_size = 7;
				content_arr = new String[]{"行政区域","村数(个)","全部通生活用电的村数(个)","全通生活用电村比例(%)","年初未全部通生活用电的村数(个)","完成改造村数(个)","改造完成率(%)"};
			}
			else if("ElectricityManagement".equals(export_task_id)){
				exportFileName = "生活用电分析明细表";
				content_size = 7;
				content_arr = new String[]{"市","县","镇","村","是否全通生活用电","年初是否全通生活用电","是否完成改造"};
			}
			else if("ElectricityOrder".equals(export_task_id)){
				exportFileName = getValue(params,"order_table_title")+"表";
				content_size = 2;
				content_arr = new String[]{"行政区域","指标"};
			}
			else if("ElectricityOrderArea".equals(export_task_id)){
				exportFileName = "生活用电排序区域数据表";
				content_size = 7;
				content_arr = new String[]{"行政区域","村数(个)","全部通生活用电的村数(个)","全部通生活用电的村比例(%)","年初未全部通生活用电的村数(个)","完成改造村数(个)","改造完成率(%)"};
			}else if("MedicalFacilityImplementManagement".equals(export_task_id)){
				exportFileName = "医疗设施落实管理统计表";
				content_size = 10;
				content_arr = new String[]{"行政区域","村数(个)","有卫生室的村数(个)","有卫生站的村比例(%)","无卫生室的村数(个)","新建卫生站的村数(个)","有执业医师的村数(个)","有执业医师的村比例(%)","未有行政村执业(助理)医师的村数(个)","新增执业医师的村数(个)"};
			}else if("MedicalFacilityImplementAnalysis".equals(export_task_id)){
				exportFileName = "医疗设施落实分析统计表";
				content_size = 10;
				content_arr = new String[]{"行政区域","村数(个)","有卫生室的村数(个)","有卫生站的村比例(%)","无卫生室的村数(个)","新建卫生站的村数(个)","有执业医师的村数(个)","有执业医师的村比例(%)","未有行政村执业(助理)医师的村数(个)","新增执业医师的村数(个)"};
			}
			else if("MedicalFacilityManagement".equals(export_task_id)){
				exportFileName = "医疗设施分析明细表";
				content_size = 8;
				content_arr = new String[]{"市","县","镇","村","卫生室个数(个)","行政村执业(助理)医师(个)","新建卫生站个数(个)","新增执业医师个数(个)"};
			}
			else if("MedicalFacilityOrder".equals(export_task_id)){
				exportFileName = getValue(params,"order_table_title")+"表";
				content_size = 2;
				content_arr = new String[]{"行政区域","指标"};
			}else if("MedicalFacilityOrderArea".equals(export_task_id)){
				exportFileName = "医疗设施排序区域数据表";
				content_size =10;
				content_arr = new String[]{"行政区域","村数(个)","有卫生室的村数(个)","有卫生站的村比例","无卫生站的村数(个)","新建卫生站的村数(个)","有执业医师的村数(个)","有执业医师的村比例(%)","未有行政村执业(助理)医师的村数(个)","新增执业医师的村数(个)"};
			}else if("BroadcastImplementManagement".equals(export_task_id)){
				exportFileName = "广播电视落实管理统计表";
				content_size = 7;
				content_arr = new String[]{"行政区域","村数(个)","全通广播电视的村数(个)","全通广播电视村比例(%)","年初未通广播电视的村数(个)","完成改造村数(个)","改造完成率(%)"};
			}else if("BroadcastImplementAnalysis".equals(export_task_id)){
				exportFileName = "广播电视落实分析统计表";
				content_size = 7;
				content_arr = new String[]{"行政区域","村数(个)","全通广播电视的村数(个)","全通广播电视村比例(%)","年初未通广播电视的村数(个)","完成改造村数(个)","改造完成率(%)"};
			}
			else if("BroadcastManagement".equals(export_task_id)){
				exportFileName = "广播电视分析明细表";
				content_size = 7;
				content_arr = new String[]{"市","县","镇","村","是否全通广播电视","未通广播电视的户数(户)","是否完成改造"};
			}
			else if("BroadcastOrder".equals(export_task_id)){
				exportFileName = getValue(params,"order_table_title")+"表";
				content_size = 2;
				content_arr = new String[]{"行政区域","指标"};
			}
			else if("BroadcastOrderArea".equals(export_task_id)){
				exportFileName = "广播电视排序区域数据表";
				content_size =7;
				content_arr = new String[]{"行政区域","村数(个)","全通广播电视的村数(个))","全通广播电视的比例(%)","年初未全通广播电视的村数(个)","完成改造村数(个)","改造完成率(%)"};
			}else if("NetImplementManagement".equals(export_task_id)){
				exportFileName = "网络覆盖落实管理统计表";
				content_size = 7;
				content_arr = new String[]{"行政区域","村数(个)","网络全覆盖的村数(个)","网络全覆盖的村比例(%)","年初网络未全覆盖的村数(个)","完成改造村数(个)","改造完成率(%)"};
			}else if("NetImplementAnalysis".equals(export_task_id)){
				exportFileName = "网络覆盖落实分析统计表";
				content_size = 7;
				content_arr = new String[]{"行政区域","村数(个)","网络全覆盖的村数(个)","网络全覆盖的村比例(%)","年初网络未全覆盖的村数(个)","完成改造村数(个)","改造完成率(%)"};
			}
			else if("NetManagement".equals(export_task_id)){
				exportFileName = "网络覆盖分析明细表";
				content_size = 7;
				content_arr = new String[]{"市","县","镇","村","网络是否全覆盖","年初是否全覆盖 ","是否完成改造"};
			}
			else if("NetdOrder".equals(export_task_id)){
				exportFileName = getValue(params,"order_table_title")+"表";
				content_size = 2;
				content_arr = new String[]{"行政区域","指标"};
			}
			else if("NetOrderArea".equals(export_task_id)){
				exportFileName = "网络覆盖排序区域数据表";
				content_size =7;
				content_arr = new String[]{"行政区域","村数(个)","全覆盖的村数(个)","全覆盖的村比例(%)","年初未全覆盖的村数(个)","完成改造村数(个)","改造完成率(%)"};
			}else if("AlarmedVisitExceptionMonitor".equals(export_task_id)){
				exportFileName = "走访异常监控数据表";
				content_size =5;
				content_arr = new String[]{"行政区域","累计贫困户数","帮扶责任人数","一年内未被走访的累计贫困户数","比例(%)"};
			}else if("AlarmedVisitdOrder".equals(export_task_id)){
				exportFileName = getValue(params,"order_table_title")+"表";
				content_size =2;
				content_arr = new String[]{"行政区域","指标"};
			}else if("AlarmedVisitOrderArea".equals(export_task_id)){
				exportFileName = "走访异常排序区域数据表";
				content_size =5;
				content_arr = new String[]{"行政区域","累计贫困户数","帮扶责任人数","一年内未被走访的累计贫困户数","比例(%)"};
			}else if("ProjectOrder".equals(export_task_id)){
				String[] taxis_indexes = {"累计项目总数","累计资金总数","累计村项目数","累计村项目资金","累计户项目数 ","累计户项目资金","年度项目总数","年度项目资金","年度到村项目数","年度到村项目资金","年度户项目数 ","年度户项目资金"};
				exportFileName = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
				//exportFileName = "项目监控项目排序数据表";
				content_size =2;
				content_arr = new String[]{"行政区域","指标"};
			}
//到户
			else if("ManagementRoad".equals(export_task_id)){
				exportFileName = "村道硬底化明细表";
				content_size =10;
				content_arr = new String[]{"市","县","镇","村","项目名称","项目子类","开始日期","里程(公里)","投入资金(万元)","项目状态"};
			}else if("ManagementWater".equals(export_task_id)){
				exportFileName = "饮水工程明细表";
				content_size =10;
				content_arr = new String[]{"市","县","镇","村","项目名称","开始日期","受益户数","受益人数","投入资金(万元)","项目状态"};
			}else if("ManagementRecreationSport".equals(export_task_id)){
				exportFileName = "文体设施明细表";
				content_size =9;
				content_arr = new String[]{"市","县","镇","村","项目名称","项目子类","开始日期","投入资金(万元)","项目状态"};
			}else if("ManagementHygiene".equals(export_task_id)){
				exportFileName = "卫生设施明细表";
				content_size =9;
				content_arr = new String[]{"市","县","镇","村","项目名称","项目子类","开始日期","投入资金(万元)","项目状态"};
			}else if("ManagementLamp".equals(export_task_id)){
				exportFileName = "路灯安装明细表";
				content_size =9;
				content_arr = new String[]{"市","县","镇","村","项目名称","开始日期","路灯数(盏)","投入资金(万元)","项目状态"};
			}else if("ManagementFarm".equals(export_task_id)){
				exportFileName = "农田水利明细表";
				content_size =9;
				content_arr = new String[]{"市","县","镇","村","项目名称","项目子类","开始日期","投入资金(万元)","项目状态"};
			}else if("ManagementFacility".equals(export_task_id)){
				exportFileName = "公共设施明细表";
				content_size =8;
				content_arr = new String[]{"市","县","镇","村","项目名称","开始日期","投入资金(万元)","项目状态"};
			}else if("ManagementEconomy".equals(export_task_id)){
				exportFileName = "集体经济明细表";
				content_size =9;
				content_arr = new String[]{"市","县","镇","村","项目名称","开始日期","投入资金(万元)","项目收益(万元)","项目状态"};
			}else if("ManagementEdu".equals(export_task_id)){
				exportFileName = "教育教学明细表";
				content_size =8;
				content_arr = new String[]{"市","县","镇","村","项目名称","开始日期","投入资金(万元)","项目状态"};
			
			}else if("AnalysisRoad".equals(export_task_id)){
				exportFileName = "村道硬底化统计表";
				content_size =4;
				content_arr = new String[]{"行政区域","项目数(个)","里程(公里)","投入资金(万元)"};
			}else if("AnalysisWater".equals(export_task_id)){
				exportFileName = "饮水工程统计表";
				content_size =5;
				content_arr = new String[]{"行政区域","项目数(个)","受益户数","受益人数","投入资金(万元)"};
			}else if("AnalysisSport".equals(export_task_id)){
				exportFileName = "文体设施统计表";
				content_size =7;
				content_arr = new String[]{"行政区域","项目数(个)/合计","项目数(个)/文化室","项目数(个)/篮球场","项目数(个)/休闲运动设施","项目数(个)/其他","投入资金(万元)"};
			}else if("AnalysisHygiene".equals(export_task_id)){
				exportFileName = "卫生设施统计表";
				content_size =7;
				content_arr = new String[]{"行政区域","项目数(个)/合计","项目数(个)/卫生站","项目数(个)/公厕","项目数(个)/垃圾池","项目数(个)/其他","投入资金(万元)"};
			}else if("AnalysisLamp".equals(export_task_id)){
				exportFileName = "路灯安装统计表";
				content_size =4;
				content_arr = new String[]{"行政区域","项目数(个)","路灯数(盏)","投入资金(万元)"};
			}else if("AnalysisFarm".equals(export_task_id)){
				exportFileName = "农田水利统计表";
				content_size =9;
				content_arr = new String[]{"行政区域","项目数(个)/合计","项目数(个)/三面光水渠","项目数(个)/机耕路","项目数(个)/机耕桥","项目数(个)/河提(坝)","项目数(个)/排灌站","项目数(个)/其他","投入资金(万元)"};
			}else if("AnalysisFacility".equals(export_task_id)){
				exportFileName = "公共设施统计表";
				content_size =3;
				content_arr = new String[]{"行政区域","项目数(个)","投入资金(万元)"};
			}else if("AnalysisEconomy".equals(export_task_id)){
				exportFileName = "集体经济统计表";
				content_size =4;
				content_arr = new String[]{"行政区域","项目数(个)","投入资金(万元)","项目收益(万元)"};
			}else if("AnalysisEdu".equals(export_task_id)){
				exportFileName = "教育教学统计表";
				content_size =3;
				content_arr = new String[]{"行政区域","项目数(个)","投入资金(万元)"};
			}else if("AlarmedVisit".equals(export_task_id)){
				exportFileName = "贫困户走访异常明细表";
				content_size =11;
				content_arr = new String[]{"市","县","镇","村","户码","户主姓名","驻村队长","帮扶责任人","当年走访次数","累计走访次数","操作"};
			}
				
			else if("ProjectOrderArea".equals(export_task_id)){
				exportFileName = "项目监控项目排序区域数据表";
				content_size = 43;
				content_arr = new String[]{"行政区域","总计/数量","总计/金额","户项目/合计/数量","户项目/合计/金额","户项目/产业扶贫/数量","户项目/产业扶贫/金额","户项目/金融扶贫/数量","户项目/金融扶贫/金额","户项目/住房改造/数量","户项目/住房改造/金额","户项目/资产扶贫/数量","户项目/资产扶贫/金额","户项目/慰问扶贫/数量","户项目/慰问扶贫/金额","户项目/就业扶贫/数量","户项目/就业扶贫/金额","户项目/技能培训/数量","户项目/技能培训/金额","户项目/教育扶贫/数量","户项目/教育扶贫/金额","户项目/政策补贴+社会保障/数量","户项目/政策补贴+社会保障/金额","村项目/合计/数量","村项目/合计/金额","村项目/村道建设/数量","村项目/村道建设/金额","村项目/饮水工程/数量","村项目/饮水工程/金额","村项目/文体设施/数量","村项目/文体设施/金额","村项目/卫生设施/数量","村项目/卫生设施/金额","村项目/路灯安装/数量","村项目/路灯安装/金额","村项目/农田水利/数量","村项目/农田水利/金额","村项目/公共设施/数量","村项目/公共设施/金额","村项目/集体经济/数量","村项目/集体经济/金额","村项目/教育教学/数量","村项目/教育教学/金额"};
			}else if("ProjectFamilyIndustry".equals(export_task_id)){
				exportFileName = "产业扶贫统计表";
				content_size =6;
				content_arr = new String[]{"行政区域","项目数（个）","投入金额（万元）","项目收益（万元）","项目受益/户数","项目受益/人数"};
			}else if("ProjectFamilyFinance".equals(export_task_id)){
				exportFileName = "金融扶贫统计表";
				content_size =5;
				content_arr = new String[]{"行政区域","贷款户数","贷款项目数（个）","贷款金融（万元）","贷款利息（万元）"};
			}else if("ProjectFamilyHouse".equals(export_task_id)){
				exportFileName = "住房改造统计表";
				content_size =6;
				content_arr = new String[]{"行政区域","计划改造户数","完成改造户数","完成比例（%）","项目数（个）","改造投入金额（万元）"};
			}else if("ProjectFamilyProperty".equals(export_task_id)){
				exportFileName = "资产扶贫统计表";
				content_size =5;
				content_arr = new String[]{"行政区域","参与户数","项目数（个）","投入金额（万元）","项目收益（万元）"};
			}else if("ProjectFamilyVisit".equals(export_task_id)){
				exportFileName = "慰问扶贫统计表";
				content_size =4;
				content_arr = new String[] {"行政区域","慰问户数","项目数（个）","慰问金额（万元）"};
			}else if("ProjectFamilyEmployment".equals(export_task_id)){
				exportFileName = "就业扶贫统计表";
				content_size =6;
				content_arr = new String[]{"行政区域","项目数（个）","参与户数","就业人数","就业收益（万元）","年人均就业收入（万元）"};
			}else if("ProjectFamilySkill".equals(export_task_id)){
				exportFileName = "技能培训统计表";
				content_size =6;
				content_arr = new String[]{"行政区域","在家务农人数","项目数（个）","参与户数","培训人数","投入资金（万元）"};
			}else if("ProjectFamilyEdu".equals(export_task_id)){
				exportFileName = "教育扶贫统计表";
				content_size =5;
				content_arr = new String[]{"行政区域","项目数（个）","参与户数","教育扶贫人数","投入资金（万元）"};
			
			}else if("THEFAMIndustryDetail".equals(export_task_id)){
				exportFileName = "产业扶贫明细表";
				content_size =14;
				content_arr = new String[]{"市","县","镇","村","户码","户主姓名","贫困属性","劳动力属性","产业名称","项目开始时期","项目规模","投入资金（万元）",
						"项目收益（万元）","项目状态"};			
			}else if("THEFAMFinanceDetail".equals(export_task_id)){
				exportFileName = "金融扶贫明细表";
				content_size =12;
				content_arr = new String[]{"市","县","镇","村","户码","户主姓名","贫困属性","贷款人姓名","贷款开始时间","贷款金额（万元）","贷款利息（万元）",
						"项目状态"};			}else if("THEFAMHouseDetail".equals(export_task_id)){
				exportFileName = "住房改造明细表";
				content_size =11;
				content_arr = new String[]{"市","县","镇","村","户码","户主姓名","贫困属性","改造开始时间","是否完工","改造金额（万元）","项目状态"};			
				}else if("THEFAMPropertyDetail".equals(export_task_id)){
				exportFileName = "资产扶贫明细表";
				content_size =12;
				content_arr = new String[]{"市","县","镇","村","户码","户主姓名","贫困属性","项目开始时间","投入资金（万元）",
						"项目收益（万元）","项目状态","操作"};			
			}else if("THEFAMVisitDetail".equals(export_task_id)){
				exportFileName = "慰问扶贫明细表";
				content_size =11;
				content_arr = new 	String[]{"市","县","镇","村","户码","户主姓名","贫困属性","慰问时间","慰问金额（元）",
					"项目状态","操作"};			
			}else if("THEFAMEmploymentDetail".equals(export_task_id)){
				exportFileName = "就业扶贫明细表";
				content_size =14;
				content_arr = new String[]{"年份","市","县","镇","村","户码","户主姓名","贫困属性","就业人员姓名","就业地点","临时工/稳定工","年就业收益（万元）",
						"项目状态","操作"};			
			}else if("THEFAMSkillDetail".equals(export_task_id)){
				exportFileName = "技能培训明细表";
				content_size = 13;
				content_arr = new String[]{"市","县","镇","村","户码","户主姓名","贫困属性","培训人员姓名","培训内容","培训时间","投入资金（万元）",
						"项目状态","操作"};			
			}else if("THEFAMEduDetail".equals(export_task_id)){
				exportFileName = "教育扶贫明细表";
				content_size =13;
				content_arr = new String[]{"市","县","镇","村","户码","户主姓名","贫困属性","学生姓名","教育阶段","扶贫时间","投入资金（万元）",
						"项目状态","操作"};
			}else if("FundAnalysisManagementDetail".equals(export_task_id)){
				exportFileName = "资金分析统计明细表";
				content_size =30;
				content_arr = new String[]{"市","县","镇","村","户","人","项目类型","项目名称","项目预计投入金额"
			            ,"项目状态","项目开始时间","项目结束时间","项目实际投入金额","帮扶单位自筹资金",
			            "帮扶市/地级市财政资金","帮扶市/区级和镇级财政资金","帮扶市/社会资金",
			            "中央资金/中央财政资金","中央资金/中央行业资金",
			            "省资金/省级财政资金","省资金/省级行业资金","省资金/社会资金",
			            "被帮扶市资金/地级市财政资金","被帮扶市资金/县级和镇级财政资金","被帮扶市资金/地级市行业资金","被帮扶市资金/县级和镇级行业资金","被帮扶市资金/社会资金",
			            "个人自筹","医疗报销","项目收益"};
			}else if("FundManagement".equals(export_task_id)){
				exportFileName = "资金管理统计表";
				content_size =20;
				content_arr = new String[]{"行政区域","年度项目资金总额","帮扶单位自筹资金",
						"帮扶市/合计","帮扶市/地级市财政资金","帮扶市/区级和镇级财政资金","帮扶市/社会资金",
						"中央/合计","中央/中央财政资金","中央/中央行业资金",
						"省级/合计","省级/省级财政资金","省级/省级行业资金","省级/社会资金",
						"被帮扶市/合计","被帮扶市/地级市财政资金","被帮扶市/县级和镇级财政资金","被帮扶市/地级市行业资金","被帮扶市/县级和镇级行业资金","被帮扶市/社会资金"};
			}else if("FundAnalysisManagement".equals(export_task_id)){
				exportFileName = "资金分析统计表";
				content_size =20;
				content_arr = new String[]{"行政区域","年度项目资金总额","帮扶单位自筹资金",
						"帮扶市/合计","帮扶市/地级市财政资金","帮扶市/区级和镇级财政资金","帮扶市/社会资金",
						"中央/合计","中央/中央财政资金","中央/中央行业资金",
						"省级/合计","省级/省级财政资金","省级/省级行业资金","省级/社会资金",
						"被帮扶市/合计","被帮扶市/地级市财政资金","被帮扶市/县级和镇级财政资金","被帮扶市/地级市行业资金","被帮扶市/县级和镇级行业资金","被帮扶市/社会资金"};
			}else if("ExampleManagementww".equals(export_task_id)){
				String month = (String) getValue(params,"month");
				String state = (String) getValue(params,"state");
				String att="";
				String btt="";
				int size=0;
				
				if("done".equals(state)){
					att="自然村达标个数";
					btt="示范村达标个数";
					exportFileName = "示范村"+month+"已完成统计表";
				}else if("doing".equals(state)){
					att="自然村建设中个数";
					btt="示范村建设中个数";
					exportFileName = "示范村"+month+"建设中统计表";
				}else if("unstart".equals(state)){
					att="自然村未启动个数";
					btt="示范村未启动个数";
					exportFileName = "示范村"+month+"未启动统计表";
				}
				if("country".equals(level)){
					content_size=13;
					content_arr = new String[]{"行政区域","相对贫困村(个)","20户以上自然村(个)","编制整治规划","三清三拆三整治","村道巷道硬化","垃圾处理","污水处理","集中供水","住房建设","公共服务设施","成立村民理事会",att};
				}else{
					content_size=14;
					content_arr = new String[]{"行政区域","相对贫困村(个)","20户以上自然村(个)","编制整治规划","三清三拆三整治","村道巷道硬化","垃圾处理","污水处理","集中供水","住房建设","公共服务设施","成立村民理事会",att,btt};
				}
			}else if("ExampleManagementplees".equals(export_task_id)){
				String month = (String) getValue(params,"month");
				String state = (String) getValue(params,"state");
				exportFileName = "示范村"+month+"整治达标明细表";
				content_size =16;
				content_arr = new String[]{"市","县","镇","村","20户以上自然村(个)","编制整治规划","三清三拆三整治","村道巷道硬化","垃圾处理","污水处理","集中供水","住房建设","公共服务设施","成立村民理事会","自然村达标标准","示范村达标标准"};
				
			}else if("FundAnalysisSort".equals(export_task_id)){
				exportFileName = getValue(params,"order_table_title")+"表";
				content_size = 2;
				content_arr = new String[]{"行政区域","指标"};
			}else if("FundAnalysisSortTth".equals(export_task_id)){
				exportFileName = getValue(params,"order_table_title")+"表";
				content_size = 2;
				content_arr = new String[]{"行政区域","文件号","指标"};
			}else if("FundAnalysisSortDetail".equals(export_task_id)){
				exportFileName = "资金监控排序区域数据表";
				content_size =20;
				content_arr = new String[]{"行政区域","年度项目资金总额","帮扶单位自筹资金",
						"帮扶市/合计","帮扶市/地级市财政资金","帮扶市/区级和镇级财政资金","帮扶市/社会资金",
						"中央/合计","中央/中央财政资金","中央/中央行业资金",
						"省级/合计","省级/省级财政资金","省级/省级行业资金","省级/社会资金",
						"被帮扶市/合计","被帮扶市/地级市财政资金","被帮扶市/县级和镇级财政资金","被帮扶市/地级市行业资金","被帮扶市/县级和镇级行业资金","被帮扶市/社会资金"};
			}else if("ExampleManagementple".equals(export_task_id)){
				exportFileName = "资金监控排序区域数据表";
				content_size =20;
				content_arr = new String[]{"行政区域","年度项目资金总额","帮扶单位自筹资金",
						"帮扶市/合计","帮扶市/地级市财政资金","帮扶市/区级和镇级财政资金","帮扶市/社会资金",
						"中央/合计","中央/中央财政资金","中央/中央行业资金",
						"省级/合计","省级/省级财政资金","省级/省级行业资金","省级/社会资金",
						"被帮扶市/合计","被帮扶市/地级市财政资金","被帮扶市/县级和镇级财政资金","被帮扶市/地级市行业资金","被帮扶市/县级和镇级行业资金","被帮扶市/社会资金"};
			}

				//排序区域导出
			else if("AlarmedPoorOrderArea".equals(export_task_id)){
				exportFileName = "贫困识别监控异常排序区域数据表";
				content_size = 17;
				content_arr = new String[]{"行政区域","合计(条)/比对项","合计(条)/异常项","身份信息不匹配/比对项","身份信息不匹配/异常项","低保五保不匹配/比对项","低保五保不匹配/异常项","残疾信息不匹配/比对项","残疾信息不匹配/异常项","名下有房/比对项","名下有房/异常项","名下有车/比对项","名下有车/异常项","有工商注册信息/比对项","有工商注册信息/异常项","享受财政供养/比对项","享受财政供养/异常项"};
			}else if("AlarmedRecordsOrderArea".equals(export_task_id)){
				exportFileName = "建档立卡异常记录监控异常监控区域数据表";
				content_size = 11;
				content_arr = new String[]{"行政区域","合计(条)/监测记录数","合计(条)/异常记录数","户主身份信息错漏/监测记录数","户主身份信息错漏/异常记录数","成员身份信息错漏/监测记录数","成员身份信息错漏/异常记录数","残疾人信息错漏/监测记录数","残疾人信息错漏/异常记录数","重复身份信息记录/监测记录数","重复身份信息记录/异常记录数"};
			}else if("AlarmedMoneyOrderArea".equals(export_task_id)){
				exportFileName = "帮扶资金监控资金排序区域数据表";
				content_size = 4;
				content_arr = new String[]{"行政区域","相对贫困村数(个)","帮扶资金小于＜30万的相对贫困村数(个)","比例(%)"};
			}
				else if("ChangeOrderArea".equals(export_task_id)){
					exportFileName = "扶贫对象监控变动分析区域数据表";
					content_size = 19;
					content_arr = new String[]{"行政区域","年初贫困户总数户数","年初贫困户总数人数","新增贫困户总数户数","新增贫困户总数人数","终止贫困户总数户数","终止贫困户总数人数","销户贫困户总数户数","销户贫困户总数人数","拆户贫困户总数户数","拆户贫困户总数人数","并户贫困户总数户数","并户贫困户总数人数","自然增加人数","自然减少人数","贫困户总数户数","贫困户总数人数","准确率（%）按户数","准确率（%）按人数"};
				}else if("ResultOrderArea".equals(export_task_id)){
					exportFileName = "脱贫对象监控成效排序区域数据表";
					content_size = 13;
					content_arr = new String[]{"行政区域","累计贫困户户数","累计贫困户人数","当年脱贫计划数户数","当年脱贫计划数人数","监测达标数户数","监测达标数人数","达标数与计划比例(%)按户数","达标数与计划比例(%)按人数","累计脱贫完成数户数","累计脱贫完成数人数","累计脱贫完成率(%)按户数","累计脱贫完成率(%)按人数"};
				}else if("IncomeOrderArea".equals(export_task_id)){
					exportFileName = "人均可支配收入监控收入排序区域数据表";
					content_size = 20;
					content_arr = new String[]{"行政区域","工资性收入(万元)","生成经营性收入(万元)","财产性收入(万元)","转移性收入计划生育金(万元)","转移性收入低保金(万元)","转移性收入五保金(万元)","转移性收入养老保险金(万元)","转移性收入生态补偿金(万元)","转移性收入其他转移性收入(万元)","转移性收入城乡居民基本医疗保险报销医疗费(万元)","转移性收入医疗救助金(万元)","生产经营性支出(万元)","转移性支出个人所得税(万元)","转移性支出社会保障支出(万元)","转移性支出赡养支出(万元)","转移性支出其他转移性支出(万元)","未偿还借(贷)款(万元)","可支配收入(万元)","人均可支配收入(元)"};
				}else if("FiveLowOrderArea".equals(export_task_id)){
					exportFileName = "低保五保政策落实监控落实排序区域数据表";
					content_size = 38;
					content_arr = new String[]{"行政区域","贫困户数户数","贫困户数人数",
							"应该享受低保/五保政策一般贫困户户数","应该享受低保/五保政策一般贫困户人数","应该享受低保/五保政策低保户户数","应该享受低保/五保政策低保户人数","应该享受低保/五保政策五保户户数","应该享受低保/五保政策五保户人数",
							"已经享受低保/五保政策一般贫困户户数","已经享受低保/五保政策一般贫困户人数","已经享受低保/五保政策低保户户数","已经享受低保/五保政策低保户人数","已经享受低保/五保政策五保户户数","已经享受低保/五保政策五保户人数",
							"政策落实比例(%)一般贫困户按户数","政策落实比例(%)一般贫困户按人数","政策落实比例(%)低保户按户数","政策落实比例(%)低保户按人数","政策落实比例(%)五保户按户数","政策落实比例(%)五保户按人数",
							"行业数据一般贫困户户数","行业数据一般贫困户人数","行业数据低保户户数","行业数据低保户人数","行业数据五保户户数","行业数据五保户人数",
							"重合数一般贫困户户数","重合数一般贫困户人数","重合数低保户户数","重合数低保户人数","重合数五保户户数","重合数五保户人数",
							"重合率(%)一般贫困户按户数","重合率(%)一般贫困户按人数","重合率(%)低保户按户数","重合率(%)低保户按人数","重合率(%)五保户按户数","重合率(%)五保户按人数"};
				}else if("EduOrderArea".equals(export_task_id)){
					exportFileName = "教育政策落实监控落实排序区域数据表";
					content_size = 12;
					content_arr = new String[]{"行政区域","贫困人口数","应该享受教育政策人数义务教育人数","应该享受教育政策人数高中教育人数","已经享受教育政策人数义务教育人数","已经享受教育政策人数高中教育人数","教育政策落实率(%)义务教育人数","教育政策落实率(%)高中教育人数","行业重合数义务教育人数","行业重合数高中教育人数","重合率(%)义务教育人数","重合率(%)高中教育人数"};
				}else if("MedicalOrderArea".equals(export_task_id)){
					exportFileName = "医疗政策落实监控落实排序区域数据表";
					content_size = 6;
					content_arr = new String[]{"行政区域","贫困户人数","已购买医疗保险人数","医疗政策落实率(%)","行业重合数","重合率(%)"};
				}else if("HouseOrderArea".equals(export_task_id)){
					exportFileName = "住房政策落实监控落实排序区域数据表";
					content_size = 26;
					content_arr = new String[]{"行政区域","危房需改造户数合计","危房需改造户数D级","危房需改造户数C级","危房需改造户数B级","危房需改造户数A级","已完成改造户数合计","已完成改造户数D级","已完成改造户数C级","已完成改造户数B级","已完成改造户数A级","动工改造户数合计","动工改造户数D级","动工改造户数C级","动工改造户数B级","动工改造户数A级","未动工改造户数合计","未动工改造户数D级","未动工改造户数C级","未动工改造户数B级","未动工改造户数A级","危房改造完成率(%)合计","危房改造完成率(%)D级","危房改造完成率(%)C级","危房改造完成率(%)B级","危房改造完成率(%)A级"};
				}else if("PoorAnalysis".equals(export_task_id)){
					exportFileName = "贫困分析明细表";
					content_size = 12;
					content_arr = new String[]{"序号","市","县","镇","村","户号","户主姓名","贫困户属性","劳动力属性","致贫原因","期初人均可支配收入(元)","操作"};
				}else if("AlarmedPoorExceptionMonitor".equals(export_task_id)){
					exportFileName = "异常统计表";
					content_size = 5;
					content_arr = new String[]{"序号","行政区域","建档立卡比对项数(条)","异常项数(条)","异常项比例(%)"};
				}else if("projectManagement".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = projectManagementDao.projectManagementTotal(params);//合计
					}
					exportFileName = "项目监控管理统计表";
					content_size = 12;
					content_arr = new String[]{"行政区域","项目总数/数量","项目总数/计划资金","项目总数/实际资金","完工项目/数量","完工项目/计划资金","完工项目/实际资金","进行中项目/数量","进行中项目/计划资金","进行中项目/实际资金","未进行项目/数量","未进行项目/计划资金"};
				}else if("projectAnalysis".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = projectManagementDao.projectAnalysisTotal(params);//合计
					}
					exportFileName = "项目监控分析统计表";
					content_size = 7;
					content_arr = new String[]{"行政区域","总计/数量","总计/金额","户项目/数量","户项目/金额","村项目/数量","村项目/金额"};
				}else if("projectAnalysisToCountry".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = projectManagementDao.projectAnalysisToCountryTotal(params);//合计
					}
					exportFileName = "项目监控分析到村统计表";
					content_size = 21;
					content_arr = new String[]{"行政区域","合计/数量","合计/金额","村道建设/数量","村道建设/金额","饮水工程/数量","饮水工程/金额","文体设施/数量","文体设施/金额","卫生设施/数量","卫生设施/金额","路灯安装/数量","路灯安装/金额","农田水利/数量","农田水利/金额","公共设施/数量","公共设施/金额","集体经济/数量","集体经济/金额","教育教学/数量","教育教学/金额"};
				}else if("projectAnalysisToFamily".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = projectManagementDao.projectAnalysisToFamilyTotal(params);//合计
					}
					exportFileName = "项目监控分析到户统计表";
					content_size = 21;
					content_arr = new String[]{"行政区域","合计/数量","合计/金额","产业扶贫/数量","产业扶贫/金额","金融扶贫/数量","金融扶贫/金额","住房改造/数量","住房改造/金额","资产扶贫/数量","资产扶贫/金额","慰问扶贫/数量","慰问扶贫/金额","就业扶贫/数量","就业扶贫/金额","技能培训/数量","技能培训/金额","教育扶贫/数量","教育扶贫/金额","政策补贴+社会保障/数量","政策补贴+社会保障/金额"};
				}else if("dutyManagement".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = dutyDao.dutyManagementTotal(params);//合计
					}
					exportFileName = "责任监控管理统计表";
					content_size = 5;
					content_arr = new String[] {"行政区域","帮扶单位(个)","驻村干部(人)","帮扶责任人(人)","帮扶走访量(次)"};
				}else if("dutyAnalysis".equals(export_task_id)){
					if(!"country".equals(level)){
						table_list_c = dutyDao.dutyAnalyseTotal(params);//合计
					}
					exportFileName = "责任监控分析统计表";
					content_size = 25;
					content_arr = new String[] {"行政区域","省直和中直驻粤单位帮扶/村数","省直和中直驻粤单位帮扶/户数","省直和中直驻粤单位帮扶/人数","广州市/村数","广州市/户数","广州市/人数","深圳市/村数","深圳市/户数","深圳市/人数","珠海市/村数","珠海市/户数","珠海市/人数","佛山市/村数","佛山市/户数","佛山市/人数","东莞市/村数","东莞市/户数","东莞市/人数","中山市/村数","中山市/户数","中山市/人数","自身帮扶/村数","自身帮扶/户数","自身帮扶/人数"};
				}else if("queryFundFileDetailList".equals(export_task_id)){
					exportFileName = "资金文件详情统计表";
					content_size = 31;
					content_arr = new String[]{"中央财政资金/文件号","中央财政资金/下拨日期","中央财政资金/下拨金额","省财政资金/资金下拨地级市","省财政资金/文件号","省财政资金/下拨日期","省财政资金/下拨金额","省财政资金/下拨周期","省财政资金/省级财政资金余额","地级市财政资金/资金下拨县","地级市财政资金/文件号","地级市财政资金/下拨日期","地级市财政资金/下拨金额","地级市财政资金/下拨周期","地级市财政资金/市级财政资金余额","县级财政资金/资金下拨镇","县级财政资金/文件号","县级财政资金/下拨日期","县级财政资金/下拨金额","县级财政资金/下拨周期","县级财政资金/县级财政资金余额","镇级财政资金/资金下拨村","镇级财政资金/文件号","镇级财政资金/下拨日期","镇级财政资金/下拨金额","镇级财政资金/下拨周期","镇级财政资金/镇级财政资金余额","项目名称","项目日期","项目金额","项目收益"};
				}


				
				//分页================================================================================
				//扶贫对象管理导出
				if("PoorChangeAnalysisDetail".equals(export_task_id)){
					exportFileName = "扶贫对象分析明细表";
					table_count = pcDao.queryObjectDetailCount(params);
					content_size = 9;
					content_arr = new String[]{"市","县","镇","村","户码","户主姓名","状态属性","自然增加成员","自然减少成员"};
				}else if("dutyAnalysisDetail".equals(export_task_id)){
					table_count = dutyDao.dutyAnalysisDetailCount(params);
					exportFileName = "责任监控分析明细表";
					content_size = 10;
					content_arr = new String[] {"市","县","镇","村","户码","户主姓名","帮扶单位","驻村干部","帮扶责任人","走访次数(次)"};
				}
				//脱贫对象管理导出
				else if("PoorResultResultAnalysisDetail".equals(export_task_id)){
					exportFileName = "脱贫对象成效分析明细表";
					table_count = prDao.queryAnalysisOfEffectsDetailsCount(params);
					content_size = 8;
					content_arr = new String[]{"市","县","镇","村","户码","户主姓名","计划脱贫时间(年)","实际脱贫时间(年)"};
				}
				//可支配收入-脱贫户分析、贫困户分析导出
				else if("AverageIncomeSuccessAnalysisDetail".equals(export_task_id)){
					exportFileName = "脱贫户分析明细表";
					table_count = diDao.queryDetailOfIncomeTableC(params);
					content_size = 13;
					content_arr = new String[]{"年份","市","县","镇","村","户码","户主姓名","家庭人数","预脱贫时间(年)","总收入(元)","总支出(元)","可支配收入(元)","人均可支配收入(元)"};
				}else if("AverageIncomePoorAnalysisDetail".equals(export_task_id)){
					exportFileName = "贫困户分析明细表";
					table_count = diDao.queryDetailOfIncomeTableC(params);
					content_size = 13;
					content_arr = new String[]{"年份","市","县","镇","村","户码","户主姓名","家庭人数","预脱贫时间(年)","总收入(元)","总支出(元)","可支配收入(元)","人均可支配收入(元)"};
				}
				//教育政策落实分析明细
				else if("FiveLowAnalysisDetail".equals(export_task_id)){
					exportFileName = "低保五保政策落实分析明细表";
					table_count = flDao.flveLowDetailCount(params);
					content_size = 10;
					content_arr = new String[]{"市","县","镇","村","户码","户主姓名","贫困属性","家庭人口数","落实政策人口数","行业重合人口数"};
				}
				//教育政策落实分析明细
				else if("EduImplementAnalysisDetail".equals(export_task_id)){
					exportFileName = "教育政策落实分析明细表";
					table_count = eduDao.queryEducationDetailCount(params);
					content_size = 10;
					content_arr = new String[]{"市","县","镇","村","户码","户主姓名","学生姓名","教育阶段","是否享受政策","是否行业重合"};
				}
				//医疗政策落实分析明细
				else if("MedicalImplementAnalysisDetail".equals(export_task_id)){
					exportFileName = "医疗政策落实分析明细表";
					table_count = medicalDao.queryMedicalDetailCount(params);
					content_size = 9;
					content_arr = new String[]{"市","县","镇","村","户码","户主姓名","成员姓名","是否政策落实","是否行业重合"};
				}
				//住房政策落实分析明细
				else if("HouseImplementAnalysisDetail".equals(export_task_id)){
					exportFileName = "住房政策落实分析明细表";
					table_count = houseDao.queryHousingDetailCount(params);
					content_size = 9;
					content_arr = new String[]{"市","县","镇","村","户码","户主姓名","危房级别","改造进度","是否列入行业计划"};
				}else if("AlarmedPoorDetail".equals(export_task_id)){
					exportFileName = "贫困识别监控异常明细表";
					table_count = alarmedPoorExceptionDao.queryPoorIndetificationDetailoCount(params);
					content_size = 9;
					content_arr = new String[]{"市","县","镇","村","户码","户主姓名","成员姓名","异常类型","异常描述"};
				}else if("AlarmedRecordsDetail".equals(export_task_id)){
					exportFileName = "建档立卡异常记录监控异常明细表";
					table_count = alarmedPoorExceptionDao.queryPoorRecordsDetailCount(params);
					content_size = 10;
					content_arr = new String[]{"市","县","镇","村","户码","户主姓名","成员姓名","异常类型","异常描述","异常记录"};
				}else if("AlarmedMoneyDetail".equals(export_task_id)){
					exportFileName = "帮扶资金监控异常明细表";
					table_count = alarmedPoorExceptionDao.queryPoorMoneyDetailCount(params);
					content_size = 8;
					content_arr = new String[]{"市","县","镇","村","帮扶单位","主要领导","当年帮扶资金(万元)","累计帮扶资金(万元)"};
				}else if("projectAnalysisDetail".equals(export_task_id)){
					exportFileName = "项目监控分析明细表";
					content_size = 9;
					content_arr = new String[]{"市","县","镇","村","项目属性","项目类型","项目数(个)","投资金额(万元)","项目完成率(%)"};
				}else if("queryPopDetail".equals(export_task_id)){
					exportFileName = "贫困人口明细表";
					content_size = 80;
					content_arr = new String[]{"镇(乡、街道)","行政村","自然村","是否相对贫困村","户登记表年度","户主姓名","户主证件号码","联系电话","户码","开户银行","银行卡卡号","贫困户人数","是否新识别贫困户","是否水库移民户","有劳动能力人数(户)","残疾人个数(户)","A17识别标准","A18农户属性","A19是否军烈属","A20是否独生子女户","A21是否双女户","A22主要致贫原因(单选)","A23其他致贫原因(可复选)","A32是否通生活用电","A33饮水是否困难","A34饮水是否安全","A36是否加入农民专业合作社","A39家庭合计工资性收入","A40家庭合计生产经营性收入","A41家庭合计财产性收入","A42家庭合计转移性收入","A42a家庭合计计划生育金","A42b家庭合计低保金","A42c家庭合计五保金","A42d家庭合计养老保险金","A42e家庭合计生态补偿金","A42f家庭合计其他转移性收入","A42g家庭合计城乡居民基本医疗保险报销医疗费","A42h家庭合计医疗救助金","A43家庭合计生产经营性支出","A50家庭合计转移性支出","A50a家庭合计个人所得税","A50b家庭合计社会保障性支出","A50c家庭合计赡养支出","A50d家庭合计其他转移性支出","A51家庭合计未偿还借(贷)款","A52家庭合计家庭可支配收入","A53家庭合计家庭人均可支配收入","A56主要住房是否危房","姓名","性别","证件类型","身份证号码","残疾证号码","军官证号码","出生日期","务工时间(月)","年龄","与户主关系","民族","政治面貌","文化程度","在校生状态","在校生状况","劳动能力","入读技工院校意愿","健康状况","务工状况","是否现役军人","技能状况","培训需求","就业意向","就业预期","城乡居民基本养老保险个人缴费档次或取养老保险金水平","是否参加大病医疗保险","是否参加城镇职工基本养老保险	是否参加城乡居民基本医疗保险","是否参加城乡居民基本养老保险","务工企业名称","是否享受低保","脱贫年份"};
				}else if("PoorFamilyList".equals(export_task_id)){
					exportFileName = "建档立卡贫困户列表";
					/*查询地图权限*/
					String userPac = UserUtils.getAreaList().iterator().next().toString();
					
					if(userPac != null && userPac.length() == 12){
						if(!userPac.substring(9,12).equals("000")){
							level="country";
						}
					}
					
					if("town".equals(level)){
						content_size = 6;
						content_arr = new String[]{"村名","户主姓名","经纬度","脱贫年份",params.get("F_COUNTRYFILE_HELP_TRENDSY")+"年综合得分","当年走访次数"};
					}else if("country".equals(level)){
						content_size = 5;
						content_arr = new String[]{"户主姓名","经纬度","脱贫年份",params.get("F_COUNTRYFILE_HELP_TRENDSY")+"年综合得分","当年走访次数"};
					}
				}
				//排序导出
				else if("ChangeOrder".equals(export_task_id)){
					exportFileName = getValue(params,"order_table_title")+"表";
					table_count = pcDao.queryPoorChangeSortCount(params);
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("ResultOrder".equals(export_task_id)){
					exportFileName = getValue(params,"order_table_title")+"表";
					table_count = prDao.queryPoorResultSortCount(params);
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("IncomeOrder".equals(export_task_id)){
					exportFileName = getValue(params,"order_table_title")+"表";
					table_count = diDao.queryIncomeSortCount(params);
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("FiveLowOrder".equals(export_task_id)){
					exportFileName = getValue(params,"order_table_title")+"表";
					table_count = flDao.flveLowSortCount(params);
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("EduOrder".equals(export_task_id)){
					exportFileName = getValue(params,"order_table_title")+"表";
					table_count = eduDao.queryEducationSortCount(params);
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("MedicalOrder".equals(export_task_id)){
					exportFileName = getValue(params,"order_table_title")+"表";
					table_count = medicalDao.queryMedicalSortCount(params);
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("HouseOrder".equals(export_task_id)){
					exportFileName = getValue(params,"order_table_title")+"表";
					table_count = houseDao.queryHousingSortCount(params);
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("PoorExceptionOrder".equals(export_task_id)){
					String[] taxis_indexes = {"异常项数","异常数比例"};
					exportFileName = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
					//exportFileName = "异常项数排序数据表";
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("PoorRecordsOrder".equals(export_task_id)){
					String[] taxis_indexes = {"异常项数","异常数比例"};
					exportFileName = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"taxis_indexes").toString())-1]+"排序";
					//exportFileName = "异常项数排序数据表";
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("AlarmedMoneydOrder".equals(export_task_id)){
					String[] taxis_indexes = {"帮扶资金小于＜30万的相对贫困村比例","帮扶资金小于＜30万的相对贫困村数"};
					exportFileName = sort_area[Integer.parseInt(getValue(params,"sort_area").toString())-1]+taxis_indexes[Integer.parseInt(getValue(params,"exception_type").toString())-1]+"排序";
					content_size = 2;
					content_arr = new String[]{"行政区域","指标"};
				}else if("PoorAnalysis".equals(export_task_id)){
					//System.out.println("贫困分析明细表导出=====");
					exportFileName = "贫困分析明细表";
					table_count = poorAnalyseDao.queryPoorAnalysisDetailCount(params);
					content_size = 10;
					content_arr = new String[]{"市","县","镇","村","户号","户主姓名","贫困户属性","劳动力属性","致贫原因","期初人均可支配收入(元)"};
				}else if("AlarmedPoorExceptionMonitor".equals(export_task_id)){
					exportFileName = "异常统计表";
					table_list_c = alarmedPoorExceptionDao.queryPoorIndetificationTableCount(params);//合计
					content_size = 4;
					content_arr = new String[]{"行政区域","建档立卡比对项数(条)","异常项数(条)","异常项比例(%)"};
				}else if("AlarmedRecordsExceptionMonitor".equals(export_task_id)){
					exportFileName = "统计表";
					table_list_c = alarmedPoorExceptionDao.queryRecordsTableCount(params);//合计
					content_size = 4;
					content_arr = new String[]{"行政区域","监测建档立卡记录数(条)","异常记录数(条)","异常记录比例(%)"};
				}else if("AlarmedMoneyExceptionMonitor".equals(export_task_id)){
					exportFileName = "统计表";
					table_list_c = alarmedPoorExceptionDao.queryAlarmedMoneyTableCount(params);//合计
					content_size = 4;
					content_arr = new String[]{"行政区域","监测建档立卡记录数(条)","异常记录数(条)","异常记录比例(%)"};
				}
				
				//=======================================================================
				String path =Global.getConfig("temp.filePath");
				LinkedHashMap titleMap = new LinkedHashMap();
				for(int i=0;i<content_size;i++){
					titleMap.put(i+"", content_arr[i]);
				}
				/*生成带标题信息的csv文件*/
				File csv = CSVUtils.createEmptyCSV(titleMap, path, exportFileName);
				//合计
				if(table_list_c!=null){
					List<Map<String, Object>> total_row_c =  new ArrayList<Map<String,Object>>();
					Map total_row = new LinkedHashMap();
					for(int i=0;i<content_size;i++){
						total_row.put(i+"", table_list_c.get("A" + (i + 2)));
					}
					total_row_c.add(total_row);
					CSVUtils.appendCSV(total_row_c, titleMap, csv);
				}
				
				/*计算分页查询页数与分页导出数据并写入文件*/
				int totalRecords = (int)table_count;
				
				int pageSize_temp = 50000;//查询数设定值
				int temp = totalRecords / pageSize_temp;
				int queryCount = 1;
				if(totalRecords > 0){
					queryCount = (totalRecords % pageSize_temp) != 0 ? temp + 1 : temp;
				}
		        //如果超出最大线程数，查询数重新设定
		        if(queryCount>15){
		        	queryCount = 15;
		        	pageSize_temp = (totalRecords % 15) + (totalRecords / 15);
		        }
		        
		        final int pageSize = pageSize_temp;
		        
				List<Map<String, Object>> resultList = Collections.synchronizedList(new ArrayList<Map<String, Object>>());// 线程安全的List
		        // 同步辅助类,当所有子线程全部执行结束后来执行不同辅助类指定的同步线程FileWriteTaskThread
		        CyclicBarrier cyclicBarrier = new CyclicBarrier(queryCount, new FileWriteTaskThread(resultList,titleMap,csv,export_task_id,loadPool));
		        
				/*计算分页查询页数与分页导出数据并写入文件*/
				params.put("pageSize", pageSize);
				int index = 0;
				for (int i = 0; i < queryCount; i++) {
					ReadThread readThread = new ReadThread();
					readThread.setPageSize(pageSize);
					readThread.setPage(i+1);;
					readThread.setParameters(params);
					readThread.setService(this);
					readThread.setLoadPool(loadPool);
					readThread.setContentSize(content_size);
					readThread.setIndex(index*pageSize+1);
					readThread.setResultList(resultList);
					readThread.setTotalRecords((int)table_count);
					readThread.setSearchKey(export_task_id);
					readThread.setCyclicBarrier(cyclicBarrier);
					// 启动多线程来从数据库中分页读取数据
		            taskExecutor.execute(readThread);
					
				}
				map.put(export_task_id+"csvFilePath", csv.getPath());
				map.put(export_task_id+"fileName", exportFileName+".csv");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("分页进行数据写入csv文件操作失败异常信息："+e.getMessage());
		}
		return fileName;
	}
	
	/**
	 * 获取导出查询记录
	 * @param params
	 * @param searchKey
	 * @param contentSize
	 * @param index
	 * @param totalRecords
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRows(Map<String, Object> params, String searchKey,
			Integer contentSize, Integer index, Integer totalRecords,
			Integer page) {
		Map<String, Object> paramters = new HashMap<String,Object>();
		paramters.putAll(params);
		paramters.put("page", page);
		int num=2;

		/*Iterator<Entry<String, Object>> entries = params.entrySet().iterator();  
		while (entries.hasNext()) {  
		    Entry<String, Object> entry = entries.next();
		   // params.put(entry.getKey(),entry.getValue()[0]); 
		    System.out.println("key: "+entry.getKey()+"  valule: "+entry.getValue());
		}*/
		List<Map<String, Object>> table_list = null;
		List rows = new ArrayList<Map>();
		//扶贫对象管理导出
		if("PoorChangeManagement".equals(searchKey)){
			
			table_list = pcDao.queryThePoorTrendAllTable(params);
			
		}else if("PoorChangeAnalysisAll".equals(searchKey)){
			table_list = pcDao.queryThePoorTrendAllTable(params);
		}else if("PoorChangeAnalysisCur".equals(searchKey)){
			table_list = pcDao.queryThePoorTrendAllTable(params);
		}else if("PoorChangeAnalysisAdd".equals(searchKey)){
			table_list = pcDao.queryThePoorTrendAddTable(params);
		}else if("PoorChangeAnalysisEnd".equals(searchKey)){
			table_list = pcDao.queryThePoorTrendEndChart(params);
		}else if("PoorChangeAnalysisNaturalChange".equals(searchKey)){
			table_list = pcDao.queryThePoorTrendNAddTable(params);
		}
		//脱贫对象管理导出
		else if("PoorResultPlanManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(prDao.queryPovertyResultsTableC(params));
			table_list.addAll(prDao.queryPovertyResultsTable(params));
		}else if("PoorResultResultAnalysis".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(prDao.queryAnalysisOfEffectsTableC(params));
			table_list.addAll(prDao.queryAnalysisOfEffectsTable(params));
		}else if("PoorResultNotSuccessAnalysis".equals(searchKey)){
			table_list = prDao.queryNotAnalysisTable(params);
		}
		//可支配人均收入管理
		else if("AverageIncomeIncomeManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(diDao.queryDisposableIncomeMTableC(params));
			table_list.addAll(diDao.queryDisposableIncomeMTable(params));
		}else if("AverageIncomeSuccessAnalysis".equals(searchKey)){
			table_list = diDao.queryExpensesAnalyzeTable(params);
		}else if("AverageIncomePoorAnalysis".equals(searchKey)){
			table_list = diDao.queryExpensesAnalyzeTable(params);
		}else if("AverageIncomeSuccessAnalysisTP".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(diDao.querySpendingAnalysisC(params));
			table_list.addAll(diDao.querySpendingAnalysis(params));
		}else if("AverageIncomePoorAnalysisTP".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(diDao.querySpendingAnalysisC(params));
			table_list.addAll(diDao.querySpendingAnalysis(params));
		}else if("AverageIncomeSuccessAnalysisTI".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(diDao.queryIncomeAnalysisC(params));
			table_list.addAll(diDao.queryIncomeAnalysis(params));
		}else if("AverageIncomePoorAnalysisTI".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(diDao.queryIncomeAnalysisC(params));
			table_list.addAll(diDao.queryIncomeAnalysis(params));
		}
		//低保五保政策落实
		else if("FiveLowImplementManagement".equals(searchKey)){
			table_list = flDao.flveLowGovern(params);
		}else if("FiveLowImplementAnalysis".equals(searchKey)){
			table_list = flDao.flveLowAnalyze(params);
		}
		//教育政策落实
		else if("EduImplementManagement".equals(searchKey)){
			table_list = eduDao.queryEducationManageTable(params);
		}else if("EduImplementAnalysis".equals(searchKey)){
			table_list = eduDao.queryEducationAnalysisTable(params);
		}
		//医疗政策落实
		else if("MedicalPolicyImplementManagement".equals(searchKey)){
			table_list = medicalDao.queryMedicalManageTable(params);
		}else if("MedicalPolicyImplementAnalysis".equals(searchKey)){
			table_list = medicalDao.queryMedicalManageTable(params);
		}
		//住房政策落实
		else if("HouseImplementManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(houseDao.queryHousingManageTableC(params));
			table_list.addAll(houseDao.queryHousingManageTable (params));
		}else if("HouseImplementAnalysis".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(houseDao.queryHousingAnalyzeTableC(params));
			table_list.addAll(houseDao.queryHousingAnalyzeTable(params));
			
			//贫困分析导出
		}else if("PoorAnalysis".equals(searchKey)){
			table_list =  poorAnalyseDao.queryPoorAnalysisDetail(params);
		}else if("AlarmedPoorExceptionMonitor".equals(searchKey)){
			table_list =  alarmedPoorExceptionDao.queryPoorIndetificationTable(params);//数据体
		}else if("AlarmedRecordsExceptionMonitor".equals(searchKey)){
			table_list =  alarmedPoorExceptionDao.queryRecordsTable(params);//数据体
		}else if("AlarmedMoneyExceptionMonitor".equals(searchKey)){
			table_list =  alarmedPoorExceptionDao.queryRecordsTable(params);//数据体
		}else if("AlarmedMoneyExceptionMonitor".equals(searchKey)){
			table_list =  alarmedPoorExceptionDao.queryRecordsTable(params);//数据体
		}else if("PoorExceptionOrder".equals(searchKey)){
			table_list =  alarmedPoorExceptionDao.queryPoorIndetificationSort(params);//数据体
		}else if("PoorRecordsOrder".equals(searchKey)){
			table_list =  alarmedPoorExceptionDao.queryPoorRecordsSort(params);//数据体
		}else if("AlarmedMoneydOrder".equals(searchKey)){
			table_list =  alarmedPoorExceptionDao.queryAlarmedMoneySort(params);//数据体
		}
		else if("RoadImplementManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryRoadImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryRoadImplementManagementTable(params));
		}
		else if("RoadImplementAnalysis".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryRoadImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryRoadImplementManagementTable(params));
		}else if("RoadManagement".equals(searchKey)){
			table_list = fairlyDao.queryRoadImplementAnalysisDetail(params);

		}else if("RoadOrder".equals(searchKey)){
			table_list = fairlyDao.queryRoadImplementAnalysisSort(paramters);

		}else if("RoadOrderArea".equals(searchKey)){
			table_list = fairlyDao.queryRoadSortDetail(params);
			
			
		}else if("WaterImplementManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryWaterImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryWaterImplementManagementTable(params));
			
		}else if("WaterImplementAnalysis".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryWaterImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryWaterImplementManagementTable(params));
		}else if("WaterManagement".equals(searchKey)){
					table_list = fairlyDao.queryWaterSortImplementAnalysisDetail(params);
					
		}else if("WaterOrder".equals(searchKey)){
					table_list=fairlyDao.queryWaterSortImplementAnalysisSort(paramters);
		}else if("WaterOrderArea".equals(searchKey)){
					table_list = fairlyDao.queryWaterSortSortDetail(params);
					
		}else if("ElectricityImplementManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryElectricityImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryElectricityImplementManagementTable(params));
		}else if("projectManagement".equals(searchKey)){
					table_list = new ArrayList<Map<String,Object>>();
					//table_list.add(projectManagementDao.projectManagementTotal(params));
					table_list.addAll(projectManagementDao.projectManagementCount(params));
		}else if("projectAnalysis".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(projectManagementDao.projectAnalysisTotal(params));
			table_list.addAll(projectManagementDao.projectAnalysisCount(params));
        }else if("projectAnalysisToCountry".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			//table_list.add(projectManagementDao.projectAnalysisToCountryTotal(params));
			table_list.addAll(projectManagementDao.projectAnalysisToCountryCount(params));
        }else if("projectAnalysisToFamily".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.addAll(projectManagementDao.projectAnalysisToFamilyCount(params));
        }else if("dutyManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.addAll(dutyDao.dutyManagementCount(params));
        }else if("dutyAnalysis".equals(searchKey)){
        	//table_list = new ArrayList<Map<String,Object>>();
        	table_list = dutyDao.dutyAnalyseCount(params);
        }else if("queryFundFileDetailList".equals(searchKey)){
        	//table_list = new ArrayList<Map<String,Object>>();
        	table_list = fmtDao.queryFundFileDetailListo(params);
        }else if("ElectricityImplementAnalysis".equals(searchKey)){
					table_list = fairlyDao.queryElectricityImplementManagementTable(params);
		}else if("ElectricityManagement".equals(searchKey)){
					table_list = fairlyDao.queryElectricitySortDetail(params);
		}else if("ElectricityOrder".equals(searchKey)){
					table_list=fairlyDao.queryElectricitySortImplementAnalysisSort(paramters);
		}else if("ElectricityOrderArea".equals(searchKey)){
					table_list = fairlyDao.queryElectricitySortAortDetail(params);
		}else if("MedicalFacilityImplementManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryMedicalFacilityImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryMedicalFacilityImplementManagementTable(params));
			
		}else if("MedicalFacilityImplementAnalysis".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryMedicalFacilityImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryMedicalFacilityImplementManagementTable(params));
		}else if("MedicalFacilityManagement".equals(searchKey)){
					table_list = fairlyDao.queryMedicalSortDetail(params);
		}else if("MedicalFacilityOrder".equals(searchKey)){
					table_list=fairlyDao.queryMedicalSortImplementAnalysisSort(paramters);
		}else if("MedicalFacilityOrderArea".equals(searchKey)){
					table_list = fairlyDao.queryMedicalSortAortDetail(params);
					
					
		}else if("BroadcastImplementManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryBroadcastImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryBroadcastImplementManagementTable(params));
		}else if("BroadcastImplementAnalysis".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryBroadcastImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryBroadcastImplementManagementTable(params));
		}else if("BroadcastManagement".equals(searchKey)){
					table_list = fairlyDao.queryBroadcastSortDetail(params);
		}else if("BroadcastOrder".equals(searchKey)){
					table_list=fairlyDao.queryBroadcastSortImplementAnalysisSort(paramters);
		}else if("BroadcastOrderArea".equals(searchKey)){
					table_list = fairlyDao.queryBroadcastSortAortDetail(params);
	//资金				
		}else if("FundManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fmtDao.queryFundMonitoringManagementTableC(params));
			table_list.addAll(fmtDao.queryFundMonitoringManagementTable(params));
		}else if("FundAnalysisManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fmtDao.queryFundMonitoringManagementTableC(params));
			table_list.addAll(fmtDao.queryFundMonitoringManagementTable(params));
		}else if("ExampleManagementww".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(exampleCountryDao.queryExampleManagementTableC(params));
			table_list.addAll(exampleCountryDao.queryExampleManagementTable(params));
		}else if("FundAnalysisManagementDetail".equals(searchKey)){
			table_list = fmtDao.queryFundAnalysisManagementDetail(params);
		}else if("FundAnalysisSort".equals(searchKey)){
			table_list =fmtDao.queryFundAnalysisSort(paramters);
		}else if("FundAnalysisSortTth".equals(searchKey)){
			table_list =fmtDao.queryFundAnalysisSortTtH(params);
		}else if("FundAnalysisSortDetail".equals(searchKey)){
			table_list =fmtDao.queryFundAnalysisSortDetail(params);
		}else if("ExampleManagementplees".equals(searchKey)){
			table_list = exampleCountryDao.queryFundAnalysisManagementDetail(params);
		}else if("NetImplementManagement".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryNetImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryNetImplementManagementTable(params));
		}else if("NetImplementAnalysis".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(fairlyDao.queryNetImplementManagementTableC(params));
			table_list.addAll(fairlyDao.queryNetImplementManagementTable(params));
		}else if("NetManagement".equals(searchKey)){
					table_list = fairlyDao.queryNetcastSortDetail(params);
		}else if("NetdOrder".equals(searchKey)){
					table_list=fairlyDao.queryNetSortImplementAnalysisSort(paramters);
		}else if("NetOrderArea".equals(searchKey)){
					table_list = fairlyDao.queryNetSortAortDetail(params);
					
		}else if("AlarmedVisitExceptionMonitor".equals(searchKey)){
			Map<String,Object> table_list_wq= warningDao.queryAlarmedImplementManagementTableC(params);
			List<Map<String,Object>> table_list_wq1 =warningDao.queryAlarmedImplementManagementTable(params);
			//帮扶责任人数
			Map<String,Object> table_list_a3 = warningDao.queryAlarmedA3(params);//合计
			if(table_list_a3.get("A4")!=null){
				table_list_a3.put("A4", table_list_a3.get("A4"));
				
			}
			
			
			List<Map<String,Object>> table_list_a = warningDao.queryAlarmedA4(params);
			if(table_list_wq1 .size()!=0 ){
				if (table_list_a.size()!=0) {
					for (int i = 0; i < table_list_wq1.size(); i++) {
						
						table_list_wq1.get(i).put("A4",table_list_a.get(i).get("A4"));
					
				}
				}
			}
			
			table_list = new ArrayList<Map<String,Object>>();
			table_list.add(table_list_wq);
			table_list.addAll(table_list_wq1);
			
		}else if("AlarmedVisitdOrder".equals(searchKey)){
					table_list=warningDao.queryAlarmedImplementAnalysisSort(paramters);
		}else if("ProjectOrder".equals(searchKey)){
			table_list=projectManagementDao.projectAnalysisSort(params);
		}else if("AlarmedVisitOrderArea".equals(searchKey)){
			
			table_list = warningDao.queryAlarmedSortDetail(params);
			
			List<Map<String,Object>> table_list_a = warningDao.queryAlarmedA5(params);
			if (table_list_a.size()!=0) {
				for (int i = 0; i < table_list.size(); i++) {
					
					table_list.get(i).put("A4",table_list_a.get(i).get("A4"));
				
			}
			}
		}else if("AlarmedVisit".equals(searchKey)){

			table_list = warningDao.queryAlarmedImplementAnalysisDetail(params);
			for (int i = 0; i < table_list.size(); i++) {
				String Str=(String) table_list.get(i).get("A9");
			    Str = Str.replaceAll(",",".");
				table_list.get(i).put("A9",Str);
			
				}
		}
//到户
		else if("ManagementRoad".equals(searchKey)){
			table_list = countryDao.queryRoadImplementAnalysisDetail(params);
		}else if("ManagementWater".equals(searchKey)){
			table_list = countryDao.queryWaterImplementAnalysisDetail(params);
		}else if("ManagementRecreationSport".equals(searchKey)){
			table_list = countryDao.querySportImplementAnalysisDetail(params);
		}else if("ManagementHygiene".equals(searchKey)){
			table_list = countryDao.queryHygieneImplementAnalysisDetail(params);
		}else if("ManagementLamp".equals(searchKey)){
			table_list = countryDao.queryLampImplementAnalysisDetail(params);
		}else if("ManagementFarm".equals(searchKey)){
			table_list = countryDao.queryFarmImplementAnalysisDetail(params);
		}else if("ManagementFacility".equals(searchKey)){
			table_list = countryDao.queryFacilityImplementAnalysisDetail(params);
		}else if("ManagementEconomy".equals(searchKey)){
			table_list = countryDao.queryEconomyImplementAnalysisDetail(params);
		}else if("ManagementEdu".equals(searchKey)){
			table_list = countryDao.queryEduImplementAnalysisDetail(params);
		}
		
		else if("AnalysisRoad".equals(searchKey)){
			table_list = countryDao.queryRoadImplementManagementTable(params);
		}else if("AnalysisWater".equals(searchKey)){
			table_list = countryDao.queryWaterImplementManagementTable(params);
		}else if("AnalysisSport".equals(searchKey)){
			table_list = countryDao.querySportImplementManagementTable(params);
		}else if("AnalysisHygiene".equals(searchKey)){
			table_list = countryDao.queryHygieneImplementManagementTable(params);
		}else if("AnalysisLamp".equals(searchKey)){
			table_list = countryDao.queryLampImplementManagementTable(params);
		}else if("AnalysisFarm".equals(searchKey)){
			table_list = countryDao.queryFarmImplementManagementTable(params);
		}else if("AnalysisFacility".equals(searchKey)){
			table_list = countryDao.queryFacilityImplementManagementTable(params);
		}else if("AnalysisEconomy".equals(searchKey)){
			table_list = countryDao.queryEconomyImplementManagementTable(params);
		}else if("AnalysisEdu".equals(searchKey)){
			table_list = countryDao.queryEduImplementManagementTable(params);
		
		}else if("ProjectFamilyIndustry".equals(searchKey)){
			table_list = fpDao.queryMoneyTrendAllTable(params);
		}else if("ProjectFamilyFinance".equals(searchKey)){
			table_list = fpDao.queryFinanceTrendAllTable(params);
		}else if("ProjectFamilyHouse".equals(searchKey)){
			table_list = fpDao.queryHouseTrendAllTable(params);
		}else if("ProjectFamilyProperty".equals(searchKey)){
			table_list = fpDao.queryPropertyTrendAllTable(params);
		}else if("ProjectFamilyVisit".equals(searchKey)){
			table_list = fpDao.queryVisitTrendAllTable(params);
		}else if("ProjectFamilyEmployment".equals(searchKey)){
			table_list = fpDao.queryEmploymentTrendAllTable(params);
		}else if("ProjectFamilySkill".equals(searchKey)){
			table_list = fpDao.querySkillTrendAllTable(params);
		}else if("ProjectFamilyEdu".equals(searchKey)){
			table_list = fpDao.queryEduTrendAllTable(params);
			
		}else if("THEFAMIndustryDetail".equals(searchKey)){
			table_list = fpDao.queryIndustryDetail(params);
		}else if("THEFAMFinanceDetail".equals(searchKey)){
			table_list = fpDao.queryFinanceDetail(params);
		}else if("THEFAMHouseDetail".equals(searchKey)){
			table_list = fpDao.queryHouseDetail(params);
		}else if("THEFAMPropertyDetail".equals(searchKey)){
			table_list =fpDao.queryPropertyDetail(params);
		}else if("THEFAMVisitDetail".equals(searchKey)){
			table_list = fpDao.queryVisitDetail(params);
		}else if("THEFAMEmploymentDetail".equals(searchKey)){
			table_list = fpDao.queryEmploymentDetail(params);
		}else if("THEFAMSkillDetail".equals(searchKey)){
			table_list = fpDao.querySkillDetail(params);
		}else if("THEFAMEduDetail".equals(searchKey)){
			table_list = fpDao.queryEduDetail(params);
		}
		
		//排序区域导出
		else if("ChangeOrderArea".equals(searchKey)){
			table_list = pcDao.querySortDetail(params);
		}else if("ProjectOrderArea".equals(searchKey)){
			table_list = new ArrayList<Map<String,Object>>();
			table_list.addAll(projectManagementDao.projectAnalysisSortDetail(params));
		}else if("ResultOrderArea".equals(searchKey)){
			table_list = prDao.querySortDetail(params);
		}else if("IncomeOrderArea".equals(searchKey)){
			table_list = diDao.querySortDetail(params);
		}else if("FiveLowOrderArea".equals(searchKey)){
			table_list = flDao.flveLowArea(params);
		}else if("EduOrderArea".equals(searchKey)){
			table_list = eduDao.queryEducationSortDetail(params);
		}else if("MedicalOrderArea".equals(searchKey)){
			table_list = medicalDao.querySortDetail(params);
		}else if("HouseOrderArea".equals(searchKey)){
			table_list = houseDao.querySortDetail(params);
		}else if("AlarmedPoorOrderArea".equals(searchKey)){
			table_list = alarmedPoorExceptionDao.queryPoorIndetificationDetail(params);
		}else if("AlarmedRecordsOrderArea".equals(searchKey)){
			table_list = alarmedPoorExceptionDao.queryPoorRecordDetail(params);
		}else if("AlarmedMoneyOrderArea".equals(searchKey)){
			table_list = alarmedPoorExceptionDao.queryAlarmedMoneyDetail(params);
		}
		
		if ("PoorChangeAnalysisDetail".equals(searchKey)) {
			table_list = pcDao.queryObjectDetail(paramters);
			table_list=CommaProcessing.ProcessingComma(table_list, "A8");
			table_list=CommaProcessing.ProcessingComma(table_list, "A9");
			table_list=CommaProcessing.ProcessingComma(table_list, "A10");
			
		} else if ("PoorResultResultAnalysisDetail".equals(searchKey)) {
			table_list = prDao.queryAnalysisOfEffectsDetails(paramters);
		}else if ("dutyAnalysisDetail".equals(searchKey)) {
			table_list = dutyDao.dutyAnalysisDetail(paramters);
		} else if ("AverageIncomeSuccessAnalysisDetail".equals(searchKey)) {
			table_list = diDao.queryDetailOfIncomeTable(paramters);
		} else if ("AverageIncomePoorAnalysisDetail".equals(searchKey)) {
			table_list = diDao.queryDetailOfIncomeTable(paramters);
		} else if ("FiveLowAnalysisDetail".equals(searchKey)) {
			table_list = flDao.flveLowDetail(paramters);
		} else if ("EduImplementAnalysisDetail".equals(searchKey)) {
			table_list = eduDao.queryEducationDetail(paramters);
		} else if ("MedicalImplementAnalysisDetail".equals(searchKey)) {
			table_list = medicalDao.queryMedicalDetail(paramters);
		} else if ("HouseImplementAnalysisDetail".equals(searchKey)) {
			table_list = houseDao.queryHousingDetail(paramters);
		} else if ("AlarmedPoorDetail".equals(searchKey)) {
			table_list = alarmedPoorExceptionDao.queryPoorIndetificationDetailo(params);
		} else if ("AlarmedRecordsDetail".equals(searchKey)) {
			table_list = alarmedPoorExceptionDao.queryPoorRecordsDetail(params);
		} else if ("AlarmedMoneyDetail".equals(searchKey)) {
			table_list = alarmedPoorExceptionDao.queryPoorMoneyDetail(params);
		}else if ("projectAnalysisDetail".equals(searchKey)) {
			table_list = projectManagementDao.projectAnalysisDetail(params);
		}else if ("queryPopDetail".equals(searchKey)) {
			table_list = pcDao.queryPopDetail(params);
		}else if ("ChangeOrder".equals(searchKey)) {
			table_list = pcDao.queryPoorChangeSort(paramters);
		} else if ("ResultOrder".equals(searchKey)) {
			table_list = prDao.queryPoorResultSort(paramters);
		} else if ("IncomeOrder".equals(searchKey)) {
			table_list = diDao.queryIncomeSort(paramters);
		} else if ("FiveLowOrder".equals(searchKey)) {
			table_list = flDao.flveLowSort(paramters);
		} else if ("EduOrder".equals(searchKey)) {
			table_list = eduDao.queryEducationSort(paramters);
		} else if ("MedicalOrder".equals(searchKey)) {
			table_list = medicalDao.queryMedicalSort(paramters);
		} else if ("HouseOrder".equals(searchKey)) {
			table_list = houseDao.queryHousingSort(paramters);
		}else if ("PoorFamilyList".equals(searchKey)) {
			
			String level = (String) getValue(params,"level");
			String COUNTRY_PAC = (String) getValue(params,"COUNTRY_PAC");

			/*查询地图权限*/
			String userPac;
			try {
				userPac = UserUtils.getAreaList().iterator().next().toString();
				if(userPac != null && userPac.length() == 12){
					if(!userPac.substring(9,12).equals("000")){
						COUNTRY_PAC = userPac;
						level="country";
					}
				}
				params.put("COUNTRY_PAC", COUNTRY_PAC);
				
				
				table_list = fmtDao.queryPoorFamilyListTable(params);
				if("country".equals(level)){
					num=3;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
		}
		
		Map row = null;
		for (int a = 0; a < table_list.size() && table_list.get(a) != null; a++) {
			row = new LinkedHashMap();
			for (int b = 0; b < contentSize; b++) {
				row.put(b + "", table_list.get(a).get("A" + (b + num)));
			}
			if (index < totalRecords)
				index++;
			rows.add(row);
		}
		return rows;
	}
	
	/**
	 * 一相当-道路硬底化落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorRoadImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询一相当-道路硬底化落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("RoadImplementManagement", params);
			//查图表数据
			
			List<Map<String,Object>> chart_list = fairlyDao.queryRoadImplementManagement(params);
			
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c= fairlyDao.queryRoadImplementManagementTableC(params);

			}
			List<Map<String,Object>> table_list = fairlyDao.queryRoadImplementManagementTable(params);

			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "道路硬底化落实统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=RoadImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","完全硬底化村数(个)","完全硬底化村比例(%)","200人以上自然村到行政村未通沥青(水泥)村数(个)","年初行政村到乡镇未通沥青(水泥)路里程(公里)","年初自然村到行政村未通沥青(水泥)路里程(公里)","年初帮扶村未通里程数合计(公里)","改造完成里程(公里)","改造完成率(%)"};
			for(int j=0;j<11;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<11;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<11;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-道路硬底化落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-道路硬底化落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorRoadImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询一相当-道路硬底化落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("RoadImplementAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryRoadImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryRoadImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryRoadImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=RoadImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","完全硬底化村数(个)","完全硬底化村比例(%)","200人以上自然村到行政村未通沥青(水泥)村数(个)","年初行政村到乡镇未通沥青(水泥)路里程(公里)","年初自然村到行政村未通沥青(水泥)路里程(公里)","年初帮扶村未通里程数合计(公里)","改造完成里程(公里)","改造完成率(%)"};
			for(int j=0;j<11;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<11;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<11;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-道路硬底化落实分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-安全饮水落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorWaterImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询一相当-安全饮水落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("WaterImplementManagement", params);
			//查图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryWaterImplementManagement(params);

			
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryWaterImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryWaterImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=WaterImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","全实现安全饮水的村数(个)","全实现安全饮水村比例(%)","未全实现安全饮水的村数(个)","年初未实现饮水安全户数(户)","完成改造户数(户)","改造完成率(%)"};
			for(int j=0;j<9;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<9;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<9;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-安全饮水落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-安全饮水落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorWaterImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询一相当-安全饮水落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("WaterImplementAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryWaterImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryWaterImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryWaterImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=WaterImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","全实现安全饮水的村数(个)","全实现安全饮水村比例(%)","未全实现安全饮水的村数(个)","年初未实现饮水安全户数(户)","完成改造户数(户)","改造完成率(%)"};
			for(int j=0;j<9;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<9;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<9;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-安全饮水落实分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-生活用电落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorElectricityImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询一相当-生活用电落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("ElectricityImplementManagement", params);
			//查图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryElectricityImplementManagement(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryElectricityImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryElectricityImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ElectricityImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","全部通生活用电的村数(个)","全通生活用电村比例(%)","年初未全部通生活用电的村数(个)","完成改造村数(个)","改造完成率(%)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-生活用电落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-生活用电落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorElectricityImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询一相当-生活用电落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("ElectricityImplementAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryElectricityImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryElectricityImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryElectricityImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ElectricityImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","全部通生活用电的村数(个)","全通生活用电村比例(%)","年初未全部通生活用电的村数(个)","完成改造村数(个)","改造完成率(%)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-落实分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-医疗设施落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorMedicalFacilityImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询一相当-医疗设施落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("MedicalFacilityImplementManagement", params);
			//查图表数据
			List<Map<String,Object>> chart_l = fairlyDao.queryMedicalFacilityImplementManagement(params);
			List<Map<String,Object>> chart_r = fairlyDao.queryMedicalImplementManagement(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryMedicalFacilityImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryMedicalFacilityImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend_station = new JSONObject();
			chart_config_trend_station.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", chart_l.get(i).get("S_GROUP"));
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend_station.put("config", config_list);
			data.put("chart_config_trend_station", chart_config_trend_station);
			
			
			JSONObject chart_config_trendNew = new JSONObject();
			chart_config_trendNew.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_listNew = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config_listNew.add(config);
			}
			chart_config_trendNew.put("config", config_listNew);
			data.put("chart_config_trend_doctor", chart_config_trendNew);
			
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalFacilityImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","有卫生室的村数(个)","有卫生站的村比例(%)","无卫生室的村数(个)","新建卫生站的村数(个)","有执业医师的村数(个)","有执业医师的村比例(%)","未有行政村执业(助理)医师的村数(个)","新增执业医师的村数(个)"};
			for(int j=0;j<11;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<11;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<11;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-医疗设施落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-医疗设施落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorMedicalFacilityImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询一相当-医疗设施落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("MedicalFacilityImplementAnalysis", params);
			//查图表数据
			List<Map<String,Object>> chart_l = fairlyDao.queryMedicalImplementManagement3(params);
			List<Map<String,Object>> chart_r = fairlyDao.queryMedicalImplementManagement2(params);

			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryMedicalFacilityImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryMedicalFacilityImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend_station = new JSONObject();
			chart_config_trend_station.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", chart_l.get(i).get("S_GROUP"));
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend_station.put("config", config_list);
			data.put("chart_config_trend_station", chart_config_trend_station);
			
			JSONObject chart_config_trendNew = new JSONObject();
			chart_config_trendNew.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_listNew = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config_listNew.add(config);
			}
			chart_config_trendNew.put("config", config_listNew);
			data.put("chart_config_trend_doctor", chart_config_trendNew);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=MedicalFacilityImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","有卫生室的村数(个)","有卫生站的村比例(%)","无卫生室的村数(个)","新建卫生站的村数(个)","有执业医师的村数(个)","有执业医师的村比例(%)","未有行政村执业(助理)医师的村数(个)","新增执业医师的村数(个)"};
			for(int j=0;j<11;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<11;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<11;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-医疗设施落实分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-通广播电视落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorBroadcastImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询一相当-通广播电视落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("BroadcastImplementManagement", params);
			//查图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryBroadcastImplementManagement(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryBroadcastImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryBroadcastImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=BroadcastImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","全通广播电视的村数(个)","全通广播电视村比例(%)","年初未通广播电视的村数(个)","完成改造村数(个)","改造完成率(%)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-通广播电视落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-通广播电视落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorBroadcastImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询一相当-通广播电视落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("BroadcastImplementAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryBroadcastImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryBroadcastImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryBroadcastImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=BroadcastImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","全通广播电视的村数(个)","全通广播电视村比例(%)","年初未通广播电视的村数(个)","完成改造村数(个)","改造完成率(%)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-通广播电视查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-网络覆盖落实管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorNetImplementManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询一相当-网络覆盖落实管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("NetImplementManagement", params);
			//查图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryNetImplementManagement(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryNetImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryNetImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=NetImplementManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","网络全覆盖的村数(个)","网络全覆盖的村比例(%)","年初网络未全覆盖的村数(个)","完成改造村数(个)","改造完成率(%)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-网络覆盖落实管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 一相当-网络覆盖落实分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorNetImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询一相当-网络覆盖落实分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("NetImplementAnalysis", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = fairlyDao.queryNetImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fairlyDao.queryNetImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fairlyDao.queryNetImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_trend", chart_config_trend);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=NetImplementAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","村数(个)","网络全覆盖的村数(个)","网络全覆盖的村比例(%)","年初网络未全覆盖的村数(个)","完成改造村数(个)","改造完成率(%)"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("一相当-网络覆盖查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 预警监控-走访异常
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorAlarmedVisitExceptionMonitor(HttpServletRequest request,String id,String level,String year) {
		log.info("查询预警监控-走访异常===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("AlarmedVisitExceptionMonitor", params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			JSONObject table = new JSONObject();
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();

						//查图表数据
						List<Map<String,Object>> chart_list = warningDao.queryAlarmedImplementManagement(params);
						List<Map<String,Object>> chart_list2 = warningDao.queryAlarmedImplementManagement2(params);
						JSONObject chart_config_trend = new JSONObject();
						chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
						List<JSONObject> config_list = new ArrayList<JSONObject>();
						for(int i=0;i<chart_list.size();i++){
							JSONObject config = new JSONObject();
							config.put("name", chart_list.get(i).get("S_NAME"));
							config.put("group", chart_list.get(i).get("S_GROUP"));
							config.put("value", chart_list.get(i).get("S_VALUE"));
							config_list.add(config);
						}
						chart_config_trend.put("config", config_list);
						data.put("chart_config_not_visited", chart_config_trend);
						//new
						JSONObject chart_config_trendNew = new JSONObject();
						chart_config_trendNew.put("convert_method", "genChartLegendBarsConfig");
						List<JSONObject> config_listNew = new ArrayList<JSONObject>();
						for(int i=0;i<chart_list2.size();i++){
							JSONObject config = new JSONObject();
							config.put("name", chart_list2.get(i).get("S_NAME"));
							config.put("group", chart_list2.get(i).get("S_GROUP"));
							config.put("value", chart_list2.get(i).get("S_VALUE"));
							config_listNew.add(config);
						}
						chart_config_trendNew.put("config", config_listNew);
						data.put("chart_config_area_distribution", chart_config_trendNew);

						Map<String,Object> table_list_c = null;
						if(!"country".equals(level)){
							table_list_c = warningDao.queryAlarmedImplementManagementTableC(params);//合计
						}
						//帮扶责任人数
		Map<String,Object> table_list_a3 = warningDao.queryAlarmedA3(params);//合计
		if(table_list_a3.get("A4")!=null){
			table_list_c.put("A4", table_list_a3.get("A4"));
			
		}

		
						table.put("title", "统计表");
						table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedVisitExceptionMonitor");
						List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
						List<JSONObject> head1 = new ArrayList<JSONObject>();
						String[] content_arr = {"序号","行政区域","累计贫困户数","帮扶责任人数","一年内未被走访的累计贫困户数","比例(%)"};
						for(int j=0;j<6;j++){
							JSONObject head_1 =  new JSONObject();
							head_1.put("content", content_arr[j]);
							head1.add(head_1);
						}
						head_list.add(head1);
						table.put("head", head_list);
						//合计
						List<JSONObject> body0 =  new ArrayList<JSONObject>();
						if(table_list_c!=null){
							for(int n=0;n<6;n++){
								JSONObject head_content =  new JSONObject();
								if(n==0){
									head_content.put("content", "-");
								}else{
									head_content.put("content", table_list_c.get("A"+(n+1)));
								}
								body0.add(head_content);
							}
			 				body_list.add(body0);
						}
					List<Map<String,Object>> table_list = warningDao.queryAlarmedImplementManagementTable(params);
					
					List<Map<String,Object>> table_list_a = warningDao.queryAlarmedA4(params);
					if(table_list.size()!=0 ){
						if (table_list_a.size()!=0) {
							for (int i = 0; i < table_list.size(); i++) {
								
								table_list.get(i).put("A4",table_list_a.get(i).get("A4"));
							
						}
						}
						}
					
					
					//table体数据
					for(int m=0;m<table_list.size();m++){
						List<JSONObject> body =  new ArrayList<JSONObject>();
						for(int n=0;n<6;n++){
							JSONObject head_content =  new JSONObject();
							if(n==0){
								head_content.put("content", (m+1));
							}else{
								head_content.put("content", table_list.get(m).get("A"+(n+1)));
							}
							body.add(head_content);
						}
						body_list.add(body);
					}
					table.put("body", body_list);
					data.put("table", table);

			json.put("data", data);

		}catch(Exception e){
			e.printStackTrace();
			log.error("预警监控-走访异常查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 预警监控-贫困识别
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorAlarmedPoorExceptionMonitor(HttpServletRequest request,String id,String level,String year) {
		log.info("查询预警监控-贫困识别===========");
		//System.out.println("查询预警监控-贫困识别===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			   // System.out.println("key:="+entry.getKey()+" value:="+entry.getValue()[0]);
			}
			 String exceptionType = params.get("exception_type") == null ? "" : (String)params.get("exception_type");
			    StringBuffer etype = new StringBuffer();
			    List<Integer> exceptionTypeStr = new ArrayList<Integer>();
			    if(null != exceptionType && !"".equals(exceptionType)){
			    	String[] exceptionTypes = exceptionType.split(",");
			    	for(int i=0 ; i < exceptionTypes.length ; i++ ){
			    		if(i+1==exceptionTypes.length)
			    		    etype.append(BaseConstant.exceptionTypeMap.get(exceptionTypes[i]));
			    		else
			    			etype.append(BaseConstant.exceptionTypeMap.get(exceptionTypes[i])).append(",");
			    	}
			    	
			    	String[] etypes = etype.toString().split(",");
			    	for(String et : etypes){
			    		exceptionTypeStr.add(Integer.parseInt(et));
			    	}
			    	
			    	params.put("list",exceptionTypeStr);
			    }
			//用于导出的参数
			paramsPool.put("AlarmedPoorExceptionMonitor", params);
			//查图表数据
			List<Map<String,Object>> chart_list = alarmedPoorExceptionDao.queryExceptionScale(params);
			//柱状图
			List<Map<String,Object>> chart_list1 = alarmedPoorExceptionDao.queryExceptionArea(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = alarmedPoorExceptionDao.queryPoorIndetificationTableCount(params);//合计
			}
			List<Map<String,Object>> table_list = alarmedPoorExceptionDao.queryPoorIndetificationTable(params);//数据体
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartPieConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_exception_ratio", chart_config_trend);
			//new
			JSONObject chart_config_area_distribution = new JSONObject();
			chart_config_area_distribution.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_listnew = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list1.size() && i<5;i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list1.get(i).get("S_NAME"));
				config.put("group", "比对项数");
				config.put("value", chart_list1.get(i).get("S_VALUE"));
				config_listnew.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_list1.get(i).get("S_NAME"));
				config1.put("group", "异常项数");
				config1.put("value", chart_list1.get(i).get("S_VALUE1"));
				config_listnew.add(config1);
			}
			chart_config_area_distribution.put("config", config_listnew);
			data.put("chart_config_area_distribution", chart_config_area_distribution);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedPoorExceptionMonitor");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","建档立卡比对项数(条)","异常项数(条)","异常项比例(%)"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("预警监控-贫困识别查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		//System.out.println("json=== "+json.toString());
		return json.toString();
	}
	/**
	 * 预警监控-数据异常
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return 
	 */
	public String getDataMonitorAlarmedRecordsExceptionMonitor(HttpServletRequest request,String id,String level,String year) {
		log.info("查询预警监控-数据异常===========");
		//System.out.println("查询预警监控-数据异常===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			    //System.out.println("key:="+entry.getKey()+" value:="+entry.getValue()[0]);
			}
			
			 String exceptionType = params.get("exception_type") == null ? "" : (String)params.get("exception_type");
			    StringBuffer etype = new StringBuffer();
			    List<Integer> exceptionTypeStr = new ArrayList<Integer>();
			    if(null != exceptionType && !"".equals(exceptionType) ){
			    	String[] exceptionTypes = exceptionType.split(",");
			    	for(int i=0 ; i < exceptionTypes.length ; i++ ){
			    		if(i+1==exceptionTypes.length)
			    		    etype.append(BaseConstant.exceptionTypeMap.get(exceptionTypes[i]));
			    		else
			    			etype.append(BaseConstant.exceptionTypeMap.get(exceptionTypes[i])).append(",");
			    	}
			    	
			    	String[] etypes = etype.toString().split(",");
			    	for(String et : etypes){
			    		exceptionTypeStr.add(Integer.parseInt(et));
			    	}
			    	
			    	params.put("list",exceptionTypeStr);
			    }
			//用于导出的参数
			paramsPool.put("AlarmedRecordsExceptionMonitor", params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//查图表数据
			List<Map<String,Object>> chart_list = alarmedPoorExceptionDao.queryRecordsExceptionScale(params);
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartPieConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_exception_ratio", chart_config_trend);
			
			//柱子表数据
			List<Map<String,Object>> chart_list1 = alarmedPoorExceptionDao.queryRecordsExceptionArea(params);
			//new
			JSONObject chart_config_area_distribution = new JSONObject();
			chart_config_area_distribution.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_listnew = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list1.size() && i<5;i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list1.get(i).get("S_NAME"));
				config.put("group", "监控记录数");
				config.put("value", chart_list1.get(i).get("S_VALUE"));
				config_listnew.add(config);
				
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_list1.get(i).get("S_NAME"));
				config1.put("group", "异常项数");
				config1.put("value", chart_list1.get(i).get("S_VALUE1"));
				config_listnew.add(config1);
			}
			chart_config_area_distribution.put("config", config_listnew);
			data.put("chart_config_area_distribution", chart_config_area_distribution);


			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = alarmedPoorExceptionDao.queryRecordsTableCount(params);//合计
			}
			List<Map<String,Object>> table_list = alarmedPoorExceptionDao.queryRecordsTable(params);
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedRecordsExceptionMonitor");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","监测建档立卡记录数(条)","异常记录数(条)","异常记录比例(%)"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("预警监控-数据异常查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		//System.out.println("数据异常=="+json.toString());
		return json.toString();
	}
	/**
	 * 帮扶资金监控-异常监控
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorAlarmedMoneyExceptionMonitor(HttpServletRequest request,String id,String level,String year) {
		log.info("查询帮扶资金监控-异常监控===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			paramsPool.put("AlarmedMoneyExceptionMonitor", params);
			//查柱表数据
			List<Map<String,Object>> chart_list = alarmedPoorExceptionDao.queryAlarmedMoneyScale(params);
			//查柱表数据
			List<Map<String,Object>> chart_list1 = alarmedPoorExceptionDao.queryAlarmedMoneyArea(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = alarmedPoorExceptionDao.queryAlarmedMoneyTableCount(params);//合计
			}
			List<Map<String,Object>> table_list = alarmedPoorExceptionDao.queryAlarmedMoneyTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", "帮扶资金小于<30万的相对贫困村");
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_poor_country", chart_config_trend);
			//new
			JSONObject chart_config_trendNew = new JSONObject();
			chart_config_trendNew.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_listNew = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list1.size() && i<5;i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list1.get(i).get("S_NAME"));
				config.put("group", "相对贫困村数");
				config.put("value", chart_list1.get(i).get("S_VALUE"));
				config_listNew.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_list1.get(i).get("S_NAME"));
				config1.put("group", "未达标村数");
				config1.put("value", chart_list1.get(i).get("S_VALUE1"));
				config_listNew.add(config1);
			}
			chart_config_trendNew.put("config", config_listNew);
			data.put("chart_config_area_distribution", chart_config_trendNew);
			
			JSONObject table = new JSONObject();
			table.put("title", "异常统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AlarmedMoneyExceptionMonitor");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","相对贫困村数(个)","帮扶资金<30万的相对贫困村数(个)","比例(%)"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("帮扶资金监控-异常监控查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 贫困分析
	  * @param id 行政区域的ID
	 * @param level 层级
	 * @param scope 区域类型
	 * @param poor_attribute 贫困属性
	 * @param labor_attribute 劳力属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorPoorAanalysisPoorAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
		log.info("贫困分析===========");
		JSONObject json = new JSONObject();
		try{
			//System.out.println("id="+id+" level="+level+" year"+year+" poor_attribute=="+poor_attribute+"labor_attribute="+labor_attribute+" scope="+scope);
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("PAC", id);
			params.put("level", level);
			params.put("year", "2017");
			params.put("poor_attribute", poor_attribute);
			params.put("labor_attribute", labor_attribute);
			params.put("scope", scope);
			params.put("status", 1);
			
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			   // System.out.println("key: "+entry.getKey()+"  valule: "+entry.getValue()[0]);
			}
		    String poorReason = params.get("poor_reason") == null ? "" : (String)params.get("poor_reason");
		    List<String> poorReasonStr = new ArrayList<String>();
		    if(null != poorReason && !"".equals(poorReason)){
		    	String[] poorReasons = poorReason.split(",");
		    	for(String pr : poorReasons){
		    		if(!"other".equals(pr))
		    			poorReasonStr.add(BaseConstant.poorReasonMap.get(pr));
		    		else{
		    			poorReasonStr.add("Y12");
		    			poorReasonStr.add("Y99");//因多选框其他对应两个值，需特殊处理
		    		}
		    	}
		    	params.put("list",poorReasonStr);
		    }
			//用于导出的参数
			paramsPool.put("PoorAanalysisPoorAnalysis", params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//查饼状图数据 chart_listtest;//
			 final CountDownLatch latch = new CountDownLatch(11);//线程计数器
            //贫困户属性图
			 List<JSONObject> config_list = new ArrayList<JSONObject>();
			 Runnable rn = new AnalyseThread(params,"pab",config_list,latch,this);
			 Thread t = new Thread(rn) ;
			 taskExecutor.execute(t);
			 
			 List<JSONObject> config_list1 = new ArrayList<JSONObject>();
			 Runnable rn1 = new AnalyseThread(params,"poorReason",config_list1,latch,this);
			 Thread t1 = new Thread(rn1) ;
			 taskExecutor.execute(t1);
			 
			 List<JSONObject> config_list2 = new ArrayList<JSONObject>();
			 Runnable rn2 = new AnalyseThread(params,"pic",config_list2,latch,this);
			 Thread t2 = new Thread(rn2) ;
			 taskExecutor.execute(t2);
			 
			 List<JSONObject> config_list3 = new ArrayList<JSONObject>();
			 Runnable rn3 = new AnalyseThread(params,"pag",config_list3,latch,this);
			 Thread t3 = new Thread(rn3) ;
			 taskExecutor.execute(t3);
			 
			 List<JSONObject> config_list4 = new ArrayList<JSONObject>();
			 Runnable rn4 = new AnalyseThread(params,"psx",config_list4,latch,this);
			 Thread t4 = new Thread(rn4) ;
			 taskExecutor.execute(t4);
			 
			 List<JSONObject> config_list5 = new ArrayList<JSONObject>();
			 Runnable rn5 = new AnalyseThread(params,"pnt",config_list5,latch,this);
			 Thread t5 = new Thread(rn5) ;
			 taskExecutor.execute(t5);
			 
			 List<JSONObject> config_list6 = new ArrayList<JSONObject>();
			 Runnable rn6 = new AnalyseThread(params,"ppc",config_list6,latch,this);
			 Thread t6 = new Thread(rn6) ;
			 taskExecutor.execute(t6);
			 
			 List<JSONObject> config_list7 = new ArrayList<JSONObject>();
			 Runnable rn7 = new AnalyseThread(params,"pea",config_list7,latch,this);
			 Thread t7 = new Thread(rn7) ;
			 taskExecutor.execute(t7);
			 
			 List<JSONObject> config_list8 = new ArrayList<JSONObject>();
			 Runnable rn8 = new AnalyseThread(params,"phs",config_list8,latch,this);
			 Thread t8 = new Thread(rn8) ;
			 taskExecutor.execute(t8);
			 
			 List<JSONObject> config_list9 = new ArrayList<JSONObject>();
			 Runnable rn9 = new AnalyseThread(params,"pls",config_list9,latch,this);
			 Thread t9 = new Thread(rn9) ;
			 taskExecutor.execute(t9);
			 
			 List<JSONObject> config_list10 = new ArrayList<JSONObject>();
			 Runnable rn10 = new AnalyseThread(params,"pws",config_list10,latch,this);
			 Thread t10 = new Thread(rn10) ;
			 taskExecutor.execute(t10);
			 try {
					log.info("贫困分析--等待11个查询子线程执行完毕...");
		            latch.await(60000,TimeUnit.MILLISECONDS);//最多等待60超时
		            log.info("贫困分析--11个查询子线程已经执行完毕");
		            log.info("贫困分析--继续执行主线程返回结果集");
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
			 
			JSONObject chart_config_poor_attribute = new JSONObject();
			chart_config_poor_attribute.put("convert_method", "genChartPieConfig");
			chart_config_poor_attribute.put("config", config_list);
			data.put("chart_config_poor_attribute", chart_config_poor_attribute);
			
			JSONObject chart_config_poor_reason = new JSONObject();
			chart_config_poor_reason.put("convert_method", "genChartPieConfig");
			chart_config_poor_reason.put("config", config_list1);
			data.put("chart_config_poor_reason", chart_config_poor_reason);
				
			//收入状况图
			JSONObject chart_config_income = new JSONObject();
			chart_config_income.put("convert_method", "genChartPieConfig");
			chart_config_income.put("config", config_list2);
			data.put("chart_config_income", chart_config_income);
		
			//年龄图
			JSONObject chart_config_age = new JSONObject();
			chart_config_age.put("convert_method", "genChartPieConfig");
			chart_config_age.put("config", config_list3);
			data.put("chart_config_age", chart_config_age);
		
			//性别图

			JSONObject chart_config_sex = new JSONObject();
			chart_config_sex.put("convert_method", "genChartPieConfig");
			chart_config_sex.put("config", config_list4);
			data.put("chart_config_sex", chart_config_sex);
			
			//民族图

			JSONObject chart_config_nation = new JSONObject();
			chart_config_nation.put("convert_method", "genChartPieConfig");
			chart_config_nation.put("config", config_list5);
			data.put("chart_config_nation", chart_config_nation);
		
			//政治面貌图
			
			JSONObject chart_config_political_affiliation = new JSONObject();
			chart_config_political_affiliation.put("convert_method", "genChartPieConfig");
			chart_config_political_affiliation.put("config", config_list6);
			data.put("chart_config_political_affiliation", chart_config_political_affiliation);
		
			//文化程度图
		
			JSONObject chart_config_edu_status = new JSONObject();
			chart_config_edu_status.put("convert_method", "genChartPieConfig");
			chart_config_edu_status.put("config", config_list7);
			data.put("chart_config_edu_status", chart_config_edu_status);
			
			//健康状况图

			JSONObject chart_config_health_status = new JSONObject();
			chart_config_health_status.put("convert_method", "genChartPieConfig");
			chart_config_health_status.put("config", config_list8);
			data.put("chart_config_health_status", chart_config_health_status);
	
			//劳动力状况图

			JSONObject chart_config_labor_status = new JSONObject();
			chart_config_labor_status.put("convert_method", "genChartPieConfig");
			chart_config_labor_status.put("config", config_list9);
			data.put("chart_config_labor_status", chart_config_labor_status);
		
			//务工状况图
		
			JSONObject chart_config_employment_status = new JSONObject();
			chart_config_employment_status.put("convert_method", "genChartPieConfig");
			chart_config_employment_status.put("config", config_list10);
			data.put("chart_config_employment_status", chart_config_employment_status);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("贫困分析信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
//		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-村道硬底化
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryRoad(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-村道硬底化信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisRoad", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.queryRoadImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.queryRoadImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.queryRoadImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.queryRoadImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisRoad");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","项目数(个)","里程(公里)","投入资金(万元)"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-村道硬底化查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-饮水工程
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryWater(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-饮水工程信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
				Map.Entry<String,String[]> entry = entries.next();
				params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisWater", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.queryWaterImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.queryWaterImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.queryWaterImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.queryWaterImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisWater");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","项目数(个)","受益户数","受益人数","投入资金(万元)"};
			for(int j=0;j<6;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-饮水工程查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-文体设施
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryRecreationSport(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-文体设施信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
				Map.Entry<String,String[]> entry = entries.next();
				params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisSport", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.querySportImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.querySportImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.querySportImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.querySportImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisSport");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","rowspan"};
			String[] content_arr = {"序号","行政区域","项目数(个)","投入资金(万元)"};
			for(int j=0;j<4;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 5);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"合计","文化室","篮球场","休闲运动设施","其他"};
			for(int k=0;k<5;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-文体设施查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-卫生设施
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryHygiene(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-卫生设施信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
				Map.Entry<String,String[]> entry = entries.next();
				params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisHygiene", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.queryHygieneImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.queryHygieneImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.queryHygieneImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.queryHygieneImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisHygiene");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","rowspan"};
			String[] content_arr = {"序号","行政区域","项目数(个)","投入资金(万元)"};
			for(int j=0;j<4;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 5);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"合计","卫生站","公厕","垃圾池","其他"};
			for(int k=0;k<5;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<8;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-文体设施查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-路灯安装
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryLamp(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-路灯安装信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
				Map.Entry<String,String[]> entry = entries.next();
				params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisLamp", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.queryLampImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.queryLampImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.queryLampImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.queryLampImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisLamp");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","项目数(个)","路灯数(盏)","投入资金(万元)"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-饮水工程查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-农田水利
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryFarm(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-农田水利信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
				Map.Entry<String,String[]> entry = entries.next();
				params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisFarm", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.queryFarmImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.queryFarmImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.queryFarmImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.queryFarmImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisFarm");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","rowspan"};
			String[] content_arr = {"序号","行政区域","项目数(个)","投入资金(万元)"};
			for(int j=0;j<4;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 7);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"合计","三面光水渠","机耕路","机耕桥","河提(坝)","排灌站","其他"};
			for(int k=0;k<7;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<10;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<10;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-农田水利查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-公共设施
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryPublicFacility(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-公共设施信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
				Map.Entry<String,String[]> entry = entries.next();
				params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisFacility", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.queryFacilityImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.queryFacilityImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.queryFacilityImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.queryFacilityImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisFacility");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","项目数(个)","投入资金(万元)"};
			for(int j=0;j<4;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<4;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<4;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-公共设施查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-集体经济
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryCollectiveEconomy(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-集体经济信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
				Map.Entry<String,String[]> entry = entries.next();
				params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisEconomy", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.queryEconomyImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.queryEconomyImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.queryEconomyImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.queryEconomyImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisEconomy");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","项目数(个)","投入资金(万元)","项目收益(万元)"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-集体经济 查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	/**
	 * 村扶贫项目分析-教育教学
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @param scope 区域范围
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorProjectCountryEdu(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询村扶贫项目分析-教育教学信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
				Map.Entry<String,String[]> entry = entries.next();
				params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//用于导出的参数
			paramsPool.put("AnalysisEdu", params);
			//查饼图表数据
			List<Map<String,Object>> chart_list = countryDao.queryEduImplementManagement(params);
			List<Map<String,Object>> chart_r = countryDao.queryEduImplementManagement2(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = countryDao.queryEduImplementManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = countryDao.queryEduImplementManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_list.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_list.get(i).get("S_NAME"));
				config.put("group", chart_list.get(i).get("S_GROUP"));
				config.put("value", chart_list.get(i).get("S_VALUE"));
				config_list.add(config);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_money_trend", chart_config_trend);
			
			JSONObject chart_config_progress = new JSONObject();
			chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
			}
			chart_config_progress.put("config", config2_list);
			data.put("chart_config_area_distribution", chart_config_progress);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=AnalysisEdu");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"序号","行政区域","项目数(个)","投入资金(万元)"};
			for(int j=0;j<4;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<4;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<4;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村扶贫项目分析-教育教学查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}

	
	 private Object getValue(Map map,String key){
	    	return CommonUtil.getMapValue(map,key);
		}
	 /**
		 * 项目监控-到户分析-产业扶贫
		 * @param id 行政区域的ID
		 * @param level 层级
		 * @param scope 区域类型
		 * @param poor_attribute 贫困属性
		 * @param labor_attribute 劳力属性
		 * 
		 *  @return
		 *  add by yxt
		 */
		public String getDataMonitorProjectFamilyIndustry(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute)
		{
			log.info("查询项目监控-到户分析-产业扶贫===========");
			long beginTime = System.currentTimeMillis();
			JSONObject json = new JSONObject();
			try{
				//查询数据
				Map<String, Object> params =new HashMap<String, Object>();
				params.putAll(Constants.dateContants);
				params.put("id", id);
				params.put("level", level);
				params.put("year", year);
				params.put("poor_attribute", poor_attribute);
				params.put("labor_attribute", labor_attribute);
				params.put("scope", scope);
				
				
				params.put("year_a",Integer.parseInt(year+"01"));
				params.put("year_b",Integer.parseInt(year+"12"));
				//用于导出的参数
				paramsPool.put("ProjectFamilyIndustry", params);
				//查柱状图数据   config_money_trend
				List<Map<String,Object>> chart_list = fpDao.queryMoneyTrendChart(params);
				//查区域分布数据   chart_config_area_distribution
				List<Map<String,Object>> chart_list_area = fpDao.queryMoneyTrendChartByArea(params);
				//查table数据
				Map<String,Object> table_list_c = null;
				if(!"country".equals(level)){
					table_list_c = fpDao.queryMoneyTrendAllTableC(params);//合计
				}
				List<Map<String,Object>> table_list = fpDao.queryMoneyTrendAllTable(params);
				
				json.put("code", 0);
				json.put("msg", "获取信息成功");
				JSONObject data = new JSONObject();
				
				JSONObject chart_config_trend = new JSONObject();
				chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list.get(i).get("S_NAME"));
					config.put("group", chart_list.get(i).get("S_GROUP"));
					config.put("value", chart_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_trend.put("config", config_list);
				data.put("chart_config_money_trend", chart_config_trend);
				
				JSONObject chart_area_trend_area = new JSONObject();
				chart_area_trend_area.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list_area = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list_area.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list_area.get(i).get("S_NAME"));
					config.put("group", chart_list_area.get(i).get("S_GROUP"));
					config.put("value", chart_list_area.get(i).get("S_VALUE"));
					config_list_area.add(config);
				}
				chart_area_trend_area.put("config", config_list_area);
				data.put("chart_config_area_distribution", chart_area_trend_area);

				
				JSONObject table = new JSONObject();
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectFamilyIndustry	");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan","colspan"};
				String[] content_arr = {"序号","行政区域","项目数（个）","投入金额（万元）","项目收益（万元）","项目受益"};
				for(int j=0;j<content_arr.length;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				List<JSONObject> head2 = new ArrayList<JSONObject>();
				String[] content_arr2 = {"户数","人数"};
				for(int k=0;k<content_arr2.length;k++){
					JSONObject head_2 =  new JSONObject();
					head_2.put("content", content_arr2[k]);
					head2.add(head_2);
				}
				head_list.add(head2);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null){
					for(int n=0;n<7;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<7;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				
				json.put("data", data);
			}catch(Exception e){
				e.printStackTrace();
				log.error("查询项目监控-到户分析-产业扶贫信息查询失败异常信息："+e.getMessage());
				json.put("code", 1);
				json.put("msg", "数据处理异常，获取信息失败!");			
			}
			long endTime = System.currentTimeMillis();
			if (log.isInfoEnabled()) {
				log.info("--------获取数据耗时:" + (endTime-beginTime));		
			}
			return json.toString();	
		}
		/**
		 * 项目监控-到户分析-金融扶贫
		 * @param id 行政区域的ID
		 * @param level 层级
		 * @param scope 区域类型
		 * @param poor_attribute 贫困属性
		 * 
		 * 
		 *  @return
		 *  add by yxt
		 */
		public String getDataMonitorProjectFamilyFinance(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute)
		{
			log.info("查询项目监控-到户分析-金融扶贫===========");
			JSONObject json = new JSONObject();
			try{
				//查询数据
				Map<String, Object> params =new HashMap<String, Object>();
				params.putAll(Constants.dateContants);
				params.put("id", id);
				params.put("level", level);
				params.put("year", year);
				params.put("poor_attribute", poor_attribute);
				params.put("scope", scope);
				params.put("year_a",Integer.parseInt(year+"01"));
				params.put("year_b",Integer.parseInt(year+"12"));
				//用于导出的参数
				paramsPool.put("ProjectFamilyFinance", params);
				//查柱状图数据   config_money_trend
				List<Map<String,Object>> chart_list = fpDao.queryFinanceTrendChart(params);
				//查区域分布数据   chart_config_area_distribution
				List<Map<String,Object>> chart_list_area = fpDao.queryFinanceTrendChartByArea(params);
				//查table数据
				Map<String,Object> table_list_c = null;
				if(!"country".equals(level)){
					table_list_c = fpDao.queryFinanceTrendAllTableC(params);//合计
				}
				List<Map<String,Object>> table_list = fpDao.queryFinanceTrendAllTable(params);
				
				json.put("code", 0);
				json.put("msg", "获取信息成功");
				JSONObject data = new JSONObject();
				
				JSONObject chart_config_trend = new JSONObject();
				chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list.get(i).get("S_NAME"));
					config.put("group", chart_list.get(i).get("S_GROUP"));
					config.put("value", chart_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_trend.put("config", config_list);
				data.put("chart_config_money_trend", chart_config_trend);
				
				JSONObject chart_config_progress = new JSONObject();
				chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config2_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list_area.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list_area.get(i).get("S_NAME"));
					config.put("group", chart_list_area.get(i).get("S_GROUP"));
					config.put("value", chart_list_area.get(i).get("S_VALUE"));
					config2_list.add(config);
				}
				chart_config_progress.put("config", config2_list);
				data.put("chart_config_area_distribution", chart_config_progress);
				

				JSONObject table = new JSONObject();
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectFamilyFinance");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				String[] content_arr = {"序号","行政区域","贷款户数","贷款项目数（个）","贷款金融（万元）","贷款利息（万元）"};
				for(int j=0;j<content_arr.length;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null){
					for(int n=0;n<6;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<6;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				
				json.put("data", data);
			}catch(Exception e){
				e.printStackTrace();
				log.error("查询项目监控-到户分析-产业扶贫信息查询失败异常信息："+e.getMessage());
				json.put("code", 1);
				json.put("msg", "数据处理异常，获取信息失败!");			
			}
//			//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
			return json.toString();	
		}
		/**
		 * 项目监控-到户分析-住房改造
		 * @param id 行政区域的ID
		 * @param level 层级
		 * @param scope 区域类型
		 * @param poor_attribute 贫困属性
		 * 
		 * 
		 *  @return
		 *  add by yxt
		 */
		public String getDataMonitorProjectFamilyHouse(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute)
		{
			log.info("查询项目监控-到户分析-住房改造===========");
			JSONObject json = new JSONObject();
			try{
				//查询数据
				Map<String, Object> params =new HashMap<String, Object>();
				params.putAll(Constants.dateContants);
				params.put("id", id);
				params.put("level", level);
				params.put("year", year);
				params.put("poor_attribute", poor_attribute);
				params.put("scope", scope);
				params.put("year_a",Integer.parseInt(year+"01"));
				params.put("year_b",Integer.parseInt(year+"12"));
				//用于导出的参数
				paramsPool.put("ProjectFamilyHouse", params);
				//查柱状图数据   config_money_trend
				List<Map<String,Object>> chart_list = fpDao.queryHouseTrendChart(params);
				//查区域分布数据   chart_config_area_distribution
				List<Map<String,Object>> chart_list_area = fpDao.queryHouseTrendChartByArea(params);
				//查table数据
				Map<String,Object> table_list_c = null;
				if(!"country".equals(level)){
					table_list_c = fpDao.queryHouseTrendAllTableC(params);//合计
				}
				List<Map<String,Object>> table_list = fpDao.queryHouseTrendAllTable(params);
				
				json.put("code", 0);
				json.put("msg", "获取信息成功");
				JSONObject data = new JSONObject();
				
				JSONObject chart_config_trend = new JSONObject();
				chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list.get(i).get("S_NAME"));
					config.put("group", chart_list.get(i).get("S_GROUP"));
					config.put("value", chart_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_trend.put("config", config_list);
				data.put("chart_config_money_trend", chart_config_trend);
				
				JSONObject chart_area_trend_area = new JSONObject();
				chart_area_trend_area.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list_area = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list_area.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list_area.get(i).get("S_NAME"));
					config.put("group", chart_list_area.get(i).get("S_GROUP"));
					config.put("value", chart_list_area.get(i).get("S_VALUE"));
					config_list_area.add(config);
				}
				chart_area_trend_area.put("config", config_list_area);
				data.put("chart_config_area_distribution", chart_area_trend_area);
			
				
				JSONObject table = new JSONObject();
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectFamilyHouse");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				String[] content_arr = {"序号","行政区域","计划改造户数","完成改造户数","完成比例（%）","项目数（个）","改造投入金额（万元）"};
				for(int j=0;j<content_arr.length;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null){
					for(int n=0;n<7;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<7;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				
				json.put("data", data);
			}catch(Exception e){
				e.printStackTrace();
				log.error("查询项目监控-到户分析-产业扶贫信息查询失败异常信息："+e.getMessage());
				json.put("code", 1);
				json.put("msg", "数据处理异常，获取信息失败!");			
			}
//			//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
			return json.toString();	
		}
		
		/**
		 * 项目监控-到户分析-资产扶贫
		 * @param id 行政区域的ID
		 * @param level 层级
		 * @param scope 区域类型
		 * @param poor_attribute 贫困属性
		 * 
		 * 
		 *  @return
		 *  add by yxt
		 */
		public String getDataMonitorProjectFamilyProperty(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute)
		{
			log.info("查询项目监控-到户分析-资产扶贫===========");
			JSONObject json = new JSONObject();
			try{
				//查询数据
				Map<String, Object> params =new HashMap<String, Object>();
				params.putAll(Constants.dateContants);
				params.put("id", id);
				params.put("level", level);
				params.put("year", year);
				params.put("poor_attribute", poor_attribute);
				params.put("scope", scope);
				params.put("year_a",Integer.parseInt(year+"01"));
				params.put("year_b",Integer.parseInt(year+"12"));
				//用于导出的参数
				paramsPool.put("ProjectFamilyProperty", params);
				//查柱状图数据   config_money_trend
				List<Map<String,Object>> chart_list = fpDao.queryPropertyTrendChart(params);
				//查区域分布数据   chart_config_area_distribution
				List<Map<String,Object>> chart_list_area = fpDao.queryPropertyTrendChartByArea(params);
				//查table数据
				Map<String,Object> table_list_c = null;
				if(!"country".equals(level)){
					table_list_c = fpDao.queryPropertyTrendAllTableC(params);//合计
				}
				List<Map<String,Object>> table_list = fpDao.queryPropertyTrendAllTable(params);
				
				json.put("code", 0);
				json.put("msg", "获取信息成功");
				JSONObject data = new JSONObject();
				
				JSONObject chart_config_trend = new JSONObject();
				chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list.get(i).get("S_NAME"));
					config.put("group", chart_list.get(i).get("S_GROUP"));
					config.put("value", chart_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_trend.put("config", config_list);
				data.put("chart_config_status", chart_config_trend);
				  
				JSONObject chart_config_progress = new JSONObject();
				chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config2_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list_area.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list_area.get(i).get("S_NAME"));
					config.put("group", chart_list_area.get(i).get("S_GROUP"));
					config.put("value", chart_list_area.get(i).get("S_VALUE"));
					config2_list.add(config);
				}
				chart_config_progress.put("config", config2_list);
				data.put("chart_config_area_distribution", chart_config_progress);
				
			
				
				JSONObject table = new JSONObject();
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectFamilyProperty");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				String[] content_arr = {"序号","行政区域","参与户数","项目数（个）","投入金额（万元）","项目收益（万元）"};
				for(int j=0;j<content_arr.length;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null){
					for(int n=0;n<6;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<6;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				
				json.put("data", data);
			}catch(Exception e){
				e.printStackTrace();
				log.error("查询项目监控-到户分析-产业扶贫信息查询失败异常信息："+e.getMessage());
				json.put("code", 1);
				json.put("msg", "数据处理异常，获取信息失败!");			
			}
//			//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
			return json.toString();	
		}
		/**
		 * 项目监控-到户分析-慰问扶贫
		 * @param id 行政区域的ID
		 * @param level 层级
		 * @param scope 区域类型
		 * @param poor_attribute 贫困属性
		 * 
		 * 
		 *  @return
		 *  add by yxt
		 */
		public String getDataMonitorProjectFamilyVisit(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute)
		{
			log.info("查询项目监控-到户分析-慰问扶贫===========");
			JSONObject json = new JSONObject();
			try{
				//查询数据
				Map<String, Object> params =new HashMap<String, Object>();
				params.putAll(Constants.dateContants);
				params.put("id", id);
				params.put("level", level);
				params.put("year", year);
				params.put("poor_attribute", poor_attribute);
				params.put("scope", scope);
				params.put("year_a",Integer.parseInt(year+"01"));
				params.put("year_b",Integer.parseInt(year+"12"));
				//用于导出的参数
				paramsPool.put("ProjectFamilyVisit", params);
				//查柱状图数据   config_money_trend
				List<Map<String,Object>> chart_list = fpDao.queryVisitTrendChart(params);
				//查区域分布数据   chart_config_area_distribution
				List<Map<String,Object>> chart_list_area = fpDao.queryVisitTrendChartByArea(params);
				//查table数据
				Map<String,Object> table_list_c = null;
				if(!"country".equals(level)){
					table_list_c = fpDao.queryVisitTrendAllTableC(params);//合计
				}
				List<Map<String,Object>> table_list = fpDao.queryVisitTrendAllTable(params);
				
				json.put("code", 0);
				json.put("msg", "获取信息成功");
				JSONObject data = new JSONObject();
				
				JSONObject chart_config_trend = new JSONObject();
				chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list.get(i).get("S_NAME"));
					config.put("group", chart_list.get(i).get("S_GROUP"));
					config.put("value", chart_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_trend.put("config", config_list);
				data.put("chart_config_money_trend", chart_config_trend);
				
				JSONObject chart_area_trend_area = new JSONObject();
				chart_area_trend_area.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list_area = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list_area.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list_area.get(i).get("S_NAME"));
					config.put("group", chart_list_area.get(i).get("S_GROUP"));
					config.put("value", chart_list_area.get(i).get("S_VALUE"));
					config_list_area.add(config);
				}
				chart_area_trend_area.put("config", config_list_area);
				data.put("chart_config_area_distribution", chart_area_trend_area);
			
				JSONObject table = new JSONObject();
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectFamilyVisit");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan"};
				String[] content_arr = {"序号","行政区域","慰问户数","项目数（个）","慰问金额（万元）"};
				for(int j=0;j<content_arr.length;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null){
					for(int n=0;n<5;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<5;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				
				json.put("data", data);
			}catch(Exception e){
				e.printStackTrace();
				log.error("查询项目监控-到户分析-产业扶贫信息查询失败异常信息："+e.getMessage());
				json.put("code", 1);
				json.put("msg", "数据处理异常，获取信息失败!");			
			}
//			//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
			return json.toString();	
		}
		/**
		 * 项目监控-到户分析-就业扶贫
		 * @param id 行政区域的ID
		 * @param level 层级
		 * @param scope 区域类型
		 * @param poor_attribute 贫困属性
		 * 
		 * 
		 *  @return
		 *  add by yxt
		 */
		public String getDataMonitorProjectFamilyEmployment(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute)
		{
			log.info("查询项目监控-到户分析-就业扶贫===========");
			JSONObject json = new JSONObject();
			try{
				//查询数据
				Map<String, Object> params =new HashMap<String, Object>();
				params.putAll(Constants.dateContants);
				params.put("id", id);
				params.put("level", level);
				params.put("year", year);
				params.put("poor_attribute", poor_attribute);
				params.put("scope", scope);
				params.put("year_a",Integer.parseInt(year+"01"));
				params.put("year_b",Integer.parseInt(year+"12"));
				//用于导出的参数
				paramsPool.put("ProjectFamilyEmployment", params);
				//查柱状图数据   config_money_trend
				List<Map<String,Object>> chart_list = fpDao.queryEmploymentTrendChart(params);
				//查区域分布数据   chart_config_area_distribution
				List<Map<String,Object>> chart_list_area = fpDao.queryEmploymentTrendChartByArea(params);
				//查table数据
				Map<String,Object> table_list_c = null;
				if(!"country".equals(level)){
					table_list_c = fpDao.queryEmploymentTrendAllTableC(params);//合计
				}
				List<Map<String,Object>> table_list = fpDao.queryEmploymentTrendAllTable(params);
				
				json.put("code", 0);
				json.put("msg", "获取信息成功");
				JSONObject data = new JSONObject();
				
				JSONObject chart_config_trend = new JSONObject();
				chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list.get(i).get("S_NAME"));
					config.put("group", chart_list.get(i).get("S_GROUP"));
					config.put("value", chart_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_trend.put("config", config_list);
				data.put("chart_config_status", chart_config_trend);
				
				JSONObject chart_area_trend_area = new JSONObject();
				chart_area_trend_area.put("convert_method", "genChartLegendBarsConfig");	
				List<JSONObject> config_list_area = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list_area.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list_area.get(i).get("S_NAME"));
					config.put("group", chart_list_area.get(i).get("S_GROUP"));
					config.put("value", chart_list_area.get(i).get("S_VALUE"));
					config_list_area.add(config);
				}
				chart_area_trend_area.put("config", config_list_area);
				data.put("chart_config_area_distribution", chart_area_trend_area);
				
				
				
				JSONObject table = new JSONObject();
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectFamilyEmployment");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				String[] content_arr = {"序号","行政区域","项目数（个）","参与户数","就业人数","就业收益（万元）","年人均就业收入（万元）"};
				for(int j=0;j<content_arr.length;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null){
					for(int n=0;n<7;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<7;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				
				json.put("data", data);
			}catch(Exception e){
				e.printStackTrace();
				log.error("查询项目监控-到户分析-产业扶贫信息查询失败异常信息："+e.getMessage());
				json.put("code", 1);
				json.put("msg", "数据处理异常，获取信息失败!");			
			}
 //System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
			return json.toString();	
		}
		/**
		 * 项目监控-到户分析-技能培训
		 * @param id 行政区域的ID
		 * @param level 层级
		 * @param scope 区域类型
		 * @param poor_attribute 贫困属性
		 * 
		 * 
		 *  @return
		 *  add by yxt
		 */
		public String getDataMonitorProjectFamilySkill(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute)
		{
			log.info("查询项目监控-到户分析-技能培训===========");
			JSONObject json = new JSONObject();
			try{
				//查询数据
				Map<String, Object> params =new HashMap<String, Object>();
				params.putAll(Constants.dateContants);
				params.put("id", id);
				params.put("level", level);
				params.put("year", year);
				params.put("poor_attribute", poor_attribute);
				params.put("scope", scope);
				params.put("year_a",Integer.parseInt(year+"01"));
				params.put("year_b",Integer.parseInt(year+"12"));
				//用于导出的参数
				paramsPool.put("ProjectFamilySkill", params);
				//查柱状图数据   config_money_trend
				List<Map<String,Object>> chart_list = fpDao.querySkillTrendChart(params);
				//查区域分布数据   chart_config_area_distribution
				List<Map<String,Object>> chart_list_area = fpDao.querySkillTrendChartByArea(params);
				//查table数据
				Map<String,Object> table_list_c = null;
				if(!"country".equals(level)){
					table_list_c = fpDao.querySkillTrendAllTableC(params);//合计
				}
				List<Map<String,Object>> table_list = fpDao.querySkillTrendAllTable(params);
				
				json.put("code", 0);
				json.put("msg", "获取信息成功");
				JSONObject data = new JSONObject();
				
				JSONObject chart_config_trend = new JSONObject();
				chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list.get(i).get("S_NAME"));
					config.put("group", chart_list.get(i).get("S_GROUP"));
					config.put("value", chart_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_trend.put("config", config_list);
				data.put("chart_config_status", chart_config_trend);
				
				JSONObject chart_config_progress = new JSONObject();
				chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config2_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list_area.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list_area.get(i).get("S_NAME"));
					config.put("group", chart_list_area.get(i).get("S_GROUP"));
					config.put("value", chart_list_area.get(i).get("S_VALUE"));
					config2_list.add(config);
				}
				chart_config_progress.put("config", config2_list);
				data.put("chart_config_area_distribution", chart_config_progress);
				
				
				JSONObject table = new JSONObject();
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectFamilySkill");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				String[] content_arr = {"序号","行政区域","在家务农人数","项目数（个）","参与户数","培训人数","投入资金（万元）"};
				for(int j=0;j<content_arr.length;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null){
					for(int n=0;n<7;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<7;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				
				json.put("data", data);
			}catch(Exception e){
				e.printStackTrace();
				log.error("查询项目监控-到户分析-产业扶贫信息查询失败异常信息："+e.getMessage());
				json.put("code", 1);
				json.put("msg", "数据处理异常，获取信息失败!");			
			}
//			//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
			return json.toString();	
		}
		/**
		 * 项目监控-到户分析-教育扶贫
		 * @param id 行政区域的ID
		 * @param level 层级
		 * @param scope 区域类型
		 * @param poor_attribute 贫困属性
		 * 
		 * 
		 *  @return
		 *  add by yxt
		 */
		public String getDataMonitorProjectFamilyEdu(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute)
		{
			log.info("查询项目监控-到户分析-教育扶贫===========");
			JSONObject json = new JSONObject();
			try{
				//查询数据
				Map<String, Object> params =new HashMap<String, Object>();
				params.putAll(Constants.dateContants);
				params.put("id", id);
				params.put("level", level);
				params.put("year", year);
				params.put("poor_attribute", poor_attribute);
				params.put("scope", scope);
				params.put("year_a",Integer.parseInt(year+"01"));
				params.put("year_b",Integer.parseInt(year+"12"));
				//用于导出的参数
				paramsPool.put("ProjectFamilyEdu", params);
				//查柱状图数据   config_money_trend
				List<Map<String,Object>> chart_list = fpDao.queryEduTrendChart(params);
				//查区域分布数据   chart_config_area_distribution
				List<Map<String,Object>> chart_list_area = fpDao.queryEduTrendChartByArea(params);
				//查table数据
				Map<String,Object> table_list_c = null;
				if(!"country".equals(level)){
					table_list_c = fpDao.queryEduTrendAllTableC(params);//合计
				}
				List<Map<String,Object>> table_list = fpDao.queryEduTrendAllTable(params);
				
				json.put("code", 0);
				json.put("msg", "获取信息成功");
				JSONObject data = new JSONObject();
				
				JSONObject chart_config_trend = new JSONObject();
				chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list.get(i).get("S_NAME"));
					config.put("group", chart_list.get(i).get("S_GROUP"));
					config.put("value", chart_list.get(i).get("S_VALUE"));
					config_list.add(config);
				}
				chart_config_trend.put("config", config_list);
				data.put("chart_config_status", chart_config_trend);
				
				JSONObject chart_config_progress = new JSONObject();
				chart_config_progress.put("convert_method", "genChartLegendBarsConfig");
				List<JSONObject> config2_list = new ArrayList<JSONObject>();
				for(int i=0;i<chart_list_area.size();i++){
					JSONObject config = new JSONObject();
					config.put("name", chart_list_area.get(i).get("S_NAME"));
					config.put("group", chart_list_area.get(i).get("S_GROUP"));
					config.put("value", chart_list_area.get(i).get("S_VALUE"));
					config2_list.add(config);
				}
				chart_config_progress.put("config", config2_list);
				data.put("chart_config_area_status", chart_config_progress);
				
				
				JSONObject table = new JSONObject();
				table.put("title", "统计表");
				table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id=ProjectFamilyEdu");
				List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
				List<JSONObject> head1 = new ArrayList<JSONObject>();
				String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","rowspan","rowspan"};
				String[] content_arr = {"序号","行政区域","项目数（个）","参与户数","教育扶贫人数","投入资金（万元）"};
				for(int j=0;j<content_arr.length;j++){
					JSONObject head_1 =  new JSONObject();
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
					head1.add(head_1);
				}
				head_list.add(head1);
				table.put("head", head_list);
				List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
				//合计
				List<JSONObject> body0 =  new ArrayList<JSONObject>();
				if(table_list_c!=null){
					for(int n=0;n<6;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", "-");
						}else{
							head_content.put("content", table_list_c.get("A"+(n+1)));
						}
						body0.add(head_content);
					}
					body_list.add(body0);
				}
				//table体数据
				for(int m=0;m<table_list.size();m++){
					List<JSONObject> body =  new ArrayList<JSONObject>();
					for(int n=0;n<6;n++){
						JSONObject head_content =  new JSONObject();
						if(n==0){
							head_content.put("content", (m+1));
						}else{
							head_content.put("content", table_list.get(m).get("A"+(n+1)));
						}
						body.add(head_content);
					}
					body_list.add(body);
				}
				table.put("body", body_list);
				data.put("table", table);
				
				json.put("data", data);
			}catch(Exception e){
				e.printStackTrace();
				log.error("查询项目监控-到户分析-教育扶贫信息查询失败异常信息："+e.getMessage());
				json.put("code", 1);
				json.put("msg", "数据处理异常，获取信息失败!");			
			}
//			//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
			return json.toString();	
		}
}