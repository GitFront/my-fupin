<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="rp" uri="/WEB-INF/tlds/report-layout.tld" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/base.min.css">
	<style type="text/css">
		.body{
		  font-size: 14px;
		}
	</style>
	<script type="text/javascript" src="${ctxStatic}/js/common/base.min.js"></script>
 		
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts-more.js"></script>
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/modules/solid-gauge.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/combo.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/area-negative.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/polar.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/bubble.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/gauge-solid.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/demo4.js"></script>
	
 	<script type="text/javascript">
 		$(function(){
 			common.report.page.init("search");
 			aspire.birp.demo4.datagrid.init('dg','head-search');
		});
 	</script>
 	
 	
</head>
<body>
	<rp:search-content formid="search" btns="search:;reset:;download:" singlerow="true">
		   <rp:search-cellbox title="商户ID">
				<input  id="merchant_id" name="merchant_id" style="width:180px;"/>
			</rp:search-cellbox>
	</rp:search-content>
	
	<rp:content>
		<rp:row>
			<rp:grid-col title="活跃用户信息日报表">
				  <!-- datagrid -->
				<table id="dg"  data-options="title:'活跃用户信息日报表',height:430,singleSelect:true,data:[],fitColumns:false,striped:false,loadMsg:'数据加载中...',
				 url:'${ctx}/a/component/gridData?queryName=aspire.bi.activities.marketing.s_use_act_user_d',remoteSort:false,multiSort:true,pagination:true,pageSize:20,pageList:[10,20,30,40,50],border:true"
				   componentType='datagrid'>
					    <thead>
							<tr> 
								<th data-options="field:'stat_time',width:100,sortable:true">时间</th>
								<th data-options="field:'merchant_id',width:100,sortable:true">商户ID</th>
								<th data-options="field:'merchant_name',width:150,sortable:true">商户名称</th>
								<th data-options="field:'act_user',width:150,sortable:true">活跃用户数</th>
								<th data-options="field:'act_rate',width:100,sortable:true">活跃率</th>
								<th data-options="field:'new_act_user',width:100,sortable:true">新增活跃用户数</th>
								<th data-options="field:'old_act_user',width:100,sortable:true">活跃用户数</th>
		  					</tr>
					  </thead>
				</table>
			</rp:grid-col>
		</rp:row>
	</rp:content> 
	
 <%-- 

	<div id="head-search">
	    <div class="search-container">
	        <div class="row singlerow">
 	            <div class="cellbox">
	                <span class="title">商户ID：</span>
	                <div class="element">
	                	<input  id="merchant_id" name="merchant_id" style="width:180px;"> 
	                </div>
	            </div>
	            
	            <div class="cellbox">
 	                <a id="queryBtn"  class="easyui-linkbutton"  data-options="iconCls:'icon-btn-search'" href="#">查  询</a>
		            <a id="repeatBtn"  class="easyui-linkbutton"  data-options="iconCls:'icon-btn-repeat'"  href="#">重  置</a>
		            <a id="downloadBtn"  class="easyui-linkbutton"  data-options="iconCls:'icon-btn-download'"  href="#">导  出</a>
	            </div>
	            
  	        </div>	
 	       
	    </div>
	</div>
	
	<div class="page-content" ><!-- style="top: 128px;" -->
		<div class="kit-container">
			<div class="kit-row">
				<div class="kit-col-grid" style="width:100%;border:0px !important">
 				 <!-- datagrid -->
				<table id="dg"  data-options="title:'活跃用户信息日报表',height:450,singleSelect:true,data:[],fitColumns:false,striped:false,loadMsg:'数据加载中...',
				 remoteSort:false,multiSort:true,pagination:true,pageSize:20,pageList:[10,20,30,40,50],border:true"
								    componentType='datagrid'   >
					    <thead>
							<tr> 
								<th data-options="field:'stat_time',width:100,sortable:true">时间</th>
								<th data-options="field:'merchant_id',width:100,sortable:true">商户ID</th>
								<th data-options="field:'merchant_name',width:150,sortable:true">商户名称</th>
								<th data-options="field:'act_user',width:150,sortable:true">活跃用户数</th>
								<th data-options="field:'act_rate',width:100,sortable:true">活跃率</th>
								<th data-options="field:'new_act_user',width:100,sortable:true">新增活跃用户数</th>
								<th data-options="field:'old_act_user',width:100,sortable:true">活跃用户数</th>
		  					</tr>
					  </thead>
				</table>
				
				</div>
	        </div>
		
        
        </div>
	</div> --%>
</body>
</html>
