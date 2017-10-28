package com.aspire.birp.modules.smartQuery.task.service;

import com.aspire.birp.modules.smartQuery.task.entity.SqTaskInfo;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskStatusInfo;

import java.util.List;

/**
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/3 16:59
 */
public interface SqTaskCommonService {
    /**
     * 查询当前周期在statusInfo表里面的信息
     * @param sqTaskInfo
     * @return
     */
    List<SqTaskStatusInfo> queryCurrentTaskStatusInfo(SqTaskInfo sqTaskInfo);

    /**
     * 删除异常的statusInfo信息
     * @param taskId
     */
    void deleteExceptionStatusInfo(String taskId);
}
