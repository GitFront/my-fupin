package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class RpSearchRow extends TagSupport {
	
	

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {

			   out.print("<div class=\"row\">");   
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_BODY_INCLUDE;
	}
	
	
	public int doEndTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {
			   out.print("</div>");
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
	}



}
