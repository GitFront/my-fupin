package com.aspire.birp.modules.common.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bi.common.web.controller.BaseController;
import com.aspire.birp.modules.base.constant.BaseConstant;
import com.aspire.birp.modules.common.service.ComponentDataService;
import com.aspire.birp.modules.common.service.ReportService;
import com.aspire.birp.modules.common.vo.GroupDataVo;
import com.aspire.birp.modules.common.vo.InvertedLineVo;
import com.aspire.birp.modules.common.vo.PieDataVo;


/**
 * Title: 用于封装图表生成的通用方法 <br>
 * Description: 主要用于生成Bar\StackBars\Line\TimeSeriesLine等图表的数据列表 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年7月9日 下午17:45:03 <br>
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/report")
public class ReportController extends BaseController{

	
	private static Logger log = Logger.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	@Autowired
	private ComponentDataService componentDataService;
	
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
	public void setComponentDataService(ComponentDataService componentDataService) {
		this.componentDataService = componentDataService;
	}
	
	

	/**
	 * Mybatis查询名称
	 */
	private String queryName;
	/**
	 * Mybatis过滤条件参数
	 */
	private Map<String, Object> parameters;
	/**
	 * 字段对应mapper映射
	 */
	private Map<String, Object> mapper;
	/**
	 * 仪表盘的数据精度
	 */
	private Integer decimal;
	/**
	 * 隐藏分组配置
	 */
	private String[] hideGroup;
	
	/**
	 * 行转列配置（饼状图）
	 */
	private Map<String,String> rowsValue;
	
	/**
	 * 行转列配置（分组报表）
	 */
	private Map<String,String> rowsGroup;
	
	/**
	 * 地图区域KEY码
	 */
	private String joinBy;
	
	/**
	 * 是否使用分页配置
	 */
	private boolean isPage;
	
	
	/**
	 * 通用页面跳转方法
	 * @return
	 */
	@RequestMapping("/forwards.htm")
	public String forwards(@RequestParam String path){
		return path;
	}
	
	/**
	 * 通用页面重定向方法
	 * @return
	 */
	@RequestMapping("/redirects.htm")
	public String redirects(@RequestParam String path){
		return "redirect:"+path;
	}
	
	
	/**
	 * 通用查询垂直折线图数据
	 * @param params 传入参数
	 * @return
	 */
	@RequestMapping(value = "/highchart/invertedLine/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<InvertedLineVo> loadInvertedLine(@RequestParam Map<String, Object> params){

		List<InvertedLineVo> vos = new ArrayList<InvertedLineVo>();
		
		/*初始化参数值*/
		this.initParameters(params);
		try {
   			vos = reportService.queryInvertedLineData(queryName, parameters, mapper,hideGroup);
  		} catch (Exception e) {
			log.error("查询 loadGroupData 失败",e);
 		}
		return vos;
	}
	
	/**
	 * 通用查询列表数据
	 * @param params 传入参数
	 * @return
	 */
	@RequestMapping(value = "/grid/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Map<String, Object> loadGrid(@RequestParam Map<String, Object> params){
		 Map<String, Object> result = new HashMap<String, Object>(2); 
		
		/*初始化参数值*/
		this.initParameters(params);
		
		if(StringUtils.isNotBlank(queryName)){
			try {
	   			if(this.isPage){
	   				Integer page =  Integer.parseInt(params.get("page").toString().trim());
	   	  			Integer rows = Integer.parseInt(params.get("rows").toString().trim());
	   	  			Integer startIndex = (page - 1)*rows;
	   	   			Object[] data =  componentDataService.queryDataGrid(queryName, parameters, startIndex, rows);
	   	  	  		result.put("total", Long.parseLong(data[0].toString()));  
	   	  	  		result.put("rows", (ArrayList<?>)data[1]);   	  			
	   			}else{
	   				List<Map<String,Object>> list = reportService.queryList(queryName, parameters);
	   				if(list != null){
	   					result.put("total", Long.valueOf(list.size()));  
		   	  	  		result.put("rows", list);
	   				}   				
	   			}
	  			
	  		} catch (Exception e) {
				log.error("查询griddata 失败",e);
	 		}
		}   		
   		return result;
	}
	
	/**
	 * 通用查询仪表盘数据
	 * @param params 传入参数
	 * @return
	 */
	@RequestMapping(value = "/highchart/gauge/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Double loadGauge(@RequestParam Map<String, Object> params){
		Double value = 0d;
		/*初始化参数值*/
		this.initParameters(params);
		try {
			value = reportService.queryGaugeData(queryName, parameters, mapper, decimal);
  		} catch (Exception e) {
			log.error("查询 loadGroupData 失败",e);
 		}
		return value;
	}
	
	/**
	 * 通用查询info表格数据
	 * @param params 传入参数
	 * @return
	 */
	@RequestMapping(value = "/infotable/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Map<String,Object> loadInfoTable(@RequestParam Map<String, Object> params){	

		Map<String,Object> info = new HashMap<String,Object>();
		/*初始化参数值*/
		this.initParameters(params);
		try {
			info = reportService.queryInfoTableData(queryName, parameters, mapper);
  		} catch (Exception e) {
			log.error("查询 info数据表格 失败",e);
 		}
		
		return info;
	}
	
	/**
	 * 通用查询分组数据
	 * @param params 传入参数
	 * @return
	 */
	@RequestMapping(value = "/highchart/groupData/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<GroupDataVo> loadGroupData(@RequestParam Map<String, Object> params){	

		List<GroupDataVo> vos = new ArrayList<GroupDataVo>();
		/*初始化参数值*/
		this.initParameters(params);
		try {
   			vos = reportService.queryGroupData(queryName, parameters, mapper,hideGroup,rowsGroup);
  		} catch (Exception e) {
			log.error("查询 loadGroupData 失败",e);
 		}
		
		return vos;
	}
	
	
	
	/**
	 * 通用查询饼状图数据
	 * @param params 传入参数
	 * @return
	 */
	@RequestMapping(value = "/highchart/pieData/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<PieDataVo> loadPieData(@RequestParam Map<String, Object> params){	
		List<PieDataVo> vos = new ArrayList<PieDataVo>();
		/*初始化参数值*/
		this.initParameters(params);
   		try {
   			vos = reportService.queryPieData(queryName, parameters, mapper,rowsValue);
  		} catch (Exception e) {
			log.error("查询 loadPieData 失败",e);
 		}
		return vos;
	}
	
	/**
	 * 通用查询饼状图数据
	 * @param params 传入参数
	 * @return
	 */
	@RequestMapping(value = "/highmaps/mapData/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Map<String,Object>> loadMapData(@RequestParam Map<String, Object> params){	
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		/*初始化参数值*/
		this.initParameters(params);
   		try {
   			list = reportService.queryMapData(queryName, parameters, mapper,joinBy);
  		} catch (Exception e) {
			log.error("查询 loadMapData 失败",e);
 		}
		return list;
	}
	
	private void initParameters(Map<String, Object> p){
		/*初始化传入参数*/
		String queryName_ = "";
		boolean isPage_ = false;
		Map<String, Object> parameters_ = new HashMap<String, Object>();
		Map<String, Object> mapper_ = new HashMap<String, Object>();
		/*参数赋值*/
		if(p != null && !p.isEmpty()){
			if(p.get("queryName") != null) queryName_ = p.get("queryName").toString();
			
			if(p.get("hideGroup") != null){
				String hide_ = p.get("hideGroup").toString();
				this.hideGroup = StringUtils.split(hide_,";");
			}else{
				this.hideGroup = new String[]{};
			}
			if(p.get("isPage") != null && "true".equals(p.get("isPage").toString())) isPage_ = true;
			
			if(p.get("decimal") != null){
				String decimal_ = p.get("decimal").toString();
				try {
						this.decimal = Integer.valueOf(decimal_);
				} catch (NumberFormatException e) {
					this.decimal = 2;
				}
			}else{
				this.decimal = 2;
			}	
			
			if(p.get("joinBy") != null){
				this.joinBy = p.get("joinBy").toString();
			}else{
				this.joinBy = "key";
			}
			 Iterator<Map.Entry<String,Object>> it = p.entrySet().iterator();
			   while (it.hasNext()) {
				Map.Entry<String,Object> entry = it.next();
			    String key = entry.getKey();
			    Object value = entry.getValue();
			    if(key.startsWith("param[") && key.endsWith("]")){
					String temp_key = StringUtils.replaceEach(key, new String[]{"param[","]"}, new String[]{"",""});
					if(temp_key.endsWith(BaseConstant.RP_PARAMETER_ARRAY_SUFFIX )){
						String[] array = StringUtils.split(String.valueOf(value),"|");
						if(array.length == 0) array = null;
						parameters_.put(temp_key, array);
					}else{
						parameters_.put(temp_key, value);
					}
				}else if(key.startsWith("mapper[") && key.endsWith("]")){
					String temp_key = StringUtils.replaceEach(key, new String[]{"mapper[","]"}, new String[]{"",""});
					mapper_.put(temp_key, value);
				}
			  }
		}
		
		/*转化行转列数据格式*/
		this.setRowsValue(putRowsColumn(p,"rowsValue"));;
		this.setRowsGroup(putRowsColumn(p,"rowsGroup"));;
		
		this.setQueryName(queryName_);
		this.setParameters(parameters_);
		this.setMapper(mapper_);
		this.setPage(isPage_);
	}
	
	/**
	 * 转化行转列字符格式
	 */
	private Map<String, String> putRowsColumn(Map<String, Object> p,String paramName){

		Map<String, String> rs = new LinkedHashMap<String, String>();
		
		if(p.get(paramName) != null){
			String temp_ = p.get(paramName).toString();;
			String[] opts = StringUtils.split(temp_, ";");
			for (int i = 0; i < opts.length; i++) {
				String v_ = opts[i];
				String[] o_ = StringUtils.split(v_, ":");
				switch (o_.length) {
				case 2:
					rs.put(o_[0], o_[1]);
					break;
				default:
					break;
				}			
			}
		} 
		return rs;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void setMapper(Map<String, Object> mapper) {
		this.mapper = mapper;
	}

	public void setDecimal(Integer decimal) {
		this.decimal = decimal;
	}

	public void setHideGroup(String[] hideGroup) {
		this.hideGroup = hideGroup;
	}

	public void setRowsGroup(Map<String, String> rowsGroup) {
		this.rowsGroup = rowsGroup;
	}

	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}

	public void setRowsValue(Map<String, String> rowsValue) {
		this.rowsValue = rowsValue;
	}

	public void setJoinBy(String joinBy) {
		this.joinBy = joinBy;
	}

	
	
	
}
