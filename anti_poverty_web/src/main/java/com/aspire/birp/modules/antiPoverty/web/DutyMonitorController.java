package com.aspire.birp.modules.antiPoverty.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.DutyMonitorService;

/**
 * 责任监控controller类 
 * @author zhudiyuan
 * @version 
 */

@Controller
@RequestMapping("/antiPoverty/dutyMonitor")
public class DutyMonitorController {
	
	@Autowired
    private DutyMonitorService dutyMonitorService; 
	
    @RequestMapping("/getDataMonitorDutyDutyManagement")
	@ResponseBody
	public String getDataMonitorDutyDutyManagement(HttpServletRequest request,String id,String level,String year) {
 		return dutyMonitorService.getDataMonitorDutyDutyManagement(request,id, level, year);
	}
	
	@RequestMapping("/getDataMonitorDutyDutyAnalysis")
	@ResponseBody
	public String getDataMonitorDutyDutyAnalysis(HttpServletRequest request,String id,String level,String year) {
 		return dutyMonitorService.getDataMonitorDutyDutyAnalysis(request,id, level, year);
	}
}
