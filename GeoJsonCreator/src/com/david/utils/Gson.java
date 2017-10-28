package com.david.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.david.vo.MapGeoFeature;
import com.david.vo.MapGeoGeometry;
import com.david.vo.MapGeometryVo;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Gson {
	

	private static Boolean bool = false;
	
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
	
	public static void  readForGjson(int id,String filePath,String exportPath ){
		try {

			int dec = 0;// 满十减一

			if (id == 1) {
				dec = 2;
			} else if (id == 2) {
				dec = 4;
			} else if (id == 3) {
				dec = 6;
			} else if (id == 4) {
				dec = 15;
			}

			
			JsonParser parser = new JsonParser(); // 创建JSON解析器

			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"),
					50 * 1024 * 1024);

			JsonObject object = (JsonObject) parser.parse(in); // 创建JsonObject对象

			JsonArray array = object.get("features").getAsJsonArray(); // 得到为features的数组

			List<Map<String, Object>> lis = new ArrayList<Map<String, Object>>();// 存储所有的pacGEOMETRY

			DecimalFormat formater = new DecimalFormat("#0.######");// 保留小数位不四舍五入
			formater.setRoundingMode(RoundingMode.FLOOR);

			for (int i = 0; i < array.size(); i++) {// 获取所有的PAC GEOMETRY 添加到list
													// map
				JsonObject subObject = array.get(i).getAsJsonObject();
				JsonObject json1 = subObject.get("attributes").getAsJsonObject();
				JsonObject json2 = subObject.get("geometry").getAsJsonObject();
				JSONArray JArray = new JSONArray();
				 StringBuilder stringBuilder=new StringBuilder(); 
				JsonArray JArs = json2.get("rings").getAsJsonArray();
				for (int j = 0; j < JArs.size(); j++) {
					JsonArray JArsk = JArs.get(j).getAsJsonArray();
	                 StringBuilder stringBu=new StringBuilder();  
					for (int k = 0; k < JArsk.size(); k++) {
						JsonArray JArskl = JArsk.get(k).getAsJsonArray();
						if (dec != 0 && (k % dec != 0 || k == 0)) { // 满十减一
							for (int l = 0; l < JArskl.size() / 2; l++) {
								String setScale = formater.format(JArskl.get(l).getAsDouble());
								String setScaleTwo = formater.format(JArskl.get(l + 1).getAsDouble());
								String geomey = "[" + setScale + "," + setScaleTwo + "]";
								 stringBu.append(","+geomey);//将JSONObject对象添加到Json数组中
							}
						}

					}
					   stringBuilder.append(",["+stringBu.toString().substring(1, stringBu.length())+"]");
				}
				Map<String, Object> ames = new HashMap<String, Object>();
				ames.put("PAC", json1.get("PAC").getAsString());
				ames.put("GEOMETRY", stringBuilder.toString().substring(1, stringBuilder.length()));
				lis.add(ames);
			}
			for (int i = 0; i < array.size(); i++) {
				JsonObject subObject = array.get(i).getAsJsonObject();
				JsonObject attributes = subObject.get("attributes").getAsJsonObject();

				 StringBuilder stringBuilder=new StringBuilder(); 

                 for (int j = 0; j < lis.size(); j++) {
                		 if(attributes.get("PAC").getAsString().equals(lis.get(j).get("PAC"))){
 							stringBuilder.append(","+lis.get(j).get("GEOMETRY").toString());
            		}
            		
                 }

				String PAC = attributes.get("PAC").getAsString();
				String NAME = attributes.get("NAME").getAsString();

				if (NAME.indexOf("飞地") != -1 && "飞地".equals(NAME.substring(NAME.indexOf("飞地"), NAME.length())))
					continue;
				if (NAME.indexOf("争议地") != -1 && "争议地".equals(NAME.substring(NAME.indexOf("争议地"), NAME.length())))
					continue;

				String PPAC = PAC.substring(0, 2) + "0000000000";

				if (id == 1) {
					PPAC = PAC.substring(0, 2) + "0000000000";
				} else if (id == 2) {
					PPAC = PAC.substring(0, 4) + "00000000";
				} else if (id == 3) {
					PPAC = PAC.substring(0, 6) + "000000";
				} else if (id == 4) {
					PPAC = PAC.substring(0, 9) + "000";
				}

				System.out.println("PAC : " + PAC + " ； PPAC ：" + PPAC + " ； NAME ：" + NAME);

                String GEOMETRY="["+stringBuilder.toString().substring(1, stringBuilder.length())+"]";
				
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
				String filenameTemp = exportPath + PAC + ".json";// 文件路径+名称+文件类型
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
			in.close();
			System.out.println("==================END===========================");
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @author 张健雄
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int id = Integer.valueOf(args[0]);
		
		String abcPath = "/home/aspire-bigdata/software/maps/FeaturesToJSON" + id + ".json"; 
		String tempPath = "/home/aspire-bigdata/software/maps/temp/";
		
		/*String abcPath = "C:/Users/david/Downloads/maps/HK_geo.json";
		String tempPath = "C:/Users/david/Downloads/temp/";*/
	
		Gson.readForGjson(id,abcPath,tempPath);

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
		Gson.bool = bool;
	}
	
}