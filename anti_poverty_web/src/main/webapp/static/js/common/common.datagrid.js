$.namespace("common.utils");
common.utils.datagrid = {
		gridId:'dataGrid',
		searchDiv:'searchDiv',
		wpcfgId:'',
  		init:function(dataGridId,searchDivId,wpcfgId){
  			
  			if(dataGridId!=null && undefined != dataGridId){
  				this.gridId = dataGridId;
  			}
   			if(searchDivId!=null && undefined != searchDivId){
  				this.searchDiv = searchDivId;
  			}
   			this.wpcfgId = wpcfgId;
   			$("#queryBtn").click(this.queryOk);
   			$("#exportBtn").click(this.dataExport);
   			
   			//$("#resetDevice").click(ipnet.emgy.device.query.reset);
   			
 			common.utils.datagrid.$grid = $("#"+this.gridId);
  			//注册事件
 			common.utils.datagrid.$grid.datagrid({
 				onBeforeLoad:function(param){
 					// 为适应低能的struct2 进而把json对象 转换 返回给后台处理
 					var paramStr =JSON.stringify(common.utils.datagrid.getQueryParams(common.utils.datagrid.searchDiv)); 
  					param.parameter = paramStr;
  				}
 			});
		},
 		queryOk:function(){common.utils.datagrid.$grid.datagrid('reload');},
		reset:function(){},
		getQueryParams:function(div){
 				var params = new Array();
				var temp = $("#" + div).find(":input");
				temp.each(function() {
					var temp = $(this).attr("name");
					var dataType = $(this).attr("dataType");
					var s_ = $(this).val();
					if(s_!=null)
					s_ = Common.delHtml(s_);
					if (temp != null && undefined != temp) {
						//s[temp] = s_;
		 				var param = {};
		 				param.name = temp;
		 				param.dataType = dataType;
		 				param.value = s_;
		 				params.push(param);
					}
 	 			});
				return params;
			},
		dataExport:function(){
			var paramStr =JSON.stringify(common.utils.datagrid.getQueryParams(common.utils.datagrid.searchDiv)); 
			var param = {};
		    param.parameter = paramStr;
		    param.wpcfgId = common.utils.datagrid.wpcfgId;
		    
		    //实现post形式
		    var form=$("<form>");//定义一个form表单
		    form.attr("style","display:none");
		    form.attr("target","");
		    form.attr("method","post");
		    form.attr("action",basePath + '/ExportDataAction_export.do');
		    
		    var input1=$("<input>");
		    input1.attr("type","hidden");
		    input1.attr("name","parameter");
		    input1.attr("value",param.parameter);
		    
		    var input2=$("<input>");
		    input2.attr("type","hidden");
		    input2.attr("name","wpcfgId");
		    input2.attr("value",param.wpcfgId);
		    
		    //将表单放置在web中
		    $("body").append(form);
		    form.append(input1);
		    form.append(input2);

		    form.submit();//表单提交 
		    form.remove();//下载后删除
 		    
		}

		
}