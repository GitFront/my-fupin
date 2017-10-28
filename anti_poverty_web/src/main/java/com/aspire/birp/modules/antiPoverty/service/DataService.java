package com.aspire.birp.modules.antiPoverty.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.modules.antiPoverty.dao.DataAnalyseDao;
import com.aspire.birp.modules.antiPoverty.dao.DataMonDao;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.antiPoverty.utils.DataMapTemplate;
import com.aspire.birp.modules.antiPoverty.utils.JsonFormatUtils;
import com.aspire.birp.modules.sys.utils.UserUtils;

import net.sf.json.JSONObject;

/** 
 * 数据监控服务Service 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:33:23 
 */
@Service
public class DataService {
	private static Logger log = LoggerFactory.getLogger(DataService.class);
	
	@Autowired
	private DataMonDao dao;
	@Autowired
	private DataAnalyseDao daDao;
	/**
	 * 获取数据监控JSON静态数据
	 * @return
	 * @throws Exception 
	 */
	public String getDataStaticData(Integer scope,String id,String level) {
		JSONObject json = new JSONObject();
		try{
			Long userPac = Long.parseLong(UserUtils.getAreaList().iterator().next().toString());
			if(userPac>Long.parseLong(id))  {
				Map<String, Object> params =new HashMap<String, Object>();
				params.put("PAC", userPac);
				Map<String,Object> userMap = dao.queryAreaById(params);
				id = userPac.toString();
				level = userMap.get("S_LEVEL") == null ? "" : userMap.get("S_LEVEL").toString();
			}
			
			Map<String, Object> params =Constants.dateContants;
			params.put("level", level);
			params.put("id", id);
			params.put("scope", scope);
			//组装JSON数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//params.put("scope", scope);
			data.put("area_level", level);
			Map<String,Object> hpcount = dao.queryHomePageCount(params);
			if(hpcount == null){
				throw new Exception("用户未获取数据，数据为空。");
			}
			//queryAreaDesc = DataMapTemplate.dataStaticMapTpl(dao.queryAreaDesc(params),"AreaDesc");
			String areaName = dao.queryAreaName(params); //区域简称
			data.put("level_name", areaName);
			String areaDesc = dao.queryAreaDesc1(params);
			areaDesc =	areaDesc == null ? "" : areaDesc ;
			data.put("area_desc", areaDesc.replace("？", ""));
			data.put("area_id", hpcount.get("PAC") == null ? "" : hpcount.get("PAC"));
			data.put("area_name", areaName);
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data.put("time_end", df.parse("2018-12-31 23:59:59").getTime());
			JSONObject average = new JSONObject();
			//两不愁
		    JSONObject chart_config_labor = new JSONObject();
			chart_config_labor.put("percent", hpcount.get("CHART_CONFIG_LABOR"));
			average.put("chart_config_labor", chart_config_labor);
			//社会保障兜底异常
			JSONObject chart_config_society = new JSONObject();
			chart_config_society.put("percent", hpcount.get("CHART_CONFIG_SOCIETY"));
			average.put("chart_config_society", chart_config_society);
			data.put("average", average);
			
			//三保障
			JSONObject guarantee = new JSONObject();
			List<JSONObject> edu_listData = new ArrayList<JSONObject>();
			List<JSONObject> house_listData = new ArrayList<JSONObject>();
			JSONObject house = new JSONObject();
			house.put("name", "");//住房安全
			house.put("value", hpcount.get("ZFAQ"));
			house_listData.add(house);
			guarantee.put("chart_config_house", house_listData);
			
			JSONObject edu = new JSONObject();
			edu.put("name", "");//义务教育
			edu.put("value", hpcount.get("YWJY"));
			edu_listData.add(edu);
			guarantee.put("chart_config_edu", edu_listData);
			
			List<JSONObject> medical_listData = new ArrayList<JSONObject>();
			JSONObject medical = new JSONObject();
			medical.put("name", "");//基本医疗
			medical.put("value", hpcount.get("JBYL"));
			medical_listData.add(medical);
			guarantee.put("chart_config_medical", medical_listData);
			data.put("guarantee", guarantee);
			
			//一相当
			List<JSONObject> bars_listData = new ArrayList<JSONObject>();
			JSONObject b1 = new JSONObject();
			b1.put("name", "道路硬化");
			b1.put("percent", hpcount.get("DLYH"));
			bars_listData.add(b1);
			JSONObject b2 = new JSONObject();
			b2.put("name", "安全饮水");
			b2.put("percent", hpcount.get("AQYS"));
			bars_listData.add(b2);
			JSONObject b3 = new JSONObject();
			b3.put("name", "生活用电");
			b3.put("percent", hpcount.get("DWGZ"));
			bars_listData.add(b3);
			JSONObject b4 = new JSONObject();
			b4.put("name", "医疗设施");
			b4.put("percent", hpcount.get("WSZ"));
			bars_listData.add(b4);
			JSONObject b5 = new JSONObject();
			b5.put("name", "广播电视");
			b5.put("percent", hpcount.get("GBDS"));
			bars_listData.add(b5);
			JSONObject b6 = new JSONObject();
			b6.put("name", "网络覆盖");
			b6.put("percent", hpcount.get("KDWL"));
			bars_listData.add(b6);
			data.put("bars", bars_listData);
			
			//贫困分析
			JSONObject chart_config_poor_analysis = new JSONObject();
			//chart_config_poor_analysis.put("count", queryPkfx.get(0).get("S_COUNT"));
			List<JSONObject> poor_analysis_listData = new ArrayList<JSONObject>();
			Long  pcount = 0l;
			Long temp = 0l;
			for (int i = 0; i < 5; i++) {
				temp = Long.parseLong(hpcount.get("PC"+(i+1))== null ? "0" : hpcount.get("PC"+(i+1)).toString());
				if(temp > 0){
					JSONObject p1 = new JSONObject();
					p1.put("name", hpcount.get("PN"+(i+1)));
					p1.put("value", hpcount.get("PC"+(i+1)));
					poor_analysis_listData.add(p1);
					pcount = pcount + temp;
				}
				
			}
			Long tempp = Long.parseLong(hpcount.get("OTHER")== null ? "0" : hpcount.get("OTHER").toString());
			if(tempp>0){
				JSONObject p2 = new JSONObject();
				p2.put("name", "其他");
				p2.put("value", hpcount.get("OTHER"));
				pcount = pcount + tempp;
				poor_analysis_listData.add(p2);
			}
			chart_config_poor_analysis.put("count", pcount);
			chart_config_poor_analysis.put("listData", poor_analysis_listData);
			data.put("chart_config_poor_analysis", chart_config_poor_analysis);
			
			//预警监控
			JSONObject alarm = new JSONObject();
			alarm.put("family", hpcount.get("FAMILY"));
			alarm.put("records", hpcount.get("RECORDS"));
			alarm.put("country", hpcount.get("COUNTRY"));
			alarm.put("family_not_visited", hpcount.get("FAMILY_NOT_VISITED"));
			data.put("alarm", alarm);
			
			//项目资金
			List<JSONObject> bubble_listData = new ArrayList<JSONObject>();
		    List<Map<String, Object>> queryXmzj = dao.queryCapitalCount(params);
			Map<String, Object> queryXmzjCount = dao.queryProjectCapitalCount(params);
			JSONObject capital = new JSONObject();
			JSONObject stat = new JSONObject();
			stat.put("invested", queryXmzjCount.get("S_INVESTED") ==null ? "0万" : queryXmzjCount.get("S_INVESTED")+"万");
			long cnt = Long.parseLong(queryXmzjCount.get("S_PROJECTS") == null ? "0" : queryXmzjCount.get("S_PROJECTS").toString());
			stat.put("projects", cnt>10000?Math.round(cnt/10000)+"万个":cnt+"个");
			stat.put("rate_completed", queryXmzjCount.get("S_PROJECTS") == null ? "0.00%" : queryXmzjCount.get("S_RATE_COMPLETED"));
			capital.put("stat", stat);
			for (int i = 0; i < queryXmzj.size(); i++) {
				List<Double> vList1 = new ArrayList<Double>();//value1=项目数  value2=完成率 value3=资金投入
				JSONObject bu1 = new JSONObject();
				Map<String, Object> map = queryXmzj.get(i);
				bu1.put("name", map.get("S_NAME"));
				bu1.put("group", map.get("S_NAME"));
				vList1.add(Double.parseDouble(map.get("GS").toString()));
				vList1.add(Double.parseDouble(map.get("WCL").toString()));
				vList1.add(Double.parseDouble(map.get("ZJ").toString()));
				bu1.put("value", vList1);
				bubble_listData.add(bu1);
			}
			JSONObject chart_config_bubble = new JSONObject();
			chart_config_bubble.put("listData", bubble_listData);
			chart_config_bubble.put("count", queryXmzjCount.get("S_COUNT"));
			capital.put("chart_config_bubble", chart_config_bubble);
			data.put("capital", capital);
			
			//
			JSONObject top_indexes = new JSONObject();
			top_indexes.put("poor_family_amount", hpcount.get("S_POOR_FAMILY_AMOUNT"));
			top_indexes.put("success_family_amount", hpcount.get("S_SUCCESS_FAMILY_AMOUNT"));
			top_indexes.put("success_family_rate", hpcount.get("SUCCESS_FAMILY_R"));
			top_indexes.put("poor_people_amount", hpcount.get("S_POOR_PEOPLE_AMOUNT"));
			top_indexes.put("success_people_amount", hpcount.get("S_SUCCESS_PEOPLE_AMOUNT"));
			top_indexes.put("success_people_rate", hpcount.get("SUCCESS_PEOPLE_R"));
			if("country".equals(level)){
				int isFurtherPoor = daDao.IfFurtherPpoor(id);
				if(isFurtherPoor==1){
					top_indexes.put("score", hpcount.get("SCORE_A"));
				}
			}
			data.put("top_indexes", top_indexes);
			
			JSONObject stat1 = new JSONObject();
			stat1.put("amount_org", hpcount.get("AMOUNT_ORG"));
			stat1.put("amount_responsible", hpcount.get("AMOUNT_RESPONSIBLE"));
			stat1.put("amount_leader", hpcount.get("AMOUNT_LEADER"));
			stat1.put("amount_interview", hpcount.get("AMOUNT_INTERVIEW"));
			data.put("stat", stat1);
				
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("数据监控静态数据查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败：");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 获取数据监控JSON动态数据
	 * @return
	 */
	public String getDataDynamicIndexData(Integer scope,String id,String level,String isPoor) {
		JSONObject json = new JSONObject();
		try{
			Long userPac = Long.parseLong(UserUtils.getAreaList().iterator().next().toString());
			if(userPac>Long.parseLong(id))  {
				Map<String, Object> params =new HashMap<String, Object>();
				params.put("PAC", userPac);
				Map<String,Object> userMap = dao.queryAreaById(params);
				id = userPac.toString();
				level = userMap.get("S_LEVEL").toString();
			}
			//查询数据
			Map<String, Object> params =Constants.dateContants;
			params.put("level", level);
			params.put("id", id);
			params.put("scope", scope);
			Map<String, Object> map = DataMapTemplate.dataDynamicIndexTpl(dao.queryCount(params));
			JSONObject data = new JSONObject();
			
			json.put("code", map.get("RET_CODE"));
			json.put("msg", map.get("RET_MSG"));
			data.put("area_level", level);
			JSONObject top_indexes = new JSONObject();
			top_indexes.put("poor_family_amount", map.get("S_POOR_FAMILY_AMOUNT"));
			top_indexes.put("success_family_amount", map.get("S_SUCCESS_FAMILY_AMOUNT"));
			top_indexes.put("success_family_rate", map.get("SUCCESS_FAMILY_R"));
			top_indexes.put("poor_people_amount", map.get("S_POOR_PEOPLE_AMOUNT"));
			top_indexes.put("success_people_amount", map.get("S_SUCCESS_PEOPLE_AMOUNT"));
			top_indexes.put("success_people_rate", map.get("SUCCESS_PEOPLE_R"));
			if("country".equals(level)){
				int isFurtherPoor = daDao.IfFurtherPpoor(id);
				if(isFurtherPoor==1){
					top_indexes.put("score", map.get("SCORE_A"));
				}
			}
			data.put("top_indexes", top_indexes);
			
			JSONObject stat = new JSONObject();
			stat.put("amount_org", map.get("AMOUNT_ORG"));
			stat.put("amount_responsible", map.get("AMOUNT_RESPONSIBLE"));
			stat.put("amount_leader", map.get("AMOUNT_LEADER"));
			stat.put("amount_interview", map.get("AMOUNT_INTERVIEW"));
			data.put("stat", stat);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("数据监控动态数据异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}

	public String getLoginIndexMsg(Integer scope, String id, String level, String string) {
		JSONObject json = new JSONObject();
		try{
			User user = UserUtils.getUser(); //获取用户id
			//查询数据
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			Map<String, Object> params =Constants.dateContants;
			String userId = user.getId();
			params.put("USERID", userId);
			JSONObject data = new JSONObject();
			Map<String, Object> newMsg = dao.queryMsgById(params);
			if(null != newMsg && null != userId && !"".equals(userId)){
				String noticeId =  newMsg.get("NOTICEID") == null ? "" : newMsg.get("NOTICEID").toString();
				params.put("NOTICEID", noticeId);
				Map<String, Object> userStatus = dao.queryMsgByUserId(params);
				if(null == userStatus || null == userStatus.get("USERID")){
					data.put("show", 1);
					data.put("msg", newMsg.get("NOTICEDETAIL"));
					params.put("STATUS", 1);
					dao.insertUserMsg(params); //记录用户
				}else{
					data.put("show", 0);
				}
			}else{
				data.put("show", 0);
			}
			
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("数据监控动态数据异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
}
