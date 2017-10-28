package com.aspire.birp.modules.antiPoverty.service;

import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.common.utils.ClobToStringUtls;
import com.aspire.birp.modules.antiPoverty.dao.CountryFileDao;
import com.aspire.birp.modules.antiPoverty.model.poorFile.BaseListMode;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileBasic.CountryFileBasic;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileBasic.CountryFileBasicData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileBusiness.CountryFileBusiness;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileBusiness.CountryFileBusinessData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileDevelopmentStatus.CountryFileDevelopmentStatus;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileDevelopmentStatus.CountryFileDevelopmentStatusData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileEliminatePath.CountryFileEliminatePath;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileEliminatePath.CountryFileEliminatePathData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileEliminatePath.CountryFileListBase;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileImplement.CountryFileImpBase;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileImplement.CountryFileImplement;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileImplement.CountryFileImplementData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileMeetingNewsDetail.CountryFileMeetingNewsDetail;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileMeetingNewsDetail.CountryFileMeetingNewsDetailData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileNews.CountryFileBase;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileNews.CountryFileNews;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileNews.CountryFileNewsData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileNewsDetail.CountryFileNewsDetail;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileNewsDetail.CountryFileNewsDetailData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFilePlan.CountryFilePlan;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFilePlan.CountryFilePlanBase;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFilePlan.CountryFilePlanData;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.antiPoverty.utils.CountryMapTemplate;
import com.aspire.birp.modules.antiPoverty.utils.JsonFormatUtils;
import com.aspire.birp.common.utils.CommonUtil;

/** 
 * 村档案数据服务Service 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:33:23 
 */
@Service
public class CountryService {
	private static Logger log = LoggerFactory.getLogger(CountryService.class);
	
	@Autowired
	private CountryFileDao dao;
	
	/**
	 * 获取村档案基础信息JSON数据
	 * @return
	 */
	public String getCountryFileBasicData(String id) {
		log.info("查询村档案基础信息=====");
		JSONObject json = new JSONObject();
		/*CountryFileBasic di = new CountryFileBasic();
		CountryFileBasicData gd = new CountryFileBasicData();*/
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			Map<String,Object> map = CountryMapTemplate.countryFileBasicTpl(dao.queryCountryFileBasicInfo(params));
			
			//组装JSON数据
			json.put("code", map.get("RET_CODE"));
			json.put("msg", map.get("RET_MSG"));
			
			JSONObject data = new JSONObject();
			data.put("country_name", map.get("S_COUNTRY_NAME"));
			JSONObject summary = new JSONObject();
			summary.put("responsible_name", map.get("S_RESPONSIBLE_NAME"));
			summary.put("responsible_avatar", Global.getConfig("image.path")+map.get("S_RESPONSIBLE_AVATAR"));
			summary.put("responsible_phone", map.get("S_RESPONSIBLE_PHONE"));
			summary.put("responsible_duty", map.get("S_RESPONSIBLE_DUTY"));
			summary.put("country_leader_name", map.get("S_COUNTRY_LEADER_NAME"));
			summary.put("country_leader_avatar", Global.getConfig("image.path")+map.get("S_COUNTRY_LEADER_AVATAR"));
			summary.put("country_leader_phone", map.get("S_TEAM_LEADER_TEL"));
			summary.put("country_leader_org", map.get("S_COUNTRY_LEADER_ORG"));
			summary.put("country_leader_start_time", map.get("S_COUNTRY_LEADER_START_TIME"));
			data.put("summary", summary);
			
			JSONObject stat = new JSONObject();
			boolean[] achieved_arr = {false,true};
			stat.put("amount_total_family", map.get("S_AMOUNT_TOTAL_FAMILY"));
			stat.put("amount_total_people", map.get("S_AMOUNT_TOTAL_PEOPLE"));
			stat.put("amount_poor_family", map.get("S_AMOUNT_POOR_FAMILY"));
			stat.put("amount_poor_people", map.get("S_AMOUNT_POOR_PEOPLE"));
			stat.put("ratio_poor", map.get("S_RATIO_POOR"));
			stat.put("amount_success_family", map.get("S_AMOUNT_SUCCESS_FAMILY"));
			stat.put("amount_success_people", map.get("S_AMOUNT_SUCCESS_PEOPLE"));
			if(map.get("S_SCORE")!=null){
				stat.put("score", map.get("S_SCORE"));
				stat.put("has_achieved", achieved_arr[Integer.parseInt(map.get("S_HAS_ACHIEVED").toString())]);
			}else{
				stat.put("score","-");
				stat.put("has_achieved", "true");
			}
			data.put("stat", stat);
			
			json.put("data", data);
			
			/*//20170512 add 
			
			di.setCode((Integer)getValue(map,"RET_CODE"));
			di.setMsg(getValue(map,"RET_MSG")+"");
			gd = (CountryFileBasicData)dao.queryCountryFileBasicData(params);
			di.setData(gd);*/
			
			//System.out.println(JsonFormatUtils.JsonFormat(di.toString()));
		
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案基础信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");		
		}
		return /*di.toString()*/json.toString();
	}
	
	/**
	 * 获取村档案业务办理JSON数据
	 * @return
	 */
	public String getCountryFileBusinessData(String id) {
		log.info("村档案业务办理信息=====");
		JSONObject json = new JSONObject();
		/*CountryFileBusiness di = new CountryFileBusiness();
		CountryFileBusinessData gd = new CountryFileBusinessData();*/
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			params.put("PROCESS_STATUS", "在办");
			List<Map<String,Object>> doingDataList = dao.queryBusiProcessList(params);
			//List<BaseListMode> doingDataListNew = dao.queryBusiProcessListNew(params);
			params.put("PROCESS_STATUS", "已办");
			List<Map<String,Object>> doneDataList = dao.queryBusiProcessList(params);
			//List<BaseListMode> doneDataListNew = dao.queryBusiProcessListNew(params);
			/*params.put("PROCESS_STATUS", "待办");
			List<Map<String,Object>> toDoDataList = dao.queryBusiProcessList(params);*/
			//List<BaseListMode> toDoDataListNew = dao.queryBusiProcessListNew(params);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			/*di.setCode(0);
			di.setMsg("获取信息成功");
			gd.setListDoing(doingDataListNew);
			gd.setListToDo(toDoDataListNew);
			gd.setListDone(doneDataListNew);
			di.setData(gd);*/
			//System.out.println(gd.toString());
			//System.out.println(di.toString());
			JSONObject data = new JSONObject();
			
			//在办
			JSONObject list_doing = new JSONObject();
			list_doing.put("amount_total", doingDataList.size());
			List<JSONObject> listDoing = new ArrayList<JSONObject>();
			int doing_size = doingDataList.size()>3?3:doingDataList.size();
			for(int i=0;i<doing_size;i++){
				JSONObject doing = new JSONObject();
				doing.put("text", doingDataList.get(i).get("S_TEXT"));
				doing.put("link", doingDataList.get(i).get("S_LINK"));
				listDoing.add(doing);
			}
			list_doing.put("list", listDoing);
			data.put("list_doing", list_doing);
			//已办
			JSONObject list_done = new JSONObject();
			list_done.put("amount_total", doneDataList.size());
			List<JSONObject> listDone = new ArrayList<JSONObject>();
			int done_size = doneDataList.size()>3?3:doneDataList.size();
			for(int i=0;i<done_size;i++){
				JSONObject done = new JSONObject();
				done.put("text", doneDataList.get(i).get("S_TEXT"));
				done.put("link", doneDataList.get(i).get("S_LINK"));
				listDone.add(done);
			}
			list_done.put("list", listDone);
			data.put("list_done", list_done);
/*			//在办
			JSONObject list_todo = new JSONObject();
			list_todo.put("amount_total", toDoDataList.size());
			List<JSONObject> todoList = new ArrayList<JSONObject>();
			int todo_size = doneDataList.size()>3?3:doneDataList.size();
			for(int i=0;i<todo_size;i++){
				JSONObject todo = new JSONObject();
				todo.put("text", toDoDataList.get(i).get("S_TEXT"));
				todo.put("link", toDoDataList.get(i).get("S_LINK"));
				todoList.add(todo);
			}
			list_todo.put("list", todoList);
			data.put("list_todo", list_todo);
			*/
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案业务办理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");		
			/*di.setCode(1);
			di.setMsg("数据处理异常，获取信息失败!");*/
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		//System.out.println(json.toString());
		return json.toString();
	}
	
	/**
	 * 获取村档案业务办理更多JSON数据
	 * @return
	 */
	public String getCountryFileBusinessMoreData(String id,String type) {
		log.info("村档案业务办理更多信息");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			String process_status = "";
			if("list_doing".equals(type)){
				process_status = "在办";
			}else if("list_done".equals(type)){
				process_status = "已办";
			}/*else if("list_todo".equals(type)){
				process_status = "待办";
			}*/
			params.put("PROCESS_STATUS", process_status);
			List<Map<String,Object>> dataList = dao.queryBusiProcessList(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			//在办
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=3;i<list.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("text", dataList.get(i).get("S_TEXT"));
				obj.put("link", dataList.get(i).get("S_LINK"));
				list.add(obj);
			}
			data.put("list", list);
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案业务办理更多异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取村档案数据轨迹JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCountryFileDataPathData(String id) {
		log.info("村档案数据轨迹信息");
		JSONObject json = new JSONObject();
		try{
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			Map<String,Object>  capital = dao.queryCountryFileDataPath(params);
			List<Map<String,Object>> capitalList = dao.queryCountryFileDataPathData(params);
			
			Map<String,Object> map = CountryMapTemplate.countryFileDataPathTpl(capital,capitalList);
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			JSONObject data = new JSONObject();
			JSONObject create = new JSONObject();
			create.put("date", getValue(map,"S_DATES"));
			create.put("time", getValue(map,"S_TIMES"));
			create.put("publisher_organization", getValue(map,"S_PUBLISHER_ORGANIZATIONS"));
			create.put("publisher_name", getValue(map,"S_PUBLISHER_NAMES"));
			data.put("create", create);
			
			
			List<JSONObject> updateList = new ArrayList<JSONObject>();
			for(int i=0;i<capitalList.size();i++){
				JSONObject update = new JSONObject();
				update.put("date", capitalList.get(i).get("S_DATE"));
				update.put("time", capitalList.get(i).get("S_TIME"));
				update.put("publisher_organization", capitalList.get(i).get("S_PUBLISHER_ORGANIZATION"));
				update.put("publisher_name", capitalList.get(i).get("S_PUBLISHER_NAME"));
				List<JSONObject> list = new ArrayList<JSONObject>();
				JSONObject obj = new JSONObject();
				obj.put("name", capitalList.get(i).get("S_NAME"));
				obj.put("old", capitalList.get(i).get("S_OLD"));
				obj.put("new", capitalList.get(i).get("S_NEW"));
				list.add(obj);
				update.put("list", list);
				updateList.add(update);
			}
			data.put("update", updateList);
			
			json.put("data", data);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案数据轨迹查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(json.toString());
		return json.toString();
	}
	
	/**
	 * 获取村档案发展现状JSON数据
	 * @return
	 */
	public String getCountryFileDevelopmentStatusData(String id) {
		log.info("村档案发展现状信息");
		JSONObject json = new JSONObject();
		/*CountryFileDevelopmentStatus di = new CountryFileDevelopmentStatus();
		CountryFileDevelopmentStatusData gd = new CountryFileDevelopmentStatusData();*/
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			Map<String,Object> devEntity = CountryMapTemplate.countryFileDevelopmentStatusTpl(dao.queryCountryDevStatus(params));
			
			//组装JSON数据
			json.put("code", devEntity.get("RET_CODE"));
			json.put("msg", devEntity.get("RET_MSG"));
			
			JSONObject data = new JSONObject();
			JSONObject income = new JSONObject();
			income.put("average", devEntity.get("S_AVERAGE")+"元");
			income.put("collective", devEntity.get("S_COLLECTIVE")+"万元");
			data.put("income", income);
			
			JSONObject social_security = new JSONObject();
			social_security.put("rural_medical", devEntity.get("S_RURAL_MEDICAL")+"人");
			social_security.put("pension_insurance", devEntity.get("S_PENSION_INSURANCE")+"人");
			social_security.put("medical_help", devEntity.get("S_MEDICAL_HELP")+"人次");
			data.put("social_security", social_security);
			
			JSONObject danger_house = new JSONObject();
			danger_house.put("amount", devEntity.get("S_AMOUNT")+"户");
			data.put("danger_house", danger_house);
			
			JSONObject country_road = new JSONObject();
			country_road.put("distance_admin_to_town", devEntity.get("S_DISTANCE_ADMIN_TO_TOWN")+"公里");
			country_road.put("bus_available", devEntity.get("S_BUS_AVAILABLE"));
			country_road.put("amount_natural_to_admin", devEntity.get("S_AMOUNT_NATURAL_TO_ADMIN")+"个");
			country_road.put("distance_natural", devEntity.get("S_DISTANCE_NATURAL")+"公里");
			data.put("country_road", country_road);
			
			JSONObject electricity = new JSONObject();
			electricity.put("not_enabled_living_country", devEntity.get("S_NOT_ENABLED_LIVING_COUNTRY")+"个");
			electricity.put("not_enabled_production_country", devEntity.get("S_NOT_ENA_PROD_COUNTRY")+"个");
			electricity.put("not_enabled_living_family", devEntity.get("S_NOT_ENABLED_LIVING_FAMILY")+"户");
			electricity.put("enabled_country", devEntity.get("S_ENABLED_COUNTRY")+"个");
			data.put("electricity", electricity);
			
			JSONObject water_safety = new JSONObject();
			water_safety.put("unsafe", devEntity.get("S_UNSAFE")+"户");
			water_safety.put("difficult", devEntity.get("S_DIFFICULT")+"户");
			data.put("water_safety", water_safety);
			
			JSONObject industry = new JSONObject();
			industry.put("amount_fsc", devEntity.get("S_AMOUNT_FSC")+"个");
			industry.put("amount_family_fsc", devEntity.get("S_AMOUNT_FAMILY_FSC")+"户");
			data.put("industry", industry);
			
			JSONObject travel = new JSONObject();
			travel.put("travel_family", devEntity.get("S_TRAVEL_FAMILY")+"户");
			travel.put("travel_people", devEntity.get("S_TRAVEL_PEOPLE")+"人");
			travel.put("rural_inn_family", devEntity.get("S_RURAL_INN_FAMILY")+"户");
			travel.put("rural_inn_average", devEntity.get("S_RURAL_INN_AVERAGE")+"元");
			data.put("travel", travel);
			
			JSONObject culture = new JSONObject();
			culture.put("library", devEntity.get("S_LIBRARY")+"个");
			culture.put("tv", devEntity.get("S_TV")+"户");
			data.put("culture", culture);
			
			JSONObject health = new JSONObject();
			health.put("office", devEntity.get("S_OFFICE")+"个");
			health.put("doctor", devEntity.get("S_DOCTOR")+"人");
			health.put("toilet", devEntity.get("S_TOILET")+"个");
			health.put("garbage", devEntity.get("S_GARBAGE")+"个");
			data.put("health", health);
			
			JSONObject information = new JSONObject();
			information.put("broadband_family", devEntity.get("S_BROADBAND_FAMILY")+"户");
			information.put("mobile_family", devEntity.get("S_MOBILE_FAMILY")+"户");
			information.put("broadband_natural", devEntity.get("S_BROADBAND_NATURAL")+"个");
			information.put("broadband_country", devEntity.get("S_BROADBAND_COUNTRY")+"个");
			information.put("admin_staff", devEntity.get("S_ADMIN_STAFF")+"人");
			data.put("information", information);
			
			JSONObject move = new JSONObject();
			move.put("amount", devEntity.get("S_RST_AMOUNT")+"户/"+devEntity.get("S_PERSON_AMOUNT")+"人");
			data.put("move", move);
			
			JSONObject rain = new JSONObject();
			rain.put("middle_school", devEntity.get("S_MIDDLE_SCHOOL")+"人");
			rain.put("joined", devEntity.get("S_JOINED")+"人");
			rain.put("high_school", devEntity.get("S_HIGH_SCHOOL")+"人");
			data.put("rain", rain);
			
			JSONObject loan = new JSONObject();
			loan.put("allow", devEntity.get("S_ALLOW")+"户");
			loan.put("cur_year", devEntity.get("S_CUR_YEAR")+"户");
			loan.put("due", devEntity.get("S_DUE")+"户");
			data.put("loan", loan);
			
			JSONObject party = new JSONObject();
			party.put("amount", devEntity.get("S_PARTY_AMOUNT")+"人");
			party.put("ratio", devEntity.get("S_RATIO"));
			data.put("party", party);
			
			json.put("data", data);
			/*//20170512 add 
			
			di.setCode((Integer) devEntity.get("RET_CODE"));
			di.setMsg(devEntity.get("RET_MSG")+"");
			gd = (CountryFileDevelopmentStatusData)dao.querycountryFileDevelopmentStatusData(params);
			di.setData(gd);*/
			//System.out.println(json.toString());
			//System.out.println(JsonFormatUtils.JsonFormat(di.toString()));
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案发展现状查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");	
			/*di.setCode(1);
			di.setMsg("数据处理异常，获取信息失败!");	*/
		}
		return json.toString();
	}
	
	/**
	 * 获取村档案脱贫轨迹JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCountryFileEliminatePathData(String id,String year) {
		log.info("村档案脱贫轨迹信息");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			String yeartwo =Constants.dateContants.get("F_COUNTRYFILE_AID_TRAIL_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("F_COUNTRYFILE_CAPITAL_DM").toString()));
			
	
			params.put("COUNTRY_PAC", id);
			if (year == null || year.equals("")) {
				year=yeartwo;
			}
			params.put("year", year);
			Map<String,Object> map = CountryMapTemplate.countryFileEliminatePathTpl(dao.queryCountryfileChart(params));
			
			//组装JSON数据
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			List<JSONObject> availableYears = new ArrayList<JSONObject>();
			for( int i=2016;i<2019;i++)
			 {
			   JSONObject yearsTable = new JSONObject();
			   yearsTable.put("name", i+"");
			   yearsTable.put("value", i+"");
			   availableYears.add(yearsTable);
			 }
			data.put("available_years", availableYears);
		
			
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			List<Map<String,Object>> chartDataList = (List<Map<String,Object>>)map.get("MAPLIST");
			for(int i=0;i<chartDataList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartDataList.get(i).get("S_NAME"));
				chart.put("group", chartDataList.get(i).get("S_GROUP"));
				chart.put("value", chartDataList.get(i).get("S_TOTAL_SCORE"));
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			
			JSONObject default_time = new JSONObject();
			if (Integer.parseInt(year)< Integer.parseInt(yeartwo)) {
				month="12";
			}
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案脱贫轨迹查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(json.toString());
		return json.toString();
	}
	
	/**
	 * 获取村档案脱贫轨迹得分JSON数据
	 * @return
	 */
	public String getCountryFileEliminateScoresData(String id,String year,String month) {
		log.info("村档案脱贫轨迹得分信息");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			params.put("MONTH_TAG", year+month+"01");
			Map<String,Object> scoresData = CountryMapTemplate.countryFileEliminateScoresTpl(dao.queryCountryfileAidTrail(params));
			
			//组装JSON数据
			json.put("code", getValue(scoresData,"RET_CODE"));
			json.put("msg", getValue(scoresData,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			boolean[] b_arr = {false,true};
			List<JSONObject> scoresList = new ArrayList<JSONObject>();
			//贫困发生率
			JSONObject scores1 = new JSONObject();
			scores1.put("index_name", "贫困发生率");
			scores1.put("weight", "20");
			scores1.put("score", getValue(scoresData,"POOR_RATE_SCORE"));
			scores1.put("value_name", "实际贫困发生率");
			scores1.put("value_actual", getValue(scoresData,"POOR_RATE"));
			scores1.put("value_target", "＜"+getValue(scoresData,"POOR_RATE_TARGET"));
			scores1.put("has_achieved", b_arr[Integer.parseInt(getValue(scoresData,"POOR_HAS_ACHIEVED").toString())]);
			JSONObject tips1 = new JSONObject();
			tips1.put("desc", "实现相对贫困村相对贫困发生率降至2%以下。");
			tips1.put("source", "建档立卡系统");
			tips1.put("calculation", "贫困人口/全村人口，2%以下为达标");
			scores1.put("tips", tips1);
			scoresList.add(scores1);
			data.put("scores", scoresList);
			//自筹扶贫资金
			JSONObject scores2 = new JSONObject();
			scores2.put("index_name", "帮扶资金");
			scores2.put("weight", "20");
			scores2.put("score", getValue(scoresData,"OWN_CAPITAL_SCORE"));
			scores2.put("value_name", "实际帮扶资金投入");
			scores2.put("value_actual", getValue(scoresData,"OWN_CAPITAL"));
			scores2.put("value_target", "≥"+getValue(scoresData,"OWN_CAPITAL_TARGET"));
			scores2.put("has_achieved", b_arr[Integer.parseInt(getValue(scoresData,"OWN_HAS_ACHIEVED").toString())]);
			JSONObject tips2 = new JSONObject();
			tips2.put("desc", "投入相对贫困村和贫困户的单位自筹（含社会捐赠）及财政专项扶贫资金（不含人均2万元财政专项资金）30万元。");
			tips2.put("source", "建档立卡系统");
			tips2.put("calculation", "建档立卡资金投入数*20/30，最高20分");
			scores2.put("tips", tips2);
			scoresList.add(scores2);
			data.put("scores", scoresList);
			//道路硬底化
			JSONObject scores3 = new JSONObject();
			scores3.put("index_name", "道路硬底化");
			scores3.put("weight", "10");
			scores3.put("score", getValue(scoresData,"ROAD_SCORE"));
			scores3.put("value_name", "脱贫实际值");
			scores3.put("value_actual", getValue(scoresData,"ROAD"));
			scores3.put("value_target", getValue(scoresData,"ROAD_TARGET"));
			scores3.put("has_achieved", b_arr[Integer.parseInt(getValue(scoresData,"ROAD_HAS_ACHIEVED").toString())]);
			JSONObject tips3 = new JSONObject();
			tips3.put("desc", "贫困村200人以上自然村100%硬底化。");
			tips3.put("source", "建档立卡系统");
			tips3.put("calculation", "200人以上自然村入村道路全部硬底化为达标。");
			scores3.put("tips", tips3);
			scoresList.add(scores3);
			data.put("scores", scoresList);
			//农村饮水保障
			JSONObject scores4 = new JSONObject();
			scores4.put("index_name", "农村饮水保障");
			scores4.put("weight", "10");
			scores4.put("score", getValue(scoresData,"WATER_SCORE"));
			scores4.put("value_name", "脱贫实际值");
			scores4.put("value_actual", getValue(scoresData,"WATER"));
			scores4.put("value_target", getValue(scoresData,"WATER_TARGET"));
			scores4.put("has_achieved", b_arr[Integer.parseInt(getValue(scoresData,"WATER_HAS_ACHIEVED").toString())]);
			JSONObject tips4 = new JSONObject();
			tips4.put("desc", "贫困村村民有安全饮水。");
			tips4.put("source", "建档立卡系统");
			tips4.put("calculation", "村全部人口有安全饮水为达标。");
			scores4.put("tips", tips4);
			scoresList.add(scores4);
			data.put("scores", scoresList);
			//贫困村通电
			JSONObject scores5 = new JSONObject();
			scores5.put("index_name", "贫困村通电");
			scores5.put("weight", "10");
			scores5.put("score", getValue(scoresData,"ELECTRIFY_SCORE"));
			scores5.put("value_name", "脱贫实际值");
			scores5.put("value_actual", getValue(scoresData,"ELECTRIFY"));
			scores5.put("value_target", getValue(scoresData,"ELECTRIFY_TARGET"));
			scores5.put("has_achieved", b_arr[Integer.parseInt(getValue(scoresData,"ELECTRIFY_HAS_ACHIEVED").toString())]);
			JSONObject tips5 = new JSONObject();
			tips5.put("desc", "贫困村村民100%通电。");
			tips5.put("source", "建档立卡系统");
			tips5.put("calculation", "村民全部通电为达标。");
			scores5.put("tips", tips5);
			scoresList.add(scores5);
			data.put("scores", scoresList);
			//贫困村通广播电视
			JSONObject scores6 = new JSONObject();
			scores6.put("index_name", "贫困村通广播电视");
			scores6.put("weight", "10");
			scores6.put("score", getValue(scoresData,"TV_SCORE"));
			scores6.put("value_name", "脱贫实际值");
			scores6.put("value_actual", getValue(scoresData,"TV"));
			scores6.put("value_target", getValue(scoresData,"TV_TARGET"));
			scores6.put("has_achieved", b_arr[Integer.parseInt(getValue(scoresData,"TV_HAS_ACHIEVED").toString())]);
			JSONObject tips6 = new JSONObject();
			tips6.put("desc", "贫困村村民100%通广播电视。");
			tips6.put("source", "建档立卡系统");
			tips6.put("calculation", "村民全部通广播电视为达标。");
			scores6.put("tips", tips6);
			scoresList.add(scores6);
			data.put("scores", scoresList);
			//贫困村通宽带网络
			JSONObject scores7 = new JSONObject();
			scores7.put("index_name", "贫困村通宽带网络");
			scores7.put("weight", "10");
			scores7.put("score", getValue(scoresData,"NET_SCORE"));
			scores7.put("value_name", "脱贫实际值");
			scores7.put("value_actual", getValue(scoresData,"NET"));
			scores7.put("value_target", getValue(scoresData,"NET_TARGET"));
			scores7.put("has_achieved", b_arr[Integer.parseInt(getValue(scoresData,"NET_HAS_ACHIEVED").toString())]);
			JSONObject tips7 = new JSONObject();
			tips7.put("desc", "贫困村村民100%网络覆盖。");
			tips7.put("source", "建档立卡系统");
			tips7.put("calculation", "村民全部网络全覆盖为达标。");
			scores7.put("tips", tips7);
			scoresList.add(scores7);
			data.put("scores", scoresList);
			//贫困村医疗设施建设
			JSONObject scores8 = new JSONObject();
			scores8.put("index_name", "贫困村医疗设施建设");
			scores8.put("weight", "10");
			scores8.put("score", getValue(scoresData,"HEALTH_SCORE"));
			scores8.put("value_name", "脱贫实际值");
			scores8.put("value_actual", getValue(scoresData,"HEALTH"));
			scores8.put("value_target", getValue(scoresData,"HEALTH_TARGET"));
			scores8.put("has_achieved", b_arr[Integer.parseInt(getValue(scoresData,"HEALTH_HAS_ACHIEVED").toString())]);
			JSONObject tips8 = new JSONObject();
			tips8.put("desc", "贫困村建有卫生站。");
			tips8.put("source", "建档立卡系统");
			tips8.put("calculation", "有1个以上卫生站和1个以上执业（助理）医师为达标。");
			scores8.put("tips", tips8);
			scoresList.add(scores8);
			data.put("scores", scoresList);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案脱贫轨迹得分查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(json.toString());
		return json.toString();
	}
	
	/**
	 * 获取村档案帮扶施策JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCountryFileImplementData(String id,String year) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			String yeartwo =Constants.dateContants.get("F_COUNTRYFILE_AID_TRAIL_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("F_COUNTRYFILE_AID_TRAIL_DM").toString()));

			params.put("COUNTRY_PAC", id);
			if (year == null || year.equals("")) {
				year=yeartwo;
			}
			params.put("year", year);
			Map<String,Object> ImplementTotalEntity = dao.queryCountryfileHelpCount(params);
			
			params.put("PROJ_STATUS", "进行中");//0：进行中
			List<Map<String,Object>> ImplementRunningList = dao.queryCountryfileHelpList(params);
			//List<CountryFileImpBase> lementList = dao.queryCountryFileImplement1(params);

			params.put("PROJ_STATUS", "完成");//1：已完成
			List<Map<String,Object>> ImplementCompletedList = dao.queryCountryfileHelpList(params);
			//List<CountryFileImpBase> ImCompletedList = dao.queryCountryFileImplement1(params);
			
			Map<String,Object> map = CountryMapTemplate.countryFileImplementTpl(ImplementTotalEntity, ImplementRunningList, ImplementCompletedList);
			
			
			//组装JSON数据
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			List<JSONObject> availableYears = new ArrayList<JSONObject>();
			for( int i=2016;i<2019;i++)
			 {
			   JSONObject yearsTable = new JSONObject();
			   yearsTable.put("name", i+"");
			   yearsTable.put("value", i+"");
			   availableYears.add(yearsTable);
			 }
			data.put("available_years", availableYears);
			JSONObject default_time = new JSONObject();
			if (year != null || !year.equals("")) {
				yeartwo=year;
			}
			default_time.put("year", yeartwo);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			
			
			JSONObject summary = new JSONObject();
			JSONObject projects = new JSONObject();
			projects.put("total", getValue(map,"S_TOTAL"));
			projects.put("running", getValue(map,"S_RUNNING"));
			projects.put("completed", getValue(map,"S_COMPLETED"));
			summary.put("projects", projects);
			summary.put("invested", getValue(map,"S_INVESTED"));
			summary.put("profit", getValue(map,"S_PROFIT"));
			data.put("summary", summary);
			
			JSONObject table = new JSONObject();
			/*List<JSONObject> list_running = new ArrayList<JSONObject>();
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			for(int i=0;i<mapList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("type", mapList.get(i).get("S_TYPE"));
				obj.put("name", mapList.get(i).get("S_NAME"));
				obj.put("size", mapList.get(i).get("S_PROJ_CNT"));
				obj.put("invest_expected", mapList.get(i).get("S_INVEST_EXPECTED"));
				obj.put("invest_actual", mapList.get(i).get("S_INVEST_ACTUAL"));
				obj.put("profit", mapList.get(i).get("S_PROFIT"));
				obj.put("time_start", mapList.get(i).get("S_TIME_START"));
				obj.put("time_completed", mapList.get(i).get("S_TIME_COMPLETED"));
				JSONObject album = new JSONObject();
				Map<String,Object> picParams = Constants.dateContants;
				picParams.put("PROJ_ID", mapList.get(i).get("S_PROJ_ID"));
				picParams.put("PIC_TYPE", "实施前");
				List<Map<String,Object>> before_albumList = dao.queryCountryfileHelpPic(picParams);
				if(CollectionUtils.isNotEmpty(before_albumList)){
					album.put("before", Global.getConfig("image.path")+before_albumList.get(0).get("PATH"));//取第一条数据
				}
				picParams.put("PIC_TYPE", "实施中");
				List<Map<String,Object>>during_albumList = dao.queryCountryfileHelpPic(picParams);
				if(CollectionUtils.isNotEmpty(during_albumList)){
					album.put("during", Global.getConfig("image.path")+during_albumList.get(0).get("PATH"));//取第一条数据
				}
				picParams.put("PIC_TYPE", "完成");
				List<Map<String,Object>>after_albumList = dao.queryCountryfileHelpPic(picParams);
				if(CollectionUtils.isNotEmpty(after_albumList)){
					album.put("after", Global.getConfig("image.path")+after_albumList.get(0).get("PATH"));//取第一条数据
				}
				obj.put("album", album);
				list_running.add(obj);
			}
			table.put("list_running", list_running);*/
			
			List<JSONObject> list_completed = new ArrayList<JSONObject>();
			List<Map<String,Object>> mapList2= (List<Map<String,Object>>)map.get("MAPLIST2");
			for(int i=0;i<mapList2.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("type", mapList2.get(i).get("S_TYPE"));
				obj.put("name", mapList2.get(i).get("S_NAME"));
				obj.put("size", mapList2.get(i).get("S_PROJ_CNT"));
				obj.put("invest_expected", mapList2.get(i).get("S_INVEST_EXPECTED"));
				obj.put("invest_actual", mapList2.get(i).get("S_INVEST_ACTUAL"));
				obj.put("profit", mapList2.get(i).get("S_PROFIT"));
				obj.put("time_start", mapList2.get(i).get("S_TIME_START"));
				obj.put("time_completed", mapList2.get(i).get("S_TIME_COMPLETED"));
				JSONObject album = new JSONObject();
				Map<String,Object> picParams = Constants.dateContants;
				picParams.put("PROJ_ID", mapList2.get(i).get("S_PROJ_ID"));
				picParams.put("PIC_TYPE", "实施前");
				List<Map<String,Object>> before_albumList = dao.queryCountryfileHelpPic(picParams);
				if(CollectionUtils.isNotEmpty(before_albumList)){
					album.put("before", Global.getConfig("image.path")+before_albumList.get(0).get("PATH"));//取第一条数据
				}
				picParams.put("PIC_TYPE", "实施中");
				List<Map<String,Object>>during_albumList = dao.queryCountryfileHelpPic(picParams);
				if(CollectionUtils.isNotEmpty(during_albumList)){
					album.put("during", Global.getConfig("image.path")+during_albumList.get(0).get("PATH"));//取第一条数据
				}
				picParams.put("PIC_TYPE", "完成");
				List<Map<String,Object>>after_albumList = dao.queryCountryfileHelpPic(picParams);
				if(CollectionUtils.isNotEmpty(after_albumList)){
					album.put("after", Global.getConfig("image.path")+after_albumList.get(0).get("PATH"));//取第一条数据
				}
				obj.put("album", album);
				list_completed.add(obj);
			}
			table.put("list", list_completed);
			data.put("table", table);
			
			json.put("data", data);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案帮扶施策查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(json.toString());
		return json.toString();
	}
	
	/**
	 * 获取村档案资金投入JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCountryFileInvestedData(String id,String year) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			String yeartwo = Constants.dateContants.get("F_COUNTRYFILE_CAPITAL_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("F_COUNTRYFILE_AID_TRAIL_DM").toString()));

			params.put("COUNTRY_PAC", id);
			if (year == null || year.equals("")) {
				year=yeartwo;
			}
			params.put("year", year);
			params.put("START_MONTH", yeartwo+"01");
			params.put("END_MONTH", yeartwo+"12");
			Map<String,Object> capitalCount = dao.queryCapitalTotalCount(params);//累计
			Map<String,Object> capitalCountYear = dao.queryCapitalCount(params);//当年
			Map<String,Object> capitalYear = dao.queryCapitalListByYear(params);
			List<Map<String,Object>> capitalList = dao.queryCapitalList(params);
			Map<String,Object> map = CountryMapTemplate.countryFileInvestedTpl(capitalCount, capitalCountYear, capitalYear, capitalList);
			
			//组装JSON数据
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			List<JSONObject> availableYears = new ArrayList<JSONObject>();
			for( int i=2016;i<2019;i++)
			 {
			   JSONObject yearsTable = new JSONObject();
			   yearsTable.put("name", i+"");
			   yearsTable.put("value", i+"");
			   availableYears.add(yearsTable);
			 }
			data.put("available_years", availableYears);
			JSONObject default_time = new JSONObject();
			if (year != null || !year.equals("")) {
				yeartwo=year;
			}
			default_time.put("year", yeartwo);
			default_time.put("month", month);
			data.put("default_time", default_time);

			JSONObject summary = new JSONObject();
			summary.put("total", getValue(map,"S_TOTAL"));
			summary.put("cur_year", getValue(map,"S_YEAR_TOTAL"));
			data.put("summary", summary);
			
			List<JSONObject> tableList = new ArrayList<JSONObject>();
			
			JSONObject total_table = new JSONObject();
			total_table.put("title", "当年累计");
			total_table.put("organization", getValue(map,"S_ORGANIZATION"));
			JSONObject total_helper_city = new JSONObject();
			total_helper_city.put("city_finance", getValue(map,"S_HELPER_CITY_FINANCE"));
			total_helper_city.put("district_town_finance", getValue(map,"S_DISTRICT_TOWN_FINANCE"));
			total_helper_city.put("social_money", getValue(map,"S_HELPER_SOCIAL_MONEY"));
			total_table.put("helper_city", total_helper_city);
			JSONObject total_central_province = new JSONObject();
			total_central_province.put("central_finance", getValue(map,"S_CENTRAL_FINANCE"));
			total_central_province.put("province_finance", getValue(map,"S_PROVINCE_FINANCE"));
			total_central_province.put("central_industry", getValue(map,"S_CENTRAL_INDUSTRY"));
			total_central_province.put("province_industry", getValue(map,"S_PROVINCE_INDUSTRY"));
			total_central_province.put("social_money", getValue(map,"S_CENTRAL_SOCIAL_MONEY"));
			total_table.put("central_province", total_central_province);
			JSONObject total_helped_city = new JSONObject();
			total_helped_city.put("city_finance", getValue(map,"S_HELPED_CITY_FINANCE"));
			total_helped_city.put("county_town_finance", getValue(map,"S_COUNTY_TOWN_FINANCE"));
			total_helped_city.put("city_industry", getValue(map,"S_CITY_INDUSTRY"));
			total_helped_city.put("county_town_industry", getValue(map,"S_COUNTY_TOWN_INDUSTRY"));
			total_helped_city.put("social_money", getValue(map,"S_HELPED_SOCIAL_MONEY"));
			total_table.put("helped_city", total_helped_city);
			total_table.put("summary", getValue(map,"S_SUMMARY"));
			tableList.add(total_table);
			
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			for(int i=0;i<mapList.size();i++){
				JSONObject table = new JSONObject();
				table.put("title", mapList.get(i).get("S_TITLE"));
				table.put("organization", mapList.get(i).get("S_ORGANIZATION"));
				JSONObject helper_city = new JSONObject();
				helper_city.put("city_finance", mapList.get(i).get("S_HELPER_CITY_FINANCE"));
				helper_city.put("district_town_finance", mapList.get(i).get("S_DISTRICT_TOWN_FINANCE"));
				helper_city.put("social_money", mapList.get(i).get("S_HELPER_SOCIAL_MONEY"));
				table.put("helper_city", helper_city);
				JSONObject central_province = new JSONObject();
				central_province.put("central_finance", mapList.get(i).get("S_CENTRAL_FINANCE"));
				central_province.put("province_finance", mapList.get(i).get("S_PROVINCE_FINANCE"));
				central_province.put("central_industry", mapList.get(i).get("S_CENTRAL_INDUSTRY"));
				central_province.put("province_industry", mapList.get(i).get("S_PROVINCE_INDUSTRY"));
				central_province.put("social_money", mapList.get(i).get("S_CENTRAL_SOCIAL_MONEY"));
				table.put("central_province", central_province);
				JSONObject helped_city = new JSONObject();
				helped_city.put("city_finance", mapList.get(i).get("S_HELPED_CITY_FINANCE"));
				helped_city.put("county_town_finance", mapList.get(i).get("S_COUNTY_TOWN_FINANCE"));
				helped_city.put("city_industry", mapList.get(i).get("S_CITY_INDUSTRY"));
				helped_city.put("county_town_industry", mapList.get(i).get("S_COUNTY_TOWN_INDUSTRY"));
				helped_city.put("social_money", mapList.get(i).get("S_HELPED_SOCIAL_MONEY"));
				table.put("helped_city", helped_city);
				table.put("summary", mapList.get(i).get("S_SUMMARY"));
				tableList.add(table);
			}
			data.put("table", tableList);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案资金投入查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取村档案会议动态JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCountryFileMeetingNewsData(String id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			Map<String,Object> map = CountryMapTemplate.countryFileMeetingNewsTpl(dao.queryCountryfileMeetingList(params));
			
			//组装JSON数据
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			
			List<JSONObject> meetingList = new ArrayList<JSONObject>();
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			for(int i=0;i<mapList.size();i++){
				JSONObject meeting = new JSONObject();
				meeting.put("id", mapList.get(i).get("S_ID"));
				meeting.put("time", mapList.get(i).get("S_TIME"));
				meeting.put("desc", mapList.get(i).get("S_DESC"));
				List<String> picsList = new ArrayList<String>();
				if(mapList.get(i).get("S_PICS")!=null){
					String pics = ClobToStringUtls.ClobToString((Clob)mapList.get(i).get("S_PICS"));
					String[] picsArr = pics.split(";");
					for(int j=0;j<picsArr.length;j++) {
						picsList.add(Global.getConfig("image.path")+picsArr[j]);
					}
				}
				meeting.put("pics", picsList);
				meetingList.add(meeting);
			}
			data.put("list", meetingList);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案会议动态查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取村档案会议动态详情JSON数据
	 * @return
	 */
	public String getCountryFileMeetingNewsDetailData(String id) {
		JSONObject json = new JSONObject();
		try{
			
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("HELP_ID", id);
			List<Map<String,Object>> pics = dao.queryCountryfileTrendsPicById(params);
			
			List<Map<String,Object>> meets = dao.queryCountryfileMeetingById(params);
			Map<String,Object> obj = new HashMap<String, Object>();
			if(CollectionUtils.isNotEmpty(meets))
				obj = meets.get(0);
			
			Map<String,Object> map = CountryMapTemplate.countryFileMeetingNewsDetailTpl(obj);
			
			//组装JSON数据
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			data.put("desc", getValue(map,"S_DESC"));
			List<String> picsList = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(pics)){
				for(int j=0;j<pics.size();j++) {
					String mpath = String.valueOf(pics.get(j).get("PATH"));
					if(StringUtils.isNotBlank(mpath))
						picsList.add(Global.getConfig("image.path")+mpath);
				}
			}
			data.put("pics", picsList);
			json.put("data", data);
			
			//20170515 WANG 
			/*CountryFileMeetingNewsDetail di = new CountryFileMeetingNewsDetail();
			CountryFileMeetingNewsDetailData gd = new CountryFileMeetingNewsDetailData();
			di.setCode((Integer)getValue(map,"RET_CODE"));
			di.setMsg(getValue(map,"RET_MSG")+"");
			gd = (CountryFileMeetingNewsDetailData)dao.queryCountryFileMeetingNewsDetailData(params);
			gd.setpics1(pics);
			di.setData(gd);
			System.out.println(1+json.toString());
			System.out.println(2+JsonFormatUtils.JsonFormat(di.toString()));*/
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案会议动态详情查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取村档案驻村动态JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCountryFileNewsData(String id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			Map<String,Object> map = CountryMapTemplate.countryFileNewsTpl(dao.queryCountryfileTrendsList(params));
			
			//20170518
			/*List<CountryFileBase> countryFileBase = dao.queryCountryfileList(params);
			CountryFileNews di = new CountryFileNews();
			CountryFileNewsData gd = new CountryFileNewsData();
			di.setCode((Integer) getValue(map,"RET_CODE"));
			di.setMsg(getValue(map,"RET_MSG")+"");
			gd.setChartConfig(countryFileBase);*/
			
			//组装JSON数据
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			
			List<JSONObject> newsList = new ArrayList<JSONObject>();
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			for(int i=0;i<mapList.size();i++){
				JSONObject news = new JSONObject();
				news.put("id", mapList.get(i).get("S_ID"));
				news.put("time", mapList.get(i).get("S_TIME"));
				news.put("desc", mapList.get(i).get("S_DESC"));
				List<String> picsList = new ArrayList<String>();
				if(mapList.get(i).get("S_PICS")!=null){
					String pics = ClobToStringUtls.ClobToString((Clob)mapList.get(i).get("S_PICS"));
					//System.out.println(mapList.get(i).get("S_PICS"));
					String[] picsArr = pics.split(";");
					for(int j=0;j<picsArr.length;j++) {
						picsList.add(Global.getConfig("image.path")+picsArr[j]);
					}
				}
				//countryFileBase.get(0).setPics(picsList);
				news.put("pics", picsList);
				newsList.add(news);
			}
			data.put("list", newsList);
			
			json.put("data", data);
			
			//di.setData(gd);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案驻村动态查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取村档案驻村动态详情JSON数据
	 * @return
	 */
	public String getCountryFileNewsDetailData(String id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("HELP_ID", id);
			List<Map<String,Object>> pics = dao.queryCountryfileTrendsPicById(params);
			/*StringBuffer picsStr = new StringBuffer();
			if(CollectionUtils.isNotEmpty(picList)){
				for (int i = 0; i < picList.size(); i++) {
					picsStr.append(picList.get(i).get("PATH"));
					if(i!=0){
						picsStr.append(";");
					}
				}
			}*/
			List<Map<String,Object>> descs = dao.queryCountryfileTrendsById(params);
			Map<String,Object> obj = new HashMap<String,Object>();
			if(CollectionUtils.isNotEmpty(descs))
				obj = descs.get(0);
			Map<String,Object> map = CountryMapTemplate.countryFileMeetingNewsDetailTpl(obj);
			
			//组装JSON数据
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			data.put("desc", getValue(map,"S_DESC"));
			
			List<String> picsList = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(pics)){
				for(int j=0;j<pics.size();j++) {
					String ppath = String.valueOf(pics.get(j).get("PATH"));
					if(StringUtils.isNotBlank(ppath)){
						picsList.add(Global.getConfig("image.path")+ppath);
					}				
				}
			}
			data.put("pics", picsList);
			json.put("data", data);
			
			/*//20170518  
			CountryFileNewsDetail di = new CountryFileNewsDetail();
			CountryFileNewsDetailData gd = new CountryFileNewsDetailData();
			di.setCode((Integer)getValue(map,"RET_CODE"));
			di.setMsg(getValue(map,"RET_MSG")+"");
			gd = (CountryFileNewsDetailData)dao.queryCountryById(params);
			gd.setpics1(pics);
			di.setData(gd);
			System.out.println(1+json.toString());
			System.out.println(2+JsonFormatUtils.JsonFormat(di.toString()));*/
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案驻村动态详情查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取村档案帮扶计划JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCountryFilePlanData(String id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			Integer year = Integer.parseInt("2016");//df.format(new Date()));
			String[] item = {String.valueOf(year),String.valueOf(year+1),String.valueOf(year+2),"999"};
			params.put("item", item);
			List<Map<String,Object>> CountryFilePl=dao.queryCountryFilePlanList(params);
			Map<String,Object> map = CountryMapTemplate.countryFilePlanTpl(CountryFilePl);
			String[] tabTitleArr = {"三年帮扶总规划",String.valueOf(year)+"年度帮扶计划",String.valueOf(year+1)+"年度帮扶计划",String.valueOf(year+2)+"年度帮扶计划"};
			
			json.put("code", map.get("RET_CODE"));
			json.put("msg", map.get("RET_MSG"));
			JSONObject data = new JSONObject();
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			List<JSONObject> plansList = new ArrayList<JSONObject>();
			int n = CountryFilePl.size();
            if(n > 0 ){
            	JSONObject plans = new JSONObject();
				plans.put("tab_title", "三年帮扶总规划");
				JSONObject summary = new JSONObject();
				summary.put("country_leader_sign", mapList.get(n-1).get("S_COUNTRY_LEADER_SIGN"));
				summary.put("house_holder_sign", mapList.get(n-1).get("S_CHOUSE_HOLDER_SIGN"));
				summary.put("time", mapList.get(n-1).get("S_TIME"));
				plans.put("summary", summary);
				if(mapList.get(n-1).get("S_CONTENT")!=null){
					plans.put("content", ClobToStringUtls.ClobToString((Clob)mapList.get(n-1).get("S_CONTENT")));
				}else{
					plans.put("content", "");
				}
				plansList.add(plans);
            }
			for(int i=0;i<n-1;i++){
				JSONObject plans = new JSONObject();
				plans.put("tab_title",  mapList.get(i).get("S_PLAN_YEAR")+"年度帮扶计划");
				JSONObject summary = new JSONObject();
				summary.put("country_leader_sign", mapList.get(i).get("S_COUNTRY_LEADER_SIGN"));
				summary.put("house_holder_sign", mapList.get(i).get("S_CHOUSE_HOLDER_SIGN"));
				summary.put("time", mapList.get(i).get("S_TIME"));
				plans.put("summary", summary);
				if(mapList.get(i).get("S_CONTENT")!=null){
					plans.put("content", ClobToStringUtls.ClobToString((Clob)mapList.get(i).get("S_CONTENT")));
				}else{
					plans.put("content", "");
				}
				plansList.add(plans);
			}
			data.put("plans", plansList);
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案帮扶计划查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取村档案基本情况JSON数据
	 * @return
	 */
	public String getCountryFileStatusData(String id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("COUNTRY_PAC", id);
			Map<String,Object> statusEntity = CountryMapTemplate.countryFileStatusTpl(dao.queryCountryFileStatus(params));
			
			//组装JSON数据
			json.put("code", getValue(statusEntity,"RET_CODE"));
			json.put("msg", getValue(statusEntity,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			
			JSONObject summary = new JSONObject();
			summary.put("country_name", getValue(statusEntity,"S_COUNTRY_NAME"));
			summary.put("country_attribute", getValue(statusEntity,"S_COUNTRY_ATTRIBUTE"));
			summary.put("topography", getValue(statusEntity,"S_TOPOGRAPHY"));
			summary.put("dev_target", getValue(statusEntity,"S_DEV_TARGET"));
			summary.put("groups", getValue(statusEntity,"S_GROUPS"));
			data.put("summary", summary);
			
			JSONObject population = new JSONObject();
			JSONObject family = new JSONObject();
			family.put("total", getValue(statusEntity,"S_FAM"));
			List<JSONObject> family_barsList = new ArrayList<JSONObject>();
			String[] fam_name_arr = {"贫困户数","低保户数","五保户数"};
			String[] fam_count_arr = {getValue(statusEntity,"S_POOR_FAM").toString(),getValue(statusEntity,"S_LOW_FAM").toString(),getValue(statusEntity,"S_FIV_FAM").toString()};
			for(int i=0;i<fam_name_arr.length;i++){
				JSONObject bars = new JSONObject();
				bars.put("name", fam_name_arr[i]);
				bars.put("value", fam_count_arr[i]);
				family_barsList.add(bars);
			}
			family.put("bars", family_barsList);
			population.put("family", family);
			JSONObject people = new JSONObject();
			people.put("total", getValue(statusEntity,"S_POP"));
			List<JSONObject> people_barsList = new ArrayList<JSONObject>();
			String[] people_name_arr = {"贫困人口","低保贫困人口","五保贫困人口","少数民族人口","妇女人口","残疾人口"};
			String[] people_count_arr = {getValue(statusEntity,"S_POOR_POP").toString(),getValue(statusEntity,"S_LOW_POP").toString(),getValue(statusEntity,"S_FIV_POP").toString(),
					getValue(statusEntity,"S_MINORITY_POP").toString(),getValue(statusEntity,"S_FEME_POP").toString(),getValue(statusEntity,"S_DISABLED_POP").toString()};
			for(int j=0;j<people_name_arr.length;j++){
				JSONObject bars = new JSONObject();
				bars.put("name", people_name_arr[j]);
				bars.put("value", people_count_arr[j]);
				people_barsList.add(bars);
			}
			people.put("bars", people_barsList);
			population.put("people", people);
			JSONObject labor = new JSONObject();
			labor.put("total", getValue(statusEntity,"S_WB_POP"));
			List<JSONObject> labor_barsList = new ArrayList<JSONObject>();
			String[] labor_name_arr = {"外出务工人口","在家务农人口"};
			String[] labor_count_arr = {getValue(statusEntity,"S_OUT_WORK_POP").toString(),getValue(statusEntity,"S_FARM_WORK_POP").toString()};
			for(int k=0;k<labor_name_arr.length;k++){
				JSONObject bars = new JSONObject();
				bars.put("name", labor_name_arr[k]);
				bars.put("value", labor_count_arr[k]);
				labor_barsList.add(bars);
			}
			labor.put("bars", labor_barsList);
			population.put("labor", labor);
			data.put("population", population);
			
			JSONObject production = new JSONObject();
			JSONObject cultivated_land = new JSONObject();
			cultivated_land.put("total", getValue(statusEntity,"S_CULTIVATED_TOTAL"));
			cultivated_land.put("available", getValue(statusEntity,"S_CULTIVATED_AVAILABLE"));
			production.put("cultivated_land", cultivated_land);
			JSONObject forest_land = new JSONObject();
			forest_land.put("total", getValue(statusEntity,"S_FOREST_TOTAL"));
			forest_land.put("available", getValue(statusEntity,"S_FOREST_AVAILABLE"));
			production.put("forest_land", forest_land);
			production.put("grass_total", getValue(statusEntity,"S_GRASS_TOTAL"));
			production.put("water_total", getValue(statusEntity,"S_WATER_TOTAL"));
			data.put("production", production);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("村档案基本情况查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
    private Object getValue(Map map,String key){
    	return CommonUtil.getMapValue(map,key);
	}
}
