package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class RpContent extends TagSupport {

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {
			  out.print("<script type=\"text/javascript\">");
			  out.print("common.utils.showLoading();");
			  out.print("</script>");
			   out.print("<div class=\"page-content\">");
			   out.print(" <div class=\"kit-container\">");
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_BODY_INCLUDE;//<body-content>设置不为空，则必须返回 EVAL_BODY_INCLUDE
		  }
	
	
	public int doEndTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {
			   out.print(" </div>");
			   out.print("</div>");
			   out.print("<script type=\"text/javascript\">");
			   out.print("common.utils.closeLoading();");
			   out.print("</script>");
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;//<body-content>设置不为空，则必须返回 EVAL_BODY_INCLUDE
		  }
}
