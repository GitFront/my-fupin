   var loading_api = null;
   
   
   function initOption(userPac,userLevel){
   	
   		var option = {
				title:false,
				mapName:'广东省',
				height:'550', 
				authInfo:{userPac:userPac,userLevel:userLevel},
				fontFamily:'Microsoft YaHei', /*地图全部的字体样式*/
				topNode:'440000000000', /*地图顶层父节点ID*/
				isLayerLoad:true,
				color:{
					backgroundColor: '#1f1e30',  /*地图底色(默认为空)*/
			        borderColor: '#1d1a29',   /*地图边界色(默认为空)*/
			        seriesNullColor:'#56627c', /*地图空块默认颜色(默认为空)*/
			        seriesBorderColor:'#212832', /*地图区域边界颜色(默认为空)*/
			        seriesSelectColor: '#f3e924', /*地图区域选中色(默认为空)*/
			        dataLabelColor:'#FFF', /*地图区域字体颜色*/
			        tooltipNameColor:'#f3e924', /*地图浮层区域名称字体颜色*/
			        tooltipValueColor:'#f3e924', /*地图浮层主数据显示字体颜色*/
			        tooltipMinorValueColor:'#FFFFFF', /*地图浮层次要数据显示字体颜色*/
			        tooltipBackgroundColor:'#1d1a28',
			        tooltipBorderColor:'#76d1fd'
				},
				pointLoader:{
						isLoadPoint:true, /*是否自动加载地图点属性*/
						isRemote:true,
						doSetPoint:'showpoint'
					},
				mapLoader:{
					isRemote:true, /*是否直接从后台加载数据*/
					remoteType:'common',/*远程地图读取方式："ajax"通过自主ajax读取，"common"通过通用方法读取*/
					commonQueryName:'aspire.bi.common.povertygeometry.queryMapGeoData', /*通过common方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
					commonMapper:{name:'NAME',PAC:'PAC',PPAC:'PPAC',drillKey:'PAC',geometry:'GEOMETRY',ifFurtherPoor:'IF_FURTHER_POOR'} /*通过common方式读取，读取的查询转义{name:'NAME1',mapKey:'ID',parentKey:'PID',drillKey:'ID',geometry:'GEO'}*/
				},
				showPoint:true,
				colorStops:['#91d4ff','#2e5fff','#ff0000'],
				legend:{
					enabled : true,
					legendFloat : true,
					legendAlign : 'bottom',
					backgroundColor: 'rgba(29,26,40,0.7)',
					x : -168,
					y : -5
				},
		        drillUpButton:{
			     	relativeTo: 'spacingBox', 
			     	y: 30,
			        x: -30, 
			        fill: '#6cc3ed', 
			        strokeWidth: 1,
			        stroke: '#87e6fa', 
			        r: 3, 
			        fontColor:'#1c1925',
			        fontSize:13, 
			        selectColor:'#3bb0c9' 
			    },
				navigation:true,
				navigationButton:false,
				valueDecimal:2,
				parentKey:'PPAC',
				drillKey:'drillKey',
				valueMapper:[{key:'value',name:'相对贫困村数',valueSuffix:'个'},{key:'value2',name:'示范村达标村数',valueSuffix: '个'},{key:'value3',name:'示范村达标比例',valueSuffix: '%'}],
				doClick : 're',
				doDrilldown:'changeData',
				doDrillup:'fan',
	            setLoading:['showLoadingMask','closeLoading']
		};
		return option;
   }
   
  function getDate(){
	  return $.ajax({ type:"post", url:ctx + "/a/map/mapDate", dataType:"json" });
  }
   var levelMap = new Map();
	   levelMap.put(1, "province");
	   levelMap.put(2, "city");
	   levelMap.put(3, "county");
	   levelMap.put(4, "town");
	   levelMap.put(5, "country");
	
   var chartid = '';
   var type=1;
   var scope='1';
   var statTime = '';
   var queryName="com.aspire.birp.modules.antiPoverty.dao.ExampleCountryMapDao.queryEcMapData";
    $(function(){
    	var ret = getDate();     	
    	if(ret){ret.then(function(res){
    		  var option_ = initOption(res.userPac,res.userLevel);
    		  statTime = res.ecStatMonth;
    		  chartid = Highmap.mybatis.Map("map",queryName,{"type":type,"scope":scope,"month":statTime},
    		  	{key:'PAC',value:'PROVICE_NATURAL_NUM',value2:'E_NATURAL_NUM',value3:'E_NATURAL_RATE'},"countries/cn/custom/cn-gongdong","PAC",option_);
    		  changeData(null,'440000000000',1);
    	})}

    })
    //地图刷新数据--出列村  
    function changeDataMapDataType(t){
    	type=t;
    	loadMap(t,scope);
    };
    
    function loadMap(type){
    	var valueMapper = {};
    	var mapper = {};
    	if(type==1){
    		valueMapper=[{key:'value',name:'相对贫困村数',valueSuffix:'个'},{key:'value2',name:'示范村达标村数',valueSuffix: '个'},{key:'value3',name:'示范村达标比例',valueSuffix: '%'}];
    		mapper = {key:'PAC',value:'PROVICE_NATURAL_NUM',value2:'E_NATURAL_NUM',value3:'E_NATURAL_RATE'};
    	}else if(type==2){
    		valueMapper=[{key:'value',name:'示范村达标村数',valueSuffix: '个'},{key:'value2',name:'相对贫困村数',valueSuffix:'个'},{key:'value3',name:'示范村达标比例',valueSuffix: '%'}];
    		mapper = {key:'PAC',value:'E_NATURAL_NUM',value2:'PROVICE_NATURAL_NUM',value3:'E_NATURAL_RATE'};
    	}else if(type==3){
    		valueMapper=[{key:'value',name:'示范村达标比例',valueSuffix: '%'},{key:'value2',name:'相对贫困村数',valueSuffix:'个'},{key:'value3',name:'示范村达标村数',valueSuffix: '个'}];
    		mapper = {key:'PAC',value:'E_NATURAL_RATE',value2:'PROVICE_NATURAL_NUM',value3:'E_NATURAL_NUM'};
    	}else if(type==4){
    		valueMapper=[{key:'value',name:'完成提升创建村数',valueSuffix: '个'},{key:'value2',name:'相对贫困村数',valueSuffix:'个'},{key:'value3',name:'完成提升创建村比例',valueSuffix:'%'}];
    		mapper = {key:'PAC',value:'P_NATURAL_NUM',value2:'PROVICE_NATURAL_NUM',value3:'P_NATURAL_RATE'};
    	}else if(type==5){
    		valueMapper=[{key:'value',name:'完成提升创建村比例',valueSuffix: '%'},{key:'value2',name:'相对贫困村数',valueSuffix:'个'},{key:'value3',name:'完成提升创建村数',valueSuffix:'个'}];
    		mapper = {key:'PAC',value:'P_NATURAL_RATE',value2:'PROVICE_NATURAL_NUM',value3:'P_NATURAL_NUM'};
    	}else if(type==6){
    		valueMapper=[{key:'value',name:'20户以上自然村数',valueSuffix: '个'},{key:'value2',name:'自然村达标村数',valueSuffix:'个'},{key:'value3',name:'自然村达标村比例',valueSuffix: '%'}];
    		mapper = {key:'PAC',value:'NATURAL_NUM',value2:'COMPLETE_MODEL_NUM',value3:'COMPLETE_MODEL_RATE'};
    	}else if(type==7){
    		valueMapper=[{key:'value',name:'自然村达标村数',valueSuffix: '个'},{key:'value2',name:'20户以上自然村数',valueSuffix:'个'},{key:'value3',name:'自然村达标村比例',valueSuffix: '%'}];
    		mapper = {key:'PAC',value:'COMPLETE_MODEL_NUM',value2:'NATURAL_NUM',value3:'COMPLETE_MODEL_RATE'};
    	}else if(type==8){
    		valueMapper=[{key:'value',name:'自然村达标村比例',valueSuffix: '%'},{key:'value2',name:'20户以上自然村数',valueSuffix:'个'},{key:'value3',name:'自然村达标村数',valueSuffix: '个'}];
    		mapper = {key:'PAC',value:'COMPLETE_MODEL_RATE',value2:'NATURAL_NUM',value3:'COMPLETE_MODEL_NUM'};
    	}else if(type==9){
    		valueMapper=[{key:'value',name:'完成提升创建自然村数',valueSuffix: '个'},{key:'value2',name:'20户以上自然村数',valueSuffix:'个'},{key:'value3',name:'完成提升创建自然村比例',valueSuffix: '%'}];
    		mapper = {key:'PAC',value:'COMPLETE_UPGRADE_NUM',value2:'NATURAL_NUM',value3:'COMPLETE_UPGRADE_RATE'};
    	}else if(type==10){
    		valueMapper=[{key:'value3',name:'完成提升创建自然村比例',valueSuffix: '%'},{key:'value2',name:'20户以上自然村数',valueSuffix:'个'},{key:'value',name:'完成提升创建自然村数',valueSuffix: '个'}];
    		mapper = {key:'PAC',value:'COMPLETE_UPGRADE_RATE',value2:'NATURAL_NUM',value3:'COMPLETE_UPGRADE_NUM'};
    	}
		Highmap.mybatis.reloadData(chartid,queryName,{"type":type,"scope":scope,"month":statTime},mapper,valueMapper);
    	
    }
    
  //地图刷新数据--全局
    function changeDataMapDataScope(s){
    	var PAC=Highmap.levelid;
    	scope=s;
    	loadMap(type,scope);
    	
    }
     //地图点击事件
     function re(e){
         var pac=e.properties['PAC'];
         var name=e.properties['name'];
         var pointType=e.properties['type'];
	     if(pointType=='PKC'){
	       	$.poorFile.showCountryFile(pac)
	      }
     }
     function test(){
    	 alert(1)
     }
     
     //切换界面
     function changeData(point,pac,level){
	         $.ajax({
	    		 type: "POST",
	    		 url: ctx + "/a/map/mapPac",
	    		 dataType: "json",
	    		 data:{pacid:pac,level:level},
	    		 success: function(map){
	    			 var id=map.pac;
	    			 var opts={hasCountryFile:false};
	    			 loading_api = changeDataDistrict(levelMap.get(level),id,opts);
	    			 
	    		 }
	    	 });
     }

     function showpoint(pac,level){
    	var loader = {};
		 if(level < 5) {
		 	/*省、市、县、镇级统一读取村级位置点*/
		 	loader['isLoadPoint'] = true;
			 loader['isRemote'] = true;
			 var radius = 2;
			 if(level > 1) radius = 3;
			 var point1 = {
					 queryName:'com.aspire.birp.modules.antiPoverty.dao.ExampleCountryMapDao.queryMappiontGeoForComplete', /*通过远程方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
					 mapper:{name:'NAME',PAC:'PAC',PPAC:'PPAC',longitude:'LONGITUDE',latitude:'LATITUDE',type:'POINT_TYPE'}, /*通过远程方式读取，读取的查询转义*/
					 pointName:'示范村达标', /*配置地图点默认属性名称*/
					 pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
					 pointColor:'#009311',/*标识点颜色*/
					 radius:radius
			 };
			 
			 var point2 = {
					 queryName:'com.aspire.birp.modules.antiPoverty.dao.ExampleCountryMapDao.queryMappiontGeoForBuilding', /*通过远程方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
					 mapper:{name:'NAME',PAC:'PAC',PPAC:'PPAC',longitude:'LONGITUDE',latitude:'LATITUDE',type:'POINT_TYPE'}, /*通过远程方式读取，读取的查询转义*/
					 pointName:'示范村建设中', /*配置地图点默认属性名称*/
					 pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
					 pointColor:'#ffff32',/*标识点颜色*/
					 radius:radius
			 };
			 
			 var point3 = {
					 queryName:'com.aspire.birp.modules.antiPoverty.dao.ExampleCountryMapDao.queryMappiontGeoForNostart', /*通过远程方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
					 mapper:{name:'NAME',PAC:'PAC',PPAC:'PPAC',longitude:'LONGITUDE',latitude:'LATITUDE',type:'POINT_TYPE'}, /*通过远程方式读取，读取的查询转义*/
					 pointName:'未启动的村', /*配置地图点默认属性名称*/
					 pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
					 pointColor:'#FF5F20',/*标识点颜色*/
					 radius:radius
			 };
			 
		 	loader['points']=[point1,point2,point3];
		 }else{
		 	/*村级统计不读取*/
		 	loader['isLoadPoint'] = false;
		 }
         return loader;
	 }
     //返回事件
     function fan(pac,level){
    	 showLoadingMask();
    	 $.ajax({
    		 type: "POST",
    		 url: ctx + "/a/map/mapPac",
    		 dataType: "json",
    		 data:{pacid:pac,level:level},
    		 success: function(map){    		 	
    			 var id=map.pac;    			 
    			 var opts={hasCountryFile:false};
    			 loading_api = changeDataDistrict(levelMap.get(level),id,opts);
    			  closeLoading();
    		 },
    		 error:function(msg){
    			 loading_api.onError();
    		 }
    	 });
     }
     //监控加载完成情况
     var waitLoad = null;
     function closeLoading(){
     	/*线程等待*/
     	if(!waitLoad){
     		waitLoad = setInterval( function(){
	     		var out = false;
	     		if(loading_api !== null)
	     			out = true;
	     		if(out){
	     			loading_api.onSuccess();
	     			clearInterval(waitLoad);
	     		}
	     	}  , 20);
     	}else{
     		loading_api.onSuccess();
     	}     	  	
     	/*loading_api.onSuccess();*/
     }
     
     //绑定地图返回事件
     function dataMapBack() {
        Highmap.mapObject.drillUp();
    }

