package com.aspire.birp.modules.antiPoverty.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommaProcessing {

	//逗号处理
	public static List<Map<String, Object>> ProcessingComma(List<Map<String, Object>> table_list,String Name){
		
		for (int i = 0; i < table_list.size(); i++) {
			String Str=(String) table_list.get(i).get(Name);
		    Str = Str.replaceAll(",",".");
			table_list.get(i).put(Name,Str);
			}
		return table_list;
	}
	public final static Map<String, String> projClassMap=new HashMap<String, String>();
	static{
		projClassMap.put("country_road","村道硬底化");
		projClassMap.put("country_water","饮水工程");
		projClassMap.put("country_recreation_sport","文体设施");
		projClassMap.put("country_hygiene","卫生设施");
		projClassMap.put("country_lamp","路灯");
		projClassMap.put("country_farm","农田水利");
		projClassMap.put("country_public_facility","公共设施服务");
		projClassMap.put("country_collective_economy","村集体经济类别");
		projClassMap.put("country_edu","教育教学");
		projClassMap.put("country_other","其他");
		projClassMap.put("family_industry","产业扶贫");
		projClassMap.put("family_finance","贷款贴息");
		projClassMap.put("family_house","住房改造");
		projClassMap.put("family_property","资产扶贫");
		projClassMap.put("family_visit","慰问");
		projClassMap.put("family_employment","就业扶贫");
		projClassMap.put("family_skill","技能培训");
		projClassMap.put("family_edu","教育扶贫");
		projClassMap.put("family_policy","政策补贴和社会保障");
		
	}
	
	
}
