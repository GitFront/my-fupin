<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>微力数据中心<sitemesh:title /></title>
<link href="${themePath}/css/themes.css" rel="stylesheet" type="text/css" />
<link href="${themePath}/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${themePath}/js/themes.js"></script>
<script type="text/javascript">
	var parentId = '${param.parentId}';
  	$(function(){
		Common.init();
		aspire.birp.main.init();
		
	});
</script>

<sitemesh:head /> 	
</head>	
<body  style="overflow:hidden">
 <div id="Top">
  <div class="Toolbar1">
    <div class="CentreBox">
      <div class="Logo" >
	  <b> <div style="font-size:24px;padding-top:5px;color:#FFFFFF">微力数据中心</div></b>
	  <!-- <a href="http://www.baidu.com" target="_self img src="Images/Logo.png" alt="网站名称"/></a> -->
	 </div>
	  <div class="TopMenu">
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
				<li onclick="aspire.birp.main.page.loadPage('${menu.id}','${menu.pageType}','${menu.href}')"
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
        <div class="NickName"  ><span class="PicMiddle"> </span>&nbsp;&nbsp;<a href="#" title="上一次登录时间:2015年5月26日 16:30">登录用户：${user.loginName}</a><font color="#FFFFFF">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修改密码&nbsp;|&nbsp;收藏&nbsp;|&nbsp;注销</font></div>
      </div>
     </div>
   </div>
</div>
 
<div id="main">
	<sitemesh:body />
</div>
	
	
</body>
</html>