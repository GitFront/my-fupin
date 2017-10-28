package com.aspire.birp.modules.smartQuery.base.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aspire.bi.common.web.controller.BaseController;
import com.aspire.birp.modules.dataMapping.mapping.service.DmMappingService;
import com.aspire.birp.modules.smartQuery.market.service.SqDataMarketService;
import com.aspire.birp.modules.smartQuery.query.service.SqListFilterService;
import com.aspire.birp.modules.smartQuery.query.service.SqQueryService;
import com.aspire.birp.modules.sys.utils.UserUtils;


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
@RequestMapping(value = "${adminPath}/smart-query")
public class SqBaseController extends BaseController{

	
	private static Logger log = LoggerFactory.getLogger(SqBaseController.class);
	
	/*映射管理服务类*/
	@Autowired
	private DmMappingService dmMappingService;
	
	/*智能查询管理服务类*/
	@Autowired
	private SqQueryService sqQueryService;
	
	/*列表过滤器管理服务类*/
	@Autowired
	private SqListFilterService sqListFilterService;
	
	/*数据超市管理服务类*/
	@Autowired
	private SqDataMarketService sqDataMarketService;

	public DmMappingService getDmMappingService() {
		return dmMappingService;
	}

	public void setDmMappingService(DmMappingService dmMappingService) {
		this.dmMappingService = dmMappingService;
	}

	public SqQueryService getSqQueryService() {
		return sqQueryService;
	}

	public void setSqQueryService(SqQueryService sqQueryService) {
		this.sqQueryService = sqQueryService;
	}
	
	public SqDataMarketService getSqDataMarketService() {
		return sqDataMarketService;
	}

	public void setSqDataMarketService(SqDataMarketService sqDataMarketService) {
		this.sqDataMarketService = sqDataMarketService;
	}
	
	public SqListFilterService getSqListFilterService() {
		return sqListFilterService;
	}

	public void setSqListFilterService(SqListFilterService sqListFilterService) {
		this.sqListFilterService = sqListFilterService;
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
	
	/**
	 * 智能查询功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/query.htm")
	public String forwardQuery(){
		log.info("智能查询功能页面跳转");
		return "/modules/smart_query/query/query";
	}	
	/**
	 * 智能查询功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/query_old.htm")
	public String forwardQuery2(){
		log.info("智能查询功能页面跳转");
		return "/modules/smart_query/query/query_old";
	}	
	
	/**
	 * 智能查询功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/query.do")
	public String forwardQueryPage(){
		log.info("智能查询功能主页面跳转");
		return "/modules/smart_query/query/queryPage";
	}	
	/**
	 * 智能查询功能跳转
	 * @param path JSP页名
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/query2.do")
	public String forwardQueryPage2(){
		log.info("智能查询功能主页面跳转");
		return "/modules/smart_query/query/queryPage_old";
	}	
	
	/**
	 * 数据超市功能的跳转
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/data-market.htm")
	public String forwardDataMarket(Model model){
		/*定义当前用户账号*/		
		model.addAttribute("loginName", UserUtils.getUser().getLoginName());
		log.info("数据超市功能页面跳转");
		return "/modules/smart_query/market/data_market";
	}
	
	/**
	 * 数据超市功能的跳转
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/data-market.do")
	public String forwardDataMarketPage(){
		
		log.info("数据超市功能主页面跳转");
		return "/modules/smart_query/market/marketPage";
	}
	
	/**
	 * 建设中页面跳转
	 * @return
	 * @author 张健雄
	 */
	@RequestMapping("/building.htm")
	public String forwardBuildingPage(){
		
		log.info("建设中页面跳转");
		return "/building/building";
	}
	
}
