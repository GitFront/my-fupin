<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/themes/css/birp-icon.css" rel="stylesheet" type="text/css" />
<style>
/* IE9 下出现 panel body 滚动条 特此重写样式  如果panel-body 内嵌不是iframe 可考虑 把此重写样式剔除 */
.panel-body{
overflow:hidden !important;
}
</style>
</head>
<body>

<div id="mainPanel"  class="easyui-layout" fit="true" style="width:100%;height:500px;" >

<div id="footerPanel" region="south" split="true" border="false"  style="height: 26px;overflow:hidden;">
	<div class="footer" style="background: #eee;text-align:center">卓望公司版权所有.</div>
</div>

<div id="treeContentWest"  region="west" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="treeContent" class="easyui-accordion" fit="true" border="false">
	
	<c:set var="menuList" value="${fns:getMenuList2(param.parentId,2)}"/>
	<c:set var="firstMenu" value="true"/>
	
	<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
		<c:if test="${menu.isShow eq '1'}">
			    <div title="${menu.name}"   style="padding:0px;overflow:auto;">
			      <div><ul>
						<c:forEach items="${menu.childList}" var="menuChild">
							<c:if test="${menuChild.isShow eq '1'}">
						    	 <li><div>
						    	 	 <a id="${menuChild.id}" href="#" url="${ctx}/${menuChild.href}" style="line-height:18px" >
						    	 	  <span class="icon-${menuChild.icon}"></span>
						    	 	  ${menuChild.name}
						    	 	 </a>
						    	 </div></li>
							<c:set var="firstMenu" value="false"/>
							</c:if>
						</c:forEach>
 				 </ul></div>
			</div>
		</c:if>
	</c:forEach>
	
	
	 <!--   
      <div title="用户管理"   style="padding:0px;overflow:auto;">
          <div >
             <ul>
                 <li>
                     <div>
                 	     <a   id="111" href="#" url="/birp_web/highcharts.jsp" ><span class="icon icon-add"></span>添加用户信息</a>
                 	</div>
                 </li>
                 
                 <li>
                   <div>
                 	<a id="222"  href="#" url="http://www.baidu.com" ><span class="icon icon-users"></span>用户信息管理</a>
                 	</div>
                 </li>
                 
                  <li>
                   <div>
                 	<a   href="#" url="http://www.hao123.com" ><span class="icon icon-users"></span>角色信息管理</a>
                 	</div>
                 </li>
                 
             </ul>
             </div>
      </div>
      导航内容 -->
       
      
  	</div>
</div>


	 
<div id="mainPanle" region="center" style=" overflow-y:hidden">
        <div id="mainTabs" class="easyui-tabs"  fit="true" border="false" >
			<div id="tab1" title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
				admin 欢迎您登陆。
 			</div>
		</div>
</div>
    
     

</div>

</body>
</html>
