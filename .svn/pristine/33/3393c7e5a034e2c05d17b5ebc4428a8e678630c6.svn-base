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
		/* 定义明细查看列表窗口 */
		var i = 0;
		/**
		 * 过滤条件的联动处理
		 */
		function linkage(rec) {
			var url = ctx + '/a/mmd/activities/activity/select?sourceId='+rec.source_id;
			$('#actId').combobox('reload', url);
		}
		
		/**
		 * 点击查看详情事件
		 */
		function checkInfo(actid,actname){
			$("#act_info").panel({title:"营销活动“"+actname+"”趋势分析"});
			$("#openXXXIframe")[0].src=ctx + "/a/mmd/activities/act_analyze.htm?actId="+actid;
			$("#act_info").window('open');
		}
		
		function formatOper(val,row,index){  
			var actid = row["act_id"];	
			var actname = row["act_name"];	
			return "<a href=\"javascript:void(0);\" onclick=\"checkInfo('"+actid+"','"+actname+"')\" class=\"view_button\">查 看</a>";
		} 
		
		function loadCss(data){
			 $(".view_button").linkbutton({text:'查 看',iconCls:'icon-btn-zoom-in',plain:true});
		}
	</script>
</head>
  
  <body>
	<rp:search-content singlerow="true" buttonrow="true" formid="act_search_form" btns="search:">
		<rp:search-cellbox title="地市">
			<select id="sourceId" name="sourceId" class="easyui-combobox" style="width:150px" 
				data-options="url:'${ctx}/a/mmd/activities/source/select',
					valueField:'source_id',textField:'source_name',editable:true,required:false,onSelect:linkage" ></select> 
		</rp:search-cellbox>
		<rp:search-cellbox title="活动名称">
			<select id="actId" name="actId" class="easyui-combobox " style="width:150px" 
				data-options="url:'${ctx}/a/mmd/activities/activity/select?sourceId=',
					valueField:'act_id',textField:'act_name',editable:true"  ></select>
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
			<rp:grid-col title="营销活动总体报表" >
				<rp:table-panel>
					<rp:grid id="activies_info" queryName="aspire.bi.activities.activity_market_list" 
							 pageSize="20" height="630" pages="true"
							 onLoadSuccess="loadCss">
						<rp:grid-field fieldId="source_name" headName="地市" align="center" sortable="true"/>
						<rp:grid-field fieldId="act_name" headName="活动名称" align="left" sortable="true"/>
						<rp:grid-field fieldId="act_start_time" headName="活动开始时间" align="center" sortable="true"/>
						<rp:grid-field fieldId="act_end_time" headName="活动结束时间" align="center"/>
						<rp:grid-field fieldId="vis_usrcnt" headName="访问人数" align="right"/>
						<rp:grid-field fieldId="join_usrcnt" headName="参与人数" align="right"/>
						<rp:grid-field fieldId="fans_rate" headName="粉丝参与率" align="right" unit="%"/>
						<rp:grid-field fieldId="share_usrcnt" headName="分享人数" align="right"/>
						<rp:grid-field fieldId="share_rete" headName="分享率" align="right" unit="%"/>
						<rp:grid-field fieldId="act_status" headName="当前情况" align="center"/>
						<rp:grid-field fieldId="act_id" headName="操作" align="center" formatter="formatOper"/>
					</rp:grid>
				</rp:table-panel>
			</rp:grid-col>
		</rp:row>
	</rp:content>
	
     <div id="act_info" class="easyui-window"  title="明细信息" style="width:1034px;height:530px;"
     		 data-options="iconCls:'icon-btn-zoom-in',modal:true,closed:true,draggable:true,resizable:true,collapsible:false">
     		<iframe scrolling="auto" id='openXXXIframe' frameborder="0"  src="" style="width:98.5%;height:93%;top: 29px;left: 8px"></iframe>
	</div>
	
	</body> 
</html>
