
package com.aspire.birp.modules.dataMapping.mapping.service;

import java.util.List;
import java.util.Map;

import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableCatalog;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMapping;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmDatabaseColumn;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmDatabaseTable;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableCatalogVO;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmTableMappingVO;


/**  
 * @Title: 映射管理功能服务接口类
 * @Description: 用于定义映射管理功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2015年9月15日 下午2:29:38
 * @version: V1.0
 */
public interface DmMappingService {

	/**
	 * 查询数据库或配置映射信息
	 * @param id 配置表ID值
	 * @param tableName 数据库或配置映射表名
	 * @return 返回宽表列表信息,id,table,comment
	 * @author 张健雄
	 */
	public Map<String, String> queryWideTableInfo(String id,String tableName);
	
	/**
	 * 查询数据库的宽表列表信息
	 * @param keyword 过滤的前缀关键字
	 * @return 返回宽表列表信息
	 * @author 张健雄
	 * @param tableName 
	 */
	public List<DmDatabaseTable> queryWideTableList(String keyword, String tableName);
	
	/**
	 * 通过数据库表来查询对应列信息
	 * @param table 数据表名
	 * @return 返回该数据库表来查询对应列信息
	 * @author 张健雄
	 */
	public List<DmDatabaseColumn> queryDatabaseColumnList(String table);
	
	/**
	 * 查询已配置映射表的列表信息
	 * @return 返回已配置映射表的列表信息
	 * @author 张健雄
	 * @param tableName 
	 */
	public List<DmTableMappingVO> queryTableMappingList(String mappingName);
	
	/**
	 * 查询已配置维度映射表的列表信息
	 * @return 返回已配置映射表的列表信息
	 * @author 张健雄
	 */
	public List<DmTableMappingVO> queryDimMappingList();
	
	/**
	 * 通过目录ID查询已配置映射表的列表信息
	 * @param 目录ID（如果ID为空时，则返回没有目录的宽表列表）
	 * @return 返回已配置映射表的列表信息
	 * @author 张健雄
	 * @param rootId 
	 */
	public List<DmTableMappingVO> queryTableMappingListByCatalog(String catalogId, String type);
	
	/**
	 * 通过映射表ID查询表名信息
	 * @param id 映射表ID
	 * @return 返回已配置映射表的数据表信息
	 * @author 张健雄
	 */
	public String queryTableMappingName(String id);
	
	/**
	 * 通过映射表ID查询表映射对象
	 * @param id 映射表ID
	 * @return 返回已配置映射表的数据信息
	 * @author 张健雄
	 */
	public DmTableMappingVO queryTableMappingById(String id);
	
	/**
	 * 查询已配置映射表的数据列信息
	 * @param mpTableId 映射表ID值
	 * @return 返回已配置映射表的数据列信息
	 * @author 张健雄
	 */
	public List<DmColumnMappingVO> queryColumnMappingList(String mpTableId);
	
	/**
	 * 查询尚未选择的映射数据列信息
	 * @param mpTableId 映射表ID值
	 * @param sqId 查询ID值
	 * @param isTemp 是否为临时数据
	 * @return 返回已配置映射表的数据列信息
	 * @author 张健雄
	 */
	public List<DmColumnMappingVO> queryColumnMappingList(String mpTableId,String sqId,String isTemp);
	
	
	/**
	 * 通过映射列ID查询列名信息
	 * @param id 映射列ID
	 * @return 返回已配置映射表的数据列信息
	 * @author 张健雄
	 */
	public String queryColumnMappingName(String id);
	
	
	
	/**
	 * 保存配置映射表的属性信息
	 * @param dmTableMapping
	 * @return
	 */
	public String saveTableMapping(DmTableMapping dmTableMapping);
	
	/**
	 * 更新配置映射表的属性信息
	 * @param id 映射ID值
	 * @param mapping 映射名
	 * @param isPerimssion 是否包含权限数据
	 * @return 返回是否更新成功
	 * @author 张健雄
	 * @param storageType 
	 * @param tableType 
	 */
	public boolean updateTableMapping(String id,String mapping,String isPerimssion,String isDim, String tableType, String storageType);
	
	/**
	 * 更新配置映射表的属性信息
	 * @param tmids 绑定目录的表映射ID集
	 * @param catalogId 目录ID值，如果为空时，则为解绑目录
	 * @return 返回是否绑定成功
	 * @author 张健雄
	 * @param on 
	 */
	public boolean updateTableMappings(String tmids,String catalogId, Boolean on);
	
	/**
	 * 删除配置映射表的属性信息
	 * @param id 映射ID值
	 * @return 返回是否删除成功
	 * @author 张健雄
	 */
	public boolean removeTableMapping(String id);
	
	/**
	 * 保存配置映射表的列属性信息
	 * @param mpTableId 映射表ID
	 * @param mpTableName 映射表名
	 * @param columns 保存的列信息
	 * @return 返回是否保存成功
	 * @author 张健雄
	 */
	public boolean saveColumnMappings(String mpTableId,String mpTableName,List<DmDatabaseColumn> columns);	
	
	
	/**
	 * 更新配置映射表的列属性信息
	 * @param id 映射列ID
	 * @param mapping 映射列名
	 * @param columnType 映射列类型
	 * @return 返回是否更新成功
	 * @author 张健雄
	 * @param isKeys 
	 */
	public boolean updateColumnMappings(String id,String mapping,String columnType, String isKeys);	
	
	/**
	 * 删除配置映射表的列属性信息
	 * @param idList 需要删除的ID信息集
	 * @return 返回是否保存成功
	 * @author 张健雄
	 */
	public boolean removeColumnMappings(List<String> idList);
	
	/**
	 * 查询宽表目录的列表信息
	 * @return 返回宽表目录列表信息
	 * @author 张健雄
	 * @param id 
	 */
	public List<DmTableCatalogVO> queryTableCatalogList(String id);
	
	/**
	 * 通过ID来查询宽表目录的信息
	 * @return 返回宽表目录信息
	 * @author 张健雄
	 */
	public DmTableCatalogVO queryTableCatalogInfo(String id);
	
	/**
	 * 保存宽表目录信息
	 * @param id 目录ID
	 * @param name 目录名称
	 * @param pId 父目录ID
	 * @param fullPath 目录全路径
	 * @return 返回是否成功保存目录信息
	 * @author 张健雄
	 * @param type 
	 */
	public boolean saveTableCatalog(String id,String name,String pId,String fullPath, String type);
	
	/**
	 * 重命名宽表目录信息
	 * @param id 目录ID
	 * @param name 目录名称
	 * @param fullPath 全路径信息
	 * @return 返回是否成功更新目录信息
	 * @author 张健雄
	 * @param root 
	 */
	public boolean updateTableCatalog(String id,String name,String fullPath);
	
	/**
	 * 删除宽表目录信息
	 * @param id 目录ID
	 * @return 返回是否成功删除目录信息
	 * @author 张健雄
	 */
	public boolean removeTableCatalog(String id);
	
	/**
	 * 是否可以删除宽表目录信息
	 * @param id 目录ID
	 * @return 返回是否可以删除目录信息
	 * @author 张健雄
	 */
	public boolean isremoveTableCatalog(String id);

	public List<Map<String, Object>> selectThemeByType();

	public Object[] saveTheme(DmTableCatalog dmTableCatalog);


	


}


