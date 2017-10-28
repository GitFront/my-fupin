
package com.aspire.birp.modules.base.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;

/**  
 * @Title: json对象处理类 
 * @Description: 主要用于处理json的对象转换
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月23日 下午4:21:25
 * @version: V1.0
 */
public class JsonUtils {

	/**
	 * 将json数组转化为String型列表
	 * @param str
	 * @return
	 */
	public static List<String> getJsonToStringList(String str) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isBlank(str)) return list;
		JSONArray jsonArray = JSONArray.fromObject(str);		
		
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add(jsonArray.getString(i));
		}
		return list;
	}
	
	/**
     * 将json数组转化为Long型
     * @param str
     * @return
     */
    public static Long[] getJsonToLongArray(String str) {
         JSONArray jsonArray = JSONArray.fromObject(str);
         Long[] arr=new Long[jsonArray.size()];
         for(int i=0;i<jsonArray.size();i++){
             arr[i]=jsonArray.getLong(i);
         }
         return arr;
   }
    /**
     * 将json数组转化为String型
     * @param str
     * @return
     */
    public static String[] getJsonToStringArray(String str) {
         JSONArray jsonArray = JSONArray.fromObject(str);
         String[] arr=new String[jsonArray.size()];
         for(int i=0;i<jsonArray.size();i++){
             arr[i]=jsonArray.getString(i);
         }
         return arr;
   }
    /**
     * 将json数组转化为Double型
     * @param str
     * @return
     */
    public static Double[] getJsonToDoubleArray(String str) {
         JSONArray jsonArray = JSONArray.fromObject(str);
         Double[] arr=new Double[jsonArray.size()];
         for(int i=0;i<jsonArray.size();i++){
             arr[i]=jsonArray.getDouble(i);
         }
         return arr;
   }
    /**
     * 将json数组转化为Date型
     * @param str
     * @return
     */
    public static Date[] getJsonToDateArray(String jsonString) {

         JSONArray jsonArray = JSONArray.fromObject(jsonString);
         Date[] dateArray = new Date[jsonArray.size()];
         String dateString;
         Date date;
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
         for (int i = 0; i < jsonArray.size(); i++) {
             dateString = jsonArray.getString(i);
             try {
                 date=sdf.parse(dateString);
                 dateArray[i] = date;
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return dateArray;
   }

}


