<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>示范村-广东省扶贫云</title>
	<link rel="stylesheet" href="${ctxStatic}/themes/css/fupin/main_strategy.css">
<script type="text/javascript">
	var parentId = "${param.parentId}";
	</script>
</head>

<style>
</style>

<body>
<div id="example-country-wrap" class="main-wrap  data-wrap">
		<div id="dataWrap">
			<header>
				<div class="logo"></div>
				<ul class="nav-ul nav-country">
				<c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
				<c:if test="${menu.parent.name eq '顶级菜单' && menu.isShow eq '1'}">
					<c:choose>
						<c:when test="${menu.name =='搜索'}">
						<%-- <li id="li_${menu.id}" class="search">
							<a class="nav1-a" href="#" onclick="loadPage('${menu.id}','${menu.pageType}','${menu.href}');"></a>
						</li> --%>
						<li id="navSearch" class="ico-btn search">
							<div class="presearch-wrapper" id="presearchWrapper">
								<div class="presearch-form">
									<div class="select-wrapper">
										<select id="selectPresearchTypes">
											<option value="family" selected>贫困户</option>
											<option value="country">相对贫困村</option>
										</select>
									</div>
									<div class="input-wrapper">
										<input type="text" id="inputPresearchField" class="input-presearch-field" placeholder="请输入搜索关键词">
										<div id="btnClearPresearch" class="btn-clear-presearch"></div>
									</div>
								</div>
								<div class="presearch-suggest" id="searchsSuggest"><ul></ul></div>
							</div>
						</li>
						</c:when>
						<c:otherwise>
						<c:choose>
							<c:when test="${menu.pageType eq '0'}">
							<li class="on second-nav" >
							<a>${menu.name}<i class="icon-arrow-down"></i></a>
								<ul class="select-list">
								
								<c:forEach items="${fns:getMenuList2(menu.id,1)}" var="menu2" varStatus="idxStatus2">
									<li id="li_${menu2.id}">
										<a class="nav1-a" href="#" onclick="loadPage('${menu2.id}','${menu2.pageType}','${menu2.href}');">${menu2.name}</a>
									</li>
								</c:forEach>
									
								</ul>
							</li>
				
							</c:when>
							<c:otherwise>
							
							<li id="li_${menu.id}">
								<a class="nav1-a" href="#" onclick="loadPage('${menu.id}','${menu.pageType}','${menu.href}');">${menu.name}</a>
							</li>
							</c:otherwise>
							</c:choose>
							
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			<li class="ico-btn logout"><a class="not-text" href="${ctx}/a/logout.do"></a></li>
			</ul>
			</header>
			<div class="data-blocks-wrap">
			<!--公共地图部分-->
				<section id="mapWrap">
					<div id="map" class="map-con"></div>

				</section>

				<div class="area-wrap">
					<strong id="btnArea">广东省</strong>
				</div>
				<!--<div class="btn-map-back" id="btnMapBack">返回</div>-->
				<div class="block no-border block-top" id="blockTop">
				</div>
				<div class="block no-border block-bottom" id="blockBottom">
					<div class="blue-select">
						<div id="btnMapIndex" class="btn btn-area"><strong>相对贫困村数</strong><i class="icon icon-arrow"></i></div>
						<ul class="select-list" id="scopeList">
							<li class="active" data-index="1">相对贫困村数</li>
							<li data-index="2">示范村达标村数</li>
							<li data-index="3">示范村达标比例</li>
							<li data-index="4">完成提升创建村数</li>
							<li data-index="4">完成提升创建村比例</li>
							<li data-index="4">20户以上自然村数</li>
							<li data-index="4">自然村达标村数</li>
							<li data-index="5">自然村达标村比例</li>
							<li data-index="5">完成提升创建自然村数</li>
							<li data-index="5">完成提升创建自然村比例</li>
						</ul>
					</div>
					<div class="block block-map-data-types" id="blockMapDataTypes">
					<table>
						<tr>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="1">
									<span>示范村达标</span>
								</label>
							</td>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="2" checked>
									<span>示范村建设中</span>
								</label>
							</td>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="3">
									<span>未启动的村</span>
								</label>
							</td>
						</tr>
					</table>
				</div>
				</div>

				<!-- 左 -->
				<div class="block block-average block-side-top">
					<div id="blockAverage" class="content"></div>
				</div>
				<div class="block block-guarantee block-side-center">
					<div id="blockGuarantee" class="content">
					</div>
				</div>
				<div class="block block-bars block-side-bttm" id="blockBarsWrap">
					<div id="blockBars" class="content"></div>
				</div>

				<!-- 右 -->
				<div class="block block-poor-analysis block-side-top right" id="blockPoorAnalysisWrap">
					<div id="blockPoorAnalysis" class="content"></div>
				</div>

				<div class="block block-capital block-side-center right">
					<div id="blockCapital" class="content"></div>
				</div>

				<div class="block block-alarm block-side-bttm right">
					<p class="block-title">完善基础设施和基层治理</p>
				</div>
				
				
				
				
			</div>

			<!-- 示范村相册 固定形式 -->

<section id="exampleCountryPhoto" class="example-country-photo fixed">
	<div class="btn-close js-second-popup-close"></div>
	<div id="exampleCountryPhoto">
	</div>
</section>

<script id="tplExampleCountryPhoto" type="text/html">
	<div class="title clearfix">
		<div id="btnSelectCountry" class="btn btn-select-country js-select-box select-box"><span data-select-id="<#=select_country.selected_id#>"><#=select_country.selected_name#></span><i class="icon icon-arrow arrow"></i>
	
				<div class="select-list js-select-list">
					<div>
						<ul>
							<#for(var i=0;i< select_country.list.length;i++){#>
							<# var item=select_country.list[i]#>
							<li  data-id=<#=item.id #>><#=item.name #></li>
							<#}#>
						</ul>
					</div>
				</div>
			</div>
	</div>

	<div class="photo-switch">
		
    </div>
</script>
<!-- 示范村相册 固定形式 end-->
		</div>
		<div class="copyright">版权所有：广东省扶贫开发办公室&nbsp;&nbsp;&nbsp;&nbsp;技术支持：中国移动通信集团广东有限公司</div>
	</div>

	<div id="popBigImgWrap" class="pop-big-img-wrap">
		<div class="big-img-wrap">
			<div class="btn-close js-second-popup-close"></div>
			<img src="" class="big-img">
			<p class="tips"></p>
		</div>
	</div>	

	<script type="text/html" id="tplTopIndex">
		<ul>
			<li class="data1">
			<#if(area_level=="country"){#>
				<p class="country-state"><#=reach_standard_country.country_state#></p>
				<p class="country-tips">省定贫困村建设</p>
				<p class="country-tips">新农村示范村达标</p>
			<#}else{#>
				<p><strong><#=reach_standard_country.complete_num#></strong>个示范村达标</p>
				<p><span><#=reach_standard_country.province_standard_num#></span>个省定贫困村</p>

				<div id="topChartLeft" class="top-chart"></div>
			<#}#>
			</li>
			<li class="data2">
			<#if(area_level=="country"){#>
				<p class="country-state"><#=finish_promote_country.country_state#></p>
				<p class="country-tips">省定贫困村建设</p>
				<p class="country-tips">新农村示范村提升创建</p>
			<#}else{#>
				<p><strong><#=finish_promote_country.complete_num#></strong>个完成提升创建</p>
				<p><span><#=finish_promote_country.province_standard_num#></span>个省定贫困村</p>

				<div id="topChartRight" class="top-chart"></div>
			<#}#>
			</li>
			
		</ul>
	</script>

	<!-- 左一 -->
	<script type="text/html" id="tplAverage">
		<p><strong class="data"><#=total#> </strong> 个≥20户自然村 | 达标 <em class="data"><#=reach_standard#></em> 个</p>
			
			<div class="percent-bar-wrap">
			<div class="percent-bar">
				<p class="finish" style="width:<#=bar.finish.percent#>%">
					<span class="tips" >
						<em><#=bar.finish.percent#>%</em>
						<em><#=bar.finish.num#>个</em>
					</span>
				</p>
				<p class="building" style="width:<#=bar.building.percent#>%">
					<span class="tips" >
						<em><#=bar.building.percent#>%</em>
						<em><#=bar.building.num#>个</em>
					</span>
				</p>
				<p class="unstart" style="width:<#=bar.unstart.percent#>%">
					<span class="tips" >
						<em><#=bar.unstart.percent#>%</em>
						<em><#=bar.unstart.num#>个</em>
					</span>
				</p>
			</div>
			<p class="bttm-tips">
				<em class="left">0%</em>
				<em class="right">100%</em>
			</p>
		</div>

		<div class="cutline">
			<span><i class="green"></i>已完成</span>
			<span><i class="blue"></i>建设中</span>
			<span><i class="empty"></i>未启动</span>
		</div>
	</script>
	<!-- 左二 -->
	<script type="text/html" id="tplGuarantee">
		<div class="chart-wrap">
			<p class="title">编制整治规划<br/><strong class="data"><#=compile_renovate_plan.total #></strong> 个</p>
			
			<div id="compileRenovatePlanChart" class="center-chart"></div>
		</div>
		<div class="chart-wrap">
			<p class="title">三清三拆三整治<br/><strong class="data"><#=renovate_three.total #></strong> 个</p>
			
			<div id="renovateThreeChart" class="center-chart"></div>
		</div>
		<div class="cutline">
			<span><i class="green"></i>已完成</span>
			<span><i class="blue"></i>建设中</span>
			<span><i class="empty"></i>未启动</span>
		</div>
	</script>
	<!-- 左三 -->
	<script type="text/html" id="tplBars">
		<p class="block-title">补齐基础设施短板</p>
		<div class="bttm-chart-wrap">
			<svg id="facilityShortChart1" class="bttm-chart"></svg>	
			<p class="tips">村道巷道硬化</p>
		</div>
		<div class="bttm-chart-wrap">
			<svg id="facilityShortChart2" class="bttm-chart"></svg>	
			<p class="tips">生活垃圾处理</p>
		</div>
		<div class="bttm-chart-wrap">
			<svg id="facilityShortChart3" class="bttm-chart"></svg>	
			<p class="tips">污水处理</p>
		</div>
		<div class="bttm-chart-wrap">
			<svg id="facilityShortChart4" class="bttm-chart"></svg>	
			<p class="tips">集中供水</p>
		</div>
	</script>

	<!-- 右一 -->
	<script type="text/html" id="tplPoorAnalysis">
		<p>≥20户自然村 | 提升创建 <em class="data"><#=promote_creation#></em> 个</p>
		<div class="percent-bar-wrap">
			<div class="percent-bar">
				<p class="finish" style="width:<#=bar.finish.percent#>%">
					<span class="tips" >
						<em><#=bar.finish.percent#>%</em>
						<em><#=bar.finish.num#>个</em>
					</span>
				</p>
				<p class="building" style="width:<#=bar.building.percent#>%">
					<span class="tips" >
						<em><#=bar.building.percent#>%</em>
						<em><#=bar.building.num#>个</em>
					</span>
				</p>
				<p class="unstart" style="width:<#=bar.unstart.percent#>%">
					<span class="tips" >
						<em><#=bar.unstart.percent#>%</em>
						<em><#=bar.unstart.num#>个</em>
					</span>
				</p>
			</div>
			<p class="bttm-tips">
				<em class="left">0%</em>
				<em class="right">100%</em>
			</p>
		</div>
		<div class="cutline">
			<span><i class="green"></i>已完成</span>
			<span><i class="blue"></i>建设中</span>
			<span><i class="empty"></i>未启动</span>
		</div>
	</script>
	<!-- 右二 -->
	<script type="text/html" id="tplCapital">
		<div class="chart-wrap">
			<p class="title">编制提升规划<br/><strong class="data"><#=compile_promote_plan.total#></strong> 个</p>
			<div id="compilePromotePlanChart" class="center-chart"></div>
		</div>
		<div class="chart-wrap">
			<p class="title">绿化美化<br/><strong class="data"><#=green_beautiful.total#></strong> 个</p>
			<div id="greenBeautifulChart" class="center-chart"></div>
		</div>
		<div class="cutline">
			<span><i class="green"></i>已完成</span>
			<span><i class="blue"></i>建设中</span>
			<span><i class="empty"></i>未启动</span>
		</div>
	</script>
	<!-- 右三 -->
	<script type="text/html" id="tplAlarm">
		<p class="block-title">补齐基础设施短板</p>
	</script>





	<!-- 示范村相册 弹窗形式 -->
<section id="exampleCountryPhotoPopWrap" class="pop-poor-monitor-second-wrap example-country-photo-pop-wrap">
	<div id="popupFileDetail" class="pop-poor-monitor-second example-country-photo">
        <div class="btn-close js-second-popup-close"></div>
        <div id="exampleCountryPhotoPop">
        </div>
    </div>
</section>

<script id="tplExampleCountryPhotoPop" type="text/html">
	<div class="title clearfix">
		<p class="title-p"><#=title #></p>
			<div id="btnSelectCountry" class="btn btn-select-country js-select-box select-box"><span data-select-id="<#=select_country.selected_id#>"><#=select_country.selected_name#></span><i class="icon icon-arrow arrow"></i>
	
				<div class="select-list js-select-list">
					<div>
						<ul>
							<#for(var i=0;i< select_country.list.length;i++){#>
							<# var item=select_country.list[i]#>
							<li  data-id=<#=item.id #>><#=item.name #></li>
							<#}#>
						</ul>
					</div>
				</div>
			</div>
			
	</div>

	<div class="photo-switch">
		<i class="icon icon-arrow-left"></i>
		<div class="overflow-wrap">
			<ul class="tab-list">
				<#for(var i=0;i< photo_list.length;i++){#>
					<# var item=photo_list[i]#>
				<li style="background-image: url(<#=item.brief_src#>);" data-belong-id="<#=item.belong_country_id#>" data-big-src="<#=item.big_src#>">
					<p class="tips"><#=item.tips#></p>	
				</li>
				<#}#>
				
			</ul>
		</div>
    	 <i class="icon icon-arrow-right"></i>

    </div>


</script>
<!-- 示范村相册 弹窗形式 end-->



<script id="tplPhotoSwitch" type="text/html">
	<i class="icon icon-arrow-left"></i>
		<div class="overflow-wrap">
			<ul class="tab-list">
				<#for(var i=0;i< photo_list.length;i++){#>
					<# var item=photo_list[i]#>
				<li style="background-image: url(<#=item.brief_src#>);" data-belong-id="<#=item.belong_country_id#>" data-big-src="<#=item.big_src#>">
					<p class="tips"><#=item.tips#></p>	
				</li>
				<#}#>
			</ul>
		</div>
    	 <i class="icon icon-arrow-right"></i>
</script>

<!-- 示范村相册 end-->
<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>
	<script src="${ctxStatic}/js/modules/fupin/common/common-menu.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/echarts.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/d3.v3.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/template-native.min.js"></script>
	<script src="${ctxStatic}/js/modules/script/common/liquidFillGauge.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.selectbox-0.2.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.mousewheel.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.jscrollpane.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/tipso.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/owl.carousel.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.ztree.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/common/urls_mock.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/common/common.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/common/votelist.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/common/components.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/common/monitor.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/countActive-int.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin/controllers/poor_country_detail_ctrl.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/search_common.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/example_country.min.js"></script>
	<!--<script src="${ctxStatic}/js/modules/fupin/map/echars-map.min.js"></script>-->
	<script src="${ctxStatic}/js/common/jquery-namespace.min.js"></script>
	<script src="${ctxStatic}/js/common/charts/common-echarts.min.js"></script>
	<script src="${ctxStatic}/js/common/charts/common-utils.min.js"></script>
	<script src="${ctxStatic}/js/common/charts/common-echarts-config.min.js"></script>



	<!-- map -->
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/common/jquery-namespace.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts-more.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/modules/exporting.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts-zh_CN.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/modules/solid-gauge.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/map.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/data.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/drilldown.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/themes/dark-unica.min.js"></script>

	<script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/common/layer/layer.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common-utils.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common-map.min.js"></script>

	<script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highcharts.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highmaps.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-report-page.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/Highcharts.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highcharts-map.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/maps/cn-guangdong.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/data3.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/index_ec_map.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/fpy_d3.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/fpy_highcharts.min.js"></script>
    <script src="${ctxStatic}/js/modules/script/echarts-all-3.min.js"></script>
</body>
</html>



