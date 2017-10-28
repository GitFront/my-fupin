$.namespace("modules.datalabel");

/**
 * 标签树配置页面控制类js
 * @type 
 */

modules.datalabel.config = {
	/*页面对象管理*/
	treeId:"labelTreeID",
	labelTree:{},
	/**
	 * 页面初始化方法
	 */
	init:function(){		
		/*初始化页面元素*/
		this.tree.init();
	},
	tree:{
		init:function(){
			/* 加载目录数据 */
			var opt = {
					async:true,/* 是否异步获取节点数据*/
					url:ctx + "/a/data-label/config/tree", /*服务器访问地址*/
					mapper:{id:'id',name:'name',pId:'pid',fullPath:"fullPath",type:'type',
							assoTable:'assoTable',assoField:'assoField',assoDim:'assoDim',assoRule:'assoRule',topNum:'topNum',
							isAll:'isAll',creator:'creator',createTime:'createTime',sort:'sort',isParent:'isParent',open:'open'}, /*字段对应关系*/
					newRootName:"数据标签树定义",
					newEditName:"新目录或标签", /* 新增节点默认名称*/
					isRootRename:false
				};
			var callback = {
					noNewRoot:"modules.datalabel.config.tree.edit.addRootNode",
					beforeNew:"modules.datalabel.config.tree.edit.beforeAdd",
					onNew:"modules.datalabel.config.tree.edit.addChildNode",
					onRename:"modules.datalabel.config.tree.edit.renameNode",
					onRemove:"modules.datalabel.config.tree.edit.removeNode",
					onClick:"modules.datalabel.config.utils.treeClick"
				};
			
			modules.datalabel.config.labelTree = new zEditTree(modules.datalabel.config.treeId,[],{},callback,opt);
		},
		reload:function(){
			modules.datalabel.config.labelTree.asynReload();
		},
		/* 标签树的操作方式 */
		edit:{
			/* 保存默认根节点信息 */
			addRootNode:function(nodeId,nodeName,nodeParentId){
				$.ajax({
		             type: "POST",
		             url: ctx + "/a/data-label/config/save/node",
		             data: {id:nodeId,name:nodeName,pid:nodeParentId,fullPath:nodeName},
		             dataType: "json",
		             success: function(suc){
		             	if(!suc) {
		             		common.utils.showErrorMsg("标签根节点保存失败，请稍候再试！",function(){
		             			modules.datalabel.config.labelTree.asynReload();
		             		});
		             	}
		             }
		         });
			},
			/*新增保存节点前操作*/
			beforeAdd:function(treeId, treeNode){
				var type = treeNode.type;
				if(type === 'label'){
					common.utils.showErrorMsg("数据标签不能再定义叶子节点！");
					return false;
				}
			},
			/* 新增保存节点信息 */
			addChildNode:function(treeId, treeNode, newNodeid, newNodeName, newParentId){
				var newFullPath = modules.datalabel.config.utils.getNodePath(treeNode) +"/"+ newNodeName;
				$.ajax({
		             type: "POST",
		             url: ctx + "/a/data-label/config/save/node",
		             data: {id:newNodeid,name:newNodeName,pid:newParentId,fullPath:newFullPath},
		             dataType: "json",
		             success: function(suc){
		             	if(!suc) {
		             		common.utils.showErrorMsg("标签节点保存失败，请稍候再试！",function(){
		             			modules.datalabel.config.labelTree.asynReload();
		             		});
		             	}
		             }
		         });
			},
			/* 重命名节点信息 */
			renameNode:function(treeId, treeNode){
				var id = treeNode.id;
				var name = treeNode.name;
				var fullPath = modules.datalabel.config.utils.getNodePath(treeNode);
				$.ajax({
		             type: "POST",
		             url: ctx + "/a/data-label/config/rename",
		             data: {id:id,name:name,fullPath:fullPath},
		             dataType: "json",
		             success: function(suc){
		             	if(!suc) {
		             		common.utils.showErrorMsg("标签节点重命名失败，请稍候再试！",function(){
			             		editTree.asynReload();
		             		});
		             	}
		             }
		         });
			},
			/* 删除节点信息 */
			removeNode:function(treeId, treeNode){
				// 删除操作
				var id = treeNode.id;
				$.ajax({
				   type: "POST",
				   url: ctx + "/a/data-label/config/remove?id="+id,
				   dataType: "json",
				   success: function(suc){
				        if(suc){	            		
				            common.utils.showSucMsg("标签节点删除成功！",function(){
					            modules.datalabel.config.tabs.removeTab(treeNode.id,treeNode.name);
					        });
				        }else{
				            common.utils.showErrorMsg("标签节点删除失败，请稍候再试！",function(){
					            modules.datalabel.config.labelTree.asynReload();
				            });
				        }				             	
				 	}
				});			
			}
		}
	},
	tabs:{
		addTab:function(id,title,url){
			title = modules.datalabel.config.tabs.getTabName(title);
			/* 判断标签是否存在 */
			if($('#configs').tabs('exists',title)){
				
				/* 刷新标签内容 */
				document.getElementById(id).src = url;
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
		},
		/* 标签页删除方法 */
		removeTab:function(id,title){
			title = modules.datalabel.config.tabs.getTabName(title);
			/* 判断标签是否存在 */
			if($('#configs').tabs('exists',title)){
				/* 释放iframe内容 */
				var ifm=$(id);  
                ifm.src=""; 
				/* 关闭标签信息 */
			    $('#configs').tabs('close',title);				
			}		
		},
		getTabName:function(nodeName){
			return "[编辑]"+nodeName;
		}
	},
	utils:{
			/*获取ztree的节点全路径节点名称信息*/
			getNodePath:function(treeNode){
				if(treeNode === null)return "";
				var nodename = treeNode.name;
				var pNode = treeNode.getParentNode();
				if(pNode!=null){
					nodename = modules.datalabel.config.utils.getNodePath(pNode) +"/"+ nodename;
				}
				return nodename;
			},
			treeClick:function(treeId, treeNode){
				var url =ctx + "/a/data-label/config/label-config.htm?id="+treeNode.id+"&isParent="+ treeNode.isParent;
				modules.datalabel.config.tabs.addTab(treeNode.id,treeNode.name,url);
			}
		}
};