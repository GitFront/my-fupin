
package com.aspire.birp.modules.smartQuery.query.vo;

import com.aspire.birp.modules.base.vo.TreeObject;


/**  
 * @Title: 智能查询字段映射表树的数据结构 
 * @Description: 用于定义智能查询字段映射表的特有属性
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月22日 下午2:51:49
 * @version: V1.0
 */
public class SqTableMappingTree extends TreeObject {

	/*映射表名*/
	private String mpTableName;

	//皮肤的class
	private String iconSkin;
	//字段类型
	private String columnType;
	//周期类型
	private String cycType;
	//I F全
	private String storageType;
	
	private String field;
	
	
	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public SqTableMappingTree() {
		super();
	}
	
	
	public SqTableMappingTree(String id, String name, String pId) {
		super(id, name, pId);
	}

	public String getMpTableName() {
		return mpTableName;
	}

	public void setMpTableName(String mpTableName) {
		this.mpTableName = mpTableName;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}


	public String getColumnType() {
		return columnType;
	}


	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}


	public String getCycType() {
		return cycType;
	}


	public void setCycType(String cycType) {
		this.cycType = cycType;
	}


	public String getStorageType() {
		return storageType;
	}


	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}	
	
	
}


