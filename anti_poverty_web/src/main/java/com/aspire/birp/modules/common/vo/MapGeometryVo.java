
package com.aspire.birp.modules.common.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**  
 * @Title: 地图GEO数据封装对象
 * @Description: 用于生成地图GEOJSON数据
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2017年5月11日 下午4:43:58
 * @version: V1.0
 */
public class MapGeometryVo {

	private String type = "FeatureCollection";
	
	private List<MapGeoFeature> features;
	
	public MapGeometryVo() {
		features = new ArrayList<MapGeoFeature>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MapGeoFeature> getFeatures() {
		return features;
	}

	public void setFeatures(List<MapGeoFeature> features) {
		this.features = features;
	}

	public void addFeatures(MapGeoFeature features) {
		this.features.add(features);
	}

	@Override
	public String toString() {
		StringBuffer json = new StringBuffer();
		json.append("{\"type\":\""+getType()+"\",");
		json.append("\"features\":[");
		for (int i = 0; i < features.size(); i++) {
			MapGeoFeature feature = features.get(i);
			if(i != 0) json.append(",");
			json.append("{");
			json.append("\"type\": \""+feature.getType()+"\",");
			json.append("\"properties\": {");
			Map<String,Object> properties = feature.getProperties();
			Iterator<Entry<String,Object>> iter = properties.entrySet().iterator();
			int num = 0;
			while(iter.hasNext()){
				Map.Entry<String,Object> entry = iter.next();
				if(num == 0){
					json.append("\""+entry.getKey()+"\":\""+entry.getValue()+"\"");
				}else{
					json.append(",\""+entry.getKey()+"\":\""+entry.getValue()+"\"");
				}
				num++;
			}
			json.append("},");
			json.append("\"geometry\" : {");
			json.append("\"type\": \""+feature.getGeometry().getType()+"\",");
			json.append("\"coordinates\": "+feature.getGeometry().getCoordinateStr());
			json.append("}");
			json.append("}");
		}
		json.append("]");
		json.append("}");
		
		return json.toString();
	}
	
	
	
}


