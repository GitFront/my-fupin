package com.aspire.birp.modules.antiPoverty.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.birp.common.utils.CommonUtil;
import com.aspire.birp.modules.antiPoverty.dao.DataMonDao;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.antiPoverty.utils.StrategyMapTemplate;

/** 
 * 战略地图服务Service 
 *
 * @author 卢桂湖 E-mail: luguihu@aspirecn.com
 * @version 创建时间：2016年11月24日 下午3:33:23 
 */
@Service
public class StrategyService {
	private static Logger log = LoggerFactory.getLogger(StrategyService.class);
	
	@Autowired
	private DataMonDao dao;
	
	/**
	 * 获取战略地图JSON静态数据
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getStrategyStaticData() throws Exception {
		JSONObject json = new JSONObject();
		try{
			Map<String,Object> params = Constants.dateContants;
			params.put("id", "440000000000");//广东省
			params.put("scope","1");//全部
			params.put("level","province");//省
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data.put("time_end", df.parse("2018-12-31 23:59:59").getTime());
			//两不愁
			JSONObject average = new JSONObject();
			Map<String, Object> queryDis = StrategyMapTemplate.strategyStaticMapTpl(dao.queryDis(params),"Dis");
			Map<String, Object> queryBottom = StrategyMapTemplate.strategyStaticMapTpl(dao.queryBottom(params),"Bottom");
			JSONObject chart_config_labor = new JSONObject();
			chart_config_labor.put("percent", getValue(queryDis,"CHART_CONFIG_LABOR"));
			average.put("chart_config_labor", chart_config_labor);
			JSONObject chart_config_society = new JSONObject();
			chart_config_society.put("percent", getValue(queryBottom,"CHART_CONFIG_SOCIETY"));
			average.put("chart_config_society", chart_config_society);
			data.put("average", average);
			//三保障
			Map<String, Object> queryYwjy = StrategyMapTemplate.strategyStaticMapTpl(dao.queryYwjy(params),"Ywjy");
			Map<String, Object> queryJbyl = StrategyMapTemplate.strategyStaticMapTpl(dao.queryJbyl(params),"Jbyl");
			Map<String, Object> queryZfaq = StrategyMapTemplate.strategyStaticMapTpl(dao.queryZfaq(params),"Zfaq");
			JSONObject guarantee = new JSONObject();
			List<JSONObject> edu_listData = new ArrayList<JSONObject>();
			JSONObject edu = new JSONObject();
			edu.put("name", "");//义务教育
			edu.put("value", getValue(queryYwjy,"YWJY"));
			edu_listData.add(edu);
			guarantee.put("chart_config_edu", edu_listData);
			List<JSONObject> medical_listData = new ArrayList<JSONObject>();
			JSONObject medical = new JSONObject();
			medical.put("name", "");//基本医疗
			medical.put("value", getValue(queryJbyl,"JBYL"));
			medical_listData.add(medical);
			guarantee.put("chart_config_medical", medical_listData);
			List<JSONObject> house_listData = new ArrayList<JSONObject>();
			JSONObject house = new JSONObject();
			house.put("name", "");//住房安全
			house.put("value", getValue(queryZfaq,"ZFAQ"));
			house_listData.add(house);
			guarantee.put("chart_config_house", house_listData);
			data.put("guarantee", guarantee);
			//一相当
			Map<String, Object> queryYxd = StrategyMapTemplate.strategyStaticMapTpl(dao.queryYxd(params),"Yxd");
			List<JSONObject> bars_listData = new ArrayList<JSONObject>();
			JSONObject b1 = new JSONObject();
			b1.put("name", "道路硬化");
			b1.put("percent", getValue(queryYxd,"DLYH"));
			bars_listData.add(b1);
			JSONObject b2 = new JSONObject();
			b2.put("name", "安全饮水");
			b2.put("percent", getValue(queryYxd,"AQYS"));
			bars_listData.add(b2);
			JSONObject b3 = new JSONObject();
			b3.put("name", "生活用电");
			b3.put("percent", getValue(queryYxd,"DWGZ"));
			bars_listData.add(b3);
			JSONObject b4 = new JSONObject();
			b4.put("name", "医疗设施");
			b4.put("percent", getValue(queryYxd,"WSZ"));
			bars_listData.add(b4);
			JSONObject b5 = new JSONObject();
			b5.put("name", "广播电视");
			b5.put("percent", getValue(queryYxd,"GBDS"));
			bars_listData.add(b5);
			JSONObject b6 = new JSONObject();
			b6.put("name", "网络覆盖");
			b6.put("percent", getValue(queryYxd,"KDWL"));
			bars_listData.add(b6);
			data.put("bars", bars_listData);
			//预警监控
			Map<String, Object> queryYjjk = StrategyMapTemplate.strategyStaticMapTpl(dao.queryYjjk(params),"Yjjk");
			JSONObject alarm = new JSONObject();
			alarm.put("family", getValue(queryYjjk,"FAMILY"));
			alarm.put("records", getValue(queryYjjk,"RECORDS"));
			alarm.put("country", getValue(queryYjjk,"COUNTRY"));
			JSONObject family_not_visited = new JSONObject();
			family_not_visited.put("label", getValue(queryYjjk,"FAMILY_NOT_VISITED_LABEL"));
			family_not_visited.put("value", getValue(queryYjjk,"FAMILY_NOT_VISITED_VALUE"));
			alarm.put("family_not_visited", family_not_visited);
			data.put("alarm", alarm);
			
			Map<String, Object> xmzjMap = StrategyMapTemplate.strategyStaticListTpl(dao.queryXmzj(params),"Xmzj");
			List<Map<String, Object>> queryXmzj = (List<Map<String, Object>>)xmzjMap.get("MAPLIST");
			Map<String, Object> queryXmzjCount = StrategyMapTemplate.strategyStaticMapTpl(dao.queryXmzjCount(params),"XmzjCount");
			JSONObject capital = new JSONObject();
			JSONObject stat = new JSONObject();
			stat.put("invested", getValue(queryXmzjCount,"S_INVESTED")+"万");
			stat.put("projects", getValue(queryXmzjCount,"S_PROJECTS")+"个");
			stat.put("rate_completed", getValue(queryXmzjCount,"S_RATE_COMPLETED")+"%");
			capital.put("stat", stat);
			List<JSONObject> pie_listData = new ArrayList<JSONObject>();
			for (int i = 0; i < queryXmzj.size(); i++) {
				JSONObject p1 = new JSONObject();
				Map<String, Object> map = queryXmzj.get(i);
				p1.put("name", getValue(map,"S_NAME"));
				p1.put("value", Double.parseDouble(getValue(map,"ZJ").toString()));
				pie_listData.add(p1);
			}
			capital.put("chart_config_pie", pie_listData);
			List<JSONObject> bubble_listData = new ArrayList<JSONObject>();
			for (int i = 0; i < queryXmzj.size(); i++) {
				List<Double> vList1 = new ArrayList<Double>();//value1=项目数  value2=完成率 value3=资金投入
				JSONObject bu1 = new JSONObject();
				Map<String, Object> map = queryXmzj.get(i);
				bu1.put("name", getValue(map,"S_NAME"));
				bu1.put("group", getValue(map,"S_NAME"));
				vList1.add(Double.parseDouble(getValue(map,"GS").toString()));
				vList1.add(Double.parseDouble(getValue(map,"WCL").toString()));
				vList1.add(Double.parseDouble(getValue(map,"ZJ").toString()));
				bu1.put("value", vList1);
				bubble_listData.add(bu1);
			}
			JSONObject chart_config_bubble = new JSONObject();
			chart_config_bubble.put("listData", bubble_listData);
			chart_config_bubble.put("count", 802770);
			capital.put("chart_config_bubble", chart_config_bubble);
			data.put("capital", capital);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("战略地图静态数据查询异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 获取战略地图JSON动态数据
	 * @return
	 */
	public String getStrategyDynamicIndexData() {
		JSONObject json = new JSONObject();
		try{
			Map<String,Object> params = Constants.dateContants;
			params.put("id", "440000000000");
			params.put("scope", "1");
			params.put("level","province");
			Map<String, Object> map = StrategyMapTemplate.strategyDynamicIndexTpl(dao.queryCount(params));
			
			json.put("code", getValue(map,"RET_CODE"));
			json.put("msg", getValue(map,"RET_MSG"));
			
			JSONObject data = new JSONObject();
			
			JSONObject top_indexes = new JSONObject();
			JSONObject success_country = new JSONObject();
			success_country.put("amount", getValue(map,"SUCCESS_COUNTRY_A"));
			success_country.put("rate", getValue(map,"SUCCESS_COUNTRY_R"));
			top_indexes.put("success_country", success_country);
			JSONObject success_family = new JSONObject();
			success_family.put("amount", getValue(map,"SUCCESS_FAMILY_A"));
			success_family.put("rate",getValue(map,"SUCCESS_FAMILY_R"));
			top_indexes.put("success_family", success_family);
			JSONObject success_people = new JSONObject();
			success_people.put("amount", getValue(map,"SUCCESS_PEOPLE_A"));
			success_people.put("rate", getValue(map,"SUCCESS_PEOPLE_R"));
			top_indexes.put("success_people", success_people);
			data.put("top_indexes", top_indexes);
			
			JSONObject stat = new JSONObject();
			stat.put("amount_org", getValue(map,"AMOUNT_ORG"));
			stat.put("amount_responsible", getValue(map,"AMOUNT_RESPONSIBLE"));
			stat.put("amount_leader", getValue(map,"AMOUNT_LEADER"));
			stat.put("amount_interview", getValue(map,"AMOUNT_INTERVIEW"));
			data.put("stat", stat);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("战略地图动态数据查询异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");		
		}
		return json.toString();
	}
	
	 private Object getValue(Map map,String key){
	    	return CommonUtil.getMapValue(map,key);
		}
}
