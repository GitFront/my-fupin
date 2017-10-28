$.namespace("modules.datalabel");

/**
 * 标签树配置页面控制类js
 * @type 
 */

modules.datalabel.config = {
	/*页面对象管理*/
	formID:'labelConfig',
	labelID:"",
	labelNAME:"",
	parentNode:false,
	currentNode:{},
	fieldTree:{},
	/**
	 * 页面初始化方法
	 */
	init:function(id,name,isParent){
		/*绑定按钮功能*/
		$('#label_save_btn').bind('click', this.form.save); 
		$('#label_repeat_btn').bind('click', this.form.repeat); 

		$('#field_select_btn').bind('click', this.windows.choiceField); 
		$('#seach_field_btn').bind('click', this.windows.openField);
		$('#close_field_select_btn').bind('click', this.windows.closeField);

		$('#dim_select_btn').bind('click', this.windows.choiceDim); 
		$('#seach_dim_btn').bind('click', this.windows.openDim);
		$('#close_dim_select_btn').bind('click', this.windows.closeDim);
		
		if(isParent === 'true')
			modules.datalabel.config.parentNode = true;
		modules.datalabel.config.labelID = id;
		modules.datalabel.config.labelNAME = name;
		/*初始化页面元素*/
		this.form.init(id);
		this.windows.init();
	},
	form:{
		init:function(id){
			common.utils.showLoading();
			var url = ctx + "/a/data-label/config/query/byid";
			$.ajax({
	             type: "POST",
	             url: url,
	             data: {id:id},
	             dataType: "json",
	             success: function(rs){
	             	common.utils.closeLoading();
	             	if(rs){
	             		$('#'+modules.datalabel.config.formID).form('load',rs);
	             		if(rs.type === 'label')
	             			$("#labelProperty").show();
	             		modules.datalabel.config.currentNode = rs;
	             	}else{
	    				common.utils.showErrorMsg("初始化数据标签表单数据操作失败，请稍候再试！");		    				
	             	}
	             }
	         });
		},
		/*表单失效控制*/
		disabled:function(){
			$("#property :input").attr('disabled', true);
			$('#label_save_btn').linkbutton('disable');
			$('#label_repeat_btn').linkbutton('disable');
		},
		/*表单有效控制*/
		enable:function(){
			$("#property :input").removeAttr('disabled');
			$('#label_save_btn').linkbutton('enable');
			$('#label_repeat_btn').linkbutton('enable');
		},
		
		/*选择标签类型*/
		choiceLabelType:function(rec){
			if(rec.id === 'catalog'){
				$("#labelProperty").hide();
			}else{
				if(modules.datalabel.config.parentNode){
					common.utils.showErrorMsg("只能定义叶子节点为数据标签类型！");
					$('#type').combobox('setValue', 'catalog');
					return;
				}					
				$("#labelProperty").show();
			}			
		},
		/*保存表单信息*/
		save:function(){
			
			/*启动加载*/
			common.utils.showLoading();
			
			$("#"+modules.datalabel.config.formID).form('submit', {    
			    url:ctx + "/a/data-label/config/update",    
			    onSubmit: function(params){ 
			    	/*数据验证*/
					var type = $("#type").combobox('getValue');
					var active = $("#active").combobox('getValue');
					
					/*数据标签类型需要验证标签属性*/
					if(type === 'label' && active === '1'){
						var valid = $(this).form('validate');
						if(!valid){
							common.utils.showErrorMsg('请把数据标签属性信息填写完整！');
							common.utils.closeLoading();
							return false;
						}
					}
					return true;			          
			    },
				success: function(rs){
					common.utils.closeLoading();
					var data = $.parseJSON(rs);
					if(data.flag){						
						common.utils.showSucMsg("数据标签配置保存成功！",function(){
							/*关闭标签窗口*/
							parent.window.modules.datalabel.config.tabs.removeTab(modules.datalabel.config.labelID,modules.datalabel.config.labelNAME);
						});
					}else{
						common.utils.showErrorMsg("标签配置保存失败，请稍候再试！");
					}					
				}    
			}); 
		},
		/*重置表单信息*/
		repeat:function(){
			$.messager.confirm('确认对话框', '是否需要重置属性表单信息？', function(r){
				if (r){
					$('#'+modules.datalabel.config.formID).form('load',modules.datalabel.config.currentNode);
					if(modules.datalabel.config.currentNode.type === 'catalog')
	             		$("#labelProperty").hide();
				}
			});	
		}
	},
	windows:{
		init:function(){
			/* 加载字段树数据 */
			var setting = {
				treeId:'fieldTree',
				view: {
					selectedMulti: false
				},
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "all"
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
			$.ajax({
	             type: "POST",
	             url: ctx + "/a/smart-query/query/querytree/column/choice",
	             dataType: "json",
	             success: function(data){
	             	if(data) {
	             		/* 加载映射字段树信息 */
	        			modules.datalabel.config.fieldTree = $.fn.zTree.init($("#fieldTree"), setting, data);
	             	}
	             }
	         });
	         
	       $('#dimTable').combogrid({    
			    panelWidth:350,   
				idField:'tableName',    
				textField:'mappingName',    
				url:ctx+'/a/dataMapping/mapping/tablemapping/list/dim',    
				columns:[[
					{field:'id',hidden:true}, 
					{field:'tableName',title:'数据表名',width:180},    
					{field:'mappingName',title:'映射名',width:150}    
				]],
				onSelect:modules.datalabel.config.windows.selectDim
			}); 
		},
		selectDim:function(index,rec){
			var id = rec.id;
			var url = ctx + "/a/dataMapping/mapping/columnmapping/list?mpTableId="+id;
			$("#ruleID").combobox('reload',url);
			$("#ruleID").combobox('setValue',"");
			$("#ruleName").combobox('reload',url);
			$("#ruleName").combobox('setValue',"");
			$("#ruleSort").combobox('reload',url);
			$("#ruleSort").combobox('setValue',"");
			$("#ruleTop").combobox('reload',url);
			$("#ruleTop").combobox('setValue',"");			
		},
		openField:function(){
			$('#fieldSelectWin').dialog('open'); 
		},
		openDim:function(){
			$('#dimSelectWin').dialog('open'); 
		},
		choiceField:function(){
			var treeObj = modules.datalabel.config.fieldTree;
			var nodes = treeObj.getCheckedNodes(true);
			if(common.utils.isArrayEmpty(nodes)){
				common.utils.showErrorMsg("请至少选择一个关联属性！");
				return;
			}
			var node = nodes[0];
			if(node.isParent){
				common.utils.showErrorMsg("只能选择关联的叶子属性节点，请重新选择！");
				return;
			}
			$("#assoTableId").val(node.pId);
			$("#assoTable").val(node.mpTable);
			$("#assoFieldId").val(node.id);
			$("#assoField").val(node.mpColumn);
			$("#fieldName").textbox('setValue',node.name);
			modules.datalabel.config.windows.closeField();
		},
		choiceDim:function(){
			var vaild = $("#dimSelecter").form('validate');
			if(!vaild){
				common.utils.showErrorMsg("请选择完整的维表读取信息！");
				return;
			}
			var dg = $('#dimTable').combogrid('grid');	// 获取数据表格对象
			var dr = dg.datagrid('getSelected');	// 获取选择的行
			/*关联规则整理 (ruleID;ruleName;ruleSort;ruleTop) */
			var assoRule = $("#ruleID").combobox('getValue') + ";" + $("#ruleName").combobox('getValue') + ";" ;
			var ruleSort = $("#ruleSort").combobox('getValue');
			var ruleTop = $("#ruleTop").combobox('getValue');
			if(!common.utils.isEmpty(ruleSort)){
				assoRule += ruleSort + ";";
			}else{
				assoRule += "null;";
			}
			if(!common.utils.isEmpty(ruleTop)){
				assoRule += ruleTop ;
			}else{
				assoRule += "null";
			}			
			
			$("#assoDimId").val(dr.id);
			$("#assoDim").val(dr.tableName);
			$("#assoRule").val(assoRule);
			$("#dimName").textbox('setValue',dr.mappingName);
			modules.datalabel.config.windows.closeDim();
		},
		closeField:function(){
			$('#fieldSelectWin').dialog('close');  
		},
		closeDim:function(){
			$('#dimSelectWin').dialog('close');  
		}
	},
	utils:{}
};