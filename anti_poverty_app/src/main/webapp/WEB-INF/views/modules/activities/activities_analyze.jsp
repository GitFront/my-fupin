<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/param.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<%@ include file="/WEB-INF/views/include/report.jsp"%>
<html>
<head>
	
	<script type="text/javascript">
		$(function(){
			common.report.page.init("act_search_form");
		});
		/*定义首次加载参数*/
		var firstLoad = true;
		/*定义界面报表的时间类型*/
		var dateType = "date";
		/* 界面初始化方法 */
		/**
		 * 过滤条件的联动处理
		 */
		function linkage(rec) {
			var url = ctx + '/a/mmd/activities/activity/select?sourceId='+rec.source_id;
			$('#actId').combobox('reload', url);
		}
		
		/**
		 * 过滤条件的联动处理
		 */
		function actSelectLoad(){
			var data = $('#actId').combobox('getData');
			if(data == null || data == '') {
				$('#actId').combobox('select',''); 
			} else {
				if(firstLoad){
					$('#actId').combobox('select','${actId}');
					firstLoad = false;
				}else{
					$('#actId').combobox('select',data[0].act_id);
				}
							
			}
		}
		/* 定义自定义界面查询方法 */
		function search(){
			var activies = $('#actId').combobox('getValue');
			if(common.utils.isEmpty(activies)){
				$.messager.alert("警告","请至少选择一个营销活动！");
				return;
			}
			/* 重新加载界面元素 */
			common.report.page.reload("container_table");
			if(dateType === "hour"){
				/* 小时视图 */
				common.report.page.reload("activies_time_series","aspire.bi.activities.activity_market_by_hour");
				common.report.page.reload("activies_grid_data","aspire.bi.activities.activity_market_by_hour");
			}else{
				/* 日期视图 */
				common.report.page.reload("activies_time_series","aspire.bi.activities.activity_market_by_date");
				common.report.page.reload("activies_grid_data","aspire.bi.activities.activity_market_by_date");
			}
		}
		
		/**
		 * 时间类型切换
		 */
		function typeSwitch(e){
			if(e){
				/* 小时视图 */
				dateType = "hour";
				common.report.page.reload("activies_time_series","aspire.bi.activities.activity_market_by_hour");
				common.report.page.reload("activies_grid_data","aspire.bi.activities.activity_market_by_hour");
			}else{
				/* 日期视图 */
				dateType = "date";
				common.report.page.reload("activies_time_series","aspire.bi.activities.activity_market_by_date");
				common.report.page.reload("activies_grid_data","aspire.bi.activities.activity_market_by_date");
			}
		}
	</script>
</head>
  
  <body>
	<rp:search-content singlerow="true" buttonrow="true" formid="act_search_form" btns="search:search">
		<rp:search-cellbox title="地市">
			<select id="sourceId" name="sourceId" class="easyui-combobox" style="width:150px" 
				data-options="url:'${ctx}/a/mmd/activities/source/select',
					valueField:'source_id',textField:'source_name',editable:true,required:false,onSelect:linkage" ></select> 
		</rp:search-cellbox>
		<rp:search-cellbox title="活动名称">
			<select id="actId" name="actId" class="easyui-combobox " style="width:150px" 
				data-options="url:'${ctx}/a/mmd/activities/activity/select?sourceId=',
					valueField:'act_id',textField:'act_name',editable:true,required:true,onLoadSuccess:actSelectLoad"  ></select>
		</rp:search-cellbox>
		<rp:search-cellbox title="起始日期">
			<input id="from" name="from" type="text" class="easyui-datebox"></input>
		</rp:search-cellbox>
		<rp:search-cellbox title="结束日期">
			<input id="to" name="to" type="text" class="easyui-datebox"></input>
		</rp:search-cellbox>
	</rp:search-content>
	<rp:content>
		<rp:row>
			<rp:col title="营销活动累计分析数据" icon="">
				<rp:table-panel>
					<rp:info-table id="container_table" thead="访问情况;参与情况;分享情况;广告点击情况" 
						 queryName="aspire.bi.activities.activity_market_info" parameters="actId:${actId}">
						<rp:info-tr rowno="1">
							<rp:info-td label="访问人数" unit="人" fieldName="vis_usrcnt"></rp:info-td>
							<rp:info-td label="参与人数" unit="人" fieldName="join_usrcnt"></rp:info-td>
							<rp:info-td label="分享人数" unit="人" fieldName="share_usrcnt"></rp:info-td>
							<rp:info-td label="广告点击人数" unit="人" fieldName="adclk_usrcnt"></rp:info-td>
						</rp:info-tr>
						<rp:info-tr rowno="2">
							<rp:info-td label="访问次数" unit="次" fieldName="vis_cnt"></rp:info-td>
							<rp:info-td label="参与转化率" unit="%" fieldName="join_rate"></rp:info-td>
							<rp:info-td label="分享次数" unit="次" fieldName="share_cnt"></rp:info-td>
							<rp:info-td label="广告点击次数" unit="次" fieldName="adclk_cnt"></rp:info-td>
						</rp:info-tr>
						<rp:info-tr rowno="3">
							<rp:info-td label=""></rp:info-td>
							<rp:info-td label="粉丝转化率" unit="%" fieldName="fans_rate"></rp:info-td>
							<rp:info-td label="分享率" unit="%" fieldName="share_rete"></rp:info-td>
							<rp:info-td label="广告点击率" unit="%" fieldName="adclk_rate"></rp:info-td>
						</rp:info-tr>
					</rp:info-table>
				</rp:table-panel>
			</rp:col>
		</rp:row>
		<rp:row>
			<rp:col title="营销活动变化趋势分析" icon="Line" btns="toggle:{icon:icon-btn-time,click:typeSwitch,name:小时视图}">
				<rp:panel-content height="300px">
					<hc:time-series-line id="activies_time_series" queryName="aspire.bi.activities.activity_market_by_date"
						parameters="actId:${actId}"
						mapper="name:start_time" 
						option="yMin:0"
						hideGroup="分享人数;分享次数;广告点击人数;广告点击次数"
						rowsGroup="vis_usrcnt:访问人数;vis_cnt:访问次数;join_usrcnt:参与人数;share_usrcnt:分享人数;share_cnt:分享次数;adclk_usrcnt:广告点击人数;adclk_cnt:广告点击次数"/>
				</rp:panel-content>
			</rp:col>
		</rp:row>
		<rp:row>
			<rp:grid-col title="营销活动变化趋势明细列表" icon="Table">
				<rp:table-panel>
					<rp:grid id="activies_grid_data" queryName="aspire.bi.activities.activity_market_by_date"
							 parameters="actId:${actId}"
							 height="340" pages="true" pageSize="10">
						<rp:grid-field fieldId="start_time" headName="日期" align="center" sortable="true"/>
						<rp:grid-field fieldId="vis_usrcnt" headName="访问人数" align="right"/>
						<rp:grid-field fieldId="vis_cnt" headName="访问次数" align="right"/>
						<rp:grid-field fieldId="join_usrcnt" headName="参与人数" align="right"/>
						<rp:grid-field fieldId="join_rate" headName="参与转化率" align="right" unit="%"/>
						<rp:grid-field fieldId="fans_rate" headName="粉丝参与率" align="right" unit="%"/>
						<rp:grid-field fieldId="share_usrcnt" headName="分享人数" align="right"/>
						<rp:grid-field fieldId="share_cnt" headName="分享次数" align="right"/>
						<rp:grid-field fieldId="share_rete" headName="分享率" align="right" unit="%"/>
						<rp:grid-field fieldId="adclk_usrcnt" headName="广告点击人数" align="right"/>
						<rp:grid-field fieldId="adclk_cnt" headName="广告点击次数" align="right"/>
						<rp:grid-field fieldId="adclk_rate" headName="广告点击率" align="right" unit="%"/>
					</rp:grid>
				</rp:table-panel>
			</rp:grid-col>
		</rp:row>
	</rp:content>
	
	
	</body> 
</html>
