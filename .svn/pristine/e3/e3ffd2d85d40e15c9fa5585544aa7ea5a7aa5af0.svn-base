<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="rp" uri="/WEB-INF/tlds/report-layout.tld" %>
<%@ taglib prefix="hc" uri="/WEB-INF/tlds/report-highcharts.tld" %>
<%@ taglib prefix="hm" uri="/WEB-INF/tlds/report-highmaps.tld" %>

<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/base.min.css">
<script type="text/javascript" src="${ctxStatic}/js/common/base.min.js"></script>
	

<script type="text/javascript" src="${ctxStatic}/js/common/charts/common-highcharts-map.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common/charts/common-highcharts.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common/charts/common-highmaps.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common/charts/common-report-page.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/common/export.js"></script>	


<script type="text/javascript">
	$(window).resize(function(){
		/* datagrid自适应屏幕宽度 */
		var objects = common.report.highcharts.object.map;
		var oarr = objects.values();
		$.each(oarr,function(n,obj) {
            if(obj["chartType"] === 'Grid'){
            	var grid = obj["chart"];
            	if(grid){
            		grid.datagrid("resize");
            	}            	
            }
        });
	});
</script>