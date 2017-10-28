<!--贫困分析-->
<div id="popupPoorMonitor" class="pop-poor-monitor-wrap">
    <section id="monitorHeader" class="monitor-header">
       
    </section>
   <section id="monitorContent" class="monitor-content">
       
   </section>
    <section id="monitorTree" class="monitor-tree">

    </section>
</div>

<!-- 一级弹窗容器模板 -->
<script id="tplMonitorPopupWin" type="text/html">
    <div class="pop-poor-monitor-wrap">
        <section class="monitor-header">
        </section>
        <section class="monitor-content">
        </section>
        <section class="monitor-tree">
        </section
    </div>
</script>

<!-- 一级弹窗内容通用模板 -->
<script id="tplMonitorPageContentCommon" type="text/html">
    <div class="page-content-wrap">
        <section class="check-area-wrap check-area clearfix">

        </section>
        <section class="chart-table-wrap">

        </section>
    </div>
</script>

<div id="exportWrap" class="export-wrap">
    <section id="exportContent" class="export-content">
      
    </section>
</div>
<!--导出模板-->
<script id="tplExportContent" type="text/html">
     <p class="tips">数据导出中......</p>
     <div class="export-bar-wrap">
            <div class="export-bar">
                <p style="width:<#=progress#>%;" class="export-bar-process"></p>
            </div>
            <em class="rate-num"><#=progress#>%</em>
     </div>
</script>

<!--header模板-->
<script id="tplMonitorHeader" type="text/html">
     <h2 class="header-title" data-win-type=<#= TYPE #> ><#=TITLE#></h2>
     <div class="tab-year tab-switch"></div>
     <ul class="monitor-nav <#=TAB.length > 8 ? 'tabs-s' : '' #>">
        <#for(var i = 0;i < TAB.length;i++){#>
        <#if(i==0){#>  
        <li class="active" data-tab-type=<#=TAB[i].VALUE #> ><strong><#=TAB[i].NAME#></strong></li>
        <#}else{#>
        <li data-tab-type=<#=TAB[i].VALUE #> 
           data-config-order=<#=TAB[i].CONFIG_ORDER#>>
           <strong><#=TAB[i].NAME#></strong>
        </li>
       <#}#>
       <#}#>
   </ul>
   <div id="secondTabTitle" class="second-tab-title hide" data-tpl-id="" data-tabtype="" data-url=""></div>
   <#if(hasBack !== false){#>
       <i class="yellow-btn back-btn js-monitor-close">返回</i>
   <#}#>

    <!-- 下拉-下载列表 -->
   <div id="headDownloadTable" class="select-box js-select-box download-select hide">
   
  </div>
  
</script>

<!--头部下载表格列表模板-->
<script type="text/html" id="tplHeadDownloadTable">
     <i class="icon icon-down"></i>
    <strong>贫困人口统计表</strong>
    <i class="arrow"></i>
    <div class="select-list js-select-list" style="display: none; ">
      <div>
        <ul>
          <# for(var i=0;i< download_list.list.length;i++){#>
          <# var item=download_list.list[i]#>
          <li class="" title= <#=item.name#> data-export-url=<#=item.export_url#>>
            <#=item.name#>
          </li>
          <#}#>
        </ul>
      </div>
    </div>
</script>

<!--树模板-->
<script type="text/html" id="tplDataMonitorTree">
    <div class="tree-container">
        <div class="tree-wrapper">
            <ul class="tree ztree tree-ul">
            </ul>
        </div>
        <div class="ico-quest">
            <div class="tip"></div>
        </div>
    </div>
</script>

<!--二级弹窗-->
<div id="popupPoorMonitorSecondWrap" class="pop-poor-monitor-second-wrap ">
</div>

<script id="tplSecondPopup" type="text/html">
    <div id="popupPoorMonitorSecond" class="pop-poor-monitor-second">
        <div class="btn-close js-second-popup-close"></div>
        <h2 class="second-popup-header second-popup-title" id="secondPopupTitle"></h2>
        <div class="page-content-wrap">
            <section id="basicInfo" class="basic-info-wrap">

            </section>

            <section id="paramsCheckArea" class="check-area check-area-wrap">

            </section>

            <section id="secondPopupTable" class="config-table second-popup-table chart-table-wrap">
              
            </section>
        </div>
    </div>
</script>

<!--二级弹窗容器模板 -->
<script id="tplMonitorSecondPopupWin" type="text/html">
	<div class="pop-poor-monitor-second-wrap">
	</div>
</script>

<!--二级弹窗基本信息-->
<script id="tplSecondPopupBasicInfo" type="text/html">
     <#if(hasSelectArea){#>
          <div class="select-area clearfix">
              <div class="select-row select-year">
                  <label>年份：</label>
                  <span id="selectYear">
                      <div class="select-box js-select-box" >
                          <#if(years.selected_value==""){#>
                          <span data-value="">全部</span>
                          <#}else{#>
                          <span data-value=<#=years.selected_value#>><#=years.selected_value#></span>
                          <#}#>
                          <i class="arrow"></i>
                          <div class="select-list js-select-list">
                              <div>
                                  <ul>
                                      <#for(var i=0;i<years.available_years.length;i++){#>
                                      <li class="<#= (years.available_years[i].value==years.selected_value) ? 'active':''#>" data-year=<#=years.available_years[i].value#>>
                                         <#=years.available_years[i].name#>
                                     </li>
                                     <#}#>
                                 </ul>
                             </div>
                         </div>
                     </div>
                 </span>
                  <!-- 月份 -->
                  <#if(month){#>
                  <span id="selectMonth">
                      <div class="select-box js-select-box" >
                          <#if(month.selected_value==""){#>
                          <span data-value="">全部</span>
                          <#}else{#>
                          <span data-value=<#=month.selected_value#>>
                          <#for(var i=0;i<month.available_month.length;i++){#>
                                     <#= (month.available_month[i].value==month.selected_value) ? month.available_month[i].name : "" #>
                          <#}#>
                          </span>
                          <#}#>
                          <i class="arrow"></i>
                          <div class="select-list js-select-list">
                              <div>
                                  <ul>
                                      <#for(var i=0;i<month.available_month.length;i++){#>
                                      <li class="<#= (month.available_month[i].value==month.selected_value) ? 'active':''#>" data-month=<#=month.available_month[i].value#>>
                                         <#=month.available_month[i].name#>
                                     </li>
                                     <#}#>
                                 </ul>
                             </div>
                         </div>
                     </div>
                 </span>
                 <#}#>
             </div>
             <div class="select-row area-barrio">
                 <label>区域类型：</label>
                 <span id="areaBarrio">
                     <#for(var i = 0, len = area_path.length; i < len; i++) {#>
                     <div class="select-box js-select-box <#=(area_path[i].authority===false)?'forbid':''#>" level="<#=area_path[i].level#>" selected_id="<#=area_path[i].selected_id#>">
                         <span ><#=area_path[i].selected_name#></span>
                         <i class="arrow"></i>
                         <div class="select-list js-select-list">
                             <div>
                                 <ul>
                                     <#for(var j = 0, len2 = area_path[i].list.length; j<len2; j++) {#>
                                     <li title="<#=area_path[i].list[j].name#>" class="<#= (area_path[i].list[j].id==area_path[i].selected_id) ? 'active':''#>" data-id="<#=area_path[i].list[j].id#>">
                                        <#=area_path[i].list[j].name#>
                                    </li>
                                    <#}#>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <#}#>
                </span>
            </div>
        </div>
     <#}#>
</script>

<!-- 公用(后台配置)选择区域模板 -->
<script type="text/html" id="tplConfigCheckArea">
    <table class="check-area-table">
       <#for(var i=0;i< params.length;i++){#>
       <#var item=params[i]#>
       <tr class="<#=item.type#>-row clearfix" data-key=<#=item.key#> >
           <td class="check-area-label">
              <strong><#=item.label#>：</strong>
           </td>
           <td class="check-area-values">
              <p>
                  <#if(item.type=="check"){#>
                      <# if(item.has_check_all) { #>
                          <a class="<#=item.type#> <#=item.selected_value.length === item.values.length ? 'active ' : ' '#>" data-check-all>全选</a>
                      <# } #>
                  <# } #>
              <#for(var j=0;j< item.values.length;j++){#>
                  <#var eachValue=item.values[j]#>
                  <#if(item.type=="radio"){#>
                  <a class="<#=item.type#> <#=eachValue.disabled?'disabled':''#> <#= item.selected_value==eachValue.value?'active':'' #>" data-value=<#=eachValue.value#>  ><#=eachValue.label#></a>
                  <#}else{#>
                      <#var tag=false#>
                      <#for(var n=0;n< item.selected_value.length;n++){#>
                          <#if(eachValue.value==item.selected_value[n]){#>
                          <#var tag=true#>
                          <#}#>
                      <#}#>
                      <#if(tag){#>
                      <a class="<#=item.type#> active" data-value=<#=eachValue.value#>     ><#=eachValue.label#></a>
                      <#}else{#>
                      <a class="<#=item.type#>" data-value=<#=eachValue.value#>     ><#=eachValue.label#></a>
                      <#}#>
                  <#}#>
              <#}#>
              </p>
           </td>
       </tr>
       <#}#>
    </table>
</script>

<!-- 公用(后台配置)表格模板 -->
<script type="text/html" id="tplConfigTable">
    <div class="table-title">
        <strong><#=title#></strong>
        <p class="btn-area">
            <a class="yellow-btn export-btn" data-export-url="<#=export_url#>">导出</a>
            <#if(hasDetailBtn){#>
            <a class="yellow-btn detail-btn">明细</a>
            <#}#>
        </p>
    </div>
    <div class="content clearfix">
        <div >
            <div class="monitor-tb-wrap">
               <table class="monitor-tb">
                   <thead>
                       <#for(var i=0;i < head.length;i++){#>
                       <tr>
                           <#for(var j=0;j < head[i].length;j++){#>
                           <#var item=head[i][j]#>
                           <td 
                           <#=item.colspan?"colspan="+item.colspan:""#>
                           <#=item.rowspan?"rowspan="+item.rowspan:""#>
                           ><#=# item.content#></td>
                           <#}#>
                       </tr>
                       <#}#>
                   </thead>
                   <tbody>
                   <#for(var i=0;i < body.length;i++){#>
                         <tr>
                           <#for(var j=0;j < body[i].length;j++){#>
                           <#var item=body[i][j]#>
                           <td  
                           <#=item.colspan?"colspan="+item.colspan:""#>
                           <#=item.rowspan?"rowspan="+item.rowspan:""#>
                           <#=item.action_type?"data-action-type="+item.action_type:""#>
                           <#if(item.action_type=="family_file"){#>
                               <#=item.action_data?"data-action-family-id="+item.action_data.family_id:""#>
                           <#}else if(item.action_type=="file_detail"){#>
                               <#=item.action_data?"data-action-file-id="+item.action_data.file_id:""#>
                           <#}else{#>
                           <#=item.action_data?"data-action-area-level="+item.action_data.area_level:""#>
                           <#=item.action_data?"data-action-area-id="+item.action_data.area_id:""#>
                           <#}#>
                           ><#=# item.content#></td>
                           <#}#>
                       </tr>    
                        <#}#>
                   </tbody>
               </table>
            </div>
            <#if(notes){#>
            <p class="table-notes">
               <#=notes#>
            </p>
            <#}#>
            <#if(pagination){#>
            <div class="table-pagination">
                <a class="icon arrow-first"></a>
                <a class="icon arrow-left"></a>
                <strong class="cur-page">第 <#=pagination.page_cur#> 页</strong>
                <a class="icon arrow-right"></a>
                <a class="icon arrow-end"></a>
                <em>共<#=pagination.page_total#>页 总计<#=pagination.amount_total#>行记录</em>
            </div>
            <#}#>
        </div>
    </div>
</script>

<!--后台配置排序-->
<script id="tplConfigOrder" type="text/html">
    <div class="page-content-wrap">
        <section id="configOrderCheckArea" class="check-area check-area-wrap">
           
        </section>
        <section id="configOrderChartTable" class="chart-table-wrap">
        
        </section>
    </div>
</script>
<!--后台配置排序表-->
<script type="text/html" id="tplConfigOrderChartTable">
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- =========================具体页面============================== -->

<!-- ===========扶贫对象监控==============-->
<!--扶贫对象监控-变动管理-->
<script id="tplPoorChangeManagement" type="text/html">
    <div class="page-content-wrap">
        <section id="poorChangeManagementChartTable">
        
        </section>
    </div>
</script>
<!--扶贫对象监控-变动管理图表-->
<script type="text/html" id="tplPoorChangeManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">当年贫困人口变化</div>
                <!-- <div class="chart-item-title">当年贫困人口变化（年初数：<#=amount_poor_changed_yearly_begin#>）</div> -->
                <div id="poorChangeManagementChartPoorChangedYearly" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">累计贫困人口变化</div>
                <!-- <div class="chart-item-title">累计贫困人口变化（认定数：<#=amount_poor_changed_accumulated#>）</div> -->
                <div id="poorChangeManagementChartPoorChangedAccumulated" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--扶贫对象监控-变动分析-->
<script id="tplPoorChangeAnalysis" type="text/html">
    <div class="page-content-wrap">
        <section id="poorChangeAnalysisCheckArea" class="check-area clearfix">
           
        </section>
        <section id="poorChangeAnalysisChartTable">
        
        </section>
    </div>
</script>
<!--扶贫对象监控-变动分析图表-->
<script type="text/html" id="tplPoorChangeAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">贫困人口趋势</div>
                <div id="poorChangeAnalysisChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--扶贫对象监控-变动分析图表-自然增减户-->
<script type="text/html" id="tplPoorChangeAnalysisChartTableNaturalChange">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">贫困人口趋势</div>
                <ul class="chart-tab" data-active="poorChangeAnalysisChartTrendAdd">
                    <li data-target="poorChangeAnalysisChartTrendAdd">增加</li>
                    <li data-target="poorChangeAnalysisChartTrendCut">减少</li>
                </ul>
                <section class="charts-wrap js-tab-charts-wrap">
                    <div id="poorChangeAnalysisChartTrendAdd" class="chart"></div>
                    <div id="poorChangeAnalysisChartTrendCut" class="chart"></div>
                </section>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========脱贫成效监控==============-->
<!--脱贫成效监控-计划管理-->
<script id="tplResultPlanManagement" type="text/html">
    <div class="page-content-wrap">
        <section id="resultPlanManagementChartTable">
        
        </section>
    </div>
</script>
<!--脱贫成效监控-计划管理图表-->
<script type="text/html" id="tplResultPlanManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">累计趋势</div>
                <div id="resultPlanManagementChartAccumulated" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">当年度统计</div>
                <div id="resultPlanManagementChartCurYear" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--脱贫成效监控-成效分析-->
<script id="tplResultResultAnalysis" type="text/html">
    <div class="page-content-wrap">
        <section id="resultResultAnalysisCheckArea" class="check-area clearfix">
           
        </section>
        <section id="resultResultAnalysisChartTable">
        
        </section>
    </div>
</script>
<!--脱贫成效监控-成效分析图表-->
<script type="text/html" id="tplResultResultAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-33p">
                <div class="chart-item-title">按贫困属性</div>
                <div id="resultResultAnalysisChartByPoorAttribute" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">按劳动能力属性</div>
                <div id="resultResultAnalysisChartByLaborAttribute" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">按村属性</div>
                <div id="resultResultAnalysisChartByScope" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--脱贫成效监控-未脱贫分析 -->
<script id="tplResultNotSuccessAnalysis" type="text/html">
    <div class="page-content-wrap">
        <section id="resultNotSuccessAnalysisCheckArea" class="check-area clearfix">
           
        </section>
        <section id="resultNotSuccessAnalysisChartTable">
        
        </section>
    </div>
</script>
<!--脱贫成效监控-未脱贫分析 图表-->
<script type="text/html" id="tplResultNotSuccessAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">月趋势</div>
                <div id="resultNotSuccessAnalysisChartNotSuccess" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
          <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========人均可支配收入监控==============-->
<!--人均可支配收入监控-收入管理-->
<script id="tplAverageIncomeIncomeManagement" type="text/html">
    <div class="page-content-wrap">
        <section id="averageIncomeIncomeManagementChartTable">
        
        </section>
    </div>
</script>
<!--人均可支配收入监控-收入管理图表-->
<script type="text/html" id="tplAverageIncomeIncomeManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">人均可支配收入年趋势</div>
                <div id="averageIncomeIncomeManagementChartAverageIncomeTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">收入区域分布</div>
                <div id="averageIncomeIncomeManagementChartIncomeAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--人均可支配收入监控-脱贫户分析-->
<script id="tplAverageIncomeSuccessAnalysis" type="text/html">
    <div class="page-content-wrap">
        <section id="averageIncomeSuccessAnalysisCheckArea" class="check-area clearfix">
           
        </section>
        <section id="averageIncomeSuccessAnalysisChartTable">
        
        </section>
    </div>
</script>
<!--人均可支配收入监控-脱贫户分析图表-->
<script type="text/html" id="tplAverageIncomeSuccessAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">人均可支配收入（元）</div>
                <div id="averageIncomeSuccessAnalysisChartAverageDisposableIncome" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">收入构成（万元）</div>
                <div class="chart-item-extra">
                    <a id="linkTransferredIncome" class="link-a js-link-tab">查看转移性收入 &gt;</a>
                </div>
                <div id="averageIncomeSuccessAnalysisChartIncomeComposition" class="chart "></div>
            </div>
        </div>
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">人均可支配收入分布（户数）</div>
                <div id="averageIncomeSuccessAnalysisChartIncomeDistribution" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">支出构成（万元）</div>
                <div class="chart-item-extra">
                    <a id="linkTransferredPayment" class="link-a js-link-tab">查看转移性支出 &gt;</a>
                </div>
                <div id="averageIncomeSuccessAnalysisChartPaymentComposition" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--人均可支配收入监控-脱贫户分析-转移性收入-->
<script id="tplAverageIncomeSuccessAnalysisTransferredIncome" type="text/html">
    <div class="page-content-wrap">
        <section id="averageIncomeSuccessAnalysisTransferredIncomeCheckArea" class="check-area clearfix">

        </section>
        <section id="averageIncomeSuccessAnalysisTransferredIncomeChartTable">

        </section>
    </div>
</script>
<!--人均可支配收入监控-脱贫户分析-转移性收入图表-->
<script type="text/html" id="tplAverageIncomeSuccessAnalysisTransferredIncomeChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">转移性收入构成（万元）</div>
                <div id="averageIncomeSuccessAnalysisTransferredIncomeChartComposition" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--人均可支配收入监控-脱贫户分析-转移性支出-->
<script type="text/html" id="tplAverageIncomeSuccessAnalysisTransferredPayment">
    <div class="page-content-wrap">
        <section id="averageIncomeSuccessAnalysisTransferredPaymentCheckArea" class="check-area clearfix">

        </section>
        <section id="averageIncomeSuccessAnalysisTransferredPaymentChartTable">

        </section>
    </div>
</script>
<!--人均可支配收入监控-脱贫户分析-转移性支出图表-->
<script type="text/html" id="tplAverageIncomeSuccessAnalysisTransferredPaymentChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">转移性支出构成（万元）</div>
                <div id="averageIncomeSuccessAnalysisTransferredPaymentChartComposition" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--人均可支配收入监控-贫困户分析-->
<script id="tplAverageIncomePoorAnalysis" type="text/html">
    <div class="page-content-wrap">
        <section id="averageIncomePoorAnalysisCheckArea" class="check-area clearfix">
           
        </section>
        <section id="averageIncomePoorAnalysisChartTable">
        
        </section>
    </div>
</script>
<!--人均可支配收入监控-贫困户分析图表-->
<script type="text/html" id="tplAverageIncomePoorAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">人均可支配收入（元）</div>
                <div id="averageIncomePoorAnalysisChartAverageDisposableIncome" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">收入构成（万元）</div>
                <div class="chart-item-extra">
                    <a id="poorAnalysisToTransferredIncome" class="link-a js-link-tab">查看转移性收入 &gt;</a>
                </div>
                <div id="averageIncomePoorAnalysisChartIncomeComposition" class="chart "></div>
            </div>
        </div>
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">人均可支配收入分布（户数）</div>
                <div id="averageIncomePoorAnalysisChartIncomeDistribution" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">支出构成（万元）</div>
                <div class="chart-item-extra">
                    <a id="poorAnalysisToTransferredPayment" class="link-a js-link-tab">查看转移性支出 &gt;</a>
                </div>
                <div id="averageIncomePoorAnalysisChartPaymentComposition" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--人均可支配收入监控-贫困户分析-转移性收入-->
<script type="text/html" id="tplAverageIncomePoorAnalysisTransferredIncome">
    <div class="page-content-wrap">
        <section id="averageIncomePoorAnalysisTransferredIncomeCheckArea" class="check-area clearfix">

        </section>
        <section id="averageIncomePoorAnalysisTransferredIncomeChartTable">

        </section>
    </div>
</script>
<!--人均可支配收入监控-贫困户分析-转移性收入图表-->
<script type="text/html" id="tplAverageIncomePoorAnalysisTransferredIncomeChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">转移性收入构成（万元）</div>
                <div id="averageIncomePoorAnalysisTransferredIncomeChartComposition" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--人均可支配收入监控-贫困户分析-转移性支出-->
<script type="text/html" id="tplAverageIncomePoorAnalysisTransferredPayment">
    <div class="page-content-wrap">
        <section id="averageIncomePoorAnalysisTransferredPaymentCheckArea" class="check-area clearfix">

        </section>
        <section id="averageIncomePoorAnalysisTransferredPaymentChartTable">

        </section>
    </div>
</script>
<!--人均可支配收入监控-贫困户分析-转移性支出图表-->
<script type="text/html" id="tplAverageIncomePoorAnalysisTransferredPaymentChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">转移性支出构成（万元）</div>
                <div id="averageIncomePoorAnalysisTransferredPaymentChartComposition" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>


<!-- ===========低五保政策落实监控==============-->
<!--低五保政策落实监控-落实管理-->
<script type="text/html" id="tplFiveLowImplementManagement">
    <div class="page-content-wrap">
        <section id="fiveLowImplementManagementChartTable">

        </section>
    </div>
</script>
<!--低五保政策落实监控-落实管理-图表-->
<script type="text/html" id="tplFiveLowImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">低保五保政策落实(户数)</div>
                <div id="fiveLowImplementManagementChartTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">低保五保政策落实(人数)</div>
                <div id="fiveLowImplementManagementChartCurrent" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--低五保政策落实监控-落实分析-->
<script type="text/html" id="tplFiveLowImplementAnalysis">
    <div class="page-content-wrap">
        <section id="fiveLowImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="fiveLowImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--低五保政策落实监控-落实分析-图表-->
<script type="text/html" id="tplFiveLowImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">低保五保政策落实趋势(人数)</div>
                <div id="fiveLowImplementAnalysisChartTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">当前低保五保政策落实(人数)</div>
                <div id="fiveLowImplementAnalysisChartCurrent" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========教育政策落实监控==============-->
<!--教育政策落实监控-落实管理-->
<script type="text/html" id="tplEduImplementManagement">
    <div class="page-content-wrap">
        <section id="eduImplementManagementChartTable">

        </section>
    </div>
</script>
<!--教育政策落实监控-落实管理-图表-->
<script type="text/html" id="tplEduImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">义务教育政策落实趋势(人数)</div>
                <div id="eduImplementManagementChartTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">当前教育政策落实(人数)</div>
                <div id="eduImplementManagementChartCurrent" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--教育政策落实监控-落实分析-->
<script type="text/html" id="tplEduImplementAnalysis">
    <div class="page-content-wrap">
        <section id="eduImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="eduImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--教育政策落实监控-落实分析-图表-->
<script type="text/html" id="tplEduImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">教育政策落实趋势(人数)</div>
                <div id="eduImplementAnalysisChartTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">当前教育政策落实(人数)</div>
                <div id="eduImplementAnalysisChartCurrent" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========医疗政策落实监控==============-->
<!--医疗政策落实监控-落实管理-->
<script type="text/html" id="tplMedicalPolicyImplementManagement">
    <div class="page-content-wrap">
        <section id="medicalPolicyImplementManagementChartTable">

        </section>
    </div>
</script>
<!--医疗政策落实监控-落实管理-图表-->
<script type="text/html" id="tplMedicalPolicyImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">医疗政策落实趋势(人数)</div>
                <div id="medicalPolicyImplementManagementChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--医疗政策落实监控-落实分析-->
<script type="text/html" id="tplMedicalPolicyImplementAnalysis">
    <div class="page-content-wrap">
        <section id="medicalPolicyImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="medicalPolicyImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--医疗政策落实监控-落实分析-图表-->
<script type="text/html" id="tplMedicalPolicyImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">医疗政策落实趋势(人数)</div>
                <div id="medicalPolicyImplementAnalysisChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========住房政策落实监控==============-->
<!--住房政策落实监控-落实管理-->
<script type="text/html" id="tplHouseImplementManagement">
    <div class="page-content-wrap">
        <section id="houseImplementManagementChartTable">

        </section>
    </div>
</script>
<!--住房政策落实监控-落实管理-图表-->
<script type="text/html" id="tplHouseImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">C/D级危房及改造趋势(户数)</div>
                <div id="houseImplementManagementChartTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">C/D级危房改造进度(户数)</div>
                <div id="houseImplementManagementChartProgress" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--住房政策落实监控-落实分析-->
<script type="text/html" id="tplHouseImplementAnalysis">
    <div class="page-content-wrap">
        <section id="houseImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="houseImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--住房政策落实监控-落实分析-图表-->
<script type="text/html" id="tplHouseImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">危房进度趋势(户数)</div>
                <div id="houseImplementAnalysisChartTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">各级别危房改造(户数)</div>
                <div id="houseImplementAnalysisChartLevels" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========道路硬底化监控==============-->
<!--道路硬底化监控-落实管理-->
<script type="text/html" id="tplRoadImplementManagement">
    <div class="page-content-wrap">
        <section id="roadImplementManagementChartTable">

        </section>
    </div>
</script>
<!--道路硬底化监控-落实管理-图表-->
<script type="text/html" id="tplRoadImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">落实年趋势(公里)</div>
                <div id="roadImplementManagementChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--道路硬底化监控-落实分析-->
<script type="text/html" id="tplRoadImplementAnalysis">
    <div class="page-content-wrap">
        <section id="roadImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="roadImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--道路硬底化监控-落实分析-图表-->
<script type="text/html" id="tplRoadImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">落实趋势(公里)</div>
                <div id="roadImplementAnalysisChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========安全饮水监控==============-->
<!--安全饮水监控-落实管理-->
<script type="text/html" id="tplWaterImplementManagement">
    <div class="page-content-wrap">
        <section id="waterImplementManagementChartTable">

        </section>
    </div>
</script>
<!--安全饮水监控-落实管理-图表-->
<script type="text/html" id="tplWaterImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">落实年趋势(户)</div>
                <div id="waterImplementManagementChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--安全饮水监控-落实分析-->
<script type="text/html" id="tplWaterImplementAnalysis">
    <div class="page-content-wrap">
        <section id="waterImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="waterImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--安全饮水监控-落实分析-图表-->
<script type="text/html" id="tplWaterImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">落实趋势(户)</div>
                <div id="waterImplementAnalysisChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========生活用电监控==============-->
<!--生活用电监控-落实管理-->
<script type="text/html" id="tplElectricityImplementManagement">
    <div class="page-content-wrap">
        <section id="electricityImplementManagementChartTable">

        </section>
    </div>
</script>
<!--生活用电监控-落实管理-图表-->
<script type="text/html" id="tplElectricityImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">落实年趋势</div>
                <div id="electricityImplementManagementChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--生活用电监控-落实分析-->
<script type="text/html" id="tplElectricityImplementAnalysis">
    <div class="page-content-wrap">
        <section id="electricityImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="electricityImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--生活用电监控-落实分析-图表-->
<script type="text/html" id="tplElectricityImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">落实趋势</div>
                <div id="electricityImplementAnalysisChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========医疗设施监控==============-->
<!--医疗设施监控-落实管理-->
<script type="text/html" id="tplMedicalFacilityImplementManagement">
    <div class="page-content-wrap">
        <section id="medicalFacilityImplementManagementChartTable">

        </section>
    </div>
</script>
<!--医疗设施监控-落实管理-图表-->
<script type="text/html" id="tplMedicalFacilityImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">卫生室建设年趋势</div>
                <div id="medicalFacilityImplementManagementChartTrendStation" class="chart"></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">执业医师年趋势</div>
                <div id="medicalFacilityImplementManagementChartTrendDoctor" class="chart"></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--医疗设施监控-落实分析-->
<script type="text/html" id="tplMedicalFacilityImplementAnalysis">
    <div class="page-content-wrap">
        <section id="medicalFacilityImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="medicalFacilityImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--医疗设施监控-落实分析-图表-->
<script type="text/html" id="tplMedicalFacilityImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">落实趋势</div>
                <ul class="chart-tab" data-active="medicalFacilityImplementAnalysisChartTrendStation">
                    <li data-target="medicalFacilityImplementAnalysisChartTrendStation">卫生室</li>
                    <li data-target="medicalFacilityImplementAnalysisChartTrendDoctor">执业医师</li>
                </ul>
                <section class="charts-wrap js-tab-charts-wrap">
                    <div id="medicalFacilityImplementAnalysisChartTrendStation" class="chart"></div>
                    <div id="medicalFacilityImplementAnalysisChartTrendDoctor" class="chart"></div>
                </section>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========通广播电视监控==============-->
<!--通广播电视监控-落实管理-->
<script type="text/html" id="tplBroadcastImplementManagement">
    <div class="page-content-wrap">
        <section id="broadcastImplementManagementChartTable">

        </section>
    </div>
</script>
<!--通广播电视监控-落实管理-图表-->
<script type="text/html" id="tplBroadcastImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">落实年趋势</div>
                <div id="broadcastImplementManagementChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--通广播电视监控-落实分析-->
<script type="text/html" id="tplBroadcastImplementAnalysis">
    <div class="page-content-wrap">
        <section id="broadcastImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="broadcastImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--通广播电视监控-落实分析-图表-->
<script type="text/html" id="tplBroadcastImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">落实趋势</div>
                <div id="broadcastImplementAnalysisChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========网络覆盖监控==============-->
<!--网络覆盖监控-落实管理-->
<script type="text/html" id="tplNetImplementManagement">
    <div class="page-content-wrap">
        <section id="netImplementManagementChartTable">

        </section>
    </div>
</script>
<!--网络覆盖监控-落实管理-图表-->
<script type="text/html" id="tplNetImplementManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">落实年趋势</div>
                <div id="netImplementManagementChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--网络覆盖监控-落实分析-->
<script type="text/html" id="tplNetImplementAnalysis">
    <div class="page-content-wrap">
        <section id="netImplementAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="netImplementAnalysisChartTable">

        </section>
    </div>
</script>
<!--网络覆盖监控-落实分析-图表-->
<script type="text/html" id="tplNetImplementAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole">
                <div class="chart-item-title">落实趋势</div>
                <div id="netImplementAnalysisChartTrend" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!-- ===========贫困分析==============-->
<!--贫困分析-->
<script type="text/html" id="tplPoorAnalysisPoorAnalysis">
    <div class="page-content-wrap">
        <section id="poorAnalysisPoorAnalysisCheckArea" class="check-area clearfix">

        </section>
        <section id="poorAnalysisPoorAnalysisChartTable">

        </section>
    </div>
</script>
<!--贫困分析-图表-->
<script type="text/html" id="tplPoorAnalysisPoorAnalysisChartTable">
    <a class="yellow-btn detail-btn">明细</a>
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-33p">
                <div class="chart-item-title">贫困户属性</div>
                <div id="poorAnalysisPoorAnalysisChartPoorAttribute" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">致贫原因</div>
                <div id="poorAnalysisPoorAnalysisChartPoorReason" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">收入状况</div>
                <div id="poorAnalysisPoorAnalysisChartIncome" class="chart "></div>
            </div>
        </div>
        <div class="chart-row">
            <div class="chart-item chart-33p">
                <div class="chart-item-title">年龄</div>
                <div id="poorAnalysisPoorAnalysisChartAge" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">性别</div>
                <div id="poorAnalysisPoorAnalysisChartSex" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">民族</div>
                <div id="poorAnalysisPoorAnalysisChartNation" class="chart "></div>
            </div>
        </div>
        <div class="chart-row">
            <div class="chart-item chart-33p">
                <div class="chart-item-title">政治面貌</div>
                <div id="poorAnalysisPoorAnalysisChartPoliticalAffiliation" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">文化程度</div>
                <div id="poorAnalysisPoorAnalysisChartEduStatus" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">健康状况</div>
                <div id="poorAnalysisPoorAnalysisChartHealthStatus" class="chart "></div>
            </div>
        </div>
        <div class="chart-row">
            <div class="chart-item chart-33p">
                <div class="chart-item-title">劳动力状况</div>
                <div id="poorAnalysisPoorAnalysisChartLaborStatus" class="chart "></div>
            </div>
            <div class="chart-item chart-33p">
                <div class="chart-item-title">务工状况</div>
                <div id="poorAnalysisPoorAnalysisChartEmploymentStatus" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>


<!-- ===========预警监控==============-->
<!--贫困识别监控-异常监控-->
<script type="text/html" id="tplAlarmedPoorExceptionMonitor">
    <div class="page-content-wrap">
        <section id="alarmedPoorExceptionMonitorCheckArea" class="check-area clearfix">

        </section>
        <section id="alarmedPoorExceptionMonitorChartTable">

        </section>
    </div>
</script>
<!--贫困识别监控-异常监控-图表-->
<script type="text/html" id="tplAlarmedPoorExceptionMonitorChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">各类异常占比</div>
                <div id="alarmedPoorExceptionMonitorChartExceptionRatio" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">地区分布图</div>
                <div id="alarmedPoorExceptionMonitorChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--建档立卡异常记录监控-异常监控-->
<script type="text/html" id="tplAlarmedRecordsExceptionMonitor">
    <div class="page-content-wrap">
        <section id="alarmedRecordsExceptionMonitorCheckArea" class="check-area clearfix">

        </section>
        <section id="alarmedRecordsExceptionMonitorChartTable">

        </section>
    </div>
</script>
<!--建档立卡异常记录监控-异常监控-图表-->
<script type="text/html" id="tplAlarmedRecordsExceptionMonitorChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">各类异常占比</div>
                <div id="alarmedRecordsExceptionMonitorChartExceptionRatio" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">地区分布图</div>
                <div id="alarmedRecordsExceptionMonitorChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--帮扶资金监控-资金监控-->
<script type="text/html" id="tplAlarmedMoneyMoneyMonitor">
    <div class="page-content-wrap">
        <section id="alarmedMoneyMoneyMonitorCheckArea" class="check-area clearfix">

        </section>
        <section id="alarmedMoneyMoneyMonitorChartTable">

        </section>
    </div>
</script>
<!--帮扶资金监控-资金监控-图表-->
<script type="text/html" id="tplAlarmedMoneyMoneyMonitorChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">帮扶资金小于<30万的相对贫困村</div>
                <div id="alarmedMoneyMoneyMonitorChartPoorCountry" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">地区分布图</div>
                <div id="alarmedMoneyMoneyMonitorChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--贫困户走访监控-走访监控 -->
<script type="text/html" id="tplAlarmedVisitVisitMonitor">
    <div class="page-content-wrap">
        <section id="alarmedVisitVisitMonitorCheckArea" class="check-area clearfix">

        </section>
        <section id="alarmedVisitVisitMonitorChartTable">

        </section>
    </div>
</script>
<!--贫困户走访监控-走访监控 -图表-->
<script type="text/html" id="tplAlarmedVisitVisitMonitorChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">一年内未被走访的户数</div>
                <div id="alarmedVisitVisitMonitorChartNotVisited" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">地区分布图</div>
                <div id="alarmedVisitVisitMonitorChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========项目监控==============-->
<!--扶贫项目管理-项目管理-->
<!--扶贫项目管理-项目管理-图表-->
<script type="text/html" id="tplProjectProjectManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目规模（个）</div>
                <div id="projectProjectManagementChartScale" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金（万元）</div>
                <div id="projectProjectManagementChartMoney" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--扶贫项目管理-项目分析-->
<!--扶贫项目管理-项目分析-全部-图表-->
<script type="text/html" id="tplProjectProjectAnalysisAllChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectProjectAnalysisChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">村户项目分布</div>
                <div id="projectProjectAnalysisChartProjectDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--扶贫项目管理-项目分析-到村项目-图表-->
<!--扶贫项目管理-项目分析-到户项目-图表-->
<script type="text/html" id="tplProjectProjectAnalysisCountryChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectProjectAnalysisChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金分布</div>
                <div class="chart-item-extra">
                    <a class="link-a link-more js-link-tab">更多分析 &gt;</a>
                </div>
                <div id="projectProjectAnalysisChartMoneyDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--村扶贫项目分析-村道硬底化-图表-->
<script type="text/html" id="tplProjectCountryRoadChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryRoadChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryRoadChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--村扶贫项目分析-饮水工程-图表-->
<script type="text/html" id="tplProjectCountryWaterChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryWaterChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryWaterChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--村扶贫项目分析-文体设施-图表-->
<script type="text/html" id="tplProjectCountryRecreationSportChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryRecreationSportChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryRecreationSportChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--村扶贫项目分析-卫生设施-图表-->
<script type="text/html" id="tplProjectCountryHygieneChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryHygieneChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryHygieneChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--村扶贫项目分析-路灯安装-图表-->
<script type="text/html" id="tplProjectCountryLampChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryLampChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryLampChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--村扶贫项目分析-农田水利-图表-->
<script type="text/html" id="tplProjectCountryFarmChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryFarmChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryFarmChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--村扶贫项目分析-公共设施-图表-->
<script type="text/html" id="tplProjectCountryPublicFacilityChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryPublicFacilityChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryPublicFacilityChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--村扶贫项目分析-集体经济-图表-->
<script type="text/html" id="tplProjectCountryCollectiveEconomyChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryCollectiveEconomyChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryCollectiveEconomyChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--村扶贫项目分析-教育教学-图表-->
<script type="text/html" id="tplProjectCountryEduChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectCountryEduChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectCountryEduChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!--户扶贫项目分析-产业扶贫-图表-->
<script type="text/html" id="tplProjectFamilyIndustryChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectFamilyIndustryChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectFamilyIndustryChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--户扶贫项目分析-金融扶贫-图表-->
<script type="text/html" id="tplProjectFamilyFinanceChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectFamilyFinanceChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectFamilyFinanceChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--户扶贫项目分析-住房改造-图表-->
<script type="text/html" id="tplProjectFamilyHouseChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectFamilyHouseChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectFamilyHouseChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--户扶贫项目分析-资产扶贫-图表-->
<script type="text/html" id="tplProjectFamilyPropertyChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目进行情况</div>
                <div id="projectFamilyPropertyChartStatus" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectFamilyPropertyChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--户扶贫项目分析-慰问扶贫-图表-->
<script type="text/html" id="tplProjectFamilyVisitChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目资金趋势</div>
                <div id="projectFamilyVisitChartMoneyTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectFamilyVisitChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--户扶贫项目分析-就业扶贫-图表-->
<script type="text/html" id="tplProjectFamilyEmploymentChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目进行情况</div>
                <div id="projectFamilyEmploymentChartStatus" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectFamilyEmploymentChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--户扶贫项目分析-技能培训-图表-->
<script type="text/html" id="tplProjectFamilySkillChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目进行情况</div>
                <div id="projectFamilySkillChartStatus" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">区域分布</div>
                <div id="projectFamilySkillChartAreaDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--户扶贫项目分析-教育扶贫-图表-->
<script type="text/html" id="tplProjectFamilyEduChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">项目进行情况</div>
                <div id="projectFamilyEduChartStatus" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">行政区改造推进情况</div>
                <div id="projectFamilyEduChartAreaStatus" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>


<!-- ===========责任监控==============-->
<!--责任监控-责任管理-->
<!--责任监控-责任管理-图表-->
<script type="text/html" id="tplDutyDutyManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">帮扶主体趋势</div>
                <div id="dutyDutyManagementChartHelperTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">帮扶主体分布</div>
                <div id="dutyDutyManagementChartHelperDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--责任监控-责任分析-图表-->
<script type="text/html" id="tplDutyDutyAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">帮扶责任分布</div>
                <div id="dutyDutyAnalysisChartDutyDistribution" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">帮扶力度分布</div>
                <div id="dutyDutyAnalysisChartStrengthDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>

<!-- ===========资金监控==============-->
<!--资金监控-资金管理-->
<!--资金监控-资金管理-图表-->
<script type="text/html" id="tplCapitalCapitalManagementChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">年度资金来源（万元）</div>
                <div id="capitalCapitalManagementChartCapitalSource" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">年度资金区域排序（万元）</div>
                <div id="capitalCapitalManagementChartAreaOrder" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--资金监控-资金分析-图表-->
<script type="text/html" id="tplCapitalCapitalAnalysisChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-half">
                <div class="chart-item-title">年度项目资金趋势（万元）</div>
                <div id="capitalCapitalAnalysisChartCapitalTrend" class="chart "></div>
            </div>
            <div class="chart-item chart-half">
                <div class="chart-item-title">年度项目资金分布（万元）</div>
                <div id="capitalCapitalAnalysisChartCapitalDistribution" class="chart "></div>
            </div>
        </div>
    </article>
    <article class="page-content clearfix">
        <div class="table-item config-table"></div>
    </article>
</script>
<!--资金监控-文件号搜索-->


<!-- ===========示范村建设==============-->
<!--示范村建设-整治达标-->
<script id="tplExampleCountryBuildingRenovateStandard" type="text/html">
    <div class="page-content-wrap">
        <section id="exampleCountryBuildingRenovateStandardChartTable">
        
        </section>
    </div>
</script>
<!--示范村建设-整治达标图表-->
<script type="text/html" id="tplExampleCountryBuildingRenovateStandardChartTable">
    <article class="page-content clearfix">
        <div class="chart-row">
            <div class="chart-item chart-whole top-no-title">
                <ul class="chart-tab" data-active="exampleCountryBuildingRenovateStandardChartRenovateStandardRate">
                    <li data-target="exampleCountryBuildingRenovateStandardChartRenovateStandardRate">比例</li>
                    <li data-target="exampleCountryBuildingRenovateStandardChartRenovateStandardCount">个数</li>
                </ul>
                <section class="charts-wrap high js-tab-charts-wrap" >
                    <div id="exampleCountryBuildingRenovateStandardChartRenovateStandardRate" class="chart"></div>
                    <div id="exampleCountryBuildingRenovateStandardChartRenovateStandardCount" class="chart"></div>
                </section>
            </div>
        </div>
    </article>
    <article class="page-content clearfix table-page-content">
        <div class="table-title-select" id="exampleCountryBuildingRenovateStandardTableSelect">
            <div class="select-area clearfix">
              <div class="select-row">
                  <span >
                      <div id="selectMonth" class="select-box js-select-box">
                          <span data-month="1">1月</span>
                          <i class="arrow"></i>
                          <div class="select-list js-select-list" style="display: none; ">
                              <div>
                                  <ul>
                                      <li class="active" data-month="1">
                                         1月
                                     </li>
                                     
                                      <li class="" data-month="2">
                                         2月
                                     </li>
                                     
                                      <li class="" data-month="3">
                                         3月
                                     </li>

                                      <li class="" data-month="4">
                                         4月
                                     </li>
                                      <li class="" data-month="5">
                                         5月
                                     </li>
                                      <li class="" data-month="6">
                                         6月
                                     </li>
                                      <li class="" data-month="7">
                                         7月
                                     </li>
                                      <li class="" data-month="8">
                                         8月
                                     </li>

                                      <li class="" data-month="9">
                                         9月
                                     </li>
                                      <li class="" data-month="10">
                                         10月
                                     </li>
                                      <li class="" data-month="11">
                                         11月
                                     </li>
                                     <li class="" data-month="12">
                                         12月
                                     </li>
                                     
                                 </ul>
                             </div>
                         </div>
                     </div>

                     <div id="selectState" class="select-box js-select-box">
                          <span data-state="doing">建设中</span>
                          <i class="arrow"></i>
                          <div class="select-list js-select-list" style="display: none;">
                              <div>
                                  <ul>
                                      
                                      <li  data-state="done">
                                         已完成
                                     </li>
                                     
                                      <li class="active" class="" data-state="doing">
                                         建设中
                                     </li>
                                     
                                      <li class="" data-state="unstart">
                                         未启动
                                     </li>
                                     
                                 </ul>
                             </div>
                         </div>
                     </div>
                 </span>
             </div>
             </div>
        </div>
        <div id="exampleCountryBuildingRenovateStandardTable" class="table-item config-table has-title-select" ></div>
    </article>
</script>
