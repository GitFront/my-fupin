<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-fun.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
	<!-- 功能插件引入 -->
	<script type="text/javascript" src="${ctxStatic}/js/common/plugins/ztree-edit-tree.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/data_label/label-tree.js"></script>
	<script type="text/javascript">
		$(function(){
			modules.datalabel.config.init();
		});		
	</script>
</head>
<body>
	<body class="easyui-layout">  
	    <div id="lTreeDiv"  data-options="region:'west',split:true,title:'标签管理'" style="width:220px;padding:5px;">
			<!-- 目录树 -->
			<ul id="labelTreeID" class="ztree"></ul>
	    </div>
	    <div id="configs" class="easyui-tabs" data-options="region:'center'"  style="overflow:hidden;"></div>
	</body>
</body>
</html>
