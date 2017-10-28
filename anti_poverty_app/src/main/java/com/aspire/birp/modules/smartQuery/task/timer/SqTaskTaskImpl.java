package com.aspire.birp.modules.smartQuery.task.timer;

import com.aspire.birp.modules.smartQuery.task.service.SqTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 执行SQ_前端定时作业配置信息表的任务
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/10/20 15:56
 */
@Component
public class SqTaskTaskImpl implements SqTaskTask {

    private static Logger log = LoggerFactory.getLogger(SqTaskTaskImpl.class);

    @Autowired
    private SqTaskService sqTaskService;

    /*每20分钟执行一次定时任务*/
    //@Scheduled(cron="0 0/20 * * * ?")
    public void execute(){
        log.debug("执行SQ_前端定时作业配置信息表的任务");
        sqTaskService.executeTask();
    }
}
