<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%> 
<html>
<head>
  <title>列表过滤器管理</title>
  <script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/list-filter-config.js"></script>
  <script type="text/javascript">
		$(function(){
			modules.smartquery.listfilter.init();
		});
	</script>
</head>

<body>
	<!-- 过滤器列表 start-->
	<div style="padding:5px;">
	  <table id="listFilterTable" componentType='datagrid' autoResize="true" resizeWidth="true" resizeHeight="20" fitColumns="true">
	    <thead>
		    <tr>
			    <th data-options="field:'cb',checkbox:true"></th>
			    <th data-options="field:'id',hidden:true"></th>			    
			    <th data-options="field:'listName',width:150,halign:'center',align:'left',sortable:true">过滤器名称</th>
			    <th data-options="field:'listSeparator',width:60,halign:'center',align:'center',sortable:true,formatter:modules.smartquery.listfilter.list.separatorFormat">条目分隔符</th>
			    <th data-options="field:'listNum',width:80,halign:'center',align:'right',sortable:true">条目数量</th>
			    <th data-options="field:'creator',width:50,halign:'center',align:'center'">创建人</th>
			    <th data-options="field:'createTime',width:80,halign:'center',align:'center'">创建时间</th>
		     	<th data-options="field:'oper',width:120,halign:'center',align:'center',formatter:modules.smartquery.listfilter.list.operFormat">操作</th>
		    </tr>
	    </thead>
	  </table>
	</div>
	<!-- 过滤器列表 end-->
	
	<!-- 过滤器列表工具栏  start-->
	<div id="list_ctrl_tb" style="padding:3px;height:auto">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<input class="easyui-textbox" id="listinfo" name="listinfo" style="width:180px" data-options="prompt:'请输入过滤器名称进行查询...'"/>
		   			<a id="search_list_filter_btn" href="#" class="easyui-linkbutton" iconCls="icon-btn-search" plain="true">查 询</a>
				</td>
				<td style="margin-left: 5px;margin-right: 5px;">
					<div class="datagrid-btn-separator"></div>
				</td>
				<td>
					<a id="add_list_filter_btn" href="#" class="easyui-linkbutton" iconCls="icon-btn-plus-sign" plain="true">新 增</a>
				</td>
				<td>
					<a id="del_list_filter_btn" href="#" class="easyui-linkbutton" iconCls="icon-btn-remove-sign" plain="true">删 除</a>  
				</td>
			</tr>
		</table>
	</div>  
	<!-- 过滤器列表工具栏  end-->

	<!-- 新增过滤器上传窗口 start -->
	<div id="listuploadWin" class="easyui-dialog" title="上传列表过滤器窗口" 
		 data-options="iconCls:'icon-btn-upload',resizable:false,modal:true,draggable:true,closable:false,closed:true,
		 buttons:[{id:'upload_list_btn',text:'上 传',iconCls:'icon-btn-ok'},{id:'close_upload_list_btn',text:'取 消',iconCls:'icon-btn-remove'}]">   
		 <div id="listFilterDiv" style="padding:15px;" >
			<form id="listFilterForm" method="post" target="uploadFrame" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id">
				<input type="hidden" id="listNum" name="listNum">
				<input type="hidden" id="filePath" name="filePath">
				<input type="hidden" id="creatorId" name="creatorId">
				<input type="hidden" id="creator" name="creator">
				<input type="hidden" id="creatorAcc" name="creatorAcc">
				<input type="hidden" id="createTime" name="createTime">
				<input type="hidden" id="sort" name="sort">
				<div style="margin-top: 10px">
					<label for="listName">列表名称：</label>
					<input class="easyui-textbox" type="text" id="listName" name="listName" style="width:230px;" 
						   data-options="prompt:'请输入过滤器名称...',required:true"/>
				</div>
				<div style="margin-top: 10px">
					<label for="listFile">上传列表：</label>
					<input class="easyui-filebox" id="listFile" name="listFile" style="width:230px" data-options="prompt:'上传匹配文件...',accept:'txt'">
				</div>
				<div style="margin-top: 10px">
					<label for="listSeparator">条目分隔：</label>
					<input class="easyui-textbox" type="text" id="listSeparator" name="listSeparator" style="width:230px;" 
						   data-options="prompt:'请输入条目的分隔符，默认以换行分隔...',required:false"/>
				</div>
				<div style="margin-top: 10px">
					<label for="listSeparator">数据文件：</label>
					<input class="easyui-textbox" type="text" id="fileName" name="fileName" style="width:230px;" 
						   data-options="prompt:'上传成功后自动生成...',required:false,readonly:true"/>
				</div>
			</form>
		</div>
	</div>
	<form id="downloadForm" method="post" target="uploadFrame">
		<input type="hidden" id="downloadId" name="downloadId">
	</form>
	<iframe style="display: none;" id="uploadFrame" name="uploadFrame"></iframe>
	<!-- 新增过滤器上传窗口 end -->
</body>
</html>
