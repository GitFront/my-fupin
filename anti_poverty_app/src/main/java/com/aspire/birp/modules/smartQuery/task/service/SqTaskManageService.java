package com.aspire.birp.modules.smartQuery.task.service;

import com.aspire.bi.common.web.bean.easyui.DataGrid;
import com.aspire.birp.modules.smartQuery.query.entity.SqQueryInfo;
import com.aspire.birp.modules.smartQuery.query.vo.SqQueryInfoVO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 查询任务管理的service
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/10/25 15:21
 */
public interface SqTaskManageService {
    /**
     * 页面列表查询
     * @param page
     * @param pageSize
     * @return
     */
    public Map<String,Object> queryTaskList(int page,int pageSize,String user,String type,String name);

    /**
     * 启动或者暂停任务
     * @param taskId
     */
    public String startOrStopTask(String taskId);

    /**
     * 删除任务
     * @param taskId
     */
    public String deleteTask(String taskId);

    /**
     * 查询任务创建人，管理员可以看到全部，非管理员只能看到自己
     * @return
     */
    List<Map<String, Object>> queryCreators();

    /**
     * 任务异常日志查询
     * @param taskId
     * @param page
     * @param pageSize
     * @return
     */
    DataGrid queryTaskExceptions(String taskId, int page, int pageSize);

    Map<String, Object> queryInfoById(String id);

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    String updateSqQueryInfo(SqQueryInfoVO sqQueryInfoVo);
}
