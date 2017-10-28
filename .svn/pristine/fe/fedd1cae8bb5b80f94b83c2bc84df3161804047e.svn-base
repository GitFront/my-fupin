package com.aspire.birp.modules.smartQuery.query.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bi.common.constant.DateConstant;
import com.aspire.bi.common.util.DateUtils;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.common.vo.GroupDataVo;
import com.aspire.birp.modules.common.vo.PieDataVo;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.constant.TackCycType;
import com.aspire.birp.modules.smartQuery.base.entity.DimFilterFormula;
import com.aspire.birp.modules.smartQuery.base.entity.DimFilterRalation;
import com.aspire.birp.modules.smartQuery.base.entity.DimSqSort;
import com.aspire.birp.modules.smartQuery.base.vo.ColumnType;
import com.aspire.birp.modules.smartQuery.base.vo.CombotreeObject;
import com.aspire.birp.modules.smartQuery.base.vo.FilterType;
import com.aspire.birp.modules.smartQuery.base.vo.SqDataGridObject;
import com.aspire.birp.modules.smartQuery.base.web.SqBaseController;
import com.aspire.birp.modules.smartQuery.market.vo.SqPersonalCatalogVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqColumnMappingTree;
import com.aspire.birp.modules.smartQuery.query.vo.SqFilterInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryData;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSelectInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqSortInfoVO;
import com.aspire.birp.modules.smartQuery.query.vo.SqTableMappingTree;

import net.sf.json.JSONObject;


/**
 * Title: 智能查询功能服务控制类 <br>
 * Description: 主要用于处理智能查询功能的请求信息 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年9月22日 下午14:45:03 <br>
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/smart-query/query")
public class SqQueryController extends SqBaseController{

	private static Logger log = LoggerFactory.getLogger(SqQueryController.class);
	/**
	 * 临时文件信息储存信息
	 */
	private static Map<String,String> fileMap;
	
	public SqQueryController() {
		super();
		if(fileMap == null){
			fileMap = new HashMap<String, String>();
		}
	}

	public static void clearFileMap() {
		log.info("清空临时文件储存信息");
		SqQueryController.fileMap.clear();
	}



	/**
	 * 智能查询配置功能的跳转
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/query-config.htm")
	public String forwardQueryConfig(@RequestParam(required=false,value="id") String id,
			@RequestParam(required=false,value="tableids") String tableids,Model model){
		SqQueryInfoVO query;
		if(StringUtils.isNotBlank(id)){
			/*如果存在ID值，则查询数据库的查询对象信息*/
			query = getSqQueryService().queryQueryConfigById(id);
			
		}else{
			/*新建查询则保存一个临时查询对象*/
			query = getSqQueryService().saveSqQueryInfoTemp(tableids);
		}		
		JSONObject queryinfo = JSONObject.fromObject(query);
		/*定义参数返回值*/		
		model.addAttribute("query", queryinfo.toString());
		
		log.info("智能查询功能页面跳转");
		return "/modules/smart_query/query/query-config";
	}
	
	/**
	 * 查询已配置的映射表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/querytree/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqTableMappingTree> loadTableMappingTree(String id){	

		List<SqTableMappingTree> list = new ArrayList<SqTableMappingTree>();
		try {
   			list = getSqQueryService().queryTableMappingTree(id);
  		} catch (Exception e) {
			log.error("查询宽表目录树失败",e);
 		}		
		return list;
	}
	
	/**
	 * 查询已配置的映射字段信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/querytree/column/choice", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqColumnMappingTree> loadColumnMappingTreeForChoice(){	

		List<SqColumnMappingTree> list = new ArrayList<SqColumnMappingTree>();
		try {
   			list = getSqQueryService().queryColumnMappingTree(true);
  		} catch (Exception e) {
			log.error("查询宽表字段目录树失败",e);
 		}		
		return list;
	}
	
	/**
	 * 查询映射表集合的可用数据列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/columns/select", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmColumnMappingVO> loadColumnListByTables(@RequestParam(required=true,value="tableids") String tableids,
			@RequestParam(required=false,value="type") String type){	
		
		List<DmColumnMappingVO> list = new ArrayList<DmColumnMappingVO>();
		try {
   			list = getSqQueryService().queryColumnListByTables(tableids,type);
  		} catch (Exception e) {
			log.error("查询映射表集合的可用数据列信息失败");
 		}		
		return list;
	}
	
	/**
	 * 查询尚未选择的映射数据列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqselect/list/withoutselect", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmColumnMappingVO> loadColumnMappingListWithoutSelect(HttpServletRequest request){	
		
		/*获取请求参数信息*/
		String sqId = request.getParameter("sqId");
		String isTemp = request.getParameter("isTemp");
		
		List<DmColumnMappingVO> list = new ArrayList<DmColumnMappingVO>();
		try {
   			list = getSqQueryService().queryUnselectColumnList(sqId, isTemp);
  		} catch (Exception e) {
			log.error("查询尚未选择的映射数据列信息");
 		}		
		return list;
	}
	
	/**
	 * 查询已选择的映射数据列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqselect/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqSelectInfoVO> loadSqSelectInfoList(HttpServletRequest request){	
		
		/*获取请求参数信息*/
		String sqId = request.getParameter("sqId");
		String isTemp = request.getParameter("isTemp");
		
		List<SqSelectInfoVO> list = new ArrayList<SqSelectInfoVO>();
		try {
   			list = getSqQueryService().querySqSelectInfoList(sqId, isTemp);
  		} catch (Exception e) {
			log.error("查询已选择的映射数据列信息");
 		}		
		return list;
	}
	
	
	/**
	 * 批量保存已选择显示列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/queryselect/savelist", produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean saveSqSelectInfo(@RequestParam("sqId") String sqId,
			@RequestParam("columnids[]") List<String> ids,@RequestParam("isTemp") String isTemp){	
		boolean flag = true;
		
		try {
			flag = getSqQueryService().saveSqSelectInfo(sqId,isTemp, ids);
  		} catch (Exception e) {
			log.error("批量保存已选择显示列信息失败",e);
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 批量删除已选择显示列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/queryselect/removelist", produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean removeSqSelectInfo(@RequestParam("sqId") String sqId,
			@RequestParam("columnids[]") List<String> ids,@RequestParam("isTemp") String isTemp){	
		boolean flag = true;
		
		try {
			flag = getSqQueryService().removeSqSelectInfo(sqId,isTemp, ids);
  		} catch (Exception e) {
			log.error("批量删除已选择显示列信息失败",e);
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 通过列ID查询过滤条件关系的名称信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/dimension/filterralation/name", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadDimFilterRalationName(@RequestParam(value="id") String id){	
		String cname = "未知"; 
		try {
			cname = getSqQueryService().queryRalationName(id);
  		} catch (Exception e) {
			log.error("通过列ID查询过滤条件关系的名称信息失败",e);
 		}		
		return cname;
	}
	
	/**
	 * 查询过滤条件关系的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/dimension/filterralation/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DimFilterRalation> loadDimFilterRalation(){	
		List<DimFilterRalation> list = new ArrayList<DimFilterRalation>();
		try {
			list = getSqQueryService().queryFilerRalation();
  		} catch (Exception e) {
			log.error("查询过滤条件关系的列表失败");
 		}		
		return list;
	}
	
	/**
	 * 通过列ID查询过滤条件匹配公式的名称信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/dimension/filterformula/name", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadDimFilterFormulaName(@RequestParam(value="id") String id){	
		String cname = "未知"; 
		try {
			cname = getSqQueryService().queryFormulaName(id);
  		} catch (Exception e) {
			log.error("通过列ID查询过滤条件匹配公式的名称信息失败",e);
 		}		
		return cname;
	}
	
	/**
	 * 查询过滤条件关系的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/dimension/filterformula/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DimFilterFormula> loadDimFilterFormula(@RequestParam(value="type",required=false) String type){	
		List<DimFilterFormula> list = new ArrayList<DimFilterFormula>();
		try {
			list = getSqQueryService().queryFilerFormula(type);
  		} catch (Exception e) {
			log.error("查询过滤条件关系的列表失败");
 		}		
		return list;
	}
	
	
	/**
	 * 通过列ID查询排序方式的名称信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/dimension/sorttype/name", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadDimSqSortName(@RequestParam(value="id") String id){	
		String cname = "未知"; 
		try {
			cname = getSqQueryService().querySortTypeName(id);
  		} catch (Exception e) {
			log.error("通过列ID查询排序方式的名称信息失败",e);
 		}		
		return cname;
	}
	
	/**
	 * 查询排序方式的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/dimension/sorttype/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DimSqSort> loadDimSqSort(){	
		List<DimSqSort> list = new ArrayList<DimSqSort>();
		try {
			list = getSqQueryService().querySortType();
  		} catch (Exception e) {
			log.error("查询排序方式的列表失败");
 		}		
		return list;
	}
	
	/**
	 * 查询过滤条件列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqfilter/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqFilterInfoVO> loadSqFilterList(@RequestParam(value="sqId") String sqId,@RequestParam(value="isTemp") String isTemp){	

		List<SqFilterInfoVO> list = new ArrayList<SqFilterInfoVO>();
		try {
   			list = getSqQueryService().querySqFilterInfoList(sqId, isTemp);
  		} catch (Exception e) {
			log.error("查询过滤条件列表信息失败",e);
 		}		
		return list;
	}
	
	/**
	 * 通过ID查询过滤条件信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqfilter/find", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	SqFilterInfoVO findSqFilterById(@RequestParam(value="id") String id,@RequestParam(value="isTemp") String isTemp){	

		SqFilterInfoVO vo = new SqFilterInfoVO();
		try {
			vo = getSqQueryService().querySqFilterInfoById(id, isTemp);
  		} catch (Exception e) {
			log.error("通过ID查询过滤条件信息操作失败",e);
 		}		
		return vo;
	}
	
	/**
	 * 删除过滤条件信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqfilter/remove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean removeSqFilter(@RequestParam(value="id") String id,@RequestParam(value="isTemp") String isTemp){	

		boolean flag = true;
		try {
			flag = getSqQueryService().removeSqFilterInfo(id, isTemp);
  		} catch (Exception e) {
  			flag = false;
			log.error("删除过滤条件信息失败",e);
 		}		
		return flag;
	}
	
	/**
	 * 保存或更新过滤条件列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqfilter/saveorupdate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void saveOrUpdateSqFilter(@RequestParam Map<String, Object> params,Model model,HttpServletResponse response){	
		
		boolean flag = true;
		SqFilterInfoVO filter = this.packageFilterInfo(params);
		String isTemp = params.get("isTemp").toString().trim();
		
		try {
			flag = getSqQueryService().saveOrUpdateSqFilterInfo(filter,isTemp);			
			Map<String,Object> retMap = new HashMap<String,Object>();
	        retMap.put("flag", flag);
	        PrintWriter out = response.getWriter();
	        out.write(net.sf.json.JSONObject.fromObject(retMap).toString());
	        //此处如果返回一个List，需要用net.sf.json.JSONArray.fromObject().toString()
	        out.flush();
	        out.close();
  		} catch (Exception e) {
			log.error("保存或更新过滤条件列表信息失败",e);
 		}
	}
	
	/**
	 * 查询排序方式列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqsort/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqSortInfoVO> loadSqSortList(@RequestParam(value="sqId") String sqId,@RequestParam(value="isTemp") String isTemp){	

		List<SqSortInfoVO> list = new ArrayList<SqSortInfoVO>();
		try {
   			list = getSqQueryService().querySqSortInfoList(sqId, isTemp);
  		} catch (Exception e) {
			log.error("查询排序方式列表信息失败",e);
 		}		
		return list;
	}
	
	/**
	 * 删除排序方式信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqsort/remove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean removeSqSort(@RequestParam(value="id") String id,@RequestParam(value="isTemp") String isTemp){	

		boolean flag = true;
		try {
			flag = getSqQueryService().removeSqSortInfo(id, isTemp);
  		} catch (Exception e) {
  			flag = false;
			log.error("删除排序方式信息失败",e);
 		}		
		return flag;
	}
	
	/**
	 * 保存或更新排序方式列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqsort/saveorupdate", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean saveOrUpdateSqSort(HttpServletRequest request){	
		
		boolean flag = true;
		
		/*获取请求参数信息*/
		String sqId = request.getParameter("sqId");
		String isTemp = request.getParameter("isTemp");
		String sroter = request.getParameter("sroter");
	
		/*JSON对象处理*/
		JSONObject json = JSONObject.fromObject(sroter);
		SqSortInfoVO vo = (SqSortInfoVO) JSONObject.toBean(json, SqSortInfoVO.class);
		
		try {
			flag = getSqQueryService().saveOrUpdateSqSortInfo(sqId, isTemp, vo);
  		} catch (Exception e) {
  			flag = false;
			log.error("保存或更新排序方式列表信息失败",e);
 		}		
		return flag;
	}
	
	/**
	 * 验证查询信息有效性
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/smartquery/valid", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String validConfig(@RequestParam(value="id") String id,@RequestParam(value="isTemp") String isTemp){	
		
		boolean flag = true;
		
		try {
			flag = getSqQueryService().smartQueryValid(id, isTemp);
  		} catch (Exception e) {
  			flag = false;
			log.error("验证查询信息失败");
 		}
		if(!flag) return null;
		return "true";
	}
	
	/**
	 * 通过查询配置信息，初始化查询信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/smartquery/init", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqDataGridObject> initDataList(@RequestParam(value="id") String id,@RequestParam(value="isTemp") String isTemp){	
		
		List<SqDataGridObject> rs = new ArrayList<SqDataGridObject>();
		
		try {
			/*查询列定义信息*/
			rs = getSqQueryService().queryDataColumns(id, isTemp);
			/*生成动态SQL并保存查询对象*/
			boolean sf = getSqQueryService().updateSmartQuerySql(id, isTemp);
			if(!sf)
				throw new RuntimeException("查询SQL保存不成功，初始化查询信息失败！");
  		} catch (Exception e) {
			log.error("初始化查询信息失败！",e);
 		}		
		return rs;
	}
	
	/**
	 * 通过查询配置信息查询数据列表
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/smartquery/datalist", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Map<String, Object> queryDataList(@RequestParam Map<String, Object> params){	
		
		Map<String, Object> rs = new HashMap<String, Object>();
		
		String sqId = params.get("id").toString();
		String isTemp = params.get("isTemp").toString();
		Integer page =  Integer.parseInt(params.get("page").toString().trim());
		Integer rows = Integer.parseInt(params.get("rows").toString().trim());
		
		try {
			rs = getSqQueryService().querySqlDatabySqId(sqId, isTemp, page, rows);
  		} catch (Exception e) {
			log.error("智能查询数据列表失败！",e);
 		}		
		return rs;
	}
	

	
	/**
	 * 文件导出前，通过查询配置信息，生成临时文件
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/smartquery/export/temp", produces = "application/json;charset=UTF-8")
	public @ResponseBody
		Map<String, Object>  createTempData(@RequestParam String sqId,@RequestParam String isTemp){	

		boolean flag = true;
		String tempId = UUID.randomUUID().toString();
		
		try {
			/*生成临时数据*/
			Map<String,String> info = getSqQueryService().saveTempDatabySqId(sqId, isTemp);
			String filePath = info.get("path");
			String fileName = info.get("name");
			if(StringUtils.isBlank(filePath) || StringUtils.isBlank(fileName)){
				flag = false;
			}else{
				fileMap.put(tempId, fileName + "|" + filePath);
				flag = true;
			}
  		} catch (Exception e) {
			log.error("生成临时csv文件失败",e);
			flag = false;
 		}
		
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("flag", flag);
		rs.put("id", tempId);
		
		return rs;
	}
	
	/**
	 * 通过查询配置信息，导出数据列表
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/smartquery/export", produces = "application/json;charset=UTF-8")
	public void dataExport(@RequestParam String fileId,HttpServletResponse response){

		try {
			/*获取文件路径*/
			String info = fileMap.get(fileId);
			if(StringUtils.isBlank(info)) 
				throw new RuntimeException("找不到临时文件生成路径，数据导出前请先调用/smartquery/export/temp生成临时文件！");
			String[] infos = StringUtils.split(info, "|");
			String fileName = infos[0];
			String filePath = infos[1];
			
			CSVUtils.exportFile(response, filePath, fileName);
			
  		} catch (Exception e) {
			log.error("excel数据导出失败",e);
 		}		
	}
	
	/**
	 * 保存或更新智能查询信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/config/saveorupdate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void saveOrUpdateConfig(@RequestParam Map<String, Object> params,Model model,HttpServletResponse response){
		boolean flag = true;
		SqQueryInfoVO query = this.packageQueryInfo(params);
		try {
			flag = getSqQueryService().saveOrUpdateQuery(query);
			if(flag){
				/*定义参数返回值*/		
				model.addAttribute("sqId", query.getId());
				model.addAttribute("sqName", query.getName());
				model.addAttribute("mpTableId", query.getMpTableId());	
				model.addAttribute("sqTable", query.getSqTable());
				model.addAttribute("isTemp", SmartQueryConstant.COMMON_FLAG_FALSE);
			}
			
			Map<String,Object> retMap = new HashMap<String,Object>();
	        retMap.put("flag", flag);
	        PrintWriter out = response.getWriter();
	        out.write(net.sf.json.JSONObject.fromObject(retMap).toString());
	        //此处如果返回一个List，需要用net.sf.json.JSONArray.fromObject().toString()
	        out.flush();
	        out.close();
			
  		} catch (Exception e) {
			log.error("保存或更新智能查询信息失败",e);
 		}
	}
	
	/**
	 * 删除智能查询信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/config/remove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean removeConfig(@RequestParam String id){
		boolean flag = true;
		try {
			flag = getSqQueryService().deleteQuery(id);
  		} catch (Exception e) {
  			flag = false;
			log.error("删除智能查询信息失败",e);
 		}		
		return flag;
	}
	
	/**
	 * 查询分页配置信息列表
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/config/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Map<String, Object> queryConfigList(@RequestParam Map<String, Object> params){	
		
		Map<String, Object> rs = new HashMap<String, Object>();
		
		Integer page =  Integer.parseInt(params.get("page").toString().trim());
		Integer rows = Integer.parseInt(params.get("rows").toString().trim());
		
		try {
			rs = getSqQueryService().querySqQueryInfoList(page, rows);
  		} catch (Exception e) {
			log.error("查询分页查询配置信息列表失败！",e);
 		}		
		return rs;
	}
	
	/**
	 * 查询全部配置信息列表
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/config/listall", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqQueryInfoVO> queryConfigListall(){	
		List<SqQueryInfoVO> vos = new ArrayList<SqQueryInfoVO>();
		try {
			vos = getSqQueryService().querySqQueryInfoList();
  		} catch (Exception e) {
			log.error("查询全部查询配置信息列表失败！",e);
 		}		
		return vos;
	}
	
	/**
	 * 查询全部自动任务配置信息列表
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/config/listauto", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqQueryInfoVO> queryConfigListAuto(){	
		List<SqQueryInfoVO> vos = new ArrayList<SqQueryInfoVO>();
		try {
			vos = getSqQueryService().querySqQueryAutoList();
  		} catch (Exception e) {
			log.error("查询自动任务配置信息列表失败！",e);
 		}		
		return vos;
	}
	
	/**
	 * 验证查询名称信息有效性
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/smartquery/valid/name", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String validConfigName(@RequestParam(value="id") String id,@RequestParam(value="name") String name){	
		
		boolean flag = true;
		
		try {
			flag = getSqQueryService().validQueryName(id, name);
  		} catch (Exception e) {
  			flag = false;
			log.error("验证查询名称信息失败");
 		}
		if(!flag) return null;
		return "true";
	}
	
	/**
	 * 查询个人目录信息的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/personalcatalog/list/ztree", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqPersonalCatalogVO> loadPersonalCatalogList(){	

		List<SqPersonalCatalogVO> list = new ArrayList<SqPersonalCatalogVO>();
		try {
   			list = getSqDataMarketService().queryPersonalCatalogList();
  		} catch (Exception e) {
			log.error("查询个人目录的编辑列表信息失败");
 		}		
		return list;
	}
	
	/**
	 * 查询个人目录信息的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/personalcatalog/list/combotree", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<CombotreeObject> loadPersonalCatalogListByCombotree(){	

		List<CombotreeObject> list = new ArrayList<CombotreeObject>();
		try {
   			list = getSqDataMarketService().queryPersonalCatalogListByCombotree();
  		} catch (Exception e) {
			log.error("查询个人目录的列表信息失败",e);
 		}		
		return list;
	}
	
	
	/**
	 * 新增保存个人目录信息
	 * @return 是否保存成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/personalcatalog/save", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean addPersonalCatalog(HttpServletRequest request){
		boolean flag = true;
		/*接收的目录保存信息*/
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String pId = request.getParameter("pId");
		String fullPath = new String(request.getParameter("fullPath"));
		String fullPathId = new String(request.getParameter("fullPathId"));
		try {
			flag = getSqDataMarketService().savePersonalCatalog(id, name, pId,fullPathId, fullPath);
  		} catch (Exception e) {
			log.error("保存个人目录信息失败");
			flag = false;
 		}
		return flag;
	}
	
	/**
	 * 重命名保存个人目录信息
	 * @return 是否重命名成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/personalcatalog/rename", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean renamePersonalCatalog(HttpServletRequest request){
		boolean flag = true;
		/*接收的目录保存信息*/
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String fullPath = request.getParameter("fullPath");
		try {
			flag = getSqDataMarketService().updatePersonalCatalog(id, name,fullPath);
  		} catch (Exception e) {
			log.error("重命名个人目录信息失败");
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 判断是否可删除个人目录信息
	 * @return 是否可删除个人目录信息
	 * @author 张健雄
	 */
	@RequestMapping(value = "/personalcatalog/isremove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String isremovePersonalCatalog(@RequestParam String id){
		try {
			boolean flag = getSqDataMarketService().isremovePersonalCatalog(id);
			if(!flag) return null;
  		} catch (Exception e) {
			log.error("判断是否可删除个人目录信息失败");
			return null;
 		}		
		return "true";
	}
	
	
	
	/**
	 * 删除个人目录信息
	 * @return 是否删除成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/personalcatalog/remove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean removePersonalCatalog(@RequestParam String id){
		boolean flag = true;
		try {
			flag = getSqDataMarketService().removePersonalCatalog(id);
  		} catch (Exception e) {
			log.error("删除个人目录信息失败");
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 查询分享用户列表
	 * @param ids 已分享的用户列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/share/userlist", produces = "application/json;charset=UTF-8")
	public @ResponseBody Map<String, Object> queryShareUsers(@RequestParam Map<String, Object> params){	
		String userids = params.get("userids").toString().trim();
		String userinfo = params.get("userinfo").toString().trim();
		Integer page =  Integer.parseInt(params.get("page").toString().trim());
 		Integer rows = Integer.parseInt(params.get("rows").toString().trim());
 		
 		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = getSqDataMarketService().queryShareUserList(userids,userinfo,page,rows);
  		} catch (Exception e) {
			log.error("查询分享用户列表信息失败",e);
 		}		
		return data;
	}
	
	/**
	 * 保存并分享数据文件
	 * @return
	 * @author 张健雄
	 * @throws IOException 
	 */
	@RequestMapping(value = "/queryfile/save", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void saveDataFile(@RequestParam Map<String, Object> params,HttpServletResponse response) throws IOException{	
		boolean flag = true;
		String sqId = params.get("id").toString().trim();
		String isTemp = params.get("isTemp").toString().trim();	
		String fileName = "";
		String catalogId = "";
		List<String> userids = null;
		/*后台任务数据导出*/
		if("task".equalsIgnoreCase(params.get("saveType").toString().trim())){
			fileName = params.get("taskFileName").toString().trim();
			catalogId = params.get("personalCatalogId3").toString().trim();
		}else{
			fileName = params.get("savefileName").toString().trim();
			catalogId = params.get("personalCatalogId2").toString().trim();
			String ids = params.get("userids").toString().trim();
			userids = com.aspire.bi.common.util.StringUtils.getIdList(ids);
		}
		
		try {
			flag = getSqDataMarketService().saveDataFile(sqId, isTemp, fileName, catalogId, userids);
			
  		} catch (Exception e) {
			log.error("保存并分享数据文件失败",e);
			flag = false;
 		}
		
		Map<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("flag", flag);
        PrintWriter out = response.getWriter();
        out.write(net.sf.json.JSONObject.fromObject(retMap).toString());
        //此处如果返回一个List，需要用net.sf.json.JSONArray.fromObject().toString()
        out.flush();
        out.close();
	}
	
	
	/**
	 * 封装传入的查询对象信息属性
	 * @param params
	 * @return
	 * @author 张健雄
	 */
	private SqQueryInfoVO packageQueryInfo(Map<String, Object> params){
		SqQueryInfoVO vo = new SqQueryInfoVO();
		String id = params.get("id").toString().trim();
		String name = params.get("name").toString().trim();
		String configType = params.get("configType").toString().trim();
		String mpTableId = params.get("mpTableId").toString().trim();
		String sqTable = params.get("sqTable").toString().trim();
		String isTemp = params.get("isTemp").toString().trim();		
		
		vo.setId(id);
		vo.setName(name);
		vo.setConfigType(configType);
		vo.setMpTableId(mpTableId);
		vo.setSqTable(sqTable);
		vo.setIsTemp(isTemp);		
		
		if(SmartQueryConstant.QUERY_TASK_TYPE_AUTO.equals(configType)){
			
			String fileName = params.get("fileName").toString().trim();
			int dataStoreDate = Integer.parseInt(params.get("dataStoreDate").toString().trim());
			String personalCatalogId = params.get("personalCatalogId").toString().trim();
			String validDate = params.get("validDate").toString().trim();
			String invalidDate = params.get("invalidDate").toString().trim();
			String cycType = params.get("cycType").toString().trim();
			int cycLen = Integer.parseInt(params.get("cycLen").toString().trim());
			
			vo.setFileName(fileName);
			vo.setDataStoreDate(dataStoreDate);
			vo.setPersonalCatalogId(personalCatalogId);
			vo.setValidDate(validDate);
			vo.setInvalidDate(invalidDate);
			vo.setCycType(cycType);
			vo.setCycLen(cycLen);			
		}else{
			vo.setFileName(name);
			vo.setDataStoreDate(30);
			vo.setPersonalCatalogId("");
			vo.setValidDate(DateUtils.dateTimeToString(new Date(), DateConstant.YEAR_MOUTH_DAY));
			vo.setInvalidDate("");
			vo.setCycType(TackCycType.m.toString());
			vo.setCycLen(1);
		}
		
		return vo;
	}
	
	/**
	 * 封装传入的查询条件对象信息属性
	 * @param params
	 * @return
	 * @author 张健雄
	 */
	private SqFilterInfoVO packageFilterInfo(Map<String, Object> params){
		
		SqFilterInfoVO vo = new SqFilterInfoVO();
		String id = params.get("id").toString().trim();
		String sqId = params.get("sqId").toString().trim();
		String filterType = params.get("filterType").toString().trim();
		String relationId = params.get("relationId").toString().trim();
		String mpColumnId = params.get("mpColumnId").toString().trim();
		String sqColumnType = params.get("sqColumnType").toString().trim();		
		String formulaId = params.get("formulaId").toString().trim();		
		String monthCycType = params.get("monthCycType").toString().trim();
		Integer monthCycValue = Integer.parseInt(params.get("monthCycValue").toString().trim());
		String dateCycType = params.get("dateCycType").toString().trim();
		Integer dateCycValue = Integer.parseInt(params.get("dateCycValue").toString().trim());
		
		Long sort = Long.parseLong(params.get("sort").toString().trim());		
		/*转换条件数据*/
		String value = "";
		if(FilterType.list.toString().equals(filterType)){
			value = params.get("lvalue").toString().trim();
		}else{
			if(ColumnType.d.toString().equals(sqColumnType)){
				value = params.get("dvalue").toString().trim();
			}else if(ColumnType.n.toString().equals(sqColumnType)){
				value = params.get("nvalue").toString().trim();
			}else{
				value = params.get("svalue").toString().trim();
			}
		}
		
		vo.setId(id);
		vo.setFilterType(filterType);
		vo.setFormulaId(formulaId);;
		vo.setMpColumnId(mpColumnId);
		vo.setSqColumnType(sqColumnType);
		vo.setRelationId(relationId);
		vo.setValue(value);
		vo.setMonthCycType(monthCycType);
		vo.setMonthCycValue(monthCycValue);
		vo.setDateCycType(dateCycType);
		vo.setDateCycValue(dateCycValue);
		vo.setSqId(sqId);
		vo.setSort(sort);		
		
		return vo;
	}
	
	/**
	 * 查询已选择的数据列适用于维度的列
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqselect/report/dim", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmColumnMappingVO> loadReportDimension(HttpServletRequest request){	
		return this.queryReportSelect(request, SmartQueryConstant.SQ_REPORT_DIMENSION);
	}
	
	/**
	 * 查询已选择的数据列适用于数值的列
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqselect/report/amount", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmColumnMappingVO> loadReportAmount(HttpServletRequest request){	
		return this.queryReportSelect(request, SmartQueryConstant.SQ_REPORT_AMOUNT);
	}
	
	/**
	 * 查询已选择的数据列适用于分组的列
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/sqselect/report/group", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmColumnMappingVO> loadReportGroup(HttpServletRequest request){	
		return this.queryReportSelect(request, SmartQueryConstant.SQ_REPORT_GROUP);		
	}
	
	/**
	 * 查询报表统计列信息
	 * @param request
	 * @param types
	 * @return
	 * @author 张健雄
	 */
	private List<DmColumnMappingVO> queryReportSelect(HttpServletRequest request,String[] types){
		/*获取请求参数信息*/
		String sqId = request.getParameter("sqId");
		String isTemp = request.getParameter("isTemp");
		
		List<DmColumnMappingVO> list = new ArrayList<DmColumnMappingVO>();
		try {
   			list = getSqQueryService().querySqSelectInfoListByType(sqId, isTemp, types);
  		} catch (Exception e) {
			log.error("查询报表查询统计列信息失败");
 		}		
		return list;
	}
	
	/**
	 * 查询分组的图表统计数据
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/report/statistic/group", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<GroupDataVo> statisticReportByGroup(HttpServletRequest request){	
		/*获取请求参数信息*/
		String sqId = request.getParameter("sqId");
		String isTemp = request.getParameter("isTemp");
		String name = request.getParameter("name");
		String group = request.getParameter("group");
		String value = request.getParameter("value");
		
		List<GroupDataVo> list = new ArrayList<GroupDataVo>();
		try {
   			list = getSqQueryService().queryGroupReport(sqId, isTemp, name, value, group);
  		} catch (Exception e) {
			log.error("查询分组的图表统计数据失败");
 		}		
		return list;
	}
	
	/**
	 * 查询饼状图表统计数据
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/report/statistic/pie", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<PieDataVo> statisticReportByPie(HttpServletRequest request){	
		/*获取请求参数信息*/
		String sqId = request.getParameter("sqId");
		String isTemp = request.getParameter("isTemp");
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		
		List<PieDataVo> list = new ArrayList<PieDataVo>();
		try {
   			list = getSqQueryService().queryPieReport(sqId, isTemp, name, value);
  		} catch (Exception e) {
			log.error("查询饼状图表统计数据失败");
 		}		
		return list;
	}
	
	/**
	 * 判断数据表之间是否存在关联关系 
	 * @return 
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tables/isAssociation", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Map<String,Object> isAssociation(@RequestParam String tableids){
		Map<String,Object> rs = new HashMap<String, Object>();
		try {
			rs = getSqQueryService().isAssociation(tableids);
  		} catch (Exception e) {
			log.error("判断数据表之间是否存在关联失败");
			rs.put("flag", false);
 		}		
		return rs;
	}
	
	
	
	
	@RequestMapping(value = "/queryData", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,Object> queryData(@RequestBody SqQueryData sqQueryData){
		Map<String,Object> rs = new HashMap<String, Object>();
		try {
			rs=getSqQueryService().queryData(sqQueryData.getSearchKey(), sqQueryData.getSelecters(), sqQueryData.getFilters()
					, sqQueryData.getPage(), sqQueryData.getRows());
  		} catch (Exception e) {
			log.error("智能查询失败");
			rs.put("flag", false);
			e.printStackTrace();
 		}		
		return rs;
	}
	
	
	/**
     * 查询匹配字符列表
     * @param id
     * @return
     */
    @RequestMapping("queryColumList.do")
    @ResponseBody
    public List<Map<String, Object>> queryColumList(String columsId) {
    	try {
			
    		List<Map<String, Object>>datas=new ArrayList<Map<String, Object>>();
    		Map<String, Object> data=null;
    		data=new HashMap<String, Object>();
    		data.put("AREA_CD","HY02Z1");
    		data.put("AREA_NAM","龙川未知");
    		datas.add(data);
    		data=new HashMap<String, Object>();
    		data.put("AREA_CD","HY033");
    		data.put("AREA_NAM","忠信区域");
    		datas.add(data);
    		data=new HashMap<String, Object>();
    		data.put("AREA_CD","HY065");
    		data.put("AREA_NAM","蓝口区域");
    		datas.add(data);
    		return datas;
    		//return this.getSqQueryService().queryColumList(columsId);
    		
		} catch (Exception e) {
		}
    	return null;
    }
   
    /**
	 * 查询数据表的可用历史周期
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/period/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Map<String,String>> loadPeriodList(String tableName){	

		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
   			list = this.getSqQueryService().queryPeriodList(tableName);
  		} catch (Exception e) {
			log.error("查询数据表的可用历史周期操作失败",e);
 		}		
		return list;
	}
}
