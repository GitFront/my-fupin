package com.aspire.birp.modules.antiPoverty.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.aspire.birp.modules.antiPoverty.dao.FairlyDao;
import com.aspire.birp.modules.antiPoverty.dao.ProjectManagementDao;
import com.aspire.birp.modules.antiPoverty.utils.Constants;
import com.aspire.birp.modules.antiPoverty.utils.JsonFormatUtils;

import net.sf.json.JSONObject;

/**
 * 项目监控Service
 * 
 * @author zhudiyuan
 * @version
 */
@Service
public class ProjectMonitorService extends DataMonitorService {
	private static Logger log = LoggerFactory.getLogger(ProjectMonitorService.class);

	@Autowired
	private ProjectManagementDao projectManagementDao;

	/**
	 * 项目监控管理
	 * 
	 * @param id
	 *            区域ID
	 * @param level
	 *            区域层级
	 * @param year
	 *            年
	 * @return
	 */
	public String getDataMonitorProjectProjectManagement(HttpServletRequest request, String id, String level,
			String year) {
		log.info("查询项目监控管理信息===========");
		System.out.println("查询项目监控管理信息===========");
		JSONObject json = new JSONObject();
		try {
			// 查询数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", Short.parseShort(year));
			Map<String, String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String[]> entry = entries.next();
				params.put(entry.getKey(), entry.getValue()[0]);
				// System.out.println("key:="+entry.getKey()+"
				// value:="+entry.getValue()[0]);
			}
			// 用于导出的参数
			getParamsPool().put("projectManagement", params);

			json.put("code", 0);
			json.put("msg", "获取信息成功");

			JSONObject data = new JSONObject();

			// 查饼图表数据
			List<Map<String, Object>> chart_l = projectManagementDao.projectScale(params);
			JSONObject chart_config_trend = new JSONObject();
			chart_config_trend.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			BigDecimal cprojNum = new BigDecimal(0);// 到村累计项目数
			BigDecimal fprojNum = new BigDecimal(0);// 到户累计项目数
			BigDecimal ftemp = null;
			BigDecimal ctemp = null;
			for (int i = 0; i < chart_l.size(); i++) {
				ftemp = (BigDecimal) chart_l.get(i).get("S_VALUE");
				ctemp = (BigDecimal) chart_l.get(i).get("S_VALUE1");
				fprojNum = fprojNum.add(ftemp);
				cprojNum = cprojNum.add(ctemp);
			}
			JSONObject config2 = new JSONObject();
			config2.put("name", "累计");
			config2.put("group", "到户");
			config2.put("value", fprojNum);
			config_list.add(config2);
			JSONObject config3 = new JSONObject();
			config3.put("name", "累计");
			config3.put("group", "到村");
			config3.put("value", cprojNum);
			config_list.add(config3);
			for (int i = 0; i < chart_l.size(); i++) {
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "到户");
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config_list.add(config);

				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("S_NAME"));
				config1.put("group", "到村");
				config1.put("value", chart_l.get(i).get("S_VALUE1"));
				config_list.add(config1);

			}

			chart_config_trend.put("config", config_list);
			data.put("chart_config_scale", chart_config_trend);

			//List<Map<String, Object>> chart_r = projectManagementDao.projectFunds(params);
			JSONObject chart_config_current = new JSONObject();
			chart_config_current.put("convert_method", "genChartLegendBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			Double cmoneyNum = 0d;// 到村累计资金数
			Double fmoneyNum = 0d;// 到户累计资金数
			Double fmtemp = 0d;
			Double cmtemp = 0d;
			for (int i = 0; i < chart_l.size(); i++) {
				fmtemp = Double.parseDouble(
						chart_l.get(i).get("M_VALUE") == null ? "0" : chart_l.get(i).get("M_VALUE")+"");
				cmtemp = Double.parseDouble(
						chart_l.get(i).get("M_VALUE") == null ? "0" : chart_l.get(i).get("M_VALUE1")+"");
				fmoneyNum = fmoneyNum + fmtemp;
				cmoneyNum = cmoneyNum + cmtemp;
			}
			JSONObject configm2 = new JSONObject();
			configm2.put("name", "累计");
			configm2.put("group", "到户");
			configm2.put("value", fmoneyNum);
			config2_list.add(configm2);
			JSONObject configm3 = new JSONObject();
			configm3.put("name", "累计");
			configm3.put("group", "到村");
			configm3.put("value", cmoneyNum);
			config2_list.add(configm3);
			for (int i = 0; i < chart_l.size(); i++) {
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "到户");
				config.put("value", chart_l.get(i).get("M_VALUE"));
				config2_list.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("M_NAME"));
				config1.put("group", "到村");
				config1.put("value", chart_l.get(i).get("M_VALUE1"));
				config2_list.add(config1);
			}
			chart_config_current.put("config", config2_list);
			data.put("chart_config_money", chart_config_current);

			// 查table数据
			Map<String, Object> table_list_c = null;
			if (!"country".equals(level)) {
				table_list_c = projectManagementDao.projectManagementTotal(params);// 合计
			}
			List<Map<String, Object>> table_list = projectManagementDao.projectManagementCount(params);
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()
					+ "/antiPoverty/dataMonitor/beforeExport.do?export_task_id=projectManagement");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = { "rowspan", "rowspan", "colspan", "colspan", "colspan", "colspan", "colspan" };
			String[] content_arr = { "序号", "行政区域", "项目总数", "完工项目", "进行中项目", "未进行项目" };
			for (int j = 0; j < 6; j++) {
				JSONObject head_1 = new JSONObject();
				if (j == 2 || j == 3 || j == 4) {
					head_1.put(span_arr[j], 3);
				} else {
					head_1.put(span_arr[j], 2);
				}
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = { "数量", "计划资金", "实际资金", "数量", "计划资金", "实际资金", "数量", "计划资金", "实际资金", "数量", "计划资金" };
			for (int k = 0; k < 11; k++) {
				JSONObject head_2 = new JSONObject();
				head_2.put("content", content_arr2[k]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			// 合计
			List<JSONObject> body0 = new ArrayList<JSONObject>();
			if (table_list_c != null) {
				for (int n = 0; n < 13; n++) {
					JSONObject head_content = new JSONObject();
					if (n == 0) {
						head_content.put("content", "-");
					} else {
						head_content.put("content", table_list_c.get("A" + (n + 1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			// table体数据
			for (int m = 0; m < table_list.size(); m++) {
				List<JSONObject> body = new ArrayList<JSONObject>();
				for (int n = 0; n < 13; n++) {
					JSONObject head_content = new JSONObject();
					if (n == 0) {
						head_content.put("content", (m + 1));
					} else {
						head_content.put("content", table_list.get(m).get("A" + (n + 1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			table.put("notes", "备注：上表“数量“单位为个，“计划资金”、“实际资金”单位为万元。");
			data.put("table", table);
			json.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询项目监控管理信息：" + e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");
		}
		// System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}

	/**
	 * 项目监控分析--全部
	 * 
	 * @param id
	 *            区域ID
	 * @param level
	 *            区域层级
	 * @param year
	 *            年
	 * @return
	 */
	public String getDataMonitorProjectProjectAnalysisAll(HttpServletRequest request, String id, String level,
			String year) {
		log.info("查询项目监控分析信息--全部===========");
		System.out.println("查询项项目监控分析信息--全部===========");
		JSONObject json = new JSONObject();
		try {
			// 查询数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", Short.parseShort(year));
			params.put("smonth", Integer.parseInt(year + "01"));
			params.put("emonth", Integer.parseInt(year + "12"));
			Map<String, String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String[]> entry = entries.next();
				params.put(entry.getKey(), entry.getValue()[0]);
				// System.out.println("key:="+entry.getKey()+"
				// value:="+entry.getValue()[0]);
			}
			// 用于导出的参数
			getParamsPool().put("projectAnalysis", params);

			json.put("code", 0);
			json.put("msg", "获取信息成功");

			JSONObject data = new JSONObject();
			// 查图表数据
			List<Map<String, Object>> chart_l = projectManagementDao.projectAnalysisFundsTrends(params);
			JSONObject chart_config_average_income_trend = new JSONObject();
			chart_config_average_income_trend.put("convert_method", "genChartLineBarsConfig");
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			List<JSONObject> expand_config_list = new ArrayList<JSONObject>();
			int l_size = chart_l.size();
			int k = 0;
			if (l_size > 5)
				k = l_size - 5;
			else
				k = 0;
			for (int i = k; i < chart_l.size(); i++) {
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("S_NAME"));
				config1.put("group", "项目数");
				config1.put("value", chart_l.get(i).get("S_VALUE1"));
				config1.put("type", "bar");
				config_list.add(config1);
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "资金数");
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config.put("type", "line");
				config_list.add(config);
			}
			for (int i = 0; i < chart_l.size(); i++) {
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("S_NAME"));
				config1.put("group", "项目数");
				config1.put("value", chart_l.get(i).get("S_VALUE1"));
				config1.put("type", "bar");
				expand_config_list.add(config1);
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "资金数");
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config.put("type", "line");
				expand_config_list.add(config);

			}

			chart_config_average_income_trend.put("config", config_list);
			// 扩展
			chart_config_average_income_trend.put("expand_convert_method", "genChartLineBarsConfig");
			chart_config_average_income_trend.put("expand_config", expand_config_list);
			data.put("chart_config_money_trend", chart_config_average_income_trend);

			List<Map<String, Object>> chart_r = projectManagementDao.projectAnalysisCountry(params);
			JSONObject chart_config_income_area_distribution = new JSONObject();
			chart_config_income_area_distribution.put("convert_method", "genChartLineBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			for (int i = 0; i < chart_r.size(); i++) {
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", "项目数");
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config.put("type", "bar");
				config2_list.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_r.get(i).get("S_NAME"));
				config1.put("group", "资金数");
				config1.put("value", chart_r.get(i).get("S_VALUE1"));
				config1.put("type", "line");
				config2_list.add(config1);
			}
			chart_config_income_area_distribution.put("config", config2_list);
			data.put("chart_config_project_distribution", chart_config_income_area_distribution);

			// 查table数据
			Map<String, Object> table_list_c = null;
			if (!"country".equals(level)) {
				table_list_c = projectManagementDao.projectAnalysisTotal(params);// 合计
			}
			List<Map<String, Object>> table_list = projectManagementDao.projectAnalysisCount(params);
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()
					+ "/antiPoverty/dataMonitor/beforeExport.do?export_task_id=projectAnalysis");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = { "rowspan", "rowspan", "colspan", "colspan", "colspan" };
			String[] content_arr = { "序号", "行政区域", "总计", "户项目", "村项目" };
			for (int j = 0; j < 5; j++) {
				JSONObject head_1 = new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = { "数量", "金额", "数量", "金额", "数量", "金额" };
			for (int m = 0; m < 6; m++) {
				JSONObject head_2 = new JSONObject();
				head_2.put("content", content_arr2[m]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			// 合计
			List<JSONObject> body0 = new ArrayList<JSONObject>();
			if (table_list_c != null) {
				for (int n = 0; n < 8; n++) {
					JSONObject head_content = new JSONObject();
					if (n == 0) {
						head_content.put("content", "-");
					} else {
						head_content.put("content", table_list_c.get("A" + (n + 1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			// table体数据
			for (int m = 0; m < table_list.size(); m++) {
				List<JSONObject> body = new ArrayList<JSONObject>();
				for (int n = 0; n < 8; n++) {
					JSONObject head_content = new JSONObject();
					if (n == 0) {
						head_content.put("content", (m + 1));
					} else {
						head_content.put("content", table_list.get(m).get("A" + (n + 1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			table.put("notes", "备注：上表“数量“单位为个，“计划资金”、“实际资金”单位为万元。");
			data.put("table", table);

			json.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询项项目监控分析信息--全部：" + e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");
		}
		// System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}

	/**
	 * 项目监控分析--到村
	 * 
	 * @param id
	 *            区域ID
	 * @param level
	 *            区域层级
	 * @param year
	 *            年
	 * @return
	 */
	public String getDataMonitorProjectProjectAnalysisCountry(HttpServletRequest request, String id, String level,
			String year) {
		log.info("查询项目监控分析信息--到村===========");
		System.out.println("查询项项目监控分析信息--到村===========");
		JSONObject json = new JSONObject();
		try {
			// 查询数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", Short.parseShort(year));
			params.put("smonth", Integer.parseInt(year + "01"));
			params.put("emonth", Integer.parseInt(year + "12"));
			Map<String, String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String[]> entry = entries.next();
				params.put(entry.getKey(), entry.getValue()[0]);
				// System.out.println("key:="+entry.getKey()+"
				// value:="+entry.getValue()[0]);
			}
			// 用于导出的参数
			getParamsPool().put("projectAnalysisToCountry", params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");

			JSONObject data = new JSONObject();

			// 查图表数据
			List<Map<String, Object>> chart_l = projectManagementDao.projectAnalysisFundsTrends(params);
			JSONObject chart_config_average_income_trend = new JSONObject();
			chart_config_average_income_trend.put("convert_method", "genChartLineBarsConfig");
			List<JSONObject> expand_config_list = new ArrayList<JSONObject>();
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			int l_size = chart_l.size();
			int k = 0;
			if (l_size > 5)
				k = l_size - 5;
			else
				k = 0;
			for (int i = k; i < chart_l.size(); i++) {
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("S_NAME"));
				config1.put("group", "项目数");
				config1.put("value", chart_l.get(i).get("S_VALUE1"));
				config1.put("type", "bar");
				config_list.add(config1);

				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "资金数");
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config.put("type", "line");
				config_list.add(config);
			}
			for (int i = 0; i < chart_l.size(); i++) {
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("S_NAME"));
				config1.put("group", "项目数");
				config1.put("value", chart_l.get(i).get("S_VALUE1"));
				config1.put("type", "bar");
				expand_config_list.add(config1);
				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "资金数");
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config.put("type", "line");
				expand_config_list.add(config);

			}

			chart_config_average_income_trend.put("config", config_list);
			// 扩展
			chart_config_average_income_trend.put("expand_convert_method", "genChartLineBarsConfig");
			chart_config_average_income_trend.put("expand_config", expand_config_list);
			data.put("chart_config_money_trend", chart_config_average_income_trend);

			List<Map<String, Object>> chart_r = projectManagementDao.projectAnalysisMoneyToCountry(params);
			JSONObject chart_config_income_area_distribution = new JSONObject();
			chart_config_income_area_distribution.put("convert_method", "genChartLineBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			List<JSONObject> expand_config2_list = new ArrayList<JSONObject>();
			for (int i = 0; i < chart_r.size(); i++) {
				if (i < 5) {
					JSONObject config = new JSONObject();
					config.put("name", chart_r.get(i).get("S_NAME"));
					config.put("group", "项目数");
					config.put("value", chart_r.get(i).get("S_VALUE"));
					config.put("type", "bar");
					config2_list.add(config);
					JSONObject config1 = new JSONObject();
					config1.put("name", chart_r.get(i).get("S_NAME"));
					config1.put("group", "资金数");
					config1.put("value", chart_r.get(i).get("S_VALUE1"));
					config1.put("type", "line");
					config2_list.add(config1);
				}
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", "项目数");
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config.put("type", "bar");
				expand_config2_list.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_r.get(i).get("S_NAME"));
				config1.put("group", "资金数");
				config1.put("value", chart_r.get(i).get("S_VALUE1"));
				config1.put("type", "line");
				expand_config2_list.add(config1);

			}
			chart_config_income_area_distribution.put("config", config2_list);
			// 扩展
			chart_config_income_area_distribution.put("expand_convert_method", "genChartLineBarsConfig");
			chart_config_income_area_distribution.put("expand_config", expand_config2_list);
			data.put("chart_config_money_distribution", chart_config_income_area_distribution);

			// 查table数据
			Map<String, Object> table_list_c = null;
			if (!"country".equals(level)) {
				table_list_c = projectManagementDao.projectAnalysisToCountryTotal(params);// 合计
			}
			List<Map<String, Object>> table_list = projectManagementDao.projectAnalysisToCountryCount(params);
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()
					+ "/antiPoverty/dataMonitor/beforeExport.do?export_task_id=projectAnalysisToCountry");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = { "rowspan", "rowspan", "colspan", "colspan", "colspan", "colspan", "colspan",
					"colspan", "colspan", "colspan", "colspan", "colspan" };
			String[] content_arr = { "序号", "行政区域", "合计", "村道<br>建设", "饮水<br>工程", "文体<br>设施", "卫生<br>设施", "路灯<br>安装",
					"农田<br>水利", "公共设施", "集体经济", "教育教学" };
			for (int j = 0; j < 12; j++) {
				JSONObject head_1 = new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = { "数量", "金额", "数量", "金额", "数量", "金额", "数量", "金额", "数量", "金额", "数量", "金额", "数量",
					"金额", "数量", "金额", "数量", "金额", "数量", "金额" };
			for (int m = 0; m < 20; m++) {
				JSONObject head_2 = new JSONObject();
				head_2.put("content", content_arr2[m]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			// 合计
			List<JSONObject> body0 = new ArrayList<JSONObject>();
			if (table_list_c != null) {
				for (int n = 0; n < 22; n++) {
					JSONObject head_content = new JSONObject();
					if (n == 0) {
						head_content.put("content", "-");
					} else {
						head_content.put("content", table_list_c.get("A" + (n + 1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			// table体数据
			for (int m = 0; m < table_list.size(); m++) {
				List<JSONObject> body = new ArrayList<JSONObject>();
				for (int n = 0; n < 22; n++) {
					JSONObject head_content = new JSONObject();
					if (n == 0) {
						head_content.put("content", (m + 1));
					} else {
						head_content.put("content", table_list.get(m).get("A" + (n + 1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			table.put("notes", "备注：上表“数量“单位为个，“计划资金”、“实际资金”单位为万元。");
			data.put("table", table);

			json.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询项项目监控分析信息--到村：" + e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");
		}
		// System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}

	/**
	 * 项目监控分析--到户
	 * 
	 * @param id
	 *            区域ID
	 * @param level
	 *            区域层级
	 * @param year
	 *            年
	 * @return
	 */
	public String getDataMonitorProjectProjectAnalysisFamily(HttpServletRequest request, String id, String level,
			String year) {
		log.info("查询项目监控分析信息--到户===========");
		System.out.println("查询项项目监控分析信息--到户===========");
		JSONObject json = new JSONObject();
		try {
			// 查询数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.putAll(Constants.dateContants);
			params.put("id", id);
			params.put("level", level);
			params.put("year", Short.parseShort(year));
			params.put("smonth", Integer.parseInt(year + "01"));
			params.put("emonth", Integer.parseInt(year + "12"));
			Map<String, String[]> paramMap = request.getParameterMap();
			Iterator<Map.Entry<String, String[]>> entries = paramMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String[]> entry = entries.next();
				params.put(entry.getKey(), entry.getValue()[0]);
				// System.out.println("key:="+entry.getKey()+"
				// value:="+entry.getValue()[0]);
			}
			// 用于导出的参数
			getParamsPool().put("projectAnalysisToFamily", params);
			json.put("code", 0);
			json.put("msg", "获取信息成功");

			JSONObject data = new JSONObject();

			// 查图表数据
			List<Map<String, Object>> chart_l = projectManagementDao.projectAnalysisFundsTrends(params);
			JSONObject chart_config_average_income_trend = new JSONObject();
			chart_config_average_income_trend.put("convert_method", "genChartLineBarsConfig");
			List<JSONObject> expand_config_list = new ArrayList<JSONObject>();
			List<JSONObject> config_list = new ArrayList<JSONObject>();
			int l_size = chart_l.size();
			int k = 0;
			if (l_size > 5)
				k = l_size - 5;
			else
				k = 0;
			for (int i = k; i < chart_l.size(); i++) {
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("S_NAME"));
				config1.put("group", "项目数");
				config1.put("value", chart_l.get(i).get("S_VALUE1"));
				config1.put("type", "bar");
				config_list.add(config1);

				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "资金数");
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config.put("type", "line");
				config_list.add(config);
			}
			for (int i = 0; i < chart_l.size(); i++) {
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_l.get(i).get("S_NAME"));
				config1.put("group", "项目数");
				config1.put("value", chart_l.get(i).get("S_VALUE1"));
				config1.put("type", "bar");
				expand_config_list.add(config1);

				JSONObject config = new JSONObject();
				config.put("name", chart_l.get(i).get("S_NAME"));
				config.put("group", "资金数");
				config.put("value", chart_l.get(i).get("S_VALUE"));
				config.put("type", "line");
				expand_config_list.add(config);

			}

			chart_config_average_income_trend.put("config", config_list);
			// 扩展
			chart_config_average_income_trend.put("expand_convert_method", "genChartLineBarsConfig");
			chart_config_average_income_trend.put("expand_config", expand_config_list);
			data.put("chart_config_money_trend", chart_config_average_income_trend);

			List<Map<String, Object>> chart_r = projectManagementDao.projectAnalysisMoneyToCountry(params);// 和到村用同一个查询
			JSONObject chart_config_income_area_distribution = new JSONObject();
			chart_config_income_area_distribution.put("convert_method", "genChartLineBarsConfig");
			List<JSONObject> config2_list = new ArrayList<JSONObject>();
			List<JSONObject> expand_config2_list = new ArrayList<JSONObject>();
			for (int i = 0; i < chart_r.size(); i++) {
				if (i < 5) {
					JSONObject config = new JSONObject();
					config.put("name", chart_r.get(i).get("S_NAME"));
					config.put("group", "项目数");
					config.put("value", chart_r.get(i).get("S_VALUE"));
					config.put("type", "bar");
					config2_list.add(config);
					JSONObject config1 = new JSONObject();
					config1.put("name", chart_r.get(i).get("S_NAME"));
					config1.put("group", "资金数");
					config1.put("value", chart_r.get(i).get("S_VALUE1"));
					config1.put("type", "line");
					config2_list.add(config1);
				}
				JSONObject config = new JSONObject();
				config.put("name", chart_r.get(i).get("S_NAME"));
				config.put("group", "项目数");
				config.put("value", chart_r.get(i).get("S_VALUE"));
				config.put("type", "bar");
				expand_config2_list.add(config);
				JSONObject config1 = new JSONObject();
				config1.put("name", chart_r.get(i).get("S_NAME"));
				config1.put("group", "资金数");
				config1.put("value", chart_r.get(i).get("S_VALUE1"));
				config1.put("type", "line");
				expand_config2_list.add(config1);

			}
			chart_config_income_area_distribution.put("config", config2_list);
			// 扩展
			chart_config_income_area_distribution.put("expand_convert_method", "genChartLineBarsConfig");
			chart_config_income_area_distribution.put("expand_config", expand_config2_list);
			data.put("chart_config_money_distribution", chart_config_income_area_distribution);

			// 查table数据
			Map<String, Object> table_list_c = null;
			if (!"country".equals(level)) {
				table_list_c = projectManagementDao.projectAnalysisToFamilyTotal(params);// 合计
			}
			List<Map<String, Object>> table_list = projectManagementDao.projectAnalysisToFamilyCount(params);
			JSONObject table = new JSONObject();
			table.put("title", "统计表");
			table.put("export_url", request.getContextPath()
					+ "/antiPoverty/dataMonitor/beforeExport.do?export_task_id=projectAnalysisToFamily");
			List<List<JSONObject>> head_list = new ArrayList<List<JSONObject>>();
			List<JSONObject> head1 = new ArrayList<JSONObject>();
			String[] span_arr = { "rowspan", "rowspan", "colspan", "colspan", "colspan", "colspan", "colspan",
					"colspan", "colspan", "colspan", "colspan", "colspan" };
			String[] content_arr = { "序号", "行政区域", "合计", "产业<br>扶贫", "金融<br>扶贫", "住房<br>改造", "资产<br>扶贫", "慰问<br>扶贫",
					"就业扶贫", "技能培训", "教育扶贫", "政策补贴+<br>社会保障" };
			for (int j = 0; j < 12; j++) {
				JSONObject head_1 = new JSONObject();
				head_1.put(span_arr[j], 2);
				head_1.put("content", content_arr[j]);
				head1.add(head_1);
			}
			head_list.add(head1);
			List<JSONObject> head2 = new ArrayList<JSONObject>();
			String[] content_arr2 = { "数量", "金额", "数量", "金额", "数量", "金额", "数量", "金额", "数量", "金额", "数量", "金额", "数量",
					"金额", "数量", "金额", "数量", "金额", "数量", "金额" };
			for (int m = 0; m < 20; m++) {
				JSONObject head_2 = new JSONObject();
				head_2.put("content", content_arr2[m]);
				head2.add(head_2);
			}
			head_list.add(head2);
			table.put("head", head_list);
			List<List<JSONObject>> body_list = new ArrayList<List<JSONObject>>();
			// 合计
			List<JSONObject> body0 = new ArrayList<JSONObject>();
			if (table_list_c != null) {
				for (int n = 0; n < 22; n++) {
					JSONObject head_content = new JSONObject();
					if (n == 0) {
						head_content.put("content", "-");
					} else {
						head_content.put("content", table_list_c.get("A" + (n + 1)));
					}
					body0.add(head_content);
				}
				body_list.add(body0);
			}
			// table体数据
			for (int m = 0; m < table_list.size(); m++) {
				List<JSONObject> body = new ArrayList<JSONObject>();
				for (int n = 0; n < 22; n++) {
					JSONObject head_content = new JSONObject();
					if (n == 0) {
						head_content.put("content", (m + 1));
					} else {
						head_content.put("content", table_list.get(m).get("A" + (n + 1)));
					}
					body.add(head_content);
				}
				body_list.add(body);
			}
			table.put("body", body_list);
			table.put("notes", "备注：上表“数量“单位为个，“计划资金”、“实际资金”单位为万元。");
			data.put("table", table);

			json.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询项项目监控分析信息--到户：" + e.getMessage());
			json.put("code", 1);
			json.put("msg", "数据处理异常，获取信息失败!");
		}
		// System.out.println(JsonFormatUtils.JsonFormat(json.toString()));
		return json.toString();
	}

}
