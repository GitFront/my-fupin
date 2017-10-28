<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<%@ include file="/WEB-INF/views/include/report.jsp"%>
<html>
<head>
	<!-- 引入地图数据 -->
	<script type="text/javascript" src="${ctxStatic}/js/maps/cn-gd-heyuan.geo.js"></script>
	
	<script type="text/javascript">
		$(function(){
			common.report.page.init("search_form_id");
		});
	
		function testclick(name,x,y){
			window.alert("数据点“"+name+"”已经被选中，x值为“" + x + "”,y值为“" + y+"” ");
		}
		function search(){
			window.alert("error!");
		}
		function reset(){
			window.alert("test1");
		}
		function toggle(e){
			if(e){
				window.alert("视图开启");
			}else{
				window.alert("视图关闭");
			}
		}
		function fok(){
			window.alert("ok!");
			
		}
	</script>
</head>
  
  <body>
	<rp:search-content singlerow="true" buttonrow="false" formid="search_form_id" btns="search:;reset:;download:">
		<rp:search-cellbox title="数据分组" columns="S2">
			<select id="group" class="easyui-combobox" name="type" style="width:120px;">   
				<option value="">全部</option>   
				<option value="1">分组1</option>   
				<option value="2">分组2</option>   
			</select> 
		</rp:search-cellbox>
		<rp:search-cellbox title="数据范围" columns="S2">
			<select id="space" class="easyui-combobox" name="space" style="width:120px;">   
				<option value="1">范围1</option>   
				<option value="2">范围2</option>  
			</select> 
		</rp:search-cellbox>
	</rp:search-content>
	
	<rp:content>
		<rp:row columns="true">
			<rp:col title="动态饼状图" icon="Pie" btns="button:{id:ok1,icon:icon-btn-ok,click:fok};toggle:{id:groups1,icon:icon-btn-time,click:toggle,name:小时视图}">
				<rp:panel-content height="300px">
					<hc:pie id="container1" queryName="aspire.bi.common.report.selectReport" parameters="type:pie" mapper="name:NAME;value:YVALUE" option="valueName:'累计访问人数';yUnit:'人'"/>
				</rp:panel-content>
			</rp:col>
			<rp:col title="动态柱状图" icon="Bar">
				<rp:panel-content height="300px">
					<hc:bars id="container2" queryName="aspire.bi.common.report.selectReport" parameters="type:group" mapper="name:NAME;value:YVALUE;group:RGROUP" option="horiz:true;doClick:'testclick'"/>
				</rp:panel-content>
			</rp:col>
		</rp:row>
		
		<rp:row columns="true">			
			<rp:col title="动态地图" icon="Map">
				<rp:panel-content height="400px">
					<hm:map joinBy="hy-key" mapData="countries/cn/custom/cn-gd-he-yuan" queryName="aspire.bi.common.report.selectReport" parameters="type:map" id="container20" 
							mapper="key:NAME;value:YVALUE;value1:XVALUE" option="mapName:'河源市';navigation:false;valueDecimal:2;valueMapper:[{key:'value',name:'业务量'},{key:'value1',name:'用户量'}]"/>
				</rp:panel-content>
			</rp:col>
			<rp:col title="动态折线图" icon="Line">
				<rp:panel-content height="400px">
					<hc:line id="container21" queryName="aspire.bi.common.report.selectReport" parameters="type:group" mapper="name:NAME;value:YVALUE;group:RGROUP" hideGroup="for"/>
				</rp:panel-content>
			</rp:col>
		</rp:row>
		
		<rp:row columns="true">
			<rp:col title="动态折线图" icon="Line">
				<rp:panel-content height="300px">
					<hc:line id="container3" queryName="aspire.bi.common.report.selectReport" parameters="type:group" mapper="name:NAME;value:YVALUE;group:RGROUP" hideGroup="for"/>
				</rp:panel-content>
			</rp:col>
			<rp:col title="动态堆积图" icon="Bar">
				<rp:panel-content height="300px">
					<hc:stack-bars id="container4" queryName="aspire.bi.common.report.selectReport" parameters="type:group" mapper="name:NAME;value:YVALUE;group:RGROUP"/>
				</rp:panel-content>
			</rp:col>
		</rp:row>
		
		
		<rp:row columns="true">
			<rp:col title="垂直折线图" icon="Line">
				<rp:panel-content height="300px">
					<hc:inverted-line id="container5" queryName="aspire.bi.common.report.selectReport" parameters="type:inverted" 
						mapper="x:YVALUE;y:XVALUE;group:RGROUP"
						option="yMin:-80"/>
				</rp:panel-content>
			</rp:col>
			<rp:col title="仪表盘" icon="Gauge">
				<rp:panel-content height="300px">
					<hc:gauge id="container6" queryName="aspire.bi.common.report.selectReport" parameters="type:gauge" mapper="value:YVALUE"/>
				</rp:panel-content>
			</rp:col>
		</rp:row>
	</rp:content>
	</body> 
</html>
