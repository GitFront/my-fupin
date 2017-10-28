package com.aspire.birp.modules.smartQuery.exception.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aspire.bi.common.util.DateUtils;
import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.exception.entity.ModuleType;
import com.aspire.birp.modules.smartQuery.exception.entity.SqExceptionInfo;
import com.aspire.birp.modules.smartQuery.exception.service.SqExceptionService;

/**
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/4 9:02
 */
@Service
public class SqExceptionServiceImpl extends SqBaseService implements SqExceptionService {

    /**
     * 保存异常数据
     * @param log 异常报的日志
     * @param type 异常类型
     * @param description 异常人为描述
     * @param moduleType 模块类型
     * @param extraPropOne 额外属性
     * @param extraPropTwo 额外属性
     */
    @Override
    public void saveExceptionInfo(String log, Integer type, String description,
                                  ModuleType moduleType, String extraPropOne,
                                  String extraPropTwo) {
        SqExceptionInfo sqExceptionInfo = new SqExceptionInfo();
        sqExceptionInfo.setId(UUID.randomUUID().toString());
        sqExceptionInfo.setLog(log);
        sqExceptionInfo.setType(type);
        sqExceptionInfo.setDescription(description);
        sqExceptionInfo.setModuleId(moduleType.getId());
        sqExceptionInfo.setExtraPropOne(extraPropOne);
        sqExceptionInfo.setExtraPropTwo(extraPropTwo);
        sqExceptionInfo.setCreateTime(DateUtils.dateToTimestamp(new Date()));
        getHibernateTemplate().save(sqExceptionInfo);
    }
}
