package com.aspire.birp.common.logAop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public interface LoggerDao {

	
	//public void myMethod();
	//@After("@annotation(com.aspire.birp.common.logAop.ControllerLog)")
	@Before("execution(* com.aspire.birp.modules.sys.web.controller.*.*(..))")
	public void beforeMethod(JoinPoint jp) throws Exception;
	
	@AfterReturning(pointcut="execution(* com.aspire.birp.modules.sys.web.controller.*.*(..))",returning="returnValue")
	public void afteReturning(JoinPoint jp,Object returnValue)  throws Exception;
	
	@AfterThrowing(pointcut="execution(* com.aspire.birp.modules.sys.web.controller.*.*(..))",throwing ="e")
	public void afterThrowing(JoinPoint jp , Exception e);
	
	@Around("execution(* com.aspire.birp.modules.sys.web.controller.*.*(..))")
	public Object around(ProceedingJoinPoint jp);
	
	@After("execution(* com.aspire.birp.modules.sys.web.controller.*.*(..))")
	public void afterMethod(JoinPoint jp);
	
	
	public Method getAop(JoinPoint jp) throws NoSuchMethodException, SecurityException;
	
}
