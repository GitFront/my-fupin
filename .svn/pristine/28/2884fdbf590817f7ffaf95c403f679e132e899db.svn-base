<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<%@ include file="/WEB-INF/views/include/report.jsp"%>
<html>
<head>
	<!-- 引入地图数据 -->
	<link href="${ctxStatic}/themes/css/font-awesome.css" rel="stylesheet">
	<%-- <script type="text/javascript" src="${ctxStatic}/js/maps/cn-guangdong2.js"></script> --%>
	<%-- 	<script type="text/javascript" src="${ctxStatic}/js/maps/cn-gd-heyuan.geo.js"></script> --%>
	<%-- <script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/china_map2.js"></script> --%>
	<%-- <script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/data3.js"></script> --%>
	<%-- <script type="text/javascript" src="${ctxStatic}/js/modules/rp_demo/data-bubble.js"></script> --%>
	
	<script type="text/javascript">
	 function re(e){
		 if(e.properties['type'] === 'PKC')
			 alert(e.properties['name']);
	 }
	 
	 function down(e){
		 /* alert(e.properties['name']) */
	 }
	 
	 function showpoint(key,level){
		 var loader = {};
		 if(level === 1) return loader;
		 if(level === 2){
			 if(key === '441800000000'){
				 loader['isLoadPoint'] = true;
				 loader['isRemote'] = true;
				 var point1 = {
						 queryName:'aspire.bi.common.map.queryMapPoint1', /*通过远程方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
						 mapper:{name:'NAME',PAC:'ID',PID:'PID',longitude:'VALUE1',latitude:'VALUE2',type:'POINT_TYPE'}, /*通过远程方式读取，读取的查询转义*/
						 pointName:'脱贫户', /*配置地图点默认属性名称*/
						 pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
						 pointColor:'#32d741', /*标识点颜色*/
						 radius: 2
				 };
				 
				 var point2 = {
						 queryName:'aspire.bi.common.map.queryMapPoint2', /*通过远程方式读取，读取的查询名称（mybatis）读取的查询参数为pac:区域ID，level:区域等级 */
						 mapper:{name:'NAME',PAC:'ID',PID:'PID',longitude:'VALUE1',latitude:'VALUE2',type:'POINT_TYPE'}, /*通过远程方式读取，读取的查询转义*/
						 pointName:'贫困户', /*配置地图点默认属性名称*/
						 pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
						 pointColor:'#ffff32', /*标识点颜色*/
						 radius: 2
				 };
				 loader['points']=[point1,point2];
			 }else {
				 loader['isLoadPoint'] = false;
			 } 
		 }
		 if(level === 3){
			 loader['isLoadPoint'] = false;			 
		 }
         return loader;
	 }
	 
	 function download1(){
		 var mapSeries = {};
		 $.each(Highmap.mapObject.series, function(i,s) { 
			 if(s.userOptions['type'] === 'map')
				 mapSeries = s;
		 });
		 
		 var points = mapSeries.points;
		 $.each(points, function(i,p) {    
			var name = p.properties['name'];
			if(name === "梅州市"){
				var e = Highmap.mapObject.series[0].points[i];
				 var mobject = common.report.highcharts.object.get(chartid);
				 var opt = mobject.option;
				 var data = mobject.data;
				 var joinBy = mobject.joinBy;
				 var drillKey = e.properties[opt.drillKey];
				 var key = e.properties[joinBy];
		     	 var dmv_ = Highmap.ChartDataFormate.FormatMapData(data,opt.parentKey);
				 
		     	 Highmap.utils.mapDrilldown(e,drillKey,joinBy,dmv_[key]);
			}   
	     });
		 
		 /* Highmap.mapObject.get('gd_qingyuan').select(); */
	 }
	 
	 function upload(){
		 Highmap.mapObject.drillUp();
		 Highmap.levarray.length = 1;
	 }
	 
	 
	 function fok(){
		 var mapSeries = {};
		 $.each(Highmap.mapObject.series, function(i,s) { 
			 if(s.userOptions['type'] === 'map')
				 mapSeries = s;
		 });
		 
		  var points=mapSeries.points;
		 $.each(points,function(i,v){
			 if("兴宁市"===v.name){
				 map_index=i;
				 map_name=v.name;
				 if(points[i].selected){
					points[i].select(false);
				 }else{
					 points[i].select();
				 }
			 } 
			 
		 })
	 }
	 
	 function reloadf(){
		 Highmap.mybatis.reloadData(chartid,"aspire.bi.common.map.queryMapInfoLayer",
				 {type:'map'},{key:'ID',value:'VALUE1',value2:'VALUE2',value3:'VALUE3',value4:'VALUE4'},
				 [{key:'value',name:'脱贫户',valueSuffix:'人'},{key:'value2',name:'贫困户脱贫率',valueSuffix: '%'}],true);
	 }
	 
	 function plus(){
		 Highmap.mapObject.mapZoom(0.5);
	 }
	 
	 function minus(){
		 Highmap.mapObject.mapZoom(2);
	 }
	
	/*  var option = {
			title:false,
			mapName:'广东省',
			height:'800', 
			showPoint:true,
			seriesBorderColor:'#8E8E8E',
			legend:{
				enabled : true,
				legendFloat : true,
				legendAlign : 'bottom',
				x : 0,
				y : -100
			},
			navigation:true,
			navigationButton:false,
			valueDecimal:2,
			parentKey:'PPAC',
			valueMapper:[{key:'value',name:'脱贫人口',valueSuffix:'人'},{key:'value2',name:'贫困人口脱贫率',valueSuffix: '%'}],
			isDrilldown:true,
			doClick : 're'
	}; */
	 
	 var option = {
				title:false,
				mapName:'广东省',
				height:'750', 
				isLayerLoad:true,
				fontFamily:'Microsoft YaHei', /*地图全部的字体样式*/
				topNode:'440000000000', /*地图顶层父节点ID*/
				color:{
					backgroundColor: '#1c1925',  /*地图底色(默认为空)*/
			        borderColor: '#1c1925',   /*地图边界色(默认为空)*/
			        seriesNullColor:'rgba(200, 200, 200, 0.2)', /*地图空块默认颜色(默认为空)*/
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
					isRemote:false,
					localUrl:'/static/js/maps/json_mappoint/',
					doSetPoint:'showpoint',
					points:[{
						pointName:'标识点1', /*配置地图点默认属性名称*/
						pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
						pointColor:'#fffc1b', /*标识点颜色*/
						radius: 1
					},{
						pointName:'标识点2', /*配置地图点默认属性名称*/
						pointVisible:false,  /*配置地图标识点是否默认显示(默认不显示)*/
						pointColor:'#ff9000', /*标识点颜色*/
						radius: 2
					}]					
				},
				colorStops:['#91d4ff','#2e5fff','#ff0000'],
				legend:{
					enabled : true,
					legendFloat : false,
					legendAlign : 'bottom',
					x : 300,
					y : -40
				},
				mapLoader:{
					/* isRemote:true,
					remoteType:'common',
					commonQueryName:'aspire.bi.common.map.queryMapGeoData', 
					commonMapper:{name:'NAME',PAC:'PAC',PPAC:'PPAC',drillKey:'PAC',geometry:'GEOMETRY'}  */
					isRemote:false,
					isDomain:false,
					jsonpCallback:'mapGeoJsonp', 
					/* localUrl:'http://10.9.14.87:6677/' */
					localUrl:'http://210.76.68.69/MapDemo/'
				},
				drillUpButton:{
			     	relativeTo: 'spacingBox', 
			        y: 0,
			        x: 0, 
			        fill: 'rgba(0,0,0,0)', 
			        strokeWidth: 1,
			        stroke: '#87e6fa', 
			        r: 3, 
			        fontColor:'#6cc3ed',
			        fontSize:13, 
			        selectColor:'rgba(0,0,0,0)' 
			    },
				navigation:true,
				navigationButton:false,
				valueDecimal:2,
				parentKey:'PPAC',
				drillKey:'drillKey',
				/* valueMapper:[{key:'value',name:'脱贫人口',valueSuffix:'人'},{key:'value2',name:'贫困人口脱贫率',valueSuffix: '%'},
										  {key:'value3',name:'贫困户',valueSuffix: '户'},{key:'value4',name:'贫困户脱贫率',valueSuffix: '%'}], */
				valueMapper:[{key:'value',name:'自然村达标村数',valueSuffix:'个'},{key:'value3',name:'20户以上自然村总数',valueSuffix: '个'},{key:'value4',name:'自然村达标比例',valueSuffix: '%'}], 
				isDrilldown:true,
				doDrilldown:'down',
				doClick : 're'
		};
	 
	var chartid = '';
		$(function(){
			/* countries/cn/custom/cn-gongdong
				chartid = Highmap.create.init("container",gd_maps_data,"countries/cn/custom/cn-gongdong","PAC",option); */
			chartid = Highmap.mybatis.Map("container","aspire.bi.common.map.queryMapInfoLayerTest1",{type:'map'},{key:'ID',value:'VALUE3',value2:'VALUE1',value3:'VALUE2',value4:'VALUE4'},
					"PAC:440000000000","PAC",option);
		}); 
	</script>
</head>
  
  <body>
	
<%-- 	<rp:content>
		<rp:row columns="false">			
			<rp:col title="动态地图" icon="Map">
				<rp:panel-content >
					<hm:map joinBy="PAC" mapData="countries/cn/custom/cn-gongdong" queryName="aspire.bi.common.map.queryMapInfo" parameters="type:map" id="container" 
							mapper="key:ID;value:VALUE;value1:VALUE1;value2:VALUE2;value3:VALUE3;value4:VALUE4" option="mapName:'广东省';navigation:false;valueDecimal:2;valueMapper:[{key:'value',name:'业务量'},{key:'value1',name:'用户量'}]"/>
				</rp:panel-content>
			</rp:col>
		</rp:row>
	</rp:content> --%>
	
	<rp:content>
		<rp:row columns="false">			
			<rp:col title="动态地图" icon="Map" btns="button:{id:ok3,icon:icon-btn-download,click:download1};button:{id:ok2,icon:icon-btn-upload,click:upload};button:{id:ok1,icon:icon-btn-ok,click:fok};button:{id:ok2,icon:icon-btn-plus-sign,click:plus};button:{id:ok3,icon:icon-btn-minus-sign,click:minus};button:{id:rk1,icon:icon-btn-refresh,click:reloadf};">
				<rp:panel-content height="1000">
					<div id="container"></div>
				</rp:panel-content>
			</rp:col>
			<%-- <rp:col title="条形堆积图" icon="Bar">
				<rp:panel-content height="500" >
					<div id="container-bars-volume1"></div>
				</rp:panel-content>
			</rp:col> --%>
		</rp:row>
		
	</rp:content> 
	
	<!-- <div id="container"></div> -->
	
	</body> 
</html>
