package com.aspire.birp.modules.common.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.modules.antiPoverty.dao.NewDateDao;
import com.aspire.birp.modules.common.service.PovertyGeometryService;
import com.aspire.birp.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/map")
public class PovertyGeometryController {
	
	private static Logger log = Logger.getLogger(PovertyGeometryController.class);
	
	@Autowired
	private PovertyGeometryService povertyGeometryService;
	@Autowired
	private NewDateDao  newDateDao;
	


	@RequestMapping("/mapPac")
	@ResponseBody
 	public Object queryGridDataPac(String pacid,String level){
		Map<String,Object> params = new HashMap<String,Object>();
		String queryDataMap =null;
		try{  
				queryDataMap = povertyGeometryService.lsbPacToPac(pacid);
				params.put("pac", queryDataMap);
  		} catch (Exception e) {
  			
				log.error("查询建档立卡pac失败",e);
 		}
   		return params;
	}
	@RequestMapping("/mapDate")
	@ResponseBody
 	public Map<String,Object> queryGridMapDate(){
		Map<String,Object> reMap = new HashMap<String,Object>();
		try{  
			int statTime = newDateDao.queryMapDate();
			int ecStatMonth = newDateDao.queryEcMapDate();
			/*获取地图GIS数据路径*/
			String geoPath = Global.getConfig("map.path");
			
			UserUtils.putHelpPacToParams(reMap);
			
			reMap.put("statTime", statTime);
			reMap.put("ecStatMonth", ecStatMonth);
			reMap.put("geoPath",geoPath);
			
			/*查询地图权限*/
			String userPac = UserUtils.getAreaList().iterator().next().toString();
			Map<String,String> mapInfo = povertyGeometryService.pacToLsbPac(userPac);
			int userLevel = 5;
			if(userPac != null && userPac.length() == 12){
				if(userPac.substring(2,4).equals("00")){
					userLevel = 1;
				}else if(userPac.substring(4,6).equals("00")){
					userLevel = 2;
				}else  if(userPac.substring(6,9).equals("000")){
					userLevel = 3;
				}else if(userPac.substring(9,12).equals("000")){
					userLevel = 4;
				}
			}else{
				userPac = "449999999999";
			}
			reMap.put("mapPac", mapInfo.get("pac"));
			reMap.put("mapName", mapInfo.get("name"));
			reMap.put("userPac", userPac);
			reMap.put("userLevel", userLevel);
  		} catch (Exception e) {
			log.error("查询建地图最新统计时间及权限失败",e);
 		}
		
   		return reMap;
	}
	
}
