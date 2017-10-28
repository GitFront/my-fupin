<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>广东省扶贫大数据平台<sitemesh:title /></title>
<link href="${themePath}/css/themes.css" rel="stylesheet" type="text/css" />
<link href="${themePath}/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${themePath}/js/themes.js"></script>
<script type="text/javascript">
var parentId = '${param.parentId}';
$(function(){
Common.init();
aspire.birp.main.init();
	document.getElementById('li_'+parentId).className="Select";
});
var getWindowSize=function(){
	return["Height","Width"].map(function(a){
	return window["inner"+a]||document.compatMode==="CSS1Compat"&&document.documentElement["client"+a]||document.body["client"+a]})
};
window.onload = window.onresize =  function(){
	var minHeight = 500;
var strs=getWindowSize().toString().split(",");
if($("#mainPanel")){
	$("#mainPanel").height((strs[0]<minHeight?minHeight:strs[0])-$("#Top").height());
		$('#mainPanel').layout("resize");
}

if($("#mainFrame")){
	$("#mainFrame").height((strs[0]<minHeight?minHeight:strs[0])-$("#Top").height()-28);
}
 };
 	 
</script>

<sitemesh:head /> 	
</head>	
<body  style="overflow:hidden">
 <div id="Top"  >
  <div class="Toolbar1">
    <div class="CentreBox">
      <div class="Logo" >
	  <b> <div style="font-size:24px;padding-top:5px;color:#FFFFFF;font-family:微软雅黑 !important">广东省扶贫大数据平台</div></b>
	  <!-- <a href="http://www.baidu.com" target="_self img src="Images/Logo.png" alt="网站名称"/></a> -->
	 </div>
	  <div class="TopMenu"  style="font-family:微软雅黑 !important" >
       <!--  <ul class="List1">
          <li class="Select"><a href="#">&nbsp;&nbsp;首页&nbsp;&nbsp;</a></li>
          <li><a href="#" >数据质量监控</a></li>
          <li><a href="#"  >数据报表分析</a></li>
          <li><a href="#"  >数据提取中心</a></li>
          <li><a href="#"  >配置管理</a></li>
          <li><a href="#" >系统管理</a></li>
        </ul>
         -->
         <ul class="List1">
         
         <c:set var="firstMenu" value="true"/>
 		 <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
			<c:if test="${menu.parent.name eq '顶级菜单' && menu.isShow eq '1'}">
				<li id="li_${menu.id}" onclick="aspire.birp.main.page.loadPage('${menu.id}','${menu.pageType}','${menu.href}')"
  				<%-- <c:if test="${(firstMenu == true and param.parentId == null) or (param.parentId != null and menu.id == param.parentId)}" >
  					  class="Select"
 				</c:if> --%>
                 >
				<a  href="#"  >${menu.name}</a>
				</li>
				<c:if test="${firstMenu}">
					<c:set var="firstMenuId" value="${menu.id}"/>
				</c:if>
				<c:set var="firstMenu" value="false"/>
			</c:if>
		 </c:forEach>
         </ul>
        </div>
       <div class="UserInfo" >
        <div class="NickName"  ><span class="PicMiddle"> </span>&nbsp;&nbsp;<a href="#"  >登录用户：${fns:getCache("user", null).loginName}</a><font color="#FFFFFF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="aspire.birp.main.page.favorite('广东省扶贫大数据平台')"></>收藏&nbsp;|&nbsp;<a href="${ctx}/a/logout"  >注销</a></font></div>
      </div>
     </div>
   </div>
</div>
 
<div id="main">
	<sitemesh:body />
</div>
	
	
</body>
</html>