package com.aspire.birp.modules.common.service.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Clob;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aspire.birp.modules.common.service.PovertyGeometryService;

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
	public String queryDataMap(String ppac) {
		List<Map<String, Object>>selectList = sqlSessionTemplate.selectList("aspire.bi.common.povertygeometry.queryDataMap",ppac);
		StringBuffer sb=new StringBuffer();
		sb.append("{\"type\":\"FeatureCollection\",\"features\":[");
		
		for(int i=0;i<selectList.size();i++){
			Map<String, Object> map = selectList.get(i);
			
			Clob clob = (Clob)map.get("GEOMETRY");
			sb.append( "{\"type\":\"Feature\",\"properties\":{"
					+ "\"name\":\""+map.get("NAME")+"\","
					+ "\"pac\":\""+map.get("PAC")+"\"," 
					+ "\"ppac\":\""+map.get("PPAC")+"\"," 
					+ "\"drill-key\":\""+map.get("PAC")+"\"}" 
					+",\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":"+ClobToString(clob)+"}}");
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
	        if (sb == null)  
	            return null;  
	        else  
	            return sb.toString();  
	    }  
	
	
	@Test
	public void test() {
		queryDataMap("441900000000");
		
	}
}
