
package com.aspire.birp.modules.smartQuery.market.service;

import java.util.List;
import java.util.Map;

import com.aspire.birp.modules.smartQuery.market.entity.SqPersonalCatalogShare;


/**  
 * @Title: 目录分享接口类
 * @Description: 
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月27日 上午11:02:01
 * @version: V1.0
 */

public interface SqPersonalCatalogService {
	
	
	/**
	 * 查询目录列表
	 * @param name 
	 * @param creator 
	 * @param rows 
	 * @param page 
	 * @return
	 */
	public Map<String, Object> querySqPersonalCatalogList(Integer page, Integer rows ,String creator, String name);
	/**
	 * 查询用户树列表
	 * @return
	 */
	public List<Map<String, Object>> queryUserTree();
	/**
	 * 保存
	 * @param catalogId
	 * @param userIds
	 */
	public void savePersonalCatalogShare(String catalogId, String[] userIds);
	/**
	 * 查询已经分配的目录用户
	 */
	public List<SqPersonalCatalogShare> queryUserSelectTree(String id);
	/**
	 * 通过用户列表为没有个人目录的用户创建初始化个人目录
	 * @return
	 * @author 张健雄
	 */
	public boolean savePersonalCatalogByUsers();
	
}


