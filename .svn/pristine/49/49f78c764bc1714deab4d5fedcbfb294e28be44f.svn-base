<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>资金监控-广东省扶贫云</title>
	<link rel="stylesheet" href="${ctxStatic}/themes/css/fupin/main_strategy.css">

	<script type="text/javascript">
	var parentId = "${param.parentId}";
	</script>
</head>

<body>
<div class="main-wrap duty-wrap">
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
							<a style = "color:#3390ff">${menu.name}<i class="icon-arrow-down"></i></a>
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
		
	</div>
	<!-- 查文件号 -->
	<section class="search-file-num hide">
		<div class="center-wrap">
			<table class="check-area-table">
				<tr id="capitalSource" class="radio-row clearfix" data-key="scope">
					<td class="check-area-label"><strong>资金来源：</strong></td>
					<td>
					<p><a class="radio active" data-value="all">全部</a><a class="radio" data-value="state">中央</a><a class="radio " data-value="province">省</a><a class="radio " data-value="city">市</a><a class="radio" data-value="county">县</a><a class="radio" data-value="town">镇</a></p>
					</td>
				</tr>
				<tr class="radio-row input-row clearfix" data-key="scope">
					<td class="check-area-label"><strong>文件号筛选：</strong></td>
					<td>
						<div class="search-input-wrap">
							<input id="keywordInput" class="keyword" type="text" value="" placeholder="请输入查询的文件号">
							<button id="popupSearchBtn" class="btn-search">检索</button>
						</div>
					</td>
				</tr>
			</table>
			<hr>
			<div class="search-result-none hide">
				未找到相关内容，请更改关键词重试。
			</div>
			<div id="searchResultTable" class="search-result-table">
				
			</div>
		</div>	
	</section>
</div>

<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>
<script src="${ctxStatic}/js/modules/fupin/common/common-menu.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/jquery.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/echarts.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/template-native.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/jquery.selectbox-0.2.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/jquery.mousewheel.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/jquery.jscrollpane.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/tipso.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/owl.carousel.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/jquery.ztree.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/common/urls_mock.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/common/common.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/common/components.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/common/monitor.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/controllers/poor_country_detail_ctrl.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/search_common.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/capital.min.js"></script>

<script src="${ctxStatic}/js/common/jquery-namespace.min.js"></script>
<script src="${ctxStatic}/js/common/charts/common-echarts.min.js"></script>
<script src="${ctxStatic}/js/common/charts/common-utils.min.js"></script>
<script src="${ctxStatic}/js/common/charts/common-echarts-config.min.js"></script>
</body>
</html>
