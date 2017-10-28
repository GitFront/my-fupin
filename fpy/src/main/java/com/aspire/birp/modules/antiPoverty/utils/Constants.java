package com.aspire.birp.modules.antiPoverty.utils;

import java.util.HashMap;
import java.util.Map;

import com.aspire.birp.modules.antiPoverty.service.NewDateService;

/** 
 *  公共变量类 
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月15日 下午2:58:09
 */
public class Constants {
	/**人均可支配收入分析贫困类型常量*/
	public static final Integer POOR_TYPE_ALL = 0;  //全部
	public static final Integer POOR_TYPE_HAVE_LABOR =1;  //有劳动力户
	public static final Integer POOR_TYPE_NO_LABOR=2;  //无劳动力户
	public static Map<String, Integer> poorTypeMap;
	static{
		poorTypeMap = new HashMap<String,Integer>();
		poorTypeMap.put("all",POOR_TYPE_ALL);
		poorTypeMap.put("have_labor",POOR_TYPE_HAVE_LABOR);
		poorTypeMap.put("no_labor",POOR_TYPE_NO_LABOR);
	}
	
	/**找下一级层级level区域常量*/
	public static final String CITY = "city";
	public static final String COUNTY = "county";
	public static final String TOWN = "town";
	public static final String COUNTRY = "country";
	public static Map<String, String> nextLevelMap;
	static{
		nextLevelMap = new HashMap<String,String>();
		nextLevelMap.put("province",CITY);
		nextLevelMap.put("city",COUNTY);
		nextLevelMap.put("county",TOWN);
		nextLevelMap.put("town",COUNTRY);
		nextLevelMap.put("country",COUNTRY);
	}
	
	/**人均可支配收入分析贫困类型常量*/
	public static final String POOR_ATTRIBUTE_ALL ="0";  //低保
	public static final String POOR_ATTRIBUTE_LOW ="1";  //低保
	public static final String POOR_ATTRIBUTE_FIVE ="2";  //五保
	public static final String POOR_ATTRIBUTE_NO_LABOR="3";  //无劳动力户
	public static Map<String, String> poorAttributeMap;
	static{
		poorAttributeMap = new HashMap<String,String>();
		poorAttributeMap.put("all",POOR_ATTRIBUTE_ALL);
		poorAttributeMap.put("low",POOR_ATTRIBUTE_LOW);
		poorAttributeMap.put("five",POOR_ATTRIBUTE_FIVE);
		poorAttributeMap.put("no_labor",POOR_ATTRIBUTE_NO_LABOR);
	}
	
	/**根据层级level转换数字常量*/
	public static final String ORDER_CITY ="1";  //低保
	public static final String ORDER_COUNTY ="2";  //低保
	public static final String ORDER_TOWN ="3";  //五保
	public static final String ORDER_COUNTRY="4";  //无劳动力户
	public static Map<String, String> levelTranMap;
	static{
		levelTranMap = new HashMap<String,String>();
		levelTranMap.put("province",ORDER_CITY);
		levelTranMap.put("city",ORDER_CITY);
		levelTranMap.put("county",ORDER_COUNTY);
		levelTranMap.put("town",ORDER_TOWN);
		levelTranMap.put("country",ORDER_COUNTRY);
	}

	public static Map<String, Object> dateContants =NewDateService.getDateMapCostants();   
}
