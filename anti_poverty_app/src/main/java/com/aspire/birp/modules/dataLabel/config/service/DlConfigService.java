
package com.aspire.birp.modules.dataLabel.config.service;

import java.util.List;

import com.aspire.birp.modules.dataLabel.config.vo.DmLabelTreeDefVO;

/**  
 * @Title: 数据标签配置功能服务接口类
 * @Description: 用于定义数据标签配置功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年4月26日 下午2:57:52
 * @version: V1.0
 */
public interface DlConfigService {

	/**
	 * 查询数据标签可编辑树列表信息
	 * @return
	 * @author 张健雄
	 */
	public List<DmLabelTreeDefVO> queryLabelEditTree();
	
	/**
	 * 通过ID值查询数据标签信息
	 * @param id ID值
	 * @return
	 * @author 张健雄
	 */
	public DmLabelTreeDefVO queryLabelDef(String id);
	
	/**
	 * 保存数据标签的简要信息（并新增一个树节点）
	 * @param id 节点ID
	 * @param name 节点名称
	 * @param pid 父节点ID
	 * @param fullPath 全路径名称
	 * @return 是否保存成功
	 * @author 张健雄
	 */
	public boolean saveTreeNode(String id,String name,String pid,String fullPath);
	
	/**
	 * 重命名节点属性
	 * @param id 节点ID
	 * @param name 节点新名称
	 * @param fullPath 节点全路径
	 * @return
	 * @author 张健雄
	 */
	public boolean renameNode(String id ,String name,String fullPath);
	
	/**
	 * 更新数据标签配置信息
	 * @param vo 数据标签配置信息对象
	 * @return 是否保存成功
	 * @author 张健雄
	 */
	public boolean updateTreeNode(DmLabelTreeDefVO vo);
	
	/**
	 * 通过ID删除数据标签信息
	 * @param id 数据标签的ID值
	 * @return
	 * @author 张健雄
	 */
	public boolean deleteLabelNode(String id);
	
}


