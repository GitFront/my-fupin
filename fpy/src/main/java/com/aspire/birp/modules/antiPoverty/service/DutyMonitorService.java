package com.aspire.birp.modules.antiPoverty.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.birp.common.utils.CommonUtil;
import com.aspire.birp.modules.antiPoverty.dao.DutyDao;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.sys.utils.UserUtils;

import net.sf.json.JSONObject;

/** 
 * 责任监控Service 
 * @author zhudiyuan
 * @version 
 */
@Service
public class DutyMonitorService  extends DataMonitorService{
	private static Logger log = LoggerFactory.getLogger(ProjectMonitorService.class);
	
	@Autowired
	private DutyDao dutyDao;
	/**
	 * 责任监控管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorDutyDutyManagement(HttpServletRequest request,String id,String level,String year) {
		log.info("查询责任监控管理信息===========");
		System.out.println("查询责任监控管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			UserUtils.putHelpPacToParams(params);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			    //System.out.println("key:="+entry.getKey()+" value:="+entry.getValue()[0]);
			}
			//用于导出的参数
			String exportId = "dutyManagement-"+CommonUtil.getUUID();
			getParamsPool().put(exportId, params);
			//查饼图表数据
			List<Map<String,Object>> chart_l = dutyDao.dutyHelpTrends(params);
			List<Map<String,Object>> chart_r = dutyDao.dutyHelpFunds(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = dutyDao.dutyManagementTotal(params);//合计
			}
			List<Map<String,Object>> table_list = dutyDao.dutyManagementCount(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "帮扶单位");
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("S_NAME"));
				config1.put("group", "驻村干部");
				config1.put("value", chart_l.get(i).get("S_VALUE1"));
				config_list.add(config1);
				JSONObject config2 = new JSONObject();
				config2.put("name", chart_l.get(i).get("S_NAME"));
				config2.put("group", "帮扶责任人");
				config2.put("value", chart_l.get(i).get("S_VALUE2"));
				config_list.add(config2);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_helper_trend", chart_config_trend);
			
			JSONObject chart_config_current = new JSONObject();
			chart_config_current.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size() && i<5;i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", "帮扶单位");
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config2_list.add(config);
				
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_r.get(i).get("S_NAME"));
				config1.put("group", "驻村干部");
				config1.put("value", chart_r.get(i).get("S_VALUE1"));
				config2_list.add(config1);
				
				JSONObject config2 = new JSONObject();
				config2.put("name", chart_r.get(i).get("S_NAME"));
				config2.put("group", "帮扶责任人");
				config2.put("value", chart_r.get(i).get("S_VALUE2"));
				config2_list.add(config2);
			}
			chart_config_current.put("config", config2_list);
			data.put("chart_config_helper_distribution", chart_config_current);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id="+exportId);
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","colspan","colspan","colspan","colspan","colspan"};
			String[] content_arr = {"序号","行政区域","帮扶单位(个)","驻村干部(人)","帮扶责任人(人)","帮扶走访量(次)"};
			for(int j=0;j<6;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put(span_arr[j], 1);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<6;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("查询责任监控管理信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 责任监控分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorDutyDutyAnalysis(HttpServletRequest request,String id,String level,String year) {
		log.info("查询责任监控分析信息===========");
		System.out.println("查询责任监控分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", 40001004);
			params.put("level", level);
			params.put("year", year);
			UserUtils.putHelpPacToParams(params);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			   // System.out.println("key:="+entry.getKey()+" value:="+entry.getValue()[0]);
			}
			//用于导出的参数
			String exportId = "dutyAnalysis-"+CommonUtil.getUUID();
			getParamsPool().put(exportId, params);
			//查饼图表数据
			List<Map<String,Object>> chart_l = dutyDao.dutyAnalyseHelpTrends(params);
			List<Map<String,Object>> chart_r = dutyDao.dutyAnalyseHelpFunds(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = dutyDao.dutyAnalyseTotal(params);//合计
			}
			List<Map<String,Object>> table_list = dutyDao.dutyAnalyseCount(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLineBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", "省直和中直");
				config.put("group", "村");
				config.put("value", chart_l.get(i).get("A1"));
				config.put("type", "bar");
				config_list.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", "省直和中直");
				config1.put("group", "户");
				config1.put("value", chart_l.get(i).get("A2"));
				config1.put("type", "bar");
				config_list.add(config1);
				JSONObject config2 = new JSONObject();
				config2.put("name", "省直和中直");
				config2.put("group", "人");
				config2.put("value", chart_l.get(i).get("A3"));
				config2.put("type", "line");
				config_list.add(config2);
				
				JSONObject config3 = new JSONObject();
				config3.put("name", "广州市");
				config3.put("group", "村");
				config3.put("value", chart_l.get(i).get("B1"));
				config3.put("type", "bar");
				config_list.add(config3);
				JSONObject config4 = new JSONObject();
				config4.put("name", "广州市");
				config4.put("group", "户");
				config4.put("value", chart_l.get(i).get("B2"));
				config4.put("type", "bar");
				config_list.add(config4);
				JSONObject config5 = new JSONObject();
				config5.put("name", "广州市");
				config5.put("group", "人");
				config5.put("value", chart_l.get(i).get("B3"));
				config5.put("type", "line");
				config_list.add(config5);

				JSONObject config6 = new JSONObject();
				config6.put("name", "深圳市");
				config6.put("group", "村");
				config6.put("value", chart_l.get(i).get("C1"));
				config6.put("type", "bar");
				config_list.add(config6);
				JSONObject config7 = new JSONObject();
				config7.put("name", "深圳市");
				config7.put("group", "户");
				config7.put("value", chart_l.get(i).get("C2"));
				config7.put("type", "bar");
				config_list.add(config7);
				
				JSONObject config8 = new JSONObject();
				config8.put("name", "深圳市");
				config8.put("group", "人");
				config8.put("value", chart_l.get(i).get("C3"));
				config8.put("type", "line");
				config_list.add(config8);

				JSONObject config9 = new JSONObject();
				config9.put("name", "东莞市");
				config9.put("group", "村");
				config9.put("value", chart_l.get(i).get("D1"));
				config9.put("type", "bar");
				config_list.add(config9);
				JSONObject config10 = new JSONObject();
				config10.put("name", "东莞市");
				config10.put("group", "户");
				config10.put("value", chart_l.get(i).get("D2"));
				config10.put("type", "bar");
				config_list.add(config10);
				JSONObject config11 = new JSONObject();
				config11.put("name", "东莞市");
				config11.put("group", "人");
				config11.put("value", chart_l.get(i).get("D3"));
				config11.put("type", "line");
				config_list.add(config11);

				JSONObject config12 = new JSONObject();
				config12.put("name", "佛山市");
				config12.put("group", "村");
				config12.put("value", chart_l.get(i).get("E1"));
				config12.put("type", "bar");
				config_list.add(config12);
				JSONObject config13 = new JSONObject();
				config13.put("name", "佛山市");
				config13.put("group", "户");
				config13.put("value", chart_l.get(i).get("E2"));
				config13.put("type", "bar");
				config_list.add(config13);
				JSONObject config14 = new JSONObject();
				config14.put("name", "佛山市");
				config14.put("group", "人");
				config14.put("value", chart_l.get(i).get("E3"));
				config14.put("type", "line");
				config_list.add(config14);

				JSONObject config15 = new JSONObject();
				config15.put("name", "珠海市");
				config15.put("group", "村");
				config15.put("value", chart_l.get(i).get("F1"));
				config15.put("type", "bar");
				config_list.add(config15);
				JSONObject config16 = new JSONObject();
				config16.put("name", "珠海市");
				config16.put("group", "户");
				config16.put("value", chart_l.get(i).get("F2"));
				config16.put("type", "bar");
				config_list.add(config16);
				JSONObject config17 = new JSONObject();
				config17.put("name", "珠海市");
				config17.put("group", "人");
				config17.put("value", chart_l.get(i).get("F3"));
				config17.put("type", "line");
				config_list.add(config17);

				JSONObject config18 = new JSONObject();
				config18.put("name", "中山市");
				config18.put("group", "村");
				config18.put("value", chart_l.get(i).get("G1"));
				config18.put("type", "bar");
				config_list.add(config18);
				JSONObject config19 = new JSONObject();
				config19.put("name", "中山市");
				config19.put("group", "户");
				config19.put("value", chart_l.get(i).get("G2"));
				config19.put("type", "bar");
				config_list.add(config19);
				JSONObject config20 = new JSONObject();
				config20.put("name", "中山市");
				config20.put("group", "人");
				config20.put("value", chart_l.get(i).get("G3"));
				config20.put("type", "line");
				config_list.add(config20);
			}
			chart_config_trend.put("config", config_list);
			data.put("chart_config_duty_distribution", chart_config_trend);
			
			JSONObject chart_config_current = new JSONObject();
			chart_config_current.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", "省直和中直");
				config.put("group", "帮扶单位");
				config.put("value", chart_r.get(i).get("A1"));
				config2_list.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", "省直和中直");
				config1.put("group", "帮扶责任人");
				config1.put("value", chart_r.get(i).get("A2"));
				config2_list.add(config1);
				JSONObject config2 = new JSONObject();
				config2.put("name", "省直和中直");
				config2.put("group", "驻村干部");
				config2.put("value", chart_r.get(i).get("A3"));
				config2_list.add(config2);

				JSONObject config3 = new JSONObject();
				config3.put("name", "广州市");
				config3.put("group", "帮扶单位");
				config3.put("value", chart_r.get(i).get("B1"));
				config2_list.add(config3);
				JSONObject config4 = new JSONObject();
				config4.put("name", "广州市");
				config4.put("group", "帮扶责任人");
				config4.put("value", chart_r.get(i).get("B2"));
				config2_list.add(config4);
				JSONObject config5 = new JSONObject();
				config5.put("name", "广州市");
				config5.put("group", "驻村干部");
				config5.put("value", chart_r.get(i).get("B3"));
				config2_list.add(config5);

				JSONObject config6 = new JSONObject();
				config6.put("name", "深圳市");
				config6.put("group", "帮扶单位");
				config6.put("value", chart_r.get(i).get("C1"));
				config2_list.add(config6);
				JSONObject config7 = new JSONObject();
				config7.put("name", "深圳市");
				config7.put("group", "帮扶责任人");
				config7.put("value", chart_r.get(i).get("C2"));
				config2_list.add(config7);

				JSONObject config8 = new JSONObject();
				config8.put("name", "深圳市");
				config8.put("group", "驻村干部");
				config8.put("value", chart_r.get(i).get("C3"));
				config2_list.add(config8);

				JSONObject config9 = new JSONObject();
				config9.put("name", "东莞市");
				config9.put("group", "帮扶单位");
				config9.put("value", chart_r.get(i).get("D1"));
				config2_list.add(config9);
				JSONObject config10 = new JSONObject();
				config10.put("name", "东莞市");
				config10.put("group", "帮扶责任人");
				config10.put("value", chart_r.get(i).get("D2"));
				config2_list.add(config10);
				JSONObject config11 = new JSONObject();
				config11.put("name", "东莞市");
				config11.put("group", "驻村干部");
				config11.put("value", chart_r.get(i).get("D3"));
				config2_list.add(config11);

				JSONObject config12 = new JSONObject();
				config12.put("name", "佛山市");
				config12.put("group", "帮扶单位");
				config12.put("value", chart_r.get(i).get("E1"));
				config2_list.add(config12);
				JSONObject config13 = new JSONObject();
				config13.put("name", "佛山市");
				config13.put("group", "帮扶责任人");
				config13.put("value", chart_r.get(i).get("E2"));
				config2_list.add(config13);
				JSONObject config14 = new JSONObject();
				config14.put("name", "佛山市");
				config14.put("group", "驻村干部");
				config14.put("value", chart_r.get(i).get("E3"));
				config2_list.add(config14);

				JSONObject config15 = new JSONObject();
				config15.put("name", "珠海市");
				config15.put("group", "帮扶单位");
				config15.put("value", chart_r.get(i).get("F1"));
				config2_list.add(config15);
				JSONObject config16 = new JSONObject();
				config16.put("name", "珠海市");
				config16.put("group", "帮扶责任人");
				config16.put("value", chart_r.get(i).get("F2"));
				config2_list.add(config16);
				JSONObject config17 = new JSONObject();
				config17.put("name", "珠海市");
				config17.put("group", "驻村干部");
				config17.put("value", chart_r.get(i).get("F3"));
				config2_list.add(config17);

				JSONObject config18 = new JSONObject();
				config18.put("name", "中山市");
				config18.put("group", "帮扶单位");
				config18.put("value", chart_r.get(i).get("G1"));
				config2_list.add(config18);
				JSONObject config19 = new JSONObject();
				config19.put("name", "中山市");
				config19.put("group", "帮扶责任人");
				config19.put("value", chart_r.get(i).get("G2"));
				config2_list.add(config19);
				JSONObject config20 = new JSONObject();
				config20.put("name", "中山市");
				config20.put("group", "驻村干部");
				config20.put("value", chart_r.get(i).get("G3"));
				config2_list.add(config20);


			}
			chart_config_current.put("config", config2_list);
			data.put("chart_config_strength_distribution", chart_config_current);
			
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id="+exportId);
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","rowspan","colspan","rowspan"};
			String[] content_arr = {"序号","行政区域","省直和中直驻粤<br>单位帮扶","珠三角对口帮扶市","自身帮扶"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				if(j==0 || j==1){
					head_1.put(span_arr[j], 3);
					head_1.put("content", content_arr[j]);
				}
				if(j==2){
					head_1.put(span_arr[j], 2);
					head_1.put("colspan", 3);
					head_1.put("content", content_arr[j]);
				}
				if(j==3){
					head_1.put(span_arr[j], 18);
					head_1.put("content", content_arr[j]);
				}
				
				if(j==4){
					head_1.put(span_arr[j], 2);
					head_1.put("colspan", 3);
					head_1.put("content", content_arr[j]);
				}
				
				head1.add(head_1);
			}
			head_list.add(head1);
			
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] span_arr2 = {"colspan","colspan","colspan","colspan","colspan","colspan"};
			String[] content_arr2 = {"广州市","深圳市","珠海市","佛山市","东莞市","中山市"};
			for(int k=0;k<6;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put(span_arr2[k], 3);
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			
			List<JSONObject> head3 = new ArrayList<JSONObject>();
			//String[] span_arr3 = {"colspan","colspan","colspan","colspan","colspan","colspan"};
			String[] content_arr3 = {"村数","户数","人数","村数","户数","人数","村数","户数","人数","村数","户数","人数","村数","户数","人数","村数","户数","人数","村数","户数","人数","村数","户数","人数"};
			for(int n=0;n<24;n++){
				JSONObject head_3 =  new JSONObject();
				head_3.put("content", content_arr3[n]);
				head3.add(head_3);
			}
			head_list.add(head3);
			
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//合计
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null && table_list_c.size() > 0){
				for(int n=0;n<26;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", "-");
					}else{
						head_content.put("content", table_list_c.get("A"+(n+1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<26;n++){
					JSONObject head_content =  new JSONObject();
					if(n==0){
						head_content.put("content", (m+1));
					}else{
						head_content.put("content", table_list.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("查询责任监控分析信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	
}
