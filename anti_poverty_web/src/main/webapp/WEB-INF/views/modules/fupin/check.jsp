<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>绩效考核-广东扶贫云</title>
	<link rel="stylesheet" href="${ctxStatic}/themes/css/fupin/main_strategy.css">
	<script type="text/javascript">
	var parentId = "${param.parentId}";
	</script>
</head>

<body>
<div class="main-wrap under-construction-wrap">
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
	<div class="pic-wrap">
		<img src="${ctxStatic}/themes/images/fupin_under_construction/绩效考核没有顶部.png">
	</div>
</div>
<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>
<script src="${ctxStatic}/js/modules/fupin/common/common-menu.min.js"></script>
</body>
</html>
