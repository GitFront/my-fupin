<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head-easyui-1.4.jsp"%>
<%@ include file="/WEB-INF/views/include/report.jsp"%>
<html>
<head>
	<script type="text/javascript">
		/**
		 * 过滤条件的联动处理
		 */
		/* function channelLinkage(rec) {
			var url ='';
			if(rec.ONE_LVL_CHANNEL_ID=='addup999'){
				url += ctx + '/a/aplus/digital-content/twolvlchannel/select';
			}else{
				url += ctx + '/a/aplus/digital-content/twolvlchannel/select?oneChannelId='+rec.ONE_LVL_CHANNEL_ID;
			}
			$('#twoLvlChannelID_array').combobox('reload', url);
			$('#twoLvlChannelID_array').combobox('setValue', "");
			$('#twoLvlChannelID_array').combobox('select', "addup999");
		} */

		//特定时间格式
		$.fn.datebox.defaults.formatter = function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+""+(m<10?('0'+m):m)+""+(d<10?('0'+d):d);
		};
		$.fn.datebox.defaults.parser = function(s){
			if (!s) return new Date();
			s.substring(0,3);
			var y = s.substring(0,4);
			var m = s.substring(4,6);
			var d = s.substring(6,8);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		};
		var from1=new Date();
		
		function day(){
			common.report.page.reload("selectTable","aspire.bi.common.report.selectTable");
		}
		function week(){
			common.report.page.reload("selectTable","aspire.bi.common.report.selectTable");
		}
		function month(){
			common.report.page.reload("selectTable","aspire.bi.common.report.selectTable");
		}
		$(function(){
			$('#from').datebox('setValue','${indate}');
			//$('#appId').combobox('select', "addup999");
			//$('#apId').combobox('select', "addup999");
			common.report.page.init('search_form','selectTable','表格展示');
		});
		function resetValue(){
			$("#oneLvlChannelID").combobox('select',"addup999");
			$('#twoLvlChannelID_array').combobox('setValue', "");
			$('#twoLvlChannelID_array').combobox('select', "addup999");
			$('#channelName').textbox('setValue', "");
			$('#channelId').textbox('setValue', "");
			$('#appId').textbox('setValue', "");
			$('#apId').textbox('setValue', "");
			$('#to').textbox('setValue', "");
		}
	</script>
</head>
  
  <body>
	<rp:search-content singlerow="false" buttonrow="true" formid="search_form" btns="search:;reset:resetValue();download:;">
		<rp:search-row>
			<rp:search-cellbox title="数据周期">
				<a href="javascript:;" class="easyui-linkbutton" data-options="toggle:true,group:'scyc',selected:true" onclick="javascript:day()">日报表</a>
				<a href="javascript:;" class="easyui-linkbutton" data-options="toggle:true,group:'scyc'" onclick="javascript:week()" style="margin-left: 10px;">周报表</a>
				<a href="javascript:;" class="easyui-linkbutton" data-options="toggle:true,group:'scyc'" onclick="javascript:month()" style="margin-left: 10px;">月报表</a>
			</rp:search-cellbox>
			<rp:search-cellbox title="开始时间">
				<input id="from" name="from" type="text" class="easyui-datebox" style="width:200px"></input>
			</rp:search-cellbox>
			<rp:search-cellbox title="结束时间">
				<input id="to" name="to" type="text" class="easyui-datebox" style="width:200px"></input>
			</rp:search-cellbox>
		</rp:search-row>
		<rp:search-row>
			<rp:search-cellbox title="一级渠道商">
				<select id="oneLvlChannelID" name="oneLvlChannelID" class="easyui-combobox" style="width:200px">
				<option value="">全部</option>
				<option value="1">测试</option>
						</select>
			</rp:search-cellbox>
			<rp:search-cellbox title="二级渠道商">
				<select id="twoLvlChannelID_array" name="twoLvlChannelID_array"  class="easyui-combobox" style="width:200px" >
						<option value="">全部</option>
						<option value="2">测试</option>
						</select>
			</rp:search-cellbox>
			<rp:search-cellbox title="渠道商名称">
				<input id="channelName" name="channelName" class="easyui-textbox" style="width:200px">
			</rp:search-cellbox>
			<rp:search-cellbox title="渠道商ID">
				<input id="channelId" name="channelId" class="easyui-textbox" style="width:200px">
			</rp:search-cellbox>
		</rp:search-row>
		<rp:search-row>
			<rp:search-cellbox title="应用名称">
				<select id="appId" name="appId" class="easyui-combobox" style="width:200px" >
				<option value="">全部</option>
				<option value="3">测试</option>
						</select>
			</rp:search-cellbox>
			<rp:search-cellbox title="AP名称">
				<select id="apId" name="apId" class="easyui-combobox" style="width:200px" >
				<option value="">全部</option>
				<option value="4">测试</option>
						</select>
			</rp:search-cellbox>
		</rp:search-row>
	</rp:search-content>
	
	<rp:content>
		<rp:row>
			<rp:grid-col  title="渠道销售情况" >
				<rp:table-panel>
					<rp:grid id="selectTable" queryName="aspire.bi.common.report.selectTable" 
					parameters="from:${indate};"
					pageSize="20"  pages="true" autoHeight="true">
						<rp:grid-field fieldId="STAT_TIME" headName="日期" align="center" width="120" sortable="true"/>
						<rp:grid-field fieldId="ONE_LVL_CHANNEL_NAME" headName="一级合作渠道名称" align="left"/>
						<rp:grid-field fieldId="TWO_LVL_CHANNEL_NAME" headName="二级级合作渠道名称" align="left"/>
						<rp:grid-field fieldId="APP_NAME" headName="应用名称" align="left"/>
						<rp:grid-field fieldId="AP_NAME" headName="AP名称" align="left"/>
						<rp:grid-field fieldId="NEW_USER_CNT" headName="新增用户数" align="right"/>
						<rp:grid-field fieldId="ACT_USER_CNT" headName="启动用户数" align="right"/>
						<rp:grid-field fieldId="FEE_USER_CNT" headName="付费用户数" align="right"/>
						<rp:grid-field fieldId="FEE_CNT" headName="付费次数" align="right"/>
						<rp:grid-field fieldId="FEE_AMOUNT" headName="付费金额" align="right"/>
						<rp:grid-field fieldId="NEW_FEE_CR" headName="新增付费转换率" unit="%" align="right"/>
						<rp:grid-field fieldId="ACT_FEE_CR" headName="启动付费转换率" unit="%" align="right"/>
					</rp:grid>
				</rp:table-panel>
			</rp:grid-col>
		</rp:row>
	</rp:content>
	
	</body> 
</html>
