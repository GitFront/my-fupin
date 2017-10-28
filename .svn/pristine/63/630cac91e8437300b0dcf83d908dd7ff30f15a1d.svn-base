<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="rp" uri="/WEB-INF/tlds/report-layout.tld" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/base.min.css">
	
	<script type="text/javascript" src="${ctxStatic}/js/common/base.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			common.report.page.init("ff");
		});
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
		
		function testBtn(arg){
			if(!arg){
				window.alert("没有带参数");
				return;
			} 
			window.alert("参数为："+arg);
		}
		 
	</script>
</head>
<body>

	<rp:search-content formid="ff" btns="search:;reset:testBtn('reset')" singlerow="true" buttonrow="false">
			<rp:search-cellbox title="地市">
				<select id="cc" class="easyui-combobox" name="dept" style="width:180px;">   
					<option value="aa">主干</option>   
					<option>广州</option>   
					<option>东莞</option>   
					<option>佛山</option>   
					<option>珠海</option>   
				</select>  
			</rp:search-cellbox>
			<rp:search-cellbox title="营销活动">
				<input  id="cc1" name="action" style="width:180px;"/>
			</rp:search-cellbox>
			<rp:search-cellbox title="活动时间">
				<input id="dd1" name="act_time" type="text" class="easyui-datebox" style="width:180px;"/>
			</rp:search-cellbox>
	</rp:search-content>


	<rp:content>	
		<rp:row>
			<rp:col title="营销活动趋势分析" icon="Line" 
				btns="add:testBtn();delete:testBtn('del');edit:testBtn('edit');search:testBtn();download:window.alert('直接写javascript');upload:">
				<rp:panel-content height="">
					<div class="chart " id="FChart5" style="height: 320px"></div>
					<rp:panel-ps title="备注:">
						<span class="normal">访问人数</span>：统计期内，某一活动的访问人数(UV)；<br/>
						<span class="normal">参与人数</span>：统计期内，参与某一具体活动的人数；<br/>
						<span class="normal">粉丝参与率</span>：公式=参与人数/统计期末上一天的粉丝数*100%，<span class="warn">仅杭州、台州、绍兴、金华有数据</span><br/>
						<span class="normal">分享人数</span>：公式=参与人数/统计期末上一天的粉丝数*100%，<span class="warn">仅杭州、台州、绍兴、金华有数据</span><br/>
						<span class="normal">分享率</span>：公式=分享人数/参与人数*100%
					</rp:panel-ps>
				</rp:panel-content>
			</rp:col>
		</rp:row>
		
		<rp:row>
			<rp:col title="总体情况分析" icon="Gauge">
				<rp:panel-content height="300px" width="50%">
					<div class="chart " id="FChart8"></div>
				</rp:panel-content>
				<rp:panel-content height="300px" width="50%">
					<div class="chart " id="FChart11"></div>
				</rp:panel-content>
			</rp:col>
		</rp:row>
        
        <rp:row columns="true">
        	<rp:col title="活动参与率分析" icon="Polar">
        		<rp:panel-content height="300px">
        			<div class="chart" id="FChart6"></div>
        		</rp:panel-content>
        	</rp:col>
        	
        	<rp:col title="各环节转化比较" icon="Area">
        		<rp:panel-content height="300px">
        			<div class="chart" id="FChart9"></div>
        		</rp:panel-content>
        	</rp:col>
        </rp:row>
    
		<rp:row columns="true">
			<rp:col title="活动分享率分析" icon="Scatter">
				<rp:panel-content height="300px">
					<div class="chart" id="FChart7"></div>
				</rp:panel-content>
			</rp:col>
			<rp:col title="活动活跃分布对比" icon="Scatter" btns="upload:testBtn()">
				<rp:panel-content height="300px">
					<div class="chart" id="FChart10"></div>
				</rp:panel-content>
			</rp:col>
		</rp:row>
		</rp:content>
</body>
</html>
