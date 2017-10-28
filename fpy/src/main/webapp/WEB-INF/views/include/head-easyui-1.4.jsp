<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=7,IE=8,IE=9,IE=10" />

<!-- Easy-ui包引用 -->
<link rel="stylesheet" type="text/css" href="${ctxPlugins}/jquery/jquery-easyui-1.4.3/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctxPlugins}/jquery/jquery-easyui-1.4.3/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/birp-icon.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/birp-btn-icon.css">

<!-- JQuery包引用 -->
<script type="text/javascript" src="${ctxPlugins}/jquery/jquery-easyui-1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctxPlugins}/jquery/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctxPlugins}/jquery/jquery-easyui-1.4.3/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common/jquery.easyui.utils.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common/jquery-namespace.js"></script>

<!-- Highcharts类引用 -->
<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts.js"></script>
<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts-more.js"></script>
<!-- Highcharts仪表盘扩展 -->
<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/modules/solid-gauge.js"></script>
<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/modules/exporting.js"></script>
<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts-zh_CN.js"></script>

<!-- Layer包引用 -->
<script type="text/javascript" src="${ctxPlugins}/jquery/layer/layer.js"></script>

<!-- 公共工具包引用 -->
<script type="text/javascript" src="${ctxStatic}/js/common/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/pages/main.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common/common-map.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common/common-utils.js"></script>


<script type="text/javascript">
    var ctx = "${ctx}";
    var baseCtx = "${baseCtx}";
	//页面自适应
	$(window).load(Page.utils.autoResize);
	$(window).resize(Page.utils.autoResize);
</script>