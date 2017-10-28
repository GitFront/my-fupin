
package com.aspire.birp.modules.common.vo;

import java.io.Reader;
import java.sql.Clob;

/**  
 * @Title: 地图GEO数据经纬度对象
 * @Description: 用于生成地图区域块的GEO信息
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2017年5月11日 下午4:50:26
 * @version: V1.0
 */
public class MapGeoGeometry {

	/*经纬度类型*/
	private String type = "MultiPolygon";
	/*经纬度组信息*/
	private Object[] coordinates;
	/*经纬度组信息（字符串格式）*/
	private String coordinateStr;
	
	public MapGeoGeometry() {
	}
	
	public MapGeoGeometry(String type) {
		super();
		this.type = type;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Object[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Object[] coordinates) {
		this.coordinates = coordinates;
	}

	public String getCoordinateStr() {
		return coordinateStr;
	}

	public void setCoordinateStr(String coordinateStr) {
		this.coordinateStr = coordinateStr;
	}

	public void setCoordinates(Clob coordinates) {
		String coordinatesStr =  ClobToString(coordinates);
		this.coordinateStr = coordinatesStr;
	}
	
	public void setCoordinates(Double longitude , Double latitude) {
		this.coordinates = new Object[]{longitude,latitude};
	}

	/**
     * 处理clob大字段文件属性
     * @param clob
     * @return
     * @author 张健雄
     */
    public static String ClobToString(Clob  clob)  { 
		 if (clob == null)  
	            return null;
	        StringBuffer sb = new StringBuffer();
	        Reader clobStream = null;  
	        try {  
	            clobStream = clob.getCharacterStream();  
	            char[] b = new char[60000];// 每次获取60K  
	            int i = 0;  
	            while ((i = clobStream.read(b)) != -1) {  
	                sb.append(b, 0, i);  
	            }  
	        } catch (Exception ex) {  
	            sb = null;  
	        } finally {  
	            try {  
	                if (clobStream != null) {  
	                    clobStream.close();  
	                }  
	            } catch (Exception e) {  
	            }  
	        }  
	        if (sb == null) {
	        	return null; 
	        }else {
	        	 return sb.toString();  
	        }
	    }
	
}

