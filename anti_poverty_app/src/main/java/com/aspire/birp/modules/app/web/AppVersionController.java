package com.aspire.birp.modules.app.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.app.dao.AppDao;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="app/version")
public class AppVersionController {
	
	private static Logger log = LoggerFactory.getLogger(AppVersionController.class);
	@Autowired
	private AppDao AppService;
	
	@RequestMapping("versionUpdate")
	@ResponseBody
	public Object receive(String params){
		Map<String, Object> map = new HashMap<String, Object>(); 
		System.out.println("接受字符串"+params);
		JSONObject jsonMap=null;
		try{
		  jsonMap = JSONObject.fromObject(params); 
		}catch(Exception e){
			log.error("参数格式不对",e);
			return "参数格式不对";
		}
		Iterator<String> it=null;
		try{
		  it = jsonMap.keys();  
		  while(it.hasNext()) {  
			  String k = it.next();  
			  Object value = jsonMap.get(k);
			  map.put(k, value);  
		  }
		}catch(Exception e){
			log.error("参数空",e);
			return "参数为空";
		}
	    String timeStamp = (String)jsonMap.get("timeStamp");
	    String  appId = (String)jsonMap.get("appId");
	    String  auth= (String)jsonMap.get("auth");
	    try{
		    if(auth.equals(generate(appId+timeStamp+"01202f6bd0754a409c8f16cd7ad23b36"))){
		    	System.out.println("匹配成功");
		    	Map<String, Object> result = this.AppService.queryCommonOne("aspire.bi.common.app.queryVersion", map);
		    	return result;  
		    }
		} catch (Exception e) {
			log.error("查询失败",e);
	}
	    System.out.println("匹配失败");
	    return null;
}
	 /**
     * 产生加密信息
     * @param key
     * @return
     */
   public String generate(String key) {
       String cacheKey;
       try {
           final MessageDigest mDigest = MessageDigest.getInstance("MD5");
           mDigest.update(key.getBytes());
           cacheKey = bytesToHexString(mDigest.digest());
       } catch (NoSuchAlgorithmException e) {
           cacheKey = String.valueOf(key.hashCode());
       }
       return cacheKey;
   }

   private String bytesToHexString(byte[] bytes) {
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < bytes.length; i++) {
           String hex = Integer.toHexString(0xFF & bytes[i]);
           if (hex.length() == 1) {
               sb.append('0');
           }
           sb.append(hex);
       }
       return sb.toString();
   }
    


}
