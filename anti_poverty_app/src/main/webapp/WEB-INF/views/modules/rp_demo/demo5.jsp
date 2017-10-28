<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="aspire" uri="/WEB-INF/tlds/report-layout.tld" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/base.min.css">
	
	<script type="text/javascript" src="${ctxStatic}/js/common/base.min.js"></script>
	
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts.js"></script>
	
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts.js"></script>
	
	<script type="text/javascript">
		
	</script>
	
	
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/combo.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/area-negative.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/polar.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/bubble.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/gauge-solid.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/scatter.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/bar-stacked.js"></script>
	<script type="text/javascript">
		var staticpath = "${ctxStatic}";
		$(function(){
			$('#cc1').combobox({    
			    required:false,    
			    multiple:true,
			    editable:true,
			    data:[{
					id: '1',
					value: '父亲节活动'
				},{
					id: '2',
					value: '风暴活动'
				},{
					id: '3',
					value: '母亲节活动'
				},{
					id: '4',
					value: '母亲节活动2015'
				},{
					id: '5',
					value: '母亲节活动2016'
				}],
				valueField: 'id',
				textField: 'value'
			});
			$('#btn1').linkbutton({    
			    iconCls: 'icon-btn-search'
			}); 
			$('#btn2').linkbutton({    
			    iconCls: 'icon-btn-repeat'
			}); 
			
			$('#dg').datagrid({ 
				title:'营销活动明细数据',
				fitColumns: true,
				rownumbers:true,
				singleSelect:true,
			    url:staticpath +'/js/modules/rp_demo/data/datagrid_data.json',    
			    columns:[[   
			        {field:'source_name',title:'地市'},    
			        {field:'act_name',title:'活动名称',width:100},    
			        {field:'act_start_time',title:'活动开始时间'},
			        {field:'act_end_time',title:'活动结束时间'},    
			        {field:'vis_usrcnt',title:'访问人数',align:'right'},    
			        {field:'join_usrcnt',title:'参与人数',align:'right'},
			        {field:'fans_rate',title:'粉丝参与率',align:'right'},    
			        {field:'share_usrcnt',title:'分享人数',align:'right'},    
			        {field:'share_rate',title:'分享率',align:'right'},    
			        {field:'act_status',title:'当前状况',align:'right'}
			    ]]    
			}); 
		});
		
		 
	</script>
</head>
<body>
	<div class="head-search">
	    <div class="search-container">
	        <div class="row singlerow">
	        	<form id="ff" method="post">   
	            <div class="cellbox">
	                <span class="title">地市：</span>
	                <div class="element">
		                <select id="cc" class="easyui-combobox" name="dept" style="width:180px;">   
						    <option value="aa">主干</option>   
						    <option>广州</option>   
						    <option>东莞</option>   
						    <option>佛山</option>   
						    <option>珠海</option>   
						</select>  
					</div>
	            </div>
	            <div class="cellbox">
	                <span class="title">营销活动：</span>
	                <div class="element">
	                	<input  id="cc1" name="action" style="width:180px;"> 
	                </div>
	            </div>
	            <div class="cellbox">
	                <span class="title">活动时间：</span>
	                <div class="element">
	                    <input id="dd1" name="act_time" type="text" class="easyui-datebox" style="width:180px;"></input>
	                </div>
	            </div>
	            <div class="cellbox">
	                <a id="search" href="#">查  询</a>
	                <a id="reset" href="#">重  置</a>
	            </div>
	            </form>
	        </div>	        
	    </div>
	</div>

	<div class="page-content">
		<div class="kit-container">
		
		<div class="kit-row">
            <div class="kit-col" style="width:100%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart3"></i>营销活动趋势分析</h2>
                        <div class="btn-group floatright">
                            <a href="javascript:;" class="kit-icon-medium kit-icon-add1"></a>
                            <a href="javascript:;" class="kit-icon-medium kit-icon-edit1"></a>
                            <a href="javascript:;" class="kit-icon-medium kit-icon-del1"></a>
                        	<a href="javascript:;" class="kit-icon-medium kit-icon-search1"></a>
                            <a href="javascript:;" class="kit-icon-medium kit-icon-import"></a>
                            <a href="javascript:;" class="kit-icon-medium kit-icon-export"></a>
                            
                        </div>
                    </div>
                    <div class="panel-content" >
                        <div class="chart " id="FChart5" style="height: 340px"></div>
                        <div class="ps" >
                            <div class="title">备注:</div>
                            <p><span class="normal">访问人数</span>：统计期内，某一活动的访问人数(UV)；<br/>
							   <span class="normal">参与人数</span>：统计期内，参与某一具体活动的人数；<br/>
							   <span class="normal">粉丝参与率</span>：公式=参与人数/统计期末上一天的粉丝数*100%，<span class="warn">仅杭州、台州、绍兴、金华有数据</span><br/>
							   <span class="normal">分享人数</span>：公式=参与人数/统计期末上一天的粉丝数*100%，<span class="warn">仅杭州、台州、绍兴、金华有数据</span><br/>
							   <span class="normal">分享率</span>：公式=分享人数/参与人数*100%
                            </p>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
        
        <div class="kit-row">
            <div class="kit-col" style="width:100%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart7"></i>总体情况分析</h2>
                    </div>
                    <div class="panel-content width50" style="height: 300px;">
                        <div class="chart " id="FChart8"></div>
                    </div>
                    <div class="panel-content width50" style="height: 300px;">
                        <div class="chart width50" id="FChart11"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="kit-row" >
            <div class="kit-col" style="width:50%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart6"></i>活动参与率分析</h2>
                    </div>
                    <div class="panel-content" style="height: 300px">
                        <div class="chart" id="FChart6"></div>
                    </div>
                </div>
            </div>
            <div class="kit-col" style="width:50%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart4"></i>各环节转化比较</h2>
                    </div>
                    <div class="panel-content" style="height: 300px">
                        <div class="chart" id="FChart9"></div>
                    </div>
                    
                </div>
            </div> 
        </div>
        
		<div class="kit-row" >
            <div class="kit-col" style="width:50%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart5"></i>活动参与率分析</h2>
                    </div>
                    <div class="panel-content" style="height: 300px">
                        <div class="chart" id="FChart7"></div>
                    </div>
                </div>
            </div>
            <div class="kit-col" style="width:50%">
                <div class="panel">
                    <div class="panel-head">
                        <h2><i class="kit-icon-medium kit-icon-chart5"></i>各环节转化比较</h2>
                    </div>
                    <div class="panel-content" style="height: 300px">
                        <div class="chart" id="FChart10"></div>
                    </div>
                </div>
            </div> 
        </div>
        
        <div class="kit-row">
            <div class="kit-col" style="width:100%">
                <div class="panel-content nopadding">
                    <table id="dg" class="easyui-datagrid"></table>                         
                </div>
            </div>
        </div>

		</aspire:rp-content>
        
        </div>
	</div>
</body>
</html>
