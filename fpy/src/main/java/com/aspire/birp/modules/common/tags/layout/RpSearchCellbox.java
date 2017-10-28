package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class RpSearchCellbox extends TagSupport {
	
	/**
	 * 查询条件的标题名
	 */
	private String title;
	/**
	 * 查询条件的列数
	 */
	private String columns;
	

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();

		  try {
			   out.print("<div class=\""+this.putWidth(this.columns)+"\">");
			   out.print("	<span class=\"title\">"+this.title+"：</span>");
			   out.print("	<div class=\"element\">");
			   
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
			   out.print("	</div>");
			   out.print("</div>");
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
	}

	/**
	 * 设置列宽度比，默认为4列放置
	 * @param width
	 * @param columns
	 * @return
	 */
	private String putWidth(String columns){
		String width_ = "cellbox";
		if(!StringUtils.isBlank(columns)){
			if("S1".equalsIgnoreCase(columns)){
				width_ = "cellboxs1";
			}else if("S2".equalsIgnoreCase(columns)){
				width_ = "cellboxs2";
			}else if("C1".equalsIgnoreCase(columns)){
				width_ = "cellbox4";
			}else if("C2".equalsIgnoreCase(columns)){
				width_ = "cellbox2";
			}else if("C3".equalsIgnoreCase(columns)){
				width_ = "cellbox3";
			}
		}	
		return width_;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public void setColumns(String columns) {
		this.columns = columns;
	}


}
