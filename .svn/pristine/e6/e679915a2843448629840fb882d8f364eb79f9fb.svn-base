package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.aspire.birp.modules.common.tags.report.HcReport;

@SuppressWarnings("serial")
public class RpGrid extends HcReport {
	
	/*
	 * 列表数据的表格高度（默认为400）
	 */
	private String height;
	/**
	 * 默认分页数
	 */
	private String pageSize;
	
	/**
	 * 是否显示分页信息（默认为不显示分页信息）
	 */
	private boolean pages = false;
	/**
	 * 是否自适应高度（默认为固定高度）
	 */
	private boolean autoHeight = false;
	
	/*成功加载后调用函数*/
	private String onLoadSuccess;
	/*加载前调用函数*/
	private String onBeforeLoad;
	

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  if(StringUtils.isBlank(this.height)) this.height = "400";
		  if(this.height.indexOf("px") != -1) this.height = this.height.replace("px", "");
		  if(StringUtils.isBlank(this.pageSize)) this.pageSize = "20";
		  try {
			   out.print("<table id=\""+this.getId()+"\" data-options=\"height:"+this.height+"");
			   out.print(",singleSelect:true,rownumbers:true,data:[],fitColumns:true,striped:false,loadMsg:'数据加载中...'," +
				 "remoteSort:false,multiSort:true");
			   if(this.pages) out.print(",pagination:true,pageSize:"+this.pageSize+",pageList:[10,15,20,25,30,35,40,45,50]");
			   if(StringUtils.isNotBlank(this.onLoadSuccess)) out.print(",onLoadSuccess:"+this.onLoadSuccess);
			   if(StringUtils.isNotBlank(this.onBeforeLoad)) out.print(",onLoadSuccess:"+this.onBeforeLoad);
			   out.print(",border:true\" componentType='datagrid'>");
			   out.print("	<thead>");
			   out.print("		<tr>");
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
			   out.print(" 		</tr>");
			   out.print("	</thead>");
			   out.print("</table>");
			   if(!StringUtils.isBlank(this.getQueryName())){
				   out.print("<script type=\"text/javascript\">");
				   out.print("common.report.page.load.Grid(\""+this.getId()+"\",\""+this.getQueryName()+"\","+
						   this.putParameters()+","+this.pages+","+this.autoHeight+");");
				   out.print("</script>");		
			   }
			   out.flush();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
		  }

	public void setHeight(String height) {
		this.height = height;
	}


	public void setPages(boolean pages) {
		this.pages = pages;
	}


	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}


	public void setOnLoadSuccess(String onLoadSuccess) {
		this.onLoadSuccess = onLoadSuccess;
	}


	public void setOnBeforeLoad(String onBeforeLoad) {
		this.onBeforeLoad = onBeforeLoad;
	}


	public void setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
	}

	
	
}
