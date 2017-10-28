package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileNews;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

@Alias("CountryFileNewsData")
public class CountryFileNewsData<CountryFileBase>  extends BaseData<CountryFileBase> {
	private List<CountryFileBase> chartConfig;

	public List<CountryFileBase> getChartConfig() {
		return chartConfig;
	}

	public void setChartConfig(List<CountryFileBase> chartConfig) {
		this.chartConfig = chartConfig;
	}

	@Override
	public String toString() {
		return "{\"list\":" + chartConfig + "}  ";
	}
	
}
