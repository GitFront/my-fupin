$.namespace("modules.smartquery");

/**
 * 智能查询页面控制类js
 * @type 
 */
modules.smartquery.query = {
	/*定义查询页面静态参数*/
	sqId:'',
	sqName:'',
	mpTableId:'',
	sqTable:'',
	isTemp:'',
	tableNames:[],
	maxFileSize:60000,
	/*智能查询信息对象*/
	query:{},
	/*数据展示工具栏*/
	dataGridToolbar:{},	
	/*显示列选择器*/
	columnsSelecter:{},
	/*过滤条件编辑器*/
	filterTable:{},
	/*排序条件编辑器*/
	sortTable:{},
	/*显示列选择器*/
	shareSelecter:{},
	/*初始化页面信息*/
	init:function(queryinfo){
		/*初始化参数信息*/
		this.query = queryinfo;
		this.sqId = queryinfo.id;
		this.sqName = queryinfo.name;
		this.mpTableId = queryinfo.mpTableId;
		this.sqTable = queryinfo.sqTable;
		this.isTemp = queryinfo.isTemp;
		this.tableNames = queryinfo.tableNames;
		/*绑定按钮操作定义*/
		$('#run_config_btn').bind('click', this.runQuery); 
		$('#save_config_win_btn').bind('click', this.windows.openSaveConfig); 
		$('#save_config_btn').bind('click', this.saveConfig);		
		$('#filter_edit_btn').bind('click', this.filter.saveOrUpdateFilter);
		$('#choose_personal_catalog_btn').bind('click', this.catalogTree.usePath); 
		$('#save_data_btn').bind('click', this.saveData); 
		$('#isOpen').bind('click', this.buttons.timerSwitch); 
		$('#isShare').bind('click', this.buttons.shareSwitch); 
		$('#personal_catalog_btn').bind('click',{catalogId:"personalCatalogId"},this.windows.openEditCatalogTree); 
		$('#personal_catalog_btn2').bind('click', {catalogId:"personalCatalogId2"},this.windows.openEditCatalogTree);
		$('#personal_catalog_btn3').bind('click', {catalogId:"personalCatalogId3"},this.windows.openEditCatalogTree);
		
		$('#add_list_filter_btn').bind('click', modules.smartquery.listfilter.list.newList);
		$('#create_task_btn').bind('click', this.exports.createTaskFile); 
		$('#user_info_search_btn').bind('click',this.dataShare.searchUserInfo);
		
		$('#close_save_config_btn').bind('click', this.windows.closeSaveConfig); 
		$('#close_save_data_btn').bind('click',this.windows.closeSaveData);
		$('#close_filter_edit_btn').bind('click',this.windows.closeFilterEdit);
		$('#close_personal_catalog_btn').bind('click',this.windows.closeEditCatalogTree);
		
		$('#create_report_btn').bind('click', this.reportConfig.createReportChart);
		$('#switch_btn_Bars').bind('click', {type:"Bars"}, this.reportConfig.switchReportChart);
		$('#switch_btn_StackBars').bind('click', {type:"StackBars"}, this.reportConfig.switchReportChart);
		$('#switch_btn_Line').bind('click', {type:"Line"}, this.reportConfig.switchReportChart);
		$('#switch_btn_Pie').bind('click', {type:"Pie"}, this.reportConfig.switchReportChart);
		$('#switch_btn_TimeSeriesLine').bind('click', {type:"TimeSeriesLine"}, this.reportConfig.switchReportChart);
		
		/*初始化工具栏信息*/
		this.dataGridToolbar = [{
			text:'导出Csv文件',
			iconCls:'icon-btn-download',
			handler:this.exports.cvsExport
		},"-",{
			text:'保存数据',
			iconCls:'icon-btn-hdd',
			handler:this.windows.openSaveData
		},"-",{
			text:'切换图表',
			iconCls:'icon-btn-picture',
			handler:this.windows.openConfigReport
		}];
		
		/*初始化页面数据*/
		this.initTableZoom();
		this.initForm();
		this.columns.initColumnSelecter();
		this.filter.initFilterTable();
		this.sorts.initSortTable();
		this.catalogTree.initPersonalCatalog();
		this.dataShare.initShareSelecter();
		modules.smartquery.listfilter.initForQuery();
		
	},	
	/**
	 * 初始化宽表展示区域
	 */
	initTableZoom:function(){
		/*控件ID集合*/
		var html = "";
		for (var index = 0; index < this.tableNames.length; index++) {
			html += "<span class='domBtn'>"+this.tableNames[index]+"</span>"
		}
		$("#tableZoom").append(html);
	},
	/**
	 * 初始化表单数据
	 */
	initForm:function(){
			/* 配置保存表单 */
			$("#name").textbox('setValue',this.query.name);
			$("#fileName").textbox('setValue',this.query.fileName);
			$("#dataStoreDate").numberspinner('setValue',this.query.dataStoreDate); 
			$("#personalCatalogId").combotree('setValue',this.query.personalCatalogId);
			$("#validDate").datebox('setValue',this.query.validDate);
			$("#invalidDate").datebox('setValue',this.query.invalidDate);
			$("#cycType").combobox('setValue',this.query.cycType);
			$("#cycLen").numberspinner('setValue',this.query.cycLen); 
			if(this.query.configType === '0'){
				/* 手动任务 */
				$("#isOpen").linkbutton('unselect');
				$("#timer").hide();	 
			}else{
				$("#isOpen").linkbutton('select');
				$("#timer").show();	 
			}
			$('#saveConfigWin').dialog('resize');			
	},
	/**
	 * 运行查询操作
	 */
	runQuery:function(){
			common.utils.showLoading();
			/* 验证用户选择的查询参数是否完整（是否含有显示 列） */
			var url = ctx + "/a/smart-query/query/smartquery/valid";
			var params = {id:modules.smartquery.query.sqId,isTemp:modules.smartquery.query.isTemp};
			var validConfig = Boolean($.ajax({ url: url,data: params, async: false }).responseText);
			if(!validConfig) {
				common.utils.closeLoading();
				common.utils.showErrorMsg("不存在对应的数据显示列，请选择相关的显示列后再运行查询操作！");				
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
	             	common.utils.closeLoading();
	             	if(rs){
	             		/* 如果存在，则先关闭列过滤器 */
	             		/* if($("#dataDiv").is(":visible"))
	        				$("#dataTable").datagrid("disableFilter"); */
	             		/* 生成动态数据列表 */
	             		modules.smartquery.query.createDatagrid(rs);
	             	}else{
	    				common.utils.showErrorMsg("初始化查询数据失败，请稍候再试！");		    				
	             	}
	             }
	         });					
		},
	/**
	 * 生成动态数据列表方法
	 * @param {} columns 需要传入列定义配置信息
	 */
	createDatagrid:function(columns){
			
			$("#data_win").window({title:"“"+modules.smartquery.query.sqName+"” 数据展示"});
			$("#data_win").window('open');
			var h = $(window).height()-36;
			var pageSize = common.utils.getGridPage(h);
			
			$("#dataTable").datagrid({
				singleSelect:true,
				height:h,
				rownumbers:true,
				url:ctx + "/a/smart-query/query/smartquery/datalist",
				queryParams:{id:modules.smartquery.query.sqId,isTemp:modules.smartquery.query.isTemp},
				fitColumns:true,
				loadMsg:'数据加载中...',
				pagination:true,
				pageSize:pageSize,
				pageList:[10,15,20,25,30,35,40,45,50],
				border:true,
				toolbar:modules.smartquery.query.dataGridToolbar,
		        idField: 'id',
		        columns:[columns]  
			});			
		},
	/**
	 * 保存查询配置信息操作
	 */
	saveConfig:function(){
			common.utils.showLoading();
			var smartQuery = modules.smartquery.query;
			/* 获取表单判断数据 */
			var name = $("#name").val();
			var isOpen = $("#isOpen").linkbutton('options').selected;
			
			if(common.utils.isEmpty(name)){
				common.utils.closeLoading();
				common.utils.showErrorMsg("配置名称不能为空！");
				return;
			}
			
			/* 验证查询配置名称是否唯一 */
			var url = ctx + "/a/smart-query/query/smartquery/valid/name";
			
			var validName = Boolean($.ajax({ url: url,data: {id:smartQuery.sqId,name:name}, type: "POST", async: false }).responseText);
			if(!validName) {
				common.utils.closeLoading();
				common.utils.showErrorMsg("配置名称已存在，请使用其它名称对此查询配置进行标识！");
				return;
			}
			
			$("#saveConfig").form('submit', {    
			    url:ctx + "/a/smart-query/query/config/saveorupdate",    
			    onSubmit: function(params){   
			    	var isValid = $(this).form('validate');
					if (isOpen && !isValid){
						common.utils.closeLoading();//如果表单是无效的则隐藏进度条
						common.utils.showErrorMsg("请将查询配置信息的必填项补充完整！");
						return isValid
					}
					/* 提交额外的参数 */
					params.id = smartQuery.sqId;    
					params.name = name;
					params.configType = isOpen?"1":"0";
					params.mpTableId = smartQuery.mpTableId;
					params.sqTable = smartQuery.sqTable;
					params.isTemp = smartQuery.isTemp;
					return true;			          
			    },
				success: function(rs){
					common.utils.closeLoading();
					var data = $.parseJSON(rs);
					if(data.flag){
						smartQuery.sqName = name;
						smartQuery.isTemp = "0";
						smartQuery.columns.reloadColumnSelecter();
						$("#filterTable").datagrid('reload');
						$("#sortTable").datagrid('reload');
						parent.window.modules.smartquery.main.queryList.reloadQueryList();
						$('#saveConfigWin').dialog('close');
						common.utils.showSucMsg("查询配置保存成功！");
					}else{
						common.utils.showErrorMsg("查询配置保存失败，请稍候再试！");
					}					
				}    
			}); 
		},
	/**
	 * 保存数据文件操作
	 */
	saveData:function(){
			var smartQuery = modules.smartquery.query;
			var isValid;
			var url = ctx + "/a/smart-query/query/queryfile/save";		
			$("#saveFileData").form('submit', {    
			    url:url,    
			    onSubmit: function(params){
			    	isValid = $(this).form('validate');
					if (!isValid){
						common.utils.closeLoading();//如果表单是无效的则隐藏进度条
						common.utils.showErrorMsg("保存文件名或保存目录信息不能为空！");
						return isValid
					}
					
					/* 提交额外的参数 */
					params.id = smartQuery.sqId;
					params.isTemp = smartQuery.isTemp;
					params.userids = smartQuery.dataShare.shared;
					params.saveType='save';
					return true;			          
			    }    
			});
			if(isValid){
				modules.smartquery.query.windows.closeSaveData();
				common.utils.showSucMsg("保存数据操作已提交至后台任务调度，请稍候到数据超市查询并下载相关数据文件！");
			}
			
		},
	
	/*个人目录编辑器*/
	catalogTree:{
		/*当前个人目录选择对象*/
		currentCatalogId:'',
		/*个人目录编辑器*/
		personalTree:{},
		/**
		 * 获取ztree的节点全路径节点名称信息
		 * @param {} treeNode
		 * @return {String}
		 */
		getNodePath:function(treeNode){
			if(treeNode === null) return "";
			var nodename = treeNode.name;
			var pNode = treeNode.getParentNode();
			if(pNode!=null){
				nodename = modules.smartquery.query.catalogTree.getNodePath(pNode) +"/"+ nodename;
			}
			return nodename;
		},
		/**
		 * 获取ztree的节点全路径节点ID信息
		 * @param {} treeNode
		 * @return {String}
		 */
		getNodePathId:function(treeNode){
			if(treeNode === null) return "";
			var nodeid = treeNode.id;
			var pNode = treeNode.getParentNode();
			if(pNode!=null){
				nodeid = modules.smartquery.query.catalogTree.getNodePathId(pNode) +"/"+ nodeid;
			}
			return nodeid;
		},
		/**
		 * 初始化个人目录编辑器
		 */
		initPersonalCatalog:function(){
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
			this.personalTree = new zEditTree("pathTree",[],{},callback,opt);
		},
		/**
		 * 重新加载个人目录树的数据
		 */
		reloadPersonalCatalog:function(){
			this.personalTree.asynReload();
		},
		/**
		 * 选取对应的个人路径
		 */
		usePath:function(){
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
			$('#personalCatalogId2').combotree('reload'); 	
			$('#'+modules.smartquery.query.catalogTree.currentCatalogId).combotree('setValue',selected);
			$('#editPathWin').dialog('close');
		}
		
	},
	/*数据分享编辑器*/
	dataShare:{
		shared:new Array(),
		userinfo:"",
		/**
		 * 初始化数据分享列表
		 */
		initShareSelecter:function(){
			/*注册查询框回车事件*/
			$('#userinfo').textbox('textbox').keydown(function (e) {
                if (e.keyCode == 13) {
                   modules.smartquery.query.dataShare.searchUserInfo();
                }
            });	
			
			var noselectuser = {
				title:"未授权用户",
				method:'post',
				striped:true,
				loadMsg:'数据加载中...',
				pagination:true,
				pageSize:5,
				pageList:[5,10,20,30,40,50],
		    	/*url: ctx + '/a/smart-query/query/share/userlist',
		    	queryParams:{userids:this.shared.join(",")},*/
				data:[],
		    	idField:'id',
		    	toolbar:"#user_search_tb",
		    	columns:[
		    	   {field : 'id',hidden:true}, 
				   {field : 'userAccount',title : '账号',width : '120'}, 
				   {field : 'name',title : '用户名',width : '100'}, 
				   {field : 'departmentPath',title : '所在机构',width : '80'}]
			};
			
			var hasselectuser = {
					title:"已授权用户",
					method:'post',
					striped:true,
			    	data:[],
			    	idField:'id',
			    	columns:[
					   	{field : 'id',hidden:true}, 
					    {field : 'userAccount',title : '账号',width : '120'}, 
					    {field : 'name',title : '用户名',width : '100'}, 
					    {field : 'departmentPath',title : '所在机构',width : '80'}]
			};
			/*自定义用户分页栏*/
			var customPage = {
				pageSize:5,
				layout:['first','prev','next','last','sep','refresh'],
				displayMsg: ''
			};
			modules.smartquery.query.shareSelecter = new TwoWindowsSelecter(
								"share_usr",
								{title:"数据文件分享",width:700,height:245,pagination:customPage},
								noselectuser,
								hasselectuser,
								{moveIn:"shareMoveIn",moveOut:"shareMoveOut"});	
								
			
			$("#sharer").hide();
			$('#saveDataWin').dialog('close');
		},
		
		/**
		 * 加载分享用户选择器
		 */
		loadShareSelecter:function(){
			var url = ctx + '/a/smart-query/query/share/userlist';
			modules.smartquery.query.shareSelecter.loadLeft(url,{
							userids:modules.smartquery.query.dataShare.shared.join(","),
							userinfo:modules.smartquery.query.dataShare.userinfo});
		},
		/**
		 * 查询用户列表
		 */
		searchUserInfo:function(){
			var userinfo = $('#userinfo').val();
			modules.smartquery.query.dataShare.userinfo = userinfo;
			modules.smartquery.query.shareSelecter.reloadLeft({userids:this.shared.join(","),userinfo:this.userinfo});			
		},
		
		/**
		 * 重新加载分享用户选择器
		 */
		reloadShareSelecter:function(){
			modules.smartquery.query.shareSelecter.reloadLeft({userids:this.shared.join(","),userinfo:this.userinfo});
			modules.smartquery.query.shareSelecter.cleanRight();
		},
		/**
		 * 重置分享窗口控件数据
		 */
		resetShareWin:function(){
			$("#savefileName").textbox('setValue',"");
			$('#personalCatalogId2').combotree('setValue',"");
			this.shared = new Array();
			this.reloadShareSelecter();
			$("#isShare").linkbutton('unselect');
			$("#sharer").hide();
			$('#saveDataWin').dialog('resize');
		}
	},	
	/*图表切换编辑器*/
	reportConfig:{
		/*图表ID值*/
		reportid:"",
		/*图表数据*/
		reportData:[],
		/*当前图表类型*/
		switchBtn:"",
		groupField:"",
		nameField:"",
		valueField:"",
		/**
		 * 初始化维表选择器
		 */
		initSelecter:function(){
			var sqId = modules.smartquery.query.sqId;
			var isTemp = modules.smartquery.query.isTemp;
			modules.smartquery.query.utils.initColumnPicker("reportDim",ctx + '/a/smart-query/query/sqselect/report/dim?sqId='+sqId+'&isTemp='+isTemp);
			modules.smartquery.query.utils.initColumnPicker("reportAmount",ctx + '/a/smart-query/query/sqselect/report/amount?sqId='+sqId+'&isTemp='+isTemp);
			modules.smartquery.query.utils.initColumnPicker("reportGroup",ctx + '/a/smart-query/query/sqselect/report/group?sqId='+sqId+'&isTemp='+isTemp,false);		
		},
		
		/**
		 * 通过所选参数生成图表信息
		 */
		createReportChart:function(){
			common.utils.showLoading();
			/*判断参数是否合法*/
			var isValid = $("#reportConfig").form('validate');
			if (!isValid){
				common.utils.showErrorMsg("图表维表或图表数值参数不能为空！");
				return;
			}
			$("#switchDiv").show();
			var type = "Bars";/*默认显示类型*/
			/*初始化控件参数*/
			modules.smartquery.query.reportConfig.groupField = $("#reportGroup").combobox("getValue");
			modules.smartquery.query.reportConfig.nameField = $("#reportDim").combobox("getValue");
			modules.smartquery.query.reportConfig.valueField = $("#reportAmount").combobox("getValue");
				 
			/* 后台统计报表数据 */
			var url = ctx + "/a/smart-query/query/report/statistic/group";
			var params = {
				sqId:modules.smartquery.query.sqId,
				isTemp:modules.smartquery.query.isTemp,
				name:modules.smartquery.query.reportConfig.nameField,
				value:modules.smartquery.query.reportConfig.valueField,
				group:modules.smartquery.query.reportConfig.groupField
			};
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	             	if(modules.smartquery.query.reportConfig.reportid === ""){
						modules.smartquery.query.reportConfig.reportid =  Highchart.create.init("dataChart",rs,{yDecimal:2,isPercent:true},type);
					}else{
						Highchart.reload(modules.smartquery.query.reportConfig.reportid, rs, {yDecimal:2,isPercent:true}, type);
					}
					modules.smartquery.query.reportConfig.reportData = rs;
					modules.smartquery.query.reportConfig.switchBtn = type;
					$("#switch_btn_"+type).linkbutton({selected:true});
					common.utils.closeLoading();
	             }
	         });		
		},
		/**
		 * 切换当前图表类型
		 * @param {} type 图表类型
		 */
		switchReportChart:function(e){
			if(common.utils.isEmpty(modules.smartquery.query.reportConfig.reportid)) return;
			var type = e.data.type;
			common.utils.showLoading();
			var url;
			/*后台传递参数*/
			var params = {
				sqId:modules.smartquery.query.sqId,
				isTemp:modules.smartquery.query.isTemp,
				name:modules.smartquery.query.reportConfig.nameField,
				value:modules.smartquery.query.reportConfig.valueField,
				group:modules.smartquery.query.reportConfig.groupField
			};
			if(type !== 'Pie' && modules.smartquery.query.reportConfig.switchBtn === 'Pie'){
				/*如果是饼状图切换为分组图表，则需要重新由后台做数据统计*/
				url = ctx + "/a/smart-query/query/report/statistic/group";
				$.ajax({
		             type: "POST",
		             url: url,
		             data: params,
		             dataType: "json",
		             success: function(rs){
		             	Highchart.reload(modules.smartquery.query.reportConfig.reportid, rs, {yDecimal:2,isPercent:true}, type);
		             	modules.smartquery.query.reportConfig.reportData = rs;
		             	common.utils.closeLoading();
		             }
		         });
			}else if(type === 'Pie' && modules.smartquery.query.reportConfig.switchBtn !== 'Pie'){
				/*如果是分组图表切换为饼状图，则需要重新由后台做数据统计*/
				url = ctx + "/a/smart-query/query/report/statistic/pie";
				$.ajax({
		             type: "POST",
		             url: url,
		             data: params,
		             dataType: "json",
		             success: function(rs){
		             	Highchart.reload(modules.smartquery.query.reportConfig.reportid, rs, {yDecimal:2,isPercent:true}, type);
		             	modules.smartquery.query.reportConfig.reportData = rs;
		             	common.utils.closeLoading();
		             }
		         });
			}else{
				/*如果是分组图表之间的切换，则直接使用缓存数据*/
				Highchart.reload(modules.smartquery.query.reportConfig.reportid, modules.smartquery.query.reportConfig.reportData, {yDecimal:2,isPercent:true}, type);
				common.utils.closeLoading();
			}
			/*设置当前图表类型*/
			modules.smartquery.query.reportConfig.switchBtn = type;
		}
	},
	/*显示列编辑器*/
	columns:{
		/**
		 * 初始化显示列选择器
		 */
		initColumnSelecter:function(){
			/* 显示列配置 */
			var noselectcol = {
					title:"未选择显示列",
					rownumbers:true,
					striped:true,
					loadMsg:'数据加载中...',
					url:ctx + "/a/smart-query/query/sqselect/list/withoutselect",	
					queryParams:{
						sqId:modules.smartquery.query.sqId,
						isTemp:modules.smartquery.query.isTemp},	    	
			    	idField:'id',
			    	columns : [
			    		{field : 'id',hidden:true},
			    		{field : 'columnType',hidden:true},
			    		{field : 'sqColumn',hidden:true},
			    		{field : 'tableMappingName',title : '表映射名',width:'40%'},
			    		{field : 'mappingName',title : '列映射名',width:'40%'},
			    		{field : 'columnTypeName',title : '类型',align : "center",width:'10%'}]
			};
			
			var hasselectcol = {
					title:"已选择显示列",
					rownumbers:true,
					resizeWidth:true,
					striped:true,
					url:ctx + "/a/smart-query/query/sqselect/list",	
					queryParams:{
						sqId:modules.smartquery.query.sqId,
						isTemp:modules.smartquery.query.isTemp},	    	
			    	idField:'id',
			    	columns : [
			    		{field : 'id',hidden:true},
			    		{field : 'columnType',hidden:true},
			    		{field : 'sqColumn',hidden:true}, 
			    		{field : 'tableMappingName',title : '表映射名',width:'40%'}, 
			    		{field : 'sqColumnName',title : '列映射名',width:'40%'}, 
			    		{field : 'columnTypeName',title : '类型',align : "center",width:'10%'} ]
			};
			
			modules.smartquery.query.columnsSelecter = new TwoWindowsSelecter(
						"columnsSelecter",
						{title:"查询显示列配置",width:'98.5%',height:250,fit:false,fitColumns:true},
						noselectcol,
						hasselectcol,
						{moveIn:"selectMoveIn",moveOut:"selectMoveOut"}
			);
			
		},
		/**
		 * 重载显示列选择器数据
		 */
		reloadColumnSelecter:function(){
			modules.smartquery.query.columnsSelecter.reload(
				{sqId:modules.smartquery.query.sqId,
				 mpTableId:modules.smartquery.query.mpTableId,
				 isTemp:modules.smartquery.query.isTemp}, 
				{sqId:modules.smartquery.query.sqId,
				 isTemp:modules.smartquery.query.isTemp});
		}
	},	
	/*查询条件编辑器*/
	filter:{
		sortNum:1,
		/* 保存或更新条件过滤列信息 */
		saveOrUpdateFilter:function(){
			common.utils.showLoading();
			
			$("#saveFilterInfo").form('submit', {    
			    url:ctx + "/a/smart-query/query/sqfilter/saveorupdate",
			    onSubmit: function(params){   
			    	var isValid = $(this).form('validate');
					if (!isValid){
						common.utils.closeLoading();//如果表单是无效的则隐藏进度条
						common.utils.showErrorMsg("请将查询条件配置信息的必填项补充完整！");
						return isValid
					}
					/* 提交额外的参数 */
					params.sqId = modules.smartquery.query.sqId;    
					params.isTemp = modules.smartquery.query.isTemp;
					return true;			          
			    },
				success: function(rs){
					common.utils.closeLoading();
					var data = $.parseJSON(rs);
					if(data.flag){
						$("#filterTable").datagrid('reload');
						modules.smartquery.query.windows.closeFilterEdit();
					}else{
						common.utils.showErrorMsg("查询条件配置保存失败，请稍候再试！");
					}					
				}    
			});  	
		},
		/**
		 * 初始化过滤条件编辑窗口
		 */
		initEditWindow:function(){
			
			$("#filterType").combobox({onSelect:this.switchFilterType});
			$("#monthCycType").combobox({onSelect:this.switchMonthCycType});
			$("#dateCycType").combobox({onSelect:this.switchDateCycType});
			modules.smartquery.query.filter.initListFilter();
			modules.smartquery.query.utils.initColumnPicker(
					"mpColumnId",
					ctx+"/a/smart-query/query/columns/select?tableids="+modules.smartquery.query.mpTableId,
					true,
					modules.smartquery.query.filter.switchColumnType);
					
		},
		/**
		 * 初始化过滤条件编辑器
		 */
		initFilterTable:function(){
			this.initEditWindow();
			modules.smartquery.query.filterTable = $("#filterTable").datagrid({
			 	striped : true,
				rownumbers : true,
				singleSelect:true,
				loadMsg:'正在加载数据...',
		        collapsible: true,
		        url:ctx + "/a/smart-query/query/sqfilter/list",
				queryParams:{sqId:modules.smartquery.query.sqId,isTemp:modules.smartquery.query.isTemp},		        
		        idField: 'id',
		        columns: [[
		         { field: 'id',hidden:true },		         
		         { field: 'relationName', halign:'center',align:'center',title: '条件关系', width: '10%'},
		         { field: 'sqTableName', title: '映射表名',halign:'center', width: '25%'},
		         { field: 'sqColumnName', title: '映射列名',halign:'center', width: '20%'},
		         { field: 'formulaName', title: '匹配公式', halign:'center',align:'center',width: '10%'},
		         { field: 'valueDesc', title: '匹配值',halign:'center', width: '20%'},
		         { field: 'sort', title: '排序',halign:'center',align:'center', width: '10%'}
		        ]],
		        toolbar: [{
		            text: '添加查询条件', iconCls: 'icon-btn-plus-sign', handler: this.addFilterRow
		        }, '-', {
		            text: '删除查询条件', iconCls: 'icon-btn-remove-sign', handler: this.deleteFilterRows
		        }],
		        onDblClickRow: this.editFilterRow
		  });
		},
		/*初始化列表过滤器的选择器*/
		initListFilter:function(){
			$('#lvalue').combogrid({
				url:ctx+"/a/smart-query/listFilter/list/own",
			    idField:'id',textField:'listName', panelWidth:350,
			    filter: function(q, row){
					var opts = $(this).combogrid('options');
					return row[opts.textField].indexOf(q) == 0;
				},
			    columns:[[  
					{field:'listName',title:'列表名称',width:180},  
					{field:'listNum',title:'条目数量',width:60},  
					{field:'createTime',title:'创建时间',width:100},
					{field:'id',hidden:true}
				]]});
		},
		
		/**
		 * 过滤器表单加载
		 * @param {} filter
		 */
		loadFilterForm:function(filter){
			modules.smartquery.query.filter.loadFilterEdit(filter);
			
			$("#saveFilterInfo").form('load',filter);
		},
		/**
		 * 根据过滤器类型与列类型刷新列可选项、匹配公式可选项
		 * @param {} filterType
		 * @param {} columnType
		 * @param {} iscolumn
		 */
		reloadFilterSelecter:function(filterType,columnType,iscolumn){
			if(!common.utils.isDefined(iscolumn)) iscolumn=true;
			var column_url = ctx+"/a/smart-query/query/columns/select?tableids="+modules.smartquery.query.mpTableId;
			var formula_url = ctx + '/a/smart-query/query/dimension/filterformula/list';
			if(filterType === 'cyc'){
				column_url += "&type=d";
				formula_url += '?type=d';
			}else if(filterType === 'list'){
				formula_url += '?type=l';
			}else{
				if(!common.utils.isEmpty(columnType))
					formula_url += '?type='+columnType;
			}
			/*刷新可选数据列*/
			if(iscolumn){
				$('#mpColumnId').combogrid('grid').datagrid('reload',column_url);				
				$('#mpColumnId').combogrid('setValue', '');
			}
			/*刷新匹配公式*/
			$("#formulaId").combobox('reload',formula_url);
			if(filterType === 'list'){
				$("#formulaId").combobox('setValue','7');
			}else{
				$("#formulaId").combobox('setValue','1');
			}			
			
		},
		
		/*根据过滤器属性加载页面元素*/
		loadFilterEdit:function(filter){
			var filterType = filter.filterType;
			var sqColumnType = filter.sqColumnType;
			var monthCycType = filter.monthCycType;
			var dateCycType = filter.dateCycType;
			var value = filter.value;
			if(filterType === 'normal'){
				$("#cycDiv").hide();
				$("#listDiv").hide();
				switch(sqColumnType)
				{
					case 'n':
					  	/*数字类型列*/
						$("#stringDiv").hide();
						$("#numberDiv").show();
						$("#dateDiv").hide();
						filter["svalue"]='';
						filter["nvalue"]=value;
						filter["dvalue"]='';
						break;
					case 'd':
					  	/*日期类型列*/
						$("#stringDiv").hide();
						$("#numberDiv").hide();
						$("#dateDiv").show();
						filter["svalue"]='';
						filter["nvalue"]=0;
						filter["dvalue"]=value;
					  	break;
					default:
						/*其它类型列*/
						$("#stringDiv").show();
						$("#numberDiv").hide();
						$("#dateDiv").hide();
						filter["svalue"]=value;
						filter["nvalue"]=0;
						filter["dvalue"]='';
				}				
			}else if(filterType === 'cyc'){
				$("#listDiv").hide();
				$("#stringDiv").hide();
				$("#numberDiv").hide();
				$("#dateDiv").hide();
				filter["svalue"]='';
				filter["nvalue"]=0;
				filter["dvalue"]='';
				
				$("#cycDiv").show();
			}else{
				$("#cycDiv").hide();
				$("#stringDiv").hide();
				$("#numberDiv").hide();
				$("#dateDiv").hide();
				filter["svalue"]='';
				filter["nvalue"]=0;
				filter["dvalue"]='';
				
				$("#listDiv").show();
			}			
			/*周期值显示设置*/
			this.switchMonthCycType({value:monthCycType});
			this.switchDateCycType({value:dateCycType});
			/*根据过滤器类型与列类型刷新列可选项、匹配公式可选项*/
			this.reloadFilterSelecter(filterType,sqColumnType);
			
		},
		/**
		 * 切换月周期类型操作
		 * @param {} index 数据选择索引
		 * @param {} column 数据对象
		 */
		switchMonthCycType:function(record){
			switch(record.value){
				case 'custom':
					$("#customMonthCycDiv").show();	
				  	break;
				default:
					$("#customMonthCycDiv").hide();	
			}			
		},
		/**
		 * 切换日周期类型操作
		 * @param {} index 数据选择索引
		 * @param {} column 数据对象
		 */
		switchDateCycType:function(record){
			switch(record.value){
				case 'custom':
					$("#customDateCycDiv").show();
				  	break;
				default:
					$("#customDateCycDiv").hide();	
			}			
		},
		
		/*上传列表文件*/
		uploadList:function(){
			
		},		
		/**
		 * 列类型切换操作
		 * @param {} index 数据选择索引
		 * @param {} column 数据对象
		 */
		switchFilterType:function(record){
			var filterType = record.value;
			/*根据字段类型刷新匹配值*/
			switch(filterType){
				case 'cyc':
					$("#stringDiv").hide();
					$("#numberDiv").hide();
					$("#dateDiv").hide();
					$("#cycDiv").show();
					$("#listDiv").hide();
				  	break;
				case 'list':
					$("#stringDiv").hide();
					$("#numberDiv").hide();
					$("#dateDiv").hide();
					$("#cycDiv").hide();
					$("#listDiv").show();	
				  	break;
				default:
					$("#numberDiv").hide();
					$("#dateDiv").hide();
					$("#cycDiv").hide();
					$("#listDiv").hide();	
					$("#stringDiv").show();
			}
			/*根据过滤器类型与列类型刷新列可选项、匹配公式可选项*/
			modules.smartquery.query.filter.reloadFilterSelecter(filterType);
		},
		/**
		 * 列类型切换操作
		 * @param {} index 数据列选择索引
		 * @param {} column 数据列对象
		 */
		switchColumnType:function(index,column){
			if(!common.utils.isJson(column)) return;
			var columnType = column.columnType;
			$("#sqColumnType").val(columnType);
			var filterType = $("#filterType").combobox('getValue');
			if(filterType === 'cyc' || filterType === 'list') return;
			/*根据字段类型刷新匹配公式*/
			modules.smartquery.query.filter.reloadFilterSelecter(filterType,columnType,false);
			/*根据字段类型刷新匹配值*/
			switch(columnType)
			{
				case 'n':
				  	/*数字类型列*/
					$("#stringDiv").hide();
					$("#numberDiv").show();
					$("#dateDiv").hide();
					break;
				case 'd':
				  	/*日期类型列*/
					$("#stringDiv").hide();
					$("#numberDiv").hide();
					$("#dateDiv").show();
				  	break;
				default:
					/*其它类型列*/
					$("#stringDiv").show();
					$("#dateDiv").hide();
					$("#numberDiv").hide();
			}
		},
		/**
		 * 添加过滤条件信息
		 */
		addFilterRow:function(){
			/* 动态生成ID */
		   	var id = common.utils.guid();
		    var newRow = {id:id,filterType:'normal',relationId:'1',mpColumnId:'',sqColumnType:'s',
		    			formulaId:'1',value:'',monthCycType:'onMonth',monthCycValue:2,
		    			dateCycType:'monthStart',dateCycValue:1,sort:modules.smartquery.query.filter.sortNum};
		    modules.smartquery.query.windows.openFilterEdit();
		    modules.smartquery.query.filter.loadFilterForm(newRow);
		    modules.smartquery.query.filter.sortNum++;
		},
		/**
		 * 编辑过滤条件信息
		 */
		editFilterRow:function(rowIndex, rowData){
			/*初始化编辑窗口*/
			modules.smartquery.query.filter.initEditWindow();
			var url = ctx + "/a/smart-query/query/sqfilter/find";
		    var params = {id:rowData.id,isTemp:modules.smartquery.query.isTemp};
		    /* 异步请求操作 */
		    $.ajax({
		         type: "POST",
		         url: url,
		         data: params,
		         dataType: "json",
		         traditional:true,
		         success: function(rs){
			         if(!rs){
			            common.utils.showErrorMsg("查询条件编辑失败，请稍候再试！");
			         }
			         modules.smartquery.query.windows.openFilterEdit();
			         modules.smartquery.query.filter.loadFilterForm(rs);
		     	 }
		     });
		},
		/**
		 * 删除过滤条件信息
		 */
		deleteFilterRows:function(){
		    var row = $("#filterTable").datagrid('getSelections');
		    if(row.length === 0){
		        common.utils.showErrorMsg("请选择需要删除的查询条件！");
		        return;
		    }
		    var url = ctx + "/a/smart-query/query/sqfilter/remove";
		    var params = {id:row[0].id,isTemp:modules.smartquery.query.isTemp};
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
	},	
	/*排序编辑器*/
	sorts:{
		sortEditRow:'',
		sortTypeFormatter:function(value, rowData, rowIndex){
			var url =  ctx + "/a/smart-query/query/dimension/sorttype/name?id="+value;
			return $.ajax({ url: url, async: false }).responseText; 
		},
		
		/* 保存或更新条件过滤列信息 */
		saveOrUpdateSorter:function(rowData){
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
			var params = {
				sqId:modules.smartquery.query.sqId,
				isTemp:modules.smartquery.query.isTemp,
				sroter:JSON.stringify(rowData)
			};
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
		},
		/**
		 * 初始化排序编辑器
		 */
		initSortTable:function(){
			modules.smartquery.query.sortTable = $("#sortTable").datagrid({
			 	striped : true,
				rownumbers : true,
				singleSelect:true,
				loadMsg:'正在加载数据...',
		        collapsible: true,
		        collapsed:true,
		        url:ctx + "/a/smart-query/query/sqsort/list",
				queryParams:{sqId:modules.smartquery.query.sqId,isTemp:modules.smartquery.query.isTemp},		        
		        idField: 'id',
		        columns: [[
		  		{field:'checkbox',checkbox:true},
		        { field: 'id',hidden:true },
		        { field: 'mpColumnId', title: '数据字段',halign:'center', width: 180, formatter: modules.smartquery.query.utils.columnFormatter, 
		        	 editor: { type:'combogrid', align:'center',
		        		 options: {url:ctx+"/a/smart-query/query/columns/select?tableids="+modules.smartquery.query.mpTableId,
		        		 	idField:'id',textField:'mappingName', panelWidth:430,
		        		 	filter: function(q, row){
								var opts = $(this).combogrid('options');
								return row[opts.textField].indexOf(q) == 0;
							},
		        		 	columns:[[  
						        {field:'tableMappingName',title:'映射表名',width:180},  
						        {field:'mappingName',title:'映射列名',width:150},  
						        {field:'columnTypeName',title:'类型',width:80},  
						        {field:'id',hidden:true}
						    ]], required:true }}},		        	 
		         { field: 'sortTypeId', title: '排序类型', halign:'center',align:'center',width: 150, formatter: this.sortTypeFormatter,
		            	editor: { type:'combobox', align:'center', 
		            		options: {url:ctx+"/a/smart-query/query/dimension/sorttype/list",editable:false, valueField: "id", textField: "name",required:true }}}
		        ]],
		        toolbar: [{
		            text: '添加排序方式', iconCls: 'icon-btn-plus-sign', handler: function () {
		                if (modules.smartquery.query.sorts.sortEditRow !== '') {
		                    $("#sortTable").datagrid('endEdit', modules.smartquery.query.sorts.sortEditRow);
		                }
		                if (modules.smartquery.query.sorts.sortEditRow == '') {
		                	/* 动态生成ID */
		                	var id = common.utils.guid();
		                	var newRow = {id:id,relationId:'1',mpColumnId:'',sortTypeId:'1'};
		                    $("#sortTable").datagrid('insertRow', {
		                        row: newRow
		                    });
		                    var index = $("#sortTable").datagrid('getRowIndex', newRow);
		                    $("#sortTable").datagrid('beginEdit', index);
		                    modules.smartquery.query.sorts.sortEditRow = index;
		                }
		            }
		        }, '-', {
		            text: '删除排序方式', iconCls: 'icon-btn-remove-sign', handler: function () {
		            	if (modules.smartquery.query.filter.sortEditRow !== '') {
		            		common.utils.showErrorMsg("只能删除已生效的排序方式！");
		                    return;
		                }
		                var row = $("#sortTable").datagrid('getSelections');
		                if(row.length === 0){
		                	common.utils.showErrorMsg("请选择需要删除的排序方式！");
		        			return;
		                }
		                var url = ctx + "/a/smart-query/query/sqsort/remove";
		        		var params = {id:row[0].id,isTemp:modules.smartquery.query.isTemp};
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
		        }, /*'-', {
		            text: '上 移', iconCls: 'icon-btn-circle-arrow-up', handler: function () {
		            	layer.msg('上移');
		            }
		        }, '-', {
		            text: '下 移', iconCls: 'icon-btn-circle-arrow-down', handler: function () {
		            	layer.msg('下移');
		            }
		        }, */'-', {
		            text: '生 效', iconCls: 'icon-btn-ok-sign', handler: function () {
		            	if (modules.smartquery.query.sorts.sortEditRow !== '') {
			                $("#sortTable").datagrid('endEdit', modules.smartquery.query.sorts.sortEditRow);
			            }
		            }
		        }],
		        onAfterEdit: function (rowIndex, rowData, changes) {
		        	/* 更新排序方式 */
                    modules.smartquery.query.sorts.saveOrUpdateSorter(rowData);
		            modules.smartquery.query.sorts.sortEditRow = '';
		        },
		        onDblClickRow: function (rowIndex, rowData) {
		            if (modules.smartquery.query.sorts.sortEditRow != '') {
		                $("#sortTable").datagrid('endEdit', modules.smartquery.query.sorts.sortEditRow);
		            }
		            if (modules.smartquery.query.sorts.sortEditRow == '') {
		                $("#sortTable").datagrid('beginEdit', rowIndex);
		                modules.smartquery.query.sorts.sortEditRow = rowIndex;
		            }
		        },
		        onClickRow: function (rowIndex, rowData) {
		            if (modules.smartquery.query.sorts.sortEditRow != '') {
		                $("#sortTable").datagrid('endEdit', modules.smartquery.query.sorts.sortEditRow);
		            }
		        }
		  });
		}
	},
	exports:{
		/**
		 * Excel导出方法
		 */
		cvsExport:function(){
			common.utils.showLoading();
			/*读取数据总记录*/
			var options = $('#dataTable').datagrid('getPager').data("pagination").options;
			var total = options.total;
			
			var x = 10000;
			/*咨询是否转为后台任务运行*/
			if(total > x){
				common.utils.closeLoading();
				var i = layer.confirm('需要导出数据量超过'+x+'条，可能会导致过长的等待时间，是否将导出任务转至后台保存到数据超市中', {
				    btn: ['转至后台任务','继续导出']
				}, function(){
				    /*需要选择保存的个人目录路径*/
					layer.close(i);
					modules.smartquery.query.windows.openCreateTaskWin();
				}, function(){
					layer.close(i);
					common.utils.showLoading();
				    modules.smartquery.query.exports.exportFile();
				});
			}else{
				modules.smartquery.query.exports.exportFile();
			}			
		},
		/*直接数据文件导出*/
		exportFile:function(){
			 var url = ctx + "/a/smart-query/query/smartquery/export/temp";
			var params = {
				sqId:modules.smartquery.query.sqId,
				isTemp:modules.smartquery.query.isTemp
			};
					
			/*生成导出临时文件*/
			$.ajax({
			     type: "POST",
			     url: url,
			     data: params,
			     dataType: "json",
			     success: function(rs){
			     common.utils.closeLoading();
			     if(rs.flag){
			        var fileId = rs.id;
			        /*导出临时文件*/
			    	document.forms[0].action=ctx + "/a/smart-query/query/smartquery/export?fileId="+fileId;
					document.forms[0].target="exportFrame";
					document.forms[0].submit();
			     }else{
			        common.utils.showErrorMsg("导出生成文件失败，请稍候再试！");
			     }
			  }
			});
		},
		
		/*创建后台任务数据文件*/
		createTaskFile:function(){
			$("#createTask").form('submit', {    
			    url:ctx + "/a/smart-query/query/queryfile/save",    
			    onSubmit: function(params){
			    	var isValid = $(this).form('validate');
					if (isOpen && !isValid){
						common.utils.showErrorMsg("保存文件名或保存目录信息不能为空！");
						return isValid
					}
					/* 提交额外的参数 */
					params.id = modules.smartquery.query.sqId;    
					params.isTemp = modules.smartquery.query.isTemp;
					params.saveType='task';
					return true;			          
			    }  
			});
			modules.smartquery.query.windows.closeCreateTaskWin();
			common.utils.showSucMsg("保存数据操作已提交至后台任务调度，请稍候到数据超市查询并下载相关数据文件！");
		}
	},
	buttons:{
		/**
		 * 定时任务切换器操作
		 */
		timerSwitch:function(){
				var isOpen = $("#isOpen").linkbutton('options').selected;
				if(isOpen){			
					$("#timer").show();	
					$('#saveConfigWin').dialog('resize');
				}else{
					$("#timer").hide();	 
					$('#saveConfigWin').dialog('resize');
				}
			},
		firstShare:true,
		/**
		 * 用户分享切换器操作
		 */
		shareSwitch:function(){
			var isShare = $("#isShare").linkbutton('options').selected;
			if(isShare){
				$("#sharer").show();
				$('#saveDataWin').dialog('resize');
				if(modules.smartquery.query.buttons.firstShare){
					modules.smartquery.query.dataShare.loadShareSelecter();
					modules.smartquery.query.buttons.firstShare = false;
				}				
			}else{
				$("#sharer").hide();
				$('#saveDataWin').dialog('resize');
			}
		}
		
	},	
	windows:{
		/* 打开保存查询配置窗口 */
		openSaveConfig:function(){
			$('#saveConfigWin').dialog('open');		
			$("#saveConfigWin").panel("move",{top:$(document).scrollTop()});	
			$('#saveConfigWin').dialog('resize');
		},
		closeSaveConfig:function(){
			$('#saveConfigWin').dialog('close');
		},		
		/* 打开保存数据窗口 */
		openSaveData:function(){
			//隐藏且禁用横向纵向两个滚动条
			/*document.body.parentNode.style.overflow = "hidden";*/
			$('#saveDataWin').dialog('open');
			$("#saveDataWin").panel("move",{top:$(document).scrollTop()}); 
			$('#saveDataWin').dialog('resize');
		},
		closeSaveData:function(){
			$('#saveDataWin').dialog('close');
		},
		/*打开过滤条件编辑窗口*/
		openFilterEdit:function(){
			$('#filterEditWin').dialog('open');
			$('#filterEditWin').dialog('resize');
		},
		closeFilterEdit:function(){
			$('#filterEditWin').dialog('close');
		},
		/**
		 * 打开个人路径编辑窗口
		 * @param {} catalogId 编辑目录ID
		 */
		openEditCatalogTree:function(e){
			modules.smartquery.query.catalogTree.currentCatalogId = e.data.catalogId;
			$('#editPathWin').dialog('open');
		},
		closeEditCatalogTree:function(){
			$('#editPathWin').dialog('close');
		},
		/*打开图表配置窗口*/
		openConfigReport:function(){
			/*初始化查询数据列*/
			modules.smartquery.query.reportConfig.initSelecter();
			$('#reportConfigWin').dialog('open');
			$("#reportConfigWin").panel("move",{top:$(document).scrollTop()}); 
			$('#reportConfigWin').dialog('resize');
		},
		/*打开创建任务信息窗口*/
		openCreateTaskWin:function(){
			$('#createTaskWin').dialog('open');
		},
		closeCreateTaskWin:function(){
			$('#createTaskWin').dialog('close');
		}
	},
	
	utils:{
		/* 列名注解 */
		columnFormatter:function(value, rowData, rowIndex){
			var url =  ctx + "/a/dataMapping/mapping/columnmapping/query/name?id="+value;
			return $.ajax({ url: url, async: false }).responseText; 
		},
		/**
		 * 初始化字段选择器
		 * @param {} container 指向DIV
		 * @param {} url 访问的服务器URL参数
		 * @param {} required 是否为必填字段（默认为true）
		 * @param {} onSelect 选择方法体
		 */
		initColumnPicker:function(container,url,required,onSelect){
			if(!common.utils.isDefined(required)) required = true;
			var className = $("#"+container).attr("class");
			if(className === "easyui-combogrid"){
				/*已经初始化控件，需要刷新列表数据*/
				$('#'+container).combogrid('grid').datagrid('reload',url);
			}else {
				/*未初始化控件，需要对控件进行初始化*/
				$('#'+container).combogrid({
					url:url,
			        idField:'id',textField:'mappingName', panelWidth:430,
			        filter: function(q, row){
						var opts = $(this).combogrid('options');
						return row[opts.textField].indexOf(q) == 0;
					},
			        columns:[[  
						{field:'tableMappingName',title:'映射表名',width:180},  
						{field:'mappingName',title:'映射列名',width:150},  
						{field:'columnTypeName',title:'类型',width:80},
						{field:'columnType',hidden:true},
						{field:'id',hidden:true}
					]], 
					required:required});
				 if(common.utils.isFunction(onSelect)) $('#'+container).combogrid({onSelect:onSelect});
			}			
		}
	}
	
};