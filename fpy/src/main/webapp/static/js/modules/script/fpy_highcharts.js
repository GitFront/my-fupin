$.namespace("fpy.report.highcharts");

var FpyHcReports = {
	/**
	 * 示范村功能模块
	 * @type 
	 */
	exampleCountry:{
		/**
		 * 示范村仪表盘自动生成方法
		 * @param {} container 图形容器DIV的ID值
		 * @param {} data 仪表盘的具体数据 - eg: 60.4（数值）
		 */
		Gauge:function(container,data){
			var option = {
				"valueName": "完成改造比例", 
				"yUnit": "%", 
				"gauge_stops": [0, 0.99, 1],
				"exporting":false,
				"gauge_valueSize":18,
				"yVisible":false 
			};
			Highchart.create.Gauge(container,data,option);
		},
		/**
		 * 半圆饼图自动生成方法
		 * @param {} container 图形容器DIV的ID值
		 * @param {} data 具体数据 - eg: [{name:'xxx',value:'xxx'},{name:'xxx1',value:'xxx1'}]
		 */
		SemicirclePie:function(container,data){
			var total = 0;
			var finish = 0;
			$.each(data,function(n,v){
		            if(v.name === '已完成')
		            	finish = v.value;
		            total += v.value;
		     });
		    var fRate = (total === 0) ? 0:((finish/total)*100).toFixed(1); 
			var option = {
				"valueName":'村数',
				"yUnit":'个',
				"color":['#6bdb1f','#739ffa','#30507a'],
				"exporting":false,
				"legend":false,
				"pie_startAngle":90,
				"pie_endAngle":270,
				"pie_size":'550%',
				"pie_innerSize":'60%',
				"pie_center": ['50%', '0%'],
				"title":fRate + '%',
				"title_x":0,
				"title_y":60
			};
			Highchart.create.Pie(container,data,option);
		}
	}	
}





