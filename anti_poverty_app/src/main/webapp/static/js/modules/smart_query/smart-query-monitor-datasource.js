$.namespace("modules.smartquery");


modules.smartquery.datasource = {
	/*页面初始化方法*/
	init:function(){
		this.datagrid.init("#datasource_datagrid","aspire.birp.hydc.monitor.datasource.v_etl_auditor");
	},
	pieClick:function(name,x,y){
		var option={};
		option.valueName='累计';
		option.yUnit='个';
		common.report.page.reload("datasource_pie","aspire.birp.hydc.monitor.datasource.queryDelayFlg",{srcName:name},null,option);
		common.report.page.reload("datasource_bar","aspire.birp.hydc.monitor.datasource.queryDelayFlg",{srcName:name});
	},
	getBack:function(){
		var option={};
		option.valueName='累计';
		option.yUnit='个';
		option.doClick='modules.smartquery.datasource.pieClick';
		common.report.page.reload("datasource_pie","aspire.birp.hydc.monitor.datasource.queryPieData",{srcName:''},null,option);
		common.report.page.reload("datasource_bar","aspire.birp.hydc.monitor.datasource.queryDelayFlg",{srcName:''});
	},
	datagrid:{
		init:function(gridId,queryName){
			modules.smartquery.datasource.datagrid.$grid=$(gridId);
			modules.smartquery.datasource.datagrid.$grid.datagrid({
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
			$('#srcName').combobox('select','-999');
		},
		search:function () {
			var param = {};
		    param.delayflg = $("#delayflg").combobox('getValue');
			param.srcName = $("#srcName").combobox('getValue');
			modules.smartquery.datasource.datagrid.$grid.datagrid("reload",param);
		},
		 changColor:function(value,row,index){
				if('延时'==value){
					return "<font color='red'>"+ value+"<font>"
				
				}
				return value
			 }	
		}
}