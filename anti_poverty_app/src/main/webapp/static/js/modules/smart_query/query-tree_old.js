$.namespace("modules.smartquery");

/**
 * 智能查询主页面控制类js
 * @type 
 */

modules.smartquery.main = {
	tabsid:"Query",
	tableids:[],
	/*宽表树对象*/
	tableTreeObj:{},
	/*查询列表对象*/
	queryListObj:{},
	tabCount:1,
	/*页面初始化方法*/
	init:function(){
		/*初始化多表选择窗口*/
		this.combobox.init();	
		this.tableSelecter.init();
	//	this.tableTree.init();
		this.queryList.init();				
	},
	//主题下拉列表
	combobox:{
		id:'#theme',
		init:function(){
			$(this.id).combobox({
				onLoadSuccess:function (none){
					$(this).combobox('select',none[0].type);
				},
				onSelect:function(record){
					if(record){
						modules.smartquery.main.tableTree.init(record.type);
					}
				}
			}); 
		},
		getValue:function(){
			return $(modules.smartquery.main.combobox.id).combobox('getValue');
		}
		
	},
	tabs:{
		/* 标签页添加方法 */
		addTabs:function(id,title,url){
			/* 判断标签是否存在 */
			if($('#'+modules.smartquery.main.tabsid).tabs('exists',title)){
				/* 选择已有标签 */
				$('#'+modules.smartquery.main.tabsid).tabs('select',title);
			}else{
				/* 创建新的标签页及iframe内容 */
				var content ="<iframe id='"+id+"',name='"+id+"' scroll=auto frameborder='0' src='"+url+"' style='width:100%; height:100%; overflow-x:hidden;'></iframe>";  
				$('#'+modules.smartquery.main.tabsid).tabs('add',{
					 title:title,
					 content:content,
					 closable:true,
					 fit : true
				});  
			}		
		},
		/* 标签页删除方法 */
		removeTab:function(id,title){
			/* 判断标签是否存在 */
			if($('#'+modules.smartquery.main.tabsid).tabs('exists',title)){
				/* 释放iframe内容 */
				var ifm=$(id);  
	            ifm.src=""; 
				/* 关闭标签信息 */
				$('#'+modules.smartquery.main.tabsid).tabs('close',title);				
			}		
		}
	},
	tableTree:{
		/*业务宽表树初始化*/
		init:function(){
			var setting = {
				treeId:'wideTableTree',
				view: {
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
	                beforeClick:this.beforeClick,
	                onClick:this.queryClick
				}
			};
			var params={};
			params.type=modules.smartquery.main.combobox.getValue;
			$.ajax({
				 data:params,
	             type: "POST",
	             url: ctx + "/a/dataMapping/tableMappingAssociation/queryTableMappingTree.do",
	             dataType: "json",
	             success: function(data){
	             	if(data) {
	             		/* 加载映射表信息 */
	        			modules.smartquery.main.tableTreeObj = $.fn.zTree.init($("#tableMappingTree"), setting, data);
	             	}
	             }
	         });			
		},
		/*树节点点击事件*/
		queryClick:function(e,treeId, treeNode){
			/*判断是否有重复元素*/
			if(modules.smartquery.main.tableids.indexOf(treeNode.id) === -1){
				$("#dom_table").append("<span class='domBtn' domId='" + treeNode.id + "'>" + treeNode.name + "</span>");
				modules.smartquery.main.tableids.push(treeNode.id);
			}			
			$('#tableSelecterWin').dialog("open");
		},
		/*过滤父节点点击事件*/
		beforeClick:function(treeId,treeNode){
			if(treeNode.mpTableName){
				  return true;
			}else if(treeNode.isParent){
                return false;
            }else{
                return true;
            }
        }
	},
	queryList:{
		init:function(){
			modules.smartquery.main.queryListObj = $("#queryInfoList").datalist({
				url : ctx + "/a/smart-query/query/config/listall",
				valueField : "id",
				textField : "name",
				checkbox : true,
				lines : true,
				onSelect : this.queryinfoClick
			});
		},
		queryinfoClick:function(rowIndex,rowData){
			var url =ctx + "/a/smart-query/query/query-config.htm?id="+rowData.id;
			modules.smartquery.main.tabs.addTabs(rowData.id,"智能查询("+rowData.name+")",url);
		},
		reloadQueryList:function(){
			$("#queryInfoList").datalist("reload");			
		}
	},
	tableSelecter:{
		init:function(){
			/*按钮绑定*/
			$('#query_win_btn').bind('click', this.createQuery); 
			$('#clean_win_btn').bind('click', this.cleanWindow); 		
			$('#ts_close_win_btn').bind('click', this.closeWindow);
		},
		createQuery:function(){
			var tableids = modules.smartquery.main.tableids;
			if(tableids.length === 0){
				common.utils.showErrorMsg("请选择至少一个需要查询的业务宽表！");
				return;
			}else{
				var url = ctx + "/a/smart-query/query/tables/isAssociation?tableids="+tableids;
				$.ajax({
		             type: "POST",
		             url: url,
		             dataType: "json",
		             success: function(rs){
		             	if(rs.flag){
		    				var url =ctx + "/a/smart-query/query/query-config.htm?tableids="+modules.smartquery.main.tableids;
							modules.smartquery.main.tabs.addTabs(modules.smartquery.main.tabCount,"智能查询窗口("+modules.smartquery.main.tabCount+")",url);
							modules.smartquery.main.tabCount++;
							modules.smartquery.main.tableSelecter.cleanWindow();
							$('#tableSelecterWin').dialog('close');            		
		             	}else{
		             		var source = rs.source;
		             		common.utils.showErrorMsg("“"+source+"”不存在关联关系，无法进行关联查询！");
		             		return;
		             	}
		             }
		         });
			}			
		},
		cleanWindow:function(){
			/*清除表格选择框的数据*/
			$("#dom_table").empty();
			modules.smartquery.main.tableids = new Array();
		},
		closeWindow:function(){
			modules.smartquery.main.tableSelecter.cleanWindow();
			$('#tableSelecterWin').dialog('close');
		}
	}
};