
package com.aspire.birp.modules.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**  
 * @Title: 河源移动项目的公用方法类
 * @Description: 用于对公共库的共用方法补充
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月29日 上午10:06:45
 * @version: V1.0
 */
public class Utils {

	/**
     * 获取某月的最后一天
     * @Title:getLastDayOfMonth
     * @Description:
     * @param year
     * @param month
     * @return:String
     * @throws
     */
    public static Date getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, 1); 
        cal.add(Calendar.DATE, -1); 
        //格式化日期
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());*/
        Date date=cal.getTime();
        
        return date;
    }
    
    /**
     * 获取某月的第一天
     * @Title:getLastDayOfMonth
     * @Description:
     * @param year
     * @param month
     * @return:String
     * @throws
     */
    public static Date getFirstDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DATE, 1); 
        //格式化日期
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());*/
        Date date=cal.getTime();
        
        return date;
    }
    
    /**
	 * 在指定的日期上进行月数加法运算
	 * @param date 指定的一个合法日期
	 * @param months 需要加的月数，如果是大于0则表示加，如果是小于0则表示减；如果等于0表示不变
	 * @return 加后的日期
	 * @author 张健雄
	 */
	  public static Date dateAddMonth(Date date, int months) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();//sdf.format();
	}
    
    /**
	   * 下载附件工具
	   * @param request  当前的请求对象
	   * @param response  当前的响应对象
	   * @param filePath 需要下载的文件路径
	   * @author 张健雄
	   * @throws IOException 
	   * @Date: 2009-06-01
	   */
	
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String filePath) {
		FileInputStream is = null;
		OutputStream os = null;
		long fileLength = 0;
		try {
			if (filePath == null) {
			     return;
			}
			File file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				String html="<script>javascript:common.utils.showErrorMsg('文件读取失败，请稍候再试！');</script>";
	            html = new String(html.getBytes("UTF-8"), "ISO-8859-1");
	            response.getWriter().write(html);
			    return;
			}
			fileLength = file.length();
			String fileName = file.getName();
			try {
				// 客户使用保存文件的对话框
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment;Filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
				
				// 通知客户文件的MIME类型
				// response.setContentType("application/" + type);
				
				// 通知客户文件的长度
				String length = String.valueOf(fileLength);
				response.setHeader("Content-Length", length);
				
				is = new FileInputStream(file);
				byte buf[] = new byte[1024];
				os = response.getOutputStream();
				int num = 0;
				while ((num = is.read(buf)) > 0) {
					os.write(buf, 0, num);
				}
				buf = null;
			} catch (IOException e) { 
				String html="<script>javascript:common.utils.showErrorMsg('文件读取失败，请稍候再试！');</script>";
	            html = new String(html.getBytes("UTF-8"), "ISO-8859-1");
	            response.getWriter().write(html);
			    return;
			} finally {
				if (is != null) {
						is.close();
					}
				if (os != null) {
						os.close();
				}
			}
		} catch (IOException e) {			
		}
	}
    
    /**
     * @Title:main
     * @Description:
     * @param:@param args
     * @return: void
     * @throws
     */
    public static void main(String[] args) 
    {
        Date lastDay = getLastDayOfMonth(Integer.valueOf("2015"),Integer.valueOf("01"));
        Date firstDay = getFirstDayOfMonth(Integer.valueOf("2015"),Integer.valueOf("01"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(lastDay);
        String firstDayOfMonth = sdf.format(firstDay);
        System.out.println("获取当前月的第一天：" + firstDayOfMonth);
        System.out.println("获取当前月的最后一天：" + lastDayOfMonth);
        
    }
    
}


