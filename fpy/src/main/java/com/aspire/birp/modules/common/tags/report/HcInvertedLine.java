package com.aspire.birp.modules.common.tags.report;

import javax.servlet.jsp.JspException;

@SuppressWarnings("serial")
public class HcInvertedLine extends HcReport {

	
	public int doEndTag() throws JspException {
		return this.doGroupchartTag(Types.InvertedLine.toString());
	}
	
	
}
