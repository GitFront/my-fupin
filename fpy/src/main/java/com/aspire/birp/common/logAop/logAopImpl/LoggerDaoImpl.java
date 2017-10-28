package com.aspire.birp.common.logAop.logAopImpl;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.common.logAop.ControllerLog;
import com.aspire.birp.common.logAop.LoggerDao;
import com.aspire.birp.common.utils.CommonUtil;
import com.aspire.birp.modules.sys.service.LogService;
import com.aspire.birp.modules.sys.utils.UserUtils;

@Aspect
@Component
public class LoggerDaoImpl implements LoggerDao {
	 private static Logger log = LoggerFactory.getLogger(LoggerDaoImpl.class);

	@Autowired
	LogService logService;
	
	 @Pointcut("@annotation(com.aspire.birp.common.logAop.ControllerLog)")  
	 public  void controllerAspect() {  
	    }  

	
	@Override
	@Before("controllerAspect()")
	public void beforeMethod(JoinPoint jp) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	@AfterReturning(pointcut="execution(* com.aspire.birp.modules.sys.web.controller.*.*(..))",returning="returnValue")
	public void afteReturning(JoinPoint jp, Object returnValue) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	@AfterThrowing(pointcut="execution(* com.aspire.birp.modules.sys.web.controller.*.*(..))",throwing ="e")
	public void afterThrowing(JoinPoint jp, Exception e) {
		// TODO Auto-generated method stub
	}

	@Override
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint jp){
		long start = 0;
		Object result = null;
		ControllerLog log = null;
		long end = 0;
		User user = null;
		HttpServletRequest request = null;
		try {
			start = System.currentTimeMillis();
			result = jp.proceed();
			end = System.currentTimeMillis();
			log = (ControllerLog)this.getAop(jp).getAnnotation(ControllerLog.class);
			user = UserUtils.getUser(); //获取用户
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	        request = ((ServletRequestAttributes) ra).getRequest();
			logService.saveLog("2", log.ModelName(), "0", log.ActionName(),(end-start)+"ms", CommonUtil.getIpAddr(request),user);
		} catch (Throwable e) {
			logService.saveLog("2", log.ModelName(), "1", log.ActionName(),(end-start)+"ms", CommonUtil.getIpAddr(request),user);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	@After("controllerAspect()") 
	public void afterMethod(JoinPoint jp) {
		// TODO Auto-generated method stub
	}

	public Method getAop(JoinPoint jp) throws NoSuchMethodException, SecurityException{
		Signature signature=jp.getSignature();
		MethodSignature methodSignature=(MethodSignature)signature;
		Method targetMethod=methodSignature.getMethod();
		Method realMethod=jp.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
		return realMethod;
		
	}
	
	
}
