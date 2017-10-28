
package com.aspire.birp.modules.smartQuery.query.vo;
/**  
 * @Title: 智能查询显示列传输对象
 * @Description: 主要用于传递显示列的基本信息
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年8月7日 上午11:19:37
 * @version: V1.0
 */
public class SqSelecterVO {

	/**
	 * 数据映射表ID值
	 */
	private String mpTableId;
	
	/**
	 * 数据映射列ID值
	 */
	private String mpColumnId;
	
	/**
	 * 过滤器的数据周期时间（默认为空时，则查询当前最新时间参数）
	 */
	private String statTime;

	public SqSelecterVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMpTableId() {
		return mpTableId;
	}

	public void setMpTableId(String mpTableId) {
		this.mpTableId = mpTableId;
	}

	public String getMpColumnId() {
		return mpColumnId;
	}

	public void setMpColumnId(String mpColumnId) {
		this.mpColumnId = mpColumnId;
	}

	public String getStatTime() {
		return statTime;
	}

	public void setStatTime(String statTime) {
		this.statTime = statTime;
	}
	
}


