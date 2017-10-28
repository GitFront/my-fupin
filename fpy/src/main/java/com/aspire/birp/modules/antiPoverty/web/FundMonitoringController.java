package com.aspire.birp.modules.antiPoverty.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.FundMonitoringService;
@Controller
@RequestMapping("/antiPoverty/fund")
public class FundMonitoringController {
	
	@Autowired
    private FundMonitoringService fundMonitoringService;
	
	@RequestMapping("/getDataMonitorCapitalCapitalManagement")
	@ResponseBody
	public String getgetDataMonitorCapitalCapitalManagement(HttpServletRequest request,String id,String level,String year,String scope) {
		return fundMonitoringService.getgetDataMonitorCapitalCapitalManagement(request, id, level, year, scope);
	}
	@RequestMapping("/getDataMonitorCapitalCapitalAnalysis")
	@ResponseBody
	public String getDataMonitorCapitalCapitalAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String project_type_country,String project_type_family) {
		return fundMonitoringService.getDataMonitorCapitalCapitalAnalysis(request, id, level, year, scope,project_type_country,project_type_family);
	}
	
	@RequestMapping("/getDataMonitorCapitalCapitalFile")
	@ResponseBody
	public String getDataMonitorCapitalCapitalFile(HttpServletRequest request,String level,String year,String keyword,Integer page) {
		return fundMonitoringService.getDataMonitorCapitalCapitalFile(request, level, year,keyword,page);
	}
	
	@RequestMapping("/getDataMonitorCapitalCapitalFileDetailTable")
	@ResponseBody
	public String getDataMonitorCapitalCapitalFileDetailTable(HttpServletRequest request,String id,String area_level,String area_id,Integer page) {
		return fundMonitoringService.getDataMonitorCapitalCapitalFileDetailTable(request, id, area_level,area_id,page);
	}
	
	@RequestMapping("/getDataMonitorCapitalCapitalFileBasicInfo")
	@ResponseBody
	public String getDataMonitorCapitalCapitalFileBasicInfo(HttpServletRequest request,String id) {
		return fundMonitoringService.getDataMonitorCapitalCapitalFileBasicInfo(request, id);
	}
	
	@RequestMapping("/getDataMonitorFileTree")
	@ResponseBody
	public String getDataMonitorFileTree(String id,Integer file_id,String level,String year,String wintype,String tabtype) {
 		return fundMonitoringService.getDataMonitorFileTree(id,file_id, level, year, wintype, tabtype);
	}
	
	@RequestMapping("/getDataMonitorFileSubtree")
	@ResponseBody
	public String getDataMonitorFileSubtree(String id,Integer file_id,String level,String year,String wintype,String tabtype) {
 		return fundMonitoringService.getDataMonitorFileSubtree(id,file_id, level, year, wintype, tabtype);
	}
	
	@RequestMapping("/downloadFoundFile")
	public void downloadFoundFile(HttpServletResponse response,String filePath,String fileName,String fileType) {
		fundMonitoringService.downloadFoundFile(response,filePath,fileName,fileType);
	}
	
	@RequestMapping("/getPoorFamilyList")
	@ResponseBody
	public String getPoorFamilyList(HttpServletRequest request,String level,String id,Integer page)   {
		return fundMonitoringService.getPoorFamilyList(request, level,id,page);
	}
}
