<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>东西扶贫协作-广东扶贫云</title>
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
							<li class="on  second-nav">
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
			<div  class="data-blocks-wrap">
				<!--公共地图部分-->
				<section id="mapWrap">
					<div id="map" class="map-con"></div>
				</section>

				<section id="poorEastWestWrap"></section>
			</div>
		</div>
	</div>

	<script id="tplPoorEastWestIndex" type="text/html">
		<div class="block no-border block-top" id="blockTop">
			<ul>
				<li class="data1">
					<div class="num"><#=top.amount_peer_county#> <span class="unit">个</span></div>
					<div class="label">''携手奔小康''结对县</div>
					
				</li>
				<li class="data1">
					<div class="num"><#=top.amount_poor_people#><span class="unit">万</span></div>
					<div class="label">帮扶贫困人口</div>
					
				</li>
			</ul>
		</div>
		<div class="block no-border block-bottom" id="blockBottom">
			<ul>
				<li class="data1">
					<div class="num">
						<#=bottom.amount_help_group#>
					</div>
					<div class="label">帮扶工作组</div>
				</li>
				<li class="data2">
					<div class="num">
						<#=bottom.amount_group_member#>
					</div>
					<div class="label">扶贫工作组成员</div>
				</li>
			</ul>
		</div>
		
		<section class="left-help-province">
			<div class="block">
				<div class="title">帮扶贵州</div>
				<div class="content">
                    <p><strong>结对县</strong><em data-target="guizhou"><#=help_targets.guizhou.amount_peer_county#></em>个</p>
                    <p><strong>帮扶贫困人口</strong><span><#=help_targets.guizhou.amount_poor_people#>万</span></p>
                </div>
			</div>
			<div class="block">
				<div class="title">帮扶广西</div>
				<div class="content">
                    <p><strong>结对县</strong><em data-target="guangxi"><#=help_targets.guangxi.amount_peer_county#></em>个</p>
                    <p><strong>帮扶贫困人口</strong><span><#=help_targets.guangxi.amount_poor_people#>万</span></p>
                </div>
			</div>
			<div class="block">
				<div class="title">帮扶四川</div>
				<div class="content">
                    <p><strong>结对县</strong><em data-target="sichuan"><#=help_targets.sichuan.amount_peer_county#></em>个</p>
                    <p><strong>帮扶贫困人口</strong><span><#=help_targets.sichuan.amount_poor_people#>万</span></p>
                </div>
			</div>
            <div class="block">
                <div class="title">帮扶云南</div>
                <div class="content">
                    <p><strong>结对县</strong><em data-target="yunnan"><#=help_targets.yunnan.amount_peer_county#></em>个</p>
                    <p><strong>帮扶贫困人口</strong><span><#=help_targets.yunnan.amount_poor_people#>万</span></p>
                </div>
            </div>
		</section>
		<div class="block no-border block-countdown">
            <h3>东西扶贫协作攻坚倒计时</h3>
			<h4>离2020年12月31日还有</h4>
			<div class="time-item">
				<strong id="dayshow">00</strong>天
				<strong id="hourshow">00</strong>时
				<strong id="minuteshow">00</strong>分
				<strong id="secondshow">00</strong>秒
			</div>
		</div>
		<div class="block block-invest" style="height:330px;">
			<div class="title">资金投入</div>
			<div class="content">
                <p class="title">总帮扶<span class="num"><#=invested.amount_county#></span>个县</p>
                <p><#=# invested.desc_county#></p> 
                <p class="title">2016年总投入<span class="num">10.38</span>亿元</p>
                 <p class="title" style="padding-top:0px; ">2017年计划总投入<span class="num">11.42</span>亿元</p>
                <p><#=# invested.desc_money#></p>         
            </div>
		</div>

		<!-- 弹窗 -->
		<div id="peerCountyPopupWrap" class="normal-popup-wrap hide">
		</div>

	</script>

	<script id="tplPeerCountyPopup" type="text/html">
		<section id="peerCountyPopup" class="normal-popup">
			<p class="underline-title"><#=peer_popup_title#></p>
			<div class="btn-close"></div>
			<#if(Statistics){#>
				<ul class="peer-county-list clearfix statistics">
					<#for(var i=0;i< peer_county_list_guangxi.length;i++){#>
					<li class="clearfix peer-county-all">
						<span class="peer-city-name"><#=peer_county_list_guangxi[i].city#>：</span>
						<ul class="peer-county-content">
							<# var childrens=peer_county_list_guangxi[i].childrens#>
							<#for(var j=0;j< childrens.length;j++){#>
								<li><#=childrens[j]#></li>
							<#}#>
						</ul>
					</li>
					<#}#>
				</ul>
			<#}else{#>
				<ul class="peer-county-list clearfix">
					<#for(var i=0;i< peer_county_list.length;i++){#>
					<li><#=peer_county_list[i]#></li>
					<#}#>
				</ul>
			<#}#>

		</section>
	</script>
	
	</script>
<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>
<%-- <script src="${ctxStatic}/js/modules/fupin/common/common-menu.min.js"></script> --%>
<%-- <script src="${ctxStatic}/js/modules/fupin_strategy/common/monitor.js"></script> --%>
<%-- <script src="${ctxStatic}/js/modules/fupin/controllers/poor_country_detail_ctrl.min.js"></script> --%>
<%-- <script src="${ctxStatic}/js/modules/fupin_strategy/search_common.min.js"></script> --%>
<script src="${ctxStatic}/js/modules/fupin_strategy/poor_east_west.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/map/echars-china.min.js"></script>
</body>
</html>
