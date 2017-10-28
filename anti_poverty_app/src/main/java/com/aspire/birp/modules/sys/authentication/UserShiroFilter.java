package com.aspire.birp.modules.sys.authentication;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class UserShiroFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		System.out.println("用户自定义过滤器-----------isAccessAllowed");
		return true;
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws IOException {
		System.out.println("用户自定义过滤器------------------onAccessDenied22");
		return super.onAccessDenied(request, response);
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		System.out.println("用户自定义过滤器------------------onAccessDenied33");
		return super.onAccessDenied(request, response, mappedValue);
	}

}
