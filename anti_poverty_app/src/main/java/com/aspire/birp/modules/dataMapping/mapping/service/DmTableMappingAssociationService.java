package com.aspire.birp.modules.dataMapping.mapping.service;

import java.util.List;
import java.util.Map;

import com.aspire.bi.common.web.bean.easyui.DataGrid;
import com.aspire.birp.modules.dataMapping.mapping.entity.DmColumnMapping;

/**
 * 数据表关联映射管理service
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/16 14:48
 */
public interface DmTableMappingAssociationService {

    List<Map<String, String>> queryTablesForSelector();

    Map<String,Object> checkAssociation(String sourceId, String targetId);

    void saveAssociation(DmColumnMapping source, DmColumnMapping target);

    void deleteAssociation(String source, String target);

    Map<String, Object> deleteCheck(String source, String target);

    DataGrid queryAssociations(String table1, String table2, int page, int pageSize);
}
