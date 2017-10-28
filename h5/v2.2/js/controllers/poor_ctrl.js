/*贫困分析*/
	//地图人员添加的代码
     var map_index=null;
	 var map_name=null;
     function selectMap(name){
        var mapSeries = {};
         $.each(Highmap.mapObject.series, function(i,s) {
             if(s.userOptions['type'] === 'map')
                 mapSeries = s;
         });
          var points=mapSeries.points;
        if(map_index!=null&&map_name!=name){
             $.each(points,function(i,v){
                points[i].select(false);
             });
            }
         $.each(points,function(i,v){
             if(name==v.name){
                 map_index=i;
                 map_name=v.name;
                 if(points[i].selected){
                    points[i].select(false);
                 }else{
                     points[i].select();
                 }
             }

         })
     }//end 地图人员添加的代码

$(function(){
renderLocation(LEVELS.PROVINCE, 'guangdong');
renderTabCommon(LEVELS.PROVINCE, 'guangdong', 'zhipinyuanyin');

/*贫困指数排行点击
*/
 $(document).on("click",".skillbar-title",function(event) {
        _this=event.target;
        var _index=$(_this).data("index");
        var index=Number(_index);
	 selectMap(RANK_ARR[index]);
	  if($(_this).parents(".skillbar").hasClass('check')){
	  	$(_this).parents(".skillbar").removeClass('check');
	  	return 0;
	  }
      $(_this).parents("#leftRank").find(".skillbar").removeClass('check');
	  $(_this).parents(".skillbar").addClass('check');
     });
/*end 贫困指数排行点击
*/


/*指标下拉selector*/
	$(".selector-title").click(function(event) {
		$(this).parents(".selector-div").focus();
		if(!$(this).hasClass('checked')){
      	  $(this).next().show();
       	 $(this).addClass('checked');
        }
        else{
        	$(this).next().hide();
        	 $(this).removeClass('checked');
        }
	});

	$(".selector-div .selector-ul li").click(function(event) {
		var _str=$(this).text();
		$(this).parents(".selector-div").find(".selector-title").removeClass('checked');
		$(this).parents(".selector-div").find(".selector-title").text(_str);
		$(this).parent().hide();
	});

	$(".selector-div").bind("blur", function () {
		 $(this).find(".selector-title").removeClass('checked');
		 $(this).find(".selector-ul").hide();
	});
	/*end指标下拉selector*/

	/*收缩按钮*/
	$("#mainRightShrink .arrow-right").click(function(event) {
		if(!$(this).hasClass('arrow-left')){
			$("#poorMainRight").css("width","0px");
			$("#rightInner .selector-div,#rightInner .chart-div").hide();
			$("#rightInner").animate({width:"0px",opacity:"0"});
			$(this).removeClass('arrow-right');
			$(this).addClass('arrow-left');

		}
		else{
			$("#poorMainRight").css("width","415px");
			$("#rightInner").animate({width:"385px",opacity:"1"},function(){
				$("#rightInner .selector-title").text("致贫原因");
				renderTabCommon(AREA_LEVEL, AREA_NOW, 'zhipinyuanyin');
				$("#rightInner .selector-div,#rightInner .chart-div").show();
			});
			$(this).removeClass('arrow-left');
			$(this).addClass('arrow-right');

		}
	});

	/*end 收缩按钮*/

	/*村档案*/
	$("#countryDangan").click(function(event) {
		$.poorDetail.showCountry('xinzhai');//country_name为村的全拼音
	});
	/*end村档案*/
	
})/*end贫困分析*/