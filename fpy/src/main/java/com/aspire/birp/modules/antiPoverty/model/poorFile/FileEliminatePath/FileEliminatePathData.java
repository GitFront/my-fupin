
package com.aspire.birp.modules.antiPoverty.model.poorFile.FileEliminatePath;


import java.util.ArrayList;
import java.util.List;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.ChartConfigBar;

public class FileEliminatePathData extends BaseData{

    private List<ChartConfigBar> chartConfig = new ArrayList<ChartConfigBar>();
    private String year;
    private String month;

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

	public List<ChartConfigBar> getChartConfig() {
		return chartConfig;
	}

	public void setChartConfig(List<ChartConfigBar> chartConfig) {
		this.chartConfig = chartConfig;
	}
	
	 private String getChartList(){
	    	StringBuffer sb =new StringBuffer("[");
		  	   for(int i = 0;i <chartConfig.size();i++){
		  		 ChartConfigBar ccb = chartConfig.get(i);
		  		 sb.append("{");
		  		 sb.append("\"name\":").append("\""+ccb.getName()+"\"").append(",");
		  		 sb.append("\"group\":").append("\""+ccb.getGroups()+"\"").append(",");
		  		 sb.append("\"value\":").append(ccb.getValue());
		  		if((i+1) == chartConfig.size()){
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
		return "{\"default_time\":{\"year\":\""+year+"\",\"month\":\""+month+"\"},"
				+"\"chart_config\":"+getChartList()+"}";
	}

}
