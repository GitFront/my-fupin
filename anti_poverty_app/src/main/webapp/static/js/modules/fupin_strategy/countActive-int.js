//金额化数字,type=0时不带小数,否则带2位小数
    function formatMoney(s, type) {  
    // if (/[^0-9\.]/.test(s))  
    //     return "0";  
    // if (s == null || s == "")  
    //     return "0";  
    // s = s.toString().replace(/^(\d*)$/, "$1.");  
    // s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");  
    // s = s.replace(".", ",");  
    // var re = /(\d)(\d{3},)/;  
    // while (re.test(s))  
    //     s = s.replace(re, "$1,$2");  
    // s = s.replace(/,(\d\d)$/, ".$1");  
    // if (type == 0) {// 不带小数位(默认是有小数位)  
    //     var a = s.split(".");  
    //     if (a[1] == "00") {  
    //         s = a[0];  
    //     }  
    // }  
    return s;  
    } 

$.fn.countTo = function(a) {
    a = a || {};
    return $(this).each(function() {
        var c = $.extend({},
        $.fn.countTo.defaults, {
            from: $(this).data("from"),
            to: $(this).data("to"),
            speed: $(this).data("speed"),
            refreshInterval: $(this).data("refresh-interval"),
            decimals: $(this).data("decimals")
        },
        a);
        console.log(c)
        var h = Math.ceil(c.speed / c.refreshInterval),
        i = (c.to - c.from) / h;
        var j = this,
        f = $(this),
        e = 0,
        g = c.from,
        d = f.data("countTo") || {};
        f.data("countTo", d);
        if (d.interval) {
            clearInterval(d.interval)
        }
        d.interval = setInterval(k, c.refreshInterval);
        b(g);
        function k() {
            g += i;
            e++;
            b(g);
            if (typeof(c.onUpdate) == "function") {
                c.onUpdate.call(j, g)
            }
            if (e >= h) {
                f.removeData("countTo");
                clearInterval(d.interval);
                g = c.to;
                if (typeof(c.onComplete) == "function") {
                    c.onComplete.call(j, g)
                }
            }
        }
        function b(m) {
            var l = c.formatter.call(j, m, c);
            f.html(l)
        }
    })
};
$.fn.countTo.defaults = {
    from: 0,
    to: 0,
    speed: 1000,
    refreshInterval: 100,
    decimals: 0,
    formatter: formatter,
    onUpdate: null,
    onComplete: null
};
function formatter(b, a) {
        //return b.toFixed(2)  原始
     return  formatMoney(Math.round(b),0);//修改后,返回格式化金额
  
}
$("#count-number").data("countToOptions", {
    formatter: function(b, a) {
        return b.toFixed(2).replace(/\B(?=(?:\d{3})+(?!\d))/g, ",")
    }
});
// $(".timer-int").each(count);
// function count(a) {
//     var b = $(this);
//     a = $.extend({},
//     a || {},
//     b.data("countToOptions") || {});
//     b.countTo(a)
// };


