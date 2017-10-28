
package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;

@Alias("FileIncomeData")
public class FileIncomeData  extends BaseData{
    private String totalIncome;
    private String totalExpense;
    private String disposableIncome;
    private String yearAverageDisposableIncome;
    private String year;
    private String month;
    private List<ChartConfigBar> chartConfigBar = new ArrayList<ChartConfigBar>();

    public String getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}

	public String getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(String totalExpense) {
		this.totalExpense = totalExpense;
	}

	public String getDisposableIncome() {
		return disposableIncome;
	}

	public void setDisposableIncome(String disposableIncome) {
		this.disposableIncome = disposableIncome;
	}

	public String getYearAverageDisposableIncome() {
		return yearAverageDisposableIncome;
	}

	public void setYearAverageDisposableIncome(String yearAverageDisposableIncome) {
		this.yearAverageDisposableIncome = yearAverageDisposableIncome;
	}

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

	public List<ChartConfigBar> getChartConfigBar() {
        return chartConfigBar;
    }

    public void setChartConfigBar(List<ChartConfigBar> chartConfigBar) {
        this.chartConfigBar = chartConfigBar;
    }
    private String getChartList(){
    	StringBuffer sb =new StringBuffer("[");
	  	   for(int i = 0;i <chartConfigBar.size();i++){
	  		 ChartConfigBar ccb = chartConfigBar.get(i);
	  		 sb.append("{");
	  		 sb.append("\"name\":").append("\""+ccb.getName()+"\"").append(",");
	  		 sb.append("\"group\":").append("\""+ccb.getGroups()+"\"").append(",");
	  		 sb.append("\"value\":").append(ccb.getValue());
	  		if((i+1) == chartConfigBar.size()){
	  			sb.append("}");
	  		 }else{
	  			sb.append("}").append(",");
	  		 }
	  	   }
	  	 sb.append("]");
    	return sb.toString();
    }
    @Override
	public String toString() {
		return "{\"default_time\": {\"year\": \""+year+"\",\"month\": \""+month+"\"},\"stat\":{\"total_income\": \""+totalIncome+"元\",\"total_expense\": \""+totalExpense+"元\",\"disposable_income\": \""+disposableIncome+"元\",\"year_average_disposable_income\": \""+yearAverageDisposableIncome+"元\"},"
				+"\"chart_config_bar\": "+getChartList()+"}";
	}

}
