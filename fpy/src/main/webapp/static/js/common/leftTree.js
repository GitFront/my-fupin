
function navList(id) {
	var $obj = $("#menu"), $item = $("#menu_" + id);

	$item.removeClass("none").parent().addClass("selected");
	$obj.find(".list-title").hover(function() {
				$(this).addClass("hover");
			}, function() {
				$(this).removeClass("hover");
			});

	$obj.find(".list-title h5").click(function(e) {

				var $div = $(this).parent().siblings(".list-item");

				if ($(this).parent().parent().hasClass("selected")) {
					$div.slideUp(600);
					$(this).parent().parent().removeClass("selected");
				}
				if ($div.is(":hidden")) {
					$("#menu li").find(".list-item").slideUp(600);
					$("#menu li").removeClass("selected");
					$(this).parent().parent().addClass("selected");
					$div.slideDown(600);
					try {
						var openHandler = $div.attr('openHandler');
						var handler = eval("(" + openHandler + ")");
						handler($div);

					} catch (e) {
					}
				} else {
					$div.slideUp(600);
				}
			});
	$obj.find(".ico_tab13").click(function(e) {

				var $div = $(this).parent().parent().siblings(".list-item");
				if ($(this).parent().parent().parent().hasClass("selected")) {
					$div.slideUp(600);
					$(this).parent().parent().parent().removeClass("selected");
				}
				if ($div.is(":hidden")) {
					$("#menu li").find(".list-item").slideUp(600);
					$("#menu li").removeClass("selected");
					$(this).parent().parent().parent().addClass("selected");
					$div.slideDown(600);
					try {
						var openHandler = $div.attr('openHandler');
						var handler = eval("(" + openHandler + ")");
						handler($div);

					} catch (e) {
					}
				} else {
					$div.slideUp(600);
				}
			});
}
