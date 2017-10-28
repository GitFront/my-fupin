package com.aspire.birp.modules.sys.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.aspire.birp.modules.sys.utils.UserUtils;

public class UserSessionListener implements HttpSessionListener {
	
	private static Logger logger = Logger.getLogger(UserSessionListener.class);
	
	public void sessionCreated(HttpSessionEvent sessionEvent) {
  	}

	/**
	 * 防止浏览器关闭没清空缓存 session过期后启动清理缓存
	 */
 	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
 		if(sessionEvent.getSession().getAttribute("userName")!=null){
 			logger.info("清理过期用户["+sessionEvent.getSession().getAttribute("userName")+"]缓存...");
 			UserUtils.reomveCacheMap(sessionEvent.getSession().getAttribute("userName").toString());
 			logger.info("清理过期用户["+sessionEvent.getSession().getAttribute("userName")+"]完毕!");
 		}
	}

}
