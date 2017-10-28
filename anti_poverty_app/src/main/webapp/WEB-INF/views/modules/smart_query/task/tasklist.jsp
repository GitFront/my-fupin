<%@ page contentType="text/html;charset=UTF-8" %>
 <%@ include file="/WEB-INF/views/include/param.jsp"%>
 <%@ include file="/WEB-INF/views/include/head-fun.jsp"%> 
<html>
<head>
  <title>配置项管理</title>
  <script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/smart-query-personal-catalog.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/request.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/smart-query-tasklist-table.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/smart-query-tasklist.js"></script>
  
</head>

<body>

<fieldset style="padding: 10px;  margin-left: 50px;"  >
  <legend>查询区域</legend>
  <div style="padding:5px;background:#fafafa;width:97%;border:1px solid #ccc;">
    <label for="user">创建人：</label>
    <span><input id="user" name="user"  style="width: 120px"/></span>
    <label for="type" style="margin-left: 10px">任务类型：</label>
			     <span>
			     	<select id="type" class="easyui-combobox" name="type"
                            data-options="required:true,editable:false, panelHeight:'auto'" style="width:120px;">
                      <option value="all" selected="selected">全部</option>
                      <option value="1">自动任务</option>
                      <option value="0">手动任务</option>
                    </select>
				</span>
    <label for="name" style="margin-left: 10px">任务名称：</label>
			     <span>
			     	<input id="name" name="name" class="easyui-textbox" type="text" style="width:120px;" />
				</span>
			     <span style="margin-left: 20px;">
			       	<a id="btn1" href="javascript:search();" class="easyui-linkbutton" data-options="iconCls:'icon-btn-refresh'">查 询</a>
			     </span>
  </div>
</fieldset>
<div style="margin-top: 20px;  margin-left: 50px;">
  <table id="configTable" singleSelect="true"  componentType='datagrid' autoResize="true" resizeWidth="true" resizeHeight="20" fitColumns="true">
    <thead>
    <tr>
      <th data-options="field:'id',hidden:true"></th>
      <th data-options="field:'name',width:80,halign:'center',align:'left',sortable:true">查询任务</th>
      <th data-options="field:'type',width:50,halign:'center',align:'center',sortable:true,formatter:exptypeformatter">导出类型</th>
      <th data-options="field:'file_name',width:180,halign:'center',align:'left',sortable:true">文件名定义</th>
      <th data-options="field:'cyc_type',width:50,halign:'center',align:'center',formatter:formatGap">重复间隙</th>
      <th data-options="field:'auther',width:50,halign:'center',align:'center',sortable:true">创建人</th>
      <th data-options="field:'commit_time',width:110,halign:'center',align:'center',sortable:true">创建时间</th>
      <th data-options="field:'last_data_cyc',width:80,halign:'center',align:'center',sortable:true,formatter:formatCommon">上次运行时间</th>
      <th data-options="field:'last_dur',width:85,halign:'center',align:'right',formatter:tickCountformatter">运行时长</th>
      <th data-options="field:'cyc_next',width:80,halign:'center',align:'center',sortable:true,formatter:formatCommon">预计运行时间</th>
      <th data-options="field:'current_status',width:50,halign:'center',align:'center',formatter:statusformatter">状态</th>
      <th data-options="field:'oper',width:300,halign:'center',align:'right',formatter:formatOper">操作</th>
    </tr>
    </thead>
  </table>
</div>

<!-- window 区域 -->
<div id="his_exception_Win" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,title:'异常日志列表'" style="width:700px;height:450px;padding:10px">
  <div class="easyui-layout" data-options="fit:true">
    <div style="  margin: 20px 20px 0px 20px;">
      <table id="exception_logs_table" singleSelect="true"  componentType='datagrid' autoResize="true" resizeWidth="true" resizeHeight="20" fitColumns="true">
        <thead>
        <tr>
          <th data-options="field:'data_cyc',width:100,halign:'center',align:'left',sortable:true">数据周期</th>
          <th data-options="field:'createTime',width:100,halign:'center',align:'center',sortable:true">异常产生时间</th>
          <th data-options="field:'description',width:180,halign:'center',align:'left',sortable:true">异常描述</th>
        </tr>
        </thead>
      </table>
    </div>
    <div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;">
      <a id="close_exception_win" class="easyui-linkbutton"   href="javascript:void(0)"  style="width:80px">关闭</a>
    </div>
  </div>
</div>

<!-- 保存配置窗口 start -->
<div id="saveConfigWin" class="easyui-dialog" title="保存查询配置"
     data-options="iconCls:'icon-btn-cog',resizable:false,modal:true,closed:true,draggable:true,
		        buttons:[{id:'save_config_btn',text:'更 新',iconCls:'icon-btn-ok'},{text:'取 消',iconCls:'icon-btn-remove',handler:function(){$('#saveConfigWin').dialog('close');}}]">
  <div style="padding:15px;" >
    <form id="saveConfig" method="post">
      <div>
        <input type="hidden" name="id" id="id"/>
        <label for="name">配置名称:</label>
        <input class="easyui-textbox" type="text" id="task_name" name="name" data-options="required:true"/>
      </div>
      <div style="margin-top: 10px">
        <a id="isOpen" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-calendar',toggle:true,plain:false" onclick="javascript:timerSwitch();">启用自动任务选项</a>
      </div>
      <fieldset id="timer" style="width:340px;height:250px;margin-top: 10px;">
        <legend>自动任务配置</legend>
        <div style="margin-top: 10px">
          <label for="fileName">数据文件名称：</label>
          <input class="easyui-textbox" type="text" id="fileName" name="fileName" style="width:160px;"
                 data-options="required:true" /> + 导出日期
        </div>
        <div style="margin-top: 10px">
          <label for=dataStoreDate>文件期限(天)：</label>
          <input id="dataStoreDate" name="dataStoreDate" class="easyui-numberspinner" style="width:160px;"
                 required="required" data-options="min:1,max:180,editable:true">
        </div>
        <div style="margin-top: 10px">
          <label for="personalCatalogId">文件保存路径：</label>
          <span><select id="personalCatalogId" name="personalCatalogId" class="easyui-combotree" data-options="url:'${ctx}/a/smart-query/query/personalcatalog/list/combotree',required:true,editable:false" style="width:160px;"></select></span>
          <span><a id="personal_catalog_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-plus-sign'">新增目录</a></span>
        </div>
        <div style="margin-top: 10px">
          <label for="validDate">任务生效时间：</label>
          <input id="validDate" name="validDate" type="text" class="easyui-datebox" required="required" style="width:160px;"></input>
        </div>
        <div style="margin-top: 10px">
          <label for="invalidDate">任务失效时间：</label>
          <input id="invalidDate" name="invalidDate" type="text" class="easyui-datebox" required="required" style="width:160px;"></input>
        </div>
        <div style="margin-top: 10px">
          <label for="cycType">任务重复周期：</label>
          <select id="cycType" class="easyui-combobox" name="cycType" style="width:160px;"  data-options="editable:false, panelHeight:'auto'">
            <option value="d">每天</option>
            <option value="w">每周</option>
            <option value="m">每月</option>
            <option value="y">每年</option>
          </select>
        </div>
        <div style="margin-top: 10px">
          <label for="cycLen">任务周期步长：</label>
          <input id="cycLen" name="cycLen" class="easyui-numberspinner" style="width:160px;"
                 required="required" data-options="min:1,max:100,editable:false">
        </div>
      </fieldset>
    </form>
  </div>
</div>

<!-- 选择路径配置窗口 start -->
<div id="editPathWin" class="easyui-dialog" title="编辑个人目录"
     data-options="iconCls:'icon-btn-tags',resizable:false,modal:true,closed:true,draggable:true,
		        buttons:[{id:'choose_personal_catalog_btn',text:'选 取',iconCls:'icon-btn-ok'},{text:'取 消',iconCls:'icon-btn-remove',handler:function(){$('#editPathWin').dialog('close');}}]">
  <div >
    <ul id="pathTree" class="ztree" style="height: 300px;width: 250px;"></ul>
  </div>
</div>
<!-- 选择路径配置窗口 end -->
</body>
</html>
