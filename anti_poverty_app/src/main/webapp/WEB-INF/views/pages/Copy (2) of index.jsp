<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/themes/css/birp-icon.css" rel="stylesheet" type="text/css" />

<script type="text/javascript"> 
 	$(function(){
		Common.init();
 	});
 	 //resize ================================
	 function resize(){
		 document.getElementById("mainPanel").style.height = $(window).height()-50;
		 $('#mainPanel').layout("resize");
 	 }
  	 
	 window.onload = window.onresize = function(){
 		resize();
 	 };

	 
</script>

</head>
<body   >
 
<div id="mainPanel"  class="easyui-layout"  style="width:100%;height:500px;" >

<div region="south" split="true" border="false"  style="height: 26px;overflow:hidden;">
	<div class="footer" style="background: #eee;text-align:center">卓望公司版权所有.</div>
</div>
	
<div id="treeContentWest"  region="west" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="treeContent" class="easyui-accordion" fit="true" border="false">
	   
	<!-- 
	
	 <ul>
 <li>
  <div id="1a2ff397022345978da0345b959cb822" title="营销活动"  style="overflow:auto;padding:0px;">
 <div><a id="2765f01f17bc4ba3ba3715ff824240e8"  href="#" url=undefined >
 <span class="icon undefined" ></span>营销活动趋势分析</a></div></li>
  
 <li><div><a id="dd3119f332604f7c9b68debcd27363af"  href="#" url=undefined >
 <span class="icon undefined" ></span>营销活动总体报表</a></div></li> 
 </ul>
	</div> 	
	
     导航内容
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
      
      <div title="报表DEMO" style="padding:10px;">
        <div >
             <ul >
                 <li>
                     <div>
                 	     <a target="mainFrame" href="/birp_web/d/demo.htm?id=demo1"><span class="icon icon-add"></span>首页样式</a>
                 	</div>
                 </li>
                 
                 <li>
                   <div>
                 		<a target="mainFrame"  href="/birp_web/d/demo.htm?id=demo2" ><span class="icon icon-users"></span>多图表查询页面</a>
                 	</div>
                 </li>
                 
                  <li>
                   <div>
                 		<a target="mainFrame"  href="/birp_web/d/demo.htm?id=demo3" ><span class="icon icon-users"></span>明细信息页</a>
                 	</div>
                 </li>
                 
             </ul>
         </div>
      </div>
       -->
      
      
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
