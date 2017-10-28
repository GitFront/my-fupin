package com.aspire.birp.modules.common.tags.report;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class HmMap extends HcReport {

	/**
	 * 地图数据标识
	 */
	private String mapData;
	
	/**
	 * 地图与数据对应标识
	 */
	private String joinBy;
	
	public int doEndTag() throws JspException {
		/*动态生成javascript语句*/
		String script = "common.report.page.load."+Types.Map.toString()+"(\""+this.getId()+"\",\""+this.getQueryName()+"\","+
				  this.putParameters()+","+this.putMapper() + ",\""+ this.getMapData() + "\",\"" + this.getJoinBy() + "\"," +this.putOption() + ");";
		return this.doHighmapsTag(Types.Map.toString(),script.toString());
	}
	
	/**
	 * 统一初始化Highmap的标签输出
	 * @param type
	 * @param script
	 * @return
	 * @throws JspException
	 */
	protected int doHighmapsTag(String type,String script) throws JspException {
		  JspWriter out = pageContext.getOut();
		  
		  if(StringUtils.isBlank(type)) type = Types.Map.toString();
		  try {	
			  if(StringUtils.isBlank(this.getId()) || StringUtils.isBlank(this.getQueryName()) || 
					  StringUtils.isBlank(this.getMapData())  || StringUtils.isBlank(this.getJoinBy())){
				  /*返回参数错误页面*/
				  out.print("<div class=\"nodataBox\">");
				  out.print("	<div class=\"nodata\">");
				  out.print("		<i class=\""+this.getNodataMap().get(type)+" icon-nodata\"></i>");
				  out.print("		<div class=\"nodata-info\">");
				  out.print("			<p>对不起，标签输入参数有误！<br/>[id属性]、[queryName属性]、[mapData属性]、[joinBy]属性<br/>都不能为空！</p>");
				  out.print("		</div>");
				  out.print("	</div>");
				  out.print("</div>");
				  return TagSupport.EVAL_PAGE;				  
			  }			
		  	   
			   out.print("<div class=\"chart\" id=\""+this.getId()+"\"></div>");
			   if(!Types.Table.toString().equalsIgnoreCase(type)){
				   out.print("<script type=\"text/javascript\">");
				   out.print(script);
				   out.print("</script>");	
			   }			   		   
			   out.flush();
			   //out.close();
		   } catch (IOException e) {
		   	   e.printStackTrace();
		   }
		    return TagSupport.EVAL_PAGE;
	}

	public void setMapData(String mapData) {
		this.mapData = mapData;
	}

	public void setJoinBy(String joinBy) {
		this.joinBy = joinBy;
	}

	public String getMapData() {
		return mapData;
	}

	public String getJoinBy() {
		return joinBy;
	}
	
}
