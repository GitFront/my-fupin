package com.aspire.birp.modules.antiPoverty.web;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.NewDateService;
import com.aspire.birp.modules.common.service.PovertyGeometryService;

@Controller
@RequestMapping(value = "index/map")
public class indexMapController implements ApplicationListener<ContextRefreshedEvent> {
	
	private static Logger log = Logger.getLogger(indexMapController.class);
	@Autowired
	private PovertyGeometryService  povertyGeometryService;
	public static List<Map<String,Object>> queryIndexMapData=null;
	@Autowired
	private NewDateService newDateService;	

	
	@RequestMapping("/mapData")
	@ResponseBody
 	public  Object queryGridData(){
		try {
			if(queryIndexMapData==null){
				log.error("数据为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("地图首页数据，应该为空了", e);
		}
   		return queryIndexMapData;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO 实现 ApplicationListener<ContextRefreshedEvent> Sprinng初始化完成后执行
		if(event.getApplicationContext().getParent()==null){
			//地图首页数据
			queryIndexMapData = povertyGeometryService.queryIndexMap();
			//数据最新时间
			newDateService.refreshDateContants();
		}
	}



	
	


}
