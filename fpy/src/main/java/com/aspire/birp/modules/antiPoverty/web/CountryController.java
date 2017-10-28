package com.aspire.birp.modules.antiPoverty.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.CountryService;

/** 
 * 村档案controller类 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:46:36 
 */
@Controller
@RequestMapping("/antiPoverty/country")
public class CountryController {
	
	@Autowired
    private CountryService countryService;
	
	@RequestMapping("/getCountryFileBasicData")
	@ResponseBody
	public String getCountryFileBasicData(String id){
 		return countryService.getCountryFileBasicData(id);
	}
	
	@RequestMapping("/getCountryFileBusinessData")
	@ResponseBody
	public String getCountryFileBusinessData(String id){
 		return countryService.getCountryFileBusinessData(id);
	}
	
	@RequestMapping("/getCountryFileBusinessMoreData")
	@ResponseBody
	public String getCountryFileBusinessMoreData(String id,String type){
 		return countryService.getCountryFileBusinessMoreData(id,type);
	}
	
	@RequestMapping("/getCountryFileDataPathData")
	@ResponseBody
	public String getCountryFileDataPathData(String id){
 		return countryService.getCountryFileDataPathData(id);
	}
	
	@RequestMapping("/getCountryFileDevelopmentStatusData")
	@ResponseBody
	public String getCountryFileDevelopmentStatusData(String id){
 		return countryService.getCountryFileDevelopmentStatusData(id);
	}
	
	@RequestMapping("/getCountryFileEliminatePathData")
	@ResponseBody
	public String getCountryFileEliminatePathData(String id,String year){
 		return countryService.getCountryFileEliminatePathData(id,year);
	}
	
	@RequestMapping("/getCountryFileEliminateScoresData")
	@ResponseBody
	public String getCountryFileEliminateScoresData(String id,String year,String month){
 		return countryService.getCountryFileEliminateScoresData(id,year,month);
	}
	
	@RequestMapping("/getCountryFileImplementData")
	@ResponseBody
	public String getCountryFileImplementData(String id,String year){
 		return countryService.getCountryFileImplementData(id,year);
	}
	
	@RequestMapping("/getCountryFileInvestedData")
	@ResponseBody
	public String getCountryFileInvestedData(String id,String year){
 		return countryService.getCountryFileInvestedData(id,year);
	}
	
	@RequestMapping("/getCountryFileMeetingNewsData")
	@ResponseBody
	public String getCountryFileMeetingNewsData(String id){
 		return countryService.getCountryFileMeetingNewsData(id);
	}
	
	@RequestMapping("/getCountryFileMeetingNewsDetailData")
	@ResponseBody
	public String getCountryFileMeetingNewsDetailData(String news_id){
 		return countryService.getCountryFileMeetingNewsDetailData(news_id);
	}
	
	@RequestMapping("/getCountryFileNewsData")
	@ResponseBody
	public String getCountryFileNewsData(String id){
 		return countryService.getCountryFileNewsData(id);
	}
	
	@RequestMapping("/getCountryFileNewsDetailData")
	@ResponseBody
	public String getCountryFileNewsDetailData(String news_id){
 		return countryService.getCountryFileNewsDetailData(news_id);
	}
	
	@RequestMapping("/getCountryFilePlanData")
	@ResponseBody
	public String getCountryFilePlanData(String id){
 		return countryService.getCountryFilePlanData(id);
	}
	
	@RequestMapping("/getCountryFileStatusData")
	@ResponseBody
	public String getCountryFileStatusData(String id){
 		return countryService.getCountryFileStatusData(id);
	}
}
