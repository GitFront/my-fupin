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
/*禁止使用世界标准时间*/
Highcharts.setOptions({
	global: {
		useUTC: false
	}
});
/**
 * Highcharts二次封装工具类
 * @type 
 */
var Highchart = {	
	 
	   colors:["#7cb5ec","#434348","#90ed7d","#f7a35c","#8085e9","#f15c80","#e4d354","#2b908f","#f45b5b","#91e8e1"],	 
	  /**
	   * 数据格式化方法
	   * @type 
	   */
	  ChartDataFormate: {
		  /**
		   * 处理普通数据（无分组）
		   * @param {} data 待办理json数据
		   * @return {}
		   */
	      FormateNOGroupData: function (data) {
	      	  if(!common.utils.isArray(data)) data = [];
	          var categories = [];
	          var datas = [];
	          for (var i = 0; i < data.length; i++) {
	              categories.push(data[i].name || "");
	              datas.push([data[i].name, data[i].value || 0]);
	          }
	          return { category: categories, data: datas };
	      },
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
	       * 处理时序分组数据，数据格式 : name：XXX，group：XXX，value：XXX用于垂直折线图
	       * @param {} data 待办理json数据
	       * @return {}
	       */
	      FormatInvertedData: function (data) {
	      	  if(!common.utils.isArray(data)) data = [];
	          var names = new Array();
	          var groups = new Array();
	          var series = new Array();
	          for (var i = 0; i < data.length; i++) {
	              if (!names.contains(data[i].y))
	                  names.push(data[i].y);
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
	                      if (groups[i] == data[j].group && data[j].y == names[k])
	                          temp_data.push([data[j].y,data[j].x]);
	              }
	              temp_series = { name: groupName, data: temp_data, visible:visible };	              
	              series.push(temp_series);
	          }
	          
	          return { category: names, series: series };
	      },	      
	      /**
	       * 处理时序分组数据，数据格式 : name：XXX，group：XXX，value：XXX用于时序图
	       * @param {} data
	       * @param {} opt
	       * @return {}
	       */
	      FormatTimeSeriesData: function (data,opt) {
	      	  if(!common.utils.isArray(data)) data = [];
	          var groups = new Array();
	          var series = new Array();
	          var irregular = opt.irregular;
	          var type = 'area';
	          for (var i = 0; i < data.length; i++) {
	              if (!groups.contains(data[i].group))
	                  groups.push(data[i].group);
	          }
	          if(groups.length > 1) type = 'spline';
	          
	          for (var i = 0; i < groups.length; i++) {
	              var temp_series = {};
	              var temp_pointStart='';
	              var temp_names = new Array();
	              var temp_data = new Array();
	              var start  = true;
	              var groupName = groups[i];
	              var visible = true;/*默认为可见*/
	              if(groups[i].endsWith('|hide')){
	              	visible = false;
	              	groupName = groups[i].replace('|hide','');
	              }
	              /*是否为不规则数据*/
	              if(irregular){
	              	for (var j = 0; j < data.length; j++) {
	                      if (groups[i] == data[j].group){
	                      	  /*处理names相同的数据*/
	                      	  if(!temp_names.contains(data[j].name)){
	                      	  	temp_names.push(data[j].name);
	                      	  	temp_data.push([common.utils.getDate(data[j].name).getTime(),data[j].value]);
	                      	  }	                      	  
	                      }	                      
		              }
		             temp_series = { 
		              	type:type,
		              	name: groupName,
		              	data: temp_data,
		              	visible:visible
		              };
	              }else {
	              	 for (var j = 0; j < data.length; j++) {
	                      if (groups[i] == data[j].group){
	                      	  if(start) {
	                      	  	/*处理UTC时间数据*/
	                      	  	temp_pointStart = common.utils.getDate(data[j].name).getTime();
	                      	  	start=false;
	                      	  }
	                      	  temp_data.push(data[j].value);
	                      } 
		              }
		              temp_series = { 
		              	type:type,
		              	name: groupName,
		              	pointStart:temp_pointStart,
		              	pointInterval:opt.pointInterval,
		              	data: temp_data,
		              	visible:visible
		              };
	              }
	              
	              series.push(temp_series);
	          }
	          
	          return { series: series };
	      },
	      /**
	       * 处理结果主要用来展示X轴为日期的具有增幅的趋势的图，数据类型：name：XXX，value：XXX
	       * @param {} data 数据
	       * @param {} name 柱形图的图例名
	       * @param {} name1 线形图的图例名
	       * @return {}
	       */
	      FormatBarLineData: function (data, name, name1) {
	      	  if(!common.utils.isArray(data)) data = [];
	          var category = [];
	          var series = [];
	          var s1 = [];
	          var s2 = [];
	          for (var i = 1; i < data.length; i++) {
	              if (!category.contains(data[i].name))
	                  category.push(data[i].name);
	              s1.push(data[i].value);
	              var growth = 0;
	              if (data[i].value != data[i - 1].value)
	                  if (data[i - 1].value != 0)
	                      growth = Math.round((data[i].value / data[i - 1].value - 1) * 100);
	                  else
	                      growth = 100;
	              s2.push(growth);
	          }
	          series.push({ name: name, color: '#4572A7', type: 'column', yAxis: 1, data: s1, tooltip: { valueStuffix: ''} });
	          series.push({ name: name1, color: '#89A54E', type: 'spline', yAxis: 1, data: s2, tooltip: { valueStuffix: '%'} });
	          return {series: series};
	      }
	  },
	  ChartOptionTemplates: {
	  	  /**
	  	   * 饼状图
	  	   * @param {} container
	  	   * @param {} data
	  	   * @param {} opt
	  	   * @return {}
	  	   */
	      Pie: function (container,data, opt) {
	          var pie_datas = Highchart.ChartDataFormate.FormateNOGroupData(data);
	          
	          var option = {
	              chart: {
	              	  renderTo:container,
	                  type: 'pie',
	                  plotBackgroundColor: null,
	                  plotBorderWidth: null,
	                  plotShadow: false,
	                  events: {
						load: function(event) {
							var funcName = opt.doLoad;
							if(common.utils.isFunction(funcName)){
								eval(funcName + "(event.target)");
							}
						}
					  }

	              },
	              title: {
	                  text: opt.title || ''
	              },
	              tooltip: {
	                  pointFormat: '{series.name}: <b>{point.percentage'+opt.percentDecimal+'}%</b> ({point.y'+opt.yDecimalf+'}'+opt.yUnit+')'
	              },
	              plotOptions: {
	                  pie: {
	                      allowPointSelect: true,
	                      cursor: opt.cursor,
	                      dataLabels: {
	                          enabled: false
	                      },
	                      showInLegend: true
	                  },
	                  series: {
						point: {
							events: {
								click: function() {
									var funcName = opt.doClick;
									if(common.utils.isFunction(funcName)){
										
										eval(funcName + "(this.name,this.x,this.y)");
									}
								}
							}
						}
					  }
	              },
		          legend: {
			        	layout: 'vertical',
			        	enabled: opt.legend,
					    align: 'right',
					    verticalAlign: 'top',
					    x: 5,
					    y: 50,
					    borderWidth: 0,
					    labelFormatter: function () {
					        return this.name + '&nbsp';
					    },
					    useHTML: true
			        },              
	              series: [{
	                  name: opt.valueName || '',
	                  data: pie_datas.data
	              }]
	          };
	          return option;
	      },
	      /**
	       * 可以下钻的饼图
	       * @param {} data
	       * @param {} name
	       * @param {} title
	       * @return {}
	       */
	      DrillDownPie: function (data, name, title) {
	          var drilldownpie_datas;
	          var option = {
	              chart: {
	                  type: 'pie'
	              },
	              title: {
	                  text: title || ''
	              },
	              subtitle: {
	                  text: '请点击饼图项看详细占比'
	              },
	              plotOptions: {
	                  series: {
	                      dataLabels: {
	                          enabled: true,
	                          format: '{point.name}: {point.y:.1f}%'
	                      }
	                  }
	              },
	              tooltip: {
	                  headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	                  pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
	              },
	              series: [{
	                  name: name || '',
	                  colorByPoint: true,
	                  data: drilldownpie_datas.fir_data
	              }],
	              drilldown: {
	                  series: drilldownpie_datas.series
	              }
	          };
	          return option;
	      },
	      /**
	       * 折线图
	       * @param {} container
	       * @param {} data
	       * @param {} opt
	       * @return {}
	       */
	      Line: function (container,data,opt) {
	          var line_datas = Highchart.ChartDataFormate.FormatGroupData(data);
	          
	          
	          var option = {
	          	  chart: {
	              	  renderTo:container,
	              	  type:'spline',
	                  zoomType:opt.zoomType,
	                  events: {
						load: function(event) {
							var funcName = opt.doLoad;
							if(common.utils.isFunction(funcName)){
								eval(funcName + "(event.target)");
							}
						}
					  }
	              },
	              title: {
	                  text: opt.title || ''
	              },
	              subtitle: {
	                  text: ''
	              },
	              xAxis: {
	              	  title: {
	                      text: opt.xName || ''
	                  },
	                  labels: {
			                formatter: function () {
			                    return this.value + opt.xUnit;
			                }
			            },
			          min: opt.xMin,
			          allowDecimals:opt.xAllowDecimal,
	                  categories: line_datas.category
	              },
	              yAxis: {
	                  title: {
	                      text: opt.yName || ''
	                  },
			          min: opt.yMin,
			          allowDecimals:opt.yAllowDecimal,
	                  labels: {
			                formatter: function () {
			                    return this.value + opt.yUnit;
			                }
			            },
	                  plotLines: [{
	                      value: 0,
	                      width: 1,
	                      color: '#808080'
	                  }]
	              },
	              plotOptions: {
	                  spline: {
			             marker: {
			                 enable: false
			             }
			          },
	                  series: {
	                  	cursor: opt.cursor,
						point: {
							events: {
								click: function() {
									var funcName = opt.doClick;
									if(common.utils.isFunction(funcName)){
										eval(funcName + "(this.series.name,this.category,this.y)");
									}
								}
							}
						}
					  }
	              },
	              tooltip: {
	                  pointFormat: '<span style="color:{series.color}">{series.name}</span> : <b>{point.y'+opt.yDecimalf+'} '+opt.yUnit+'</b><br/>',
	                  shared: true,
	                  useHTML: true,
	    			  crosshairs: true
	              },
	              legend: {
	              	  enabled: opt.legend,
	                  layout: 'horizontal',
	                  align: 'center',
	                  verticalAlign: 'bottom'
	              },
	              series: line_datas.series
	          };
	          return option;
	      },
	      /**
	       * 时序折线图
	       * @param {} container
	       * @param {} data
	       * @param {} opt
	       */
	      TimeSeriesLine:function(container,data,opt){
	      	 var line_datas = Highchart.ChartDataFormate.FormatTimeSeriesData(data,opt);
	         
	         var option = {
	         	chart: {
	              	renderTo:container,
		            zoomType: 'x',
	                events: {
						load: function(event) {
							var funcName = opt.doLoad;
							if(common.utils.isFunction(funcName)){
								eval(funcName + "(event.target)");
							}
						}
					}
		        },
		        title: {
		            text: opt.title || ''
		        },
		        subtitle: {
		            text: ''
		        },
		        xAxis: {
		        	title: {
	                   text: opt.xName || ''
	                },
		            type: 'datetime',
		            allowDecimals:opt.xAllowDecimal,
		            tickInterval: opt.minRange
		        },
		        yAxis: {
		            title: {
	                   text: opt.yName || ''
	                },
			        min: opt.yMin,
			        allowDecimals:opt.yAllowDecimal,
	                labels: {
			           formatter: function () {
			               return this.value + opt.yUnit;
			           }
			        },
	                plotLines: [{
	                  value: 0,
	                  width: 1,
	                  color: '#808080'
	                }]
		        },
		        legend: {
		            enabled: opt.legend,
	                layout: 'horizontal',
	                align: 'center',
	                verticalAlign: 'bottom'
		        },
		        tooltip: {
	               pointFormat: '<span style="color:{series.color}">{series.name}</span> : <b>{point.y'+opt.yDecimalf+'} '+opt.yUnit+'</b><br/>',
	               shared: true,
	               useHTML: true,
	    		   crosshairs: true
	            },
		        plotOptions: {
		            area: {
		                fillColor: {
		                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
		                    stops: [
		                        [0, Highcharts.getOptions().colors[0]],
		                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
		                    ]
		                },
		                marker: {
		                    radius: 2
		                },
		                lineWidth: 1,
		                states: {
		                    hover: {
		                        lineWidth: 1
		                    }
		                },
		                threshold: null
		            },
		            spline:{
		                marker: {
		                    radius: 2
		                },
		                lineWidth: 2,
		                states: {
		                    hover: {
		                        lineWidth: 2
		                    }
		                },
		                threshold: null
		            },
	                  series: {
	                  	cursor: opt.cursor,
						point: {
							events: {
								click: function() {
									var funcName = opt.doClick;
									if(common.utils.isFunction(funcName)){
										eval(funcName + "(this.series.name,this.x,this.y)");
									}
								}
							}
						}
					  }
		        },
		        series: line_datas.series	              
	          };
	          return option;
	      },
     	      
	      /**
	       * 垂直折线图
	       * @param {} container
	       * @param {} data
	       * @param {} opt
	       * @return {}
	       */
	      InvertedLine: function (container,data,opt) {
	          var line_datas = Highchart.ChartDataFormate.FormatInvertedData(data);
	          
	          var option = {
	          	  chart: {
	          	  		renderTo:container,
			            type: 'spline',
			            inverted: true,
	                  	zoomType:opt.zoomType,
		                events: {
							load: function(event) {
								var funcName = opt.doLoad;
								if(common.utils.isFunction(funcName)){
									eval(funcName + "(event.target)");
								}
							}
						}
			        },
			        title: {
			            text: opt.title || ''
			        },
			        subtitle: {
			            text: ''
			        },
			        xAxis: {
			            reversed: false,
			            title: {
			                text: opt.xName || ''
			            },
			            min: opt.xMin,
			            allowDecimals:opt.xAllowDecimal,
			            labels: {
			                formatter: function () {
			                    return this.value + opt.xUnit;
			                }
			            },
            			showLastLabel: true
			        },
			        yAxis: {
			            title: {
			                text: opt.yName || ''
			            },
			          	min: opt.yMin,
			          	allowDecimals:opt.yAllowDecimal,
			            labels: {
			                formatter: function () {
			                    return this.value + opt.yUnit;
			                }
			            },
	                  plotLines: [{
	                      value: 0,
	                      width: 1,
	                      color: '#808080'
	                  }]
			        },
			        tooltip: {
			          headerFormat: '<b>{series.name}</b><br/>',
            		  pointFormat: '{point.x'+opt.xDecimalf+'} '+opt.xUnit+' :  {point.y'+opt.yDecimalf+'}' + opt.yUnit
			        },
			        plotOptions: {
			            spline: {
			                marker: {
			                    enable: false
			                }
			            },
	                    series: {
	                  		cursor: opt.cursor,
							point: {
								events: {
									click: function() {
										var funcName = opt.doClick;
										if(common.utils.isFunction(funcName)){
											eval(funcName + "(this.series.name,this.category,this.y)");
										}
									}
								}
							}
					    }			            
			        },
			        legend: {
			             enabled: opt.legend
		              },
			        series: line_datas.series            
	              
	          };
	          return option;
	      },
	      /**
	       * 柱形图
	       * @param {} container
	       * @param {} data
	       * @param {} opt
	       * @return {}
	       */
	      Bars: function (container,data,opt) {
	          var bars_datas = Highchart.ChartDataFormate.FormatGroupData(data);
	          
	          var option = {
	              chart: {
	              	  renderTo:container,
	                  type: opt.horiz,
	                  zoomType:opt.zoomType,
		              events: {
							load: function(event) {
								var funcName = opt.doLoad;
								if(common.utils.isFunction(funcName)){
									eval(funcName + "(event.target)");
								}
							}
					  }
	              },
	              title: {
	                  text: opt.title || ''
	              },
	              subtitle: {
	                  text: ''
	              },
	              credits: {
	                  enabled: false
	              },
	              xAxis: {
	              	  title: {
	                      text: opt.xName
	                  },
			          min: opt.xMin,
			          allowDecimals:opt.xAllowDecimal,
	                  labels: {
			                formatter: function () {
			                    return this.value + opt.xUnit;
			                }
			           },
	                  categories: bars_datas.category
	              },
	              yAxis: {
	                  //min: 0,
	                  title: {
	                      text: opt.yName
	                  },
			          min: opt.yMin,
			          allowDecimals:opt.yAllowDecimal,
	                  labels: {
			                formatter: function () {
			                    return this.value + opt.yUnit;
			                }
			          }
	              },
	              tooltip: {
	                  pointFormat: '<span style="color:{series.color}">{series.name}</span> : <b>{point.y'+opt.yDecimalf+'}</b><br/>',
	                  shared: true,
	                  useHTML: true,
	    			  crosshairs: true
	              },
	              plotOptions: {
	                  series: {
	                  	  cursor: opt.cursor,
	                      pointPadding: 0.2,
	                      borderWidth: 0,
	                      point: {
								events: {
									click: function() {
										var funcName = opt.doClick;
										if(common.utils.isFunction(funcName)){
											eval(funcName + "(this.series.name,this.category,this.y)");
										}
									}
							   }
						  }	
	                  }
	              },
	              legend: {
			             enabled: opt.legend,
		                 layout: 'horizontal',
		                 align: 'center',
		                 verticalAlign: 'bottom'
		           },
	              series: bars_datas.series
	          };
	          return option;
	      },
	      /**
	       * 堆积柱形图
	       * @param {} container
	       * @param {} data
	       * @param {} opt
	       * @return {}
	       */
	      StackBars:function(container,data,opt){
	      	var bars_datas = Highchart.ChartDataFormate.FormatGroupData(data);
	      	
	          var option = {
	              chart: {
	              	  renderTo:container,
	                  type: opt.horiz,
	                  zoomType:opt.zoomType,
		              events: {
							load: function(event) {
								var funcName = opt.doLoad;
								if(common.utils.isFunction(funcName)){
									eval(funcName + "(event.target)");
								}
							}
					  }
	              },
	              title: {
	                  text: opt.title || ''
	              },
	              subtitle: {
	                  text: ''
	              },
	              credits: {
	                  enabled: false
	              },
	              xAxis: {
	              	  title: {
	                      text: opt.xName || ''
	                  },
			          min: opt.xMin,
			          allowDecimals:opt.xAllowDecimal,
	                  labels: {
			                formatter: function () {
			                    return this.value + opt.xUnit;
			                }
			            },
	                  categories: bars_datas.category
	              },
	              yAxis: {
	                  //min: 0,
	                  title: {
	                      text: opt.yName || ''
	                  },
			          min: opt.yMin,
			          allowDecimals:opt.yAllowDecimal,
	                  labels: {
			                formatter: function () {
			                    return this.value + opt.yUnit;
			                }
			            }
	              },
	              legend: {
			             enabled: opt.legend,
		                 layout: 'horizontal',
		                 align: 'center',
		                 verticalAlign: 'bottom'
		           },
	              series: bars_datas.series
	          };
	      	  var stack_option = {};
	          if (opt.isPercent) {
	              stack_option = {
	                  tooltip: {
	                      pointFormat: '<span style="color:{series.color}">{series.name}</span> : <b>{point.y'+opt.yDecimalf+'}</b> ({point.percentage'+opt.percentDecimal+'}%)<br/>',
	                      shared: true,
	    				  crosshairs: true
	                  },
	                  plotOptions: {
	                      series: {
	                  		  cursor: opt.cursor,
	                          stacking: 'percent',
		                      point: {
									events: {
										click: function() {
											var funcName = opt.doClick;
											if(common.utils.isFunction(funcName)){
												eval(funcName + "(this.series.name,this.category,this.y)");
											}
										}
								   }
							  }
	                      }
	                  }
	              };
	              
	          }else {
	              stack_option = {
	                  tooltip: {
	                  	  formatter: function () {
	                            return  this.x + '<br/>' +
				                        '<span>'+this.series.name + '</span> : <b>' + Number(this.y).toFixed(opt.yDecimal) + '</b><br/>' +
				                        '<span style="color:#F58221">Total</span> : <b>' + Number(this.point.stackTotal).toFixed(opt.yDecimal) + '</b>';
	                        }
	                  },
	                  plotOptions: {
	                      series: {
	                          stacking: 'normal',
	                  		  cursor: opt.cursor,
		                      point: {
									events: {
										click: function() {
											var funcName = opt.doClick;
											if(common.utils.isFunction(funcName)){
												eval(funcName + "(this.series.name,this.category,this.y)");
											}
										}
								   }
							  }
	                      }
	                  }
	              };
	          }
	          return $.extend({}, option, stack_option);
	      },  

	      /**
	       * 仪表盘
	       * @param {} container
	       * @param {} data
	       * @param {} opt
	       * @return {}
	       */
	      Gauge:function(container,data,opt){
	      	  if(!common.utils.isArray(data)) data = [data];
	      	  var option = {
	      	  		chart: {
	      	  			renderTo:container,
			            type: 'solidgauge',
			            events: {
							load: function(event) {
								var funcName = opt.doLoad;
								if(common.utils.isFunction(funcName)){
									eval(funcName + "(event.target)");
								}
							}
						}
			        },
			        title: null,
			        pane: {
			            center: ['50%', '85%'],
			            size: '140%',
			            startAngle: -90,
			            endAngle: 90,
			            background: {
			                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
			                innerRadius: '60%',
			                outerRadius: '100%',
			                shape: 'arc'
			            }
			        },
			        tooltip: {
			            enabled: false
			        },
			        yAxis: {
			        	min: opt.gaugeMin,
			            max: opt.gaugeMax,
			            title: {
			                text: null
			            },
			            allowDecimals:opt.yAllowDecimal,
			            stops:opt.stops,
			            lineWidth: 0,
			            minorTickInterval: null,
			            tickPixelInterval: 400,
			            tickWidth: 0,
			            title: {
			                y: -70
			            },
			            labels: {
			                y: 16
			            }
			        },
			        plotOptions: {
			            solidgauge: {
			                dataLabels: {
			                    y: 5,
			                    borderWidth: 0,
			                    useHTML: true
			                }
			            }
			        },			
			        credits: {
			            enabled: false
			        },			
			        series: [{
			            name: opt.valueName || '',
			            data: data,
			            dataLabels: {
			                format: '<div style="text-align:center"><span style="font-size:25px;color:' +
			                    ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
			                       '<span style="font-size:12px;color:silver">'+opt.yUnit+'</span></div>'
			            },
			            tooltip: {
			                valueSuffix: opt.yUnit || ''
			            }
			        }]
		      	  };
		      	  return option;
	      },     
	      
	      
	      
	      Pyramid: function (data, name, title) {
	          var pyramid_datas = Highchart.ChartDataFormate.FormateNOGroupData(data);
	          var option = {
	              chart: {
	                  type: 'pyramid',
	                  marginRight: 100
	              },
	              title: {
	                  text: title || '',
	                  x: -50
	              },
	              plotOptions: {
	                  series: {
	                      dataLabels: {
	                          enabled: true,
	                          format: '<b>{point.name}</b> ({point.y'+opt.yDecimalf+'})',
	                          color: 'black',
	                          softConnector: true
	                      }
	                  }
	              },
	              legend: {
	                  enabled: false
	              },
	              series: [{
	                  name: name || '',
	                  data: pyramid_datas.data
	              }]
	          };
	          return option;
	      }
	  },
	  create:{
	  	/**
	  	 * 创建报表的初始化方法
	  	 * @param {} container
	  	 * @param {} data
	  	 * @param {} option
	  	 * @param {} type
	  	 * @return {}
	  	 */
	  	init:function(container,data, option,type){
	  		/*清洗并规范化自定义配置项*/
	  		var option_ = Highchart.utils.defaultOption(option);
	  		/*判断是否需要从前端统计数据*/
	  		if(option_.stats) data = Highchart.utils.dataStatistics(data,type);
	  		if(type === 'TimeSeriesLine') {
			  /*动态设置数据的步长信息*/
			  option_["minRange"] = Highchart.utils.getTimeSeriesMinRange(data);
			}
	  		option_["container"] = container;
	  		option_["optionType"] = type;
	  		var opt = eval("Highchart.ChartOptionTemplates."+type+"(container,data,option_)");
	    	var chart = new Highcharts.Chart(opt);
	    	var obj = {
	    		chart:chart,
	    		chartType:type,
	    		option:option_,
	    		data:data
	    	};
	    	/*对报表对象及自定义配置项进行管理*/
	    	var id = common.report.highcharts.object.add(obj);    	
	    	return id;
	  	},  	
	  	/**
	  	 * 初始化饼图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} data 数据集(DIM NAME and DATA)
	  	 * @param {} option 自定义配置参数
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Pie:function(container,data, option){
	  		return this.init(container,data,option,"Pie");
	  	},
	    /**
	  	 * 初始化折线图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} data 数据集(DIM NAME and DATA)
	  	 * @param {} option 自定义配置参数
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Line:function(container,data, option){
	  		return this.init(container,data,option,"Line");
	  	},
	  	/**
	  	 * 初始化垂直折线图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} data 数据集(DIM NAME and DATA)
	  	 * @param {} option 自定义配置参数
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	InvertedLine:function(container,data, option){
	  		return this.init(container,data,option,"InvertedLine");
	  	},
	    /**
	  	 * 初始化柱形图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} data 数据集(DIM NAME and DATA)
	  	 * @param {} option 自定义配置参数
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Bars:function(container,data,option){
	  		return this.init(container,data,option,"Bars");
	  	},
	  	/**
	  	 * 初始化堆积柱形图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} data 数据集(DIM NAME and DATA)
	  	 * @param {} option 自定义配置参数
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	StackBars:function(container,data,option){
	  		return this.init(container,data,option,"StackBars");
	  	},
	  	/**
	  	 * 初始化时序图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} data 数据集(DIM NAME and DATA)
	  	 * @param {} option 自定义配置参数
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	TimeSeriesLine:function(container,data,option){
	  		return this.init(container,data,option,"TimeSeriesLine");
	  	},
	  	/**
	  	 * 初始化仪表盘图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} data 数据集(DIM NAME and DATA)
	  	 * @param {} option 自定义配置参数
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Gauge:function(container,data,option){
	  		return this.init(container,data,option,"Gauge");
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
	  reload: function (chartid,data,newOption,type) {
	      var obj = common.report.highcharts.object.get(chartid);
	      if(obj != null){
	      	  var chart = obj.chart;
	      	  chart.showLoading();
			  var opt = obj.option;
			  var container = opt.container;
			  if(!common.utils.isDefined(type)){
			  	 type = opt.optionType;
			  }
			  
			  if(!common.utils.isDefined(data)){
			  	  data = obj.data;
			  }			  
			  if(common.utils.isDefined(newOption)){
			  	  opt = Highchart.utils.defaultOption(newOption);
			  	  opt["container"] = container;
	  			  opt["optionType"] = type;
			  }
			  /*判断是否需要从前端统计数据*/
	  		  if(opt.stats) data = Highchart.utils.dataStatistics(data,type);
	  		  if(type === 'TimeSeriesLine') {
			  	 /*动态设置数据的步长信息*/
			  	 opt["minRange"] = Highchart.utils.getTimeSeriesMinRange(data);
			  } 
			  
			  var option = eval("Highchart.ChartOptionTemplates."+type+"(container,data,opt)");
			  /*销毁图表对象 */
			  chart.destroy();
			  chart.hideLoading();
			  chart= new Highcharts.Chart(option);
			  
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
	  	 * @param {} option 自定义配置项
	  	 * @param {} type 图表类型
	  	 * @return {} 
	  	 */
	  	init:function(container,url,parameters,option,type){
	  		/*创建空的报表，并获取报表对象*/
	  		var id = Highchart.create.init(container,[],option,type);
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
	             	
	             	Highchart.reload(id,data);
	             	/*动态加载数据*/
	             	/*chart = Highchart.utils.initSeries(chart,data,opt,type);*/
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
	  	 * 通过ajax初始化饼图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} url 请求url
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"} (可选)
	  	 * @param {} option 自定义配置参数(可选)
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Pie:function(container,url,parameters, option){
	  		return this.init(container,url,parameters,option,"Pie");
	  	},
	    /**
	  	 * 通过ajax初始化折线图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} url 请求url
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}(可选)
	  	 * @param {} option 自定义配置参数(可选)
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Line:function(container,url,parameters, option){
	  		return this.init(container,url,parameters,option,"Line");
	  	},
	  	/**
	  	 * 通过ajax初始化垂直折线图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} url 请求url
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}(可选)
	  	 * @param {} option 自定义配置参数(可选)
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	InvertedLine:function(container,url,parameters, option){
	  		return this.init(container,url,parameters,option,"InvertedLine");
	  	},
	    /**
	  	 * 通过ajax初始化柱形图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} url 请求url
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}(可选)
	  	 * @param {} option 自定义配置参数(可选)
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Bars:function(container,url,parameters,option){
	  		return this.init(container,url,parameters,option,"Bars");
	  	},
	  	/**
	  	 * 通过ajax初始化堆积柱形图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} url 请求url
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}(可选)
	  	 * @param {} option 自定义配置参数(可选)
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	StackBars:function(container,url,parameters,option){
	  		return this.init(container,url,parameters,option,"StackBars");
	  	},
	  	/**
	  	 * 通过ajax初始化时序图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} url 请求url
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}(可选)
	  	 * @param {} option 自定义配置参数(可选)
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	TimeSeriesLine:function(container,url,parameters,option){
	  		return this.init(container,url,parameters,option,"TimeSeriesLine");
	  	},
	  	/**
	  	 * 通过ajax初始化仪表盘图图表
	  	 * @param {} container 报表显示指向
	  	 * @param {} url 请求url
	  	 * @param {} parameters 请求参数对象必须为key/value格式，例如{foo1:"bar1",foo2:"bar2"}(可选)
	  	 * @param {} option 自定义配置参数(可选)
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Gauge:function(container,url,parameters,option){
	  		return this.init(container,url,parameters,option,"Gauge");
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
		    		  return Highchart.reload(chartid,data,newOption);
		    		
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
			             	Highchart.reload(chartid,data,newOption);
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
	  	 * 通过mybatis初始化饼图图表
	  	 * @param {} container 报表指向的divid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{name:'name?',value:'value?'}
		 * @param {} option 自定义配置项
		 * @param {} rowsValue 行转列配置，针对同一行数据不同字段进行分维度显示。（格式为field_name1:维度名称1;field_name2:维度名称2）
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Pie:function(container,queryName,parameters,mapper,option,rowsValue){
	  		var type = 'Pie';
			var url = ctx+"/a/report/highchart/pieData/load";	
	  		/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(parameters);
			params.mapper = common.utils.strToJson(mapper);
			params.rowsValue = rowsValue;			

	  		return Highchart.ajax.init(container,url,params,option,type);
	  	},
	    /**
	  	 * 通过mybatis初始化折线图图表
	  	 * @param {} container 报表指向的divid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{name:'name?',value:'value?',group:'group?'}
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 * @param {} rowsGroup 行转列配置，针对同一行数据不同字段进行分组显示。（格式为{field_name1:'分组名称1',field_name2:'分组名称2'}）
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Line:function(container,queryName,parameters,mapper,option,hideGroup,rowsGroup){
	  		var type = 'Line';
			var url = ctx+"/a/report/highchart/groupData/load";	
	  		/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(parameters);
			params.mapper = common.utils.strToJson(mapper);
			params.hideGroup = hideGroup;
			params.rowsGroup = rowsGroup;

	  		return Highchart.ajax.init(container,url,params,option,type);
	  	},
	  	/**
	  	 * 通过mybatis初始化垂直折线图图表
	  	 * @param {} container 报表指向的divid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{x:'x?',y:'y?',group:'group?'}
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	InvertedLine:function(container,queryName,parameters,mapper,option,hideGroup){
	  		var type = 'InvertedLine';
			var url = ctx+"/a/report/highchart/invertedLine/load";	
	  		/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(parameters);
			params.mapper = common.utils.strToJson(mapper);
			params.hideGroup = hideGroup;

	  		return Highchart.ajax.init(container,url,params,option,type);
	  	},
	    /**
	  	 * 通过mybatis初始化柱形图图表
	  	 * @param {} container 报表指向的divid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{name:'name?',value:'value?',group:'group?'}
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 * @param {} rowsGroup 行转列配置，针对同一行数据不同字段进行分组显示。（格式为{field_name1:'分组名称1',field_name2:'分组名称2'}）
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Bars:function(container,queryName,parameters,mapper,option,hideGroup,rowsGroup){
	  		var type = 'Bars';
			var url = ctx+"/a/report/highchart/groupData/load";
	  		/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(parameters);
			params.mapper = common.utils.strToJson(mapper);
			params.hideGroup = hideGroup;
			params.rowsGroup = rowsGroup;

	  		return Highchart.ajax.init(container,url,params,option,type);
	  	},
	  	/**
	  	 * 通过mybatis初始化堆积柱形图图表
	  	 * @param {} container 报表指向的divid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{name:'name?',value:'value?',group:'group?'}
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 * @param {} rowsGroup 行转列配置，针对同一行数据不同字段进行分组显示。（格式为{field_name1:'分组名称1',field_name2:'分组名称2'}）
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	StackBars:function(container,queryName,parameters,mapper,option,hideGroup,rowsGroup){
	  		var type = 'StackBars';
			var url = ctx+"/a/report/highchart/groupData/load";	
	  		/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(parameters);
			params.mapper = common.utils.strToJson(mapper);
			params.hideGroup = hideGroup;
			params.rowsGroup = rowsGroup;

	  		return Highchart.ajax.init(container,url,params,option,type);
	  	},
	  	/**
	  	 * 通过mybatis初始化时序图图表
	  	 * @param {} container 报表指向的divid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{name:'name?',value:'value?',group:'group?'}
		 * @param {} option 自定义配置项
		 * @param {} hideGroup 隐藏分组名。格式为'group1名称;group2名称;group3名称'
		 * @param {} rowsGroup 行转列配置，针对同一行数据不同字段进行分组显示。（格式为{field_name1:'分组名称1',field_name2:'分组名称2'}）
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	TimeSeriesLine:function(container,queryName,parameters,mapper,option,hideGroup,rowsGroup){
	  		var type = 'TimeSeriesLine';
			var url = ctx+"/a/report/highchart/groupData/load";	
	  		/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(parameters);
			params.mapper = common.utils.strToJson(mapper);
			params.hideGroup = hideGroup;
			params.rowsGroup = rowsGroup;

	  		return Highchart.ajax.init(container,url,params,option,type);
	  	},
	  	/**
	  	 * 通过mybatis初始化仪表盘图图表
	  	 * @param {} container 报表指向的divid(必选)
		 * @param {} queryName mybatis的对应查询名称(必选)
		 * @param {} parameters 自定义查询参数。格式为{key1:'value1',key2:'value2'}
		 * @param {} mapper 数据字段对应信息。格式为{value:'value?'}
		 * @param {} option 自定义配置项
		 * @param {} decimal 数据精度
	  	 * @return {} 返回统一对象管理ID (可用common.report.highcharts.object.get(id).chart来获取图表对象)
	  	 */
	  	Gauge:function(container,queryName,parameters,mapper,option,decimal){
	  		var type = 'Gauge';
			var url = ctx+"/a/report/highchart/gauge/load";	
			/*通过AJAX生成Highchart报表*/
			var params = {};
			params.queryName = queryName;
			params.param = common.utils.strToJson(parameters);
			params.mapper = common.utils.strToJson(mapper);
			params.decimal = decimal;

	  		return Highchart.ajax.init(container,url,params,option,type);
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
		    	return Highchart.ajax.reload(chartid,url,params,newOption); 				
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
				horiz : false, /* 柱形图是否需要横向显示 */
				valueName : '数值', /* 数据名称(默认为‘数值’)-饼状图、仪表盘特有 */
				xName : '', /* x轴名称(默认为空) */
				xMin : 0, /* x轴最少值(默认为0) */
				xUnit : '', /* x轴单位(默认为空) */
				xDecimal : 0, /* x轴数据精度(默认为不保留小数位){最高只支持4位小数} */
				xAllowDecimal:true, /*x轴是否允许显示小数(默认为true)*/
				yName : '', /* y轴名称(默认为空) */
				yMin : 0, /* y轴最少值(默认为0) */
				yUnit : '', /* y轴单位(默认为空) */
				yDecimal : 0, /* y轴数据精度(默认为不保留小数位){最高只支持4位小数} */
				yAllowDecimal:true, /*y轴是否允许显示小数(默认为true)*/
				stats : false, /* 是否需要进行前端数据汇总(默认为false) */
				isPercent : false, /* 是否显示百分比数据(默认为false)-堆积图特有 */
				percentDecimal : 2,/* 显示百分比数据精度(默认为保留2位小数){最高只支持4位小数} */
				legend : true, /* 是否显示图例(默认为true) */
				zoomType : '', /* 开启哪个轴的放大缩小功能'x'/'y'(默认不开启) */
				minRange : 14 * 24 * 3600000, /* 时序图的x轴间隙时间为多少(默认为14天) */
				pointInterval : 24 * 3600 * 1000, /* 时序图每个数据点的间隙时间为多少(默认为1天) */
				irregular : true, /* 时序图数据点是否不连续(默认为不连续数据)，为true时，数据可以为断续数据；为false时，数据只能为连续数据 */
				gaugeMax : 100, /* 仪表盘最大值(默认为100) */
				gaugeMin : 0, /* 仪表盘最小值(默认为0) */
				stops : [0.4, 0.6, 0.8], /* 仪表盘颜色步长设置，输入数组，百分比数据(默认为[0.4,0.6,0.8]) */
				greatValue : 'up', /*仪表盘颜色分布设置(默认为'up')；up:大值优良，分布从左至右为红/黄/绿；down:小值优良，分布从左至右为绿/黄/红*/
				doClick : '', /*传入方法名，可以在图表数据点点击时触发监听事件，附带参数为chart对象，eg:function testload(chart)*/
				doLoad : '' /*传入方法名，可以在图表加载之前触发监听事件，附带参数为（数据点名，x轴值，y轴值），eg:function：testclick(name,x,y) */
			};
			/*设置传入自定义参数默认值*/
			var option_ = common.utils.setDefaultOption(option,defaultOpt);
			
			/* 转换xy轴数据精度数据格式 */
			option_["xDecimalf"] = this.convertDecimal(option_.xDecimal);
			option_["yDecimalf"] = this.convertDecimal(option_.yDecimal);
			option_["percentDecimal"] = this
					.convertDecimal(option_.percentDecimal);

			/* 转换柱形图显示方式 */
			if (option_.horiz) {
				option_["horiz"] = 'bar';
			} else {
				option_["horiz"] = 'column';
			}
			/*转换图表步长信息*/
			option_["stops"] = this.convertStops(option_.stops,
					option_.greatValue);

			/* 设置数据点鼠标样式 */
			option_["cursor"] = '';
			if (common.utils.isFunction(option_["doClick"])) {
				option_["cursor"] = 'pointer';
			}
			return option_;
		  	  
		  },	  
		  /**
			 * 转换数据精度格式（最高只支持4位小数）
			 * 
			 * @param {} opt
			 */
		  convertDecimal:function(decimal){	  	  
			  var temp_ = '';
		  	  switch(decimal)
			  {
				case 1:
				  temp_ = ':.1f' ;
				  break;
				case 2:
				  temp_ = ':.2f' ;
				  break;
				case 3:
				  temp_ = ':.3f' ;
				  break;
				case 4:
				  temp_ = ':.4f' ;
				  break;
				default:
				  temp_ = ':.0f' ;	
			  }
			  return temp_;
			  
		  },
		  /**
		   * 转换仪表盘步长数据格式
		   * @param {} stops
		   * @param {} greatValue
		   * @return {}
		   */
		  convertStops:function(stops,greatValue){	  	  
			  var temp_ =  new Array();
			  var colors = new Array();
			  /*定义颜色值*/
			  if(greatValue == 'down'){
			  	colors[0] = '#55BF3B';/*green*/
			  	colors[1] = '#DDDF0D';/*yellow*/
			  	colors[2] = '#DF5353';/*red*/
			  }else{
			  	colors[0] = '#DF5353';/*red*/
			  	colors[1] = '#DDDF0D';/*yellow*/
			  	colors[2] = '#55BF3B';/*green*/
			  }
		  	  for (var index = 0; index < colors.length; index++) {
		  	  	temp_.push([stops[index],colors[index]]);
		  	  }		  
			  return temp_;
			  
		  },
		  /**
		   * 初始化报表的series数据
		   * @param {} chart
		   * @param {} data
		   * @param {} opt
		   * @param {} type
		   * @return 返回报表对象
		   */
		  initSeries:function(chart,data,opt,type){
		  	/*先清除原有的series数据*/
			while(chart.series.length > 0){
				chart.series[0].remove(false);
			}
			/*针对不同的报表进行数据加载*/
			switch (type)
				{
				case 'Pie':
				  var pie_datas = Highchart.ChartDataFormate.FormateNOGroupData(data);
				  var series_ = {
	                  name: opt.valueName || '',
	                  data: pie_datas.data
	              };
	              chart.addSeries(series_,false);
				  break;
				case 'Line':
				  var line_datas = Highchart.ChartDataFormate.FormatGroupData(data);
				  chart.xAxis[0].setCategories(line_datas.category,false);
				  $.each(line_datas.series, function(itemNo, series_) {
				  	chart.addSeries(series_,false);
				  });
				  break;
				case 'InvertedLine':
				  var line_datas = Highchart.ChartDataFormate.FormatInvertedData(data);
				  $.each(line_datas.series, function(itemNo, series_) {
				  	chart.addSeries(series_,false);
				  });				  
				  break;
				case 'Bars':
				  var bars_datas = Highchart.ChartDataFormate.FormatGroupData(data);
				  chart.xAxis[0].setCategories(bars_datas.category,false);
				  $.each(bars_datas.series, function(itemNo, series_) {
				  	chart.addSeries(series_,false);
				  });
				  break;
				case 'StackBars':
				  var bars_datas = Highchart.ChartDataFormate.FormatGroupData(data);
				  chart.xAxis[0].setCategories(bars_datas.category,false);
				  $.each(bars_datas.series, function(itemNo, series_) {
				  	chart.addSeries(series_,false);
				  });				
				  break;
				case 'TimeSeriesLine':
				  var line_datas = Highchart.ChartDataFormate.FormatTimeSeriesData(data,opt.pointInterval);
				  $.each(line_datas.series, function(itemNo, series_) {
				  	chart.addSeries(series_,false);
				  });
				  break;
				case 'Gauge':
				  if(!common.utils.isArray(data)) data = [data];
				  	chart.options.series[0].data = data;			
				  break;
				default:
				  break;
			}
			/*重新加载报表数据*/
			chart.redraw();
			return chart;
				
		  },
		  /**
		   * 通过数据集成计算最小跨度
		   * @param {} data
		   */
		  getTimeSeriesMinRange:function(data){
				var minRange = 24*3600000;
				if(!common.utils.isArray(data) || data.length === 0) return minRange;
				var group_ = "";
				var s_datetime_ = "";
				var e_datetime_ = "";
				var groupNum = 0;
				for (var index = 0; index < data.length; index++) {
					if(index === 0){
						group_ = data[0].group;
						s_datetime_ = data[0].name;
						e_datetime_ = data[0].name;
						groupNum++ ;
					}else{						
						if(group_ === data[index].group){
							if(data[index].name > e_datetime_) e_datetime_ = data[index].name;
							groupNum++ ;
						}
					}					
				}
				
				var dateType = 'day';
				if(s_datetime_.length > 10) dateType = 'hour';
				
				var len = common.utils.getDateDiff(s_datetime_,e_datetime_,dateType);
				
				minRange = this.countMinRange(len,9,dateType);
				return minRange;
		  },
		  
		   /**
			 * 计算时序图表横轴间隙算法
			 * @param len 数据点数
			 * @param x 算法基数
			 * @param dateType 数据点时间类型
			 * @return 返回间隙数
			 */
		  countMinRange:function(len,x,dateType){
				var minRange = 0;
				var hour = 3600 * 1000;
				var day = 24;
				var week = 7;
				var month = 30;
				var year = 365;
				if(dateType === 'hour'){
					//小时列表算法
					if(len <= 10 ){
						minRange = hour;
					}else if(len > 10 && len <= (x*day)){
						var y = len/x;
						if(len%x != 0) y++;
						minRange = y * hour; //小时
					}else if(len > (x*day) && len <= (x*day*week)){
						var y = len/(x*day);
						if(len%(x*day) != 0) y++;
						minRange = y * day * hour; //天
					}else if(len > (x*day*week) && len <= (x*day*month)){
						var y = len/(x*day*week);
						if(len%(x*day*week) != 0) y++;
						minRange = y * day * week * hour; //周
					}else if(len > (x*day*month) && len <= (x*day*year)){
						var y = len/(x*day*month);
						if(len%(x*day*month) != 0) y++;
						minRange = y * day * month * hour; //月
					}else{
						var y = len/(x*day*year);
						if(len%(x*day*year) != 0) y++;
						minRange = y * day * year * hour; //年
					}		
				}else{
					//日期列表算法			
					if(len <= 10 ){
						minRange = day * hour;
					}else if(len > 10 && len <= (week*x)){
						var y = len/x;
						if(len%x != 0) y++;
						minRange = y * day * hour;//天
					}else if(len > (week*x) && len <= (x*month)){
						var y = len/(x*week);
						if(len%(x*week) != 0) y++;
						minRange = y * week * day * hour;//周
					}else if(len > (x*month) &&  len <= (x*year)){
						var y = len/(x*month);
						if(len%(x*month) != 0) y++;
						minRange = y * month * day * hour;//月
					}else{
						var y = len/(x*year);
						if(len%(x*year) != 0) y++;
						minRange = y * year * day * hour;//年
					}			
				}
				return minRange;
			},
			/**
			 * 通过前台对报表数据进行后处理
			 * @param {} rows 数据来源
			 * @param {} type 图表类型
			 * @return {}
			 */
			dataStatistics:function(rows,type){
				var isGroup = true;
				if(type === 'Pie') isGroup = false;
				var s_data = new Array(); 
				 
				 for (var i = 0; i < rows.length; i++) {
					 var merger = false;
					 var name_ = rows[i]["name"];
					 var value_ = rows[i]["value"];
					 var group_ = rows[i]["group"];
					 
					 if(isGroup) {
					 	/*分组图表处理*/
						 for (var j = 0; j < s_data.length; j++) {
							 if(s_data[j].group === group_ && s_data[j].name === name_){
								 s_data[j].value = s_data[j].value + value_;
								 merger = true;
								 break;
							 }								 
						 }
					 }else{
					 	/*非分组图表处理*/
						 for (var j = 0; j < s_data.length; j++) {
							 if(s_data[j].name === name_){
								 s_data[j].value = s_data[j].value + value_;
								 merger = true;
								 break;
							 }								 
						 }
					 }
					 /*不需要匹配统计数据*/
					 if(!merger){
						 var data_ = {};
						 data_["name"] =  name_;
						 data_["value"] =  value_;
						 if(isGroup) data_["group"] =  group_;
						 s_data.push(data_); 
					 }					 					
				 }
				return s_data;
			}
			
	  }	  
	  
};
