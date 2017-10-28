package com.aspire.birp.modules.smartQuery.task.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aspire.birp.modules.smartQuery.base.service.SqBaseService;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskInfo;
import com.aspire.birp.modules.smartQuery.task.entity.SqTaskStatusInfo;
import com.aspire.birp.modules.smartQuery.task.service.SqTaskCommonService;

/**
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/3 17:00
 */
@Service
public class SqTaskCommonServiceImpl extends SqBaseService implements SqTaskCommonService {

    /**
     * 查询当前周期在statusInfo表里面的信息
     * @param sqTaskInfo
     * @return
     */
    @Override
    public List<SqTaskStatusInfo> queryCurrentTaskStatusInfo(SqTaskInfo sqTaskInfo) {
        String hql = "from SqTaskStatusInfo where taskId = ? and dataCyc=?";
        return (List<SqTaskStatusInfo>) this.getHibernateTemplate().find(hql,sqTaskInfo.getTaskId(), sqTaskInfo.getDataCyc());
    }

    /**
     * 删除异常的statusInfo信息
     * @param taskId
     */
    @Override
    public void deleteExceptionStatusInfo(String taskId) {
        SqTaskInfo sqTaskInfo = getSqTaskInfoDAO().findById(taskId);
        List<SqTaskStatusInfo> list = queryCurrentTaskStatusInfo(sqTaskInfo);
        if(list.size() > 0) {
            getHibernateTemplate().delete(list.get(0));
        }
    }
}
