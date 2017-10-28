package com.aspire.birp.modules.antiPoverty.utils;

import java.util.HashMap;
import java.util.Map;

/** 
 * 类说明 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月17日 上午10:36:00 
 */
public class DataMonitorTemplate {
	
	public static Map<String,Object> dataMonitorTpl(Map<String,Object> map,String type){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			if("treeTotal".equals(type)){
				ret_map.put("S_HAS_ACHIEVED", "0");
			}			
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
}
