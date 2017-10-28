<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
	
	<script type="text/javascript">
		var editTree = {};
		/* 标签页添加方法 */
		function addTabs(id,title,url){
			/* 判断标签是否存在 */
			if($('#configs').tabs('exists',title)){		
				/* 选择已有标签 */
			    $('#configs').tabs('select',title);
				
			}else{
				/* 创建新的标签页及iframe内容 */
				var content ="<iframe id='"+id+"',name='"+id+"' scroll=auto frameborder='0' src='"+url+"' style='width:100%; height:100%; overflow-x:hidden;'></iframe>";  
				$('#configs').tabs('add',{
					 title:title,
					 content:content,
					 closable:true,
					 fit : true
				});  
			}		
		}
		
		/* 映射列表点击方法 */
		function mappingClick(rowIndex, rowData){
			
			var url =ctx + "/a/dataMapping/mapping/mapping-config.htm?table="+rowData.tableName+"&id="+rowData.id;
			addTabs(rowData.tableName,"映射管理("+rowData.tableName+")",url);
		}
		
		function nomappingClick(rowIndex, rowData){
			
			var url =ctx + "/a/dataMapping/mapping/mapping-config.htm?table="+rowData.table;
			addTabs(rowData.table,"映射管理("+rowData.table+")",url);
		}
		
		function reloadMappingList(){
			$("#noMappingList").datalist("reload");
			$("#hasMappingList").datalist("reload");
		}
		
		/**
		 * 映射类型分组管理
		 */
		function typeFormatter(value,rows){
			var tableType = "事实表";
			if(value === 'DIM')
				tableType = "维度表";
			return tableType;
		}
		
		$(document).ready(function(){
			
			/* 加载未配置列表 */
			$("#noMappingList").datalist({
				url : ctx + "/a/dataMapping/mapping/dbtable/list",
				valueField : "table",
				textField : "table",
				checkbox : true,
				lines : true,
				fit:true,
				onSelect : nomappingClick
			});

			/* 加载已配置列表 */
			$("#hasMappingList").datalist({
				url : ctx + "/a/dataMapping/mapping/tablemapping/list",
				valueField : "id",
				textField : "mappingName",
				groupField: "tableType",
				groupFormatter: typeFormatter,
				checkbox : true,
				lines : false,
				fit:true,
				onSelect : mappingClick
			});
			
			$('#searchMP').textbox('textbox').keydown(function(e) {
				if (e.keyCode == 13) {
					var mappingName=$("#searchMP").textbox("getValue");
					$("#hasMappingList").datalist("reload",{mappingName:mappingName});
				}
			});
			$('#searchDB').textbox('textbox').keydown(function(e) {
				if (e.keyCode == 13) {
					var tableName=$("#searchDB").textbox("getValue");
					$("#noMappingList").datalist("reload",{tableName:tableName});
				}
			});
			
		});
	</script>
	
</head>
	<body>  
	<div class="easyui-layout" fit="true" >
	  
	    <div id="cgTree" class="easyui-accordion"  data-options="border:false,region:'west',split:true,title:'映射管理'" style="width:250px;">
	       
	    	<div title="已配置宽表管理" data-options="iconCls:'icon-btn-eye-open',selected:true" style="overflow-x:hidden;">
	    		<div style="padding: 2px;">
					<input class="easyui-textbox" type="text" id="searchMP" name="searchMP" style="width:225px;"
						data-options="prompt:'请输入表名称并按回车键进行查询...',required:false" />
				</div>
	    		<!-- 已配置映射列表 -->
				<div id="hasMappingList" style="width:100px;"></div>
			</div>
			<div title="未配置宽表管理" data-options="iconCls:'icon-btn-eye-close'" style="overflow-x:hidden;">
				<div style="padding: 2px;">
					<input class="easyui-textbox" type="text" id="searchDB" name="searchDB" style="width:225px;"
						data-options="prompt:'请输入表名称并按回车键进行查询...',required:false" />
				</div>
	    		<!-- 未配置映射列表 -->
				<div id="noMappingList"></div>
			</div>
	    </div>
	    <div id="configs" class="easyui-tabs"  data-options="region:'center'"  style="padding: 1px;overflow:hidden;"></div>
	    
	 </div>   
</body>
</html>
