<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="org.jasig.cas.client.session.SingleSignOutHttpSessionListener"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>  
<html>
<head>
<meta name="decorator" content="default" />

<script type="text/javascript"> 
 	 
	 //resize ================================
	 function resize(){
		 document.getElementById("mainPanel").style.height = $(window).height()-50;
		 $('#mainPanel').layout("resize");
 	 }
	 
	 $(function(){
			Common.init();
			initMenus();
 	});
	 
	 window.onload = window.onresize = function(){
 		resize();
		//Page.utils.autoResize();
	 };
	 
	 function initMenus(){
 		    //$(".easyui-accordion").append(menulist);
 			$('.easyui-accordion li a').click(function(){
				var tabTitle = $(this).text();
				var url = $(this).attr("href");
				addTab(tabTitle,url);
				$('.easyui-accordion li div').removeClass("selected");
				$(this).parent().addClass("selected");
			}).hover(function(){
				$(this).parent().addClass("hover");
			},function(){
				$(this).parent().removeClass("hover");
			});
 			$(".easyui-accordion").accordion();
 	 }
	 
 	 function addTab(subtitle,url){
	 	if(!$('#tabs').tabs('exists',subtitle)){
	 		$('#tabs').tabs('add',{
	 			title:subtitle,
	 			content:createFrame(url),
	 			closable:true,
	 			width:$('#mainPanle').width()-10,
	 			height:$('#mainPanle').height()-26
	 		});
	 	}else{
	 		$('#tabs').tabs('select',subtitle);
	 	}
	 	tabClose();
	 }

	 function createFrame(url)
	 {
	 	var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	 	return s;
	 }
	 
</script>

</head>
<body   >
 
<div id="mainPanel"  class="easyui-layout"  style="width:100%;height:500px;" >

<div region="south" split="true" border="false"  style="height: 26px;overflow:hidden;">
	<div class="footer" style="background: #eee;text-align:center">卓望公司版权所有.</div>
</div>
	
<div region="west" split="true" title="导航菜单" style="width:180px;" id="west">
	<div class="easyui-accordion" fit="true" border="false">
    <!--  导航内容 -->
      <div title="用户管理"   style="padding:0px;overflow:auto;">
          <div >
             <ul>
                 <li>
                     <div>
                 	     <a target="mainFrame"   href="/birp_web/highcharts.jsp"><span class="icon icon-add"></span>添加用户信息</a>
                 	</div>
                 </li>
                 
                 <li>
                   <div>
                 	<a href="#" ><span class="icon icon-users"></span>用户信息管理</a>
                 	</div>
                 </li>
                 
                  <li>
                   <div>
                 	<a href="#" ><span class="icon icon-users"></span>角色信息管理</a>
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
    
 	</div>
</div>


	 
<div id="mainPanle" region="center" style=" overflow-y:hidden">
        <div id="mainTabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:20px;overflow:hidden;" id="home">
				admin 欢迎您登陆。
 			</div>
		</div>
</div>
    
     

</div>

</body>
</html>
