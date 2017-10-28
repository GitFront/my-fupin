package com.aspire.birp.modules.antiPoverty.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.SearchService;

/** 
 * 搜索controller类 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:46:36 
 */
@Controller
@RequestMapping("/antiPoverty/search")
public class SearchController {
	
	@Autowired
    private SearchService searchService;
	
	@RequestMapping("/getPreSearchData")
	@ResponseBody
	public String getPreSearchData(String key,String type){
 		return searchService.getPreSearchData(key,type);
	} 
	
	@RequestMapping("/getSearchResultData")
	@ResponseBody
	public String getSearchResultData(String id,String type){
 		return searchService.getSearchResultData(id,type);
	} 
}
