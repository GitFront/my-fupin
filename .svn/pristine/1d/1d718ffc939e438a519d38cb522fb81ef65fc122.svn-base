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
	/*当前地图ID标识*/
	mapid:'',
	/*当前地图层级KEY*/
	levelid:'',
	levelname:'',
	/*地图全路径层级*/
	levarray:new Array(),
	/*地图全路径名称层级*/
	namearray:new Array(),
	mapValue:{},
	pointFormat:{},
	mapObject:{},
	setLoading:[],
	showPoint:false,
	valueType:'map',
	/**
	 * 当前地图数据的顶级父节点数据信息
	 */
	 topMapNode:common.utils.guid(),
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
	      },
	      /**
	       * 归类地图数据
	       * @param {} data 完整的地图数据
	       * @param {} parentKey 父级数据KEY
	       */
	      FormatMapData: function (data,parentKey){
	      		var mdata = {};
	      		var topValues = new Array();
	      		var type = Highmap.valueType;
	      		$.each(data, function (i) {
	      			var item = this;
	      			if(type === 'mapbubble' && !item.hasOwnProperty('z')){
	      				item['z'] = item.value;
	      			}
	      			if((item.hasOwnProperty(parentKey) && item[parentKey] === '-1') || !item.hasOwnProperty(parentKey)){
	      				topValues.push(item);
	      			}else{
	      				var pk = item[parentKey];
	      				if(mdata.hasOwnProperty(pk)){
	      					mdata[pk].push(item);
	      				}else{
	      					mdata[pk] = new Array();
	      					mdata[pk].push(item);
	      				}
	      			}
	            });
	            mdata[Highmap.topMapNode]=topValues;
	            return mdata;
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
	      	  Highmap.mapValue = data;
	      	  Highmap.valueType = opt.valueType;
	      	  Highmap.showPoint = opt.showPoint;
	      	  Highmap.setLoading = opt.setLoading;
	          var mapValue_ = Highmap.ChartDataFormate.FormatMapData(data,opt.parentKey);
	          Highmap.pointFormat = Highmap.utils.convValueFormat(opt.valueMapper,opt.valueSuffix);
	          var series = Highmap.utils.createMapSeries(opt.mapName,mapValue_[Highmap.topMapNode],mapData,joinBy);

	          var option = {
		        chart : {
		        	height:opt.height,
		        	/*width:opt.width,*/
		        	backgroundColor: '#151618',
	          	  	renderTo:container,
	          	  	events: {
		                drilldown: function (e) {
		                	window.alert('drilldown');
		                },
		                drillup: function (e) {
		                	if(common.utils.isDefined(this.subtitle))
		                    	this.setTitle(null, { text: e.seriesOptions['name'] });

	    					/*上钻重新加载数据
		                    Highmap.loadData(Highmap.mapid,Highmap.mapValue,null,true);*/

		                    if(Highmap.showPoint)
		                    	Highmap.mapObject.series[0].remove(false);

		                    /*保存当前的层级ID及地图全路径*/
				            Highmap.levarray.pop();
				            Highmap.namearray.pop();
	    					Highmap.levelid = Highmap.levarray[Highmap.levarray.length-1];
	    					Highmap.levelname = e.seriesOptions['name'];

		                    var funcName = opt.doDrillup;
							if(common.utils.isFunction(funcName)){
								eval(funcName + "(e.point,Highmap.levelid)");
							}
		                }
		            }
		        },
		        title : {
		            text : ''
		        },
		        credits: {
		            enabled: false
		        },
		        subtitle: {
		            text: '',
		            floating: true,
		            align: 'right',
		            y: 50,
		            style: {
		                fontSize: '16px'
		            }
		        },
		        legend: opt.legend,
		        colorAxis: {
		            min: 0,
		            stops: opt.colorStops
		        },
		        loading: {
					labelStyle: { "fontWeight": "bold", "position": "relative", "top": "45%" },
					showDuration: 10,
					style: {
						position: 'absolute',
						backgroundColor: '#c1c1c1',
						opacity: 0.2,
						textAlign: 'center'
					}
				},
		        exporting : {enabled: false},
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
			            		if (!e.seriesOptions) {
				            		/*判断是否需要启动地图下钻功能*/
					            	if(opt.isDrilldown){
					            		var cname = e.point.properties['name'];
					            		var key = e.point.properties[joinBy];
				                    	var dmv_ = Highmap.ChartDataFormate.FormatMapData(Highmap.mapValue,opt.parentKey);
										var drillKey=e.point.properties[opt.drillKey];
										if(common.utils.isDefined(drillKey)){
											Highmap.utils.mapDrilldown(e.point,drillKey,joinBy,dmv_[key]);
						            		/*保存当前的层级ID及地图全路径*/
								            Highmap.levarray.push(key);
								            Highmap.namearray.push(cname);
					    					Highmap.levelid = key;
						    				Highmap.levelname = cname;
										}

					            	}
					            	if(opt.title)
					            		Highmap.mapObject.setTitle(null, { text: cname });
				            		var funcName = opt.doClick;
									if(common.utils.isFunction(funcName)){
										eval(funcName + "(e.point,Highmap.levelid)");
									}
			            		}
			            	}
			            }
					 }
			    },
		        series : series,
		        drilldown: {
		            activeDataLabelStyle: {
		                color: '#FFFFFF',
		                textDecoration: 'none',
		                textShadow: '0 0 3px #000000'
		            },
		            animation:true,
		            drillUpButton: {
		                relativeTo: 'spacingBox',
		                position: {
		                    y: 0,
		                    x: 1000
		                },
		                theme: {
		                    fill: 'white',
		                    'stroke-width': 1,
		                    stroke: 'silver',
		                    r: 0,
		                    states: {
		                        hover: {
		                            fill: '#bada55'
		                        },
		                        select: {
		                            stroke: '#039',
		                            fill: '#bada55'
		                        }
		                    }
		                }
		            }
		        }
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
	    	Highmap.mapObject = new Highcharts.Map(opt);
	    	/*保存当前及地图的全路径信息*/
	    	Highmap.levarray.push(Highmap.topMapNode);
	    	Highmap.namearray.push(option_.mapName);
	    	Highmap.levelid = Highmap.topMapNode;
	    	Highmap.levelname = option_.mapName;

	    	/** TODO 加载贫困村信息*/
	    	if(Highmap.showPoint)
		    	Highmap.utils.loadMappoint();

	    	if(option_.title)
	    		Highmap.mapObject.setTitle(null, {text:option_.mapName});
	    	var obj = {
	    		chart:Highmap.mapObject,
	    		chartType:type,
	    		option:option_,
	    		data:data,
	    		mapData:mapData,
	    		joinBy:joinBy
	    	};
	    	/*对报表对象及自定义配置项进行管理*/
	    	Highmap.mapid = common.report.highcharts.object.add(obj);
	    	return Highmap.mapid;
	  	}

	  },

	  /**
	   * 重新加载报表数据
	   * @param {} chartid 报表ID
	   * @param {} data 重新加载的数据
	   * @return 是否成功更新对象
	   */
	  loadData: function (chartid,data,valueMapper,isDrillup) {
	      var obj = common.report.highcharts.object.get(chartid);
	      if(obj != null){
	      	  var chart = obj.chart;
	      	  Highmap.utils.mapShowLoading();
	      	  var opt = obj.option;
			  if(!common.utils.isDefined(data)){
			  	  data = obj.data;
			  }
			  if(!common.utils.isDefined(valueMapper)){
			  	  valueMapper = obj.valueMapper;
			  }

			  Highmap.mapValue = data;
			  var mapValue_ = Highmap.ChartDataFormate.FormatMapData(data,opt.parentKey);
			  Highmap.pointFormat = Highmap.utils.convValueFormat(valueMapper,opt.valueSuffix);

			  if(isDrillup){
			  	chart.series[0].setData(mapValue_[Highmap.levelid]);
			  	chart.series[1].setData(mapValue_[Highmap.levarray[Highmap.levarray.length-2]]);
			  }else{
			  	chart.series[0].setData(mapValue_[Highmap.levelid]);
			  }

			  /*更新地图全局数据*/
			  obj["chart"] = chart;
			  obj["data"] = data;
			  common.report.highcharts.object.reset(chartid,obj);
			  chart.redraw(true);
			  Highmap.utils.mapHideLoading();
	          /*var pointFormat = Highmap.utils.convValueFormat(opt.valueMapper,opt.valueSuffix);
	          Highmap.utils.mapShowLoading();
			  // 加载区域数据
			  $.ajax({
						type : "GET",
						url : ctx + "/static/js/maps/json_map/" + key + ".geo.json",
						contentType : "application/json; charset=utf-8",
						dataType : 'json',
						crossDomain : true,
						success : function(json) {
							chart.hideLoading();
							chart.addSeries({
									data : Highmap.mapValue[key],
									mapData : Highcharts.geojson(json),
									joinBy : joinBy,
									name : cname,
									color : Highcharts.getOptions().colors[2],
									states : {
										hover : {
											color : '#BADA55'
										}
									},
									dataLabels : {
										enabled : true,
										format : '{point.properties.name}'
									},
									tooltip : {
										headerFormat : '',
										pointFormat : pointFormat
									}
								});
							 对报表对象进行更新管理
						  	obj["chart"] = chart;
						  	obj["data"] = Highmap.mapValue[key];
					      	common.report.highcharts.object.reset(chartid,obj);
						},
						error : function(XMLHttpRequest, textStatus,errorThrown) {
							common.utils.showErrorMsg('缺少地图数据，无法完成“' + cname + '”的区域细化！');
							Highmap.reload(Highmap.mapid);
						}
					});*/
	      }
	  },

	  /**
		 * 对整个报表对象进行重新加载
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
	      	  Highmap.utils.mapShowLoading();
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

			  Highmap.utils.mapHideLoading();
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
	  		Highmap.utils.mapShowLoading();
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
	                Highmap.utils.mapHideLoading();
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
		    		 Highmap.utils.mapShowLoading();
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
			                Highmap.utils.mapHideLoading();
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
				title : true, /* 是否显示报表标题(默认为显示) */
				mapName:'', /* 地图名称(默认为空) */
				height:500, /* 地图高度(默认为空) */
				width:800, /* 地图宽度(默认为空) */
				showPoint:false, /*是否显示地图标记点*/
				colorStops:['#EFEFFF',Highcharts.getOptions().colors[0],'#006cee'], /* 步长颜色定义 */
				valueType:'map', /*数据的展示方式(默认为map，按色阶生成对比)*/
				valueDecimal : 0, /* 数据精度(默认为不保留小数位){最高只支持4位小数} */
				percentDecimal : 2,/* 显示百分比数据精度(默认为保留2位小数){最高只支持4位小数} */
				valueSuffix: '', /* 显示数据单位(默认为空) */
				setLoading:[], /*配置变更加载方法*/
				legend : true, /* 是否显示图例(默认为true) */
				legendFloat : false, /*是否显示图例浮动*/
				legendAlign : 'right', /*图例显示方位(默认为right) 左边:left，右边:right，上方:top，下方:bottom*/
				navigation:true, /* 是否显示缩放导航(默认为true) */
				navigationButton:true, /* 是否显示缩放导航按钮(默认为true) */
				valueMapper:[{key:'value',name:'数值'}], /*数据显示及其对应关系*/
				isDrilldown:false,/* 是否启动地图下钻功能(默认为false) */
				parentKey:'parentKey',/* 是否地图数据父级主键 (默认为parentKey) */
				drillKey:'drill-key',/* 是否地图下钻主键 (默认为drill-key) */
				doClick : '', /*传入方法名，可以在图表数据点点击时触发监听事件，附带参数为chart对象，eg:function testload(chart)*/
				doDrillup : '' /*传入方法名，可以在图表点击返回时触发监听事件，附带参数为chart对象，eg:function testload(chart)*/
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
		   * @param {} valueSuffix 数据单位
		   * @return {} 返回地图可视化format字段
		   */
		  convValueFormat:function(mapper,valueSuffix){

				var pointFormat = '<p><b style="font-weight:bold;color:#F58221;font-size:13px">{point.properties.name}<b/><br>';
			    if(common.utils.isArray(mapper)){
			    	for (var index = 0; index < mapper.length; index++) {
			    		if(index === 0){
			    			pointFormat += '<b style="color:#f4e925;font-size:12px">'+mapper[index].name+'：</b><b style="font-weight:bold;color:#f4e925;">{point.'+mapper[index].key+'} '+ valueSuffix + '</b><br/>';
			    		}else{
			    			pointFormat += mapper[index].name+'：<b>{point.'+mapper[index].key+'} '+ valueSuffix + '</b><br/>';
			    		}
			    	}
			    	pointFormat += '</p>';
			    }else if (common.utils.isJson(mapper)){
			    	pointFormat += '<b style="color:#f4e925;font-size:12px">'+mapper.name+'：</b><b style="font-weight:bold;color:#f4e925;">{point.'+mapper.key+'} '+ valueSuffix + '</b><br/></p>';
			    }
			    return pointFormat;
		  },

		  /**
		   * 地图下钻事件
		   * @param {} e map属性
		   * @param {} drillKey 下钻主键
		   * @param {} joinBy 数据对应主键
		   * @param {} data 列表数据
		   * @return {Boolean}
		   */
		  mapDrilldown:function(e,drillKey,joinBy,data){
			    if (!e.seriesOptions) {

	                var key = e.properties[joinBy];
	                var cname=e.properties["name"];
	                Highmap.utils.mapShowLoading();
	                /*var mapfile = ctx+"/static/js/maps/json_map/"+drill+".geo.json";
	                var fso = new ActiveXObject("Scripting.FileSystemObject");
					if(!fso.FileExists(mapfile)){
						common.utils.showErrorMsg('缺少地图数据，无法完成“'+cname+'”的区域细化！');
					}else{*/
						// 加载区域数据
			            $.ajax({
			                type: "GET",
							url:"../script/maps/json_map/"+drillKey+".geo.json",
			                contentType: "application/json; charset=utf-8",
			                dataType:'json',
			                crossDomain: true,
			                success: function(json) {
			                      Highmap.utils.mapHideLoading();
			                      var type = Highmap.valueType;
			                      if(type === 'mapbubble'){
			                      	Highmap.mapObject.addSeriesAsDrilldown(e, {
			                      		type:'map',
						                name: cname,
						                mapData: Highcharts.geojson(json),
						                enableMouseTracking: false,
						                borderColor: '#606060',
						                nullColor: 'rgba(200, 200, 200, 0.2)',
						                showInLegend: false,
							            color: Highcharts.getOptions().colors[1],
							            dataLabels: {
							                enabled: true,
							                format: '{point.properties.name}'
							            }
						            });

			                        Highmap.mapObject.addSeries({
						                type: Highmap.valueType,
						                mapData: Highcharts.geojson(json),
						                name: '相对贫困户数',
						                joinBy:[joinBy,joinBy],
						                data : data,
						                minSize: 4,
						                maxSize: '20%',
						                states: {
							                hover: {
							                    color: '#BADA55'
							                }
							            },
							            dataLabels: {
							                enabled: true,
							                format: '{point.properties.z}'
							            },
							            tooltip: {
							               headerFormat: '',
							               pointFormat: Highmap.pointFormat
							            }
						            });

			                      }else{
			                      	Highmap.mapObject.addSeriesAsDrilldown(e, {
			                      		type : 'map',
								  		data : data,
							            mapData: Highcharts.geojson(json),
							            joinBy:joinBy,
			                            name: cname,
			                            borderColor: '#202125',
	                					nullColor: '#333c49',
			                            /*borderColor: '#606060',
						                nullColor: 'rgba(200, 200, 200, 0.2)',*/
						                showInLegend: false,
							            color: Highcharts.getOptions().colors[1],
							            states: {
							                hover: {
							                    color: '#ffa132'
							                },
							                select: {
							                    color: '#ffa132'
							                }
							            },
							            dataLabels: {
							                enabled: true,
							                format: '{point.properties.name}'
							            },
							            tooltip: {
							               headerFormat: '',
							               pointFormat: Highmap.pointFormat
							            }
			                        });
			                      }
			                      /** TODO 添加相对贫困村*/
			                      if(Highmap.showPoint){
			                      	  var vname = '相对贫困村';
			                      	  var visible = false;
				                      if(Highmap.levarray.length > 3){
				                      		vname = '相对贫困户';
				                      		if(Highmap.levarray.length > 4)
				                      			visible = true;
				                      }
									  Highmap.mapObject.addSeries({
												name: vname,
								                type: 'mappoint',
								                data: Highcharts.geojson(json, 'mappoint'),
								                showInLegend:true,
							                	color:'#fffc1b',
		            							/*enableMouseTracking: false,		*/
								                marker: {
								                    radius: 2
								                },
								                dataLabels: {
								                    align: 'left',
								                    verticalAlign: 'middle'
								                },
								                animation: true,
								                visible:visible,
								                tooltip: {
								                    pointFormat: '{point.name}'
								                }
											});
										Highmap.mapObject.redraw();
			                      }
			                    },
	                            error: function (XMLHttpRequest, textStatus, errorThrown) {
									common.utils.showErrorMsg('缺少地图数据，无法完成“'+cname+'”的区域细化！',function(){Highmap.reload(Highmap.mapid);});
	                            }
			                });

						/*}  */
	              	}
		  },
		  loadMappoint:function(){
		  	 Highmap.utils.mapShowLoading();
		  	 var key = Highmap.levelid;
		  	 if(key === Highmap.topMapNode)
		  	 	key = 'gd';
		  	 $.ajax({
						type : "GET",
						url : "../script/maps/json_mappoint/"+key+"_pkcpoint.geo.json",
						contentType : "application/json; charset=utf-8",
						dataType : 'json',
						crossDomain : true,
						success : function(json) {
							Highmap.utils.mapHideLoading();
							Highmap.mapObject.addSeries({
										name: '相对贫困村',
						                type: 'mappoint',
						                data: Highcharts.geojson(json, 'mappoint'),
						                showInLegend:true,
						                color:'#fffc1b',
            							/*enableMouseTracking: false,		*/
						                marker: {
						                    radius: 2
						                },
						                dataLabels: {
						                    align: 'left',
						                    verticalAlign: 'middle'
						                },
						                animation: true,
						                visible:false,
						                tooltip: {
						                    pointFormat: '{point.name}'
						                }
									});
							Highmap.mapObject.redraw();
						}
					});
		  },
		  /**
		   * 初始化series
		   * @param {} mapName 地图名称
		   * @param {} data 地图数据
		   * @param {} mapData 地图物理数据
		   * @param {} joinBy 地图KEY对应关系
		   * @return {}
		   */
		  createMapSeries:function(mapName,data,mapData,joinBy){
		  	 var series = {};
		  	 if(Highmap.valueType === 'mapbubble'){
		  	 	series = [{
		  	 		type:'map',
	                name: mapName,
	                mapData: Highcharts.maps[mapData],
	                enableMouseTracking: false,
	                borderColor: '#606060',
	                nullColor: 'rgba(200, 200, 200, 0.2)',
	                showInLegend: false,
		            color: Highcharts.getOptions().colors[1],
		            dataLabels: {
		                enabled: true,
		                format: '{point.properties.name}'
		            },states: {
		                hover: {
		                    color: '#BADA55'
		                },
		                select: {
		                    color: '#ffcc32'
		                }
		            }

	            }, {
	                type: Highmap.valueType,
	                mapData: Highcharts.maps[mapData],
	                name: '相对贫困户数',
	                joinBy:[joinBy,joinBy],
	                data : data,
	                minSize: 4,
	                maxSize: '20%',
	                states: {
		                hover: {
		                    color: '#BADA55'
		                }
		            },
		            dataLabels: {
		                enabled: true,
		                format: '{point.properties.z}'
		            },
		            tooltip: {
		               headerFormat: '',
		               pointFormat: Highmap.pointFormat
		            }
	            }];
		  	 }else{
		  	 	series = [{
		  	 		type:'map',
		            data : data,
		            mapData: Highcharts.maps[mapData],
		            joinBy:joinBy,
		            name: mapName,
		            borderColor: '#202125',
	                nullColor: '#333c49',
	                /*borderColor: '#606060',
	                nullColor: 'rgba(200, 200, 200, 0.2)',*/
	                showInLegend: false,
		            color: Highcharts.getOptions().colors[1],
		            states: {
		                hover: {
		                    color: '#ffa132'
		                },
		                select: {
		                    color: '#ffa132'
		                }
		            },
		            dataLabels: {
		                enabled: true,
		                format: '{point.properties.name}'
		            },
		            tooltip: {
		               headerFormat: '',
		               pointFormat: Highmap.pointFormat
		            }
		        }]
		  	 }
		  	 return series;
		  },
		  mapShowLoading:function(){
		  	if(Highmap.setLoading !== null && Highmap.setLoading.length > 1
		  			&& common.utils.isFunction(Highmap.setLoading[0]) && common.utils.isFunction(Highmap.setLoading[1])){
		  		eval(Highmap.setLoading[0] + "()");
		  	}else{
		  		Highmap.mapObject.showLoading('<i class="icon-spinner icon-spin icon-3x"></i>');
		  	}

		  },
		  mapHideLoading:function(){
		  	if(Highmap.setLoading !== null && Highmap.setLoading.length > 1
		  			&& common.utils.isFunction(Highmap.setLoading[0]) && common.utils.isFunction(Highmap.setLoading[1])){
		  		eval(Highmap.setLoading[1] + "()");
		  	}else{
		  		Highmap.mapObject.hideLoading();
		  	}

		  }

	  }

};
