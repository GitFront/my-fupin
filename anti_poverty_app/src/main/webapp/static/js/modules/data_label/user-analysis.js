$.namespace("modules.datalabel");

/**
 * 目标用户标签分析控制类js
 * @type 
 */

modules.datalabel.useranalysis = {
	/*页面对象管理*/
	treeId:'labelTreeID',
	searchId:'searchTreeID',
	searchNumId:'user_num_info',
	domTimerId:'dom_timer',
	domTagsId:'dom_tags',
	tableId:'resultTableID',
	historyListID:'historyID',
	settingWinId:'settingWinId',
	searchTypeId:'searchType',
	historyWinId:'historyWinId',
	progressWinId:'progressWinId',
	progressID:'exportProgress',
	/*数据标签随机key*/
	inLabelKey:'',
	/*数据标签查询当前查询的总条数*/
	inLabelTotal:0,
	/*当前选中数据周期的属性ID值*/
	currPropertyID:'',
	/*默认时间周期信息*/
	defaultTime:new Map(),
	/*用户号码默认字段名*/
	numColumn:'USER_NUM',
	/*标签树对象*/
	labelTreeObj:{},
	/*是否启动自动查询*/
	autoSearch:false,
	/*属性存储对象*/
	propertyMap : new Map(),
	/*标签存储对象*/
	labelMap : new Map(),
	/*数据标签历史周期存储对象*/
	timeMap : new Map(),
	/*临时属性存储对象*/
	tempPropertyMap : new Map(),
	/*临时标签存储对象*/
	tempLabelMap : new Map(),
	/*临时标签历史周期存储对象*/
	tempTimeMap : new Map(),
	/**
	 * 页面初始化方法
	 */
	init:function(){		
		/*初始化页面元素*/
		/*注册查询框回车事件*/
		$('#'+modules.datalabel.useranalysis.searchId).textbox('textbox').keydown(function(e) {
			if (e.keyCode == 13) {
				modules.datalabel.useranalysis.tree.search.searchNode();
			}
		});
		/*注册查询框回车事件*/
		$('#'+modules.datalabel.useranalysis.searchNumId).textbox('textbox').keydown(function (e) {
             if (e.keyCode == 13) {
                modules.datalabel.useranalysis.tags.filterNum();
             }
        });			
		
		/*操作区按钮绑定*/
		$('#label_search_btn').bind('click', this.tags.filterNum);
		$('#label_clear_btn').bind('click', this.tags.outLabel);
		$('#label_setting_btn').bind('click', this.utils.openSettingWin);
		$('#data_export_btn').bind('click', this.result.saveTemp);
		
		$('#setting_btn').bind('click', this.utils.setting);
		$('#close_setting_btn').bind('click', this.utils.closeSettingWin);
		
		$('#select_history_btn').bind('click', this.timer.choiceHistory);
		$('#close_select_history_btn').bind('click', this.timer.closeHistoryWin);
		
						
		this.tree.init();
		this.tree.initTimeTip();
		this.result.clear();
		
	},
	/*数据标签树*/
	tree:{
		init:function(){
			var setting = {
				treeId : modules.datalabel.useranalysis.treeId,
				edit : {
					enable : true,
					showRemoveBtn : false,
					showRenameBtn : false,
					drag : {
						prev: modules.datalabel.useranalysis.tree.MoveDom.prevTree,
						next: modules.datalabel.useranalysis.tree.MoveDom.nextTree,
						inner: modules.datalabel.useranalysis.tree.MoveDom.innerTree
					}
				},
				view : {
					selectedMulti : false,
					fontCss : modules.datalabel.useranalysis.tree.search.getFontCss
				},
				async: {
				    //异步加载
					enable: true,
					url: ctx + "/a/data-label/user-analysis/tree/layer",
					autoParam: ["id", "name", "pId","nodeType"]
				},
				data : {
					keep : {
						parent : true,
						leaf : true
					},
					key : {
						title : "name"
					},
					simpleData : {
						enable : true
					}
				},
				callback : {
					beforeAsync: modules.datalabel.useranalysis.tree.beforeAsync,
					onAsyncError: modules.datalabel.useranalysis.tree.onAsyncError,
					beforeDrag : modules.datalabel.useranalysis.tree.MoveDom.dragTree2Dom,
					onDrop : modules.datalabel.useranalysis.tree.MoveDom.dropTree2Dom
				}
			};
			/* 查询数据标签树信息 */
			common.utils.showLoading();
			var url = ctx + "/a/data-label/user-analysis/tree";
			$.ajax({
	             type: "POST",
	             url: url,
	             dataType: "json",
	             success: function(rs){
	             	common.utils.closeLoading();
	             	if(rs){
	             		modules.datalabel.useranalysis.labelTreeObj = $.fn.zTree.init($("#"+modules.datalabel.useranalysis.treeId), setting, rs);
	             	}else{
	    				common.utils.showErrorMsg("查询数据标签树操作失败，请稍候再试！");		    				
	             	}
	             }
	         });
		},
		/*初始化默认时间显示*/
		initTimeTip:function(){
			var url = ctx + "/a/data-label/user-analysis/period/default";
			$.ajax({
	             type: "POST",
	             url: url,
	             dataType: "json",
	             success: function(rs){
	             	if(rs){
	             		for (var index = 0; index < rs.length; index++) {
	             			modules.datalabel.useranalysis.defaultTime.put(rs[index].tableName,rs[index]);
	             		}
	             	}
	             }
	         });
		},
		reload:function(){
			
		},
		beforeAsync:function(treeId, treeNode) {
			/*只有在标签属性才进行异步加载*/
			if(treeNode.nodeType === 'label')
				return true;
			return false;
		},
		onAsyncError:function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			common.utils.showErrorMsg("“"+treeNode.name+"” 标签的分层数据加载失败，请稍候再试！");
		},
		/* 节点定位功能 */
		search:{
			nodeList:[], 
			fontCss:{},
			/*定位节点功能*/
			searchNode:function() {
				var zTree = modules.datalabel.useranalysis.labelTreeObj;
				var value = $.trim($('#'+modules.datalabel.useranalysis.searchId).val());
				modules.datalabel.useranalysis.tree.search.updateNodes(false);
				if (!common.utils.isEmpty(value)) {
					modules.datalabel.useranalysis.tree.search.nodeList = zTree.getNodesByParamFuzzy("name", value);
					modules.datalabel.useranalysis.tree.search.updateNodes(true);
				}
			},
			/*定位更新节点功能*/
			updateNodes:function (highlight) {
				var zTree = modules.datalabel.useranalysis.labelTreeObj;
				for (var i = 0, l = modules.datalabel.useranalysis.tree.search.nodeList.length; i < l; i++) {
					modules.datalabel.useranalysis.tree.search.nodeList[i].highlight = highlight;
					zTree.updateNode(modules.datalabel.useranalysis.tree.search.nodeList[i]);
				}
			},
			/*获取高亮字体样式*/
			getFontCss:function (treeId, treeNode) {
				return (!!treeNode.highlight) ? {
					color : "#A60000",
					"font-weight" : "bold"
				} : {
					color : "#333",
					"font-weight" : "normal"
				};
			},
			/*ztree过滤器*/
			filter:function(node) {
				return !node.isParent && node.isFirstNode;
			}
		},
		/*数据标签拖拽互动*/
		MoveDom: {
			curTarget : null,
			curTmpTarget : null,
			noSel : function() {
				try {
					window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
				} catch (e) {
						
				}
			},
			prevTree: function(treeId, treeNodes, targetNode) {
				return false;
			},
			nextTree: function(treeId, treeNodes, targetNode) {
				return false;
			},
			innerTree: function(treeId, treeNodes, targetNode) {
				return false;
			},		
			dragTree2Dom : function(treeId, treeNodes) {
				return !treeNodes[0].isParent;
			},
			dropTree2Dom : function(e, treeId, treeNodes, targetNode,moveType) {
				/*判断标签是否拖至标签筛选区*/
				var domId = modules.datalabel.useranalysis.domTagsId
				if( domId == e.target.id || $(e.target).parents("#" + domId).length > 0){
					/*获取当前已选标签信息*/
					var labelkeys = modules.datalabel.useranalysis.labelMap.keys();
					/*去除目标用户数的显示*/
					var nodeName = treeNodes[0].name;
					if (nodeName.indexOf(")") !== -1)
						nodeName = nodeName.substring(0, nodeName.lastIndexOf("(") - 1);
					if (labelkeys.indexOf(treeNodes[0].id) === -1) {
						var pnode = treeNodes[0].getParentNode();
						var pid = pnode.id;
						var propertyKeys = modules.datalabel.useranalysis.propertyMap.keys();
						var isActiveProperty = modules.datalabel.useranalysis.tempPropertyMap.containsKey(pid);
						/* 添加周期过滤器 */
						if (propertyKeys.indexOf(pid) === -1) {
							/*已存在属性可直接激活*/
							if(isActiveProperty){						
								modules.datalabel.useranalysis.timer.active(pid);
							}else{
								$("#"+modules.datalabel.useranalysis.domTimerId).append(
											"<a id='"+ pnode.id + "_dom' href='#' class='table-linkbutton"+ pnode.id+ "' "
											+ "style='padding:5px;margin-left:5px;margin-bottom:5px;' "
											+ "onclick='javascript:modules.datalabel.useranalysis.tree.MoveDom.onPropertyDom(\""
											+ pnode.id + "\")'> " + pnode.name + "</a>");
								$(".table-linkbutton" + pnode.id).linkbutton({iconCls : 'icon-btn-th-list',plain : false});
								modules.datalabel.useranalysis.propertyMap.put(pnode.id, pnode);
								var now = new Date();
								var pTime = common.utils.dateFormat(new Date(),'yyyyMMdd');
								if(common.utils.isDefined(modules.datalabel.useranalysis.defaultTime.get(pnode.assoTable)))
									pTime = modules.datalabel.useranalysis.defaultTime.get(pnode.assoTable).time;
								
								modules.datalabel.useranalysis.timeMap.put(pnode.id,pTime);
							}						
						}
						
						/*添加数据标签*/
						$("#"+modules.datalabel.useranalysis.domTagsId)
							.append("<a id='" + treeNodes[0].id + "_dom' href='#' class='tag-linkbutton" + treeNodes[0].id + "' "
									+ "style='padding:5px;margin-left:5px;margin-bottom:5px;' "
									+ " onclick='javascript:modules.datalabel.useranalysis.tree.MoveDom.onclickDom(\""
									+ treeNodes[0].id + "\")'> 【" + pnode.name + "】" + nodeName + "</a>");
						$(".tag-linkbutton" + treeNodes[0].id).linkbutton({iconCls : 'icon-btn-tag',plain : true,toggle : true,selected : true});
						modules.datalabel.useranalysis.labelMap.put(treeNodes[0].id, treeNodes[0]);
						if (modules.datalabel.useranalysis.autoSearch)
							modules.datalabel.useranalysis.tags.filterNum();
					} else {
						common.utils.showErrorMsg("“" + nodeName + "”标签已选中！");
					}
				}else{
					common.utils.showErrorMsg("请把数据标签拖拽到标签筛选区中！");
				}
			},
			/*选中或取消所选的数据标签信息*/
			onclickDom : function(id) {
				var selected = $("#" + id + "_dom").linkbutton("options").selected;
				/*切换按钮样式*/
				var sp = $('span:last', $("#" + id + "_dom")), tag = sp.hasClass('icon-btn-tag');
            	sp[tag ? 'removeClass' : 'addClass']('icon-btn-tag')[tag ? 'addClass' : 'removeClass']('label-disable');
				if (!selected) {
					/*处理标签生效操作*/
					var node = modules.datalabel.useranalysis.tempLabelMap.get(id);
					if (modules.datalabel.useranalysis.tempLabelMap.containsKey(id)) {						
						modules.datalabel.useranalysis.labelMap.put(id, node);
						modules.datalabel.useranalysis.tempLabelMap.remove(id);
					}
					/*处理属性生效操作*/
					var pid = node.pId;
					var isActiveProperty = modules.datalabel.useranalysis.tempPropertyMap.containsKey(pid);
					/*历遍属性区是否已存在这个属性，如果存在则不操作，不存在则生效属性*/
					
					if(isActiveProperty){						
						modules.datalabel.useranalysis.timer.active(pid);
					}
					/*$("#" + id + "_dom").linkbutton({iconCls:'icon-btn-tag',selected:selected});*/
					/*$("#" + id + "_dom").linkbutton('options').iconCls='icon-btn-tag';*/
				} else {
					/*处理标签失效操作*/
					var node = modules.datalabel.useranalysis.labelMap.get(id);
					if (!modules.datalabel.useranalysis.tempLabelMap.containsKey(id)) {
						modules.datalabel.useranalysis.tempLabelMap.put(id, node);
						modules.datalabel.useranalysis.labelMap.remove(id);
					} else {
						modules.datalabel.useranalysis.labelMap.remove(id);
					}
					/*处理属性失效操作*/
					var pid = node.pId;
					var isSaveProperty = false;
					var labels = modules.datalabel.useranalysis.labelMap.values();
					/*历遍分层段是否还存在该属性的值存在，如果存在则保留属性，不存在则删除属性*/
					for (var index = 0; index < labels.length; index++) {
						if(labels[index].pId === pid)
							isSaveProperty=true;
					}
					if(!isSaveProperty){
						modules.datalabel.useranalysis.timer.unactive(pid);
					}
					/*$("#" + id + "_dom").linkbutton({iconCls:'label-disable',selected:selected});*/
					/*$("#" + id + "_dom").linkbutton('options').iconCls='label-disable';*/
				}
				if (modules.datalabel.useranalysis.autoSearch)
					 modules.datalabel.useranalysis.tags.filterNum();
			},
			/*选中数据周期筛选窗口*/
			onPropertyDom : function(id) {
				modules.datalabel.useranalysis.currPropertyID = id;
				modules.datalabel.useranalysis.timer.openHistoryWin();
			}
		}
		
	},
	/*周期属性区*/
	timer:{
		/**
		 * 通过ID激活属性
		 * @param {} id
		 */
		active:function(id){
			var property = modules.datalabel.useranalysis.tempPropertyMap.get(id);
			modules.datalabel.useranalysis.propertyMap.put(id,property);
			modules.datalabel.useranalysis.tempPropertyMap.remove(id);
						
			var time = modules.datalabel.useranalysis.tempTimeMap.get(id);
			modules.datalabel.useranalysis.timeMap.put(id,time);
			modules.datalabel.useranalysis.tempTimeMap.remove(id);
						
			$("#"+id+"_dom").linkbutton('enable');
		},
		/**
		 * 通过ID失效属性
		 * @param {} id
		 */
		unactive:function(id){
			var property = modules.datalabel.useranalysis.propertyMap.get(id);
			modules.datalabel.useranalysis.tempPropertyMap.put(id,property);
			modules.datalabel.useranalysis.propertyMap.remove(id);
						
			var time = modules.datalabel.useranalysis.timeMap.get(id);						
			modules.datalabel.useranalysis.tempTimeMap.put(id,time);
			modules.datalabel.useranalysis.timeMap.remove(id);
						
			$("#"+id+"_dom").linkbutton('disable');
		},
		/*关闭数据周期选择窗口*/
		closeHistoryWin:function(){
			$('#'+modules.datalabel.useranalysis.historyWinId).dialog('close');
		},
		/*打开数据周期选择窗口*/
		openHistoryWin:function(){
			/*获取属性信息*/
			var property = modules.datalabel.useranalysis.propertyMap.get(modules.datalabel.useranalysis.currPropertyID);
			var name = property.name;
			/*获取属性所属的数据表名*/
			var table = property.assoTable;
			
			$('#'+modules.datalabel.useranalysis.historyListID).datalist({
				title:'“'+name+'”历史',
			    url: ctx+'/a/data-label/user-analysis/period/list?tableName='+table,
				loadMsg:'数据加载中...',
			    checkbox: true,
			    lines: false,
			    singleSelect: true,
			    valueField:'id',
			    textField:'text',
			    groupField:'group'
			});
			
			$('#'+modules.datalabel.useranalysis.historyWinId).dialog('open');
			$("#"+modules.datalabel.useranalysis.historyWinId).panel("move",{top:$(document).scrollTop()});	
			$('#'+modules.datalabel.useranalysis.historyWinId).dialog('resize');
		},
		/*选取历史数据*/
		choiceHistory:function(){
			var check = $('#'+modules.datalabel.useranalysis.historyListID).datalist('getChecked');
			if(check.length === 0){
				common.utils.showErrorMsg("请选择所需查询的数据周期信息！");
				return;
			}
			/*记录属性周期信息*/
			/*获取属性信息*/
			var property = modules.datalabel.useranalysis.propertyMap.get(modules.datalabel.useranalysis.currPropertyID);
			var id = property.id;
			var time = check[0].id;
			if(modules.datalabel.useranalysis.timeMap.containsKey(id))
				modules.datalabel.useranalysis.timeMap.remove(id);
			modules.datalabel.useranalysis.timeMap.put(id,time);
			
			/*更新属性显示方式*/
			var name = $("#" + id + "_dom").linkbutton("options").text;
			if (name.indexOf("】") !== -1)
				name = name.substring(0, name.lastIndexOf("【"));
			name = name + "【" + check[0].text + "】";
			$("#" + id + "_dom").linkbutton({text : name});	
			modules.datalabel.useranalysis.timer.closeHistoryWin();
		}
	},
	/*数据标签区*/
	tags:{
		/**
		 * 通过用户号码查询标签信息
		 * @param {} userNum
		 */
		inlabel:function(userNum) {
			if(!common.utils.isString(userNum))
				userNum = "";
			/*生成一个查询随机码*/
			modules.datalabel.useranalysis.inLabelKey = common.utils.guid();
			/*读取字段列名*/
			var properties = modules.datalabel.useranalysis.propertyMap.values();
			if (properties.length === 0) {
				common.utils.showErrorMsg("请选择需要进行数据筛选的标签拖拽至标签筛选区中！");
				return;
			}
			/* 添加用户号码 */
			var columns = new Array();			
			columns.push({field : modules.datalabel.useranalysis.numColumn,title : '用户号码',halign : 'center',align : 'left'});
				
			for (var int = 0; int < properties.length; int++) {
				var field = properties[int].assoField;
				var title = properties[int].name;
				columns.push({
					field : field,
					title : title,
					halign : 'center',
					align : 'left'
				});
			}
			var ids = new Array();
			var times = new Array();
			var values = new Array();
			/*读取字段查询数据周期及值集合*/
			for (var int = 0; int < properties.length; int++) {
				var propertie  = properties[int];
				var propertieId = propertie.id;
				ids.push(propertieId);
				var assoTable = propertie.assoTable;
				/*处理属性的周期信息*/
				var time = "";
				if(modules.datalabel.useranalysis.timeMap.containsKey(propertieId)){
					/*取用户所选择的数据周期*/
					time = modules.datalabel.useranalysis.timeMap.get(propertieId);
				}else{
					/*取默认数据库的数据周期*/
					time = modules.datalabel.useranalysis.defaultTime.get(assoTable).time;
				}
				times.push(time);
				
				/*处理属性的值信息*/
				var value = new Array();
				var labelArray = modules.datalabel.useranalysis.labelMap.values();
				for (var index = 0; index < labelArray.length; index++) {
					if(propertieId === labelArray[index].pId){
						value.push(labelArray[index].dimKey);
					}
				}
				values.push(value.join("|"));				
			}			
			
			/*组织查询SQL并输出查询结果*/
			var queryParams = {
				labels:ids.join(",") ,
				times:times.join(","),
				values:values.join(","),
				userNum:userNum,
				searchKey:modules.datalabel.useranalysis.inLabelKey
			};
			
			var h = $(document).height()-322;
			var pageSize = common.utils.getGridPage(h);
			
			/*查询并展示结果数据*/
			$("#"+modules.datalabel.useranalysis.tableId).datagrid({
				singleSelect:true,
				title:'结果输出区',
				height:h,
				rownumbers:true,
				url:ctx + "/a/data-label/user-analysis/datalist",
				queryParams:queryParams,
				fitColumns:true,
				loadMsg:'数据加载中...',
		    	toolbar:"#result_search_tb",
				pagination:true,
				pageSize:pageSize,
				pageList:[10,15,20,25,30,35,40,45,50],
				border:true,
		        idField: modules.datalabel.useranalysis.numColumn,
		        columns:[columns]  
			});
			
			/*重置查询参数信息*/
			 $('#'+modules.datalabel.useranalysis.tableId).datagrid('options').queryParams = {searchKey:modules.datalabel.useranalysis.inLabelKey,userNum:userNum};
			
		},
		/* 清空标签功能 */
		outLabel:function() {
			$.messager.confirm('消息确认', '是否需要清空全部已选择的数据标签？', function(r) {
				if (r) {
					/* 清空周期属性区与标签筛选区样式 */
					$("#"+modules.datalabel.useranalysis.domTimerId).empty();
					$("#"+modules.datalabel.useranalysis.domTagsId).empty();

					/* 清空缓存信息 */
					modules.datalabel.useranalysis.propertyMap.clear();
					modules.datalabel.useranalysis.labelMap.clear();
					modules.datalabel.useranalysis.timeMap.clear();
					modules.datalabel.useranalysis.tempPropertyMap.clear();
					modules.datalabel.useranalysis.tempLabelMap.clear();
					modules.datalabel.useranalysis.tempTimeMap.clear();
					modules.datalabel.useranalysis.inLabelKey = "";
					
					/*清空输出结果数据*/
					modules.datalabel.useranalysis.result.clear();
				}
			});
		},
		/*过滤用户号码*/
		filterNum:function(){
			/*获取用户号码过滤信息*/
			var info = $('#'+modules.datalabel.useranalysis.searchNumId).val();
			modules.datalabel.useranalysis.tags.inlabel(info);
		}
	},
	/*结果输出区*/
	result:{
		/*临时保存结果数据*/
		saveTemp:function(){
			
			var searchKey = modules.datalabel.useranalysis.inLabelKey;
			if(common.utils.isEmpty(searchKey)){
				common.utils.showErrorMsg("请先选择数据标签生成查询结果再进行导出操作！");
				return;
			}
			
			/*获取导出的数据总数*/
			var options = $('#'+modules.datalabel.useranalysis.tableId).datagrid('getPager').data("pagination").options;
			modules.datalabel.useranalysis.inLabelTotal = options.total;
			
			/** 数据导出操作*/
			var url = ctx + "/a/data-label/user-analysis/export/temp";
			var params = {searchKey:modules.datalabel.useranalysis.inLabelKey,total:modules.datalabel.useranalysis.inLabelTotal};
			/*生成导出临时文件*/
			$.ajax({
			     type: "POST",
			     url: url,
			     data: params,
			     dataType: "json",
			     success: function(rs){
				     if(!rs.flag){
				        common.utils.showErrorMsg("导出生成文件失败，请稍候再试！");
				     }
				  }
			});
			/*启动进度条加载信息*/
			modules.datalabel.useranalysis.result.progress.run();
		},
		/*导出结果数据*/
		exportData:function(){
			/*导出临时文件*/
			document.forms[0].action=ctx + "/a/data-label/user-analysis/export?fileId="+modules.datalabel.useranalysis.inLabelKey;
			document.forms[0].target="exportFrame";
			document.forms[0].submit();
		},
		/*清空结果列表数据*/
		clear:function(){
			var columns = new Array();
			/* 添加用户号码与ID值 */
			columns.push({field : modules.datalabel.useranalysis.numColumn,title : '用户号码',halign : 'center',align : 'left'});

			$("#"+modules.datalabel.useranalysis.tableId).datagrid({
					title:'结果输出区',
					singleSelect : true,
					rownumbers : true,
					data : {total:0,rows:[]},
					url:'',
					queryParams:{},
					fitColumns : true,
					striped : true,
					loadMsg : '数据加载中...',
					pagination : true,
					pageSize : '10',
					pageList : [ 10, 20, 30, 40, 50 ],
					border : true,
					idField : modules.datalabel.useranalysis.numColumn,
					columns : [ columns ]
			});
		},
		/*进度条处理*/
		progress:{
			/**
			 * 导出进度条处理
			 * @param {} scanTime
			 * @param {} interval
			 */
			run:function(){
				/*清空进度条*/
				$('#'+modules.datalabel.useranalysis.progressID).progressbar('setValue', '0');
			    $('#'+ modules.datalabel.useranalysis.progressWinId).window('open');
			    
			    $('#'+modules.datalabel.useranalysis.progressID).progressbar({
					onChange: function(value){
						if(value === 100){
						  /*前端导出操作*/
			              common.utils.showSucMsg("数据文件已导出完毕，请注意保存！",function(){
			              		modules.datalabel.useranalysis.result.exportData();
			              		$('#'+ modules.datalabel.useranalysis.progressWinId).window('close'); 
			              });
			           }
					}
				});
				
			    /*设置自动更新时间参数*/
			    var timer = setInterval(function(){
			    	$.ajax({
					     type: "POST",
					     url: ctx + "/a/data-label/user-analysis/export/progress",
			     		 data: {searchKey:modules.datalabel.useranalysis.inLabelKey},
					     dataType: "json",
					     success: function(rs){
						     if(rs.isFinish){
			                    $('#'+modules.datalabel.useranalysis.progressID).progressbar('setValue', '100');
			                    clearInterval(timer);
			                 }else{
			                   	$('#'+modules.datalabel.useranalysis.progressID).progressbar('setValue', rs.progress);
			                 }
						  }
					});
			      }, 2000);
			}			
		},
		/*输出结果语义翻译*/
		formatterColumn:function(val, row, index){
			var properties = modules.datalabel.useranalysis.propertyMap.values();
			
			var property = properties[index].id;
			var url = ctx+'/a/data-label/user-analysis/column/formatter';
			var params = {value:val,label:property};
			return $.ajax({ url: url,data: params, async: false }).responseText;
		}
	},
	/*公用工具区*/
	utils:{
		/*查询方式配置*/
		setting:function(){
			var type = $("#"+modules.datalabel.useranalysis.searchTypeId).combobox("getValue");
			if (modules.datalabel.useranalysis.autoSearch) {
				if (type === 'normal') {
					common.utils.showSucMsg("当前查询方式已修改为“被动查询”！");
					modules.datalabel.useranalysis.autoSearch = false;
				}
			} else {
				if (type === 'instant') {
					common.utils.showSucMsg("当前查询方式已修改为“主动查询”！");
					modules.datalabel.useranalysis.autoSearch = true;
				}
			}
			$('#'+modules.datalabel.useranalysis.settingWinId).dialog('close');
		},
		openSettingWin:function(){
			$('#'+modules.datalabel.useranalysis.settingWinId).dialog('open');
			$("#"+modules.datalabel.useranalysis.settingWinId).panel("move",{top:$(document).scrollTop()});	
			$('#'+modules.datalabel.useranalysis.settingWinId).dialog('resize');
		},
		closeSettingWin:function(){
			$('#'+modules.datalabel.useranalysis.settingWinId).dialog('close');
		},
		/*添加默认时间周期提示*/
		tipDefaultTime:function() {
			this.id = 'last_stat_btn';

			var content = "";
			if(modules.datalabel.useranalysis.defaultTime.isEmpty()){
				content = "<span style='color: red;'>无法读取数据表的数据周期</span>";
			}else{
				
				var m_table = "TA_TARGET_CUSTOMER_M";
				var d_table = "TA_TARGET_CUSTOMER_D";
				var m_time_value = "无法读取";
				var d_time_value = "无法读取";
				
				if(common.utils.isDefined(modules.datalabel.useranalysis.defaultTime.get(d_table)))
					d_time_value = modules.datalabel.useranalysis.defaultTime.get(d_table).timeValue;
				if(common.utils.isDefined(modules.datalabel.useranalysis.defaultTime.get(m_table)))
					m_time_value = modules.datalabel.useranalysis.defaultTime.get(m_table).timeValue;
				
				content = "<span>" + "数据标签树默认统计周期:<br/>日周期：<b>"+d_time_value+"</b><br/>"
						 +"月周期：<b>"+m_time_value+"</b></span>";
			}			
			layer.tips(content,'#last_stat_btn',{tips: 3,time: 3000});
		}
		
	}
};