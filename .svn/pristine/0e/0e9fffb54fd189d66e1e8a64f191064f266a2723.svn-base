package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * Search类DAO
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月16日 下午3:26:48
 */
@Repository
public interface SearchDao {

	 /**
     * 预搜索户查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryPreSearchFamily(Map<String,Object> params);
    
    /**
     * 预搜索村查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryPreSearchCountry(Map<String,Object> params);
    
    /**
     * 根据户id查找村
     * @param params
     * @return
     */
    public Map<String,Object> queryCountryNameByFamId(String id);
    
    /**
     * 根据村id查找村
     * @param params
     * @return
     */
    public Map<String,Object> queryCountryNameById(String id);
    
    /**
     * 搜索结果贫困户概况
     * @param params
     * @return
     */
    public Map<String,Object> queryFamilyStatus(String id);
    
    /**
     * 搜索结果贫困户帮扶施策总概况
     * @param params
     * @return
     */
    public Map<String,Object> queryFamImplCount(String id);
    
    /**
     * 搜索结果贫困户帮扶施策详细(取前三条记录)
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryFamImplList(String id);
    
    /**
     * 判断这个村是否重点贫困村 1是 0不是 
     * @param params
     * @return
     */
    public int isFurtherPoor(String id);
    
    /**
     * 搜索结果相对贫困村概况
     * @param params
     * @return
     */
    public Map<String,Object> queryPKCountryStatus(String id);
    
    /**
     * 搜索结果分散村概况
     * @param params
     * @return
     */
    public Map<String,Object> queryFSCountryStatus(String id);
    
    /**
     * 搜索结果贫困村致贫原因
     * @param params
     * @return
     */
    public Map<String,Object> queryCouReasonList(Map<String,Object> params);
    
    /**
     * 搜索结果贫困村帮扶施策概况
     * @param params
     * @return
     */
    public Map<String,Object> queryCouImplCount(String id);
    
    /**
     * 搜索贫困村帮扶施策详细(取前三条记录)
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryCouImplList(String id);
    
    /**
     * 搜索结果上一级镇概况
     * @param params
     * @return
     */
    public Map<String,Object> queryTownStatus(String id);
    
    /**
     * 搜索结果村上一级镇致贫原因
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryTownReasonList(String id);

}
