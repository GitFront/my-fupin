<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 功能插件引入 -->
<script type="text/javascript"
	src="${ctxStatic}/js/modules/data_label/label-config.js"></script>
<script type="text/javascript">
	$(function() {
		var id = '${treeNode}';
		var name = '${nodeName}';
		var isParent = '${isParent}';
		modules.datalabel.config.init(id,name,isParent);
	});
</script>
</head>
<body>
	<body>
		<!-- 标签属性编辑区 start -->
		<div style="border:1px solid #96c2f1;background:#eff7ff;padding: 10px;width: 380px;">
			<div style="padding:5px;background:#fafafa;width: 350px;border:1px solid #ccc;">
				<span> <a id="label_save_btn" href="#"
					class="easyui-linkbutton" data-options="iconCls:'icon-btn-hdd'">保存属性</a>
					<a id="label_repeat_btn" href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-btn-repeat'" style="margin-left: 5px">重置属性</a>
				</span>
			</div>
			<div id="property" style="margin-top: 10px">
				<form id="labelConfig" method="post">
					<div>
						<label for="fullPath">节点路径:</label> <input class="easyui-textbox"
							type="text" id="fullPath" name="fullPath"
							style="width:200px;height:50px"
							data-options="prompt:'自动读取路径...',required:true,readonly:true,multiline:true" />
						<input type="hidden" id="id" name="id" /> 
						<input type="hidden" id="pid" name="pid" /> 
					</div>
					<div style="margin-top: 10px">
						<label for="name">节点名称:</label> <input class="easyui-textbox"
							type="text" id="name" name="name" style="width:200px;"
							data-options="prompt:'标签或目录的名称...',required:true,readonly:true" />
					</div>
					<div style="margin-top: 10px">
						<label for="type">节点类型:</label> <select id="type"
							class="easyui-combobox" name="type" style="width:200px;"
							data-options="valueField:'id',textField:'text',onSelect:modules.datalabel.config.form.choiceLabelType">
							<option value="catalog" selected="selected">目录</option>
							<option value="label">标签</option>
						</select>
					</div>
					<div style="margin-top: 10px">
						<label for="sort">节点排序:</label> <input id="sort" name="sort"
							class="easyui-numberspinner" style="width:200px;"  
							data-options="min:0,editable:true,prompt:'节点的排列...'">
					</div>
					
					<fieldset id="labelProperty"
						style="margin-top: 10px;display: none;width: 350px">
						<legend>数据标签属性</legend>
						<div style="margin-top: 10px">
							<label for="assoField">关联属性:</label> <input
								class="easyui-textbox" type="text" id="fieldName"
								name="fieldName" style="width:150px;"
								data-options="required:true,readonly:true,prompt:'请选择标签的关联属性...'" />
							<a id="seach_field_btn" href="#" class="easyui-linkbutton"
								data-options="iconCls:'icon-btn-search'"></a> 
								<input type="hidden" id="assoTableId" name="assoTableId" /> 
								<input type="hidden" id="assoTable" name="assoTable" /> 
								<input type="hidden" id="assoFieldId" name="assoFieldId" />
								<input type="hidden" id="assoField" name="assoField" />
						</div>
						<div style="margin-top: 10px">
							<label for="dimName">关联维表:</label> 
							<input class="easyui-textbox"
									type="text" id="dimName" name="dimName" style="width:150px;"
									data-options="required:true,readonly:true,prompt:'请选择标签的关联维表...'" />
								<a id="seach_dim_btn" href="#" class="easyui-linkbutton"
									data-options="iconCls:'icon-btn-search'"></a>
								<input type="hidden" id="assoDimId" name="assoDimId" /> 
								<input type="hidden" id="assoDim" name="assoDim" /> 
								<input type="hidden" id="assoRule" name="assoRule" /> 
						</div>
						<div style="margin-top: 10px">
							<label for="topNum">取数排名:</label> <input id="topNum"
								name="topNum" class="easyui-numberspinner" style="width:180px;"
								data-options="min:0,max:100,editable:true,prefix:'top',prompt:'为零时则取全部数据...',value:0" />
						</div>
						<div style="margin-top: 10px">
							<label for="active">是否激活:</label> <select id="active"
								class="easyui-combobox" name="active" style="width:180px;"
								data-options="valueField:'id',textField:'text'">
								<option value="1" selected="selected">启用</option>
								<option value="0">停用</option>
							</select>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
		<!-- 标签属性编辑区 end -->

		<!-- 选择关联属性配置窗口 start -->
		<div id="fieldSelectWin" class="easyui-dialog" title="关联属性选择窗口"
			data-options="iconCls:'icon-btn-retweet',resizable:false,modal:true,closed:true,draggable:true,
			        buttons:[{id:'field_select_btn',text:'选 取',iconCls:'icon-btn-ok'},{id:'close_field_select_btn',text:'取 消',iconCls:'icon-btn-remove'}]">
			<div>
				<ul id="fieldTree" class="ztree" style="height: 350px;width: 350px;"></ul>
			</div>
		</div>
		<!-- 选择关联属性配置窗口 end -->
		
		<!-- 选择关联维表配置窗口 start -->
		<div id="dimSelectWin" class="easyui-dialog" title="关联维表选择窗口" style="width: 300px;"
			data-options="iconCls:'icon-btn-retweet',resizable:false,modal:true,closed:true,draggable:true,
			        buttons:[{id:'dim_select_btn',text:'确 定',iconCls:'icon-btn-ok'},{id:'close_dim_select_btn',text:'取 消',iconCls:'icon-btn-remove'}]">
			<div style="padding: 5px;">
				<form  id="dimSelecter" method="post">
					<div style="margin-top: 10px">
						<label for="dimTable">关联维表:</label> 
						<select id="dimTable" class="easyui-combogrid" name="dimTable" style="width:180px;"   
						        data-options="required:true"></select>  
					</div>
					<div style="margin-top: 10px">
						<label for="ruleID">对应主键:</label> 
						<input id="ruleID" class="easyui-combobox" name="ruleID"   style="width:180px;" 
    							data-options="required:true,valueField:'columnName',textField:'mappingName'" />
					</div>
					<div style="margin-top: 10px">
						<label for="ruleName">对应名称:</label> 
						<input id="ruleName" class="easyui-combobox" name="ruleName"  style="width:180px;"  
    							data-options="required:true,valueField:'columnName',textField:'mappingName'" />
					</div>
					<div style="margin-top: 10px">
						<label for="ruleSort">对应排序:</label> 
						<input id="ruleSort" class="easyui-combobox" name="ruleSort"  style="width:180px;"  
    							data-options="required:false,valueField:'columnName',textField:'mappingName'" />
					</div>
					<div style="margin-top: 10px">
						<label for="ruleTop">对应排名:</label> 
						<input id="ruleTop" class="easyui-combobox" name="ruleTop"  style="width:180px;"
    							data-options="required:false,valueField:'columnName',textField:'mappingName'" />
					</div>
				</form>
			</div>
		</div>
		<!-- 选择关联维表配置窗口 end -->
	</body>
</body>
</html>
