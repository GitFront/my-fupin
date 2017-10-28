package com.aspire.birp.modules.antiPoverty.service;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.birp.common.utils.CommonUtil;
import com.aspire.birp.modules.antiPoverty.dao.AreaDao;
import com.aspire.birp.modules.antiPoverty.dao.DataAnalyseDao;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.antiPoverty.utils.DataAnalyseMapTemplate;
import com.aspire.birp.modules.antiPoverty.utils.JsonFormatUtils;
import com.aspire.birp.modules.sys.utils.UserUtils;

/** 
 * 数据分析服务Service 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:33:23 
 */
@Service
public class DataAnalyseService {
	
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private DataAnalyseDao analyseDao;
	
	/**
	 * 获取上一级的PAC
	 * @param id 区域PAC
	 * @return
	 */
	public String queryParentId(String id) {
		return areaDao.queryParentId(id);
	}
	
	/**
	 * 获取行政区域数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @return
	 */
	public Map<String,Object> getAreaPathMap(String id,String level) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String userPac = UserUtils.getAreaList().iterator().next().toString();
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("ID", id);
			params.put("level", level);
			map.put("area", areaDao.queryAreaById(params));
			map.put("scope", areaDao.queryAreaScope(params));
			if(!"country".equals(level)){
				params.put("userPac", userPac);
				if(Long.parseLong(id)<Long.parseLong(userPac)){
					map.put("areaList", areaDao.querySubAreaListByUserPac(params));
				}else{
					map.put("areaList", areaDao.querySubAreaList(params));
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 获取数据分析行政区域JSON数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAreaPathData(Map<String,Object> Map) {
		JSONObject json = new JSONObject();
		try{
			Map<String,Object> provinceMap = (Map<String,Object>)Map.get("provinceMap");
			Map<String,Object> cityMap = (Map<String,Object>)Map.get("cityMap");
			List<Map<String,Object>> cityList = (List<Map<String,Object>>)Map.get("cityList");
			Map<String,Object> countyMap = (Map<String,Object>)Map.get("countyMap");
			List<Map<String,Object>> countyList = (List<Map<String,Object>>)Map.get("countyList");
			Map<String,Object> townMap = (Map<String,Object>)Map.get("townMap");
			List<Map<String,Object>> townList = (List<Map<String,Object>>)Map.get("townList");
			Map<String,Object> countryMap = (Map<String,Object>)Map.get("countryMap");
			List<Map<String,Object>> countryList = (List<Map<String,Object>>)Map.get("countryList");
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			List<JSONObject> areaList = new ArrayList<JSONObject>();
			JSONObject province = new JSONObject();
			province.put("level", "province");
			province.put("selected_id", (String)getValue(provinceMap,"ID"));
			province.put("selected_name", (String)getValue(provinceMap,"NAME"));
			List<JSONObject> province_list = new ArrayList<JSONObject>();
			JSONObject province_obj = new JSONObject();
			province_obj.put("id", (String)getValue(provinceMap,"ID"));
			province_obj.put("name", (String)getValue(provinceMap,"NAME"));
			province_list.add(province_obj);
			province.put("list", province_list);
			areaList.add(province);
			
			JSONObject city = new JSONObject();
			city.put("level", "city");
			if(cityMap == null){
				city.put("selected_id", JSONNull.getInstance());
				city.put("selected_name", "全部");
			}else{
				city.put("selected_id", (String)getValue(cityMap,"ID"));
				city.put("selected_name", (String)getValue(cityMap,"NAME"));
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
			
			if(cityMap != null && countyList.size()>0){
				JSONObject county = new JSONObject();
				county.put("level", "county");
				if(countyMap == null){
					county.put("selected_id", JSONNull.getInstance());
					county.put("selected_name", "全部");
				}else{
					county.put("selected_id", (String)getValue(countyMap,"ID"));
					county.put("selected_name", (String)getValue(countyMap,"NAME"));
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
			
			if(countyMap != null && townList.size()>0){
				JSONObject town = new JSONObject();
				town.put("level", "town");
				if(townMap == null){
					town.put("selected_id", JSONNull.getInstance());
					town.put("selected_name", "全部");
				}else{
					town.put("selected_id", (String)getValue(townMap,"ID"));
					town.put("selected_name", (String)getValue(townMap,"NAME"));
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
				if(countryMap == null){
					country.put("selected_id", JSONNull.getInstance());
					country.put("selected_name", "全部");
				}else{
					country.put("selected_id", (String)getValue(countryMap,"ID"));
					country.put("selected_name", (String)getValue(countryMap,"NAME"));
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
			
			JSONObject data = new JSONObject();
			data.put("area_path", areaList);
			
			Map<String,Object> scopeMap = (Map<String,Object>)Map.get("scopeMap");
			Boolean[] scopeFlag = {false,true};
			JSONObject scope = new JSONObject();
			scope.put("1", scopeFlag[1]);
			if("1".equals(getValue(scopeMap,"IF_FURTHER_POOR").toString())){
				scope.put("2", scopeFlag[1]);
				scope.put("3", scopeFlag[0]);
			}else{
				scope.put("2", scopeFlag[0]);
				scope.put("3", scopeFlag[1]);
			}
			scope.put("4", scopeFlag[Integer.parseInt(getValue(scopeMap,"IF_REVOLUTION_AREA").toString())]);
			scope.put("5", scopeFlag[Integer.parseInt(getValue(scopeMap,"IF_SOVIET_AREA").toString())]);
			data.put("scope_available", scope);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析人均可支配收入分析JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_type（ all:全部；have_labor:有劳动力户；no_labor:无劳动力户）
	 * @return
	 */
	public String getAverageIncomeData(String id,String level,String scope,String poor_type,String year) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("poor_type", Constants.poorTypeMap.get(poor_type).toString());
			params.put("year", year);
			
			//总数
			Map<String, Object> dataTotalMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAverageIncomeCount(params),"AverageIncomeTotal");
			//各类人口数
			Map<String, Object> dataFamMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAveragePopCnt(params),"AverageIncomeFam");
			//统计图
			List<Map<String, Object>> chartlist =  analyseDao.queryAverageCount(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject balance = new JSONObject();
			balance.put("total_income", getValue(dataTotalMap,"S_TOTAL_INCOME"));
			balance.put("total_payment", getValue(dataTotalMap,"S_TOTAL_PAYMENT"));
			balance.put("disposable_income", getValue(dataTotalMap,"S_DISPOSABLE_INCOME"));
			balance.put("average_disposable_income", getValue(dataTotalMap,"S_AVERAGE_DISPOSABLE_INCOME"));
			data.put("balance", balance);
			JSONObject stat = new JSONObject();
			stat.put("poor_family", getValue(dataFamMap,"S_POOR_FAMILY"));
			stat.put("common_poor_family", getValue(dataFamMap,"S_COMMON_POOR_FAMILY"));
			stat.put("have_labor_low_family", getValue(dataFamMap,"S_HAVE_LABOR_LOW_FAMILY"));
			stat.put("no_labor_low_family", getValue(dataFamMap,"S_NO_LABOR_LOW_FAMILY"));
			stat.put("five_family", getValue(dataFamMap,"S_FIVE_FAMILY"));
			stat.put("poor_people", getValue(dataFamMap,"S_POOR_PEOPLE"));
			stat.put("common_poor_people", getValue(dataFamMap,"S_COMMON_POOR_PEOPLE"));
			stat.put("have_labor_low_people", getValue(dataFamMap,"S_HAVE_LABOR_LOW_PEOPLE"));
			stat.put("no_labor_low_people", getValue(dataFamMap,"S_NO_LABOR_LOW_PEOPLE"));
			stat.put("five_people", getValue(dataFamMap,"S_FIVE_PEOPLE"));
			data.put("stat", stat);
			List<JSONObject> chart_config_bars = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartlist.get(i).get("S_NAME"));
				chart.put("group", chartlist.get(i).get("S_GROUP"));
				chart.put("value", chartlist.get(i).get("S_VALUE"));
				chart_config_bars.add(chart);
			}
			data.put("chart_config_bars", chart_config_bars);
			JSONObject default_time = new JSONObject();
//			String year =Constants.dateContants.get("R_ANALY_POOR_FAM_PROPERTY_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_POOR_FAM_PROPERTY_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析人均可支配收入分析统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_type（ all:全部；have_labor:有劳动力户；no_labor:无劳动力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getAverageIncomeTableData(String id,String level,String scope,String poor_type,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("poor_type", Constants.poorTypeMap.get(poor_type).toString());
			params.put("month", year+month+"01");
			params.put("year", year);
			//明细列表
			List<Map<String, Object>> datalist = analyseDao.queryAverageTable(params);
			//明细列表
			Map<String, Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAverageTotal(params),"AverageIncomeTable");
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<datalist.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_name", datalist.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("average_disposable_income", datalist.get(i).get("S_AVERAGE_DISPOSABLE_INCOME"));
				obj.put("total_income", datalist.get(i).get("S_TOTAL_INCOME"));
				obj.put("salary_income", datalist.get(i).get("S_SALARY_INCOME"));
				obj.put("production_income", datalist.get(i).get("S_PRODUCTION_INCOME"));
				obj.put("property_income", datalist.get(i).get("S_PROPERTY_INCOME"));
				obj.put("birth_subsidy", datalist.get(i).get("S_BIRTH_SUBSIDY"));
				obj.put("low_subsidy", datalist.get(i).get("S_LOW_SUBSIDY"));
				obj.put("five_subsidy", datalist.get(i).get("S_FIVE_SUBSIDY"));
				obj.put("pension", datalist.get(i).get("S_PENSION"));
				obj.put("env_subsidy", datalist.get(i).get("S_ENV_SUBSIDY"));
				obj.put("medical_insurance", datalist.get(i).get("S_MEDICAL_INSURANCE"));
				obj.put("medical_subsidy", datalist.get(i).get("S_MEDICAL_SUBSIDY"));
				obj.put("other_transferred_income", datalist.get(i).get("S_OTHER_TRANSFERRED_INCOME"));
				obj.put("total_payment", datalist.get(i).get("S_TOTAL_PAYMENT"));
				obj.put("production_payment", datalist.get(i).get("S_PRODUCTION_PAYMENT"));
				obj.put("individual_income_tax", datalist.get(i).get("S_INDIVIDUAL_INCOME_TAX"));
				obj.put("social_security_payment", datalist.get(i).get("S_SOCIAL_SECURITY_PAYMENT"));
				obj.put("alimony", datalist.get(i).get("S_ALIMONY"));
				obj.put("other_transferred_payment", datalist.get(i).get("S_OTHER_TRANSFERRED_PAYMENT"));
				obj.put("unpaid_loan", datalist.get(i).get("S_UNPAID_LOAN"));
				obj.put("family_disposable_income", datalist.get(i).get("S_FAMILY_DISPOSABLE_INCOME"));
				list.add(obj);
			}
			JSONObject obj = new JSONObject();
			obj.put("area_name", "合计");
			obj.put("order", "-");
			obj.put("average_disposable_income", getValue(dataMap,"S_AVERAGE_DISPOSABLE_INCOME"));
			obj.put("total_income", getValue(dataMap,"S_TOTAL_INCOME"));
			obj.put("salary_income", getValue(dataMap,"S_SALARY_INCOME"));
			obj.put("production_income", getValue(dataMap,"S_PRODUCTION_INCOME"));
			obj.put("property_income", getValue(dataMap,"S_PROPERTY_INCOME"));
			obj.put("birth_subsidy", getValue(dataMap,"S_BIRTH_SUBSIDY"));
			obj.put("low_subsidy", getValue(dataMap,"S_LOW_SUBSIDY"));
			obj.put("five_subsidy", getValue(dataMap,"S_FIVE_SUBSIDY"));
			obj.put("pension", getValue(dataMap,"S_PENSION"));
			obj.put("env_subsidy", getValue(dataMap,"S_ENV_SUBSIDY"));
			obj.put("medical_insurance", getValue(dataMap,"S_MEDICAL_INSURANCE"));
			obj.put("medical_subsidy", getValue(dataMap,"S_MEDICAL_SUBSIDY"));
			obj.put("other_transferred_income", getValue(dataMap,"S_OTHER_TRANSFERRED_INCOME"));
			obj.put("total_payment", getValue(dataMap,"S_TOTAL_PAYMENT"));
			obj.put("production_payment", getValue(dataMap,"S_PRODUCTION_PAYMENT"));
			obj.put("individual_income_tax", getValue(dataMap,"S_INDIVIDUAL_INCOME_TAX"));
			obj.put("social_security_payment", getValue(dataMap,"S_SOCIAL_SECURITY_PAYMENT"));
			obj.put("alimony", getValue(dataMap,"S_ALIMONY"));
			obj.put("other_transferred_payment", getValue(dataMap,"S_OTHER_TRANSFERRED_PAYMENT"));
			obj.put("unpaid_loan", getValue(dataMap,"S_UNPAID_LOAN"));
			obj.put("family_disposable_income", getValue(dataMap,"S_FAMILY_DISPOSABLE_INCOME"));
			list.add(obj);
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析社会政策性兜底JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @return
	 */
	public String getSocialPolicyData(String id,String level,String scope,String poor_attribute) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("poor_attribute", Constants.poorAttributeMap.get(poor_attribute));
			params.put("month",Constants.dateContants.get("R_ANALY_BOTTOM_CALC_DM"));
			//最上面 两个
			Map<String, Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.querySocialPolicyAmount(params),"SocialPolicy");
			//统计图
			List<Map<String,Object>> chartlist = analyseDao.querySocialPolicyCount(params);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject family = new JSONObject();
			family.put("amount_total", getValue(dataMap,"S_TOTAL_AMOUNT_POOR_FAMILY"));
			family.put("amount_diff", getValue(dataMap,"S_TOTAL_AMOUNT_DIFF_FAMILY"));
			family.put("percent", getValue(dataMap,"S_TOTAL_RATIO_DIFF_FAMILY"));
			data.put("family", family);
			JSONObject people = new JSONObject();
			people.put("amount_total", getValue(dataMap,"S_TOTAL_AMOUNT_POOR_PEOPLE"));
			people.put("amount_diff", getValue(dataMap,"S_TOTAL_AMOUNT_DIFF_PEOPLE"));
			people.put("percent", getValue(dataMap,"S_TOTAL_RATIO_DIFF_PEOPLE"));
			data.put("people", people);
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject chart1 = new JSONObject();
				chart1.put("name", chartlist.get(i).get("S_NAME"));
				chart1.put("group", chartlist.get(i).get("S_LINE_GROUP"));
				chart1.put("value", chartlist.get(i).get("S_LINE_VALUE"));
				chart1.put("type", "line");
				chart_config.add(chart1);
			}
			for(int i=0;i<chartlist.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartlist.get(i).get("S_NAME"));
				chart.put("group", chartlist.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartlist.get(i).get("S_BAR_VALUE"));
				chart.put("type", "bar");
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_BOTTOM_CALC_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_BOTTOM_CALC_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析社会政策性兜底统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getSocialPolicyTableData(String id,String level,String scope,String poor_attribute,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("poor_attribute", Constants.poorAttributeMap.get(poor_attribute));
			params.put("month", year+month+"01");
			Map<String,Object> totalMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.querySocialPolicyAmount(params),"SocialPolicyTable");
			List<Map<String,Object>> datalist = analyseDao.querySocialPolicyTable(params);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_poor_family = new JSONObject();
			amount_poor_family.put("source", "建档立卡");
			amount_poor_family.put("stat_method", "建档立卡系统是无劳动能力的一般贫困户/五保户/低保户的户数。");
			tips.put("amount_poor_family", amount_poor_family);
			JSONObject amount_poor_people = new JSONObject();
			amount_poor_people.put("source", "建档立卡");
			amount_poor_people.put("stat_method", "建档立卡系统是无劳动能力的一般贫困户/五保户/低保户的贫困人口总数。");
			tips.put("amount_poor_people", amount_poor_people);
			JSONObject amount_diff_family = new JSONObject();
			amount_diff_family.put("source", "建档立卡、省民政厅");
			amount_diff_family.put("stat_method", "建档立卡是无劳动能力的一般贫困户/五保户/低保户应该享受兜底政策贫困户，比对民政低保户和五保人口信息,两者户家庭人口不能完全匹配时的贫困户数。");
			tips.put("amount_diff_family", amount_diff_family);
			JSONObject amount_diff_people = new JSONObject();
			amount_diff_people.put("source", "建档立卡、省民政厅");
			amount_diff_people.put("stat_method", "建档立卡是无劳动能力的一般贫困户/五保户/低保户应该享受兜底政策贫困人口，比对民政低保户和五保户人口信息,两者人口信息不一致的家庭人口数。");
			tips.put("amount_diff_people", amount_diff_people);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<datalist.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", datalist.get(i).get("S_AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", datalist.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_poor_family", datalist.get(i).get("S_FAMILY_AMOUNT_TOTAL"));
				obj.put("amount_poor_people", datalist.get(i).get("S_PEOPLE_AMOUNT_TOTAL"));
				obj.put("amount_diff_family", datalist.get(i).get("S_FAMILY_AMOUNT_DIFF"));
				obj.put("amount_diff_people", datalist.get(i).get("S_PEOPLE_AMOUNT_DIFF"));
				obj.put("ratio_diff_people", datalist.get(i).get("S_PEOPLE_PERCENT")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_poor_family", totalMap.get("S_TOTAL_AMOUNT_POOR_FAMILY"));
			total.put("amount_poor_people", totalMap.get("S_TOTAL_AMOUNT_POOR_PEOPLE"));
			total.put("amount_diff_family", totalMap.get("S_TOTAL_AMOUNT_DIFF_FAMILY"));
			total.put("amount_diff_people", totalMap.get("S_TOTAL_AMOUNT_DIFF_PEOPLE"));
			total.put("ratio_diff_people", totalMap.get("S_TOTAL_RATIO_DIFF_PEOPLE")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析社会政策性兜底未落实政策详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，空为查全部
	 * @return
	 */
	public String getSocialPolicyDetailSearchData(String id,String level,String scope,String poor_attribute,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("poor_attribute", Constants.poorAttributeMap.get(poor_attribute));
			params.put("month", year+month+"01");
			params.put("keyword", keyword);
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> datalist = analyseDao.querySocialPolicyDetail(params);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			JSONObject poor_attributes = new JSONObject();
			poor_attributes.put("source", "建档立卡");
			poor_attributes.put("stat_method", "建档立卡系统填写是无劳动能力的一般贫困户、五保户、低保户");
			tips.put("poor_attributes", poor_attributes);
			JSONObject amount_family_members = new JSONObject();
			amount_family_members.put("source", "建档立卡");
			amount_family_members.put("stat_method", "贫困户家庭成员个数。");
			tips.put("amount_family_members", amount_family_members);
			JSONObject amount_diff_family_members = new JSONObject();
			amount_diff_family_members.put("source", "建档立卡");
			amount_diff_family_members.put("stat_method", "建档立卡是无劳动能力的一般贫困户/五保户/低保户应该享受兜底政策贫困人口、比对民政低保户和五保户的人口,两者人口信息不一致的家庭人口数。");
			tips.put("amount_diff_family_members", amount_diff_family_members);
			JSONObject list_diff_family_members = new JSONObject();
			list_diff_family_members.put("source", "建档立卡");
			list_diff_family_members.put("stat_method", "五保户可按人的身份证匹配，不一致展示家庭成员数。低保可能不可展示。");
			tips.put("list_diff_family_members", list_diff_family_members);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<datalist.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", datalist.get(i).get("S_CITY_NAME"));
				obj.put("county_name", datalist.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", datalist.get(i).get("S_TOWN_NAME"));
				params.put("PAC", datalist.get(i).get("S_COUNTRY_ID").toString());
				if("0".equals(datalist.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", datalist.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", datalist.get(i).get("S_COUNTRY_NAME"));
				obj.put("house_holder_name", datalist.get(i).get("S_HOUSE_HOLDER_NAME"));
				obj.put("poor_attributes", datalist.get(i).get("S_POOR_ATTRIBUTES"));
				obj.put("family_id", datalist.get(i).get("S_FAMILY_ID"));
				obj.put("amount_family_members", datalist.get(i).get("S_AMOUNT_FAMILY_MEMBERS"));
				obj.put("amount_diff_family_members", datalist.get(i).get("S_AMOUNT_DIFF_FAMILY_MEMBERS"));
				obj.put("list_diff_family_members", datalist.get(i).get("S_LIST_DIFF_FAMILY_MEMBERS"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析义务教育JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getGuaranteeEduData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryGuaranteeEdu(params),"GuaranteeEdu");
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_3ENSURE_EDUCATION_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryGuaranteeEducChart(params);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject edu = new JSONObject();
			edu.put("amount_school_age", getValue(dataMap,"S_AMOUNT_SCHOOL_AGE"));
			edu.put("amount_compulsory_edu", getValue(dataMap,"S_AMOUNT_COMPULSORY_EDU"));
			edu.put("amount_diff", getValue(dataMap,"S_AMOUNT_DIFF"));
			edu.put("percent", getValue(dataMap,"S_PERCENT"));
			data.put("edu", edu);
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_LINE_NAME"));
				chart.put("group", chartList.get(i).get("S_LINE_GROUP"));
				chart.put("value", chartList.get(i).get("S_LINE_VALUE"));
				chart.put("type", "line");
				chart_config.add(chart);
			}
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart.put("type", "bar");
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_3ENSURE_EDUCATION_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_3ENSURE_EDUCATION_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析义务教育统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getGuaranteeEduTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryGuaranteeEduList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryGuaranteeEduCount(params),"GuaranteeEduTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_at_age = new JSONObject();
			amount_at_age.put("source", "建档立卡");
			amount_at_age.put("stat_method", "截止每年8月31日时“年龄>6岁-15岁”的贫困人口数。");
			tips.put("amount_at_age", amount_at_age);
			JSONObject amount_diff = new JSONObject();
			amount_diff.put("source", "建档立卡、省教育厅");
			amount_diff.put("stat_method", "义务教育适龄儿童建档立卡是在校生、比对教育部也是在校生，两者人口信息不一致的贫困人口数。差异的情况包括：1、建档立卡是在校生、省教育厅不是；2、建档立卡不是在校生、省教育厅是；3、建档立卡不是在校生、省教育厅也不是");
			tips.put("amount_diff", amount_diff);
			JSONObject ratio_diff_people = new JSONObject();
			ratio_diff_people.put("source", "建档立卡、省教育厅");
			ratio_diff_people.put("stat_method", "分子：差异的人口数；分母：贫困人口义务教育适龄人数");
			tips.put("ratio_diff_people", ratio_diff_people);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_at_age", dataList.get(i).get("S_AMOUNT_AT_AGE"));
				obj.put("amount_diff", dataList.get(i).get("S_AMOUNT_DIFF"));
				obj.put("ratio_diff_people", dataList.get(i).get("S_RATIO_DIFF_PEOPLE")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_at_age", getValue(dataMap,"S_AMOUNT_AT_AGE"));
			total.put("amount_diff", getValue(dataMap,"S_AMOUNT_DIFF"));
			total.put("ratio_diff_people", getValue(dataMap,"S_RATIO_DIFF_PEOPLE")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析义务教育详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getGuaranteeEduDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryGuaranteeEduSearch(params);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				obj.put("house_holder_name", dataList.get(i).get("S_HOUSE_HOLDER_NAME"));
				obj.put("family_id", dataList.get(i).get("S_FAMILY_ID"));
				obj.put("student_name", dataList.get(i).get("S_STUDENT_NAME"));
				obj.put("doc_info", dataList.get(i).get("S_DOC_INFO"));
				obj.put("edu_department_info", dataList.get(i).get("S_EDU_DEPARTMENT_INFO"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析医疗保障JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getGuaranteeMedicalData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryGuaranteeMedical(params),"GuaranteeMedical");
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_3ENSURE_MEDICAL_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryGuaranteeMedicalChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject medical = new JSONObject();
			medical.put("amount_poor_people", getValue(dataMap,"S_AMOUNT_POOR_PEOPLE"));
			medical.put("amount_have_medical_insurance", getValue(dataMap,"S_AMOUNT_HAVE_MEDICAL_INSUR"));
			medical.put("amount_diff", getValue(dataMap,"S_AMOUNT_DIFF"));
			medical.put("percent", getValue(dataMap,"S_PERCENT"));
			data.put("medical", medical);
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_LINE_NAME"));
				chart.put("group", chartList.get(i).get("S_LINE_GROUP"));
				chart.put("value", chartList.get(i).get("S_LINE_VALUE"));
				chart.put("type", "line");
				chart_config.add(chart);
			}
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart.put("type", "bar");
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_3ENSURE_MEDICAL_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_3ENSURE_MEDICAL_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析医疗保障统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getGuaranteeMedicalTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryGuaranteeMedicalList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryGuaranteeMedicalCount(params),"GuaranteeMedicalTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_poor_people = new JSONObject();
			amount_poor_people.put("source", "建档立卡");
			amount_poor_people.put("stat_method", "建档立卡系统的全部贫困人口数。");
			tips.put("amount_poor_people", amount_poor_people);
			JSONObject amount_diff = new JSONObject();
			amount_diff.put("source", "建档立卡、省社会保险基金管理局");
			amount_diff.put("stat_method", "建档立卡系统参加了参加城乡居民基本医疗保险、比对社保局也参加了城镇职工/城乡居民医疗保险信息不一致的贫困人口数。差异的情况包括：1、建档立卡是参加医保、省社保没有信息；2、建档立卡是没有参加医保、省社保厅有参加；3、建档立卡没有参加、省社保厅也没有信息；");
			tips.put("amount_diff", amount_diff);
			JSONObject ratio_diff_people = new JSONObject();
			ratio_diff_people.put("source", "建档立卡、省社会保险基金管理局");
			ratio_diff_people.put("stat_method", "分子：差异人数；分母：贫困人口数");
			tips.put("ratio_diff_people", ratio_diff_people);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_poor_people", dataList.get(i).get("S_AMOUNT_POOR_PEOPLE"));
				obj.put("amount_diff", dataList.get(i).get("S_AMOUNT_DIFF"));
				obj.put("ratio_diff_people", dataList.get(i).get("S_RATIO_DIFF_PEOPLE")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_poor_people", getValue(dataMap,"S_AMOUNT_POOR_PEOPLE"));
			total.put("amount_diff", getValue(dataMap,"S_AMOUNT_DIFF"));
			total.put("ratio_diff_people", getValue(dataMap,"S_RATIO_DIFF_PEOPLE")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析医疗保障详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getGuaranteeMedicalDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryGuaranteeMedicalSearch(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				obj.put("house_holder_name", dataList.get(i).get("S_HOUSE_HOLDER_NAME"));
				obj.put("family_id", dataList.get(i).get("S_FAMILY_ID"));
				obj.put("member_name", dataList.get(i).get("S_MEMBER_NAME"));
				obj.put("doc_info", dataList.get(i).get("S_DOC_INFO"));
				obj.put("social_insurance_department_info", dataList.get(i).get("S_SOCIAL_INSURANCE_DEPAR_INFO"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析住房保障JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getGuaranteeHouseData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryGuaranteeHouse(params),"GuaranteeHouse");
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_3ENSURE_HOUSE_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryGuaranteeHouseChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			
			JSONObject house = new JSONObject();
			house.put("amount_danger_house", getValue(dataMap,"S_AMOUNT_DANGER_HOUSE"));
			house.put("amount_retrofit", getValue(dataMap,"S_AMOUNT_RETROFIT"));
			house.put("amount_diff", getValue(dataMap,"S_AMOUNT_DIFF"));
			house.put("percent", getValue(dataMap,"S_PERCENT"));
			data.put("house", house);
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_LINE_NAME"));
				chart.put("group", chartList.get(i).get("S_LINE_GROUP"));
				chart.put("value", chartList.get(i).get("S_LINE_VALUE"));
				chart.put("type", "line");
				chart_config.add(chart);
			}
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart.put("type", "bar");
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_3ENSURE_HOUSE_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_3ENSURE_HOUSE_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析住房保障统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getGuaranteeHouseTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryGuaranteeHouseList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryGuaranteeHouseCount(params),"GuaranteeHouseTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_danger_house = new JSONObject();
			amount_danger_house.put("source", "建档立卡");
			amount_danger_house.put("stat_method", "建档立卡系统填写了主要住房“是危房”的贫困户数。");
			tips.put("amount_danger_house", amount_danger_house);
			JSONObject amount_diff = new JSONObject();
			amount_diff.put("source", "建档立卡、省住建部");
			amount_diff.put("stat_method", "建档立卡系统<户帮扶计划表住房改造项目>是完成状态的贫困户、比对住建部危房改造也是完成状态的贫困户，户主(或比对上的贫困人口的户主)比对信息不一致时的贫困户数。差异的情况：1、建档立卡系统显示已完成改造、住建部找不到信息；2、建档立卡系统显示已完成改造、住建部显示没有完成改造；3、建档立卡系统显示为没有完成改造、住建部显示已完成；4、建档立卡系统显示为没有完成改造、住建部");
			tips.put("amount_diff", amount_diff);
			JSONObject ratio_diff_people = new JSONObject();
			ratio_diff_people.put("source", "建档立卡、省住建部");
			ratio_diff_people.put("stat_method", "分子：差异户数；分母：危房户数");
			tips.put("ratio_diff_people", ratio_diff_people);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_danger_house", dataList.get(i).get("S_AMOUNT_DANGER_HOUSE"));
				obj.put("amount_diff", dataList.get(i).get("S_AMOUNT_DIFF"));
				obj.put("ratio_diff_people", dataList.get(i).get("S_RATIO_DIFF_PEOPLE")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_danger_house", getValue(dataMap,"S_AMOUNT_DANGER_HOUSE"));
			total.put("amount_diff", getValue(dataMap,"S_AMOUNT_DIFF"));
			total.put("ratio_diff_people", getValue(dataMap,"S_RATIO_DIFF_PEOPLE")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户“三保障”分析住房保障详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getGuaranteeHouseDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryGuaranteeHouseSearch(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				obj.put("family_id", dataList.get(i).get("S_FAMILY_ID"));
				obj.put("house_holder_name", dataList.get(i).get("S_HOUSE_HOLDER_NAME"));
	//			obj.put("member_name", dataList.get(i).get("S_MEMBER_NAME"));
				obj.put("doc_info", dataList.get(i).get("S_DOC_INFO"));
				obj.put("house_department_info", dataList.get(i).get("S_HOUSE_DEPARTMENT_INFO"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析道路硬化JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getConditionRoadData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			Map<String,Object> areaMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAreaNameByPac(params),"ConditionRoad");
			params.put("SCOPE", scope);
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryConditionRoadChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			data.put("area_name", areaMap.get("S_AREA_NAME"));
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析道路硬化统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getConditionRoadTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryConditionRoadList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryConditionRoadCount(params),"ConditionRoadTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_country_have_road = new JSONObject();
			amount_country_have_road.put("source", "建档立卡");
			amount_country_have_road.put("stat_method", "行政村到乡镇未通沥青(水泥)路里程数为0的村数。");
			tips.put("amount_country_have_road", amount_country_have_road);
			JSONObject amount_country_no_road = new JSONObject();
			amount_country_no_road.put("source", "建档立卡");
			amount_country_no_road.put("stat_method", "行政村到乡镇未通沥青(水泥)路里程数>0的相对贫困村数。");
			tips.put("amount_country_no_road", amount_country_no_road);
			JSONObject rate_country_no_road = new JSONObject();
			rate_country_no_road.put("source", "建档立卡");
			rate_country_no_road.put("stat_method", "未完成道路硬底化的村数/村数。");
			tips.put("rate_country_no_road", rate_country_no_road);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_country", dataList.get(i).get("S_AMOUNT_COUNTRY"));
				obj.put("amount_country_have_road", dataList.get(i).get("S_AMOUNT_COUNTRY_HAVE_ROAD"));
				obj.put("amount_country_no_road", dataList.get(i).get("S_AMOUNT_COUNTRY_NO_ROAD"));
				obj.put("rate_country_no_road", dataList.get(i).get("S_RATE_COUNTRY_NO_ROAD")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_country", getValue(dataMap,"S_AMOUNT_COUNTRY"));
			total.put("amount_country_have_road", getValue(dataMap,"S_AMOUNT_COUNTRY_HAVE_ROAD"));
			total.put("amount_country_no_road", getValue(dataMap,"AMOUNT_COUNTRY_NO_ROAD"));
			total.put("rate_country_no_road", getValue(dataMap,"S_RATE_COUNTRY_NO_ROAD")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析道路硬化详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getConditionRoadDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryConditionRoadSearch(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			JSONObject distance = new JSONObject();
			distance.put("source", "建档立卡");
			distance.put("stat_method", "建档立卡系统行政村到乡镇未通沥青(水泥)路里程数累计。");
			tips.put("distance", distance);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				obj.put("distance", dataList.get(i).get("S_DISTANCE"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析安全饮水JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getConditionWaterData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			Map<String,Object> areaMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAreaNameByPac(params),"ConditionWater");
			params.put("SCOPE", scope);
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryConditionWaterChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			data.put("area_name", areaMap.get("S_AREA_NAME"));
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析安全饮水统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getConditionWaterTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryConditionWaterList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryConditionWaterCount(params),"ConditionWaterTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_country_have_water_security = new JSONObject();
			amount_country_have_water_security.put("source", "建档立卡");
			amount_country_have_water_security.put("stat_method", "行政村未实现饮水安全户数为0的村数。");
			tips.put("amount_country_have_water_security", amount_country_have_water_security);
			JSONObject amount_country_no_water_security = new JSONObject();
			amount_country_no_water_security.put("source", "建档立卡");
			amount_country_no_water_security.put("stat_method", "行政村未实现饮水安全户数>0的相对贫困村数。");
			tips.put("amount_country_no_water_security", amount_country_no_water_security);
			JSONObject rate_no_water_security = new JSONObject();
			rate_no_water_security.put("source", "建档立卡");
			rate_no_water_security.put("stat_method", "未全部实现安全饮水的村数/村数。");
			tips.put("rate_no_water_security", rate_no_water_security);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_family", dataList.get(i).get("S_AMOUNT_FAMILY"));
				obj.put("amount_country_have_water_security", dataList.get(i).get("S_AMOUNT_COUN_HAVE_WATER_SECUR"));
				obj.put("amount_country_no_water_security", dataList.get(i).get("S_AMOUNT_COUN_NO_WATER_SECUR"));
				obj.put("rate_no_water_security", dataList.get(i).get("S_RATE_NO_WATER_SECURITY")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_family", getValue(dataMap,"S_AMOUNT_FAMILY"));
			total.put("amount_country_have_water_security", getValue(dataMap,"S_AMOUNT_COUN_HAVE_WATER_SECUR"));
			total.put("amount_country_no_water_security", getValue(dataMap,"S_AMOUNT_COUN_NO_WATER_SECUR"));
			total.put("rate_no_water_security", getValue(dataMap,"S_RATE_NO_WATER_SECURITY")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析安全饮水详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getConditionWaterDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryConditionWaterSearch(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			JSONObject amount_no_water_security = new JSONObject();
			amount_no_water_security.put("source", "建档立卡");
			amount_no_water_security.put("stat_method", "政村未实现饮水安全户数之和。");
			tips.put("amount_no_water_security", amount_no_water_security);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				obj.put("amount_no_water_security", dataList.get(i).get("S_AMOUNT_NO_WATER_SECURITY"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析通电JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getConditionElectricityData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			Map<String,Object> areaMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAreaNameByPac(params),"ConditionElectricity");
			params.put("SCOPE", scope);
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryConditionElectricityChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			data.put("area_name", areaMap.get("S_AREA_NAME"));
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析通电统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getConditionElectricityTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryConditionElectricityList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryConditionElectricityCount(params),"ConditionElectricityTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_country_have_electricity = new JSONObject();
			amount_country_have_electricity.put("source", "建档立卡");
			amount_country_have_electricity.put("stat_method", "行政村未通生活用电的自然村数为0且未通生活用电的户数为0的村数。");
			tips.put("amount_country_have_electricity", amount_country_have_electricity);
			JSONObject amount_country_no_electricity = new JSONObject();
			amount_country_no_electricity.put("source", "建档立卡");
			amount_country_no_electricity.put("stat_method", "行政村未通生活用电的自然村数>0或未通生活用电的户数>0的相对贫困村数。");
			tips.put("amount_country_no_electricity", amount_country_no_electricity);
			JSONObject rate_no_electricity = new JSONObject();
			rate_no_electricity.put("source", "建档立卡");
			rate_no_electricity.put("stat_method", "未全通点的村数/村数。");
			tips.put("rate_no_electricity", rate_no_electricity);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_country", dataList.get(i).get("S_AMOUNT_COUNTRY"));
				obj.put("amount_country_have_electricity", dataList.get(i).get("S_AMOUNT_COUN_HAVE_ELECTRICITY"));
				obj.put("amount_country_no_electricity", dataList.get(i).get("S_AMOUNT_COUN_NO_ELECTRICITY"));
				obj.put("rate_no_electricity", dataList.get(i).get("S_RATE_NO_ELECTRICITY")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_country", getValue(dataMap,"S_AMOUNT_COUNTRY"));
			total.put("amount_country_have_electricity", getValue(dataMap,"S_AMOUNT_COUN_HAVE_ELECTRICITY"));
			total.put("amount_country_no_electricity", getValue(dataMap,"S_AMOUNT_COUN_NO_ELECTRICITY"));
			total.put("rate_no_electricity", getValue(dataMap,"S_RATE_NO_ELECTRICITY")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析通电详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getConditionElectricityDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryConditionElectricitySearch(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			JSONObject amount_no_electricity = new JSONObject();
			amount_no_electricity.put("source", "建档立卡");
			amount_no_electricity.put("stat_method", "行政村未通生活用电的户数之和。");
			tips.put("amount_no_electricity", amount_no_electricity);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				obj.put("amount_no_electricity", dataList.get(i).get("S_AMOUNT_NO_ELECTRICITY"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析卫生站JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getConditionHealthData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			Map<String,Object> areaMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAreaNameByPac(params),"ConditionHealth");
			params.put("SCOPE", scope);
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryConditionHealthChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			data.put("area_name", areaMap.get("S_AREA_NAME"));
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析卫生站统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getConditionHealthTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryConditionHealthList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryConditionHealthCount(params),"ConditionHealthTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_country_have_health_station = new JSONObject();
			amount_country_have_health_station.put("source", "建档立卡");
			amount_country_have_health_station.put("stat_method", "行政村卫生室个数>0且1个以上执业（助理）医师的村数。");
			tips.put("amount_country_have_health_station", amount_country_have_health_station);
			JSONObject amount_country_no_doctor = new JSONObject();
			amount_country_no_doctor.put("source", "建档立卡");
			amount_country_no_doctor.put("stat_method", "行政村卫生室个数等于0或执业（助理）医师等于0的相对贫困村数。");
			tips.put("amount_country_no_doctor", amount_country_no_doctor);
			JSONObject rate_no_health_care = new JSONObject();
			rate_no_health_care.put("source", "建档立卡");
			rate_no_health_care.put("stat_method", "无卫生室或执业(助理))医师的村数/村数。");
			tips.put("rate_no_health_care", rate_no_health_care);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_country", dataList.get(i).get("S_AMOUNT_COUNTRY"));
				obj.put("amount_country_have_health_station", dataList.get(i).get("S_AMOUNT_COUN_HAVE_HEA_STATION"));
				obj.put("amount_country_no_doctor", dataList.get(i).get("S_AMOUNT_COUNTRY_NO_DOCTOR"));
				obj.put("rate_no_health_care", dataList.get(i).get("S_RATE_NO_HEALTH_CARE")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_country", getValue(dataMap,"S_AMOUNT_COUNTRY"));
			total.put("amount_country_have_health_station", getValue(dataMap,"S_AMOUNT_COUN_HAVE_HEA_STATION"));
			total.put("amount_country_no_doctor", getValue(dataMap,"S_AMOUNT_COUNTRY_NO_DOCTOR"));
			total.put("rate_no_health_care", getValue(dataMap,"S_RATE_NO_HEALTH_CARE")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析卫生站详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getConditionHealthDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryConditionHealthSearch(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析通广播电视JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getConditionBroadcastData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			Map<String,Object> areaMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAreaNameByPac(params),"ConditionBroadcast");
			params.put("SCOPE", scope);
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryConditionBroadcastChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			data.put("area_name", areaMap.get("S_AREA_NAME"));
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析通广播电视统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getConditionBroadcastTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryConditionBroadcastList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryConditionBroadcastCount(params),"ConditionBroadcastTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_country_have_broadcast = new JSONObject();
			amount_country_have_broadcast.put("source", "建档立卡");
			amount_country_have_broadcast.put("stat_method", "行政村通广播电视户数等于全村总户数的村数。");
			tips.put("amount_country_have_broadcast", amount_country_have_broadcast);
			JSONObject amount_country_no_broadcast = new JSONObject();
			amount_country_no_broadcast.put("source", "建档立卡");
			amount_country_no_broadcast.put("stat_method", "行政村通广播电视户数小于全村总户数的相对贫困村数。");
			tips.put("amount_country_no_broadcast", amount_country_no_broadcast);
			JSONObject rate_country_no_broadcast = new JSONObject();
			rate_country_no_broadcast.put("source", "建档立卡");
			rate_country_no_broadcast.put("stat_method", "未全部通广播电视的村数/村数。");
			tips.put("rate_country_no_broadcast", rate_country_no_broadcast);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_family", dataList.get(i).get("S_AMOUNT_FAMILY"));
				obj.put("amount_country_have_broadcast", dataList.get(i).get("S_AMOUNT_COUN_HAVE_BROADCAST"));
				obj.put("amount_country_no_broadcast", dataList.get(i).get("S_AMOUNT_COUN_NO_BROADCAST"));
				obj.put("rate_country_no_broadcast", dataList.get(i).get("S_RATE_COUNTRY_NO_BROADCAST")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_family", getValue(dataMap,"S_AMOUNT_FAMILY"));
			total.put("amount_country_have_broadcast", getValue(dataMap,"S_AMOUNT_COUN_HAVE_BROADCAST"));
			total.put("amount_country_no_broadcast", getValue(dataMap,"S_AMOUNT_COUN_NO_BROADCAST"));
			total.put("rate_country_no_broadcast", getValue(dataMap,"S_RATE_COUNTRY_NO_BROADCAST")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析通广播电视详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getConditionBroadcastDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryConditionBroadcastSearch(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				obj.put("amount_no_broadcast", dataList.get(i).get("S_AMOUNT_NO_BROADCAST"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析宽带网络JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getConditionNetData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			Map<String,Object> areaMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryAreaNameByPac(params),"ConditionNet");
			params.put("SCOPE", scope);
//			params.put("YEAR_TAG", Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString());
			List<Map<String,Object>> chartList = analyseDao.queryConditionNetChart(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			data.put("area_name", areaMap.get("S_AREA_NAME"));
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartList.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartList.get(i).get("S_BAR_NAME"));
				chart.put("group", chartList.get(i).get("S_BAR_GOURP"));
				chart.put("value", chartList.get(i).get("S_BAR_VALUE"));
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_1MATCH_DIFF_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析宽带网络统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getConditionNetTableData(String id,String level,String scope,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("level", level);
			params.put("MONTH_TAG", year+month+"01");
			List<Map<String,Object>> dataList = analyseDao.queryConditionNetList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryConditionNetCount(params),"ConditionNetTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_country_have_net = new JSONObject();
			amount_country_have_net.put("source", "建档立卡");
			amount_country_have_net.put("stat_method", "行政村能用手机上网的户数等于全村总户数的村数。");
			tips.put("amount_country_have_net", amount_country_have_net);
			JSONObject amount_country_no_net = new JSONObject();
			amount_country_no_net.put("source", "建档立卡");
			amount_country_no_net.put("stat_method", "行政村能用手机上网的户数小于全村总户数的相对贫困村数。");
			tips.put("amount_country_no_net", amount_country_no_net);
			JSONObject rate_country_no_net = new JSONObject();
			rate_country_no_net.put("source", "建档立卡");
			rate_country_no_net.put("stat_method", "宽带网络未全覆盖的村数/村数。");
			tips.put("rate_country_no_net", rate_country_no_net);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_country", dataList.get(i).get("S_AMOUNT_COUNTRY"));
				obj.put("amount_country_have_net", dataList.get(i).get("S_AMOUNT_COUNTRY_HAVE_NET"));
				obj.put("amount_country_no_net", dataList.get(i).get("S_AMOUNT_COUNTRY_NO_NET"));
				obj.put("rate_country_no_net", dataList.get(i).get("S_RATE_COUNTRY_NO_NET")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_country", getValue(dataMap,"S_AMOUNT_COUNTRY"));
			total.put("amount_country_have_net", getValue(dataMap,"S_AMOUNT_COUNTRY_HAVE_NET"));
			total.put("amount_country_no_net", getValue(dataMap,"S_AMOUNT_COUNTRY_NO_NET"));
			total.put("rate_country_no_net", getValue(dataMap,"S_RATE_COUNTRY_NO_NET")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困村“一相当”分析宽带网络详表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getConditionNetDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("PAC", id);
			params.put("level", level);
			params.put("SCOPE", scope);
			params.put("KEYWORD", keyword);
			params.put("MONTH_TAG", year+month+"01");
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> dataList = analyseDao.queryConditionNetSearch(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", dataList.get(i).get("S_CITY_NAME"));
				obj.put("county_name", dataList.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", dataList.get(i).get("S_TOWN_NAME"));
				if("0".equals(dataList.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", dataList.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", dataList.get(i).get("S_COUNTRY_NAME"));
				obj.put("amount_no_net", dataList.get(i).get("S_AMOUNT_NO_NET"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析务工状况分析JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getPoorLaborData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			List<Map<String, Object>> chartlist = analyseDao.queryPoorLabor(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryPoorLaborCount(params),"PoorLabor");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject total = new JSONObject();
			total.put("amount", getValue(dataMap,"S_TOTAL_AMOUNT"));
			data.put("total", total);
			JSONObject farm_at_home = new JSONObject();
			farm_at_home.put("amount", getValue(dataMap,"S_WORK_AT_HOME_AMOUNT"));
			farm_at_home.put("percent", getValue(dataMap,"S_WORK_AT_HOME_PERCENT"));
			data.put("farm_at_home", farm_at_home);
			List<JSONObject> chart_config_labor_status = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject s1 = new JSONObject();
				s1.put("name", chartlist.get(i).get("S_S_NAME"));
				s1.put("value", chartlist.get(i).get("S_S_VALUE"));
				chart_config_labor_status.add(s1);
			}
			data.put("chart_config_labor_status", chart_config_labor_status);
			
			Map<String, Object> queryPoorLaborZJ = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryPoorLaborZJ(params),"PoorLaborZJ");
			List<JSONObject> chart_config_farm_distribution = new ArrayList<JSONObject>();
				JSONObject d1 = new JSONObject();
				d1.put("name", getValue(queryPoorLaborZJ,"S_W_NAME"));
				d1.put("value", getValue(queryPoorLaborZJ,"S_WB_VALUE"));
				chart_config_farm_distribution.add(d1);
				JSONObject d2 = new JSONObject();
				d2.put("name", getValue(queryPoorLaborZJ,"S_U_NAME"));
				d2.put("value", getValue(queryPoorLaborZJ,"S_UW_VALUE"));
				chart_config_farm_distribution.add(d2);
				JSONObject d3 = new JSONObject();
				d3.put("name", getValue(queryPoorLaborZJ,"S_N_NAME"));
				d3.put("value", getValue(queryPoorLaborZJ,"S_NW_VALUE"));
				chart_config_farm_distribution.add(d3);
			data.put("chart_config_farm_distribution", chart_config_farm_distribution);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析教育分析JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getPoorEduData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			List<Map<String,Object>> chartlist = analyseDao.queryPoorEdu(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryPoorEduCount(params),"PoorEdu");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject total = new JSONObject();
			total.put("amount", getValue(dataMap,"S_TOTAL_AMOUNT"));
			data.put("total", total);
			JSONObject at_school = new JSONObject();
			at_school.put("amount", getValue(dataMap,"S_AT_SCHOOL_AMOUNT"));
			at_school.put("percent", getValue(dataMap,"S_AT_SCHOOL_PERCENT"));
			data.put("at_school", at_school);
			List<JSONObject> chart_config_edu_qualification = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject q1 = new JSONObject();
				q1.put("name", chartlist.get(i).get("S_NAME"));
				q1.put("value", chartlist.get(i).get("S_Q_VALUE"));
				chart_config_edu_qualification.add(q1);
			}
			data.put("chart_config_edu_qualification", chart_config_edu_qualification);
			
			List<JSONObject> chart_config_school_age = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
			JSONObject a1 = new JSONObject();
			a1.put("name", chartlist.get(i).get("S_NAME"));
			a1.put("value", chartlist.get(i).get("S_A_VALUE"));
				chart_config_school_age.add(a1);
			}
			data.put("chart_config_school_age", chart_config_school_age);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析致贫原因分析JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getPoorReasonData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			List<Map<String, Object>> chartlist = analyseDao.queryPoorReason(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryPoorReasonCount(params),"PoorReason");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject family = new JSONObject();
			family.put("amount", getValue(dataMap,"S_FAMILY_AMOUNT"));
			data.put("family", family);
			JSONObject people = new JSONObject();
			people.put("amount", getValue(dataMap,"S_PEOPLE_AMOUNT"));
			data.put("people", people);
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject c1 = new JSONObject();
				c1.put("name", chartlist.get(i).get("S_NAME"));
				c1.put("value", chartlist.get(i).get("S_VALUE"));
				chart_config.add(c1);
			}
			data.put("chart_config", chart_config);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户危房分析JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getPoorDangerHouseData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			//级别
			List<Map<String, Object>> chartlevellist = analyseDao.queryDangerHouseLevel(params);
			//结构
			List<Map<String, Object>> chartstructurelist = analyseDao.queryDangerHouseStructure(params);
			//总数
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryDangerHouseLevelCount(params),"PoorDangerHouse");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject danger_house = new JSONObject();
			danger_house.put("amount", getValue(dataMap,"S_DANGER_HOUSE_AMOUNT"));
			danger_house.put("percent", getValue(dataMap,"S_DANGER_HOUSE_PERCENT"));
			data.put("danger_house", danger_house);
			List<JSONObject> chart_config_level = new ArrayList<JSONObject>();
			for(int i=0;i<chartlevellist.size();i++){
				JSONObject l1 = new JSONObject();
				l1.put("name", chartlevellist.get(i).get("S_L_NAME"));
				l1.put("value", chartlevellist.get(i).get("S_L_VALUE"));
				chart_config_level.add(l1);
			}
			data.put("chart_config_level", chart_config_level);
			
			List<JSONObject> chart_config_structure = new ArrayList<JSONObject>();
			for(int i=0;i<chartstructurelist.size();i++){
				JSONObject s1 = new JSONObject();
				s1.put("name", chartstructurelist.get(i).get("S_S_NAME"));
				s1.put("value", chartstructurelist.get(i).get("S_S_VALUE"));
				chart_config_structure.add(s1);
			}
			data.put("chart_config_structure", chart_config_structure);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析贫困户属性分析JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getPoorAttributeData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			List<Map<String, Object>> chartlist = analyseDao.queryPoorAttribute(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryPoorAttributeCount(params),"PoorAttribute");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject total = new JSONObject();
			total.put("amount", getValue(dataMap,"S_FAM"));
			data.put("total", total);
			JSONObject disabled = new JSONObject();
			disabled.put("amount", getValue(dataMap,"S_DISABLED_AMOUNT"));
			disabled.put("percent", getValue(dataMap,"S_DISABLED_PERCENT"));
			data.put("disabled", disabled);
			JSONObject migrator = new JSONObject();
			migrator.put("amount", getValue(dataMap,"S_MIGRATOR_AMOUNT"));
			migrator.put("percent", getValue(dataMap,"S_MIGRATOR_PERCENT"));
			data.put("migrator", migrator);
			List<JSONObject> chart_config_poor_attributes = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject a11 = new JSONObject();//一般贫困户户数
				a11.put("name", chartlist.get(i).get("S_N_NAME"));
				a11.put("group", chartlist.get(i).get("S_F_GROUP"));
				a11.put("value", chartlist.get(i).get("S_N_F_VALUE"));
				chart_config_poor_attributes.add(a11);
				JSONObject a111 = new JSONObject();//一般贫困户人数
				a111.put("name", chartlist.get(i).get("S_N_NAME"));
				a111.put("group", chartlist.get(i).get("S_P_GROUP"));
				a111.put("value", chartlist.get(i).get("S_N_P_VALUE"));
				chart_config_poor_attributes.add(a111);
				
				JSONObject a31 = new JSONObject();//低保户户数
				a31.put("name", chartlist.get(i).get("S_W_NAME"));
				a31.put("group", chartlist.get(i).get("S_F_GROUP"));
				a31.put("value", chartlist.get(i).get("S_L_F_VALUE"));
				chart_config_poor_attributes.add(a31);
				JSONObject a311 = new JSONObject();//低保户人数
				a311.put("name", chartlist.get(i).get("S_W_NAME"));
				a311.put("group", chartlist.get(i).get("S_P_GROUP"));
				a311.put("value", chartlist.get(i).get("S_L_P_VALUE"));
				chart_config_poor_attributes.add(a311);
				
				JSONObject a21 = new JSONObject();//五保户
				a21.put("name", chartlist.get(i).get("S_F_NAME"));
				a21.put("group", chartlist.get(i).get("S_F_GROUP"));
				a21.put("value", chartlist.get(i).get("S_F_F_VALUE"));
				chart_config_poor_attributes.add(a21);
				JSONObject a211 = new JSONObject();//五保户
				a211.put("name", chartlist.get(i).get("S_F_NAME"));
				a211.put("group", chartlist.get(i).get("S_P_GROUP"));
				a211.put("value", chartlist.get(i).get("S_F_P_VALUE"));
				chart_config_poor_attributes.add(a211);
			}
			data.put("chart_config_poor_attributes", chart_config_poor_attributes);
			
			List<JSONObject> chart_config_labor = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject l11 = new JSONObject();//有劳户
				l11.put("name", chartlist.get(i).get("S_YOU_NAME"));
				l11.put("group", chartlist.get(i).get("S_F_GROUP"));
				l11.put("value", chartlist.get(i).get("S_W_F_VALUE"));
				chart_config_labor.add(l11);
				JSONObject l111 = new JSONObject();//有劳人
				l111.put("name", chartlist.get(i).get("S_YOU_NAME"));
				l111.put("group", chartlist.get(i).get("S_P_GROUP"));
				l111.put("value", chartlist.get(i).get("S_W_P_VALUE"));
				chart_config_labor.add(l111);
				
				JSONObject l21 = new JSONObject();
				l21.put("name", chartlist.get(i).get("S_WU_NAME"));
				l21.put("group", chartlist.get(i).get("S_F_GROUP"));
				l21.put("value", chartlist.get(i).get("S_U_F_VALUE"));
				chart_config_labor.add(l21);
				JSONObject l211 = new JSONObject();
				l211.put("name", chartlist.get(i).get("S_WU_NAME"));
				l211.put("group", chartlist.get(i).get("S_P_GROUP"));
				l211.put("value", chartlist.get(i).get("S_U_P_VALUE"));
				chart_config_labor.add(l211);
			}
			data.put("chart_config_labor", chart_config_labor);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析健康状况分析JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getPoorHealthData(String id,String level,String scope) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			List<Map<String,Object>> chartlist = analyseDao.queryPoorHealth(params);
			List<Map<String, Object>> chartlistTop = analyseDao.queryPoorHealthTOP(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryPoorHealthCount(params),"PoorHealth");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject total = new JSONObject();
			total.put("amount", getValue(dataMap,"S_POP"));
			data.put("total", total);
			JSONObject ill_health = new JSONObject();
			ill_health.put("amount", getValue(dataMap,"S_TOTAL_AMOUNT"));
			ill_health.put("percent", getValue(dataMap,"S_TOTAL_PERCENT"));
			data.put("ill_health", ill_health);
			List<JSONObject> chart_config_pie = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject p1 = new JSONObject();
				p1.put("name", chartlist.get(i).get("S_P_NAME"));
				p1.put("value", chartlist.get(i).get("S_P_VALUE"));
				chart_config_pie.add(p1);
			}
			data.put("chart_config_pie", chart_config_pie);
			
			List<JSONObject> chart_config_top = new ArrayList<JSONObject>();
			for(int i=0;i<chartlistTop.size();i++){
			JSONObject t1 = new JSONObject();
				t1.put("name", chartlist.get(i).get("S_T_NAME"));
				t1.put("group", chartlist.get(i).get("S_T_GROUP"));
				t1.put("value", chartlist.get(i).get("S_T_VALUE"));
				chart_config_top.add(t1);
			}
			data.put("chart_config_top", chart_config_top);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析预警监控存在异常的贫困户JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @return
	 */
	public String getAlarmedFamilyData(String id,String level,String scope,String dept) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("DEPT", dept);
			params.put("month_cur",Constants.dateContants.get("R_ANALY_ALARM_CALC_DM"));
			        
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryFamilyChange(params),"AlarmedFamily");
			List<Map<String,Object>> chartlist = analyseDao.queryFamilyCount(params);
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject family = new JSONObject();
			family.put("amount_total", getValue(dataMap,"S_AMOUNT_TOTAL"));
			family.put("amount_diff", getValue(dataMap,"S_AMOUNT_DIFF"));
			family.put("percent", getValue(dataMap,"S_PERCENT"));
			data.put("family", family);
			List<JSONObject> chart_config = new ArrayList<JSONObject>();
			for(int i=0;i<chartlist.size();i++){
				JSONObject chart = new JSONObject();
				chart.put("name", chartlist.get(i).get("S_NAME"));
				chart.put("group", chartlist.get(i).get("S_GROUP"));
				chart.put("value", chartlist.get(i).get("S_VALUE"));
				chart_config.add(chart);
			}
			data.put("chart_config", chart_config);
			JSONObject default_time = new JSONObject();
			String year =Constants.dateContants.get("R_ANALY_ALARM_CALC_DY").toString();
			SimpleDateFormat df= new SimpleDateFormat("MM");
			SimpleDateFormat df2= new SimpleDateFormat("yyyyMMdd");
			String month = df.format(df2.parse(Constants.dateContants.get("R_ANALY_ALARM_CALC_DM").toString()));
			default_time.put("year", year);
			default_time.put("month", month);
			data.put("default_time", default_time);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析预警监控存在异常的贫困户统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getAlarmedFamilyTableData(String id,String level,String scope,String year,String month,String dept) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String,Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("DEPT", dept);
			List<Map<String,Object>> dataList = analyseDao.queryFamilyDetailList(params);
			Map<String,Object> dataMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.queryFamilyChange(params),"AlarmedFamilyTable");
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject rate = new JSONObject();
			rate.put("source", "建档立卡、省民政厅");
			rate.put("stat_method", "建档立卡是一般贫困的无劳动能力户/五保户/低保户应该享受兜底政策贫困人口、民政是低保户和五保户的人口,两者人口信息不能完全匹配时的贫困户数。");
			tips.put("rate", rate);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<dataList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", dataList.get(i).get("S_AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", dataList.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_total", dataList.get(i).get("S_AMOUNT_TOTAL"));
				obj.put("amount_industry_dept_records", dataList.get(i).get("S_AMOUNT_INDUSTRY_DEPT_RECORDS"));
				obj.put("amount_diff", dataList.get(i).get("S_AMOUNT_DIFF"));
				obj.put("rate", dataList.get(i).get("S_RATE")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_total", getValue(dataMap,"S_AMOUNT_TOTAL"));
			total.put("amount_industry_dept_records", getValue(dataMap,"S_AMOUNT_INDUSTRY_DEPT_RECORDS"));
			total.put("amount_diff", getValue(dataMap,"S_AMOUNT_DIFF"));
			total.put("rate", getValue(dataMap,"S_RATE")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据分析预警监控存在异常的贫困户统计表详细JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param year 年
	 * @param month 月
	 * @param keyword 关键字，为空则查全部
	 * @return
	 */
	public String getAlarmedFamilyDetailSearchData(String id,String level,String scope,String year,String month,String dept,String keyword,Integer next_id) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("DEPT", dept);
			params.put("MONTH_TAG", year+month+"01");
			params.put("keyword", keyword);
			if(next_id==null) next_id = 0;
			params.put("START", next_id*20+1);
			params.put("END", (next_id+1)*20);
			List<Map<String,Object>> datalist = analyseDao.queryFamilyAlarmed(params);
			
			
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//滚动分页
			data.put("next_id", next_id+1);//下一页码
			data.put("count_total", 20);//页数20页
			
			JSONObject tips = new JSONObject();
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<datalist.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("order", i+1);
				obj.put("city_name", datalist.get(i).get("S_CITY_NAME"));
				obj.put("county_name", datalist.get(i).get("S_COUNTY_NAME"));
				obj.put("town_name", datalist.get(i).get("S_TOWN_NAME"));
				if("0".equals(getValue(datalist.get(i),"IF_FURTHER_POOR"))){//datalist.get(i).get("IF_FURTHER_POOR").toString())){
					obj.put("country_id", JSONNull.getInstance());
				}else{
					obj.put("country_id", datalist.get(i).get("S_COUNTRY_ID"));
				}
				obj.put("country_name", datalist.get(i).get("S_COUNTRY_NAME"));
				obj.put("house_holder_name", datalist.get(i).get("S_HOUSE_HOLDER_NAME"));
				obj.put("family_id", datalist.get(i).get("S_FAMILY_ID"));
				obj.put("member_name", datalist.get(i).get("S_MEMBER_NAME"));
				obj.put("alarmed_desc", datalist.get(i).get("S_ALARMED_DESC"));
				obj.put("department_name", datalist.get(i).get("S_DEPARTMENT_NAME"));
				list.add(obj);
			}
			table.put("list", list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	
	/*******表导出***********************************************************************************/
	/**
	 * 获取数据分析社会政策性兜底统计表JSON数据
	 * @param id 区域PAC
	 * @param level 层级（province:省；city:市；county:县；town:镇；country:村）
	 * @param scope 范围（1:全部；2:相对贫困村；3:分散村；4:革命老区；5:中央苏区）
	 * @param poor_attribute（ all:全部；low:低保户；five:五保户；no_labor:一般贫困的无劳动能力户）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
	public String getSocialPolicyTableExport(String id,String level,String scope,String poor_attribute,String year,String month) {
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params = Constants.dateContants;
			params.put("level", level);
			params.put("PAC", id);
			params.put("SCOPE", scope);
			params.put("poor_attribute", Constants.poorAttributeMap.get(poor_attribute));
			params.put("month", year+month+"01");
			Map<String,Object> totalMap = DataAnalyseMapTemplate.dataAnalyseMapTpl(analyseDao.querySocialPolicyAmount(params),"SocialPolicyTable");
			List<Map<String,Object>> datalist = analyseDao.querySocialPolicyTable(params);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject tips = new JSONObject();
			JSONObject amount_poor_family = new JSONObject();
			amount_poor_family.put("source", "建档立卡");
			amount_poor_family.put("stat_method", "建档立卡系统是无劳动能力的一般贫困户/五保户/低保户的户数。");
			tips.put("amount_poor_family", amount_poor_family);
			JSONObject amount_poor_people = new JSONObject();
			amount_poor_people.put("source", "建档立卡");
			amount_poor_people.put("stat_method", "建档立卡系统是无劳动能力的一般贫困户/五保户/低保户的贫困人口总数。");
			tips.put("amount_poor_people", amount_poor_people);
			JSONObject amount_diff_family = new JSONObject();
			amount_diff_family.put("source", "建档立卡、省民政厅");
			amount_diff_family.put("stat_method", "建档立卡是无劳动能力的一般贫困户/五保户/低保户应该享受兜底政策贫困户，比对民政低保户和五保人口信息,两者户家庭人口不能完全匹配时的贫困户数。");
			tips.put("amount_diff_family", amount_diff_family);
			JSONObject amount_diff_people = new JSONObject();
			amount_diff_people.put("source", "建档立卡、省民政厅");
			amount_diff_people.put("stat_method", "建档立卡是无劳动能力的一般贫困户/五保户/低保户应该享受兜底政策贫困人口，比对民政低保户和五保户人口信息,两者人口信息不一致的家庭人口数。");
			tips.put("amount_diff_people", amount_diff_people);
			data.put("tips", tips);
			
			JSONObject table = new JSONObject();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i=0;i<datalist.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("area_id", datalist.get(i).get("S_AREA_ID"));
				obj.put("area_level", Constants.nextLevelMap.get(level));
				obj.put("area_name", datalist.get(i).get("S_AREA_NAME"));
				obj.put("order", i+1);
				obj.put("amount_poor_family", datalist.get(i).get("S_FAMILY_AMOUNT_TOTAL"));
				obj.put("amount_poor_people", datalist.get(i).get("S_PEOPLE_AMOUNT_TOTAL"));
				obj.put("amount_diff_family", datalist.get(i).get("S_FAMILY_AMOUNT_DIFF"));
				obj.put("amount_diff_people", datalist.get(i).get("S_PEOPLE_AMOUNT_DIFF"));
				obj.put("ratio_diff_people", datalist.get(i).get("S_PEOPLE_PERCENT")+"%");
				list.add(obj);
			}
			table.put("list", list);
			JSONObject total = new JSONObject();
			total.put("amount_poor_family", totalMap.get("S_TOTAL_AMOUNT_POOR_FAMILY"));
			total.put("amount_poor_people", totalMap.get("S_TOTAL_AMOUNT_POOR_PEOPLE"));
			total.put("amount_diff_family", totalMap.get("S_TOTAL_AMOUNT_DIFF_FAMILY"));
			total.put("amount_diff_people", totalMap.get("S_TOTAL_AMOUNT_DIFF_PEOPLE"));
			total.put("ratio_diff_people", totalMap.get("S_TOTAL_RATIO_DIFF_PEOPLE")+"%");
			table.put("total", total);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	 private Object getValue(Map map,String key){
	    	return CommonUtil.getMapValue(map,key);
		}
}
