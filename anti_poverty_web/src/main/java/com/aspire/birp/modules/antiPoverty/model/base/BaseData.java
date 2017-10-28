package com.aspire.birp.modules.antiPoverty.model.base;

import java.util.List;

import com.aspire.birp.modules.antiPoverty.model.poorFile.BaseListMode;

/**
 * data基类
 * @author zhudiyuan
 *
 */
public class BaseData<T> {
       public String listToStr(List<T> list){
    	  StringBuffer sb =new StringBuffer("[");
    	   for(int i = 0;i <list.size();i++)
    	   {
    		   sb.append(list.get(i).toString()).append(",");
    	   }
    	   if(list.size() == 0)
    	   {
    		   return sb.append("]").toString();
    	   }
    	   return sb.replace(sb.length()-1, sb.length(), "]").toString();
       }
       
}
