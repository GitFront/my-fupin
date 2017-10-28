package com.aspire.birp.modules.antiPoverty.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.bi.common.config.Global;
import com.aspire.birp.common.excel.CSVUtils;
import com.aspire.birp.common.utils.CommonUtil;
import com.aspire.birp.common.utils.DownLoadFileUtils;
import com.aspire.birp.modules.antiPoverty.dao.FundMonitoringDao;
import com.aspire.birp.modules.antiPoverty.dao.TreeDataDao;
import com.aspire.birp.modules.antiPoverty.utils.CommaProcessing;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.sys.utils.UserUtils;

import net.sf.json.JSONObject;
@Service
public class FundMonitoringService extends DataMonitorService{
	private static Logger log = LoggerFactory.getLogger(FundMonitoringService.class);
	
	@Autowired
	private FundMonitoringDao fmtDao;
	@Autowired
	private TreeDataDao treeDao;
	
	/**
	 * 资金管理
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getgetDataMonitorCapitalCapitalManagement(HttpServletRequest request,String id,String level,String year,String scope) {
		log.info("查询资金管理信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			UserUtils.putHelpPacToParams(params);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//用于导出的参数
			
			getParamsPool().put("FundManagement", params);
			String exportId = "FundManagement-"+CommonUtil.getUUID();
			getParamsPool().put(exportId, params);
			//查图表数据
			List<Map<String,Object>> chart_l = fmtDao.queryFundMonitoringManagement1(params);
			List<Map<String,Object>> chart_r = fmtDao.queryFundMonitoringManagement2(params);
			List<Map<String,Object>> chart_t = fmtDao.queryFundMonitoringManagement3(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fmtDao.queryFundMonitoringManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fmtDao.queryFundMonitoringManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//one
			JSONObject one = new JSONObject();
			one.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list_one = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", chart_l.get(i).get("S_GROUP"));
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list_one.add(config);
			}
			one.put("config", config_list_one);
			data.put("chart_config_capital_source", one);
			
			//two
			JSONObject two = new JSONObject();
			two.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list_two = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config_list_two.add(config);
			}
			two.put("config", config_list_two);
			
			two.put("expand_convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list_expand= new ArrayList<JSONObject>();
			for(int i=0;i<chart_t.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_t.get(i).get("S_NAME"));
				config.put("group", chart_t.get(i).get("S_GROUP"));
				config.put("value", chart_t.get(i).get("S_VALUE"));
				config_list_expand.add(config);
			}
			two.put("expand_config", config_list_expand);
			data.put("chart_config_area_order", two);
			
//统计表
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id="+exportId);
//表头
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","colspan","colspan","colspan","colspan"};
			String[] content_arr = {"序号","行政<br/>区域","年度<br/>项目<br/>资金<br/>总额","帮扶单位<br/>自筹资金","帮扶市","中央","省级","被帮扶市"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				if (j==5) {
					head_1.put(span_arr[j], 3);
				}else if(j==7){
					head_1.put(span_arr[j], 6);

				}else{
					head_1.put(span_arr[j], 4);
				}
				

				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			
		
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"合计","地级<br/>市财<br/>政资<br/>金","区级<br/>和镇级<br/>财政<br/>资金","社会<br/>资金",
					"合计","中央<br/>财政<br/>资金","中央<br/>行业<br/>资金",
					"合计","省级<br/>财政<br/>资金","省级<br/>行业<br/>资金","社会<br/>资金",
					"合计","地级<br/>市财<br/>政资<br/>金","县级<br/>和镇<br/>级财<br/>政资<br/>金","地级<br/>市行<br/>业资<br/>金","县级<br/>和镇<br/>级行<br/>业资<br/>金","社会<br/>资金"};
			for(int k=0;k<17;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			

			table.put("head", head_list);
//合计
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<21;n++){
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
				for(int n=0;n<21;n++){
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
			table.put("notes", "备注：表中统计数据为万元");
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("资金管理查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	/**
	 * 资金分析
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getDataMonitorCapitalCapitalAnalysis(HttpServletRequest request,String id,String level,String year,String scope,String project_type_country,String project_type_family) {
		log.info("查询资金分析信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			UserUtils.putHelpPacToParams(params);
			params.put("id", id);
			params.put("level", level);
			params.put("year", year);
			//选择
			String toCountry = project_type_country;
			String toFamily = project_type_family;
		    List<String> projClass = new ArrayList<String>();
		    if(null != toCountry && !"".equals(toCountry) ){
		    	String[] toCountrys = toCountry.split(",");
		    	for(String et : toCountrys){
		    		if("country_other".equals(et)){//选择村项目其他
    					projClass.add("捐赠慰问");
		    			projClass.add("基金");
		    			projClass.add("村容村貌");
		    		}else
		    		projClass.add(CommaProcessing.projClassMap.get(et));
		    	}
		    	
		    }
		    if(null != toFamily && !"".equals(toFamily) ){
		    	String[] toFamilys = toFamily.split(",");
		    	for(String st : toFamilys){
		    		if("family_policy".equals(st)){//选择户项目政策补贴和社会保障
    					projClass.add("低保金");
		    			projClass.add("五保金");
		    			projClass.add("养老保险");
		    			projClass.add("医疗保险");
		    			projClass.add("医疗救助");
		    			projClass.add("政策性补贴");
		    			projClass.add("转移性收入");
		    			projClass.add("转移性支出");
		    			projClass.add("以奖代补");
		    		}else
		    		projClass.add(CommaProcessing.projClassMap.get(st));
		    	}
		    	
		    }
		    params.put("list",projClass);
		    
			//用于导出的参数
			String exportId = "FundAnalysisManagement-"+CommonUtil.getUUID();
			getParamsPool().put(exportId, params);
			//查图表数据
			List<Map<String,Object>> chart_l = fmtDao.queryFundAnalysisManagement1(params);
			List<Map<String,Object>> chart_r = fmtDao.queryFundAnalysisManagement2(params);
			List<Map<String,Object>> chart_t = fmtDao.queryFundAnalysisManagement3(params);
			//查table数据
			Map<String,Object> table_list_c = null;
			if(!"country".equals(level)){
				table_list_c = fmtDao.queryFundAnalysisManagementTableC(params);//合计
			}
			List<Map<String,Object>> table_list = fmtDao.queryFundAnalysisManagementTable(params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			//one
			JSONObject one = new JSONObject();
			one.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list_one = new ArrayList<JSONObject>();
			for(int i=0;i<chart_l.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", chart_l.get(i).get("S_GROUP"));
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list_one.add(config);
			}
			one.put("config", config_list_one);
			data.put("chart_config_capital_trend", one);
			
			//two
			JSONObject two = new JSONObject();
			two.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list_two = new ArrayList<JSONObject>();
			for(int i=0;i<chart_r.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", chart_r.get(i).get("S_GROUP"));
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config_list_two.add(config);
			}
			two.put("config", config_list_two);
			
			two.put("expand_convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list_expand= new ArrayList<JSONObject>();
			for(int i=0;i<chart_t.size();i++){
				JSONObject config = new JSONObject();
				config.put("name", chart_t.get(i).get("S_NAME"));
				config.put("group", chart_t.get(i).get("S_GROUP"));
				config.put("value", chart_t.get(i).get("S_VALUE"));
				config_list_expand.add(config);
			}
			two.put("expand_config", config_list_expand);
			data.put("chart_config_capital_distribution", two);
			
			//统计表
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id="+exportId);
//表头
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = {"rowspan","rowspan","rowspan","rowspan","colspan","colspan","colspan","colspan"};
			String[] content_arr = {"序号","行政<br/>区域","年度<br/>项目<br/>资金<br/>总额","帮扶单位<br/>自筹资金","帮扶市","中央","省级","被帮扶市"};
			for(int j=0;j<8;j++){
				JSONObject head_1 =  new JSONObject();
				if (j==5) {
					head_1.put(span_arr[j], 3);
				}else if(j==7){
					head_1.put(span_arr[j], 6);

				}else{
					head_1.put(span_arr[j], 4);
				}
				

				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			
		
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = {"合计","地级<br/>市财<br/>政资<br/>金","区级<br/>和镇级<br/>财政<br/>资金","社会<br/>资金",
					"合计","中央<br/>财政<br/>资金","中央<br/>行业<br/>资金",
					"合计","省级<br/>财政<br/>资金","省级<br/>行业<br/>资金","社会<br/>资金",
					"合计","地级<br/>市财<br/>政资<br/>金","县级<br/>和镇<br/>级财<br/>政资<br/>金","地级<br/>市行<br/>业资<br/>金","县级<br/>和镇<br/>级行<br/>业资<br/>金","社会<br/>资金"};
			for(int k=0;k<17;k++){
				JSONObject head_2 =  new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			

			table.put("head", head_list);
//合计
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> body0 =  new ArrayList<JSONObject>();
			if(table_list_c!=null){
				for(int n=0;n<21;n++){
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
				for(int n=0;n<21;n++){
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
			table.put("notes", "备注：表中统计数据为万元");
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("资金分析查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 查询文件号
	 * @param request
	 * @param level
	 * @param year
	 * @return
	 */
	public String getDataMonitorCapitalCapitalFile(HttpServletRequest request, String level, String year,String keyword,Integer page) {
		log.info("查询文件号信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("level", level);
			params.put("year", year);
			params.put("pageSize", 10);
			params.put("page", page);
			params.put("keyword", keyword);
			//查询数据
			List<Map<String,Object>> fundFileList = null;
			Long fundFileCount = 0l;
			if(null != keyword && !"".equals(keyword)){
				fundFileList = fmtDao.queryFundFileList(params);
				fundFileCount = fmtDao.queryFundFileListCount(params);
			}

			json.put("code", 0);
			json.put("msg", "获取信息成功");
			JSONObject data = new JSONObject();
			
			//查询列表
			JSONObject table = new JSONObject();
			table.put("title", "");
			table.put("export_url", "");
            //表头
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] content_arr = {"文件号","资金年份","资金级别","金额(万元)","操作"};
			for(int j=0;j<5;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			table.put("head", head_list);

			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			//table体数据
			for(int m=0;fundFileList != null && m<fundFileList.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				for(int n=0;n<5;n++){
					JSONObject head_content =  new JSONObject();
					if(n==4){
						head_content.put("content", "查看");
						head_content.put("action_type", "file_detail");
						JSONObject action_data =  new JSONObject();
						action_data.put("file_id", fundFileList.get(m).get("A"+(n+1)));
						head_content.put("action_data", action_data);
					}else{
						head_content.put("content", fundFileList.get(m).get("A"+(n+1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			JSONObject pagination = new JSONObject();
			pagination.put("page_total", fundFileCount%10 > 0 ? fundFileCount/10+1 : fundFileCount/10);
			pagination.put("page_cur", page);
			pagination.put("amount_total", fundFileCount);
			table.put("pagination", pagination);
			data.put("table", table);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("文件号查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
	
	/**
	 * 查看文件详情-统计表
	 * @param request
	 * @param id
	 * @param area_level
	 * @param area_id
	 * @param page
	 * @return
	 */
	public String getDataMonitorCapitalCapitalFileDetailTable(HttpServletRequest request, String id, String area_level,
			String area_id, Integer page) {
		log.info("查看文件详情-统计表===========");
		JSONObject json = new JSONObject();
		try {
			// 查询数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("area_id", area_id);
			params.put("pageSize", 10);
			params.put("page", page);
			Map<String, String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String[]> entry = entries.next();
				params.put(entry.getKey(), entry.getValue()[0]);
				// System.out.println("key:="+entry.getKey()+"
				// value:="+entry.getValue()[0]);
			}
			// 用于导出的参数
			String exportId = "queryFundFileDetailList-"+CommonUtil.getUUID();
			getParamsPool().put(exportId, params);
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");

			JSONObject data = new JSONObject();

			// 查table数据
			List<Map<String, Object>> table_list = fmtDao.queryFundFileDetailList(params);
			Long fundFileCount = fmtDao.queryFundFileDetailListCount(params);
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()
					+ "/antiPoverty/dataMonitor/beforeExport.do?export_task_id="+exportId);
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = { "rowspan", "colspan", "colspan", "colspan", "colspan", "colspan", "rowspan",
					"rowspan", "rowspan", "rowspan", "rowspan"};
			String[] content_arr = { "序<br>号", "中央财政资金", "省级财政资金", "地级市级财政资金", "县(市)级财政资金", "镇(乡)级财政资金", 
					"项目<br>名称", "项目<br>日期","项目<br>金额", "项目<br>收益", "操<br>作"};
			for (int j = 0; j < 11; j++) {
				JSONObject head_1 = new JSONObject();
				if(1 == j){
					head_1.put(span_arr[j], 3);
					head_1.put("content", content_arr[j]);
				}else if(2 == j || 3 == j || 4 == j || 5 == j){
					head_1.put(span_arr[j], 5);
					head_1.put("content", content_arr[j]);
				}else{
					head_1.put(span_arr[j], 2);
					head_1.put("content", content_arr[j]);
				}
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = { "文<br>件<br>号", "下拨<br>日期", "下拨<br>金额", "资金<br>下拨<br>地级<br>市", "文<br>件<br>号", "下拨<br>日期", "下拨<br>金额", "下拨<br>周期", "资金<br>下拨<br>县市", "文<br>件<br>号", "下拨<br>日期", "下拨<br>金额", "下拨<br>周期",
					"资金<br>下拨<br>镇乡", "文<br>件<br>号", "下拨<br>日期", "下拨<br>金额", "下拨<br>周期", "资金<br>下拨<br>村", "文<br>件<br>号", "下拨<br>日期", "下拨<br>金额", "下拨<br>周期" };
			for (int m = 0; m < 23; m++) {
				JSONObject head_2 = new JSONObject();
				head_2.put("content", content_arr2[m]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			// table体数据
			for (int m = 0; null != table_list && m < table_list.size(); m++) {
				List<JSONObject> body = new ArrayList<JSONObject>();
				for (int n = 0; n < 28; n++) {
					JSONObject head_content = new JSONObject();
					head_content.put("content", table_list.get(m).get("A" + n));
					body.add(head_content);
					if (n == 27) {
						JSONObject viewContent = new JSONObject();
						viewContent.put("content", "-");
						body.add(viewContent);
				    } 
					
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			JSONObject pagination = new JSONObject();
			pagination.put("page_total", fundFileCount%10 > 0 ? fundFileCount/10+1 : fundFileCount/10);
			pagination.put("page_cur", page);
			pagination.put("amount_total", fundFileCount);
			table.put("pagination", pagination);
			table.put("notes", "备注：上表“数量“单位为个，“计划资金”、“实际资金”单位为万元。");
			data.put("table", table);

			json.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查看文件详情-统计表异常：" + e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");
		}
		// System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 查看文件详情-文件佐证
	 * @param request
	 * @param id
	 * @return
	 */
	public String getDataMonitorCapitalCapitalFileBasicInfo(HttpServletRequest request, String id) {
		log.info("查看文件佐证===========");
		JSONObject json = new JSONObject();
		try {
			// 查询数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("FILE_ID", id);
			Map<String, String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String[]> entry = entries.next();
				params.put(entry.getKey(), entry.getValue()[0]);
				// System.out.println("key:="+entry.getKey()+"
				// value:="+entry.getValue()[0]);
			}
			json.put("code", 0);
			json.put("msg", "获取信息成功");

			JSONObject data = new JSONObject();
			
			// 查佐证数据
			List<Map<String, Object>> table_list = fmtDao.queryFundFileBasicInfo(params);
			Map<String,Object> fileMap = fmtDao.queryFundFileById(params);
			String fileName = fileMap.get("FILE_NAME") == null ? "other" : fileMap.get("FILE_NAME").toString();
			List<JSONObject> downloadList = new ArrayList<JSONObject>();
			for (int m = 0; null != table_list && m < table_list.size(); m++) {
				JSONObject downloadObj = new JSONObject();
				downloadObj.put("title", table_list.get(m).get("A2"));
				downloadObj.put("download_url", request.getContextPath()+"/antiPoverty/fund/downloadFoundFile.do?filePath="+Global.getConfig("file.path")+table_list.get(m).get("A1")+"&fileName="+fileName+"&fileType="+table_list.get(m).get("A2"));
				downloadList.add(downloadObj);
			}
			data.put("file_name", "粤财农[2017]10号");
			data.put("download_list", downloadList);
			data.put("area_level","province");
			data.put("area_id","440000000000");
			json.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询文件佐证信息：" + e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");
		}
		// System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	
	/**
	 * 文件号详情树
	 * @param id 查找对应行政区域的ID
	 * @param level 见层级取值
	 * @param year 年
	 * @param wintype 数据监控窗口所属类型
	 * @param tabtype 数据监控窗口标签页类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMonitorFileTree(String id,Integer file_id,String level,String year,String wintype,String tabtype) {
		log.info("查询文件号详情树信息===========");
		JSONObject json = new JSONObject();
		try{
			Map<String, Object> params =new HashMap<String, Object>();
			
			String unit = "";
			String hy_time = "";
			String total_count_field = "";
			String tips = "";
			String time_str = "";
			boolean isIndustryTime = false;
			
			JSONObject data = new JSONObject();
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			params.putAll(Constants.dateContants);
			unit = "";
			total_count_field = "FILE_ACOUNT";
			tips = "此处指标为：财政资金总额/余额，<br>单位“万元”，";
			
			
			String jd_time_s = "";
			String match_id = id;
			//Long userPac = Long.parseLong(UserUtils.getAreaList().iterator().next().toString());
			//params.put("PAC", userPac);
			params.put("year", year);
			params.put("FILE_ID", file_id);
			Map<String,Object> fileMap = fmtDao.queryFundFileById(params);
			String filePac = fileMap.get("PAC") == null ? "" : fileMap.get("PAC").toString();
			params.put("PAC", filePac);
			Map<String,Object> userMap = fmtDao.queryFundFileByFileId(params);
			String user_level = userMap.get("S_LEVEL").toString();//用户等级相当于文件级别，需转换成英文，后续加上
			Long userPac = Long.parseLong(userMap.get("S_ID").toString());
			System.out.println("================ddddddd======="+user_level);
			Map<String,Object> areaMap = new HashMap<String,Object>();
			/*if(Long.parseLong(id)>userPac){
				params.put("PAC", id);
			}else{
				params.put("PAC", userPac);
			}*/
			params.put("PAC",  userMap.get("S_ID").toString());
			List<Map<String,Object>> areaList = fmtDao.queryFundFileBySubId(params);
			jd_time_s = areaList.get(0).get("STAT_TIME").toString();
			Map<String,Object> province_map = new HashMap<String,Object>();
			Map<String,Object> city_map = new HashMap<String,Object>();
			Map<String,Object> county_map = new HashMap<String,Object>();
			Map<String,Object> town_map = new HashMap<String,Object>();
			Map<String,Object> country_map = new HashMap<String,Object>();
			for(Map<String,Object> map: areaList){
				Integer lvl = Integer.parseInt(map.get("S_LEVEL").toString());
				if(lvl == 1)province_map= map;
				if(lvl == 2)city_map= map;
				if(lvl == 3)county_map= map;
				if(lvl == 4)town_map= map;
				if(lvl == 5)country_map= map;
			}
			if("province".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get("PIN_ALLOCATE_MONEY")+"/"+userMap.get("PIN_ALLOCATE_BALANCE"));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				params.put("PPAC", province_map.get("S_ID").toString());
				areaMap.put(province_map.get("S_ID").toString(), fmtDao.queryFundFileSubTree(params));
				//县
				String cityid = city_map.get("S_ID")==null?"":city_map.get("S_ID").toString();
				params.put("PPAC", cityid);
				areaMap.put(cityid, fmtDao.queryFundFileSubTree(params));
				//镇
				String countyid = county_map.get("S_ID")==null?"":county_map.get("S_ID").toString();
				params.put("PPAC", countyid);
				areaMap.put(countyid, fmtDao.queryFundFileSubTree(params));
				//村
				String townid = town_map.get("S_ID")==null?"":town_map.get("S_ID").toString();
				params.put("PPAC", townid);
				areaMap.put(townid, fmtDao.queryFundFileSubTree(params));
			}else if("city".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get("PIN_ALLOCATE_MONEY")+"/"+userMap.get("PIN_ALLOCATE_BALANCE"));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				List<Map<String,Object>> citylist = new ArrayList<Map<String,Object>>();
				city_map.put("AREA_ID", userPac);
				city_map.put("AREA_LEVEL", user_level);
				city_map.put(total_count_field, userMap.get("CITY_ALLOCATE_MONEY")+"/"+userMap.get("CITY_ALLOCATE_BALANCE"));
				citylist.add(city_map);
				areaMap.put(province_map.get("S_ID").toString(), citylist);
				//县
				String cityid = city_map.get("S_ID")==null?"":city_map.get("S_ID").toString();
				params.put("PPAC", cityid);
				areaMap.put(cityid, fmtDao.queryFundFileSubTree(params));
				//镇
				String countyid = county_map.get("S_ID")==null?"":county_map.get("S_ID").toString();
				params.put("PPAC", countyid);
				areaMap.put(countyid, fmtDao.queryFundFileSubTree(params));
				//村
				String townid = town_map.get("S_ID")==null?"":town_map.get("S_ID").toString();
				params.put("PPAC", townid);
				areaMap.put(townid, fmtDao.queryFundFileSubTree(params));
			}else if("county".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get("PIN_ALLOCATE_MONEY")+"/"+userMap.get("PIN_ALLOCATE_BALANCE"));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				List<Map<String,Object>> citylist = new ArrayList<Map<String,Object>>();
				city_map.put("AREA_ID", userPac);
				city_map.put("AREA_LEVEL", user_level);
				city_map.put(total_count_field, userMap.get("CITY_ALLOCATE_MONEY")+"/"+userMap.get("CITY_ALLOCATE_BALANCE"));
				citylist.add(city_map);
				areaMap.put(province_map.get("S_ID").toString(), citylist);
				//县
				List<Map<String,Object>> countylist = new ArrayList<Map<String,Object>>();
				county_map.put("AREA_ID", userPac);
				county_map.put("AREA_LEVEL", user_level);
				county_map.put(total_count_field, userMap.get("COUNTY_ALLOCATE_MONEY")+"/"+userMap.get("COUNTY_ALLOCATE_BALANCE"));
				countylist.add(county_map);
				areaMap.put(city_map.get("S_ID").toString(), countylist);
				//镇
				String countyid = county_map.get("S_ID")==null?"":county_map.get("S_ID").toString();
				params.put("PPAC", countyid);
				areaMap.put(countyid, fmtDao.queryFundFileSubTree(params));
				//村
				String townid = town_map.get("S_ID")==null?"":town_map.get("S_ID").toString();
				params.put("PPAC", townid);
				areaMap.put(townid, fmtDao.queryFundFileSubTree(params));
			}else if("town".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get("PIN_ALLOCATE_MONEY")+"/"+userMap.get("PIN_ALLOCATE_BALANCE"));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				List<Map<String,Object>> citylist = new ArrayList<Map<String,Object>>();
				city_map.put("AREA_ID", userPac);
				city_map.put("AREA_LEVEL", user_level);
				city_map.put(total_count_field, userMap.get("CITY_ALLOCATE_MONEY")+"/"+userMap.get("CITY_ALLOCATE_BALANCE"));
				citylist.add(city_map);
				areaMap.put(province_map.get("S_ID").toString(), citylist);
				//县
				List<Map<String,Object>> countylist = new ArrayList<Map<String,Object>>();
				county_map.put("AREA_ID", userPac);
				county_map.put("AREA_LEVEL", user_level);
				county_map.put(total_count_field, userMap.get("COUNTY_ALLOCATE_MONEY")+"/"+userMap.get("COUNTY_ALLOCATE_BALANCE"));
				countylist.add(county_map);
				areaMap.put(city_map.get("S_ID").toString(), countylist);
				//镇
				List<Map<String,Object>> townlist = new ArrayList<Map<String,Object>>();
				town_map.put("AREA_ID", userPac);
				town_map.put("AREA_LEVEL", user_level);
				town_map.put(total_count_field, userMap.get("TOWN_ALLOCATE_MONEY")+"/"+userMap.get("TOWN_ALLOCATE_BALANCE"));
				townlist.add(town_map);
				areaMap.put(county_map.get("S_ID").toString(), townlist);
				//村
				String townid = town_map.get("S_ID")==null?"":town_map.get("S_ID").toString();
				params.put("PPAC", townid);
				areaMap.put(townid, fmtDao.queryFundFileSubTree(params));
			}else if("country".equals(user_level)){
				//省
				List<Map<String,Object>> prolist = new ArrayList<Map<String,Object>>();
				province_map.put("AREA_ID", userPac);
				province_map.put("AREA_LEVEL", user_level);
				province_map.put(total_count_field, userMap.get("PIN_ALLOCATE_MONEY")+"/"+userMap.get("PIN_ALLOCATE_BALANCE"));
				prolist.add(province_map);
				areaMap.put("province", prolist);
				//市
				List<Map<String,Object>> citylist = new ArrayList<Map<String,Object>>();
				city_map.put("AREA_ID", userPac);
				city_map.put("AREA_LEVEL", user_level);
				city_map.put(total_count_field, userMap.get("CITY_ALLOCATE_MONEY")+"/"+userMap.get("CITY_ALLOCATE_BALANCE"));
				citylist.add(city_map);
				areaMap.put(province_map.get("S_ID").toString(), citylist);
				//县
				List<Map<String,Object>> countylist = new ArrayList<Map<String,Object>>();
				county_map.put("AREA_ID", userPac);
				county_map.put("AREA_LEVEL", user_level);
				county_map.put(total_count_field, userMap.get("COUNTY_ALLOCATE_MONEY")+"/"+userMap.get("COUNTY_ALLOCATE_BALANCE"));
				countylist.add(county_map);
				areaMap.put(city_map.get("S_ID").toString(), countylist);
				//镇
				List<Map<String,Object>> townlist = new ArrayList<Map<String,Object>>();
				town_map.put("AREA_ID", userPac);
				town_map.put("AREA_LEVEL", user_level);
				town_map.put(total_count_field, userMap.get("TOWN_ALLOCATE_MONEY")+"/"+userMap.get("TOWN_ALLOCATE_BALANCE"));
				townlist.add(town_map);
				areaMap.put(county_map.get("S_ID").toString(), townlist);
				//村
				List<Map<String,Object>> countrylist = new ArrayList<Map<String,Object>>();
				country_map.put("AREA_ID", userPac);
				country_map.put("AREA_LEVEL", user_level);
				country_map.put(total_count_field, userMap.get("TOWN_ALLOCATE_MONEY")+"/"+userMap.get("TOWN_ALLOCATE_BALANCE"));
				countrylist.add(country_map);
				areaMap.put(town_map.get("S_ID").toString(), countrylist);
			}
			if(isIndustryTime){
				time_str = "建档立卡数据日期"+jd_time_s.substring(0, 4)+"年"+jd_time_s.substring(4, 6)+"月"+jd_time_s.substring(6, 8)+"日"
						+"。<br>行业数据更新日期"+hy_time.substring(0, 4)+"年"+hy_time.substring(4, 6)+"月"+hy_time.substring(6, 8)+"日"+"。";
			}else{
				time_str = "建档立卡数据日期"+jd_time_s.substring(0, 4)+"年"+jd_time_s.substring(4, 6)+"月"+jd_time_s.substring(6, 8)+"日。";
			}
			data.put("tip", tips+"<br>"+time_str);
			data.put("match_node_id", match_id);
			
			List<Map<String,Object>> provList = (List<Map<String, Object>>) areaMap.get("province");
			List<JSONObject> provtree = new ArrayList<JSONObject>();
			JSONObject prov = new JSONObject();
			prov.put("id", provList.get(0).get("S_ID"));
			prov.put("name", provList.get(0).get("S_NAME")+"("+provList.get(0).get(total_count_field)+unit+")");
			prov.put("area_level", provList.get(0).get("AREA_LEVEL"));
			prov.put("area_id", provList.get(0).get("AREA_ID"));
			/*****************市列表***********************************************/
			List<Map<String,Object>> cityList = (List<Map<String, Object>>) areaMap.get(provList.get(0).get("S_ID"));
			if(cityList!=null && cityList.size()>0){
				List<JSONObject> citytree = new ArrayList<JSONObject>();
				for(Map<String,Object> citymap: cityList){//市级层次
					JSONObject city = new JSONObject();
					city.put("id", citymap.get("S_ID"));
					city.put("name", citymap.get("S_NAME")+"("+citymap.get(total_count_field)+unit+")");
					if(cityList.size() > 1){
						city.put("area_level", "city");
						city.put("area_id", citymap.get("S_ID"));
					}else{
						city.put("area_level", citymap.get("AREA_LEVEL"));
						city.put("area_id", citymap.get("AREA_ID"));
					}
					city.put("isParent", true);
					/*****************县列表***********************************************/
					List<Map<String,Object>> countyList = (List<Map<String, Object>>) areaMap.get(citymap.get("S_ID"));
					if(countyList!=null && countyList.size()>0){
						List<JSONObject> countytree = new ArrayList<JSONObject>();
						for(Map<String,Object> countymap: countyList){//县级层次
							JSONObject county = new JSONObject();
							county.put("id", countymap.get("S_ID"));
							county.put("name", countymap.get("S_NAME")+"("+countymap.get(total_count_field)+unit+")");
							if(countyList.size() > 1){
								county.put("area_level", "county");
								county.put("area_id", countymap.get("S_ID"));
							}else{
								county.put("area_level", countymap.get("AREA_LEVEL"));
								county.put("area_id", countymap.get("AREA_ID"));
							}
							county.put("isParent", true);
							/*****************镇列表***********************************************/
							List<Map<String,Object>> townList = (List<Map<String, Object>>) areaMap.get(countymap.get("S_ID"));
							if(townList!=null && townList.size()>0){
								List<JSONObject> towntree = new ArrayList<JSONObject>();
								for(Map<String,Object> townMap:townList){//镇级层次
									JSONObject town = new JSONObject();
									town.put("id", townMap.get("S_ID"));
									town.put("name", townMap.get("S_NAME")+"("+townMap.get(total_count_field)+unit+")");
									if(townList.size() > 1){
										town.put("area_level", "town");
										town.put("area_id", townMap.get("S_ID"));
									}else{
										town.put("area_level", townMap.get("AREA_LEVEL"));
										town.put("area_id", townMap.get("AREA_ID"));
									}
									town.put("isParent", true);
									/*****************村列表***********************************************/
									List<Map<String,Object>> countryList = (List<Map<String, Object>>) areaMap.get(townMap.get("S_ID"));
									if(countryList!=null && countryList.size()>0){
										List<JSONObject> countrytree = new ArrayList<JSONObject>();
										for(Map<String,Object> countryMap : countryList){//村级层次
											JSONObject country = new JSONObject();
											country.put("id", countryMap.get("S_ID"));
											country.put("name", countryMap.get("S_NAME")+"("+countryMap.get(total_count_field)+unit+")");
											country.put("area_level", "country");
											country.put("area_id", countryMap.get("S_ID"));
											countrytree.add(country);
										}
										town.put("children", countrytree);
									}
									towntree.add(town);
								}
								county.put("children", towntree);
							}
							countytree.add(county);
						}
						city.put("children", countytree);
					}
					citytree.add(city);
				}
				prov.put("children", citytree);
			}
			provtree.add(prov);
			data.put("tree", provtree);
			
			json.put("data", data);
		}catch(Exception e){
			e.printStackTrace();
			log.error("文件号详情树查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 文件号详情子树
	 * @param id
	 * @param level
	 * @param year
	 * @param wintype
	 * @param tabtype
	 * @return
	 */
	public String getDataMonitorFileSubtree(String id, Integer file_id, String level, String year, String wintype, String tabtype) {
		log.info("查询文件号详情子树子节点信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("PAC", "440000000000");
			params.put("file_id", file_id);
			params.put("year", year);
			List<Map<String,Object>> subAreaList = fmtDao.queryFundFileSubTree(params);
			
			String unit = "/";
			String total_count_field = "";
			String balance = ""; //余额
			if("province".equals(level)){
				total_count_field = "PIN_ALLOCATE_MONEY";
				balance = "PIN_ALLOCATE_BALANCE";
			}else if("city".equals(level)){
				total_count_field = "PIN_ALLOCATE_MONEY";
				balance = "PIN_ALLOCATE_BALANCE";
			}else if("county".equals(level)){
				total_count_field = "CITY_ALLOCATE_MONEY";
				balance = "CITY_ALLOCATE_BALANCE";
			}else if("town".equals(level)){
				total_count_field = "COUNTY_ALLOCATE_MONEY";
				balance = "COUNTY_ALLOCATE_BALANCE";
			}else if("country".equals(level)){
				total_count_field = "TOWN_ALLOCATE_MONEY";
				balance = "TOWN_ALLOCATE_BALANCE";
			}
			
			
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			List<JSONObject> dataList = new ArrayList<JSONObject>();
			if(subAreaList!=null){
				for(Map<String,Object> map: subAreaList){
					JSONObject area = new JSONObject();
					area.put("id", map.get("S_ID"));
					area.put("pId", map.get("S_ID"));
					area.put("name", map.get("S_NAME")+"("+map.get(total_count_field)+unit+map.get(balance)+")");
					area.put("area_level", Constants.nextLevelMap.get(level));
					area.put("area_id", map.get("S_ID"));
					if(!"country".equals(Constants.nextLevelMap.get(level))){
						area.put("isParent", true);
					}
					dataList.add(area);
				}
			}
			json.put("data", dataList);
		}catch(Exception e){
			e.printStackTrace();
			log.error("文件号树子节点查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		//System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}
	
	/**
	 * 下载佐证文件
	 * @param response
	 * @param filePath
	 * @param fileName
	 */
	public void downloadFoundFile(HttpServletResponse response, String filePath, String fileName,String fileType) {
		log.info("下载佐证文件=====");
		try {
			DownLoadFileUtils.downLoadFile(response, filePath, fileName,fileType);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("下载佐证文件失败异常信息："+e.getMessage());
		}
		
	}
	/**
	 * 贫困户列表
	 * @param id 区域ID
	 * @param level 区域层级
	 * @param year 年
	 * @return
	 */
	public String getPoorFamilyList(HttpServletRequest request,String level,String id,Integer page) {
		log.info("查询贫困户列表信息===========");
		JSONObject json = new JSONObject();
		try{
			//查询数据
			Map<String, Object> params =new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			Map<String,String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String,String[]>> entries = paramMap.entrySet().iterator();  
			while (entries.hasNext()) {  
			    Map.Entry<String,String[]> entry = entries.next();
			    params.put(entry.getKey(),entry.getValue()[0]); 
			}
			//查询table数据
			params.put("pageSize", 5);
			
			
			/*查询地图权限*/
			String userPac = UserUtils.getAreaList().iterator().next().toString();
			
			if(userPac != null && userPac.length() == 12){
				if(!userPac.substring(9,12).equals("000")){
					id = userPac;
					level="country";
				}
			}
			params.put("COUNTRY_PAC", id);
			
			
			
			params.put("level", level);
			List<Map<String, Object>> table_list = new ArrayList<Map<String, Object>>();
			long total_count = 0;
			
			int size = 0;
			String[] content_arr = null;
			String export_task_id = "";
			String export_url = "";
			String notes = "";
			
			table_list = fmtDao.queryPoorFamilyListTable(params);
			total_count = fmtDao.queryPoorFamilyListTableC(params);
			export_task_id = "PoorFamilyList";
			String exportId = export_task_id+"-"+CommonUtil.getUUID();
			export_url = request.getContextPath()+"/antiPoverty/dataMonitor/beforeExport.do?export_task_id="+exportId;
			
			if("town".equals(level)){
				size = 7;
				content_arr = new String[]{"序号","村名","户主姓名","经纬度","脱贫年份",params.get("F_COUNTRYFILE_HELP_TRENDSY")+"年综合得分","当年走访次数"};
			}else if("country".equals(level)){
				size = 6;
				content_arr = new String[]{"序号","户主姓名","经纬度","脱贫年份",params.get("F_COUNTRYFILE_HELP_TRENDSY")+"年综合得分","当年走访次数"};
			}
			
			
			getParamsPool().put(exportId, params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");
			
			JSONObject data = new JSONObject();
			JSONObject table = new JSONObject();
			table.put("title", "建档立卡贫困户列表");
			table.put("export_url", export_url);
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head = new ArrayList<JSONObject>();
			for(int j=0;j<size;j++){
				JSONObject head_1 =  new JSONObject();
				head_1.put("content", content_arr[j]);
				head.add(head_1);
			}
			head_list.add(head);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
//table体数据
			for(int m=0;m<table_list.size();m++){
				List<JSONObject> body =  new ArrayList<JSONObject>();
				if("town".equals(level)){
					for(int n=0;n<size;n++){
						JSONObject head_content =  new JSONObject();
						JSONObject action_data =  new JSONObject();
						if(n==0){
							head_content.put("content", table_list.get(m).get("RM"));
						}else{
							if(n==2){
								head_content.put("content", table_list.get(m).get("A"+(n+1)));
								head_content.put("action_type", "family_file");
								action_data.put("family_id", table_list.get(m).get("FAMILYID"));
								head_content.put("action_data", action_data);
							}else{
								head_content.put("content", table_list.get(m).get("A"+(n+1)));
							}
						}
						body.add(head_content);
					}
				}else if("country".equals(level)){
					for(int n=0;n<size;n++){
						JSONObject head_content =  new JSONObject();
						JSONObject action_data =  new JSONObject();
						if(n==0){
							head_content.put("content", table_list.get(m).get("RM"));
						}else{
							if(n==1){
								head_content.put("content", table_list.get(m).get("A"+(n+2)));
								head_content.put("action_type", "family_file");
								action_data.put("family_id", table_list.get(m).get("FAMILYID"));
								head_content.put("action_data", action_data);
							}else{
								head_content.put("content", table_list.get(m).get("A"+(n+2)));
							}
						}
						body.add(head_content);
					}
				}
				
				body_list.add(body);
			}
			table.put("body", body_list);
			JSONObject pagination =  new JSONObject();
			pagination.put("page_total", total_count%5>0?total_count/5+1:total_count/5);
			pagination.put("page_cur",page);
			pagination.put("amount_total", total_count);
			table.put("pagination", pagination);
			
			data.put("table", table);
			
			json.put("data", data);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("贫困户列表查询失败异常信息："+e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");			
		}
		return json.toString();
	}
}
