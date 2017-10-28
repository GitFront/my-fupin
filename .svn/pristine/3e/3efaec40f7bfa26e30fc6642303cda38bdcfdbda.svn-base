package com.aspire.birp.modules.smartQuery.base.constant;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.aspire.birp.modules.base.constant.BaseConstant;

/**
 * 
 * Title: 智能查询常用常量类 <br>
 * Description: 用于保存智能查询常用的常量信息<br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年9月21日 上午10:32:55 <br>
 *
 */
public class SmartQueryConstant extends BaseConstant {
	
	public static String TABLE_MAPPING_ALLOW_PREFIX = "TW_,TA_,TEST_,DIM_";
	/**
	 * 宽表映射的默认目录
	 */
	public static String TABLE_MAPPING_DEFAULT_CATALOG = "9999";
	/**
	 * 字段说明注解去除标识分隔符
	 */
	public static String COLUMN_COMMENT_NOTE_SPLIT = "[";
	/**
	 * 手动查询任务编码
	 */
	public static String QUERY_TASK_TYPE_MANUAL = "0";
	/**
	 * 自动查询任务编码
	 */
	public static String QUERY_TASK_TYPE_AUTO = "1";
	
	/*字段过滤条件包含关系与不包含关系标识*/
	public static String COLUMN_FORMULA_CONTAINS_CODE = "like";
	public static String COLUMN_FORMULA_NOT_CONTAINS_CODE = "not like";
	
	/*数据超市时间树的时间类型定义(年，月，日)*/
	public static String DATA_MARKET_TIME_TYPE_YRAR = "y";
	public static String DATA_MARKET_TIME_TYPE_MONTH = "m";
	public static String DATA_MARKET_TIME_TYPE_DAY = "d";
	
	/*数据超市树默认ID值*/
	public static String DATA_MARKET_ALL_FILE = "bc48aa34-067d-4427-b9e7-5a05c31f7aa0";
	public static String DATA_MARKET_PERSONAL_CATALOG = "ffa398c6-4915-40b6-a0ce-4b65e5dfafce";
	public static String DATA_MARKET_SHARE_CATALOG = "5d596db2-c4b0-4eb8-bb23-31dd43b7aa5b";
	
	/*报表统计列选择定义(维表列，数值列，分组列)*/
	public static String[] SQ_REPORT_DIMENSION = {"s","d","n"};
	public static String[] SQ_REPORT_AMOUNT = {"n"};
	public static String[] SQ_REPORT_GROUP = {"s"};
	
	//任务执行状态
	//运行中
	public static final String TASK_STATUS_RUNNING = "1";
	//已挂起
	public static final String TASK_STATUS_SUSPEND = "2";
	//已完成
	public static final String TASK_STATUS_FINDISH = "3";
	//异常状态
	public static final String TASK_STATUS_EXCEPTION = "4";

	//数据文件状态
	public static String FILE_STATUS_NORMAL = "0";
	public static String FILE_STATUS_SHARE = "1";
	public static String FILE_STATUS_LOST = "2";
	public static String FILE_STATUS_EXPIRE = "3";
	
	/**
	 * 
	 * @author 张健雄
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("201501".substring(0,4));
		System.out.println("201501".substring(4));
		Properties pro = new Properties();
		FileInputStream in = new FileInputStream("a.properties");
		pro.load(in);
		System.out.println(pro.get("101"));
		in.close();
	}
}
