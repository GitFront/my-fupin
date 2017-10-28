package com.aspire.birp.modules.sys.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.aspire.auth.modules.sys.entity.Log;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.modules.base.constant.BaseConstant;
import com.aspire.birp.modules.sys.service.LogService;
import com.aspire.birp.modules.sys.utils.LogSaver;
import com.aspire.birp.modules.sys.utils.UserUtils;

/**
 * Title: hydc_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 *
 * @author <a href="mailto:suweicheng@aspirecn.com">苏伟城</a>
 * @version 1.0
 * @e-mail: suweicheng@aspirecn.com
 * @creatdate 2015/11/3 11:14
 */

@Service
public class LogServiceImpl implements LogService{

    private static Logger log = LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 日志记录
     * @param type 日志类型
     * @param function 功能模块
     * @param operation 操作
     * @param content 日志具体信息
     */
    @Override
    public void saveLog(String type, String function, String operation, String content) {
        Log sysLog = new Log();
        try{
            sysLog.setType(type);
            sysLog.setContent(content);
            //设置创建用户
            User user = UserUtils.getUser();
            sysLog.setCreateBy(user);
            sysLog.setCreateDate(new Date());
            taskExecutor.execute(new LogSaver(sysLog));
        }catch (Exception e) {
            log.error("日志入库失败：",e);
            log.error("日志内容为",log.toString());
        }
    }

    /**
     * 日志记录
     * @param type 日志类型
     * @param function 功能模块
     * @param operation 操作
     * @param content 日志具体信息
     */
    @Override
    public void saveLog(String type, String function, String operation, String content,User user) {
        Log sysLog = new Log();
        try{
            sysLog.setType(type);
            sysLog.setContent(content);
            //设置创建用户
            sysLog.setCreateBy(user);
            sysLog.setCreateDate(new Date());
            taskExecutor.execute(new LogSaver(sysLog));
        }catch (Exception e) {
            log.error("日志入库失败：",e);
            log.error("日志内容为",log.toString());
        }
    }

    /**
     * 日志记录
     * @param type 日志类型
     * @param function 功能模块
     * @param operation 操作
     * @param content 日志具体信息
     * @param takeTime 耗时时间
     */
	@Override
	public void saveLog(String type, String function, String operation, String content, String takeTime,String IP, User user) {
		Log sysLog = new Log();
        try{
            sysLog.setType(type);
            sysLog.setContent(content);
            sysLog.setFunModel(function);
            sysLog.setTakeTime(takeTime);
            sysLog.setRemoteAddr(IP);
            sysLog.setFlag(operation);
            sysLog.setSysModel(BaseConstant.SYSTEM_NAME);
            //设置创建用户
            sysLog.setCreateBy(user);
            sysLog.setCreateDate(new Date());
            taskExecutor.execute(new LogSaver(sysLog));
        }catch (Exception e) {
            log.error("日志入库失败：",e);
            log.error("日志内容为",log.toString());
        }
		
	}
    
    
}
