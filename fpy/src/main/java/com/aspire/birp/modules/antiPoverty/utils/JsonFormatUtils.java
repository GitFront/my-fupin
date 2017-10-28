package com.aspire.birp.modules.antiPoverty.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/** 
 * 格式化json格式公用类 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:36:50 
 */
public class JsonFormatUtils {
	/**
	 * 得到格式化json数据 退格用\t 换行用\r
	 */
	public static String JsonFormat(String jsonStr) {
		int level = 0;
		StringBuffer jsonForMatStr = new StringBuffer();
		for (int i = 0; i < jsonStr.length(); i++) {
			char c = jsonStr.charAt(i);
			if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
				jsonForMatStr.append(getLevelStr(level));
			}
			switch (c) {
			case '{':
			case '[':
				jsonForMatStr.append(c + "\n");
				level++;
				break;
			case ',':
				jsonForMatStr.append(c + "\n");
				break;
			case '}':
			case ']':
				jsonForMatStr.append("\n");
				level--;
				jsonForMatStr.append(getLevelStr(level));
				jsonForMatStr.append(c);
				break;
			default:
				jsonForMatStr.append(c);
				break;
			}
		}
		return jsonForMatStr.toString();
	}

	private static String getLevelStr(int level) {
		StringBuffer levelStr = new StringBuffer();
		for (int levelI = 0; levelI < level; levelI++) {
			levelStr.append("\t");
		}
		return levelStr.toString();
	}
	
	  /**
     * 将对象的大写转换为下划线加小写，例如：userName-->user_name
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String toUnderlineJSONString(Object object) throws JsonProcessingException{
    	ObjectMapper mapper = MaperJson.getInstance();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.setSerializationInclusion(Include.NON_NULL);      
        String reqJson = mapper.writeValueAsString(object);
        return reqJson;
    }
    /**
     * 将Json的KEY值由驼峰转换为下划线加小写，例如：
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String toUnderlineJSONStringByStr(String str )throws JsonProcessingException{
    	
        String reqJson = JsonKeyTranslate.toUnderlineJSONString(str);
        return reqJson;
    }
}
