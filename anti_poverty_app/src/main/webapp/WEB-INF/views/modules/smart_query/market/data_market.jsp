<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<!-- 页面功能封装 -->
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/data-market.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			/* 获取当前用户账号信息 */
			var userAcc = '${loginName}';
			modules.smartquery.market.init(userAcc);
		});
	</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'分类浏览信息',split:true" style="width:260px;">
	    <div id="qy" class="easyui-accordion"  data-options="fit:true" >
	    	<div title="按存储目录浏览" data-options="iconCls:'icon-btn-folder-open',selected:true" style="padding:5px;">
	    		<!-- 存储目录树 -->
				<ul id="fileCatalogTree" class="ztree"></ul>
			</div>
			<div title="按创建时间浏览" data-options="iconCls:'icon-btn-time'" style="padding:5px;">   
				<!-- 创建时间树 -->
				<ul id="fileTimeTree" class="ztree"></ul>
			</div>
			<div title="按自动任务项浏览" data-options="iconCls:'icon-btn-bookmark'" style="padding:5px;">   
				<!-- 自动任务配置列表 -->
				<div id="configList"></div> 
			</div>
	    </div>
    </div>
    <div data-options="region:'center',title:'数据文件详细信息'">   
        <div style="padding: 15px">
			<table id="fileTable" componentType='datagrid' autoResize="true" resizeWidth=true resizeHeight="30">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'creatorAcc',hidden:true"></th>
						<th data-options="field:'creatorId',hidden:true"></th>
						<th data-options="field:'fileName',width:180,halign:'center',align:'left'">文件名</th>
						<th data-options="field:'fileType',width:80,halign:'center',align:'center'">文件类型</th>
						<th data-options="field:'fileSizeCov',width:80,halign:'center',align:'right'">文件大小</th>
						<th data-options="field:'dataCyc',width:120,halign:'center',align:'center'">数据周期</th>
						<th data-options="field:'catalogPath',width:180,halign:'center',align:'left'">存储目录</th>
						<th data-options="field:'creator',width:50,halign:'center',align:'center'">创建人</th>
						<th data-options="field:'createTime',halign:'center',align:'center'">创建时间</th>
						<th data-options="field:'status',width:50,halign:'center',align:'center',formatter:modules.smartquery.market.files.statusFormat">状态</th>
						<th data-options="field:'oper',width:120,halign:'center',align:'left',formatter:modules.smartquery.market.files.operFormat">操作</th>
			  		</tr>
				</thead>
			</table>
			<!-- 文件下载隐藏区域 -->
		    <form id="downloadFrom" method="post" action="${ctx}/a/smart-query/market/file/download"></form>
			<iframe style="display: none;" id="downloadFrame" name="downloadFrame"></iframe>
		</div>
		<!-- window 分享区域 -->
		<div id="userTreeWin" class="easyui-dialog" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,title:'分享用户列表',
			        buttons:[{text:'分 享',iconCls:'icon-btn-share-alt',handler:modules.smartquery.market.shareUser.share},
			        {text:'取 消',iconCls:'icon-btn-remove',handler:modules.smartquery.market.shareUser.close}]">
			<div style="padding: 10px;">
				<ul autoResize="false"  resizeHeight="10" resizeWidth="true" id="userztree" class="ztree" style="width:380px;height:400px;overflow-x:hidden"></ul>
			</div>
	    </div> 
    </div>
    
    
 </body>
</html>
