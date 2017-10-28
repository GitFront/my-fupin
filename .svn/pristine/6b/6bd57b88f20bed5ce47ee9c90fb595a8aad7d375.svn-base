package com.aspire.birp.modules.antiPoverty.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.StrategyService;

/** 
 * 战略地图controller类 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:46:36 
 */
@Controller
@RequestMapping("/antiPoverty/strategy")
public class StrategyController {
	
	@Autowired
    private StrategyService strategyService;
	
	@RequestMapping("/getStrategyStaticData")
	@ResponseBody
	public String getStrategyStaticData() throws Exception{
 		return strategyService.getStrategyStaticData();
	} 
	
	@RequestMapping("/getStrategyDynamicIndexData")
	@ResponseBody
	public String getStrategyDynamicIndexData(){
 		return strategyService.getStrategyDynamicIndexData();
	} 
}
