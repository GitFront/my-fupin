package com.aspire.birp.modules.common.tags.report;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class HcGauge extends HcReport {

	private String decimal;
	
	public int doEndTag() throws JspException {
		/*动态生成javascript语句*/
		String script = "common.report.page.load."+Types.Gauge.toString()+"(\""+this.getId()+"\",\""+this.getQueryName()+"\","+
				  this.putParameters()+","+this.putMapper()+","+this.putOption()+","+this.putDecimal() + ");";
		return this.doHighchartTag(Types.Gauge.toString(),script.toString());
	}
	
	/**
	 * 转化数据精度参数格式
	 * @return 返回JSON格式的字符串
	 */
	private String putDecimal(){
	
		if(StringUtils.isBlank(this.decimal)){
			return "\"2\"";
		}else {
			return "\"" + this.decimal + "\"";
		} 
		
	}

	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}
	
}
