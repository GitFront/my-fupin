   var loading_api = null;
   var badColor = ['#91d4ff','#2e5fff','#ff0000'];
   var greatColor = ['#91d4ff','#2e5fff','#6bdb1f'];
   
   function initOption(mapPac,mapName,userPac,userLevel,geoPath){
   	
   		var option = {
				title:false,
				mapName:mapName,
				height:'550', 
				authInfo:{userPac:userPac,userLevel:userLevel},
				fontFamily:'Microsoft YaHei', /*地图全部的字体样式*/
				topNode: mapPac, /*设置地图顶层父节点ID*/
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
					/*isRemote:true, 是否直接从后台加载数据
					remoteType:'common',远程地图读取方式："ajax"通过自主ajax读取，"common"通过通用方法读取
					commonQueryName:'aspire.bi.common.povertygeometry.queryMapGeoData', 通过common方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 
					commonMapper:{name:'NAME',PAC:'PAC',PPAC:'PPAC',drillKey:'PAC',geometry:'GEOMETRY',ifFurtherPoor:'IF_FURTHER_POOR'} 通过common方式读取，读取的查询转义{name:'NAME1',mapKey:'ID',parentKey:'PID',drillKey:'ID',geometry:'GEO'}*/
					/*读取共享地图信息*/
					isRemote: false,
					isDomain: false,
					jsonpCallback: 'mapGeoJsonp', 
					localUrl: geoPath		
				},
				showPoint:true,
				colorStops:badColor,
				legend:{
					enabled : true,
					legendFloat : true,
					legendAlign : 'bottom',
					backgroundColor: 'rgba(29,26,40,0.7)',
					x : -95,
					y : -25
				},
		        drillUpButton:{
			     	relativeTo: 'spacingBox', 
			     	y: 20,
			        x: -60, 
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
				valueMapper:[{key:'value',name:'贫困人数',valueSuffix:'人'},{key:'value2',name:'预脱贫人数',valueSuffix: '人'},{key:'value3',name:'预脱贫率（按人数）',valueSuffix: '%'}],
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
	
   var type=1;
   var scope='1';
   var statTime = '';
   var helppac = '999';
   var queryName="com.aspire.birp.modules.antiPoverty.dao.DataMonDao.queryMapCun";
    $(function(){
    	var ret = getDate();     	
    	if(ret){ret.then(function(res){
    		  var option_ = initOption(res.mapPac,res.mapName,res.userPac,res.userLevel,res.geoPath);
    		  statTime = res.statTime;
    		  helppac = res.helppac;
    		  Highmap.mybatis.Map("map",queryName,{"type":type,"scope":scope,"newDate":statTime ,"helppac":helppac},
    		  	{key:'PAC',value:'POOR_POP',value2:'AID_POOR_POP',value3:'AID_POOR_POP_R'},"PAC:"+res.mapPac,"PAC",option_);
    		  changeData(null, res.userPac, res.userLevel);
    	})}

    })
    //地图刷新数据--出列村  
    function changeDataMapDataType(t){
    	type=t;
    	loadMap(t,scope);
    };
    
    function loadMap(type,scope){
    	var valueMapper = {};
    	var mapper = {};
    	var colorStops = [];
    	if(type==1){
    		valueMapper=[{key:'value',name:'贫困户数',valueSuffix:'户'},{key:'value2',name:'预脱贫户数',valueSuffix:'户'},{key:'value3',name:'预脱贫户比例',valueSuffix: '%'}];
    		mapper = {key:'PAC',value:'POOR_FAM',value2:'AID_POOR_FAM',value3:'AID_POOR_FAM_R'};
    		colorStops=badColor;
    	}else if(type==5){
    		valueMapper=[{key:'value',name:'预脱贫户比例',valueSuffix: '%'},{key:'value2',name:'预脱贫户数',valueSuffix:'户'},{key:'value3',name:'贫困户数',valueSuffix:'户'}];
    		mapper = {key:'PAC',value:'AID_POOR_FAM_R',value2:'AID_POOR_FAM',value3:'POOR_POP'};
    		colorStops=greatColor;
    	}else if(type==3){
    		valueMapper=[{key:'value',name:'预脱贫户数',valueSuffix:'户'},{key:'value2',name:'贫困户数',valueSuffix:'户'},{key:'value3',name:'预脱贫户比例',valueSuffix: '%'}];
    		mapper = {key:'PAC',value:'AID_POOR_FAM',value2:'POOR_FAM',value3:'AID_POOR_FAM_R'};
    		colorStops=greatColor;
    	}else if(type==4){
    		valueMapper=[{key:'value',name:'预脱贫人数',valueSuffix: '人'},{key:'value2',name:'贫困人数',valueSuffix:'人'},{key:'value3',name:'预脱贫率（按人数）',valueSuffix:'%'}];
    		mapper = {key:'PAC',value:'AID_POOR_POP',value2:'POOR_POP',value3:'AID_POOR_POP_R'};
    		colorStops=greatColor;
    	}else if(type==2){
    		valueMapper=[{key:'value',name:'贫困人数',valueSuffix:'人'},{key:'value2',name:'预脱贫人数',valueSuffix: '人'},{key:'value3',name:'预脱贫率（按人数）',valueSuffix: '%'}];
    		mapper = {key:'PAC',value:'POOR_POP',value2:'AID_POOR_POP',value3:'AID_POOR_POP_R'};
    		colorStops=badColor;
    	}else if(type==6){
    		valueMapper=[{key:'value',name:'预脱贫率（按人数）',valueSuffix: '%'},{key:'value2',name:'贫困人数',valueSuffix:'人'},{key:'value3',name:'预脱贫人数',valueSuffix: '人'}];
    		mapper = {key:'PAC',value:'AID_POOR_POP_R',value2:'POOR_POP',value3:'AID_POOR_POP'};
    		colorStops=greatColor;
    	}
		Highmap.mybatis.reloadData(Highmap.mapid,queryName,{"type":type,"scope":scope,"newDate":statTime ,"helppac":helppac},mapper,valueMapper,colorStops);
    	
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
	         if(pointType=='PKH'){
	        	 $.poorFile.showFamilyFile(pac);
	         }
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
	    			 var opts={hasCountryFile:true};
	    			 loading_api = changeDataDistrict(levelMap.get(level),id,opts);
	    			 
	    		 }
	    	 });
     }

     function showpoint(pac,level){
    	var loader = {};
		 if(level < 5) {
		 	/*省、市、县级统一读取村级位置点*/
		 	loader['isLoadPoint'] = true;
			 loader['isRemote'] = true;
			 var radius = 2;
			 if(level > 1) radius = 3;
			 var point1 = {
					 queryName:'aspire.bi.common.povertygeometry.queryMappiontGeoForCountry', /*通过远程方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
					 mapper:{name:'NAME',PAC:'PAC',PPAC:'PPAC',longitude:'LONGITUDE',latitude:'LATITUDE',type:'POINT_TYPE'}, /*通过远程方式读取，读取的查询转义*/
					 pointName:'相对贫困村', /*配置地图点默认属性名称*/
					 pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
					 pointColor:'#ffff32',/*标识点颜色*/
					 radius:radius
			 };
		 	loader['points']=[point1];
		 }else{
		 	/*镇、村级统计读取脱贫户和贫困户位置点*/
		 	loader['isLoadPoint'] = true;
			loader['isRemote'] = true;
		 	var pointVisible = true;
		 	var radius = 3.5;
		 	var mapper = {name:'NAME',PAC:'PAC',PPAC:'PPAC',longitude:'LONGITUDE',latitude:'LATITUDE',type:'POINT_TYPE'};
			var point1 = {
					 queryName:"aspire.bi.common.povertygeometry.queryMappiontGeoForFam", 
					 parameters:{"isOut":1},
					 mapper:mapper, 
					 pointName:'预脱贫户', /*配置地图点默认属性名称*/
					 pointVisible:pointVisible,  /*配置地图标识点是否默认显示(默认不显示)*/
					 pointColor:'#009311',/*标识点颜色*/
					 radius:radius
			 };
			 
			 var point2 = {
					 queryName:"aspire.bi.common.povertygeometry.queryMappiontGeoForFam", 
					 parameters:{"isOut":0},
					 mapper:mapper, 
					 pointName:'未脱贫户', /*配置地图点默认属性名称*/
					 pointVisible:pointVisible,  /*配置地图标识点是否默认显示(默认不显示)*/
					 pointColor:'#ffff32',/*标识点颜色*/
					 radius:radius
			 };
			 loader['points']=[point1,point2];
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

