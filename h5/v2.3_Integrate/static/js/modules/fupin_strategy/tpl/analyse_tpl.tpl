<!--贫困分析-->
<div id="popupPoorAnalyse" class="pop-poorAnalyse-wrap">
    <section id="analyseNav" class="analyse-nav">
        
    </section>
   <section id="analyseContent" class="analyse-content">
       
   </section>
</div>
<!--行政区域-->
<script id="tplAreaSelect" type="text/html">
    <#for(var i = 0, len = data.area_path.length; i < len; i++) {#>
    <div class="select-box js-select-box" level="<#=data.area_path[i].level#>" selected_id="<#=data.area_path[i].selected_id#>">
        <span><#=data.area_path[i].selected_name#></span>
        <i class="arrow"></i>
        <div class="select-list js-select-list">
            <div>
                <ul>
                    <#for(var j = 0, len2 = data.area_path[i].list.length; j<len2; j++) {#>
                         <li title="<#=data.area_path[i].list[j].name#>" class="<#= (data.area_path[i].list[j].id==data.area_path[i].selected_id) ? 'active':''#>" data-id="<#=data.area_path[i].list[j].id#>">
                             <#=data.area_path[i].list[j].name#>
                         </li>
                    <#}#>
                </ul>
            </div>
        </div>
    </div>
    <#}#>
</script>
<!--行政类型-->
<script id="tplAreaSort" type="text/html">
   <label>行政类型：</label>
       <div class="js-radio-tag radio-tag active <#= data.scope_available[data.AREA_SCOPE.QUANBU] ? '':'disabled' #>" data-scope="1">
           <i class="radio"></i>
           全部
       </div>
       <div class="js-radio-tag radio-tag <#= data.scope_available[data.AREA_SCOPE.XIANGDUIPINKUNCUN] ? '':'disabled' #>" data-scope="2">
           <i class="radio"></i>
           相对贫困村
       </div>
       <div class="js-radio-tag radio-tag <#= data.scope_available[data.AREA_SCOPE.FUNSANCUN] ? '':'disabled' #>" data-scope="3">
           <i class="radio"></i>
           分散村
       </div>
       <div class="js-radio-tag radio-tag <#= data.scope_available[data.AREA_SCOPE.GEMINGLAOQU] ? '':'disabled' #>" data-scope="4">
           <i class="radio"></i>
           革命老区
       </div>
       <div class="js-radio-tag radio-tag <#= data.scope_available[data.AREA_SCOPE.ZHONGYANGSUQU] ? '':'disabled' #>" data-scope="5">
           <i class="radio"></i>
           中央苏区
       </div>
</script>
<!--统计表-->
<script type="text/html" id="tplTable">
    <div class="chart-item">
        <div class="chart-item-title"><span class="js-analyse-sub-title"><#=table_title#></span></div>
        <div class="analyse-total-table"></div>
    </div>
</script>
<!--搜索详情页-->
<script type="text/html" id="tplDetailSearch">
    <div class="chart-page-content clearfix">
        <div class="chart-item">
            <div class="chart-item-title"><span class="js-analyse-sub-title"><#=search_title#></span></div>
            <div class="search-top-option">
                <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>&poor_attribute=<#=export_data.poor_attribute#>&dept=<#=export_data.dept#>&keyword=<#=export_data.keyword#>" >数据导出</a>
                <span>|</span>
                <div class="return-main js-back"><i class="icon-back" data-back="averageIncome"></i>返回</div>
            </div>
          
            <div class="data-search-header">
                <input class="keyword" type="text" value="<#=search_keyword#>">
                <button class="btn-search">搜索</button>
            </div>
            <div class="data-search-content">
                <div class="detail-search-content"></div>
                <div class="none-search">未找到相关内容，请重试</div>
            </div>

        </div>
    </div>
</script>
<!--贫困分析致贫原因分析-->
<script id="tplPoorReason" type="text/html">
    <div class="analyse-poor-reason">
        <div class="poor-reason-header clearfix">
            <div class="poor-reason-item">
                <span><#=family.amount#></span>户
                <p>贫困户</p>
            </div>
            <div class="poor-reason-item ml">
                <span><#=people.amount#></span>人
                <p>贫困人口</p>
            </div>
        </div>
        <div id="chartPoorReason" class="chart-poor-reason"></div>
    </div>
</script>
<!--贫困户属性分析-->
<script id="tplPoorAttribute" type="text/html">
    <div class="analyse-poor-page">
        <div class="clearfix">
            <div class="poor-page-header poor-one-header clearfix inline-block mr10">
                <div class="poor-page-item">
                    <span class="amount"><#=total.amount#></span>户
                    <p class="poor-name">贫困户</p>
                </div>
            </div>
            <div class="poor-page-header clearfix inline-block">
                <div class="poor-page-item">
                    <span class="amount"><#=disabled.amount#></span>户
                    <p class="poor-name">残疾户</p>
                    <div class="poor-bar">
                        <p class="litem">
                            <span class="bar" style="width:<#=disabled.percent#>%;"></span>
                        </p>
                        <span class="vnum"><#=disabled.percent#>%</span>
                    </div>
                </div>
                <div class="poor-page-item ml">
                    <span class="amount"><#=migrator.amount#></span>户
                    <p class="poor-name">水库移民</p>
                    <div class="poor-bar">
                        <p class="litem">
                            <span class="bar" style="width:<#=migrator.percent#>%;"></span>
                        </p>
                        <span class="vnum"><#=migrator.percent#>%</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="chart-page-content">
            <div class="chart-item fl">
                <div class="chart-item-title">贫困属性</div>
                <div id="poorAttributeChart" class="poor-chart"></div>
            </div>
            <div class="chart-item fr">
                <div class="chart-item-title">劳动力情况</div>
                <div id="workForceChart" class="poor-chart"></div>
            </div>
        </div>
    </div>
</script>
<!--贫困户危房分析-->
<script id="tplPoorDangerHouse" type="text/html">
    <div class="analyse-poor-page">
        <div class="poor-page-header poor-one-header clearfix">
            <div class="poor-page-item">
                <span class="amount"><#=danger_house.amount#></span>户
                <p class="poor-name">危房户</p>
                <div class="poor-bar">
                    <p class="litem">
                        <span class="bar" style="width:<#=danger_house.percent#>%;"></span>
                    </p>
                    <span class="vnum"><#=danger_house.percent#>%</span>
                </div>
            </div>
        </div>
        <div class="chart-page-content">
            <div class="chart-item fl">
                <div class="chart-item-title">危房级别分析</div>
                <div id="dangerHouseLevelChart" class="poor-chart"></div>
            </div>
            <div class="chart-item fr">
                <div class="chart-item-title">危房结构分布</div>
                <div id="dangerHouseStructureChart" class="poor-chart"></div>
            </div>
        </div>
    </div>
</script>
<!--贫困人口务工状况分析-->
<script id="tplPoorLabor" type="text/html">
    <div class="analyse-poor-page">
        <div class="poor-page-header clearfix">
            <div class="poor-page-item">
                <span class="amount"><#=total.amount#></span>人
                <p class="poor-name">贫困人口</p>
            </div>
            <div class="poor-page-item ml">
                <span class="amount"><#=farm_at_home.amount#></span>人
                <p class="poor-name">在家务农人口</p>
                <div class="poor-bar">
                    <p class="litem">
                        <span class="bar" style="width:<#=farm_at_home.percent#>%;"></span>
                    </p>
                    <span class="vnum"><#=farm_at_home.percent#>%</span>
                </div>
            </div>
        </div>
        <div class="chart-page-content">
            <div class="chart-item fl">
                <div class="chart-item-title">贫困人口务工状况</div>
                <div id="laborStatusChart" class="poor-chart"></div>
            </div>
            <div class="chart-item fr">
                <div class="chart-item-title">在家务农的劳动能力分布</div>
                <div id="farmDistributionChart" class="poor-chart"></div>
            </div>
        </div>
    </div>
</script>
<!--贫困人口教育分析-->
<script id="tplPoorEdu" type="text/html">
    <div class="analyse-poor-page">
        <div class="poor-page-header clearfix">
            <div class="poor-page-item">
                <span class="amount"><#=total.amount#></span>人
                <p class="poor-name">贫困人口</p>
            </div>
            <div class="poor-page-item ml">
                <span class="amount"><#=at_school.amount#></span>人
                <p class="poor-name">在校人口</p>
                <div class="poor-bar">
                    <p class="litem">
                        <span class="bar" style="width:<#=at_school.percent#>%;"></span>
                    </p>
                    <span class="vnum"><#=at_school.percent#>%</span>
                </div>
            </div>
        </div>
        <div class="chart-page-content">
            <div class="chart-item fl">
                <div class="chart-item-title">贫困人口学历分布</div>
                <div id="eduQualificationChart" class="poor-chart"></div>
            </div>
            <div class="chart-item fr">
                <div class="chart-item-title">在校生学龄段分布</div>
                <div id="eduSchoolChart" class="poor-chart"></div>
            </div>
        </div>
    </div>
</script>
<!--贫困人口健康状况分析-->
<script id="tplPoorHealth" type="text/html">
    <div class="analyse-poor-page">
        <div class="poor-page-header clearfix">
            <div class="poor-page-item">
                <span class="amount"><#=total.amount#></span>人
                <p class="poor-name">贫困人口</p>
            </div>
            <div class="poor-page-item ml">
                <span class="amount"><#=ill_health.amount#></span>人
                <p class="poor-name">非健康人口</p>
                <div class="poor-bar">
                    <p class="litem">
                        <span class="bar" style="width:<#=ill_health.percent#>%;"></span>
                    </p>
                    <span class="vnum"><#=ill_health.percent#>%</span>
                </div>
            </div>
        </div>
        <div class="chart-page-content">
            <div class="chart-item fl">
                <div class="chart-item-title">健康状况分布</div>
                <div id="healthPieChart" class="poor-chart"></div>
            </div>
            <div class="chart-item fr">
                <div class="chart-item-title">Top5疾病</div>
                <div id="healthTopChart" class="poor-chart"></div>
            </div>
        </div>
    </div>
</script>
<!--贫困户“三保障”分析义务教育-->
<script id="tplGuaranteeEdu" type="text/html">
    <div class="analyse-main-content">
        <div>
            <div class="square-info-box clearfix">
                <ul>
                    <li>
                        <label class="info-name">适龄人数：</label>
                        <span class="yellow"><#=edu.amount_school_age#></span></li>
                    <li>
                        <label class="info-name">差异人数：</label>
                        <span class="yellow"><#=edu.amount_diff#></span>
                    </li>
                    <li>
                        <p class="litem">
                            <span class="bar" style="width:<#=edu.percent#>%"></span>
                        </p>
                        <span class="vnum"><#=edu.percent#>%</span>
                    </li>
                </ul>
            </div>
            <div class="chart-page-content clearfix">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 差异人数月趋势</div>
                    <div id="guaranteeEduChart" class="edu-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="average-income-search data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困户“三保障”分析义务教育统计表-->
<script id="tplGuaranteeEduTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="javascript:void(0);">数据导出<span>0</span></a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td>适龄人数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_at_age.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_at_age.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>差异人数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_diff.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_diff.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>差异人数比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.ratio_diff_people.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.ratio_diff_people.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
            <#for(var i=0,len=table.list.length;i<len;i++){#>
            <tr>
                <td><#=table.list[i].area_name#></td>
                <td><#=table.list[i].order#></td>
                <td><#=table.list[i].amount_at_age#></td>
                <td>
                    <a href="javascript:void(0);" class="js-search-diff"
                       data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                        <#=table.list[i].amount_diff#>
                    </a>
                </td>
                <td><#=table.list[i].ratio_diff_people#></td>
            </tr>
            <#}#>
                <tr>
                    <td>合计</td>
                    <td>-</td>
                    <td><#=table.total.amount_at_age#></td>
                    <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_diff#></a></td>
                    <td><#=table.total.ratio_diff_people#></td>
                </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困户“三保障”分析义务教育详表-->
<script id="tplGuaranteeEduDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>户主姓名</td>
            <td>学生姓名</td>
            <td>建档立卡的信息</td>
            <td>省教育厅的信息</td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplGuaranteeEduDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困户“三保障”分析义务教育详表条目-->
<script id="tplGuaranteeEduDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#> <tr>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].city_name#></td>
            <td><#=table.list[i].county_name#></td>
            <td><#=table.list[i].town_name#></td>
            <td>
                <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
                   data-country-id="<#=table.list[i].country_id#>">
                    <#=table.list[i].country_name#>
                </a>
            </td>
            <td>
                <a href="javascript:void(0);" class="<#=table.list[i].family_id?'':'no-detail'#> js-redirect-family"
                   data-family-id="<#=table.list[i].family_id#>">
                    <#=table.list[i].house_holder_name#>
                </a>
            </td>
            <td><#=table.list[i].student_name#></td>
            <td><#=table.list[i].doc_info#></td>
            <td><#=table.list[i].edu_department_info#></td>
        </tr>
    <#}#>
</script>
<!--数据分析贫困户“三保障”分析医疗保障-->
<script id="tplGuaranteeMedical" type="text/html">
    <div class="analyse-main-content">
        <div>
            <div class="square-info-box clearfix">
                <ul>
                    <li>
                        <label class="info-name">贫困人口数：</label>
                        <span class="yellow"><#=medical.amount_poor_people#></span></li>
                    <li>
                        <label class="info-name">差异人数：</label>
                        <span class="yellow"><#=medical.amount_diff#></span>
                    </li>
                    <li>
                        <p class="litem">
                            <span class="bar" style="width:<#=medical.percent#>%"></span>
                        </p>
                        <span class="vnum"><#=medical.percent#>%</span>
                    </li>
                </ul>
            </div>
            <div class="chart-page-content clearfix">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 差异人数月趋势</div>
                    <div id="guaranteeMedicalChart" class="edu-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困户“三保障”分析医疗保障统计表-->
<script id="tplGuaranteeMedicalTable" type="text/html">
     <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td>贫困人口数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_poor_people.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_poor_people.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>差异人数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_diff.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_diff.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
            <td>差异人数比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.ratio_diff_people.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.ratio_diff_people.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_poor_people#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_diff#>
                </a>
            </td>
            <td><#=table.list[i].ratio_diff_people#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_poor_people#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_diff#></a></td>
                <td><#=table.total.ratio_diff_people#></td>
            </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困户“三保障”分析医疗保障搜索统计表-->
<script id="tplGuaranteeMedicalDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>户主姓名</td>
            <td>成员姓名</td>
            <td>建档立卡的信息</td>
            <td>省社会保险基金管理局的信息</td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplGuaranteeMedicalDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困户“三保障”分析医疗保障搜索统计表条目-->
<script id="tplGuaranteeMedicalDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
        <td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].family_id?'':'no-detail'#> js-redirect-family"
               data-family-id="<#=table.list[i].family_id#>">
                <#=table.list[i].house_holder_name#>
            </a>
        </td>
                        <td><#=table.list[i].member_name#></td>
        <td><#=table.list[i].doc_info#></td>
        <td><#=table.list[i].social_insurance_department_info#></td>
    </tr>
    <#}#>
</script>
<!--数据分析贫困户“三保障”分析住房保障-->
<script id="tplGuaranteeHouse" type="text/html">

    <div class="analyse-main-content">
        <div>
            <div class="square-info-box clearfix">
                <ul>
                    <li>
                        <label class="info-name">危房改造户数：</label>
                        <span class="yellow"><#=house.amount_danger_house#></span></li>
                    <li>
                        <label class="info-name">差异户数：</label>
                        <span class="yellow"><#=house.amount_diff#></span>
                    </li>
                    <li>
                        <p class="litem">
                            <span class="bar" style="width:<#=house.percent#>%"></span>
                        </p>
                        <span class="vnum"><#=house.percent#>%</span>
                    </li>
                </ul>
            </div>
            <div class="chart-page-content clearfix">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 差异户数月趋势</div>
                    <div id="guaranteeHouseChart" class="edu-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困户“三保障”分析住房保障统计表-->
<script id="tplGuaranteeHouseTable" type="text/html">
     <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td>危房户数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_danger_house.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_danger_house.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>差异户数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_diff.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_diff.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
            <td>差异户数比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.ratio_diff_people.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.ratio_diff_people.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_danger_house#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_diff#>
                </a>
            </td>
            <td><#=table.list[i].ratio_diff_people#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_danger_house#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_diff#></a></td>
                <td><#=table.total.ratio_diff_people#></td>
            </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困户“三保障”分析住房保障搜索统计表-->
<script id="tplGuaranteeHouseDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>户主姓名</td>
            <td>建档立卡的信息</td>
            <td>省住建部的信息</td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplGuaranteeHouseDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困户“三保障”分析住房保障搜索统计表条目-->
<script id="tplGuaranteeHouseDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
        <td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].family_id?'':'no-detail'#> js-redirect-family"
               data-family-id="<#=table.list[i].family_id#>">
                <#=table.list[i].house_holder_name#>
            </a>
        </td>
        <td><#=table.list[i].doc_info#></td>
        <td><#=table.list[i].house_department_info#></td>
    </tr>
    <#}#>
</script>
<!--数据分析贫困村“一相当”分析道路硬化-->
<script id="tplConditionRoad" type="text/html">
    <div class="analyse-main-content">
        <div>
            <div class="chart-page-content clearfix mt15">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 未完成道路硬化的村数月趋势</div>
                    <div id="conditionRoadChart" class="condition-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困村“一相当”分析道路硬化统计表-->
<script id="tplConditionRoadTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td><span>村数</span></td>
            <td>已通硬底化道路的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_have_road.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_have_road.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>未完成道路硬化的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_no_road.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_no_road.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
            <td>未完成道路硬化的村比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.rate_country_no_road.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.rate_country_no_road.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_country#></td>
            <td><#=table.list[i].amount_country_have_road#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_country_no_road#>
                </a>
            </td>
            <td><#=table.list[i].rate_country_no_road#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_country#></td>
                <td><#=table.total.amount_country_have_road#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_country_no_road#></a></td>
                <td><#=table.total.rate_country_no_road#></td>
            </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困村“一相当”分析道路硬化搜索统计表-->
<script id="tplConditionRoadDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>行政村到乡镇未通沥青(水泥)路里程数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.distance.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.distance.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplConditionRoadDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困村“一相当”分析道路硬化搜索统计表条目-->
<script id="tplConditionRoadDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
        <td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td><#=table.list[i].distance#></td>
    </tr>
    <#}#>
</script>
<!--数据分析贫困村“一相当”分析安全饮水-->
<script id="tplConditionWater" type="text/html">
    <div class="analyse-main-content">
        <div>
            <div class="chart-page-content clearfix mt15">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 未实现安全饮水村数月趋势</div>
                    <div id="conditionWaterChart" class="condition-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困村“一相当”分析安全饮水统计表-->
<script id="tplConditionWaterTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td><span>村数</span></td>
            <td>全实现安全饮水的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_have_water_security.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_have_water_security.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>未全部实现安全饮水的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_no_water_security.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_no_water_security.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
            <td>未全实现安全饮水的村比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.rate_no_water_security.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.rate_no_water_security.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_family#></td>
            <td><#=table.list[i].amount_country_have_water_security#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_country_no_water_security#>
                </a>
            </td>
            <td>
                <#=table.list[i].rate_no_water_security#>
            </td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_family#></td>
                <td><#=table.total.amount_country_have_water_security#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_country_no_water_security#></a></td>
                <td>
                    <#=table.total.rate_no_water_security#>
                </td>
            </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困村“一相当”分析安全饮水搜索统计表-->
<script id="tplConditionWaterDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>未实现安全饮水的户数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_no_water_security.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_no_water_security.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplConditionWaterDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困村“一相当”分析安全饮水搜索统计表条目-->
<script id="tplConditionWaterDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
        <td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td><#=table.list[i].amount_no_water_security#></td>
    </tr>
    <#}#>
</script>
<!--数据分析贫困村“一相当”分析卫生站-->
<script id="tplConditionHealth" type="text/html">
    <div class="analyse-main-content">
        <div>
            <div class="chart-page-content clearfix mt15">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 无卫生室医师的村数月趋势</div>
                    <div id="conditionHealthChart" class="condition-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困村“一相当”分析卫生站统计表-->
<script id="tplConditionHealthTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td><span>村数</span></td>
            <td>有卫生站的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_have_health_station.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_have_health_station.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>无卫生室(或执业(助理)医师的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_no_doctor.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_no_doctor.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
            <td>无卫生室(或执业(助理)医师的村比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.rate_no_health_care.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.rate_no_health_care.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_country#></td>
            <td><#=table.list[i].amount_country_have_health_station#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_country_no_doctor#>
                </a>
            </td>
            <td><#=table.list[i].rate_no_health_care#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_country#></td>
                <td><#=table.total.amount_country_have_health_station#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_country_no_doctor#></a></td>
                <td><#=table.total.rate_no_health_care#></td>
            </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困村“一相当”分析卫生站搜索统计表-->
<script id="tplConditionHealthDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplConditionHealthDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困村“一相当”分析卫生站搜索统计表条目-->
<script id="tplConditionHealthDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
        <td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
    </tr>
    <#}#>
</script>
<!--数据分析贫困村“一相当”分析通电-->
<script id="tplConditionElectricity" type="text/html">
    <div class="analyse-main-content">
        <div>
            <div class="chart-page-content clearfix mt15">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 未全部通电的村数月趋势</div>
                    <div id="conditionElectricityChart" class="condition-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困村“一相当”分析通电统计表-->
<script id="tplConditionElectricityTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td><span>村数</span></td>
            <td>全部通电的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_have_electricity.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_have_electricity.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>未全部通电的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_no_electricity.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_no_electricity.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>未全部通电的村比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.rate_no_electricity.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.rate_no_electricity.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_country#></td>
            <td><#=table.list[i].amount_country_have_electricity#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_country_no_electricity#>
                </a>
            </td>
            <td><#=table.list[i].rate_no_electricity#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_country#></td>
                <td><#=table.total.amount_country_have_electricity#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_country_no_electricity#></a></td>
                <td><#=table.total.rate_no_electricity#></td>
            </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困村“一相当”分析通电搜索统计表-->
<script id="tplConditionElectricityDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>未通电户数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_no_electricity.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_no_electricity.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplConditionElectricityDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困村“一相当”分析通电搜索统计表条目-->
<script id="tplConditionElectricityDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
        <td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td><#=table.list[i].amount_no_electricity#></td>
    </tr>
    <#}#>
</script>
<!--数据分析贫困村“一相当”分析通广播电视-->
<script id="tplConditionBroadcast" type="text/html">
    <div class="analyse-main-content">
        <div>
            <div class="chart-page-content clearfix mt15">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 未全部通广播电视的村数月趋势</div>
                    <div id="conditionBroadcastChart" class="condition-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困村“一相当”分析通广播电视统计表-->
<script id="tplConditionBroadcastTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td><span>村数</span></td>
            <td>全部通广播电视的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_have_broadcast.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_have_broadcast.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>未全部通广播电视的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_no_broadcast.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_no_broadcast.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>未全部通广播电视的村比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.rate_country_no_broadcast.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.rate_country_no_broadcast.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_family#></td>
            <td><#=table.list[i].amount_country_have_broadcast#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_country_no_broadcast#>
                </a>
            </td>
            <td><#=table.list[i].rate_country_no_broadcast#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_family#></td>
                <td><#=table.total.amount_country_have_broadcast#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_country_no_broadcast#></a></td>
                <td><#=table.total.rate_country_no_broadcast#></td>
            </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困村“一相当”分析通广播电视搜索统计表-->
<script id="tplConditionBroadcastDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>未通广播电视的户数</td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplConditionBroadcastDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困村“一相当”分析通广播电视搜索统计表条目-->
<script id="tplConditionBroadcastDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
    	<td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td><#=table.list[i].amount_no_broadcast#></td>
    </tr>
    <#}#>
</script>
<!--数据分析贫困村“一相当”分析宽带网络-->
<script id="tplConditionNet" type="text/html">
    <div class="analyse-main-content">
        <div>
            <div class="chart-page-content clearfix mt15">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 宽带网络未全覆盖的村数月趋势</div>
                    <div id="conditionNetChart" class="condition-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--数据分析贫困村“一相当”分析宽带网络统计表-->
<script id="tplConditionNetTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td><span>村数</span></td>
            <td>宽带网络全覆盖的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_have_net.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_have_net.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>宽带网络未全覆盖的村数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_country_no_net.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_country_no_net.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>宽带网络未全覆盖的村比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.rate_country_no_net.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.rate_country_no_net.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_country#></td>
            <td><#=table.list[i].amount_country_have_net#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_country_no_net#>
                </a>
            </td>
            <td><#=table.list[i].rate_country_no_net#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_country#></td>
                <td><#=table.total.amount_country_have_net#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_country_no_net#></a></td>
                <td><#=table.total.rate_country_no_net#></td>
            </tr>
        </tbody>
    </table>
</script>
<!--数据分析贫困村“一相当”分析宽带网络搜索统计表-->
<script id="tplConditionNetDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>宽带网络未覆盖的户数</td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplConditionNetDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--数据分析贫困村“一相当”分析宽带网络搜索统计表条目-->
<script id="tplConditionNetDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
    	<td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td><#=table.list[i].amount_no_net#></td>
    </tr>
    <#}#>
</script>

<!--人均可支配收入分析-->
<script id="tplAverageIncome" type="text/html">
    <div class="average-income">
        <div>
            <div class="chart-page-content clearfix">
                <div class="chart-item chart-small">
                    <div class="chart-item-title">人均可支配收入年趋势</div>
                    <div id="averageIncomeYearChart" class="chart "></div>
                </div>
                <div class="chart-item chart-big">
                    <div class="chart-item-title">收入区域分布</div>
                    <div id="averageIncomeAreaChart" class="chart "></div>
                </div>
            </div>
            <div class="chart-page-content average-income-detail clearfix">

            </div>
        </div>
    </div>
</script>
<!--数据分析人均可支配收入分析统计表-->
<script type="text/html" id="tplAverageIncomeTable">
    <div class="table-item">
        <div class="table-title">收入统计表</div>
        <div class="content">
        <div>
        <table class="analyse-tb">
            <thead>
                <tr>
                <td rowspan="2">行政区域</td>
                <td colspan="2">贫困户数贫困户数贫困户数贫<br/>困户数贫困户数贫困户数贫困户</td>
                <td rowspan="2">总收入</td>
                <td rowspan="2">总支出</td>
                <td rowspan="2">可支配收入</td>
                <td rowspan="2">人均可支配收入</td>
                </tr>
                <tr>
                    <td>户数（户）</td>
                    <td>人数（人）</td>
                </tr>
            </thead>
        <tbody>
        <#for(var i=0,len=table.length;i<len;i++){#>
            <tr>
            <td><#=table[i].area_name#></td>
            <td><#=table[i].poor.amount_family#></td>
            <td><#=table[i].poor.amount_people#></td>
            <td><#=table[i].total_income#></td>
            <td><#=table[i].total_payment#></td>
            <td><#=table[i].disposable_income#></td>
            <td><#=table[i].average_disposable_income#></td>
            </tr>
        <#}#>
        </tbody>
        </table>
        </div>
        </div>
    </div>
</script>
<!--社会保障兜底-->
<script type="text/html" id="tplSocialPolicy">
    <div class="social-policy analyse-main-content">
        <div>
            <div class="data-analyse-tab clearfix">
                <label class="tab-title">贫困类型：</label>
                <ul class="social-policy-tab">
                    <li class="<#=poor_attribute=='all'?'active':''#>" data-attr="all"><label>全部</label></li>
                    <li class="<#=poor_attribute=='low'?'active':''#>" data-attr="low"><label>低保户</label></li>
                    <li class="<#=poor_attribute=='five'?'active':''#>" data-attr="five"><label>五保户</label></li>
                    <li class="<#=poor_attribute=='no_labor'?'active':''#>" data-attr="no_labor" style="width:180px"><label>无劳动能力的一般贫困户</label></li>
                </ul>
            </div>
            <div class="social-policy-count mt20">
                <div class="square-info-box clearfix">
                    <ul>
                        <li>
                            <label class="info-name">户数：</label>
                            <span class="yellow"><#=family.amount_total#></span></li>
                        <li>
                            <label class="info-name">有差异的户数：</label>
                            <span class="yellow"><#=family.amount_diff#></span>
                        </li>
                        <li>
                            <p class="litem">
                                <span class="bar" style="width:<#=family.percent#>%"></span>
                            </p>
                            <span class="vnum"><#=family.percent#>%</span>
                        </li>
                    </ul>
                </div>
                <div class="square-info-box clearfix">
                    <ul>
                        <li>
                            <label class="info-name">人口数：</label>
                            <span class="yellow"><#=people.amount_total#></span></li>
                        <li>
                            <label class="info-name">有差异的人口数：</label>
                            <span class="yellow"><#=people.amount_diff#></span>
                        </li>
                        <li>
                            <p class="litem">
                                <span class="bar" style="width:<#=people.percent#>%"></span>
                            </p>
                            <span class="vnum"><#=people.percent#>%</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="chart-page-content clearfix">
                <div class="chart-item">
                    <div class="chart-item-title"><#=area_name#> 差异人数月趋势</div>
                    <div id="socialPolicyChart" class="edu-chart"></div>
                </div>
            </div>
            <div class="chart-page-content chart-table-content clearfix">
            </div>
        </div>
    </div>
    <div class="average-income-search data-search-box" style="display:none;">
    </div>
</script>
<!--社会保障兜底统计表-->
<script id="tplSocialPolicyTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&poor_attribute=<#=export_data.poor_attribute#>&year=<#=export_data.year#>&month=<#=export_data.month#>" >数据导出</a>
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td>
                贫困户数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_poor_family.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_poor_family.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>
                贫困人口数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_poor_people.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_poor_people.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
            <td>
                有差异的户数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_diff_family.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_diff_family.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
            <td>
                有差异人口数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_diff_people.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_diff_people.stat_method#></td>
                            </tr>
                        </table>

                    </div>
                </i>
            </td>
            <td><span>有差异人口比例</span></td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_poor_family#></td>
            <td><#=table.list[i].amount_poor_people#></td>
            <td><#=table.list[i].amount_diff_family#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_diff_people#>
                </a>
            </td>
            <td><#=table.list[i].ratio_diff_people#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_poor_family#></td>
                <td><#=table.total.amount_poor_people#></td>
                <td><#=table.total.amount_diff_family#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_diff_people#></a></td>
                <td><#=table.total.ratio_diff_people#></td>
            </tr>
        </tbody>
    </table>
      </div>
</script>
<!--社会保障兜底搜索详情页-->
<script id="tplSocialPolicyDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>户主姓名</td>
            <td>
                贫困属性
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.poor_attributes.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.poor_attributes.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>
                家庭成员数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_family_members.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_family_members.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>
                有差异的家庭成员数
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.amount_diff_family_members.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.amount_diff_family_members.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
            <td>
                有差异的家庭成员名单
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.list_diff_family_members.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.list_diff_family_members.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
        <tbody>
            <#include('tplSocialPolicyDetailSearchItems')#>
        </tbody>
    </table>
        </div>
</script>
<!--社会保障兜底搜索详情页条目-->
<script id="tplSocialPolicyDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
        <td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].family_id?'':'no-detail'#> js-redirect-family"
               data-family-id="<#=table.list[i].family_id#>">
                <#=table.list[i].house_holder_name#>
            </a>
        </td>
        <td><#=table.list[i].poor_attributes#></td>
        <td><#=table.list[i].amount_family_members#></td>
        <td><#=table.list[i].amount_diff_family_members#></td>
        <td><#=table.list[i].list_diff_family_members#></td>
    </tr>
    <#}#>
</script>
<!--预警监控存在异常的贫困户监控-->
<script id="tplAlarmedFamily" type="text/html">
    <div class="alarmed-family analyse-main-content">
        <div class="data-analyse-tab clearfix">
                <label class="tab-title">行政部门：</label>
                <ul class="alarmed-family-tab">
                    <li class="<#=dept=='all'?'active':''#>" data-attr="all"><label>全部</label></li>
                    <li class="<#=dept=='gongan'?'active':''#>" data-attr="gongan"><label>公安</label></li>
                    <li class="<#=dept=='minzheng'?'active':''#>" data-attr="minzheng"><label>民政</label></li>
                    <li class="<#=dept=='canlian'?'active':''#>" data-attr="canlian"><label>残联</label></li>
                    <li class="<#=dept=='gongshang'?'active':''#>" data-attr="gongshang"><label>工商</label></li>
                    <li class="<#=dept=='fangguan'?'active':''#>" data-attr="fangguan"><label>房管</label></li>
                    <li class="<#=dept=='renshe'?'active':''#>" data-attr="renshe" style="width:130px"><label>财政供养 (人社)</label></li>
                </ul>
            </div>
            <div class="social-policy-count mt20">
                <div class="square-info-box clearfix">
                    <ul>
                        <li>
                            <label class="info-name">对比项：</label>
                            <span class="yellow"><#=family.amount_total#></span></li>
                        <li>
                            <label class="info-name">异常项：</label>
                            <span class="yellow"><#=family.amount_diff#></span>
                        </li>
                        <li>
                            <p class="litem">
                                <span class="bar" style="width:<#=family.percent#>%"></span>
                            </p>
                            <span class="vnum"><#=family.percent#>%</span>
                        </li>
                    </ul>
                </div>
            </div>
        <div class="chart-page-content clearfix">
            <div class="chart-item">
                <div class="chart-item-title"><#=area_name#> 异常项月趋势</div>
                <div id="alarmedFamilyChart" class="edu-chart"></div>
            </div>
        </div>
        <div class="chart-page-content alarmed-family-simple clearfix">
        
        </div>

    </div>
    <div class="data-search-box" style="display:none;">
    </div>
</script>
<!--预警监控存在异常的贫困户监控异常项统计表外层-->
<script id="tplAlarmedFamilyDetailSearchSimple" type="text/html">
    <div class="chart-item">
        <div class="chart-item-title"><#=table_title#></div>
        <!-- <a href="javascript:void(0);" class="check-all js-search-diff">查看全部 &gt;</a> -->
        <div class="analyse-total-table alarmed-family-table">

        </div>
    </div>
</script>
<!--预警监控存在异常的贫困户监控异常项统计表-->
<script id="tplAlarmedFamilyTable" type="text/html">
    <a class="export-btn in-table" target="_blank"  href="<#=export_data.url#>?level=<#=export_data.level#>&id=<#=export_data.id#>&scope=<#=export_data.scope#>&year=<#=export_data.year#>&month=<#=export_data.month#>&dept=<#=export_data.dept#>" >数据导出</a>    
    <table class="analyse-tb ">
        <thead>
        <tr>
            <td>
                <span>行政区域</span>
            </td>
            <td><span>排序</span></td>
            <td>
                <span>建档立卡比对项数</span>
            </td>
            <td>
                <span>行业部门记录数</span>
            </td>
            <td>
               <span>异常项数</span>
            </td>
            <td>
                比例
                <i class="ico-quest js-quest">
                    <div class="tip">
                        <table>
                            <tr>
                                <td>数据来源：</td>
                                <td><#=tips.rate.source#></td>
                            </tr>
                            <tr>
                                <td>统计方法：</td>
                                <td><#=tips.rate.stat_method#></td>
                            </tr>
                        </table>
                    </div>
                </i>
            </td>
        </tr>
        </thead>
        <tbody>
        <#for(var i=0,len=table.list.length;i<len;i++){#>
        <tr>
            <td><#=table.list[i].area_name#></td>
            <td><#=table.list[i].order#></td>
            <td><#=table.list[i].amount_total#></td>
            <td><#=table.list[i].amount_industry_dept_records#></td>
            <td>
                <a href="javascript:void(0);" class="js-search-diff"
                   data-area-id="<#=table.list[i].area_id#>" data-area-level="<#=table.list[i].area_level#>" data-area-name="<#=table.list[i].area_name#>">
                    <#=table.list[i].amount_diff#>
                </a>
            </td>
            <td><#=table.list[i].rate#></td>
        </tr>
        <#}#>
            <tr>
                <td>合计</td>
                <td>-</td>
                <td><#=table.total.amount_total#></td>
                <td><#=table.total.amount_industry_dept_records#></td>
                <td><a href="javascript:void(0);" class="js-search-diff"><#=table.total.amount_diff#></a></td>
                <td><#=table.total.rate#></td>
            </tr>
        </tbody>
    </table>
      </div>
</script>
<!--预警监控存在异常的贫困户监控搜索详情页搜索-->
<script id="tplAlarmedFamilyDetailSearch" type="text/html">
    <table class="analyse-tb">
        <thead>
        <tr>
            <td>序号</td>
            <td>市</td>
            <td>县</td>
            <td>镇</td>
            <td>村</td>
            <td>户主姓名</td>
            <td>家庭成员姓名</td>
            <td>异常描述</td>
            <td>行业部门名称</td>
        </tr>
        </thead>
    </table>
    <div class="jsp-search-content">
        <table class="analyse-tb">
            <tbody>
            <#include('tplAlarmedFamilyDetailSearchItems')#>
            </tbody>
        </table>
    </div>
</script>
<!--预警监控存在异常的贫困户监控搜索详情页搜索-->
<script id="tplAlarmedFamilyDetailSearchItems" type="text/html">
    <#for(var i=0,len=table.list.length;i<len;i++){#>
    <tr>
        <td><#=table.list[i].order#></td>
        <td><#=table.list[i].city_name#></td>
        <td><#=table.list[i].county_name#></td>
        <td><#=table.list[i].town_name#></td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].country_id?'':'no-detail'#> js-redirect-country"
               data-country-id="<#=table.list[i].country_id#>">
                <#=table.list[i].country_name#>
            </a>
        </td>
        <td>
            <a href="javascript:void(0);" class="<#=table.list[i].family_id?'':'no-detail'#> js-redirect-family"
               data-family-id="<#=table.list[i].family_id#>">
                <#=table.list[i].house_holder_name#>
            </a>
        </td>
        <td><#=table.list[i].member_name#></td>
        <td><#=table.list[i].alarmed_desc#></td>
        <td><#=table.list[i].department_name#></td>
    </tr>
    <#}#>
</script>
