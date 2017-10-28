$(function(){
  /*顶部导航*/
  $(function(){
    $(".nav-ul li").hover(function() {
      $(this).find(".second-nav").show()
    }, function() {
      $(this).find(".second-nav").hide()
    });
  })
  /*end顶部导航*/


}) /*end all*/
