package com.aspire.birp.modules.antiPoverty.model.poorFile;

import org.apache.ibatis.type.Alias;

@Alias("baseListMode")
public class BaseListMode {
	private String text;
	private String link;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "{\"text\":\"" + text + "\",\"link\":\"" + link + "\"} ";
	}
	
}
