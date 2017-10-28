package com.aspire.birp.modules.antiPoverty.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.DataAnalyseService;
import com.aspire.birp.modules.antiPoverty.service.DataMonitorService;

/**
 * 数据分析controller类 
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2017年3月20日 下午6:13:54
 */
@Controller
@RequestMapping("/antiPoverty/dataMonitor")
public class DataMonitorController {
	
	@Autowired
    private DataAnalyseService analyseService;
	@Autowired
    private DataMonitorService dataMonitorService;
	
	@RequestMapping("/getDataMonitorTree")
	@ResponseBody
	public String getDataMonitorTree(String id,String level,String year,String wintype,String tabtype) {
 		return dataMonitorService.getDataMonitorTree(id, level, year, wintype, tabtype);
	}
	
	@RequestMapping("/getDataMonitorSubTree")
	@ResponseBody
	public String getDataMonitorSubTree(String id,String level,String year,String wintype,String tabtype) {
 		return dataMonitorService.getDataMonitorSubTree(id, level, year, wintype, tabtype);
	}
	
	@RequestMapping("/getDataMonitorBasicInfo")
	@ResponseBody
	public String getDataMonitorBasicInfo(String id,String level,String year) {
 		return dataMonitorService.getDataMonitorBasicInfo(id, level,year);
	}
	
	@RequestMapping("/getDataMonitorContentBasicInfo")
	@ResponseBody
	public String getDataMonitorContentBasicInfo(String id,String level,String year) {
 		return dataMonitorService.getDataMonitorContentBasicInfo(id, level, year);
	}
	
	@RequestMapping("/getDataMonitorOrderBasicInfo")
	@ResponseBody
	public String getDataMonitorOrderBasicInfo(String id,String level,String year,String wintype,String tabtype) {
 		return dataMonitorService.getDataMonitorOrderBasicInfo(id, level, year, wintype, tabtype);
	}
	
	@RequestMapping("/getDataMonitoOrderTable")
	@ResponseBody
	public String getDataMonitoOrderTable(HttpServletRequest request,String id,String level,String year,String wintype,String tabtype,Integer page) {
 		return dataMonitorService.getDataMonitoOrderTable(request, id, level, year, wintype, tabtype, page);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getDataMonitorDetailBasicInfo")
	@ResponseBody
	public String getDataMonitorDetailBasicInfo(HttpSession session,String id,String level,String year,String month,String wintype,String tabtype) {
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
 		return dataMonitorService.getDataMonitorDetailBasicInfo(id, level, year,month, wintype, tabtype, Map);
	}
	
	@RequestMapping("/getDataMonitorDetailParamsInfo")
	@ResponseBody
	public String getDataMonitorDetailParamsInfo(String id,String level,String year,String wintype,String tabtype) {
 		return dataMonitorService.getDataMonitorDetailParamsInfo(id, level, year, wintype, tabtype);
	}
	
	@RequestMapping("/getDataMonitorDetailTable")
	@ResponseBody
	public String getDataMonitorDetailTable(HttpServletRequest request,String id,String level,String year,String month,String wintype,String tabtype,Integer page) {
 		return dataMonitorService.getDataMonitorDetailTable(request, id, level, year,month, wintype, tabtype, page);
	}
	
	@RequestMapping("/getDataMonitorAreaDataTable")
	@ResponseBody
	public String getDataMonitorAreaDataTable(HttpServletRequest request,String id,String level,String year,String wintype,String tabtype) {
 		return dataMonitorService.getDataMonitorAreaDataTable(request, id, level, year, wintype, tabtype);
	}
	
	@RequestMapping("/getDataMonitorPoorChangeManagement")
	@ResponseBody
	public String getDataMonitorPoorChangeManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorPoorChangeManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorPoorChangeAnalysisAll")
	@ResponseBody
	public String getDataMonitorPoorChangeAnalysisAll(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
 		return dataMonitorService.getDataMonitorPoorChangeAnalysisAll(request,id, level, year, scope, poor_attribute, labor_attribute, status_attribute);
	}
	
	@RequestMapping("/getDataMonitorPoorChangeAnalysisCur")
	@ResponseBody
	public String getDataMonitorPoorChangeAnalysisCur(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
 		return dataMonitorService.getDataMonitorPoorChangeAnalysisCur(request, id, level, year, scope, poor_attribute, labor_attribute, status_attribute);
	}
	
	@RequestMapping("/getDataMonitorPoorChangeAnalysisAdd")
	@ResponseBody
	public String getDataMonitorPoorChangeAnalysisAdd(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
 		return dataMonitorService.getDataMonitorPoorChangeAnalysisAdd(request, id, level, year, scope, poor_attribute, labor_attribute, status_attribute);
	}
	
	@RequestMapping("/getDataMonitorPoorChangeAnalysisEnd")
	@ResponseBody
	public String getDataMonitorPoorChangeAnalysisEnd(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
 		return dataMonitorService.getDataMonitorPoorChangeAnalysisEnd(request, id, level, year, scope, poor_attribute, labor_attribute, status_attribute);
	}
	
	@RequestMapping("/getDataMonitorPoorChangeAnalysisNaturalChange")
	@ResponseBody
	public String getDataMonitorPoorChangeAnalysisNaturalChange(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
 		return dataMonitorService.getDataMonitorPoorChangeAnalysisNaturalChange(request, id, level, year, scope, poor_attribute, labor_attribute, status_attribute);
	}
	
	@RequestMapping("/getDataMonitorPoorResultPlanManagement")
	@ResponseBody
	public String getDataMonitorPoorResultPlanManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorPoorResultPlanManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorPoorResultResultAnalysis")
	@ResponseBody
	public String getDataMonitorPoorResultResultAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute) {
 		return dataMonitorService.getDataMonitorPoorResultResultAnalysis(request, id, level, year, scope, poor_attribute, labor_attribute);
	}
	
	@RequestMapping("/getDataMonitorPoorResultNotSuccessAnalysis")
	@ResponseBody
	public String getDataMonitorPoorResultNotSuccessAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute) {
 		return dataMonitorService.getDataMonitorPoorResultNotSuccessAnalysis(request, id, level, year, scope, poor_attribute, labor_attribute);
	}
	
	@RequestMapping("/getDataMonitorAverageIncomeIncomeManagement")
	@ResponseBody
	public String getDataMonitorAverageIncomeIncomeManagement(HttpServletRequest request,String id,String level,String year,String scope) {
 		return dataMonitorService.getDataMonitorAverageIncomeIncomeManagement(request, id, level, year, scope);
	}
	
	@RequestMapping("/getDataMonitorAverageIncomeSuccessAnalysis")
	@ResponseBody
	public String getDataMonitorAverageIncomeSuccessAnalysis(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
 		return dataMonitorService.getDataMonitorAverageIncomeSuccessAnalysis(request, id, level, scope, poor_attribute, labor_attribute, year);
	}
	
	@RequestMapping("/getDataMonitorAverageIncomeSuccessAnalysisTransferredIncome")
	@ResponseBody
	public String getDataMonitorAverageIncomeSuccessAnalysisTransferredIncome(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
 		return dataMonitorService.getDataMonitorAverageIncomeSuccessAnalysisTransferredIncome(request, id, level, scope, poor_attribute, labor_attribute, year);
	}
	
	@RequestMapping("/getDataMonitorAverageIncomeSuccessAnalysisTransferredPayment")
	@ResponseBody
	public String getDataMonitorAverageIncomeSuccessAnalysisTransferredPayment(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
 		return dataMonitorService.getDataMonitorAverageIncomeSuccessAnalysisTransferredPayment(request, id, level, scope, poor_attribute, labor_attribute, year);
	}
	
	@RequestMapping("/getDataMonitorAverageIncomePoorAnalysis")
	@ResponseBody
	public String getDataMonitorAverageIncomePoorAnalysis(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
 		return dataMonitorService.getDataMonitorAverageIncomePoorAnalysis(request, id, level, scope, poor_attribute, labor_attribute, year);
	}
	
	@RequestMapping("/getDataMonitorAverageIncomePoorAnalysisTransferredIncome")
	@ResponseBody
	public String getDataMonitorAverageIncomePoorAnalysisTransferredIncome(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
 		return dataMonitorService.getDataMonitorAverageIncomePoorAnalysisTransferredIncome(request, id, level, scope, poor_attribute, labor_attribute, year);
	}
	
	@RequestMapping("/getDataMonitorAverageIncomePoorAnalysisTransferredPayment")
	@ResponseBody
	public String getDataMonitorAverageIncomePoorAnalysisTransferredPayment(HttpServletRequest request,String id,String level,String scope,String poor_attribute,String labor_attribute,String year) {
 		return dataMonitorService.getDataMonitorAverageIncomePoorAnalysisTransferredPayment(request, id, level, scope, poor_attribute, labor_attribute, year);
	}
	
	@RequestMapping("/getDataMonitorFiveLowImplementManagement")
	@ResponseBody
	public String getDataMonitorFiveLowImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorFiveLowImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorFiveLowImplementAnalysis")
	@ResponseBody
	public String getDataMonitorFiveLowImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
 		return dataMonitorService.getDataMonitorFiveLowImplementAnalysis(request, id, level, year, scope, poor_attribute);
	}
	
	@RequestMapping("/getDataMonitorEduImplementManagement")
	@ResponseBody
	public String getDataMonitorEduImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorEduImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorEduImplementAnalysis")
	@ResponseBody
	public String getDataMonitorEduImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String edu_levels) {
 		return dataMonitorService.getDataMonitorEduImplementAnalysis(request, id, level, year, scope, poor_attribute, edu_levels);
	}
	
	@RequestMapping("/getDataMonitorMedicalPolicyImplementManagement")
	@ResponseBody
	public String getDataMonitorMedicalPolicyImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorMedicalPolicyImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorMedicalPolicyImplementAnalysis")
	@ResponseBody
	public String getDataMonitorMedicalPolicyImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
 		return dataMonitorService.getDataMonitorMedicalPolicyImplementAnalysis(request, id, level, year, scope, poor_attribute);
	}
	
	@RequestMapping("/getDataMonitorHouseImplementManagement")
	@ResponseBody
	public String getDataMonitorHouseImplementManagement(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
 		return dataMonitorService.getDataMonitorHouseImplementManagement(request, id, level, year, scope, poor_attribute);
	}
	
	@RequestMapping("/getDataMonitorHouseImplementAnalysis")
	@ResponseBody
	public String getDataMonitorHouseImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
 		return dataMonitorService.getDataMonitorHouseImplementAnalysis(request, id, level, year, scope);
	}
	
	@RequestMapping("/beforeExport")
	@ResponseBody
	public String beforeExport(String export_task_id) throws Exception{
 		return dataMonitorService.beforeExport(export_task_id);
	}
	
	@RequestMapping("/loadProgress")
	@ResponseBody
	public String loadProgress(HttpServletRequest request,String export_task_id) {
 		return dataMonitorService.loadProgress(request, export_task_id);
	}
	
	@RequestMapping("/downloadCsvFile")
	public void downloadCsvFile(HttpServletResponse response,String csvFilePath,String fileName) {
 		dataMonitorService.downloadCsvFile(response,csvFilePath,fileName);
	}
	
	@RequestMapping("/getDataMonitorRoadImplementManagement")
	@ResponseBody
	public String getDataMonitorRoadImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorRoadImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorRoadImplementAnalysis")
	@ResponseBody
	public String getDataMonitorRoadImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
 		return dataMonitorService.getDataMonitorRoadImplementAnalysis(request, id, level, year, scope);
	}
	
	@RequestMapping("/getDataMonitorWaterImplementManagement")
	@ResponseBody
	public String getDataMonitorWaterImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorWaterImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorWaterImplementAnalysis")
	@ResponseBody
	public String getDataMonitorWaterImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
 		return dataMonitorService.getDataMonitorWaterImplementAnalysis(request, id, level, year, scope);
	}
	
	@RequestMapping("/getDataMonitorElectricityImplementManagement")
	@ResponseBody
	public String getDataMonitorElectricityImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorElectricityImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorElectricityImplementAnalysis")
	@ResponseBody
	public String getDataMonitorElectricityImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
 		return dataMonitorService.getDataMonitorElectricityImplementAnalysis(request, id, level, year, scope);
	}
	
	@RequestMapping("/getDataMonitorMedicalFacilityImplementManagement")
	@ResponseBody
	public String getDataMonitorMedicalFacilityImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorMedicalFacilityImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorMedicalFacilityImplementAnalysis")
	@ResponseBody
	public String getDataMonitorMedicalFacilityImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
 		return dataMonitorService.getDataMonitorMedicalFacilityImplementAnalysis(request, id, level, year, scope);
	}
	
	@RequestMapping("/getDataMonitorBroadcastImplementManagement")
	@ResponseBody
	public String getDataMonitorBroadcastImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorBroadcastImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorBroadcastImplementAnalysis")
	@ResponseBody
	public String getDataMonitorBroadcastImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
 		return dataMonitorService.getDataMonitorBroadcastImplementAnalysis(request, id, level, year, scope);
	}
	
	@RequestMapping("/getDataMonitorNetImplementManagement")
	@ResponseBody
	public String getDataMonitorNetImplementManagement(HttpServletRequest request,String id,String level,String year) {
 		return dataMonitorService.getDataMonitorNetImplementManagement(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorNetImplementAnalysis")
	@ResponseBody
	public String getDataMonitorNetImplementAnalysis(HttpServletRequest request,String id,String level,String year,String scope) {
 		return dataMonitorService.getDataMonitorNetImplementAnalysis(request, id, level, year, scope);
	}
	
	@RequestMapping("/getDataMonitorPoorAanalysisPoorAnalysis")
	@ResponseBody
	public String getDataMonitorPoorAanalysisPoorAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
 		return dataMonitorService.getDataMonitorPoorAanalysisPoorAnalysis(request,id,level,year,scope,poor_attribute,labor_attribute,status_attribute);
	}
	
	@RequestMapping("/getDataMonitorAlarmedPoorExceptionMonitor")
	@ResponseBody
	public String getDataMonitorAlarmedPoorExceptionMonitor(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
 		return dataMonitorService.getDataMonitorAlarmedPoorExceptionMonitor(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorAlarmedRecordsExceptionMonitor")
	@ResponseBody
	public String getDataMonitorAlarmedRecordsExceptionMonitor(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute,String status_attribute) {
 		return dataMonitorService.getDataMonitorAlarmedRecordsExceptionMonitor(request, id, level, year);
	}
	
	@RequestMapping("/getDataMonitorAlarmedVisitExceptionMonitor")
	@ResponseBody
	public String getDataMonitorAlarmedVisitExceptionMonitor(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorAlarmedVisitExceptionMonitor(request, id, level, year);
	}
	@RequestMapping("/getDataMonitorAlarmedMoneyExceptionMonitor")
	@ResponseBody
	public String getDataMonitorAlarmedMoneyExceptionMonitor(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorAlarmedMoneyExceptionMonitor(request, id, level, year);
	}
	//项目监控户扶贫项目分析-产业扶贫  By YXT
	@RequestMapping("/getDataMonitorProjectFamilyIndustry")
	@ResponseBody
	public String getDataMonitorProjectFamilyIndustry(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute,String labor_attribute) {
 		return dataMonitorService.getDataMonitorProjectFamilyIndustry(request,id, level, year, scope, poor_attribute,labor_attribute);
	}
	//项目监控户扶贫项目分析-金融扶贫  By YXT
		@RequestMapping("/getDataMonitorProjectFamilyFinance")
		@ResponseBody
		public String getDataMonitorProjectFamilyFinance(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
	 		return dataMonitorService.getDataMonitorProjectFamilyFinance(request,id, level, year, scope, poor_attribute);
		}
	//项目监控户扶贫项目分析-住房改造  By YXT
		@RequestMapping("/getDataMonitorProjectFamilyHouse")
		@ResponseBody
		public String getDataMonitorProjectFamilyHouse(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
			 return dataMonitorService.getDataMonitorProjectFamilyHouse(request,id, level, year, scope, poor_attribute);
		}
	//项目监控户扶贫项目分析-资产扶贫  By YXT
			@RequestMapping("/getDataMonitorProjectFamilyProperty")
			@ResponseBody
			public String getDataMonitorProjectFamilyProperty(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
				 return dataMonitorService.getDataMonitorProjectFamilyProperty(request,id, level, year, scope, poor_attribute);
			}
	//项目监控户扶贫项目分析-慰问扶贫  By YXT
	@RequestMapping("/getDataMonitorProjectFamilyVisit")
	@ResponseBody
	public String getDataMonitorProjectFamilyVisit(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
		 return dataMonitorService.getDataMonitorProjectFamilyVisit(request,id, level, year, scope, poor_attribute);
	}
	//项目监控户扶贫项目分析-就业扶贫  By YXT
	@RequestMapping("/getDataMonitorProjectFamilyEmployment")
	@ResponseBody
	public String getDataMonitorProjectFamilyEmployment(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
		 return dataMonitorService.getDataMonitorProjectFamilyEmployment(request,id, level, year, scope, poor_attribute);
	}
	//项目监控户扶贫项目分析-技能培训  By YXT
		@RequestMapping("/getDataMonitorProjectFamilySkill")
		@ResponseBody
		public String getDataMonitorProjectFamilySkill(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
			 return dataMonitorService.getDataMonitorProjectFamilySkill(request,id, level, year, scope, poor_attribute);
		}
	//项目监控户扶贫项目分析-教育扶贫  By YXT
			@RequestMapping("/getDataMonitorProjectFamilyEdu")
			@ResponseBody
			public String getDataMonitorProjectFamilyEdu(HttpServletRequest request,String id,String level,String year,String scope,String poor_attribute) {
				 return dataMonitorService.getDataMonitorProjectFamilyEdu(request,id, level, year, scope, poor_attribute);
			}
	
	
	@RequestMapping("/getDataMonitorProjectCountryRoad")
	@ResponseBody
	public String getDataMonitorProjectCountryRoad(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryRoad(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorProjectCountryWater")
	@ResponseBody
	public String getDataMonitorProjectCountryWater(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryWater(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorProjectCountryRecreationSport")
	@ResponseBody
	public String getDataMonitorProjectCountryRecreationSport(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryRecreationSport(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorProjectCountryHygiene")
	@ResponseBody
	public String getDataMonitorProjectCountryHygiene(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryHygiene(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorProjectCountryLamp")
	@ResponseBody
	public String getDataMonitorProjectCountryLamp(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryLamp(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorProjectCountryFarm")
	@ResponseBody
	public String getDataMonitorProjectCountryFarm(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryFarm(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorProjectCountryPublicFacility")
	@ResponseBody
	public String getDataMonitorProjectCountryPublicFacility(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryPublicFacility(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorProjectCountryCollectiveEconomy")
	@ResponseBody
	public String getDataMonitorProjectCountryCollectiveEconomy(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryCollectiveEconomy(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorProjectCountryEdu")
	@ResponseBody
	public String getDataMonitorProjectCountryEdu(HttpServletRequest request,String id,String level,String year,String scope) {
		return dataMonitorService.getDataMonitorProjectCountryEdu(request, id, level, year, scope);
	}

}
