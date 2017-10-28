package com.aspire.birp.modules.dataLabel.base.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aspire.bi.common.web.controller.BaseController;
import com.aspire.birp.modules.dataLabel.config.service.DlConfigService;
import com.aspire.birp.modules.dataLabel.label.service.DlUserAnalysisService;
import com.aspire.birp.modules.dataMapping.mapping.service.DmMappingService;


/**
 * Title: 数据标签功能公共服务控制类 <br>
 * Description: 主要用于处理数据标签的通用功能 <br>
 * Copyright: aspire Copyright (C) 2009 <br>
 * 
 * @author <a href="mailto:zhangjianxiong@aspirecn.com">张健雄</a><br>
 * @e-mail: zhangjianxiong@aspirecn.com<br>
 * @version 1.0 <br>
 * @creatdate 2016年4月26日 下午14:45:03 <br>
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/data-label")
public class DlBaseController extends BaseController{

	
	private static Logger log = LoggerFactory.getLogger(DlBaseController.class);
	
	/*映射管理服务类*/
	@Autowired
	private DmMappingService sqMappingService;

	public DmMappingService getSqMappingService() {
		return sqMappingService;
	}

	public void setSqMappingService(DmMappingService sqMappingService) {
		this.sqMappingService = sqMappingService;
	}
	
	/*数据标签配置服务类*/
	@Autowired
	private DlConfigService dlConfigService;
	
	public DlConfigService getDlConfigService() {
		return dlConfigService;
	}

	public void setDlConfigService(DlConfigService dlConfigService) {
		this.dlConfigService = dlConfigService;
	}
	
	/*数据标签用户分析服务类*/
	@Autowired
	private DlUserAnalysisService dlUserAnalysisService;
	
	public DlUserAnalysisService getDlUserAnalysisService() {
		return dlUserAnalysisService;
	}

	public void setDlUserAnalysisService(DlUserAnalysisService dlUserAnalysisService) {
		this.dlUserAnalysisService = dlUserAnalysisService;
	}

	/**
	 * 数据标签定义功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/data-label-config.htm")
	public String forwardDataLabelConfig(){
		log.info("数据标签定义功能页面跳转");
		return "/modules/data_label/label-tree";
	}
	
	/**
	 * 用户标签分析功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/user_analysis.htm")
	public String forwardUserAnalysis(){
		log.info("用户标签分析功能页面跳转");
		return "/modules/data_label/user-analysis";
	}
	
	/**
	 * 用户标签分析功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/userAnalysis.do")
	public String forwardUserAnalysisPage(){
		return "/modules/data_label/userAnalysisPage";
	}
	
}
