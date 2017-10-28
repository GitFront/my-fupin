$.namespace("common.plugins.ztree");

/**
 * 动态调用自定义函数值
 * @param {} treeId ztree对象的ID值
 * @param {} callType 回调函数的类型
 * @param {} parameter 回调函数参数值
 * @param {} defaults 初始化默认值
 * @return {} 返回回调函数返回值
 */
function zEditTreeDIYCallback(treeId,callType,parameter,defaults){
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var flag = defaults;
	var call = zTree.diyCallback;
	if(call.hasOwnProperty(callType) && common.utils.isFunction(call[callType])) {
		/*回调函数操作*/
		var flag = eval('(' + call[callType] + '(parameter.treeId, parameter.treeNode)' + ')');
	}
	return flag;
}

/**
	* ztree 可编辑树初始化方法
	* @param {} container 可编辑树指向对象ID
	* @param {} zNodes 初始化可编辑树数据
	* @param {} setting ztree的setting对象（部分内置参数无法修改）
	* @param {} callback 可编辑树的触发对象，为json格式，属性如下
	* 				beforeDrag:拖拉前操作(返回true或false可以控制操作是否继续进行)-默认不可操作(参数：treeId, treeNodes)
	* 				beforeNew:新增前操作(返回false可以控制操作是否中断，继续则不需要返回)-默认不可操作(参数：treeId, treeNodes)
	* 				beforeEditName：节点编辑操作前回调函数(返回true或false可以控制操作是否继续进行)(参数：treeId, treeNode)
	* 				beforeRemove：节点删除前操作前回调函数(返回true或false可以控制操作是否继续进行)(参数：treeId, treeNode)
	* 				beforeRename：节点重命名操作前回调函数(返回true或false可以控制操作是否继续进行)(参数：treeId, treeNode)
	* 				noNewRoot:当返回无数据时主动生成root节点的回调方法(参数：rootid,rootNodeName,rootParentId)
	* 				onNew:节点新增回调函数(参数：treeId, treeNode, newNodeid, newNodeName, newParentId)
	* 				onRemove：节点删除后回调函数(参数：treeId, treeNode)
	* 				onRename：节点重命名后回调函数(参数：treeId, treeNode)
	* 				onDrop:节点拖拉后回调函数(参数：treeId, treeNodes, targetNode, moveType, isCopy)
	* 				onClick：节点点击回调函数(参数：treeId, treeNode)
	* @param {} option async 是否需要通过异步查询数据（默认是false）
	* 				   url 异步加载url
	* 				   params 传递参数 eg:{n1:参数1,n2:参数2}
	* 				   mapper 字段对应信息 eg:{id:'nid',name:'nodename',pId:'parentId'}
	* 				   newRootName 默认的根节点名称
	* 				   newEditName 默认的新建节点名称
	* 				   isExpand 是否需要默认打开
	* 				   isFolder 是否为文件夹
	* @return 返回可编辑树对象
	*/
function zEditTree(container,zNodes,setting,callback,option){
	   /*内置存储树对象*/
	   this.treeObj={};
	   
	   this.container = container;
	   
	   if(!($("#"+container).length)) return;
	   /*处理参数默认值*/
	   this.parameter = this.defaults(zNodes,setting,callback,option);
	
	   if(this.parameter.option.async && this.parameter.option.url !== ""){
	   		/*异步数据处理*/
	   		this.treeObj = this.asyncTree();	   		
	   }else{
		   /*通过静态数据生成ztree*/
		   this.treeObj = $.fn.zTree.init($("#"+container), this.parameter.setting, this.parameter.zNodes);
		   /*初始化编辑树参数*/
		   this.treeParams(this.treeObj);
	   }
	};
		/**
		 * 刷新异常可编辑树的数据
		 */
		zEditTree.prototype.asynReload = function() {
			if(this.parameter.option.async && this.parameter.option.url !== ""){
				this.asyncTree();
			}			
		};
		
		/**
		 * 调用用户自定义新增根节点函数
		 * @return {} 返回默认根节点信息
		 */
		zEditTree.prototype.onNewRoot = function() {
			if(this.parameter.noRoot){	
				var returnNode = undefined;
				var call = this.parameter.callback;
				if(call.hasOwnProperty("noNewRoot") && common.utils.isFunction(call["noNewRoot"])) {
					/*回调函数操作*/
					returnNode = eval('(' + call["noNewRoot"] + '(this.defaultRootNode[0].id, this.defaultRootNode[0].name,this.defaultRootNode[0].pId)' + ')');
				}
				if(returnNode){
					return returnNode;
				}else{
					return this.defaultRootNode;
				}				
			}
			return undefined;
		};
		
		
		
		/**
		 * 处理异步加载数据
		 * @param {} container
		 * @param {} parameter
		 */
		zEditTree.prototype.asyncTree = function() {
			var ztree = this;
		    var url = ztree.parameter.option.url;
	   		var params = ztree.parameter.option.params;
	   		var mapper = ztree.parameter.option.mapper;
	   		$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             cache:true,
	             dataType: "json",
	             success: function(data){
	             	var nodes = [];
	             	if(!common.utils.isArrayEmpty(data)){
	             		/*动态加载数据*/
		             	for (var i = 0; i < data.length; i++) {
		             		var node = {};
		             		for (var item in mapper) {
								node[item] = data[i][mapper[item]];
							}
							if(!node.hasOwnProperty("isParent") && ztree.parameter.option.showFolder) node["isParent"] = true;				
							
		             		nodes.push(node);
		             	}
	             	}
	             	/*通过静态数据生成ztree*/
				    var treeObj = $.fn.zTree.init($("#"+ztree.container), ztree.parameter.setting, nodes);
				    /*初始化编辑树参数*/
				    ztree.treeParams(treeObj);
				    return treeObj;
	             }
	         });
		};
		
		/**
		 * 初始化编辑树参数
		 * @param {} treeObj
		 */
		zEditTree.prototype.treeParams = function(treeObj) {
			/*加载用户自定义回调函数*/
			treeObj.diyCallback = this.parameter.callback;
			/*新建节点计数器*/
			treeObj.Z_EDIT_TREE_COUNT = 1;
			/*是否显示Folder样式*/
			treeObj.showFolder = this.parameter.option.showFolder;
			/*新建节点默认名*/
			treeObj.Z_EDIT_TREE_NEW_EDIT_NAME = this.parameter.option.newEditName;
			/*是否默认展开节点信息*/
			if(this.parameter.option.isExpand)
				treeObj.expandAll(this.parameter.option.isExpand);
		};
		
		/**
		 * 拖拉前回调方法
		 * @param {} treeId
		 * @param {} treeNodes
		 * @return {}
		 */
		zEditTree.prototype.beforeDrag = function(treeId, treeNodes) {
			/*拖拉前操作*/
			return zEditTreeDIYCallback(treeId,"beforeDrag",{treeId:treeId,treeNode:treeNodes},false);
		};
		
		/**
		 * 编辑节点前回调方法
		 * @param {} treeId
		 * @param {} treeNode
		 * @return {}
		 */
		zEditTree.prototype.beforeEditName=function(treeId, treeNode) {
			/*节点编辑前操作*/
			return zEditTreeDIYCallback(treeId,"beforeEditName",{treeId:treeId,treeNode:treeNode},true);
		};
		
		/**
		 * 节点删除前操作
		 * @param {} treeId
		 * @param {} treeNode
		 * @return {}
		 */
		zEditTree.prototype.beforeRemove = function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.selectNode(treeNode);
			var r=confirm('是否确认删除“' + treeNode.name + '”节点？')
				if (r){
					// 删除回调操作
					return zEditTreeDIYCallback(treeId,"beforeRemove",{treeId:treeId,treeNode:treeNode},true);
				}else{
					return false;
				}
			
		};
		
		/**
		 * 重命名前回调方法
		 * @param {} treeId
		 * @param {} treeNode
		 * @param {} newName
		 * @return {Boolean}
		 */
		zEditTree.prototype.beforeRename = function(treeId, treeNode, newName) {
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			if (newName.length === 0) {
				layer.msg('节点名称不能为空！', {icon: 2,shade: 0.2});				
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			/*节点重命名前操作*/
			return zEditTreeDIYCallback(treeId,"beforeRename",{treeId:treeId,treeNode:treeNode},true);
		};
		
		/**
		 * 删除后回调方法
		 * @param {} e
		 * @param {} treeId
		 * @param {} treeNode
		 */
		zEditTree.prototype.onRemove = function(e, treeId, treeNode) {	
			/*节点删除后操作*/
			zEditTreeDIYCallback(treeId,"onRemove",{treeId:treeId,treeNode:treeNode});
			
		};
		
		/**
		 * 重命名后回调方法
		 * @param {} e
		 * @param {} treeId
		 * @param {} treeNode
		 */
		zEditTree.prototype.onRename = function(e, treeId, treeNode) {
			/*节点重命名后操作*/
			zEditTreeDIYCallback(treeId,"onRename",{treeId:treeId,treeNode:treeNode});
		};
		
		/**
		 * 拖拉后回调方法
		 * @param {} e
		 * @param {} treeId
		 * @param {} treeNodes
		 * @param {} targetNode
		 * @param {} moveType
		 * @param {} isCopy
		 */
		zEditTree.prototype.onDrop = function(e, treeId, treeNodes, targetNode, moveType, isCopy) {
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			var call = zTree.diyCallback;
			if(call.hasOwnProperty("onDrop")) {
				/*节点拖拉后操作:moveType(:inner\prev) (isCopy==null? "cancel" : isCopy ? "copy" : "move")*/
				eval(call["onDrop"] + '(treeId, treeNodes, targetNode, moveType, isCopy)');				
			}
		};
		
		/**
		 * 节点点击方法
		 * @param {} e
		 * @param {} treeId
		 * @param {} treeNode
		 * @param {} clickFlag
		 */
		zEditTree.prototype.onNodeClick = function(e, treeId, treeNode,clickFlag) {
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			var call = zTree.diyCallback;
			if(call.hasOwnProperty("onClick")) {
				/*节点点击后操作:clickFlag(1)*/
				eval(call["onClick"] + '(treeId, treeNode,clickFlag)');				
			}
		};
		
		/**
		 * 节点添加新增按钮方法
		 * @param {} treeId
		 * @param {} treeNode
		 */
		zEditTree.prototype.addHoverDom = function(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.id
				+ "' title='新增叶子节点' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.id);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				var call = zTree.diyCallback;
				if(call.hasOwnProperty("beforeNew")) {
					/*节点新增后操作:beforeNew*/
					eval(call["beforeNew"] + '(treeId, treeNode)');			
				}
				var nodeid = common.utils.guid(); /*生成随机数*/
				var nodeName = zTree.Z_EDIT_TREE_NEW_EDIT_NAME + "_" + (zTree.Z_EDIT_TREE_COUNT++);
				var parentId = treeNode.id;
				zTree.addNodes(treeNode, {id:nodeid, pId:parentId, name:nodeName,isParent:zTree.showFolder});
				
				if(call.hasOwnProperty("onNew")) {
					/*节点新增后操作:onNew*/
					eval(call["onNew"] + '(treeId, treeNode, nodeid, nodeName, parentId)');				
				}
				return false;
			});
		};
		
		/**
		 * 节点解绑新增按钮方法
		 * @param {} treeId
		 * @param {} treeNode
		 */
		zEditTree.prototype.removeHoverDom = function(treeId, treeNode) {
			$("#addBtn_"+treeNode.id).unbind().remove();
		};
		
		/**
		 * 是否允许删除操作
		 * @param {} treeId
		 * @param {} treeNode
		 * @return {Boolean}
		 */
		zEditTree.prototype.isRemove = function(treeId, treeNode) {
			/*根节点不能删除*/
			//if(treeNode.level === 0) return false;
			return true;
		};
		
		/**
		 * 是否允许首节点编辑操作
		 * @param {} treeId
		 * @param {} treeNode
		 * @return {Boolean}
		 */
		zEditTree.prototype.noRootRename = function(treeId, treeNode) {
			/*根节点不能删除*/
			//if(treeNode.level === 0) return false;
			return true;
		};
		
		/**
		 * 参数格式化默认值
		 * @param {} zNodes
		 * @param {} setting
		 * @param {} callback
		 * @param {} option
		 */
		zEditTree.prototype.defaults = function(zNodes,setting,callback,option) {
			var setting_ = {};
			var callback_ = {};
			var noRoot = true;
			/*自定义配置的默认值设置*/
			var defaultOpt = {
					async:false,/* 是否异步获取节点数据*/
					url:'', /*服务器访问地址*/
					params:{}, /*服务器传递参数*/
					mapper:{id:'id',name:'name',pId:'pId'}, /*字段对应关系*/
					newRootName:"默认根节点", /* 新增默认根节点名称*/
				    newEditName:'新节点', /* 新增节点默认名称*/
				  	isExpand:false, /*是否默认展开全部树节点*/
				  	showFolder:false, /*是否默认显示文件夹样式*/
				  	showRenameBtn:true,/*是否显示重命名按钮*/
				  	showRemoveBtn:true,/*是否显示删除按钮*/
				  	renameTitle:"重命名",/*编辑按钮显示名称*/
				  	removeTitle:"删除",/*删除按钮显示名称*/
				  	isRootRename:true/*是否允许首节点编辑操作*/
			};
			var option_ = common.utils.setDefaultOption(option,defaultOpt);
			
			/*创建默认根节点*/
		   var defaultRootId = common.utils.guid();
		   this.defaultRootNode = [{id:defaultRootId,pId:-1, name:option_.newRootName,isParent:true}];
		   
			var zNodes_ = this.defaultRootNode;
			/*根节点不能进行删除操作*/
			if(option_.showRemoveBtn)
				option_.showRemoveBtn = this.isRemove;
			/*判断根节点是否允许进行编辑操作*/
			if(option_.showRenameBtn && !option_.isRootRename)
				option_.showRenameBtn = this.noRootRename;
				
			/*对象初始化操作*/
			var default_setting = {
				view: {
					addHoverDom: this.addHoverDom,
					removeHoverDom: this.removeHoverDom,
					selectedMulti: false
				},
				edit: {
					enable: true,
					showRemoveBtn:option_.showRemoveBtn,
					showRenameBtn:option_.showRenameBtn,
					removeTitle:option_.removeTitle,
					renameTitle:option_.renameTitle,
					/* 编辑名称时是否全选 text */
					editNameSelectAll: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeDrag: this.beforeDrag,
					beforeEditName: this.beforeEditName,
					beforeRemove: this.beforeRemove,
					beforeRename: this.beforeRename,
					onRemove: this.onRemove,
					onRename: this.onRename,
					onClick:this.onNodeClick
				}
			};
			
			/* 合并自定义配置项 */
			if(common.utils.isJson(setting)) setting_ = $.extend({}, setting, default_setting);
			/*初始化数据对象*/
			if(!common.utils.isArrayEmpty(zNodes)) {
				noRoot = false;
				zNodes_ = zNodes;
			} 
			/* 初始化callback对象 */
			if(common.utils.isJson(callback)) callback_ = callback;
			
			var parameter = {
				noRoot:noRoot,
				zNodes:zNodes_,
				setting:setting_,
				callback:callback_,
				option:option_
			};			
			return parameter;
		};
		
