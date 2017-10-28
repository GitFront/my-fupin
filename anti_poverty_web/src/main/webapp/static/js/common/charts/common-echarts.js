$.namespace("common.report.echarts");
/* 判断数组中是否包含某个元素 */
Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}
/**
 * Echarts二次封装工具类
 * 
 * @type
 */
var Echart = {

    /**
	 * 数据格式化方法
	 * 
	 * @type
	 */
    ChartDataFormate : {
        /**
		 * 处理普通数据（无分组）
		 * 
		 * @param {}
		 *            data 待办理json数据
		 * @return {}
		 */
        FormateNOGroupData : function(data) {
            if (!common.utils.isArray(data))
                data = [];
            var categories = [];
            var datas = [];
            for (var i = 0; i < data.length; i++) {
                categories.push(data[i].name || "");
                datas.push(data[i]);
            }
            return {
                category : categories,
                data : datas
            };
        },
        /**
		 * 处理分组数据，数据格式 : name：XXX，group：XXX，value：XXX用于折线图、柱形图（分组，堆积）
		 * 
		 * @param {}
		 *            data 待办理json数据
		 * @return {}
		 */
        FormatGroupData : function(data) {
            if (!common.utils.isArray(data))
                data = [];
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
                /*
				 * if (groups[i].endsWith('|hide')) { visible = false; groupName =
				 * groups[i].replace('|hide', ''); }
				 */
                var temp_data = new Array();
                for (var j = 0; j < data.length; j++) {
                    for (var k = 0; k < names.length; k++)
                        if (groups[i] == data[j].group
                            && data[j].name == names[k])
                            temp_data.push(data[j].value);

                }
                temp_series = {
                    name : groupName,
                    data : temp_data,
                    visible : visible
                };
                series.push(temp_series);
            }

            return {
                category : names,
                groups : groups,
                series : series
            };
        },

        /**
		 * 处理分组数据，数据格式 : name：XXX，group：XXX，value：XXX type：xxx 用于柱状折线图
		 * 
		 * @param {}
		 *            data 待办理json数据
		 * @return {}
		 */
        FormatGroupTypeData : function(data) {


            if (!common.utils.isArray(data))
                data = [];
            var names = new Array();
            var groups = new Array();
            var series = new Array();
            // var types=new Array();
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
                var type;
                var temp_data = new Array();
                for (var k = 0; k < names.length; k++){
                	for (var j = 0; j < data.length; j++) {
                    	if (groups[i] == data[j].group
                            && data[j].name == names[k]){
                            temp_data.push(data[j].value);
                            type=data[j].type;
                        }
                	}
                } 
                temp_series = {
                    name : groupName,
                    data : temp_data,
                    visible : visible,
                    type:type
                };
                series.push(temp_series);
            }
            return {
                category : names,
                groups : groups,
                series : series
            };
        },
        /**
		 * 处理气泡地图数据 {for:{name:"xx市",coords:[113.5107,23.2196]},
		 * to:[{name:"xx市",coords:[116.1255,24.1534],value:[180494,64128,349]},
		 * {name:"xxx市",coords:[112.9175, 24.3292],value:[141137,62182,261]} ]}
		 * 
		 * 
		 * @param data
		 * @returns {Array}
		 */
        FormatScatterMapData :function(data) {
            var res = [];
            $.each(data,function (i,a) {
                var _for=a.for;
                var _to=a.to;
                var temp=[];
                $.each(_to,function (i,to) {

                    temp.push({
                        name:to.name,
                        fromName: _for.name,
                        coords: [_for.coords, to.coords],
                        value:  to.coords.concat(to.value)
                    })

                });
                temp.push({
                    name:_for.name,
                    fromName: _for.name,
                    coords: [_for.coords, _for.coords],
                    value:  _for.coords.concat(_for.value)
                });
                res.push(temp);
            });


            return res;

        }
    },
    /* COption */
    ChartOptionTemplates : {
        /**
		 * 饼状图
		 * 
		 * @param {}
		 *            data
		 * @param {}
		 *            opt
		 * @return {}
		 */
        Pie : function(data, opt) {

            var pie_datas = Echart.ChartDataFormate.FormateNOGroupData(data);
            var series_ = Echart.utils.createSeries(pie_datas.data,opt,"Pie");

            var option = {
                backgroundColor : opt.backgroundColor,
                color:opt.color,
                title : {
                    text : opt.title
                },
                tooltip : {
                    trigger : 'item',
                    formatter : "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    show:opt.isLegend,
                    data: pie_datas.groups

                },
                visualMap : "",
                series : series_
            };
            if(opt.isVisualMap){
                option.visualMap={
                    show : false,
                    min : opt.visualMap_min,
                    max :  Echart.utils.getSumValue(data),
                    inRange : {
                        colorLightness : [0.45, 1]
                    }

                }
            }




            return option;
        },
        
        /**
		 * 圆环饼状图
		 * 
		 * @param {}
		 *            data
		 * @param {}
		 *            opt
		 * @return {}
		 */
        RPie : function(data, opt) {

            var pie_datas = Echart.ChartDataFormate.FormateNOGroupData(data);
            var series_ = Echart.utils.createSeries(pie_datas.data,opt,"RPie");

            var option = {
                backgroundColor : opt.backgroundColor,
                title : {
                    text : opt.title
                },
                tooltip : {
                    trigger : 'item',
                    formatter : "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    show:opt.isLegend,
                    data: pie_datas.groups

                },
                visualMap : "",
                series : series_
            };
            if(opt.isVisualMap){
                option.visualMap={
                    show : false,
                    min : opt.visualMap_min,
                    max :  Echart.utils.getSumValue(data),
                    inRange : {
                        colorLightness : [0.45, 1]
                    }

                }
            }




            return option;
        },
        
        /**
		 * 条形图
		 * 
		 * @param {}
		 *            data
		 * @param {}
		 *            opt
		 * @return {}
		 */
        Bars : function(data, opt) {
            var bars_datas = Echart.ChartDataFormate.FormatGroupTypeData(data);

            var series_ = Echart.utils.createSeries(bars_datas.series,opt,"Bars");

            var option = {

                backgroundColor : opt.backgroundColor,
                color:opt.color,
                barGap:opt.barGap,
                barCategoryGap:opt.barCategoryGap,
                title: {
                    text: opt.title
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    show:opt.isLegend,
                    data: bars_datas.groups,
                    textStyle:{
                        color:"#fff"
                    },
                    x:opt.legendX,
                    y:opt.legendY,
                    itemHeight:opt.legendItemHeight,
                    itemWidth:opt.legendItemWidth
                },
                grid: {
                    left: opt.grid_left,
                    right: opt.grid_right,
                    bottom: opt.grid_bottom,
                    top: opt.grid_top,
                    containLabel: true
                },

                xAxis:  {

                    type: 'category',
                    data: bars_datas.category,
                    splitLine: {
                        show:opt.xAxis_sline_show,
                        lineStyle: {
                            color:opt.xAxis_sline_color// y轴标分隔线颜色
                        }

                    },
                    axisLabel : {
                        textStyle:{
                            color:opt.xAxis_label_color// y轴标签颜色
                        }
                    },
                    axisLine:{

                        lineStyle: {
                            color:opt.xAxis_line_color// y轴线颜色
                        }
                    }
                },
                yAxis:[
                    {
                        type: 'value',
                        splitLine: {
                            show:opt.yAxis_sline_show,
                            lineStyle: {
                                color:opt.yAxis_sline_color// y轴标分隔线颜色
                            }

                        },
                        axisLabel : {
                            textStyle:{
                                color:opt.yAxis_label_color// y轴标签颜色
                            }
                        },
                        axisLine:{
                            lineStyle: {
                                color:opt.yAxis_line_color// y轴线颜色
                            }
                        }

                    }


                ],
                series: series_
            };



            if(opt.enable2y){
            	var percent='{value}';
            	var max=null;
            	if(opt.line_percent){
            		percent='{value}%';
            		max:100;
            	}
            	
            	
                option.yAxis.push( {
                    type: 'value',
                    min: 0,
                    max: max,
                    splitLine: {
                        show:opt.yAxis_sline_show,
                        lineStyle: {
                            color:opt.yAxis_sline_color// y轴标分隔线颜色
                        }

                    },
                    axisLabel : {
                        formatter: percent,
                        textStyle:{
                            color:opt.yAxis_label_color// y轴标签颜色
                        }
                    },
                    axisLine:{
                        lineStyle: {
                            color:opt.yAxis_line_color// y轴线颜色
                        }
                    }

                });
            }




            if(opt.exchangeXY){
                var x=option.xAxis;
                var y=option.yAxis;
                option.xAxis=y;
                option.yAxis=x;
            }
            return option;
        },
        /**
		 * 折线图
		 * 
		 * @param {}
		 *            data
		 * @param {}
		 *            opt
		 * @return {}
		 */
        Line : function(data, opt) {

            var datas = Echart.ChartDataFormate.FormatGroupData(data);

            var series_ = Echart.utils.createSeries(datas.series,opt,"Line");

            var option = {
                backgroundColor : opt.backgroundColor,
                color:opt.color,
                title: {
                    text: opt.title
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    show:opt.isLegend,
                    data: datas.groups
                },
                grid: {
                    left: opt.grid_left,
                    right: opt.grid_right,
                    bottom: opt.grid_bottom,
                    top: opt.grid_top,
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: datas.category,
                    splitLine: {
                        lineStyle: {
                            color:opt.xAxis_sline_color// y轴标分隔线颜色
                        }

                    },
                    axisLabel : {
                        textStyle:{
                            color:opt.xAxis_label_color// y轴标签颜色
                        }
                    },
                    axisLine:{
                        lineStyle: {
                            color:opt.xAxis_line_color// y轴线颜色
                        }
                    }
                },
                yAxis: {
                    type: 'value',
                    splitLine: {
                        lineStyle: {
                            color:opt.yAxis_sline_color// y轴标分隔线颜色
                        }

                    },
                    axisLabel : {
                        textStyle:{
                            color:opt.yAxis_label_color// y轴标签颜色
                        }
                    },
                    axisLine:{
                        lineStyle: {
                            color:opt.yAxis_line_color// y轴线颜色
                        }
                    }
                },
                series: series_
            };
            if(opt.exchangeXY){
                var x=option.xAxis;
                var y=option.yAxis;
                option.xAxis=y;
                option.yAxis=x;
            }
            return option;
        },
        /**
		 * 仪表图
		 * 
		 * @param {}
		 *            data
		 * @param {}
		 *            opt
		 * @return {}
		 */
        Gauge : function(data, opt) {
            var datas = Echart.ChartDataFormate.FormateNOGroupData(data);

            var series_ = Echart.utils.createSeries(datas.data,opt,"Gauge");

            var option = {
                backgroundColor : opt.backgroundColor,
                title: {
                    text: opt.title
                },
                tooltip : {
                    formatter: "{a}</br> {b}: {c}%"
                },
                series: series_
            };
            return option;
        },
        /**
		 * 气泡图
		 * 
		 * @param {}
		 *            data
		 * @param {}
		 *            opt
		 * @return {}
		 */
        Statter : function(data, opt) {
            var datas = Echart.ChartDataFormate.FormatGroupData(data);
            var series_ = Echart.utils.createSeries(datas.series,opt,"Statter");
            var option = {
                color:opt.color,
                backgroundColor : opt.backgroundColor,
                title: {
                    text: opt.title
                },
                legend: {
                    show:opt.isLegend,
                    data: datas.groups
                },
                visualMap: [
                    {
                        show : false,
                        dimension: 2,
                        max: Echart.utils.getMaxValue(data),
                        inRange: {
                            symbolSize: [10, 40]
                        }
                    }
                ],
                tooltip: {
                    padding: 5,
                    backgroundColor: '#222',
                    borderColor: '#777',
                    borderWidth: 1,
                    formatter: function (obj) {
                        var schema=opt.schema;
                        var r=obj.seriesName+'<br>'
                        $.each(obj.value,function(i,v){
                            r+=schema[i]+' : '+v+'<br>'
                        });
                        return r;
                    }
                },
                grid: {
                    left: opt.grid_left,
                    right: opt.grid_right,
                    bottom: opt.grid_bottom,
                    top: opt.grid_top,
                    containLabel: true
                },
                xAxis: {
                    name:opt.xAxisName,
                    nameTextStyle:{
                        color:opt.xAxis_text_color
                    },
                    splitLine: {
                        lineStyle: {
                            color:opt.xAxis_sline_color, // x轴标分隔线颜色
                            type: 'dashed'
                        }
                    },
                    axisLabel : {
                        textStyle:{
                            color:opt.xAxis_label_color // x轴标签颜色
                        }
                    },
                    axisLine:{
                        lineStyle: {
                            color:opt.xAxis_line_color// x轴线颜色
                        }
                    }
                },
                yAxis: {
                    name: opt.yAxisName,
                    nameTextStyle:{
                        color:opt.yAxis_text_color
                    },
                    /*
					 * min:0, max:100,
					 */
                    splitLine: {
                        lineStyle: {
                            color:opt.yAxis_sline_color,// y轴标分隔线颜色
                            type: 'dashed'
                        }

                    },
                    axisLabel : {
                        formatter: '{value}%',
                        textStyle:{
                            color:opt.yAxis_label_color// y轴标签颜色
                        }
                    },
                    axisLine:{
                        lineStyle: {
                            color:opt.yAxis_line_color// y轴线颜色
                        }
                    },
                    scale: true
                },


                series: series_
            };
            return option;
        },
        /**
		 * 气泡地图
		 * 
		 * @param {}
		 *            data
		 * @param {}
		 *            opt
		 * @return {}
		 */
        StatterMap : function(data, opt) {

            var datas = Echart.ChartDataFormate.FormatScatterMapData(data);

            var series_ = Echart.utils.createSeries(datas,opt,"StatterMap");

            var option = {

                backgroundColor : opt.backgroundColor,
                tooltip : {
                    trigger: 'item',
                    formatter: function (params, ticket, callback) {
                        var obj= params.data;

                        if(obj.value==undefined||obj.value[3]==undefined){
                            return null;
                        }else{

                            var v=obj.name+"<br/>";

                            $.each(obj.value,function (i,item) {
                                if(i>1){
                                    v+=opt.schema[i-2]+" : "+item+"<br/>";
                                }

                            });
                            return v;

                        }
                    }
                },
                visualMap: [
                    {
                        show : false,
                        dimension: 2,
                        max: 180494,
                        inRange: {
                            symbolSize: [15, 32]
                        }
                    }
                ],
                geo: {
                    selectedMode:'single',
                    label: {
                        emphasis: {
                            show: false
                        }
                    },
                    map:opt.mapName,
                    roam: true,
                    itemStyle: {
                        normal: {
                            areaColor: '#323c48',
                            borderColor: '#252d35',
                            shadowColor: 'rgba(0, 0, 0, 0.5)',
                            shadowBlur: 0.5
                        },
                        emphasis: {
                            areaColor: '#2a333d'
                        }
                    }
                },
                series: series_


            };


            return option;
        }
    },
    create : {
        /**
		 * 创建报表的初始化方法
		 * 
		 * @param {}
		 *            container
		 * @param {}
		 *            data
		 * @param {}
		 *            option
		 * @param {}
		 *            type
		 * @return {}
		 */
        init : function(container, data, option, type) {

            /* 清洗并规范化自定义配置项 */
            var option_ = Echart.utils.defaultOption(option);
            option_["container"] = container;
            option_["optionType"] = type;
            var opt = eval("Echart.ChartOptionTemplates." + type
                + "(data,option_)");
            var chart = echarts.init(document.getElementById(container));
            // chart.on("click",option.dbClick);
            chart.setOption(opt);

            var obj = {
                chart : chart,
                chartType : type,
                option : option_,
                data : data
            };
            /* 对报表对象及自定义配置项进行管理 */
            var id = common.report.highcharts.object.add(obj);
            return id;
        },

        /**
		 * 初始化饼图图表
		 * 
		 * @param {}
		 *            container 报表显示指向
		 * @param {}
		 *            data 数据集(DIM NAME and DATA)
		 * @param {}
		 *            option 自定义配置参数
		 * @return {} 返回统一对象管理ID
		 *         (可用common.report.highcharts.object.get(id).chart来获取图表对象)
		 */
        Pie : function(container, data, option) {
            return this.init(container, data, option, "Pie");
        },
        
        /**
		 * 初始化圆环饼图图表
		 * 
		 * @param {}
		 *            container 报表显示指向
		 * @param {}
		 *            data 数据集(DIM NAME and DATA)
		 * @param {}
		 *            option 自定义配置参数
		 * @return {} 返回统一对象管理ID
		 *         (可用common.report.highcharts.object.get(id).chart来获取图表对象)
		 */
        RPie : function(container, data, option) {
            return this.init(container, data, option, "Pie");
        },
        /**
		 * 初始化柱形图表
		 * 
		 * @param {}
		 *            container 报表显示指向
		 * @param {}
		 *            data 数据集(DIM NAME and DATA)
		 * @param {}
		 *            option 自定义配置参数
		 * @return {} 返回统一对象管理ID
		 *         (可用common.report.highcharts.object.get(id).chart来获取图表对象)
		 */
        Bars : function(container, data, option) {
            return this.init(container, data, option, "Bars");
        },
        /**
		 * 初始化折线图表
		 * 
		 * @param {}
		 *            container 报表显示指向
		 * @param {}
		 *            data 数据集(DIM NAME and DATA)
		 * @param {}
		 *            option 自定义配置参数
		 * @return {} 返回统一对象管理ID
		 *         (可用common.report.highcharts.object.get(id).chart来获取图表对象)
		 */
        Line : function(container, data, option) {
            return this.init(container, data, option, "Line");
        },
        /**
		 * 初始化仪表图表
		 * 
		 * @param {}
		 *            container 报表显示指向
		 * @param {}
		 *            data 数据集(DIM NAME and DATA)
		 * @param {}
		 *            option 自定义配置参数
		 * @return {} 返回统一对象管理ID
		 *         (可用common.report.highcharts.object.get(id).chart来获取图表对象)
		 */
        Gauge : function(container, data, option) {
            return this.init(container, data, option, "Gauge");
        },
        /**
		 * 初始化气泡图表
		 * 
		 * @param {}
		 *            container 报表显示指向
		 * @param {}
		 *            data 数据集(DIM NAME and DATA)
		 * @param {}
		 *            option 自定义配置参数
		 * @return {} 返回统一对象管理ID
		 *         (可用common.report.highcharts.object.get(id).chart来获取图表对象)
		 */
        Statter : function(container, data, option) {

            return this.init(container, data, option, "Statter");
        },
        /**
		 * 初始化气泡地图
		 * 
		 * @param {}
		 *            container 报表显示指向
		 * @param {}
		 *            data 数据集(DIM NAME and DATA)
		 * @param {}
		 *            option 自定义配置参数
		 * @return {} 返回统一对象管理ID
		 *         (可用common.report.highcharts.object.get(id).chart来获取图表对象)
		 */
        StatterMap : function(container, data, option) {
            var url=option.url;
            var mapName= url.slice(url.lastIndexOf("/")+1,url.lastIndexOf("."));
            var that=this;
            $.get(url, function (mapJson) {
                echarts.registerMap(mapName, mapJson);
                option.mapName=mapName;
                return that.init(container, data, option, "StatterMap");

            });
            return null;
        }

    },

    /**
	 * 对报表数据重新加载
	 * 
	 * @param {}
	 *            chartid 报表ID
	 * @param {}
	 *            data 重新加载的数据(可选)
	 * @param {}
	 *            newOption 新的自定义项(可选)
	 * @param {}
	 *            type 变换报表类型(可选)
	 * @return 是否成功更新对象
	 */
    reload : function(chartid, data, newOption, type) {

    },
    /**
	 * 销毁报表对象
	 * 
	 * @param {}
	 *            chartid 报表ID
	 * @return 是否成功销毁对象
	 */
    remove : function(chartid) {

    },

    /**
	 * 内部工具类
	 * 
	 * @type
	 */
    utils : {
        /**
		 * 处理自定义配置项的默认值
		 * 
		 * @param {}
		 *            option
		 * @return {}
		 */
        defaultOption : function(option) {

            /* 自定义配置的默认值设置 addopt */
            var defaultOpt = {
                color:['#7ddc79','#fffa7d','#ffaf60','#7d7dff','#ea97ff','#d156ff','#2f7dff', '#2faaff','#6dc3ff', '#88e7fb'],
                title : '', /* 报表标题(默认为空) */
                backgroundColor:'',/* 报表背景色(默认为空) */
                valueName:'数值',/* 饼状图/仪表图的数据名称(默认为数值) */
                valueName2:'数值',/* 内圆 饼状图/仪表图的数据名称(默认为数值) */
                radius:'55%',/* 饼图/仪表图大小 数组的第一项是横坐标，第二项是纵坐标。 */
                radius2:[0, '30%'],/* 内圆 */
                pie_center:['50%', '50%'],/* 饼图大小 */
                pie_item_color:"",/* 饼图颜色 */
                pie_label_color:"#fff",/* 标签的文本颜色 */
                pie_label_color2:"#fff",/* 标签的文本颜色 */
                pie_line_color:"#fff",/* 标签的视觉引导线样式颜色 */
                pie_label_show:true,/* 是否显示标签 */
                pie_label_show2:true,/* 是否显示标签 */
                nest:false,/* 嵌套环形图 */
                line_width:4,/* 线宽 */
                visualMap_min:80,
                visualMap_max:600,
                barCategoryGap:'40%',
                pie_roseType:'',/*
								 * 'radius' 面积展现数据的百分比，半径展现数据的大小。 'area'
								 * 所有扇区面积相同，仅通过半径展现数据大小。
								 */
                isLegend:false,/* 是否显示图例(默认true显示) */
                legendX:'center',/*
									 * 图例显示水平安放位置，默认为中间，可选为：'center' | 'left' |
									 * 'right' | {number}（x坐标，单位px）
									 */
                legendY:'bottom',/*
									 * 垂直安放位置，默认为全图底端，可选为：'top' | 'bottom' |
									 * 'center' | {number}（y坐标，单位px）
									 */
                legendItemWidth:25,/* 图例标记的图形宽度,默认25（单位px） */
                legendItemHeight:14,/* 图例标记的图形高度,默认14（单位px） */
                exchangeXY:false,/* 是否交换xAxis与yAxis的数据，用于柱状图垂直显示和水平显示效果 */
                bar_stack:false, /* 柱状图是否显示为堆积效果,(默认false不显示) */
                gauge_center:[["50%", "50%"]] ,/* 仪表图位置 数组的第一项是横坐标，第二项是纵坐标。 */
                gauge_title_color:'#9ED9E1',/* 仪表盘标题颜色 */
                gauge_item_color:'#95ffff',/* 仪表盘指针颜色 */
                gauge_detail_color:'#fff',/* 仪表盘值文本颜色 */
                gauge_split_color:'#96b5ff',/* 仪表盘分隔线颜色 */
                gauge_axis_color:'#96b5ff',/* 仪表盘坐标轴小标记颜色 */
                gauge_split_width:2,/* 刻度线宽度 */
                gauge_line_width:2,/* 坐标轴线宽度 */
                gauge_axis_show:true,/* 是否标签 */
                gauge_splitNumber:10,
                gauge_max:100,
                line_percent:true,
                gauge_line_color:[[[0.2, '#91c7ae'], [0.8, '#ff1652'], [1, '#f0e126']]],/* 仪表盘的轴线可以被分成不同颜色的多段。每段的结束位置和颜色可以通过一个数组来表示。 */
                schema:[],/* 自定义的一些名称 */
                yAxisName:"",/* y轴名称 */
                yAxis_sline_color:"#404068",/* y轴分割线颜色 */
                yAxis_line_color:"#a0c0ff",/* y轴线颜色 */
                yAxis_label_color:"#fff",/* y轴标签颜色 */
                yAxis_text_color:"#fff",/* y轴名颜色 */
                enable2y:false,/* 显示第二条y轴 目前只有柱状图有效 */
                yAxis_sline_show:true,/* 是否显示Y分割线 */
                xAxisName:"",/* 轴名称 */
                xAxis_sline_color:"#404068",/* x轴分割线颜色 */
                xAxis_line_color:"#a0c0ff",/* x轴线颜色 */
                xAxis_label_color:"#fff",/* x轴标签颜色 */
                xAxis_text_color:"#fff",/* x轴名颜色 */
                xAxis_sline_show:true,/* 是否显示x分割线 */
                grid_left:"4%",
                grid_right:"4%",
                grid_bottom:"4%",
                grid_top:"4%",
                isVisualMap:false,/* 是否使用VisualMap视觉映射组件 */
                label_position:'top',/*
										 * top left right bottom inside
										 * insideLeft insideRight insideTop
										 * insideBottom insideTopLeft
										 * insideBottomLeft insideTopRight
										 * insideBottomRight'
										 */
                mapName:"",
                doClick : '', /*
								 * 传入方法名，可以在图表数据点点击时触发监听事件，附带参数为chart对象，eg:function
								 * testload(chart)
								 */
                doLoad : '' /* 传入方法名，可以在图表加载之前触发监听事件，附带参数为（数据点名，x轴值，y轴值），eg:function：testclick(name,x,y) */
            };
            /* 设置传入自定义参数默认值 */
            var option_ = common.utils.setDefaultOption(option, defaultOpt);
            return option_;

        },


        getOption : function(data, option, type) {
            /* 清洗并规范化自定义配置项 */
            var option_ = Echart.utils.defaultOption(option);
            var opt = eval("Echart.ChartOptionTemplates." + type
                + "(data,option_)");
            return opt;
        },


        /**
		 * 根据图表类型生成数据series
		 * 
		 * @param {}
		 *            data
		 * @param {}
		 *            opt
		 * @param {}
		 *            type
		 * @return {}
		 */
        createSeries:function(data, opt, type){
            var series_ = {};
            /* 针对不同的报表进行数据加载 213 */


            switch (type) {
                case 'Pie' :
                    var data_=data;
                    if(!opt.nest){
                        data_=[];
                        data_[0]=data;
                    }
                    series_ = [];
                    series_.push({
                        name : opt.valueName,
                        type : 'pie',
                        radius : opt.radius,
                        center : opt.pie_center,


                        data : data_[0].sort(function(a, b) {
                            return a.value - b.value
                        }),
                        roseType : opt.pie_roseType,
                        label : {
                            normal : {
                                show:opt.pie_label_show,
                                textStyle:{
                                    color:opt.pie_label_color
                                }
                            }
                        },
                        labelLine : {
                            normal : {
                                show:true,
                                lineStyle:{
                                    color:opt.pie_line_color
                                }
                            }
                        },
                        itemStyle : {
                            normal: {
                                color: opt.pie_item_color
                            },
                            emphasis: {
                                shadowBlur: 20,
                                shadowOffsetX: 0,
                                shadowColor: "rgba(0, 0, 0, 0.5)"
                            }
                        }
                    });
                    if(opt.nest){
                        data_[1][0].selected=true;

                        series_.push(
                            {
                                name : opt.valueName2,
                                type:'pie',
                                selectedMode: 'single',
                                // radius: [0, '30%'],
                                radius : opt.radius2,
                                label: {
                                    normal: {
                                        position: 'inner',
                                        show:opt.pie_label_show2,
                                        textStyle:{
                                            color:opt.pie_label_color2
                                        }
                                    }
                                },
                                labelLine: {
                                    normal: {
                                        show: false
                                    }
                                },
                                data: data_[1].sort(function(a, b) {
                                    return a.value - b.value
                                })
                            }
                        );
                    }


                    break;
                
                case 'RPie' :
                    var data_=data;
                    if(!opt.nest){
                        data_=[];
                        data_[0]=data;
                    }
                    series_ = [];
                    series_.push({
                        name : opt.valueName,
                        type : 'pie',
                        radius : opt.radius,
                        center : opt.pie_center,
                        color: ['#6bdb1f','#739ffa','#30507a'],
                        data : data_[0],
                        roseType : opt.pie_roseType,
                        label : {
                            normal : {
                                show:opt.pie_label_show,
                                textStyle:{
                                    color:opt.pie_label_color
                                }
                            }
                        },
                        labelLine : {
                            normal : {
                                show:true,
                                lineStyle:{
                                    color:opt.pie_line_color
                                }
                            }
                        },
                        itemStyle : {
                            emphasis: {
                                shadowBlur: 20,
                                shadowOffsetX: 0,
                                shadowColor: "rgba(0, 0, 0, 0.5)"
                            }
                        }
                       
                    });
                    
                    if(opt.nest){
                        data_[1][0].selected=true;

                        series_.push(
                            {
                                name : opt.valueName2,
                                type:'pie',
                                selectedMode: 'single',
                                // radius: [0, '30%'],
                                radius : opt.radius2,
                                label: {
                                    normal: {
                                        position: 'inner',
                                        show:opt.pie_label_show2,
                                        textStyle:{
                                            color:opt.pie_label_color2
                                        }
                                    }
                                },
                                labelLine: {
                                    normal: {
                                        show: false
                                    }
                                },
                                itemStyle : {
                                    emphasis: {
                                        shadowBlur: 20,
                                        shadowOffsetX: 0,
                                        shadowColor: "rgba(0, 0, 0, 0.5)"
                                    }
                                },
                                data: data_[1].sort(function(a, b) {
                                    return a.value - b.value
                                }),
                               
                            }
                        );
                    }


                    break;
                case 'Bars' :
                    series_ = [];

                    
                    $.each(data,function(i,value) {
                        var isBar=value.type=="line"?false:true;
                        var bar_label_show=opt.bar_label_show;

                        if(value.type=="line"){
                            bar_label_show=false;
                        }	
                        
                        
                        var series_temp = {
                            name: value.name,
                            type: value.type==null?'bar':value.type,
                            clockwise:false,
                            stack:opt.bar_stack,
                            symbol:'',
                            symbolSize:0,
                            smooth:true,
                            yAxisIndex: isBar==true?0:1,
                            label: {
                                normal: {
                                    show: bar_label_show,
                                    position: opt.label_position
                                }
                            },
                            lineStyle:{
                                normal:{
                                    width:opt.line_width
                                }
                            },
                            data: value.data
                        }
                        series_.push(series_temp);
                    });

                    break;

                case 'Line' :
                    series_ = [];

                    $.each(data,function(i,value) {
                        var series_temp = {
                            name: value.name,
                            type: 'line',
                            stack:true,
                            smooth: true,
                            data: value.data
                        }
                        series_.push(series_temp);
                    });

                    break;
                case 'Gauge' :
                    series_ = [];
                    $.each(data,function(i,value) {
                        var series_temp = {
                            name:opt.valueName,
                            type: "gauge",
                            min: 0,
                            max: opt.gauge_max,

                            splitNumber:  opt.gauge_splitNumber,
                            center: opt.gauge_center[i],
                            radius: opt.radius,
                            axisLine: {
                                lineStyle: {
                                    color: opt.gauge_line_color[i],
                                    width: opt.gauge_line_width
                                }
                            },
                            axisLabel: {
                                show:opt.gauge_axis_show,
                                textStyle: {
                                    color: opt.gauge_axis_color
                                }
                            },

                            axisTick: {
                                length: 7,
                                lineStyle: {
                                    color: "auto"
                                }
                            },
                            splitLine: {
                                length: 10,
                                lineStyle: {
                                    width: opt.gauge_split_width,
                                    color: opt.gauge_split_color
                                }
                            },
                            pointer: {
                                "width": 5
                            },
                            title: {
                                offsetCenter: [
                                    0,
                                    "90%"
                                ],
                                textStyle: {
                                    fontSize: 13,
                                    color: opt.gauge_title_color // 标题颜色
                                }
                            },
                            itemStyle:{
                                normal:{
                                    color:opt.gauge_item_color// 指针颜色
                                }
                            },
                            detail: {
                                show:true,
                                offsetCenter: [
                                    0,
                                    "25%"
                                ],
                                formatter:"{value}%",
                                textStyle: {
                                    fontSize: 18,
                                    fontWeight: "bolder",
                                    color: opt.gauge_detail_color// 值颜色
                                }
                            },




                            data: value
                        }
                        series_.push(series_temp);
                    });

                    break;
                case 'Statter' :
                    series_ = [];
                    data=data.sort(function(a,b){
                        return a.data[0][a.data[0].length-1]-
                            b.data[0][b.data[0].length-1];
                    }).reverse();
                    $.each(data,function(i,value) {
                        var series_temp = {
                            name:value.name,
                            data:value.data,
                            type: 'scatter'
                        }
                        series_.push(series_temp);
                    });

                    break;
                case 'StatterMap' :
                    series_ = [];

                    $.each(data,function (i, dataItem) {

                        series_.push(
                            {
                                name: dataItem.name,
                                type: 'lines',
                                zlevel: 1,
                                effect: {
                                    show: true,
                                    period: 6,
                                    trailLength: 0.7,
                                    color: '#F4E925',
                                    symbolSize: 3
                                },
                                lineStyle: {
                                    normal: {
                                        color: '#F4E925',
                                        width: 0,
                                        curveness: 0.2
                                    }
                                },
                                data: dataItem
                            },

                            {
                                name: dataItem.name,
                                type: 'lines',
                                zlevel: 2,
                                effect: {
                                    show: true,
                                    period: 6,
                                    trailLength: 0,
                                    // symbol: planePath,
                                    symbolSize: 0
                                },
                                lineStyle: {
                                    normal: {
                                        color: "#F4E925",// 55f073//9dcd1a
                                        width: 1,
                                        opacity: 0.4,
                                        curveness: 0.2
                                    }
                                },
                                data:  dataItem


                            },
                            {
                                name:dataItem.name,
                                type: 'effectScatter',
                                coordinateSystem: 'geo',
                                zlevel: 2,
                                rippleEffect: {
                                    brushType: 'stroke'
                                },
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'right',
                                        formatter: '{b}'
                                    }
                                },

                                itemStyle: {
                                    normal: {
                                        color: '#F4E925'// 圆色55f073//9dcd1a
                                    }
                                },
                                data: dataItem

                            });
                    });

                    break;
                default :
                    break;
            }

            return series_;
        },

        /**
		 * 获取饼图数组value 的和
		 */
        getSumValue:function (data) {

            var sum=0;

            $.each(data,function (i,v) {


                sum+=v.value;
            });

            return sum;
        },

        /**
		 * 获取气泡图[index]最大值
		 */
        getMaxValue:function (data,index) {
            var max=0;
            $.each(data,function (i,v) {
                if(v.value[2]>max){
                    max=v.value[2];
                }

            });
            return max;
        },
        sort:function (data) {




        }


    }

};
