
package com.aspire.birp.modules.dataLabel.label.service;

import java.util.List;
import java.util.Map;

import com.aspire.birp.modules.base.vo.ProgressObject;
import com.aspire.birp.modules.dataLabel.label.vo.LabelTreeObject;

/**  
 * @Title: 数据标签用户分析功能服务接口类
 * @Description: 用于定义数据标签用户分析功能服务方法
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2016年5月5日 下午2:57:52
 * @version: V1.0
 */
public interface DlUserAnalysisService {

	/**
	 * 查询数据标签展示树的列表信息
	 * @return
	 * @author 张健雄
	 */
	public List<LabelTreeObject> queryLabelTree();
	
	/**
	 * 标签数据筛选运算服务方法
	 * @param searchKey 查询识别ID值
	 * @param labelIDs 标签ID集合
	 * @param values 标签筛选值集合
	 * @param times 标签数据周期集合
	 * @param searchNum 查询用户号码
	 * @return 返回标签筛选的结果集
	 * @author 张健雄
	 */
	public Map<String,Object> queryLabelData(String searchKey,List<String> labelIDs,List<String[]> values,List<String> times,String searchNum,int page,int rows);
	
	/**
	 * 通过表名来确定数据周期选择列表
	 * @param tableName 数据表名
	 * @return
	 * @author 张健雄
	 */
	public List<Map<String,String>> queryPeriodList(String tableName);
	
	/**
	 * 查询标签树数据表的默认数据周期
	 * @return
	 * @author 张健雄
	 */
	public List<Map<String,String>> queryLastPeriod();
	
	/**
	 * 通过标签ID与维表的值查询维表的值语义
	 * @param dimValue 维表的值
	 * @param labelID 标签ID
	 * @return
	 * @author 张健雄
	 */
	public String queryDimValue(String dimValue,String labelID);
	
	/**
	 * 通过用户定义的标签属性查询对应的分层信息
	 * @param pid 标签ID属性
	 * @return
	 * @author 张健雄
	 */
	public List<LabelTreeObject> queryDimLabel(String pid);
	
	/**
	 * 通过查询标识生成临时数据文件
	 * @param searchKey 数据文件标识
	 * @param total 数据总条数
	 * @return
	 * @author 张健雄
	 */
	public Map<String, String> saveTempData(String searchKey,int total);
	
	/**
	 * 通过查询标识生成临时数据文件(多线程执行)
	 * @param searchKey 数据文件标识
	 * @param total 数据总条数
	 * @return
	 * @author 张健雄
	 */
	public Map<String, String> saveTempForThread(String searchKey,int total);
	
	/**
	 * 通过查询KEY来查询文件导出进度状态
	 * @param searchKey
	 * @return
	 * @author 张健雄
	 */
	public ProgressObject queryLoadStatus(String searchKey);
	
}


