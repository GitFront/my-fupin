$.namespace("aspire.birp.demo4");
aspire.birp.demo4.datagrid = {
		gridId:'dg',
		searchDiv:'searchDiv',
  		init:function(dataGridId,searchDivId){
  			
   			if(dataGridId!=null && undefined != dataGridId){
  				this.gridId = dataGridId;
  			}
   			if(searchDivId!=null && undefined != searchDivId){
  				this.searchDiv = searchDivId;
  			}
    		
			 $("#btn-search").click(this.queryOk);
			 $("#btn-reset").click(this.reset);
			 $("#btn-export").click(this.dataExport);
   			
  			aspire.birp.demo4.datagrid.$grid = $("#"+this.gridId);
  			//注册事件
 			aspire.birp.demo4.datagrid.$grid.datagrid({
 				onBeforeLoad:function(param){
 					var params = aspire.birp.demo4.datagrid.getQueryParams(aspire.birp.demo4.datagrid.searchDiv); 
 					for(var i = 0 ; i < params.length ; i++){
  						param[params[i].name] = params[i].value;
  					}
   				}
 			});
		},
 		queryOk:function(){
 			aspire.birp.demo4.datagrid.$grid.datagrid('reload');
 		},
		reset:function(){
			$("#merchant_id").val('');
		},
		getQueryParams:function(div){
 				var params = new Array();
				var temp = $("#" + div).find(":input");
				temp.each(function() {
					var temp = $(this).attr("name");
 					var s_ = $(this).val();
					if(s_!=null)
					s_ = Common.delHtml(s_);
					if (temp != null && undefined != temp) {
						//s[temp] = s_;
		 				var param = {};
		 				param.name = temp;
		 				param.value = s_;
		 				params.push(param);
					}
 	 			});
				return params;
			},
		dataExport:function(){
			
			var paramStr =JSON.stringify(aspire.birp.demo4.datagrid.getQueryParams(aspire.birp.demo4.datagrid.searchDiv)); 
			var param = {};
		    param.parameter = paramStr;
		    param.wpcfgId = aspire.birp.demo4.datagrid.wpcfgId;
		    
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