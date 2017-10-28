package com.aspire.birp.modules.antiPoverty.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.ExampleCountryService;
/**
 * 示范村controller类 
 * @author zhudiyuan
 *
 */
@Controller
@RequestMapping("/antiPoverty/exampleCountry")
public class ExampleCountryController {
	
	@Autowired
    private ExampleCountryService exampleCountryService;
	
	@RequestMapping("/queryCountryIndexStaticData")
	@ResponseBody
	public String queryCountryIndexStaticData(Integer scope,String id,String level){
 		return exampleCountryService.queryCountryIndexStaticData(scope,id,level);
	}
	
	@RequestMapping("/getDataExampleCountryCountryPhoto")
	@ResponseBody
	public String getDataExampleCountryCountryPhoto(String id){
 		return exampleCountryService.getDataExampleCountryCountryPhoto(id);
	}

	@RequestMapping("/getExampleCountryRenovateStandardChart")
	@ResponseBody
	public String getExampleCountryRenovateStandardChart(String id,String level,String year) {
		return exampleCountryService.getExampleCountryRenovateStandardChart(id, level, year);
	}
	@RequestMapping("/getExampleCountryRenovateStandardTable")
	@ResponseBody
	public String getExampleCountryRenovateStandardTable(HttpServletRequest request,String id,String level,String year,String table_month,String table_state) {
		return exampleCountryService.getExampleCountryRenovateStandardTable(request, id, level, year,table_month,table_state);
	}
}
