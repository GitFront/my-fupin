package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFilePlan;

import org.apache.ibatis.type.Alias;

@Alias("CountryFilePlanBase")
public class CountryFilePlanBase {
	private String countryLeaderSign;
	private String responsibleRign;
	private String time;
	private String tabTitle;
	private String content;
	public String getCountryLeaderSign() {
		return countryLeaderSign;
	}
	public void setCountryLeaderSign(String countryLeaderSign) {
		this.countryLeaderSign = countryLeaderSign;
	}
	public String getResponsibleRign() {
		return responsibleRign;
	}
	public void setResponsibleRign(String responsibleRign) {
		this.responsibleRign = responsibleRign;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTabTitle() {
		return tabTitle;
	}
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "{" 
				+ "\"summary\":{"
				+"\"countryLeaderRign\":\"" + countryLeaderSign
				+ "\",\"responsibleRign\":\"" + responsibleRign
				+ "\",\"time\":\"" + time 
				+ "\"},\"tabTitle\":\"" + tabTitle
				+ "\",\"content\":\"" + content + "\"}  ";
	}
	
}
