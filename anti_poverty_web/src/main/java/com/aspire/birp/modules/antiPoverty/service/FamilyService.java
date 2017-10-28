package com.aspire.birp.modules.antiPoverty.service;

import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.common.utils.ClobToStringUtls;
import com.aspire.birp.common.utils.CommonUtil;
import com.aspire.birp.modules.antiPoverty.dao.FamilyFileDao;
import com.aspire.birp.modules.antiPoverty.model.poorFile.BaseListMode;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan.FilePlan;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan.FilePlanData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan.Plan;
import com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileBasic.CountryFileBasicData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileBasic.FamilyFileBasic;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileBasic.FamilyFileBasicData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileBusiness.FamilyFileBusiness;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileBusiness.FamilyFileBusinessData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileCondition.FamilyFileCondition;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileCondition.FamilyFileConditionData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.ChartConfigBar;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.FileIncome;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.FileIncomeData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers.FileMembers;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FileEliminatePath.FileEliminatePath;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FileEliminatePath.FileEliminatePathData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNews.FileNews;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNewsDetail.FileNewsDetail;
import com.aspire.birp.modules.antiPoverty.model.poorFile.fileBusinessRest.FileBusinessRest;
import com.aspire.birp.modules.antiPoverty.model.poorFile.fileBusinessRest.FileBusinessRestData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.fileBusinessRest.TextLink;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.antiPoverty.utils.FamilyMapTemplate;
import com.aspire.birp.modules.antiPoverty.utils.JsonFormatUtils;

/** 
 * 户档案数据服务Service 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:33:23 
 */
@Service
public class FamilyService {
	private static Logger log = LoggerFactory.getLogger(FamilyService.class);
	
	@Autowired
	private FamilyFileDao dao;
	
	/**
	 * 获取户档案基本信息JSON数据
	 * @return
	 */
	public String getFamilyFileBasicData(String id) {
		JSONObject json = new JSONObject();
		
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			/*Iterator<Entry<String, Object>> entries = params.entrySet().iterator();  
			while (entries.hasNext()) {  
				Entry<String, Object> entry = entries.next();
				//params.put(entry.getKey(),entry.getValue());
				System.out.println("key= "+entry.getKey()+"  value== "+entry.getValue());
			}*/
			Map<String,Object> map = FamilyMapTemplate.familyFileBasicTpl(dao.queryFamilyFileInfo(params));
			//组装JSON
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			JSONObject data = new JSONObject();
			
			JSONObject summary = new JSONObject();
			summary.put("house_holder_name", getValue(map,"S_HOUSE_HOLDER_NAME"));
			summary.put("house_holder_avatar", Global.getConfig("image.path")+getValue(map,"S_HOUSE_HOLDER_AVATAR"));
			summary.put("house_holder_phone", getValue(map,"S_HOUSE_HOLDER_PHONE"));
			summary.put("poor_attribute", getValue(map,"S_POOR_ATTRIBUTE"));
			summary.put("poor_reason", getValue(map,"S_POOR_REASON"));
			summary.put("start_income", getValue(map,"S_START_INCOME"));
			summary.put("country_leader_name", getValue(map,"S_COUNTRY_LEADER_NAME"));
			summary.put("country_leader_avatar", Global.getConfig("image.path")+getValue(map,"S_COUNTRY_LEADER_AVATAR"));
			summary.put("country_leader_phone", getValue(map,"S_COUNTRY_LEADER_PHONE"));
			summary.put("country_leader_org", getValue(map,"S_COUNTRY_LEADER_ORG"));
			summary.put("country_leader_start_time", getValue(map,"S_COUNTRY_LEADER_START_TIME"));
			data.put("summary", summary);
			
			JSONObject stat = new JSONObject();
			boolean[] boolean_arr = {false,true};
			stat.put("income", getValue(map,"S_INCOME"));
			stat.put("edu_total", getValue(map,"S_EDU_TOTAL"));
			stat.put("edu_achieved", getValue(map,"S_EDU_ACHIEVED"));
			stat.put("medical_security_total", getValue(map,"S_MEDICAL_SECURITY_TOTAL"));
			stat.put("medical_security_achieved", getValue(map,"S_MEDICAL_SECURITY_ACHIEVED"));
			String house_lvel = getValue(map,"S_HOUSE_DANGER_LEVEL") == null ? "" : getValue(map,"S_HOUSE_DANGER_LEVEL").toString();
			if(getValue(map,"S_HOUSE_DANGER_LEVEL").equals("")){
				stat.put("is_danger_house", JSONNull.getInstance());
			}else{
				if(!house_lvel.equals("A") && !house_lvel.equals("B") 
						&& !house_lvel.equals("C") && !house_lvel.equals("D")){
					stat.put("is_danger_house", false);
				}else{
					stat.put("is_danger_house", true);
				}
				
			}
			stat.put("has_house_become_safe", Integer.parseInt(getValue(map,"S_IS_DANGER_HOUSE").toString())==1 ? false : true );
			stat.put("house_danger_level", getValue(map,"S_HOUSE_DANGER_LEVEL"));
			if(getValue(map,"S_HOUSE_DANGER_LEVEL").toString().endsWith("2")){
				stat.put("house_danger_level", JSONNull.getInstance());
			}else{
				stat.put("house_danger_level", getValue(map,"S_HOUSE_DANGER_LEVEL"));
			}
			stat.put("index", getValue(map,"S_INDEX_"));
			stat.put("has_achieved", boolean_arr[Integer.parseInt(getValue(map,"S_HAS_ACHIEVED").toString())]);
			data.put("stat", stat);
			
			String atime = map.get("AID_TIME") == null ? "29991231" : (String)map.get("AID_TIME"); //脱贫时间
			String adesc = map.get("ABNORMAL_DESC") == null ? "" : (String)map.get("ABNORMAL_DESC") ;//异常描述
			BigDecimal adesctime = map.get("ABNORMAL_TIME") == null ? new BigDecimal(0) : (BigDecimal)map.get("ABNORMAL_TIME");//异常描述
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			if(!"".equals(adesc)){
				JSONObject status = new JSONObject();
				status.put("date", fmt.format(sdf.parse(adesctime.toString())));
				status.put("code", "10");
				status.put("name", "已"+adesc);
				data.put("status", status);
			}else if(!"29991231".equals(atime)){
				JSONObject status = new JSONObject();
				status.put("date", fmt.format(sdf.parse(atime)));
				status.put("code", "1");
				status.put("name", "预脱贫");
				data.put("status", status);
			}
			
			json.put("data", data);
			
			//20170515 add 
			/*FamilyFileBasic di = new FamilyFileBasic();
			FamilyFileBasicData gd = null;
			di.setCode((Integer)getValue(map,"RET_CODE"));
			di.setMsg(getValue(map,"RET_MSG")+"");
			gd = (FamilyFileBasicData)dao.queryFamilyFileBasicData(params);
			gd = gd == null ? new FamilyFileBasicData() : gd;
			//处理""中的,
			String a=gd.getPoorAttribute1()==null ? "" : gd.getPoorAttribute1();
			a=a.replaceAll(",",".");
			gd.setPoorAttribute1(a);
			System.out.println(a);
			
			di.setData(gd);
			System.out.println(json.toString());
			//System.out.println(JsonFormatUtils.JsonFormat(di.toString()));
			System.out.println(di.toString());*/
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案基本信息查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取户档案家庭成员JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFamilyFileMembersData(String id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			Map<String,Object> map = FamilyMapTemplate.familyFileMembersTpl(dao.queryPovertyMember(params), dao.queryFamilyfileFamPic(params));
			
			json.put("code", map.get("RET_CODE"));
			json.put("msg", map.get("RET_MSG"));
			List<Map<String,Object>> memList = (List<Map<String, Object>>) map.get("MAPLIST");
			List<Map<String,Object>> picList = (List<Map<String, Object>>) map.get("PICLIST");
			
			JSONObject data = new JSONObject();
			JSONObject member = new JSONObject();
			List<JSONObject> tableList = new ArrayList<JSONObject>();
			int inCount = 0; //减少的人数
			int talCount = memList == null ? 0 : memList.size();
			for(int i=0;i<memList.size();i++){
				JSONObject mem = new JSONObject();
				String bz = memList.get(i).get("S_UPDATE_REASON") == null ? "" : memList.get(i).get("S_UPDATE_REASON").toString() ;
				if("2".equals(bz)){
					mem.put("order", "-");
					inCount++;
				}
				else mem.put("order", i+1);
				mem.put("name", memList.get(i).get("S_NAME"));
				mem.put("sex", memList.get(i).get("S_SEX"));
				mem.put("credential_type", memList.get(i).get("S_CREDENTIAL_TYPE"));
				mem.put("age", memList.get(i).get("S_AGE"));
				mem.put("relationship", memList.get(i).get("S_RELATIONSHIP"));
				mem.put("nationality", memList.get(i).get("S_NATIONALITY"));
				mem.put("political_affiliation", memList.get(i).get("S_POLITICAL_AFFILIATION"));
				mem.put("education", memList.get(i).get("S_EDUCATION"));
				mem.put("at_school", memList.get(i).get("S_AT_SCHOOL"));
				mem.put("health", memList.get(i).get("S_HEALTH"));
				mem.put("labor_capacity", memList.get(i).get("S_LABOR_CAPACITY"));
				mem.put("work_status", memList.get(i).get("S_WORK_STATUS"));
				mem.put("work_time", memList.get(i).get("S_WORK_TIME"));
				mem.put("active_service", memList.get(i).get("S_ACTIVE_SERVICE"));
				mem.put("serious_illness_insurance", memList.get(i).get("S_SERIOUS_ILLNESS_INSURANCE"));
				mem.put("technical_school_willing", memList.get(i).get("S_TECHNICAL_SCHOOL_WILLING"));
				mem.put("train_need", memList.get(i).get("S_TRAIN_NEED"));
				mem.put("skill_status", memList.get(i).get("S_SKILL_STATUS"));
				mem.put("employment_willing", memList.get(i).get("S_EMPLOYMENT_WILLING"));
				mem.put("employment_expectation", memList.get(i).get("S_EMPLOYMENT_EXPECTATION"));
				mem.put("staff_pension_insurance", memList.get(i).get("S_STAFF_PENSION_INSURANCE"));
				mem.put("resident_medical_insurance", memList.get(i).get("S_RESIDENT_MEDICAL_INSURANCE"));
				mem.put("resident_pension_insurance", memList.get(i).get("S_RESIDENT_PENSION_INSURANCE"));
				mem.put("pension_level", memList.get(i).get("S_PENSION_LEVEL"));
				if("2".equals(bz) || "1".equals(bz)){
					String time = memList.get(i).get("S_UPDATE_TIME") == null ? "" : memList.get(i).get("S_UPDATE_TIME").toString();
					if(!"".equals(time)){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
					    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    Date dates = fmt.parse(time);
					    if("1".equals(bz)) mem.put("comment", sdf.format(dates)+" 自然增加，增加原因"+memList.get(i).get("S_ADD_STATUS"));
					    if("2".equals(bz)) mem.put("comment", sdf.format(dates)+" 自然减少，减少原因"+memList.get(i).get("S_REDUCE_STATUS"));
					}else{
						if("1".equals(bz)) mem.put("comment", " 自然增加，增加原因"+memList.get(i).get("S_ADD_STATUS"));
						if("2".equals(bz)) mem.put("comment", " 自然减少，减少原因"+memList.get(i).get("S_REDUCE_STATUS"));
					}
					 
				}
				tableList.add(mem);
			}
			member.put("count", talCount-inCount);
			member.put("table", tableList);
			data.put("member", member);
			
			//家庭成员照片
			JSONObject album = new JSONObject();
			String house_holder_name = "";
			List<JSONObject> srcList = new ArrayList<JSONObject>();
			for(int j=0;j<picList.size();j++){
				house_holder_name = picList.get(j).get("S_HOUSE_HOLDER_NAME").toString();
				JSONObject pic = new JSONObject();
				pic.put("src", Global.getConfig("image.path")+picList.get(j).get("PATH"));
				srcList.add(pic);
			}
			album.put("house_holder_name", house_holder_name);
			album.put("list", srcList);
			data.put("album", album);
			json.put("data", data);
			
			//20170516
			//FileMembers fm = null;
			//fm = FamilyMapTemplate.familyFileMembersTplo(dao.queryPovertyMembero(params), dao.queryFamilyfileFamPico(params));
			//System.out.println("fmjson== "+fm.toString());
			//System.out.println("json== "+json.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案家庭成员查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
			//fm.setCode(1);
			//fm.setMsg("数据处理异常，获取信息失败!");		
		}
		return json.toString();
	}
	
	/**
	 * 获取户档案生活条件JSON数据
	 * @return
	 */
	public String getFamilyFileConditionData(String id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			Map<String,Object> map = FamilyMapTemplate.familyFileConditionTpl(dao.queryFamilyFileCond(params));
			
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			JSONObject data = new JSONObject();
			
			JSONObject production = new JSONObject();
			JSONObject cultivated_land = new JSONObject();
			cultivated_land.put("total", getValue(map,"S_TOTAL1"));
			cultivated_land.put("available", getValue(map,"S_AVAILABLE"));
			production.put("cultivated_land", cultivated_land);
			JSONObject forest_land = new JSONObject();
			forest_land.put("total", getValue(map,"S_TOTAL2"));
			forest_land.put("return", getValue(map,"S_RETURN"));
			forest_land.put("fruit", getValue(map,"S_FRUIT"));
			production.put("forest_land", forest_land);
			production.put("grass_total", getValue(map,"S_GRASS_TOTAL"));
			production.put("water_total", getValue(map,"S_WATER_TOTAL"));
			data.put("production", production);
			
			JSONObject living = new JSONObject();
			living.put("production_electricity", getValue(map,"S_PRODUCTION_ELECTRICITY"));
			living.put("living_electricity", getValue(map,"S_LIVING_ELECTRICITY"));
			living.put("water_safety", getValue(map,"S_WATER_SAFETY"));
			living.put("house_area", getValue(map,"S_HOUSE_AREA"));
			living.put("distance_main_road", getValue(map,"S_DISTANCE_MAIN_ROAD"));
			living.put("road_type", getValue(map,"S_ROAD_TYPE"));
			living.put("fuel_type", getValue(map,"S_FUEL_TYPE"));
			living.put("toilet", getValue(map,"S_TOILET"));
			living.put("joined_fsc", getValue(map,"S_JOINED_FSC"));
			data.put("living", living);
			json.put("data", data);
			
			//FamilyFileCondition di = new FamilyFileCondition();
			//FamilyFileConditionData gd = new FamilyFileConditionData();
			//di.setCode((Integer)getValue(map,"RET_CODE"));
			//di.setMsg(getValue(map,"RET_MSG")+"");
			//gd = (FamilyFileConditionData)dao.queryFamilyFileConditionData(params);
			//di.setData(gd);
			//System.out.println(json.toString());
			//System.out.println(gd.toString());
			//System.out.println(JsonFormatUtils.JsonFormat(di.toString()));
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案生活条件查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取户档案帮扶计划JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFamilyFilePlanData(String id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			Integer year = Integer.parseInt("2016");//df.format(new Date()));
			String[] item = {String.valueOf(year),String.valueOf(year+1),String.valueOf(year+2),"999"};
			params.put("item", item);
			Map<String,Object> map = FamilyMapTemplate.familyFilePlanTpl(dao.queryFamilyFilePlan(params));
			String[] tabTitleArr = {"三年帮扶总规划",String.valueOf(year)+"年度帮扶计划",String.valueOf(year+1)+"年度帮扶计划",String.valueOf(year+2)+"年度帮扶计划"};
			
			json.put("code", map.get("RET_CODE"));
			json.put("msg", map.get("RET_MSG"));
			JSONObject data = new JSONObject();
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			List<JSONObject> plansList = new ArrayList<JSONObject>();
			int n = mapList.size();
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
				plans.put("tab_title",  (mapList.get(i).get("S_PLAN_YEAR")==null ? 2016+i : mapList.get(i).get("S_PLAN_YEAR"))+"年度帮扶计划");
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
			log.error("户档案帮扶计划查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		/*FilePlan map = null;
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			Integer year = Integer.parseInt("2016");//df.format(new Date()));
			String[] item = {String.valueOf(year),String.valueOf(year+1),String.valueOf(year+2),"999"};
			params.put("item", item);
			map = FamilyMapTemplate.familyFilePlanTplo(dao.queryFamilyFilePlano(params));
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案帮扶计划查询失败异常信息："+e.getMessage());
			map.setCode(1);
			map.setMsg("数据处理异常，获取信息失败!");		
		}
		//System.out.println("json=== "+json.toString());
		//System.out.println("jsonmap=== "+map.toString());
*/		return json.toString();
	}
	
	/**
	 * 获取户档案帮扶施策JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFamilyFileImplementData(String id,String year) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			SimpleDateFormat df3= new SimpleDateFormat("yyyy");
			String nowyear = df3.format(new Date());
			params.put("YEAR", year==null ? nowyear : year);
			Map<String,Object> map = FamilyMapTemplate.familyFileImplementTpl(dao.queryFamilyFileMethodCount(params),dao.queryFamilyFileMethodList(params));
			
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
			JSONObject defaultTime = new JSONObject();
			defaultTime.put("year", "2017");
			data.put("default_time", defaultTime);
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
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			List<JSONObject> tableList = new ArrayList<JSONObject>();
			for(int i=0;i<mapList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("type", mapList.get(i).get("S_TYPE"));
				obj.put("object", mapList.get(i).get("S_OBJECT"));
				obj.put("name", mapList.get(i).get("S_NAME"));
				obj.put("amount", mapList.get(i).get("S_AMOUNT"));
				obj.put("invest_expected", mapList.get(i).get("S_INVEST_EXPECTED"));
				obj.put("invest_actual", mapList.get(i).get("S_INVEST_ACTUAL"));
				obj.put("profit", mapList.get(i).get("S_PROFIT"));
				obj.put("status", mapList.get(i).get("S_STATUS"));
				obj.put("time_start", mapList.get(i).get("S_TIME_START"));
				obj.put("time_completed", mapList.get(i).get("S_TIME_COMPLETED"));
				JSONObject album = new JSONObject();
				Map<String,Object> picParams = Constants.dateContants;
				picParams.put("PROJ_ID", mapList.get(i).get("S_PROJ_ID"));
				picParams.put("PIC_TYPE", "实施前");
				List<Map<String,Object>> before_albumList = dao.queryFamilyFileHelpPic(picParams);
				if(before_albumList.size()>0){
					album.put("before", Global.getConfig("image.path")+before_albumList.get(0).get("PATH"));//取第一条数据
				}
				picParams.put("PIC_TYPE", "实施中");
				List<Map<String,Object>>during_albumList = dao.queryFamilyFileHelpPic(picParams);
				if(during_albumList.size()>0){
					album.put("during", Global.getConfig("image.path")+during_albumList.get(0).get("PATH"));//取第一条数据
				}
				picParams.put("PIC_TYPE", "完成");
				List<Map<String,Object>>after_albumList = dao.queryFamilyFileHelpPic(picParams);
				if(after_albumList.size()>0){
					album.put("after", Global.getConfig("image.path")+after_albumList.get(0).get("PATH"));//取第一条数据
				}
				obj.put("album", album);
				tableList.add(obj);
			}
			table.put("list", tableList);
			
			JSONObject total = new JSONObject();
			total.put("invest_expected", getValue(map,"S_INVEST_EXPECTED"));
			total.put("invest_actual", getValue(map,"S_INVESTED"));
			total.put("profit", getValue(map,"S_PROFIT"));
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案帮扶施策查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取户档案帮扶动态JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFamilyFileNewsData(String id) {
		JSONObject json = new JSONObject();
		//FileNews map1 = null;
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			Map<String,Object> map = FamilyMapTemplate.familyFileNewsTpl(dao.queryFamilyFileTrends(params));
			
			json.put("code", map.get("RET_CODE"));
			json.put("msg", map.get("RET_MSG"));
			
			JSONObject data = new JSONObject();
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			List<JSONObject> newsList = new ArrayList<JSONObject>();
			for(int i=0;i<mapList.size();i++){
				JSONObject news = new JSONObject();
				news.put("id", mapList.get(i).get("S_ID"));
				news.put("time", mapList.get(i).get("S_TIME"));
				news.put("desc", mapList.get(i).get("S_DESC"));
				List<String> picsList = new ArrayList<String>();
				if(mapList.get(i).get("S_PICS")!=null){
					String pics = ClobToStringUtls.ClobToString((Clob)mapList.get(i).get("S_PICS"));
					String[] picsArr = pics.split(";");
					for(int j=0;j<picsArr.length;j++) {
						picsList.add(Global.getConfig("image.path")+picsArr[j]);
					}
				}
				news.put("pics", picsList);
				newsList.add(news);
			}
			data.put("list", newsList);
			json.put("data", data);
			
			/*//20170515 add
			
			//查询数据
			map1 = FamilyMapTemplate.familyFileNewsTplo(dao.queryFamilyFileTrendso(params));
			//System.out.println("json=== "+json.toString());
			System.out.println("jsonmap=== "+map1.toString());*/
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案帮扶动态查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");
			//map1.setCode(1);
			//map1.setMsg("数据处理异常，获取信息失败!");		
		}
		return json.toString();
	}
	
	/**
	 * 获取户档案帮扶动态详情JSON数据
	 * @return
	 */
	public String getFamilyFileNewsDetailData(String id) {
		JSONObject json = new JSONObject();
		//FileNewsDetail fnd = null;
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("HELP_ID", id);
			
			List<Map<String,Object>> pics = dao.queryFamilyFileTrendsPicById(params);
			
			
			Map<String,Object> map = FamilyMapTemplate.familyFileNewsDetailTpl(dao.queryFamilyFileTrendsById(params));
			
			json.put("code", map.get("RET_CODE"));
			json.put("msg", map.get("RET_MSG"));
			JSONObject data = new JSONObject();
			data.put("desc", map.get("S_DESC"));
			List<String> picsList = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(pics)){
				for(int j=0;j<pics.size();j++) {
					String pic = String.valueOf(pics.get(j).get("PATH"));
					if(StringUtils.isNotBlank(pic))
						picsList.add(Global.getConfig("image.path")+pic);
				}
			}
			data.put("pics", picsList);
			json.put("data", data);
			
			/*//20170516 add
			//查询数据
			List<Map<String,Object>> pics1 = dao.queryFamilyFileTrendsPicById(params);
			fnd = FamilyMapTemplate.familyFileNewsDetailTplo(dao.queryFamilyFileTrendsByIdo(params));
			List<String> picsList1 = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(pics1)){
				for(int j=0;j<pics1.size();j++) {
					String pic = String.valueOf(pics1.get(j).get("PATH"));
					if(StringUtils.isNotBlank(pic))
						picsList1.add(Global.getConfig("image.path")+pic);
				}
			}
			fnd.setPicsList(picsList1);
			fnd.toString();*/
			//System.out.println("json=== "+json.toString());
			//System.out.println("jsonmap=== "+fnd.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案帮扶动态详情查询失败异常信息："+e.getMessage());
			//fnd.setCode(1);
			//fnd.setMsg("数据处理异常，获取信息失败!");
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取户档案脱贫轨迹JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFamilyFileEliminatePathData(String id,String year) {
	    JSONObject json = new JSONObject();
		//FileEliminatePath fep = null;
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			params.put("YEAR_TAG", Constants.dateContants.get("F_FAMILYFILE_AID_TRAIL_DY").toString());
			SimpleDateFormat yeardf= new SimpleDateFormat("yyyy");
			String nowYear = yeardf.format(new Date());
			params.put("YEAR", year == null ? nowYear : year);
			Map<String,Object> map = FamilyMapTemplate.familyFileEliminatePathTpl(dao.queryFamilyfileChart(params));
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
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
			JSONObject defaultTime = new JSONObject();
			/*defaultTime.put("year", "2016");
			defaultTime.put("month", "5");
			data.put("default_time", defaultTime);*/
			String monthStr = null;
			List<Map<String,Object>> chartDataList = (List<Map<String,Object>>)map.get("MAPLIST");
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartDataList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartDataList.get(i).get("S_NAME"));
				chart.put("group", chartDataList.get(i).get("S_GROUP"));
				chart.put("value", chartDataList.get(i).get("S_TOTAL_SCORE"));
				chart_config.add(chart);
				if((i+1)==chartDataList.size()){
					//获取该年最新月份
					monthStr = (String) chartDataList.get(i).get("S_NAME");
					monthStr = monthStr.substring(5);
				}
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year1 =Constants.dateContants.get("F_FAMILYFILE_AID_TRAIL_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("F_FAMILYFILE_AID_TRAIL_DM").toString()));
			default_time.put("year", year == null ? year1 : year);
			default_time.put("month", monthStr == null ? month : monthStr);
			data.put("default_time", default_time);
			json.put("data", data);
			
			/*//20170517 add
			fep = FamilyMapTemplate.familyFileEliminatePathTplo(dao.queryFamilyfileCharto(params));
			FileEliminatePathData fepd = (FileEliminatePathData) fep.getData();
			String year =Constants.dateContants.get("F_FAMILYFILE_AID_TRAIL_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("F_FAMILYFILE_AID_TRAIL_DM").toString()));
			fepd.setMonth(month);
			fepd.setYear(year);*/
			//System.out.println("json=== "+json.toString());
			//System.out.println("jsonmap=== "+fep.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案脱贫轨迹查询失败异常信息："+e.getMessage());
			//fep.setCode(1);
			//fep.setMsg("数据处理异常，获取信息失败!");
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取户档案脱贫轨迹得分JSON数据
	 * @return
	 */
	public String getFamilyFileEliminateScoresData(String id,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			params.put("MONTH_TAG", year+month+"01");
			Map<String,Object> map = FamilyMapTemplate.familyFileEliminateScoresTpl(dao.queryFamilyfileAidTrail(params));
			
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			JSONObject data = new JSONObject();
			boolean[] b_arr = {false,true};
			List<JSONObject> scoresList = new ArrayList<JSONObject>();
			int fam_property = Integer.parseInt(getValue(map,"FAM_PROPERTY").toString());
			//家庭人均收入、低保五保政策落实
			if(fam_property==1){
				JSONObject scores1 = new JSONObject();
				scores1.put("index_name", "家庭人均收入");
				scores1.put("weight", "30");
				scores1.put("score", getValue(map,"FAM_DPI_SCORE"));
				scores1.put("value_name", "实际人均收入");
				scores1.put("value_actual", getValue(map,"FAM_DPI"));
				scores1.put("value_target", "≥"+getValue(map,"FAM_DPI_TARGET"));
				scores1.put("has_achieved", b_arr[Integer.parseInt(getValue(map,"FAM_HAS_ACHIEVED").toString())]);
				JSONObject tips1 = new JSONObject();
				tips1.put("desc", "有劳动能力户（含一般贫困户、有劳动能力的低保户），家庭年人均收入统计。");
				tips1.put("source", "建档立卡");
				tips1.put("calculation", "当前人均收入*30/当年目标值");
				scores1.put("tips", tips1);
				scoresList.add(scores1);
			}else{
				JSONObject scores1 = new JSONObject();
				scores1.put("index_name", "低保五保政策落实");
				scores1.put("weight", "30");
				scores1.put("score", getValue(map,"BASIC_LIVING_SCORE"));
				scores1.put("value_name", "脱贫实际值");
				scores1.put("value_actual", getValue(map,"BASIC_LIVING"));
				scores1.put("value_target", getValue(map,"BASIC_TARGET"));
				scores1.put("has_achieved", b_arr[Integer.parseInt(getValue(map,"BASIC_HAS_ACHIEVED").toString())]);
				JSONObject tips1 = new JSONObject();
				tips1.put("desc", "无劳动能力户低保五保政策落实情况统计。");
				tips1.put("source", "民政部门、建档立卡");
				tips1.put("calculation", "与每月享受低保或五保补贴建档立卡是无劳动能力户应该享受五保低保政策兜底贫困人口、民政是低保户和五保户的人口,两者人口信息一致时享受到了政策性兜底，为达标。");
				scores1.put("tips", tips1);
				scoresList.add(scores1);
			}
			//义务教育
			JSONObject scores2 = new JSONObject();
			scores2.put("index_name", "义务教育");
			scores2.put("weight", "15");
			scores2.put("score", getValue(map,"EDUCATION_SCORE"));
			scores2.put("value_name", "脱贫实际值");
			scores2.put("value_actual", getValue(map,"EDUCATION"));
			scores2.put("value_target","＞"+ getValue(map,"EDUCATION_TARGET"));
			scores2.put("has_achieved", b_arr[Integer.parseInt(getValue(map,"EDUCATION_HAS_ACHIEVED").toString())]);
			JSONObject tips2 = new JSONObject();
			tips2.put("desc", "贫困户义务教育毛入学率达到93%以上。");
			tips2.put("source", "教育部门、建档立卡");
			tips2.put("calculation", "1）有义务教育适龄儿童的贫困户：分子为义务教育适龄儿童指每年8月31日时“年龄>6岁-15岁”的贫困人口建档立卡为在校生、比对教育部也是当年在校生，两者人口信息一致时的人数；分母为贫困户义务教育适龄儿童人数  2）无义务教育适龄儿童的贫困户：均为达标。");
			scores2.put("tips", tips2);
			scoresList.add(scores2);
			data.put("scores", scoresList);
			//社会保障
			JSONObject scores3 = new JSONObject();
			scores3.put("index_name", "社会保障");
			scores3.put("weight", "10");
			scores3.put("score", getValue(map,"SOCIAL_SCORE"));
			scores3.put("value_name", "脱贫实际值");
			scores3.put("value_actual", getValue(map,"SOCIAL"));
			scores3.put("value_target", "＝"+getValue(map,"SOCIAL_TARGET"));
			scores3.put("has_achieved", b_arr[Integer.parseInt(getValue(map,"SOCIAL_HAS_ACHIEVED").toString())]);
			JSONObject tips3 = new JSONObject();
			tips3.put("desc", "贫困户60岁以上符合领取待遇的老人100%享受城乡居民社会养老保险待遇。");
			tips3.put("source", "人社部门、建档立卡");
			tips3.put("calculation", "1）有年满60周岁的贫困户：分子为建档立卡系统是年满60周岁的贫困人口，比对人社部门，每月领取了养老保险金的人数；分母为年满60周岁的家庭人口数 2）无年满60周岁人口的贫困户：均为达标。");
			scores3.put("tips", tips3);
			scoresList.add(scores3);
			data.put("scores", scoresList);
			//住房保障
			JSONObject scores4 = new JSONObject();
			scores4.put("index_name", "住房保障");
			scores4.put("weight", "20");
			scores4.put("score", getValue(map,"HOUSE_SCORE"));
			scores4.put("value_name", "脱贫实际值");
			scores4.put("value_actual", getValue(map,"HOUSE"));
			scores4.put("value_target", getValue(map,"HOUSE_TARGET"));
			scores4.put("has_achieved", b_arr[Integer.parseInt(getValue(map,"HOUSE_HAS_ACHIEVED").toString())]);
			JSONObject tips4 = new JSONObject();
			tips4.put("desc", "贫困户需有安全稳固住房。");
			tips4.put("source", "住建部门、建档立卡");
			tips4.put("calculation", "1）危房户：分子为建档立卡系统<户帮扶计划表住房改造项目>是完成状态的贫困户、比对住建部危房改造也是完成状态的贫困户为达标。  2）非危房户：均为达标。");
			scores4.put("tips", tips4);
			scoresList.add(scores4);
			data.put("scores", scoresList);
			//医疗保障
			JSONObject scores5 = new JSONObject();
			scores5.put("index_name", "医疗保障");
			scores5.put("weight", "10");
			scores5.put("score", getValue(map,"MEDICAL_SCORE"));
			scores5.put("value_name", "脱贫实际值");
			scores5.put("value_actual", getValue(map,"MEDICAL"));
			scores5.put("value_target", "＝"+getValue(map,"MEDICAL_TARGET"));
			scores5.put("has_achieved", b_arr[Integer.parseInt(getValue(map,"MEDICAL_HAS_ACHIEVED").toString())]);
			JSONObject tips5 = new JSONObject();
			tips5.put("desc", "贫困户合作医疗参加率100%。");
			tips5.put("source", "人社部门、建档立卡");
			tips5.put("calculation", "分子为建档立卡系统参加了参加城乡居民基本医疗保险、比对社保局也参加了城镇职工/城乡居民医疗");
			scores5.put("tips", tips5);
			scoresList.add(scores5);
			data.put("scores", scoresList);
			//帮扶到户
			JSONObject scores6 = new JSONObject();
			scores6.put("index_name", "帮扶到户");
			scores6.put("weight", "15");
			scores6.put("score", getValue(map,"HELP_FAM_SCORE"));
			scores6.put("value_name", "脱贫实际值");
			scores6.put("value_actual", getValue(map,"HELP_FAM"));
			scores6.put("value_target", getValue(map,"HELP_FAM_TARGET"));
			scores6.put("has_achieved", b_arr[Integer.parseInt(getValue(map,"HELP_FAM_HAS_ACHIEVED").toString())]);
			JSONObject tips6 = new JSONObject();
			tips6.put("desc", "享受到扶贫政策项目落实，至少有1项已完成。");
			tips6.put("source", "建档立卡");
			tips6.put("calculation", "户年度帮扶计划表至少有1个项是完成状态时为达标。");
			scores6.put("tips", tips6);
			scoresList.add(scores6);
			data.put("scores", scoresList);
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案脱贫轨迹得分查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取户档案资金投入JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFamilyFileInvestedData(String id,String year) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			String year1 = Constants.dateContants.get("F_FAMILYFILE_CAPITAL_DY").toString();
			year = year == null ? year1 : year;
			params.put("START_MONTH", year+"01");
			params.put("END_MONTH", year+"12");
			params.put("YEAR", year);
			Map<String,Object> capitalCount = dao.queryCapitalTotalCount(params);//累计
			Map<String,Object> capitalCountYear = dao.queryCapitalCount(params);//当年
			Map<String,Object> capitalYear = dao.queryCapitalListByYear(params);
			List<Map<String,Object>> capitalList = dao.queryCapitalList(params);
			Map<String,Object> map = FamilyMapTemplate.familyFileInvestedTpl(capitalCount, capitalCountYear, capitalYear, capitalList);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
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
			JSONObject defaultTime = new JSONObject();
			defaultTime.put("year", "2017");
			data.put("default_time", defaultTime);
			JSONObject summary = new JSONObject();
			summary.put("total", getValue(map,"S_TOTAL"));
			summary.put("cur_year", getValue(map,"S_YEAR_TOTAL"));
			data.put("summary", summary);
			List<JSONObject> tableList = new ArrayList<JSONObject>();
			JSONObject table = new JSONObject();
			table.put("title", "当年累计");
			table.put("organization", getValue(map,"S_ORGANIZATION"));
			JSONObject helper_city = new JSONObject();
			helper_city.put("city_finance", getValue(map,"S_HELPER_CITY_FINANCE"));
			helper_city.put("district_town_finance", getValue(map,"S_DISTRICT_TOWN_FINANCE"));
			helper_city.put("social_money", getValue(map,"S_HELPER_SOCIAL_MONEY"));
			table.put("helper_city", helper_city);
			JSONObject central_province = new JSONObject();
			central_province.put("central_finance", getValue(map,"S_CENTRAL_FINANCE"));
			central_province.put("province_finance", getValue(map,"S_PROVINCE_FINANCE"));
			central_province.put("central_industry", getValue(map,"S_CENTRAL_INDUSTRY"));
			central_province.put("province_industry", getValue(map,"S_PROVINCE_INDUSTRY"));
			central_province.put("social_money", getValue(map,"S_CENTRAL_SOCIAL_MONEY"));
			table.put("central_province", central_province);
			JSONObject helped_city = new JSONObject();
			helped_city.put("city_finance", getValue(map,"S_HELPED_CITY_FINANCE"));
			helped_city.put("county_town_finance", getValue(map,"S_COUNTY_TOWN_FINANCE"));
			helped_city.put("city_industry", getValue(map,"S_CITY_INDUSTRY"));
			helped_city.put("county_town_industry", getValue(map,"S_COUNTY_TOWN_INDUSTRY"));
			helped_city.put("social_money", getValue(map,"S_HELPED_SOCIAL_MONEY"));
			table.put("helped_city", helped_city);
			table.put("summary", getValue(map,"S_SUMMARY"));
			tableList.add(table);
			
			List<Map<String,Object>> mapList = (List<Map<String,Object>>)map.get("MAPLIST");
			for(int i=0;i<mapList.size();i++){
				JSONObject capital_table = new JSONObject();
				capital_table.put("title", mapList.get(i).get("S_TITLE"));
				capital_table.put("organization", mapList.get(i).get("S_ORGANIZATION"));
				JSONObject capital_helper_city = new JSONObject();
				capital_helper_city.put("city_finance", mapList.get(i).get("S_HELPER_CITY_FINANCE"));
				capital_helper_city.put("district_town_finance", mapList.get(i).get("S_DISTRICT_TOWN_FINANCE"));
				capital_helper_city.put("social_money", mapList.get(i).get("S_HELPER_SOCIAL_MONEY"));
				capital_table.put("helper_city", capital_helper_city);
				JSONObject capital_central_province = new JSONObject();
				capital_central_province.put("central_finance", mapList.get(i).get("S_CENTRAL_FINANCE"));
				capital_central_province.put("province_finance", mapList.get(i).get("S_PROVINCE_FINANCE"));
				capital_central_province.put("central_industry", mapList.get(i).get("S_CENTRAL_INDUSTRY"));
				capital_central_province.put("province_industry", mapList.get(i).get("S_PROVINCE_INDUSTRY"));
				capital_central_province.put("social_money", mapList.get(i).get("S_CENTRAL_SOCIAL_MONEY"));
				capital_table.put("central_province", capital_central_province);
				JSONObject capital_helped_city = new JSONObject();
				capital_helped_city.put("city_finance", mapList.get(i).get("S_HELPED_CITY_FINANCE"));
				capital_helped_city.put("county_town_finance", mapList.get(i).get("S_COUNTY_TOWN_FINANCE"));
				capital_helped_city.put("city_industry", mapList.get(i).get("S_CITY_INDUSTRY"));
				capital_helped_city.put("county_town_industry", mapList.get(i).get("S_COUNTY_TOWN_INDUSTRY"));
				capital_helped_city.put("social_money", mapList.get(i).get("S_HELPED_SOCIAL_MONEY"));
				capital_table.put("helped_city", capital_helped_city);
				capital_table.put("summary", mapList.get(i).get("S_SUMMARY"));
				tableList.add(capital_table);
			}
			data.put("table", tableList);
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案资金投入查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取户档案数据轨迹JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getFamilyFileDataPathData(String id) {
		JSONObject json = new JSONObject();
		Map<String,Object> params = Constants.dateContants;
		params.put("FAM_ID", id);
		SimpleDateFormat df= new SimpleDateFormat("yyyy");
		String year = df.format(new Date());
		params.put("YEAR", year);
		try{
			Map<String,Object> map = FamilyMapTemplate.familyFileDataPathTpl(dao.queryFamilyFileDataPath(params));//目前空数据
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			JSONObject data = new JSONObject();
			JSONObject create = new JSONObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
			Map<String,Object> cmap = (Map<String, Object>) map.get("create");
			create.put("date", cmap == null || cmap.get("MOD_TIME") == null ? "" : sdf.format(fmt.parse(getValue(cmap,"MOD_TIME").toString())));
			create.put("time", "");
			create.put("publisher_organization", getValue(cmap,"UPDATER_AREA_NAME"));
			create.put("publisher_name", getValue(cmap,"UPDATER_NAME"));
			data.put("create", create);
			
			List<Object> updateListobj = (List<Object>) map.get("update");
			List<JSONObject> updateList = new ArrayList<JSONObject>();
			for(int i=0;null!= updateListobj && i<updateListobj.size();i++){
				List<Map<String,Object>> upList = (List<Map<String, Object>>) updateListobj.get(i);
				JSONObject update = new JSONObject();
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int k=0;null!= upList && k<upList.size();k++){
					Map<String,Object> mt = upList.get(k);
					if(k==0){
						update.put("date",sdf.format(fmt.parse(getValue(mt,"MOD_TIME").toString())));
						update.put("time", "");
						update.put("publisher_organization", getValue(mt,"UPDATER_AREA_NAME"));
						update.put("publisher_name", getValue(mt,"UPDATER_NAME"));
					}
					JSONObject obj = new JSONObject();
					obj.put("name", getValue(mt,"MOD_CONTENT")+"（"+getValue(mt,"POP_NAME")+"）");
					obj.put("old", getValue(mt,"VAL_BEFORE"));
					obj.put("new", getValue(mt,"VAL_AFTER"));
					list.add(obj);
					
					
				}
				update.put("list", list);
				updateList.add(update);
			}
			data.put("update", updateList);
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案数据轨迹查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取户档案业务办理JSON数据
	 * @return
	 */
	public String getFamilyFileBusinessData(String id) {
		//JSONObject json = new JSONObject();
		FamilyFileBusiness di = new FamilyFileBusiness();
		FamilyFileBusinessData gd = new FamilyFileBusinessData();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			params.put("PROCESS_STATUS", "在办");
			//List<Map<String,Object>> doingDataList = dao.queryBusiProcessList(params);
			List<BaseListMode> doingDataListNew = dao.queryBusiProcessListNew(params);
			params.put("PROCESS_STATUS", "已办");
			//List<Map<String,Object>> doneDataList = dao.queryBusiProcessList(params);
			List<BaseListMode> doneDataListNew = dao.queryBusiProcessListNew(params);
			params.put("PROCESS_STATUS", "待办");
			//List<Map<String,Object>> toDoDataList = dao.queryBusiProcessList(params);
			List<BaseListMode> toDoDataListNew = dao.queryBusiProcessListNew(params);
			
			di.setCode(0);
			di.setMsg("获取信息成功");
			gd.setListDoing(doingDataListNew);
			gd.setListToDo(toDoDataListNew);
			gd.setListDone(doneDataListNew);
			di.setData(gd);
			/*json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			//在办
			JSONObject list_doing = new JSONObject();
			list_doing.put("amount_total", toDoDataList.size());
			List<JSONObject> listDoing = new ArrayList<JSONObject>();
			int doing_size = toDoDataList.size()>3?3:toDoDataList.size();
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
			//在办
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
			json.put("data", data);*/
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案业务办理查询失败异常信息："+e.getMessage());
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
	 * 获取户档案业务办理更多JSON数据
	 * @return
	 */
	public String getFamilyFileBusinessMoreData(String id,String type) {
		//JSONObject json = new JSONObject();
		FileBusinessRest fbr = null;
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			String process_status = "";
			if("list_doing".equals(type)){
				process_status = "在办";
			}else if("list_done".equals(type)){
				process_status = "已办";
			}else if("list_todo".equals(type)){
				process_status = "待办";
			}
			params.put("PROCESS_STATUS", process_status);
			/*List<Map<String,Object>> dataList = dao.queryBusiProcessList(params);
			
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
			json.put("data", data);*/
			
			//20170516 add
			List<TextLink> textList = dao.queryBusiProcessListo(params);
			fbr = new FileBusinessRest();
			FileBusinessRestData fbrd = new FileBusinessRestData();
			fbrd.setList(textList);
			fbr.setCode(0);
			fbr.setMsg("获取信息成功");
			//System.out.println("fbrJson=="+fbr.toString());
			//System.out.println("Json=="+json.toString());
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案业务办理更多查询失败异常信息："+e.getMessage());
			fbr.setCode(1);
			fbr.setMsg("数据处理异常，获取信息失败!");
			//json.put("code", 1);
			//json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return fbr.toString();
	}
	
	/**
	 * 获取户档案人均可支配收入JSON数据
	 * @param id 户主Id
	 * @param year 年份
	 * @return
	 */
	public String getFamilyIncomeData(String id,String year) {
		JSONObject json = new JSONObject();
		FileIncome famMaps = null;
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			SimpleDateFormat df3= new SimpleDateFormat("yyyy");
			String nowyear = df3.format(new Date());
			params.put("YEAR", year==null ? nowyear : year);
			/*Iterator<Entry<String, Object>> entries = params.entrySet().iterator();  
			while (entries.hasNext()) {  
				Entry<String, Object> entry = entries.next();
				//params.put(entry.getKey(),entry.getValue());
				System.out.println("key= "+entry.getKey()+"  value== "+entry.getValue());
			}*/
			//各类统计数
			Map<String, Object> FamMap = FamilyMapTemplate.familyIncomeTpl(dao.queryPerCapitaDisincomeCount(params));
			//统计图
			List<Map<String, Object>> chartlist =  dao.queryPerCapitaDisincomeChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
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
//			year =Constants.dateContants.get("TW_FAMILY_INCOME_M_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("TW_FAMILY_INCOME_MM").toString()));
			default_time.put("year", year==null ? nowyear : year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			JSONObject stat = new JSONObject();
			stat.put("total_income", FamMap.get("S_TOTAL_INCOME"));
			stat.put("total_expense", FamMap.get("S_TOTAL_EXPENSE"));
			stat.put("disposable_income", FamMap.get("S_DISPOSABLE_INCOME"));
			BigDecimal savgIncome = FamMap.get("S_YEAR_AVG_DISPOSABLE_INCOME") == null ? new BigDecimal(0) : (BigDecimal)FamMap.get("S_YEAR_AVG_DISPOSABLE_INCOME");
			stat.put("year_average_disposable_income", savgIncome+"元");
			data.put("stat", stat);
			List<JSONObject> chart_config_bar = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartlist.get(i).get("S_NAME"));
				chart.put("group", chartlist.get(i).get("S_GROUP"));
				chart.put("value", chartlist.get(i).get("S_VALUE"));
				chart_config_bar.add(chart);
			}
			data.put("chart_config_bar", chart_config_bar);
			json.put("data", data);
			
		/*	//各类统计数
			famMaps = FamilyMapTemplate.familyIncomeTplo(dao.queryPerCapitaDisincomeCounto(params));
			//统计图
			List<ChartConfigBar> chartlists =  dao.queryPerCapitaDisincomeCharto(params);
			FileIncomeData fid = (FileIncomeData) famMaps.getData();
			fid.setChartConfigBar(chartlists);
		//	year =Constants.dateContants.get("TW_FAMILY_INCOME_M_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("TW_FAMILY_INCOME_MM").toString()));
			fid.setYear(year);
			fid.setMonth(month);*/
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案人均可支配收入查询失败异常信息："+e.getMessage());
			/*famMaps.setCode(1);
			famMaps.setMsg("数据处理异常，获取信息失败!");*/
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println("户档案人均可支配收入=="+JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取户档案人均可支配收入统计表JSON数据
	 * @param id 区域PAC
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getFamilyIncomeTableData(String id,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("FAM_ID", id);
			params.put("month", year+month+"01");
			params.put("YEAR", year);
			//明细列表
			List<Map<String, Object>> datalist = dao.queryPerCapitaDisincomeTable(params);
//			//明细列表
//			Map<String, Object> dataMap = FamilyMapTemplate.familyIncomeTableTpl(null);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			List<JSONObject> table = new ArrayList<JSONObject>();
//			JSONObject obj = new JSONObject();
//			obj.put("title", year+"年累计");
//			obj.put("average_disposable_income", dataMap.get("S_AVERAGE_DISPOSABLE_INCOME"));
//			obj.put("family_disposable_income", dataMap.get("S_FAMILY_DISPOSABLE_INCOME"));
//			JSONObject income = new JSONObject();
//			income.put("total_income", dataMap.get("S_TOTAL_INCOME"));
//			income.put("salary_income", dataMap.get("S_SALARY_INCOME"));
//			income.put("production_income", dataMap.get("S_PRODUCTION_INCOME"));
//			income.put("property_income", dataMap.get("S_PROPERTY_INCOME"));
//			income.put("transferred_income", dataMap.get("S_TRANSFERRED_INCOME"));
//			income.put("birth_subsidy", dataMap.get("S_BIRTH_SUBSIDY"));
//			income.put("low_subsidy", dataMap.get("S_LOW_SUBSIDY"));
//			income.put("five_subsidy", dataMap.get("S_FIVE_SUBSIDY"));
//			income.put("pension", dataMap.get("S_PENSION"));
//			income.put("env_subsidy", dataMap.get("S_ENV_SUBSIDY"));
//			income.put("medical_insurance", dataMap.get("S_MEDICAL_INSURANCE"));
//			income.put("medical_subsidy", dataMap.get("S_MEDICAL_SUBSIDY"));
//			income.put("other_transferred_income", dataMap.get("S_OTHER_TRANSFERRED_INCOME"));
//			obj.put("income", income);
//			JSONObject expense = new JSONObject();
//			expense.put("total_payment", dataMap.get("S_TOTAL_PAYMENT"));
//			expense.put("production_payment", dataMap.get("S_PRODUCTION_PAYMENT"));
//			expense.put("transferred_income", dataMap.get("S_TRANSFERRED_INCOME"));
//			expense.put("individual_income_tax", dataMap.get("S_INDIVIDUAL_INCOME_TAX"));
//			expense.put("social_security_payment", dataMap.get("S_SOCIAL_SECURITY_PAYMENT"));
//			expense.put("alimony", dataMap.get("S_ALIMONY"));
//			expense.put("other_transferred_payment", dataMap.get("S_OTHER_TRANSFERRED_PAYMENT"));
//			expense.put("unpaid_loan", dataMap.get("S_UNPAID_LOAN"));
//			obj.put("expense", expense);
//			table.add(obj);
			
			for(int i=0;i<datalist.size();i++){
				JSONObject obj2 = new JSONObject();
				obj2.put("title", datalist.get(i).get("S_TITLE"));
				obj2.put("average_disposable_income", datalist.get(i).get("S_AVERAGE_DISPOSABLE_INCOME"));
				obj2.put("family_disposable_income", datalist.get(i).get("S_FAMILY_DISPOSABLE_INCOME"));
				
				JSONObject income2 = new JSONObject();
				income2.put("total_income", datalist.get(i).get("S_TOTAL_INCOME"));
				income2.put("salary_income", datalist.get(i).get("S_SALARY_INCOME"));
				income2.put("production_income", datalist.get(i).get("S_PRODUCTION_INCOME"));
				income2.put("property_income", datalist.get(i).get("S_PROPERTY_INCOME"));
				income2.put("transferred_income", datalist.get(i).get("S_TRANSFERRED_INCOME"));
				income2.put("birth_subsidy", datalist.get(i).get("S_BIRTH_SUBSIDY"));
				income2.put("low_subsidy", datalist.get(i).get("S_LOW_SUBSIDY"));
				income2.put("five_subsidy", datalist.get(i).get("S_FIVE_SUBSIDY"));
				income2.put("pension", datalist.get(i).get("S_PENSION"));
				income2.put("env_subsidy", datalist.get(i).get("S_ENV_SUBSIDY"));
				income2.put("medical_insurance", datalist.get(i).get("S_MEDICAL_INSURANCE"));
				income2.put("medical_subsidy", datalist.get(i).get("S_MEDICAL_SUBSIDY"));
				income2.put("other_transferred_income", datalist.get(i).get("S_OTHER_TRANSFERRED_INCOME"));
				obj2.put("income", income2);
				JSONObject expense2 = new JSONObject();
				expense2.put("total_payment", datalist.get(i).get("S_TOTAL_PAYMENT"));
				expense2.put("production_payment", datalist.get(i).get("S_PRODUCTION_PAYMENT"));
				expense2.put("transferred_income", datalist.get(i).get("S_TRANSFERRED_INCOME2"));
				expense2.put("individual_income_tax", datalist.get(i).get("S_INDIVIDUAL_INCOME_TAX"));
				expense2.put("social_security_payment", datalist.get(i).get("S_SOCIAL_SECURITY_PAYMENT"));
				expense2.put("alimony", datalist.get(i).get("S_ALIMONY"));
				expense2.put("other_transferred_payment", datalist.get(i).get("S_OTHER_TRANSFERRED_PAYMENT"));
				expense2.put("unpaid_loan", datalist.get(i).get("S_UNPAID_LOAN"));
				obj2.put("expense", expense2);
				
				table.add(obj2);
			}
			if(null==datalist || datalist.size() < 1){
				JSONObject obj2 = new JSONObject();
				obj2.put("title", year+"年累计");
				obj2.put("average_disposable_income", 0);
				obj2.put("family_disposable_income", 0);
				
				JSONObject income2 = new JSONObject();
				income2.put("total_income", 0);
				income2.put("salary_income", 0);
				income2.put("production_income", 0);
				income2.put("property_income", 0);
				income2.put("transferred_income", 0);
				income2.put("birth_subsidy", 0);
				income2.put("low_subsidy", 0);
				income2.put("five_subsidy", 0);
				income2.put("pension", 0);
				income2.put("env_subsidy", 0);
				income2.put("medical_insurance", 0);
				income2.put("medical_subsidy", 0);
				income2.put("other_transferred_income", 0);
				obj2.put("income", income2);
				JSONObject expense2 = new JSONObject();
				expense2.put("total_payment", 0);
				expense2.put("production_payment", 0);
				expense2.put("transferred_income", 0);
				expense2.put("individual_income_tax", 0);
				expense2.put("social_security_payment", 0);
				expense2.put("alimony", 0);
				expense2.put("other_transferred_payment", 0);
				expense2.put("unpaid_loan", 0);
				obj2.put("expense", expense2);
				
				table.add(obj2);
			}
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("户档案人均可支配收入统计表查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	 private Object getValue(Map map,String key){
	    	return CommonUtil.getMapValue(map,key);
		}
	 
	 public static void main(String[] args) throws ParseException {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		    Date dates = new Date();
		    System.out.println("=============5555== "+dates);
			System.out.println("=============当前月份：== "+sdf.format(dates));
			
			
	}
}
