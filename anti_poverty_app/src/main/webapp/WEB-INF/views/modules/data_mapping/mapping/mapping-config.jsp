<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<html>
<head>
	<style type="text/css">
	.datagrid-cell-c2-isKeys tr td{text-align: center;}
	.datagrid-cell-c2-isKeys input{width: 15px;height: 18px;}
	</style>
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/two-window-selecter.js"></script>
	<SCRIPT type="text/javascript">
		var selecter;
		var id = '${id}';
		var table = '${table}';
		var mapping = '${comment}';
		
		
		/*保存宽表信息并加载属性信息*/
		function savecolumn(){
			var tableName = '${table}';
			var mappingName = $("#mappingName").textbox('getValue');
			var status = $("#status").val();
			var isPerimssion = $("#isPerimssion").combobox("getValue");
			var tableType = $("#tableType").combobox("getValue");
			var cycType = $("#cycType").combobox("getValue");
			var storageType = $("#storageType").combobox("getValue");
			if(common.utils.isEmpty(mappingName)){
				common.utils.showErrorMsg("数据表映射名不能为空！");
				return;
			}
			
			var url = "";
			var params = {};
			var isSave = true;/* 是否为保存操作 */
			if(common.utils.isEmpty(id)){
				/* 判断ID值是否存在,不存在则为新增数据 */
				isSave = true;
				url = ctx + "/a/dataMapping/mapping/tablemapping/save";
				params = {tableName:tableName,mappingName:mappingName,status:status,isPerimssion:isPerimssion,tableType:tableType,cycType:cycType,storageType:storageType};
			}else{
				isSave = false;
				url = ctx + "/a/dataMapping/mapping/tablemapping/rename";
				params = {id:id,mappingName:mappingName,isPerimssion:isPerimssion,tableType:tableType,cycType:cycType,storageType:storageType};
			}
			/* 异步请求操作 */
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	             	if(isSave){
	             		id = rs.id;
	             		$("#id").val(id);
	             		/* 刷新选择器列表 */
	             		selecter.reload({id:id,table:table}, {mpTableId:id});
	             		$('#load_btn').linkbutton('enable');
	             		$('#remove_btn').linkbutton('enable');
	             		parent.window.reloadMappingList();
	             		common.utils.showSucMsg("数据表映射已保存成功！");
	             	}else{
	             		common.utils.showSucMsg("数据表映射已保存成功！");
	             		parent.window.reloadMappingList();
	             	}
	             }
	         });
		}
		/* 加载数据表的列信息 */
		function loadcolumn(){
			var id = '${id}';
			if(common.utils.isEmpty(id)){
				common.utils.showErrorMsg("请先保存当前的映射表信息！");
				return;
			}
			/* 刷新选择器列表 */
     		selecter.reload({id:id,table:table}, {mpTableId:id});
		}
		
		/* 删除数据表映射信息（关联删除对应的列信息） */
		function deleteTableColumn(){
			layer.confirm('是否确定删除该映射配置？', {
			    btn: ['删除','取消'] 
			}, function(){
				var id = '${id}';
				if(common.utils.isEmpty(id)){
					common.utils.showErrorMsg("请先保存当前的映射表信息！");
					return;
				}
				var url = ctx + "/a/dataMapping/mapping/tablemapping/remove?id="+id;
				$.ajax({
		             type: "POST",
		             url: url,
		             dataType: "json",
		             success: function(rs){
		             	if(rs){
		    				common.utils.showSucMsg("数据表映射已删除成功！",function(){
		             			/* 关闭对应的tab页 */
		             			parent.window.reloadMappingList();
		             			parent.window.removeTab(table,"映射管理("+table+")");
		             		});	             		
		             	}else{
		             		common.utils.showErrorMsg("数据表映射删除失败，请稍候再试！");	 
		             	}
		             }
		         });
				
			}, function(){
			    
			});
		}

	</SCRIPT>
	
	<!-- 定义属性动态选择器 -->
	<script type="text/javascript">
		
		var noselectcol = {
				title:"未配置列信息",
				url:ctx + "/a/dataMapping/mapping/dbcolumn/list",
				queryParams:{id:id,table:table},
				idField : 'column',
				columns : [{
					field : 'dataLength',
					hidden:true
				},{
					field : 'dataPrecision',
					hidden:true
				},{
					field : 'nullable',
					hidden:true
				},{
					field : 'columnId',
					hidden:true
				},{
					width : '40%',
					title : '数据列名',
					field : 'column'
				},{
					width : '40%',
					title : '注释',
					field : 'comment',
					sortable : true
				},{
					width : '15%',
					title : '数据类型',
					field : 'dataType'
				}]
		};
		
		/* 定义后台数据类型枚举值 */
		var types = [
		       { "id": "s", "name": "字符" }, 
		       { "id": "n", "name": "数字" }, 
		       { "id": "d", "name": "日期" }
		 ];
		
		function typeFormatter(value, rowData, rowIndex) {
			for (var i = 0; i < types.length; i++) {
			    if (types[i].id === value) {
			        return types[i].name;
			    }
			}
		 }
		
		function isKeysFormatter(value, rowData, rowIndex) {
			var check="";
			if(value=='Y'){
				check='checked="checked"';
			}
			//return '<input style="width: 15px;height: 18px" '+check+' type="checkbox" class="keyscheck" />';
			return '<div class="keyscheck" style="">'+value+'</div>';
			
		 }
		function updateKeys(){
			console.log('11')
		}
		
		var hasselectcol = {
				title:"已配置列信息",
				url:ctx + "/a/dataMapping/mapping/columnmapping/list",
				queryParams:{mpTableId:id},
				editRow:undefined,
				idField : 'id',
				columns : [{
					field : 'id',
					hidden:true
				}, {
					width : '35%',
					title : '数据列名',
					field : 'columnName'
				}, {
					width : '40%',
					title : '映射名',
					field : 'mappingName',
					editor:{type:'textbox', align:'left',options:{required:true}}
				}, {
					width : '15%',
					title : '类型',
					field : 'columnType',
					align : "center",
					formatter:typeFormatter,
					editor: { type:'combobox',  options: {data: types,editable:false,valueField: "id", textField: "name" }}
				}, {
					width : '6%',
					title : '主键',
					field : 'isKeys',
					align : "center",
				//	formatter:isKeysFormatter,
					editor:{type:'checkbox',options:{on:'Y',off:'N'}}

				}
				
				
				],
				/* 列映射自定义操作 */
				onAfterEdit: function (rowIndex, rowData, changes) {
					var id = rowData["id"];
					var mapping = rowData["mappingName"];
					var columnType = rowData["columnType"]
					var isKeys = rowData["isKeys"]
		        	updateColumnMapping(id,mapping,columnType,isKeys);
		            this.editRow = undefined; 
		        },
				onDblClickRow: function (rowIndex, rowData) {
			        if (this.editRow != undefined) {
			        	$("#" + selecter.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID).datagrid('endEdit', this.editRow);
			     	}
			 
			      if (this.editRow == undefined) {
			    	   $("#" + selecter.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID).datagrid('beginEdit', rowIndex);
			           this.editRow = rowIndex;
			      }
			    },
			    onClickRow: function (rowIndex, rowData) {
			       if (this.editRow != undefined) {
			    	   $("#" + selecter.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID).datagrid('endEdit', this.editRow);
			      }			 
			    }
		};
		
		/* 通过可编辑ID值更新行信息 */
		function updateColumnMapping(id,mapping,columnType,isKeys){
	    	if(common.utils.isEmpty(mapping)) {
				common.utils.showErrorMsg("映射列名不能为空！");
				return false;
	    	}
	    	var url = ctx + "/a/dataMapping/mapping/columnmapping/update";
			var params = {id:id,mapping:mapping,columnType:columnType,isKeys:isKeys};
			/* 异步请求操作 */
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	             	if(!rs){
	    				common.utils.showErrorMsg("映射列更新失败，请稍候再试！");
	             	}
	             }
	         });	    	
		}
		
		
		/* 自定义动态入库方法 */
		/* movein和moveout自定义操作 */
		function mappingMoveIn(nogridid,hasgridid){
			common.utils.showLoading();
			var checkeds = $("#"+nogridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.closeLoading();
				common.utils.showErrorMsg("请选择要移入的数据列！");
				return;
			}
			
			var id = $('#id').val();
			var table = $('#tableName').textbox('getValue');
			var url = ctx + "/a/dataMapping/mapping/columnmapping/savelist";
			var params = {
					mpTableId:id,
					mpTableName:table,
					columns:JSON.stringify(checkeds)
			};
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             traditional:true,
	             success: function(rs){
	            	 common.utils.closeLoading();
	             	if(rs){
	             		/* 需要清除已选择部分 */
	             		selecter.reload({id:id,table:table}, {mpTableId:id});	             		
	             	}else{
	    				common.utils.showErrorMsg("数据列映射保存失败，请稍候再试！");
	             	}
	             }
	         });
		}
		
		function mappingMoveOut(nogridid,hasgridid){
			common.utils.showLoading();
			var checkeds = $("#"+hasgridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.closeLoading();
				common.utils.showErrorMsg("请选择要移出的数据列！");
				return;
			}
			var columnids = new Array();
			for (var i = checkeds.length-1; i >= 0 ; i--) {
				columnids.push(checkeds[i].id);
			}
			var url = ctx + "/a/dataMapping/mapping/columnmapping/removelist";
			$.ajax({
	             type: "POST",
	             url: url,
	             data: {columnids:columnids},
	             dataType: "json",
	             success: function(rs){
	            	 common.utils.closeLoading();
	             	if(rs){
	             		/* 刷新选择器列表 */
	             		selecter.reload({id:id,table:table}, {mpTableId:id});
	             	}else{
	    				common.utils.showErrorMsg("数据列映射删除失败，请稍候再试！");
	             	}
	             }
	         });
			
		}
		
		
		$(function(){
			/* 初始化ID，数据表名与注释信息 */
			$("#id").val('${id}');
			$("#tableName").textbox('setValue','${table}');
			$("#mappingName").textbox('setValue','${comment}');
			$("#isPerimssion").combobox('setValue', '${isPerimssion}');
			$("#tableType").combobox('setValue', '${tableType}');
			$("#cycType").combobox('setValue', '${cycType}');
			$("#storageType").combobox('setValue', '${storageType}');
			$("#status").val('${status}');
			
			/* 初始化按钮控制 */
			if(common.utils.isEmpty(id)){
				$('#load_btn').linkbutton('disable');
		        $('#remove_btn').linkbutton('disable');
			}			
			
			var h = $(window).height()-151;
			
			/* 初始化两选框控件 */
			selecter = new TwoWindowsSelecter(
					"database_column_selecter",
					{title:"数据列配置",height:360,width:'98.5%',height:h,fit:false,fitColumns:true},
					noselectcol,
					hasselectcol,
					{moveIn:"mappingMoveIn",moveOut:"mappingMoveOut"}
			);
	 	
			
			
		});
		
		
	</script>
</head>
<body> 
  <fieldset>
	<legend>业务宽表映射配置</legend>
		<br>
  		<form id="mappingConfig" method="post">
		    <div  style="padding:5px;background:#fafafa;width:97.7%;border:1px solid #ccc;">   
		    	<table>
		    		<tr>
		    			<td>
		    				<label for="tableName">数据表名：</label>   
					        <input type="hidden" id="id" name="id">  
					        <input type="hidden" id="status" name="status">
					        <input id="tableName" class="easyui-textbox" readonly="readonly" name="tableName" style="width:200px;" />
		    			</td>
		    			<td>
		    				<span style="margin-left: 20px;">
			    				<label for="mappingName">映射表名：</label>
			    				<input id="mappingName" class="easyui-textbox"  name="mappingName" style="width:200px;" data-options="required:true"/>
		    				</span>
		    			</td>
		    			<td>
		    				<span style="margin-left: 20px;">
			    				<label for="cycType">周期类型：</label>
			    				<select id="cycType" class="easyui-combobox" name="cycType" style="width:200px;" data-options="required:true"> 
									<option value="D" selected="selected">日周期</option>    
									<option value="W" >周周期</option> 
									<option value="M" >月周期</option> 
									<option value="Y" >年周期</option> 
								</select>
		    				</span>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>
		    				<label for="isPerimssion">数据权限：</label> 
		    				<select id="isPerimssion" class="easyui-combobox" name="isPerimssion" style="width:200px;" data-options="required:true"> 
								<option value="1">包含权限数据</option>    
								<option value="0" selected="selected">不包含权限数据</option> 
							</select>
		    			</td>
		    			<td>
		    				<span style="margin-left: 20px;">
			    				<label for="tableType">数据表类型：</label> 
			    				<select id="tableType" class="easyui-combobox" name="tableType" style="width:200px;" data-options="required:true"> 
									<option value="DIM">维度表</option>    
									<option value="FACT" selected="selected">事实表</option> 
								</select>
							</span>
		    			</td>
		    			
		    			<td>
		    				<span style="margin-left: 20px;">
			    				<label for="storageType">存储类型：</label>
			    				<select id="storageType" class="easyui-combobox" name="storageType" style="width:200px;" data-options="required:true"> 
									<option value="I" >增量</option>    
									<option value="F"  selected="selected">全量</option> 
								</select>
		    				</span>
		    			</td>
		    			
		    			
		    			
		    			<td>
		    				<span style="margin-left: 20px;">
					        	<a id="save_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-hdd'" onclick="javascript:savecolumn()">保存映射</a>
					        	<a id="remove_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-trash'" style="margin-left: 10px" onclick="javascript:deleteTableColumn()">删除映射</a>
					        </span>
		    			</td>
		    		</tr>
		    	</table>
		    </div>  
		</form>
		<hr/>
  		<div id="database_column_selecter"></div>   
		
    </fieldset>
  </body>
</html>
