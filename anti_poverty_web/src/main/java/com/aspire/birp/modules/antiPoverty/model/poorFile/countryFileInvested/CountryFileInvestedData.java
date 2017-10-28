package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileInvested;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

@Alias("CountryFileInvestedData")
public class CountryFileInvestedData<CountryBase>  extends BaseData<CountryBase> {
	private String total;
	private String cur_year;
	private List<CountryBase> chartConfig;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCur_year() {
		return cur_year;
	}
	public void setCur_year(String cur_year) {
		this.cur_year = cur_year;
	}
	public List<CountryBase> getChartConfig() {
		return chartConfig;
	}
	public void setChartConfig(List<CountryBase> chartConfig) {
		this.chartConfig = chartConfig;
	}
	@Override
	public String toString() {
		return "{" 
				+ "\"summary\":{"
				+"\"total\":\"" + total 
				+ "\",\"cur_year\":\"" + cur_year
				+ "\",\"table\":\"" + chartConfig + "\"}  ";
	}
	
}
