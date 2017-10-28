/*贫困分析切换地区*/
	//地图返回按钮
function mapBackBtn(name){
	$(".map-back-btn .up-area").text(name);
}
	//loading
function loadingShow(){
	$("#loadingDiv").removeClass('hide');
}
function loadingHide(){
	$("#loadingDiv").addClass('hide');
}



$(function(){
	// 地图放大缩小
$("#mapBigger").click(function(event) {
		Highmap.mapObject.mapZoom(0.5);
});
$("#mapSmaller").click(function(event) {

	Highmap.mapObject.mapZoom(2);
});
	// end地图放大缩小

})





function tabAreaCommon(){
	$("#countryDangan").addClass('hide');
	$("#rightInner .selector-title").text("致贫原因");
}

function tabProvince(areaName){
	tabAreaCommon();
	renderLocation(LEVELS.PROVINCE, areaName);
	renderTabCommon(LEVELS.PROVINCE, areaName, TAB_NAMES.ZHIPINYUANYIN);
	AREA_LEVEL="province";
 	AREA_NOW=areaName;
}

function tabCity(areaName){
	tabAreaCommon();
	renderLocation(LEVELS.CITY, areaName);
	renderTabCommon(LEVELS.CITY, areaName, TAB_NAMES.ZHIPINYUANYIN);
	AREA_LEVEL="city";
 	AREA_NOW=areaName;
}

function tabCounty(areaName){
	tabAreaCommon();
	renderLocation(LEVELS.COUNTY, areaName);
	renderTabCommon(LEVELS.COUNTY, areaName, TAB_NAMES.ZHIPINYUANYIN);
	AREA_LEVEL="county";
 	AREA_NOW=areaName;
}

function tabTown(areaName){
	tabAreaCommon();
	renderLocation(LEVELS.TOWN, areaName);
	renderTabCommon(LEVELS.TOWN, areaName, TAB_NAMES.ZHIPINYUANYIN);
	AREA_LEVEL="town";
 	AREA_NOW=areaName;
}


function tabCountry(areaName){
	$("#countryDangan").removeClass('hide');
	$("#rightInner .selector-title").text("致贫原因");
	renderLocation(LEVELS.COUNTRY, areaName);
	renderTabCommon(LEVELS.COUNTRY, areaName, TAB_NAMES.ZHIPINYUANYIN);
	AREA_LEVEL="country";
 	AREA_NOW=areaName;

}
/*end 切换地区*/






/*责任监控切换地区*/
function tabDutyProvince(){
	renderCheckAreaLevel1(440000000000);
    renderCheckAreaLevel2(441400000000);
}

function tabDutyCity(){
	renderCheckAreaLevel1(441400000000);
    renderCheckAreaLevel2(441481000000);
}

function tabDutyCounty(){
	renderCheckAreaLevel1(441481000000);
    renderCheckAreaLevel2(441481107000);
}
function tabDutyTown(){
    renderCheckAreaLevel1(441481107000);
}
/*end责任监控切换地区*/