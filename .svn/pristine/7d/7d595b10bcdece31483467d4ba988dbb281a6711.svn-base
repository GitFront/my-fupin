package com.aspire.birp.modules.dataMapping.mapping.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.auth.common.utils.StringUtils;
import com.aspire.bi.common.web.bean.easyui.DataGrid;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmColumnMapping;
import com.aspire.birp.modules.dataMapping.mapping.service.DmMappingService;
import com.aspire.birp.modules.dataMapping.mapping.service.DmTableMappingAssociationService;
import com.aspire.birp.modules.dataMapping.mapping.vo.DmColumnMappingVO;
import com.aspire.birp.modules.smartQuery.query.service.SqQueryService;
import com.aspire.birp.modules.smartQuery.query.vo.SqTableMappingTree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 数据表关联映射管理后台处理
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/16 14:05
 */
@Controller
@RequestMapping(value = "${adminPath}/dataMapping/tableMappingAssociation")
public class DmTableMappingAssociationController {

    private static Logger log = LoggerFactory.getLogger(DmTableMappingAssociationController.class);

    /**
     * 用于获取所有表的树形结构
     */
    @Autowired
    private SqQueryService sqQueryService;

    /**
     * 用于查询某个表的列
     */
    @Autowired
    private DmMappingService sqMappingService;

    @Autowired
    private DmTableMappingAssociationService dmTableMappingAssociationService;

    /**
     * 数据表关联映射管理页面
     * @return
     */
    @RequestMapping("main.do")
    public String main(Model model) {
/*        List<SqTableMappingTree> sqTableMappingTrees = queryTableMappingTree();
        ObjectMapper objectMapper = new ObjectMapper();
        String treeData = "[]";
        try {
            treeData = objectMapper.writeValueAsString(sqTableMappingTrees);
        } catch (JsonProcessingException e) {
            log.error("关联映射表树数据转JSON失败",e);
        }
        model.addAttribute("treeData", treeData);*/
        return "/modules/data_mapping/mapping/tableassociation";
    }

    /**
     * 关联管理的所有表的树形结构
     * @return
     */
    @RequestMapping("queryTableMappingTree.do")
    @ResponseBody
    public List<SqTableMappingTree> queryTableMappingTree(String type) {
        List<SqTableMappingTree> list = sqQueryService.queryTableMappingTree(type);
       /* if(list.size() != 0) {
            list.get(0).setOpen(true);
        }*/
        return list;
    }

    /**
     * 根据表名查找列
     * @param id
     * @return
     */
    @RequestMapping("queryTableColums.do")
    @ResponseBody
    public List<SqTableMappingTree> queryTableColums(String id) {
        List<DmColumnMappingVO> columns = sqMappingService.queryColumnMappingList(id);
       //所有列的信息
        List<SqTableMappingTree> leaves = new ArrayList<SqTableMappingTree>(columns.size());
        for(DmColumnMappingVO dmColumnMappingVO : columns) {
            SqTableMappingTree sqTableMappingTree = new SqTableMappingTree();
            sqTableMappingTree.setId(dmColumnMappingVO.getId());
            sqTableMappingTree.setColumnType(dmColumnMappingVO.getColumnType());
            sqTableMappingTree.setField(dmColumnMappingVO.getColumnName());
        	if(StringUtils.isNotBlank(dmColumnMappingVO.getMappingName())){
        		sqTableMappingTree.setName(dmColumnMappingVO.getMappingName());
			}else{
				sqTableMappingTree.setName(dmColumnMappingVO.getColumnName());
			}
            leaves.add(sqTableMappingTree);
        }
        return leaves;
    }

    @RequestMapping("queryTables.do")
    @ResponseBody
    public List<Map<String,String>> queryTables() {
        return dmTableMappingAssociationService.queryTablesForSelector();
    }

    /**
     * 增加关联关系
     * @return
     */
    @RequestMapping("addAssociation.do")
    @ResponseBody
    public Map<String,Object> addAssociation(String sourceId,String targetId) {
        Map<String,Object> result = new HashMap<String, Object>(2);
        boolean success = false;
        String msg = null;
        try {
            Map<String,Object> checkResult = dmTableMappingAssociationService.checkAssociation(sourceId, targetId);
            boolean isSuccess = (Boolean)checkResult.get("success");
            if (!isSuccess) { //校验不通过
                return checkResult;
            }else {
                DmColumnMapping source = (DmColumnMapping) checkResult.get("source");
                DmColumnMapping target = (DmColumnMapping) checkResult.get("target");
                dmTableMappingAssociationService.saveAssociation(source,target);
                success = true;
                msg = "新增成功";
            }
        } catch (Exception e) {
           log.error("新增关联关系失败",e);
            msg = "新增失败";
        }
        result.put("success",success);
        result.put("msg",msg);
        return result;
    }

    /**
     * 删除关联关系
     * @param source
     * @param target
     * @return
     */
    @RequestMapping("deleteAssociation.do")
    @ResponseBody
    public Map<String,Object> deleteAssociation(String source,String target) {
        Map<String,Object> result = new HashMap<String, Object>(2);
        boolean success = false;
        String msg = null;
        try {
            Map<String,Object> checkResult = dmTableMappingAssociationService.deleteCheck(source,target);
            if(!(Boolean)checkResult.get("success")) {
                return checkResult;
            }
            dmTableMappingAssociationService.deleteAssociation(source,target);
            success = true;
            msg = "删除成功";
        } catch (Exception e) {
            log.error("删除关联关系失败",e);
            msg = "删除失败";
        }
        result.put("success",success);
        result.put("msg",msg);
        return result;
    }

    /**
     * 关联列表查询
     * @param table1 表一值
     * @param table2 表二值
     * @param page 当前页码
     * @param rows 分页数
     * @return
     */
    @RequestMapping("queryAssociations.do")
    @ResponseBody
    public String queryAssociations(String table1,String table2,int page,int rows) {
        DataGrid dataGrid = dmTableMappingAssociationService.queryAssociations(table1,table2,page,rows);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";
        try {
            result = objectMapper.writeValueAsString(dataGrid);
        } catch (JsonProcessingException e) {
            log.error("write datagrid to json fail",e);
        }
        return result;
    }

}
