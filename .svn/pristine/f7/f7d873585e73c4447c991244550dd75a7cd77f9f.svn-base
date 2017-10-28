package com.aspire.birp.modules.antiPoverty.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.DataAnalyseService;
import com.aspire.birp.modules.common.service.PovertyGeometryService;

/** 
 * 数据分析controller类 
 * 
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月8日 下午5:15:25
 */
@Controller
@RequestMapping("/antiPoverty/analyse")
public class DataAnalyseController {
	
	@Autowired
    private DataAnalyseService analyseService;
	
	@Autowired
    private PovertyGeometryService PGService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getAreaPathData")
	@ResponseBody
	public String getAreaPathData(String level,String id,HttpSession session){
		Map<String,Object> Map = new HashMap<String,Object>();
		
		Map<String,Object> scopeMap = null;
		
		Map<String,Object> provinceMap = null;
		Map<String,Object> cityMap = null;
		List<Map<String,Object>> cityList = null;
		Map<String,Object> countyMap = null;
		List<Map<String,Object>> countyList = null;
		Map<String,Object> townMap = null;
		List<Map<String,Object>> townList = null;
		Map<String,Object> countryMap = null;
		List<Map<String,Object>> countryList = null;
		if("province".equals(level)){
			Map<String,Object> data = analyseService.getAreaPathMap(id,level);
			provinceMap = (Map<String,Object>)data.get("area");
			scopeMap =  (Map<String,Object>)data.get("scope");
			cityList = (List<Map<String,Object>>)data.get("areaList");
		}else if("city".equals(level)){
			if(provinceMap == null){
				String prov_id = analyseService.queryParentId(id);
				Map<String,Object> data = analyseService.getAreaPathMap(prov_id,"province");
				provinceMap = (Map<String,Object>)data.get("area");
				cityList = (List<Map<String,Object>>)data.get("areaList");
			}
			
			Map<String,Object> data = analyseService.getAreaPathMap(id,level);
			cityMap = (Map<String,Object>)data.get("area");
			scopeMap =  (Map<String,Object>)data.get("scope");
			countyList = (List<Map<String,Object>>)data.get("areaList");
		}else if("county".equals(level)){
			if(provinceMap == null || cityMap == null){
				String city_id = analyseService.queryParentId(id);
				Map<String,Object> data1 = analyseService.getAreaPathMap(city_id,"city");
				cityMap = (Map<String,Object>)data1.get("area");
				countyList = (List<Map<String,Object>>)data1.get("areaList");
				String prov_id = analyseService.queryParentId(city_id);
				Map<String,Object> data2 = analyseService.getAreaPathMap(prov_id,"province");
				provinceMap = (Map<String,Object>)data2.get("area");
				cityList = (List<Map<String,Object>>)data2.get("areaList");
				
				session.setAttribute("provinceMap", provinceMap);
				session.setAttribute("cityList", cityList);
				session.setAttribute("cityMap", cityMap);
				session.setAttribute("countyList", countyList);
			}
			
			Map<String,Object> data = analyseService.getAreaPathMap(id,level);
			countyMap = (Map<String,Object>)data.get("area");
			scopeMap =  (Map<String,Object>)data.get("scope");
			townList = (List<Map<String,Object>>)data.get("areaList");
		}else if("town".equals(level)){
			if(provinceMap == null || cityMap == null || countyMap == null){
				String county_id = analyseService.queryParentId(id);
				Map<String,Object> data1 = analyseService.getAreaPathMap(county_id,"county");
				countyMap = (Map<String,Object>)data1.get("area");
				townList = (List<Map<String,Object>>)data1.get("areaList");
				String city_id = analyseService.queryParentId(county_id);
				Map<String,Object> data2 = analyseService.getAreaPathMap(city_id,"city");
				cityMap = (Map<String,Object>)data2.get("area");
				countyList = (List<Map<String,Object>>)data2.get("areaList");
				String prov_id = analyseService.queryParentId(city_id);
				Map<String,Object> data3 = analyseService.getAreaPathMap(prov_id,"province");
				provinceMap = (Map<String,Object>)data3.get("area");
				cityList = (List<Map<String,Object>>)data3.get("areaList");
			}
			
			Map<String,Object> data = analyseService.getAreaPathMap(id,level);
			townMap = (Map<String,Object>)data.get("area");
			scopeMap =  (Map<String,Object>)data.get("scope");
			countryList = (List<Map<String,Object>>)data.get("areaList");
		}else if("country".equals(level)){
			if(provinceMap == null || cityMap == null || countyMap == null || townMap == null){
				String country_id = analyseService.queryParentId(id);
				Map<String,Object> data0 = analyseService.getAreaPathMap(country_id,"town");
				townMap = (Map<String,Object>)data0.get("area");
				countryList = (List<Map<String,Object>>)data0.get("areaList");
				String county_id = analyseService.queryParentId(country_id);
				Map<String,Object> data1 = analyseService.getAreaPathMap(county_id,"county");
				countyMap = (Map<String,Object>)data1.get("area");
				townList = (List<Map<String,Object>>)data1.get("areaList");
				String city_id = analyseService.queryParentId(county_id);
				Map<String,Object> data2 = analyseService.getAreaPathMap(city_id,"city");
				cityMap = (Map<String,Object>)data2.get("area");
				countyList = (List<Map<String,Object>>)data2.get("areaList");
				String prov_id = analyseService.queryParentId(city_id);
				Map<String,Object> data3 = analyseService.getAreaPathMap(prov_id,"province");
				provinceMap = (Map<String,Object>)data3.get("area");
				cityList = (List<Map<String,Object>>)data3.get("areaList");
			}
			
			Map<String,Object> data = analyseService.getAreaPathMap(id,level);
			scopeMap =  (Map<String,Object>)data.get("scope");
			countryMap = (Map<String,Object>)data.get("area");
		}
		
		Map.put("provinceMap", provinceMap);
		Map.put("cityMap", cityMap);
		Map.put("cityList", cityList);
		Map.put("countyMap", countyMap);
		Map.put("countyList", countyList);
		Map.put("townMap", townMap);
		Map.put("townList", townList);
		Map.put("countryMap", countryMap);
		Map.put("countryList", countryList);
		Map.put("scopeMap", scopeMap);
 		return analyseService.getAreaPathData(Map);
	}
	
	@RequestMapping("/getAverageIncomeData")
	@ResponseBody
	public String getAverageIncomeData(String id,String level,String scope,String poor_type,String year){
 		return analyseService.getAverageIncomeData(id,level,scope,poor_type,year);
	}
	
	@RequestMapping("/getAverageIncomeTableData")
	@ResponseBody
	public String getAverageIncomeTableData(String id,String level,String scope,String poor_type,String year,String month){
		return analyseService.getAverageIncomeTableData(id,level,scope,poor_type,year,month);
	}
	
	@RequestMapping("/getSocialPolicyData")
	@ResponseBody
	public String getSocialPolicyData(String id,String level,String scope,String poor_attribute){
 		return analyseService.getSocialPolicyData(id,level,scope,poor_attribute);
	}
	
	@RequestMapping("/getSocialPolicyTableData")
	@ResponseBody
	public String getSocialPolicyTableData(String id,String level,String scope,String poor_attribute,String year,String month){
		return analyseService.getSocialPolicyTableData(id,level,scope,poor_attribute,year,month);
	}
	
	@RequestMapping("/getSocialPolicyDetailSearchData")
	@ResponseBody
	public String getSocialPolicyDetailSearchData(String id,String level,String scope,String poor_attribute,String year,String month,String keyword,Integer next_id){
 		return analyseService.getSocialPolicyDetailSearchData(id,level,scope,poor_attribute,year,month,keyword,next_id);
	}
	
	@RequestMapping("/getGuaranteeEduData")
	@ResponseBody
	public String getGuaranteeEduData(String id,String level,String scope){
 		return analyseService.getGuaranteeEduData(id,level,scope);
	}
	
	@RequestMapping("/getGuaranteeEduTableData")
	@ResponseBody
	public String getGuaranteeEduTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getGuaranteeEduTableData(id,level,scope,year,month);
	}
	
	@RequestMapping("/getGuaranteeEduDetailSearchData")
	@ResponseBody
	public String getGuaranteeEduDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getGuaranteeEduDetailSearchData(id, level, scope, year, month, keyword,next_id);
	}
	
	@RequestMapping("/getGuaranteeMedicalData")
	@ResponseBody
	public String getGuaranteeMedicalData(String id,String level,String scope){
 		return analyseService.getGuaranteeMedicalData(id,level,scope);
	}
	
	@RequestMapping("/getGuaranteeMedicalTableData")
	@ResponseBody
	public String getGuaranteeMedicalTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getGuaranteeMedicalTableData(id,level,scope,year,month);
	}
	
	@RequestMapping("/getGuaranteeMedicalDetailSearchData")
	@ResponseBody
	public String getGuaranteeMedicalDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getGuaranteeMedicalDetailSearchData(id, level, scope, year, month, keyword, next_id);
	}
	
	@RequestMapping("/getGuaranteeHouseData")
	@ResponseBody
	public String getGuaranteeHouseData(String id,String level,String scope){
 		return analyseService.getGuaranteeHouseData(id,level,scope);
	}
	
	@RequestMapping("/getGuaranteeHouseTableData")
	@ResponseBody
	public String getGuaranteeHouseTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getGuaranteeHouseTableData(id,level,scope,year,month);
	}
	
	@RequestMapping("/getGuaranteeHouseDetailSearchData")
	@ResponseBody
	public String getGuaranteeHouseDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getGuaranteeHouseDetailSearchData(id, level, scope, year, month, keyword, next_id);
	}
	
	@RequestMapping("/getConditionRoadData")
	@ResponseBody
	public String getConditionRoadData(String id,String level,String scope){
 		return analyseService.getConditionRoadData(id, level, scope);
	}
	
	@RequestMapping("/getConditionRoadTableData")
	@ResponseBody
	public String getConditionRoadTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getConditionRoadTableData(id, level, scope, year, month);
	}
	
	@RequestMapping("/getConditionRoadDetailSearchData")
	@ResponseBody
	public String getConditionRoadDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getConditionRoadDetailSearchData(id, level, scope, year, month, keyword, next_id);
	}
	
	@RequestMapping("/getConditionWaterData")
	@ResponseBody
	public String getConditionWaterData(String id,String level,String scope){
 		return analyseService.getConditionWaterData(id,level,scope);
	}
	
	@RequestMapping("/getConditionWaterTableData")
	@ResponseBody
	public String getConditionWaterTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getConditionWaterTableData(id, level, scope, year, month);
	}
	
	@RequestMapping("/getConditionWaterDetailSearchData")
	@ResponseBody
	public String getConditionWaterDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getConditionWaterDetailSearchData(id, level, scope, year, month, keyword, next_id);
	}
	
	@RequestMapping("/getConditionElectricityData")
	@ResponseBody
	public String getConditionElectricityData(String id,String level,String scope){
 		return analyseService.getConditionElectricityData(id,level,scope);
	}
	
	@RequestMapping("/getConditionElectricityTableData")
	@ResponseBody
	public String getConditionElectricityTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getConditionElectricityTableData(id, level, scope, year, month);
	}
	
	@RequestMapping("/getConditionElectricityDetailSearchData")
	@ResponseBody
	public String getConditionElectricityDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getConditionElectricityDetailSearchData(id, level, scope, year, month, keyword, next_id);
	}
	
	@RequestMapping("/getConditionHealthData")
	@ResponseBody
	public String getConditionHealthData(String id,String level,String scope){
 		return analyseService.getConditionHealthData(id,level,scope);
	}
	
	@RequestMapping("/getConditionHealthTableData")
	@ResponseBody
	public String getConditionHealthTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getConditionHealthTableData(id, level, scope, year, month);
	}
	
	@RequestMapping("/getConditionHealthDetailSearchData")
	@ResponseBody
	public String getConditionHealthDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getConditionHealthDetailSearchData(id, level, scope, year, month, keyword, next_id);
	}
	
	@RequestMapping("/getConditionBroadcastData")
	@ResponseBody
	public String getConditionBroadcastData(String id,String level,String scope){
 		return analyseService.getConditionBroadcastData(id,level,scope);
	}
	
	@RequestMapping("/getConditionBroadcastTableData")
	@ResponseBody
	public String getConditionBroadcastTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getConditionBroadcastTableData(id, level, scope, year, month);
	}
	
	@RequestMapping("/getConditionBroadcastDetailSearchData")
	@ResponseBody
	public String getConditionBroadcastDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getConditionBroadcastDetailSearchData(id, level, scope, year, month, keyword, next_id);
	}
	
	@RequestMapping("/getConditionNetData")
	@ResponseBody
	public String getConditionNetData(String id,String level,String scope){
 		return analyseService.getConditionNetData(id,level,scope);
	}
	
	@RequestMapping("/getConditionNetTableData")
	@ResponseBody
	public String getConditionNetTableData(String id,String level,String scope,String year,String month){
 		return analyseService.getConditionNetTableData(id, level, scope, year, month);
	}
	
	@RequestMapping("/getConditionNetDetailSearchData")
	@ResponseBody
	public String getConditionNetDetailSearchData(String id,String level,String scope,String year,String month,String keyword,Integer next_id){
 		return analyseService.getConditionNetDetailSearchData(id, level, scope, year, month, keyword, next_id);
	}
	
	@RequestMapping("/getPoorLaborData")
	@ResponseBody
	public String getPoorLaborData(String id,String level,String scope){
 		return analyseService.getPoorLaborData(id,level,scope);
	}
	
	@RequestMapping("/getPoorEduData")
	@ResponseBody
	public String getPoorEduData(String id,String level,String scope){
 		return analyseService.getPoorEduData(id,level,scope);
	}
	
	@RequestMapping("/getPoorReasonData")
	@ResponseBody
	public String getPoorReasonData(String id,String level,String scope){
 		return analyseService.getPoorReasonData(id,level,scope);
	}
	
	@RequestMapping("/getPoorDangerHouseData")
	@ResponseBody
	public String getPoorDangerHouseData(String id,String level,String scope){
 		return analyseService.getPoorDangerHouseData(id,level,scope);
	}
	
	@RequestMapping("/getPoorAttributeData")
	@ResponseBody
	public String getPoorAttributeData(String id,String level,String scope){
 		return analyseService.getPoorAttributeData(id,level,scope);
	}
	
	@RequestMapping("/getPoorHealthData")
	@ResponseBody
	public String getPoorHealthData(String id,String level,String scope){
 		return analyseService.getPoorHealthData(id,level,scope);
	}
	
	@RequestMapping("/getAlarmedFamilyData")
	@ResponseBody
	public String getAlarmedFamilyData(String id,String level,String scope,String dept){
 		return analyseService.getAlarmedFamilyData(id,level,scope,dept);
	}
	
	@RequestMapping("/getAlarmedFamilyTableData")
	@ResponseBody
	public String getAlarmedFamilyTableData(String id,String level,String scope,String year,String month,String dept){
 		return analyseService.getAlarmedFamilyTableData(id, level, scope, year, month, dept);
	}
	
	@RequestMapping("/getAlarmedFamilyDetailSearchData")
	@ResponseBody
	public String getAlarmedFamilyDetailSearchData(String id,String level,String scope,String year,String month,String dept,String keyword,Integer next_id){
 		return analyseService.getAlarmedFamilyDetailSearchData(id, level, scope, year, month, dept, keyword, next_id);
	}
}
