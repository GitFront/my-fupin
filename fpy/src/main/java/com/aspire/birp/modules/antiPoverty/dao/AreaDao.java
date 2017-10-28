package com.aspire.birp.modules.antiPoverty.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 区域数据查询
 * 
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月12日 下午3:59:20
 */
@Repository
public interface AreaDao {

    /**
     * 查询相关区域的id和名称
     * @param 区域ID
     * @return
     */
    public Map<String,Object> queryAreaById(Map<String,Object> params);

    /**
     * 查询相关区域子区域列表
     * @param 区域ID
     * @return
     */
    public List<Map<String,Object>> querySubAreaList(Map<String,Object> params);
    
    /**
     * 查询相关区域子区域列表
     * @param 区域ID
     * @return
     */
    public List<Map<String,Object>> querySubAreaListByUserPac(Map<String,Object> params);
    
    /**
     * 查询上一级区域子区域ID
     * @param 区域ID
     * @return
     */
    public String queryParentId(String id);
    
    /**
     * 查询区域scope属性
     * @param 区域ID
     * @return
     */
    public Map<String,Object> queryAreaScope(Map<String,Object> params);
}
