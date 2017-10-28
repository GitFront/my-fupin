(function(){

    /**
     * Created by kamyam on 2016/10/14.
     */
    var geoCoordMap = {

        '韶关市':{coords:[113.7964, 24.7028],value3:278,value:36195,value2:93047},
        '深圳市':{coords:[114.5435, 22.5439],value3:0,value:0,value2:0},
        '珠海市':{coords:[113.7305, 22.1155],value3:0,value:0,value2:0},
        '汕头市':{coords:[117.1692, 23.3405],value3:37,value:30613,value2:100356},
        '江门市':{coords:[112.6318, 22.1484],value3:37,value:0,value2:0},
        '惠州市':{coords:[114.6204, 23.1647],value3:46,value:17999,value2:41007},
        '梅州市':{coords:[116.1255, 24.1534],value3:349,value:64128,value2:180494},
        '汕尾市':{coords:[115.5762, 23.0438],value3:142,value:42255,value2:140689},
        '河源市':{coords:[114.917,  23.9722],value3:255,value:43971,value2:121283},
        '阳江市':{coords:[111.8298, 22.0715],value3:80,value:40420,value2:84231},
        '清远市':{coords:[112.9175, 24.3292],value3:261,value:62182,value2:141137},
        '东莞市':{coords:[113.8953, 22.901],value3:0,value:0,value2:0},
        '中山市':{coords:[113.4229, 22.478],value3:0,value:0,value2:0},
        '潮州市':{coords:[116.7847, 23.8293],value3:45,value:16491,value2:37395},
        '揭阳市':{coords:[116.1255, 23.313], value3:162,value:47537,value2:135206},
        '云浮市':{coords:[111.7859, 22.8516],value3:105,value:50585,value2:123878},
        '佛山市':{coords:[112.8955, 23.1097],value3:0,value:0,value2:0},
        '广州市':{coords:[113.5107, 23.2196],value3:0,value:0,value2:0},
        '湛江市':{coords:[110.3577, 20.9894],value3:218,value:83991,value2:231652},
        '茂名市':{coords:[111.0059, 22.0221],value3:180,value:75733,value2:178087},
        '肇庆市':{coords:[112.1265, 23.5822],value3:111,value:42930,value2:97778}
    };

    var GZData = [
        [{name:'广州市'},{name:'梅州市',value:31}],
        [{name:'广州市'},{name:'清远市',value:30}],
        [{name:'广州市'},{name:'广州市',value:2}],
    ];
    var DGData=[
        [{name:'东莞市'},{name:'韶关市',value:21}],
        [{name:'东莞市'},{name:'揭阳市',value:26}],
        [{name:'东莞市'},{name:'东莞市',value:2}],
    ]

    var SZData=[
        [{name:'深圳市'},{name:'河源市',value:24}],
        [{name:'深圳市'},{name:'汕尾市',value:19}],
        [{name:'深圳市'},{name:'深圳市',value:2}]
    ]
    var ZHData=[
        [{name:'珠海市'},{name:'阳江市',value:24}],
        [{name:'珠海市'},{name:'茂名市',value:36}],
        [{name:'珠海市'},{name:'珠海市',value:2}],
    ]
    var FSData=[
        [{name:'佛山市'},{name:'湛江市',value:38}],
        [{name:'佛山市'},{name:'云浮市',value:27}],
        [{name:'佛山市'},{name:'佛山市',value:2}]
    ]

    var ZSData=[
        [{name:'中山市'},{name:'肇庆市',value:27}],
        [{name:'中山市'},{name:'中山市',value:2}],
        [{name:'中山市'},{name:'潮州市',value:13}]
    ]
    var JMData=[
        [{name:'江门市'},{name:'江门市',value:2}]
    ]

    var STData=[
        [{name:'汕头市'},{name:'汕头市',value:18}]
    ]

    var HZData=[
        [{name:'惠州市'},{name:'惠州市',value:15}]
    ]

    var convertData = function (data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            var dataItem = data[i];
            var fromCoord = geoCoordMap[dataItem[0].name].coords;
            var toCoord = geoCoordMap[dataItem[1].name].coords;
            if (fromCoord && toCoord) {
                res.push({
                    fromName: dataItem[0].name,
                    toName: dataItem[1].name,
                    coords: [fromCoord, toCoord],
                    v1:geoCoordMap[dataItem[1].name].value,
                    v2:geoCoordMap[dataItem[1].name].value2,
                    v3:geoCoordMap[dataItem[1].name].value3
                });
            }
        }
        return res;
    };
   // var planePath = 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z';

    var series = [];
    [['广州市', GZData],['深圳市', SZData],['东莞市', DGData],['中山市', ZSData],
        ['珠海市', ZHData],['佛山市', FSData],['江门市', JMData],['惠州市', HZData],['汕头市', STData]].
        forEach(function (item, i) {
            series.push(
                {
                    name: item[0],
                    type: 'lines',
                    zlevel: 1,
                    effect: {
                        show: true,
                        period: 6,
                        trailLength: 0.7,
                        color: '#fff',
                        symbolSize: 3
                    },
                    lineStyle: {
                        normal: {
                            color: '#F4E925',
                            width: 0,
                            curveness: 0.2
                        }
                    },
                    data: convertData(item[1])
                },

                {
                    name: item[0] + ' Top10',
                    type: 'lines',
                    zlevel: 2,
                    effect: {
                        show: true,
                        period: 6,
                        trailLength: 0,
                        //symbol: planePath,
                        symbolSize: 0
                    },
                    lineStyle: {
                        normal: {
                            color: "#F4E925",
                            width: 1,
                            opacity: 0.4,
                            curveness: 0.2
                        }
                    },
                    data: convertData(item[1])


                },


                {
                    name: item[0],
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
                    symbolSize: function (val) {
                        return val[2];
                    },
                    itemStyle: {
                        normal: {
                            color: '#F4E925'
                        }
                    },
                    data: item[1].map(function (dataItem) {
                        return {
                            name: dataItem[1].name,
                            value: geoCoordMap[dataItem[1].name].coords.concat([dataItem[1].value])
                        };
                    })
                });
        });

    option = {
        /*backgroundColor: '#1E1B26',*/
        tooltip : {
            trigger: 'item',
            formatter: function (params, ticket, callback) {
                var obj= params.data;
                if(obj.value==undefined||geoCoordMap[obj.name].value==0||geoCoordMap[obj.name].value2==0){
                    return null;
                }else{
                    return obj.name+"<br/>贫困户 : "+geoCoordMap[obj.name].value+"<br/>贫困人口 : "+geoCoordMap[obj.name].value2;
                }
            }
        },
        geo: {
            selectedMode:'single',
            label: {
                emphasis: {
                    show: false
                }
            },
           // zoom:1.25,//设置初始大小
            roam: true,
            itemStyle: {
                normal: {
                    areaColor: '#323c48',
                    borderColor: '#3e70c4',
                    shadowColor: 'rgba(255, 255, 255, 0.7)',
                    shadowBlur: 0.9,
                },
                emphasis: {
                    areaColor: '#2a333d'
                }
            }
        },
        series: series
    };



    init("guangdong");
    function init(mapName){
        $.get('../../../static/js/maps/'+mapName+'.json', function (mapJson) {
            echarts.registerMap(mapName, mapJson);
            var myChart = echarts.init(document.getElementById('map'));
            option.geo.map=mapName;
            myChart.setOption(option);
        });
    }




})();