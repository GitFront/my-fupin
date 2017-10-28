package com.aspire.birp.modules.antiPoverty.utils;

import java.util.HashMap;
import java.util.Map;

/** 
 * 类说明 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月17日 上午10:36:00 
 */
public class DataAnalyseMapTemplate {
	
	public static Map<String,Object> dataAnalyseMapTpl(Map<String,Object> map,String type){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if("AverageIncomeTotal".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_TOTAL_INCOME", "0");
				ret_map.put("S_TOTAL_PAYMENT", "0");
				ret_map.put("S_DISPOSABLE_INCOME", "0");
				ret_map.put("S_AVERAGE_DISPOSABLE_INCOME", "0");
			}
		}else if("AverageIncomeFam".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_POOR_FAMILY", "0");
				ret_map.put("S_COMMON_POOR_FAMILY", "0");
				ret_map.put("S_HAVE_LABOR_LOW_FAMILY", "0");
				ret_map.put("S_NO_LABOR_LOW_FAMILY", "0");
				ret_map.put("S_FIVE_FAMILY", "0");
				ret_map.put("S_POOR_PEOPLE", "0");
				ret_map.put("S_COMMON_POOR_PEOPLE", "0");
				ret_map.put("S_HAVE_LABOR_LOW_PEOPLE", "0");
				ret_map.put("S_NO_LABOR_LOW_PEOPLE", "0");
				ret_map.put("S_FIVE_PEOPLE", "0");
			}
		}else if("AverageIncomeTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AVERAGE_DISPOSABLE_INCOME", "0");
				ret_map.put("S_TOTAL_INCOME", "0");
				ret_map.put("S_SALARY_INCOME", "0");
				ret_map.put("S_PRODUCTION_INCOME", "0");
				ret_map.put("S_PROPERTY_INCOME", "0");
				ret_map.put("S_BIRTH_SUBSIDY", "0");
				ret_map.put("S_LOW_SUBSIDY", "0");
				ret_map.put("S_FIVE_SUBSIDY", "0");
				ret_map.put("S_PENSION", "0");
				ret_map.put("S_ENV_SUBSIDY", "0");
				ret_map.put("S_MEDICAL_INSURANCE", "0");
				ret_map.put("S_MEDICAL_SUBSIDY", "0");
				ret_map.put("S_OTHER_TRANSFERRED_INCOME", "0");
				ret_map.put("S_TOTAL_PAYMENT", "0");
				ret_map.put("S_PRODUCTION_PAYMENT", "0");
				ret_map.put("S_INDIVIDUAL_INCOME_TAX", "0");
				ret_map.put("S_SOCIAL_SECURITY_PAYMENT", "0");
				ret_map.put("S_ALIMONY", "0");
				ret_map.put("S_OTHER_TRANSFERRED_PAYMENT", "0");
				ret_map.put("S_UNPAID_LOAN", "0");
				ret_map.put("S_FAMILY_DISPOSABLE_INCOME", "0");
			}
		}else if("SocialPolicy".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_TOTAL_AMOUNT_POOR_FAMILY", "0");
				ret_map.put("S_TOTAL_AMOUNT_DIFF_FAMILY", "0");
				ret_map.put("S_TOTAL_RATIO_DIFF_FAMILY", "0");
				
				ret_map.put("S_TOTAL_AMOUNT_POOR_PEOPLE", "0");
				ret_map.put("S_TOTAL_AMOUNT_DIFF_PEOPLE", "0");
				ret_map.put("S_TOTAL_RATIO_DIFF_PEOPLE", "0");
			}
		}else if("SocialPolicyTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_TOTAL_AMOUNT_POOR_FAMILY", "0");
				ret_map.put("S_TOTAL_AMOUNT_POOR_PEOPLE", "0");
				ret_map.put("S_TOTAL_AMOUNT_DIFF_FAMILY", "0");
				ret_map.put("S_TOTAL_AMOUNT_DIFF_PEOPLE", "0");
				ret_map.put("S_TOTAL_RATIO_DIFF_PEOPLE", "0");
			}
		}else if("GuaranteeEdu".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_SCHOOL_AGE", "0");
				ret_map.put("S_AMOUNT_COMPULSORY_EDU", "0");
				ret_map.put("S_AMOUNT_DIFF", "0");
				ret_map.put("S_PERCENT", "0");
			}
		}else if("GuaranteeEduTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_AT_AGE", "0");
				ret_map.put("S_AMOUNT_DIFF", "0");
				ret_map.put("S_RATIO_DIFF_PEOPLE", "0");
			}
		}else if("GuaranteeMedical".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_POOR_PEOPLE", "0");
				ret_map.put("S_AMOUNT_HAVE_MEDICAL_INSUR", "0");
				ret_map.put("S_AMOUNT_DIFF", "0");
				ret_map.put("S_PERCENT", "0");
			}
		}else if("GuaranteeMedicalTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_POOR_PEOPLE", "0");
				ret_map.put("S_AMOUNT_DIFF", "0");
				ret_map.put("S_RATIO_DIFF_PEOPLE", "0");
			}
		}else if("GuaranteeHouse".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_DANGER_HOUSE", "0");
				ret_map.put("S_AMOUNT_RETROFIT", "0");
				ret_map.put("S_AMOUNT_DIFF", "0");
				ret_map.put("S_PERCENT", "0");
			}
		}else if("GuaranteeHouseTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_DANGER_HOUSE", "0");
				ret_map.put("S_AMOUNT_DIFF", "0");
				ret_map.put("S_RATIO_DIFF_PEOPLE", "0");
			}
		}else if("ConditionRoad".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AREA_NAME", "");
			}
		}else if("ConditionRoadTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_COUNTRY", "0");
				ret_map.put("S_AMOUNT_COUNTRY_HAVE_ROAD", "0");
				ret_map.put("AMOUNT_COUNTRY_NO_ROAD", "0");
			}
		}else if("ConditionWater".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AREA_NAME", "");
			}
		}else if("ConditionWaterTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_FAMILY", "0");
				ret_map.put("S_AMOUNT_COUN_HAVE_WATER_SECUR", "0");
				ret_map.put("S_AMOUNT_COUN_NO_WATER_SECUR", "0");
			}
		}else if("ConditionElectricity".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AREA_NAME", "");
			}
		}else if("ConditionElectricityTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_COUNTRY", "0");
				ret_map.put("S_AMOUNT_COUN_HAVE_ELECTRICITY", "0");
				ret_map.put("S_AMOUNT_COUN_NO_ELECTRICITY", "0");
			}
		}else if("ConditionHealth".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AREA_NAME", "");
			}
		}else if("ConditionHealthTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_COUNTRY", "0");
				ret_map.put("S_AMOUNT_COUN_HAVE_HEA_STATION", "0");
				ret_map.put("S_AMOUNT_COUNTRY_NO_DOCTOR", "0");
			}
		}else if("ConditionBroadcast".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AREA_NAME", "");
			}
		}else if("ConditionBroadcastTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_FAMILY", "0");
				ret_map.put("S_AMOUNT_COUN_HAVE_BROADCAST", "0");
				ret_map.put("S_AMOUNT_COUN_NO_BROADCAST", "0");
			}
		}else if("ConditionNet".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AREA_NAME", "");
			}
		}else if("ConditionNetTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_COUNTRY", "0");
				ret_map.put("S_AMOUNT_COUNTRY_HAVE_NET", "0");
				ret_map.put("S_AMOUNT_COUNTRY_NO_NET", "0");
			}
		}else if("PoorLabor".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_TOTAL_AMOUNT", "0");
				ret_map.put("S_WORK_AT_HOME_AMOUNT", "0");
				ret_map.put("S_WORK_AT_HOME_PERCENT", "0");
			}
		}else if("PoorLaborZJ".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_W_NAME", "");
				ret_map.put("S_WB_VALUE", "0");
				ret_map.put("S_U_NAME", "");
				ret_map.put("S_UW_VALUE", "0");
				ret_map.put("S_N_NAME", "");
				ret_map.put("S_NW_VALUE", "0");
			}
		}else if("PoorEdu".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_TOTAL_AMOUNT", "0");
				ret_map.put("S_AT_SCHOOL_AMOUNT", "0");
				ret_map.put("S_AT_SCHOOL_PERCENT", "0");
			}
		}else if("PoorReason".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_FAMILY_AMOUNT", "0");
				ret_map.put("S_PEOPLE_AMOUNT", "0");
			}
		}else if("PoorDangerHouse".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_DANGER_HOUSE_AMOUNT", "0");
				ret_map.put("S_DANGER_HOUSE_PERCENT", "0");
			}
		}else if("PoorAttribute".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_FAM", "0");
				ret_map.put("S_DISABLED_AMOUNT", "0");
				ret_map.put("S_DISABLED_PERCENT", "0");
				ret_map.put("S_MIGRATOR_AMOUNT", "0");
				ret_map.put("S_MIGRATOR_PERCENT", "0");
			}
		}else if("PoorHealth".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_POP", "0");
				ret_map.put("S_TOTAL_AMOUNT", "0");
				ret_map.put("S_TOTAL_PERCENT", "0");
			}
		}else if("AlarmedFamily".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_TOTAL", "0");
				ret_map.put("S_AMOUNT_DIFF", "0");
				ret_map.put("S_PERCENT", "0");
			}
		}else if("AlarmedFamilyTable".equals(type)){
			if(map!=null){
				ret_map = map;
			}else{
				RET_CODE = 0;
				RET_MSG = "由于数据缺失，获取信息失败";
				ret_map.put("S_AMOUNT_TOTAL", "0");
				ret_map.put("S_AMOUNT_INDUSTRY_DEPT_RECORDS", "0");
				ret_map.put("S_AMOUNT_DIFF", "0");
				ret_map.put("S_RATE", "0");
			}
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
}
