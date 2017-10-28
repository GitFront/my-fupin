<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>战略地图-广东扶贫云</title>
	<link rel="stylesheet" href="${ctxStatic}/themes/css/fupin/main_strategy.css">
	<style>
        .liquidFillGaugeText { font-family: Helvetica; font-weight: bold; }
    </style>
</head>

<body>
<div class="main-wrap strategy-wrap">
	<!--责任监控html结构-->
	<div id="strategyWrap" data-root="strategy_ctrl">
		<header>
			<div class="logo"></div>
		</header>

		<div class="strategy-blocks-wrap">
			<!--公共地图部分-->
			<section id="mapWrap">
				<div id="map" class="map-con"></div>

			</section>

			<div class="scope-wrap">
				<div id="btnScope" class="btn-scope">全省</div>
				<ul class="scope-list" id="scopeList">
					<li class="active" data-scope="1">全省</li>
					<li data-scope="2">相对贫困村</li>
					<li data-scope="3">分散村</li>
					<li data-scope="4">革命老区</li>
					<li data-scope="5">中央苏区</li>
				</ul>
			</div>
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
			<div class="block block-bars">
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
			<div class="block block-alarm">
				<div class="title">预警监控</div>
				<div id="blockAlarm" class="content"></div>
			</div>
			<div class="block block-capital" >
			<div class="title">项目资金监控</div>
			<div id="blockCapital" class="content"></div>
			</div>
			 <!--底部左侧操作按钮 -->

		</div>


	</div>

	<!--村档案详情-->

	<!--户档案详情-->


	<div class="copyright">版权所有：广东省扶贫开发办公室&nbsp;&nbsp;&nbsp;&nbsp;技术支持：中国移动通信集团广东有限公司</div>
</div> <!-- end main-wrap -->

<script id="tplPreSearch" type="text/html">
	<ul>
		<#for(var i = 0, len = result.length; i < len; i++){#>
		<#var item = result[i];#>
			<li data-id="<#=item.id#>"><#=item.name#><span> - <#=item.place#></span></li>
			<#}#>
	</ul>
</script>

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
<script type="text/html" id="tplBars">
<div class="chart-bars">
	<ul class="vote-box-list clearfix" id="chartBars">
		<#for(var i = 0, len = list.length; i < len; i++){#>
		<#var item = list[i];#>
		<li class="vl-item <#=item.percent==100?'completed':''#>">
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
			<td <#=#family != 0 ? 'data-target="alarmed_family"' : ''#>>
				<p>贫困户识别存在</p>
				<p><b><#=family#></b>项异常</p>
			</td>
			<td>
				<p>建档立卡数据</p>
				<p><b><#=records#></b>项异常</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>帮扶资金&lt;30万的</p>
				<p><b><#=country#></b>个相对贫困村</p>
			</td>
			<td>
				<p><#=family_not_visited.label#></p>
				<p><b><#=family_not_visited.value#></b>户贫困户</p>
			</td>
		</tr>
	</table>
</script>
<script type="text/html" id="tplCapital">
	<div class="upper">
		<div class="values">
			<p><span class="label">总投入</span><span class="num"><#=stat.invested#></span></p>
			<p><span class="label">总项目</span><span class="num"><#=stat.projects#></span></p>
			<p><span class="label">完成率</span><span class="num"><#=stat.rate_completed#></span></p>
		</div>
		<div class="chart chart-pie" id="chartPie"></div>
	</div>
	<div class="lower">
		<div class="chart chart-bubble" id="chartBubble"></div>
		<div class="sub">备注：(x:项目数，y:完成率)气泡大小表示项目资金投入</div>
	</div>
</script>
<script type="text/html" id="tplTopIndex">
<ul>
	<li class="data1">
		<div class="num"><#=success_country.amount#></div>
		<div class="label">出列村</div>
		<div class="bar-wrap">
			<div class="bar-bg"><div class="bar" style="width:<#=success_country.rate#>%"></div></div>
			<div class="rate"><#=success_country.rate#>%</div>
		</div>
	</li>
	<li class="data2">
		<div class="num"><#=success_family.amount#></div>
		<div class="label">脱贫户</div>
		<div class="bar-wrap">
			<div class="bar-bg"><div class="bar" style="width:<#=success_family.rate#>%"></div></div>
			<div class="rate"><#=success_family.rate#>%</div>
		</div>
	</li>
	<li class="data3">
		<div class="num"><#=success_people.amount#></div>
		<div class="label">脱贫人口</div>
		<div class="bar-wrap">
			<div class="bar-bg"><div class="bar" style="width:<#=success_people.rate#>%"></div></div>
			<div class="rate"><#=success_people.rate#>%</div>
		</div>
	</li>
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
<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>
<script src="${ctxStatic}/js/modules/fupin_strategy/render_strategy.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/map/echars-map.min.js"></script>
</body>
</html>




