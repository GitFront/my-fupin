package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.aspire.birp.modules.common.tags.report.HcReport;

@SuppressWarnings("serial")
public class RpGridField extends HcReport {
	/**
	 * 对应的取数字段值
	 */
	private String fieldId;
	/**
	 * 表头显示名称
	 */
	private String headName;
	/**
	 * 字段显示宽度
	 */
	private String width;
	/**
	 * 是否需要作为排序字段(true/false)
	 */
	private String sortable;
	/**
	 * 字段的对齐方式(左对齐left/右对齐right/居中对齐center)
	 */
	private String align;
	/**
	 * 字段显示单位
	 */
	private String unit;
	/**
	 * 自定义返回值
	 */
	private String formatter;
	
	
	public int doEndTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  if(StringUtils.isBlank(fieldId)) return TagSupport.EVAL_PAGE;
		  if(StringUtils.isBlank(headName)) this.headName = "";
		  try {
			   StringBuffer html = new StringBuffer();
			   html.append("<th data-options=\"field:'"+fieldId+"',halign:'center'");
			   if(StringUtils.isNotBlank(this.sortable)) html.append(",sortable:"+this.sortable);
			   if(StringUtils.isNotBlank(this.align)) html.append(",align:'"+this.align+"'");
			   if(StringUtils.isNotBlank(this.formatter)) html.append(",formatter:"+this.formatter+"");
			   if(StringUtils.isBlank(this.formatter) && StringUtils.isNotBlank(this.unit)) html.append(",formatter: function(value,row,index){if(common.utils.isEmpty(value)){return '';} return value + '"+this.unit+"'}");
			   html.append("\"");
			   if(StringUtils.isNotBlank(this.width)) html.append(" width=\""+this.width + "\"");
			   html.append(">"+this.headName+"</th>");
			   out.print(html.toString());
			   out.flush();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
		  }


	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}


	public void setHeadName(String headName) {
		this.headName = headName;
	}


	public void setWidth(String width) {
		this.width = width;
	}


	public void setSortable(String sortable) {
		this.sortable = sortable;
	}


	public void setAlign(String align) {
		this.align = align;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	
}
