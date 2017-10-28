package com.aspire.birp.modules.sys.utils;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.aspire.auth.modules.sys.entity.Log;
import com.aspire.bi.common.service.SpringContextHolder;

public class LogWatch {
	
	
	private static Logger logger = Logger.getLogger(LogWatch.class);

	//日志类型（1：访问；2：操作）
	public final static String LOG_TYPE_VISIT = "1";
	public final static String LOG_TYPE_OPERATION = "2";
	
	//操作是否成功（1：成功；2：失败）
	public final static String FLAG_SUCCESS = "1";
	public final static String FLAG_FAILURE = "0";

	
	private Long time1 = null;
	private Long time2 = null;
	Log log = new Log();
	
	private static ThreadPoolTaskExecutor taskExecutor = null;
	
   private static ThreadPoolTaskExecutor getTaskExecutor(){
    	if(taskExecutor==null){ 
    		taskExecutor = (ThreadPoolTaskExecutor)SpringContextHolder.getBean("taskExecutor");
     	}
    	return taskExecutor;
    }

	
	/*public Object[] updateState(String id,String value,HttpServletRequest request){
		long time1 = System.currentTimeMillis();
		Log sysLog = new Log();
	    StringBuilder content = new StringBuilder();
		Object[] obj = new Object[]{Boolean.FALSE,"更新失败"};
		try {
			Feedback feedback = new Feedback();
			feedback.setId(id);
			feedback.setFinished(value.equals("是")?1:0);
			feedback.setFinishedTime(new Date());
			feedbackDao.updateFeedbackState(feedback);
			obj = new Object[]{Boolean.TRUE,"更新成功"};
		   	  sysLog.setFlag("0");
			  content.append("更新问题反馈成功,id:"+id);
		} catch (Exception e) {
			logger.error("更新feedback完成状态失败", e);
			sysLog.setFlag("0");
			content.append("更新问题反馈失败,id:"+id);
 		}
		
		long time2 = System.currentTimeMillis();
		try {
  			sysLog.setSysModel("统一运维支撑系统自动化平台");
 	   		sysLog.setCreateDate(new Date());
 	   		sysLog.setCreateBy(UserUtils.getUser());
 	   		sysLog.setFunModel("联系我们.问题反馈");
 	   		sysLog.setTakeTime((time2 - time1)+"ms");
 	   		sysLog.setType("2");
 	   		sysLog.setRemoteAddr(request.getRemoteAddr());
 	   		sysLog.setContent(content.toString());
   	   		//增加日志保存
 	    	taskExecutor.execute(new LogSaver(sysLog));
		} catch (Exception e) {
 			log.error("保存操作日志失败",e);
 		}
		
		return obj;
	}*/
	
	public void start(){
		time1 = System.currentTimeMillis();
		log.setCreateDate(new Date());
	}
	
	public void start(HttpServletRequest request){
		time1 = System.currentTimeMillis();
		log.setCreateDate(new Date());
	}
	
	
	public void end(String modules,String content,String type,String flag){
		try {
			time2 = System.currentTimeMillis(); 
			log.setContent(content);
			log.setType(type);
			log.setCreateBy(UserUtils.getUser());
			getTaskExecutor().execute(new LogSaver(log));
		} catch (Exception e) {
			logger.error("保存操作日志失败",e);
		}
 	}
	
	public void end(String modules,String content,String remoteAddr,String type,String flag){
		try {
			time2 = System.currentTimeMillis(); 
			log.setContent(content);
			log.setType(type);
			log.setCreateBy(UserUtils.getUser());
			getTaskExecutor().execute(new LogSaver(log));
		}catch (Exception e) {
			logger.error("保存操作日志失败",e);
		}
 	}
	
	
}
