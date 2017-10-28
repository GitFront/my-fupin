// JavaScript Document
$(document).ready(function () {
    $('#Top .Toolbar1 .CentreBox .TopMenu .List1 li').click(function () {
        var index = $(this).parent().children().index(this);
        $(this).parent().children().each(function () {
            if ($(this).hasClass('Select')) {
                $(this).removeClass('Select');
            }
        });
        $(this).addClass('Select');

        $('#Top .Toolbar2 .CentreBox .TopMenu').each(function () {
            if (!$(this).hasClass('Hide')) {
                $(this).addClass('Hide');
            }
        });
        $('#Top .Toolbar2 .CentreBox .TopMenu').eq(index).removeClass('Hide');
    });
	
    $('#Top .Toolbar2 .CentreBox .TopMenu ul li a').mouseenter(function () {
        var index = $('#Top .Toolbar2 .CentreBox .TopMenu ul li a').index(this);
        $('#Top .Toolbar2 .CentreBox .TopMenu ul li').each(function () {
            if ($(this).hasClass('Select')) {
                $(this).removeClass('Select');
            }
        });
        $(this).parent().addClass('Select');
    });
});