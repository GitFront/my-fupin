$.namespace("common.report.highcharts");
/*
 * document.write("<script type="text/javascript" src="${ctxPlugins}/jquery/jquery-1.9.1.js"></script>");
 * document.write("<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts.js"></script>");
 * document.write("<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts-more.js"></script>");
 * document.write("<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/modules/exporting.js"></script>");
 * document.write("<script type="text/javascript" src="${ctxPlugins}/heighcharts-4.1.5/highcharts-zh_CN.js"></script>"); 
*/
/*判断数组中是否包含某个元素*/
Array.prototype.contains = function (obj) {
  var i = this.length;
  while (i--) {
      if (this[i] === obj) {
          return true;
      }
  }
  return false;
}

Highcharts.setOptions({
    lang:{
      	drillUpText:"返回 > {series.name}"
    }
});

/**
 * Highmaps 二次封装工具类
 * @type 
 */
var Highmap = {	
	 
	  /**
	   * 数据格式化方法
	   * @type 
	   */
	  ChartDataFormate: {
	      /**
	       * 处理分组数据，数据格式 : name：XXX，group：XXX，value：XXX用于折线图、柱形图（分组，堆积）
	       * @param {} data 待办理json数据
	       * @return {}
	       */
	      FormatGroupData: function (data) {
	      	  if(!common.utils.isArray(data)) data = [];
	          var names = new Array();
	          var groups = new Array();
	          var series = new Array();
	          for (var i = 0; i < data.length; i++) {
	              if (!names.contains(data[i].name))
	                  names.push(data[i].name);
	              if (!groups.contains(data[i].group))
	                  groups.push(data[i].group);
	          }
	          
	          for (var i = 0; i < groups.length; i++) {
	              var temp_series = {};
	              var groupName = groups[i];
	              var visible = true;
	              if(groups[i].endsWith('|hide')){
	              	visible = false;
	              	groupName = groups[i].replace('|hide','');
	              }          
	              var temp_data = new Array();
	              for (var j = 0; j < data.length; j++) {
	                  for (var k = 0; k < names.length; k++)
	                      if (groups[i] == data[j].group && data[j].name == names[k])
	                      		temp_data.push(data[j].value);
	                          
	              }
	              temp_series = { name: groupName, data: temp_data,visible:visible };
	              series.push(temp_series);
	          }
	          
	          return { category: names, series: series };
	      }
	  },
	  MapOptionTemplates: {
	  	  
	  	  /**
	  	   * 地图参数
	  	   * @param {} container 地图指向DIV
	  	   * @param {} data 地图数据信息
	  	   * @param {} mapData 地图数据信息
	  	   * @param {} joinBy 地图与数据对应字段
	  	   * @param {} opt 地图参数
	  	   * @return {}
	  	   */
	      Map: function (container,data,mapData,joinBy,opt) {
	          /*var line_datas = Highmap.ChartDataFormate.FormatGroupData(data);*/
	          var pointFormat = Highmap.utils.convValueFormat(opt.valueMapper);
	          var option = {
		        chart : {
	          	  	renderTo:container
		        },
		        title : {
		            text : opt.title
		        },
		        credits: {
		            enabled: false
		        },
		        subtitle: {
		            text: null
		        },
		        legend: opt.legend,
		        colorAxis: {
		            min: 0,
		            stops: opt.colorStops
		        },
		        colors: function(color) {
		            return {
		                radialGradient: {
		                    cx: 0.5,
		                    cy: 0.3,
		                    r: 0.7
		                },
		                stops: [
		                    [0, color],
		                    [1, Highcharts.Color(color).brighten(-0.3).get('rgb')]
		                ]
		            };
		        }(),
		        mapNavigation: {
		            enabled: opt.navigation,
		            enableButtons: opt.navigationButton
		        },
		        plotOptions: {
			         series: {
			            cursor: opt.cursor,
			            events: {
			            	click: function (e) {
			            		var funcName = opt.doClick;
								if(common.utils.isFunction(funcName)){
									eval(funcName + "(e.point)");
								}
			            	}
			            }
					 }
			    },
		        series : [{
		            data : data,
		            mapData: Highcharts.maps[mapData],
		            joinBy:joinBy,
		            name: opt.mapName,
		            color: Highcharts.getOptions().colors[2],
		            states: {
		                hover: {
		                    color: '#BADA55'
		                }
		            },
		            dataLabels: {
		                enabled: true,
		                format: '{point.properties.name}'
		            },
		            tooltip: {
		               headerFormat: '',
		               pointFormat: pointFormat
		            }
		        }]
		    };
	        return option;
	      }
	  },
	  create:{
	  	/**
	  	 * 创建报表的初始化方法
	  	 * @param {} container 地图指向DIV
	  	 * @param {} data 地图数据信息
	  	 * @param {} mapData 地图数据信息
	  	 * @param {} joinBy 地图与数据对应字段
	  	 * @param {} option
	  	 * @return {}
	  	 */
	  	init:function(container,data,mapData,joinBy, option){
	  		/*清洗并规范化自定义配置项*/
	  		var type = "Map";
	  		var option_ = Highmap.utils.defaultOption(option);
	  		option_["container"] = container;
	  		option_["optionType"] = type;
	  		var opt = eval("Highmap.MapOptionTemplates."+type+"(container,data,mapData,joinBy,option_)");
	    	var map = new Highcharts.Map(opt);
	    	var obj = {
	    		chart:map,
	    		chartType:type,
	    		option:option_,
	    		data:data,
	    		mapData:mapData,
	    		joinBy:joinBy
	    	};
	    	/*对报表对象及自定义配置项进行管理*/
	    	var id = common.report.highcharts.object.add(obj);    	
	    	return id;
	  	}
	  	
	  },
	  
	  /**
	   * 对报表数据重新加载
	   * @param {} chartid 报表ID
	   * @param {} data 重新加载的数据(可选)
	   * @param {} newOption 新的自定义项(可选)
	   * @param {} type 变换报表类型(可选)
	   * @return 是否成功更新对象
	   */
	  reload: function (chartid,data,newOption) {
	      var obj = common.report.highcharts.object.get(chartid);
	      if(obj != null){
	      	  var chart = obj.chart;
	      	  chart.showLoading();
	      	  /*提取报表对象参数*/
			  var opt = obj.option;
			  var mapData = obj.mapData;
			  var joinBy = obj.joinBy;
			  var container = opt.container;
			  var type = opt.optionType;
			  
			  if(!common.utils.isDefined(data)){
			  	  data = obj.data;
			  }
			  if(common.utils.isDefined(newOption)){
			  	  opt = Highmap.utils.defaultOption(newOption);
			  	  opt["container"] = container;
	  			  opt["optionType"] = type;
			  }
			  
			  var option = eval("Highmap.MapOptionTemplates."+type+"(container,data,mapData,joinBy,opt)");
			  		  
			  chart.hideLoading();
			  /*销毁图表对象 */	
			  chart.destroy();
			  chart= new Highcharts.Map(option);
			  
		      /*对报表对象进行更新管理*/
			  obj["chart"] = chart;
			  obj["option"] = opt;
			  obj["data"] = data;
	          
		      common.report.highcharts.object.reset(chartid,obj); 
		      return true;
	      }else {
	      	  return false;
	      }
	      
	  },
	  /**
	   * 销毁报表对象
	   * @param {} chartid 报表ID
	   * @return 是否成功销毁对象
	   */
	  remove: function (chartid) {
	  	  /*销毁图表对象 */
	  	  if(common.report.highcharts.object.containsKey(chartid)){
	  	  	var obj = common.report.highcharts.object.get(chartid);
	  	  	obj.chart.destroy();
		  	common.report.highcharts.object.remove(chartid);
		  	return true;
	  	  }else{
	  	  	return false;
	  	  }	  
	  },
	  
	  ajax:{
	  	/**
	  	 * ajax初始化方法
	  	 * @param {} container 报表显示指向
	  	 * @param {} url 请求url
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}
	  	 * @param {} mapData 地图数据信息
	  	 * @param {} joinBy 地图与数据对应字段
	  	 * @param {} option 自定义配置项
	  	 * @return {} 
	  	 */
	  	init:function(container,url,parameters,mapData,joinBy,option){
	  		/*创建空的报表，并获取报表对象*/
	  		var id = Highmap.create.init(container,[],mapData,joinBy,option);
	  		var obj = common.report.highcharts.object.get(id);
	  		var chart = obj.chart;
	  		chart.showLoading();
	  		parameters = common.utils.strToJson(parameters);
	  		$.ajax({
	             type: "POST",
	             url: url,
	             data: parameters,
	             cache:true,
	             dataType: "json",
	             success: function(data){
	             	
	             	Highmap.reload(id,data);
	             	/*动态加载数据*/
	             	/*chart = Highmap.utils.initSeries(chart,data,opt,type);*/
	                chart.hideLoading();
	                /*管理报表对象信息*/
	                obj = common.report.highcharts.object.get(id);
			        obj["url"] = url;
			        obj["parameters"] = parameters;
	                common.report.highcharts.object.reset(id,obj);
	             }
	         });
	         return id;
	  	},
	  	
	  	/**
	  	 * 通过ajax重新加载数据
	  	 * @param {} container 报表显示指向
	  	 * @param {} url(可选) 请求url
	  	 * @param {} parameters(可选) 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}
	  	 * @param {} newOption(可选) 更新后的图表参数项
	  	 * @return {Boolean}
	  	 */
	  	reload:function(chartid,url,parameters,newOption){
	  		var obj = common.report.highcharts.object.get(chartid);
		    if(obj != null){
		    	if(common.utils.isEmpty(url)){
		    		  /*使用缓存数据进行处理*/
		    		 var data = obj.data;
		    		 return Highmap.reload(chartid,data,newOption);		    		
		    	}else{
		    		 /*进行后台数据更新操作*/
		    		 var chart = obj.chart;
		    		 chart.showLoading();
					 if(common.utils.isEmpty(url)) url = obj.url;
					 if(common.utils.isEmpty(parameters)) parameters = obj.parameters;					  
		  		
			  		 parameters = common.utils.strToJson(parameters);
			  		 $.ajax({
			             type: "POST",
			             url: url,
			             data: parameters,
			             cache:true,
			             dataType: "json",
			             success: function(data){
			             	Highmap.reload(chartid,data,newOption);
			                chart.hideLoading();
			                /*更新管理报表对象信息*/
			                obj = common.report.highcharts.object.get(chartid);
			                obj["url"] = url;
			                obj["parameters"] = parameters;
			                common.report.highcharts.object.reset(chartid,obj);
			                return true;
			             }
			          });
		    	}
		      	  
		    }else {
		      	  return false;
		    }
	  	}
	  	
	  },
	  
	  mybatis:{
	  	
	  	/**
	  	 * 通过mybatis初始化地图图表
	  	 * @param {} container 报表指向的divid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{key:'key?',value:'value?'}
	  	 * @param {} mapData 地图数据信息
	  	 * @param {} joinBy 地图与数据对应字段
		 * @param {} option 自定义配置项
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Map:function(container,queryName,parameters,mapper,mapData,joinBy,option){
	  		var type = 'Map';
			var url = ctx+"/a/report/highmaps/mapData/load";	
	  		/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(parameters);
			params.mapper = common.utils.strToJson(mapper);
			params.joinBy = joinBy;

	  		return Highmap.ajax.init(container,url,params,mapData,joinBy,option);
	  	},
	    
	  	/**
	  	 * 通过mybatis重新加载数据
	  	 * @param {} chartid 生成报表时返回的ID值
	  	 * @param {} queryName(可选) mybatis查询名称
	  	 * @param {} parameters(可选) 自定义查询参数。格式为{key1:'value1',key2:'value2'}
	  	 * @param {} mapper(可选) 数据字段对应信息。格式为{value:'value?'}
	  	 * @param {} newOption(可选) 自定义配置项
	  	 * @return {Boolean} 返回是否加载成功
	  	 */
	  	reload:function(chartid,queryName,parameters,mapper,newOption){
	  		var obj = common.report.highcharts.object.get(chartid);
		    if(obj != null){
				var url = obj.url;				
		    	/*进行后台数据更新操作*/
		    	var params = obj["parameters"];
		    	if(!common.utils.isEmpty(queryName)) params["queryName"] = queryName;
		    	if(!common.utils.isEmpty(parameters)) params["param"] = common.utils.strToJson(parameters);
		    	if(!common.utils.isEmpty(mapper)) params["mapper"] = common.utils.strToJson(mapper);
		    	return Highmap.ajax.reload(chartid,url,params,newOption); 				
		    }else {
		      	  return false;
		    }
	  	}	  	
	  },
	  
	  
	  /**
	   * 内部工具类
	   * @type 
	   */
	  utils:{
	  	 /**
		   * 处理自定义配置项的默认值
		   * @param {} option
		   * @return {}
		   */
		  defaultOption:function(option){
		  	
		  	/* 自定义配置的默认值设置 */
			var defaultOpt = {
				title : '', /* 报表标题(默认为空) */
				mapName:'', /* 地图名称(默认为空) */
				colorStops:['#EFEFFF',Highcharts.getOptions().colors[0],'#006cee'], /* 步长颜色定义 */
				valueDecimal : 0, /* 数据精度(默认为不保留小数位){最高只支持4位小数} */
				percentDecimal : 2,/* 显示百分比数据精度(默认为保留2位小数){最高只支持4位小数} */
				legend : true, /* 是否显示图例(默认为true) */
				legendFloat : false, /*是否显示图例浮动*/
				legendAlign : 'right', /*图例显示方位(默认为right) 左边:left，右边:right，上方:top，下方:bottom*/
				navigation:true, /* 是否显示缩放导航(默认为true) */
				navigationButton:true, /* 是否显示缩放导航按钮(默认为true) */
				valueMapper:[{key:'value',name:'数值'}], /*数据显示及其对应关系*/
				doClick : '' /*传入方法名，可以在图表数据点点击时触发监听事件，附带参数为chart对象，eg:function testload(chart)*/
			};
			/*设置传入自定义参数默认值*/
			var option_ = common.utils.setDefaultOption(option,defaultOpt);
			
			/* legend图例显示方式转换 */
			option_["legend"] = this.convLegend(option_);
			
			/*转换图表步长信息*/
			option_["colorStops"] = this.convColorstops(option_.colorStops);

			if(!option_.navigation) option_["navigationButton"] = false;
			
			/* 设置数据点鼠标样式 */
			option_["cursor"] = '';
			if (common.utils.isFunction(option_["doClick"])) {
				option_["cursor"] = 'pointer';
			}
			return option_;
		  	  
		  },	  
		  /**
			 * legend图例显示方式转换
			 * @param {} opt
			 */
		  convLegend:function(opt){	  	  
			  var legend = {};
			  if(!opt.legend) {
			  	legend["enabled"] = false;
			  	return legend;
			  }
			  legend["enabled"] = true;
			  legend["floating"] = opt.legendFloat;
		  	  switch(opt.legendAlign)
			  {
				case 'left':
				  legend["align"] = 'left';
				  legend["layout"] = 'vertical';
				  legend["verticalAlign"] = 'middle';
				  break;
				case 'top':
				  legend["layout"] = 'horizontal';
				  legend["verticalAlign"] = 'top';
				  break;
				case 'bottom':
				  legend["layout"] = 'horizontal';
				  legend["verticalAlign"] = 'bottom';
				  break;
				default:
				  legend["align"] = 'right';
				  legend["layout"] = 'vertical';
				  legend["verticalAlign"] = 'middle';	
			  }
			  return legend;			  
		  },
		  /**
		   * 转换仪表盘步长数据格式
		   * @param {} stops
		   * @param {} greatValue
		   * @return {}
		   */
		  convColorstops:function(colorstops){
		  	  /*默认颜色步长*/
		  	  var defaultStop = [[0, '#EFEFFF'],[0.5, Highcharts.getOptions().colors[0]],[1, '#006cee']];		  	  
		  	  if(!common.utils.isArray(colorstops)) return defaultStop;
		  	  var stops = new Array();
			  var len =  colorstops.length;
			  /*定义步长颜色*/
			  switch(len)
			  {
				case 0:
				  stops = defaultStop;
				  break;
				case 1:
				  stops = defaultStop;
				  break;
				default:
				  var s = 1/(len-1);
				  for (var i = 0; i < len; i++) {
				  	 stops.push([s*i,colorstops[i]]);
				  }				  
			  }	  
			  return stops;			  
		  },
		  
		  /**
		   * 转换图示展示方式
		   * @param {} mapper 图示数据的对应map eg.:[{key:'value',name:'用户数'},{key:'value1',name:'4G用户数'}]，第一个数据为主数据信息，单个信息时可为{key:'value',name:'用户数'}
		   * @return {} 返回地图可视化format字段
		   */
		  convValueFormat:function(mapper){
				
				var pointFormat = '<p><b style="font-weight:bold;color:#F58221;font-size:13px">{point.properties.name}<b/><br>';
			    if(common.utils.isArray(mapper)){
			    	for (var index = 0; index < mapper.length; index++) {
			    		if(index === 0){
			    			pointFormat += '<b style="color:#0000FF;font-size:12px">'+mapper[index].name+'：</b><b style="font-weight:bold;color:#0000FF;">{point.'+mapper[index].key+'}</b><br/>';
			    		}else{
			    			pointFormat += mapper[index].name+'：<b>{point.'+mapper[index].key+'}</b><br/>';
			    		}
			    	}
			    	pointFormat += '</p>';
			    }else if (common.utils.isJson(mapper)){
			    	pointFormat += '<b style="color:#0000FF;font-size:12px">'+mapper.name+'：</b><b style="font-weight:bold;color:#0000FF;">{point.'+mapper.key+'}</b><br/></p>';
			    }
			    return pointFormat;
		  }			
	  }	  
	  
};
