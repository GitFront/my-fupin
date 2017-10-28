package com.aspire.birp.modules.antiPoverty.model.poorFile.countryFileImplement;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

@Alias("CountryFileImplementData")
public class CountryFileImplementData<CountryFileImpBase>  extends BaseData<CountryFileImpBase>  {
	private String total;
	private String running;
	private String completed;
	private String invested;
	private String profit;
	private List<CountryFileImpBase> chartList;
	private List<CountryFileImpBase> ListTwo;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRunning() {
		return running;
	}
	public void setRunning(String running) {
		this.running = running;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public String getInvested() {
		return invested;
	}
	public void setInvested(String invested) {
		this.invested = invested;
	}
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public List<CountryFileImpBase> getChartList() {
		return chartList;
	}
	public void setChartList(List<CountryFileImpBase> chartList) {
		this.chartList = chartList;
	}
	
	public List<CountryFileImpBase> getListTwo() {
		return ListTwo;
	}
	public void setListTwo(List<CountryFileImpBase> ListTwo) {
		this.ListTwo = ListTwo;
	}
	@Override
	public String toString() {
		return "{"
			+ "\"summary\":{"
			+ "\"projects\":{"
				+"\"total\":\"" + total 
				+ "\",\"running\":\"" + running
				+ "\",\"completed\":\"" + completed 
				+ "\"},\"invested\":\""+ invested 
				+ "\",\"profit\":\"" + profit
			+ "\"},\"table\":{"  
				+ "\"listRunning\":" +ListTwo
				+ ",\"list_completed\":" +chartList+ "}} ";
	}
	
	
}
