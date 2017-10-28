package com.aspire.birp.modules.common.tags.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;



@SuppressWarnings("serial")
public class HcReport extends TagSupport {
	
	private String id;

	/**
	 * mybatis的对应查询名称
	 */
	private String queryName;
	/**
	 * 自定义查询参数
	 */
	private String parameters;
	/**
	 * 数据字段对应信息
	 */
	private String mapper;	

	/**
	 * 报表高度定义：300px
	 */
	private String height;
	
	/**
	 * 自定义配置项
	 */
	private String option;
	
	/**
	 * 隐藏分组
	 */
	private String hideGroup;
	
	/**
	 * 行转列配置，针对同一行数据不同字段进行分组显示
	 */
	private String rowsGroup;
	
	/**
	 * 类型对应关系
	 */
	private Map<String,String> nodataMap;
	
	public HcReport() {
		super();
		this.nodataMap = new HashMap<String,String>();
		this.nodataMap.put("Pie", "PieChart-nodata");
		this.nodataMap.put("Bars", "histogram-nodata");
		this.nodataMap.put("StackBars", "histogram-nodata");
		this.nodataMap.put("Line", "trend-nodata");
		this.nodataMap.put("InvertedLine", "trend-nodata");
		this.nodataMap.put("TimeSeriesLine", "AreaChart-nodata");
		this.nodataMap.put("Area", "AreaChart-nodata");
		this.nodataMap.put("Gauge", "dashboard-nodata");
		this.nodataMap.put("Map", "map-nodata");
		this.nodataMap.put("Bubble", "BubbleChart-nodata");
		this.nodataMap.put("Scatter", "BubbleChart-nodata");
		this.nodataMap.put("default", "table-nodata");
	}
	
	/**
	 * 统一初始化Highchart的标签输出
	 * @param type
	 * @return
	 * @throws JspException
	 */
	protected int doHighchartTag(String type) throws JspException {
		  String script = "common.report.page.load."+type+"(\""+this.getId()+"\",\""+this.getQueryName()+"\","+
				   this.putParameters()+","+this.putMapper()+","+this.putOption()+");";
		    return this.doHighchartTag(type, script);
	}
	
	/**
	 * 统一初始化Highchart的标签输出
	 * @param type
	 * @return
	 * @throws JspException
	 */
	protected int doGroupchartTag(String type) throws JspException {
		  String rowsGroup_ = "";
		  if(StringUtils.isNotBlank(this.rowsGroup)) rowsGroup_ = this.rowsGroup;
		  String script = "common.report.page.load."+type+"(\""+this.getId()+"\",\""+this.getQueryName()+"\","+
				   this.putParameters()+","+this.putMapper()+","+this.putOption()+ "," + this.putHideGroup()+ ",\"" + rowsGroup_ + "\");";
		  return this.doHighchartTag(type, script);
	}
	
	/**
	 * 统一初始化Highchart的标签输出
	 * @param type
	 * @param script
	 * @return
	 * @throws JspException
	 */
	protected int doHighchartTag(String type,String script) throws JspException {
		  JspWriter out = pageContext.getOut();
		  
		  if(StringUtils.isBlank(type)) type = Types.Table.toString();
		  try {	
			  if(StringUtils.isBlank(this.getId()) || StringUtils.isBlank(this.getQueryName())){
				  /*返回参数错误页面*/
				  out.print("<div class=\"nodataBox\">");
				  out.print("	<div class=\"nodata\">");
				  out.print("		<i class=\""+this.nodataMap.get(type)+" icon-nodata\"></i>");
				  out.print("		<div class=\"nodata-info\">");
				  out.print("			<p>对不起，标签输入参数有误！<br/>id属性或queryName属性<br/>不能为空！</p>");
				  out.print("		</div>");
				  out.print("	</div>");
				  out.print("</div>");
				  return TagSupport.EVAL_PAGE;				  
			  }			
		  	   
			  StringBuffer divOut = new StringBuffer();
			  divOut.append("<div class=\"chart\" id=\""+this.getId()+"\" style=\"min-height:150px;");
			  if(StringUtils.isNotBlank(this.height)) 
				  divOut.append("height: "+this.height+";");
			  divOut.append(" \"></div>");
			  out.print(divOut.toString());
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

	/**
	 * 转化配置项格式
	 * @param opt
	 * @return
	 */
	protected String putOption(){
	
		StringBuffer opt_s = new StringBuffer();
		if( StringUtils.isBlank(this.option)) return "{}";
		opt_s.append("{");
		String[] opts = StringUtils.split(this.option, ";");
		for (int i = 0; i < opts.length; i++) {
			String temp_ = opts[i];
			if(temp_.indexOf(":") == -1) continue;
			if(temp_.indexOf("{") != -1 && temp_.indexOf("}") != -1 ) {
				opt_s.append(temp_ + ",");
				continue;
			}
			String[] o_ = StringUtils.split(temp_, ":");
			switch (o_.length) {
			case 2:
				opt_s.append(o_[0]+":"+o_[1]+",");
				break;
			default:
				break;
			}			
		}
		if(opt_s.indexOf(",") != -1) opt_s.deleteCharAt(opt_s.lastIndexOf(","));
		opt_s.append("}");
		return opt_s.toString();
	}
	
	/**
	 * 转化自定义查询参数格式
	 * @return 返回JSON格式的字符串
	 */
	public String putParameters(){
		
		StringBuffer opt_s = new StringBuffer();
		if(StringUtils.isBlank(this.parameters)) return "{}";
		opt_s.append("{");
		String[] opts = StringUtils.split(this.parameters, ";");
		for (int i = 0; i < opts.length; i++) {
			String temp_ = opts[i];
			String[] o_ = StringUtils.split(temp_, ":");
			switch (o_.length) {
			case 2:
				opt_s.append(o_[0]+":'"+o_[1]+"',");
				break;
			default:
				break;
			}			
		}
		if(opt_s.indexOf(",") != -1) opt_s.deleteCharAt(opt_s.lastIndexOf(","));
		opt_s.append("}");
		return opt_s.toString();
	}
	
	/**
	 * 转化自定义对应参数
	 * @return 返回JSON格式的字符串
	 */
	public String putMapper(){
	
		StringBuffer opt_s = new StringBuffer();
		if(StringUtils.isBlank(this.mapper)) return "{}";
		opt_s.append("{");
		String[] opts = StringUtils.split(this.mapper, ";");
		for (int i = 0; i < opts.length; i++) {
			String temp_ = opts[i];
			String[] o_ = StringUtils.split(temp_, ":");
			switch (o_.length) {
			case 2:
				opt_s.append(o_[0]+":'"+o_[1]+"',");
				break;
			default:
				break;
			}			
		}
		if(opt_s.indexOf(",") != -1) opt_s.deleteCharAt(opt_s.lastIndexOf(","));
		opt_s.append("}");
		return opt_s.toString();
	}
	
	/**
	 * 转化隐藏行参数格式
	 * @return 返回JSON格式的字符串
	 */
	public String putHideGroup(){
	
		if(StringUtils.isBlank(this.hideGroup)){
			return "\"\"";
		}else {
			return "\"" + this.hideGroup + "\"";
		} 
		
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getMapper() {
		return mapper;
	}

	public void setMapper(String mapper) {
		this.mapper = mapper;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getId() {
		if(StringUtils.isBlank(id)) id = UUID.randomUUID().toString();
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHideGroup(String hideGroup) {
		this.hideGroup = hideGroup;
	}

	public void setNodataMap(Map<String, String> nodataMap) {
		this.nodataMap = nodataMap;
	}

	public void setRowsGroup(String rowsGroup) {
		this.rowsGroup = rowsGroup;
	}

	public Map<String, String> getNodataMap() {
		return nodataMap;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHideGroup() {
		return hideGroup;
	}

	public String getRowsGroup() {
		return rowsGroup;
	}
	
	
	
}
