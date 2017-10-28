<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	
	<link rel="stylesheet" type="text/css" href="${ctxPlugins}/jquery/zTree_v3/css/zTreeStyle/metro.css"></link>
	<!-- 标签显示样式 -->
	<style type="text/css">
		.domBtnDiv {
			display: block;
			padding: 4px;
			border: 1px gray dotted;
			background-color: #fafafa;
			width: 98.5%;
			height:86%;
			margin-left: 2px;
		}
		
		.tableDiv {
			display: inline-block;width:100%;height:100%;
		}
		
		.domBtn {
			display: inline-block;
			cursor: pointer;
			padding: 5px;
			margin: 2px 7px;
			border: 0px;
			background-color: #72CFD7;
			color: #FFFFFF;
			font-family: "Microsoft YaHei";
		}
	</style>
	
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/modules/data_label/user-analysis.js"></script>
	<script type="text/javascript">
		$(function(){
			modules.datalabel.useranalysis.init();
		});		
	</script>
</head>
<body class="easyui-layout">
	<div id="types" data-options="region:'west',split:true,title:'数据标签树',
		    	tools:[{iconCls:'icon-btn-time',handler:modules.datalabel.useranalysis.utils.tipDefaultTime},{iconCls:'icon-btn-info-sign'}]"
		style="width:400px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',collapsible:false" style="height: 28px;">
				<div style="padding: 2px;">
					<input class="easyui-textbox" type="text" id="searchTreeID" name="searchTreeID" style="width:386px;"
						data-options="prompt:'请输入节点名称并按回车键进行查询...',required:false" />
				</div>
			</div>
			<div data-options="region:'center'">
				<div>
					<!-- 数据标签树 -->
					<ul id="labelTreeID" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>

	<div data-options="region:'center'">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',title:'周期属性区',collapsible:false" style="height: 132px;">
				<div class="domBtnDiv">
					<div id="dom_timer" class="tableDiv"></div>
				</div>
			</div>
			<div data-options="region:'center'"
				style="padding:5px;background:#eee;">
				<div style="padding:5px;background:#fafafa;border:1px solid #eee;">   
				   <span>				   	   
				   	   <shiro:hasPermission name="hydc.datalabel.search">
				       		<a id="label_search_btn" href="#" class="easyui-linkbutton" style="margin-left: 5px;" data-options="iconCls:'icon-btn-search'">数据查询</a>
				       </shiro:hasPermission>
					   <shiro:hasPermission name="hydc.datalabel.export">
			 		  	 	<a id="data_export_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-share'" style="margin-left: 5px;">数据导出</a>	  	 	  
				  	   </shiro:hasPermission>
				  	   <shiro:hasPermission name="hydc.datalabel.search">
				  	   		<a id="label_clear_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-trash'" style="margin-left: 5px;">标签清空</a>
				       		<a id="label_setting_btn" href="#" class="easyui-linkbutton" style="margin-left: 5px;" data-options="iconCls:'icon-btn-cog'">操作配置</a>
				       </shiro:hasPermission>
				   </span>
				</div>
				<div style="margin-top: 5px;">
					<table id="resultTableID"></table>
				</div>
			</div>
			<div
				data-options="region:'south',title:'标签筛选区',collapsible:false"
				style="height: 132px;">
				<div class="domBtnDiv">
					<div id="dom_tags" class="tableDiv"></div>
				</div>
			</div>
		</div>
	</div>

	<div id="settingWinId" class="easyui-dialog" title="查询设置窗口"
		data-options="iconCls:'icon-btn-cog',resizable:false,draggable:true,closable:false,closed:true,
			 buttons:[{id:'setting_btn',text:'应 用',iconCls:'icon-btn-ok'},{id:'close_setting_btn',text:'关 闭',iconCls:'icon-btn-remove'}]">
		<div id="searchNode" style="padding:5px;">
			<div>
				<label for="searchType">查询方式：</label> <select id="searchType"
					class="easyui-combobox" name="searchType" style="width:150px;">
					<option value="normal">被动查询</option>
					<option value="instant">主动查询</option>
				</select>
			</div>
		</div>
	</div>

	<div id="progressWinId" class="easyui-window" title="数据导出进度"
		data-options="iconCls:'icon-btn-time',modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,draggable:true,closable:false,closed:true">
		<div style="padding:5px;">
			<div id="exportProgress" class="easyui-progressbar" data-options="value:0" style="width:400;"></div> 
		</div>
	</div>
	
	<div id="historyWinId" class="easyui-dialog" title="历史周期选择窗口"
		data-options="iconCls:'icon-btn-calendar',resizable:false,draggable:true,closable:true,closed:true,
			 buttons:[{id:'select_history_btn',text:'选 择',iconCls:'icon-btn-ok'},{id:'close_select_history_btn',text:'关 闭',iconCls:'icon-btn-remove'}]">
		<div style="padding:5px;">
			<ul id="historyID" class="easyui-datalist" title="数据周期" style="width:200px;height: 350px;"></ul>
		</div>
	</div>
	
	<!-- 查询结果过滤工具栏  start-->
	<div id="result_search_tb" style="padding:3px;height:auto">     
		<div>
		   	<input class="easyui-textbox" id="user_num_info" name="user_num_info" style="width:350px" data-options="prompt:'请输入用户号码并按回车键进行数据过滤...'"/>
		</div>
	</div>  
	<!-- 查询结果过滤工具栏  end-->
	
	<!-- 数据导出隐藏区 start -->
	<div>
		<form id="exportFrom" method="post" action="/a/data-label/user-analysis/export"></form>
		<iframe style="display: none;" id="exportFrame" name="exportFrame"></iframe>
	</div>
	<!-- 数据导出隐藏区 end -->
</body>
</html>
