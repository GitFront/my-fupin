package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.aspire.bi.common.util.StringUtils;

@SuppressWarnings("serial")
public class RpHeadSearch extends TagSupport {
	
	/**
	 * 条件过滤时提交的form表单ID值
	 */
	private String formid = "";
	/**
	 * 是否单行显示过滤条件
	 */
	private boolean singlerow = false;
	/**
	 * 是否显示按钮行，如果singlerow属性为false时，该属性只能为true
	 */
	private boolean buttonrow = true;
	
	/**
	 * 查询区域的按钮区配置
	 */
	private String btns = "";

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();  
		  
		  try {
			   out.print("<div id=\"head-search\">");
			   out.print("	<div class=\"search-container\">");
			   
			   out.print("		<form id=\""+this.formid+"\" method=\"post\">");	
			   
			   if(this.singlerow){
				   if(this.buttonrow) {
					   out.print("		<div class=\"row\">");  
				   }else{
					   out.print("		<div class=\"row singlerow\">");  
				   }
				   
			   }
			   
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
			  if(this.singlerow){
				  if(!this.buttonrow){
					  out.print("	<div class=\"cellbox\">");
					  out.print(this.putBtns(this.btns));
					  out.print("	</div>"); 
					  out.print("</div>");
				  }else{
					  out.print("</div>");
					  out.print("<div class=\"search-bottom\">");
					  out.print(this.putBtns(this.btns));
					  out.print("</div>");
				  }
				    
			   }else{
				   out.print("<div class=\"search-bottom\">");
				   out.print("<a href=\"javascript:;\" id=\"search-toggle\">点击展开<i class=\"kit-icon kit-icon-arrowdown3\"></i></a>");
				   out.print(this.putBtns(this.btns));
				   out.print("</div>");
			   }
			   out.print("		</form>");  
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
	 * 处理按钮组
	 * @param btns
	 * @return
	 */
	private String putBtns(String btns){
		StringBuffer tag_ = new StringBuffer();
		/*如果没有符合条件的字符，则返回默认查询按钮*/
		if(StringUtils.isBlank(btns)) {
			tag_.append("<a id=\"hc-report-btn-search\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-btn-search',disabled:true\">查  询</a>");
/*			script_.append("if(common.utils.isFunction(\"common_report_search\")) $(\"#btn-search\").click(common_report_search);");
			script_.insert(0, "<script type=\"text/javascript\"> $(function () {");
			script_.append("});</script>");
			tag_.append(script_.toString());*/
			return tag_.toString();
		} 
		
		String[] btnInfo = StringUtils.split(btns,";");
		for (int i = 0; i < btnInfo.length; i++) {
			String[] btn_ = StringUtils.split(btnInfo[i],":");
			String iconName = btn_[0];
			String fn = "";
			if(btn_.length > 1) fn = btn_[1];
			if(Buttons.search.toString().equals(iconName)){
				/*script_.append("if(common.utils.isFunction(\""+fn+"\")) {$(\"#btn-search\").click("+fn+");}"
						+ "else{if(common.utils.isFunction(\"common_report_search\")) $(\"#btn-search\").click(common_report_search);}");*/
				tag_.append("<span style=\"margin-right: 8px;margin-left: 12px;\"><a id=\"hc-report-btn-search\" href=\"javascript:"+fn+";\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-btn-search'");
				if(StringUtils.isBlank(fn)) 
					tag_.append(",disabled:true");
				tag_.append("\">查  询</a></span>");
			}else if(Buttons.reset.toString().equals(iconName)){
				/*script_.append("if(common.utils.isFunction(\""+fn+"\")) $(\"#btn-reset\").click("+fn+");");*/
				tag_.append("<span style=\"margin-right: 8px;\"><a id=\"hc-report-btn-reset\" href=\"javascript:"+fn+";\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-btn-repeat'");
				if(StringUtils.isBlank(fn)) 
					tag_.append(",disabled:true");
				tag_.append("\">重  置</a></span>");
			}else if(Buttons.download.toString().equals(iconName)){
				/*script_.append("if(common.utils.isFunction(\""+fn+"\")) $(\"#btn-export\").click("+fn+");");*/
				tag_.append("<span style=\"margin-right: 8px;\"><a id=\"hc-report-btn-export\" href=\"javascript:"+fn+";\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-btn-download'");
				if(StringUtils.isBlank(fn)) 
					tag_.append(",disabled:true");
				tag_.append("\">导  出</a></span>");
			}
		}		
		/*script_.insert(0, "<script type=\"text/javascript\"> $(function () {");
		script_.append("});</script>");		
		tag_.append(script_.toString());	*/	
		return tag_.toString();
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}


	public void setSinglerow(boolean singlerow) {
		this.singlerow = singlerow;
	}

	public void setBtns(String btns) {
		this.btns = btns;
	}


	public void setButtonrow(boolean buttonrow) {
		this.buttonrow = buttonrow;
	}

	public boolean isSinglerow() {
		return singlerow;
	}	

}
