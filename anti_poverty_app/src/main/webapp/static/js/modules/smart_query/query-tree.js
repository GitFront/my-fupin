$.namespace("modules.query");

/**
 * 智能查询主页面控制类js
 * @type 
 */

modules.query.main = {
	/*页面初始化方法*/
	init:function(){
		/*初始化多表选择窗口*/
		this.combobox.init();	
		this.datagrid.init();	
	},
	//查询结果列表
	datagrid:{
		tableId:'#queryResult',
		numColumn:'USER_NUM',
		searchKey:'',
		init:function(sqQueryData){
			//列头
			var columns = new Array();			
		//	columns.push({field :this.numColumn,title : '用户号码',halign : 'center',align : 'left',width:50});
			if(sqQueryData){
				$.each(sqQueryData.selecters,function(i,selecter){
						columns.push({
							field : selecter.field,
							title : selecter.title,
							halign : 'center',
							align : 'left',
							width:100
									
						});
				});
			}
			var h = $(document).height()-322;
			var pageSize = common.utils.getGridPage(h);
			var url;
			if(sqQueryData!=undefined){
				url= ctx + "/a/smart-query/query/queryData";
				this.searchKey=sqQueryData.searchKey;		
			}
			/*查询并展示结果数据*/
			$(modules.query.main.datagrid.tableId).datagrid({
				singleSelect:true,
				contentType: true,
				rownumbers:true,
				params:sqQueryData,
				url:url,
				fit:true,
				fitColumns:true,
				loadMsg:'数据加载中...',
				pagination:true,
				pageSize:pageSize,
				pageList:[10,15,20,25,30,35,40,45,50],
				border:true,
				idField:this.numColumn,
				columns:[columns]
			
			});
			if(sqQueryData){
				delete sqQueryData.selecters;	
				delete sqQueryData.filters;	
				$(modules.query.main.datagrid.tableId).datagrid('options').params=sqQueryData;
				$('#Query').tabs('select',1);

			}
		}
	},
	//工具栏
	tools:{
		tbClick:function(none){
			var id=$('#win').data('data').id;
			var row=$('#winDataGrid').datagrid('getChecked'); 
			$('#'+id).textbox('setText',row[0].AREA_CD)
			var menu=$('#'+id).parent().menu('show');
			$('#win').window('close');
		},
		query:function(){
			if(this.isValid()){
				//获取字段的参数
				var fieldTags=$('#fieldEditor').find(".fieldTag");
				var selecters=[]
				$.each(fieldTags,function(i,fieldTag){
					selecters.push($(fieldTag).data('selecter'));
				});
				//获取条件的参数
				var filters=[];
				var conditionTags=$('#condEditor').find(".conditionTag");
				$.each(conditionTags,function(i,conditionTag){
					if($('#condEditor').find('#'+conditionTag.id+'_select').length>0){
						$(conditionTag).data('filter',
								$.extend({},$('#'+conditionTag.id).data('filter'),
										{relationId:$('#'+conditionTag.id+'_select').combobox('getValue')}));
					}else{
						$(conditionTag).data('filter',$.extend({},$('#'+conditionTag.id).data('filter'),{relationId:'1'}));
					}
					
					filters.push($(conditionTag).data('filter'));
				});	
				var sqQueryData={
					selecters:selecters,
					filters:filters,
					searchKey:common.utils.guid()
				};
				//初始化datagrid
				modules.query.main.datagrid.init(sqQueryData);	
			}
		},
		isValid:function(){
			var valid =true;
			var conditionTags=$('#condEditor').find(".conditionTag");
			var fieldTags=$('#fieldEditor').find(".fieldTag");
			if(fieldTags.length==0){
				common.utils.showErrorMsg("列字段,不能为空！");
				return false ;
			}
			if(conditionTags.length>0){
				$.each(conditionTags,function(i,conditionTag){
					if(!$('#'+conditionTag.id+'_input2').textbox('isValid')){
						common.utils.showErrorMsg("匹配字符,不能为空！");
						valid =false;
						return false ;
					};
				})
			}
			
			return valid;
		}
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
						modules.query.main.tableTree.init(record.type);
					}
				}
			}); 
		},
		getValue:function(){
			return $(modules.query.main.combobox.id).combobox('getValue');
		}
		
	},
	//业务宽表树初始化
	tableTree:{
		id:'wideTableTree',
		conditionMap:new Map(),
		fieldMap : new Map(),
		init:function(id){
			var setting = {
					treeId:this.id,
					edit: {
						enable: true,
						showRemoveBtn: false,
						showRenameBtn: false
					},
					view: {
						dblClickExpand:false,//双击节点图标展开节点
						selectedMulti:false
					},
					async: {
				        enable: true,
				        url:ctx + "/a/dataMapping/tableMappingAssociation/queryTableColums.do",
				        autoParam:["id"],
				    },
					data: {
						keep: {
							parent: true,
							leaf: true
						},
						simpleData: {
							enable: true
						}
					},
					callback: {
						//onClick: treeclick,
						beforeDrag : this.dragTree2Editor,
						onDrop : this.dropTree2Editor
						
					}
				};
			var params={};
			params.type=modules.query.main.combobox.getValue;
			$.ajax({
				 data:params,
	             type: "POST",
	             url: ctx + "/a/dataMapping/tableMappingAssociation/queryTableMappingTree.do",
	             dataType: "json",
	             success: function(data){
	             	if(data) {
	             		/* 加载映射表信息 +setting.treeId*/
	             		$.fn.zTree.init($('#'+setting.treeId), setting, data);
	             	}
	             }
	         });			
		},
		dragTree2Editor: function(treeId, treeNodes) {
			return !treeNodes[0].isParent;
		},
		
		dropTree2Editor: function(e, treeId, treeNodes, targetNode, moveType) {
			var domId = e.target.id;
			var nodeName = treeNodes[0].name;
			if (nodeName.indexOf(")") !== -1)
				nodeName = nodeName.substring(0, nodeName
						.lastIndexOf("("));
			if (nodeName.indexOf("）") !== -1)
				nodeName = nodeName.substring(0, nodeName
						.lastIndexOf("（"));
			
			if(domId == "condEditor"){
					modules.query.main.tableTree.createSplitButton(treeNodes[0],nodeName,domId);	
			}else if(domId == "fieldEditor"){
				var labelkeys = modules.query.main.tableTree.fieldMap.keys();
				if (labelkeys.indexOf(treeNodes[0].id+'_field') == -1) {
					var pnode = treeNodes[0].getParentNode();
					 modules.query.main.tableTree.createFieldButton(treeNodes[0],nodeName,domId);	
					// modules.query.main.tableTree.fieldMap.put(treeNodes[0].id+'_field', treeNodes[0]);
				} else {
					common.utils.showErrorMsg("“" + treeNodes[0].name
							+ "”字段已选中，不需要重复选取！");
				}
			
			}else{
				common.utils.showErrorMsg("请把字段标签拖拽到字段列编辑器！");
			}
		},
		close:function(id){
			
		},
		createCloseRegion:function(id,domId){
				var div=$("<div id='"+id+"'></div>").appendTo("#"+domId);
				var child=$('<div class="closeRegion"></div>').appendTo(div);
				$('<a href="javascript:void(0)"  class="close">&times;</a>').appendTo(child).bind("click", function(){
						var cId=id.split('_')[0];
						modules.query.main.tableTree.fieldMap.remove(id);
						$('#'+cId+'_input1').combobox('destroy');
						$('#'+cId+'_input2').textbox('destroy');
						$('#'+cId+'_menu').menu('destroy');
						$('#'+cId+'_select').combobox('destroy');
						$('#'+cId).splitbutton('destroy');
						$('#'+cId+'_sel').remove();
						$('#'+cId+'_cond').remove();
						$(div).remove();
				});
				
				var indicator = $('#chevron-up');
				$(div).mouseenter(function(){
					$(this).children("div").children(".close").show();
				}).mouseleave(function(){		
						$(this).children("div").children(".close").hide();
				}).draggable({
					cursor:'auto',
					proxy: 'clone',
					revert:true,
					onBeforeDrag:function(e){	
						$(e.currentTarget).css('z-index','999');
					},
					onDrag:function(e){
					},
					onStartDrag:function(e){
					},
					onStopDrag:function(e){
							$(e.currentTarget).css('z-index','1');
					}
				}).droppable({
					onDragOver:function(e,source){
								var s=$(source).position();			
								var t=$(this).position();		
								if(s.left<t.left ||s.top < t.top){
									indicator.css({
										display:'block',
										left:$(this).offset().left+$(this).width()-3,
										top:$(this).offset().top+$(this).outerHeight()-5
									});
								}else{
									indicator.css({
									display:'block',
									left:$(this).offset().left-6,
									top:$(this).offset().top+$(this).outerHeight()-5
									});
								}
						},
						onDragLeave:function(e,source){
							indicator.hide();
						},
						onDrop:function(e,source){
							var s=$(source).position();			
							var t=$(this).position();		
							indicator.hide();
							$(e.currentTarget).css('z-index','1');
							if(s.left<t.left ||(s.left<t.left &&s.top < t.top)){
								$(source).insertAfter(this);
							}else{
								$(source).insertBefore(this);
							}
						}
					}); 
			return child;
		},
		createFieldButton :function(treeNode,nodeName,domId){
			var id=common.utils.guid();
			//复制
			var menu=null;
			//
			if('I'==treeNode.getParentNode().storageType){
				menu=$('#fieldMenu').clone().attr('id',id+'_menu').prependTo('body').menu({
					onHide: modules.query.main.tableTree.menuHide
				});
				$(menu).find('input[id="_input3"]').attr('id',id+'_input3').combobox({
					valueField: 'id',
					textField: 'text',
					groupField:'group',
					url:ctx+'/a/smart-query/query/period/list',
					queryParams:{
						tableName:treeNode.getParentNode().mpTableName
					}
				}).parent().show();
			}else{
				$(menu).find('input[id="_input3"]').attr('id',id+'_input3').combobox().parent().hide();
			}
			var a=$('<a class="fieldTag" id="'+id+'" href="javascript:void(0)" >'+nodeName+'</a>')
					.data("selecter",$.extend({},$('#'+id).data('selecter'),{mpColumnId:treeNode.id,mpTableId:treeNode.getParentNode().id,title:nodeName,field:treeNode.field}))
					.splitbutton({
						plain:false,
						text:nodeName,
						menu:menu
					});;
			this.createCloseRegion(id+'_field',domId).prepend(a);
		},
		createSplitButton :function(treeNode,nodeName,domId){
			
			var id=common.utils.guid();
			if($("#"+domId).children().length>0){
				 modules.query.main.tableTree.createCombobox(id,domId);
			}
			//复制
			var menu=$('#condMenu').clone().attr('id',id+'_menu').prependTo('body').menu({
				onHide: modules.query.main.tableTree.menuHide
				
			});
			//替换菜单里的标签ID
			$(menu).find('input[id="_input2"]').attr('id',id+'_input2').textbox({
				valueField: 'id',
				textField: 'name',
				buttonText:'选择',
				required:true,
				queryParams:{
					columsId:treeNode.id
				},onClickButton:function(){
					$('#win').data('data',{id:this.id});  
					$('#win').window('open');  
				}
			});
			//
			if('I'==treeNode.getParentNode().storageType){
				$(menu).find('input[id="_input3"]').attr('id',id+'_input3').combobox({
					valueField: 'id',
					textField: 'text',
					groupField:'group',
					url:ctx+'/a/smart-query/query/period/list',
					queryParams:{
						tableName:treeNode.getParentNode().mpTableName
					}
				}).parent().show();
			}else{
				$(menu).find('input[id="_input3"]').attr('id',id+'_input3').combobox().parent().hide();
			}
			
			
			var combobox=$(menu).find('input[id="_input1"]').attr('id',id+'_input1').combobox({
					editable:false,
					valueField: 'id',
					textField: 'name',
					url:ctx+'/a/smart-query/query/dimension/filterformula/list?type='+treeNode.columnType,
					onLoadSuccess:function (none){
						$(combobox).combobox("select",1);
						var text=$(combobox).combobox('getText');
						var value=$(combobox).combobox('getValue');
					    //创建分割按钮
						var a=$('<a  id="'+id+'"  class="conditionTag" '
						+' href="javascript:void(0)" style=""></a>')
						.data('filter',$.extend({},$('#'+id).data('filter'),{mpColumnId:treeNode.id,mpTableId:treeNode.getParentNode().id}))
						.splitbutton({
							plain:false,
							text:nodeName+" "+text,
							menu:menu
						});
						modules.query.main.tableTree.createCloseRegion(id+'_cond',domId).prepend(a);
					}
			});
		},
		createCombobox :function(id,domId){
			var relationId=null;
			var select=$('<input  id="'+id+'_select" style="width:50px;height:34px"  data-options="" />');
			modules.query.main.tableTree.createCloseRegion(id+'_sel',domId).prepend(select);
			select.combobox({
				 	url:ctx+'/a/smart-query/query/dimension/filterralation/list',
				 	valueField:'id',
				 	textField:'name',
					panelHeight:45,
					onLoadSuccess:function(){
						$(this).combobox('select',1)
					},onSelect:function(record){
						//$('#'+id).data('filter',$.extend({},$('#'+id).data('filter'),{relationId:record.id}));
						
					}
			 });
			return  relationId;
			
		},
		menuHide:function(){
			//菜单隐藏后触发
			var id=this.id.substring(0,this.id.lastIndexOf('_'));
			var text='';
			var selecter;
			var statTime=$(this).find("#"+id+"_input3").combobox("getValue");
			if($(this).find("#"+id+"_input1").length>0){
				var comboValue=$(this).find("#"+id+"_input1").combobox("getValue");
				var comboText=$(this).find("#"+id+"_input1").combobox("getText");
				var combobox2=$(this).find("#"+id+"_input2").textbox("getText");
				text=$("#"+id).text().split(" ")[0]+" ";
				if(statTime!=undefined && statTime!='')text+="("+statTime+") ";
				if(comboText !=undefined && comboText!='')text+=comboText;
				if(combobox2!=undefined && combobox2!='')text+=":"+combobox2;
				selecter=$.extend({},$("#"+id).data("filter"),{statTime:statTime,value:[combobox2],formulaId:comboValue});
				$("#"+id).data("filter",selecter);
			}else{
				text=$("#"+id).text().split(" ")[0]+" ";
				if(statTime!=undefined && statTime!='')text+="("+statTime+") ";
				selecter=$.extend({},$("#"+id).data("selecter"),{statTime:statTime});
				$("#"+id).data("selecter",selecter);
			}
			if(statTime=='')statTime=undefined;
			$("#"+id).splitbutton({
					text:text
			});
		
			
		}
	}
};


(function($){  
	$.fn.menu.defaults.zIndex=8000;

	$.fn.datagrid.defaults.loader=function(param, success, error){
		 	var opts = $(this).datagrid('options');  
	        if (!opts.url) return false;  
	        if (opts.pagination && opts.pageNumber == 0){  
	            opts.pageNumber = 1;  
	            param.page = 1;  
	        }  
	        if (param.page == 0){  
	            return false;  
	        }   
	        if(opts.params){
	        	param=$.extend({},param, opts.params);
	        }
	        var contentType= 'application/x-www-form-urlencoded;charset=utf-8';
	        if(opts.contentType){
	        	contentType= 'application/json;charset=utf-8';
	        	param=JSON.stringify($.extend({},param, opts.params));    
	        }
	        
	        $.ajax({  
	            type: opts.method,  
	            url: opts.url, 
	            data: param,  
	            dataType: 'json',  
	            contentType:contentType,  
	            success: function(data){ 
	            	if(data){
	            		success(data);
	            	}
	            },  
	            error: function(){  
	            	error.apply(this,arguments);
	            }  
	        });  
		
		
	}

})(jQuery); 

