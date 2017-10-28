
package com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNews;


import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.common.utils.ClobToStringUtls;
import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan.Plan;

public class FileNewsData  extends BaseData{

    private List<News> list = new ArrayList<News>();

	public List<News> getList() {
		return list;
	}

	public void setList(List<News> list) {
		this.list = list;
	}
	
	private List<String> getpicsList(String pics){
		List<String> picsList = new ArrayList<String>();
        String[] picsArr = pics.split(";");
		for(int j=0;j<picsArr.length;j++) {
			picsList.add(Global.getConfig("image.path")+picsArr[j]);
		}
		return picsList;
	}
	
	 public String listToStrs(List<News> list){
	  	  StringBuffer sb =new StringBuffer("[");
	  	   for(int i = 0;i <list.size();i++)
	  	   {
	  		 News news = list.get(i);
	  		 sb.append("{");
	  		 sb.append("\"id\":").append(news.getId()).append(",");
	  		 sb.append("\"time\":").append("\""+news.getTime()+"\"").append(",");
	  		 sb.append("\"desc\":").append("\""+news.getDescs()+"\"").append(",");
	  		 sb.append("\"pics\":").append(news.getPics()==null ? "" : getpicsList(ClobToStringUtls.ClobToString((Clob)news.getPics())));
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
	    	return "{\"list\":" + listToStrs(list) + "}";
	    	}


}
