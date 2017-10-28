package com.aspire.birp.modules.sys.service;

import com.aspire.auth.modules.sys.entity.User;

/**
 * 系统日志服务
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/3 11:13
 */
public interface LogService {
    /**
     * 日志记录
     * @param type 日志类型
     * @param function 功能模块
     * @param operation 操作
     * @param content 日志具体信息
     */
    void saveLog(String type, String function, String operation, String content);

    void saveLog(String type, String function, String operation, String content, User user);
    
    void saveLog(String type, String function, String operation, String content, String takeTime,String IP,User user);
}
