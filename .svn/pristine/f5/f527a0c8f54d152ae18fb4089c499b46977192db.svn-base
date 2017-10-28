package com.aspire.birp.modules.smartQuery.query.web;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.modules.smartQuery.base.web.SqBaseController;
import com.aspire.birp.modules.smartQuery.query.vo.SqListFilterInfoVO;


/**
 * Title: 列表过滤器功能服务控制类 <br>
 * Description: 主要用于处理列表过滤器功能的请求信息 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2016年3月23日 下午14:45:03 <br>
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/smart-query/listFilter")
public class SqListFilterController extends SqBaseController{

	private static Logger log = LoggerFactory.getLogger(SqListFilterController.class);

	/**
	 * 列表过滤器配置功能的跳转
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/list-filter-config.htm")
	public String forwardQueryConfig(){
		
		log.info("列表过滤器配置功能的跳转");
		return "/modules/smart_query/query/list-filter";
	}
	
	/**
	 * 查询当前用户所上传的列表过滤器的列表信息
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/list/own", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqListFilterInfoVO> loadListFilter() {

		List<SqListFilterInfoVO> list = new ArrayList<SqListFilterInfoVO>();
		try {
			list = getSqListFilterService().queryOwnList();
		} catch (Exception e) {
			log.error("查询列表过滤器的列表信息失败", e);
		}
		return list;
	}
	
	/**
	 * 查询当前用户所上传的列表过滤器的列表信息
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/search/by/name", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<SqListFilterInfoVO> loadListFilterForName(@RequestParam(value="listInfo",required=false) String listInfo) {

		List<SqListFilterInfoVO> list = new ArrayList<SqListFilterInfoVO>();
		try {
			list = getSqListFilterService().queryOwnList(listInfo);
		} catch (Exception e) {
			log.error("查询列表过滤器的列表信息失败", e);
		}
		return list;
	}
	
	/**
	 * 根据ID查询的列表过滤器信息
	 * 
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/search/by/id", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	SqListFilterInfoVO loadListFilterForId(@RequestParam(value="id",required=true) String id) {

		SqListFilterInfoVO vo = new SqListFilterInfoVO();
		try {
			vo = getSqListFilterService().queryListFilter(id);
		} catch (Exception e) {
			log.error("根据ID查询的列表过滤器信息失败", e);
		}
		return vo;
	}
	
	/**
	 * 下载列表过滤器的原始文件
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/download", method=RequestMethod.POST , produces = "application/json;charset=UTF-8")
	public void downloadListFilter(@RequestParam String downloadId,HttpServletResponse response){

		try {
			/*获取文件路径*/
			SqListFilterInfoVO vo = getSqListFilterService().queryListFilter(downloadId);
			if(vo != null){
				String filePath = vo.getFilePath();
				String fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1);
				CSVUtils.exportFile(response, filePath, fileName);
			}			
  		} catch (Exception e) {
			log.error("下载列表过滤器的原始文件操作失败",e);
 		}		
	}
	
	/**
	 * 上传列表过滤器配置文件信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/save/upload", method=RequestMethod.POST , produces = "application/json;charset=UTF-8")
	public @ResponseBody void uploadList(@RequestParam(value="id",required=false) String id,@RequestParam("listName") String listName,
			@RequestParam(value="listSeparator",required=false) String listSeparator,@RequestParam(value="listFile",required=false) MultipartFile listFile,
			HttpServletResponse response){	
		boolean flag = true;
		try {
			flag = getSqListFilterService().uploadList(id,listName, listSeparator, listFile);
			Map<String,Object> retMap = new HashMap<String,Object>();
	        retMap.put("flag", flag);
	        PrintWriter out = response.getWriter();
	        out.write(net.sf.json.JSONObject.fromObject(retMap).toString());
	        out.flush();
	        out.close();
  		} catch (Exception e) {
			log.error("上传列表过滤器配置文件信息失败",e);
 		}
	}
	
	/**
	 * 删除列表过滤器配置文件信息
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/delete", method=RequestMethod.POST , produces = "application/json;charset=UTF-8")
	public @ResponseBody boolean deleteListFilter(@RequestParam("ids[]") List<String> ids){	
		boolean flag = true;
		try {
			flag = getSqListFilterService().deleteListFilter(ids);
  		} catch (Exception e) {
			log.error("删除列表过滤器配置文件信息失败",e);
			flag = false;
 		}
		return flag;
	}
	
	
}
