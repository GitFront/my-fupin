<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
</head>
<body>

<div id="mainPanel"  class="easyui-layout" data-options="fit:true"  style="width:100%;height:92.5%;" >

<div region="center" style="overflow-y:hidden;">
     <iframe id="mainFrame" src="${ctx}/a/data-label/user_analysis.htm" frameborder="0"    style="overflow-x:hidden;width: 100%; height: 100%"></iframe>
</div>
    
<div region="south" split="true" border="false"  style="height: 26px;overflow:hidden;">
	<div class="footer" style="background: #eee;text-align:center">卓望公司版权所有.</div>
</div>


</div>

</body>
</html>
