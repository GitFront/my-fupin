
package com.aspire.birp.modules.antiPoverty.model.poorFile.fileBusinessRest;

import java.util.ArrayList;
import java.util.List;

import com.aspire.birp.modules.antiPoverty.model.base.BaseData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.ChartConfigBar;

public class FileBusinessRestData extends BaseData{

    private List<TextLink> list = new ArrayList<TextLink>();

	public List<TextLink> getList() {
		return list;
	}

	public void setList(List<TextLink> list) {
		this.list = list;
	}
	
	private String getTextList(){
    	StringBuffer sb =new StringBuffer("[");
	  	   for(int i = 0;i <list.size();i++){
	  		 TextLink tl = list.get(i);
	  		 sb.append("{");
	  		 sb.append("\"text\":").append("\""+tl.getText()+"\"").append(",");
	  		 sb.append("\"link\":").append("\""+tl.getLink()+"\"");
	  		if((i+1) == list.size()){
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
		return "{\"list\":"+getTextList()+"}";
	}
    

}
