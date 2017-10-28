package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileMeetingNewsDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.type.Alias;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

@Alias("CountryFileMeetingNewsDetailData")
public class CountryFileMeetingNewsDetailData extends BaseData{
	private String desc1;
	private List<Map<String,Object>>  pics1;

	public String getdesc1() {
		return desc1;
	}
	public void setdesc1(String desc1) {
		this.desc1 = desc1;
	}
	public List<Map<String, Object>> getpics1() {
		return pics1;
	}
	public void setpics1(List<Map<String, Object>> pics1) {
		this.pics1 = pics1;
	}
	/*int[] arr ={1,2,3,4,5};
	String arrString = Arrays.toString(arr);*/
	

	@Override
	public String toString() {
		List<String> pics1List = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(pics1)){
		for(int j=0;j<pics1.size();j++) {
				String mpath = String.valueOf(pics1.get(j).get("PATH"));
				pics1List.add(Global.getConfig("image.path")+mpath);
			}
		}
		return "{\"desc\":\"" + desc1 
				+ "\",\"pics\":" + pics1List
				+ "\"}  ";
	}


}
