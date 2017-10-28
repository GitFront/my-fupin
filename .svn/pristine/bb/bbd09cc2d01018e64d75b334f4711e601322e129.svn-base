$.namespace("modules.smartquery");

/**
 * 数据超市页面控制类js
 * @type 
 */

modules.smartquery.market = {
	/*页面对象管理*/
	userAcc:"",
	gridId:"fileTable",
	catalogTreeObj:{},
	timeTreeObj:{},
	configListObj:{},
	filegrid:{},
	/**
	 * 页面初始化方法
	 */
	init:function(loginName){ 			
		this.userAcc = loginName;
		/*初始化目录树*/
		this.catalogTree.init();
		/*初始化时间树*/
		this.timeTree.init();
		/*初始化任务列表*/
		this.configList.init();
		/*用户列表窗口初始化*/
		this.shareUser.initTree();
		/*初始化数据文件列表*/
		this.files.init();
	},
	/*目录管理树*/
	catalogTree:{
		init:function(){
			/*初始化参数设置*/
			var setting = {
				treeId:'fileCatalogTree',
				view: {
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: this.catalogClick
				}
			};
			/* 查询文件目录树信息 */
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/market/catalogtree/list",
	             dataType: "json",
	             success: function(data){
	             	if(data) {
	             		/* 加载目录树信息 */
	        			modules.smartquery.market.catalogTree = $.fn.zTree.init($("#fileCatalogTree"), setting, data);
	             	}
	             }
	         });			
		},
		catalogClick:function(e,treeId, treeNode){
			var url =ctx + "/a/smart-query/market/file/list/catalog";
			var queryParams = {catalogId:treeNode.id};
			/*刷新数据文件列表*/
			$("#fileTable").datagrid({url:url,queryParams:queryParams});	
		}
	},
	/*时间管理树*/
	timeTree:{
		init:function(){
			/*初始化参数设置*/
			var setting = {
				treeId:'fileTimeTree',
				view: {
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: this.timeClick
				}
			};
			/* 查询文件目录树信息 */
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/market/timetree/list",
	             dataType: "json",
	             success: function(data){
	             	if(data) {
	             		/* 加载目录树信息 */
	        			modules.smartquery.market.timeTreeObj = $.fn.zTree.init($("#fileTimeTree"), setting, data);
	             	}
	             }
	         });			
		},
		timeClick:function(e,treeId, treeNode){
			var url =ctx + "/a/smart-query/market/file/list/time";
			var queryParams = {time:treeNode.id,timeType:treeNode.timeType};
			/*刷新数据文件列表*/
			$("#fileTable").datagrid({url:url,queryParams:queryParams});			
		}
	},
	/*配置管理树*/
	configList:{
		init:function(){
			modules.smartquery.market.configListObj = $("#configList").datalist({
				url : ctx + "/a/smart-query/query/config/listauto",
				valueField : "id",
				textField : "name",
				checkbox : true,
				lines : false,
				onSelect : this.configClick
			});
		},
		configClick:function(rowIndex, rowData){
			var url =ctx + "/a/smart-query/market/file/list/task";
			var queryParams = {taskId:rowData.id};
			/*刷新数据文件列表*/
			$("#fileTable").datagrid({url:url,queryParams:queryParams});			
		}
	},
	/*用户分享窗口*/
	shareUser:{
		/*选择分享的数据文件ID*/
		shareFileId:"",
		userTree:{},
 		initTree:function(){
			/*初始化参数设置*/
			var setting = {
				treeId:'userztree',
				view: {
					selectedMulti: false
				},
				check : {
					enable : true
				},
				data: {
					simpleData: {
						enable: true
					}
				}			
			}
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/catalog/user/list",
	             dataType: "json",
	             success: function(data){
	             	if(data) {
	             		/* 加载目录树信息 */
	        			modules.smartquery.market.shareUser.userTree = $.fn.zTree.init($("#userztree"), setting, data);
	             	}
	             }
	         });	
		},
 		open:function(fileid,filename){
 			modules.smartquery.market.shareUser.shareFileId = fileid;
 			var treeObj = $.fn.zTree.getZTreeObj("userztree");
 			if(treeObj != null){
 				$("#userTreeWin").panel("move",{top:$(document).scrollTop()});			
 			common.utils.showLoading();
 			var params={}
 			params.fileId=fileid;
        	treeObj.checkAllNodes(false);
        	$('#userTreeWin').dialog({title: "分享文件名称："+filename});
			$.ajax({
	             type: "POST",
	             data:params,
	             url: ctx + "/a/smart-query/market/userSelect/list",
	             dataType: "json",
	             success: function(data){
	             	 common.utils.closeLoading();
	            	 $.each(data,function(i,v){
	            		 var id=v;
	            		 var node=treeObj.getNodeByParam("id", id);
	            		 treeObj.checkNode(node, true, true);
	            	 });	            	 
	             },error:function(){
	            	 common.utils.closeLoading();
	            	 common.utils.showErrorMsg("文件分享失败，请稍候再试！");
	             }
	         });
			 $("#userTreeWin").dialog('open');
			 $("#userTreeWin").dialog('resize');
 			}
 		},
 		close:function(){
 			$("#userTreeWin").dialog('close');
 		},
 		share:function(){
 			common.utils.showLoading();
 			var params={};
 			var userIds=[];
 			var checkedNodes = $.fn.zTree.getZTreeObj("userztree").getCheckedNodes()
 			$.each(checkedNodes,function(i,node){
 				if(!node.isParent){
 					userIds.push(node.id);
 				}
 			})
 			$.extend(params,{userIds:userIds});
 			params.fileId=modules.smartquery.market.shareUser.shareFileId;
 			params=jQuery.param(params,true);
 		 	$.ajax({
	 			type : 'post',
	 			data : params,
	 			dataType:"json",
	 			url : ctx + "/a/smart-query/market/fileshare/save",
	 			success : function(result){
	 				common.utils.closeLoading();
	 				if(result){
	 					common.utils.showSucMsg("数据文件分享成功！",function(){
	 						modules.smartquery.market.files.$grid.datagrid("reload");
	 						modules.smartquery.market.shareUser.close();
	 					});
	 				}else{
	 					common.utils.showErrorMsg("数据文件分享失败，请稍候再试！");
	 				}
	 			}
 		 	});
 		}
 	},
	/*文件列表*/
	files:{
		init:function(){
			var h = $(window).height()-28;
			var pageSize = common.utils.getGridPage(h);
			modules.smartquery.market.files.$grid=$("#"+modules.smartquery.market.gridId);
			modules.smartquery.market.files.$grid.datagrid({
				singleSelect:true,
				rownumbers:true,
				url:ctx + "/a/smart-query/market/file/list",
				fitColumns:true,
				striped:true,
				loadMsg:'数据加载中...',
				pagination:true,
				pageSize:pageSize,
				pageList:[10,15,20,25,30,35,40,45,50],
				border:true,
		        idField: 'id',
		        onLoadSuccess:this.loadCss
			});
		},
		loadCss:function(data){
			$(".download_button").linkbutton({text:'下载',iconCls:'icon-btn-download',plain:true});
			$(".no_download_button").linkbutton({text:'下载',iconCls:'icon-btn-download',plain:true,disabled:true});	
			$(".share_button").linkbutton({text:'分享',iconCls:'icon-btn-share-alt',plain:true});
			$(".no_share_button").linkbutton({text:'分享',iconCls:'icon-btn-share-alt',plain:true,disabled:true});	
			$(".delete_button").linkbutton({text:'删除',iconCls:'icon-btn-trash',plain:true});
			$("#fileTable").datagrid('fixRowHeight');
		},
		operFormat:function(val,row,index){  
			var fileid = row["id"];
			var fileName = row["fileName"];
			var status = row["status"];
			var creatorAcc = row["creatorAcc"];
			var downloadClick = "javascript:modules.smartquery.market.files.downloadfile('"+fileid+"')";
			var shareClick = "javascript:modules.smartquery.market.files.sharefile('"+fileid+"','"+fileName+"')";
			var downloadClass = "download_button";
			var shareClass = "share_button";
			if(status === '2' || status === '3'){
				downloadClick = "";
				downloadClass = "no_download_button";
				shareClick = "";
				shareClass = "no_share_button";
			}
			
			var html = "<a href=\"javascript:void(0);\" onclick=\""+downloadClick+"\" class=\""+downloadClass+"\">下 载</a>";
			if(creatorAcc === modules.smartquery.market.userAcc)
				html += "<a href=\"javascript:void(0);\" onclick=\""+shareClick+"\" class=\""+shareClass+"\">分 享</a>" +
					"<a href=\"javascript:void(0);\" onclick=\"javascript:modules.smartquery.market.files.deletefile('"+fileid+"','"+fileName+"')\" class=\"delete_button\">删 除</a>";
			return html;
			
		},
		statusFormat:function(val,row,index){
			var status = row["status"];
			var v = "<font color='#95CEFF'>正常</font> ";
			if(status === '1'){
				v = "<font color='#A9FF96'>已分享</font>";
			}else if(status === '2'){
				v = "<font color='#FFBC75'>已丢失</font>";
			}else if(status === '3'){
				v = "<font color='#FF3D30'>已过期</font>";
			}
			return v;
			
		},
		sharefile:function(fileId,fileName){
			modules.smartquery.market.shareUser.open(fileId,fileName);
		},
		checkfile:function(fileId){
			var url = ctx + "/a/smart-query/market/file/check";
			return Boolean($.ajax({ url: url,data: {fileId:fileId}, async: false }).responseText);
		},		
		downloadfile:function(fileId){
			common.utils.showLoading();
			if(!this.checkfile(fileId)){
				common.utils.closeLoading();
				common.utils.showErrorMsg("数据文件不存在或已丢失，下载操作失败！",function(){
					modules.smartquery.market.files.$grid.datagrid("reload");
				});
				return;
			}				
			document.forms[0].action=ctx + "/a/smart-query/market/file/download?fileId="+fileId;
			document.forms[0].target="downloadFrame";
			document.forms[0].submit();
			common.utils.closeLoading();
		},
		deletefile:function(fileId,fileName){
			layer.confirm('是否需要删除“'+fileName+'”这个数据文件？', {
				icon: 3,
			    btn: ['删 除','取 消'], 
			    shade: 0.2 
			}, function(index){
				layer.close(index);
				common.utils.showLoading();
				$.ajax({
		             type: "POST",
		             url: ctx + "/a/smart-query/market/file/delete",
		             data: {fileId:fileId},
		             dataType: "json",
		             success: function(data){
		             	common.utils.closeLoading();
		             	if(data) {
		             		common.utils.showSucMsg("数据文件删除成功！",function(){$("#fileTable").datagrid("reload");});	             		
		             	}else{
		             		common.utils.showErrorMsg("数据文件删除失败，请稍候再试！");
		             	}
		             }
		         });
			});
		}
	}
	
	
};