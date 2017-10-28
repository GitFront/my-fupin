
package com.aspire.birp.modules.base.vo;
/**  
 * @Title: ztree的通用查询属性对象
 * @Description: 用于定义ztree查询的通用属性
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月22日 下午2:52:16
 * @version: V1.0
 */
public class TreeObject {

	/*节点ID*/
	private String id;
	/*节点名称*/
	private String name;
	/*父节点ID*/
	private String pId;
	/*是否为父节点*/
	private boolean isParent;
	/*是否为打开状态*/
	private boolean open;
	/*是否隐藏*/
	private boolean isHidden;
	/*显示图标*/
	private String icon;
	/*显示图标皮肤*/
	private String iconSkin;
	/*不显示勾选框*/
	private boolean nocheck;
	
	
	public TreeObject() {
		super();
	}
	
	public TreeObject(String id, String name, String pId) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public boolean isIsParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public boolean isIsHidden() {
		return isHidden;
	}
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	
	
}


