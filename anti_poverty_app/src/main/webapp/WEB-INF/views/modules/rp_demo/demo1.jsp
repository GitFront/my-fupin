<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>

	<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/base.min.css">
	<script type="text/javascript" src="${ctxStatic}/js/common/base.min.js"></script>
	
 	<style>
	 .kit-col .panel .panel-content {
	        min-height: 100px;
	    }
	</style>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/dchart1.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/dchart2.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/dchart3.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/dchart4.js"></script>
</head>
<body>
	<div class="page-content" >
		<div class="kit-container">
			<div class="kit-row" name="target6" id="target6">
				<div name="target2" id="target2" class="kit-col" style="width:100%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-table"></i>指标数值
							</h2>
						</div>
						<div class="panel-content nopadding">
							<div class="panel-table" style="height: auto">
								<table cellspacing="0" cellpadding="0" width="100%" class="dataIndicators">
		                            <thead>
		                            <tr>
		                                <td>访问情况</td>
		                                <td>参与情况</td>
		                                <td>分享情况</td>
		                                <td>广告点击情况</td>
		                            </tr>
		                            </thead>
		                            <tbody>
		                            <tr class="even">
		                                <td><div class="dataName">访问人数</div><div class="dataNum"><span class="num">20000</span>人</div></td>
		                                <td><div class="dataName">参与人数</div><div class="dataNum"><span class="num">15030</span>人</div></td>
		                                <td><div class="dataName">分享人数</div><div class="dataNum"><span class="num">7680</span>人</div></td>
		                                <td><div class="dataName">广告点击人数</div><div class="dataNum"><span class="num">2394</span>人</div></td>
		                            </tr>
		                            <tr class="odd">
		                                <td><div class="dataName">访问次数</div><div class="dataNum"><span class="num">23</span>万次</div></td>
		                                <td><div class="dataName">参与次数</div><div class="dataNum"><span class="num">16</span>万次</div></td>
		                                <td><div class="dataName">分享次数</div><div class="dataNum"><span class="num">8</span>万次</div></td>
		                                <td><div class="dataName">广告点击次数</div><div class="dataNum"><span class="num">1.2</span>万次</div></td>
		                            </tr>
		                            <tr class="even">
		                                <td><div class="dataName">&nbsp;</td>
		                                <td><div class="dataName">参与转化率</div><div class="dataNum"><span class="num success">75.15%</span></div></td>
		                                <td><div class="dataName">分享率</div><div class="dataNum"><span class="num stress">38.4%</span></div></td>
		                                <td><div class="dataName">广告点击率</div><div class="dataNum"><span class="num warn">1.197%</span></div></td>
		                            </tr>
		                            </tbody>
		                        </table>
							</div>
						</div>
					</div>
				</div>
			</div>
		<div class="kit-row" >
            <div class="kit-col" style="width:50%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart2"></i>参与人年龄段分析</h2>
                        <div class="floatright">
                            <a href="javascript:;" class="button-more"></a>
                        </div>
                    </div>
                    <div class="panel-content" style="height: 300px">
                        <div class="chart" id="FChart1"></div>
                    </div>
                </div>
            </div>
            <div class="kit-col" style="width:50%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart1"></i>各环节转化比较</h2>
                        <div class="btn-group floatright">
                            <a href="javascript:;" class="kit-icon-medium kit-icon-export"></a>
                        </div>
                    </div>
                    <div class="panel-content" style="height: 300px">
                        <div class="chart" id="FChart2"></div>
                    </div>
                    
                </div>
            </div>
        </div>
		<div class="kit-row">
            <div class="kit-col" style="width:100%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart3"></i>营销活动趋势分析</h2>
                    </div>
                    <div class="panel-content" style="height: 300px">
                        <div class="chart " id="FChart4"></div>
                    </div>
                    
                </div>
            </div>
        </div>
        
        </div>
	</div>
</body>
</html>
