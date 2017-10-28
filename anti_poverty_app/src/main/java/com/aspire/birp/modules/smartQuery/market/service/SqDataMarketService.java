
package com.aspire.birp.modules.smartQuery.market.service;

import java.util.List;
import java.util.Map;

import com.aspire.birp.modules.smartQuery.base.constant.OptionType;
import com.aspire.birp.modules.smartQuery.base.vo.CombotreeObject;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileCatalogTree;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileDataInfoVO;
import com.aspire.birp.modules.smartQuery.market.vo.SqFileTimeTree;
import com.aspire.birp.modules.smartQuery.market.vo.SqPersonalCatalogVO;

/**  
 * @Title: 数据超市管理接口类
 * @Description: 用于定义数据文件浏览及权限方法定义
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年10月27日 上午11:02:01
 * @version: V1.0
 */

public interface SqDataMarketService {

	/**
	 * 查询个人目录的列表信息
	 * @return 返回个人目录列表信息
	 * @author 张健雄
	 */
	public List<SqPersonalCatalogVO> queryPersonalCatalogList();
	
	/**
	 * 通过ID来查询个人目录的信息
	 * @return 返回个人目录信息
	 * @author 张健雄
	 */
	public SqPersonalCatalogVO queryPersonalCatalogInfo(String id);
	
	/**
	 * 保存个人目录信息
	 * @param id 生成ID值
	 * @param name 目录名称
	 * @param pId 父目录ID
	 * @param fullPathId 全路径ID信息
	 * @param fullPath 全路径信息
	 * @return
	 * @author 张健雄
	 */
	public boolean savePersonalCatalog(String id, String name, String pId,String fullPathId,String fullPath);
	
	/**
	 * 重命名个人目录信息
	 * @param id 目录ID
	 * @param name 目录名称
	 * @param fullPath 全路径信息
	 * @return 返回是否成功更新目录信息
	 * @author 张健雄
	 */
	public boolean updatePersonalCatalog(String id, String name,String fullPath);
	
	/**
	 * 删除个人目录信息
	 * @param id 目录ID
	 * @return 返回是否成功删除目录信息
	 * @author 张健雄
	 */
	public boolean removePersonalCatalog(String id);
	
	/**
	 * 是否可以删除个人目录信息
	 * @param id 目录ID
	 * @return 返回是否可以删除目录信息
	 * @author 张健雄
	 */
	public boolean isremovePersonalCatalog(String id);
	
	/**
	 * 查询个人目录的列表信息（easyui的combotree对象）
	 * @return 返回个人目录列表信息
	 * @author 张健雄
	 */
	public List<CombotreeObject> queryPersonalCatalogListByCombotree();
	
	/**
	 * 分页查询分享用户列表
	 * @param ids 已分享的用户ID集合(id1,id2,id3)
	 * @param filter 用户过滤信息
	 * @param page 页数
	 * @param pageSize 每页多少行
	 * @return
	 * @author 张健雄
	 */
	public Map<String,Object> queryShareUserList(String userids,String filter,int page,int pageSize);
	
	/**
	 * 保存临时数据文件
	 * @param sqId 查询ID
	 * @param isTemp 是否为临时数据
	 * @param fileName 文件名称
	 * @param personalCatalogId 保存个人路径信息
	 * @param userids 需要分享的用户ID
	 * @return
	 * @author 张健雄
	 */
	public boolean saveDataFile(String sqId,String isTemp,String fileName,String personalCatalogId,List<String> userids);
	
	/**
	 * 保存临时数据文件
	 * @param fileId 数据文件ID
	 * @param userids 需要分享的用户ID
	 * @return
	 * @author 张健雄
	 */
	public boolean saveFileShare(String fileId,String[] userids);
	
	/**
	 * 通过ID获取数据文件信息
	 * @param fileId
	 * @return
	 * @author 张健雄
	 */
	public SqFileDataInfoVO queryFileInfoById(String fileId);
	
	/**
	 * 通过ID删除数据文件信息
	 * @param fileId
	 * @return
	 * @author 张健雄
	 */
	public boolean deleteFileInfoById(String fileId);
	
	/**
	 * 通过ID校验数据文件是否有效
	 * @param fileId
	 * @return
	 * @author 张健雄
	 */
	public boolean checkFileInfoById(String fileId);
	
	/**
	 * 获取当前用户的权限访问文件列表
	 * @return
	 * @author 张健雄
	 */
	public List<SqFileDataInfoVO>  queryFileList();
	
	/**
	 * 根据个人目录ID获取当前用户的权限访问文件列表
	 * @param catalogId
	 * @return
	 * @author 张健雄
	 */
	public List<SqFileDataInfoVO>  queryFileListByCatalog(String catalogId);
	
	/**
	 * 根据创建时间获取当前用户的权限访问文件列表
	 * @param time 时间字符
	 * @return
	 * @author 张健雄
	 */
	public List<SqFileDataInfoVO>  queryFileListByTime(String time,String timeType);
	
	/**
	 * 根据自动任务获取当前用户的权限访问文件列表
	 * @param taskId 任务ID
	 * @return
	 * @author 张健雄
	 */
	public List<SqFileDataInfoVO>  queryFileListByTask(String taskId);
	
	/**
	 * 查询数据超市文件目录树相关信息
	 * @return
	 * @author 张健雄
	 */
	public List<SqFileCatalogTree> queryFileCatalogTree();
	
	/**
	 * 查询数据超市文件时间树相关信息
	 * @return
	 * @author 张健雄
	 */
	public List<SqFileTimeTree> queryFileTimeTree();
	
	/**
	 * 数据超市日志记录接口
	 * @param type 日志类型
	 * @param lp 描述参数文件
	 * @author 张健雄
	 */
	public void marketLogger(OptionType type,Map<String,String> lp);
	/**
	 * 查询已分享用户列表
	 * @param fileId
	 * @return 返回用户ID列表
	 * @author 张健雄
	 */
	public List<String> queryUserSelected(String fileId);
	
}


