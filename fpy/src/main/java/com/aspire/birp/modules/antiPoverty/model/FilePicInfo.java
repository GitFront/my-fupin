
package com.aspire.birp.modules.antiPoverty.model;

import java.util.HashMap;
import java.util.Map;

/**  
 * @Title: FilePicMap.java 
 * @Description: TODO(用一句话描述该文件做什么)
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2017年4月27日 下午4:55:03
 * @version: V1.0
 */
public class FilePicInfo {
	
	/*图片路径*/
	private String pics;
	/*图片描述*/
	private String desc; 
	
	/**
	 * 
	 */
	public FilePicInfo() {
		// TODO Auto-generated constructor stub
	}

	public FilePicInfo(String pics, String desc) {
		super();
		this.pics = pics;
		this.desc = desc;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Map<String,Object> getObjectMap(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("S_PICS", getPics());
		map.put("S_DESC", getDesc());
		return map;
	}

}


