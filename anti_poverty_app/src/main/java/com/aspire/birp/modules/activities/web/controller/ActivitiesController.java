package com.aspire.birp.modules.activities.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bi.common.web.controller.BaseController;
import com.aspire.birp.modules.activities.service.ActivitiesService;


/**
 * Title: 营销活动控制类 <br>
 * Description: 主要用于营销活动报表的前端互动开发 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年7月22日 下午17:45:03 <br>
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/mmd/activities")
public class ActivitiesController extends BaseController{

	
	private static Logger log = Logger.getLogger(ActivitiesController.class);
	
	@Autowired
	private ActivitiesService activitiesService;

	public void setActivitiesService(ActivitiesService activitiesService) {
		this.activitiesService = activitiesService;
	}
	
	@RequestMapping(value = "/source/select", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Map<String, String>> loadSourceSelect(){	

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		try {
   			list = this.activitiesService.querySelect("aspire.bi.activities.sourceSelect", null);
  		} catch (Exception e) {
			log.error("查询 地市数据列表 失败",e);
 		}
		
		return list;
	}
	
	@RequestMapping(value = "/activity/select", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Map<String, String>> loadActivitySelect(@RequestParam String sourceId){	

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("sourceId", sourceId);		
		try {
   			list = this.activitiesService.querySelect("aspire.bi.activities.activiesSelect", parameter);
  		} catch (Exception e) {
			log.error("查询 活动数据列表 失败",e);
 		}
		
		return list;
	}
	
	/**
	 * 初始化营销活动分析页
	 * @return
	 */
	@RequestMapping("/act_analyze.htm")
	public String forwardAnalyzePage(@RequestParam String actId,Model model ){	
		/*获取默认的活动信息*/
		Map<String, Object> defaultActivity = this.loadDefaultActivity(actId);
		actId = String.valueOf(defaultActivity.get("act_id"));
		model.addAttribute("actId", actId);
		return "/modules/activities/activities_analyze";
	}
	
	/**
	 * 初始化营销活动总体页
	 * @return
	 */
	@RequestMapping("/activies_info.htm")
	public String forwardActiviesInfo(){	
		return "/modules/activities/activities_info";
	}
	
	
	/**
	 * 获取默认的活动信息
	 * @param actId
	 * @return
	 */
	private Map<String, Object> loadDefaultActivity(String actId){	
		
		Map<String, Object> act = new HashMap<String, Object>();
		Map<String,Object> parameter = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(actId)) parameter.put("actId", actId);		
		try {
			List<Map<String, Object>> list = this.activitiesService.queryList("aspire.bi.activities.defaultActivityLoad", parameter);
			if(list != null) act = list.get(0);
  		} catch (Exception e) {
			log.error("查询 默认活动数据 失败",e);
 		}		
		return act;
	}
	
	/**
	 * 获取营销活动明细数据
	 * @param actId
	 * @return
	 */
	@RequestMapping(value = "/activity/marketinfo/load", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	Map<String, Object> loadActivityMarket(@RequestParam String actId){	

		Map<String, Object> activityMarket = new HashMap<String, Object>();
		if(StringUtils.isBlank(actId)) return activityMarket;
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("actId", actId);
		try {
			List<Map<String, Object>> list = this.activitiesService.queryList("aspire.bi.activities.activityMarketInfo", parameter);
			if(list != null) activityMarket = list.get(0);
  		} catch (Exception e) {
			log.error("查询 营销活动明细数据 失败",e);
 		}
		
		return activityMarket;
	}
	
	
}
