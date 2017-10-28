package com.aspire.birp.modules.antiPoverty.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.FundMonitoringService;
import com.aspire.birp.modules.antiPoverty.service.PoorObjectService;

@Controller
@RequestMapping("/antiPoverty/poorObject")
public class PoorObjectController {
	
	@Autowired
    private PoorObjectService poorObjectService;
	
	@RequestMapping("/getDataMonitorHeadDownloadTableList")
	@ResponseBody
	public String getDataMonitorHeadDownloadTableList(HttpServletRequest request,String id,String level,String year,String scope) {
		return poorObjectService.getDataMonitorHeadDownloadTableList(request, id, level, year, scope);
	}
	
	@RequestMapping("/doExport")
	@ResponseBody
	public String beforeExport(String export_task_id) throws Exception{
 		return poorObjectService.doExport(export_task_id);
	}
	
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletResponse response,String filePath,String fileName, String export_task_id) {
		poorObjectService.downloadFile(response,filePath,fileName,export_task_id);
	}

}
