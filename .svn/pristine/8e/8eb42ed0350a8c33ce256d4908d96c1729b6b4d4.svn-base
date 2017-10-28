<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>帮扶搜索-广东扶贫云</title>
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
							<li id="li_${menu.id}" class="search open">
								<%-- <a class="nav1-a" href="#" onclick="loadPage('${menu.id}','${menu.pageType}','${menu.href}');"></a> --%>
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
				 <li class="ico-btn logout"><a class="not-text" href="${ctx}/a/logout.do"></a></li>
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
					<!--<input class="search-type" type="radio" id="typeHelper" name="type" value="helper">-->
					<!--<label for="typeHelper">帮扶人</label>-->
					<!--<input class="search-type" type="radio" id="typeProject" name="type" value="project">-->
					<!--<label for="typeProject">帮扶项目</label>-->
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
		<li data-id="<#=item.id#>"><#=item.name#><span> - <#=item.place#></span></li>
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
				<#var overview_family = result.overview_family#>
				<li data-key="overview_family" data-id="<#=overview_family.family_id#>">
					<h3 class="title"><#=result.title#></h3>
					<h3>贫困户概况<span class="sub"><#=overview_family.house_holder_name#></span></h3>
					<div class="content"><#=#overview_family.content#></div>
				</li>
				<#var helper = result.helper#>
				<li data-key="helper" data-id="<#=helper.family_id#>">
					<h3>帮扶责任人<span class="sub"><#=helper.helper_name#></span></h3>
					<div class="content"><#=#helper.content#></div>
				</li>
				<#var implement = result.implement#>
				<li data-key="family_implement" data-id="<#=implement.family_id#>">
					<h3>帮扶施策<span class="sub"><#=implement.desc#></span></h3>
					<div class="content"><#=#implement.content#></div>
				</li>
				<#var country_status = result.country_status#>
				<li data-key="country_status" data-id="<#=country_status.country_id#>" class="<#=country_status.is_funsancun ? 'no-link' : ''#>">
					<h3><#=country_status.country_name#>概况<span class="sub"><#=country_status.attributes#></span></h3>
					<div class="content"><#=#country_status.content#></div>
				</li>
			<#}else if(search_type == SEARCH_TYPES.COUNTRY){#>
				<#var country_status = result.country_status#>
				<li data-key="country_status" data-id="<#=country_status.country_id#>" class="<#=country_status.is_funsancun ? 'no-link' : ''#>">
					<h3 class="title"><#=result.title#></h3>
					<h3><#=country_status.attributes#><span class="sub"><#=country_status.country_name#></span></h3>
					<div class="content"><#=#country_status.content#></div>
				</li>
				<#var country_leader = result.country_leader#>
				<li data-key="country_leader" data-id="<#=country_leader.country_id#>">
					<h3>驻村队长<span class="sub"><#=country_leader.leader_name#></span></h3>
					<div class="content"><#=#country_leader.content#></div>
				</li>
				<#var implement = result.implement#>
				<li data-key="country_implement" data-id="<#=implement.country_id#>">
					<h3>帮扶施策<span class="sub"><#=implement.desc#></span></h3>
					<div class="content"><#=#implement.content#></div>
				</li>
				<#var town_status = result.town_status#>
				<#if(town_status){#>
				<li class="no-link">
					<h3><#=town_status.town_name#>概况</h3>
					<div class="content"><#=#town_status.content#></div>
				</li>
				<#}#>
			<#}#>
		</ul>
	<#}#>
</script>
<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>
<%-- <script src="${ctxStatic}/js/modules/fupin/controllers/poor_country_detail_ctrl.min.js"></script> --%>
<%-- <script src="${ctxStatic}/js/modules/fupin_strategy/search_common.min.js"></script> --%>
<script src="${ctxStatic}/js/modules/fupin_strategy/search_result.min.js"></script>
<%-- <script src="${ctxStatic}/js/modules/fupin/common/common-menu.min.js"></script> --%>
</body>
</html>



