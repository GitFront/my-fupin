<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<html>
<head>
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/datagrid-filter.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/two-window-selecter.js"></script>
	<script type="text/javascript">
		/* 初始化页面参数 */
		var sqId = '${query.id}';
		var sqName = '${query.name}';
		var mpTableId = '${query.mpTableId}';
		var sqTable = '${query.sqTable}';
		var isTemp = '${query.isTemp}';
		var columnsSelecter = {};
		
		$(function(){
			initForm();
		});
		
		/* 初始化表单 */
		function initForm(){
			/* 显示表单 */
			$("#mappingName").textbox('setValue','${query.name}');
			/* 配置保存表单 */
			$("#name").textbox('setValue','${query.name}');
			$("#fileName").textbox('setValue','${query.fileName}');
			$("#dataStoreDate").numberspinner('setValue','${query.dataStoreDate}'); 
			$("#personalCatalogId").combotree('setValue','${query.personalCatalogId}');
			$("#validDate").datebox('setValue','${query.validDate}');
			$("#invalidDate").datebox('setValue','${query.invalidDate}');
			$("#cycType").combobox('setValue','${query.cycType}');
			$("#cycLen").numberspinner('setValue','${query.cycLen}'); 
			if('${query.configType}' === '0'){
				/* 手动任务 */
				$("#isOpen").linkbutton('unselect');
				$("#timer").hide();	 
			}else{
				$("#isOpen").linkbutton('select');
				$("#timer").show();	 
			}
			$('#saveConfigWin').dialog('resize');			
		}
		
		/* 执行查询方法 */
		function runQuery(){
			var index = layer.load(2, {shade: 0.2}); //0代表加载的风格，支持0-2
			/* 验证用户选择的查询参数是否完整（是否含有显示 列） */
			var url = ctx + "/a/smart-query/query/smartquery/valid";
			var params = {id:sqId,isTemp:isTemp};
			var validConfig = Boolean($.ajax({ url: url,data: params, async: false }).responseText);
			if(!validConfig) {
				common.utils.showErrorMsg("不存在对应的数据显示列，请选择相关的显示列后再运行查询操作！");
				layer.close(index);
				return;
			}
			/* 查询数据列配置信息 */
			url = ctx + "/a/smart-query/query/smartquery/init";
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	             	if(rs){
	             		/* 如果存在，则先关闭列过滤器 */
	             		if($("#dataDiv").is(":visible"))
	        				$("#dataTable").datagrid("disableFilter");
	             		/* 生成动态数据列表 */
	             		createDatagrid(rs);
	        			layer.close(index);
	             	}else{
	        			layer.close(index);
	    				common.utils.showErrorMsg("初始化查询数据失败，请稍候再试！");		    				
	             	}
	             }
	         });
					
		}
		
		
	</script>
	
	<script type="text/javascript">
		/* 查询配置管理 */
		/* 打开保存查询配置窗口 */
		function openSaveConfig(){
			$('#saveConfigWin').dialog('open');
		}
		/* 打开选择查询配置窗口 */
		function openSelConfigWin(){
			$('#selectConfigWin').dialog('open');
		}
		/* 打开个人路径编辑窗口 */
		function openEditCatalogTree(){
			$('#editPathWin').dialog('open');
		}
		/* 保存查询配置操作 */
		function saveConfig(){
			var index = layer.load(2, {shade: 0.2});
			
			/* 获取表单判断数据 */
			var name = $("#name").val();
			var isOpen = $("#isOpen").linkbutton('options').selected;
			
			/* 验证查询配置名称是否唯一 */
			var url = ctx + "/a/smart-query/query/smartquery/valid/name";
			
			var validName = Boolean($.ajax({ url: url,data: {id:sqId,name:name}, type: "POST", async: false }).responseText);
			if(!validName) {
				common.utils.showErrorMsg("配置名称已存在，请使用其它名称对此查询配置进行标识！");
				layer.close(index);
				return;
			}
			
			$("#saveConfig").form('submit', {    
			    url:ctx + "/a/smart-query/query/config/saveorupdate",    
			    onSubmit: function(params){   
			    	var isValid = $(this).form('validate');
					if (isOpen && !isValid){
						layer.close(index);//如果表单是无效的则隐藏进度条
						common.utils.showErrorMsg("请将查询配置信息的必填项补充完整！");
						return isValid
					}
					/* 提交额外的参数 */
					params.id = sqId;    
					params.name = name;
					params.configType = isOpen?"1":"0";
					params.mpTableId = mpTableId;
					params.sqTable = sqTable;
					params.isTemp = isTemp;
					return true;			          
			    },
				success: function(rs){
					if(rs){
						sqName = name;
						isTemp = "0";
						columnsSelecter.reload({sqId:sqId,mpTableId:mpTableId,isTemp:isTemp}, {sqId:sqId,isTemp:isTemp});
						$("#filterTable").datagrid('reload');
						$("#sortTable").datagrid('reload');
						$("#configTable").datagrid('reload');
						parent.window.reloadQueryList();
						$('#saveConfigWin').dialog('close');
						layer.close(index);
						common.utils.showSucMsg("查询配置保存成功！");
					}else{
						layer.close(index);
						common.utils.showErrorMsg("查询配置保存失败，请稍候再试！");
					}					
				}    
			}); 
			
		}
		/* 读取已有查询配置操作 */
		function useConfig(){
			var index = layer.load(2, {shade: 0.2});
			/* 获取已选中的查询配置ID */
			var selected = $("#configTable").datagrid("getSelected");
			if(selected === null){
				common.utils.showErrorMsg("请先选取需要读取的查询配置项！");
				return;
			}
			/* 初始化页面信息（刷新） */
			var url = ctx + "/a/smart-query/query/query-config.htm?id="+selected.id;
			document.URL=url;
		}
		
		/* 定时任务切换器 */
		function timerSwitch(){
			var isOpen = !$("#isOpen").linkbutton('options').selected;
			if(isOpen){			
				$("#timer").show();	
				$('#saveConfigWin').dialog('resize');
			}else{
				$("#timer").hide();	 
				$('#saveConfigWin').dialog('resize');
			}
		}
		
		/* 配置类型语义定义 */
		function configtypeformatter(value,row,index){
			var value_ = value;
			switch (value) {
				case "1":
					value_ = "自动任务";
					break;
				default:
					value_ = "手动查询";
					break;
			}
			return value_;
		}
		
		/* 删除配置项操作 */
		function deleteConfig(id,name){
			layer.confirm('是否需要删除“'+name+'”这个配置项？', {
				icon: 3,
			    btn: ['删 除','取 消'], 
			    shade: 0.2 
			}, function(index){
            	layer.close(index);
				$.ajax({
		             type: "POST",
		             url: ctx + "/a/smart-query/query/config/remove",
		             data: {id:sqId},
		             dataType: "json",
		             success: function(suc){
		             	if(!suc) {
		             		common.utils.showErrorMsg("删除智能查询信息失败，请稍候再试！");
		             	}else{
		             		("#configTable").datagrid("reload");
		             	}
		             }
		         });
			});
		 }
		
		/* 加载列表操作按钮 */
		function formatOper(val,row,index){ 
			var name = row["name"];
			var id = row["id"];
			return "<a href=\"javascript:void(0);\" onclick=\"deleteConfig('"+id+"','"+name+"')\" class=\"view_button\">删 除</a>";
		} 
		
		/* 加载列表按钮样式 */
		function loadCss(data){
			 $(".view_button").linkbutton({text:'删 除',iconCls:'icon-btn-trash',plain:true});
			 $("#configTable").datagrid('fixRowHeight');
		}
		
	</script>
	
	<script type="text/javascript">
		/* 可编辑个人目录树加载 */
		/* 获取ztree的节点全路径节点名称信息 */
		function getNodePath(treeNode){
			if(treeNode === null) return "";
			var nodename = treeNode.name;
			var pNode = treeNode.getParentNode();
			if(pNode!=null){
				nodename = getNodePath(pNode) +"/"+ nodename;
			}
			return nodename;
		}
		
		/* 定义编辑树对象 */
		var personalTree;
		
		/* 保存默认个人路径 */
		function addDefaultPath(nodeId,nodeName,nodeParentId){
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/query/personalcatalog/save",
	             data: {id:nodeId,name:nodeName,pId:nodeParentId,fullPath:nodeName},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("个人默认目录失败，请稍候再试！",function(){
	             			personalTree.asynReload();
	             		});
	             	}
	             }
	         });
		}
		
		/* 保存节点信息 */
		function addChildPath(treeId, treeNode, newNodeid, newNodeName, newParentId){
			var newFullPath = getNodePath(treeNode) +"/"+ newNodeName;
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/query/personalcatalog/save",
	             data: {id:newNodeid,name:newNodeName,pId:newParentId,fullPath:newFullPath},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("个人目录保存失败，请稍候再试！",function(){
	             			personalTree.asynReload();
	             		});
	             	}
	             }
	         });
		}
		
		/* 重命名节点信息 */
		function renamePath(treeId, treeNode){
			var id = treeNode.id;
			var name = treeNode.name;
			var fullPath = getNodePath(treeNode);
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/query/personalcatalog/rename",
	             data: {id:id,name:name,fullPath:fullPath},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("个人目录重命名失败，请稍候再试！",function(){
		             		personalTree.asynReload();
	             		});
	             	}
	             }
	         });
		}
		/* 删除节点前判断 */
		function beforeRemovePath(treeId, treeNode){
			var id = treeNode.id;
			var url =  ctx + "/a/smart-query/query/personalcatalog/isremove?id="+id;
			
			var rs = Boolean($.ajax({ url: url, async: false }).responseText); 
			if(!rs) common.utils.showErrorMsg("该目录已绑定自动任务或存在数据文件 ，请解除绑定后再进行删除操作！");
			return rs;
		}
		
		
		/* 删除节点信息 */
		function removePath(treeId, treeNode){
			var id = treeNode.id;
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/query/personalcatalog/remove?id="+id,
	             dataType: "json",
	             success: function(suc){
	            	if(suc){	            		
	            		common.utils.showSucMsg("个人目录删除成功！");	            		
	            	}else{
	            		common.utils.showErrorMsg("个人目录删除失败，请稍候再试！",function(){
		             		personalTree.asynReload();
	            		});
	            	}	             	
	             }
	         });
		}
		
		$(document).ready(function(){
	
				/* 加载目录数据 */
				var opt = {
						async:true,/* 是否异步获取节点数据*/
						url:ctx + "/a/smart-query/query/personalcatalog/list/ztree", /*服务器访问地址*/
						mapper:{id:'id',name:'name',pId:'parentId',nfullPath:"fullPath"}, /*字段对应关系*/
						newRootName:"个人主目录",
						newEditName:"新文件夹", /* 新增节点默认名称*/
						isExpand:true, /*是否默认展开全部树节点*/
						showFolder:true
					};
				var callback = {
						noNewRoot:"addDefaultPath",
						onNew:"addChildPath",
						onRename:"renamePath",
						onRemove:"removePath",
						beforeRemove:"beforeRemovePath"
				};
				
				personalTree = new zEditTree("pathTree",[],{},callback,opt);
			});
		
			/* 选取对应的个人路径 */
			function usePath(){
				var treeObj = $.fn.zTree.getZTreeObj("pathTree");
				var select = treeObj.getSelectedNodes();
				
				if(!select || select.length === 0) {
					common.utils.showErrorMsg("必须先选择一个文件保存的目录！");
					return;
				}
				/* 获取选中树节点 */
				var selected = select[0].id;
				/* 更新下拉选择树 */				
				$('#personalCatalogId').combotree('reload'); 	
				$('#personalCatalogId').combotree('setValue',selected);
				
				$('#editPathWin').dialog('close');
		
			}
	
	</script>
	
	<script type="text/javascript">
		/* 显示列配置 */
		var noselectcol = {
				title:"未选择显示列",
				url:ctx + "/a/dataMapping/mapping/columnmapping/list/withoutselect",	
				queryParams:{sqId:sqId,mpTableId:mpTableId,isTemp:isTemp},	    	
		    	idField:'id',
		    	columns : [{
					field : 'id',
					hidden:true
				},{
					field : 'columnType',
					hidden:true
				}, {
					width : '140',
					title : '数据列名',
					field : 'columnName'
				}, {
					width : '130',
					title : '映射名',
					field : 'mappingName'
				}, {
					width : '80',
					title : '类型',
					field : 'columnTypeName',
					align : "center"
				} ]
		};
		
		var hasselectcol = {
				title:"已选择显示列",
				url:ctx + "/a/smart-query/query/sqselect/list",	
				queryParams:{sqId:sqId,isTemp:isTemp},	    	
		    	idField:'id',
		    	columns : [{
					field : 'id',
					hidden:true
				},{
					field : 'columnType',
					hidden:true
				}, {
					width : '140',
					title : '数据列名',
					field : 'sqColumn'
				}, {
					width : '130',
					title : '映射名',
					field : 'sqColumnName'
				}, {
					width : '80',
					title : '类型',
					field : 'columnTypeName',
					align : "center"
				} ]
		};
		
		/* 显示列选择器保存方法 */
		function selectMoveIn(nogridid,hasgridid){
			var checkeds = $("#"+nogridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.showErrorMsg("请选择要移入的显示列！");
				return;
			}
			var columnids = new Array();
			for (var i = 0; i < checkeds.length ; i++) {
				columnids.push(checkeds[i].id);
			}
			var url = ctx + "/a/smart-query/query/queryselect/savelist";
			var params = {
					sqId:sqId,
					isTemp:isTemp,
					columnids:columnids
			};
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	             	if(rs){
	             		columnsSelecter.reload({sqId:sqId,mpTableId:mpTableId,isTemp:isTemp}, {sqId:sqId,isTemp:isTemp});	             		
	             	}else{
	    				common.utils.showErrorMsg("查询显示列选择失败，请稍候再试！");
	             	}
	             }
	         });
		}
		
		function selectMoveOut(nogridid,hasgridid){
			var checkeds = $("#"+hasgridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.showErrorMsg("请选择要移出的数据列！");
				return;
			}
			var columnids = new Array();
			for (var i = checkeds.length-1; i >= 0 ; i--) {
				columnids.push(checkeds[i].id);
			}
			var url = ctx + "/a/smart-query/query/queryselect/removelist";
			var params = {
					sqId:sqId,
					isTemp:isTemp,
					columnids:columnids
			};
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	             	if(rs){
	             		/* 刷新选择器列表 */
	             		columnsSelecter.reload({sqId:sqId,mpTableId:mpTableId,isTemp:isTemp}, {sqId:sqId,isTemp:isTemp});
	             	}else{
	    				common.utils.showErrorMsg("查询显示列移除失败，请稍候再试！");
	             	}
	             }
	         });
		}
		
		$(function(){
			columnsSelecter = new TwoWindowsSelecter(
					"columnsSelecter",
					{title:"查询显示列配置",width:860,height:250},
					noselectcol,
					hasselectcol,
					{moveIn:"selectMoveIn",moveOut:"selectMoveOut"}
			);
		});
	</script>
	
	<SCRIPT type="text/javascript">
	/* 查询条件列信息配置 */
	/* 列名注解 */
	function columnFormatter(value, rowData, rowIndex){
		var url =  ctx + "/a/dataMapping/mapping/columnmapping/query/name?id="+value;
		return $.ajax({ url: url, async: false }).responseText; 
	}
	
	function relationFormatter(value, rowData, rowIndex){
		var url =  ctx + "/a/smart-query/query/dimension/filterralation/name?id="+value;
		return $.ajax({ url: url, async: false }).responseText; 
	}
	
	function formulaFormatter(value, rowData, rowIndex){
		var url =  ctx + "/a/smart-query/query/dimension/filterformula/name?id="+value;
		return $.ajax({ url: url, async: false }).responseText; 
	}
	
	/* 保存或更新条件过滤列信息 */
	function saveOrUpdateFilter(rowData){
		/* 判断数据有效性 */
    	if(common.utils.isEmpty(rowData.relationId)) {
			common.utils.showErrorMsg("查询条件的条件关系信息不能为空！");
			return false;
    	}
    	if(common.utils.isEmpty(rowData.mpColumnId)) {
			common.utils.showErrorMsg("查询条件对应的数据列信息不能为空！");
			return false;
    	}
    	if(common.utils.isEmpty(rowData.formulaId)) {
			common.utils.showErrorMsg("查询条件对应的匹配公式信息不能为空！");
			return false;
    	}
    	if(common.utils.isEmpty(rowData.value)) {
			common.utils.showErrorMsg("查询条件对应的匹配值信息不能为空！");
			return false;
    	}
    	var url = ctx + "/a/smart-query/query/sqfilter/saveorupdate";
		var params = {sqId:sqId,isTemp:isTemp,filter:JSON.stringify(rowData)};
		/* 异步请求操作 */
		$.ajax({
             type: "POST",
             url: url,
             data: params,
             dataType: "json",
             traditional:true,
             success: function(rs){
             	if(!rs){
    				common.utils.showErrorMsg("查询条件保存失败，请稍候再试！");
             	}
             }
         });	    	
	}
	 
	$(function(){
		 var filterEditRow = undefined;
		 $("#filterTable").datagrid({
			 	striped : true,
				rownumbers : true,
				loadMsg:'正在加载数据...',
		        collapsible: true,
		        url:ctx + "/a/smart-query/query/sqfilter/list",
				queryParams:{sqId:sqId,isTemp:isTemp},		        
		        idField: 'id',
		        columns: [[
				 {field:'checkbox',checkbox:true},
		         { field: 'id',hidden:true },
		         
		         { field: 'relationId', halign:'center',align:'center',title: '条件关系', width: 100, formatter: relationFormatter, 
		        	 editor: { type:'combobox', align:'center', 
		        		 options: {url:ctx+"/a/smart-query/query/dimension/filterralation/list",editable:false, valueField: "id", textField: "name",required:true }}},
		        	 
		         { field: 'mpColumnId', title: '数据字段',halign:'center', width: 180, formatter: columnFormatter, 
		        	 editor: { type:'combobox', align:'center',
		        		 options: {url:ctx+"/a/dataMapping/mapping/columnmapping/list?mpTableId="+mpTableId,editable:false, valueField: "id", textField: "mappingName",required:true }}},
		        	 
		         { field: 'formulaId', title: '匹配公式', halign:'center',align:'center',width: 100, formatter: formulaFormatter,
		            	editor: { type:'combobox', align:'center', 
		            		options: {url:ctx+"/a/smart-query/query/dimension/filterformula/list",editable:false, valueField: "id", textField: "name",required:true }}},
		            	
		         { field: 'value', title: '匹配值',halign:'center', width: 240, editor:{type:'validatebox', align:'left',options:{required:true}}}
		        ]],
		        toolbar: [{
		            text: '添加查询条件', iconCls: 'icon-btn-plus-sign', handler: function () {
		                if (filterEditRow != undefined) {
		                    $("#filterTable").datagrid('endEdit', filterEditRow);
		                }
		                if (filterEditRow == undefined) {
		                	/* 动态生成ID */
		                	var id = common.utils.guid();
		                	var newRow = {id:id,relationId:'1',mpColumnId:'',formulaId:'1',value:''};
		                    $("#filterTable").datagrid('insertRow', {
		                        row: newRow
		                    });
		                    var index = $("#filterTable").datagrid('getRowIndex', newRow);
		                    $("#filterTable").datagrid('beginEdit', index);
		                    filterEditRow = index;
		                }
		            }
		        }, '-', {
		            text: '删除查询条件', iconCls: 'icon-btn-remove-sign', handler: function () {
		                var row = $("#filterTable").datagrid('getSelections');
		                if(row.length === 0){
		                	common.utils.showErrorMsg("请选择需要删除的查询条件！");
		        			return;
		                }
		                var url = ctx + "/a/smart-query/query/sqfilter/remove";
		        		var params = {id:row[0].id,isTemp:isTemp};
		        		/* 异步请求操作 */
		        		$.ajax({
		                     type: "POST",
		                     url: url,
		                     data: params,
		                     dataType: "json",
		                     traditional:true,
		                     success: function(rs){
		                     	if(!rs){
		            				common.utils.showErrorMsg("查询条件删除失败，请稍候再试！");
		                     	}
	            				$("#filterTable").datagrid('reload');
		                     }
		                 });		        		
		            }
		        }, '-', {
		            text: '上 移', iconCls: 'icon-btn-circle-arrow-up', handler: function () {
		            	layer.msg('上移');
		            }
		        }, '-', {
		            text: '下 移', iconCls: 'icon-btn-circle-arrow-down', handler: function () {
		            	layer.msg('下移');
		            }
		        }, '-', {
		            text: '生 效', iconCls: 'icon-btn-ok-sign', handler: function () {
		            	if (filterEditRow != undefined) {
			                $("#filterTable").datagrid('endEdit', filterEditRow);
			            }
		            }
		        }],
		        onAfterEdit: function (rowIndex, rowData, changes) {
		        	/* 更新查询条件 */
                    saveOrUpdateFilter(rowData);
		            filterEditRow = undefined;
		        },
		        onDblClickRow: function (rowIndex, rowData) {
		            if (filterEditRow != undefined) {
		                $("#filterTable").datagrid('endEdit', filterEditRow);
		            }
		 
		            if (filterEditRow == undefined) {
		                $("#filterTable").datagrid('beginEdit', rowIndex);
		                filterEditRow = rowIndex;
		            }
		        },
		        onClickRow: function (rowIndex, rowData) {
		            if (filterEditRow != undefined) {
		                $("#filterTable").datagrid('endEdit', filterEditRow);
		            }
		 
		        }
		 
		  });
	});
	</script>
	
	<SCRIPT type="text/javascript">
	/* 数据排序信息配置 */
	
	function sortTypeFormatter(value, rowData, rowIndex){
		var url =  ctx + "/a/smart-query/query/dimension/sorttype/name?id="+value;
		return $.ajax({ url: url, async: false }).responseText; 
	}
	
	/* 保存或更新条件过滤列信息 */
	function saveOrUpdateSorter(rowData){
		/* 判断数据有效性 */
    	if(common.utils.isEmpty(rowData.mpColumnId)) {
			common.utils.showErrorMsg("排序方式对应的数据列信息不能为空！");
			return false;
    	}
    	if(common.utils.isEmpty(rowData.sortTypeId)) {
			common.utils.showErrorMsg("排序方式对应的排序类型信息不能为空！");
			return false;
    	}
    	var url = ctx + "/a/smart-query/query/sqsort/saveorupdate";
		var params = {sqId:sqId,isTemp:isTemp,sroter:JSON.stringify(rowData)};
		/* 异步请求操作 */
		$.ajax({
             type: "POST",
             url: url,
             data: params,
             dataType: "json",
             traditional:true,
             success: function(rs){
             	if(!rs){
    				common.utils.showErrorMsg("排序方式保存失败，请稍候再试！");
             	}
             }
         });	    	
	}
	 
	$(function(){
		 var sortEditRow = undefined;
		 $("#sortTable").datagrid({
			 	striped : true,
				rownumbers : true,
				loadMsg:'正在加载数据...',
		        collapsible: true,
		        collapsed:true,
		        url:ctx + "/a/smart-query/query/sqsort/list",
				queryParams:{sqId:sqId,isTemp:isTemp},		        
		        idField: 'id',
		        columns: [[
		  		{field:'checkbox',checkbox:true},
		        { field: 'id',hidden:true },
		         
		        { field: 'mpColumnId', title: '数据字段',halign:'center', width: 280, formatter: columnFormatter, 
		        	 editor: { type:'combobox', align:'center',
		        		 options: {url:ctx+"/a/dataMapping/mapping/columnmapping/list?mpTableId="+mpTableId,editable:false, valueField: "id", textField: "mappingName",required:true }}},
		        	 
		         { field: 'sortTypeId', title: '排序类型', halign:'center',align:'center',width: 150, formatter: sortTypeFormatter,
		            	editor: { type:'combobox', align:'center', 
		            		options: {url:ctx+"/a/smart-query/query/dimension/sorttype/list",editable:false, valueField: "id", textField: "name",required:true }}}
		        ]],
		        toolbar: [{
		            text: '添加排序方式', iconCls: 'icon-btn-plus-sign', handler: function () {
		                if (sortEditRow != undefined) {
		                    $("#sortTable").datagrid('endEdit', sortEditRow);
		                }
		                if (sortEditRow == undefined) {
		                	/* 动态生成ID */
		                	var id = common.utils.guid();
		                	var newRow = {id:id,relationId:'1',mpColumnId:'',sortTypeId:'1'};
		                    $("#sortTable").datagrid('insertRow', {
		                        row: newRow
		                    });
		                    var index = $("#sortTable").datagrid('getRowIndex', newRow);
		                    $("#sortTable").datagrid('beginEdit', index);
		                    sortEditRow = index;
		                }
		            }
		        }, '-', {
		            text: '删除排序方式', iconCls: 'icon-btn-remove-sign', handler: function () {
		                var row = $("#sortTable").datagrid('getSelections');
		                if(row.length === 0){
		                	common.utils.showErrorMsg("请选择需要删除的排序方式！");
		        			return;
		                }
		                var url = ctx + "/a/smart-query/query/sqsort/remove";
		        		var params = {id:row[0].id,isTemp:isTemp};
		        		/* 异步请求操作 */
		        		$.ajax({
		                     type: "POST",
		                     url: url,
		                     data: params,
		                     dataType: "json",
		                     traditional:true,
		                     success: function(rs){
		                     	if(!rs){
		            				common.utils.showErrorMsg("排序方式删除失败，请稍候再试！");
		                     	}
		                     	$("#sortTable").datagrid('reload');
		                     }
		                 });
		            }
		        }, '-', {
		            text: '上 移', iconCls: 'icon-btn-circle-arrow-up', handler: function () {
		            	layer.msg('上移');
		            }
		        }, '-', {
		            text: '下 移', iconCls: 'icon-btn-circle-arrow-down', handler: function () {
		            	layer.msg('下移');
		            }
		        }, '-', {
		            text: '生 效', iconCls: 'icon-btn-ok-sign', handler: function () {
		            	if (sortEditRow != undefined) {
			                $("#sortTable").datagrid('endEdit', sortEditRow);
			            }
		            }
		        }],
		        onAfterEdit: function (rowIndex, rowData, changes) {
		        	/* 更新排序方式 */
                    saveOrUpdateSorter(rowData);
		            sortEditRow = undefined;
		        },
		        onDblClickRow: function (rowIndex, rowData) {
		            if (sortEditRow != undefined) {
		                $("#sortTable").datagrid('endEdit', sortEditRow);
		            }
		 
		            if (sortEditRow == undefined) {
		                $("#sortTable").datagrid('beginEdit', rowIndex);
		                sortEditRow = rowIndex;
		            }
		        },
		        onClickRow: function (rowIndex, rowData) {
		            if (sortEditRow != undefined) {
		                $("#sortTable").datagrid('endEdit', sortEditRow);
		            }
		 
		        }
		 
		  });
	});
	</script>
	
	
	<script type="text/javascript">
		var toolbar = [{
			text:'导出数据',
			iconCls:'icon-btn-download',
			handler:function(){
				document.forms[0].action=ctx + "/a/smart-query/query/smartquery/export?sqId="+sqId+"&isTemp="+isTemp;
			 	document.forms[0].target="exportFrame";
				document.forms[0].submit();
			}
		},"-",{
			text:'保存数据',
			iconCls:'icon-btn-hdd',
			disabled:true,
			handler:function(){
				
			}
		},"-",{
			text:'切换图表',
			iconCls:'icon-btn-picture',
			disabled:true,
			handler:function(){
				
			}
		}];
		/* 生成动态数据列表方法 */
		function createDatagrid(columns){
			$("#dataDiv").show();
			$("#dataTable").datagrid({
				title:sqName+"数据展示",
				singleSelect:true,
				height:615,
				rownumbers:true,
				url:ctx + "/a/smart-query/query/smartquery/datalist",
				queryParams:{id:sqId,isTemp:isTemp},
				fitColumns:true,
				striped:true,
				loadMsg:'数据加载中...',
				pagination:true,
				pageSize:'20',
				pageList:[10,20,30,40,50],
				border:true,
				toolbar:toolbar,
		        idField: 'id',
		        columns:[columns]  
			});
			$("#dataTable").datagrid("enableFilter");
			$("html,body").animate({scrollTop: $("#dataDiv").offset().top}, 1000);
			
		}
	</script>
	
	
	
	
</head>
 <body>
  		<!-- 智能查询配置操作 start --> 
    	<fieldset style="margin-top: 10px">
			<legend>智能查询参数配置</legend>
			<div style="padding:10px;background:#fafafa;width:838px;border:1px solid #ccc;">   
			   <label for="mappingName">业务宽表：</label>   
			   <span><input id="mappingName" name="mappingName" class="easyui-textbox" readonly="readonly" type="text" style="width:180px;" /></span>
			   <span style="margin-left: 20px;">
			       <a id="run_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-play'" onclick="javascript:runQuery();">运行查询配置</a>
			       <a id="open_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-folder-open'" style="margin-left: 5px" onclick="javascript:openSelConfigWin();">读取查询配置</a>
	   			   <a id="save_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-hdd'" style="margin-left: 5px" onclick="javascript:openSaveConfig();">保存查询配置</a>
			   </span>
			 </div> 
	        <div id="columnsSelecter" style="margin-top: 10px"></div>  
			<div style="margin-top: 10px">
				<table id="filterTable" title="查询条件配置" style="width:860px;height:200px" singleSelect="true"></table>  
			</div>
			<div style="margin-top: 10px">
				<table id="sortTable" title="排序配置" style="width:860px;height:150px" singleSelect="true"></table>  
			</div>
		</fieldset>
		<!-- 智能查询配置操作 end --> 
		<!-- 数据展示区 start -->
		<fieldset id="dataDiv" style="margin-top: 10px;display: none;">
			<legend>数据展示区域</legend>
			<div>
				<table id="dataTable"></table>
			</div>
		</fieldset>
		<!-- 数据展示区 end -->
		<form id="exportFrom" method="post" action="/a/smart-query/query/smartquery/export"></form>
		<iframe style="display: none;" id="exportFrame" name="exportFrame"></iframe>
		
		
		<!-- 保存配置窗口 start -->		
		<div id="saveConfigWin" class="easyui-dialog" title="保存查询配置" 
		        data-options="iconCls:'icon-btn-cog',resizable:false,modal:true,closed:true,draggable:true,
		        buttons:[{text:'保 存',iconCls:'icon-btn-hdd',handler:saveConfig},{text:'取 消',iconCls:'icon-btn-remove',handler:function(){$('#saveConfigWin').dialog('close');}}]">   
		        <div style="padding:15px;" >
					<form id="saveConfig" method="post">
						<div>   
					        <label for="name">配置名称:</label>
					        <input class="easyui-textbox" type="text" id="name" name="name" data-options="required:true"/>   
					    </div>
					    <div style="margin-top: 10px">
					        <a id="isOpen" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-calendar',toggle:true,plain:false" onclick="javascript:timerSwitch();">启用自动任务选项</a>  
					    </div>
					    <fieldset id="timer" style="width:340px;height:290px;margin-top: 10px;">
							<legend>自动任务配置</legend>
								<div style="margin-top: 10px">   
							        <label for="fileName">数据文件名称：</label>   
							        <input class="easyui-textbox" type="text" id="fileName" name="fileName" style="width:160px;" 
							        	data-options="required:true" /> + 导出日期
							 	</div>
							 	<div style="margin-top: 10px">   
							        <label for=dataStoreDate>文件期限(天)：</label>   
							        <input id="dataStoreDate" name="dataStoreDate" class="easyui-numberspinner" style="width:160px;"   
		        						required="required" data-options="min:1,max:100,editable:true"> 
							 	</div>
							  	<div style="margin-top: 10px">   
							        <label for="personalCatalogId">文件保存路径：</label>
							        <span><select id="personalCatalogId" name="personalCatalogId" class="easyui-combotree" data-options="url:'${ctx}/a/smart-query/query/personalcatalog/list/combotree',required:true,editable:false" style="width:160px;"></select></span>
							        <span><a id="catalog_btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-btn-plus-sign'" onclick="javascript:openEditCatalogTree();">新增目录</a></span> 
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
		<!-- 保存配置窗口 end -->
		
		<!-- 选择配置窗口 start -->
		<div id="selectConfigWin" class="easyui-dialog" title="选择已有查询配置"    
		        data-options="iconCls:'icon-btn-folder-open',resizable:false,modal:true,closed:true,draggable:true,
		        buttons:[{text:'选 取',iconCls:'icon-btn-ok',handler:useConfig},{text:'取 消',iconCls:'icon-btn-remove',handler:function(){$('#selectConfigWin').dialog('close');}}]">   
		        <div >
					<table id="configTable" class="easyui-datagrid" style="width:600px;height:455px" 
							data-options="url:'${ctx}/a/smart-query/query/config/list',fitColumns:true,singleSelect:true,rownumbers:true,striped:true,loadMsg:'数据加载中...',pagination:true,pageSize:'15',pageList:[10,15,30,40,50],border:true,idField: 'id',onLoadSuccess:loadCss">  
						<thead>
							<tr>
								<th data-options="field:'name',width:180,halign:'center',align:'left'">查询配置名称</th>
								<th data-options="field:'configType',width:80,halign:'center',align:'center',formatter:configtypeformatter">类型</th>
								<th data-options="field:'createTime',width:80,halign:'center',align:'center'">创建时间</th>
								<th data-options="field:'invalidDate',width:80,halign:'center',align:'center'">失效时间</th>
								<th data-options="field:'oper',width:60,halign:'center',align:'center',formatter:formatOper">操作</th>
								<th data-options="field:'id',hidden:true"></th>
								<th data-options="field:'validDate',hidden:true"></th>
								<th data-options="field:'mpTableId',hidden:true"></th>
								<th data-options="field:'sqTable',hidden:true"></th>
								<th data-options="field:'fileName',hidden:true"></th>
								<th data-options="field:'personalCatalogId',hidden:true"></th>
								<th data-options="field:'dataStoreDate',hidden:true"></th>
								<th data-options="field:'cycType',hidden:true"></th>
								<th data-options="field:'cycLen',hidden:true"></th>
			  				</tr>
						</thead>
					</table>
				</div>
		</div>  
		<!-- 选择配置窗口 end -->
		
		<!-- 选择路径配置窗口 start -->
		<div id="editPathWin" class="easyui-dialog" title="编辑个人目录"    
		        data-options="iconCls:'icon-btn-tags',resizable:false,modal:true,closed:true,draggable:true,
		        buttons:[{text:'选 取',iconCls:'icon-btn-ok',handler:usePath},{text:'取 消',iconCls:'icon-btn-remove',handler:function(){$('#selectConfigWin').dialog('close');}}]">   
		        <div >
					<ul id="pathTree" class="ztree" style="height: 300px;width: 250px;"></ul>
				</div>
		</div>  
		<!-- 选择路径配置窗口 end -->
		
 </body>
</html>
