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
public class CountryMapTemplate {
	
	public static Map<String,Object> countryFileBasicTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_COUNTRY_NAME", "");
			ret_map.put("S_RESPONSIBLE_NAME", "");
			ret_map.put("S_RESPONSIBLE_AVATAR", "");
			ret_map.put("S_RESPONSIBLE_PHONE", "");
			ret_map.put("S_RESPONSIBLE_DUTY", "");
			ret_map.put("S_COUNTRY_LEADER_NAME", "");
			ret_map.put("S_COUNTRY_LEADER_AVATAR", "");
			ret_map.put("S_TEAM_LEADER_TEL", "");
			ret_map.put("S_COUNTRY_LEADER_ORG", "");
			ret_map.put("S_COUNTRY_LEADER_START_TIME", "");
			
			ret_map.put("S_AMOUNT_TOTAL_FAMILY", "0");
			ret_map.put("S_AMOUNT_TOTAL_PEOPLE", "0");
			ret_map.put("S_AMOUNT_POOR_FAMILY", "0");
			ret_map.put("S_AMOUNT_POOR_PEOPLE", "0");
			ret_map.put("S_RATIO_POOR", "0");
			ret_map.put("S_AMOUNT_SUCCESS_FAMILY", "0");
			ret_map.put("S_AMOUNT_SUCCESS_PEOPLE", "0");
			ret_map.put("S_SCORE", "0");
			ret_map.put("S_HAS_ACHIEVED", "0");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileBusinessTpl(List<Map<String,Object>> mapList,List<Map<String,Object>> mapList2){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()==0){
			RET_CODE = 0;
			RET_MSG = "由于待办数据缺失，获取信息失败";
		}
		if(mapList2.size()==0){
			RET_CODE = 0;
			RET_MSG = "由于已办数据缺失，获取信息失败";
		}
		ret_map.put("MAPLIST", mapList);
		ret_map.put("MAPLIST2", mapList2);
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileDataPathTpl(Map<String,Object> map,List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_DATE", "");
			ret_map.put("S_TIME", "");
			ret_map.put("S_PUBLISHER_ORGANIZATION", "");
			ret_map.put("S_PUBLISHER_NAME", "");
		}
		ret_map.put("MAPLIST", mapList);
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileDevelopmentStatusTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_AVERAGE", "");
			ret_map.put("S_COLLECTIVE", "");
			
			ret_map.put("S_RURAL_MEDICAL", "");
			ret_map.put("S_PENSION_INSURANCE", "");
			ret_map.put("S_MEDICAL_HELP", "");
			
			ret_map.put("S_AMOUNT", "");
			
			ret_map.put("S_DISTANCE_ADMIN_TO_TOWN", "");
			ret_map.put("S_BUS_AVAILABLE", "");
			ret_map.put("S_AMOUNT_NATURAL_TO_ADMIN", "");
			ret_map.put("S_DISTANCE_NATURAL", "");
			
			ret_map.put("S_NOT_ENABLED_LIVING_COUNTRY", "");
			ret_map.put("S_NOT_ENA_PROD_COUNTRY", "");
			ret_map.put("S_NOT_ENABLED_LIVING_FAMILY", "");
			ret_map.put("S_ENABLED_COUNTRY", "");
			
			ret_map.put("S_UNSAFE", "");
			ret_map.put("S_DIFFICULT", "");
			
			ret_map.put("S_AMOUNT_FSC", "");
			ret_map.put("S_AMOUNT_FAMILY_FSC", "");
			
			ret_map.put("S_TRAVEL_FAMILY", "");
			ret_map.put("S_TRAVEL_PEOPLE", "");
			ret_map.put("S_RURAL_INN_FAMILY", "");
			ret_map.put("S_RURAL_INN_AVERAGE", "");
			
			ret_map.put("S_LIBRARY", "");
			ret_map.put("S_TV", "");
			
			ret_map.put("S_OFFICE", "");
			ret_map.put("S_DOCTOR", "");
			ret_map.put("S_TOILET", "");
			ret_map.put("S_GARBAGE", "");
			
			ret_map.put("S_BROADBAND_FAMILY", "");
			ret_map.put("S_MOBILE_FAMILY", "");
			ret_map.put("S_BROADBAND_NATURAL", "");
			ret_map.put("S_BROADBAND_COUNTRY", "");
			ret_map.put("S_ADMIN_STAFF", "");
			
			ret_map.put("S_RST_AMOUNT", "");
			ret_map.put("S_PERSON_AMOUNT", "");
			
			ret_map.put("S_MIDDLE_SCHOOL", "");
			ret_map.put("S_JOINED", "");
			ret_map.put("S_HIGH_SCHOOL", "");
			
			ret_map.put("S_ALLOW", "");
			ret_map.put("S_CUR_YEAR", "");
			ret_map.put("S_DUE", "");
			
			ret_map.put("S_PARTY_AMOUNT", "");
			ret_map.put("S_RATIO", "");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileEliminatePathTpl(List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size() == 0){
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
		}
		ret_map.put("MAPLIST", mapList);
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileEliminateScoresTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("POOR_RATE_SCORE", "");
			ret_map.put("POOR_RATE", "");
			ret_map.put("POOR_RATE_TARGET", "");
			ret_map.put("POOR_HAS_ACHIEVED", "0");
			
			ret_map.put("OWN_CAPITAL_SCORE", "");
			ret_map.put("OWN_CAPITAL", "");
			ret_map.put("OWN_CAPITAL_TARGET", "");
			ret_map.put("OWN_HAS_ACHIEVED", "0");
			
			ret_map.put("ROAD_SCORE", "");
			ret_map.put("ROAD", "");
			ret_map.put("ROAD_TARGET", "");
			ret_map.put("ROAD_HAS_ACHIEVED", "0");
			
			ret_map.put("WATER_SCORE", "");
			ret_map.put("WATER", "");
			ret_map.put("WATER_TARGET", "");
			ret_map.put("WATER_HAS_ACHIEVED", "0");
			
			ret_map.put("ELECTRIFY_SCORE", "");
			ret_map.put("ELECTRIFY", "");
			ret_map.put("ELECTRIFY_TARGET", "");
			ret_map.put("ELECTRIFY_HAS_ACHIEVED", "0");
			
			ret_map.put("TV_SCORE", "");
			ret_map.put("TV", "");
			ret_map.put("TV_TARGET", "");
			ret_map.put("TV_HAS_ACHIEVED", "0");
			
			ret_map.put("NET_SCORE", "");
			ret_map.put("NET", "");
			ret_map.put("NET_TARGET", "");
			ret_map.put("NET_HAS_ACHIEVED", "0");
			
			ret_map.put("HEALTH_SCORE", "");
			ret_map.put("HEALTH", "");
			ret_map.put("HEALTH_TARGET", "");
			ret_map.put("HEALTH_HAS_ACHIEVED", "0");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileImplementTpl(Map<String,Object> map,List<Map<String,Object>> mapList,List<Map<String,Object>> mapList2){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_TOTAL", "0");
			ret_map.put("S_RUNNING", "0");
			ret_map.put("S_COMPLETED", "0");
			
			ret_map.put("S_INVESTED", "0");
			ret_map.put("S_PROFIT", "0");
		}
		ret_map.put("MAPLIST", mapList);
		ret_map.put("MAPLIST2", mapList2);
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileInvestedTpl(Map<String,Object> map,Map<String,Object> map2,Map<String,Object> map3,List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map3!=null){
			ret_map = map3;
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_ORGANIZATION", "0");
			
			ret_map.put("S_HELPER_CITY_FINANCE", "0");
			ret_map.put("S_DISTRICT_TOWN_FINANCE", "0");
			ret_map.put("S_HELPER_SOCIAL_MONEY", "0");
			
			ret_map.put("S_CENTRAL_FINANCE", "0");
			ret_map.put("S_PROVINCE_FINANCE", "0");
			ret_map.put("S_CENTRAL_INDUSTRY", "0");
			ret_map.put("S_PROVINCE_INDUSTRY", "0");
			ret_map.put("S_CENTRAL_SOCIAL_MONEY", "0");
			
			ret_map.put("S_HELPED_CITY_FINANCE", "0");
			ret_map.put("S_COUNTY_TOWN_FINANCE", "0");
			ret_map.put("S_CITY_INDUSTRY", "0");
			ret_map.put("S_COUNTY_TOWN_INDUSTRY", "0");
			ret_map.put("S_HELPED_SOCIAL_MONEY", "0");
			
			ret_map.put("S_SUMMARY", "0");
		}
		
		if(map!=null){
			ret_map.put("S_TOTAL", map.get("S_TOTAL"));
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_TOTAL", "0");
		}
		
		if(map2!=null){
			ret_map.put("S_YEAR_TOTAL", map2.get("S_TOTAL"));
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_YEAR_TOTAL", "0");
		}
		ret_map.put("MAPLIST", mapList);
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileMeetingNewsTpl(List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			ret_map.put("MAPLIST", mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("MAPLIST", mapList);
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileMeetingNewsDetailTpl(Map<String,Object> obj){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(obj !=null){
			ret_map = obj;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_DESC", "");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileNewsTpl(List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			ret_map.put("MAPLIST", mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("MAPLIST", mapList);
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileNewsDetailTpl(Map<String,Object> map,List<Map<String,Object>> picList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
			ret_map.put("PICLIST", picList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_DESC", "");
			ret_map.put("PICLIST", picList);
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFilePlanTpl(List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			ret_map.put("MAPLIST", mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			mapList = new ArrayList<Map<String,Object>>();
			for(int i=0;i<4;i++){
				Map<String,Object> map =  new HashMap<String,Object>();
				map.put("S_CCOUNTRY_LEADER_SIGN","");
				map.put("S_RESPONSIBLE_SIGN","");
				map.put("S_TIME","");
				mapList.add(map);
			}
			ret_map.put("MAPLIST", mapList);
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> countryFileStatusTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_COUNTRY_NAME", "");
			ret_map.put("S_COUNTRY_ATTRIBUTE", "");
			ret_map.put("S_TOPOGRAPHY", "");
			ret_map.put("S_DEV_TARGET", "");
			ret_map.put("S_GROUPS", "");
			
			ret_map.put("S_FAM", "");
			ret_map.put("S_POOR_FAM", "");
			ret_map.put("S_LOW_FAM", "");
			ret_map.put("S_FIV_FAM", "");
			
			ret_map.put("S_POP", "");
			ret_map.put("S_POOR_POP", "");
			ret_map.put("S_DISABLED_POP", "");
			ret_map.put("S_FEME_POP", "");
			ret_map.put("S_LOW_POP", "");
			ret_map.put("S_FIV_POP", "");
			ret_map.put("S_MINORITY_POP", "");
			
			ret_map.put("S_WB_POP", "");
			ret_map.put("S_OUT_WORK_POP", "");
			ret_map.put("S_FARM_WORK_POP", "");
			
			ret_map.put("S_CULTIVATED_TOTAL", "");
			ret_map.put("S_CULTIVATED_AVAILABLE", "");
			
			ret_map.put("S_FOREST_TOTAL", "");
			ret_map.put("S_FOREST_AVAILABLE", "");
			
			ret_map.put("S_GRASS_TOTAL", "");
			ret_map.put("S_WATER_TOTAL", "");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
}
