<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/fupin-taglib-body.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>地图测试</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- map -->
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts-more.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/modules/exporting.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/highcharts-zh_CN.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highcharts-4.1.5/modules/solid-gauge.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/map.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/data.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/modules/drilldown.js"></script>
 <script type="text/javascript" src="${ctxStatic}/js/modules/script/highcharts/Highmaps-1.1.7/themes/dark-unica.js"></script>

  <script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/common/layer/layer.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common-utils.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/common/common-map.js"></script>

  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highcharts.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highmaps.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-report-page.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/Highcharts.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/charts/common-highcharts-map.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/script/maps/cn-guangdong.js"></script>
  <script type="text/javascript" src="${ctxStatic}/js/modules/test/index_map_test.js"></script>
  </head>
  
  <body>
    <div id="map"></div>

  </body>
</html>
