package com.aspire.birp.modules.antiPoverty.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialClob;

import com.aspire.birp.modules.antiPoverty.model.data.DynamicIndex;
import com.aspire.birp.modules.antiPoverty.model.data.DynamicIndexData;
import com.sun.org.apache.bcel.internal.generic.DDIV;

/** 
 * 类说明 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月17日 上午10:36:00 
 */
public class DataMapTemplate {
	
	public static Map<String,Object> dataStaticMapTpl(Map<String,Object> map,String type) throws Exception{
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if("AreaDesc".equals(type)){
			if(map!=null){
				ret_map = map;
				if(map.get("AREA_DESC")==null){
					ret_map.put("AREA_DESC", new SerialClob("暂无简介".toCharArray()));
				}
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("AREA_DESC", new SerialClob("暂无简介".toCharArray()));
			}
		}else if("Dis".equals(type)){
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
				ret_map.put("FAMILY_NOT_VISITED", "0");
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
	
	public static Map<String,Object> dataStaticListTpl(List<Map<String,Object>> mapList,String type){
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
	
	public static Map<String,Object> dataDynamicIndexTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_POOR_FAMILY_AMOUNT", "0");
			ret_map.put("S_SUCCESS_FAMILY_AMOUNT", "0");
			ret_map.put("S_SUCCESS_FAMILY_RATE", "0");
			ret_map.put("S_POOR_PEOPLE_AMOUNT", "0");
			ret_map.put("S_SUCCESS_PEOPLE_AMOUNT", "0");
			ret_map.put("S_SUCCESS_PEOPLE_RATE", "0");
			ret_map.put("S_SCORE", "0");
			
			ret_map.put("AMOUNT_ORG", "0");
			ret_map.put("AMOUNT_RESPONSIBLE", "0");
			ret_map.put("AMOUNT_LEADER", "0");
			ret_map.put("AMOUNT_INTERVIEW", "0");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static DynamicIndex dataDynamicIndexTplo(DynamicIndexData map){
		DynamicIndex di = new DynamicIndex();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		DynamicIndexData did = null;
		if(map!=null){
			did = map;
		}else{
			did = new DynamicIndexData();
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			did.setAmountInterview(0);
			did.setAmountLeader(0);
			did.setAmountOrg(0);
			did.setAmountResponsible(0);
			did.setAreaLevel("0");
			did.setPoorFamilyAmount(0l);
			did.setPoorPeopleAmount(0l);
			did.setScore(0);
			did.setSuccessFamilyAmount(0l);
			did.setSuccessFamilyRate(0);
			did.setSuccessPeopleAmount(0l);
			did.setSuccessPeopleRate(0);
		}
		di.setData(did);
		di.setCode(RET_CODE);
		di.setMsg(RET_MSG);
		return di;
	}
}
