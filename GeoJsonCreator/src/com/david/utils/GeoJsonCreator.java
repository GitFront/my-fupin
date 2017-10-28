
package com.david.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.david.vo.MapGeoFeature;
import com.david.vo.MapGeoGeometry;
import com.david.vo.MapGeometryVo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @Title: GeoJsonCreator.java
 * @Description: TODO(用一句话描述该文件做什么)
 * @Copyright: aspire Copyright (C) 2009
 * @author: <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a>
 * @Email: zhangjianxiong@aspirecn.com
 * @date: 2017年9月19日 下午6:34:06
 * @version: V1.0
 */
public class GeoJsonCreator {

	private static Boolean bool = false;

	/**
	 * 
	 */
	public GeoJsonCreator() {
		// TODO Auto-generated constructor stub
	}

	public static void readFastJson() throws IOException {
		/*
		 * String json = "{" + "\"array\": [1,2,3]," + "\"arraylist\": [" +
		 * "{\"a\": \"b\"," + "\"c\": \"d\"," + "\"e\": \"f\"}," +
		 * "{\"a\": \"b\"," + "\"c\": \"d\"," + "\"e\": \"f\"}," +
		 * "{\"a\": \"b\"," + "\"c\": \"d\"," + "\"e\": \"f\"}  " + "]," +
		 * "\"object\": {" + "\"a\": \"b\"," + "\"c\": \"d\"," +
		 * "\"e\": \"f\"}," + "\"string\": \"Hello World\"" + "}"; reader = new
		 * JSONReader(new FileReader(
		 * "C:/Users/david/Documents/Develop/MyEclipse/03_Aspire/GeoJsonCreator/src/com/david/utils/FeaturesToJSON1.json"
		 * )); reader.startObject();
		 * System.out.print("start read json with fastjson"); while
		 * (reader.hasNext()) { String key = reader.readString();
		 * System.out.println("key " + key); } reader.endObject();
		 * System.out.println("start fastjson");
		 */
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(
					new FileInputStream(
							"C:/Users/david/Documents/Develop/MyEclipse/03_Aspire/GeoJsonCreator/src/com/david/utils/FeaturesToJSON1.json"),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 50 * 1024 * 1024);
		String line;
		StringBuilder stringBuilder = new StringBuilder();
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}
		bufferedReader.close();
		inputStreamReader.close();
		JSONObject jsonDate = JSON.parseObject(bufferedReader.toString());
		JSONArray features = jsonDate.getJSONArray("features");
		for (int i = 0; i < features.size(); i++) {
			JSONObject attriArray = features.getJSONObject(i);
			JSONObject attributes = attriArray.getJSONObject("attributes");

			String PAC = attributes.getString("PAC");
			String NAME = attributes.getString("NAME");
			String PPAC = PAC.substring(0, 2) + "0000000000";
			System.out.println("PAC : " + PAC + " ； PPAC ：" + PPAC + " ； NAME ：" + NAME);
		}

	}

	public static void readForGJson() throws IOException {
		/*
		 * String json = "{" + "\"array\": [1,2,3]," + "\"arraylist\": [" +
		 * "{\"a\": \"b\"," + "\"c\": \"d\"," + "\"e\": \"f\"}," +
		 * "{\"a\": \"b\"," + "\"c\": \"d\"," + "\"e\": \"f\"}," +
		 * "{\"a\": \"b\"," + "\"c\": \"d\"," + "\"e\": \"f\"}  " + "]," +
		 * "\"object\": {" + "\"a\": \"b\"," + "\"c\": \"d\"," +
		 * "\"e\": \"f\"}," + "\"string\": \"Hello World\"" + "}"; reader = new
		 * JSONReader(new FileReader(
		 * "C:/Users/david/Documents/Develop/MyEclipse/03_Aspire/GeoJsonCreator/src/com/david/utils/FeaturesToJSON1.json"
		 * )); reader.startObject();
		 * System.out.print("start read json with fastjson"); while
		 * (reader.hasNext()) { String key = reader.readString();
		 * System.out.println("key " + key); } reader.endObject();
		 * System.out.println("start fastjson");
		 */
		
		int id = 4;
		
		JsonParser parser = new JsonParser();
		
		String abcPath = "/home/aspire-bigdata/software/maps/FeaturesToJSON"+id+".json";

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(abcPath), "UTF-8"),
				50 * 1024 * 1024);

		JsonObject object = (JsonObject) parser.parse(in);

		JsonArray array = object.get("features").getAsJsonArray();
		for (int i = 0; i < array.size(); i++) {
			JsonObject subObject = array.get(i).getAsJsonObject();
			JsonObject json1 = subObject.get("attributes").getAsJsonObject();
			JsonObject json2 = subObject.get("geometry").getAsJsonObject();

			JSONArray JArray = new JSONArray();
			JSONArray JArrayOne = new JSONArray();

			int leng = 6;// 保留几位小数
			int dec = 0;// 满十减一
			
			if (id==1) {
				dec = 2;
			} else if (id==2) {
				dec = 4;
			} else if (id==3) {
				dec = 6;
			} else if (id==4) {
				dec = 15;
			}

			JsonArray JArs = json2.get("rings").getAsJsonArray();

			for (int j = 0; j < JArs.size(); j++) {
				JsonArray JArsk = JArs.get(j).getAsJsonArray();

				for (int k = 0; k < JArsk.size(); k++) {
					JsonArray JArskl = JArsk.get(k).getAsJsonArray();
					if (dec != 0 && (k % dec != 0 || k == 0)) {
						for (int l = 0; l < JArskl.size() / 2; l++) {
							Double a = JArskl.get(l).getAsDouble();
							Double b = JArskl.get(l + 1).getAsDouble();

							BigDecimal bd = new BigDecimal(a);
							BigDecimal setScale = bd.setScale(leng, bd.ROUND_DOWN);

							BigDecimal bdf = new BigDecimal(b);
							BigDecimal setScaleTwo = bdf.setScale(leng, bd.ROUND_DOWN);

							String geomey = "[" + setScale + "," + setScaleTwo + "]";
							
							JArray.add(JSONArray.parseArray(geomey));// 将JSONObject对象添加到Json数组中
						}
					}
				}
			}
			JArrayOne.add(JArray);
						
			
			String PAC = json1.get("PAC").getAsString();
			String NAME = json1.get("NAME").getAsString();
			
			if(NAME.indexOf("飞地") != -1&& "飞地".equals(NAME.substring(NAME.indexOf("飞地"), NAME.length())))
					continue;
			if(NAME.indexOf("争议地") != -1&& "争议地".equals(NAME.substring(NAME.indexOf("争议地"), NAME.length())))
				continue;
			
			
			String PPAC = PAC.substring(0, 2) + "0000000000";
			
			if (id==1) {
				PPAC = PAC.substring(0, 2) + "0000000000";
			} else if (id==2) {
				PPAC = PAC.substring(0, 4) + "00000000";
			} else if (id==3) {
				PPAC = PAC.substring(0, 6) + "000000";
			} else if (id==4) {
				PPAC = PAC.substring(0, 9) + "000";
			}
			
			System.out.println("PAC : " + PAC + " ； PPAC ：" + PPAC + " ； NAME ：" + NAME);

			String GEOMETRY = "[" + JArrayOne +"]";

			MapGeoGeometry ggt = new MapGeoGeometry();
			ggt.setCoordinateStr(GEOMETRY);

			Map<String, Object> features = new HashMap<String, Object>();
			features.put("name", NAME);
			features.put("PAC", PAC);
			features.put("PPAC", PPAC);
			features.put("drillkey", PAC);

			MapGeoFeature gft = new MapGeoFeature();
			gft.setGeometry(ggt);
			gft.setProperties(features);

			List<MapGeoFeature> feat = new ArrayList<MapGeoFeature>();
			feat.add(gft);

			MapGeometryVo gtv = new MapGeometryVo();
			gtv.setFeatures(feat);
			setBool(false);
			String filenameTemp = "/home/aspire-bigdata/software/maps/temp/" + PAC + ".json";// 文件路径+名称+文件类型
			File file = new File(filenameTemp);
			try {
				// 如果文件不存在，则创建新的文件
				if (!file.exists()) {
					file.createNewFile();
					setBool(true);
					System.out.println("success create file,the file is " + filenameTemp);
					// 创建文件成功后，写入内容到文件里
					writeFileContent(filenameTemp, gtv.toString());
				} else {
					System.out.println(PAC + "================");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		System.out.println("====================== END ========================");
	}

	/**
	 * 向文件中写入内容 
	 * @param filepath 文件路径与名称
	 * @param newstr  写入的内容
	 * @return
	 * @throws IOException
	 */
	public static boolean writeFileContent(String filepath, String newstr) throws IOException {
		Boolean bool = false;
		String filein = newstr + "\r\n";// 新写入的行，换行
		String temp = "";

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filepath);// 文件路径(包括文件名称)
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buffer = new StringBuffer();

			// 文件原有内容
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				// 行与行之间的分隔符 相当于“\n”
				buffer = buffer.append(System.getProperty("line.separator"));
			}
			buffer.append(filein);

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buffer.toString().toCharArray());
			pw.flush();
			bool = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// 不要忘记关闭
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return bool;
	}

	/**
	 * @param args
	 * @author 张健雄
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			// GeoJsonCreator.readBigJson();
			GeoJsonCreator.readForGJson();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return the bool
	 */
	public static Boolean getBool() {
		return bool;
	}

	/**
	 * @param bool the bool to set
	 */
	public static void setBool(Boolean bool) {
		GeoJsonCreator.bool = bool;
	}

}
