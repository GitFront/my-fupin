package com.aspire.birp.modules.common.service.impl;

import java.rmi.RemoteException;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.birp.common.utils.ClobToStringUtls;
import com.aspire.birp.modules.antiPoverty.web.indexMapController;
import com.aspire.birp.modules.common.service.PovertyGeometryService;
import com.aspire.birp.modules.sys.utils.UserUtils;

@Service
//@RunWith(SpringJUnit4ClassRunner.class)//表示整合JUnit4进行测试
//@ContextConfiguration(locations={"classpath*:*.xml"})//加载spring配置文件
public class PovertyGeometryServiceImpl implements PovertyGeometryService {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	@Override
	public String queryDataMap(String pac,String levelStr) {
		List<Map<String, Object>>selectList = null;
		List<Map<String, Object>> loction=null;
		String PkType=null;
		int level = Integer.valueOf(levelStr);
		if(level==5){
			selectList = sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryDataMapByPAC",pac);
		}else{
			selectList = sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryDataMapByPPAC",pac);
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("PAC", lsbPacToPac(pac));
		if(level==2){//市
			loction = sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryDataLocalByPKC_S",params);
			PkType="PKC";
		}else if(level==3){//县
			loction = sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryDataLocalByPKC_X",params);
			 PkType="PKC";
		}else if(level==4){//村镇
			loction = sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryDataLocalByPKH_Z",params);
			 PkType="PKH";
		}else if(level==5){//村
			loction = sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryDataLocalByPKH_C",params);
			 PkType="PKH";
		}
		StringBuffer sb=new StringBuffer();
		sb.append("{\"type\":\"FeatureCollection\",\"features\":[");
		if(loction.size()>0){
			for (int i = 0; i < loction.size(); i++) {
				Map<String, Object> map = loction.get(i);
//				if(StringUtils.isNotBlank(map.get("LONGITUDE").toString()) && StringUtils.isNotBlank(map.get("LATITUDE").toString())){
				sb.append("{\"properties\":{"
						+ "\"PAC\":\""+map.get("PAC")+"\"," 
						+ "\"name\":\""+map.get("NAME")+"\",");
						if(level>3){
							sb.append("\"PAC\":\""+map.get("FAM_ID")+"\",");
						}
				sb.append("\"type\":\""+PkType+"\"}," 
						+ "\"type\":\"Feature\","
						+ "\"geometry\":{\"type\":\"Point\",\"coordinates\":["+map.get("LONGITUDE")+","+map.get("LATITUDE")+"]}}"
						);
						if(i!=loction.size()-1){
							sb.append(",");
						}
//				}
			}
			sb.append(",");
		}
		for(int i=0;i<selectList.size();i++){
			Map<String, Object> map = selectList.get(i);
			Clob clob = (Clob)map.get("GEOMETRY");
			sb.append( "{\"type\":\"Feature\",\"properties\":{"
					+ "\"name\":\""+map.get("NAME")+"\","
					+ "\"PAC\":\""+map.get("PAC")+"\"," 
					+ "\"PPAC\":\""+map.get("PPAC")+"\"," 
					+ "\"drill-key\":\""+map.get("PAC")+"\"}" 
					+",\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":"+ClobToStringUtls.ClobToString(clob)+"}}");
			if(i!=selectList.size()-1){
				sb.append(",");
			}
		}
		sb.append("]}");
		
		/*try {
			OutputStream out = new FileOutputStream("test.json");
			IOUtils.write(sb.toString(),out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return sb.toString();
	}
	//全省村坐标
	@Override
	public String queryDataPiont() {
		List<Map<String, Object>> loction=null;
		String PkType=null;
		Set<String> areaList;
		List<Object> plist=new ArrayList<Object>();;
		try {
			areaList = UserUtils.getAreaList();
			 
			if(areaList!=null){
				for (String pac : areaList) {
					Map<String, Object> params=new HashMap<String,Object>();
					String p=pac;
					while('0'==pac.charAt(pac.length()-1)){
			          pac=pac.substring(0,pac.lastIndexOf("0"));
			      }
				  if(pac.length()>0&&pac.length()<=2){//市
					  params.put("pac", p);
			    	  params.put("level", 0);
			    	  plist.add(params);
			      }else if(pac.length()>2&&pac.length()<=4){//市
					  params.put("pac", p);
			    	  params.put("level", 1);
			    	  plist.add(params);
			      }else if(pac.length()>4&&pac.length()<=6){//区县
			    	  params.put("pac", p);
			    	  params.put("level", 2);
			    	  plist.add(params);
			      }else if(pac.length()>6&&pac.length()<=9){//镇
			    	  params.put("pac", p);
			    	  params.put("level", 3);
			    	  plist.add(params);
			      }else if(pac.length()>9&&pac.length()<=12){//村
			    	  params.put("pac", p);
			    	  params.put("level", 4);
			    	  plist.add(params);
			      }
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			loction = sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryDataLocalByPKC_P",plist);
			PkType="PKC";
		StringBuffer sb=new StringBuffer();
		sb.append("{\"type\":\"FeatureCollection\",\"features\":[");
		if(loction.size()>0){
			for (int i = 0; i < loction.size(); i++) {
				Map<String, Object> map = loction.get(i);
				sb.append("{\"properties\":{"
						+ "\"PAC\":\""+map.get("PAC")+"\"," 
						+ "\"name\":\""+map.get("NAME")+"\","
						+ "\"type\":\""+PkType+"\"}," 
						+ "\"type\":\"Feature\","
						+ "\"geometry\":{\"type\":\"Point\",\"coordinates\":["+map.get("LONGITUDE")+","+map.get("LATITUDE")+"]}}"
						);
						if(i!=loction.size()-1){
							sb.append(",");
						}
			}
		}
		sb.append("]}");
		return sb.toString();
	}
	@Test
	public void test() {
		queryDataMap("441900000000","");
		
	}
	@Override
	public String lsbPacToPac(String pac) {
		String selectOne=null;
		if(StringUtils.isNotBlank(pac)){
			 selectOne = sqlSessionTemplate.selectOne("aspire.bi.common.povertygeometry.lsbPacToPac", pac).toString();
		}else{
			selectOne="0";
		}
		return selectOne;
	}
	@Override
	public String ifFurthurPoor(String pc,String levelStr) {
		String selectOne="";
		String PAC=lsbPacToPac(pc);
		int level = Integer.valueOf(levelStr);
		if(level>4){
			selectOne = sqlSessionTemplate.selectOne("aspire.bi.common.povertygeometry.ifFurthurPoor", PAC).toString();
		}
		return selectOne;
	}
	@Override
	public String ifPoor(String pac, String level) {
		String selectOne="1";
		if(level.equals("country")){
			selectOne = sqlSessionTemplate.selectOne("aspire.bi.common.povertygeometry.ifFurthurPoor", pac).toString();
		}
		return selectOne;
	}
	@Override
	public  Map<String,Object> ifLevel(String pac,String levelStr) {
		Map<String,Object> map = new HashMap<String,Object>();
//		String pacc=pac;
		int level = Integer.valueOf(levelStr);
		 if(pac!=null){
//             while('0'==pac.charAt(pac.length()-1)){
//                pac=pac.substring(0,pac.lastIndexOf("0"));
//            }
//
//            if(pac.length()>2&&pac.length()<=4){//市
//                map.put("city", pacc);
//                map.put("province", "440000000000");
//            }else if(pac.length()>4&&pac.length()<=6){//区县
//            	map.put("county", pacc);
//            	map.put("city", pacc.substring(0, 4)+"00000000");
//            	map.put("province", "440000000000");
//            }else if(pac.length()>6&&pac.length()<=9){//镇
//            	map.put("town", pacc);
//            	map.put("county", pacc.substring(0, 6)+"000000");
//            	map.put("city", pacc.substring(0, 4)+"00000000");
//            	map.put("province", "440000000000");
//            }else if(pac.length()>9&&pac.length()<=12){//村
//            	map.put("country", pacc);
//            	map.put("town", pacc.substring(0, 9)+"000");
//            	map.put("county", pacc.substring(0, 6)+"000000");
//            	map.put("city", pacc.substring(0, 4)+"00000000");
//            	map.put("province", "440000000000");
//            }
            if(level==2){//市
            	 map.put("city", pac);
                 map.put("province", "440000000000");
    		}else if(level==3){//县
    			map.put("county", pac);
            	map.put("city", pac.substring(0, 4)+"00000000");
            	map.put("province", "440000000000");
    		}else if(level==4){//镇
    			map.put("town", pac);
            	map.put("county", pac.substring(0, 6)+"000000");
            	map.put("city", pac.substring(0, 4)+"00000000");
            	map.put("province", "440000000000");
    		}else if(level==5){//村
    			map.put("country", pac);
            	map.put("town", pac.substring(0, 9)+"000");
            	map.put("county", pac.substring(0, 6)+"000000");
            	map.put("city", pac.substring(0, 4)+"00000000");
            	map.put("province", "440000000000");
    		}
         }
		return map;
	}
	
	@Override
	public List<Map<String,Object>>  queryIndexMap() {
		return indexMapController.queryIndexMapData= sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryIndexMap",null);
	}
	
}
