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
	/*当前层级的地图缓存属性*/
	level:{id:'',name:'',point:{},geojson:{}},
	/*地图全路径层级*/
	levarray:new Array(),
	/*全地图数据信息*/
	mapValue:{},
	mapKey:'PAC',
	parentKey:'parentKey',
	drillKey:'drillKey',
	/*地图toolpit的样式回调配置*/
	pointFormat:function(){}(),
	ajaxPoint:false,
	mapObject:{},
	setLoading:[],
	valueType:'map',
	 /* 是否需要分层加载业务数据，分层加载时，控件将动态添加map_pac,map_level两个固定参数 */
	isLayerLoad:false, 
	/* ajax加载参数：url,parameters */
	loadParameter:{}, 
	/* 静态数据：Static，动态ajax数据：Ajax */
	valueSource:'Static', 
	/* 地图权限信息：auth_user（权限用户），auth_user_pac（权限所属区域），auth_user_level（权限所属区域等级） */
	authInfo:{},  
	fontFamily:'',
	/**
	 * 当前地图数据的顶级父节点数据信息
	 */
	topMapNode:'',
	loadMapGeoUrl:'/a/report/highmaps/mapGeo/load',
	loadPointGeoUrl:'/a/report/highmaps/pointGeo/load',
	  /**
	   * 数据格式化方法
	   * @type 
	   */
	  ChartDataFormate: {
	  	  /**
	  	   * 匹配地图数据对应关系
	  	   * @param {} geoJson GEO地图数据
	  	   * @param {} data 业务数据
	  	   */
	  	  FormatGeoData:function(geoJson,data) {
	  	  	/*var data_ = geoJson;
	  	  	if(!common.utils.isDefined(data) || !common.utils.isArray(data) || data.length === 0){
	  	  		$.each(geoJson, function (i) {
		  	  		this.drilldown = this.properties[Highmap.drillKey];
				});
	  	  		return data_;
	  	  	}else{
		  	  	$.each(data_, function (i) {
		  	  		for (var index = 0; index < data.length; index++) {
		  	  			var vobj = data[index];
		  	  			if(this.properties[Highmap.mapKey] ===  vobj[Highmap.mapKey]){
		  	  				for (var item in vobj) {
								this[item] = vobj[item];
							}
							break;
		  	  			}
		  	  		}
				});
	  	  	}*/
	  	  	
	  	  	var data_ = new Array();
	  	  	if(!common.utils.isDefined(data) || !common.utils.isArray(data) || data.length === 0){
	  	  		$.each(geoJson, function (i) {
		  	  		this.drilldown = this.properties[Highmap.drillKey];
				});
	  	  		return data_;
	  	  	}else{
	  	  		$.each(geoJson, function (i) {
		  	  		this.drilldown = this.properties[Highmap.drillKey];
		  	  		for (var index = 0; index < data.length; index++) {
		  	  			var vobj = data[index];
		  	  			if(this.properties[Highmap.mapKey] ===  vobj[Highmap.mapKey]){
		  	  				data_.push(vobj);
							break;
		  	  			}
		  	  		}
				});
	  	  	}
			return data_;
	  	  },
	  	  
	  	  /**
	  	   * 更新某一层级的series数据
	  	   * @param {} levelSeries 层级缓存业务数据
	  	   * @param {} data 更新后的数据
	  	   */
	  	  FormatLevelSeries:function(levelSeries,data){
	  	  		levelSeries.data = data;
	  	  		/*$.each(levelSeries.data, function (i,vobj) {
		  	  		for (var index = 0; index < data.length; index++) {
		  	  			var vobj = data[index];
		  	  			if(this.properties[Highmap.mapKey] ===  vobj[Highmap.mapKey]){
		  	  				for (var item in vobj) {
								this[item] = vobj[item];
							}
							break;
		  	  			}
		  	  		}
				});*/
	  	  	
	  	  },	  	  
	      /**
	       * 处理分组数据，数据格式 : name：xxx，group：xxx，value：xxx 
	       * 用于折线图、柱形图（分组，堆积）
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
	       */
	      FormatMapData: function (data){
	      		var mdata = {};
	      		var topValues = new Array();
	      		var type = Highmap.valueType;
	      		$.each(data, function (i) {
	      			var item = this;
	      			if(type === 'mapbubble' && !item.hasOwnProperty('z')){
	      				item['z'] = item.value;
	      			}	      				
	      			if((item.hasOwnProperty(Highmap.parentKey) && item[Highmap.parentKey] === Highmap.topMapNode) || !item.hasOwnProperty(Highmap.parentKey)){
	      				/*delete item[Highmap.parentKey];*/
	      				topValues.push(item);
	      			}else{
	      				var pk = item[Highmap.parentKey];
	      				if(mdata.hasOwnProperty(pk)){
	      					/*delete item[Highmap.parentKey];*/
	      					mdata[pk].push(item);
	      				}else{
	      					mdata[pk] = new Array();
	      					/*delete item[Highmap.parentKey];*/
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
	      	  Highmap.topMapNode = opt.topNode;
	      	  Highmap.mapValue = data;
	      	  Highmap.fontFamily = opt.fontFamily;
	      	  Highmap.valueType = opt.valueType;
	      	  Highmap.mapKey = joinBy;
	      	  Highmap.parentKey = opt.parentKey;
	      	  Highmap.isLayerLoad = opt.isLayerLoad;
			  Highmap.drillKey = opt.drillKey;
			  Highmap.authInfo = opt.authInfo;
	      	  Highmap.setLoading = opt.setLoading;
	          var mapValue_ = Highmap.ChartDataFormate.FormatMapData(data);
	          /*Highmap.pointFormat = Highmap.utils.convValueFormat(opt);	*/
	          Highmap.pointFormat = Highmap.utils.convValueFormat(opt);
	          var series = Highmap.utils.createMapSeries(opt.mapName,mapValue_[Highmap.topMapNode],mapData,joinBy,opt);
	          
	          var option = {
		        chart : {
		        	height:opt.height,
		        	/*width:opt.width,*/
		        	backgroundColor: opt.color.backgroundColor,  
		        	borderColor: opt.color.borderColor,
		        	borderRadius: 0,
		        	borderWidth: 1,
	          	  	renderTo:container,
	          	  	events: {
		                drilldown: function (e) {
		                	if (!e.seriesOptions) {
				            	/*判断是否需要启动地图下钻功能*/
		                		var pointinfo = e.point;
					            var cname = pointinfo.properties['name'];	
					            var key = pointinfo.properties[Highmap.mapKey];
					            
					            /* 保存当前的层级ID及地图属性全路径 */
								Highmap.levelid = key;
								Highmap.level = {id : key,name : cname};
								Highmap.levarray.push(Highmap.level);
				                var data = Highmap.mapValue;
				                /*加载分层业务数据*/
				                if(Highmap.isLayerLoad && Highmap.valueSource === "Ajax"){
				                	 /** 获取业务数据同步参数，并分步加载业务数据 */
				                	var url = Highmap.loadParameter.url;
				                	var parameter = Highmap.loadParameter.parameters;
				                	parameter['map_pac'] = key;
				                	parameter['map_level'] = Highmap.levarray.length;
				                	var map_pac_level = '';
									for (var index = 0; index < Highmap.levarray.length; index++) {
										if(index === 0){
											map_pac_level = Highmap.levarray[index].id;
										}else{
											map_pac_level +=',' + Highmap.levarray[index].id;
										}
									}
									parameter['map_pac_level'] = map_pac_level;
									Highmap.utils.loadAuthParameters(parameter);
				                	// 加载区域数据
									$.ajax({
										type : "GET",
										url : url,
										data : parameter,
										contentType : "application/json; charset=utf-8",
										dataType : 'json',
										crossDomain : true,
										success : function(json){
											var dmv_ = Highmap.ChartDataFormate.FormatMapData(json);
											Highmap.utils.mapDrilldown(pointinfo,dmv_[key],opt);
										}
									}); 				                	
				                }else{
				                	var dmv_ = Highmap.ChartDataFormate.FormatMapData(Highmap.mapValue);
									Highmap.utils.mapDrilldown(pointinfo,dmv_[key],opt);
				                } 
				                
					           	if(opt.title)
					            	Highmap.mapObject.setTitle(null, { text: cname });
				            	var funcName = opt.doDrilldown;
								if(common.utils.isFunction(funcName)){
									eval(funcName + "(e.point,key,Highmap.levarray.length)");
								}
			            	}
		                },
		                drillup: function (e) {
		                	if(common.utils.isDefined(this.subtitle))
		                    	this.setTitle(null, { text: e.seriesOptions['name'] });
		                    
		                    /*移除当级标识点信息 */
		                    if(Highmap.level.point.enable){
		                    	var delDex = new Array();
		                    	for (var index = 0; index < Highmap.mapObject.series.length; index++) {
		                    		var ids = Highmap.level.point.ids;
		                    		
		                    		for (var y = 0; y < Highmap.level.point.num; y++) {
		                    			if(Highmap.mapObject.series[index].userOptions.id === ids[y]){
			                    			delDex.push(index);
			                    			continue;
			                    		}
		                    		}		                    		
		                    	}
		                    	for (var index = delDex.length-1; index >= 0; index--) {
		                    		Highmap.mapObject.series[delDex[index]].remove(false);
		                    	}
		                    }		                    	
		                    
		                    /*保存当前的层级ID及地图属性全路径*/
				            Highmap.levarray.pop();
	    					Highmap.levelid = Highmap.levarray[Highmap.levarray.length-1].id;	
	    					Highmap.level = Highmap.levarray[Highmap.levarray.length-1];
					
		                    var funcName = opt.doDrillup;
							if(common.utils.isFunction(funcName)){
								eval(funcName + "(Highmap.levelid,Highmap.levarray.length)");
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
		            	fontFamily:Highmap.fontFamily,
		                fontSize: '16px'
		            }
		        },
		        legend: opt.legend,
		        colorAxis: {
		            min: 0,
		            stops: opt.colorStops
		        },
		        tooltip: {
	                backgroundColor:opt.color.tooltipBackgroundColor,
		            borderColor:opt.color.tooltipBorderColor,
	                borderWidth: 1,
	                borderRadius: 1
	            },
		        loading: {
					labelStyle: { "fontFamily":Highmap.fontFamily,"fontWeight": "bold", "position": "relative", "top": "45%" },
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
				            		var funcName = opt.doClick;
									if(common.utils.isFunction(funcName)){
										eval(funcName + "(e.point,Highmap.levelid,Highmap.levarray.length)");
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
		                relativeTo: opt.drillUpButton.relativeTo,
		                position: {
		                    y: opt.drillUpButton.y,
		                    x: opt.drillUpButton.x
		                },
		                theme: {
		                    fill: opt.drillUpButton.fill,
		                    'stroke-width': opt.drillUpButton.strokeWidth,
		                    stroke: opt.drillUpButton.stroke,
		                    r: opt.drillUpButton.r,
		                    style:{
		                    	color:opt.drillUpButton.fontColor,
		                    	fontFamily:Highmap.fontFamily,
			        			fontSize:opt.drillUpButton.fontSize,
			        			cursor:"pointer"
		                    },
		                    states: {
		                        hover: {
		                            fill: opt.drillUpButton.selectColor
		                        },
		                        select: {
		                            stroke: '#039',
		                            fill: opt.drillUpButton.selectColor
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
	  	 * @param {} data 地图数据信息 （数据格式为：key,name,parentKey,value,value1,value2,value3...）
	  	 * @param {} mapData 地图数据信息
	  	 * @param {} joinBy 地图与数据对应字段
	  	 * @param {} isNotLoading 是否不加载动画
	  	 * @param {} option
	  	 * @return {}
	  	 */
	  	init:function(container,data,mapData,joinBy, option,isNotLoading){
	  		if(!isNotLoading) Highmap.utils.mapShowLoading();
	  		/*静态数据不支持分层加载*/
			if(Highmap.valueSource === "Static")
				option["isLayerLoad"] = false;
	  		/*清洗并规范化自定义配置项*/
	  		var type = "Map";
	  		var option_ = Highmap.utils.defaultOption(option);
	  		option_["container"] = container;
	  		option_["optionType"] = type;
	  		var opt = eval("Highmap.MapOptionTemplates."+type+"(container,data,mapData,joinBy,option_)");
	    	Highmap.mapObject = new Highcharts.Map(opt);
	  		/*Highmap.mapObject = Highcharts.mapChart(container,opt);*/
	    	Highmap.levelid = Highmap.topMapNode;	
		    var pointInfo = Highmap.utils.loadMappoint(option_,Highmap.topMapNode,1);
		    
	    	/*保存当前的层级ID及地图属性全路径*/	    	
	    	Highmap.level = {
		    	id:Highmap.topMapNode,
		    	name:option_.mapName,
		    	point:pointInfo,
		    	geojson:Highcharts.maps[mapData].features
	    	};
	    	Highmap.levarray.push(Highmap.level);
		    
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
	    	if(!isNotLoading) Highmap.utils.mapHideLoading();
	    	return Highmap.mapid;
	  	}
	  	
	  },	  
	  
	  /**
	   * 重新加载报表数据（只刷新全局数据）
	   * @param {} chartid 报表ID
	   * @param {} data 重新加载的数据
	   * @param {} valueMapper 数据结构
	   * @param {} colorStops 颜色色阶
	   * @param {} isNotLoading 是否不启动加载动画
	   * @return 是否成功更新对象
	   */
	  reloadData: function (chartid,data,valueMapper,colorStops,isNotLoading) {
	      var obj = common.report.highcharts.object.get(chartid);	      
	      if(obj != null){
	      	  var chart = obj.chart;	      	  
	      	  if(!isNotLoading) Highmap.utils.mapShowLoading();
	      	  var opt = obj.option;
			  if(!common.utils.isDefined(data)){
			  	  data = obj.data;
			  }
			  if(!common.utils.isDefined(valueMapper)){
			  	  valueMapper = opt.valueMapper;
			  }else{
			  	opt.valueMapper = valueMapper;
			  }
			  
			  Highmap.mapValue = data;
			  var mapValue_ = Highmap.ChartDataFormate.FormatMapData(data);
			  var value_ = Highmap.ChartDataFormate.FormatGeoData(Highmap.level.geojson,mapValue_[Highmap.levelid]);
			  Highmap.pointFormat = Highmap.utils.convValueFormat(opt);
			  
			  /*刷新地图本级数据*/
			  for (var index = 0; index < chart.series.length; index++) {
			  	if(chart.series[index].userOptions.type === 'map'){
			  		if(Highmap.levarray.length === 1){
			  			chart.series[index].update({data:value_},false);
			  		}else{
			  			chart.series[index].setData(value_,false);
			  		}
			  	}
			  }
			  
	          /*刷新地图上层数据*/
	          if(common.utils.isDefined(chart.drilldownLevels)){
	            var levels = chart.drilldownLevels;
	            for (var index = 0; index < levels.length; index++) {
	            	Highmap.ChartDataFormate.FormatLevelSeries(chart.drilldownLevels[levels.length-1-index].series,mapValue_[Highmap.levarray[Highmap.levarray.length-(2+index)].id]);
	            	/*chart.drilldownLevels[levels.length-1-index].levelSeriesOptions[0].data = mapValue_[Highmap.levarray[Highmap.levarray.length-(2+index)].id];*/
	            	/*chart.drilldownLevels[levels.length-1-index].levelSeriesOptions[0].tooltip = {headerFormat: '',pointFormat: Highmap.pointFormat};*/
	            }  	
	          }
	          
	          /*更新地图显示颜色色阶*/
	          if(colorStops && common.utils.isArray(colorStops)){				    
				    //chart.userOptions.colorAxis.stops = Highmap.utils.convColorstops(colorStops);
	          		chart.colorAxis[0].update({
	          			stops:Highmap.utils.convColorstops(colorStops)
	          		},true);
				    opt["colorStops"] = colorStops;
	          }
		      
			  /*更新地图全局数据*/
			  obj["chart"] = chart;
			  obj["data"] = data;
			  obj["option"] = opt;
			  common.report.highcharts.object.reset(chartid,obj);		
			  chart.redraw(true);
			  if(!isNotLoading) Highmap.utils.mapHideLoading();
	      }	      
	  },
	  
	  /**
		 * 对整个报表对象进行重新加载
		 * @param {} chartid 报表ID
		 * @param {} data 重新加载的数据(可选)
		 * @param {} newOption 新的自定义项(可选)
		 * @param {} type 变换报表类型(可选)
	     * @param {} isNotLoading 是否不启动加载动画
		 * @return 是否成功更新对象
		 */
	  reload: function (chartid,data,newOption,isNotLoading) {
	      var obj = common.report.highcharts.object.get(chartid);
	      if(obj != null){
	      	  var chart = obj.chart;
	      	  if(!isNotLoading) Highmap.utils.mapShowLoading();
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
			  		  
			  if(!isNotLoading) Highmap.utils.mapHideLoading();
			  /*销毁图表对象 */	
			  chart.destroy();
			  chart= new Highcharts.Map(option);
			  
			  Highmap.mapObject = chart;
			  
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
	  	 * @param {} url 请求url  （返回数据格式为：key,name,parentKey,value,value1,value2,value3...）
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}
	  	 * @param {} mapData 地图数据信息
	  	 * @param {} joinBy 地图与数据对应字段
	  	 * @param {} option 自定义配置项
	  	 * @return {} 
	  	 */
	  	init:function(container,url,parameters,mapData,joinBy,option){
	  		/*设置数据获取来源*/
	  		Highmap.valueSource = "Ajax";
	  		/*创建空的报表，并获取报表对象*/
	  		var id = Highmap.create.init(container,[],mapData,joinBy,option,true);
	  		var obj = common.report.highcharts.object.get(id);
	  		var chart = obj.chart;
	  		Highmap.utils.mapShowLoading();
	  		parameters = common.utils.strToJson(parameters);
	  		Highmap.utils.loadAuthParameters(parameters,option.authInfo);
	  		/*处理分层加载信息*/
			if(option.isLayerLoad){
				Highmap.loadParameter = {
					url:url,
					parameters:parameters
				};
				/*处理动态加载参数*/
				parameters['map_pac'] = Highmap.topMapNode;
				parameters['map_level'] = 1; 
				parameters['map_pac_level'] = Highmap.topMapNode;
			}		  		
	  		$.ajax({
	             type: "POST",
	             url: url,
	             data: parameters,
	             cache:true,
	             dataType: "json",
	             success: function(data){
	             	/*动态加载数据*/
	             	Highmap.reloadData(id,data,undefined,undefined,true);
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
			  		 Highmap.utils.loadAuthParameters(parameters,newOption.authInfo);
			  		 /*处理分层加载信息*/
					if(newOption.isLayerLoad){
						Highmap.loadParameter = {
							url:url,
							parameters:parameters
						};
						/*处理动态加载参数*/
						parameters['map_pac'] = Highmap.topMapNode;
						parameters['map_level'] = 1; 
						parameters['map_pac_level'] = Highmap.topMapNode;
					}		  		 
			  		 $.ajax({
			             type: "POST",
			             url: url,
			             data: parameters,
			             cache:true,
			             dataType: "json",
			             success: function(data){
			             	Highmap.reload(chartid,data,newOption,true);
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
	  	},
	  	/**
	  	 * 重新加载报表数据（只刷新全局数据）
	  	 * @param {} chartid
	  	 * @param {} url
	  	 * @param {} parameters
	  	 * @param {} valueMapper
	  	 * @param {} colorStops
	  	 * @return {}
	  	 */
	  	reloadData:function(chartid,url,parameters,valueMapper,colorStops){
	  		var obj = common.report.highcharts.object.get(chartid);
		    if(obj != null){
		    	if(common.utils.isEmpty(url)){
		    		  /*不进行数据更新处理*/
		    		 return;		    		
		    	}else{
		    		 /*进行后台数据更新操作*/
		    		 var chart = obj.chart;
		    		 Highmap.utils.mapShowLoading();
					 if(common.utils.isEmpty(url)) url = obj.url;
					 if(common.utils.isEmpty(parameters)) parameters = obj.parameters;	
			  		 parameters = common.utils.strToJson(parameters);
			  		 Highmap.utils.loadAuthParameters(parameters);
			  		 /*处理分层加载信息*/
					if(Highmap.isLayerLoad){
							Highmap.loadParameter = {
								url:url,
								parameters:parameters
							};
							/*处理动态加载参数*/
							parameters['map_pac'] = Highmap.levelid;
							parameters['map_level'] = Highmap.levarray.length; 
							var map_pac_level = '';
							for (var index = 0; index < Highmap.levarray.length; index++) {
									if(index === 0){
										map_pac_level = Highmap.levarray[index].id;
									}else{
										map_pac_level +=',' + Highmap.levarray[index].id;
									}
							}
							parameters['map_pac_level'] = map_pac_level;
					  }
			  		 $.ajax({
			             type: "POST",
			             url: url,
			             data: parameters,
			             cache:true,
			             dataType: "json",
			             success: function(data){
			             	Highmap.reloadData(chartid,data,valueMapper,colorStops,true);
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
		 * @param {} queryName mybatis的对应查询名称(必选) （查询结果数据格式为：key,name,parentKey,value,value1,value2,value3...）
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
			var parameters_ = common.utils.strToJson(parameters);
			Highmap.utils.loadAuthParameters(parameters_,option.authInfo);
			var mapper_ = common.utils.strToJson(mapper);
			/*处理分层加载信息
			if(option.isLayerLoad){
				处理动态加载参数
				parameters_['map_pac'] = Highmap.topMapNode;
				parameters_['map_level'] = 1; 
			}			*/
	  		/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = parameters_;
			params.mapper = mapper_;
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
				var parameters_ = common.utils.strToJson(parameters);
				Highmap.utils.loadAuthParameters(parameters_,newOption.authInfo);
			    var mapper_ = common.utils.strToJson(mapper);
				
		    	/*进行后台数据更新操作*/
		    	var params = obj["parameters"];
		    	if(!common.utils.isEmpty(queryName)) params["queryName"] = queryName;
		    	if(!common.utils.isEmpty(parameters)) params["param"] = parameters_;
		    	if(!common.utils.isEmpty(mapper)) params["mapper"] = mapper_;
		    	
		    	return Highmap.ajax.reload(chartid,url,params,newOption); 				
		    }else {
		      	  return false;
		    }
	  	},
	  	/**
	  	 * 重新加载报表数据（只刷新全局数据）
	  	 * @param {} chartid 报表的chartid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{key:'key?',value:'value?'}
		 * @param {} colorStops 颜色色阶信息。格式为['#91d4ff','#2e5fff','#6bdb1f']
	  	 * @param {} valueMapper
	  	 * @return {}
	  	 */
	  	reloadData:function(chartid,queryName,parameters,mapper,valueMapper,colorStops){
	  		var obj = common.report.highcharts.object.get(chartid);
		    if(obj != null){
				var url = obj.url;				
				var parameters_ = common.utils.strToJson(parameters);
				Highmap.utils.loadAuthParameters(parameters_);
			    var mapper_ = common.utils.strToJson(mapper);

		    	/*进行后台数据更新操作*/
		    	var params = obj["parameters"];
		    	if(!common.utils.isEmpty(queryName)) params["queryName"] = queryName;
		    	if(!common.utils.isEmpty(parameters)) params["param"] = parameters_;
		    	if(!common.utils.isEmpty(mapper)) params["mapper"] = mapper_;
		    	
		    	return Highmap.ajax.reloadData(chartid,url,params,valueMapper,colorStops);				
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
		  	
		  	/** TODO 自定义配置的默认值设置 */
			var defaultOpt = {
				topNode:'440000000000', /*地图顶层父节点ID*/
				title : true, /* 是否显示报表标题(默认为显示) */
				fontFamily:'Microsoft YaHei', /*地图全部的字体样式*/
				color:{}, /*地图对象配色方案*/
				mapName:'', /* 地图名称(默认为空) */
				height:500, /* 地图高度(默认为空) */
				width:800, /* 地图宽度(默认为空) */
				valueType:'map', /*数据的展示方式(默认为map，按色阶生成对比)*/
				valueDecimal : 0, /* 数据精度(默认为不保留小数位){最高只支持4位小数} */
				percentDecimal : 2,/* 显示百分比数据精度(默认为保留2位小数){最高只支持4位小数} */
		        colorStops:['#EFEFFF',Highcharts.getOptions().colors[0],'#006cee'], /* 步长颜色定义 */
				setLoading:[], /*配置变更加载方法*/
				legend : {}, /*显示配置对象*/
				navigation:true, /* 是否显示缩放导航(默认为true) */
				navigationButton:true, /* 是否显示缩放导航按钮(默认为true) */
				isLayerLoad:false, /*是否分层加载业务数据*/
				valueMapper:[{key:'value',name:'数值',valueSuffix:''}], /*数据显示及其对应关系*/
				parentKey:'parentKey',  /* 是否地图数据父级主键 (默认为parentKey) */
				drillKey:'',  /* 是否地图下钻主键 (默认为空) */
				authInfo:{},  /* 地图权限信息：auth_user（权限用户），auth_user_pac（权限所属区域），auth_user_level（权限所属区域等级） */
				mapLoader:{}, /*地图加载对象*/
				pointLoader:{},  /*地图点加载对象*/
				drillUpButton:{},  /*上钻按钮样式调整*/
				doClick : '',  /*传入方法名，可以在图表数据点点击时触发监听事件，附带参数为chart对象，eg:function testload(chart)*/
				doDrilldown : '',  /*传入方法名，可以在图表点击下钻时触发监听事件，附带参数为chart对象，eg:function testload(chart)*/
				doDrillup : ''  /*传入方法名，可以在图表点击返回时触发监听事件，附带参数为chart对象，eg:function testload(chart)*/
			};
			/*设置传入自定义参数默认值*/
			var defaultDrillUpButton = {
		          relativeTo: 'spacingBox', /*按钮样式*/
		          y: 0, /*按钮y轴坐标*/
		          x: 0, /*按钮x轴坐标*/
		          fill: 'white', /*按钮填充色*/
		          strokeWidth: 2, /*按钮描边宽度*/
		          stroke: 'silver', /*按钮描边颜色*/
		          r: 3,  /*按钮边角圆度*/
		          fontColor:'#000', /*字体颜色*/
		          fontSize:13,  /*字体大小*/
		          selectColor:'#a4edba' /*选中颜色*/
		    };
			
			var defaultColor = {
				backgroundColor: '',  /*地图底色(默认为空)*/
		        borderColor: '',   /*地图边界色(默认为空)*/
		        seriesNullColor:'rgba(200, 200, 200, 0.2)', /*地图空块默认颜色(默认为空)*/
		        seriesBorderColor:'silver', /*地图区域边界颜色(默认为空)*/
		        seriesSelectColor: '#f4e925', /*地图区域选中色(默认为空)*/
		        dataLabelColor:'', /*地图区域字体颜色*/
		        tooltipNameColor:'#F58221', /*地图浮层区域名称字体颜色*/
		        tooltipValueColor:'#0000FF', /*地图浮层主数据显示字体颜色*/
		        tooltipMinorValueColor:'', /*地图浮层次要数据显示字体颜色*/
		        tooltipBackgroundColor:'rgba(255, 255, 255, 0.85)', /*地图浮层背景字体颜色*/
		        tooltipBorderColor:'' /*地图浮层描边颜色*/
			};
			
			var defaultLegend = {
				enabled : true, /* 是否显示图例(默认为true) */
				legendFloat : false, /*是否显示图例浮动*/
				legendAlign : 'right', /*图例显示方位(默认为right) 左边:left，右边:right，上方:top，下方:bottom*/				
				backgroundColor: 'rgba(29,26,40,0.7)', /*背景色*/
				color:(Highcharts.theme && Highcharts.theme.textColor) || 'black', /*图例的字体颜色*/
				x : 0, /*调整图例X轴的数值*/
				y : 0 /*调整图例Y轴的数值*/
			};
			var defaultMapLoader = {
				isRemote:false, /*是否直接从后台加载数据*/
				remoteType:'ajax',/*远程地图读取方式："ajax"通过自主ajax读取，"common"通过通用方法读取*/
				localUrl:'/static/js/maps/json_map/PAC/',  /*本地地图数据加载文件夹，地图文件请按 #pac#.geo.json */
				remoteUrl:'', /*远程地图URL参数，请求参数为{pac:#pac}*/
				commonQueryName:'', /*通过common方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
				commonMapper:'' /*通过common方式读取，读取的查询转义{name:'NAME1'（名称）,mapKey:'ID'（主键）,parentKey:'PID'（父级主键）,drillKey:'ID'（下钻主键）,geometry:'GEO'（坐标信息）}*/
				
			};
			var defaultPointLoader = {
		  		isLoadPoint:false, /*是否自动加载地图点属性*/
		  		isRemote:false, /*是否直接从后台加载数据（本地数据只支持读取单一数据点）*/
				localUrl:'/static/js/maps/json_mappoint/',  /*本地地图数据加载文件夹，地图文件请按 #pac#.geo.json(本地位置点文件只适用于首层地图展示) */
		  		points:[],
				doSetPoint:''/*传入方法名，可以动态修改标识点的显示名与是否默认显示，eg:function testpoint(point) */
			}; 
			var drillUpButton_ = common.utils.setDefaultOption(option.drillUpButton,defaultDrillUpButton);
			var color_ = common.utils.setDefaultOption(option.color,defaultColor);
			var legend_ = common.utils.setDefaultOption(option.legend,defaultLegend);
			var mapLoader_ = common.utils.setDefaultOption(option.mapLoader,defaultMapLoader);
			var pointLoader_ = Highmap.utils.loadPointer(option.pointLoader,defaultPointLoader);
			var option_ = common.utils.setDefaultOption(option,defaultOpt);

			option_["drillUpButton"] = drillUpButton_;
			option_["mapLoader"] = mapLoader_;
			option_["pointLoader"] = pointLoader_;
			option_["color"] = color_;
			
			/* legend图例显示方式转换 */
			option_["legend"] = this.convLegend(legend_,option_.fontFamily);
			
			/*转换图表步长信息*/
			option_["colorStops"] = this.convColorstops(option_.colorStops);

			if(!option_.navigation) option_["navigationButton"] = false;
			
			/* 设置数据点鼠标样式 */
			option_["cursor"] = '';
			if (common.utils.isFunction(option_["doClick"])) {
				option_["cursor"] = 'pointer';
			}
			
			/*设置默认数值单位*/
			if(common.utils.isJson(option_.valueMapper)){
				if(!option_.valueMapper.hasOwnProperty('valueSuffix'))
					option_.valueMapper['valueSuffix']='';
			}else if(common.utils.isArray(option_.valueMapper)){
				$.each(option_.valueMapper,function(n,value) { 
					if(!value.hasOwnProperty('valueSuffix'))
						value['valueSuffix']='';
			  	});
			}
			return option_;
		  	  
		  },
		  /**
		   * 加载地图标识点属性值
		   */
		  loadPointer:function(pointOpt,defaultPointLoader){
			var pointLoader_ = common.utils.setDefaultOption(pointOpt,defaultPointLoader);
			if(pointLoader_.points !== null && pointLoader_.points.length !== 0){
				var points_ = new Array();
				for (var index = 0; index < pointLoader_.points.length; index++) {
					var defaultPoint = {
						queryName:'', /*通过远程方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
						parameters:'', /*读取位置点的固定参数*/
						mapper:'', /*通过远程方式读取，读取的查询转义{name:'NAME1'（名称）,mapKey:'ID'（主键）,parentKey:'PID'（所属区域主键）,longitude:'test1'（经度）,latitude:'test2'（纬度）}*/
						pointName:'标识点', /*配置地图点默认属性名称*/
						pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
						pointColor:'#fffc1b', /*标识点颜色*/
						tooltipTitleColor:'#ff9000', /*标识点浮层标题颜色*/
						tooltipTextColor:'#f3e924', /*标识点浮层名字颜色*/
						lineColor: 'black',
						lineWidth: 0.1,
						radius: 2
			  		};
		  			var point_ = common.utils.setDefaultOption(pointLoader_.points[index],defaultPoint);
		  			points_.push(point_);
				}
				pointLoader_["points"] = points_;
			}
			return pointLoader_;
		  },
		  
		  /**
			 * legend图例显示方式转换
			 * @param {} opt
			 */
		  convLegend:function(legend_,fontFamily){	  	  
			  var legend = {};
			  if(!legend_.enabled) {
			  	legend["enabled"] = false;
			  	return legend;
			  }
			  legend["enabled"] = true;
			  legend["floating"] = legend_.legendFloat;
		  	  switch(legend_.legendAlign)
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
			  legend["x"] = legend_.x;
			  legend["y"] = legend_.y;
			  legend["backgroundColor"] = legend_.backgroundColor;
			  legend['itemStyle'] = {
                    fontWeight: 'bold',
                    fontStyle: fontFamily,
                    color: legend_.color,
                    textDecoration: 'none'
                };
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
		  convValueFormat:function(opt){
		  		return function (point) {
					if(!opt)
		  			return '';
					var mapper = opt.valueMapper;
					var pointFormat = '<p><b style="font-weight:bold;color:'+opt.color.tooltipNameColor+';font-size:13px">'+point.properties.name+'<b/><br>';
				    if(common.utils.isArray(mapper)){
				    	for (var index = 0; index < mapper.length; index++) {
				    		if(index === 0){
				    			pointFormat += '<b style="font-family:'+Highmap.fontFamily+';color:'+opt.color.tooltipValueColor+';font-size:12px">'+mapper[index].name+'：</b><b style="font-family:'+Highmap.fontFamily+';font-weight:bold;color:'+opt.color.tooltipValueColor+';">'+point[mapper[index].key]+' '+ mapper[index].valueSuffix + '</b><br/>';
				    		}else{
				    			pointFormat += '<b style="font-family:'+Highmap.fontFamily+';color:'+opt.color.tooltipMinorValueColor+';font-size:12px">'+mapper[index].name+'：</b><b style="font-family:'+Highmap.fontFamily+';font-weight:bold;color:'+opt.color.tooltipMinorValueColor+';font-size:12px">'+point[mapper[index].key]+' '+ mapper[index].valueSuffix + '</b><br/>';
				    		}
				    	}
				    	pointFormat += '</p>';
				    }else if (common.utils.isJson(mapper)){
				    	pointFormat += '<b style="font-family:'+Highmap.fontFamily+';color:'+opt.color.tooltipValueColor+';font-size:12px">'+mapper.name+'：</b><b style="font-family:'+Highmap.fontFamily+';font-weight:bold;color:'+opt.color.tooltipValueColor+';">'+point[mapper[index].key]+' '+ mapper.valueSuffix + '</b><br/></p>';
				    }
				    return pointFormat;
				}	  		
		  },
		  /**
		   * 加载用户预定义的权限参数
		   * @param {} oparam
		   */
		  loadAuthParameters:function(oparam,auth){
		  		if(!auth)
		  			auth = Highmap.authInfo;
		  		for(var key in auth){  
	                if(!oparam.hasOwnProperty(key)){
	                	oparam[key] = auth[key];
	                }
	           }  
		  },		  
		  /**
		   * 地图下钻事件
		   * @param {} e map属性
		   * @param {} data 列表数据
		   * @return {Boolean}
		   */
		  mapDrilldown:function(e,data,opt){
	        var key = e.properties[Highmap.mapKey];
	        var dkey = e.properties[Highmap.drillKey];
	        var level = Highmap.levarray.length;
			var cname = e.properties["name"];
			Highmap.utils.mapShowLoading();
			var url = '';
			var parmeters = {};
			/* 读取地图数据读取器信息 */
			if (opt.mapLoader.isRemote) {
				if('ajax' === opt.mapLoader.remoteType){
					url = ctx + opt.mapLoader.remoteUrl;
					parmeters = {
						"map_pac" : dkey,
						"map_level" : level
					};
					Highmap.utils.loadAuthParameters(parmeters);
				}else{
					url = ctx + Highmap.loadMapGeoUrl;
					var param_ = { "map_pac":dkey,"map_level":level };
					Highmap.utils.loadAuthParameters(param_);
					parmeters = {
						queryName : opt.mapLoader.commonQueryName,
						param : param_,
						mapper : opt.mapLoader.commonMapper
					};
				}
			
			} else {
				url = ctx + opt.mapLoader.localUrl + dkey + ".geo.json";
			}
			// 加载区域数据
			$.ajax({
				type : "GET",
				url : url,
				data : parmeters,
				contentType : "application/json; charset=utf-8",
				dataType : 'json',
				crossDomain : true,
				success : function(json){
					var geoJson = Highcharts.geojson(json);
					var data_ = Highmap.ChartDataFormate.FormatGeoData(geoJson,data);					
					var type = Highmap.valueType;
					
					if (type === 'mapbubble') {
						Highmap.mapObject.addSeriesAsDrilldown(e, {
							type : 'map',
							name : cname,
							mapData : geoJson,
							enableMouseTracking : false,
							borderColor : opt.color.seriesBorderColor,
							nullColor : opt.color.seriesNullColor,
							showInLegend : false,
							color : Highcharts.getOptions().colors[1],
							dataLabels : {
								enabled : true,
								format : '<span style="font-family:'
										+ Highmap.fontFamily
										+ ';font-weight:bold;font-size: 12px;">{point.properties.name}</span>',
								color : opt.color.dataLabelColor
							}
						});

						Highmap.mapObject.addSeries({
									type : Highmap.valueType,
									mapData : geoJson,
									name : pointName,
									joinBy : [joinBy, joinBy],
									data : data_,
									minSize : 4,
									maxSize : '20%',
									states : {
										hover : {
											color : '#BADA55'
										}
									},
									dataLabels : {
										enabled : true,
										format : '{point.properties.z}'
									},
									tooltip : {
										headerFormat : '',
										pointFormat : Highmap.pointFormat
									}
								});
					} else {
						Highmap.mapObject.addSeriesAsDrilldown(e, {
							type : 'map',
							data : data_,
							mapData : geoJson,
							joinBy : Highmap.mapKey,
							name : cname,
							borderColor : opt.color.seriesBorderColor,
							nullColor : opt.color.seriesNullColor,
							borderRadius : 1,
							borderWidth : 1,
							showInLegend : false,
							color : Highcharts.getOptions().colors[1],
							states : {
								hover : {
									color : opt.color.seriesSelectColor
								},
								select : {
									color : opt.color.seriesSelectColor
								}
							},
							dataLabels : {
								enabled : true,
								format : '<span style="font-family:'
										+ Highmap.fontFamily
										+ ';font-weight:bold;font-size: 12px;">{point.properties.name}</span>',
								color : opt.color.dataLabelColor
							},
							tooltip : {
								headerFormat : '',
								pointFormatter : function() {
									return Highmap.pointFormat(this);
								}
							}
						});
					}
					var pointObj = Highmap.utils.loadMappoint(opt,dkey,level);
					/* 更新地图属性全路径内容 */
					Highmap.level = {
						id : key,
						name : cname,
						point : pointObj,
						geojson : geoJson
					};
					Highmap.levarray[Highmap.levarray.length-1] = Highmap.level ;
					Highmap.utils.mapHideLoading();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					common.utils.showErrorMsg('缺少地图数据，无法完成“' + cname
									+ '”的区域细化！', function() {
								Highmap.reload(Highmap.mapid);
							});
				}
			}); 	
		  },
		  /**
			 * 加载坐标点信息
			 * @param {}  opt
			 * @param {} 标识点的ID值
			 * @param {} 所属区域的主键
			 * @param {} 所属区域的层级
			 */
		  loadMappoint:function(opt,key,level){ 
		  	 /*Highmap.utils.mapShowLoading();*/
		  	var pointObj = {};
		  	var pointids = new Array();
		  	var pointinfo = {};
			var funcName = opt.pointLoader.doSetPoint;
			if (common.utils.isFunction(funcName)) {
					pointinfo = eval(funcName + "(key,level)");
				}
			/*加载各层标识点属性*/
			var ploader = Highmap.utils.loadPointer(pointinfo,opt.pointLoader);
		  	
		  	if(!ploader.isLoadPoint || ploader.points.length === 0){
		  		pointObj["enable"] = ploader.isLoadPoint;
		  		return pointObj;
		  	}
		  	 var url = '';
	         var parameters = {};
	         /* 读取地图数据读取器信息 */
	         if(ploader.isRemote){
	             url = ctx + Highmap.loadPointGeoUrl;
	             var queryName = "";
	             var params = new Array();
	             var mapper =  new Array();
	            
	             for (var index = 0; index < ploader.points.length; index++) {
	             	var point = ploader.points[index];
	             	pointids.push(common.utils.guid());
	             	if(index === 0){
	             		queryName = point.queryName;
	             	}else {
	             		queryName += "," + point.queryName;
	             	}
	             	var param = {"map_pac":key,"map_level":level};
	             	Highmap.utils.loadAuthParameters(param);
	             	/*加入每个属性点的固定参数*/
	             	var pp = point.parameters;
	             	if(common.utils.isJson(pp)){
						$.each(pp,function(key,value) {
							if(!param.hasOwnProperty(key))
								param[key] = value;
						});
	             	}	
	             	params.push(param);
	             	mapper.push(point.mapper);
	             }
	             parameters = {queryNames:queryName,param:params,mappers:mapper,mapperNum:mapper.length};
	             
	         }else{
	             url = ctx + ploader.localUrl + key + ".geo.json";
	         }
		  	 $.ajax({
				type : "GET",
				url : url,
				data:parameters,
				contentType : "application/json; charset=utf-8",
				dataType : 'json',
				crossDomain : true,
				success : function(json) {
							var geoArray = json;
							if(geoArray === null  || geoArray.length === 0) return;
							/*Highmap.utils.mapHideLoading();*/
							for (var index = 0; index < geoArray.length; index++) {
				             	var point = ploader.points[index];
				             	var pointid = pointids[index];
				             	var geo = geoArray[index];
				             	var data = [];
				             	if(geo.features && geo.features.length > 0){
				             		data = Highcharts.geojson(geo, 'mappoint')
				             	}
				             	Highmap.mapObject.addSeries({
										id:pointid,
										name: point.pointName,
						                type: 'mappoint',
						                data: data,
						                showInLegend:true,
						                color:point.pointColor,
            							marker: {
										   lineColor: point.lineColor,
										   lineWidth: point.lineWidth,
										   radius: point.radius
										},
						                dataLabels: {
						                    align: 'left',
						                    verticalAlign: 'middle',
						                    format: '<span style="font-family:'+Highmap.fontFamily+';font-size: 10px;">{point.properties.name}</span>'
						                },
						                animation: true,
						                visible:point.pointVisible,
						                tooltip: {
						                	headerFormat: '<p><span style="font-family:'+Highmap.fontFamily+';font-size: 12px;color:'+point.tooltipTitleColor+'">{series.name} : </span><br/>',
							                pointFormat: '<span style="font-family:'+Highmap.fontFamily+';font-weight:bold;font-size: 13px;color:'+point.tooltipTextColor+'">{point.name}</span></p>'
						                }
									});
				             }							
						}
					});
			pointObj["enable"] = ploader.isLoadPoint;
			pointObj["ids"] =  pointids;
			pointObj["num"] =  pointids.length;
			return pointObj;
		  },
		  /**
		   * 初始化series
		   * @param {} mapName 地图名称
		   * @param {} data 地图数据
		   * @param {} mapData 地图物理数据
		   * @param {} joinBy 地图KEY对应关系
		   * @return {}
		   */
		  createMapSeries:function(mapName,data,mapData,joinBy,opt){
		  	 var series = {};
		  	 var geoJson = Highcharts.geojson(Highcharts.maps[mapData]);
		  	 var data_ = Highmap.ChartDataFormate.FormatGeoData(geoJson,data);
		  	 if(Highmap.valueType === 'mapbubble'){
		  	 	series = [{
		  	 		type:'map',
	                name: mapName,
	                mapData: geoJson,
	                enableMouseTracking: false,
	                borderColor: opt.color.seriesBorderColor,
	                nullColor: opt.color.seriesNullColor,
	                showInLegend: false,
		            color: Highcharts.getOptions().colors[1],
		            dataLabels: {
		                enabled: true,
		                format: '<span style="font-family:'+Highmap.fontFamily+';font-weight:bold;font-size: 12px;color:'+opt.pointLoader.tooltipTextColor+'">{point.properties.name}</span>',
						color:opt.color.dataLabelColor
		            },states: {
		                hover: {
		                    color: opt.color.seriesSelectColor
		                },
		                select: {
		                    color: opt.color.seriesSelectColor
		                }
		            }
	            }];
		  	 }else{		  	 	
		  	 	series = [{
		  	 		type:'map',
		            data : data_,
		            mapData: geoJson,
		            joinBy:joinBy,
		            name: mapName,
		            borderColor: opt.color.seriesBorderColor,
	                nullColor: opt.color.seriesNullColor,
		        	borderRadius: 1,
		        	borderWidth: 1,
	                showInLegend: false,
		            color: Highcharts.getOptions().colors[1],
		            states: {
		                hover: {
		                    color: opt.color.seriesSelectColor
		                },
		                select: {
		                    color: opt.color.seriesSelectColor
		                }
		            },
		            dataLabels: {
		                enabled: true,
		                format: '<span style="font-family:'+Highmap.fontFamily+';font-weight:bold;font-size: 12px;">{point.properties.name}</span>',
						color:opt.color.dataLabelColor
		            },
		            tooltip: {
		               headerFormat: '',
		               pointFormatter: function () {
							return Highmap.pointFormat(this);
						}
						/*pointFormat: Highmap.pointFormat*/
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
		  		/*Highmap.mapObject.showLoading('<i class="icon-spinner icon-spin icon-3x"></i>');*/
		  		common.utils.showLoading();
		  	}
		  		
		  },
		  mapHideLoading:function(){
		  	if(Highmap.setLoading !== null && Highmap.setLoading.length > 1 
		  			&& common.utils.isFunction(Highmap.setLoading[0]) && common.utils.isFunction(Highmap.setLoading[1])){
		  		eval(Highmap.setLoading[1] + "()");
		  	}else{
		  		/*Highmap.mapObject.hideLoading();*/
		  		common.utils.closeLoading();
		  	}
		  }
	  }	  
	  
};
