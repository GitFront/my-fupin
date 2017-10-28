package com.aspire.birp.modules.dataMapping.mapping.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableCatalog;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMapping;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmDatabaseColumn;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmDatabaseTable;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableCatalogVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableMappingVO;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;
import com.aspire.birp.modules.smartQuery.base.web.SqBaseController;

import net.sf.json.JSONArray;


/**
 * Title: 映射管理功能服务控制类 <br>
 * Description: 主要用于处理映射管理功能的请求信息 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年9月15日 下午14:45:03 <br>
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/dataMapping/mapping")
public class DmMappingController extends SqBaseController{

	private static Logger log = LoggerFactory.getLogger(DmMappingController.class);
	
	
	/**
	 * 智能映射配置功能的跳转
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/mapping-config.htm")
	public String forwardMappingConfig(@RequestParam(value="id",required=false) String id,@RequestParam(value="table") String tableName,Model model){
		
		Map<String,String> rs = getDmMappingService().queryWideTableInfo(id, tableName);
		
		/*定义参数返回值*/		
		model.addAttribute("id", rs.get("id"));
		model.addAttribute("table", rs.get("table"));
		model.addAttribute("comment", (StringUtils.isBlank(rs.get("comment")) || rs.get("comment") == "null")?"":rs.get("comment"));	
		model.addAttribute("tableType", rs.get("tableType"));
		model.addAttribute("isPerimssion", rs.get("isPerimssion"));
		model.addAttribute("status", rs.get("status"));
		model.addAttribute("cycType", rs.get("cycType"));
		model.addAttribute("storageType", rs.get("storageType"));
		
		log.info("映射配置功能页面跳转");
		return "/modules/data_mapping/mapping/mapping-config";
	}
	
	/**
	 * 智能查询目录绑定功能的跳转
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/binding-config.htm")
	public String forwardBindingConfig(@RequestParam(value="id") String id,Model model){
		
		DmTableCatalogVO rs = getDmMappingService().queryTableCatalogInfo(id);
	
		/*定义参数返回值*/	
		model.addAttribute("catalogId", rs.getId());
		model.addAttribute("catalogName", rs.getName());
		model.addAttribute("catalogFullPath", rs.getFullPath());	
		
		log.info("宽表映射的目录绑定功能页面跳转");
		return "/modules/data_mapping/mapping/binding-config";
	}
	
	/**
	 * 查询数据库表列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/dbtable/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmDatabaseTable> loadDatabaseTableList(String tableName){	

		List<DmDatabaseTable> list = new ArrayList<DmDatabaseTable>();
		try {
   			list = getDmMappingService().queryWideTableList(SmartQueryConstant.TABLE_MAPPING_ALLOW_PREFIX,tableName);
  		} catch (Exception e) {
			log.error("查询数据库表列表信息失败");
 		}		
		return list;
	}
	
	/**
	 * 查询数据库表的数据表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/dbcolumn/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmDatabaseColumn> loadDatabaseColumnList(HttpServletRequest request){	

		/*获取请求参数信息*/
		String id = request.getParameter("id");
		String table = request.getParameter("table");
		List<DmDatabaseColumn> list = new ArrayList<DmDatabaseColumn>();
		/*如果页面为未保存映射表信息，则不主动加载数据列数据*/
		if(StringUtils.isBlank(id)) return list;
		
		try {
   			list = getDmMappingService().queryDatabaseColumnList(table);
  		} catch (Exception e) {
			log.error("查询数据库表列表信息失败");
 		}		
		return list;
	}
	
	
	
	/**
	 * 查询已映射表的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablemapping/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmTableMappingVO> loadTableMappingList(String mappingName){	

		List<DmTableMappingVO> list = new ArrayList<DmTableMappingVO>();
		try {
   			list = getDmMappingService().queryTableMappingList(mappingName);
  		} catch (Exception e) {
			log.error("查询已映射表的列表信息失败");
 		}		
		return list;
	}
	
	/**
	 * 查询已映射维表的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablemapping/list/dim", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmTableMappingVO> loadDimMappingList(){	

		List<DmTableMappingVO> list = new ArrayList<DmTableMappingVO>();
		try {
   			list = getDmMappingService().queryDimMappingList();
  		} catch (Exception e) {
			log.error("查询已映射维表的列表信息失败");
 		}		
		return list;
	}
	
	
	/**
	 * 通过目录ID来查询已映射表的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablemapping/listbycatalog", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmTableMappingVO> loadTableMappingListByCatalog(@RequestParam(value="catalogId",required=false) String catalogId,String type){	

		List<DmTableMappingVO> list = new ArrayList<DmTableMappingVO>();
		try {
   			list = getDmMappingService().queryTableMappingListByCatalog(catalogId,type);
  		} catch (Exception e) {
  			e.printStackTrace();
			log.error("通过目录ID查询已映射表的列表信息失败");
 		}		
		return list;
	}
	
	
	/**
	 * 查询已映射表的数据列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/columnmapping/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmColumnMappingVO> loadColumnMappingList(HttpServletRequest request){	
		
		/*获取请求参数信息*/
		String mpTableId = request.getParameter("mpTableId");
		
		List<DmColumnMappingVO> list = new ArrayList<DmColumnMappingVO>();
		try {
   			list = getDmMappingService().queryColumnMappingList(mpTableId);
  		} catch (Exception e) {
			log.error("查询已映射表的数据列信息失败");
 		}		
		return list;
	}
	
	
	/**
	 * 查询尚未选择的映射数据列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/columnmapping/list/withoutselect", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmColumnMappingVO> loadColumnMappingListWithoutSelect(HttpServletRequest request){	
		
		/*获取请求参数信息*/
		String mpTableId = request.getParameter("mpTableId");
		String sqId = request.getParameter("sqId");
		String isTemp = request.getParameter("isTemp");
		
		List<DmColumnMappingVO> list = new ArrayList<DmColumnMappingVO>();
		try {
   			list = getDmMappingService().queryColumnMappingList(mpTableId,sqId,isTemp);
  		} catch (Exception e) {
			log.error("查询尚未选择的映射数据列信息");
 		}		
		return list;
	}
	
	
	/**
	 * 通过列ID查询名称信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/columnmapping/query/name", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String loadColumnMappingName(@RequestParam(value="id") String id){	
		String cname = "未知"; 
		try {
			cname = getDmMappingService().queryColumnMappingName(id);
  		} catch (Exception e) {
			log.error("通过列ID查询名称信息失败",e);
 		}		
		return cname;
	}
	
	/**
	 * 保存映射数据表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablemapping/save", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Map<String,String> addTableMapping(HttpServletRequest request,DmTableMapping dmTableMapping){	
		Map<String,String> rs = new HashMap<String, String>();
		try {
   			String id = getDmMappingService().saveTableMapping(dmTableMapping);
   			rs.put("id", id);
  		} catch (Exception e) {
			log.error("保存映射数据表信息失败",e);
 		}		
		return rs;
	}
	
	/**
	 * 更新映射数据表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablemapping/rename", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean renameTableMapping(HttpServletRequest request){	
		boolean flag = true;
		/*获取请求参数信息*/
		String id = request.getParameter("id");
		String mappingName = request.getParameter("mappingName");
		String isPerimssion = request.getParameter("isPerimssion");
		String cycType = request.getParameter("cycType");
		String tableType = request.getParameter("tableType");
		String storageType = request.getParameter("storageType");
		
		try {
			flag = getDmMappingService().updateTableMapping(id, mappingName,isPerimssion,cycType,tableType,storageType);
  		} catch (Exception e) {
			log.error("更新映射数据表信息失败");
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 映射数据表信息绑定目录
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablemapping/bind", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean bindingTableMapping(@RequestParam(value="catalogId",required=false) String catalogId,@RequestParam("tmids[]")String tmids,Boolean on){	
		boolean flag = true;
		try {
			flag = getDmMappingService().updateTableMappings(tmids, catalogId,on);
  		} catch (Exception e) {
  			e.printStackTrace();
			log.error("映射数据表绑定目录信息失败");
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 删除映射数据表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablemapping/remove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean removeTableMapping(@RequestParam("id") String id){	
		boolean flag = true;
		
		try {
			flag = getDmMappingService().removeTableMapping(id);
  		} catch (Exception e) {
			log.error("删除映射数据表信息失败",e);
			flag = false;
 		}		
		return flag;
	}
	
	
	/**
	 * 保存数据库映射列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/columnmapping/savelist", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	public @ResponseBody boolean saveColumnMappings(HttpServletRequest request){	
		boolean flag = true;
		/*获取请求参数信息*/
		String mpTableId = request.getParameter("mpTableId");
		String mpTableName = request.getParameter("mpTableName");
		String columnstr = request.getParameter("columns");
	
		/*JSON对象处理*/
		JSONArray jsonArray = JSONArray.fromObject(columnstr);
		@SuppressWarnings("unchecked")
		List<DmDatabaseColumn> columns = (List<DmDatabaseColumn>) JSONArray.toCollection(jsonArray, DmDatabaseColumn.class);
	
		try {
			flag = getDmMappingService().saveColumnMappings(mpTableId, mpTableName, columns);
  		} catch (Exception e) {
			log.error("保存数据库列映射列表失败",e);
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 保存数据库映射列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/columnmapping/update", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	public @ResponseBody boolean updateColumnMappings(HttpServletRequest request){	
		boolean flag = true;
		/*获取请求参数信息*/
		String id = request.getParameter("id");
		String mapping = request.getParameter("mapping");
		String columnType = request.getParameter("columnType");
		String isKeys = request.getParameter("isKeys");
		
		try {
			flag = getDmMappingService().updateColumnMappings(id, mapping, columnType,isKeys);
  		} catch (Exception e) {
			log.error("保存数据库列映射列表失败");
			flag = false;
 		}		
		return flag;
	}
	
	
	/**
	 * 删除数据映射列信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/columnmapping/removelist", produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean removeColumnMappings(@RequestParam("columnids[]") List<String> ids){	
		boolean flag = true;
		
		try {
			flag = getDmMappingService().removeColumnMappings(ids);
  		} catch (Exception e) {
			log.error("删除数据库列映射列表失败");
			flag = false;
 		}		
		return flag;
	}
	
	
	
	
	/*================================宽表目录管理===============================================*/
	
	
	/**
	 * 查询宽表目录信息的列表信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablecatalog/list", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<DmTableCatalogVO> loadTableCatalogList(String type){	

		List<DmTableCatalogVO> list = new ArrayList<DmTableCatalogVO>();
		try {
   			list = getDmMappingService().queryTableCatalogList(type);
  		} catch (Exception e) {
			log.error("查询宽表目录的列表信息失败");
 		}		
		return list;
	}
	
	
	/**
	 * 新增保存宽表目录信息
	 * @return 是否保存成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablecatalog/save", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean addTableCatalog(HttpServletRequest request){
		boolean flag = true;
		/*接收的目录保存信息*/
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String pId = request.getParameter("pId");
		String type = request.getParameter("type");
		String fullPath = new String(request.getParameter("fullPath"));
		try {
			flag = getDmMappingService().saveTableCatalog(id, name, pId, fullPath,type);
  		} catch (Exception e) {
			log.error("保存宽表目录信息失败");
			flag = false;
 		}
		return flag;
	}
	
	/**
	 * 重命名保存宽表目录信息
	 * @return 是否重命名成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablecatalog/rename", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean renameTableCatalog(HttpServletRequest request){
		boolean flag = true;
		/*接收的目录保存信息*/
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String fullPath = request.getParameter("fullPath");
		try {
			flag = getDmMappingService().updateTableCatalog(id, name,fullPath);
  		} catch (Exception e) {
			log.error("重命名宽表目录信息失败");
			flag = false;
 		}		
		return flag;
	}
	
	/**
	 * 判断是否可删除宽表目录信息
	 * @return 是否可删除宽表目录信息
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablecatalog/isremove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String isremoveTableCatalog(@RequestParam String id){
		try {
			boolean flag = getDmMappingService().isremoveTableCatalog(id);
			if(!flag) return null;
  		} catch (Exception e) {
			log.error("判断是否可删除宽表目录信息失败");
			return null;
 		}		
		return "true";
	}
	
	/**
	 * 删除宽表目录信息
	 * @return 是否删除成功
	 * @author 张健雄
	 */
	@RequestMapping(value = "/tablecatalog/remove", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean removeTableCatalog(@RequestParam String id){
		boolean flag = true;
		try {
			flag = getDmMappingService().removeTableCatalog(id);
  		} catch (Exception e) {
			log.error("删除宽表目录信息失败");
			flag = false;
			e.printStackTrace();
 		}		
		return flag;
	}
	
	
	@RequestMapping(value = "/tablecatalog/saveTheme", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Object[] saveTheme(HttpServletRequest request,DmTableCatalog dmTableCatalog){
		Boolean flag=false;
		Object[] results = new Object[]{flag,"保存失败"};
		try {
			results = getDmMappingService().saveTheme(dmTableCatalog);
  		} catch (Exception e) {
			log.error("保存主题失败");
			results = new Object[]{flag,"保存失败"};
 		}		
		return results;
		
		
	}
	
	
	@RequestMapping(value = "/tablecatalog/theme", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Map<String, Object>> selectTheme(HttpServletRequest request){
		return getDmMappingService().selectThemeByType();
	}
	
	
	
	
	
	
	
	
	
}
