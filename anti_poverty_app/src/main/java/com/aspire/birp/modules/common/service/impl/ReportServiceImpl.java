package com.aspire.birp.modules.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.birp.modules.common.service.ReportService;
import com.aspire.birp.modules.common.vo.GroupDataVo;
import com.aspire.birp.modules.common.vo.InvertedLineVo;
import com.aspire.birp.modules.common.vo.PieDataVo;

/**
 * Title: 用于封装图表数据读取的通用服务方法实现类 <br>
 * Description: 主要用于读取Bar\StackBars\Line\TimeSeriesLine等图表的数据列表，并完成数据格式转换 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年7月9日 下午17:45:03 <br>
 *
 */

@Service
public class ReportServiceImpl implements ReportService {
	
	private static Logger log = Logger.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	/*对应的mapper的KEY参数*/
	private final static String KEY_NAME = "name";
	private final static String KEY_GROUP = "group";
	private final static String KEY_VALUE = "value";
	
	private final static String KEY_X = "x";
	private final static String KEY_Y = "y";
	
	/**
	 * 普通数据列表的查询
	 * @param queryName mybatis的通用查询
	 * @param params 查询参数信息
	 * @return 返回数据列表
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Map<String, Object>> queryList(String queryName,
			Map<String, Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		/*参数格式化*/
		if(StringUtils.isBlank(queryName)) return list;
		
		if(params == null) params = new HashMap<String, Object>();
		/*数据查询*/
		list = sqlSessionTemplate.selectList(queryName, params);
		log.info("通用数据列表查询");
		
		return list;
	}
	
	/**
	 * 查询饼状图的数据集合
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param rowsValue 行转列参数
	 * @return 返回饼状图的数据列表
	 * @author 张健雄
	 */
	@Override
 	public List<PieDataVo> queryPieData(String queryName, Map<String, Object> params,Map<String,Object> mapper,Map<String,String> rowsValue) {
 		List<PieDataVo> vos = new ArrayList<PieDataVo>();
 		
 		String key1 = convMapperKey(KEY_NAME,mapper);
 		String key2 = convMapperKey(KEY_VALUE,mapper);
		
		List<Map<String, Object>> dataRow  = sqlSessionTemplate.selectList(queryName, params);
		if(dataRow != null && dataRow.size() != 0){
			
			if(rowsValue != null && !rowsValue.isEmpty()){
				Iterator<Map.Entry<String,String>> it = rowsValue.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String,String> entry = it.next();
					String dimCode = entry.getKey();
					String dimName = entry.getValue();
					Double value = 0d;
					for (Map<String, Object> map : dataRow) {
						if(map.containsKey(dimCode)){
							value += Double.parseDouble(String.valueOf(map.get(dimCode)));
						}
					}
					vos.add(new PieDataVo(dimName,value));					
				}
			}else{
				for (Map<String, Object> map : dataRow) {
					vos.add(new PieDataVo(map.get(key1),map.get(key2)));
				}
			}
					
		}
		log.info("通用饼状图查询");
  	 	return vos;
	}
	
	/* (non-Javadoc)
	 * @see com.aspire.birp.modules.common.service.ReportService#queryMapData(java.lang.String, java.util.Map, java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryMapData(String queryName,
			Map<String, Object> params, Map<String, Object> mapper,String joinBy) {
		List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
 		
 		List<Map<String, Object>> dataRow  = sqlSessionTemplate.selectList(queryName, params);
		if(dataRow != null && dataRow.size() != 0){
			for (Map<String, Object> map : dataRow) {
				/*处理key的对应关系*/
				Set<String> keys_ = map.keySet();
				if(mapper != null && !mapper.isEmpty()){
					for (String key : mapper.keySet()){
						if(keys_.contains(mapper.get(key))){
							String mkey = key;
							if("KEY".equalsIgnoreCase(key)) mkey = joinBy;
							map.put(mkey, map.get(mapper.get(key)));
							map.remove(mapper.get(key));
						}
					}
					rows.add(map);
				} else {
					rows.add(map);
				}
			}		
		}
		log.info("通用地图查询");
		
		return rows;
	}
	
	/**
	 * 查询分组数据集合，包括柱状图、折线图等
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param hideGroup 隐藏分组配置项
	 * @param rowsGroup 行转列参数
	 * @return 返回分组数据列表
	 * @author 张健雄
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<GroupDataVo> queryGroupData(String queryName, Map<String, Object> params,Map<String,Object> mapper,
			String[] hideGroup,Map<String,String> rowsGroup) {
		List<GroupDataVo> vos = new ArrayList<GroupDataVo>();
		List<String> hide = Arrays.asList(hideGroup);
		String key1 = convMapperKey(KEY_NAME,mapper);
 		String key2 = convMapperKey(KEY_VALUE,mapper);
		String key3 = convMapperKey(KEY_GROUP,mapper);
		
		List<Map<String, Object>> dataRow  = sqlSessionTemplate.selectList(queryName, params);
		
		if(dataRow != null && dataRow.size() != 0){
			if(rowsGroup.isEmpty()){
				for (Map<String, Object> map : dataRow) {
					String groupName = String.valueOf(map.get(key3));
					if(hide.contains(groupName)){
						groupName += "|hide";
					}
					vos.add(new GroupDataVo(map.get(key1),groupName,map.get(key2)));
				}
			}else{
				/*行转列配置项*/
				for (Map<String, Object> map : dataRow) {
					Iterator<Map.Entry<String,String>> it = rowsGroup.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String,String> entry = it.next();
						String group = entry.getKey();
						String groupName = entry.getValue();
						if(map.containsKey(group)){
							Object name = map.get(key1);
							Object value = map.get(group);
							if(hide.contains(groupName)) groupName += "|hide";
							vos.add(new GroupDataVo(name,groupName,value));
						}
					}			
				}
				
			}				
		}
		log.info("通用分组数据列表查询");
  	 	return vos;
	}
	
	/**
	 * 查询info表格的数据集合
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @return 返回info表格的数据集合
	 * @author 张健雄
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> queryInfoTableData(String queryName,
			Map<String, Object> params, Map<String, Object> mapper) {
		
		Map<String,Object> data = new HashMap<String, Object>();
		
		List<Map<String, Object>> dataRow  = sqlSessionTemplate.selectList(queryName, params);
		
		if(dataRow != null && dataRow.size() != 0){
			data = dataRow.get(0);
			/*处理key的对应关系*/
			if(mapper != null && !mapper.isEmpty()){
				/*用于映射关系的key*/
				Set<String> keys_ = data.keySet();
				for (String key : mapper.keySet()){
					if(keys_.contains(mapper.get(key))){
						data.put(key, data.get(mapper.get(key)));
						data.remove(mapper.get(key));
					}
				}
			}
		}			
		log.info("通用信息表格数据查询");
		return data;
	}
	
	/**
	 * 查询垂直折线数据集合，包括柱状图、折线图等
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param hideGroup 隐藏分组配置项
	 * @return 返回饼状图的数据列表
	 * @author 张健雄
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<InvertedLineVo> queryInvertedLineData(String queryName,
			Map<String, Object> params, Map<String, Object> mapper,String[] hideGroup) {
		
		List<InvertedLineVo> vos = new ArrayList<InvertedLineVo>();
		List<String> hide = Arrays.asList(hideGroup);
		String key1 = convMapperKey(KEY_X,mapper);
 		String key2 = convMapperKey(KEY_Y,mapper);
		String key3 = convMapperKey(KEY_GROUP,mapper);
		
		List<Map<String, Object>> dataRow  = sqlSessionTemplate.selectList(queryName, params);
		
		if(dataRow != null && dataRow.size() != 0){
			for (Map<String, Object> map : dataRow) {
								
				String groupName = String.valueOf(map.get(key3));
				if(hide.contains(groupName)){
					groupName += "|hide";
				}
				vos.add(new InvertedLineVo(map.get(key2),groupName,map.get(key1)));
			}		
		}	
		log.info("通用垂直折线图数据查询");
		return vos;
	}
	
	/**
	 * 查询仪表盘数据集合
	 * @param queryName mybatis的查询名称
	 * @param params 查询参数
	 * @param mapper 参数名称对应集合
	 * @param decimal 数据精度
	 * @return 返回仪表盘的数据列表
	 * @author 张健雄
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Double queryGaugeData(String queryName, Map<String, Object> params,
			Map<String, Object> mapper,Integer decimal) {
		Double value = 0d;
		
		String key1 = convMapperKey(KEY_VALUE,mapper);
		
		List<Map<String, Object>> dataRow  = sqlSessionTemplate.selectList(queryName, params);
		if(dataRow != null && dataRow.size() != 0){
			Map<String, Object> map = dataRow.get(0);
			value  = Double.parseDouble(String.valueOf(map.get(key1)));
		}		
		log.info("通用仪表盘数据查询");
		return round(value,decimal);
	}
	
	/**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){
        if(scale<0){
        	/*默认为2位小数*/
        	scale = 2;
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 通过key的对应关系转换key的值
     * @param key
     * @param mapper
     * @return
     * @author 张健雄
     */
    public static String convMapperKey(final String key,Map<String,Object> mapper){
    	if(mapper == null || mapper.isEmpty()) 
    		return key.toUpperCase();
    	String key_ = "";
    	if(mapper.containsKey(key)) {
    		key_ = (String) mapper.get(key);
    	}else{
    		key_ = key.toUpperCase();
    	}
    	return key_;
    }

}
