$.namespace("modules.smartquery");


modules.smartquery.process = {
	/*页面初始化方法*/
	init:function(){
		this.datagrid.init("#process_datagrid","aspire.birp.hydc.monitor.process.v_etl_taskFlow");
		$('#tkLayerName').combobox('select','-999');
	},
	pieClick:function(name,x,y){
		var option={};
		option.valueName='累计';
		option.yUnit='个';
		common.report.page.reload("process_pie","aspire.birp.hydc.monitor.process.queryDelayFlg",{tkLayerName:name},null,option);
		common.report.page.reload("process_bar","aspire.birp.hydc.monitor.process.queryDelayFlg",{tkLayerName:name});
	},
	getBack:function(){
		var option={};
		option.valueName='累计';
		option.yUnit='个';
		option.doClick='modules.smartquery.process.pieClick';
		common.report.page.reload("process_pie","aspire.birp.hydc.monitor.process.queryPieData",{tkLayerName:''},null,option);
		common.report.page.reload("process_bar","aspire.birp.hydc.monitor.process.queryDelayFlg",{tkLayerName:''});
	},
	datagrid:{
		init:function(gridId,queryName){
			modules.smartquery.process.datagrid.$grid=$(gridId);
			modules.smartquery.process.datagrid.$grid.datagrid({
				idField:'id',    
			    fitColumns:true,
			    method:'post',
			    striped : true,
				loadMsg : '数据加载中...',
				pagination : true,
				pageSize : '15',
				pageList : [10, 15, 20, 30, 40 ],
				border : false,
				singleSelect:true,
			    url:ctx+'/a/component/gridData?queryName='+queryName,
			    toolbar:'#tb'
			});
		},
		search:function () {
			var param = {};
		    param.delayflg = $("#delayflg").combobox('getValue');
			param.tkLayerName = $("#tkLayerName").combobox('getValue');
			modules.smartquery.process.datagrid.$grid.datagrid("reload",param);
		},
		 changColor:function(value,row,index){
			if('延时'==value){
				return "<font color='red'>"+ value+"<font>"
			
			}
			return value
		 }	
	}
	
}