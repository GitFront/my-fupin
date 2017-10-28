$.namespace("modules.smartquery");
		/* 保存默认个人路径 */
		function addDefaultPath(nodeId,nodeName,nodeParentId){
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/query/personalcatalog/save",
	             data: {id:nodeId,name:nodeName,pId:nodeParentId,fullPathId:nodeId,fullPath:nodeName},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("个人默认目录失败，请稍候再试！",function(){
	             			modules.smartquery.query.catalogTree.reloadPersonalCatalog();
	             		});
	             	}
	             }
	         });
		}
		
		/* 保存节点信息 */
		function addChildPath(treeId, treeNode, newNodeid, newNodeName, newParentId){
			var newFullPath = modules.smartquery.query.catalogTree.getNodePath(treeNode) +"/"+ newNodeName;
			var newFullPathId = modules.smartquery.query.catalogTree.getNodePathId(treeNode) +"/"+ newNodeid;
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/query/personalcatalog/save",
	             data: {id:newNodeid,name:newNodeName,pId:newParentId,fullPathId:newFullPathId,fullPath:newFullPath},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("个人目录保存失败，请稍候再试！",function(){
	             			modules.smartquery.query.catalogTree.reloadPersonalCatalog();
	             		});
	             	}
	             }
	         });
		}
		
		/* 重命名节点信息 */
		function renamePath(treeId, treeNode){
			var id = treeNode.id;
			var name = treeNode.name;
			var fullPath = modules.smartquery.query.catalogTree.getNodePath(treeNode);
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/query/personalcatalog/rename",
	             data: {id:id,name:name,fullPath:fullPath},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("个人目录重命名失败，请稍候再试！",function(){
		             		modules.smartquery.query.catalogTree.reloadPersonalCatalog();
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
		             		modules.smartquery.query.catalogTree.reloadPersonalCatalog();
	            		});
	            	}	             	
	             }
	         });
		}
		