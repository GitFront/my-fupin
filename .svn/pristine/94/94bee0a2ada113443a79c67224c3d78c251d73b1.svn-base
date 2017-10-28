package com.aspire.birp.modules.common.service;

import java.util.List;
import java.util.Map;

public interface PovertyGeometryService {
	
	/**
	 * 根据上级pac查询地图json数据
	 * @param pac
	 * @throws Exception 
	 */
	public	String  queryDataMap(String pac,String level) throws Exception;
	/**
	 * 查询坐标点
	 * */
	public	String  queryDataPiont() throws Exception;
	
	/**
	 * 国土pac转建档立卡pac
	 * @pram 国土pac
	 * */
	public String lsbPacToPac(String pac);
	
	/**
	 * 建档立卡pac转国土pac
	 * @pram 建档立卡pac
	 * */
	public Map<String,String> pacToLsbPac(String pac);
	/**
	 * 是否相对贫困村 下钻时用
	 * @pram 国土pac
	 * */
	public String ifFurthurPoor(String pac,String level);
	
	/**
	 * 是否相对贫困村 到村时用
	 * @pram 建档立卡pac
	 * */
	public String ifPoor(String pac,String level);
	/**
	 * 判断当前pac所属层级 转化返回所有父级
	 * @pram 建档立卡pac
	 * */
	public Map<String,Object> ifLevel(String pac,String level);
	
	/**
	 * 通过PAC获取区域名称
	 * @param pac
	 * @return
	 * @author 张健雄
	 */
	public String queryAreaNameByPac(String pac);
	
	/**
	 * 查询首页地图数据
	 * @param pac
	 * @throws Exception 
	 */
	public	List<Map<String,Object>>   queryIndexMap() ;
}
