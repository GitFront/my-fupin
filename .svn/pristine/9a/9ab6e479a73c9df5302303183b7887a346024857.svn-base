(function(){
    var datas=[
        {for:{name:"广州市",coords:[113.5107,23.2196]},
            to:[{name:"黔南州",coords:[107.056082,25.988621],value:[58.29]},
                {name:"毕节市",coords:[105.290995,27.29600],value:[115.45]},
                {name:"甘孜州",coords:[101.96908,30.055209],value:[16.52]}
            ]
        },

        {for:{name:"深圳市",coords:[114.5435, 22.5439]},
            to:[{name:"百色市",coords:[106.623532,23.909166],value:[67.9]},
                {name:"河池市",coords:[108.090317,24.700141],value:[69.1]},
                {name:"甘孜州",coords:[101.96908,30.055209],value:[16.52]}
            ]
        },

        {for:{name:"珠海市",coords:[113.7305, 22.1155]},
            to:[{name:"怒江州",coords:[98.862614,25.827509],value:[14.84]},
                {name:"甘孜州",coords:[101.96908,30.055209],value:[16.52]}

            ]
        },
        {for:{name:"佛山市",coords:[112.8955, 23.1097]},
            to:[{name:"凉山州",coords:[102.271953,27.888196],value:[39.56]},
                {name:"甘孜州",coords:[101.96908,30.055209],value:[16.52]}

            ]
        },
        {for:{name:"东莞市",coords:[113.8953, 22.901]},
            to:[{name:"昭通市",coords:[103.719152,27.345595],value:[111.95]},
                {name:"甘孜州",coords:[101.96908,30.055209],value:[16.52]}

            ]
        },

        {for:{name:"中山市",coords:[113.4229, 22.478]},
            to:[{name:"昭通市",coords:[103.719152,27.345595],value:[111.95]},
                {name:"甘孜州",coords:[101.96908,30.055209],value:[16.52]}

            ]
        }


    ];




    var convertScatterMapData = function (datas) {
        var res = [];
        var _for=datas.for;
        var _to=datas.to||[];

        $.each(_to,function (i,to) {

            res.push({
                name:to.name,
                fromName: _for.name,
                coords: [_for.coords, to.coords],
                value:  to.coords.concat(to.value)
            })

        });
        res.push({
            name:_for.name,
            fromName: _for.name,
            coords: [_for.coords, _for.coords],
            value:  _for.coords.concat(_for.value)
        });

        return res;

    };

    var series = [];

    $.each(datas,function (i, data) {
        series.push(
            {
                name: data.for.name,
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
                data: convertScatterMapData(data)
            },

            {
                name: data.for.name,
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
                        color: "#F4E925",//55f073//9dcd1a
                        width: 1,
                        opacity: 0.4,
                        curveness: 0.2
                    }
                },
                data:  convertScatterMapData(data)


            },


            {
                name: data.for.name,
                type: 'effectScatter',
                coordinateSystem: 'geo',
                zlevel: 2,
                rippleEffect: {
                    brushType: 'stroke'
                },
                label: {
                    normal: {
                        show: true,
                        textStyle:{
                            color: '#F4E925',
                        },
                        position: 'right',
                        formatter: '{b}'
                    }
                },

                itemStyle: {
                    normal: {
                        color: '#F4E925'//圆色55f073//9dcd1a
                    }
                },
                data: convertScatterMapData(data)

            });
    });

    var title=['贫困人口'],

        option = {
            /*backgroundColor: '#1E1B26',*/
            tooltip : {
                trigger: 'item',
                formatter: function (params, ticket, callback) {
                    var obj= params.data;
                    if(obj.value==undefined||obj.value.length==2){
                        return null;
                    }else{

                        var v=obj.name+"<br/>";


                        $.each(obj.value,function (i,item) {
                            if(i>1 && item){
                                v+=title[i-2]+" : "+item+"万<br/>";
                            }

                        });
                        return v;

                    }
                }
            },
            visualMap: [
                {
                    show : false,
                    max: 115.45,
                    inRange: {
                        symbolSize: [8, 12]
                    }
                }
            ],
            legend: {
                orient: 'vertical',
                top: '450px',
                left: 'right',
                data:['广州市', '东莞市', '深圳市', '珠海市', '佛山市', '中山市'],
                textStyle: {
                    color: '#fff'
                }
            },
            geo: {
                //center: [104.97, 24.71],
               zoom:2.5,
               top : '-150px',
                selectedMode:'single',
                label: {
                    emphasis: {
                        show: false

                    }
                },
                roam: true,
                itemStyle: {
                    normal: {

                        borderWidth:0.6,
                        areaColor: '#323c48',
                        borderColor: '#4275f3',
                    },
                    emphasis: {
                        areaColor: '#2a333d'
                    }
                }
            },
            series: series

        };


    init("china-new");
    function init(mapName){
    	$.get(ctx+'/static/js/maps/'+mapName+'.json', function (mapJson) {
            echarts.registerMap(mapName, mapJson);
            var myChart = echarts.init(document.getElementById('map'));
            option.geo.map=mapName;
            myChart.setOption(option);
        });
    }




})();