   
   var option = {
			title:false,
			mapName:'广东省',
			height:'550', 
			fontFamily:'Microsoft YaHei', /*地图全部的字体样式*/
			topNode:'440000000000', /*地图顶层父节点ID*/
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
				isRemote:true, /*是否直接从后台加载数据*/
				remoteUrl:"/a/map/mapPiont", /*坐标点 远程地图URL参数，请求参数为{pac:#pac}*/
				pointName:'相对贫困村', /*配置地图点默认属性名称*/
				pointColor:'#ff9000', /*标识点颜色*/
				tooltipTitleColor:'#ff9000', 
				tooltipTextColor:'#f3e924', 
				doSetPoint:'showpoint'
			},
			mapLoader:{
				isRemote:true, /*是否直接从后台加载数据*/
				remoteUrl:"/a/map/mapData" /*远程地图URL参数，请求参数为{pac:#pac}*/
			},
			showPoint:true,
			colorStops:['#91d4ff','#2e5fff','#ff0000'],
			legend:{
				enabled : true,
				legendFloat : true,
				legendAlign : 'bottom',
				x : 25,
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
			drillKey:'drill-key',
			valueMapper:[{key:'value',name:'贫困人口数',valueSuffix:'人'}],
			doClick : 're',
			doDrilldown:'drilldown',
			doDrillup:'fan',
            setLoading:['showLoadingMask','hideLoadingMask']
	};
  
   var levelMap = new Map();
	   levelMap.put(1, "province");
	   levelMap.put(2, "city");
	   levelMap.put(3, "county");
	   levelMap.put(4, "town");
	   levelMap.put(5, "country");
	
   var chartid = '';
   var type=1;
   var scope='1';
   var queryName="aspire.bi.common.test.queryMapByPAC";
    $(function(){
    	chartid=Highmap.mybatis.Map("map",queryName,null,{key:'PAC',value:'AID_POOR_POP'},"countries/cn/custom/cn-gongdong","PAC",option);
    })
    

    

     //地图点击事件
     function re(e){
         var pac=e.properties['PAC'];
         var name=e.properties['name'];
         var pointType=e.properties['type'];
//	         if(pointType=='PKH'){
//	        	 $.poorFile.showFamilyFile(pac);
//	         }
//	         if(pointType=='PKC'){
//	        	 $.poorFile.showCountryFile(pac)
//	         }
     }
     //地图下钻事件
     function drilldown(pac,level){
	         $.ajax({
	    		 type: "POST",
	    		 url: ctx + "/a/map/mapPac",
	    		 dataType: "json",
	    		 data:{pacid:pac,level:level},
	    		 success: function(map){
	    			 var id=map.pac;
	    			 var hasCounty=map.isFun;
	    			 var opts=null;
	    			 if(hasCounty==1){
	    				 opts={hasCountryFile:true};
	    			 }
	    			// changeDataDistrict(levelMap.get(level),id,opts);
	    		 }
	    	 });
     }

     function showpoint(point,pac,level){
    	 /**
    	  * lineColor: 'black',
			lineWidth: 0.1,
    	  */
		 var vname = '相对贫困村';
     	  var visible = false;
     	  var radius = 2;
         if(level > 3){
         		vname = '相对贫困户';
         		radius = 3;
         		if(level > 4){
         			visible = true;
         			radius = 4;
         		}
         			
         }
         return {isLoadPoint:true,pointName:vname,pointVisible:visible,radius:radius}
	 }
     //返回事件
     function fan(point,pac,level){
    	 $.ajax({
    		 type: "POST",
    		 url: ctx + "/a/map/mapPac",
    		 dataType: "json",
    		 data:{pacid:pac,level:level},
    		 success: function(map){
    			 var id=map.pac;
    			 var hasCounty=map.isFun;
    			 var opts=null;
    			 if(hasCounty==1){
    				 opts={hasCountryFile:true};
    			 }
    			// changeDataDistrict(levelMap.get(level),id,opts);
    		 }
    	 });
     }
     //绑定地图返回事件
     function dataMapBack() {
        Highmap.mapObject.drillUp();
    }

