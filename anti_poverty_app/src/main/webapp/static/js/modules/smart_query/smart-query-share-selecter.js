$.namespace("modules.smartquery");

		/**
		 * 共享用户选择方法
		 * @param {} nogridid
		 * @param {} hasgridid
		 */
		function shareMoveIn(nogridid,hasgridid){
			common.utils.showLoading();
			var checkeds = $("#"+nogridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.closeLoading();
				common.utils.showErrorMsg("请选择需要共享数据的用户！");
				return;
			}
			/*更新共享列表*/
			for (var i = 0; i < checkeds.length ; i++) {
				var checkedid = checkeds[i].id;
				if(modules.smartquery.query.dataShare.shared.indexOf(checkedid) === -1){
					modules.smartquery.query.dataShare.shared.push(checkedid);
				}				
			}
			
			/* 通过左栏数据移动至右栏 */
			var hasSelecter = $("#"+hasgridid);
			var orows = hasSelecter.datagrid('getRows');
			var data = $.merge(orows,checkeds);
			hasSelecter.datagrid('loadData',{ "total":data.length,rows:data });
			/*刷新左栏数据数据*/
			var params = {userids:modules.smartquery.query.dataShare.shared.join(","),userinfo:modules.smartquery.query.dataShare.userinfo};
			var noSelecter = $("#"+nogridid);
			noSelecter.datagrid('unselectAll');
			noSelecter.datagrid('reload', params);	
			common.utils.closeLoading();
			
		}
		
		/**
		 * 共享用户删除方法
		 * @param {} nogridid
		 * @param {} hasgridid
		 */
		function shareMoveOut(nogridid,hasgridid){
			common.utils.showLoading();
			var checkeds = $("#"+hasgridid).datagrid('getChecked');
			if(checkeds.length === 0){
				common.utils.closeLoading();
				common.utils.showErrorMsg("请选择需要移除共享数据的用户！");
				return;
			}
			/*更新共享id列表*/
			for (var i = modules.smartquery.query.dataShare.shared.length-1; i >= 0 ; i--) {
				var sharedid = modules.smartquery.query.dataShare.shared[i];
				for (key in checkeds){
		            if(checkeds[key].id === sharedid)
		            	modules.smartquery.query.dataShare.shared.remove(i);
		        }
			}
			/* 通过左栏数据移动至右栏 */
			var hasSelecter = $("#"+hasgridid);
			/* 清除所选行 */
			for (var i = checkeds.length-1; i >= 0 ; i--) {
				var index = hasSelecter.datagrid('getRowIndex', checkeds[i]);
				hasSelecter.datagrid('deleteRow',index);
			}
			/*刷新左栏数据数据*/
			var params = {userids:modules.smartquery.query.dataShare.shared.join(","),userinfo:modules.smartquery.query.dataShare.userinfo};
			var noSelecter = $("#"+nogridid);
			noSelecter.datagrid('unselectAll');
			noSelecter.datagrid('reload', params);	
			common.utils.closeLoading();		
		}