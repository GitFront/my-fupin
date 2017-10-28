<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>数据监控-广东扶贫云</title>
	<link rel="stylesheet" href="${ctxStatic}/themes/css/fupin/main_strategy.css">
	<script type="text/javascript">
	var parentId = "${param.parentId}";
	</script>

</head>

<body>
	<div class="main-wrap data-wrap">
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
					<div id="btnArea" class="btn btn-area">全部</div>
					<ul class="scope-list" id="scopeList">
						<li class="active" id="scopeAreaName" data-scope="1">全部</li>
						<li data-scope="2">相对贫困村</li>
						<li data-scope="3">分散村</li>
						<li data-scope="4">革命老区</li>
						<li data-scope="5">中央苏区</li>
					</ul>
					<div id="btnCountryFile" class="btn btn-country-file">查看村档案</div>
				</div>
				<!--<div class="btn-map-back" id="btnMapBack">返回</div>-->
				<div class="block no-border block-top" id="blockTop">
				</div>
				<div class="block no-border block-bottom" id="blockBottom">
				</div>
				<div class="block block-average">
					<div class="title">两不愁</div>
					<div id="blockAverage" class="content"></div>
				</div>
				<div class="block block-guarantee">
					<div class="title">三保障</div>
					<div id="blockGuarantee" class="content">
					</div>
				</div>
				<div class="block block-bars" id="blockBarsWrap">
					<div class="title">一相当</div>
					<div id="blockBars" class="content"></div>
				</div>
				<div class="block no-border block-countdown">
					<h3>脱贫攻坚倒计时</h3>
					<div class="time-item">
						<strong id="dayshow">00</strong>天
						<strong id="hourshow">00</strong>时
						<strong id="minuteshow">00</strong>分
						<strong id="secondshow">00</strong>秒
					</div>
				</div>
				<div class="block block-poor-analysis" id="blockPoorAnalysisWrap">
					<div class="title">贫困分析</div>
					<div id="blockPoorAnalysis" class="content"></div>
					<a class="link-more" data-target="poor_analysis">更多&gt;</a>
				</div>
				<div class="block block-alarm">
					<div class="title">预警监控</div>
					<div id="blockAlarm" class="content"></div>
				</div>
				<div class="block block-capital">
					<div class="title">项目资金监控</div>
					<div id="blockCapital" class="content"></div>
					<!--<a class="link-more" data-target="capital">更多&gt;</a>-->
				</div>
				<div class="btn-map-bttm btn-poor-list" id="poorFamilyListWrap">
					<span id="btnPoorFamilyList">贫困户列表</span>
					<div id="poorList" class="poor-family-list hide"></div>

				</div>
				<div class="btn-map-bttm btn-area-desc" id="btnAreaDesc"><span>区域简介</span></div>
				<div class="block block-map-data-types" id="blockMapDataTypes">
					<table>
						<tr>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="1">
									<span>贫困户数</span>
								</label>
							</td>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="2" checked>
									<span>贫困人数</span>
								</label>
							</td>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="3">
									<span>预脱贫户数</span>
								</label>
							</td>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="4">
									<span>预脱贫人数</span>
								</label>
							</td>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="5">
									<span>预脱贫户比例</span>
								</label>
							</td>
							<td>
								<label>
									<input type="radio" name="map_data_type" value="6">
									<span>预脱贫率</span>
								</label>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="copyright">版权所有：广东省扶贫开发办公室&nbsp;&nbsp;&nbsp;&nbsp;技术支持：中国移动通信集团广东有限公司</div>
	</div>


	<script type="text/html" id="tplAverage">
		<div class="labor">
			<div class="chart chart-labor">
				<svg id="chartAverageLabor"></svg>
			</div>
			<p class="label">有劳动能力户人均可支配收入</p>
		</div>
		<div class="society">
			<div class="chart chart-society">
				<svg id="chartAverageSociety"></svg>
			</div>
			<p class="label">社会保障兜底</p>
		</div>
	</script>
	<script type="text/html" id="tplGuarantee">
		<ul>
			<li data-target="edu">
				<div class="chart chart-edu"></div>
				<div class="label">义务教育</div>
			</li>
			<li data-target="medical_policy">
				<div class="chart chart-medical"></div>
				<div class="label">基本医疗</div>
			</li>
			<li data-target="house">
				<div class="chart chart-house"></div>
				<div class="label">住房安全</div>
			</li>
		</ul>
	</script>
	<!-- 一相当 -->
	<script type="text/html" id="tplBars">
		<div class="chart-bars">
			<ul class="vote-box-list clearfix" id="chartBars">
				<#for(var i = 0, len = list.length; i < len; i++){#>
				<#var item = list[i];#>
				<li data-name="<#=item.name#>" class="vl-item <#=item.percent==100?'completed':''#>">
					<div class="vote-item-wrap">
						<h4><#=item.name#>&nbsp;</h4>
						<p class="litem"><em class=vleft></em><span class="bar"></span><em class=vright></em>
						</p><span class=vnum><#=item.percent#>%</span>
					</div>
				</li>
				<#}#>
			</ul>
		</div>
	</script>
	<script type="text/html" id="tplAlarm">
		<table>
			<tr>
				<td >
					<p>贫困户识别存在</p>
					<p><b <#=# family != 0 ? 'data-target="alarmed_family"' : ''#>><#=family#></b>项异常</p>
				</td>
				<td >
					<p>建档立卡数据</p>
					<p><b <#=# records != 0 ? 'data-target="alarmed_records"' : ''#>><#=records#></b>项异常</p>
				</td>
			</tr>
			<tr>
				<td >
					<p>帮扶资金&lt;30万的</p>
					<p><b data-target="alarmed_country" ><#=country#></b>个相对贫困村</p>
				</td>
				<td >
					<p>一年内未被走访的</p>
					<p><b <#=# family_not_visited != 0 ? 'data-target="alarmed_family_not_visited"' : ''#>><#=family_not_visited#></b>户贫困户</p>
				</td>
			</tr>
		</table>
	</script>
	<script type="text/html" id="tplCapital">
		<div class="values">
			<span class="label">总投入</span><a id="linkCapital" href="javascript:void 0" data-href="capital.html" class="num"><#=stat.invested#></a>
			<span class="label">总项目</span><a id="linkProject" href="javascript:void 0" data-href="project.html" class="num"><#=stat.projects#></a>
			<span class="label">完成率</span><span class="num"><#=stat.rate_completed#></span>
		</div>
		<div class="chart-wrap">
			<div class="chart chart-bubble" id="chartBubble"></div>
			<div class="notice">备注：(x:项目数，y:完成率)气泡大小表示项目资金投入</div>
		</div>
	</script>
	<script type="text/html" id="tplTopIndex">
		<ul>
			<li class="data1">
				<div class="cols">
					<div class="col col1">
						<div class="num"><#=poor_family_amount#></div>
						<div class="label">贫困户</div>
					</div>
					<div class="col col2">
						<div class="num"><#=success_family_amount#></div>
						<div class="label">预脱贫户</div>
					</div>
				</div>
				<div class="bar-wrap">
					<div class="bar-label">预脱贫比例</div>
					<div class="bar-bg"><div class="bar" style="width:<#=success_family_rate#>%"></div></div>
					<div class="rate"><#=success_family_rate#>%</div>
				</div>
			</li>
			<li class="data2">
				<div class="cols">
					<div class="col col1">
						<div class="num"><#=poor_people_amount#></div>
						<div class="label">贫困人数</div>
					</div>
					<div class="col col2">
						<div class="num"><#=success_people_amount#></div>
						<div class="label">预脱贫人数</div>
					</div>
				</div>
				<div class="bar-wrap">
					<div class="bar-label">预脱贫比例</div>
					<div class="bar-bg"><div class="bar" style="width:<#=success_people_rate#>%"></div></div>
					<div class="rate"><#=success_people_rate#>%</div>
				</div>
			</li>
			<#if(area_level == LEVELS.COUNTRY && score !== undefined){#>
			<li class="data3">
				<div class="num"><#=score#></div>
				<div class="label">综合得分</div>
			</li>
			<#}#>
		</ul>
	</script>
	<script type="text/html" id="tplBottomIndex">
		<ul>
			<li class="data1">
				<div class="num">
					<#=stat.amount_org#>
				</div>
				<div class="label">帮扶单位</div>
			</li>
			<li class="data2">
				<div class="num">
					<#=stat.amount_leader#>
				</div>
				<div class="label">驻村干部</div>
			</li>
			<li class="data3">
				<div class="num">
					<#=stat.amount_responsible#>
				</div>
				<div class="label">责任人</div>
			</li>
			<li class="data4">
				<div class="num dynamic" data-from="<#=lastStat.amount_interview#>" data-to="<#=stat.amount_interview#>"
					data-speed="1000">
					<#=lastStat.amount_interview#>
				</div>
				<div class="label">帮扶走访量</div>
			</li>
		</ul>
	</script>

	<!-- 贫困户列表 -->
	<script type="text/html" id="tplPoorList">

	</script>

<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>
<script src="${ctxStatic}/js/modules/fupin/common/common-menu.min.js"></script>

<%-- <script src="${ctxStatic}/js/modules/fupin/libs/jquery.min.js"></script> --%>
<%-- <script src="${ctxStatic}/js/modules/fupin/libs/echarts.min.js"></script> --%>
<%-- <script src="${ctxStatic}/js/modules/fupin/libs/d3.v3.min.js"></script>  --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin/libs/template-native.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/script/common/liquidFillGauge.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.selectbox-0.2.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.mousewheel.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.jscrollpane.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin/libs/tipso.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin/libs/owl.carousel.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin/libs/jquery.ztree.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin_strategy/common/urls_mock.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin_strategy/common/common.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin_strategy/common/votelist.min.js"></script> --%>
<%-- 	<script src="${ctxStatic}/js/modules/fupin_strategy/common/components.min.js"></script> --%>
	<script src="${ctxStatic}/js/modules/fupin_strategy/common/monitor.min.js"></script>
<%-- 	<script src="${ctxStatic}/js/modules/fupin_strategy/countActive-int.min.js"></script> --%>
	<script src="${ctxStatic}/js/modules/fupin/controllers/poor_country_detail_ctrl.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/search_common.min.js"></script>
	<script src="${ctxStatic}/js/modules/fupin_strategy/data.min.js"></script>
	<!--<script src="${ctxStatic}/js/modules/fupin/map/echars-map.js"></script>-->

	<%-- <script src="${ctxStatic}/js/common/jquery-namespace.min.js"></script> --%>
	<%-- <script src="${ctxStatic}/js/common/charts/common-echarts.min.js"></script> --%>
	<%-- <script src="${ctxStatic}/js/common/charts/common-utils.min.js"></script> --%>
	<%-- <script src="${ctxStatic}/js/common/charts/common-echarts-config.min.js"></script> --%>



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
	<script type="text/javascript" src="${ctxStatic}/js/modules/script/index_map.min.js"></script>
</body>
</html>