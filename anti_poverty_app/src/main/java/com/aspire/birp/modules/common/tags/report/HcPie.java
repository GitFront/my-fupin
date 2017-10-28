package com.aspire.birp.modules.common.tags.report;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class HcPie extends HcReport {
	
	/**
	 * 行转列配置，针对同一行数据不同字段进行分维度显示
	 */
	private String rowsValue;
	
	public int doEndTag() throws JspException {
		String rowsValue_ = "";
		if(StringUtils.isNotBlank(this.rowsValue)) rowsValue_ = this.rowsValue;
		String script = "common.report.page.load."+Types.Pie.toString()+"(\""+this.getId()+"\",\""+this.getQueryName()+"\","+
				   this.putParameters()+","+this.putMapper()+","+this.putOption()+",\"" + rowsValue_ + "\");";
		return this.doHighchartTag(Types.Pie.toString(), script);
	}

	public void setRowsValue(String rowsValue) {
		this.rowsValue = rowsValue;
	}
	
}
