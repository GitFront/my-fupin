package com.aspire.birp.modules.demo.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}")
public class DemoController {
	
	private static Logger log = Logger.getLogger(DemoController.class);

	@RequestMapping("/2016maps.htm")
	public String doMapDemo(){
		log.info("==============连接至地图测试平台===============");
		return "/modules/demo/map_demo";
	}
	
}
