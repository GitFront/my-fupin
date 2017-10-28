package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class RpInfoTd extends TagSupport {

	/**
	 * 字段名称
	 */
	private String label;
	/**
	 * 数据的显示单位
	 */
	private String unit;
	
	/**
	 * 数据显示颜色（蓝色：默认/绿色:green/橙色:orange/红色：red）
	 */
	private String color;
	
	/**
	 * 对应的字段ID值
	 */
	private String fieldName;

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  String label_ = this.label + "：";
		  if(StringUtils.isBlank(this.label)) label_ = "&nbsp;";
		 
		  try {
			   out.print("<td>");
			   out.print("	<div class=\"dataName\">"+ label_ + "</div>");
			   out.print("	<div class=\"dataNum\">");
			   out.print("		<span  id=\""+this.fieldName+"\" class=\""+this.putColor()+"\">");
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_BODY_INCLUDE;
	}
	
	
	public int doEndTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  String unit_ = this.unit;
		  if(StringUtils.isBlank(this.unit)) unit_ = "";
		  
		  try {
			   out.print("		</span>"+unit_);
			   out.print("	</div>");
			   out.print("</td>");
			   out.flush();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
	}
	
	private String putColor(){
		String color_ = "num";
		if(this.color != null){
			if(Colors.green.toString().equalsIgnoreCase(this.color)){
				color_ = "num success";
			}else if(Colors.orange.toString().equalsIgnoreCase(this.color)){
				color_ = "num stress";
			}else if(Colors.red.toString().equalsIgnoreCase(this.color)){
				color_ = "num warn";
			}
		}
		return color_;		
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	
}
