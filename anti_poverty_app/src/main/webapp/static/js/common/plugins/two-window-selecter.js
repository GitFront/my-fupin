$.namespace("common.plugins.easyui");


/**
	* 两栏选择器的初始化方法
	* @param {} container 可选择器指向对象ID
	* @param {} selecterConfig 选择器的配置项
	* 				title:选择器标题
	* 				width:选择器宽度
	* 				height:选择器高度
	* @param {} noSelectConfig 未选择列表的datagrid配置项
	* 				{
			    		title:"未选择列",
			    		data:[],
			    		url:'',
			    		queryParams:{},
			    		idField:'id',
			    		sortName:'id',
			    		sortOrder : 'asc',
			    		columns:[{field : 'id'}]
			    	}
	* @param {} hasSelectConfig 已选择列表的datagrid配置项
	* 				{
			    		title:"已选择列",
			    		data:[],
			    		url:'',
			    		queryParams:{},
			    		idField:'id',
			    		sortName:'id',
			    		sortOrder : 'asc',
			    		columns:[{field : 'id'}]
			    	}
	* @param {} callback 可编辑树的触发对象，为json格式，属性如下
	* 				moveOut：属性移出的回调函数，参数为两个grid的ID值noSelecter,hasSelecter
	* 				moveIn： 属性移入的回调函数，参数为两个grid的ID值noSelecter,hasSelecter
	* @return 返回可编辑树对象
	*/
	function TwoWindowsSelecter(container,selecterConfig,noSelectConfig,hasSelectConfig,callback){
		
		/*默认ID定义*/
		this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID = common.utils.guid();
		this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID = common.utils.guid();
		var TWO_SELECTER_NO_ATTR_DIV = common.utils.guid();
		var TWO_SELECTER_HAS_ATTR_DIV = common.utils.guid();
		var TWO_SELECTER_BTN_DIV = common.utils.guid();
		var TWO_SELECTER_BTN_1_ID = common.utils.guid();/*全部移入*/
		var TWO_SELECTER_BTN_2_ID = common.utils.guid();/*移入*/
		var TWO_SELECTER_BTN_3_ID = common.utils.guid();/*移出*/
		var TWO_SELECTER_BTN_4_ID = common.utils.guid();/*全部移出*/
		
		
		/*参数验证处理*/
		var selecterConfig_ = this.configDefault(selecterConfig,"selecter");
		var noSelectConfig_ = this.configDefault(noSelectConfig,"noSelecter");
		var hasSelectConfig_ = this.configDefault(hasSelectConfig,"hasSelecter");
		
		this.TWO_SELECTER_CONFIG = selecterConfig_;
		this.TWO_SELECTER_CALLBACK = this.configDefault(callback,"callback");
		
		/*选择器布局宽高度算法*/
		if(common.utils.isString(selecterConfig_.width) && selecterConfig_.width.indexOf("%")) 
			selecterConfig_.width = $(document.body).width()*parseFloat(selecterConfig_.width.replace('%','')/100);
		if(selecterConfig_.fit) 
			selecterConfig_.width = $(document.body).width();
		var selecterWidth = selecterConfig_.width<200?200:selecterConfig_.width;
		var otherWidth = (selecterWidth-35)/2;
		var selecterHeight = selecterConfig_.height<150?150:selecterConfig_.height;
		var btnTopHeight = (selecterHeight-154)/2;
		var fitColumnConfig = "";
		if(selecterConfig_.fitColumns) 
			fitColumnConfig = " componentType=\"datagrid\"  autoResize=\"true\"  resizeWidth=\"true\" fitColumns=\"true\"";
		/*创建控件布局*/
		$('#'+container).css("width",selecterWidth+"px");    
		$('#'+container).css("height",selecterHeight+"px");
		$('#'+container).layout({title:selecterConfig_.title,fit:false,border:true}); 		
		$('#'+container).layout('add',{
				id:TWO_SELECTER_NO_ATTR_DIV,
			    region: 'west',    
			    width: otherWidth,    
			    title:noSelectConfig_.title,
			    collapsible:false,   
			    content:"<table id=\""+this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID+"\" "+fitColumnConfig+"></table>"   
			});
		$('#'+container).layout('add',{
				id:TWO_SELECTER_HAS_ATTR_DIV,   
			    region: 'east',    
			    width: otherWidth,    
			    title:hasSelectConfig_.title,
			    collapsible:false,   
			    content:"<table id=\""+this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID+"\" "+fitColumnConfig+"></table>"   
			});
		$('#'+container).layout('add',{
				id:TWO_SELECTER_BTN_DIV,
			    region: 'center',
			    collapsible:false,   
			    content:"<a id='"+TWO_SELECTER_BTN_1_ID+"' href='#'></a><br><a id='"+TWO_SELECTER_BTN_2_ID+"' href='#'></a><br>" +
			    		"<a id='"+TWO_SELECTER_BTN_3_ID+"' href='#'></a><br><a id='"+TWO_SELECTER_BTN_4_ID+"' href='#'></a>"   
			});
		$('#'+TWO_SELECTER_BTN_DIV).css("padding-top",btnTopHeight+"px");
		$('#'+TWO_SELECTER_BTN_DIV).css("padding-left","5.4px");
			
		/*两栏datagrid列表*/
		/*列信息处理*/
		delete noSelectConfig_["title"];
		delete hasSelectConfig_["title"];
		var TWO_SELECTER_NO_ATTR_DATA_GRID = $('#'+this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID).datagrid(noSelectConfig_);
		
		var TWO_SELECTER_HAS_ATTR_DATA_GRID = $('#'+this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID).datagrid(hasSelectConfig_);
		
		/*自定义列表分页对象*/
		if(common.utils.isJson(selecterConfig_.pagination)){
			if(TWO_SELECTER_NO_ATTR_DATA_GRID.datagrid('options').pagination){
				var pager = $('#'+this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID).datagrid('getPager');
				$(pager).pagination(selecterConfig_.pagination);
			}
			if(TWO_SELECTER_HAS_ATTR_DATA_GRID.datagrid('options').pagination){
				var pager = $('#'+this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID).datagrid('getPager');
				$(pager).pagination(selecterConfig_.pagination); 
			}			
		}
		
		/*初始化按钮布局内容*/
		$('#'+TWO_SELECTER_BTN_1_ID).linkbutton({iconCls: 'icon-btn-fast-forward',plain:true});
		$('#'+TWO_SELECTER_BTN_1_ID).bind('click',{selecter:this}, this.moveInAll); 
		$('#'+TWO_SELECTER_BTN_1_ID).css("margin-bottom","15px");
		
		$('#'+TWO_SELECTER_BTN_2_ID).linkbutton({iconCls: 'icon-btn-forward',plain:true});
		$('#'+TWO_SELECTER_BTN_2_ID).bind('click',{noSelecter:this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID,hasSelecter:this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID,callback:this.TWO_SELECTER_CALLBACK},this.moveIn); 
		$('#'+TWO_SELECTER_BTN_2_ID).css("margin-bottom","15px");
		
		$('#'+TWO_SELECTER_BTN_3_ID).linkbutton({iconCls: 'icon-btn-backward',plain:true});
		$('#'+TWO_SELECTER_BTN_3_ID).bind('click',{noSelecter:this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID,hasSelecter:this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID,callback:this.TWO_SELECTER_CALLBACK},this.moveOut); 
		$('#'+TWO_SELECTER_BTN_3_ID).css("margin-bottom","15px");
		
		$('#'+TWO_SELECTER_BTN_4_ID).linkbutton({iconCls: 'icon-btn-fast-backward',plain:true});
		$('#'+TWO_SELECTER_BTN_4_ID).bind('click',{selecter:this},  this.moveOutAll);
		
	};
	
		/*自身方法定义，重载分页栏定义信息*/
		TwoWindowsSelecter.prototype.customPaging = function(pagination) {
			var noSelecter = $("#"+this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID);
			if(common.utils.isJson(pagination) && noSelecter.datagrid('options').pagination){
				var pager = noSelecter.datagrid('getPager');
				$(pager).pagination(pagination); 
			}
			var hasSelecter = $("#"+this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID);
			if(common.utils.isJson(pagination) && hasSelecter.datagrid('options').pagination){
				var pager = hasSelecter.datagrid('getPager');
				$(pager).pagination(pagination); 
			}		
		};
		/*自身方法定义，重载全部列表信息*/
		TwoWindowsSelecter.prototype.reload = function(noselectParams,hasselectParams) {
			var noSelecter = $("#"+this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID);
			var hasSelecter = $("#"+this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID);
			if(!common.utils.isJson(noselectParams)){
				noselectParams = {};
			}
			if(!common.utils.isJson(hasselectParams)){
				hasselectParams = {};
			}
			noSelecter.datagrid('unselectAll');
			noSelecter.datagrid('reload', noselectParams);
			hasSelecter.datagrid('unselectAll');
			hasSelecter.datagrid('reload', hasselectParams);			
		};
		
		/*加载选择器左边列表信息*/	
		TwoWindowsSelecter.prototype.loadLeft = function(url,noselectParams) {
			var noSelecter = $("#"+this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID);
			if(!common.utils.isJson(noselectParams)){
				noselectParams = {};
			}
			noSelecter.datagrid('unselectAll');
			noSelecter.datagrid({url:url,queryParams:noselectParams});
			/*处理分页样式*/
			var selecterConfig_ = this.TWO_SELECTER_CONFIG;
			if(common.utils.isJson(selecterConfig_.pagination)){
				if(noSelecter.datagrid('options').pagination){
					var pager = noSelecter.datagrid('getPager');
					$(pager).pagination(selecterConfig_.pagination);
				}
			}
		};
		
		/*重载选择器左边列表信息*/	
		TwoWindowsSelecter.prototype.reloadLeft = function(noselectParams) {
			var noSelecter = $("#"+this.TWO_SELECTER_NO_ATTR_DATA_GRID_ID);
			if(!common.utils.isJson(noselectParams)){
				noselectParams = {};
			}
			noSelecter.datagrid('unselectAll');
			noSelecter.datagrid('reload', noselectParams);		
		};
		
		/*加载选择器右边列表信息*/	
		TwoWindowsSelecter.prototype.loadRight = function(url,hasselectParams) {
			var hasSelecter = $("#"+this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID);
			if(!common.utils.isJson(hasselectParams)){
				hasselectParams = {};
			}
			hasSelecter.datagrid('unselectAll');
			hasSelecter.datagrid({url:url,queryParams:hasselectParams});
			/*处理分页样式*/
			var selecterConfig_ = this.TWO_SELECTER_CONFIG;
			if(common.utils.isJson(selecterConfig_.pagination)){
				if(hasSelecter.datagrid('options').pagination){
					var pager = hasSelecter.datagrid('getPager');
					$(pager).pagination(selecterConfig_.pagination);
				}
			}			
		};
		
		/*重载选择器右边列表信息*/	
		TwoWindowsSelecter.prototype.reloadRight = function(hasselectParams) {
			var hasSelecter = $("#"+this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID);
			if(!common.utils.isJson(hasselectParams)){
				hasselectParams = {};
			}
			hasSelecter.datagrid('unselectAll');
			hasSelecter.datagrid('reload', hasselectParams);		
		};
		
		/*清空右边已选择列表数据*/	
		TwoWindowsSelecter.prototype.cleanRight = function() {
			var hasSelecter = $("#"+this.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID);
			hasSelecter.datagrid('unselectAll');
			hasSelecter.datagrid('loadData',{ total:0,rows:[]});		
		};
		
		/*自身方法定义*/	
		TwoWindowsSelecter.prototype.moveInAll = function(e) {	
			var twoSelecter = e.data.selecter;
			var noSelecter = $("#"+twoSelecter.TWO_SELECTER_NO_ATTR_DATA_GRID_ID);
			var hasSelecter = $("#"+twoSelecter.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID);
			var e = {data:{
						noSelecter:twoSelecter.TWO_SELECTER_NO_ATTR_DATA_GRID_ID,
						hasSelecter:twoSelecter.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID,
						callback:twoSelecter.TWO_SELECTER_CALLBACK
					}};
			noSelecter.datagrid('selectAll');
			twoSelecter.moveIn(e);
			noSelecter.datagrid('unselectAll');
		};
		
		TwoWindowsSelecter.prototype.moveIn = function(e) {
			var callback = e.data.callback.moveIn;
			
			if(callback !== "" && common.utils.isFunction(callback)){
				eval(callback + "(e.data.noSelecter,e.data.hasSelecter)");
			}else{
				var noSelecter = $("#"+e.data.noSelecter);
				var hasSelecter = $("#"+e.data.hasSelecter);
				var checkeds = noSelecter.datagrid('getChecked');
				if(checkeds.length == 0){
					layer.msg('请选择要移入的属性', {shade: 0.2});
					return;
				}
				/* 通过左栏数据移动至右栏 */
				var orows = hasSelecter.datagrid('getRows');
				var data = $.merge(orows,checkeds);
				hasSelecter.datagrid('loadData',{ "total":data.length,rows:data });
				/* 清除所有行 */
				for (var i = checkeds.length-1; i >= 0 ; i--) {
					var index = noSelecter.datagrid('getRowIndex', checkeds[i]);
					noSelecter.datagrid('deleteRow',index);
				}
			}
		};
		
		
		TwoWindowsSelecter.prototype.moveOut = function(e) {
			var callback = e.data.callback.moveOut;
			
			if(callback !== "" && common.utils.isFunction(callback)){
				eval(callback + "(e.data.noSelecter,e.data.hasSelecter)");
			}else{
				var noSelecter = $("#"+e.data.noSelecter);
				var hasSelecter = $("#"+e.data.hasSelecter);
				var checkeds = hasSelecter.datagrid('getChecked');
			
				if(checkeds.length == 0){
					layer.msg('请选择要移出的属性', {shade: 0.2});
					return;
				}			
				/* 通过右栏数据移动至左栏 */
				var orows = noSelecter.datagrid('getRows');
				var data = $.merge(orows,checkeds);
				noSelecter.datagrid('loadData',{ "total":data.length,rows:data });
				
				/* 清除所有行 */
				for (var i = checkeds.length-1; i >= 0 ; i--) {
					var index = hasSelecter.datagrid('getRowIndex', checkeds[i]);
					hasSelecter.datagrid('deleteRow',index);
				}
			}			
		 };
		 
		 TwoWindowsSelecter.prototype.moveOutAll = function(e) {			
		 	 var twoSelecter = e.data.selecter;
		 	 var noSelecter = $("#"+twoSelecter.TWO_SELECTER_NO_ATTR_DATA_GRID_ID);
			 var hasSelecter = $("#"+twoSelecter.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID);
		 	 var e = {data:{
						noSelecter:twoSelecter.TWO_SELECTER_NO_ATTR_DATA_GRID_ID,
						hasSelecter:twoSelecter.TWO_SELECTER_HAS_ATTR_DATA_GRID_ID,
						callback:twoSelecter.TWO_SELECTER_CALLBACK
					}};
		 	 hasSelecter.datagrid('selectAll');
			 twoSelecter.moveOut(e);
			 hasSelecter.datagrid('unselectAll');
		 };
		
		 
		/**
		 * 配置项默认值验证
		 * @param {} config 配置项
		 * @param {} type 配置项类型
		 */	
		TwoWindowsSelecter.prototype.configDefault = function(config,type) {
			var pri = false;
		  	var defaults = {};
		  	/*处理自定义配置项的column属性*/
		  	if(type === 'noSelecter' || type === 'hasSelecter'){
		  		if(common.utils.isJson(config) && config.hasOwnProperty("columns")){
			    		var cs = config.columns;
			    		if(common.utils.isArray(cs)){
			    			var ischockbox = false;
			    			for (var i = 0; i < cs.length; i++) {
			    				if(cs[i].field === "checkbox"){
			    					ischockbox = true;
			    				}
			    				if(!cs[i].hasOwnProperty("halign")){
			    					cs[i].halign = "center";
			    				}		    				
			    			}
			    			if(!ischockbox){
			    				var noAttrColumn=[{field:'checkbox',checkbox:true}];
								$.merge(noAttrColumn, cs);
								config["columns"] = [noAttrColumn];
			    			}		    			
			    		}
			    	}
		  	}		  	
		  	
			switch(type){
				case 'selecter':
			    	defaults = {
			    		title:"选择器",
			    		width:860,
			    		height:300,
			    		fitColumns:false,
			    		fit:false,
			    		pagination:''
			    	};
			      	break;
			    case 'noSelecter':
			    	pri = true;
			    	defaults = {
			    		title:"未选择列",
						striped : true,
			    		rownumbers:true,
			    		loadMsg:'正在加载数据...',
			    		data:[],
			    		url:'',
			    		queryParams:{},
			    		idField:'id',
			    		columns:[[{field:'checkbox',checkbox:true},{field : 'id'}]]
			    	};
			        break;
			    case 'hasSelecter':
			    	pri = true;
			    	defaults = {
			    		title:"已选择列",
						striped : true,
			    		rownumbers:true,
			    		loadMsg:'正在加载数据...',
			    		data:[],
			    		url:'',
			    		queryParams:{},
			    		idField:'id',
			    		columns:[[{field:'checkbox',checkbox:true},{field : 'id'}]]
			    	};
			        break;
			    case 'callback':
			    	defaults = {
			    		moveOut:'',
			    		moveIn:''
			    	};
			        break;
			    default:
			    	break;
			}
			var config_ = common.utils.setDefaultOption(config,defaults,pri);
			
		  	return config_;
		};
		
