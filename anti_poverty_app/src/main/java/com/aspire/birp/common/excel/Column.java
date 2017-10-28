package com.aspire.birp.common.excel;

/**
 * @Title: jxl的列定义类
 * @Description: 用于Excel导出的辅助类，映射数据结果集(ResultSet)内列名的元数据和Excel内的显示列名
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月30日 上午11:31:22
 * @version: V1.0
 */
public class Column {
	
	
	private int index;
	private String metaName;
	private String displayName;

	/**
	 * 构造函数
	 * @param index 显示顺序，0 为显示的第一列
	 * @param meta 元列名，在ResultSet内的名字，必须大写
	 * @param display 显示列名，在Excel内的显示，可以是任何文字
	 */
	public Column(int index, String meta, String display) {
		this.index = index;
		this.metaName = meta;
		this.displayName = display;
	}

	/**
	 * 显示列名，在Excel内的显示，可以是任何文字
	 * @return
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * 显示顺序，0 为显示的第一列
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 元列名，在ResultSet内的名字，必须大写
	 * @return
	 */
	public String getMetaName() {
		return metaName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setMetaName(String metaName) {
		this.metaName = metaName;
	}
}
