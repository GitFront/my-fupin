package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.aspire.birp.modules.common.tags.report.HcReport;

@SuppressWarnings("serial")
public class RpInfoTable extends HcReport {

	/**
	 * 表格的表头信息
	 * 格式为(信息1;信息2;信息3;信息4;信息5)
	 */
	private String thead;

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  
		  try {
			   out.print("<div class=\"panel-table\" style=\"height: auto\">");
			   out.print("	<table id=\""+this.getId()+"\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" class=\"dataIndicators\">");
			   out.print(this.putTableHead());
			   out.print("	<tbody>");
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
			   out.print(" 	</tbody>");
			   out.print("	</table>");
			   out.print("</div>");
			   if(!StringUtils.isBlank(this.getQueryName())){
				   out.print("<script type=\"text/javascript\">");
				   out.print("common.report.page.load.InfoTable(\""+this.getId()+"\",\""+this.getQueryName()+"\","+
						   this.putParameters()+","+this.putMapper()+");");
				   out.print("</script>");		
			   }
			   out.flush();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
		  }

	/**
	 * 获取表头数据信息
	 * @return
	 */
	private String putTableHead(){
		StringBuffer h_ = new StringBuffer();
		String[] theads = StringUtils.split(this.thead, ";");
		if(theads != null){
			for (int i = 0; i < theads.length; i++) {
				h_.append("<td>" + theads[i] + "</td>");
			}
			h_.insert(0,"<thead><tr>");
			h_.append("</tr></thead>");
		}		
		return h_.toString();
	}

	public void setThead(String thead) {
		this.thead = thead;
	}
	
	
}
