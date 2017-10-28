$.namespace("modules.smartquery");		
		
		/**
		 * 显示列选择器保存方法
		 * @param {} nogridid
		 * @param {} hasgridid
		 */
		function selectMoveIn(nogridid,hasgridid){
			common.utils.showLoading();
			var checkeds = $("#"+nogridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.closeLoading();
				common.utils.showErrorMsg("请选择要移入的显示列！");
				return;
			}
			var columnids = new Array();
			for (var i = 0; i < checkeds.length ; i++) {
				columnids.push(checkeds[i].id);
			}
			var url = ctx + "/a/smart-query/query/queryselect/savelist";
			var params = {
					sqId:modules.smartquery.query.sqId,
					isTemp:modules.smartquery.query.isTemp,
					columnids:columnids
			};
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
	            	common.utils.closeLoading();
	             	if(rs){
	             		modules.smartquery.query.columns.reloadColumnSelecter();	             		
	             	}else{
	    				common.utils.showErrorMsg("查询显示列选择失败，请稍候再试！");
	             	}
	             }
	         });
		}
		
		/**
		 * 显示列选择器删除方法
		 * @param {} nogridid
		 * @param {} hasgridid
		 */
		function selectMoveOut(nogridid,hasgridid){
			common.utils.showLoading();
			var checkeds = $("#"+hasgridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.closeLoading();
				common.utils.showErrorMsg("请选择要移出的数据列！");
				return;
			}
			var columnids = new Array();
			for (var i = checkeds.length-1; i >= 0 ; i--) {
				columnids.push(checkeds[i].id);
			}
			var url = ctx + "/a/smart-query/query/queryselect/removelist";
			var params = {
					sqId:modules.smartquery.query.sqId,
					isTemp:modules.smartquery.query.isTemp,
					columnids:columnids
			};
			$.ajax({
	             type: "POST",
	             url: url,
	             data: params,
	             dataType: "json",
	             success: function(rs){
		            common.utils.closeLoading();
	             	if(rs){
	             		/* 刷新选择器列表 */
	             		modules.smartquery.query.columns.reloadColumnSelecter();
	             	}else{
	    				common.utils.showErrorMsg("查询显示列移除失败，请稍候再试！");
	             	}
	             }
	         });
		}