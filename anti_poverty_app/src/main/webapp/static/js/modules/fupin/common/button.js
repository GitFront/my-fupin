$(function(){
	/*顶部导航*/
	//$(".nav-ul li a").click(function(event) {
	//	$(".nav-ul li").removeClass('on');
	//	$(this).parent().addClass('on');
	//});

  $(function(){
    $(".nav-ul li").hover(function() {
      $(this).find(".second-nav").show()
    }, function() {
      $(this).find(".second-nav").hide()
    });
  })
	/*end顶部导航*/


}) /*end all*/
