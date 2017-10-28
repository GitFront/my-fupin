<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<html>
<head>
	<style type="text/css">
		.domBtnDiv {display:block;padding:4px;border:1px gray dotted;background-color:#fafafa;width:99.5%;}
		.tableDiv {display:inline-block;}
		.domBtn {display:inline-block;cursor:pointer;padding:5px;margin:2px 7px;border:0px;background-color:#72CFD7;color:#FFFFFF;font-family:"Microsoft YaHei";}
	</style>
	<!-- highchart插件引入 -->
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts.js"></script>
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts-more.js"></script>
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/modules/exporting.js"></script>
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts-zh_CN.js"></script>
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/datagrid-filter.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/two-window-selecter.js"></script>
	
	<script type="text/javascript" src="${ctxStatic}/js/common/charts/common-highcharts-map.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/common/charts/common-highcharts.js"></script>
	
	<!-- 页面功能封装 -->
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/smart-query.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/smart-query-column-selecter.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/smart-query-share-selecter.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/smart-query-personal-catalog.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/smart_query/list-filter-config.js"></script>
	
	<script type="text/javascript">
		$(function(){
			var queryinfo = eval('(' + '${query}' + ')');
			modules.smartquery.query.init(queryinfo);
		});
	</script>
	
</head>
 <body>
  		<!-- 智能查询配置操作 start --> 
  		<fieldset style="margin-top: 10px">
			<legend>查询宽表信息</legend>
			<div class="domBtnDiv">
				<div id="tableZoom" class="tableDiv"></div>
			</div>
		</fieldset>
    	<fieldset style="margin-top: 10px;">
			<legend>智能查询参数配置</legend>
			<div style="padding:5px;background:#fafafa;width:99.5%;border:1px solid #ccc;">   
			   <span>
			       <a id="run_config_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-play'">运行查询配置</a>
	   			   <a id="save_config_win_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-hdd'" style="margin-left: 5px">保存查询配置</a>
			   </span>
			 </div> 
	        <div id="columnsSelecter" style="margin-top: 10px"></div>  
			<div style="margin-top: 10px">
				
				<table id="filterTable" title="查询条件配置" style="width:100%;height:200px" singleSelect="true"></table>  
			</div>
			<div style="margin-top: 10px">
				<table id="sortTable" title="排序配置" style="width:100%;height:150px" singleSelect="true"></table>  
			</div>
		</fieldset>
		<!-- 智能查询配置操作 end --> 
		<!-- 数据展示区 start -->
		<div id="data_win" class="easyui-window" title="数据展示区域"  
        	data-options="iconCls:'icon-btn-list',modal:true,collapsible:false,minimizable:false,maximizable:false,maximized:true,closed:true">
		<!-- <fieldset id="dataDiv" style="margin-top: 10px;display: none;">
			<legend>数据展示区域</legend> -->
			<div>
				<table id="dataTable"></table>
			</div>
		<!-- </fieldset> -->
		</div>
		<!-- 数据展示区 end -->
		<form id="exportFrom" method="post" action="/a/smart-query/query/smartquery/export"></form>
		<iframe style="display: none;" id="exportFrame" name="exportFrame"></iframe>
		
		
		<!-- 保存配置窗口 start -->		
		<div id="saveConfigWin" class="easyui-dialog" title="保存查询配置" 
		        data-options="iconCls:'icon-btn-cog',resizable:false,modal:true,closed:true,draggable:true,
		        buttons:[{id:'save_config_btn',text:'保 存',iconCls:'icon-btn-ok'},{id:'close_save_config_btn',text:'取 消',iconCls:'icon-btn-remove'}]">   
		        <div style="padding:15px;" >
					<form id="saveConfig" method="post">
						<div>   
					        <label for="name">配置名称:</label>
					        <input class="easyui-textbox" type="text" id="name" name="name" data-options="required:true"/>   
					    </div>
					    <div style="margin-top: 10px">
					        <a id="isOpen" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-calendar',toggle:true,plain:false">启用自动任务选项</a>  
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
							        <select id="cycType" class="easyui-combobox" name="cycType" style="width:160px;">   
									    <option value="d">每天</option>   
									    <option value="w">每周</option>   
									    <option value="m" selected="selected">每月</option>    
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
		<!-- 保存配置窗口 end -->
		
		<!-- 选择路径配置窗口 start -->
		<div id="editPathWin" class="easyui-dialog" title="编辑个人目录"    
		        data-options="iconCls:'icon-btn-tags',resizable:false,modal:true,closed:true,draggable:true,
		        buttons:[{id:'choose_personal_catalog_btn',text:'选 取',iconCls:'icon-btn-ok'},{id:'close_personal_catalog_btn',text:'取 消',iconCls:'icon-btn-remove'}]">   
		        <div >
					<ul id="pathTree" class="ztree" style="height: 300px;width: 250px;"></ul>
				</div>
		</div>  
		<!-- 选择路径配置窗口 end -->
		
		<!-- 保存数据窗口 start -->		
		<div id="saveDataWin" class="easyui-dialog" title="保存数据文件" 
		        data-options="iconCls:'icon-btn-hdd',resizable:false,modal:true,draggable:true,closable:false,
		        buttons:[{id:'save_data_btn',text:'保 存',iconCls:'icon-btn-ok'},{id:'close_save_data_btn',text:'取 消',iconCls:'icon-btn-remove'}]">   
		        <div id="saveDataDiv" style="padding:15px;" >
					<form id="saveFileData" method="post" target="exportFrame">
						<div style="margin-top: 10px">   
							 <label for="savefileName">文件名称：</label>   
							 <input class="easyui-textbox" type="text" id="savefileName" name="savefileName" style="width:160px;" data-options="required:true"/> + 当前时间
						</div>
						<div style="margin-top: 10px">
							 <label for="personalCatalogId2">文件路径：</label>
							 <span><select id="personalCatalogId2" name="personalCatalogId2" class="easyui-combotree"  data-options="url:'${ctx}/a/smart-query/query/personalcatalog/list/combotree',required:true,editable:false"  style="width:160px;"></select></span>
							 <span><a id="personal_catalog_btn2" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-plus-sign'">新增目录</a></span> 
						</div>
					    <div style="margin-top: 10px">
					        <a id="isShare" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-share',toggle:true,plain:false" >启动数据分享</a>  
					    </div>
					    <fieldset id="sharer" style="width:700px;height:265px;margin-top: 10px;">
							<legend>分享数据浏览权限</legend>
								<div id="share_usr"></div>
						</fieldset>
					</form>
				</div>
		</div>  
		<!-- 保存数据窗口 end -->
		
		<!-- 图表配置生成窗口 start -->
		<div id="reportConfigWin" class="easyui-window" title="图形报表生成器" 
		     data-options="iconCls:'icon-btn-picture',resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,maximized:false,closed:true">   
		        <div style="width: 720px;height: 502px;" class="easyui-layout">
		        	<div  data-options="region:'north',split:false" style="height:50px;padding: 10px;overflow-y:hidden; ">
					<form id="reportConfig" method="post">
						    <span>
								 <label for="reportDim">图表维度：</label>   
							     <select id="reportDim" name="reportDim" style="width:120px;"></select>
								<label for="reportAmount" style="margin-left: 8px">图表数值：</label>
							    <select id="reportAmount" name="reportAmount" style="width:120px;"></select>
								<label for="reportGroup" style="margin-left: 8px">图表分组：</label>   
							    <select id="reportGroup" name="reportGroup" style="width:120px;"></select>  
							</span>
							<span style="margin-left: 15px">
								<a id="create_report_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-play'">生成图表</a>
							</span>
						</form>
					</div>
				<div data-options="region:'center',title:'图形报表显示区域'" style="padding:5px;background:#eee;">
					<div id="switchDiv" style="padding:10px;background:#fafafa;border:1px solid #ccc;display: none;">
				      <a id="switch_btn_Bars" href="#reportDiv" class="easyui-linkbutton" data-options="toggle:true,group:'chart',plain:true" style="margin-left: 5px">柱状图</a>
				      <a id="switch_btn_StackBars" href="#reportDiv" class="easyui-linkbutton" data-options="toggle:true,group:'chart',plain:true" style="margin-left: 5px">堆积图</a>
				      <a id="switch_btn_Line" href="#reportDiv" class="easyui-linkbutton" data-options="toggle:true,group:'chart',plain:true" style="margin-left: 5px">折线图</a>
				      <a id="switch_btn_Pie" href="#reportDiv" class="easyui-linkbutton" data-options="toggle:true,group:'chart',plain:true" style="margin-left: 5px">饼状图</a>
				      <a id="switch_btn_TimeSeriesLine" href="#reportDiv" class="easyui-linkbutton" data-options="toggle:true,group:'chart',plain:true" style="margin-left: 5px">时序图</a>
				    </div>
					<div id="dataChart" style="margin-top: 8px;"></div>
				</div>
			</div>
		</div>  
		<!-- 图表配置生成窗口 end -->
		
		<!-- 编辑Filter信息接口 start -->
		<div id="filterEditWin" class="easyui-dialog" title="查询条件信息编辑窗口" 
		     data-options="iconCls:'icon-btn-hdd',resizable:false,modal:true,draggable:true,closable:false,closed:true,
		     buttons:[{id:'filter_edit_btn',text:'保 存',iconCls:'icon-btn-ok'},{id:'close_filter_edit_btn',text:'取 消',iconCls:'icon-btn-remove'}]">   
		        <div id="filterEditDiv" style="padding:15px;" >
					<form id="saveFilterInfo" method="post">
						<div style="margin-top: 10px">
							 <label for="filterType">查询类型：</label>
							 <input type ="hidden"  id="id" name ="id"/>
							 <input type ="hidden" id="sqColumnType" name ="sqColumnType"/>
							 <input type ="hidden" id="value" name ="value"/>
							 <select id="filterType" class="easyui-combobox" name="filterType" style="width:200px;">   
							    <option value="normal" selected="selected">常规过滤器</option>   
							    <option value="cyc">周期过滤器</option>      
							    <option value="list">列表过滤器</option>   
							</select>  
						</div>
						<div style="margin-top: 10px">   
							 <label for="mpColumnId">过滤字段：</label>
							 <select id="mpColumnId" name="mpColumnId" style="width:200px;"></select>
						</div>
						<div style="margin-top: 10px">
							 <label for="relationId">条件关系：</label>
							 <input id="relationId" class="easyui-combobox" name="relationId" style="width:200px;"   
    							data-options="url:'${ctx}/a/smart-query/query/dimension/filterralation/list',editable:false,valueField:'id',textField:'name',required:true" /> 
						</div>
						<div style="margin-top: 10px">
							 <label for="formulaId">匹配公式：</label>
							 <input id="formulaId" class="easyui-combobox" name="formulaId" style="width:200px;"   
    							data-options="url:'${ctx}/a/smart-query/query/dimension/filterformula/list',editable:false,valueField:'id',textField:'name',required:true" /> 
						</div>
						<div id="stringDiv" style="margin-top: 10px">
							<label for="svalue">匹配字符：</label>
							<input class="easyui-textbox" type="text" id="svalue" name="svalue" style="width:200px;"/> 
						</div>
						<div id="numberDiv" style="margin-top: 10px;display: none;">
							<label for="nvalue">匹配数字：</label>
							<input class="easyui-numberbox" id="nvalue" name="nvalue" style="width:200px;" data-options="editable:true,precision:2"/> 
						</div>
						<div id="dateDiv" style="margin-top: 10px;display: none;">
							<label for="dvalue">匹配时间：</label>
							<input class="easyui-datebox" type="text" id="dvalue" name="dvalue" style="width:200px;"/> 
						</div>
						<div id="cycDiv" style="margin-top: 10px;display: none;">
							<fieldset>
								<legend>周期数据（单位：月份）</legend>
								<div>
									<label>月份定义：</label>
									<span>
										<select id="monthCycType" class="easyui-combobox" name="monthCycType" style="width:80px;">   
										    <option value="onMonth" selected="selected">当月</option>
										    <option value="lastMonth">上月</option>
										    <option value="custom">自定义</option>  
										</select>
									</span>
									<span id="customMonthCycDiv" style="display: none;">
										前 <input class="easyui-numberspinner" id="monthCycValue" name="monthCycValue" style="width:43px;" data-options="min:2,editable:false,value:2"/> 月
									</span>
								</div>
								<div style="margin-top: 5px">
									<label>日期定义：</label>
									<span>										
										<select id="dateCycType" class="easyui-combobox" name="dateCycType" style="width:80px;">   
										    <option value="monthStart" selected="selected">月初</option>
										    <option value="monthEnd">月末</option>
										    <option value="custom">自定义</option>  
										</select>
									</span>
									<span id="customDateCycDiv" style="display: none;">
										<input class="easyui-numberspinner" id="dateCycValue" name="dateCycValue" style="width:60px;" data-options="min:1,max:31,editable:false,value:1"/> 日
									</span>
								</div>
							</fieldset>
						</div>
						<div id="listDiv" style="margin-top: 10px;display: none;">
							<label for="lvalue">匹配列表：</label>
							<span><select id="lvalue" name="lvalue" style="width:141px;"></select></span>
							 <span><a id="add_list_filter_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-plus-sign'">新增</a></span> 
						</div>
						<div style="margin-top: 10px">
							 <label for="sort">条件排序：</label>
							 <input id="sort" name="sort" class="easyui-numberspinner" style="width:200px;"   
        							required="required" data-options="min:1,editable:false"> 
						</div>
					</form>
				</div>
		</div>
		
		<!-- 编辑Filter信息接口 end -->
		
		<!-- 后台任务保存数据窗口 start -->		
		<div id="createTaskWin" class="easyui-dialog" title="请填写数据文件保存信息" 
		        data-options="iconCls:'icon-btn-tasks',resizable:false,modal:true,draggable:true,closable:false,closed:true,
		        buttons:[{id:'create_task_btn',text:'生成任务',iconCls:'icon-btn-ok'}]">   
		        <div id="saveDataDiv" style="padding:15px;" >
					<form id="createTask" method="post" target="exportFrame">
						<div style="margin-top: 10px">   
							 <label for="taskFileName">文件名称：</label>   
							 <input class="easyui-textbox" type="text" id="taskFileName" name="taskFileName" style="width:160px;" data-options="required:true"/> + 当前时间
						</div>
						<div style="margin-top: 10px">
							 <label for="personalCatalogId3">文件路径：</label>
							 <span><select id="personalCatalogId3" name="personalCatalogId3" class="easyui-combotree"  data-options="url:'${ctx}/a/smart-query/query/personalcatalog/list/combotree',required:true,editable:false"  style="width:160px;"></select></span>
							 <span><a id="personal_catalog_btn3" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-plus-sign'">新增目录</a></span> 
						</div>
					</form>
				</div>
		</div>
		<!-- 后台任务保存数据窗口 end -->
		
		<!-- 用户列表查询工具栏  start-->
		
		<div id="user_search_tb" style="padding:3px;height:auto">     
		    <div>
		        <input class="easyui-textbox" id="userinfo" name="userinfo" style="width:250px" data-options="prompt:'请输入用户名或账号名进行查询...'"/>
		    </div>    
		</div>  
		<!-- 用户列表查询工具栏  end-->
		
		<!-- 新增过滤器上传窗口 start -->
		<div id="listuploadWin" class="easyui-dialog" title="上传列表过滤器窗口" 
			 data-options="iconCls:'icon-btn-upload',resizable:false,modal:true,draggable:true,closable:false,closed:true,
			 buttons:[{id:'upload_list_btn',text:'上 传',iconCls:'icon-btn-ok'},{id:'close_upload_list_btn',text:'取 消',iconCls:'icon-btn-remove'}]">   
			 <div id="listFilterDiv" style="padding:15px;" >
				<form id="listFilterForm" method="post" target="uploadFrame" enctype="multipart/form-data">
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
		<!-- 新增过滤器上传窗口 end -->
 </body>
</html>
