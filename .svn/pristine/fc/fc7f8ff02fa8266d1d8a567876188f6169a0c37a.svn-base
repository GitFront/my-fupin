
package com.aspire.birp.modules.smartQuery.market.vo;

import com.aspire.birp.modules.base.vo.TreeObject;

/**  
 * @Title: 数据超市目录树的数据结构 
 * @Description: 用于构建数据超市目录树的数据集合
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月27日 下午2:57:34
 * @version: V1.0
 */
public class SqFileCatalogTree extends TreeObject {
	/*目录全路径*/
	private String catalogPath;
	/*是否为目录节点*/
	private boolean isCatalog;
	
	public SqFileCatalogTree() {
		super();
		
	}
	public SqFileCatalogTree(String id, String name, String pId) {
		super(id, name, pId);
		super.setParent(true);
		super.setOpen(true);
		this.isCatalog = false;		
	}
	
	
	public SqFileCatalogTree(String id, String name, String pId,
			String catalogPath) {
		super(id, name, pId);
		super.setParent(true);
		super.setOpen(false);
		this.catalogPath = catalogPath;
		this.isCatalog = true;
	}

	public String getCatalogPath() {
		return catalogPath;
	}
	public void setCatalogPath(String catalogPath) {
		this.catalogPath = catalogPath;
	}
	public boolean isCatalog() {
		return isCatalog;
	}
	public void setCatalog(boolean isCatalog) {
		this.isCatalog = isCatalog;
	}
}


