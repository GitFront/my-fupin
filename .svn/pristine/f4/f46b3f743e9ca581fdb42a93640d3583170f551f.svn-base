$.namespace("modules.smartquery");

/**
 * 目录分享js
 * @type 
 */

modules.smartquery.catalog = {
	/*页面初始化方法*/
	init:function(){
		/*绑定查询栏按钮*/
		$('#catalog_share_btn').bind('click', this.search); 
		$('#create_catalog_btn').bind('click', this.createCatalog); 
		this.datagrid.init("#catalog","");
		this.userztree1.init();
		this.userTreeWin.init();
	},
	userTreeWin:{
 		init:function(){
 			$("#userTreeSave").bind("click",modules.smartquery.catalog.userTreeWin.save);
 			$("#userTreeCancel").bind("click",modules.smartquery.catalog.userTreeWin.close);
 		},
 		open:function(name){
 			var treeObj = $.fn.zTree.getZTreeObj("userztree1");
 			if(treeObj!=null){
 			$("#userTreeWin").window('center');
 			common.utils.showLoading();
 			var params={}
 			var row=modules.smartquery.catalog.datagrid.$grid.datagrid("getSelected");
 			params.id=row.id;
        	treeObj.checkAllNodes(false);
 			$("#legend").text("目录："+row.name);
			$.ajax({
	             type: "POST",
	             data:params,
	             url: ctx + "/a/smart-query/catalog/userSelect/list",
	             dataType: "json",
	             success: function(data){
	            	 $.each(data,function(i,v){
	            		 var id=v.id.userId;
	            		 var pid=v.id.personalCatalogId;
	            		 var pnode=treeObj.getNodeByParam("id", pid);
	            		 var node=treeObj.getNodeByParam("id", id, pnode);
	            		 treeObj.checkNode(node, true, true);
	            	 })
	            	 
	            	 common.utils.closeLoading();
	             },error:function(){
	            	 common.utils.closeLoading();
	             }
	         });
				$("#userTreeWin").window('open');
 			}
 		},
 		close:function(){
 			$("#userTreeWin").window('close');
 		},
 		save:function(){
 			//layer.load(0, {shade: [0.4,'#fff']});
 			common.utils.showLoading();
 			var row=modules.smartquery.catalog.datagrid.$grid.datagrid("getSelected");
 			var params={};
 			var userIds=[];
 			var checkedNodes=$.fn.zTree.getZTreeObj("userztree1").getCheckedNodes()
 			$.each(checkedNodes,function(i,node){
 				if(!node.isParent){
 					userIds.push(node.id);
 				}
 			})
 			$.extend(params,{userIds:userIds});
 			params.catalogId=row.id;
 			params=jQuery.param(params,true);
 		 	$.ajax({
	 			type : 'post',
	 			data : params,
	 			dataType:"json",
	 			url : ctx + "/a/smart-query/catalog//catalogshare/save",
	 			success : function(result){
	 				common.utils.closeLoading();
	 				if(result.success){
	 					common.utils.showSucMsg(result.msg,function(){
	 						modules.smartquery.catalog.datagrid.$grid.datagrid("reload");
	 						modules.smartquery.catalog.userTreeWin.close();
	 					});
	 				}else{
	 					common.utils.showErrorMsg(result.msg);
	 				}
	 				
	 			},
	 			error:function(res){
	 				//layer.closeAll('loading');
	 				common.utils.closeLoading();
	 				$.messager.alert("错误","保存失败", "error");
	 			}
 		 	});
 		}
 			
 			
 	},
 	/*初始化用户的个人目录*/
 	createCatalog:function(){
 		common.utils.showLoading();
 		$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/catalog/userCatalog/create",
	             dataType: "json",
	             success: function(flag){
	             	common.utils.closeLoading(); 
	             	if(flag){
	             		common.utils.showSucMsg("个人目录已创建成功！",modules.smartquery.catalog.search);
	             	}else{
	             		common.utils.showErrorMsg("个人目录创建失败，请稍候再试！");
	             	} 
	             }
	         });
 	},
	userztree1:{
		init:function(){
			/*初始化参数设置*/
			var setting = {
				treeId:'userztree1',
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
	        			modules.smartquery.catalog.userztree1 = $.fn.zTree.init($("#userztree1"), setting, data);
	             	}
	             }
	         });	
		}
	},
	datagrid:{
		init:function(gridId,queryName){
			modules.smartquery.catalog.datagrid.$grid=$(gridId);
			modules.smartquery.catalog.datagrid.$grid.datagrid({
				idField:'id',    
			    fitColumns:true,
			    lines:true,
			    method:'post',
			    animate:true,
			    striped : true,
				loadMsg : '数据加载中...',
				pagination : true,
				pageSize : '15',
				pageList : [10, 15, 20, 30, 40 ],
				border : true,
			    url:ctx+"/a/smart-query/catalog/list"
			});
		},
		f_oper:function(value,row,index){
			return "<a href='javascript:modules.smartquery.catalog.userTreeWin.open()' >分配<a>"
		},
		f_shareStatus:function(value,row,index){
			if(value==1){
				return "已共享"
			}
			return "未共享"
		}
	},
	search:function () {
		/*layer.msg("查询数据...", {
			shade : 0.2
		});
		*/
		var param = {};
	    param.creator = $("#creator").textbox('getValue');
		param.name = $("#name").textbox('getValue');
		modules.smartquery.catalog.datagrid.$grid.datagrid("reload",param);
	}
	

}