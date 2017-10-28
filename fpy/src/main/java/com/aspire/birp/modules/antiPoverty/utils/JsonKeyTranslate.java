package com.aspire.birp.modules.antiPoverty.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonKeyTranslate {
	 /**
     * 将Json的KEY值由驼峰转换为下划线加小写，例如：
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     * 
     * 
     */
	
	public static final char UNDERLINE='_';
	public static final int  JSONBASELEN=1;
	public static final int  JSONINSIDELEN=2;
    public static String toUnderlineJSONString(String str) 
       {
    	String strs[] = str.split(",");
    	StringBuilder sbJon = new StringBuilder();
        for (String o : strs) {
            String _tmp[]  = o.split(":");
            if(_tmp.length > JSONBASELEN){
                //替换数组2的值为驼峰
            	sbJon.append(camelToUnderline(_tmp[0]));
            	
            }else{
                //替换''里的数值为驼峰
            	sbJon.append(camelToUnderline(_tmp[0]));
            }
            //当Json里有实体类时
            if(_tmp.length > JSONINSIDELEN)
            {   
            	if(_tmp[1].contains("http:"))
            	{
            	//sbJon.append(":").append(camelToUnderline(_tmp[1])).append(":").append(_tmp[2]).append(",");
            	for(int i = 1; i < _tmp.length ; i ++)
            	 sbJon.append(":").append(_tmp[i]);
            	}
            	else if(_tmp[2].contains("http:"))
            	{
            		for(int i = 2; i < _tmp.length ; i ++)
            		{
                   	 sbJon.append(":").append(_tmp[i]);
                   	}
         
            	}
            	else{
            		for(int i = 1; i < _tmp.length-1 ; i ++)
            		{
                   	 sbJon.append(":").append(camelToUnderline(_tmp[i]));
                   	}
            		sbJon.append(":").append(_tmp[_tmp.length -1]);
            	}
            	sbJon.append(",");
            }else{
             sbJon.append(":").append(_tmp[1]).append(",");
            }
		}
       return sbJon.toString();
    }
   
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
           if( c >= 'A' && c <= 'Z' ) 
           {
             if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }
           }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
