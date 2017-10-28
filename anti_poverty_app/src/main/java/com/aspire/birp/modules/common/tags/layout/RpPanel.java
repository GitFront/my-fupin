package com.aspire.birp.modules.common.tags.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class RpPanel extends TagSupport {
	
	/**
	 * panel的宽度百分比50%/100%
	 */
	private String width = "100%";
	/**
	 * panel的高度XXpx
	 */
	private String height;
	
	
	/**
	 * <div class="panel-content width50" style="height: 300px;">
           
       </div>
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
		  String temp_w = "";
		  if(!"100%".equals(this.width)) temp_w = " width50";
		  try {
			  StringBuffer divOut = new StringBuffer();
			  divOut.append("<div class=\"panel-content"+temp_w+"\" style=\"min-height:100px;");
			  if(StringUtils.isNotBlank(this.height)) 
				  divOut.append("height: "+this.height+";");
			  divOut.append("\">");
			  out.print(divOut.toString());
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
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
	}
	
	

	
	public void setWidth(String width) {
		this.width = width;
	}


	public void setHeight(String height) {
		this.height = height;
	}
	
	

}
