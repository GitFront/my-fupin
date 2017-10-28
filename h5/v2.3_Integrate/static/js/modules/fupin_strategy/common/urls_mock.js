//地址路径
var URLS = {
	//模板
	//村户档案模板
	URL_TPL_POOR_FILE: '../../../static/js/modules/fupin_strategy/tpl/file_tpl.tpl',
	//数据分析模板
	URL_TPL_ANALYSE: '../../../static/js/modules/fupin_strategy/tpl/analyse_tpl.tpl',
	//数据分析中的监控模板
	URL_TPL_DATA_MONITOR: '../../../static/js/modules/fupin_strategy/tpl/data_monitor.tpl',


	//接口
	//登录后的首页消息弹窗
	URL_LOGIN_INDEX_MSG: '../../../data/data/login_index_msg.json',
	//贫困户档案
	//贫困户档案基础信息
	URL_FAMILY_FILE_BASIC: '../../../data/poor_file/family_file_basic.json',
	//贫困户档案家庭成员
	URL_FAMILY_FILE_MEMBERS: '../../../data/poor_file/family_file_members.json',
	//贫困户档案生产生活条件
	URL_FAMILY_FILE_CONDITION: '../../../data/poor_file/family_file_condition.json',
	//贫困户档案帮扶计划
	URL_FAMILY_FILE_PLAN: '../../../data/poor_file/family_file_plan.json',
	//贫困户档案帮扶施策
	URL_FAMILY_FILE_IMPLEMENT: '../../../data/poor_file/family_file_implement.json',
	//贫困户档案帮扶动态
	URL_FAMILY_FILE_NEWS: '../../../data/poor_file/family_file_news.json',
	//贫困户档案帮扶动态详情
	URL_FAMILY_FILE_NEWS_DETAIL: '../../../data/poor_file/family_file_news_detail.json',
	//贫困户档案可支配收入
	URL_FAMILY_FILE_INCOME: '../../../data/poor_file/family_file_income.json',
	//贫困户档案可支配收入统计表
	URL_FAMILY_FILE_INCOME_TABLE: '../../../data/poor_file/family_file_income_table.json',
	//贫困户档案脱贫轨迹
	URL_FAMILY_FILE_ELIMINATE_PATH: '../../../data/poor_file/family_file_eliminate_path.json',
	//贫困户档案脱贫轨迹得分
	URL_FAMILY_FILE_ELIMINATE_SCORES: '../../../data/poor_file/family_file_eliminate_scores.json',
	//贫困户档案资金投入
	URL_FAMILY_FILE_INVESTED: '../../../data/poor_file/family_file_invested.json',
	//贫困户档案数据轨迹
	URL_FAMILY_FILE_DATA_PATH: '../../../data/poor_file/family_file_data_path.json',
	//贫困户档案业务办理
	URL_FAMILY_FILE_BUSINESS: '../../../data/poor_file/family_file_business.json',
	//贫困户档案业务办理剩余条目
	URL_FAMILY_FILE_BUSINESS_REST: '../../../data/poor_file/file_business_rest.json',

	//村档案
	//村档案资金投入
	URL_COUNTRY_FILE_INVESTED: '../../../data/poor_file/country_file_invested.json',
	//村档案脱贫轨迹
	URL_COUNTRY_FILE_ELIMINATE_PATH: '../../../data/poor_file/country_file_eliminate_path.json',
	//村档案脱贫轨迹得分
	URL_COUNTRY_FILE_ELIMINATE_SCORES: '../../../data/poor_file/country_file_eliminate_scores.json',
	//村档案数据轨迹
	URL_COUNTRY_FILE_DATA_PATH: '../../../data/poor_file/country_file_data_path.json',
	//村档案基础信息
	URL_COUNTRY_FILE_BASIC: '../../../data/poor_file/country_file_basic.json',
	//村档案驻村动态
	URL_COUNTRY_FILE_NEWS: '../../../data/poor_file/country_file_news.json',
	//村档案驻村动态详情
	URL_COUNTRY_FILE_NEWS_DETAIL: '../../../data/poor_file/country_file_news_detail.json',
	//村档案会议动态
	URL_COUNTRY_FILE_MEETING_NEWS: '../../../data/poor_file/country_file_meeting_news.json',
	//村档案会议动态详情
	URL_COUNTRY_FILE_MEETING_NEWS_DETAIL: '../../../data/poor_file/country_file_meeting_news_detail.json',
	//村档案发展现状
	URL_COUNTRY_FILE_DEVELOPMENT_STATUS: '../../../data/poor_file/country_file_development_status.json',
	//村档案基本情况
	URL_COUNTRY_FILE_STATUS: '../../../data/poor_file/country_file_status.json',
	//村档案业务办理
	URL_COUNTRY_FILE_BUSINESS: '../../../data/poor_file/country_file_business.json',
	//村档案帮扶计划
	URL_COUNTRY_FILE_PLAN: '../../../data/poor_file/country_file_plan.json',
	//村档案帮扶施策
	URL_COUNTRY_FILE_IMPLEMENT: '../../../data/poor_file/country_file_implement.json',
	//村档案业务办理剩余条目
	URL_COUNTRY_FILE_BUSINESS_REST: '../../../data/poor_file/file_business_rest.json',

	//数据监控
	//数据监控静态数据
	URL_DATA_STATIC_DATA: '../../../data/data/static_data.json',
	//数据监控动态数据
	URL_DATA_DYNAMIC_INDEX: '../../../data/data/dynamic_index.json',
	//贫困户列表
	URL_DATA_POOR_FAMILY_LIST: '../../../data/data/poor_family_list.json',
	/*导出结果查询*/
	URL_DATA_CHECK_EXPORT_RESULT: '../../../data/data_monitor/check_export_result.json',
	
	//======数据分析各监控弹窗======
	/*数据监控分析窗基础信息*/
	URL_DATA_MONITOR_BASIC_INFO: '../../../data/data_monitor/data_monitor_basic_info.json',
	/*数据监控分析窗头部下载表格列表*/
	URL_DATA_MONITOR_HEAD_DOWNLOAD_TABLE_LIST: '../../../data/data_monitor/data_monitor_head_download_table_list.json',
	/*数据监控分析窗树*/
	URL_DATA_MONITOR_TREE: '../../../data/data_monitor/data_monitor_tree.json',
	/*文件号详情树*/
	URL_DATA_MONITOR_FILE_DETAIL_TREE: '../../../data/data_monitor/data_monitor_tree.json',
	/*数据监控分析窗树异步加载子树*/
	URL_DATA_MONITOR_SUBTREE: '../../../data/data_monitor/data_monitor_subtree.json',
	/*文件号详情树-异步加载子树*/
	URL_DATA_MONITOR_FILE_DETAIL_TREE_SUBTREE: '../../../data/data_monitor/data_monitor_subtree.json',
	/*数据监控内容基础信息*/
	URL_DATA_MONITOR_CONTENT_BASIC_INFO: '../../../data/data_monitor/data_monitor_content_basic_info.json',
	/*数据监控窗口排序页面基础信息*/
	URL_DATA_MONITOR_ORDER_BASIC_INFO: '../../../data/data_monitor/data_monitor_order_basic_info.json',
	/*数据监控窗口排序页面表格*/
	URL_DATA_MONITOR_ORDER_TABLE: '../../../data/data_monitor/data_monitor_order_table.json',
	/*数据监控窗口详细清单弹窗基础信息*/
	URL_DATA_MONITOR_DETAIL_BASIC_INFO: '../../../data/data_monitor/data_monitor_detail_basic_info.json',
	/*数据监控窗口详细清单弹窗参数信息*/
	URL_DATA_MONITOR_DETAIL_PARAMS_INFO: '../../../data/data_monitor/data_monitor_detail_params_info.json',
	/*数据监控窗口详细清单弹窗表格*/
	URL_DATA_MONITOR_DETAIL_TABLE: '../../../data/data_monitor/data_monitor_detail_table.json',
	/*数据监控窗口区域数据弹窗表格*/
	URL_DATA_MONITOR_AREA_DATA_TABLE: '../../../data/data_monitor/data_monitor_area_data_table.json',
	

	//扶贫监控变动管理
	URL_DATA_MONITOR_POOR_CHANGE_MANAGEMENT: '../../../data/data_monitor/data_monitor_poor_change_management.json',
	//扶贫监控变动分析-状态属性-累计贫困户
	URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_ALL: '../../../data/data_monitor/data_monitor_poor_change_analysis_all.json',
	//扶贫监控变动分析-状态属性-当前贫困户
	URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_CUR: '../../../data/data_monitor/data_monitor_poor_change_analysis_cur.json',
	//扶贫监控变动分析-状态属性-新增贫困户
	URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_ADD: '../../../data/data_monitor/data_monitor_poor_change_analysis_add.json',
	//扶贫监控变动分析-状态属性-终止贫困户 
	URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_END: '../../../data/data_monitor/data_monitor_poor_change_analysis_end.json',
	//扶贫监控变动分析-状态属性-自然增减
	URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_NATURAL_CHANGE: '../../../data/data_monitor/data_monitor_poor_change_analysis_natural_change.json',
	//扶贫监控变动分析变动详情表
	URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_TABLE: '../../../data/data_monitor/data_monitor_poor_change_analysis_table.json',
	//扶贫监控变动分析变动详细清单基础信息
	URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_DETAIL_BASIC_INFO: '../../../data/data_monitor/data_monitor_poor_change_analysis_detail_basic_info.json',
	//扶贫监控变动分析变动详细清单表格
	URL_DATA_MONITOR_POOR_CHANGE_ANALYSIS_DETAIL_TABLE: '../../../data/data_monitor/data_monitor_poor_change_analysis_detail_table.json',

	//脱贫成效监控-计划管理
	URL_DATA_MONITOR_RESULT_PLAN_MANAGEMENT: '../../../data/data_monitor/data_monitor_poor_result_plan_management.json',
	//脱贫成效监控-成效分析  
	URL_DATA_MONITOR_RESULT_RESULT_ANALYSIS: '../../../data/data_monitor/data_monitor_poor_result_result_analysis.json',
	//脱贫成效监控-未脱贫分析
	URL_DATA_MONITOR_RESULT_NOT_SUCCESS_ANALYSIS: '../../../data/data_monitor/data_monitor_poor_result_not_success_analysis.json',

	//人均可支配收入监控-收入管理
	URL_DATA_MONITOR_AVERAGE_INCOME_INCOME_MANAGEMENT: '../../../data/data_monitor/data_monitor_average_income_income_management.json',
	//人均可支配收入监控-脱贫户分析  
	URL_DATA_MONITOR_AVERAGE_INCOME_SUCCESS_ANALYSIS: '../../../data/data_monitor/data_monitor_average_income_success_analysis.json',
	//人均可支配收入监控-脱贫户分析-转移性支出分析
	URL_DATA_MONITOR_AVERAGE_INCOME_SUCCESS_ANALYSIS_TRANSFERRED_PAYMENT: '../../../data/data_monitor/data_monitor_average_income_success_analysis_transferred_payment.json',
	//人均可支配收入监控-脱贫户分析-转移性收入分析
	URL_DATA_MONITOR_AVERAGE_INCOME_SUCCESS_ANALYSIS_TRANSFERRED_INCOME: '../../../data/data_monitor/data_monitor_average_income_success_analysis_transferred_income.json',
	//人均可支配收入监控-贫困户分析  
	URL_DATA_MONITOR_AVERAGE_INCOME_POOR_ANALYSIS: '../../../data/data_monitor/data_monitor_average_income_poor_analysis.json',
	//人均可支配收入监控-贫困户分析-转移性支出分析
	URL_DATA_MONITOR_AVERAGE_INCOME_POOR_ANALYSIS_TRANSFERRED_PAYMENT: '../../../data/data_monitor/data_monitor_average_income_poor_analysis_transferred_payment.json',
	//人均可支配收入监控-贫困户分析-转移性收入分析  
	URL_DATA_MONITOR_AVERAGE_INCOME_POOR_ANALYSIS_TRANSFERRED_INCOME: '../../../data/data_monitor/data_monitor_average_income_poor_analysis_transferred_income.json',


	
	//低五保政策落实监控
	//低五保政策落实监控落实管理
	URL_DATA_MONITOR_FIVE_LOW_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_five_low_implement_management.json',
	//低五保政策落实监控落实分析
	URL_DATA_MONITOR_FIVE_LOW_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_five_low_implement_analysis.json',

	//教育政策落实监控
	//教育政策落实监控落实管理
	URL_DATA_MONITOR_EDU_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_edu_implement_analysis.json',
	//教育政策落实监控落实分析
	URL_DATA_MONITOR_EDU_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_edu_implement_management.json',

	//医疗政策落实监控
	//医疗政策落实监控落实管理
	URL_MONITOR_MEDICAL_POLICY_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_medical_policy_implement_management.json',
	//医疗政策落实监控落实分析
	URL_DATA_MONITOR_MEDICAL_POLICY_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_medical_policy_implement_analysis.json',

	//住房政策落实监控
	//住房政策落实监控落实管理
	URL_DATA_MONITOR_HOUSE_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_house_implement_analysis.json',
	//住房政策落实监控落实分析
	URL_DATA_MONITOR_HOUSE_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_house_implement_management.json',

	/**/
	//道路硬底化监控
	//道路硬底化监控-落实管理
	URL_DATA_MONITOR_ROAD_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_road_implement_management.json',
	//道路硬底化监控-落实分析
	URL_DATA_MONITOR_ROAD_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_road_implement_analysis.json',

	//安全饮水监控
	//安全饮水监控-落实管理
	URL_DATA_MONITOR_WATER_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_water_implement_management.json',
	//安全饮水监控-落实分析
	URL_DATA_MONITOR_WATER_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_water_implement_analysis.json',

	//生活用电监控
	//生活用电监控-落实管理
	URL_DATA_MONITOR_ELECTRICITY_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_electricity_implement_management.json',
	//生活用电监控-落实分析
	URL_DATA_MONITOR_ELECTRICITY_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_electricity_implement_analysis.json',

	// 医疗设施监控
	// 医疗设施监控-落实管理
	URL_DATA_MONITOR_MEDICAL_FACILITY_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_medical_facility_implement_management.json',
	// 医疗设施监控-落实分析
	URL_DATA_MONITOR_MEDICAL_FACILITY_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_medical_facility_implement_analysis.json',

	//通广播电视监控
	//通广播电视监控-落实管理
	URL_DATA_MONITOR_BROADCAST_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_broadcast_implement_management.json',
	//通广播电视监控-落实分析
	URL_DATA_MONITOR_BROADCAST_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_broadcast_implement_analysis.json',

	//网络覆盖监控
	//网络覆盖监控-落实管理
	URL_DATA_MONITOR_NET_IMPLEMENT_MANAGEMENT: '../../../data/data_monitor/data_monitor_net_implement_management.json',
	//网络覆盖监控-落实分析
	URL_DATA_MONITOR_NET_IMPLEMENT_ANALYSIS: '../../../data/data_monitor/data_monitor_net_implement_analysis.json',

	//贫困分析
	URL_DATA_MONITOR_POOR_ANALYSIS_POOR_ANALYSIS: '../../../data/data_monitor/data_monitor_poor_analysis_poor_analysis.json',
	
	/*预警监控*/
	//贫困识别监控
	//贫困识别监控-异常监控 
	URL_DATA_MONITOR_ALARMED_POOR_EXCEPTION_MONITOR: '../../../data/data_monitor/data_monitor_alarmed_poor_exception_monitor.json',

	//建档立卡异常记录监控
	//建档立卡异常记录监控-异常监控
	URL_DATA_MONITOR_ALARMED_RECORDS_EXCEPTION_MONITOR: '../../../data/data_monitor/data_monitor_alarmed_records_exception_monitor.json',

	//帮扶资金监控
	//帮扶资金监控-异常监控 
	URL_DATA_MONITOR_ALARMED_MONEY_EXCEPTION_MONITOR: '../../../data/data_monitor/data_monitor_alarmed_money_exception_monitor.json',

	//贫困户走访监控
	//贫困户走访监控-异常监控
	URL_DATA_MONITOR_ALARMED_VISIT_EXCEPTION_MONITOR: '../../../data/data_monitor/data_monitor_alarmed_visit_exception_monitor.json',

	//项目监控
	//项目监控-项目管理
	URL_DATA_MONITOR_PROJECT_PROJECT_MANAGEMENT: '../../../data/data_monitor/data_monitor_project_project_management.json',
	//项目监控-项目分析-全部
	URL_DATA_MONITOR_PROJECT_PROJECT_ANALYSIS_ALL: '../../../data/data_monitor/data_monitor_project_project_analysis_all.json',
	//项目监控-项目分析-户
	URL_DATA_MONITOR_PROJECT_PROJECT_ANALYSIS_FAMILY: '../../../data/data_monitor/data_monitor_project_project_analysis_family.json',
	//项目监控-项目分析-村
	URL_DATA_MONITOR_PROJECT_PROJECT_ANALYSIS_COUNTRY: '../../../data/data_monitor/data_monitor_project_project_analysis_country.json',

	//项目监控户扶贫项目分析
	//项目监控户扶贫项目分析-产业扶贫
	URL_DATA_MONITOR_PROJECT_FAMILY_INDUSTRY: '../../../data/data_monitor/data_monitor_project_family_industry.json',
	//项目监控户扶贫项目分析-金融扶贫
	URL_DATA_MONITOR_PROJECT_FAMILY_FINANCE: '../../../data/data_monitor/data_monitor_project_family_finance.json',
	//项目监控户扶贫项目分析-住房改造
	URL_DATA_MONITOR_PROJECT_FAMILY_HOUSE: '../../../data/data_monitor/data_monitor_project_family_house.json',
	//项目监控户扶贫项目分析-资产扶贫
	URL_DATA_MONITOR_PROJECT_FAMILY_PROPERTY: '../../../data/data_monitor/data_monitor_project_family_property.json',
	//项目监控户扶贫项目分析-慰问扶贫
	URL_DATA_MONITOR_PROJECT_FAMILY_VISIT: '../../../data/data_monitor/data_monitor_project_family_visit.json',
	//项目监控户扶贫项目分析-就业扶贫
	URL_DATA_MONITOR_PROJECT_FAMILY_EMPLOYMENT: '../../../data/data_monitor/data_monitor_project_family_employment.json',
	//项目监控户扶贫项目分析-技能培训
	URL_DATA_MONITOR_PROJECT_FAMILY_SKILL: '../../../data/data_monitor/data_monitor_project_family_skill.json',
	//项目监控户扶贫项目分析-教育扶贫
	URL_DATA_MONITOR_PROJECT_FAMILY_EDU: '../../../data/data_monitor/data_monitor_project_family_edu.json',

	//村扶贫项目分析
	//村扶贫项目分析-村道硬底化
	URL_DATA_MONITOR_PROJECT_COUNTRY_ROAD: '../../../data/data_monitor/data_monitor_project_country_road.json',
	//村扶贫项目分析-饮水工程
	URL_DATA_MONITOR_PROJECT_COUNTRY_WATER: '../../../data/data_monitor/data_monitor_project_country_water.json',
	//村扶贫项目分析-文体设施
	URL_DATA_MONITOR_PROJECT_COUNTRY_RECREATION_SPORT: '../../../data/data_monitor/data_monitor_project_country_recreation_sport.json',
	//村扶贫项目分析-卫生设施
	URL_DATA_MONITOR_PROJECT_COUNTRY_HYGIENE: '../../../data/data_monitor/data_monitor_project_country_hygiene.json',
	//村扶贫项目分析-路灯安装
	URL_DATA_MONITOR_PROJECT_COUNTRY_LAMP: '../../../data/data_monitor/data_monitor_project_country_lamp.json',
	//村扶贫项目分析-农田水利
	URL_DATA_MONITOR_PROJECT_COUNTRY_FARM: '../../../data/data_monitor/data_monitor_project_country_farm.json',
	//村扶贫项目分析-公共设施
	URL_DATA_MONITOR_PROJECT_COUNTRY_PUBLIC_FACILITY: '../../../data/data_monitor/data_monitor_project_country_public_facility.json',
	//村扶贫项目分析-集体经济
	URL_DATA_MONITOR_PROJECT_COUNTRY_COLLECTIVE_ECONOMY: '../../../data/data_monitor/data_monitor_project_country_collective_economy.json',
	//村扶贫项目分析-教育教学
	URL_DATA_MONITOR_PROJECT_COUNTRY_EDU: '../../../data/data_monitor/data_monitor_project_country_edu.json',

	//责任监控
	//责任监控-责任管理
	URL_DATA_MONITOR_DUTY_MANAGEMENT: '../../../data/data_monitor/data_monitor_duty_duty_management.json',
	//责任监控-责任分析
	URL_DATA_MONITOR_DUTY_ANALYSIS: '../../../data/data_monitor/data_monitor_duty_duty_analysis.json',

	//资金监控
	//资金监控-资金管理
	URL_DATA_MONITOR_CAPITAL_MANAGEMENT: '../../../data/data_monitor/data_monitor_capital_capital_management.json',
	//资金监控-资金分析
	URL_DATA_MONITOR_CAPITAL_ANALYSIS: '../../../data/data_monitor/data_monitor_capital_capital_analysis.json',
	//资金监控-查文件号
	URL_DATA_MONITOR_CAPITAL_FILE: '../../../data/data_monitor/data_monitor_capital_capital_file.json',
	//文件号基础信息
	URL_DATA_MONITOR_CAPITAL_FILE_BASIC_INFO: '../../../data/data_monitor/data_monitor_capital_capital_file_basic_info.json',
	//文件号详情表格
	URL_DATA_MONITOR_CAPITAL_FILE_DETAIL_TABLE: '../../../data/data_monitor/data_monitor_capital_capital_file_detail_table.json',
	//======数据分析各监控弹窗======


	//======扶贫服务======
	//扶贫服务-扶贫服务首页
	URL_DATA_POOR_SERVICE_INDEX: '../../../data/poor_service/poor_service_index.json',
	//扶贫服务-扶贫服务优品数据
	URL_DATA_POOR_SERVICE_INDEX_DATA_LINGNAN: '../../../data/poor_service/poor_service_index_data_lingnan.json',
	//扶贫服务-扶贫服务项目基础信息
	URL_DATA_POOR_SERVICE_ITEM_LIST_BASIC_INFO: '../../../data/poor_service/poor_service_item_list_basic_info.json',
	//扶贫服务-扶贫服务项目搜索
	URL_DATA_POOR_SERVICE_ITEM_LIST_SEARCH: '../../../data/poor_service/poor_service_item_list_search.json',

	
	

	//======数据分析各监控弹窗======

	//数据分析行政区域
	URL_DATA_ANALYSE_AREA_PATH: '../../../data/data_analyse/area_path.json',
	//数据分析地区基本信息
	URL_DATA_ANALYSE_AREA_BASIS_INFO: '../../../data/data_analyse/data_analyse_area_basic_info.json',
	//数据分析人均可支配收入分析
	URL_DATA_ANALYSE_AVERAGE_INCOME: '../../../data/data_analyse/data_analyse_average_income.json',
	//数据分析人均可支配收入分析统计表
	URL_DATA_ANALYSE_AVERAGE_INCOME_TABLE: '../../../data/data_analyse/data_analyse_average_income_table.json',
	//数据分析社会保障兜底
	URL_DATA_ANALYSE_SOCIAL_POLICY: '../../../data/data_analyse/data_analyse_social_policy.json',
	//数据分析社会保障兜底统计表
	URL_DATA_ANALYSE_SOCIAL_POLICY_TABLE: '../../../data/data_analyse/data_analyse_social_policy_table.json',
	//数据分析社会保障兜底未落实政策详表
	URL_DATA_ANALYSE_SOCIAL_POLICY_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_social_policy_detail_search.json',
	//数据分析贫困户“三保障”分析义务教育
	URL_DATA_ANALYSE_GUARANTEE_EDU: '../../../data/data_analyse/data_analyse_guarantee_edu.json',
	//数据分析贫困户“三保障”分析义务教育统计表
	URL_DATA_ANALYSE_GUARANTEE_EDU_TABLE: '../../../data/data_analyse/data_analyse_guarantee_edu_table.json',
	//数据分析贫困户“三保障”分析义务教育详表
	URL_DATA_ANALYSE_GUARANTEE_EDU_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_guarantee_edu_detail_search.json',
	//数据分析贫困户“三保障”分析医疗保障
	URL_DATA_ANALYSE_GUARANTEE_MEDICAL: '../../../data/data_analyse/data_analyse_guarantee_medical.json',
	//数据分析贫困户“三保障”分析医疗保障统计表
	URL_DATA_ANALYSE_GUARANTEE_MEDICAL_TABLE: '../../../data/data_analyse/data_analyse_guarantee_medical_table.json',
	//数据分析贫困户“三保障”分析医疗保障详表
	URL_DATA_ANALYSE_GUARANTEE_MEDICAL_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_guarantee_medical_detail_search.json',
	//数据分析贫困户“三保障”分析住房保障
	URL_DATA_ANALYSE_GUARANTEE_HOUSE: '../../../data/data_analyse/data_analyse_guarantee_house.json',
	//数据分析贫困户“三保障”分析住房保障统计表
	URL_DATA_ANALYSE_GUARANTEE_HOUSE_TABLE: '../../../data/data_analyse/data_analyse_guarantee_house_table.json',
	//数据分析贫困户“三保障”分析住房保障详表
	URL_DATA_ANALYSE_GUARANTEE_HOUSE_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_guarantee_house_detail_search.json',
	//数据分析贫困村“一相当”分析道路硬化
	URL_DATA_ANALYSE_CONDITION_ROAD: '../../../data/data_analyse/data_analyse_condition_road.json',
	//数据分析贫困村“一相当”分析道路硬化统计表
	URL_DATA_ANALYSE_CONDITION_ROAD_TABLE: '../../../data/data_analyse/data_analyse_condition_road_table.json',
	//数据分析贫困村“一相当”分析道路硬化详表
	URL_DATA_ANALYSE_CONDITION_ROAD_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_condition_road_detail_search.json',
	//数据分析贫困村“一相当”分析安全饮水
	URL_DATA_ANALYSE_CONDITION_WATER: '../../../data/data_analyse/data_analyse_condition_water.json',
	//数据分析贫困村“一相当”分析安全饮水统计表
	URL_DATA_ANALYSE_CONDITION_WATER_TABLE: '../../../data/data_analyse/data_analyse_condition_water_table.json',
	//数据分析贫困村“一相当”分析安全饮水详表
	URL_DATA_ANALYSE_CONDITION_WATER_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_condition_water_detail_search.json',
	//数据分析贫困村“一相当”分析通电
	URL_DATA_ANALYSE_CONDITION_ELECTRICITY: '../../../data/data_analyse/data_analyse_condition_electricity.json',
	//数据分析贫困村“一相当”分析通电统计表
	URL_DATA_ANALYSE_CONDITION_ELECTRICITY_TABLE: '../../../data/data_analyse/data_analyse_condition_electricity_table.json',
	//数据分析贫困村“一相当”分析通电详表
	URL_DATA_ANALYSE_CONDITION_ELECTRICITY_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_condition_electricity_detail_search.json',
	//数据分析贫困村“一相当”分析卫生站
	URL_DATA_ANALYSE_CONDITION_HEALTH: '../../../data/data_analyse/data_analyse_condition_health.json',
	//数据分析贫困村“一相当”分析卫生站统计表
	URL_DATA_ANALYSE_CONDITION_HEALTH_TABLE: '../../../data/data_analyse/data_analyse_condition_health_table.json',
	//数据分析贫困村“一相当”分析卫生站详表
	URL_DATA_ANALYSE_CONDITION_HEALTH_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_condition_health_detail_search.json',
	//数据分析贫困村“一相当”分析通广播电视
	URL_DATA_ANALYSE_CONDITION_BROADCAST: '../../../data/data_analyse/data_analyse_condition_broadcast.json',
	//数据分析贫困村“一相当”分析通广播电视统计表
	URL_DATA_ANALYSE_CONDITION_BROADCAST_TABLE: '../../../data/data_analyse/data_analyse_condition_broadcast_table.json',
	//数据分析贫困村“一相当”分析通广播电视详表
	URL_DATA_ANALYSE_CONDITION_BROADCAST_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_condition_broadcast_detail_search.json',
	//数据分析贫困村“一相当”分析宽带网络
	URL_DATA_ANALYSE_CONDITION_NET: '../../../data/data_analyse/data_analyse_condition_net.json',
	//数据分析贫困村“一相当”分析宽带网络统计表
	URL_DATA_ANALYSE_CONDITION_NET_TABLE: '../../../data/data_analyse/data_analyse_condition_net_table.json',
	//数据分析贫困村“一相当”分析宽带网络详表
	URL_DATA_ANALYSE_CONDITION_NET_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_condition_net_detail_search.json',
	//数据分析务工状况分析
	URL_DATA_ANALYSE_POOR_LABOR: '../../../data/data_analyse/data_analyse_poor_labor.json',
	//数据分析教育分析
	URL_DATA_ANALYSE_POOR_EDU: '../../../data/data_analyse/data_analyse_poor_edu.json',
	//数据分析致贫原因分析
	URL_DATA_ANALYSE_POOR_REASON: '../../../data/data_analyse/data_analyse_poor_reason.json',
	//数据分析贫困户危房分析
	URL_DATA_ANALYSE_POOR_DANGER_HOUSE: '../../../data/data_analyse/data_analyse_poor_danger_house.json',
	//数据分析贫困户属性分析
	URL_DATA_ANALYSE_POOR_ATTRIBUTE: '../../../data/data_analyse/data_analyse_poor_attribute.json',
	//数据分析健康状况分析
	URL_DATA_ANALYSE_POOR_HEALTH: '../../../data/data_analyse/data_analyse_poor_health.json',
	//数据分析预警监控存在异常的贫困户
	URL_DATA_ANALYSE_ALARMED_FAMILY: '../../../data/data_analyse/data_analyse_alarmed_family.json',
	//数据分析预警监控存在异常的贫困户监控统计表
	URL_DATA_ANALYSE_ALARMED_FAMILY_TABLE: '../../../data/data_analyse/data_analyse_alarmed_family_table.json',
	//数据分析预警监控存在异常的贫困户监控详表
	URL_DATA_ANALYSE_ALARMED_FAMILY_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_alarmed_family_detail_search.json',
	/*数据分析导出部分*/
	//社会保障兜底差异统计表
	URL_DATA_ANALYSE_EXPORT_SOCIAL_POLICY_TABLE: '../../../data/data_analyse/data_analyse_alarmed_family.json',
	//数据分析社会保障兜底未落实政策详表
	URL_DATA_ANALYSE_EXPORT_SOCIAL_POLICY_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_social_policy_detail_search.json',
	//数据分析贫困户“三保障”分析义务教育统计表
	URL_DATA_ANALYSE_EXPORT_GUARANTEE_EDU_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_guarantee_edu_table.json',
	//数据分析贫困户“三保障”分析义务教育详表
	URL_DATA_ANALYSE_EXPORT_GUARANTEE_EDU_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_guarantee_edu_detail_search.json',
	//数据分析贫困户“三保障”分析医疗保障统计表
	URL_DATA_ANALYSE_EXPORT_GUARANTEE_MEDICAL_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_guarantee_medical_table.json',
	//数据分析贫困户“三保障”分析医疗保障详表
	URL_DATA_ANALYSE_EXPORT_GUARANTEE_MEDICAL_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_guarantee_medical_detail_search.json',
	//数据分析贫困户“三保障”分析住房保障统计表
	URL_DATA_ANALYSE_EXPORT_GUARANTEE_HOUSE_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_guarantee_house_table.json',
	//数据分析贫困户“三保障”分析住房保障详表
	URL_DATA_ANALYSE_EXPORT_GUARANTEE_HOUSE_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_guarantee_house_detail_search.json',
	//数据分析贫困村“一相当”分析道路硬化统计表
	URL_DATA_ANALYSE_EXPORT_CONDITION_ROAD_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_condition_road_table.json',
	//数据分析贫困村“一相当”分析道路硬化详表
	URL_DATA_ANALYSE_EXPORT_CONDITION_ROAD_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_condition_road_detail_search.json',
	//数据分析贫困村“一相当”分析安全饮水统计表
	URL_DATA_ANALYSE_EXPORT_CONDITION_WATER_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_condition_water_table.json',
	//数据分析贫困村“一相当”分析安全饮水详表
	URL_DATA_ANALYSE_EXPORT_CONDITION_WATER_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_condition_water_detail_search.json',
	//数据分析贫困村“一相当”分析通电统计表
	URL_DATA_ANALYSE_EXPORT_CONDITION_ELECTRICITY_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_condition_electricity_table.json',
	//数据分析贫困村“一相当”分析通电详表
	URL_DATA_ANALYSE_EXPORT_CONDITION_ELECTRICITY_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_condition_electricity_detail_search.json',
	//数据分析贫困村“一相当”分析卫生站统计表
	URL_DATA_ANALYSE_EXPORT_CONDITION_HEALTH_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_condition_health_table.json',
	//数据分析贫困村“一相当”分析卫生站详表
	URL_DATA_ANALYSE_EXPORT_CONDITION_HEALTH_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_condition_health_detail_search.json',
	//数据分析贫困村“一相当”分析通广播电视统计表
	URL_DATA_ANALYSE_EXPORT_CONDITION_BROADCAST_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_condition_broadcast_table.json',
	//数据分析贫困村“一相当”分析通广播电视详表
	URL_DATA_ANALYSE_EXPORT_CONDITION_BROADCAST_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_condition_broadcast_detail_search.json',
	//数据分析贫困村“一相当”分析宽带网络统计表
	URL_DATA_ANALYSE_EXPORT_CONDITION_NET_TABLE: '../../../data/data_analyse/data_analyse_EXPORT_condition_net_table.json',
	//数据分析贫困村“一相当”分析宽带网络详表
	URL_DATA_ANALYSE_EXPORT_CONDITION_NET_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_EXPORT_condition_net_detail_search.json',
	//数据分析预警监存在异常的贫困户监控统计表
	URL_DATA_ANALYSE_EXPORT_ALARMED_FAMILY_TABLE: '../../../data/data_analyse/data_analyse_alarmed_family_table.json',
	//数据分析预警监存在异常的贫困户监控详表
	URL_DATA_ANALYSE_EXPORT_ALARMED_FAMILY_DETAIL_SEARCH: '../../../data/data_analyse/data_analyse_alarmed_family_table.json',

	//战略地图
	//战略地图静态数据
	URL_STRATEGY_STATIC_DATA: '../../../data/strategy/static_data.json',
	//战略地图动态数据
	URL_STRATEGY_DYNAMIC_INDEX: '../../../data/strategy/dynamic_index.json',

	//东西扶贫协作
	//东西扶贫协作首页
	URL_POOR_EAST_WEST_INDEX: '../../../data/poor_east_west/poor_east_west_index.json',
	
	//搜索
	//预搜索
	URL_SEARCH_PRE_SEARCH: '../../../data/search/pre_search.json',
	//搜索
	URL_SEARCH_SEARCH: '../../../data/search/search/1.json',

	//======示范村======
	/*示范村-首页基础信息*/
	URL_DATA_EXAMPLE_COUNTRY_INDEX_STATIC_DATA: '../../../data/example_country/data_example_country_index_static_data.json',
	/*示范村-示范村图片*/
	URL_DATA_EXAMPLE_COUNTRY_COUNTRY_PHOTO: '../../../data/example_country/data_example_country_country_photo.json',
	/*示范村-示范村建设-整治达标图形*/
	URL_DATA_EXAMPLE_COUNTRY_COUNTRY_RENOVATE_STANDARD_CHART: '../../../data/example_country/data_example_country_country_renovate_standard_chart.json',
	/*示范村-示范村建设-整治达标表格*/
	URL_DATA_EXAMPLE_COUNTRY_COUNTRY_RENOVATE_STANDARD_TABLE: '../../../data/example_country/data_example_country_country_renovate_standard_table.json',
	
	//登录
	//登录
	URL_LOGIN: ''
};
