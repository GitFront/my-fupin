
package com.aspire.birp.modules.antiPoverty.model.poorFile.fileBusinessRest;

import org.apache.ibatis.type.Alias;

@Alias("TextLink")
public class TextLink {

    private String text;
    private String link;
    private String famId;

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

	public String getFamId() {
		return famId;
	}

	public void setFamId(String famId) {
		this.famId = famId;
	}

}
