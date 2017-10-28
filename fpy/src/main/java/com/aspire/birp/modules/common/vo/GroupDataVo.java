package com.aspire.birp.modules.common.vo;

/**
 * Title: Bar\StackBars\Line\TimeSeriesLine等图表的数据封装类 <br>
 * Description: 主要用于生成Bar\StackBars\Line\TimeSeriesLine等图表的数据列表 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年7月9日 下午17:45:03 <br>
 *
 */
public class GroupDataVo {

	/*x轴维度信息*/
	private String name;
	/*分组信息*/
	private String group = "统计结果";
	/*对应的数据值*/
	private Double value;
	public GroupDataVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GroupDataVo(String name, String group, Double value) {
		super();
		this.name = name;
		this.group = group;
		this.value = value;
	}
	
	/**
	 * 数据格式转换类
	 * @param name
	 * @param group
	 * @param value
	 */
	public GroupDataVo(Object name, Object group, Object value) {
		super();
		this.name = String.valueOf(name);
		this.group = String.valueOf(group);
		if(value != null){
			this.value = Double.parseDouble(String.valueOf(value));
		}else{
			this.value = 0d;
		}
	}
	
	/**
	 * 数据格式转换类
	 * @param name
	 * @param value
	 */
	public GroupDataVo(Object name, Object value) {
		super();
		this.name = String.valueOf(name);
		if(value != null){
			this.value = Double.parseDouble(String.valueOf(value));
		}else{
			this.value = 0d;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
	
}
