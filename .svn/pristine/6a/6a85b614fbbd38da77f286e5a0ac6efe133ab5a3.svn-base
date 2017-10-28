$(function(){
	/*设置 page-content的top值,使搜索条件与page内容不会重叠  page1.html*/
	$( ".page-content").css("top",$( "#head-search").outerHeight() - 3 +"px");
	/*多行查询条件 收缩样式*/
	var searchContainer = $("#head-search").find(".search-container");
	if(searchContainer.size()&&$("#search-toggle").size()){
	    var rows = searchContainer.find(".row"),
	        toggleBtn = $("#search-toggle");
	
	    rows.each(function(){
	        if($(this).index()==0){
	            $(this).addClass("bottomborder");
	        }
	        if($(this).index()==0 || $(this).is(":last-child")){
	            $(this).show();
	        }else{
	            $(this).hide();
	        }
	    });
	    $( ".page-content").css("top",$( "#head-search").outerHeight() - 3 +"px");
	    toggleBtn.on("click",function(){
	        if($(this).hasClass("searchOpen")){
	            rows.each(function(){
	                if($(this).index()==0){
	                    $(this).addClass("bottomborder");
	                }
	                if($(this).index()==0  || $(this).is(":last-child")){
	                    $(this).show();
	                }else{
	                    $(this).hide();
	                }
	            });
	            $(this).html("点击展开<i class='kit-icon kit-icon-arrowdown3'></i>");
	            $( ".page-content").css("top",$( "#head-search").outerHeight() - 3 +"px");
	        }else{
	            rows.each(function(){
	                $(this).show().removeClass("bottomborder");
	            });
	            $(this).html("点击收起<i class='kit-icon kit-icon-arrowdown4'></i>");
	            $( ".page-content").css("top",$( "#head-search").outerHeight() - 3 +"px");
	        }
	        rows.filter(":visible").each(function(i){
	            if(i == (rows.filter(":visible").size()-1) ){
	                $(this).addClass("bottomborder")
	            }
	        })
	        $(this).toggleClass("searchOpen");
	
	    })
	}
	/*tabs切换内容*/
 	$(".js-kit-tabs").tabs({
		create : function(event, ui) {
			var $tab_btns = $(this).find(".ui-tabs-nav >li");

			$(this).removeClass("ui-corner-all").addClass("kit-tabs")
					.find(".ui-corner-all").removeClass("ui-corner-all");
			$(this).find(".ui-widget-header").removeClass("ui-widget-header");
			$(this).find(".ui-corner-top").removeClass("ui-corner-top");

			$tab_btns.each(function() {
						$("<i class='tab-btn-left'></i><i class='tab-btn-right'></i>")
								.appendTo($(this));
					})
		}
	});
	/*tab 切换*/
	$( ".js-tabs" ).tabs({
	  create: function() {
	      $( this).removeClass( "ui-corner-all" ).find( ".ui-corner-all").removeClass( "ui-corner-all").end().find( ".ui-corner-top").removeClass( "ui-corner-top" );
		}
  	});
  	
});
