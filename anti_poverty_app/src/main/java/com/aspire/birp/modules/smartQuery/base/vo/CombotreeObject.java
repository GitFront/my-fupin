
package com.aspire.birp.modules.smartQuery.base.vo;

import java.util.ArrayList;
import java.util.List;

/**  
 * @Title: Easyui中combotree控件的数据封装对象
 * @Description: 用于封装combotree控件的展示数据信息
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月10日 下午5:04:44
 * @version: V1.0
 */
public class CombotreeObject {

	private String id;
	private String text;
	List<CombotreeObject> children;
	
	public CombotreeObject() {
		super();
	}
	
	public CombotreeObject(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}
	
	public CombotreeObject(String id, String text,
			List<CombotreeObject> children) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<CombotreeObject> getChildren() {
		return children;
	}
	public void setChildren(List<CombotreeObject> children) {
		this.children = children;
	}
	
	/**
	 * 添加子节点
	 * @param node 节点对象
	 * @author 张健雄
	 */
	public void addChlidren(CombotreeObject node){
		if(this.children == null)
			children = new ArrayList<CombotreeObject>();
		this.children.add(node);
	}
	
	/**
	 * 通过ID与text添加子节点
	 * @param id 节点ID
	 * @param text 节点名称
	 * @author 张健雄
	 */
	public void addChlidren(String id,String text){
		if(this.children == null)
			children = new ArrayList<CombotreeObject>();
		this.children.add(new CombotreeObject(id,text));
	}
	
	
}


