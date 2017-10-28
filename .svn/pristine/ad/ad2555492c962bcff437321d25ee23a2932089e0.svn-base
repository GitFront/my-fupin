统一接口格式规范：
api.json

参数：
“简易数组”为用英文逗号分隔的值，所有多选都是用此格式

数据：
如接口中有说明“同XX.json定义”，属性值仅取该json的data属性值为该属性的值
所有chart_config属性为echart、highchart、d3等的data_path
data转option/config由后端负责
对于数据监控、责任监控、项目监控里的分析弹窗，chart_config的结构为：
```
{
    //图表的类型(echart、highchart,D3等)，当不传时，默认为echart
    "chart_type": "highchart",
    //供前端调用，为公共转换js设置的方法名。
    //如echartCommon.genBarChart为某图表的转换方法的话，值为"genBarChart"
    "convert_method": "genXxx",
    //图表数据，供转换方法调用
    "config": [],
    //highchart需要的data,或其他data
    "data":{},
    //同convert_method定义，不过它是expand_config的转换方法
    "expand_convert_method": "genXxx",
    //如图表自带“展开”按钮，需要额外附带此属性供展开使用
    "expand_config": []
}


```

kpi命名:
1. 村内村外 cunneicunwai
1. 致贫原因 zhipinyuanyin
1. 贫困属性 pinkunshuxing
1. 住房条件 zhufangtiaojian
1. 劳动力分析 laodonglifenxi
1. 健康情况 jiankangqing
1. 教育文化 jiaoyuwenhua
1. 社保医保 shebaoyibao

层级取值：
1. state：中央
1. province：省
1. city：市
1. county：县
1. town：镇
1. country：村

扶贫搜索类型取值：
1. family：贫困户
1. country：贫困村

区域类型范围取值：
1. 1：全部
1. 2：相对贫困村
1. 3：分散村
1. 4：革命老区
1. 5：中央苏区

数据监控窗口所属类型：  
1. poor：扶贫对象监控
1. result：脱贫成效监控
1. average_income：人均可支配收入监控
1. five_low: 低五保政策落实监控
1. edu: 教育政策落实监控
1. medical_policy: 医疗政策落实监控
1. house: 住房政策落实监控
1. road: 道路硬底化监控
1. water: 安全饮水监控
1. electricity: 生活用电监控
1. medical_facility: 医疗设施监控
1. broadcast: 通广播电视监控
1. net: 网络覆盖监控
1. poor_analysis: 贫困分析
1. duty: 责任监控
1. project: 项目监控
1. project_family: 项目监控户扶贫项目分析
1. project_country: 项目监控村扶贫项目分析
1. alarmed_poor: 预警监控贫困识别监控
1. alarmed_records: 预警监控建档立卡异常记录监控
1. alarmed_money: 预警监控帮扶资金监控
1. alarmed_visit: 预警监控贫困户走访监控
1. capital: 资金监控
1. example_country_building: 示范村建设

数据监控窗口标签页类型：  
1. 扶贫对象监控
    1. change_management：扶贫对象变动管理
    1. change_analysis：扶贫对象变动分析
    1. change_order：扶贫对象变动排序
1. 脱贫成效监控
    1. plan_management：脱贫计划管理
    1. result_analysis：预脱贫分析
    1. not_success_analysis：未脱贫分析
    1. result_order：脱贫成效排序
1. 人均可支配收入监控
    1. income_management：收入管理
    1. success_analysis：预脱贫户分析
    1. success_analysis_transferred_payment：预脱贫户分析转移性支出分析
    1. success_analysis_transferred_income：预脱贫户分析转移性收入分析
    1. poor_analysis：未脱贫户分析
    1. poor_analysis_transferred_payment：未脱贫户分析转移性支出分析
    1. poor_analysis_transferred_income：未脱贫户分析转移性收入分析
    1. income_order：收入排序
1. 低五保政策落实监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 教育政策落实监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 医疗政策落实监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 住房政策落实监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 道路硬底化监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 安全饮水监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 生活用电监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 医疗设施监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 通广播电视监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 网络覆盖监控
    1. implement_management: 落实管理
    1. implement_analysis: 落实分析
    1. implement_order: 落实排序
1. 贫困分析
    1. poor_analysis: 贫困分析
1. 责任监控
    1. duty_management: 责任管理
    1. duty_analysis: 责任分析
1. 项目监控
    1. project_management: 项目管理
    1. project_analysis: 项目分析
    1. project_order: 项目排序
1. 项目监控户扶贫项目分析
    1. industry: 产业扶贫
    1. finance : 金融扶贫
    1. house : 住房改造
    1. property : 资产扶贫
    1. visit: 慰问扶贫
    1. employment: 就业扶贫
    1. skill: 技能培训
    1. edu: 教育扶贫
1. 项目监控村扶贫项目分析
    1. road: 村道硬底化
    1. water: 饮水工程
    1. recreation_sport: 文体设施
    1. hygiene: 卫生设施
    1. lamp: 路灯安装
    1. farm: 农田水利
    1. public_facility: 公共设施
    1. collective_economy: 集体经济
    1. edu: 教育教学
1. 预警监控贫困识别监控
    1. exception_monitor: 异常监控
    1. exception_order: 异常排序
1. 预警监控建档立卡异常记录监控
    1. exception_monitor: 异常监控
    1. exception_order: 异常排序
1. 预警监控帮扶资金监控
    1. money_monitor: 资金监控
    1. money_order: 资金排序
1. 预警监控贫困户走访监控
    1. visit_monitor: 走访监控
    1. visit_order: 走访排序
1. 资金监控
    1. capital_management: 资金管理
    1. capital_analysis: 资金分析
    1. capital_order: 资金排序
    1. capital_file: 查文件号
    (2. capital_file_detail: 文件号详情弹窗，用于渲染树时传的tabtype)
1. 示范村建设
    1. renovate_standard: 整治达标
    1. promote_creation: 提升创建




脱贫成效监控未脱贫分析综合得分取值：
1. domain_1: 综合得分<60分
1. domain_2: 60分≤综合得分<100分
1. domain_3: 综合得分=100

脱贫成效监控未脱贫分析两不愁取值：
1. domain_1: 可支配收入<4000元
1. domain_2: 4000元≤可支配收入<7365元
1. domain_3: 可支配收入≥7365元
1. no_security: 未落实低保/无保政策

三保障取值：
1. edu: 落实教育政策
1. medical_security: 落实医保政策
1. house: 住房达标

数据分析社会保障兜底贫困属性取值：
1. all：全部
1. low：低保户
1. five：五保户
1. no_labor：一般贫困的无劳动能力户
1. normal: 一般贫困户

数据分析劳力类型：
1. all：全部
1. have_labor：有劳动力户
1. no_labor：无劳动力户

数据分析扶贫对象变动分析状态属性：
1. all：累计贫困户
1. poor_cur: 当前贫困户
1. poor_add：新增贫困户
1. poor_end：终止贫困户
1. natural_change：自然增减户

扶贫监控变动分析变动排序表指标取值：
1. accuracy：准确率
1. total_poor_family：贫困户总数
1. total_poor_people：贫困人口总数
1. poor_add：新增贫困户
1. poor_end：终止贫困户
1. poor_merge：并户贫困户
1. poor_split：拆户贫困户
1. poor_remove：销户贫困户
1. natural_change：自然增减户

脱贫成效监控成效排序指标取值：
1. amount_accumulated_success: 累计脱贫数
1. rate_accumulated_success: 累计脱贫率
1. amount_cur_year_plan: 当年计划数
1. amount_cur_year_success: 当年脱贫数
1. rate_cur_year_success: 当年脱贫率
1. amount_monitor_achieve: 监测达标数

教育阶段取值：
1. all: 全部
1. compulsory: 义务教育
1. high_school: 高中（中职）教育

致贫原因取值：
1. disease: 因病
1. disabled: 因残
1. edu: 因学
1. disaster: 因灾
1. land: 缺土地
1. water: 缺水
1. skill: 缺技术
1. labor: 缺劳力 
1. money: 缺资金
1. traffic: 交通落后
1. self_dev: 自身发展
1. other: 其他

行业部门取值：
1. all：全部
1. gongan：公安
1. minzheng：民政
1. canlian：残联
1. gongshang：工商
1. fangguan：房管
1. renshe：财政供养(人社)

贫困识别监控-异常监控-异常类型取值:
1. id_card: 身份证不匹配
1. low_five: 低保五保不匹配
1. disabled_info: 残疾信息不匹配
1. house: 名下有房
1. car: 名下有车
1. i_n_c_info: 有工商注册信息
1. finance: 享有财政供养

建档立卡的异常记录监控异常类型取值：
1. holder_id_miss: 户主身份信息错漏
1. member_id_miss: 成员身份信息错漏
1. disabled_info_miss: 残疾信息错漏
1. id_repeat: 重复身份信息记录

项目监控项目属性取值：
1. all: 全部
1. family: 到户项目
1. country: 到村项目

资金监控项目类型村取值：
1. country_road: 村道硬底化
1. country_water: 饮水工程
1. country_recreation_sport: 文体设施
1. country_hygiene: 卫生设施
1. country_lamp: 路灯安装
1. country_farm: 农田水利
1. country_public_facility: 公共设施
1. country_collective_economy: 集体经济
1. country_edu: 教育教学
1. country_other: 其他

资金监控项目类型户取值：
1. family_industry: 产业扶贫
1. family_finance : 金融扶贫
1. family_house : 住房改造
1. family_property : 资产扶贫
1. family_visit: 慰问扶贫
1. family_employment: 就业扶贫
1. family_skill: 技能培训
1. family_edu: 教育扶贫
1. family_policy: 政策补贴和社会保障

业务办理项目类型取值：
1. list_doing：在办项目
1. list_done：已办项目
1. list_todo：待办项目

扶贫服务项目主体取值：
1. personal: 个人
1. legal_person: 法人

扶贫服务项目状态取值:
1. doing: 在办
1. done: 已办

示范村建设表格状态取值:
1. doing    :   进行中
1. done     :   已完成
1. unstart  :   未启动


注：
1. 数据分析所有搜索页,如果是分散村，所有country_id和family_id返回null.
1. 以上的属性取值可能未必在每个接口全部用上，只是定义使用的常量值，具体请留意低保



接口定义：
1./api/indexMsg 登录后-首页提示消息接口
    type: GET  

    参数： 无 
    返回：login_index_msg.json  

1. /api/kpi //省，市，县，镇，村的kpi数字和top10的数据格式接口  
    type: GET  

    参数：  
    id：查找对应数据的ID  
    level：见层级取值  

    返回：kpi.json  
    当level为村时，没有chart_config_top_poor属性

1. /api/kpi/:kpi_chart_name //省，市，县，镇，村的个kpi图表数据格式接口，其中，kpi_chart_name见上面kpi命名  
    type: GET  

    参数：  
    id：查找对应数据的ID  
    level：见层级取值  

    返回：kpi_chart.json  
    chart_config_tab属性为echart_config json对象

1. /api/family_file/basic //贫困户档案基础信息    
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：family_file_basic.json

1. /api/family_file/members //贫困户档案家庭成员   
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：family_file_members.json

1. /api/family_file/condition //贫困户档案生产生活条件  
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：family_file_condition.json

1. /api/family_file/plan //贫困户档案帮扶计划  
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：family_file_plan.json

1. /api/family_file/implement //贫困户档案帮扶施策  
    type: GET  

    参数：  
    id：查找对应数据的ID  
    year：年份，不传时返回默认年份数据  

    返回：family_file_implement.json

1. /api/family_file/news //贫困户档案帮扶动态  
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：family_file_news.json

1. /api/family_file/news/detail //贫困户档案帮扶动态详情  
    type: GET  

    参数：  
    family_id：户ID  
    news_id：动态的ID  

    返回：family_file_news_detail.json

1. /api/family_file/income //贫困户档案可支配收入  
    type: GET  

    参数：  
    id：户ID  
    year：年份，不传时返回默认年份数据  

    返回：family_file_income.json

1. /api/family_file/income_table //贫困户档案可支配收入统计表  
    type: GET  

    参数：  
    id：户ID  
    year：年份  
    month：月份  

    返回：family_file_income_table.json


1. /api/family_file/eliminate_path //贫困户档案脱贫轨迹  
    type: GET  

    参数：  
    id：查找对应数据的ID  
    year：年份，不传时返回默认年份数据  

    返回：family_file_eliminate_path.json

1. /api/family_file/eliminate_scores //贫困户档案脱贫轨迹得分 
    type: GET  

    参数：  
    id：查找对应数据的ID  
    year：年份  
    month：月份  

    返回：family_file_eliminate_scores.json

1. /api/family_file/invested //贫困户档案资金投入  
    type: GET  

    参数：  
    id：查找对应数据的ID  
    year：年份，不传时返回默认年份数据  

    返回：family_file_invested.json

1. /api/family_file/data_path //贫困户档案数据轨迹  
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：family_file_data_path.json

1. /api/family_file/business //贫困户档案业务办理
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：family_file_business.json

1. /api/family_file/business_rest //贫困户档案业务办理剩余条目
    type: GET  

    参数：  
    id：查找对应数据的ID  
    type: 项目类型，见业务办理项目类型  

    返回：file_business_rest.json

1. /api/country_file/basic //村档案基础信息    
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：country_file_basic.json

1. /api/country_file/status //村档案基本情况    
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：country_file_status.json

1. /api/country_file/development_status //村档案发展现状    
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：country_file_development_status.json

1. /api/country_file/plan //村档案帮扶计划    
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：country_file_plan.json

1. /api/country_file/implement //村档案帮扶施策    
    type: GET  

    参数：  
    id：查找对应数据的ID  
    year：年份，不传时返回默认年份数据  

    返回：country_file_implement.json

1. /api/country_file/news //村档案驻村动态  
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：country_file_news.json

1. /api/country_file/news/detail //村档案驻村动态详情  
    type: GET  

    参数：  
    country_id：村ID  
    news_id：动态的ID  

    返回：country_file_news_detail.json

1. /api/country_file/meeting_news //村档案会议动态  
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：country_file_news.json

1. /api/country_file/meeting_news/detail //村档案会议动态详情  
    type: GET  

    参数：  
    country_id：村ID  
    news_id：动态的ID  

    返回：country_file_news_detail.json

1. /api/country_file/eliminate_path //村档案脱贫轨迹  
    type: GET  

    参数：  
    id：查找对应数据的ID  
    year：年份，不传时返回默认年份数据  

    返回：country_file_eliminate_path.json

1. /api/country_file/eliminate_scores //村档案脱贫轨迹得分 
    type: GET  

    参数：  
    id：查找对应数据的ID  
    year：年份  
    month：月份  

    返回：country_file_eliminate_scores.json

1. /api/country_file/invested //村档案资金投入  
    type: GET  

    参数：  
    id：查找对应数据的ID  
    year：年份，不传时返回默认年份数据  

    返回：country_file_invested.json

1. /api/country_file/data_path //村档案数据轨迹  
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：country_file_data_path.json

1. /api/country_file/business //村档案业务办理
    type: GET  

    参数：  
    id：查找对应数据的ID  

    返回：country_file_business.json

1. /api/country_file/business_rest //村档案业务办理剩余条目
    type: GET  

    参数：  
    id：查找对应数据的ID  
    type: 项目类型，见业务办理项目类型  

    返回：file_business_rest.json


1. /api/duty/child_districts_list //获取子行政区域名列表  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    返回：  
    child_districts_list.json

1. /api/duty/intro //获取责任监控中，指定行政区域（村以外）的帮扶概览1. 减贫计划与帮扶列表  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    返回：  
    duty_intro.json

1. /api/duty/responsible_people_list //获取责任监控中，指定行政区域帮扶责任人的列表  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    返回：  
    duty_responsible_people_list.json

1. /api/duty/responsible_person_poor_list //获取责任监控中，指定帮扶责任人的贫困户列表  
    type: GET  

    参数：  
    level：见层级取值  
    district_id：查找对应行政区域的ID  
    person_id：帮扶人ID  
    返回：  
    duty_responsible_person_poor_list.json

1. /api/duty/organization_list //获取责任监控中，指定行政区域帮扶单位的列表  
    type: GET  

    参数：  
    level：见层级取值  
    district_id：查找对应行政区域的ID  
    返回：  
    duty_organization_list.json

1. /api/duty/organization_country_list //获取责任监控中，指定帮扶单位的村列表  
    type: GET  

    参数：  
    level：见层级取值  
    district_id：查找对应行政区域的ID  
    organization_id：帮扶单位ID  
    返回：  
    duty_organization_country_list.json

1. /api/strategy/static_data //战略地图静态数据  
    type: GET

    参数：
    scope：范围，见区域类型范围取值  
    返回：  
    strategy_static_data.json

1. /api/strategy/dynamic_index //战略地图动态数据  
    type: GET  

    参数：  
    scope：范围，见区域类型范围取值  
    返回：
    strategy_dynamic_index.json

1. /api/search/pre_search 扶贫搜索预搜索  
    type: GET  

    参数：  
    key: 搜索词  
    type: 搜索类型，见扶贫搜索类型取值  
    返回：  
    search_pre_search.json

1. /api/search/search //扶贫搜索搜索结果  
    type: GET  

    参数：  
    id: 搜索结果ID  
    type: 搜索类型，见扶贫搜索类型取值  
    返回：  
    search_search.json  
    其中：  
    搜索贫困户：对应显示贫困户概况、帮扶人、帮扶计划、村档案  
    搜索贫困村：对应显示村档案、帮扶人、帮扶计划  
    搜索帮扶人：对应显示帮扶人  
    搜索帮扶计划：对应显示帮扶计划

1. /api/data/static_data //数据监控数据  
    type: GET  

    参数：
    level：见层级取值  
    id：查找对应行政区域的ID  
    scope：范围，见区域类型范围取值  
    返回：  
    data_static_data.json

1. /api/data/monitor/basic_info //数据监控分析窗基础信息  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year: 年，如无指定返回默认年份
    返回：  
    data_monitor_basic_info.json

1. /api/data/monitor/tree //数据监控分析窗树  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型  
    tabtype: 数据监控窗口标签页类型，见数据监控窗口标签页类型
    返回：  
    data_monitor_tree.json

1. /api/data/monitor/subtree //数据监控分析窗树节点的子树  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型  
    tabtype: 数据监控窗口标签页类型，见数据监控窗口标签页类型
    返回：  
    data_monitor_subtree.json

1. /api/data/monitor/head_download_list //数据监控头部表格下载列表
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型 

    返回：  
    data_monitor_head_download_table_list.json


1. /api/data/monitor/content_basic_info //数据监控窗口内容基础信息，目前由于只有区域类型需要根据区域禁用状态，统一使用同一接口  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    返回：  
    data_monitor_content_basic_info.json

1. /api/data/monitor/order_basic_info //数据监控窗口排序页面基础信息
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型  
    tabtype: 数据监控窗口标签页类型，见数据监控窗口标签页类型
    返回：  
    data_monitor_order_basic_info.json

1. /api/data/monitor/order_table //数据监控窗口排序页面表格  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：查找对应行政区域的ID  
    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型  
    tabtype: 数据监控窗口标签页类型，见数据监控窗口标签页类型
    额外参数：  
    根据当页的order_basic_info和用户选择结合提交  
    返回：  
    data_monitor_order_table.json

1. /api/data/monitor/detail_basic_info //数据监控窗口详细清单弹窗基础信息  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：查找对应行政区域的ID  
    month：月（没有时不传） 
    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型  
    tabtype: 数据监控窗口标签页类型，见数据监控窗口标签页类型
    返回：  
    data_monitor_detail_basic_info.json

1. /api/data/monitor/detail_params_info //数据监控窗口详细清单弹窗参数信息  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：查找对应行政区域的ID  
    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型  
    tabtype: 数据监控窗口标签页类型，见数据监控窗口标签页类型
    返回：  
    data_monitor_detail_params_info.json

1. /api/data/monitor/detail_table //数据监控窗口详细清单弹窗表格  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年
    month：月（没有时不传）
    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型  
    tabtype: 数据监控窗口标签页类型，见数据监控窗口标签页类型
    page: 第几页
    额外参数：  
    根据当页的detail_params_info和用户选择结合提交  
    返回：  
    data_monitor_detail_table.json

1. /api/data/monitor/area_data_table //数据监控窗口区域数据弹窗表格  
    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    wintype: 数据监控窗口所属类型，见数据监控窗口所属类型  
    tabtype: 数据监控窗口标签页类型，见数据监控窗口标签页类型
    返回：  
    data_monitor_area_data_table.json

1. /api/data/monitor/poor/change_management //扶贫对象监控变动管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    返回：  
    data_monitor_poor_change_management.json

1. /api/data/monitor/poor/change_analysis //扶贫对象监控变动分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    status_attribute: 状态属性，见数据分析扶贫对象变动分析状态属性  
    year：年  
    返回：  
    当状态属性为累计贫困户时:
    data_monitor_poor_change_analysis_all.json
    当状态属性为新增贫困户时:
    data_monitor_poor_change_analysis_poor_cur.json
    当状态属性为新增贫困户时:
    data_monitor_poor_change_analysis_poor_add.json
    当状态属性为终止贫困户时:
    data_monitor_poor_change_analysis_poor_end.json
    当状态属性为自然增减户时:
    data_monitor_poor_change_analysis_natural_change.json

1. /api/data/monitor/result/plan_management //脱贫成效监控脱贫计划管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    返回：  
    data_monitor_poor_result_plan_management.json

1. /api/data/monitor/result/result_analysis //脱贫成效监控脱贫成效管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：区域类型，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    返回：  
    data_monitor_poor_result_result_analysis.json

1. /api/data/monitor/result/not_success_analysis //脱贫成效监控未脱贫分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：区域类型，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    score：综合得分，见脱贫成效监控未脱贫分析综合得分取值
    income：两不愁，见脱贫成效监控未脱贫分析两不愁取值
    guarantee：三保障，见三保障取值（值名相同意义相反）
    返回：  
    data_monitor_poor_result_not_success_analysis.json

1. /api/data/monitor/average_income/income_management //人均可支配收入监控收入管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：区域类型，见区域类型范围取值  
    返回：  
    data_monitor_average_income_income_management.json

1. /api/data/monitor/average_income/success_analysis //人均可支配收入监控预脱贫户分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    year：年  
    返回：  
    data_monitor_average_income_success_analysis.json

1. /api/data/monitor/average_income/success_analysis_transferred_payment //人均可支配收入监控预脱贫户分析转移性支出分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    year：年  
    返回：  
    data_monitor_average_income_success_analysis_transferred_payment.json

1. /api/data/monitor/average_income/success_analysis_transferred_income //人均可支配收入监控预脱贫户分析转移性支收入分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    year：年  
    返回：  
    data_monitor_average_income_success_analysis_transferred_income.json

1. /api/data/monitor/average_income/poor_analysis //人均可支配收入监控未脱贫户分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    year：年  
    返回：  
    data_monitor_average_income_poor_analysis.json

1. /api/data/monitor/average_income/poor_analysis_transferred_payment //人均可支配收入监控未脱贫户分析转移性支出分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    year：年  
    返回：  
    data_monitor_average_income_poor_analysis_transferred_payment.json

1. /api/data/monitor/average_income/poor_analysis_transferred_income //人均可支配收入监控未脱贫户分析转移性收入分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    year：年  

    返回：  
    data_monitor_average_income_poor_analysis_transferred_income.json

1. /api/data/monitor/five_low/implement_management //低保五保政策落实监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_five_low_implement_management.json

1. /api/data/monitor/five_low/implement_analysis //低保五保政策落实监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值  

    返回：  
    data_monitor_five_low_implement_analysis.json

1. /api/data/monitor/edu/implement_management //教育政策落实监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_edu_implement_management.json

1. /api/data/monitor/edu/implement_analysis //教育政策落实监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值  
    edu_levels: 见教育阶段取值  

    返回：  
    data_monitor_edu_implement_analysis.json

1. /api/data/monitor/medical_policy/implement_management //医疗政策落实监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_medical_policy_implement_management.json

1. /api/data/monitor/medical_policy/implement_analysis //医疗政策落实监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值  

    返回：  
    data_monitor_medical_policy_implement_analysis.json

1. /api/data/monitor/house/implement_management //住房政策落实监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_house_implement_analysis.json

1. /api/data/monitor/house/implement_analysis //住房政策落实监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_house_implement_analysis.json

1. /api/data/monitor/road/implement_management //道路硬底化监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_road_implement_management.json

1. /api/data/monitor/road/implement_analysis //道路硬底化监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_road_implement_analysis.json

1. /api/data/monitor/water/implement_management //安全饮水监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_water_implement_management.json

1. /api/data/monitor/water/implement_analysis //安全饮水监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_water_implement_analysis.json

1. /api/data/monitor/electricity/implement_management //生活用电监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_electricity_implement_management.json

1. /api/data/monitor/electricity/implement_analysis //生活用电监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_electricity_implement_analysis.json

1. /api/data/monitor/medical_facility/implement_management //医疗设施监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_medical_facility_implement_management.json

1. /api/data/monitor/medical_facility/implement_analysis //医疗设施监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_medical_facility_implement_analysis.json


1. /api/data/monitor/broadcast/implement_management //通广播电视监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_broadcast_implement_management.json

1. /api/data/monitor/broadcast/implement_analysis //通广播电视监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_broadcast_implement_analysis.json

1. /api/data/monitor/net/implement_management //网络覆盖监控落实管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_net_implement_management.json

1. /api/data/monitor/net/implement_analysis //网络覆盖监控落实分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_net_implement_analysis.json

1. /api/data/monitor/poor_analysis/poor_analysis //贫困分析贫困分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  
    poor_reason: 致贫原因，见致贫原因取值  

    返回：  
    data_monitor_poor_analysis_poor_analysis.json

1. /api/data/monitor/alarmed_poor/exception_monitor //预警监控贫困识别监控  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    exception_type：异常类型，多选，见贫困识别监控-异常监控-异常类型取值  

    返回：  
    data_monitor_alarmed_poor_exception_monitor.json

1. /api/data/monitor/alarmed_records/exception_monitor //预警监控建档立卡异常记录监控  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    exception_type：异常类型，多选，见建档立卡的异常记录监控异常类型取值  

    返回：  
    data_monitor_alarmed_records_exception_monitor.json

1. /api/data/monitor/alarmed_money/exception_monitor //预警监控帮扶资金监控  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_alarmed_money_exception_monitor.json

1. /api/data/monitor/alarmed_visit/exception_monitor //预警监控贫困户走访监控  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_alarmed_visit_exception_monitor.json

1. /api/data/monitor/duty/duty_management //责任监控责任管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_duty_duty_management.json

1. /api/data/monitor/duty/duty_analysis //责任监控责任分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_duty_duty_analysis.json

1. /api/data/monitor/project/project_management //项目监控项目管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_monitor_project_project_management.json

1. /api/data/monitor/project/project_analysis //项目监控项目分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    project_attribute: 项目属性，见项目监控项目属性取值  

    返回：  
    当项目属性为全部时:  
    data_monitor_project_project_analysis_all.json  
    当项目属性为到户项目时:  
    data_monitor_project_project_analysis_family.json  
    当项目属性为到村项目时:  
    data_monitor_project_project_analysis_country.json

1. /api/data/monitor/project_family/industry //项目监控户扶贫项目分析产业扶贫  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值
    labor_attribute：劳力属性，见数据分析劳力类型  

    返回：  
    data_monitor_project_family_industry.json

1. /api/data/monitor/project_family/finance //项目监控户扶贫项目分析金融扶贫  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值

    返回：  
    data_monitor_project_family_finance.json

1. /api/data/monitor/project_family/house //项目监控户扶贫项目分析住房改造  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值

    返回：  
    data_monitor_project_family_house.json

1. /api/data/monitor/project_family/property //项目监控户扶贫项目分析资产扶贫  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值

    返回：  
    data_monitor_project_family_property.json

1. /api/data/monitor/project_family/visit //项目监控户扶贫项目分析慰问扶贫  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值

    返回：  
    data_monitor_project_family_visit.json

1. /api/data/monitor/project_family/employment //项目监控户扶贫项目分析就业扶贫  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值

    返回：  
    data_monitor_project_family_employment.json

1. /api/data/monitor/project_family/skill //项目监控户扶贫项目分析技能培训  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值

    返回：  
    data_monitor_project_family_skill.json

1. /api/data/monitor/project_family/edu //项目监控户扶贫项目分析教育扶贫  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    poor_attribute：贫困属性，见数据分析社会保障兜底贫困属性取值

    返回：  
    data_monitor_project_family_edu.json

1. /api/data/monitor/project_country/road //项目监控村扶贫项目分析村道硬底化  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_road.json

1. /api/data/monitor/project_country/water //项目监控村扶贫项目分析饮水工程  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_water.json

1. /api/data/monitor/project_country/recreation_sport //项目监控村扶贫项目分析文体设施  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_recreation_sport.json

1. /api/data/monitor/project_country/hygiene //项目监控村扶贫项目分析卫生设施  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_hygiene.json

1. /api/data/monitor/project_country/lamp //项目监控村扶贫项目分析路灯安装  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_lamp.json

1. /api/data/monitor/project_country/farm //项目监控村扶贫项目分析农田水利  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_farm.json

1. /api/data/monitor/project_country/public_facility //项目监控村扶贫项目分析公共设施  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_public_facility.json

1. /api/data/monitor/project_country/collective_economy //项目监控村扶贫项目分析集体经济  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_collective_economy.json

1. /api/data/monitor/project_country/edu //项目监控村扶贫项目分析教育教学  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_project_country_edu.json

1. /api/data/monitor/capital/capital_management //资金监控资金管理  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  

    返回：  
    data_monitor_capital_capital_management.json

1. /api/data/monitor/capital/capital_analysis //资金监控资金分析  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    scope：范围，见区域类型范围取值  
    project_type_country: 村项目类型，见资金监控村项目类型取值  
    project_type_family: 户项目类型，见资金监控户项目类型取值  

    返回：  
    data_monitor_capital_capital_analysis.json

1. /api/poor_service/index //扶贫服务首页
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    返回：  
    poor_service_index.json

1. /api/poor_service/index/data_lingnan //扶贫服务优品数据
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    time: 时间选值，由扶贫服务首页返回的值中取

    返回：  
    poor_service_index_data_lingnan.json

1. /api/poor_service/item_list/basic_info //扶贫服务项目基础信息  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    subject: 项目主体，见扶贫服务项目主体取值  
    status: 项目状态，见扶贫服务项目状态取值  

    返回：  
    poor_service_item_list_basic_info.json

1. /api/poor_service/item_list/search //扶贫服务项目搜索  
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    subject: 项目主体，见扶贫服务项目主体取值  
    status: 项目状态，见扶贫服务项目状态取值  
    type：类型，由basic_info返回  
    keyword：搜索关键字，空为显示全部  
    page: 第几页

    返回：  
    poor_service_item_list_search.json

1. /api/poor_east_west/index //东西扶贫协作首页  
    type: GET  

    参数：  
    返回：  
    poor_east_west_index.json

1. /api/check_export_result //导出结果查询  
    type: GET  

    参数：  
    export_task_id：导出任务的ID  
    返回：  
    check_export_result.json

1. /api/area_path //从顶层层级到子层级的所有级联行政区域数据，如当前要查找广州市的数据，需要返回广东省、广东省所有市、广州市下所有区的数据  
    type: GET  

    参数：  
    level：当前要找行政区域的层级，见层级取值  
    id：当前要找的层级行政区域的ID  
    返回：  
    area_path.json

1. /api/login //登录  
    type: POST  

    参数：  
    username：用户名
    password：密码
    code：验证码
    remember_username：是否记住用户名
    返回：  
    login.json

1. /api/entrance //平台统一入口
    type: GET

    返回：
    entrance.json



1. /api/poor_family_list //贫困户列表 
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    page: 页码
    返回：  
    poor_family_list.json



1. /api/data/monitor/capital/capital_file //资金监控-查文件号列表
    type: GET  

    参数：
    year:  年份
    level：资金来源层级（见层级取值） 
    keyword：搜索关键字，空为显示全部  
    page: 第几页

    返回：  
    data_monitor_capital_capital_file.json


1. /api/data/monitor/capital/capital_file_basic_info //文件号基础信息
    type: GET  

    参数：
    id:  文件号ID

    返回：  
    data_monitor_capital_capital_file_basic_info.json

1. /api/data/monitor/capital/capital_file_detail_table //文件号详情表格
    type: GET  

    参数：
    id:  文件号ID
    area_level：所选区域层级（见层级取值）  
    area_id：所选区域ID
    page: 第几页

    返回：  
    data_monitor_capital_capital_file_detail_table.json


<!-- 示范村相关接口 -->
1. /api/data/example_country/index_static_data //示范村首页数据
    type: GET  

    参数：
    id:地图区域ID
    level：地图区域层级

    返回：  
    data_example_country_index_static_data.json

1. /api/data/example_country/country_photo //示范村图片
    type: GET  

    参数：
    id:村的ID

    返回：  
    data_example_country_country_photo.json


1. /api/data/example_country/renovate_standard_chart //示范村建设-整治达标图形
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  

    返回：  
    data_example_country_country_renovate_standard_chart.json

1. /api/data/example_country/renovate_standard_chart //示范村建设-整治达标表格
    type: GET  

    参数：  
    level：见层级取值  
    id：查找对应行政区域的ID  
    year：年  
    table_month：统计表的月份 
    table_state：统计表状态（doing:进行中，done:已完成，unstart:未启动） 

    返回：  
    data_example_country_country_renovate_standard_table.json

<!--end 示范村相关接口 -->