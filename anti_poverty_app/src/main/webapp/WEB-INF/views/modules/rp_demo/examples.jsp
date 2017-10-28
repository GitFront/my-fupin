<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="hc" uri="/WEB-INF/tlds/report-highcharts.tld" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/themes/css/base.min.css">
	
	<script type="text/javascript" src="${ctxStatic}/js/common/base.min.js"></script>
	<style>
	 .kit-col .panel .panel-content {
	        min-height: 100px;
	    }
	</style>
	<script type="text/javascript" src="${ctxStatic}/js/common/charts/common-highcharts-map.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/common/charts/common-highcharts.js"></script>
	
	<script type="text/javascript">
		var data10;
		var id1 = ''; 
		var id3 = '';
		var id5 = '';
		var id2 = '';
		
		function testReload(){
	    	var i1 = Math.floor(Math.random()*100+1);
	    	var i2 = Math.floor(Math.random()*100+1);
	    	var i3 = Math.floor(Math.random()*100+1);
	    	var i4 = Math.floor(Math.random()*100+1);
	    	var i5 = Math.floor(Math.random()*100+1);
	    	var data1 = [
    		            { name: 'olive', value: i1 }, 
    		            { name: 'momo', value: i2 }, 
    		            { name: 'only', value: i3 }, 
    		            { name: 'for', value: i4}, 
    		            { name: 'other', value: i5}];
	    	
	    	Highchart.reload(id1, data1);
	    }
		
		function testReloadBar(){
	    	/* var i1 = Math.floor(Math.random()*1000+1);
	    	var i2 = Math.floor(Math.random()*1000+1);
	    	var i3 = Math.floor(Math.random()*1000+1);
	    	var i4 = Math.floor(Math.random()*1000+1);
	    	var i5 = Math.floor(Math.random()*1000+1);
	    	var i6 = Math.floor(Math.random()*1000+1);
	    	var i7 = Math.floor(Math.random()*1000+1);
	    	var i8 = Math.floor(Math.random()*1000+1);
	    	var i9 = Math.floor(Math.random()*1000+1);
	    	var i10 = Math.floor(Math.random()*1000+1);
	    	var i11 = Math.floor(Math.random()*1000+1);
	    	var i12 = Math.floor(Math.random()*1000+1);
	    	var data1 = [
    		               { name: '2013-01', group: 'olive', value: i1 },  
    	                   { name: '2013-01', group: 'momo', value: i2 },  
    	                   { name: '2013-01', group: 'only', value: i3 },  
    	                   { name: '2013-02', group: 'olive', value: i5 },  
    	                   { name: '2013-02', group: 'momo', value: i6 },  
    	                   { name: '2013-02', group: 'only', value: i7 },  
    	                   { name: '2013-03', group: 'olive', value: i9 },  
    	                   { name: '2013-03', group: 'momo', value: i10 },  
    	                   { name: '2013-03', group: 'only', value: i11 },  
    	                   { name: '2013-04', group: 'olive', value: i4 },  
    	                   { name: '2013-04', group: 'momo', value: i8 },  
    	                   { name: '2013-04', group: 'only', value: i12 }
    	                    { name: '2013-01', group: 'olive', value: i1 },  
           	            { name: '2013-01', group: 'momo', value: i2 },  
           	            { name: '2013-01', group: 'only', value: i3 },
           	            { name: '2013-01', group: 'for', value: i4 },
           	            { name: '2013-02', group: 'olive', value: i5 },
           	            { name: '2013-02', group: 'momo', value: i6 },
           	            { name: '2013-02', group: 'only', value: i7 },
           	            { name: '2013-02', group: 'for', value: i8 },
           	            { name: '2013-03', group: 'olive', value: i9 },
           	            { name: '2013-03', group: 'momo', value: i10 },
           	            { name: '2013-03', group: 'only', value: i11 },
           	            { name: '2013-03', group: 'for', value: i12 }
    	                 ]; */
	    	var optionn = {horiz:false};
	    	Highchart.ajax.reload(id3, "/birp_web/d/highchart/groupData/load", {param1:"自定义参数",param2:"自定义参数"},optionn);
	    	Highchart.reload(id5);
	    	//Highchart.remove(id5);
	    }
		
		var id7 = ''
		function testReloadLine(){
	    	var i1 = Math.floor(Math.random()*1000+1);
	    	var i2 = Math.floor(Math.random()*1000+1);
	    	var i3 = Math.floor(Math.random()*1000+1);
	    	var i4 = Math.floor(Math.random()*1000+1);
	    	var i5 = Math.floor(Math.random()*1000+1);
	    	var i6 = Math.floor(Math.random()*1000+1);
	    	var i7 = Math.floor(Math.random()*1000+1);
	    	var i8 = Math.floor(Math.random()*1000+1);
	    	var i9 = Math.floor(Math.random()*1000+1);
	    	var i10 = Math.floor(Math.random()*1000+1);
	    	var i11 = Math.floor(Math.random()*1000+1);
	    	var i12 = Math.floor(Math.random()*1000+1);
	    	var data2 = [
	    	                { name: '2013-01', group: 'olive', value: i1 },  
	           	            { name: '2013-01', group: 'momo', value: i2 },  
	           	            { name: '2013-01', group: 'only|hide', value: i3 },
	           	            { name: '2013-01', group: 'for', value: i4 },
	           	            { name: '2013-02', group: 'olive', value: i5 },
	           	            { name: '2013-02', group: 'momo', value: i6 },
	           	            { name: '2013-02', group: 'only|hide', value: i7 },
	           	            { name: '2013-02', group: 'for', value: i8 },
	           	            { name: '2013-03', group: 'olive', value: i9 },
	           	            { name: '2013-03', group: 'momo', value: i10 },
	           	            { name: '2013-03', group: 'only|hide', value: i11 },
	           	            { name: '2013-03', group: 'for', value: i12 }
    	                 ];

		    
	    	Highchart.reload(id2, data2);
	    }
		
		function testReloadLineTime(){
	    	var data2 = new Array();
	    	var date = new Date();
	    	for(var i = 0 ; i < 1000 ; i++ ){
	    		data2.push({name:date.format('yyyy-MM-dd hh:mm:ss'),group:'访问次数',value:Math.floor(Math.random()*1000+1)});
	    		date.addDays(1);
	    	}
	    	
	    	Highchart.reload(id7, data2,{legend:true,irregular:false});
	    }
		var id8 ='';
		function testReloadGauge(){
	    	var i =  Math.floor(Math.random()*1000+1)
	    	Highchart.reload(id8, i);
	    }
		
		
		function testload(chart){
			var temp_ = '';
			$.each(chart.series[0].data, function(itemNo, series_) {
				temp_ = temp_  +  series_.name + "->" + series_.y  + "<br/>";
			 });
			var label = chart.renderer.label("图表将要加载，图表数据为:<br/>" + temp_, 100, 120)
			            .attr({
			                fill: Highcharts.getOptions().colors[0],
			                padding: 10,
			                r: 5,
			                zIndex: 8
			            })
			            .css({
			                color: '#FFFFFF'
			            })
			            .add();
			
			        setTimeout(function () {
			            label.fadeOut();
			        }, 2000);
			//window.alert("图表将要加载，图表数据为:\n" + temp_ );
		}
		
		function testclick(name,x,y){
			window.alert("数据点“"+name+"”已经被选中，x值为“" + x + "”,y值为“" + y+"” ");
		}
		
		
    	$(function(){
    		/* 工具类测试 */
    		/* var id = common.highcharts.object.add("1234test");
    		window.alert("add : " +common.highcharts.object.get(id));
    		if(common.highcharts.object.reset(id,"0987test")){
    			window.alert("reset : " + common.highcharts.object.get(id));
    		}
    		if(common.highcharts.object.remove(id)){
    			window.alert("del : " + common.highcharts.object.get(id));
    		} */
    		
    		/* common.utils.getUTC();
    		common.utils.getUTC("20090208");
    		common.utils.getUTC("20090208235959"); */
    		
    		/* window.alert(common.utils.dateFormat(new Date(),'yyyyMMddhhmmss')); */
    		/* 
    		var option = {
    				title:'饼形图', //报表标题(默认为空)
    				valueName:'年龄占比',//数据名称(默认为‘数值’)-饼状图特有
    				xName:'Love',//x轴名称(默认为空)
    				yName:'Love-Rate',//y轴名称(默认为空)
    				isPercent:true //是否显示百分比数据(默认为false)-堆积图特有
    			};
    		 */
    		 /* 自定义配置参数 */
    		 var option1 = {
     				valueName:'占比',//数据名称
     				yUnit:'人',
     				percentDecimal:1,
     				doLoad:"testload",
     				doClick:"testclick"
     			};
    		 data10 = [
    		            { name: 'olive', value: 116 }, 
    		            { name: 'momo', value: 115 }, 
    		            { name: 'only', value: 222 }, 
    		            { name: 'for', value: 324}];
    		id1 = Highchart.create.Pie("container1",data10,option1);
    	    
    	      var data = [
        	            { name: '2013-01', group: 'olive', value: 116 },  
        	            { name: '2013-01', group: 'momo', value: 115 },  
        	            { name: '2013-01', group: 'only', value: 222 },
        	            { name: '2013-01', group: 'for', value: 324 },
        	            { name: '2013-02', group: 'olive', value: 156 },
        	            { name: '2013-02', group: 'momo', value: 185 },
        	            { name: '2013-02', group: 'only', value: 202 },
        	            { name: '2013-02', group: 'for', value: 34 },
        	            { name: '2013-03', group: 'olive', value: 16 },
        	            { name: '2013-03', group: 'momo', value: 51 },
        	            { name: '2013-03', group: 'only', value: 22 },
        	            { name: '2013-03', group: 'for', value: 84 }
        	       ]; 
    	    
    	       var option2 = {
     				xName:'时间',
     				yName:'转化率',
     				doClick:"testclick"
     			};
    	       id2 = Highchart.create.Line("container2",data,option2);
    	       
    	       
    	       var data = [  
    	                   { name: '2013-01', group: 'olive', value: 116 },  
    	                   { name: '2013-01', group: 'momo', value: 115 },  
    	                   { name: '2013-01', group: 'only', value: 222 },  
    	                   { name: '2013-01', group: 'for', value: 324 },  
    	                   { name: '2013-02', group: 'olive', value: 156 },  
    	                   { name: '2013-02', group: 'momo', value: 185 },  
    	                   { name: '2013-02', group: 'only', value: 202 },  
    	                   { name: '2013-02', group: 'for', value: 34 },  
    	                   { name: '2013-03', group: 'olive', value: 16 },  
    	                   { name: '2013-03', group: 'momo', value: 51 },  
    	                   { name: '2013-03', group: 'only', value: 22 },  
    	                   { name: '2013-03', group: 'for', value: 84 }
    	       ];  

    	       var option4 = {
    	    		   horiz:true,
        			   doClick:"testclick"
        		};
    	       id3 = Highchart.create.Bars("container3",data,option4);
    	       
    	       var id4 = Highchart.create.StackBars("container4",data,option4);
    	       
    	       var option5 = {
         				percentDecimal:2,
          				isPercent:true,
          				yUnit:'%',
         			    doClick:"testclick"
          			};
    	       
    	       id5 = Highchart.create.StackBars("container5",data,option5);
    	       
    	       var option6 = {
    	    		    legend:true,
    		     		yUnit:'°', /*x轴单位(默认为空)*/
    		     		xName:'高度', /*y轴名称(默认为空)*/
    		     		yDecimal:1, /*y轴数据精度(默认为不保留小数位){最高只支持4位小数}*/
    		     		xUnit:'km' /*y轴单位(默认为空)*/,
         				doClick:"testclick"
    		     	};
    	       
    	       var id6 = Highchart.ajax.InvertedLine("container6", "/birp_web/d/highchart/invertedLine/load", {userid:"123",username:"张三"}, option6);
    	       
    	       
    	     /* 时序图数据集注意：
    	       	1、数据必须按时间排序，每个group第一个时间为开始时间点；
    	       	2、每个数据点的时间必须是连续的；
    	       	3、name的格式可以为yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss；
    	       	4、相邻数据点之间的时间间隙必须一致 */
    	       var data = [ { name:'2014-03-06',group:'活动访问人数',value:0.8445}, { name:'2014-03-07',group:'活动访问人数',value:0.8444}, { name:'2014-03-08',group:'活动访问人数',value:0.8451}, { name:'2014-03-09',group:'活动访问人数',value:0.8418}, { name:'2014-03-10',group:'活动访问人数',value:0.8264},
							{ name:'2014-03-11',group:'活动访问人数',value:0.8232}, { name:'2014-03-12',group:'活动访问人数',value:0.8233}, { name:'2014-03-13',group:'活动访问人数',value:0.8258}, { name:'2014-03-14',group:'活动访问人数',value:0.8283}, { name:'2014-03-15',group:'活动访问人数',value:0.8278}, { name:'2014-03-16',group:'活动访问人数',value:0.8256},
							{ name:'2014-03-17',group:'活动访问人数',value:0.8239}, { name:'2014-03-18',group:'活动访问人数',value:0.8239}, { name:'2014-03-19',group:'活动访问人数',value:0.8245}, { name:'2014-03-20',group:'活动访问人数',value:0.8265}, { name:'2014-03-21',group:'活动访问人数',value:0.8261}, { name:'2014-03-22',group:'活动访问人数',value:0.8269},
							{ name:'2014-03-23',group:'活动访问人数',value:0.8244}, { name:'2014-03-24',group:'活动访问人数',value:0.8244}, { name:'2014-03-25',group:'活动访问人数',value:0.8172}, { name:'2014-03-26',group:'活动访问人数',value:0.8139}, { name:'2014-03-27',group:'活动访问人数',value:0.8146}, { name:'2014-03-28',group:'活动访问人数',value:0.8164},
							{ name:'2014-03-29',group:'活动访问人数',value:0.8269}, { name:'2014-03-30',group:'活动访问人数',value:0.8269}, { name:'2014-03-29',group:'活动访问人数',value:0.8269}, { name:'2014-04-01',group:'活动访问人数',value:0.8258}, { name:'2014-04-02',group:'活动访问人数',value:0.8247}, { name:'2014-04-03',group:'活动访问人数',value:0.8286},
							{ name:'2014-04-04',group:'活动访问人数',value:0.8316}, { name:'2014-04-05',group:'活动访问人数',value:0.832}, { name:'2014-04-06',group:'活动访问人数',value:0.8333}, { name:'2014-04-07',group:'活动访问人数',value:0.8352}, { name:'2014-04-08',group:'活动访问人数',value:0.8357}, { name:'2014-04-09',group:'活动访问人数',value:0.8355},
							{ name:'2014-04-15',group:'活动访问人数',value:0.8403}, { name:'2014-04-18',group:'活动访问人数',value:0.8403}, { name:'2014-04-23',group:'活动访问人数',value:0.8406}, { name:'2014-04-30',group:'活动访问人数',value:0.8403}, { name:'2014-05-08',group:'活动访问人数',value:0.8396}, { name:'2014-05-11',group:'活动访问人数',value:0.8418},
							{ name:'2014-05-12',group:'活动访问人数',value:0.8384}, { name:'2014-05-13',group:'活动访问人数',value:0.8386}, { name:'2014-05-18',group:'活动访问人数',value:0.8372}, { name:'2014-06-30',group:'活动访问人数',value:0.839}, { name:'2014-07-25',group:'活动访问人数',value:0.84}, { name:'2014-09-30',group:'活动访问人数',value:0.8389},
							{ name:'2014-11-02',group:'活动访问人数',value:0.8423}, { name:'2014-11-03',group:'活动访问人数',value:0.8423}, { name:'2015-03-03',group:'活动访问人数',value:0.8435}/* ,
							{ name:'2014-03-06',group:'活动分享人数',value:0.6845}, { name:'2014-03-07',group:'活动分享人数',value:0.6844}, { name:'2014-03-08',group:'活动分享人数',value:0.6851}, { name:'2014-03-09',group:'活动分享人数',value:0.6818}, { name:'2014-03-10',group:'活动分享人数',value:0.8264},
							{ name:'2014-03-11',group:'活动分享人数',value:0.4332}, { name:'2014-03-12',group:'活动分享人数',value:0.4333}, { name:'2014-03-13',group:'活动分享人数',value:0.4358}, { name:'2014-03-14',group:'活动分享人数',value:0.4383}, { name:'2014-03-15',group:'活动分享人数',value:0.4378}, { name:'2014-03-16',group:'活动分享人数',value:0.4356},
							{ name:'2014-03-17',group:'活动分享人数',value:0.4339}, { name:'2014-03-18',group:'活动分享人数',value:0.4339}, { name:'2014-03-19',group:'活动分享人数',value:0.4345}, { name:'2014-03-20',group:'活动分享人数',value:0.4365}, { name:'2014-03-21',group:'活动分享人数',value:0.4361}, { name:'2014-03-22',group:'活动分享人数',value:0.4369},
							{ name:'2014-03-23',group:'活动分享人数',value:0.4344}, { name:'2014-03-24',group:'活动分享人数',value:0.4344}, { name:'2014-03-25',group:'活动分享人数',value:0.7572}, { name:'2014-03-26',group:'活动分享人数',value:0.7539}, { name:'2014-03-27',group:'活动分享人数',value:0.7546}, { name:'2014-03-28',group:'活动分享人数',value:0.7564},
							{ name:'2014-03-29',group:'活动分享人数',value:0.4369}, { name:'2014-03-30',group:'活动分享人数',value:0.4369}, { name:'2014-03-29',group:'活动分享人数',value:0.4369}, { name:'2014-04-01',group:'活动分享人数',value:0.4358}, { name:'2014-04-02',group:'活动分享人数',value:0.4347}, { name:'2014-04-03',group:'活动分享人数',value:0.4386},
							{ name:'2014-04-04',group:'活动分享人数',value:0.5116}, { name:'2014-04-05',group:'活动分享人数',value:0.512}, { name:'2014-04-06',group:'活动分享人数',value:0.5133}, { name:'2014-04-07',group:'活动分享人数',value:0.5152}, { name:'2014-04-08',group:'活动分享人数',value:0.5157}, { name:'2014-04-09',group:'活动分享人数',value:0.5155},
							{ name:'2014-04-15',group:'活动分享人数',value:0.6803}, { name:'2014-04-18',group:'活动分享人数',value:0.6803}, { name:'2014-04-23',group:'活动分享人数',value:0.6806}, { name:'2014-04-30',group:'活动分享人数',value:0.6803}, { name:'2014-05-08',group:'活动分享人数',value:0.5196}, { name:'2014-05-11',group:'活动分享人数',value:0.6818},
							{ name:'2014-05-12',group:'活动分享人数',value:0.5184}, { name:'2014-05-13',group:'活动分享人数',value:0.5186}, { name:'2014-05-18',group:'活动分享人数',value:0.5172}, { name:'2014-06-20',group:'活动分享人数',value:0.519}, { name:'2014-06-25',group:'活动分享人数',value:0.68}, { name:'2014-11-30',group:'活动分享人数',value:0.5189},
							{ name:'2014-12-02',group:'活动分享人数',value:0.6823}, { name:'2014-12-03',group:'活动分享人数',value:0.6823}, { name:'2015-01-03',group:'活动分享人数',value:0.6835} */];
    	       var data1 = [ { name:'20140306',group:'活动访问人数',value:0.8445}, { name:'20140307',group:'活动访问人数',value:0.8444}, { name:'20140308',group:'活动访问人数',value:0.8451}, { name:'20140309',group:'活动访问人数',value:0.8418}, { name:'20140310',group:'活动访问人数',value:0.8264},
							{ name:'20140311',group:'活动访问人数',value:0.8232}, { name:'20140312',group:'活动访问人数',value:0.8233}, { name:'20140313',group:'活动访问人数',value:0.8258}, { name:'20140314',group:'活动访问人数',value:0.8283}, { name:'20140315',group:'活动访问人数',value:0.8278}, { name:'20140316',group:'活动访问人数',value:0.8256},
							{ name:'20140317',group:'活动访问人数',value:0.8239}, { name:'20140318',group:'活动访问人数',value:0.8239}, { name:'20140319',group:'活动访问人数',value:0.8245}, { name:'20140320',group:'活动访问人数',value:0.8265}, { name:'20140321',group:'活动访问人数',value:0.8261}, { name:'20140322',group:'活动访问人数',value:0.8269},
							{ name:'20140323',group:'活动访问人数',value:0.8244}, { name:'20140324',group:'活动访问人数',value:0.8244}, { name:'20140325',group:'活动访问人数',value:0.8172}, { name:'20140326',group:'活动访问人数',value:0.8139}, { name:'20140327',group:'活动访问人数',value:0.8146}, { name:'20140328',group:'活动访问人数',value:0.8164},
							{ name:'20140329',group:'活动访问人数',value:0.8269}, { name:'20140330',group:'活动访问人数',value:0.8269}, { name:'20140329',group:'活动访问人数',value:0.8269}, { name:'20140401',group:'活动访问人数',value:0.8258}, { name:'20140402',group:'活动访问人数',value:0.8247}, { name:'20140403',group:'活动访问人数',value:0.8286},
							{ name:'20140404',group:'活动访问人数',value:0.8316}, { name:'20140405',group:'活动访问人数',value:0.832}, { name:'20140406',group:'活动访问人数',value:0.8333}, { name:'20140407',group:'活动访问人数',value:0.8352}, { name:'20140408',group:'活动访问人数',value:0.8357}, { name:'20140409',group:'活动访问人数',value:0.8355},
							{ name:'20140415',group:'活动访问人数',value:0.8403}, { name:'20140418',group:'活动访问人数',value:0.8403}, { name:'20140423',group:'活动访问人数',value:0.8406}, { name:'20140430',group:'活动访问人数',value:0.8403}, { name:'20140508',group:'活动访问人数',value:0.8396}, { name:'20140511',group:'活动访问人数',value:0.8418},
							{ name:'20140512',group:'活动访问人数',value:0.8384}, { name:'20140513',group:'活动访问人数',value:0.8386}, { name:'20140518',group:'活动访问人数',value:0.8372}, { name:'20140630',group:'活动访问人数',value:0.839}, { name:'20140725',group:'活动访问人数',value:0.84}, { name:'20140930',group:'活动访问人数',value:0.8389},
							{ name:'20141102',group:'活动访问人数',value:0.8423}, { name:'20141103',group:'活动访问人数',value:0.8423}, { name:'20150303',group:'活动访问人数',value:0.8435}];
							
    	       var option7 = {
    	    		legend:false, 
    	     		zoomType:'x', 
    	     		minRange:7*24*3600000, 
    	     		pointInterval:12*3600*1000,
    	     		yUnit:'k',
    	     		yDecimal:3 		
    	       };
    	       id7 = Highchart.create.TimeSeriesLine("container7", data1, option7);
    	       
    	       var option8 = {
    	    		valueName:'活动访问频率',
    	    		yUnit:'人次/分钟',
       	    		gaugeMin:0,
       	    		gaugeMax:1000,
       	    		stops:[0.2,0.4,0.6],
       	    		greatValue:'up'
       	       };
       	       id8 = Highchart.create.Gauge("container8", [698] , option8);
	       
		});
	</script>
</head>
  
  <body>

	<div class="page-content">
		<div class="kit-container">
			<div class="kit-row">
				<div class="kit-col" style="width:50%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart2"></i>饼图
							</h2>
							<div class="floatright">
								<a href="javascript:testReload();" class="button-more"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 300px">
							<div class="chart" id="container1"></div>
						</div>
					</div>
				</div>
				<div class="kit-col" style="width:50%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart1"></i>折线图
							</h2>
							<div class="btn-group floatright">
								<a href="javascript:testReloadLine();" class="kit-icon-medium kit-icon-export"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 300px">
							<div class="chart" id="container2"></div>
						</div>

					</div>
				</div>
			</div>
			<div class="kit-row">
				<div class="kit-col" style="width:50%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart2"></i>柱形图
							</h2>
							<div class="floatright">
								<a href="javascript:testReloadBar();" class="button-more"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 300px">
							<div class="chart" id="container3"></div>
						</div>
					</div>
				</div>
				<div class="kit-col" style="width:50%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart2"></i>堆积柱形图
							</h2>
							<div class="btn-group floatright">
								<a href="javascript:;" class="kit-icon-medium kit-icon-export"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 300px">
							<div class="chart" id="container4"></div>
						</div>

					</div>
				</div>
			</div>
			<div class="kit-row">
				<div class="kit-col" style="width:50%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart2"></i>堆积柱形图(百分比)
							</h2>
							<div class="floatright">
								<a href="javascript:;" class="button-more"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 350px">
							<div class="chart" id="container5"></div>
						</div>
					</div>
				</div>
				<div class="kit-col" style="width:50%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart2"></i>垂直折线图(动态加载)
							</h2>
							<div class="btn-group floatright">
								<a href="javascript:;" class="kit-icon-medium kit-icon-export"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 350px">
							<div class="chart" id="container6"></div>
						</div>

					</div>
				</div>
			</div>
			<div class="kit-row">
				<div class="kit-col" style="width:100%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart2"></i>时序图
							</h2>
							<div class="floatright">
								<a href="javascript:testReloadLineTime();" class="button-more"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 300px">
							<div class="chart" id="container7"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="kit-row">
				<div class="kit-col" style="width:50%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart2"></i>仪表盘
							</h2>
							<div class="floatright">
								<a href="javascript:testReloadGauge();" class="button-more"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 350px">
							<div class="chart" id="container8"></div>
						</div>
					</div>
				</div>
				<div class="kit-col" style="width:50%">
					<div class="panel">
						<div class="panel-head">
							<h2>
								<i class="kit-icon-medium kit-icon-chart2"></i>测试动态标签
							</h2>
							<div class="btn-group floatright">
								<a href="javascript:;" class="kit-icon-medium kit-icon-export"></a>
							</div>
						</div>
						<div class="panel-content" style="height: 350px">
							<div class="nodataBox">
                                <div class="nodata">
                                    <i class="PieChart-nodata  icon-nodata"></i>
                                    <div class="nodata-info">
                                        <p>对不起，没有查询到对应的数据。<br>建议你<a href="javascript:;">返回</a>或换个查询条件！<br>点击<a href="javascript:;" class="js-reason-btn">查看原因</a></p>
                                    </div>
                                </div>
                                <div class="reason">
                                    <div class="table-row">
                                        <div class="table-col-1 reason-demand">需求:</div>
                                        <div class="table-col-11 reason-text">1、主网基建投资完成金额不可以形象进度乘以投资计划金额计算。根据当前计划部提供的的计算方式：土建施工、电气施工、线路施工、物资到货的形象进度（即里程碑中完成百分比）乘以对应项的概算金额（即FBS费用分解结构中值)之和。</div>
                                    </div>
                                    <div class="table-row">
                                        <div class="table-col-1 reason-question">问题:</div>
                                        <div class="table-col-11 reason-text">本功能中，“年度累计至今投资完成值”中主网基建部分的分摊金额源系统未录入，即：“财务部提供的其他费用（如人工等费用）分摊至各项目的费用”在当前业务系统中无此数据，因此无法计算“年度累计至今投资完成值”。</div>
                                    </div>
                                </div>
                            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body> 
</html>
