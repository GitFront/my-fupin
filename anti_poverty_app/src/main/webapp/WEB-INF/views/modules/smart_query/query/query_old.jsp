<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<style type="text/css">
		.domBtnDiv {display:block;padding:4px;border:1px gray dotted;background-color:powderblue}
		.tableDiv {display:inline-block;}
		.domBtn {display:inline-block;cursor:pointer;padding:5px;margin:2px 7px;border:1.5px gray solid;background-color:#FFE6B0;font-family:"Microsoft YaHei";}
	</style>
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/query-tree_old.js"></script>
	<script type="text/javascript">
		$(function(){
			modules.smartquery.main.init();
		});		
	</script>
	
	
</head>
<body  >
	<div id="querytreeDIV" class="easyui-layout" data-options="fit:true">
	
	
	  <div data-options="region:'west',title:'智能查询管理',split:true" style="width:210px;">
	  
		    <div id="qy" class="easyui-accordion"  data-options="fit:true" >
		    	<div title="宽表信息树" data-options="iconCls:'icon-btn-search',selected:true" style="padding:5px;">
		    		<!-- 业务宽表树 -->
					<input id="theme"  style="width:190px;height: 24px; " 
					data-options="valueField:'id',textField:'type',url:'${ctx}/a/dataMapping/mapping/tablecatalog/theme'" />  	
					
	    
					<ul id="tableMappingTree" class="ztree"></ul>
				</div>
				<div title="已有配置项" data-options="iconCls:'icon-btn-folder-open'" style="overflow:auto;">   
					<!-- 已有配置项 -->
				    <div id="queryInfoList"></div> 
				</div>
		    </div>
	  
	  </div>   
	 <div data-options="region:'center'">
	 
	    <div id="Query" class="easyui-tabs"  data-options="region:'center'"  style="padding: 1px;overflow:hidden;">  
		</div>
	 
	 </div>   
	
	</div>
	<div id="tableSelecterWin" class="easyui-dialog" title="请选择需要关联查询的业务宽表" style="width:400px;height: 200px;"
		 data-options="iconCls:'icon-btn-check',resizable:false,modal:false,draggable:true,closable:false,closed:true,
		 buttons:[{id:'query_win_btn',text:'生成查询窗口',iconCls:'icon-btn-search'},{id:'clean_win_btn',text:'清 空',iconCls:'icon-btn-repeat'},{id:'ts_close_win_btn',text:'关 闭',iconCls:'icon-btn-remove'}]">   
		 <div class="domBtnDiv">
			<div id="dom_table" class="tableDiv"></div>
		 </div>
	</div> 
 </body>
</html>
