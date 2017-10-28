package com.aspire.birp.modules.antiPoverty.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.DataMonitorService;
import com.aspire.birp.modules.antiPoverty.service.ProjectMonitorService;

/**
 * 项目监控controller类 
 * @author zhudiyuan
 * @version 
 */

@Controller
@RequestMapping("/antiPoverty/projectMonitor")
public class ProjectMonitorController {
	
	@Autowired
    private ProjectMonitorService projectMonitorService;
	
	@RequestMapping("/getDataMonitorProjectProjectManagement")
	@ResponseBody
	public String getDataMonitorProjectProjectManagement(HttpServletRequest request,String id,String level,String year) {
 		return projectMonitorService.getDataMonitorProjectProjectManagement(request,id, level, year);
	}
	
	@RequestMapping("/getDataMonitorProjectProjectAnalysisAll")
	@ResponseBody
	public String getDataMonitorProjectProjectAnalysisAll(HttpServletRequest request,String id,String level,String year) {
 		return projectMonitorService.getDataMonitorProjectProjectAnalysisAll(request,id, level, year);
	}
	
	@RequestMapping("/getDataMonitorProjectProjectAnalysisCountry")
	@ResponseBody
	public String getDataMonitorProjectProjectAnalysisCountry(HttpServletRequest request,String id,String level,String year) {
 		return projectMonitorService.getDataMonitorProjectProjectAnalysisCountry(request,id, level, year);
	}
	
	@RequestMapping("/getDataMonitorProjectProjectAnalysisFamily")
	@ResponseBody
	public String getDataMonitorProjectProjectAnalysisFamily(HttpServletRequest request,String id,String level,String year) {
 		return projectMonitorService.getDataMonitorProjectProjectAnalysisFamily(request,id, level, year);
	}
	
	
}
