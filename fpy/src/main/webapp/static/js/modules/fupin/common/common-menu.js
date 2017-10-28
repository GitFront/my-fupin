$(function(){
	$("#li_"+parentId).attr("class","on");
});
//加载页面
function loadPage(pageParentId,pageType,url){
	if(pageType==1){
		window.location.href = baseCtx + url+ "?parentId="+pageParentId;
	 }else if(pageType==0){
		window.location.href = baseCtx + "a?parentId="+pageParentId;			 
	 }
}
