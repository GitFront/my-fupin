$(function(){
	if(parentId=='d5913e77cbfd429383cc8af8d28c6e85'){
		$("#li_"+parentId).attr("class","search");
	}else{
		$("#li_"+parentId).attr("class","on");
	}
});
//加载页面
function loadPage(pageParentId,pageType,url){
	if(pageType==1){
		window.location.href = baseCtx + url+ "?parentId="+pageParentId;
	 }else if(pageType==0){
		window.location.href = baseCtx + "a?parentId="+pageParentId;			 
	 }
}
