<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<title>战略地图-广东省扶贫办大数据平台</title>
	<link rel="stylesheet" href="${ctxStatic}/themes/css/fupin/main_strategy.css">
	<script type="text/javascript">
	var parentId = "${param.parentId}";
	</script>
</head>

<body>
<div class="main-wrap strategy-wrap">
	<!--公共地图部分-->
	<section id="mapWrap">
		<div id="map" class="map-con"></div>

	</section>
	<!--责任监控html结构-->
	<div id="strategyWrap" data-root="strategy_ctrl">

		<header>
			<div class="logo"></div>
			<ul class="nav-ul nav-country">
				<c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
					<c:if test="${menu.parent.name eq '顶级菜单' && menu.isShow eq '1'}">
						<c:choose>
							<c:when test="${menu.name =='搜索'}">
							<li id="li_${menu.id}" class="search">
								<a class="nav1-a" href="#" onclick="loadPage('${menu.id}','${menu.pageType}','${menu.href}');"></a>
							</li>
							</c:when>
							<c:otherwise>
							<li id="li_${menu.id}">
								<a class="nav1-a" href="#" onclick="loadPage('${menu.id}','${menu.pageType}','${menu.href}');">${menu.name}</a>
							</li>
							</c:otherwise>
						</c:choose>
					</c:if>
				 </c:forEach>
			</ul>
		</header>

		<div class="strategy-blocks-wrap">
			<div class="scope-wrap">
				<div id="btnScope" class="btn-scope">全省</div>
				<ul class="scope-list" id="scopeList">
					<li class="active" data-scope="quansheng">全省</li>
					<li data-scope="xiangduipinkuncun">相对贫困村</li>
					<li data-scope="funsancun">分散村</li>
					<li data-scope="geminglaoqu">革命老区</li>
					<li data-scope="zhongyangsuqu">中央苏区</li>
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


</div> <!-- end main-wrap -->


<script type="text/html" id="tplAverage">
	<div class="chart" id="chartAverage">

	</div>
	<div class="values">
		<div>年人均收入</div>
		<div><span class="label">目标收入</span><strong>
			<#=amount.target#>
		</strong></div>
		<div class="strong"><span class="label">当前收入</span><strong>
			<#=amount.current#>
		</strong></div>
		<div><span class="label">基础收入</span><strong>
			<#=amount.basic#>
		</strong></div>
	</div>
</script>
<script type="text/html" id="tplAlarm">
	<table>
		<tr>
			<td>
				<p>贫困户识别存在</p>
				<p><b><#=family#></b>项异常</p>
			</td>
			<td>
				<p>存在异常的</p>
				<p><b><#=project#></b>个项目施策</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>自筹资金&lt;30万的</p>
				<p><b><#=country#></b>个相对贫困村</p>
			</td>
			<td>
				<p>半年内未走访的</p>
				<p><b><#=responsible#></b>个帮扶责任人</p>
			</td>
		</tr>
	</table>
</script>
<script type="text/html" id="tplCapital">
	<div class="upper">
		<div class="chart chart-pie" id="chartPie"></div>
		<div class="values">
			<p class="strong"><span class="label">总投入</span><span class="num"><#=stat.invested#></span></p>
			<p><span class="label">总项目</span><span class="num"><#=stat.projects#></span></p>
			<p><span class="label">完成率</span><span class="num"><#=stat.rate_completed#></span></p>
		</div>
	</div>
	<div class="lower">
		<div class="chart chart-bubble" id="chartBubble"></div>
		<div class="sub">备注：气泡大小表示项目资金投入</div>
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
		<div class="label">脱贫户</div>
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
				<#=stat.amount_responsible#>
			</div>
			<div class="label">责任人</div>
		</li>
		<li class="data3">
			<div class="num">
				<#=stat.amount_leader#>
			</div>
			<div class="label">驻村干部</div>
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

<script src="${ctxStatic}/js/modules/fupin/libs/jquery.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/echarts.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/template-native.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/common/template-config.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/common/button.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/countActive-int.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/render_strategy.js"></script>
<script src="${ctxStatic}/js/modules/fupin/map/echars-map.js"></script>
<script src="${ctxStatic}/js/modules/fupin/common/common-menu.js"></script>
</body>
</html>




