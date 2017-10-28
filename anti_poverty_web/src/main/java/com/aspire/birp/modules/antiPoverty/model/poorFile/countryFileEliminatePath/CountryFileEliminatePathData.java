package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileEliminatePath;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;


@Alias("CountryFileEliminatePathData")
public class CountryFileEliminatePathData<CountryFileListBase>  extends BaseData<CountryFileListBase> {
		private String year;
		private String month;
		private List<CountryFileListBase> chartConfig;
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public List<CountryFileListBase> getChartConfig() {
			return chartConfig;
		}
		public void setChartConfig(List<CountryFileListBase> chartConfig) {
			this.chartConfig = chartConfig;
		}
		@Override
		public String toString() {
			return "{"
					+ "\"default_time\":{"
					+"\"year\":\"" + year 
					+ "\",\"month\":\"" + month
					+ "\"},\"chartConfig\":" + listToStr(chartConfig) + "}  ";
		}
		
		
}
