
package com.aspire.birp.modules.smartQuery.query.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aspire.birp.modules.smartQuery.query.vo.SqListFilterInfoVO;



/**  
 * @Title: 列表过滤器功能服务接口类
 * @Description: 用于定义列表过滤器功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年3月23日 下午2:29:38
 * @version: V1.0
 */
public interface SqListFilterService {

	/**
	 * 查询当前用户的列表过滤器的列表信息
	 * @return
	 * @author 张健雄
	 */
	public List<SqListFilterInfoVO> queryOwnList();
	
	/**
	 * 通过列表名称查询当前用户的列表过滤器的列表信息
	 * @param listName 用于查询列表名称
	 * @return
	 * @author 张健雄
	 */
	public List<SqListFilterInfoVO> queryOwnList(String listName);
	
	/**
	 * 通过ID查询列表过滤器信息
	 * @param id 过滤器ID值
	 * @return
	 * @author 张健雄
	 */
	public SqListFilterInfoVO queryListFilter(String id);
	
	/**
	 * 通过ID集合删除对应的列表过滤器信息
	 * @param ids ID集合
	 * @return
	 * @author 张健雄
	 */
	public boolean deleteListFilter(List<String> ids);
	
	
	/**
	 * 上传列表过滤器配置信息
	 * @param id 列表过滤器ID（为空时则新增数据）
	 * @param listName 过滤器名称
	 * @param listSeparator 条目分隔符
	 * @param listFile 列表文件
	 * @return
	 * @author 张健雄
	 * @throws IOException 
	 */
	public boolean uploadList(String id,String listName,String listSeparator,MultipartFile listFile) throws IOException;
	
}


