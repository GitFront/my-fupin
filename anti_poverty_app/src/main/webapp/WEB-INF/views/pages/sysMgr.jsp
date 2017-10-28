<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
response.setHeader("P3P","CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
%>

<html>
<head>
<meta name="decorator" content="default" />
</head>
<body>
		<div id="mainPanel"  class="easyui-layout" data-options="fit:true"  style="width:100%;height:100%;" >

			<div region="center" style="overflow-y:hidden;">
				<iframe name="system" id="mainFrame" src='${fns:getConfig("sysMgrUrl")}' frameborder='0' style='width: 100%; height: 650px'></iframe>
			</div>

			<div region="south" split="true" border="false"  style="height: 26px;overflow:hidden;">
				<div class="footer" style="background: #eee;text-align:center">系统管理中心</div>
			</div>
		</div>
		

 </body>
</html>
