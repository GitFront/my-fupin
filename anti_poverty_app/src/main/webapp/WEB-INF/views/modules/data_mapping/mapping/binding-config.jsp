<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<html>
<head>
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/two-window-selecter.js"></script>
	<SCRIPT type="text/javascript">
		var selecter;
		var catalogId = '${catalogId}';		
		var type=parent.$('#theme').combobox('getValue');  
	</SCRIPT>
	
	<!-- 定义宽表动态选择器 -->
	<script type="text/javascript">
		
		var noselecttab = {
				title:"未绑定宽表信息",
				url:ctx + "/a/dataMapping/mapping/tablemapping/listbycatalog",
				idField : 'id',
				queryParams:{type:type},
				columns : [{
					field : 'id',
					hidden:true
				}, {
					width : '40%',
					title : '数据表名',
					field : 'tableName'
				}, {
					width : '55%',
					title : '业务名称 ',
					field : 'mappingName'
				}]
		};
		
		
		var hasselecttab = {
				title:"已绑定宽表信息",
				url:ctx + "/a/dataMapping/mapping/tablemapping/listbycatalog",
				queryParams:{catalogId:catalogId},
				idField : 'id',
				columns : [{
					field : 'id',
					hidden:true
				},{
					width : '40%',
					title : '数据表名',
					field : 'tableName'
				}, {
					width : '55%',
					title : '业务名称 ',
					field : 'mappingName'
				}]
		};		
		
		/* 自定义动态入库方法 */
		/* movein和moveout自定义操作 */
		function bindingMoveIn(nogridid,hasgridid){
			common.utils.showLoading();
			
			var checkeds = $("#"+nogridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.closeLoading();
				common.utils.showErrorMsg("请选择需要绑定的宽表映射");
				return;
			}
			var tmids = new Array();
			for (var i = checkeds.length-1; i >= 0 ; i--) {
				tmids.push(checkeds[i].id);
			}
			
			var url = ctx + "/a/dataMapping/mapping/tablemapping/bind";
			var params = {
					catalogId:catalogId,
					tmids:tmids,
					on:true
			};
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	            	 common.utils.closeLoading();
	             	if(rs){
	             		selecter.reload({type:type}, {catalogId:catalogId});	             		
	             	}else{
	             		common.utils.showErrorMsg("宽表映射绑定失败，请稍候再试！");
	             	}
	             }
	         });
		}
		
		function bindingMoveOut(nogridid,hasgridid){
			common.utils.showLoading();
			var checkeds = $("#"+hasgridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.closeLoading();
				common.utils.showErrorMsg("请选择需要解绑的宽表映射！");				
				return;
			}
			var tmids = new Array();
			for (var i = checkeds.length-1; i >= 0 ; i--) {
				tmids.push(checkeds[i].id);
			}
			
			var params = {
					catalogId:catalogId,
					tmids:tmids
			};
			var url = ctx + "/a/dataMapping/mapping/tablemapping/bind";
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	            	 common.utils.closeLoading();
	             	if(rs){
	             		selecter.reload({type:type}, {catalogId:catalogId});
	             	}else{
	    				common.utils.showErrorMsg("宽表映射解绑失败，请稍候再试！");
	             	}
	             }
	         });
			
		}
		
		
		$(function(){
			/* 初始化ID，数据表名与注释信息 */
			$("#catalogId").val('${catalogId}');
			$("#catalogName").textbox('setValue','${catalogName}');
			$("#catalogFullPath").textbox('setValue','${catalogFullPath}');			

			var h = $(window).height()-151;
			
			/* 初始化两选框控件 */
			selecter = new TwoWindowsSelecter(
					"table_mapping_selecter",
					{title:"宽表映射目录绑定配置",width:'98.5%',height:h,fit:false,fitColumns:true},
					noselecttab,
					hasselecttab,
					{moveIn:"bindingMoveIn",moveOut:"bindingMoveOut"}
			);
		});
		
		
	</script>
</head>
<body> 
  <fieldset>
	<legend>业务宽表映射目录绑定配置</legend>
		<br>
  		<form id="bindbingConfig" method="post">
		    <div  style="padding:10px;background:#fafafa;width:97.7%;border:1px solid #ccc;">   
		        <label for="catalogName">当前目录名：</label>   
		        <input type="hidden" id="catalogId" name="catalogId">
		        <span><input id="catalogName" class="easyui-textbox" readonly="readonly" type="text" name="catalogName" style="width:150px;" /></span>
		        <label for="catalogFullPath" style="margin-left: 20px">目录全路径：</label>   
		        <span><input id="catalogFullPath" class="easyui-textbox" readonly="readonly" type="text" name="catalogFullPath" style="width:300px;"/></span>
		    </div>  
		</form>
		<hr/>
  		<div id="table_mapping_selecter"></div> 		
    </fieldset>
  </body>
</html>
