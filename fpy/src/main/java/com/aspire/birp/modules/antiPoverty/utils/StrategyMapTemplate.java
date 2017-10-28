package com.aspire.birp.modules.antiPoverty.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * 类说明 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月17日 上午10:36:00 
 */
public class StrategyMapTemplate {
	
	public static Map<String,Object> strategyStaticMapTpl(Map<String,Object> map,String type){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if("Dis".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("CHART_CONFIG_LABOR", "0");
			}
		}else if("Bottom".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("CHART_CONFIG_SOCIETY", "0");
			}
		}else if("Ywjy".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("YWJY", "0");
			}
		}else if("Jbyl".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("JBYL", "0");
			}
		}else if("Zfaq".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("ZFAQ", "0");
			}
		}else if("Yxd".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("DLYH", "0");
				ret_map.put("AQYS", "0");
				ret_map.put("DWGZ", "0");
				ret_map.put("WSZ", "0");
				ret_map.put("GBDS", "0");
				ret_map.put("KDWL", "0");
			}
		}else if("Yjjk".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("FAMILY", "0");
				ret_map.put("RECORDS", "0");
				ret_map.put("COUNTRY", "0");
				ret_map.put("FAMILY_NOT_VISITED_LABEL", "一年内未被走访的");
				ret_map.put("FAMILY_NOT_VISITED_VALUE", "0");
			}
		}else if("XmzjCount".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_INVESTED", "0");
				ret_map.put("S_PROJECTS", "0");
				ret_map.put("S_RATE_COMPLETED", "0");
				ret_map.put("S_COUNT", "0");
			}
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> strategyStaticListTpl(List<Map<String,Object>> mapList,String type){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if("Xmzj".equals(type)){
			if(mapList.size()>0){
				ret_map.put("MAPLIST", mapList);
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("S_NAME","");
				map.put("GS",0.0);
				map.put("WCL",0.0);
				map.put("ZJ",0.0);
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				list.add(map);
				ret_map.put("MAPLIST", list);
			}
		}else if("Pkfx".equals(type)){
			if(mapList.size()>0){
				ret_map.put("MAPLIST", mapList);
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("S_COUNT",0.0);
				map.put("S_NAME",0.0);
				map.put("S_VALUE",0.0);
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				list.add(map);
				ret_map.put("MAPLIST", list);
			}
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> strategyDynamicIndexTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("SUCCESS_COUNTRY_A", "0");
			ret_map.put("SUCCESS_COUNTRY_R", "0");
			
			ret_map.put("SUCCESS_FAMILY_A", "0");
			ret_map.put("SUCCESS_FAMILY_R", "0");
			
			ret_map.put("SUCCESS_PEOPLE_A", "0");
			ret_map.put("SUCCESS_PEOPLE_R", "0");
			
			ret_map.put("AMOUNT_ORG", "0");
			ret_map.put("AMOUNT_RESPONSIBLE", "0");
			ret_map.put("AMOUNT_LEADER", "0");
			ret_map.put("AMOUNT_INTERVIEW", "0");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
}
