   var option = {
            title:false,
            mapName:'广东省',
            height:'650',
			colorStops:['#65a5e1','#3e5ca9','#272e8b'],
			//valueType:"mapbubble",
		   legendAlign:'bottom',
           showPoint:true,
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
         var pointType=e.properties['type'];
         $(".map-back-btn .up-area").text(Highmap.namearray[Highmap.namearray.length-2]);
             if(pac!=null){
             while('0'==pac.charAt(pac.length-1)){
                pac=pac.substring(0,pac.lastIndexOf("0"));
            }
            if(pac.length>2&&pac.length<=4){
                str.shi=pac;
                str.sname=name;
				tabCity("meizhou");
                }else if(pac.length>4&&pac.length<=6){
                    str.xian=pac;
                    str.xname=name;
                   tabCounty("xinning");
                }else if(pac.length>6&&pac.length<=9){
                    str.zhen=pac;
                    str.zname=name;
                    tabTown("yonghe");
                }else if(pac.length>9&&pac.length<=12){
                        str.cun=pac;
                        str.cname=name;
                        if(drill=="yhz-xin_zhai"){
                          tabCountry("xinzhai");
                        }else if(drill=="yhz-rlc"){
                           // tabCountry(name,'renli');
                        }
                    }
                }
                  if(pointType=='PKH'){
                        if(name=='何要娣'){
                            $.poorDetail.showFamily(1);
                        }else if(name=='潘伟中'){
                            $.poorDetail.showFamily(2);
                        }
                    }
                     if(pointType=='PKC'){
                        if(name=='新寨村'){
                            $.poorDetail.showCountry('xinzhai')
                        }else if(name=='仁里村'){
                             $.poorDetail.showCountry('renli');
                        }
                    }
     }

     //返回事件
     function fan(e){
        $(".map-back-btn .up-area").text(Highmap.namearray[Highmap.namearray.length-2]);
         if(str.cun!=null){
                str.cun=null;
               tabTown("yonghe");
            }else if(str.zhen!=null){
                str.zhen=null;
                str.cun=null;
                tabCounty("xinning");
            }else if(str.xian!=null){
                str.xian=null;
                str.zhen=null;
                str.cun=null;
               tabCity("meizhou");
            }else if(str.shi!=null){
                str.shi=null;
                str.xian=null;
                str.zhen=null;
                str.cun=null;
              tabProvince("guangdong");
              $(".map-back-btn").addClass('hide');
            }

     }
     //地图返回事件
    $("#mapBtn").click(function(event) {
        Highmap.mapObject.drillUp();
    });

	 //TOP10点击选中地图
	 // var map_index=null;
	 // var map_name=null;
	 // function shi(){
		// selectMap("清远市");
	 // }
	 // function ha(){
		//  selectMap("梅州市");
	 // }
	    // function selectMap(name){
     //    var mapSeries = {};
     //     $.each(Highmap.mapObject.series, function(i,s) {
     //         if(s.userOptions['type'] === 'map')
     //             mapSeries = s;
     //     });
     //      var points=mapSeries.points;
     //    if(map_index!=null&&map_name!=name){
     //         $.each(points,function(i,v){
     //            points[i].select(false);
     //         });
     //        }
     //     $.each(points,function(i,v){
     //         if(name==v.name){
     //             map_index=i;
     //             map_name=v.name;
     //             if(points[i].selected){
     //                points[i].select(false);
     //             }else{
     //                 points[i].select();
     //             }
     //         }

     //     })
     // }
