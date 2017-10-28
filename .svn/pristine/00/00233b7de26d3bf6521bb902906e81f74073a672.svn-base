package com.aspire.birp.modules.antiPoverty.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan.FilePlan;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan.FilePlanData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FamilyFilePlan.Plan;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FileEliminatePath.FileEliminatePath;
import com.aspire.birp.modules.antiPoverty.model.poorFile.FileEliminatePath.FileEliminatePathData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.ChartConfigBar;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.FileIncome;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileIncome.FileIncomeData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers.Album;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers.FileMembers;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers.FileMembersData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileMembers.Member;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNews.FileNews;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNews.FileNewsData;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNews.News;
import com.aspire.birp.modules.antiPoverty.model.poorFile.familyFileNewsDetail.FileNewsDetail;

import net.sf.json.JSONNull;

/** 
 * 类说明 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年12月17日 上午10:36:00 
 */
public class FamilyMapTemplate {
	
	public static Map<String,Object> familyFileBasicTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			
			ret_map.put("S_HOUSE_HOLDER_NAME", "");
			ret_map.put("S_HOUSE_HOLDER_AVATAR", "");
			ret_map.put("S_HOUSE_HOLDER_PHONE", "");
			ret_map.put("S_POOR_ATTRIBUTE", "");
			ret_map.put("S_POOR_REASON", "");
			ret_map.put("S_START_INCOME", "");
			ret_map.put("S_COUNTRY_LEADER_NAME", "");
			ret_map.put("S_COUNTRY_LEADER_AVATAR", "");
			ret_map.put("S_COUNTRY_LEADER_PHONE", "");
			ret_map.put("S_COUNTRY_LEADER_ORG", "");
			ret_map.put("S_COUNTRY_LEADER_START_TIME", "");
			
			ret_map.put("S_INCOME", "0");
			ret_map.put("S_EDU", "0");
			ret_map.put("S_MEDICAL_SECURITY", "0");
			ret_map.put("S_HOUSE_DANGER_LEVEL", "");
			ret_map.put("S_HAS_HOUSE_BECOME_SAFE", "0");
			ret_map.put("S_INDEX_", "0");
			ret_map.put("S_HAS_ACHIEVED", "0");
			ret_map.put("S_COUNTRY_LEADER_START_TIME", "");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> familyFileMembersTpl(List<Map<String,Object>> mapList,List<Map<String,Object>> picList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0 && picList.size()>0){
			ret_map.put("MAPLIST", mapList);
			ret_map.put("PICLIST", picList);
		}else if(mapList.size()==0 && picList.size()>0){
			RET_CODE = 0;
			RET_MSG = "由于家庭成员信息缺失，获取家庭成员信息失败";
			ret_map.put("MAPLIST", mapList);
			ret_map.put("PICLIST", picList);
		}else if(mapList.size()>0 && picList.size()==0){
			RET_CODE = 0;
			RET_MSG = "由于家庭成员图片信息缺失，获取家庭成员图片信息失败";
			ret_map.put("MAPLIST", mapList);
			ret_map.put("PICLIST", picList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("S_ORDER", "");
			map.put("S_NAME", "");
			map.put("S_SEX", "");
			map.put("S_CREDENTIAL_TYPE", "");
			map.put("S_AGE", "");
			map.put("S_RELATIONSHIP", "");
			map.put("S_NATIONALITY", "");
			map.put("S_POLITICAL_AFFILIATION", "");
			map.put("S_EDUCATION", "");
			map.put("S_AT_SCHOOL", "");
			map.put("S_HEALTH", "");
			map.put("S_LABOR_CAPACITY", "");
			map.put("S_WORK_STATUS", "");
			map.put("S_WORK_TIME", "");
			map.put("S_ACTIVE_SERVICE", "");
			map.put("S_SERIOUS_ILLNESS_INSURANCE", "");
			map.put("S_TECHNICAL_SCHOOL_WILLING", "");
			map.put("S_TRAIN_NEED", "");
			map.put("S_SKILL_STATUS", "");
			map.put("S_EMPLOYMENT_WILLING", "");
			map.put("S_EMPLOYMENT_EXPECTATION", "");
			map.put("S_STAFF_PENSION_INSURANCE", "");
			map.put("S_RESIDENT_MEDICAL_INSURANCE", "");
			map.put("S_RESIDENT_PENSION_INSURANCE", "");
			map.put("S_PENSION_LEVEL", "");
			list.add(map);
			ret_map.put("MAPLIST", list);
			
			List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
			Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("S_HOUSE_HOLDER_NAME", "");
			map2.put("PATH", "");
			ret_map.put("PICLIST", list2);
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static FileMembers familyFileMembersTplo(List<Member> mapList,List<Album> picList){
		FileMembers fmb = new FileMembers();
		FileMembersData fmd = new FileMembersData();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0 && picList.size()>0){
			fmd.setMembers(mapList);
			fmd.setAlbum(picList);
		}else if(mapList.size()==0 && picList.size()>0){
			RET_CODE = 0;
			RET_MSG = "由于家庭成员信息缺失，获取家庭成员信息失败";
			fmd.setMembers(mapList);
			fmd.setAlbum(picList);
		}else if(mapList.size()>0 && picList.size()==0){
			RET_CODE = 0;
			RET_MSG = "由于家庭成员图片信息缺失，获取家庭成员图片信息失败";
			fmd.setMembers(mapList);
			fmd.setAlbum(picList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			List<Member> list = new ArrayList<Member>();
			Member mb = new Member();
			list.add(mb);
			fmd.setMembers(mapList);
			
			List<Album> ablist = new ArrayList<Album>();
			Album ab = new Album();
			ab.setHouseHolderName("");
			ab.setSrc("");
			fmd.setAlbum(ablist);
		}
		fmb.setCode(RET_CODE);
		fmb.setMsg(RET_MSG);
		fmb.setData(fmd);
		return fmb;
	}
	
	public static Map<String,Object> familyFileConditionTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			
			ret_map.put("S_TOTAL1", "0");
			ret_map.put("S_AVAILABLE", "0");
			
			ret_map.put("S_TOTAL2", "0");
			ret_map.put("S_RETURN", "0");
			ret_map.put("S_FRUIT", "0");
			
			ret_map.put("S_GRASS_TOTAL", "0");
			ret_map.put("S_WATER_TOTAL", "0");
			
			ret_map.put("S_PRODUCTION_ELECTRICITY", "");
			ret_map.put("S_LIVING_ELECTRICITY", "");
			ret_map.put("S_WATER_SAFETY", "");
			ret_map.put("S_HOUSE_AREA", "0");
			ret_map.put("S_DISTANCE_MAIN_ROAD", "0");
			ret_map.put("S_ROAD_TYPE", "");
			ret_map.put("S_FUEL_TYPE", "");
			ret_map.put("S_TOILET", "");
			ret_map.put("S_JOINED_FSC", "");
		}
		
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> familyFilePlanTpl(List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			ret_map.put("MAPLIST", mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			mapList = new ArrayList<Map<String,Object>>();
			for(int i=0;i<4;i++){
				Map<String,Object> map =  new HashMap<String,Object>();
				map.put("S_CCOUNTRY_LEADER_SIGN","");
				map.put("S_RESPONSIBLE_SIGN","");
				map.put("S_TIME","");
				mapList.add(map);
			}
			ret_map.put("MAPLIST", mapList);
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static FilePlan familyFilePlanTplo(List<Plan> mapList){
		FilePlanData fpd = new FilePlanData();
		FilePlan fp = new FilePlan();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			fpd.setPlans(mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			mapList = new ArrayList<Plan>();
			for(int i=0;i<4;i++){
				Plan plan = new Plan();
				plan.setCountryLeaderSign("");
				plan.setHouseHolderSign("");
				plan.setTime("");
				mapList.add(plan);
			}
			fpd.setPlans(mapList);
		}
		fp.setData(fpd);
		fp.setCode(RET_CODE);
		fp.setMsg(RET_MSG);
		return fp;
	}
	
	public static Map<String,Object> familyFileImplementTpl(Map<String,Object> map,List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_TOTAL", "0");
			ret_map.put("S_RUNNING", "0");
			ret_map.put("S_COMPLETED", "0");
			
			ret_map.put("S_INVESTED", "0");
			ret_map.put("S_PROFIT", "0");
			
			ret_map.put("S_INVEST_EXPECTED", "0");
			ret_map.put("S_INVESTED", "0");
			ret_map.put("S_PROFIT", "0");
		}
		ret_map.put("MAPLIST", mapList);
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> familyFileNewsTpl(List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			ret_map.put("MAPLIST", mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("MAPLIST", mapList);
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static FileNews familyFileNewsTplo(List<News> mapList){
		FileNews fn = new FileNews();
		FileNewsData fnd = new FileNewsData();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			fnd.setList(mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			fnd.setList(mapList);
		}
		fn.setData(fnd);
		fn.setCode(RET_CODE);
		fn.setMsg(RET_MSG);
		return fn;
	}
	
	public static Map<String,Object> familyFileNewsDetailTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("S_DESC", "");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static FileNewsDetail familyFileNewsDetailTplo(News map){
		FileNewsDetail fnd = new FileNewsDetail();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			fnd.setData(map);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			//ret_map.put("S_DESC", "");
		}
		fnd.setCode(RET_CODE);
		fnd.setMsg(RET_MSG);
		return fnd;
	}
	
	public static Map<String,Object> familyFileEliminatePathTpl(List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			ret_map.put("MAPLIST", mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("MAPLIST", mapList);
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static FileEliminatePath familyFileEliminatePathTplo(List<ChartConfigBar> mapList){
		FileEliminatePath fep = new FileEliminatePath();
		FileEliminatePathData fepd = new FileEliminatePathData();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(mapList.size()>0){
			fepd.setChartConfig(mapList);
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			fepd.setChartConfig(mapList);
		}
		fep.setCode(RET_CODE);
		fep.setMsg(RET_MSG);
		fep.setData(fepd);
		return fep;
	}
	
	public static Map<String,Object> familyFileEliminateScoresTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "由于数据缺失，获取信息失败";
			ret_map.put("FAM_PROPERTY", "1");
			ret_map.put("FAM_DPI_SCORE", "");
			ret_map.put("FAM_DPI", "");
			ret_map.put("FAM_DPI_TARGET", "");
			ret_map.put("FAM_HAS_ACHIEVED", "0");
			
			ret_map.put("BASIC_LIVING_SCORE", "");
			ret_map.put("BASIC_LIVING", "");
			ret_map.put("BASIC_TARGET", "");
			ret_map.put("BASIC_HAS_ACHIEVED", "0");
			
			ret_map.put("EDUCATION_SCORE", "");
			ret_map.put("EDUCATION", "");
			ret_map.put("EDUCATION_TARGET", "");
			ret_map.put("EDUCATION_HAS_ACHIEVED", "0");
			
			ret_map.put("SOCIAL_SCORE", "");
			ret_map.put("SOCIAL", "");
			ret_map.put("SOCIAL_TARGET", "");
			ret_map.put("SOCIAL_HAS_ACHIEVED", "0");
			
			ret_map.put("HOUSE_SCORE", "");
			ret_map.put("HOUSE", "");
			ret_map.put("HOUSE_TARGET", "");
			ret_map.put("HOUSE_HAS_ACHIEVED", "0");
			
			ret_map.put("MEDICAL_SCORE", "");
			ret_map.put("MEDICAL", "");
			ret_map.put("MEDICAL_TARGET", "");
			ret_map.put("MEDICAL_HAS_ACHIEVED", "0");
			
			ret_map.put("HELP_FAM_SCORE", "");
			ret_map.put("HELP_FAM", "");
			ret_map.put("HELP_FAM_TARGET", "");
			ret_map.put("HELP_FAM_HAS_ACHIEVED", "0");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> familyFileInvestedTpl(Map<String,Object> map,Map<String,Object> map2,Map<String,Object> map3,List<Map<String,Object>> mapList){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map3!=null){
			ret_map = map3;
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_ORGANIZATION", "");
			
			ret_map.put("S_HELPER_CITY_FINANCE", "");
			ret_map.put("S_DISTRICT_TOWN_FINANCE", "");
			ret_map.put("S_HELPER_SOCIAL_MONEY", "");
			
			ret_map.put("S_CENTRAL_FINANCE", "");
			ret_map.put("S_PROVINCE_FINANCE", "");
			ret_map.put("S_CENTRAL_INDUSTRY", "");
			ret_map.put("S_PROVINCE_INDUSTRY", "");
			ret_map.put("S_CENTRAL_SOCIAL_MONEY", "");
			
			ret_map.put("S_HELPED_CITY_FINANCE", "");
			ret_map.put("S_COUNTY_TOWN_FINANCE", "");
			ret_map.put("S_CITY_INDUSTRY", "");
			ret_map.put("S_COUNTY_TOWN_INDUSTRY", "");
			ret_map.put("S_HELPED_SOCIAL_MONEY", "");
			
			ret_map.put("S_SUMMARY", "");
		}
		
		if(map!=null){
			ret_map.put("S_TOTAL", map.get("S_TOTAL"));
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_TOTAL", "0");
		}
		
		if(map2!=null){
			ret_map.put("S_YEAR_TOTAL", map2.get("S_TOTAL"));
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_YEAR_TOTAL", "0");
		}
		
		ret_map.put("MAPLIST", mapList);
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> familyFileDataPathTpl(List<Map<String,Object>> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		List<Object> updateList = new ArrayList<Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			BigDecimal modeType = null;
			String modeTime = "";
			String tempTime = "";
			List<Map<String,Object>> newMap = null;
			for(Map<String,Object> mp : map){
				modeType = (BigDecimal)mp.get("MOD_TYPE");
				modeTime = (String)mp.get("MOD_TIME");
				if(modeType.intValue() == 1){
					ret_map.put("create", mp);
				}else{
					if(!tempTime.equals(modeTime)){
						if(!"".equals(tempTime)){
							List<Map<String,Object>> oMap = newMap;
							updateList.add(oMap);
						}
						newMap = new ArrayList<Map<String,Object>>();
						newMap.add(mp);
						tempTime=modeTime;
					}else{
						newMap.add(mp);
					}
					
				}
			}
			updateList.add(newMap);
			ret_map.put("update", updateList);
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_DATE", "");
			ret_map.put("S_TIME", "");
			ret_map.put("S_PUBLISHER_ORGANIZATION", "");
			ret_map.put("S_PUBLISHER_NAME", "");
		}
		//ret_map.put("MAPLIST", mapList);
		//ret_map.put("MAPLIST2", mapList2);
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static Map<String,Object> familyIncomeTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_TOTAL_INCOME", "0元");
			ret_map.put("S_TOTAL_EXPENSE", "0元");
			ret_map.put("S_DISPOSABLE_INCOME", "0元");
			ret_map.put("S_YEAR_AVERAGE_DISPOSABLE_INCOME", "0元");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
	
	public static FileIncome familyIncomeTplo(FileIncomeData map){
		FileIncome fim = new FileIncome();
		FileIncomeData fid = null;
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			fid = map;
		}else{
			fid = new FileIncomeData();
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			fid.setTotalIncome("0元");
			fid.setTotalExpense("0元");
	        fid.setDisposableIncome("0元");
			fid.setYearAverageDisposableIncome("0元");
		}
		fim.setCode(RET_CODE);
		fim.setMsg(RET_MSG);
		fim.setData(fid);
		return fim;
	}
	
	public static Map<String,Object> familyIncomeTableTpl(Map<String,Object> map){
		Map<String,Object> ret_map = new HashMap<String,Object>();
		Integer RET_CODE = 0;
		String RET_MSG = "获取信息成功";
		if(map!=null){
			ret_map = map;
		}else{
			RET_CODE = 0;
			RET_MSG = "数据缺失，获取信息失败";
			ret_map.put("S_AVERAGE_DISPOSABLE_INCOME", "0");
			ret_map.put("S_FAMILY_DISPOSABLE_INCOME", "0");
			ret_map.put("S_TOTAL_INCOME", "0");
			ret_map.put("S_SALARY_INCOME", "0");
			ret_map.put("S_PRODUCTION_INCOME", "0");
			ret_map.put("S_PROPERTY_INCOME", "0");
			ret_map.put("S_TRANSFERRED_INCOME", "0");
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
			ret_map.put("S_TRANSFERRED_INCOME", "0");
			ret_map.put("S_INDIVIDUAL_INCOME_TAX", "0");
			ret_map.put("S_SOCIAL_SECURITY_PAYMENT", "0");
			ret_map.put("S_ALIMONY", "0");
			ret_map.put("S_OTHER_TRANSFERRED_PAYMENT", "0");
			ret_map.put("S_UNPAID_LOAN", "0");
		}
		ret_map.put("RET_CODE", RET_CODE);
		ret_map.put("RET_MSG", RET_MSG);
		return ret_map;
	}
}
