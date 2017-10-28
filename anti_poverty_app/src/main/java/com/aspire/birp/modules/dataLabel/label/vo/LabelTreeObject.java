
package com.aspire.birp.modules.dataLabel.label.vo;

import com.aspire.birp.modules.base.vo.TreeObject;

/**  
 * @Title: 标签树应用对象
 * @Description: 主要用于展示标签树的对象信息
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年5月4日 下午8:57:38
 * @version: V1.0
 */
public class LabelTreeObject extends TreeObject {

	/*节点类型*/
	private String nodeType;
	/*标签关联映射表ID*/
	private String assoTableId;
	/*标签关联映射表*/
	private String assoTable;
	/*标签关联映射字段ID*/
	private String assoFieldId;
	/*标签关联映射字段*/
	private String assoField;
	/*标签关联映射维度表ID*/
	private String assoDimId;
	/*标签关联映射维度表*/
	private String assoDim;
	/*标签关联映射维度表读取规则*/
	private String assoRule;
	/*标签关联映射维度表取数规则*/
	private Integer topNum;
	/*维表值标签的主键属性*/
	private String dimKey;
	/**
	 * @param id 节点ID
	 * @param name 节点名称
	 * @param pid 节点父ID
	 */
	public LabelTreeObject(String id, String name, String pid) {
		super(id,name,pid);
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getAssoTableId() {
		return assoTableId;
	}
	public void setAssoTableId(String assoTableId) {
		this.assoTableId = assoTableId;
	}
	public String getAssoTable() {
		return assoTable;
	}
	public void setAssoTable(String assoTable) {
		this.assoTable = assoTable;
	}
	public String getAssoFieldId() {
		return assoFieldId;
	}
	public void setAssoFieldId(String assoFieldId) {
		this.assoFieldId = assoFieldId;
	}
	public String getAssoField() {
		return assoField;
	}
	public void setAssoField(String assoField) {
		this.assoField = assoField;
	}
	public String getAssoDimId() {
		return assoDimId;
	}
	public void setAssoDimId(String assoDimId) {
		this.assoDimId = assoDimId;
	}
	public String getAssoDim() {
		return assoDim;
	}
	public void setAssoDim(String assoDim) {
		this.assoDim = assoDim;
	}
	public String getAssoRule() {
		return assoRule;
	}
	public void setAssoRule(String assoRule) {
		this.assoRule = assoRule;
	}
	public Integer getTopNum() {
		return topNum;
	}
	public void setTopNum(Integer topNum) {
		this.topNum = topNum;
	}
	public String getDimKey() {
		return dimKey;
	}
	public void setDimKey(String dimKey) {
		this.dimKey = dimKey;
	}
	
}


