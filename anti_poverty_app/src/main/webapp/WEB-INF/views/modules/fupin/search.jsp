<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<title>帮扶搜索-广东省扶贫办大数据平台</title>
	<link rel="stylesheet" href="${ctxStatic}/themes/css/fupin/main_strategy.css">
	<script type="text/javascript">
	var parentId = "${param.parentId}";
	</script>
</head>

<style>

 </style>

<body>
<div class="main-wrap search-result-wrap">
	<div>

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
		
		<div class="search-form-wrap">
			<div class="search-form">
				<div class="search-field-wrap">
					<input id="searchField" type="text" class="search-field" placeholder="请输入搜索关键词">
					<div class="btn" id="btnSearch">搜索</div>
					<div class="search-suggest" id="searchsSuggest"><ul></ul></div>
				</div>
				<div class="search-types">
					<input class="search-type" type="radio" id="typeFamily" name="type" value="family" checked>
					<label for="typeFamily">贫困户</label>
					<input class="search-type" type="radio" id="typeCountry" name="type" value="country">
					<label for="typeCountry">贫困村</label>
					<input class="search-type" type="radio" id="typeHelper" name="type" value="helper">
					<label for="typeHelper">帮扶人</label>
					<input class="search-type" type="radio" id="typeProject" name="type" value="project">
					<label for="typeProject">帮扶项目</label>
				</div>
			</div>
		</div>
		<div class="search-result" id="searchResult">
		</div>


	</div>




</div> <!-- end main-wrap -->

<script id="tplPreSearch" type="text/html">
	<ul>
		<#for(var i = 0, len = result.length; i < len; i++){#>
		<#var item = result[i];#>
		<li data-id="<#=item.id#>"><#=keyword#><span> - <#=item.place#></span></li>
		<#}#>
	</ul>
</script>

<script id="tplSearchResult" type="text/html">
	<#if(!result){#>
		<ul class="no-result">
			<li>未找到相关内容<br>
				请尝试重新输入关键词</li>
		</ul>
	<#}else{#>
		<ul class="has-result">
			<#if(search_type == SEARCH_TYPES.FAMILY){#>
			<#include('tplLiFamily', result.overview_family)#>
			<#include('tplLiHelper', result.helper)#>
			<#include('tplLiPlan', result.plan)#>
			<#include('tplLiFileCountry', result.file_country)#>
			<#}else if(search_type == SEARCH_TYPES.COUNTRY){#>
			<#include('tplLiFileCountry', result.file_country)#>
			<#include('tplLiHelper', result.helper)#>
			<#include('tplLiPlan', result.plan)#>
			<#}else if(search_type == SEARCH_TYPES.HELPER){#>
			<#include('tplLiHelper', result.helper)#>
			<#}else if(search_type == SEARCH_TYPES.PROJECT){#>
			<#include('tplLiPlan', result.plan)#>
			<#}#>
		</ul>
	<#}#>
</script>
<script id="tplLiFamily" type="text/html">
	<li data-key="overview_family" data-id="<#=family_id#>">
		<h3>贫困户概况</h3>
		<div class="content"><#=#content#></div>
	</li>
</script>
<script id="tplLiHelper" type="text/html">
	<li data-key="helper" data-id="<#=family_id#>">
		<h3>帮扶人</h3>
		<div class="content"><#=#content#></div>
	</li>
</script>
<script id="tplLiPlan" type="text/html">
	<li data-key="plan" data-id="<#=family_id#>">
		<h3>帮扶计划</h3>
		<div class="content"><#=#content#></div>
	</li>
</script>
<script id="tplLiFileCountry" type="text/html">
	<li data-key="file_country" data-id="<#=country_id#>">
		<h3>村档案</h3>
		<div class="content"><#=#content#></div>
	</li>
</script>

<script src="${ctxStatic}/js/modules/fupin/libs/jquery.min.js"></script>
<script src="${ctxStatic}/js/modules/fupin/libs/template-native.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/common/template-config.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/common/button.js"></script>
<script src="${ctxStatic}/js/modules/fupin/controllers/poor_country_detail_ctrl.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/search_common.js"></script>
<script src="${ctxStatic}/js/modules/fupin_strategy/search_result.js"></script>
<script src="${ctxStatic}/js/modules/fupin/common/common-menu.js"></script>
</body>
</html>



