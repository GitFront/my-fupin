<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<html>
<head>
    <title>配置项管理</title>
    <script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/request.js"></script>
    <script type="text/javascript" src="${ctxStatic}/js/modules/data_mapping/tableassosication.js"></script>
</head>
<script>
    //var treeData = JSON.parse('${treeData}');
</script>
<body>

<fieldset style="padding: 10px;  margin-left:20px;  margin-right: 20px;"  >
    <legend>表映射选择</legend>
    <div style="padding:5px;width:97%;height:50%;">
            <div>主题：<input id="theme"  style="width:135px;height: 22px; float: left;" 
            	data-options="valueField:'id',textField:'type',url:'${ctx}/a/dataMapping/mapping/tablecatalog/theme'" /></div>
        <div style="max-height:350px;width:48%;float:left;background:#fafafa;border:1px solid #ccc;overflow-y: auto;height:100%">
            <div>源表 </div>
            <div>
                <ul id="sourceTableTree" class="ztree"></ul>
            </div>
        </div>
        <div style="max-height:350px;width:48%;float:right;background:#fafafa;border:1px solid #ccc;overflow-y: auto;height:100%">
            <div>映射表</div>
            <div>
                <ul id="mapingTableTree" class="ztree"></ul>
            </div>
        </div>
    </div>
</fieldset>

<fieldset style="padding: 10px;  margin-left:20px;  margin-right: 20px;"  >
    <legend>查询区域</legend>
    <div style="padding:5px;background:#fafafa;width:97%;border:1px solid #ccc;">
        <label for="table1">表一：</label>
        <span>
			     	<select id="table1" class="easyui-combobox" name="table1"
                            data-options="editable:true, panelHeight:'auto',valueField:'key',textField:'value'" style="width:120px;">
                    </select>
				</span>
        <label for="table2" style="margin-left: 10px">表二：</label>
			     <span>
			     	<select id="table2" class="easyui-combobox" name="table2"
                            data-options="editable:true, panelHeight:'auto',valueField:'key',textField:'value'" style="width:120px;">
                    </select>
				</span>
			     <span style="margin-left: 20px;">
			       	<a id="btn1" href="javascript:search();" class="easyui-linkbutton" data-options="iconCls:'icon-btn-refresh'">查 询</a>
			     </span>
    </div>
</fieldset>
<div style="margin-top: 20px;   margin-left:20px;  margin-right: 20px;">
    <table id="associationTable" singleSelect="true"  componentType='datagrid' autoResize="false" resizeWidth="true" resizeHeight="20">
        <thead>
        <tr>
            <th data-options="field:'tn1',width:80,halign:'center',align:'left',sortable:true">源表</th>
            <th data-options="field:'cn1',width:80,halign:'center',align:'left',sortable:true">源字段</th>
            <th data-options="field:'tn2',width:80,halign:'center',align:'center'">映射表</th>
            <th data-options="field:'cn2',width:80,halign:'center',align:'center',sortable:true">映射字段</th>
            <th data-options="field:'oper',width:150,halign:'center',align:'center',formatter:formatOper">操作</th>
        </tr>
        </thead>
    </table>
</div>

</body>
</html>
