
package com.aspire.birp.common.excel;

import com.aspire.bi.common.config.Global;

/**  
 * @Title: 文件操作常量类 
 * @Description: 主要用于定义文件操作的常量信息
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月29日 下午5:55:47
 * @version: V1.0
 */
public class FileProperties {

	/*文件保存路径*/
	private static String filePath = null;
	/*临时文件保存路径*/
	private static String tempFilePath = null;
	/*excel文件最大行数限制*/
	private static Integer excelMaxSize = null;
	/*CVS文件保存编码*/
	static private String csvFileCode = null;
	/*上传文件编码*/
	static private String uploadFileCode = null;
	
	
	 static{
	  loads();
	 }
	 synchronized static public void loads(){
	  if(filePath == null || tempFilePath == null || excelMaxSize == null || csvFileCode == null || uploadFileCode == null)
	  {
/*	   System.out.println(FileProperties.class.getResource("/file.properties").getPath());
	   InputStream is = FileProperties.class.getResourceAsStream("/file.properties");
	   Properties dbProps = new Properties();
	     try {
	        dbProps.load(is);*/
	        filePath = Global.getConfig("filePath");
	        tempFilePath = Global.getConfig("temp.filePath");
	        excelMaxSize = Integer.valueOf(Global.getConfig("excel.maxSize"));
	        csvFileCode = Global.getConfig("csv.file.code");
	        uploadFileCode = Global.getConfig("upload.file.code");
/*  		 }
	      catch (Exception e) {
	        System.err.println("不能读取属性文件. " +
	       "请确保file.properties在CLASSPATH指定的路径中");
	      }*/
	  }
	 }
	public static String getFilePath() {
		if(filePath == null) loads();
		return filePath;
	}
	public static Integer getExcelMaxSize() {
		if(excelMaxSize == null) loads();
		return excelMaxSize;
	}
	public static String getCsvFileCode() {
		if(csvFileCode == null) loads();
		return csvFileCode;
	}
	public static String getTempFilePath() {
		if(tempFilePath == null) loads();
		return tempFilePath;
	}
	public static String getUploadFileCode() {
		if(uploadFileCode == null) loads();
		return uploadFileCode;
	}	
	
	  
}


