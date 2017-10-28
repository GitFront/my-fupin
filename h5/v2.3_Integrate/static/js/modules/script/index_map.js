    var loading_api = {};
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
            setLoading:['showLoadingMask','closeLoading']
        };

        var chartid = '';
    $(function(){
          chartid=Highmap.create.init("map",gd_maps_data,"countries/cn/custom/cn-gongdong","gd_key",option);
          changeData();
    })

   var levelMap = new Map();
   levelMap.put(1, "province");
   levelMap.put(2, "city");
   levelMap.put(3, "county");
   levelMap.put(4, "town");
   levelMap.put(5, "country");
   //切换界面
   function changeData(/*pac,level*/){
       var level=Highmap.levarray.length;
       var pac=Highmap.levelid;
       loading_api = changeDataDistrict(levelMap.get(level),'440000000000','');
   }

    //监控加载完成情况
    function closeLoading(){
        loading_api.onSuccess();
    }

    function re(e){
        var level=Highmap.levarray.length;
        var pac=Highmap.levelid;
        loading_api = changeDataDistrict(levelMap.get(level),pac,'');
    }

   //返回事件
   function fan(e){
       showLoadingMask();
       var level=Highmap.levarray.length;
       var pac=Highmap.levelid;
       loading_api = changeDataDistrict(levelMap.get(level),pac,'');
       closeLoading();
   }
