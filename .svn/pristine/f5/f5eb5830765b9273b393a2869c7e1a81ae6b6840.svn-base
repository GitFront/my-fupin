<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="rp" uri="/WEB-INF/tlds/report-layout.tld" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/base.min.css">
	<style type="text/css">
		.body{
		  font-size: 14px;
		}
	</style>
	<script type="text/javascript" src="${ctxStatic}/js/common/base.min.js"></script>
	
	
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts-more.js"></script>
	<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/modules/solid-gauge.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/combo.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/area-negative.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/polar.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/bubble.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/gauge-solid.js"></script>
	<script type="text/javascript">
		$(function(){
			common.report.page.init("search");
		});
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
			$('#btn3').linkbutton({    
			    iconCls: 'icon-btn-download'
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
	<rp:search-content formid="search" btns="search:;reset:testBtn('reset');download:" singlerow="false">
		<rp:search-row>
			<rp:search-cellbox title="地市1">
				<select id="cc" class="easyui-combobox" name="dept" style="width:180px;">   
					<option value="aa">主干</option>   
					<option>广州</option>   
					<option>东莞</option>   
					<option>佛山</option>   
					<option>珠海</option>   
				</select> 
			</rp:search-cellbox>
			<rp:search-cellbox title="营销活动1">
				<input  id="cc1" name="dept" style="width:180px;"/>
			</rp:search-cellbox>
			<rp:search-cellbox title="开始时间1">
				<input id="dd1" type="text" class="easyui-datebox" style="width:180px;"/>
			</rp:search-cellbox>
			<rp:search-cellbox title="结束时间1">
				<input id="dd1" type="text" class="easyui-datebox" style="width:180px;"/>
			</rp:search-cellbox>
		</rp:search-row>
		<rp:search-row>
			<rp:search-cellbox title="地市2">
				<select id="cc" class="easyui-combobox" name="dept" style="width:180px;">   
					<option value="aa">主干</option>   
					<option>广州</option>   
					<option>东莞</option>   
					<option>佛山</option>   
					<option>珠海</option>   
				</select> 
			</rp:search-cellbox>
			<rp:search-cellbox title="营销活动2">
				<input  id="cc1" name="dept" style="width:180px;"/>
			</rp:search-cellbox>
			<rp:search-cellbox title="开始时间2">
				<input id="dd1" type="text" class="easyui-datebox" style="width:180px;"/>
			</rp:search-cellbox>
		</rp:search-row>
	</rp:search-content>

	<rp:content>
		<rp:row>
			<rp:grid-col title="测试报表">
				<table id="dg" class="easyui-datagrid"></table>
			</rp:grid-col>
		</rp:row>
	</rp:content>
</body>
</html>
