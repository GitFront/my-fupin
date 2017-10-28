package com.aspire.birp.modules.demo.vo;


/**
 * Title: Pie饼状图的数据封装类 <br>
 * Description: 主要用于生成饼状图的数据列表 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年7月10日 下午17:45:03 <br>
 *
 */
public class PieDataVo {

	/*维度信息*/
	private String name;
	/*对应的数据值*/
	private Double value;
	public PieDataVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PieDataVo(String name, Double value) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	
	
}
