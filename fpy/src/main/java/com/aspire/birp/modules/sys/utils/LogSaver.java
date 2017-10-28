package com.aspire.birp.modules.sys.utils;

import com.aspire.auth.modules.security.service.SecurityService;
import com.aspire.auth.modules.sys.entity.Log;
import com.aspire.bi.common.service.SpringContextHolder;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;


/**
 * 日志记录类
 * Title: osa_web
 * Description:
 * Copyright: aspire Copyright (C) 2009
 * 
 * @author <a href="mailto:qinjianqiu@aspirecn.com">覃剑秋</a>
 * @e-mail: qinjianqiu@aspirecn.com
 * @version 1.0 
 * @creatdate 2015年7月28日 下午5:46:48
 *
 */
public class LogSaver implements Runnable {
	
	private final static Logger logger = Logger.getLogger(LogSaver.class);
	
	private Log log;
	
	public LogSaver(Log log){
		this.log = log;
	}
 	
 	private static SecurityService securityServiceHandler;
   	
    private static SecurityService getSecurityService(){
    	if(securityServiceHandler==null){ 
     		securityServiceHandler = (SecurityService)SpringContextHolder.getBean("securityServiceHandler");
     	}
    	return securityServiceHandler;
    }
    
	@Override
	public void run() {
		try {
			getSecurityService().saveLog(log);
		} catch (RemoteException e) {
			logger.error("保存日志失败", e);
		}
	}
	
	 
	
	
}
