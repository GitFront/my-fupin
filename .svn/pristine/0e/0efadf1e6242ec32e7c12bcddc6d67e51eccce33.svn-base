package com.aspire.birp.modules.dataMapping.mapping.service.impl;

import com.aspire.auth.common.utils.StringUtils;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.bi.common.util.DateUtils;
import com.aspire.bi.common.web.bean.easyui.DataGrid;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmColumnMapping;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMappingAssociation;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmTableMappingAssociationId;
import com.aspire.birp.modules.dataMapping.mapping.service.DmTableMappingAssociationService;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/16 14:49
 */
@Service
public class DmTableMappingAssociationServiceImpl extends SqBaseService implements DmTableMappingAssociationService {

    /**
     * 关联映射下拉框
     * @return
     */
    @Override
    public List<Map<String,String>> queryTablesForSelector() {
        String hql = "select new map(id as key,mappingName as value) from DmTableMapping ";
        List<Map<String,String>> tables = (List<Map<String,String>>) this.getHibernateTemplate().find(hql);
        Map<String,String> all = new HashMap<String,String>(2);
        all.put("key","-999");
        all.put("value", "全部");
        tables.add(0, all);
        return tables;
    }

    /**
     * 校验新增关联关系是否符合要求
     * @param sourceId
     * @param targetId
     * @return
     */
    @Override
    public Map<String,Object> checkAssociation(String sourceId,String targetId) {
        Map<String,Object> result = new HashMap<String, Object>(8);
        boolean success = false;
        String msg = null;
        if(StringUtils.isEmpty(sourceId) || StringUtils.isEmpty(targetId)) { //传过来的Id存在空值
            msg = "关联数据不正确";
        }else {
            if(sourceId.equals(targetId)) { //字段一样
                msg = "关联的两个字段不能是同一张表的同一个字段";
            }else {
                DmColumnMapping source = getDmColumnMappingDAO().findById(sourceId);
                DmColumnMapping target = getDmColumnMappingDAO().findById(targetId);
                if(source.getMpTableId().equals(target.getMpTableId())) { //表相同
                    msg = "不同关联同属于一张表的两个字段";
                }else {
                    if(source.getColumnName().equalsIgnoreCase("STAT_TIME") || target.getColumnName().equalsIgnoreCase("STAT_TIME")) {
                        success = true;
                        result.put("source",source);
                        result.put("target",target);
                    }else if(!source.getColumnType().equals(target.getColumnType())) { //字段相同
                        msg = "关联的两个字段的字段类型不相同";
                    }else{  //判断是否已经拥有了映射关系
                        StringBuffer hql = new StringBuffer("from DmTableMappingAssociation where ");
                        hql.append("(id.mpTableId1 = ? and id.mpTableId2 = ?) ");
                        hql.append("or (id.mpTableId2 = ? and id.mpTableId1 = ?) ");
                        List<DmTableMappingAssociation> dmTableMappingAssociations = (List<DmTableMappingAssociation>) getHibernateTemplate().find(hql.toString(), source.getMpTableId(), target.getMpTableId(),
                                source.getMpTableId(), target.getMpTableId());
                        if(dmTableMappingAssociations.size() != 0) {
                            msg = "两张表只能有一个映射关系";
                        }else {
                            success = true;
                            result.put("source",source);
                            result.put("target",target);
                        }
                    }
                }
            }
        }
        result.put("success",success);
        result.put("msg", msg);
        return result;
    }

    /**
     * 保存关联关系
     * @param source
     * @param target
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void saveAssociation(DmColumnMapping source,DmColumnMapping target) {
        DmTableMappingAssociation dmTableMappingAssociation = new DmTableMappingAssociation();
        //设置Id
        DmTableMappingAssociationId id = new DmTableMappingAssociationId();
        id.setMpTableId1(source.getMpTableId());
        id.setMpTableId2(target.getMpTableId());
        dmTableMappingAssociation.setId(id);

        //设置表1属性
        dmTableMappingAssociation.setMpColumnId1(source.getId());
        dmTableMappingAssociation.setMpColumnCode1(source.getColumnName());
        dmTableMappingAssociation.setMpTableCode1(source.getMpTable());

        //设置表2属性
        dmTableMappingAssociation.setMpColumnCode2(target.getColumnName());
        dmTableMappingAssociation.setMpColumnId2(target.getId());
        dmTableMappingAssociation.setMpTableCode2(target.getMpTable());

        User user = UserUtils.getUser();
        dmTableMappingAssociation.setCreator(user.getId());
        dmTableMappingAssociation.setCreateTime(DateUtils.dateToTimestamp(new Date()));

        getHibernateTemplate().save(dmTableMappingAssociation);
    }

    /**
     * 删除关联关系
     * @param source
     * @param target
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void deleteAssociation(String source, String target) {
        StringBuffer hql = new StringBuffer("from DmTableMappingAssociation where ");
        hql.append("id.mpTableId1 = ? and id.mpTableId2 = ? ");
        List<DmTableMappingAssociation> list = (List<DmTableMappingAssociation>) getHibernateTemplate().find(hql.toString(), source, target);
        if(list.size() != 0) {
            getHibernateTemplate().delete(list.get(0));
        }
    }

    /**
     *  @param source
     * @param target
     */
    @Override
    public Map<String, Object> deleteCheck(String source, String target) {
        Map<String,Object> result = new HashMap<String, Object>(2);
        boolean success = false;
        String msg = null;
        if(!isTableMappingAssociations(source, target)) {
            success = true;
        }else {
            msg = "关联关系已经被使用了，无法删除";
        }
        result.put("success",success);
        result.put("msg", msg);
        return result;
    }

    /**
     * 关联列表查询
     * @param table1
     * @param table2
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public DataGrid queryAssociations(String table1, String table2, int page, int pageSize) {
        List<String> params = new LinkedList<String>();
        String commonQuery = getCommonQuery(table1,table2,params);
        DataGrid dataGrid = new DataGrid();
        long total = countAssociations(commonQuery,params);
        dataGrid.setTotal(total);
        if(total > 0) {
            dataGrid.setRows(queryAssociations(commonQuery,params,page,pageSize));
        }else {
            dataGrid.setRows(new ArrayList(0));
        }
        return dataGrid;
    }

    /**
     * 公共sql查询
     * @param table1
     * @param table2
     * @param params
     * @return
     */
    private String getCommonQuery(String table1,String table2,List<String> params) {
        final String ALL_VALUE = "-999";
        StringBuilder commonQuery = new StringBuilder();
        if(StringUtils.isNotEmpty(table1) && StringUtils.isNotEmpty(table2)
                && !table1.equals(ALL_VALUE) && table2.equals(ALL_VALUE)) {
            commonQuery.append(" and (tma.id.mpTableId1 = ? or tma.id.mpTableId2 = ?) ");
            params.add(table1);
            params.add(table1);
        }
        if(StringUtils.isNotEmpty(table1) && StringUtils.isNotEmpty(table2)
                && table1.equals(ALL_VALUE) && !table2.equals(ALL_VALUE)) {
            commonQuery.append("  and (tma.id.mpTableId2 = ? or tma.id.mpTableId1 = ?) ");
            params.add(table2);
            params.add(table2);
        }
        if(StringUtils.isNotEmpty(table1) && StringUtils.isNotEmpty(table2)
                && !table1.equals(ALL_VALUE) && !table2.equals(ALL_VALUE)) {
            commonQuery.append(" and ((tma.id.mpTableId1 = ? and tma.id.mpTableId2 = ?) ");
            commonQuery.append(" or (tma.id.mpTableId2 = ? and tma.id.mpTableId1 = ?))");
            params.add(table1);
            params.add(table2);
            params.add(table1);
            params.add(table2);
        }
        return commonQuery.toString();
    }

    /**
     * 计算列表总数
     * @param commonQuery
     * @param param
     * @return
     */
    private long countAssociations(String commonQuery,List<String> param) {
        StringBuilder hql = new StringBuilder();
        hql.append("select count(*) as total from DmTableMappingAssociation tma ");
        List<Long> total;
        if(StringUtils.isNotBlank(commonQuery)) {
            hql.append(" where 1=1 ");
            hql.append(commonQuery);
            total = excuteQuery(hql.toString(),param.toArray(),1,10);
        }else {
            total = (List<Long>) getHibernateTemplate().find(hql.toString());
        }
        return total.get(0);
    }

    /**
     * 查询关联列表数据
     * @param commmonQuery
     * @param params
     * @param page
     * @param pageSize
     * @return
     */
    private List<Map> queryAssociations(String commmonQuery,List<String> params,int page,int pageSize) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new Map(tma.id.mpTableId1 as mt1,tma.id.mpTableId2 as mt2,stm.mappingName as tn1,");
        hql.append("ttm.mappingName as tn2,scm.mappingName as cn1,tcm.mappingName as cn2) ");
        hql.append(" from DmTableMappingAssociation tma,DmTableMapping stm,DmTableMapping ttm, ");
        hql.append(" DmColumnMapping scm,DmColumnMapping tcm ");
        hql.append(" where tma.id.mpTableId1 = stm.id and tma.id.mpTableId2 = ttm.id ");
        hql.append(" and tma.mpColumnId1 = scm.id and tma.mpColumnId2 = tcm.id ");
        hql.append(commmonQuery);
        hql.append(" order by tma.createTime desc ");
        List<Map> list = excuteQuery(hql.toString(),params.toArray(),page,pageSize);
        return list;
    }
}
