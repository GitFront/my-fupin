package com.aspire.birp.modules.dataMapping.base.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aspire.bi.common.web.controller.BaseController;
import com.aspire.birp.modules.dataMapping.mapping.service.DmMappingService;

/**
 * Title: 智能查询功能公共服务控制类 <br>
 * Description: 主要用于处理智能查询的通用功能 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2015年9月15日 下午14:45:03 <br>
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/dataMapping")
public class DmBaseController extends BaseController{

	
	private static Logger log = LoggerFactory.getLogger(DmBaseController.class);
	
	/*映射管理服务类*/
	@Autowired
	private DmMappingService sqMappingService;
	


	public DmMappingService getSqMappingService() {
		return sqMappingService;
	}

	public void setSqMappingService(DmMappingService sqMappingService) {
		this.sqMappingService = sqMappingService;
	}



	/**
	 * 智能查询映射功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/mapping.htm")
	public String forwardMapping(){
		log.info("映射功能页面跳转");
		return "/modules/data_mapping/mapping/mapping";
	}
	
	/**
	 * 智能查询映射功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/binding.htm")
	public String forwardbinding(){
		log.info("目录功能页面跳转");
		return "/modules/data_mapping/mapping/binding";
	}
	
	/**
	 * 智能查询映射功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/mapping.do")
	public String forwardMappingPage(){
		log.info("映射功能主页面跳转");
		return "/modules/data_mapping/mapping/mappingPage";
	}
	

	
}
