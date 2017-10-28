$.namespace("modules.smartquery");

/**
 * 列表过滤器页面控制类js
 * @type 
 */

modules.smartquery.listfilter = {
	/*页面对象管理*/
	gridId:"listFilterTable",
	/**
	 * 页面初始化方法
	 */
	init:function(){
		/*绑定按钮功能*/
		$('#search_list_filter_btn').bind('click', this.list.searchList);
		$('#add_list_filter_btn').bind('click', this.list.newList);
		$('#del_list_filter_btn').bind('click', this.list.deleteList);
		$('#upload_list_btn').bind('click', this.window.upload);
		$('#close_upload_list_btn').bind('click', this.window.close);
		/*初始化页面元素*/
		this.list.init();
		this.window.init();
	},
	initForQuery:function(){
		/*绑定按钮功能*/
		$('#upload_list_btn').bind('click', this.window.uploadForQuery);
		$('#close_upload_list_btn').bind('click', this.window.close);
		modules.smartquery.listfilter.window.init();
	},
	/*过滤器列表*/
	list:{
		init:function(){
			var h = $(window).height()-28;
			var pageSize = common.utils.getGridPage(h);
			modules.smartquery.listfilter.list.$grid=$("#"+modules.smartquery.listfilter.gridId);
			modules.smartquery.listfilter.list.$grid.datagrid({
				title:"列表过滤器的配置管理",
				iconCls:"icon-btn-list-alt",
				singleSelect:false,
				rownumbers:true,
				url:ctx + "/a/smart-query/listFilter/list/own",
				fitColumns:true,
				striped:true,
				loadMsg:'数据加载中...',
				pagination:true,
				pageSize:pageSize,
				pageList:[10,15,20,25,30,35,40,45,50],
				toolbar:"#list_ctrl_tb",
				border:true,
		        idField: 'id',
		        onLoadSuccess:this.loadCss
			});
		},
		loadCss:function(data){
			$(".edit_button").linkbutton({text:'编辑',iconCls:'icon-btn-edit ',plain:true});
			$(".download_button").linkbutton({text:'下载',iconCls:'icon-btn-download',plain:true});
			modules.smartquery.listfilter.list.$grid.datagrid('fixRowHeight');
		},
		separatorFormat:function(val,row,index){
			if(common.utils.isEmpty(val))
				val = "换行符";
			return val;
		},
		operFormat:function(val,row,index){  
			var listid = row["id"];
			var downloadClick = "javascript:modules.smartquery.listfilter.list.downloadList('"+listid+"')";
			var editClick = "javascript:modules.smartquery.listfilter.list.editList('"+index+"')";
			var html = "<a href=\"javascript:void(0);\" onclick=\""+editClick+"\" class=\"edit_button\">编辑</a>" +
					"<a href=\"javascript:void(0);\" onclick=\""+downloadClick+"\" class=\"download_button\">下载</a>";
			return html;
		},
		/*重新加载列表数据*/
		reload:function(){
			/*获取查询字符信息*/
			var search = $("#listinfo").textbox('getValue');
			
			var queryParams = {listInfo:search};
			var url = ctx + "/a/smart-query/listFilter/search/by/name";
			/*刷新数据文件列表*/
			modules.smartquery.listfilter.list.$grid.datagrid({url:url,queryParams:queryParams});
		},
		/*查询列表*/
		searchList:function(){
			modules.smartquery.listfilter.list.reload();			
		},
		/*新增列表*/
		newList:function(){
			modules.smartquery.listfilter.window.init();
			modules.smartquery.listfilter.window.open();
		},
		/*修改列表*/
		editList:function(index){
			/*var url = ctx + "/a/smart-query/listFilter/search/by/id?id="+listid;
			$('#listFilterForm').form('load', url);*/
			var row = modules.smartquery.listfilter.list.$grid.datagrid('getData').rows[index];
			$('#listFilterForm').form('load', row);
			modules.smartquery.listfilter.window.open();
		},
		/*下载列表文件*/
		downloadList:function(listid){
			$('#downloadId').val(listid);
			var url = ctx + "/a/smart-query/listFilter/download?downloadId="+listid;
			document.forms[1].action=url;
			document.forms[1].submit();
		},
		/*删除列表*/
		deleteList:function(){
			layer.confirm('是否需要删除选中的列表过滤器？', {
				icon: 3,
			    btn: ['删 除','取 消'], 
			    shade: 0.2 
			}, function(index){
				layer.close(index);
				common.utils.showLoading();
				var checkeds = modules.smartquery.listfilter.list.$grid.datagrid('getChecked');
				if(checkeds.length === 0){
					common.utils.closeLoading();
					common.utils.showErrorMsg("请选择要删除的列表过滤器！");
					return;
				}
				var columnids = new Array();
				for (var i = 0; i < checkeds.length ; i++) {
					columnids.push(checkeds[i].id);
				}
				var url = ctx + "/a/smart-query/listFilter/delete";
				var params = {ids:columnids};
				$.ajax({
		             type: "POST",
		             url: url,
		             data: params,
		             dataType: "json",
		             success: function(rs){
		            	common.utils.closeLoading();
		             	if(rs){
		             		common.utils.showSucMsg("删除列表过滤器的配置信息操作成功！",function(){
		             			modules.smartquery.listfilter.list.$grid.datagrid("uncheckAll");
								modules.smartquery.listfilter.list.reload();
							});
		             	}else{
		    				common.utils.showErrorMsg("所删除过滤器存在于已保存的查询信息中，不能进行删除操作！");
		             	}
		             }
		         });
			});			
		}
	},
	/*过滤器编辑窗口*/
	window:{
		init:function(){
			/*清除表单数据*/
			$('#listFilterForm').form('clear');
			/*$("#listName").textbox('setValue',"");
			$("#listSeparator").textbox('setValue',"");
			$("#listFile").filebox('setValue',"");
			$("#fileName").textbox('setValue',"");
			$('#id').val("");
			$('#listNum').val("");
			$('#filePath').val("");
			$('#creatorId').val("");
			$('#creator').val("");
			$('#creatorAcc').val("");
			$('#createTime').val("");
			$('#sort').val("");*/
		},
		open:function(){
			$('#listuploadWin').dialog('open');
		},
		close:function(){
			$('#listuploadWin').dialog('close');
		},
		/*上传匹配文件*/
		upload:function(){
			/*上传匹配文件*/
			var file = $("#listFile").filebox('getValue');
			var id = $('#id').val();
			
			if(common.utils.isEmpty(file) && common.utils.isEmpty(id)){
				common.utils.showErrorMsg("新建过滤器必须上传相关的匹配txt文件！");
				return;
			}
			/*获取文件类型*/
			var extStart=file.lastIndexOf(".");
        	var ext=file.substring(extStart,file.length).toUpperCase();
        	if(ext !== ".TXT"){
        		common.utils.showErrorMsg("上传匹配的数据文件必须为TXT格式文件！");
				return;
        	}
			
			common.utils.showLoading();
			$("#listFilterForm").form('submit', {    
			    url:ctx + "/a/smart-query/listFilter/save/upload",
			    onSubmit: function(params){   
			    	var isValid = $(this).form('validate');
					if (!isValid){
						common.utils.closeLoading();//如果表单是无效的则隐藏进度条
						common.utils.showErrorMsg("请将列表过滤器配置信息的必填项补充完整！");
						return isValid
					}
					return true;			          
			    },
				success: function(rs){
					modules.smartquery.listfilter.window.close();
					common.utils.closeLoading();
					var data = $.parseJSON(rs);
					if(data.flag){
						common.utils.showSucMsg("保存列表过滤器配置信息操作成功！",function(){
							modules.smartquery.listfilter.list.reload();
						});
					}else{
						common.utils.showErrorMsg("保存列表过滤器配置信息操作失败，请稍候再试！");
					}					
				}    
			});  
		},
		/*快速上传匹配文件*/
		uploadForQuery:function(){
			var file = $("#listFile").filebox('getValue');
			
			if(common.utils.isEmpty(file)){
				common.utils.showErrorMsg("新建过滤器必须上传相关的匹配txt文件！");
				return;
			}
			/*获取文件类型*/
			var extStart=file.lastIndexOf(".");
        	var ext=file.substring(extStart,file.length).toUpperCase();
        	if(ext !== ".TXT"){
        		common.utils.showErrorMsg("上传匹配的数据文件必须为TXT格式文件！");
				return;
        	}
			
			common.utils.showLoading();
			$("#listFilterForm").form('submit', {    
			    url:ctx + "/a/smart-query/listFilter/save/upload",
			    onSubmit: function(params){   
			    	var isValid = $(this).form('validate');
					if (!isValid){
						common.utils.closeLoading();//如果表单是无效的则隐藏进度条
						common.utils.showErrorMsg("请将列表过滤器配置信息的必填项补充完整！");
						return isValid
					}
					return true;			          
			    },
				success: function(rs){
					modules.smartquery.listfilter.window.close();
					common.utils.closeLoading();
					var data = $.parseJSON(rs);
					if(data.flag){
						common.utils.showSucMsg("保存列表过滤器配置信息操作成功！",function(){
							$('#lvalue').combogrid('grid').datagrid('reload');
						});
					}else{
						common.utils.showErrorMsg("保存列表过滤器配置信息操作失败，请稍候再试！");
					}					
				}    
			});  
		}		
	}
	
	
};