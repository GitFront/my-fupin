package com.aspire.birp.modules.antiPoverty.model.dataMonitor.dataMonitorOrderBasicInfo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
@Alias("DataMonitorOrderBasicInfoData")
public class DataMonitorOrderBasicInfoData extends BaseData{
	private List<DataMonitorOrderBasicInfoBese> chartList;

	public List<DataMonitorOrderBasicInfoBese> getChartList() {
		return chartList;
	}

	public void setChartList(List<DataMonitorOrderBasicInfoBese> chartList) {
		this.chartList = chartList;
	}

	@Override
	public String toString() {
		return "{\"chartList\":\"" + chartList + "\"}  ";
	}
	
}
