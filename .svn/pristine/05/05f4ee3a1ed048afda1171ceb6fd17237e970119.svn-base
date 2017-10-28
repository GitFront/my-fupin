package com.aspire.birp.modules.antiPoverty.model.poorFile.poorFilecountryFileDataPath;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
@Alias("PoorFilecountryFileDataPathData")
public class PoorFilecountryFileDataPathData<PoorBase>  extends BaseData<PoorBase> {
	private String date;
	private String time;
	private String publisherOrganization;
	private String publisherName;
	private List<PoorBase> list1;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPublisherOrganization() {
		return publisherOrganization;
	}

	public void setPublisherOrganization(String publisherOrganization) {
		this.publisherOrganization = publisherOrganization;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public List<PoorBase> getList1() {
		return list1;
	}

	public void setList1(List<PoorBase> list1) {
		this.list1 = list1;
	}

	@Override
	public String toString() {
		return "{\"date\":\"" + date 
				+ "\",\"time\":\"" + time
				+ "\",\"publisherOrganization\":\""+ publisherOrganization
				+ "\",\"publisherName\":\"" + publisherName 
				+ "\",\"list\":\""+ listToStr(list1) 
				+ "\"}  ";
	}
	
	
}
