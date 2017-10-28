package com.aspire.birp.modules.antiPoverty.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.birp.modules.antiPoverty.service.FamilyService;

/** 
 * 户档案controller类 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:46:36 
 */
@Controller
@RequestMapping("/antiPoverty/family")
public class FamilyController {
	
	@Autowired
    private FamilyService familyService;
	
	@RequestMapping("/getFamilyFileBasicData")
	@ResponseBody
	public String getFamilyFileBasicData(String id){
 		return familyService.getFamilyFileBasicData(id);
	}
	
	@RequestMapping("/getFamilyFileMembersData")
	@ResponseBody
	public String getFamilyFileMembersData(String id){
 		return familyService.getFamilyFileMembersData(id);
	} 
	
	@RequestMapping("/getFamilyFileConditionData")
	@ResponseBody
	public String getFamilyFileConditionData(String id){
 		return familyService.getFamilyFileConditionData(id);
	} 
	
	@RequestMapping("/getFamilyFilePlanData")
	@ResponseBody
	public String getFamilyFilePlanData(String id){
 		return familyService.getFamilyFilePlanData(id);
	} 
	
	@RequestMapping("/getFamilyFileImplementData")
	@ResponseBody
	public String getFamilyFileImplementData(String id,String year){
 		return familyService.getFamilyFileImplementData(id,year);
	} 
	
	@RequestMapping("/getFamilyFileNewsData")
	@ResponseBody
	public String getFamilyFileNewsData(String id){
 		return familyService.getFamilyFileNewsData(id);
	} 
	
	@RequestMapping("/getFamilyFileNewsDetailData")
	@ResponseBody
	public String getFamilyFileNewsDetailData(String news_id){
 		return familyService.getFamilyFileNewsDetailData(news_id);
	} 
	
	@RequestMapping("/getFamilyFileEliminatePathData")
	@ResponseBody
	public String getFamilyFileEliminatePathData(String id,String year){
 		return familyService.getFamilyFileEliminatePathData(id,year);
	} 
	
	@RequestMapping("/getFamilyFileEliminateScoresData")
	@ResponseBody
	public String getFamilyFileEliminateScoresData(String id,String year,String month){
 		return familyService.getFamilyFileEliminateScoresData(id,year,month);
	} 
	
	@RequestMapping("/getFamilyFileInvestedData")
	@ResponseBody
	public String getFamilyFileInvestedData(String id,String year){
 		return familyService.getFamilyFileInvestedData(id,year);
	} 
	
	@RequestMapping("/getFamilyFileDataPathData")
	@ResponseBody
	public String getFamilyFileDataPathData(String id){
 		return familyService.getFamilyFileDataPathData(id);
	} 
	
	@RequestMapping("/getFamilyFileBusinessData")
	@ResponseBody
	public String getFamilyFileBusinessData(String id){
 		return familyService.getFamilyFileBusinessData(id);
	} 
	
	@RequestMapping("/getFamilyFileBusinessMoreData")
	@ResponseBody
	public String getFamilyFileBusinessMoreData(String id,String type){
 		return familyService.getFamilyFileBusinessMoreData(id,type);
	} 
	
	@RequestMapping("/getFamilyIncomeData")
	@ResponseBody
	public String getFamilyIncomeData(String id,String year){
 		return familyService.getFamilyIncomeData(id,year);
	} 
	
	@RequestMapping("/getFamilyIncomeTableData")
	@ResponseBody
	public String getFamilyIncomeTableData(String id,String year,String month){
 		return familyService.getFamilyIncomeTableData(id,year,month);
	} 
}
