/*右侧图表切换*/
$(function(){

	//村内村外
	$("#tabIndexUl li").eq(0).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, 'cunneicunwai');
	});

	//贫困指数
	$("#tabIndexUl li").eq(1).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, TAB_NAMES.ZHIPINYUANYIN);
	});

	//致贫原因
	$("#tabIndexUl li").eq(2).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, 'pinkunshuxing');
	});

	//贫困属性
	$("#tabIndexUl li").eq(3).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, 'zhufangtiaojian');
	});

	//住房条件
	$("#tabIndexUl li").eq(4).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, 'laodonglifenxi');
	});

	//劳动力分析
	$("#tabIndexUl li").eq(5).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, 'jiankangqingkuang');
	});

	//健康情况
	$("#tabIndexUl li").eq(6).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, 'jiaoyuwenhua');
	});

	//教育文化
	$("#tabIndexUl li").eq(7).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, 'shebaoyibao');
	});

	//社保医保
	$("#tabIndexUl li").eq(8).click(function(event) {
		renderTabCommon(AREA_LEVEL, AREA_NOW, 'shebaoyibao');
	});

})
/*end 右侧图表切换*/