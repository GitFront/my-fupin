package com.aspire.birp.modules.sys.web.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aspire.birp.modules.sys.utils.AppContextFactory;


/**
 * Servlet implementation class AppCtxServlet
 */
public class AppCtxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppCtxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	public void init(ServletConfig config) throws ServletException {
 		super.init(config);
  		WebApplicationContext webAppCtx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()); 	
 		AppContextFactory.init(webAppCtx);
 		
       }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
