
package com.aspire.birp.modules.smartQuery.market.vo;

import com.aspire.birp.modules.base.vo.TreeObject;
import com.aspire.birp.modules.smartQuery.base.constant.SmartQueryConstant;

/**  
 * @Title: 数据超市目录树的数据结构 
 * @Description: 用于构建数据超市目录树的数据集合
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月27日 下午2:57:34
 * @version: V1.0
 */
public class SqFileTimeTree extends TreeObject {

	/* 创建时间*/
	private String createTime;
	/*节点日期类型*/
	private String timeType;
	/*当前时间的年信息*/
	private String year;
	/*当前时间的月信息*/
	private String month;
	/*当前时间的日信息*/
	private String date;
	/*是否为目录节点*/
	private boolean isTime;
	
	public SqFileTimeTree() {
		super();
		
	}
	/**
	 * 自定义节点处理方式
	 * @param id
	 * @param name
	 * @param pId
	 */
	public SqFileTimeTree(String id, String name, String pId) {
		super(id, name, pId);
		super.setParent(true);
		super.setOpen(true);
		this.timeType = SmartQueryConstant.DATA_MARKET_TIME_TYPE_YRAR;
		this.isTime = false;		
	}
	
	/**
	 * 创建年节点对象，时间格式为YYYYMM
	 * @param createTime
	 * @param pId
	 */
	public SqFileTimeTree(String createTime,String pId) {
		/*年时间的处理方式*/		
		super.setId(createTime.substring(0,4));
		super.setName(createTime.substring(0,4));
		super.setpId(pId);
		super.setOpen(false);
		this.createTime = createTime;
		this.year = createTime.substring(0,4);
		this.timeType = SmartQueryConstant.DATA_MARKET_TIME_TYPE_YRAR;
		this.isTime = true;
	}
	
	/**
	 * 创建月节点对象时间格式为YYYYMM
	 * @param createTime
	 */
	public SqFileTimeTree(String createTime) {
		/*月时间的处理方式*/		
		super.setId(createTime);
		super.setName(createTime.substring(4));
		super.setpId(createTime.substring(0,4));
		super.setOpen(false);
		this.createTime = createTime;
		this.year = createTime.substring(0,4);
		this.month  = createTime.substring(4);
		this.timeType = SmartQueryConstant.DATA_MARKET_TIME_TYPE_MONTH;
		this.isTime = true;
	}
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public boolean isTime() {
		return isTime;
	}
	public void setTime(boolean isTime) {
		this.isTime = isTime;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	

}


