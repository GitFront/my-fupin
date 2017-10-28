/**
 * Highcharts-zh_CN plugins v0.0.2 (2015-04-19)
 *
 * (c) 2015 HCharts.cn http://www.hcharts.cn
 *
 * Author : John Doe, Blue monkey
 *
 * License: Creative Commons Attribution (CC)
 */

(function(H) {

    ABSOLUTE = H.ABSOLUTE;
    RELATIVE = H.RELATIVE;
    hasSVG = H.hasSVG;
    isTouchDevice = H.isTouchDevice;
    var defaultOptionsZhCn = {

        lang: {
            contextButtonTitle: '图表导出菜单',
            decimalPoint: '.',
            downloadJPEG: "下载JPEG图片",
            downloadPDF: "下载PDF文件",
            downloadPNG: "下载PNG文件",
            downloadSVG: "下载SVG文件",
            drillUpText: "返回 {series.name}",
            loading: '正在加载数据...',
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            noData: "对不起，查询不到任何数据信息！",
            numericSymbols: ['k', 'M', 'G', 'T', 'P', 'E'],
            printChart: "打印图表",
            resetZoom: '重置缩放比例',
            resetZoomTitle: '重置为原始大小',
            shortMonths: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            thousandsSep: ',',
            weekdays: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天']
        },

        global: {
            useUTC: true,
            timezoneOffset: 8 * 60, // +8
            canvasToolsURL: 'http://cdn.hcharts.cn/highcharts/modules/canvas-tools.js',
            VMLRadialGradientURL: 'http://cdn.hcharts.cn/highcharts/gfx/vml-radial-gradient.png'
        },

        title: {
            text: ''
        },

        tooltip: {
            dateTimeLabelFormats: {
                millisecond: '%Y-%m-%d %H:%M:%S.%L',
                second: '%Y-%m-%d %H:%M:%S',
                minute: '%Y-%m-%d %H:%M',
                hour: '%Y-%m-%d %H:%M',
                day: '%Y-%m-%d',
                week: '%Y , %e. %b',
                month: '%Y-%m',
                year: '%Y'
            },
            headerFormat: '<span style="font-size: 10px">{point.key}</span><br/>',
            pointFormat: '{series.name}: <b>{point.y}</b><br/>'

        },

        xAxis: {
            dateTimeLabelFormats: {
                millisecond: '%H:%M:%S.%L',
                second: '%H:%M:%S',
                minute: '%H:%M',
                hour: '%H:%M',
                day: '%Y/%m/%d',
                week: '%Y/%m/%d',
                month: '%Y/%m',
                year: '%Y'
            }
        }
    };

    H.setOptions(defaultOptionsZhCn);
}(Highcharts));