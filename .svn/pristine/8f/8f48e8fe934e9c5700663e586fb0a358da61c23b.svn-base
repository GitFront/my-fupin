   var option = {
            title:false,
            mapName:'广东省',
            height:'650',
			colorStops:['#65a5e1','#3e5ca9','#272e8b'],
			//valueType:"mapbubble",
		   legendAlign:'bottom',
            navigation:true,
			navigationButton:false,
            valueMapper:[{key:'value',name:'贫困户'},{key:'value2',name:'贫困人口'}],
            isDrilldown:true,
            doClick : 're',
            doDrillup:'fan',
            setLoading:['loadingShow','loadingHide']
        };

        var chartid = '';
    $(function(){
          chartid=Highmap.create.init("map",gd_maps_data,"countries/cn/custom/cn-gongdong","gd_key",option);

    })

        var str={};

     function re(e){
         $(".map-back-btn").removeClass('hide');
        str.sheng="44";
        str.shname="广东省";
         var pac=e.properties['PAC'];
         var name=e.properties['name'];
         var drill=e.properties['gd_key'];
          $(".map-back-btn .up-area").text(Highmap.namearray[Highmap.namearray.length-2]);
             if(pac!=null){
             while('0'==pac.charAt(pac.length-1)){
                pac=pac.substring(0,pac.lastIndexOf("0"));
            }

            if(pac.length>2&&pac.length<=4){
                str.shi=pac;
                str.sname=name;
				tabDutyCity();
                }else if(pac.length>4&&pac.length<=6){
                    str.xian=pac;
                    str.xname=name;
                   tabDutyCounty();
                }else if(pac.length>6&&pac.length<=9){
                    str.zhen=pac;
                    str.zname=name;
                    tabDutyTown();
                }else if(pac.length>9&&pac.length<=12){
                    str.cun=pac;
                    str.cname=name;
                    if(drill=="yhz-xin_zhai"){

                      tabDutyCountry();
                    }else if(drill=="yhz-rlc"){

                       // tabCountry(name,'renli');
                    }

                }
            }
     }

     //返回事件
     function fan(e){
         $(".map-back-btn .up-area").text(Highmap.namearray[Highmap.namearray.length-2]);
         if(str.cun!=null){
                str.cun=null;
               tabDutyTown();
            }else if(str.zhen!=null){
                str.zhen=null;
                str.cun=null;
                tabDutyCounty();
            }else if(str.xian!=null){
                str.xian=null;
                str.zhen=null;
                str.cun=null;
               tabDutyCity();
            }else if(str.shi!=null){
                str.shi=null;
                str.xian=null;
                str.zhen=null;
                str.cun=null;
              tabDutyProvince();
               $(".map-back-btn").addClass('hide');
            }

     }

    //地图返回事件
    $("#mapBtn").click(function(event) {
        Highmap.mapObject.drillUp();
    });
	 //责任监控
	  function drilldown(area_name){
		 var points = Highmap.mapObject.series[0].points;
		 $.each(points, function(i,p) {
			var name = p.properties['name'];
			if(name === area_name){
				var e = Highmap.mapObject.series[0].points[i];
				 var mobject = common.report.highcharts.object.get(chartid);
				 var opt = mobject.option;
				 var data = mobject.data;
				 var joinBy = mobject.joinBy;
				 var drillKey = e.properties[opt.drillKey];
				 var key = e.properties[joinBy];
		     	 var dmv_ = Highmap.ChartDataFormate.FormatMapData(data,opt.parentKey);

		     	 Highmap.utils.mapDrilldown(e,Highmap.mapObject,drillKey,joinBy,dmv_[key]);
			}
	     });

		 /* Highmap.mapObject.get('gd_qingyuan').select(); */
	 }