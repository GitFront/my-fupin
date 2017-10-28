package com.aspire.birp.modules.antiPoverty.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.DataService;
import com.aspire.birp.modules.common.service.PovertyGeometryService;

/** 
 * 数据监控controller类 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:46:36 
 */
@Controller
@RequestMapping("/antiPoverty/data")
public class DataController {
	
	@Autowired
    private DataService dataService;
	@Autowired
    private PovertyGeometryService PGService;
	
	@RequestMapping("/getDataStaticData")
	@ResponseBody
	public String getDataStaticData(Integer scope,String id,String level) throws Exception{
 		return dataService.getDataStaticData(scope,id,level);
	} 
	
	@RequestMapping("/getDataDynamicIndexData")
	@ResponseBody
	public String getDataDynamicIndexData(Integer scope,String id,String level){
		//String isPoor = PGService.ifPoor(id, level);
 		return dataService.getDataDynamicIndexData(scope,id,level,"");
	} 
	
	@RequestMapping("/getLoginIndexMsg")
	@ResponseBody
	public String getLoginIndexMsg(Integer scope,String id,String level){
		//String isPoor = PGService.ifPoor(id, level);
 		return dataService.getLoginIndexMsg(scope,id,level,"");
	}
	
	
}
