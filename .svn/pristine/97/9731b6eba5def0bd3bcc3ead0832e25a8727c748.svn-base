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
		}
		
		/* 标签页删除方法 */
		function removeTab(id,title){
			/* 判断标签是否存在 */
			if($('#configs').tabs('exists',title)){
				/* 释放iframe内容 */
				var ifm=$(id);  
                ifm.src=""; 
				/* 关闭标签信息 */
			    $('#configs').tabs('close',title);				
			}		
		}
	</script>
	<script type="text/javascript">
		/* 目录树的操作方式 */
		/* 目录树点击方法 */
		var clickid = common.utils.guid();
		
		function catalogClick(treeId, treeNode){
			var url =ctx + "/a/dataMapping/mapping/binding-config.htm?id="+treeNode.id;
			addTabs(clickid,"目录绑定管理",url);
		}
		
		/**
		 * 获取ztree的节点全路径节点名称信息
		 */
		function getNodePath(treeNode){
			if(treeNode === null)return "";
			var nodename = treeNode.name;
			var pNode = treeNode.getParentNode();
			if(pNode!=null){
				nodename = getNodePath(pNode) +"/"+ nodename;
			}
			return nodename;
		}
		
		/* 保存默认根节点信息 */
		function addRootNode(nodeId,nodeName,nodeParentId){
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/dataMapping/mapping/tablecatalog/save",
	             data: {id:nodeId,name:nodeName,pId:nodeParentId,fullPath:nodeName},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("目录根节点保存失败，请稍候再试！",function(){
	             			editTree.asynReload();
	             		});
	             	}
	             }
	         });
		}
		
		/* 保存节点信息 */
		function addChildNode(treeId, treeNode, newNodeid, newNodeName, newParentId,type){
			var newFullPath = getNodePath(treeNode) +"/"+ newNodeName;
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/dataMapping/mapping/tablecatalog/save",
	             data: {id:newNodeid,name:newNodeName,pId:newParentId,fullPath:newFullPath,type:treeNode.type},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("目录节点保存失败，请稍候再试！",function(){
	             			editTree.asynReload();
	             		});
	             	}
	             }
	         });
		}
		
		/* 重命名节点信息 */
		function renameNode(treeId, treeNode){
			var id = treeNode.id;
			var name = treeNode.name;
			var fullPath = getNodePath(treeNode);
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/dataMapping/mapping/tablecatalog/rename",
	             data: {id:id,name:name,fullPath:fullPath},
	             dataType: "json",
	             success: function(suc){
	             	if(!suc) {
	             		common.utils.showErrorMsg("目录节点重命名失败，请稍候再试！",function(){
		             		editTree.asynReload();
	             		});
	             	}
	             }
	         });
		}
		/* 删除节点前判断 */
		function beforeRemoveNode(treeId, treeNode){
			var id = treeNode.id;
			var url =  ctx + "/a/dataMapping/mapping/tablecatalog/isremove?id="+id;
			
			var rs = Boolean($.ajax({ url: url, async: false }).responseText); 
			if(!rs) common.utils.showErrorMsg("目录节点存在已绑定的宽表映射信息，请解除绑定后再进行删除操作！");
			return rs;
		}
		
		
		/* 删除节点信息 */
		function removeNode(treeId, treeNode){
			var id = treeNode.id;
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/dataMapping/mapping/tablecatalog/remove?id="+id,
	             dataType: "json",
	             success: function(suc){
	            	if(suc){	            		
	            		common.utils.showSucMsg("目录节点删除成功！",function(){
	            			if(treeNode.level == 0){
	            				$('#theme').combobox('reload');  
	            			}
	            			removeTab(clickid,"目录绑定管理");
	            		});
	            		
	            	}else{
	            		common.utils.showErrorMsg("目录节点删除失败，请稍候再试！",function(){
		             		editTree.asynReload();
	            		});
	            	}
	             	
	             }
	         });
		}
		function addThemeWin(){
			$('#win').window('open');  
		}
		function saveTheme(){
			var type=$('#type').textbox('getValue');  
			if($('#type').textbox('isValid')){
				$.ajax({
		             type: "POST",
		             data:{"type":type},
		             url: ctx + "/a/dataMapping/mapping/tablecatalog/saveTheme",
		             dataType: "json",
		             success: function(data){
		            	if(data[0]){	            		
		            		common.utils.showSucMsg(data[1],function(){
		            			$('#win').window('close'); 
		            			$('#theme').combobox('reload');  

		            		});
		            	}else{
		            		common.utils.showErrorMsg(data[1]);
		            	}
		             	
		             }
		         });
			}
		}
		
		function initCategoryTree(type){
			
			/* 加载目录数据 */
			var opt = {
					async:true,/* 是否异步获取节点数据*/
					params: {"type":type},
					url:ctx + "/a/dataMapping/mapping/tablecatalog/list", /*服务器访问地址*/
					mapper:{id:'id',name:'name',pId:'parentId',nfullPath:"fullPath",type:'type'}, /*字段对应关系*/
					newRootName:"业务主题分类",
					newEditName:"新目录", /* 新增节点默认名称*/
					isExpand:true, /*是否默认展开全部树节点*/
					showFolder:true
				};
			var callback = {
					noNewRoot:"addRootNode",
					onNew:"addChildNode",
					onRename:"renameNode",
					onRemove:"removeNode",
					beforeRemove:"beforeRemoveNode",
					onClick:"catalogClick"
				};
			
			editTree = new zEditTree("categoryTree",[],{},callback,opt);
			
			
		}
		
		
		$(document).ready(function(){
			$('#theme').combobox({
				onLoadSuccess:function (none){
					$('#theme').combobox("select",none[0].type);
				},
				onSelect:function(record){
					if(record){
						initCategoryTree(record.type);
					}
				}
			}); 
		});
	</script>
</head>
	<body class="easyui-layout">  
	    <div id="cgTree"  data-options="region:'west',split:true,title:'目录管理'" style="width:220px;padding:5px;">
	    
	    	<div >
				<input id="theme"  style="width:135px;height: 22px; float: left;" data-options="valueField:'id',textField:'type',url:'${ctx}/a/dataMapping/mapping/tablecatalog/theme'" />  	
				<a id="btn" href="javascript:addThemeWin()"  style="height: 22px;"class="easyui-linkbutton" >添加主题</a>  
			</div>
			<!-- 目录树 -->
			<ul id="categoryTree" class="ztree"></ul>  
	    </div>
	    <div id="configs" class="easyui-tabs"  data-options="region:'center'"  style="padding: 1px;overflow:hidden;"></div>
	    <!-- 增加主题 -->
		<div id="win" class="easyui-window" title="增加主题"
			style="width: 400px; height: 200px"
			data-options="iconCls:'icon-save',modal:true,closed:true">
			
			<div class="easyui-layout" data-options="fit:true">   
		        <div data-options="region:'south'" style="height:40px;padding: 7 20">
		        	<a id="btn" href="javascript:saveTheme()"  style="height: 22px; float: right;"class="easyui-linkbutton" >&nbsp;&nbsp;保存&nbsp;&nbsp;</a>  
		        </div>   
		        <div data-options="region:'center'"> 
						<div style="padding: 30 70">
							<label>主题名称：</label>
							<input class="easyui-textbox" id="type" data-options="required:true" style="width:150px;height: 25px;"> 
						</div>
		 		</div>   
    	</div>   
			
			
			
		</div>



</body>
</html>
