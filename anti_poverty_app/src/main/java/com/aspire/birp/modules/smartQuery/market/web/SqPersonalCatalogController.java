package com.aspire.birp.modules.smartQuery.market.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalogShare;
import com.aspire.birp.modules.smartQuery.market.service.SqPersonalCatalogService;

/**
 * 目录分享
 * Title: <br>
 * Description: <br>
 * Copyright: aspire Copyright (C) 2009<br>
 * @author <a href="mailto:zr.wuganqin@aspirecn.com">吴淦钦</a><br>
 * @version 1.0<br>
 * @e-mail: zr.wuganqin@aspirecn.com<br>
 * @creatdate 2015年11月17日 上午10:31:42<br>
 */
@Controller
@RequestMapping(value = "${adminPath}/smart-query/catalog/")
public class SqPersonalCatalogController {

    private static Logger log = LoggerFactory.getLogger(SqPersonalCatalogController.class);
    
    private static final String BASE_PATH = "/modules/smart_query/market/";
    @Autowired
    private SqPersonalCatalogService sqDirShareService;

    
    @RequestMapping("catalogshare/save")
    @ResponseBody
	public  Map<String,Object> savePersonalCatalogShare(String catalogId,String [] userIds){
    	 Map<String,Object> result = new HashMap<String, Object>(2);
    	  boolean success = false;
          String msg = null;
          try {
			sqDirShareService.savePersonalCatalogShare(catalogId,userIds);
			success = true;
            msg = "保存成功";
		} catch (Exception e) {
			 log.error("保存失败",e);
	            msg = "保存失败";
		}
    	 
    	 result.put("success",success);
         result.put("msg",msg);
		return result;
	}
    
	/**
	 * 目录分享页面的跳转
	 * @return
	 * @author 吴淦钦
	 */
	@RequestMapping("/personalCatalog.htm")
	public String forwardDirectoryToShare(){
		log.info("目录分享页面跳转页面跳转");
		return BASE_PATH+"personalCatalog";
	}
	
	/**
	 * 目录列表查询
	 * @return
	 * @author 吴淦钦
	 */
	@RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,Object>  queryDir(Integer page,Integer rows,String user,String creator,String name){	
		Map<String,Object> result  = null;
		try {
			result  = sqDirShareService.querySqPersonalCatalogList(page,rows,creator,name);
  		} catch (Exception e) {
			log.error("查询目录列表失败",e);
 		}		
		return result;
	}
	

	/**
	 * 查询已经选择的树数据信息
	 * @return
	 * @author 吴淦钦
	 */
	@RequestMapping(value = "/userSelect/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<SqPersonalCatalogShare> loadUserSelectTree(String id){	
		
		List<SqPersonalCatalogShare> list = new ArrayList<SqPersonalCatalogShare>();
		try {
			list = sqDirShareService.queryUserSelectTree(id);
		} catch (Exception e) {
			log.error("查询文件目录树数据信息失败",e);
		}		
		return list;
	}
	/**
	 * 查询用户树数据信息
	 * @return
	 * @author 吴淦钦
	 */
	@RequestMapping(value = "/user/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Map<String, Object>> loadUserTree(String id){	

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
   			list = sqDirShareService.queryUserTree();
  		} catch (Exception e) {
			log.error("查询文件目录树数据信息失败",e);
 		}		
		return list;
	}
	
	/**
	 * 通过用户列表为没有个人目录的用户创建初始化个人目录
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping(value = "/userCatalog/create", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	boolean createPersonalCatalog(){	
		boolean flag = true;
		try {
			/*获取文件信息*/
			flag = this.sqDirShareService.savePersonalCatalogByUsers();
  		} catch (Exception e) {
			log.error("创建初始化个人目录失败",e);
			flag = false;
 		}
		return flag;
	}
	
	
}
