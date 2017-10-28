
package com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

import com.aspire.birp.common.utils.ClobToStringUtls;
import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.BaseListMode;

public class FilePlanData extends BaseData{

    private List<Plan> plans = null;

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
    
    public String listToStrs(List<Plan> list){
      Integer year = Integer.parseInt("2016");
      String[] tabTitleArr = {"三年帮扶总规划",String.valueOf(year)+"年度帮扶计划",String.valueOf(year+1)+"年度帮扶计划",String.valueOf(year+2)+"年度帮扶计划"};
  	  StringBuffer sb =new StringBuffer("[");
  	   for(int i = 0;i <list.size();i++)
  	   {
  		 Plan plan = list.get(i);
  		 sb.append("{");
  		 sb.append("\"summary\":{");
  		 sb.append("\"country_leader_sign\":").append("\""+plan.getCountryLeaderSign()+"\"").append(",");
  		 sb.append("\"house_holder_sign\":").append("\""+plan.getHouseHolderSign()+"\"").append(",");
  		 sb.append("\"time\":").append("\""+plan.getTime()+"\"");
  		 sb.append("}").append(",");
  		 sb.append("\"tab_title\":\""+tabTitleArr[i]+"\"").append(",");
  		 sb.append("\"content\":"+(plan.getContent()==null ? "\"\"" : "\""+ClobToStringUtls.ClobToString((Clob)plan.getContent())+"\""));
  		 if(i+1==list.size()){
  			sb.append("}");
  		 }else{
  			sb.append("}").append(",");
  		 }
  		 
  	   }
  	   sb.append("]");
  	   return sb.toString();
     }
    
    public String toString(){
    	return "{\"plans\":" + listToStrs(plans) + "}";
    	
    }

}
