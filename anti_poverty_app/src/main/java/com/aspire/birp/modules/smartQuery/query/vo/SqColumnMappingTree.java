
package com.aspire.birp.modules.smartQuery.query.vo;

import com.aspire.birp.modules.base.vo.TreeObject;



/**  
 * @Title: 智能查询映射字段树的数据结构 
 * @Description: 用于定义智能查询映射字段的特有属性
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年4月27日 下午2:51:49
 * @version: V1.0
 */
public class SqColumnMappingTree extends TreeObject {
	
	/*映射表英文名*/
	private String mpTable;
	
	/*映射字段英文名*/
	private String mpColumn;
	
	public SqColumnMappingTree() {
		super();
	}
	
	
	public SqColumnMappingTree(String id, String name, String pId) {
		super(id, name, pId);
	}


	public String getMpTable() {
		return mpTable;
	}


	public void setMpTable(String mpTable) {
		this.mpTable = mpTable;
	}

	public String getMpColumn() {
		return mpColumn;
	}


	public void setMpColumn(String mpColumn) {
		this.mpColumn = mpColumn;
	}
}


