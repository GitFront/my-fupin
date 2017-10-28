package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class RpPanelPs extends TagSupport {
	
	/**
	 * 备注的标题
	 */
	private String title = "";
	
	/**
	 * 
       <div class="ps" >
          <div class="title">备注:</div>
             <p><span class="normal">访问人数</span>：统计期内，某一活动的访问人数(UV)；<br/>
				<span class="normal">参与人数</span>：统计期内，参与某一具体活动的人数；<br/>
				<span class="normal">粉丝参与率</span>：公式=参与人数/统计期末上一天的粉丝数*100%，<span class="warn">仅杭州、台州、绍兴、金华有数据</span><br/>
				<span class="normal">分享人数</span>：公式=参与人数/统计期末上一天的粉丝数*100%，<span class="warn">仅杭州、台州、绍兴、金华有数据</span><br/>
				<span class="normal">分享率</span>：公式=分享人数/参与人数*100%
             </p>
       </div>
	 */

	public int doStartTag() throws JspException {
		  JspWriter out = pageContext.getOut();
		  try {
			   out.print("<div class=\"ps\">");
			   out.print("	<div class=\"title\">"+this.title+"</div>");
			   out.print("	<p>");
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
			   out.print("	</p>");
			   out.print("</div>");
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
	}


	public void setTitle(String title) {
		this.title = title;
	}


}
