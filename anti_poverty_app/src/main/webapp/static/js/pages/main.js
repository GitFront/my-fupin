$.namespace("aspire.birp");
aspire.birp.main = {
	init : function() {
	    aspire.birp.main.left.init();
		// 初始化tbs
		aspire.birp.main.tabs.init();
 	},
 	page:{
 		loadPage : function(parentId,pageType,url){
 			if(pageType==1){
 	 			window.location.href = baseCtx + url+ "?parentId="+parentId;
  	 		 }else if(pageType==0){
  	 			window.location.href = baseCtx + "a?parentId="+parentId;
 	 			 /*
 	 			 var localUrl = window.location.href.replaceAll('#','').trim();
 	  			 if(!(localUrl.endsWith("/a") || localUrl.endsWith("/a/"))){
 	 	 			window.location.href = baseCtx + "a";
 	 				aspire.birp.main.left.loadMenu(parentId);
  	 			 }else{
 	 				aspire.birp.main.left.loadMenu(parentId);
 	 			 }*/
 	 			 
 	 		 }
 		},
 		favorite : function (title) {
   		    window.external.AddFavorite(document.URL, title);
 		 }
 	},
 	left :{
 	/**
 	 * 初始化
 	 */
 	 init:	function (){
  		  $('.easyui-accordion li a').click(function(){
				var tabTitle = $(this).text();
				var url = $(this).attr("url");
				aspire.birp.main.tabs.addTab("id_"+tabTitle,tabTitle,url);
				$('.easyui-accordion li div').removeClass("selected");
				$(this).parent().addClass("selected");
			}).hover(function(){
				$(this).parent().addClass("hover");
			},function(){
				$(this).parent().removeClass("hover");
			});
  	 },
 	 loadMenu: function (parentId){
  		var level = 2;
 		//$.messager.progress();
 		var param = {};
 		param.parentId = parentId;
 		param.level = level;
  		$.ajax({
			type : 'post',
			data : param,
			dataType:"json",
			url : baseCtx + '/a/menus',
			success : function(menus){
				 if(menus!=null){
 				 var menulist = '<div id="treeContent" class="easyui-accordion" fit="true" border="false">';
  				 $.each(menus, function(i, n) {
				    	if(n.isShow==1){
				    		menulist += '<div title="'+n.name+'" style="padding:0px;overflow:auto;"><div>';
							menulist += '<ul>';
					        $.each(n.childList, function(j, o) {
					        	if(o.isShow==1){
									menulist += '<li><div><a id="'+o.id+'"  href="#" url=' + o.url + ' ><span class="icon '+o.icon+'" ></span>' + o.name + '</a></div></li> ';
					        	}
					        });
					       menulist+='</ul></div></div>';
  				    	}
				   });
 				   menulist+='</div>';
 				   $("#treeContentWest").html(menulist);
				   $('#treeContent').accordion(); 
		 		   aspire.birp.main.left.init();
     			   //$.messager.progress('close');
				 }
			},
			error :  function(res){
					if(res.responseText!=null && res.responseText != ''){
						$.messager.alert("错误", res.responseText, "error");
					}
				   //$.messager.progress('close');
				}
		});
 	 } 
 	},
	tabs : {
		/**
		 * 初始化
		 */
		init : function() {
			aspire.birp.main.$tabs = $("#mainTabs");
			if(!aspire.birp.main.$tabs.length)
				aspire.birp.main.$tabs = parent.$("#mainTabs");
		},
		addTab : function(id, title, href) {
			var content = "未实现";
			//var height = aspire.birp.main.$tabs.tabs('options').height - 35;
			if (href) {
				content = "<iframe src='" + href+ "' frameborder='0' style='width: 100%; height: 100%'></iframe>";
			}
			
			if (aspire.birp.main.$tabs.tabs("exists", title)) {// 如果tab已经存在,则选中并刷新该tab
				aspire.birp.main.$tabs.tabs("select", title);
				aspire.birp.main.$tabs.tabs('getSelected').panel('options').content = content;
				aspire.birp.main.$tabs.tabs('getSelected').panel('refresh');
			} else {
				aspire.birp.main.$tabs.tabs("add", {
							id : id,
							title : title,
							fit : true,
							closable : true,
							content : content
						});
			}
			
			
		}
	},
	closeTab : function(title){
		aspire.birp.main.$tabs.tabs("close",title);
	}
};