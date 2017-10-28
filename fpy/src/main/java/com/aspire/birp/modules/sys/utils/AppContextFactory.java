package com.aspire.birp.modules.sys.utils;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * 
 * 定义一个spring 容器 工厂 提供给流程引擎中使用
 * 
 * Title: emergency_web <br>
 * Description: <br>
 * Copyright: eastcom Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:qinjianqiu@eastcom-sw.com">覃剑秋</a><br>
 * @e-mail: qinjianqiu@eastcom-sw.com<br>
 * @version 1.0 <br>
 * @creatdate 2014年6月25日 下午5:00:22 <br>
 *
 */
public class AppContextFactory {
	
	private final static Logger logger = Logger.getLogger(AppContextFactory.class);
	
	private static ApplicationContext ctx = null;
	
	public synchronized static void init(ApplicationContext ctx){
		if(AppContextFactory.ctx==null){
			logger.info("starting initialize AppContextFactory  applictionContext。。。");
			AppContextFactory.ctx = ctx;
			logger.info(" AppContextFactory  applictionContext initialized!");
		}
	}
	
	public static Object getBean(String beanName){
		if(ctx!=null){
			try {
				return ctx.getBean(beanName);
			} catch (Exception e) {
				logger.error("获取bean["+beanName+"]失败",e);
			}
		}else {
			logger.warn(" AppContextFactory  applictionContext  is not initialize!");
		}
		return null;
	}
	
	
}
