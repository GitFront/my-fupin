   
   var option = {
			title:false,
			mapName:'广东省',
			height:'480', 
			fontFamily:'Microsoft YaHei', /*地图全部的字体样式*/
			topNode:'440000000000', /*地图顶层父节点ID*/
			color:{
				backgroundColor: '#e9f3fd',  /*地图底色(默认为空)*/
		        borderColor: '#e9f3fd',   /*地图边界色(默认为空)*/
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
			colorStops:['#91d4ff','#2e5fff','#ff0000'],
			legend:{
				enabled : true,
				legendFloat : true,
				legendAlign : 'bottom',
				x : 25,
				y : -25
			},
			navigation:false,
			navigationButton:false,
			valueDecimal:2,
			parentKey:'PPAC',
			valueMapper:[{key:'value',name:'贫困人口数',valueSuffix:'人'}],
            setLoading:['showLoadingMask','hideLoadingMask']
	};

  function getMapData(){
	return  $.ajax({
  		type:"post",
  		url:ctx+"/index/map/mapData",
  		dataType:"json",
  		success:function(dat){
  			chartid=Highmap.create.init("map_index",dat,"countries/cn/custom/cn-gongdong","PAC",option,true);
  		}
  	})
  }
    $(function(){
    		 getMapData(); 

    })

   

   
   

