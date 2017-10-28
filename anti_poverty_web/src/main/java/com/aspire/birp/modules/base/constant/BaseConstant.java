package com.aspire.birp.modules.base.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Title: 常用常量类 <br>
 * Description: 用于保存常用的常量信息<br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年9月21日 上午10:32:55 <br>
 *
 */
public class BaseConstant {
	
	/**
	 * 系统管理默认用户名
	 */
	public static String SYSTEM_DEFAULT_USER = "defaultUser";
	
	/**
	 * 报表参数名为数据类型的后缀标识
	 */
	public static String RP_PARAMETER_ARRAY_SUFFIX = "_array";
	
	/**
	 * 通用标识true
	 */
	public static String COMMON_FLAG_TRUE = "1";
	/**
	 * 通用标识false
	 */
	public static String COMMON_FLAG_FALSE = "0";
	
	public static String TABLE_TYPE_FACT = "FACT";//事实表
	public static String TABLE_TYPE_DIM = "DIM";//维表
	/*DATAGRID对齐方式定义*/
	public static String GRID_COLUMN_ALIGN_LEFT = "left";

	public static String GRID_COLUMN_ALIGN_CENTER = "center";
	
	public static String GRID_COLUMN_ALIGN_RIGHT = "right";

	//数据源表中时间列的名字
	public static String COLUMN_DATE_NAME = "STAT_TIME";
	//数据源表中时间格式
	public static String COLUMN_DATE_TYPE = "yyyyMMdd";
	
	public final static Map<String, String> projClassMap=new HashMap<String, String>();
	static{
		projClassMap.put("1", "村道硬底化");
		projClassMap.put("2", "饮水工程");
		projClassMap.put("3", "文体设施");
		projClassMap.put("4", "卫生设施");
		projClassMap.put("5", "路灯");
		projClassMap.put("6", "农田水利");
		projClassMap.put("7", "公共设施服务");
		projClassMap.put("8", "村集体经济类别");
		projClassMap.put("9", "教育教学");
		projClassMap.put("10", "其他");
		projClassMap.put("11", "产业扶贫");
		projClassMap.put("12", "贷款贴息");
		projClassMap.put("13", "住房改造");
		projClassMap.put("14", "资产扶贫");
		projClassMap.put("15", "慰问");
		projClassMap.put("16", "就业扶贫");
		projClassMap.put("17", "技能培训");
		projClassMap.put("18", "教育扶贫");
		projClassMap.put("19", "政策补贴和社会保障");
		
	}
	
	public final static Map<String, String> exceptionTypeMap=new HashMap<String, String>();

	static{
		exceptionTypeMap.put("holder_id_miss", "20");
		exceptionTypeMap.put("member_id_miss", "21");
		exceptionTypeMap.put("disabled_info_miss", "22");
		exceptionTypeMap.put("id_repeat", "23");
		//贫困识别
		exceptionTypeMap.put("id_card", "1,11,12");
		exceptionTypeMap.put("low_five", "3,4,9,10");
		exceptionTypeMap.put("disabled_info", "6");
		exceptionTypeMap.put("house", "8");
		exceptionTypeMap.put("car", "2");
		exceptionTypeMap.put("i_n_c_info", "5");
		exceptionTypeMap.put("finance", "7");
	}
	
	public final static Map<String, String> poorReasonMap=new HashMap<String, String>();

	static{
		poorReasonMap.put("disease", "Y01");
		poorReasonMap.put("disabled", "Y02");
		poorReasonMap.put("edu", "Y03");
		poorReasonMap.put("disaster", "Y04");
		poorReasonMap.put("land", "Y05");
		poorReasonMap.put("water", "Y06");
		poorReasonMap.put("skill", "Y07");
		poorReasonMap.put("labor", "Y08");
		poorReasonMap.put("money", "Y09");
		poorReasonMap.put("traffic", "Y10");
		poorReasonMap.put("self_dev", "Y11");
		poorReasonMap.put("other", "Y12");
	}
}
