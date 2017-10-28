package com.aspire.birp.modules.antiPoverty.model.dataMonitor.dataMonitorBasicInfo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.BaseListMode;

@Alias("dataMonitorBasicInfoData")
public class DataMonitorBasicInfoData<MonitorBasicList> extends BaseData<MonitorBasicList> {
	private List<MonitorBasicList> availableList;
	private String selectedValue;
	
	public List<MonitorBasicList> getAvailableList() {
		return availableList;
	}
	public void setAvailableList(List<MonitorBasicList> availableList) {
		this.availableList = availableList;
	}
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	@Override
	public String toString() {
		return "{\"years\":{" + "\"availableYears\":" +listToStr(availableList) + ",\"selectedValue\":\"" + selectedValue + "\"}}";
	}
	
	
	
}
