package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

public interface FiveLowDao {
	
	/**
	 * 五保低保政策落实管理
	 * */
	public List<Map<String,Object>> flveLowGovern(Map<String,Object> params);
	/**
	 * 五保低保政策落实管理 合计
	 * */
	public Map<String,Object> flveLowGovernCount(Map<String,Object> params);
	
	/**
	 * 落实分析>低保五保明细
	 * */
	public List<Map<String,Object>> flveLowDetail(Map<String,Object> params);
	/**
	 *落实分析>低保五保明细总数
	 * */
	public Long flveLowDetailCount(Map<String,Object> params);
	/**
	 * 五保低保政策落实分析
	 * */
	public List<Map<String,Object>> flveLowAnalyze(Map<String,Object> params);
	
	/**
	 * 五保低保政策落实分析 合计
	 * */
	public Map<String,Object> flveLowAnalyzeCount(Map<String,Object> params);
	/**
	 * 落实排序>区域数据
	 * */
	public List<Map<String,Object>> flveLowArea(Map<String,Object> params);
	
	/**
	 * 落实排序 
	 * */
	public List<Map<String,Object>> flveLowSort(Map<String,Object> params);
	/**
	 * 落实排序 总数
	 * */
	public Long flveLowSortCount(Map<String,Object> params);
	
	/**
	 *低保五保政策落实(户数) 统计图
	 * */
	public List<Map<String,Object>> flveLowGovernHu(Map<String,Object> params);
	
	/**
	 *低保五保政策落实(人数) 统计图
	 * */
	public List<Map<String,Object>> flveLowGovernRen(Map<String,Object> params);
	
	/**
	 *低保五保政策落实趋势(人数） 统计图
	 * */
	public List<Map<String,Object>> flveLowGovernQu(Map<String,Object> params);
	
	/**
	 *当前低保五保政策落实(人数) 统计图
	 * */
	public List<Map<String,Object>> flveLowGovernCurr(Map<String,Object> params);
	
}
