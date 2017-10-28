package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNewsDetail;

import java.util.List;

import com.aspire.birp.modules.antiPoverty.model.base.BaseModel;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNews.News;

public class FileNewsDetail extends BaseModel{
	private List<String> picsList;
	@Override
	public String toString() {
		try {
			News news = (News)getData();
			return "{\"code\":\"" + getCode() + "\",\"msg\":\"" + getMsg() + "\",\"data\":{\"desc:\"" +news.getDescs()+ "\",\"pics\":"+picsList+"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public List<String> getPicsList() {
		return picsList;
	}
	public void setPicsList(List<String> picsList) {
		this.picsList = picsList;
	}
	
	
}
