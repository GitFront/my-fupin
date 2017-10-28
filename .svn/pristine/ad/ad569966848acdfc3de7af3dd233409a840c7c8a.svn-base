<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/close.css">
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/query-tree.js"></script>
	<script type="text/javascript">
		$(function(){
			modules.query.main.init();
		});		
	</script>
	
	
</head>
<body class="easyui-layout">
	  <div data-options="region:'west',split:true,title:'宽表信息树'" style="width:200px;">
	    	<div >
				<input id="theme"  style="width:190px;height: 24px; " data-options="valueField:'id',textField:'type',url:'${ctx}/a/dataMapping/mapping/tablecatalog/theme'" />  	
			</div>
	    
			<!-- 业务宽表树 -->
			<ul id="wideTableTree" class="ztree"></ul>
	  
	  </div>
	  
	   <div data-options="region:'center'" >
   				<div id="Query" class="easyui-tabs" data-options="fit:true"  style="overflow:hidden;"> 
			
				<div title="查询条件" data-options="fit:true">
					<div class="easyui-layout"  data-options="fit:true">
					
						<div data-options="region:'north'" id="tab-tools" style="height:34px;" >
					    		<a id="btn1" href="javascript:modules.query.main.tools.query()" style="height:32px;padding: 5px " class="easyui-linkbutton">查询</a>  
					    		<a id="btn2" href="javascript:void(0)"  style="height:32px;" class="easyui-linkbutton">我的规则</a>  
					    		<a id="btn3" href="javascript:void(0)"  style="height:32px;" class="easyui-linkbutton">保存规则</a>  
					    		<a id="btn4" href="javascript:void(0)" style="height:32px;padding: 5px"  class="easyui-linkbutton">()</a>  
					    </div>   
						
					    <div  data-options="region:'center',title:'条件编辑器'"   style="padding: 5px">
				    			<div id="condEditor" style="width: 100% ;height: 100%" >
				    			
				    			</div>
					    </div>   
					    <div data-options="region:'south',title:'字段列编辑器'" style="height:150px;padding: 5px" >
					    		<div id="fieldEditor"  style="width: 100% ;height: 100%"  ></div>
					    </div>   
					</div>
				</div>
				<div title="查询结果" data-options="fit:true">
						<table id="queryResult" componentType='datagrid' autoResize="true" resizeWidth=true resizeHeight="30">
							<thead>
								<tr>
									<th data-options="field:'fileName',width:180,halign:'center',align:'left'">test</th>
									<th data-options="field:'fileType',width:80,halign:'center',align:'center'">aaa</th>
						  		</tr>
							</thead>
						</table>
				</div>

			</div>
	  </div>
    
	</div>



	<span id="chevron-up" class="l-btn-icon icon-btn-chevron-up"></span>
	<div id="condMenu" style="width:200px;" data-options="noline:true,hideOnUnhover:false">
			<label>匹配公式:</label><input id="_input1"   style="width:135px;height:24px"/>   
			<div class="menu-sep"></div>   	
		    <label>匹配字符:</label><input id="_input2"   style="width:135px;height:24px"/>
		    <span  style="display:none" >
			<div class="menu-sep"></div>   	
		    <label>选择周期:</label><input id="_input3"   style="width:135px;height:24px"/>    
		    </span>    
	</div>
	<div id="fieldMenu" style="width:200px;" data-options="noline:true,hideOnUnhover:false">
			<span  style="display:none" >
		    <label>选择周期:</label><input id="_input3"   style="width:135px;height:24px"/>    
		    </span>    
	</div>
	
	<div id="win" class="easyui-window" title="匹配字符列表"
			style="width: 1000px; height:500px"
			data-options="collapsible:false,minimizable:false,modal:true,closed:true,zIndex:150000">
				
		<table class="easyui-datagrid" id="winDataGrid"   
	        data-options="url:'${ctx}/a/smart-query/query/queryColumList.do',fitColumns:true,singleSelect:true,toolbar: '#tb',
	        fit:true,border:false,pagination:true">   
	    <thead>   
	        <tr>   
	            <th data-options="field:'ck',checkbox:true,width:100"></th>   
	            <th data-options="field:'AREA_CD',width:100">编码</th>   
	            <th data-options="field:'AREA_NAM',width:100">名称</th> 
	        </tr>   
	    </thead>   
	</table>  
    </div>  
    <div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="onClick:modules.query.main.tools.tbClick" >&nbsp;&nbsp;确定&nbsp;&nbsp;</a>
	</div>
    
    
     
	
	
 </body>
</html>
