package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class RpTablePanel extends TagSupport {

	/**
	 * panel的高度XXpx
	 */
	private String height;
	
	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {
			   out.print("<div class=\"panel-content nopadding\" style=\"min-height:100px;");
			   if(StringUtils.isNotBlank(this.height)) 
				   out.append("height: "+this.height+";");
			   out.append("\">");
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
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
		  }


	public void setHeight(String height) {
		this.height = height;
	}
	
	
}
