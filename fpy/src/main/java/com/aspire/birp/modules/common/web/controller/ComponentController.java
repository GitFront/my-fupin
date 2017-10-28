package com.aspire.birp.modules.common.web.controller;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bi.common.web.bean.easyui.DataGrid;
import com.aspire.bi.common.web.controller.BaseController;
import com.aspire.birp.modules.common.service.ComponentDataService;

@Controller
@RequestMapping(value = "${adminPath}/component")
public class ComponentController extends BaseController {
	
	private static Logger log = Logger.getLogger(ComponentController.class);
	
	@Autowired
	private ComponentDataService componentDataService;
	
	public void setComponentDataService(ComponentDataService componentDataService) {
		this.componentDataService = componentDataService;
	}
	
	@RequestMapping("/gridData")
	@ResponseBody
 	public DataGrid queryGridData(@RequestParam Map<String, Object> params){
 		DataGrid dataGrid = new DataGrid();
   		try {
   			String queryName = params.get("queryName").toString();
  			Integer page =  Integer.parseInt(params.get("page").toString().trim());
  			Integer rows = Integer.parseInt(params.get("rows").toString().trim());
  			Integer startIndex = (page - 1)*rows;
   			Object[] data =  componentDataService.queryDataGrid(queryName, params, startIndex, rows);
  	  		dataGrid.setTotal(Long.parseLong(data[0].toString()));	 
  			dataGrid.setRows(data[1]==null?null:(ArrayList<?>)data[1]);
  		} catch (Exception e) {
			log.error("查询griddata 失败",e);
 		}
   		return dataGrid;
	}
	
	
}
