<%@ page contentType="text/html;charset=UTF-8" %>
 <%@ include file="/WEB-INF/views/include/param.jsp"%>
 <%@ include file="/WEB-INF/views/include/head-fun.jsp"%> 
<html>
<head>
  <title>目录分享</title>
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/smart-query-dir-share.js"></script>
  	<script type="text/javascript">
       $(function(){
    	   modules.smartquery.catalog.init();
 	   });
</script>

</head>

<body>

<fieldset style="padding: 10px;  margin-left: 50px;"  >
  <legend>查询区域：</legend>
  <div style="padding:5px;background:#fafafa;width:97%;border:1px solid #ccc;">
    <label for="name" style="margin-left: 10px">目录名称：</label>
			     <span>
			     	<input id="name" name="name" class="easyui-textbox" type="text" style="width:120px;" />
				</span>
    <label for="name" style="margin-left: 10px">创建人：</label>
			     <span>
			     	<input id="creator" name="creator" class="easyui-textbox" type="text" style="width:120px;" />
				</span>
			     <span style="margin-left: 20px;">
			       	<a id="catalog_share_btn" class="easyui-linkbutton" data-options="iconCls:'icon-btn-refresh'">查 询</a>
			       	<a id="create_catalog_btn" class="easyui-linkbutton" data-options="iconCls:'icon-btn-folder-open'" style="margin-left: 5px">创建个人目录</a>
			     </span>
  </div>
</fieldset>
<div style="margin-top: 20px;  margin-left: 50px;">
	<table id="catalog" singleSelect="true" class="easyui-datagrid"  componentType='datagrid' autoResize="true" resizeWidth="true" resizeHeight="20">
		<thead>
			<tr>
				<th data-options="field:'name'" width="150" align="center">目录名称</th>
				<th data-options="field:'fullPath'" width="150" align="left">目录路径</th>
				<th data-options="field:'creator'" width="150" align="center">创建人</th>
				<th data-options="field:'createTime'" width="150" align="center">创建时间</th>
				<th data-options="field:'shareStatus',formatter:modules.smartquery.catalog.datagrid.f_shareStatus" width="150" align="center">共享状态</th>
				<th data-options="field:'oper',formatter:modules.smartquery.catalog.datagrid.f_oper" width="150" align="center" >操作</th>
			</tr>
		</thead>
	</table>
</div>


	<!-- window 区域 -->
	<div id="userTreeWin" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,title:'分享用户列表'" style="width:510px;height:440px;padding:10px">
	
      	<div class="easyui-layout" data-options="fit:true">
		<table class="table-d">
		<tr>
		<td>
		<fieldset style="width:440px;padding: 10px;height:320px;">
		<legend id="legend">查询区域</legend>	 
		     <div id="dir" class="easyui-panel"  >
		   	     <ul autoResize="false"  resizeHeight="10" resizeWidth="true" id="userztree1" class="ztree" style="margin-top:1px;width:400px;height:290px;overflow-x:hidden"></ul>
		   	 </div>
		 </fieldset>  	 
		</td>
		</tr> 
		</table>
             <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a id="userTreeSave" class="easyui-linkbutton" data-options="iconCls:'icon-btn-edit'"  href="javascript:void(0)"   style="width:80px">保存</a>
                <a id="userTreeCancel" class="easyui-linkbutton"  data-options="iconCls:'icon-btn-share-alt'"  href="javascript:void(0)"  style="width:80px">取消</a>
             </div>
          </div>   
      </div> 
</body>
</html>
