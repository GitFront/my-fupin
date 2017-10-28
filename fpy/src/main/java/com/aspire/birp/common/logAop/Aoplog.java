package com.aspire.birp.common.logAop;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface Aoplog {

	public String ActionName() default "";
	public String ModelName() default "";
	
}
