package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class RpInfoTr extends TagSupport {
	
	private int rowno;

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {
			   out.print("<tr class=\""+this.putTrClass()+"\">");
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
			   out.print("</tr>");
			   out.flush();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
	}

	private String putTrClass(){
		String class_ = "even";
		switch (this.rowno%2) {
		case 0:
			class_ = "odd";
			break;
		default:
			break;
		}
		return class_;
	}


	public void setRowno(int rowno) {
		this.rowno = rowno;
	}

	
	
}
