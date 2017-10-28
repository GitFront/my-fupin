package com.aspire.birp.modules.sys.interceptor;


import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
 
import com.aspire.auth.modules.security.service.SecurityService;
import com.aspire.auth.modules.sys.entity.Log;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.bi.common.config.Global;
import com.aspire.bi.common.service.SpringContextHolder;
import com.aspire.birp.common.utils.StringUtils;
import com.aspire.birp.modules.sys.utils.UserUtils;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 系统拦截器
 * @author ThinkGem
 * @version 2013-6-6
 */
public class LogInterceptor   implements HandlerInterceptor {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private static SecurityService securityServiceHandler =  SpringContextHolder.getBean("securityServiceHandler");
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null) {
			String viewName = modelAndView.getViewName();
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent")); 
			if(viewName.startsWith("modules/") && DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType())){
				modelAndView.setViewName(viewName.replaceFirst("modules", "mobile"));
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		
		 User user =  UserUtils.getUser();
	     String userName = user.getLoginName();
	     Log log = new Log();
	     
 		String requestRri = request.getRequestURI();
 		if (StringUtils.isNotBlank(userName)){
 			
 	        StringBuilder params = new StringBuilder();
	        int index = 0;
		        
	        for (Iterator localIterator = request.getParameterMap().keySet().iterator(); localIterator.hasNext(); ) {
	          Object param = localIterator.next();
	          params.append((index++ == 0 ? "" : "&") + param + "=");
	          params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase((String)param, 
	            "password") ? "" : request.getParameter((String)param), 100));
	        }
	        
	   	   if ((StringUtils.endsWith(requestRri, "/save")) || 
		      (StringUtils.endsWith(requestRri, "/delete")) || 
		      (StringUtils.endsWith(requestRri, "/import")) || 
		      (StringUtils.endsWith(requestRri, "/update"))) {
	 	        log.setType("2");
	  	      }
		    else {
	  		    log.setType("1");
	 		}
		 	
	 	   /* log.setCreateDate(new Date());
	        log.setFlag(ex == null ? "0" : "1");
	        log.setSysModel(Global.getConfig("modulesName"));
	        log.setCreateBy(user);
	        //log.setTakeTime(takeTime);
	        StringBuilder content = new StringBuilder();
	        content.append("user-agent:").append(request.getHeader("user-agent")).append("<br>url:").append(requestRri).append(" <br>").append("params:").append(params.toString());
	        log.setContent(content.toString());
	        log.setRemoteAddr(StringUtils.getRemoteAddr(request));*/
	      //   securityServiceHandler.saveLog(log);
//			logger.info("save log {type: {}, loginName: {}, uri: {}}, ", log.getType(), user.getLoginName(), requestRri);
 	  }
 	}
		
//		logger.debug("最大内存: {}, 已分配内存: {}, 已分配内存中的剩余空间: {}, 最大可用内存: {}", 
//				Runtime.getRuntime().maxMemory(), Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory(), 
//				Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory()); 
		
 
}
