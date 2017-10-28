(function(win){
	var commonEchart = {};
commonEchart.genVillageColumnGs = function (config){
//已完成数据
var do1 = config[0].done1;var done1=new Array();done1=do1.split(",");
var do2 = config[1].done2;var done2=new Array();done2=do2.split(",");
var do3 = config[2].done3;var done3=new Array();done3=do3.split(",");
var do4 = config[3].done4;var done4=new Array();done4=do4.split(",");
var do5 = config[4].done5;var done5=new Array();done5=do5.split(",");
var do6 = config[5].done6;var done6=new Array();done6=do6.split(",");
var do7 = config[6].done7;var done7=new Array();done7=do7.split(",");
var do8 = config[7].done8;var done8=new Array();done8=do8.split(",");
var do9 = config[8].done9;var done9=new Array();done9=do9.split(",");
var do10 = config[9].done10;var done10=new Array();done10=do10.split(",");
var do11 = config[10].done11;var done11=new Array();done11=do11.split(",");
var do12 = config[11].done12;var done12=new Array();done12=do12.split(",");
//建设中数据
var dg1 = config[0].doing1;var doing1=new Array();doing1=dg1.split(",");
var dg2 = config[1].doing2;var doing2=new Array();doing2=dg2.split(",");
var dg3 = config[2].doing3;var doing3=new Array();doing3=dg3.split(",");
var dg4 = config[3].doing4;var doing4=new Array();doing4=dg4.split(",");
var dg5 = config[4].doing5;var doing5=new Array();doing5=dg5.split(",");
var dg6 = config[5].doing6;var doing6=new Array();doing6=dg6.split(",");
var dg7 = config[6].doing7;var doing7=new Array();doing7=dg7.split(",");
var dg8 = config[7].doing8;var doing8=new Array();doing8=dg8.split(",");
var dg9 = config[8].doing9;var doing9=new Array();doing9=dg9.split(",");
var dg10 = config[9].doing10;var doing10=new Array();doing10=dg10.split(",");
var dg11 = config[10].doing11;var doing11=new Array();doing11=dg11.split(",");
var dg12 = config[11].doing12;var doing12=new Array();doing12=dg12.split(",");
//未启动数据
var app1 = config[0].unstart1;var unstart1=new Array();unstart1=app1.split(",");
var app2 = config[1].unstart2;var unstart2=new Array();unstart2=app2.split(",");
var app3 = config[2].unstart3;var unstart3=new Array();unstart3=app3.split(",");
var app4 = config[3].unstart4;var unstart4=new Array();unstart4=app4.split(",");
var app5 = config[4].unstart5;var unstart5=new Array();unstart5=app5.split(",");
var app6 = config[5].unstart6;var unstart6=new Array();unstart6=app6.split(",");
var app7 = config[6].unstart7;var unstart7=new Array();unstart7=app7.split(",");
var app8 = config[7].unstart8;var unstart8=new Array();unstart8=app8.split(",");
var app9 = config[8].unstart9;var unstart9=new Array();unstart9=app9.split(",");
var app10 = config[9].unstart10;var unstart10=new Array();unstart10=app10.split(",");
var app11 = config[10].unstart11;var unstart11=new Array();unstart11=app11.split(",");
var app12 = config[11].unstart12;var unstart12=new Array();unstart12=app12.split(",");
option = null;
var dataMap = {};
function dataFormatter(obj) {
    var pList = ['编制整治规划','三清三拆三整治','村道巷道硬化','垃圾处理','污水处理','集中供水','住房建设','公共服务设施','成立村民理事会','自然村达标数'];
    var temp;
    for (var month = 12; month <= 01; month++) {
        var max = 0;
        var sum = 0;
        temp = obj[month];
        for (var i = 0, l = temp.length; i < l; i++) {
            max = Math.max(max, temp[i]);
            sum += temp[i];
            obj[month][i] = {
                name : pList[i],
                value : temp[i]
            }
        }
        obj[month + 'max'] = Math.floor(max / 100) * 100;
        obj[month + 'sum'] = sum;
    }
    return obj;
}

dataMap.dataPI = dataFormatter({
    2012:done12,
    2011:done11,
    2010:done10,
    2009:done9,
    2008:done8,
    2007:done7,
    2006:done6,
    2005:done5,
    2004:done4,
    2003:done3,
    2002:done2,
	2001:done1

});

dataMap.dataSI = dataFormatter({
	2012:doing12,
    2011:doing11,
    2010:doing10,
    2009:doing9,
    2008:doing8,
    2007:doing7,
    2006:doing6,
    2005:doing5,
    2004:doing4,
    2003:doing3,
    2002:doing2,
	2001:doing1
});

dataMap.dataTI = dataFormatter({
	2012:unstart12,
    2011:unstart11,
    2010:unstart10,
    2009:unstart9,
    2008:unstart8,
    2007:unstart7,
    2006:unstart6,
    2005:unstart5,
    2004:unstart4,
    2003:unstart3,
    2002:unstart2,
	2001:unstart1
});

option = {
    baseOption: {
        timeline: {
            axisType: 'category',
            autoPlay: true,
            playInterval: 1000,

            data: [
                '01','02','03',
                '04', '05','06',
                '07','08','09','10','11','12',
            ],

        },

        tooltip: {
        	
        },
        legend: {
            x: 'right',
            data: ['已完成', '进行中', '未启动' ],
            textStyle:{
                color:'#96b5ff',
                fontSize:12
            }
        },
        color:['#6bdb1f','#739ffa','#30507a'],
        calculable : true,
        grid: {
            top: 50,
            bottom: 100,
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow',
                    label: {
                        show: true,
                        formatter: function (params) {
                            return params.value.replace('\n', '');
                        }
                    }
                }
            }
        },
        xAxis: [
            {
                'type':'category',
                'axisLabel':{'interval':0},
                'data':[
                    '编制整治规划','\n三清三拆三整治','村道巷道硬化','\n垃圾处理','污水处理',
                    '集中供水','\n住房建设','公共服务设施','\n成立村民理事会','自然村达标数'

                ],
                splitLine: {show: false},
                axisLine:{
                    lineStyle:{
                        color:'#96b5ff'
                    }
                } 
                
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: ' ',
                axisLine:{
                    lineStyle:{
                        color:'#96b5ff'
                    }
                } 
            }
        ],
        series: [
            {name: '已完成', type: 'bar',stack:'总量',barWidth : 30},
            {name: '进行中', type: 'bar',stack:'总量',barWidth : 30},
            {name: '未启动', type: 'bar',stack:'总量',barWidth : 30}
        ]
    },
    options: [
         {
            series: [
                {data: dataMap.dataPI['2001']},
                {data: dataMap.dataSI['2001']},
                {data: dataMap.dataTI['2001']}

            ]
        },{
            series: [
                {data: dataMap.dataPI['2002']},
                {data: dataMap.dataSI['2002']},
                {data: dataMap.dataTI['2002']}
            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2003']},
                {data: dataMap.dataSI['2003']},
                {data: dataMap.dataTI['2003']}
            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2004']},
                {data: dataMap.dataSI['2004']},
                {data: dataMap.dataTI['2004']}
            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2005']},
                {data: dataMap.dataSI['2005']},
                {data: dataMap.dataTI['2005']}
            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2006']},
                {data: dataMap.dataSI['2006']},
                {data: dataMap.dataTI['2006']}
            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2007']},
                {data: dataMap.dataSI['2007']},
                {data: dataMap.dataTI['2007']}
            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2008']},
                {data: dataMap.dataSI['2008']},
                {data: dataMap.dataTI['2008']}
            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2009']},
                {data: dataMap.dataSI['2009']},
                {data: dataMap.dataTI['2009']}

            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2010']},
                {data: dataMap.dataSI['2010']},
                {data: dataMap.dataTI['2010']}

            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2011']},
                {data: dataMap.dataSI['2011']},
                {data: dataMap.dataTI['2011']}

            ]
        },
        {
            series : [
                {data: dataMap.dataPI['2012']},
                {data: dataMap.dataSI['2012']},
                {data: dataMap.dataTI['2012']}

            ]
        }
    ]
};

return  option;

};

	commonEchart.genVillageColumnBfbR =function (config){

		//已完成数据
		var do1 = config[0].done1;var done1=new Array();
		done1=do1.split(",");
		var do2 = config[1].done2;var done2=new Array();
		done2=do2.split(",");
		var do3 = config[2].done3;var done3=new Array();
		done3=do3.split(",");
		var do4 = config[3].done4;var done4=new Array();
		done4=do4.split(",");
		var do5 = config[4].done5;var done5=new Array();
		done5=do5.split(",");
		var do6 = config[5].done6;var done6=new Array();
		done6=do6.split(",");
		var do7 = config[6].done7;var done7=new Array();
		done7=do7.split(",");
		var do8 = config[7].done8;var done8=new Array();
		done8=do8.split(",");
		var do9 = config[8].done9;var done9=new Array();
		done9=do9.split(",");
		var do10 = config[9].done10;var done10=new Array();
		done10=do10.split(",");
		var do11 = config[10].done11;var done11=new Array();
		done11=do11.split(",");
		var do12 = config[11].done12;var done12=new Array();
		done12=do12.split(",");
		//建设中数据
		var dg1 = config[0].doing1;var doing1=new Array();
		doing1=dg1.split(",");
		var dg2 = config[1].doing2;var doing2=new Array();
		doing2=dg2.split(",");
		var dg3 = config[2].doing3;var doing3=new Array();
		doing3=dg3.split(",");
		var dg4 = config[3].doing4;var doing4=new Array();
		doing4=dg4.split(",");
		var dg5 = config[4].doing5;var doing5=new Array();
		doing5=dg5.split(",");
		var dg6 = config[5].doing6;var doing6=new Array();
		doing6=dg6.split(",");
		var dg7 = config[6].doing7;var doing7=new Array();
		doing7=dg7.split(",");
		var dg8 = config[7].doing8;var doing8=new Array();
		doing8=dg8.split(",");
		var dg9 = config[8].doing9;var doing9=new Array();
		doing9=dg9.split(",");
		var dg10 = config[9].doing10;var doing10=new Array();
		doing10=dg10.split(",");
		var dg11 = config[10].doing11;var doing11=new Array();
		doing11=dg11.split(",");
		var dg12 = config[11].doing12;var doing12=new Array();
		doing12=dg12.split(",");
		//未启动数据
		var app1 = config[0].unstart1;var unstart1=new Array();
		unstart1=app1.split(",");
		var app2 = config[1].unstart2;var unstart2=new Array();
		unstart2=app2.split(",");
		var app3 = config[2].unstart3;var unstart3=new Array();
		unstart3=app3.split(",");
		var app4 = config[3].unstart4;var unstart4=new Array();
		unstart4=app4.split(",");
		var app5 = config[4].unstart5;var unstart5=new Array();
		unstart5=app5.split(",");
		var app6 = config[5].unstart6;var unstart6=new Array();
		unstart6=app6.split(",");
		var app7 = config[6].unstart7;var unstart7=new Array();
		unstart7=app7.split(",");
		var app8 = config[7].unstart8;var unstart8=new Array();
		unstart8=app8.split(",");
		var app9 = config[8].unstart9;var unstart9=new Array();
		unstart9=app9.split(",");
		var app10 = config[9].unstart10;var unstart10=new Array();
		unstart10=app10.split(",");
		var app11 = config[10].unstart11;var unstart11=new Array();
		unstart11=app11.split(",");
		var app12 = config[11].unstart12;var unstart12=new Array();
		unstart12=app12.split(",");
	option = null;
	var dataMap = {};
	function dataFormatter(obj) {
	    var pList = ['编制整治规划','三清三拆三整治','村道巷道硬化','垃圾处理','污水处理','集中供水','住房建设','公共服务设施','成立村民理事会','自然村达标数'];
	    var temp;
	    for (var month = 12; month <= 01; month++) {
	        var max = 0;
	        var sum = 0;
	        temp = obj[month];
	        for (var i = 0, l = temp.length; i < l; i++) {
	            max = Math.max(max, temp[i]);
	            sum += temp[i];
	            obj[month][i] = {
	                name : pList[i],
	                value : temp[i]
	            }
	        }
	        obj[month + 'max'] = Math.floor(max / 100) * 100;
	        obj[month + 'sum'] = sum;
	    }
	    return obj;
	}


	dataMap.dataPI = dataFormatter({
	    2012:done12,
	    2011:done11,
	    2010:done10,
	    2009:done9,
	    2008:done8,
	    2007:done7,
	    2006:done6,
	    2005:done5,
	    2004:done4,
	    2003:done3,
	    2002:done2,
		2001:done1

	});

	dataMap.dataSI = dataFormatter({
		2012:doing12,
	    2011:doing11,
	    2010:doing10,
	    2009:doing9,
	    2008:doing8,
	    2007:doing7,
	    2006:doing6,
	    2005:doing5,
	    2004:doing4,
	    2003:doing3,
	    2002:doing2,
		2001:doing1
	});

	dataMap.dataTI = dataFormatter({
		2012:unstart12,
	    2011:unstart11,
	    2010:unstart10,
	    2009:unstart9,
	    2008:unstart8,
	    2007:unstart7,
	    2006:unstart6,
	    2005:unstart5,
	    2004:unstart4,
	    2003:unstart3,
	    2002:unstart2,
		2001:unstart1
	});
	option = {
	    baseOption: {
	        timeline: {
	            axisType: 'category',
	            autoPlay: true,
	            playInterval: 1000,

	            data: [
	                '01','02','03',
	                '04', '05','06',
	                '07','08','09','10','11','12',
	            ],
	            
	        },

	        tooltip: {  
	        	
	        },
	        legend: {
	            x: 'right',
	            data: ['已完成', '进行中', '未启动' ],
	            textStyle:{
	                color:'#96b5ff',
	                fontSize:12
	            }
	        },
	        color:['#6bdb1f','#739ffa','#30507a'],
	        calculable : true,
	        grid: {
	            top: 50,
	            bottom: 100,
	            tooltip: {
	                trigger: 'axis',
	                axisPointer: {
	                    type: 'shadow',
	                    label: {
	                        show: true,
	                        formatter: function (params) {
	                            return params.value.replace('\n', '');
	                        }
	                    }
	                }
	            }
	        },
	        xAxis: [
	            {
	                'type':'category',
	                'axisLabel':{'interval':0},
	                'data':[
	                    '编制整治规划','\n三清三拆三整治','村道巷道硬化','\n垃圾处理','污水处理',
	                    '集中供水','\n住房建设','公共服务设施','\n成立村民理事会','自然村达标数'

	                ],
	                splitLine: {show: false},
	                axisLine:{
                        lineStyle:{
                            color:'#96b5ff'
                        }
                    } 
	            }
	        ],
	        yAxis: [  
	            {  
	                type: 'value',  
	                axisLabel: {  
	                      show: true,  
	                      interval: 'auto',  
	                      formatter: '{value} %'  
	                    },  
	                show: true ,
	                axisLine:{
                        lineStyle:{
                            color:'#96b5ff'
                        }
                    } 
	            }  
	        ], 
	        series: [
	            {name: '已完成', type: 'bar',stack:'总量',barWidth : 30},
	            {name: '进行中', type: 'bar',stack:'总量',barWidth : 30},
	            {name: '未启动', type: 'bar',stack:'总量',barWidth : 30}
	        ]
	    },
	    options: [
	         {
	            series: [
	                {data: dataMap.dataPI['2001']},
	                {data: dataMap.dataSI['2001']},
	                {data: dataMap.dataTI['2001']}

	            ]
	        },{
	            series: [
	                {data: dataMap.dataPI['2002']},
	                {data: dataMap.dataSI['2002']},
	                {data: dataMap.dataTI['2002']}
	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2003']},
	                {data: dataMap.dataSI['2003']},
	                {data: dataMap.dataTI['2003']}
	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2004']},
	                {data: dataMap.dataSI['2004']},
	                {data: dataMap.dataTI['2004']}
	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2005']},
	                {data: dataMap.dataSI['2005']},
	                {data: dataMap.dataTI['2005']}
	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2006']},
	                {data: dataMap.dataSI['2006']},
	                {data: dataMap.dataTI['2006']}
	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2007']},
	                {data: dataMap.dataSI['2007']},
	                {data: dataMap.dataTI['2007']}
	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2008']},
	                {data: dataMap.dataSI['2008']},
	                {data: dataMap.dataTI['2008']}
	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2009']},
	                {data: dataMap.dataSI['2009']},
	                {data: dataMap.dataTI['2009']}

	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2010']},
	                {data: dataMap.dataSI['2010']},
	                {data: dataMap.dataTI['2010']}

	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2011']},
	                {data: dataMap.dataSI['2011']},
	                {data: dataMap.dataTI['2011']}

	            ]
	        },
	        {
	            series : [
	                {data: dataMap.dataPI['2012']},
	                {data: dataMap.dataSI['2012']},
	                {data: dataMap.dataTI['2012']}

	            ]
	        }
	    ]
	};


	return  option;

	};

win.commonEchart = commonEchart;
})(window);